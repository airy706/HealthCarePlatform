package com.nirvana.bll.bo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nirvana.app.vo.NodeHomePageVO;
import com.nirvana.app.vo.NodeVO;
import com.nirvana.app.vo.UserVO;
import com.nirvana.bll.service.UserService;
import com.nirvana.dal.api.AlarmDataDao;
import com.nirvana.dal.api.CommunityDao;
import com.nirvana.dal.api.LocationDataDao;
import com.nirvana.dal.api.NodeDao;
import com.nirvana.dal.api.NodeDataDao;
import com.nirvana.dal.api.UserDao;
import com.nirvana.dal.po.AlarmData;
import com.nirvana.dal.po.LocationData;
import com.nirvana.dal.po.Node;
import com.nirvana.dal.po.NodeData;
import com.nirvana.dal.po.User;

@Service
@Transactional
public class UserServiceBO implements UserService {

	@Autowired
	private UserDao userdao;

	@Autowired
	private NodeDao nodedao;

	@Autowired
	private NodeDataDao nodedatadao;

	@Autowired
	private AlarmDataDao alarmdatadao;

	@Autowired
	private LocationDataDao locationdatadao;
	
	@Autowired
	private CommunityDao communitydao; 

	@Override
	public UserVO login(String account, String password) {
		User user = userdao.findByAccountandPsd(account, password);
		if (user == null) {
			return null;
		}
		UserVO vo = new UserVO();
		vo.setUserid(user.getUserid());
		vo.setUsername(user.getUsername());
		vo.setTypeid(user.getTypeid());
		if (user.getCommunity() != null) {
			vo.setCommunityid(user.getCommunity().getCommunityid());
			vo.setCommunityname(user.getCommunity().getCommunityname());
		}
		vo.setState(user.getState());
		return vo;
	}

	@Override
	public void add(User user) {
		userdao.save(user);
	}

	@Override
	public User findById(Integer id) {
		User user = userdao.findOne(id);
		return user;
	}

	@Override
	public Page<User> findBykeypage(String key, Integer num, Integer size, Integer cid) {
		PageRequest request = this.buildPageRequest(num, size);
		Page<User> pages = null;
		if (cid == null) {
			pages = this.userdao.findCommonByKey(key, request);
		} else {
			pages = userdao.findCommonByKeyAndCid(key, cid, request);
		}
		return pages;
	}

	private PageRequest buildPageRequest(int pageNumber, int pagzSize) {
		return new PageRequest(pageNumber - 1, pagzSize, null);
	}

	@Override
	public void updateloc(String did, String longtitude, String latitude, Date updatetime) {
		User user = userdao.findByDid(did);
		user.setLatitude(latitude);
		user.setLongtitude(longtitude);
		user.setLastupdatetime(updatetime);
		userdao.save(user);
	}

	@Override
	public List<UserVO> findOnline(Integer cid) {
		List<User> list = null;
		if (cid == null) {
			list = userdao.findAll();
		} else {
			list = userdao.findAllByCid(cid);
		}
		Date now = new Date();
		List<UserVO> volist = new ArrayList<UserVO>();
		for (User user : list) {
			// if(user.getLastupdatetime().)
			if (user.getLastupdatetime() != null) {
				long between = now.getTime() - user.getLastupdatetime().getTime();
				if (between < 60000 * 62 / user.getFrequency()) {
					volist.add(new UserVO(user, 2));
				}
			}
		}
		return volist;
	}

	@Override
	public void setFrequency(User user) {
		User u = userdao.findOne(user.getUserid());
		u.setFrequency(user.getFrequency());
		u.setValid(user.getValid());
		userdao.save(u);
	}

