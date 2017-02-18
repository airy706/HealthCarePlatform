package com.nirvana.dal.api;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
	Page<Notice> fuzzyQuery(@Param("key") String key, Pageable pageable);

	@Query("SELECT n FROM Notice n WHERE n.community.communityid=:communityid OR n.noticetype=1")
	List<Notice> findNoticeByCid(@Param("communityid") Integer communityid);

	@Query("SELECT n FROM Notice n WHERE (n.noticetitle LIKE %:key% OR n.user.username LIKE %:key%) AND n.community.communityid=:communityid")
	Page<Notice> fuzzyQueryByCid(@Param("key") String key, Pageable pageable,
			@Param("communityid") Integer communityid);

	@Query("SELECT n FROM Notice n WHERE n.noticedate>:start AND n.noticedate<:end")
	List<Notice> findByDate(@Param("start") Date start,@Param("end") Date end);
}
