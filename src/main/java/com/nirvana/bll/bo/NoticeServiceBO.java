package com.nirvana.bll.bo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

	public void add(Notice notice) {
		noticedao.save(notice);
	}

	public void delById(Integer id) {
		noticedao.delete(id);
	}

	public List<NoticeVO> findAllList() {
		List<Notice> polist = noticedao.findAll();
		return NoticeVO.toVoList(polist);
	}

	public List<NoticeVO> findAdmin(Integer num,Integer size) {
		PageRequest request = this.buildPageRequest(num, size);
		Page<Notice> page1 = noticedao.queryAdminNotTop(request);
		List<Notice> list1 = page1.getContent();
		List<Notice> list2 = noticedao.queryAdminIsTop();
		for (Notice n : list1) {
			list2.add(n);
		}
		return NoticeVO.toVoList(list2);
	}

	public List<NoticeVO> findByCommunityId(Integer id) {
		List<Notice> polist = noticedao.queryByCommunityId(id);
		return NoticeVO.toVoList(polist);
	}

	private PageRequest buildPageRequest(int pageNumber, int pagzSize) {
		return new PageRequest(pageNumber - 1, pagzSize, null);
	}

	public Page<Notice> findByTitleOrUn(String key, Integer num, Integer size, Integer communityid) {
		PageRequest request = this.buildPageRequest(num, size);
		Page<Notice> polist = null;
		if (communityid == null) {
			polist = noticedao.fuzzyQuery(key, request);
		} else {
			polist = noticedao.fuzzyQueryByCid(key, request, communityid);
		}
		return polist;
	}

	public List<NoticeVO> findNoticeByUid(Integer userid,Integer num,Integer size) {
		PageRequest request = this.buildPageRequest(num,size);
		User user = userdao.findOne(userid);
		List<Notice> list1 = null;
		List<Notice> list2 = new ArrayList<Notice>();
		if (user.getCommunity() != null) {
			//按照置顶在前 按照时间排列
			Page<Notice> page= noticedao.findNoticeByCidNotTop(user.getCommunity().getCommunityid(),request);
			list1 = page.getContent();
			list2 = noticedao.findNoticeByCidIsTop(user.getCommunity().getCommunityid());
			for (Notice n : list1) {
				if (n.isIstop()) {
					list2.add(n);
				}
			}
			for (Notice n : list1) {
				if (!n.isIstop()) {
					list2.add(n);
				}
			}
		} else {
			Page<Notice> page1= noticedao.queryAdminNotTop(request);
			list1 = page1.getContent();
			list2 = noticedao.queryAdminIsTop();
			for (Notice n : list1) {
				list2.add(n);
			}
		}
		List<NoticeVO> volist = new ArrayList<NoticeVO>();
		for (Notice notice : list2) {
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
			vo.setAttachurl(notice.getAttachurl());
			vo.setIsshort(notice.isIsshort());
			vo.setIstop(notice.isIstop());
			volist.add(vo);

		}
		return volist;
	}

	public NoticeVO findByNid(Integer noticeid) {
		Notice notice = noticedao.findOne(noticeid);
		if (notice == null) {
			return null;
		}
		NoticeVO vo = new NoticeVO(notice);
		return vo;
	}

	public List<NoticeVO> findByDate(Date start, Date end) {
		List<Notice> list = noticedao.findByDate(start, end);
		List<NoticeVO> volist = new ArrayList<NoticeVO>();
		for (Notice notice : list) {
			NoticeVO vo = new NoticeVO(notice);
			volist.add(vo);
		}
		return volist;
	}

	public void setTopByNid(Integer noticeid) {
		Notice notice = noticedao.findOne(noticeid);
		if(notice.isIstop()==true){
			notice.setIstop(false);
		}else{
			notice.setIstop(true);
		}
		noticedao.save(notice);
	}
}
