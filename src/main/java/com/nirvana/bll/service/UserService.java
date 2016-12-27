package com.nirvana.bll.service;

import com.nirvana.dal.po.User;

public interface UserService {
	 User login(String username,String password);
	 
	 void add(User user);
}
