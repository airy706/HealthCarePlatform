package com.nirvana.bll.bo;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nirvana.bll.service.NodeDataService;
import com.nirvana.dal.api.NodeDataDao;
import com.nirvana.dal.po.NodeData;

@Service
@Transactional
public class NodeDataServiceBO implements NodeDataService {
	@Autowired
	private NodeDataDao nodedatadao;

	@Override
	@Transactional
	public void addData(NodeData data) {
		data = nodedatadao.save(data);

	}

}
