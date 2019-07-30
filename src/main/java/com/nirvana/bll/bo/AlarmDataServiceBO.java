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

/**
 * 报警数据接口实现
 * 
 * @author Bin
 *
 */
@Service
@Transactional
public class AlarmDataServiceBO implements AlarmDataService {

	@Autowired
	private AlarmDataDao alarmdatadao;

	@Autowired
	private UserDao userdao;

	@Autowired
	private CommunityDao communitydao;

	/**
	 * 创建分页查询请求
	 * 
	 * @param pageNumber
	 *            页码
	 * @param pagzSize
	 *            每页容量
	 * @return
	 */
	private PageRequest buildPageRequest(int pageNumber, int pagzSize) {
		return new PageRequest(pageNumber - 1, pagzSize, null);
	}

	/**
	 * 在30min内同一种类型同一个人的报警数据不会再次发送
	 */
	public void addData(AlarmData data) {
		data.setHasresloved(0);
		PageRequest request = this.buildPageRequest(1, 1);
		List<AlarmData> list = alarmdatadao.findLatest(data.getReasontype(), data.getDid(), request).getContent();
		// 没有报警直接插入
		if (list.size() == 0) {
			alarmdatadao.save(data);
			return;
		}
		// 最新的报警
		AlarmData latest = list.get(0);
		// 时间间隔设为 30min;
		if (latest == null) {
			alarmdatadao.save(data);
		} else {
			// 计算时间间隔
			long between = data.getStatus_change_time().getTime() - latest.getStatus_change_time().getTime();
			if (between > 30 * 60000) {
				alarmdatadao.save(data);
			}
		}
	}

	public List<ExceptionVO> findAllRedo(Integer id) {
		List<AlarmData> list = null;
		List<ExceptionVO> exs = new ArrayList<ExceptionVO>();
		// 根据传入的id是否为空来做查询
		if (id == null) {
			// 超管查询所有
			list = alarmdatadao.findUnresloved();
		} else {
			// 社管查询社区里的
			List<String> dids = userdao.findAllCommondid(id);
			if (dids.size() == 0) {
				return exs;
			}
			list = alarmdatadao.findUnreslovedByCid(dids);
		}
		// 准备vo数据
		for (AlarmData data : list) {
			User user = userdao.findByDid(data.getDid());
			exs.add(new ExceptionVO(data, user));
		}
		return exs;
	}

	public List<ExceptionVO> detect(Integer aid, Integer cid) {
		// 获得网页上显示的最新的一条数据的时间
		AlarmData data = alarmdatadao.findOne(aid);
		List<ExceptionVO> exs = new ArrayList<ExceptionVO>();
		List<AlarmData> list = null;
		if (cid == null) {
			list = alarmdatadao.findAfter(data.getStatus_change_time());
		} else {
			List<String> dids = userdao.findAllCommondid(cid);
			if (dids.size() == 0) {
				return exs;
			}
			// 查询之后的报警数据
			list = alarmdatadao.findAfterByCid(data.getStatus_change_time(), dids);
		}
		// 数据包装
		for (AlarmData alarm : list) {
			User user = userdao.findByDid(alarm.getDid());
			exs.add(new ExceptionVO(alarm, user));
		}
		return exs;
	}

	public List<ExceptionVO> findAlltype() {
		List<Integer> list = alarmdatadao.findAlltype();
		List<ExceptionVO> exs = new ArrayList<ExceptionVO>();
		for (Integer alarm : list) {
			exs.add(new ExceptionVO(alarm));
		}
		return exs;
	}

	public List<ExceptionVO> findAllTimes(Integer communityid) {
		List<ExceptionVO> list = null;
		if (communityid == null) {
			list = findAlltype();
			for (ExceptionVO vo : list) {
				Integer times = alarmdatadao.findTypeTimes(vo.getAlarmType());
				vo.setAlarmTimes(times);
			}
		} else {
			List<String> dids = userdao.findAllCommondid(communityid);
			list = findAlltypeByCid(dids);
			for (ExceptionVO vo : list) {
				Integer times = alarmdatadao.findTypeTimesByCid(vo.getAlarmType(),dids);
				vo.setAlarmTimes(times);
			}
		}
		return list;
	}

