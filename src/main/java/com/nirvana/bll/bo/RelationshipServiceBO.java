package com.nirvana.bll.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nirvana.bll.service.RelationshipService;
import com.nirvana.dal.api.RelationshipDao;
import com.nirvana.dal.po.Relationship;

@Service
@Transactional
public class RelationshipServiceBO implements RelationshipService{
	@Autowired
	private RelationshipDao relationshipdao;

	@Override
	public void add(Relationship ship) {
		relationshipdao.save(ship);
	}

	@Override
	public void delById(Integer id) {
		relationshipdao.delete(id);
	}
}
