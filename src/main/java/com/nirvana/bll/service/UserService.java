package com.nirvana.bll.service;

import org.springframework.data.domain.Page;

import com.nirvana.dal.po.User;

public interface UserService {
	 User login(String username,String password);
	 
	 void add(User user);
	 
	 User findById(Integer id);
	 
	 Page<User> findBykeypage(String key,Integer num,Integer size);
}
