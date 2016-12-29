package com.nirvana.bll.service;

import com.nirvana.dal.po.Relationship;

public interface RelationshipService {
	void add(Relationship ship);
	
	void delById(Integer id);
}
