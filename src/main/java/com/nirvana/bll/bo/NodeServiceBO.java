package com.nirvana.bll.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nirvana.bll.service.NodeService;
import com.nirvana.dal.api.NodeDao;
import com.nirvana.dal.po.Node;

@Service
@Transactional
public class NodeServiceBO implements NodeService{
	@Autowired
	private NodeDao nodedao;

	@Override
	public void add(Node node) {
		nodedao.save(node);
	}
}
