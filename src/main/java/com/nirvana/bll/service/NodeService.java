package com.nirvana.bll.service;

import java.util.List;

import com.nirvana.app.vo.NodeVO;
import com.nirvana.dal.po.Node;

public interface NodeService {
	void add(String did, Integer nodetype);

	List<NodeVO> findAllByUid(Integer userid);
}
