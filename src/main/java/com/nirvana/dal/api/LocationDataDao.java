package com.nirvana.dal.api;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nirvana.dal.po.LocationData;
/**
 * 
 * @author Bin
 * 定位数据 数据库访问层
 */
@Repository
public interface LocationDataDao extends JpaRepository<LocationData, Integer> {

	/**
	 * 根据身份证查询所有的数据
	 * @param useridentity 身份证
	 * @return 数据集合
	 */
	@Query(value="SELECT l FROM LocationData l WHERE l.did=:did")
	List<LocationData> findByDid(@Param("did") String useridentity);

}
