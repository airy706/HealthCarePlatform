package com.nirvana.bll.bo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

	@Override
	public Page<User> findBykeypage(String key, Integer num, Integer size) {
		PageRequest request = this.buildPageRequest(num,size);
        Page<User> pages= this.userdao.findByKey(key, request);
        return pages;
	}
	

	private PageRequest buildPageRequest(int pageNumber, int pagzSize) {
        return new PageRequest(pageNumber - 1, pagzSize, null);
    }
	
	

}
