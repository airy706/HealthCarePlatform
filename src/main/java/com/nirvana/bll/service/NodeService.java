package com.nirvana.bll.service;

import java.util.List;

import com.nirvana.app.vo.NodeVO;

public interface NodeService {
	//添加节点
	boolean add(String did, Integer nodetype);

	//查询用户的所有节点
	List<NodeVO> findAllByUid(Integer userid);
}
