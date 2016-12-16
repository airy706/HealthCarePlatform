package com.nirvana.bll.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nirvana.bll.service.UserService;
import com.nirvana.dal.api.UserDao;
import com.nirvana.dal.po.User;

@Service
public class UserServiceBO implements UserService {
	
	@Autowired
	private UserDao userdao;

	public void save(User u) {
		// TODO Auto-generated method stub
		
	}
	
	

}
