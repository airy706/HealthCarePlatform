package com.nirvana.bll.service;

import java.util.List;

import com.nirvana.app.vo.LinkManVO;
import com.nirvana.dal.po.Relationship;

public interface RelationshipService {
	void add(Relationship ship);
	
	void delById(Integer id);

	List<LinkManVO> findAllByUid(Integer userid);
}
