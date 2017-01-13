package com.nirvana.bll.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;

import com.nirvana.app.vo.NodeHomePageVO;
import com.nirvana.app.vo.UserVO;
import com.nirvana.dal.po.User;

public interface UserService {
	 UserVO login(String username,String password);
	 
	 void add(User user);
	 
	 User findById(Integer id);
	 
	 Page<User> findBykeypage(String key,Integer num,Integer size,Integer cid);

	void updateloc(String did, String longtitude, String latitude,Date updatetime);

	List<UserVO> findOnline(Integer communityId);
	
	void setFrequency(User user);

	List<NodeHomePageVO> findNodeDataByUid(Integer userid);

	UserVO getDetailByUid(Integer userid);

	void regist(User user);

	UserVO findInfoByUid(Integer userid);

	//boolean checkPassword(Integer userid, String oldPassword);

	void updateinfo(Integer userid, User user);

	Integer getFrequencyByDid(String did);

	void updatePassword(Integer userid, String newPassword);

	List<UserVO> findManagersBy(String key);
}
