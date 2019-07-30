package com.nirvana.dal.api;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nirvana.dal.po.User;
/**
 * 
 * @author Bin
 * user表 数据访问层
 */
@Repository
public interface UserDao extends JpaRepository<User, Integer> {
	/**
	 * 根据账户和密码查询用户
	 * @param account 账户
	 * @param password 密码
	 * @return 查询符合的用户
	 */
	@Query("SELECT u FROM User u WHERE u.account=:account and u.password=:password")
	User findByAccountandPsd(@Param("account") String account, @Param("password") String password);

	/**
	 * 根据key值来模糊查询所有的普通用户
	 * @param key 可用于用户名或者社区名
	 * @param pageable 分页类
	 * @return 返回该页的用户集
	 */
	@Query("SELECT u FROM User u WHERE (u.username LIKE %:key% OR u.userapartment LIKE %:key%) AND u.typeid=3")
	Page<User> findCommonByKey(@Param("key") String key, Pageable pageable);

	/**
	 * 根据身份证查询用户
	 * @param did 
	 * @return User
	 */
	@Query("SELECT u FROM User u WHERE u.useridentity=:did")
	User findByDid(@Param("did") String did);

	/**
	 * 根据社区名查询社区中所有用户的身份证号
	 * @param id 社区id
	 * @return list of did
	 */
	@Query("SELECT u.useridentity FROM User u WHERE u.community.communityid=:id AND u.typeid=3")
	List<String> findAllCommondid(@Param("id") Integer id);

	/**
	 * 查询社区的所有社区管理员
	 * @param communityId 社区id
	 * @return list of manager belong to community selected
	 */
	@Query("SELECT u FROM User u WHERE u.community.communityid=:communityId AND u.typeid=2")
	List<User> findManagerByCid(@Param("communityId") Integer communityId);

	/**
	 * 查询社区里所有用户
	 * @param cid 社区id
	 * @return list of user belong to community selected
	 */
	@Query("SELECT u FROM User u WHERE u.community.communityid=:cid AND u.typeid=3")
	List<User> findAllByCid(@Param("cid") Integer cid);

	/**
	 * 根据key模糊查询相应社区的普通用户
	 * @param key 用户姓名
	 * @param cid 社区id
	 * @param pageable 分页
	 * @return list of common user belong to commnuity selected and their names like key 
	 */
	@Query("SELECT u FROM User u WHERE u.username LIKE %:key% AND u.community.communityid=:cid AND u.typeid=3")
	Page<User> findCommonByKeyAndCid(@Param("key") String key, @Param("cid") Integer cid, Pageable pageable);

	/**
	 * 根据用户名以及社区名模糊查询社区管理员
	 * @param key
	 * @return 符合的管理员集合
	 */
	@Query("SELECT u FROM User u WHERE (u.username LIKE %:key% OR u.community.communityname LIKE %:key%) AND u.typeid=2")
	List<User> findManagerByKey(@Param("key") String key);

	/**
	 * 根据账户名和密码搜索符合的普通用户
	 * @param account 账户
	 * @param password 密码
	 * @return 返回查找到的用户 否则返回null
	 */
	@Query("SELECT u FROM User u WHERE u.account=:account AND u.password=:password AND u.typeid=3")
	User findCommonByAccountAndPsd(@Param("account") String account,@Param("password") String password);

	/**
	 * 根据账户名查询用户
	 * @param account 账户
	 * @return
	 */
	@Query("SELECT u FROM User u WHERE u.account=:account")
	User findByAccount(@Param("account") String account);

	/**
	 * 查找所有的注册用户根据 电话 身份证  所属社区名
	 * @param key 模糊值
	 * @param pageable 分页
	 * @return 返回分页的用户集合
	 */
	@Query("SELECT u FROM User u WHERE (u.username LIKE %:key% OR u.usertel LIKE %:key% OR u.useridentity LIKE %:key% OR u.userapartment LIKE %:key%) AND (u.typeid=2 OR u.typeid=3)")
	Page<User> findRegisterByKey(@Param("key") String key, Pageable pageable);
}
