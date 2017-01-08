package com.nirvana.dal.api;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nirvana.dal.po.Notice;

@Repository
public interface NoticeDao extends JpaRepository<Notice, Integer> {
	
	
	@Query("SELECT n FROM Notice n WHERE n.noticetype=1")
	List<Notice> queryAdmin();

	@Query("SELECT n FROM Notice n WHERE n.noticetype=2 AND n.community.communityid=:communityid")
	List<Notice> queryByCommunityId(@Param("communityid") Integer communityid);

	@Query("SELECT n FROM Notice n WHERE n.noticetitle LIKE %:key% OR n.user.username LIKE %:key%")
	List<Notice> fuzzyQuery(@Param("key") String key);
	
	@Query("SELECT n FROM Notice n WHERE n.community.communityid=:communityid OR n.noticetype=1")
	List<Notice> findNoticeByCid(@Param("communityid") Integer communityid);
}
