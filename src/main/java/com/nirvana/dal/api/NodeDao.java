package com.nirvana.dal.api;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nirvana.dal.po.Node;
/**
 * 
 * @author Bin
 * 节点数据库访问层
 */
@Repository
public interface NodeDao extends JpaRepository<Node, Integer> {

	/**
	 *  查询所有节点根据用户id
	 * @param userid
	 * @return 返回节点集合
	 */
	@Query("SELECT n FROM Node n WHERE n.user.userid=:userid")
	List<Node> findAllTypeByUid(@Param("userid") Integer userid);

	/**
	 * 根据身份证和节点类型查询相应的节点
	 * @param did 身份证
	 * @param nodetype 节点类型id
	 * @return 返回相应的节点
	 */
	@Query("SELECT n FROM Node n WHERE n.user.useridentity=:did AND n.nodetype=:nodetype")
	Node findByDidAndTypeid(@Param("did") String did,@Param("nodetype") Integer nodetype);

	@Query("SELECT n FROM Node n WHERE n.nodeid=:nodeid")
	Node findOneById(@Param("nodeid") Integer nodeid);
	
	
	
}
