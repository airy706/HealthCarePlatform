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
/**
 * 
 * @author Bin
 * 公告数据库访问层
 */
@Repository
public interface NoticeDao extends JpaRepository<Notice, Integer> {

	/**
	 * 查找所有超管发布的并且可以显示的公告
	 * @return 公告集合
	 */
	@Query("SELECT n FROM Notice n WHERE n.noticetype=1 AND n.isshow=1 ORDER BY n.noticedate DESC")
	List<Notice> queryAdmin();

	/**
	 * 查找制定社区的所有公告
	 * @param communityid 社区id
	 * @return 公告集合
	 */
	@Query("SELECT n FROM Notice n WHERE n.noticetype=2 AND n.community.communityid=:communityid")
	List<Notice> queryByCommunityId(@Param("communityid") Integer communityid);

	/**
	 * 根据公告标题以及发布用户姓名搜索所有符合的公告
	 * @param key 搜索值
	 * @param pageable 分页
	 * @return 返回公告页
	 */
	@Query("SELECT n FROM Notice n WHERE n.noticetitle LIKE %:key% OR n.user.username LIKE %:key%")
	Page<Notice> fuzzyQuery(@Param("key") String key, Pageable pageable);

	/**
	 * 查询用户所属社区中所能查看的所有公告 按时间降续排列
	 * @param communityid 社区id
	 * @return 公告集合
	 */
	@Query("SELECT n FROM Notice n WHERE (n.community.communityid=:communityid OR n.noticetype=1) AND n.isshow=1 AND n.istop=0 ORDER BY n.noticedate DESC")
	Page<Notice> findNoticeByCidNotTop(@Param("communityid") Integer communityid,Pageable pageable);

	
	@Query("SELECT n FROM Notice n WHERE (n.community.communityid=:communityid OR n.noticetype=1) AND n.isshow=1 AND n.istop=1 ORDER BY n.noticedate DESC")
	List<Notice> findNoticeByCidIsTop(@Param("communityid") Integer communityid);
	
	/**
	 * 根据公告标题以及发布用户名 模糊查询所有符合的社区公告
	 * @param key 搜索值
	 * @param pageable 分页
	 * @param communityid 社区id
	 * @return 公告页
	 */
	@Query("SELECT n FROM Notice n WHERE (n.noticetitle LIKE %:key% OR n.user.username LIKE %:key%) AND n.community.communityid=:communityid")
	Page<Notice> fuzzyQueryByCid(@Param("key") String key, Pageable pageable,
			@Param("communityid") Integer communityid);

	/**
	 * 查询一定时间内的所有可显示公告
	 * @param start 开始时间
	 * @param end 截止时间
	 * @return
	 */
	@Query("SELECT n FROM Notice n WHERE n.noticedate>:start AND n.noticedate<:end AND n.isshow=1 ORDER BY n.noticedate DESC")
	List<Notice> findByDate(@Param("start") Date start,@Param("end") Date end);

	/**
	 * 查询所有的置顶公告
	 * @return
	 */
	@Query("SELECT n FROM Notice n WHERE n.noticetype=1 AND n.istop=0 AND n.isshow=1 ORDER BY n.noticedate DESC")
	Page<Notice> queryAdminNotTop(Pageable pageable);

	/**
	 * 查询所有的不置顶公告
	 * @return
	 */
	@Query("SELECT n FROM Notice n WHERE n.noticetype=1 AND n.istop=1 AND n.isshow=1 ORDER BY n.noticedate DESC")
	List<Notice> queryAdminIsTop();
}
