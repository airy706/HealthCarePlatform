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
	public User login(String username, String password) {
		User user = userdao.findByUsernameandPsd(username, password);
		return user;
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
	public Page<User> findBykeypage(String key, Integer num, Integer size) {
		PageRequest request = this.buildPageRequest(num, size);
		Page<User> pages = this.userdao.findByKey(key, request);
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
	public List<UserVO> findOnline() {
		List<User> list = userdao.findAll();
		Date now = new Date();
		List<UserVO> volist = new ArrayList<UserVO>();
		for (User user : list) {
			// if(user.getLastupdatetime().)
			if (user.getLastupdatetime() != null) {
				long between = now.getTime() - user.getLastupdatetime().getTime();
				if (between < 60000) {
					volist.add(new UserVO(user, 2));
				}
			}
		}
		return volist;
	}

	@Override
	public void setFrequency(User user) {
		userdao.updatefrequency(user.getUserid(), user.getValid(), user.getFrequency());
	}

	@Override
	public List<NodeHomePageVO> findNodeDataByUid(Integer userid) {
		User user = userdao.findOne(userid);
		String did = user.getUseridentity();
		List<Node> nodes = nodedao.findAllTypeByUid(userid);
		List<NodeHomePageVO> volist = new ArrayList<NodeHomePageVO>();
		for (Node node : nodes) {
			String data = nodedatadao.findLatestByDidAndType(did, node.getNodetype());
			NodeHomePageVO vo = new NodeHomePageVO();
			vo.setLatestData(data);
			vo.setNodeName(node.getNodename());
			vo.setNodeType(node.getNodetype());
			volist.add(vo);
		}
		return volist;
	}

}
