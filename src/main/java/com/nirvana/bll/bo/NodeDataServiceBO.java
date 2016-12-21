package com.nirvana.bll.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nirvana.bll.service.NodeDataService;
import com.nirvana.dal.api.NodeDataDao;
import com.nirvana.dal.po.NodeData;

@Service
@Transactional
public class NodeDataServiceBO implements NodeDataService {
	@Autowired
	private NodeDataDao nodedatadao;

	@Override
	public void addData(NodeData data) {
		nodedatadao.save(data);
	}
	
	
}
