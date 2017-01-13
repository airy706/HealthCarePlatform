package com.nirvana.dal.api;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nirvana.dal.po.Node;

@Repository
public interface NodeDao extends JpaRepository<Node, String> {

	@Query("SELECT n FROM Node n WHERE n.user.userid=:userid")
	List<Node> findAllTypeByUid(@Param("userid") Integer userid);

}
