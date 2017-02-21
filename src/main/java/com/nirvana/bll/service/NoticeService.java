package com.nirvana.bll.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;

import com.nirvana.app.vo.NoticeVO;
import com.nirvana.dal.po.Notice;

public interface NoticeService {
	void add(Notice notice);
	
	void delById(Integer id);
	
	List<NoticeVO> findAllList();
	
	List<NoticeVO> findAdmin();
	
	List<NoticeVO> findByCommunityId(Integer id);
	
	Page<Notice> findByTitleOrUn(String key,Integer num,Integer size,Integer communityid);
	
	List<NoticeVO> findNoticeByUid(Integer userid);

	NoticeVO findByNid(Integer noticeid);

	List<NoticeVO> findByDate(Date start, Date end);

	void setTopByNid(Integer noticeid);
}
