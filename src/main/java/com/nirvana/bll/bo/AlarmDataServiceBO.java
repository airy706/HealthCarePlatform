package com.nirvana.bll.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nirvana.bll.service.AlarmDataService;
import com.nirvana.dal.api.AlarmDataDao;
import com.nirvana.dal.po.AlarmData;

@Service
@Transactional
public class AlarmDataServiceBO implements AlarmDataService {

	@Autowired
	private AlarmDataDao alarmdatadao;

	@Override
	public void addData(AlarmData data) {
		data.setHasresloved(0);
		AlarmData latest = alarmdatadao.findLatest(data.getReasontype(), data.getDid());
		// 时间间隔设为 30min;
		if (latest == null) {
			alarmdatadao.save(data);
		} else {
			long between = data.getStatus_change_time().getTime() - latest.getStatus_change_time().getTime();
			if (between > 30 * 60000) {
				alarmdatadao.save(data);
			}
		}
	}
}
