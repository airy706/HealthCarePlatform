package com.nirvana.dal.api;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nirvana.dal.po.User;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {
//	@Query("SELECT u FROM User u WHERE u.username=:username")
//	User findByUsername(@Param("username") String username);

}
