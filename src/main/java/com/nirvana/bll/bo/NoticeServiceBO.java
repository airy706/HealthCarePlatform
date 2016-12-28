package com.nirvana.bll.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nirvana.app.vo.NoticeVO;
import com.nirvana.bll.service.NoticeService;
import com.nirvana.dal.api.NoticeDao;
import com.nirvana.dal.po.Notice;

@Service
@Transactional
public class NoticeServiceBO implements NoticeService{
	@Autowired
	private NoticeDao noticedao;

	@Override
	public void add(Notice notice) {
		noticedao.save(notice);
	}

	@Override
	public void delById(Integer id) {
		noticedao.delete(id);
	}

	@Override
	public List<NoticeVO> findAllList() {
		List<Notice> polist = noticedao.findAll();
		return NoticeVO.toVoList(polist);
	}

	@Override
	public List<NoticeVO> findAdmin() {
		List<Notice> polist =noticedao.queryAdmin();
		return NoticeVO.toVoList(polist);
	}

	@Override
	public List<NoticeVO> findByCommunityId(Integer id) {
		List<Notice> polist =noticedao.queryByCommunityId(id);
		return NoticeVO.toVoList(polist);
	}
}
