package com.nirvana.bll.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nirvana.bll.service.CommunityService;
import com.nirvana.dal.api.CommunityDao;
@Service
@Transactional
public class CommunityServiceBO implements CommunityService{
	@Autowired
	private CommunityDao communitydao;
}
