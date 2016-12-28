package com.nirvana.dal.api;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nirvana.dal.po.Community;

@Repository
public interface CommunityDao extends JpaRepository<Community, Integer>{
	@Query("SELECT c FROM Community c WHERE c.communityname LIKE %:key% OR c.communitylocation LIKE %:key%")
	List<Community> fuzzyQuery(@Param("key") String key);
}
