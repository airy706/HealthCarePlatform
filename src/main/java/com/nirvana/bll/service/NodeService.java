package com.nirvana.bll.service;

import java.util.List;

import com.nirvana.app.vo.NodeVO;

public interface NodeService {
	boolean add(String did, Integer nodetype);

	List<NodeVO> findAllByUid(Integer userid);
}
