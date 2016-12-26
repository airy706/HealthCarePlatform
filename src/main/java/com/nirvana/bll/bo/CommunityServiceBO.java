package com.nirvana.bll.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nirvana.bll.service.CommunityService;
import com.nirvana.dal.api.CommunityDao;
import com.nirvana.dal.po.Community;
@Service
@Transactional
public class CommunityServiceBO implements CommunityService{
	@Autowired
	private CommunityDao communitydao;

	@Override
	public boolean add(Community community) {
		try {
			communitydao.save(community);
		} catch (Exception e) {
			return false;
		}
		return true;
		
	}

	@Override
	public boolean delById(Integer id) {
		communitydao.delete(id);
		return true;
	}

	@Override
	public List<Community> findFuzzy(String name, String location) {
		List<Community> list = communitydao.fuzzyQuery(name, location);
		return list;
	}
}
