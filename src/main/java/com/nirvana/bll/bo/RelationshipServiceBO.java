package com.nirvana.bll.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nirvana.bll.service.RelationshipService;
import com.nirvana.dal.api.RelationshipDao;

@Service
@Transactional
public class RelationshipServiceBO implements RelationshipService{
	@Autowired
	private RelationshipDao relationshipdao;
}