	@Override
	public List<NodeHomePageVO> findNodeDataByUid(Integer userid) {
		User user = userdao.findOne(userid);
		String did = user.getUseridentity();
		List<Node> nodes = nodedao.findAllTypeByUid(userid);
		System.out.println(nodes.size());
		List<NodeHomePageVO> volist = new ArrayList<NodeHomePageVO>();
		for (Node node : nodes) {
			Integer type = node.getNodetype();
			System.out.println(type);
			System.out.println(did);
			PageRequest request = this.buildPageRequest(1, 1);
			Page<NodeData> pages = nodedatadao.findLatestByDidAndType(did, type, request);
			List<NodeData> list = pages.getContent();
			System.out.println(list.size());
			NodeHomePageVO vo = new NodeHomePageVO();
			if (list.size() > 0) {
				vo.setLatestData(list.get(0).getData());
				vo.setLastestTime(list.get(0).getStatus_change_time());
			} else {
				vo.setLatestData("暂无数据");
			}
			vo.setNodeName(node.getNodename());
			vo.setNodeType(type);
			if (type == 12 || type == 4) {
				Integer h1 = 0, h2 = 0;
				Integer l1 = 10000, l2 = 10000;
				Date end = new Date();
				Date start = new Date();
				start.setTime(end.getTime() - 7 * 24 * 60 * 60 * 1000);
				List<NodeData> datas = nodedatadao.findByDidAndTypeinWeek(did, type, start, end);
				for (int i = 0; i < datas.size(); i++) {
					String value = datas.get(i).getData();
					if (type == 4) {
						String v[] = value.split(",");
						if (Integer.parseInt(v[0]) > h1) {
							h1 = Integer.parseInt(v[0]);
							vo.setHighTime(datas.get(i).getStatus_change_time());
						}
						if (Integer.parseInt(v[1]) > h2) {
							h2 = Integer.parseInt(v[1]);
						}
						if (Integer.parseInt(v[0]) < l1) {
							l1 = Integer.parseInt(v[0]);
						}
						if (Integer.parseInt(v[1]) < l2) {
							l2 = Integer.parseInt(v[1]);
							vo.setLowTime(datas.get(i).getStatus_change_time());
						}
					} else {
						if (Integer.parseInt(value) > h1) {
							h1 = Integer.parseInt(value);
							vo.setHighTime(datas.get(i).getStatus_change_time());
						}
						if (Integer.parseInt(value) < l1) {
							l1 = Integer.parseInt(value);
							vo.setLowTime(datas.get(i).getStatus_change_time());
						}
					}
				}
				if (type == 4) {
					vo.setHigh(h1 + "," + h2);
					vo.setLow(l1 + "," + l2);
				} else {
					vo.setHigh(h1 + "");
					vo.setLow(l1 + "");
				}
			}
			volist.add(vo);
		}
		return volist;
	}

	@Override
	public UserVO getDetailByUid(Integer userid) {
		User user = userdao.findOne(userid);
		UserVO vo = new UserVO();
		vo.setUserid(user.getUserid());
		vo.setUsername(user.getUsername());
		if (user.getCommunity() != null) {
			vo.setCommunityid(user.getCommunity().getCommunityid());
			vo.setCommunityname(user.getCommunity().getCommunityname());
		}
		vo.setLatitude(user.getLatitude());
		vo.setLongitude(user.getLongtitude());
		vo.setState(user.getState());
		vo.setUsertel(user.getUsertel());
		return vo;
	}

	@Override
	public void regist(User user) {
		user.setRegisttime(new Date());
		user.setTypeid(3);
		user.setFrequency(5);
		user.setValid(0);
		user.setState(1);
		user.setUserapartment("");
		userdao.save(user);
	}

	@Override
	public UserVO findInfoByUid(Integer userid) {
		User user = userdao.findOne(userid);
		UserVO vo = new UserVO();
		vo.setUsername(user.getUsername());
		vo.setIdentity(user.getUseridentity());
		vo.setGender(user.getGender());
		vo.setAvatar(user.getAvatar());
		vo.setUseremail(user.getUseremail());
		vo.setUsertel(user.getUsertel());
		vo.setAddress(user.getUseraddress());
		if (user.getCommunity() != null) {
			vo.setCommunityid(user.getCommunity().getCommunityid());
			vo.setCommunityname(user.getCommunity().getCommunityname());
		}
		return vo;
	}

	/*
	 * @Override public boolean checkPassword(Integer userid, String
	 * oldPassword) { User user = userdao.findOne(userid); if
	 * (user.getPassword().equals(oldPassword)) { return true; } else { return
	 * false; } }
	 */

	@Override
	public void updateinfo(Integer userid, User user) {
		User u = userdao.findOne(userid);
		if (!"".equals(user.getPassword()) && user.getPassword() != null) {
			u.setPassword(user.getPassword());
		}
		u.setAvatar(user.getAvatar());
		u.setGender(user.getGender());
		u.setUseremail(user.getUseremail());
		u.setUsertel(user.getUsertel());
		u.setUseraddress(user.getUseraddress());
		u.setCommunity(user.getCommunity());
		if(user.getCommunity()!=null){
		u.setUserapartment(communitydao.findOne(user.getCommunity().getCommunityid()).getCommunityname());
		}
		userdao.save(u);
	}

