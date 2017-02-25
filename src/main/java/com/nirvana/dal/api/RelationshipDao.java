package com.nirvana.dal.api;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nirvana.dal.po.Relationship;
/**
 * 
 * @author Bin
 * 用户联系人数据库访问层
 */
@Repository
public interface RelationshipDao extends JpaRepository<Relationship, Integer>{

	/**
	 * 根据userid查找其所有的联系人
	 * @param userid 用户id
	 * @return 返回联系人集合
	 */
	@Query("SELECT r FROM Relationship r WHERE r.user.userid=:userid")
	List<Relationship> findALLByUid(@Param("userid") Integer userid);

	/**
	 * 用户联系人登录
	 * @param account 账户
	 * @param password 密码
	 * @return 查到的联系人 否则返回null
	 */
	@Query("SELECT r FROM Relationship r WHERE r.relationaccount=:account AND r.relationpassword=:password")
	Relationship findOneByAccountAndPsd(@Param("account") String account,@Param("password") String password);

}
