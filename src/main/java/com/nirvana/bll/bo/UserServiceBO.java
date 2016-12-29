package com.nirvana.bll.bo;


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

	@Override
	public User login(String username, String password) {
		User user = userdao.findByUsernameandPsd(username, password);
		return user;
	}

	@Override
	public void add(User user) {
		userdao.save(user);
	}

	@Override
	public User findById(Integer id) {
		User user = userdao.findOne(id);
		return user;
	}
	

	
	

}
