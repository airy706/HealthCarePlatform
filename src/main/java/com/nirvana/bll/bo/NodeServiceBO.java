package com.nirvana.bll.bo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nirvana.app.vo.NodeVO;
import com.nirvana.bll.service.NodeService;
import com.nirvana.dal.api.NodeDao;
import com.nirvana.dal.api.UserDao;
import com.nirvana.dal.po.Node;
import com.nirvana.dal.po.User;

@Service
@Transactional
public class NodeServiceBO implements NodeService {
	@Autowired
	private NodeDao nodedao;

	@Autowired
	private UserDao userdao;

	@Override
	public boolean add(String did, Integer nodetype) {
		Node node = new Node();
		User user = userdao.findByDid(did);
		if (user == null) {
			return false;
		}
		Node n = nodedao.findByDidAndTypeid(did, nodetype);
		if (n != null) {
			return false;
		}
		node.setNodeaddtime(new Date());
		node.setNodestatus(1);
		node.setNodetype(nodetype);
		node.setUser(user);
		if (nodetype == 4) {
			node.setNodename("血压");
		} else if (nodetype == 6) {
			node.setNodename("人体状态");
		} else if (nodetype == 3) {
			node.setNodename("压力坐垫");
		} else if (nodetype == 9) {
			node.setNodename("其他");
		} else if (nodetype == 99) {
			node.setNodename("一键求救");
		} else if (nodetype == 12) {
			node.setNodename("心率");
		} else if (nodetype == 66) {
			node.setNodename("小米手环");
		} else {
			node.setNodename("其他");
		}
		node.setFrequency(5);
		nodedao.save(node);
		return true;
	}

	@Override
	public List<NodeVO> findAllByUid(Integer userid) {
		List<Node> list = nodedao.findAllTypeByUid(userid);
		List<NodeVO> volist = new ArrayList<NodeVO>();
		for (Node node : list) {
			NodeVO vo = new NodeVO();
			vo.setNodename(node.getNodename());
			vo.setNodetype(node.getNodetype());
			volist.add(vo);
		}
		return volist;
	}

}
