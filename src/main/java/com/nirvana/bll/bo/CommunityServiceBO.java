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
	public void delById(Integer id) {
		communitydao.delete(id);
	}

	@Override
	public List<Community> findFuzzy(String key) {
		List<Community> list = communitydao.fuzzyQuery(key);
		return list;
	}

	@Override
	public Community findById(Integer id) {
		Community community = communitydao.findOne(id);
		return community;
	}
}
