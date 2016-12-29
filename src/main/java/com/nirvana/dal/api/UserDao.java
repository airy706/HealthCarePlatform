package com.nirvana.dal.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nirvana.dal.po.User;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {
	@Query("SELECT u FROM User u WHERE u.username=:username and u.password=:password")
	User findByUsernameandPsd(@Param("username") String username,@Param("password") String password);

	@Query("SELECT u FROM User u WHERE u.username LIKE %:key% OR u.community.communityname LIKE %:key%")
	Page<User> findByKey(@Param("key") String key,Pageable pageable);
}