	private List<ExceptionVO> findAlltypeByCid(List<String> dids) {
		List<Integer> list = alarmdatadao.findAlltypebyCid(dids);
		List<ExceptionVO> exs = new ArrayList<ExceptionVO>();
		for (Integer alarm : list) {
			//原因类型
			exs.add(new ExceptionVO(alarm));
		}
		return exs;
	}
//社区ID、类型ID
	public AlarmFilterVO findByFilter(String[] ids, String[] types, Date start, Date end) {
		List<Integer> typesint = new ArrayList<Integer>();
		List<Integer> communityids = new ArrayList<Integer>();
		for (int i = 0; i < types.length; i++) {

			//过滤掉空元素
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
			int i = 0;
			for (Community community : coms) {
				System.out.println(community.getCommunityid());
				communityids.add(community.getCommunityid());
				i++;
				//为什么为4 todo
				if (i == 4) {
					break;
				}
			}
		}
		AlarmFilterVO filtervo = new AlarmFilterVO();
		List<String> names = new ArrayList<String>();
		List<AlarmCommunityVO> data = new ArrayList<AlarmCommunityVO>();
		for (Integer id : communityids) {
			Community community = communitydao.findOne(id);
			//names集合元素为社区名
			names.add(community.getCommunityname());
			AlarmCommunityVO datavo = new AlarmCommunityVO();
			datavo.setName(community.getCommunityname());
			//查询社区内所有普通用户的身份证
			List<String> dids = userdao.findAllCommondid(id);
			List<Integer> times = null;
			if (dids.size() == 0) {
				times = analyseTimes(null, start.getTime(), end.getTime());
			} else {
				//一定时间内指定类型集合与指定社区人群的警报数据
				List<AlarmData> list = alarmdatadao.findFilter(typesint, dids, start, end);
				System.out.println(list.toString());
				//报警次数
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
//社区ID，用户ID集合，类型集合
	public AlarmFilterVO findPeopleByFilter(String communityid, String[] ids, String[] types, Date start, Date end) {
		List<Integer> typesint = new ArrayList<Integer>();
		List<User> useridsint = new ArrayList<User>();
		for (int i = 0; i < types.length; i++) {
			if (!"".equals(types[i])) {
				typesint.add(Integer.parseInt(types[i]));
			}
		}
		for (int i = 0; i < ids.length; i++) {
			if (!"".equals(ids[i])) {
				useridsint.add(userdao.findOne(Integer.parseInt(ids[i])));
			}
		}
		if (typesint.size() == 0) {
			List<Integer> tps = alarmdatadao.findAlltype();
			for (Integer t : tps) {
				typesint.add(t);
			}
		}
		if (useridsint.size() == 0) {
			useridsint = userdao.findAllByCid(Integer.parseInt(communityid));
		}
		AlarmFilterVO filtervo = new AlarmFilterVO();
		List<String> names = new ArrayList<String>();
		List<AlarmCommunityVO> data = new ArrayList<AlarmCommunityVO>();
		for (User user : useridsint) {
			//nmaes为用户姓名
			names.add(user.getUsername());
			AlarmCommunityVO datavo = new AlarmCommunityVO();
			datavo.setName(user.getUsername());
			List<Integer> times = null;
			//一定时间内某个用户某些原因类型的报警数据
			List<AlarmData> list = alarmdatadao.findPeopleFilter(typesint, user.getUseridentity(), start, end);
			//每天的报警次数
			times = analyseTimes(list, start.getTime(), end.getTime());
			datavo.setData(times);
			data.add(datavo);
		}
		filtervo.setNames(names);
		filtervo.setData(data);
		return filtervo;
	}
//start与end之差要为1天的整倍数
	//返回集合中每一元素为每天的报警次数
	private List<Integer> analyseTimes(List<AlarmData> list, long start, long end) {

		List<Integer> times = new ArrayList<Integer>();
		long now = 0;
		Integer count = 0;
		int index = 0;
		//警报数据集合为空，根据开始结束天数之差，在times集合中添加几个0元素
		if (list == null || list.size() == 0) {
			//天数
			long num = (end - start) / (24 * 60 * 60 * 1000);
			for (int i = 0; i < num; i++) {
				times.add(0);
			}
			return times;
		}
		//警报数据集合非空，进入循环，根据数据上传时间差（大于或等于一天跳出二级循环）
		// 分析一天的数据次数count，times添加该元素；start加上一天，count置0，继续下一次循环
		//直到start与end相等退出循环
		while (true) {
			now = start;
			count = 0;
			for (; index < list.size(); index++) {
				long test = list.get(index).getStatus_change_time().getTime();
				long between = test - now;
    			//间隔小于1天，次数加1
				if (between < 24 * 60 * 60 * 1000) {
					count++;
				} else {
					break;
				}
			}
			start = start + 24 * 60 * 60 * 1000;
			times.add(count);
			count = 0;
			if (start == end) {
				break;
			}
		}
		return times;
	}

	public void sloveByAid(Integer id) {
		AlarmData data = alarmdatadao.findOne(id);
		data.setHasresloved(1);
		alarmdatadao.save(data);
	}

	public List<AlarmData> findUndoByDid(String did) {
		List<AlarmData> list = alarmdatadao.findUndoByDid(did);
		return list;
	}

	public void rmall(Integer communityId) {
		List<AlarmData> list = null;
		if (communityId == null) {
			// 超管去除异常
			list = alarmdatadao.findUnresloved();
		} else {
			// 社区管理员去除异常
			List<String> dids = userdao.findAllCommondid(communityId);
			if (dids.size() == 0) {
				return;
			} else {
				list = alarmdatadao.findUnreslovedByCid(dids);
			}
		}
		for (AlarmData alarmData : list) {
			alarmData.setHasresloved(1);
			alarmdatadao.save(alarmData);
		}
	}

}
