package com.nirvana.dal.api;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nirvana.dal.po.Relationship;

@Repository
public interface RelationshipDao extends JpaRepository<Relationship, Integer>{

	@Query("SELECT r FROM Relationship r WHERE r.user.userid=:userid")
	List<Relationship> findALLByUid(@Param("userid") Integer userid);

}
