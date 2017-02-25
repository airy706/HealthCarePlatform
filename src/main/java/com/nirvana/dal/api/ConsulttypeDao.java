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

	/**
	 * 查询相应社区所有的咨询类型
	 * @param communityId 社区id
	 * @return 返回咨询类型集合
	 */
	@Query("SELECT c FROM Consulttype c WHERE c.community.communityid=:communityId")
	List<Consulttype> findAllTypeByCid(@Param("communityId") Integer communityId);

	
	/**
	 *  根据类型名称迷糊查询相应社区内的咨询类型
	 * @param communityId 社区id
	 * @param key 搜索值
	 * @return 类型结合
	 */
	@Query("SELECT c FROM Consulttype c WHERE c.community.communityid=:communityId AND c.typename LIKE %:key%")
	List<Consulttype> findTypeByCidAndKey(@Param("communityId") Integer communityId, @Param("key") String key);

}
