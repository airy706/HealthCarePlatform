package com.nirvana.bll.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nirvana.app.vo.CommunityVO;
import com.nirvana.bll.service.CommunityService;
import com.nirvana.dal.api.CommunityDao;
import com.nirvana.dal.po.Community;
@Service
@Transactional
public class CommunityServiceBO implements CommunityService{
	@Autowired
	private CommunityDao communitydao;

	@Override
	public void add(Community community) {
		communitydao.save(community);	
	}

	@Override
	public void delById(Integer id) {
		communitydao.delete(id);
	}

	@Override
	public List<CommunityVO> findFuzzy(String key) {
		List<Community> polist = communitydao.fuzzyQueryOrderByGBK(key);
		return CommunityVO.toListVO(polist);
	}

	@Override
	public Community findById(Integer id) {
		Community community = communitydao.findOne(id);
		return community;
	}

	@Override
	public List<CommunityVO> findAll() {
		List<Community> list =  communitydao.findAllOrderByGBK();
		List<CommunityVO> polist = new ArrayList<CommunityVO>();	
		for(Community community:list){
			polist.add(new CommunityVO(community.getCommunityid(),community.getCommunityname()));
		}
		return polist;
	}

	@Override
	public List<CommunityVO> findAllNotEmpty() {
		List<Community> list =  communitydao.findAllNotEmpty();
		List<CommunityVO> polist = new ArrayList<CommunityVO>();	
		for(Community community:list){
			polist.add(new CommunityVO(community));
		}
		return polist;
	}
}
