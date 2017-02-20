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

@Repository
public interface UserDao extends JpaRepository<User, Integer> {
	@Query("SELECT u FROM User u WHERE u.account=:account and u.password=:password")
	User findByAccountandPsd(@Param("account") String account, @Param("password") String password);

	@Query("SELECT u FROM User u WHERE (u.username LIKE %:key% OR u.community.communityname LIKE %:key%) AND u.typeid=3")
	Page<User> findCommonByKey(@Param("key") String key, Pageable pageable);

	@Query("SELECT u FROM User u WHERE u.useridentity=:did")
	User findByDid(@Param("did") String did);

	@Query("SELECT u.useridentity FROM User u WHERE u.community.communityid=:id AND u.typeid=3")
	List<String> findAllCommondid(@Param("id") Integer id);

	@Query("SELECT u FROM User u WHERE u.community.communityid=:communityId AND u.typeid=2")
	List<User> findManagerByCid(@Param("communityId") Integer communityId);

	@Query("SELECT u FROM User u WHERE u.community.communityid=:cid")
	List<User> findAllByCid(@Param("cid") Integer cid);

	@Query("SELECT u FROM User u WHERE u.username LIKE %:key% AND u.community.communityid=:cid AND u.typeid=3")
	Page<User> findCommonByKeyAndCid(@Param("key") String key, @Param("cid") Integer cid, Pageable pageable);

	@Query("SELECT u FROM User u WHERE (u.username LIKE %:key% OR u.community.communityname LIKE %:key%) AND u.typeid=2")
	List<User> findManagerByKey(@Param("key") String key);

	@Query("SELECT u FROM User u WHERE u.account=:account AND u.password=:password AND u.typeid=3")
	User findCommonByAccountAndPsd(@Param("account") String account,@Param("password") String password);

	@Query("SELECT u FROM User u WHERE u.account=:account")
	User findByAccount(@Param("account") String account);

	@Query("SELECT u FROM User u WHERE (u.usertel LIKE %:key% OR u.useridentity LIKE %:key%) AND (u.typeid=2 OR u.typeid=3)")
	Page<User> findRegisterByKey(@Param("key") String key, Pageable pageable);
}
