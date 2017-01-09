package com.nirvana.bll.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;

import com.nirvana.app.vo.NodeHomePageVO;
import com.nirvana.app.vo.UserVO;
import com.nirvana.dal.po.User;

public interface UserService {
	 User login(String username,String password);
	 
	 void add(User user);
	 
	 User findById(Integer id);
	 
	 Page<User> findBykeypage(String key,Integer num,Integer size);

	void updateloc(String did, String longtitude, String latitude,Date updatetime);

	List<UserVO> findOnline();
	
	void setFrequency(User user);

	List<NodeHomePageVO> findNodeDataByUid(Integer userid);

	UserVO getDetailByUid(Integer userid);
}
