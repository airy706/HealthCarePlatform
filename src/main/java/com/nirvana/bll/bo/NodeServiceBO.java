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
			node.setNodename("人体活动状态");
		} else if (nodetype == 3) {
			node.setNodename("压力坐垫");
		} else if (nodetype == 7) {
			node.setNodename("轮椅状态");
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
		if(node.getNodename().equals("其他")){
			return false;
		}
		nodedao.save(node);
		return true;
	}

	@Override
	public List<NodeVO> findAllByUid(Integer userid) {
		List<Node> list = nodedao.findAllTypeByUid(userid);
		List<NodeVO> volist = new ArrayList<NodeVO>();
		for (Node node : list) {
			NodeVO vo = new NodeVO(node);
			volist.add(vo);
		}
		return volist;
	}

	@Override
	public void cstatus(Integer nodeid) {
		Node node = nodedao.findOne(nodeid);
		if(node.getNodestatus()==1){
			node.setNodestatus(0);
		}else if(node.getNodestatus()==0){
			node.setNodestatus(1);
		}
		nodedao.save(node);
	}

	@Override
	public void setfreq(Integer nodeid, Integer freq) {
		Node node = nodedao.findOne(nodeid);
		node.setFrequency(freq);
		nodedao.save(node);
	}

}
