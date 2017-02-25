package com.nirvana.bll.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;

import com.nirvana.app.vo.NoticeVO;
import com.nirvana.dal.po.Notice;

public interface NoticeService {
	/**
	 * 添加公告
	 * @param notice
	 */
	void add(Notice notice);
	
	/**
	 * 删除公告
	 * @param id
	 */
	void delById(Integer id);
	
	/**
	 * 查询所有公告
	 * @return
	 */
	List<NoticeVO> findAllList();
	
	/**
	 * 查询所有超管公告
	 * @return
	 */
	List<NoticeVO> findAdmin();
	
	/**
	 * 查询所有社区公告
	 * @param id
	 * @return
	 */
	List<NoticeVO> findByCommunityId(Integer id);
	
	/**
	 * 根据标题和发布用户名模糊查询 公告 且分页
	 * @param key
	 * @param num
	 * @param size
	 * @param communityid
	 * @return
	 */
	Page<Notice> findByTitleOrUn(String key,Integer num,Integer size,Integer communityid);
	
	/**
	 * 查询该用户所能看到的所有公告
	 * @param userid
	 * @return
	 */
	List<NoticeVO> findNoticeByUid(Integer userid);

	/**
	 * 查询某一个公告
	 * @param noticeid
	 * @return
	 */
	NoticeVO findByNid(Integer noticeid);

	/**
	 * 查询一个时间段内的公告
	 * @param start
	 * @param end
	 * @return
	 */
	List<NoticeVO> findByDate(Date start, Date end);

	/**
	 * 某个公告置顶
	 * @param noticeid
	 */
	void setTopByNid(Integer noticeid);
}
