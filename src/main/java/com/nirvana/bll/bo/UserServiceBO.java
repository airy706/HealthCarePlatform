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
import com.nirvana.app.vo.UserVO;
import com.nirvana.bll.service.UserService;
import com.nirvana.dal.api.NodeDao;
import com.nirvana.dal.api.NodeDataDao;
import com.nirvana.dal.api.UserDao;
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
			pages = this.userdao.findByKey(key, request);
		} else {
			pages = userdao.findByKeyAndCid(key, cid, request);
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
				if (between < 60000 * 5) {
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
			} else {
				vo.setLatestData("");
			}
			vo.setNodeName(node.getNodename());
			vo.setNodeType(type);
			if (type == 12 || type == 6) {
				Integer h1 = 0, h2 = 0;
				Integer l1 = 0, l2 = 0;
				Date end = new Date();
				Date start = new Date();
				start.setTime(end.getTime() - 7 * 24 * 60 * 60 * 1000);
				List<NodeData> datas = nodedatadao.findByDidAndTypeinWeek(did, type, start, end);
				for (int i = 0; i < datas.size(); i++) {
					String value = datas.get(i).getData();
					if (type == 6) {
						String v[] = value.split(",");
						if (Integer.parseInt(v[0]) > h1) {
							h1 = Integer.parseInt(v[0]);
						}
						if (Integer.parseInt(v[1]) > h2) {
							h2 = Integer.parseInt(v[1]);
						}
						if (Integer.parseInt(v[0]) < l1) {
							l1 = Integer.parseInt(v[0]);
						}
						if (Integer.parseInt(v[1]) < l2) {
							l2 = Integer.parseInt(v[1]);
						}
					} else {
						if (Integer.parseInt(value) > h1) {
							h1 = Integer.parseInt(value);
						}
						if (Integer.parseInt(value) < l1) {
							l1 = Integer.parseInt(value);
						}
					}
				}
				if (type == 6) {
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
		vo.setCommunityid(user.getCommunity().getCommunityid());
		vo.setCommunityname(user.getCommunity().getCommunityname());
		vo.setLatitude(user.getLatitude());
		vo.setLongitude(user.getLongtitude());
		vo.setState(user.getState());
		vo.setUsertel(user.getUsertel());
		return vo;
	}

	@Override
	public void regist(User user) {
		user.setTypeid(3);
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
		vo.setCommunityid(user.getCommunity().getCommunityid());
		vo.setCommunityname(user.getCommunity().getCommunityname());
		return vo;
	}

	@Override
	public boolean checkPassword(Integer userid, String oldPassword) {
		User user = userdao.findOne(userid);
		if(user.getPassword().equals(oldPassword)){
			return true;
		}else{
			return false;	
		}
	}

}
