package com.nirvana.bll.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nirvana.app.vo.NoticeVO;
import com.nirvana.bll.service.NoticeService;
import com.nirvana.dal.api.NoticeDao;
import com.nirvana.dal.api.UserDao;
import com.nirvana.dal.po.Notice;
import com.nirvana.dal.po.User;

@Service
@Transactional
public class NoticeServiceBO implements NoticeService {
	@Autowired
	private NoticeDao noticedao;

	@Autowired
	private UserDao userdao;

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
		List<Notice> polist = noticedao.queryAdmin();
		return NoticeVO.toVoList(polist);
	}

	@Override
	public List<NoticeVO> findByCommunityId(Integer id) {
		List<Notice> polist = noticedao.queryByCommunityId(id);
		return NoticeVO.toVoList(polist);
	}

	@Override
	public List<NoticeVO> findByTitleOrUn(String key) {
		List<Notice> polist = noticedao.fuzzyQuery(key);
		return NoticeVO.toVoList(polist);
	}

	@Override
	public List<NoticeVO> findNoticeByUid(Integer userid) {
		User user = userdao.findOne(userid);
		List<Notice> list = noticedao.findNoticeByCid(user.getCommunity().getCommunityid());
		List<NoticeVO> volist = new ArrayList<NoticeVO>();
		for (Notice notice : list) {
			NoticeVO vo = new NoticeVO();
			vo.setNoticeid(notice.getNoticeid());
			vo.setNoticetitle(notice.getNoticetitle());
			vo.setNoticedate(notice.getNoticedate());
			vo.setIsurl(notice.isIsurl());
			if (notice.isIsurl()) {
				vo.setUrl(notice.getUrl());
			} else {
				vo.setUrl("");
			}
			volist.add(vo);

		}
		return volist;
	}
}
