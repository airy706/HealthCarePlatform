package com.nirvana.bll.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nirvana.bll.service.NodeDataService;
import com.nirvana.dal.api.NodeDataDao;

@Service
@Transactional
public class NodeDataServiceBO implements NodeDataService {
	@Autowired
	private NodeDataDao nodedatadao;
}