	@Override
	public Integer getFrequencyByDid(String did) {
		User user = userdao.findByDid(did);
		return user.getFrequency();
	}

	@Override
	public void updatePassword(Integer userid, String newPassword) {
		User user = userdao.findOne(userid);
		user.setPassword(newPassword);
		userdao.save(user);
	}

	@Override
	public List<UserVO> findManagersByKey(String key) {
		List<User> list = userdao.findManagerByKey(key);
		System.out.println(list.size());
		List<UserVO> volist = new ArrayList<UserVO>();
		for (User user : list) {
			UserVO vo = new UserVO();
			vo.setUserid(user.getUserid());
			vo.setUsername(user.getUsername());
			if (user.getCommunity() != null) {
				vo.setCommunityname(user.getCommunity().getCommunityname());
				vo.setCommunityid(user.getCommunity().getCommunityid());
			}
			vo.setAccount(user.getAccount());
			vo.setRegisttime(user.getRegisttime());
			volist.add(vo);
		}
		return volist;
	}

	@Override
	public void delByUid(Integer userid) {
		User user = userdao.findOne(userid);
		List<AlarmData> adatas = alarmdatadao.findByDid(user.getUseridentity());
		List<NodeData> ndatas = nodedatadao.findByDid(user.getUseridentity());
		List<LocationData> ldatas = locationdatadao.findByDid(user.getUseridentity());
		alarmdatadao.delete(adatas);
		nodedatadao.delete(ndatas);
		locationdatadao.delete(ldatas);
		userdao.delete(userid);

	}

	@Override
	public UserVO commonlogin(String account, String password) {
		User user = userdao.findCommonByAccountAndPsd(account, password);
		if (user == null) {
			return null;
		}
		UserVO vo = new UserVO();
		vo.setUsername(user.getUsername());
		vo.setDid(user.getUseridentity());
		vo.setState(user.getState());
		vo.setGender(user.getGender());
		vo.setAddress(user.getUseraddress());
		vo.setUseremail(user.getUseremail());
		vo.setAccount(user.getAccount());
		vo.setUsertel(user.getUsertel());
		List<Node> polist = nodedao.findAllTypeByUid(user.getUserid());
		List<NodeVO> volist = new ArrayList<NodeVO>();
		for (Node n : polist) {
			volist.add(new NodeVO(n));
		}
		vo.setNodes(volist);
		vo.setFrequency(user.getFrequency());
		return vo;
	}

	@Override
	public boolean didIsExist(String did) {
		User user = userdao.findByDid(did);
		if (user == null) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public boolean accountIsExist(String account) {
		User user = userdao.findByAccount(account);
		if (user == null) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public User findByDid(String did) {
		User user = userdao.findByDid(did);
		return user;
	}

	@Override
	public List<UserVO> findAllByCid(Integer communityid) {
		List<User> polist = userdao.findAllByCid(communityid);
		List<UserVO> volist = new ArrayList<UserVO>();
		for (User user : polist) {
			UserVO vo = new UserVO();
			vo.setUserid(user.getUserid());
			vo.setUsername(user.getUsername());
			volist.add(vo);
		}
		return volist;
	}

	@Override
	public Page<User> findRegisterByKey(String key, Integer size, Integer num) {
		PageRequest request = this.buildPageRequest(num, size);
		Page<User> page = userdao.findRegisterByKey(key, request);
		return page;
	}

	@Override
	public void updateregister(User user) {
		User u = userdao.findOne(user.getUserid());
		u.setUsername(user.getUsername());
		u.setUseridentity(user.getUseridentity());
		u.setUseraddress(user.getUseraddress());
		u.setUsertel(user.getUsertel());
		u.setUseremail(user.getUseremail());
		u.setAccount(user.getAccount());
		userdao.save(u);
	}

	@Override
	public void frozen(Integer userid) {
		User user = userdao.findOne(userid);
		user.setState(0);
		userdao.save(user);
	}

	@Override
	public void recovery(Integer userid) {
		User user = userdao.findOne(userid);
		user.setState(1);
		userdao.save(user);
	}

}
