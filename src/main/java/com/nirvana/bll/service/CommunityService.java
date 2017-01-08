package com.nirvana.bll.service;

import java.util.List;

import com.nirvana.app.vo.CommunityVO;
import com.nirvana.dal.po.Community;

public interface CommunityService {
	boolean add(Community community);
	
	void delById(Integer id);
	
	List<CommunityVO> findFuzzy(String key);
	
	Community findById(Integer id);

	List<CommunityVO> findAll();
}
