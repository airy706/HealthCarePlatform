package com.nirvana.dal.api;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nirvana.app.vo.NoticeVO;
import com.nirvana.dal.po.Notice;

@Repository
public interface NoticeDao extends JpaRepository<Notice, Integer> {
	
	
	@Query("SELECT n FROM Notice n WHERE n.noticetype=1")
	List<Notice> queryAdmin();

	@Query("SELECT n FROM Notice n WHERE n.noticetype=2 AND communityid=:communityid")
	List<Notice> queryByCommunityId(@Param("communityid") Integer communityid);
}
