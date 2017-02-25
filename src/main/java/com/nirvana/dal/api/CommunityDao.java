package com.nirvana.dal.api;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nirvana.dal.po.Community;

@Repository
public interface CommunityDao extends JpaRepository<Community, Integer>{
	@Query(nativeQuery=true,value="SELECT * FROM Community WHERE communityname LIKE %:key% OR communitylocation LIKE %:key% ORDER BY CONVERT(communityname USING gbk) ASC")
	List<Community> fuzzyQueryOrderByGBK(@Param("key") String key);

	@Query(nativeQuery=true,value="SELECT * FROM Community ORDER BY CONVERT(communityname USING gbk) ASC")
	List<Community> findAllOrderByGBK();
}
