package com.nirvana.dal.api;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nirvana.dal.po.User;

@Repository
public interface UserDao extends JpaRepository<User, Long> {
	@Query("SELECT u FROM User u WHERE u.username=:username")
	User findByUsername(@Param("username") String username);

}
