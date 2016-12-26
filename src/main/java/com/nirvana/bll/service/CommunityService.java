package com.nirvana.bll.service;

import java.util.List;

import com.nirvana.dal.po.Community;

public interface CommunityService {
	boolean add(Community community);
	
	boolean delById(Integer id);
	
	List<Community> findFuzzy(String name,String location);
}
