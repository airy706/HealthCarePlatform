package com.nirvana.bll.bo;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nirvana.bll.service.UserService;
import com.nirvana.dal.api.UserDao;
import com.nirvana.dal.po.User;

@Service
@Transactional
public class UserServiceBO implements UserService {
	
	@Autowired
	private UserDao userdao;
	
	@Transactional
	public void test(User u) {
		User user=userdao.findOne(1);
		
		user.setUsername(UUID.randomUUID().toString().substring(0,5));
		userdao.save(user);
		throw new RuntimeException("fuck");
	}
	
	

}
