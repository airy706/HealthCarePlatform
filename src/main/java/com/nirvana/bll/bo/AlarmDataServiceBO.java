package com.nirvana.bll.bo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nirvana.app.vo.AlarmCommunityVO;
import com.nirvana.app.vo.AlarmFilterVO;
import com.nirvana.app.vo.ExceptionVO;
import com.nirvana.bll.service.AlarmDataService;
import com.nirvana.dal.api.AlarmDataDao;
import com.nirvana.dal.api.CommunityDao;
import com.nirvana.dal.api.UserDao;
import com.nirvana.dal.po.AlarmData;
import com.nirvana.dal.po.Community;
import com.nirvana.dal.po.User;

@Service
@Transactional
public class AlarmDataServiceBO implements AlarmDataService {

	@Autowired
	private AlarmDataDao alarmdatadao;

	@Autowired
	private UserDao userdao;

	@Autowired
	private CommunityDao communitydao;

	private PageRequest buildPageRequest(int pageNumber, int pagzSize) {
		return new PageRequest(pageNumber - 1, pagzSize, null);
	}

	@Override
	public void addData(AlarmData data) {
		data.setHasresloved(0);
		PageRequest request = this.buildPageRequest(1, 1);
		AlarmData latest = alarmdatadao.findLatest(data.getReasontype(), data.getDid(), request).getContent().get(0);
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
		List<AlarmData> list = alarmdatadao.findUnresloved();
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
		for (AlarmData alarm : list) {
			User user = userdao.findByDid(alarm.getDid());
			exs.add(new ExceptionVO(alarm, user));
		}
		return exs;
	}

	@Override
	public List<ExceptionVO> findAlltype() {
		List<Integer> list = alarmdatadao.findAlltype();
		List<ExceptionVO> exs = new ArrayList<ExceptionVO>();
		for (Integer alarm : list) {
			exs.add(new ExceptionVO(alarm));
		}
		return exs;
	}

	@Override
	public List<ExceptionVO> findAllTimes() {
		List<ExceptionVO> list = findAlltype();
		for (ExceptionVO vo : list) {
			Integer times = alarmdatadao.findTypeTimes(vo.getAlarmType());
			vo.setAlarmTimes(times);
		}
		return list;
	}

	@Override
	public AlarmFilterVO findByFilter(String[] ids, String[] types, Date start, Date end) {
		List<Integer> typesint = new ArrayList<Integer>();
		List<Integer> communityids = new ArrayList<Integer>();
		for (int i = 0; i < types.length; i++) {
			if (!"".equals(types[i])) {
				typesint.add(Integer.parseInt(types[i]));
			}
		}
		for (int i = 0; i < ids.length; i++) {
			if (!"".equals(ids[i])) {
				communityids.add(Integer.parseInt(ids[i]));
			}
		}
		if (typesint.size() == 0) {
			List<Integer> tps = alarmdatadao.findAlltype();
			for (Integer t : tps) {
				typesint.add(t);
			}
		}
		if (communityids.size() == 0) {
			List<Community> coms = communitydao.findAll();
			for (Community community : coms) {
				communityids.add(community.getCommunityid());
			}
		}
		AlarmFilterVO filtervo = new AlarmFilterVO();
		List<String> names = new ArrayList<String>();
		List<AlarmCommunityVO> data = new ArrayList<AlarmCommunityVO>();
		for (Integer id : communityids) {
			Community community = communitydao.findOne(id);
			names.add(community.getCommunityname());
			AlarmCommunityVO datavo = new AlarmCommunityVO();
			datavo.setName(community.getCommunityname());
			List<String> dids = userdao.findAlldid(id);
			List<Integer> times = null;
			if (dids.size() == 0) {
				times = analyseTimes(null, start.getTime(), end.getTime());
			} else {
				List<AlarmData> list = alarmdatadao.findFilter(typesint, dids, start, end);
				System.out.println(list.toString());
				times = analyseTimes(list, start.getTime(), end.getTime());
				System.out.println("over");
			}
			datavo.setData(times);
			data.add(datavo);
		}
		filtervo.setNames(names);
		filtervo.setData(data);
		return filtervo;
	}

	private List<Integer> analyseTimes(List<AlarmData> list, long start, long end) {

		List<Integer> times = new ArrayList<Integer>();
		List<AlarmData> copy = null;
		long now = 0;
		Integer count = 0;
		if (list == null || list.size() == 0) {
			long num = (end - start) / (24 * 60 * 60 * 1000);
			for (int i = 0; i < num; i++) {
				times.add(0);
			}
			return times;
		}
		while (true) {
			copy = new ArrayList<AlarmData>(list);
			for (int i = 0; i < copy.size(); i++) {
				now = start;
				if ((list.get(i).getStatus_change_time().getTime() - now) < 24 * 60 * 60 * 1000) {
					count++;
					list.remove(i);
				} else {
					break;
				}
			}
			copy = null;
			times.add(count);
			count = 0;
			start = start + 24 * 60 * 60 * 1000;
			if (start == end) {
				break;
			}
		}
		return times;
	}

	@Override
	public void sloveByAid(Integer id) {
		AlarmData data = alarmdatadao.findOne(id);
		data.setHasresloved(1);
		alarmdatadao.save(data);
	}

}
