package com.nirvana.bll.service;

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
	
	Page<Notice> findByTitleOrUn(String key,Integer num,Integer size);
	
	List<NoticeVO> findNoticeByUid(Integer userid);
}
