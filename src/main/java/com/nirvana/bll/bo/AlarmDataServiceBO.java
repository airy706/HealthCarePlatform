package com.nirvana.bll.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nirvana.app.vo.ExceptionVO;
import com.nirvana.bll.service.AlarmDataService;
import com.nirvana.dal.api.AlarmDataDao;
import com.nirvana.dal.api.UserDao;
import com.nirvana.dal.po.AlarmData;
import com.nirvana.dal.po.User;

@Service
@Transactional
public class AlarmDataServiceBO implements AlarmDataService {

	@Autowired
	private AlarmDataDao alarmdatadao;

	@Autowired
	private UserDao userdao;

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

	@Override
	public List<ExceptionVO> findAllRedo() {
		List<AlarmData> list = alarmdatadao.findAll();
		List<ExceptionVO> exs = new ArrayList<ExceptionVO>();
		for (AlarmData data : list) {
			User user = userdao.findByDid(data.getDid());
			exs.add(new ExceptionVO(data, user));
		}
		return exs;
	}

	@Override
	public List<ExceptionVO> detect(Integer id) {
		AlarmData data = alarmdatadao.findOne(id);
		List<AlarmData> list = alarmdatadao.findAfter(data.getStatus_change_time());
		List<ExceptionVO> exs = new ArrayList<ExceptionVO>();
		for(AlarmData alarm:list){
			User user = userdao.findByDid(alarm.getDid());
			exs.add(new ExceptionVO(alarm,user));
		}
		return exs;
	}
}
