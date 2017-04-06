package com.nirvana.bll.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;

import com.nirvana.app.vo.NodeHomePageVO;
import com.nirvana.app.vo.UserVO;
import com.nirvana.dal.po.User;

/**
 * 
 * @author Bin 用户服务接口
 */
public interface UserService {
	/**
	 * 用户登录
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	UserVO login(String username, String password);

	/**
	 * 添加用户
	 * 
	 * @param user
	 */
	void add(User user);

	/**
	 * 根据id搜索用户
	 * 
	 * @param id
	 * @return
	 */
	User findById(Integer id);

	/**
	 * 模糊查询社区中的用户 并分页
	 * @param key
	 * @param num
	 * @param size
	 * @param cid
	 * @return
	 */
	Page<User> findBykeypage(String key, Integer num, Integer size, Integer cid);

	/**
	 * 更新用户的位置信息
	 * @param did
	 * @param longtitude
	 * @param latitude
	 * @param updatetime
	 */
	void updateloc(String did, String longtitude, String latitude, Date updatetime);

	/**
	 * 查询所有社区在线的用户
	 * @param communityId
	 * @return
	 */
	List<UserVO> findOnline(Integer communityId);

	/**
	 * 设置用户的位置数据上传频率
	 * @param user
	 */
	void setFrequency(User user);

	/**
	 * 查询用户首页的显示数据
	 * @param userid
	 * @return
	 */
	List<NodeHomePageVO> findNodeDataByUid(Integer userid);

	/**
	 * 查询用户的细节信息
	 * @param userid
	 * @return
	 */
	UserVO getDetailByUid(Integer userid);

	/**
	 * 注册用户
	 * @param user
	 */
	void regist(User user);

	/**
	 * 查询用户的信息
	 * @param userid
	 * @return
	 */
	UserVO findInfoByUid(Integer userid);

	// boolean checkPassword(Integer userid, String oldPassword);

	/**
	 * 更新用户的信息
	 * @param userid
	 * @param user
	 */
	void updateinfo(Integer userid, User user);

	/**
	 * 根据用户的身份证获取上传地址数据频率
	 * @param did
	 * @return
	 */
	Integer getFrequencyByDid(String did);

	/**
	 * 修改密码
	 * @param userid
	 * @param newPassword
	 */
	void updatePassword(Integer userid, String newPassword);

	/**
	 * 模糊搜索社区管理员
	 * @param key
	 * @return
	 */
	List<UserVO> findManagersByKey(String key);

	/**
	 * 删除用户
	 * @param userid
	 */
	void delByUid(Integer userid);

	/**
	 * 普通用户登录
	 * @param account
	 * @param password
	 * @return
	 */
	UserVO commonlogin(String account, String password);

	/**
	 * 身份证号是否已存在
	 * @param did
	 * @return
	 */
	boolean didIsExist(String did);

	/**
	 * 账户名是否存在
	 * @param account
	 * @return
	 */
	boolean accountIsExist(String account);

	/**
	 * 根据身份证号查找用户
	 * @param did
	 * @return
	 */
	User findByDid(String did);

	/**
	 * 查找社区下的所有用户
	 * @param communityid
	 * @return
	 */
	List<UserVO> findAllByCid(Integer communityid);

	/**
	 * 模糊查询所有注册的用户
	 * @param key
	 * @param size
	 * @param num
	 * @return
	 */
	Page<User> findRegisterByKey(String key, Integer size, Integer num);

	/**
	 * 跟新用户
	 * @param user
	 */
	void updateregister(User user);

	/**
	 * 冻结某用户
	 * @param userid
	 */
	void frozen(Integer userid);

	/**
	 * 解冻某用户
	 * @param userid
	 */
	void recovery(Integer userid);

	void update(User user);
}
