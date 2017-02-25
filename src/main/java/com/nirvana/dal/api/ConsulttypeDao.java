package com.nirvana.dal.api;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nirvana.dal.po.Consulttype;
/**
 * 
 * @author Bin
 * 咨询类型的数据库访问层
 */
@Repository
public interface ConsulttypeDao extends JpaRepository<Consulttype, Integer> {

	
	@Query("SELECT c FROM Consulttype c WHERE c.community.communityid=:communityId")
	List<Consulttype> findAllTypeByCid(@Param("communityId") Integer communityId);

	@Query("SELECT c FROM Consulttype c WHERE c.community.communityid=:communityId AND c.typename LIKE %:key%")
	List<Consulttype> findTypeByCidAndKey(@Param("communityId") Integer communityId, @Param("key") String key);

}
