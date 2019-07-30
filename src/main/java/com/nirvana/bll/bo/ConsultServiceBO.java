package com.nirvana.bll.bo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.nirvana.app.vo.ConsultVO;
import com.nirvana.app.vo.ConsulttypeVO;
import com.nirvana.app.vo.UserVO;
import com.nirvana.bll.service.ConsultService;
import com.nirvana.dal.api.ConsultDao;
import com.nirvana.dal.api.ConsulttypeDao;
import com.nirvana.dal.api.UserDao;
import com.nirvana.dal.po.Consult;
import com.nirvana.dal.po.Consulttype;
import com.nirvana.dal.po.User;

@Service
@Transactional
public class ConsultServiceBO implements ConsultService {

	@Autowired
	private ConsulttypeDao typedao;

	@Autowired
	private UserDao userdao;

	@Autowired
	private ConsultDao consultdao;

	public List<ConsulttypeVO> findAllTypeByCid(Integer communityId, String key) {
		List<Consulttype> list = null;
		if (key == null || "".equals(key)) {
			list = typedao.findAllTypeByCid(communityId);
		} else {
			list = typedao.findTypeByCidAndKey(communityId, key);
		}
		List<ConsulttypeVO> volist = new ArrayList<ConsulttypeVO>();
		for (Consulttype type : list) {
			ConsulttypeVO vo = new ConsulttypeVO();
			vo.setTypeid(type.getTypeid());
			vo.setTypename(type.getTypename());
			volist.add(vo);
		}
		return volist;
	}

	public List<UserVO> findAskByCid(Integer communityId) {
		List<User> list = userdao.findManagerByCid(communityId);
		List<UserVO> volist = new ArrayList<UserVO>();
		for (User user : list) {
			UserVO vo = new UserVO();
			vo.setUserid(user.getUserid());
			vo.setUsername(user.getUsername());
			volist.add(vo);
		}
		return volist;
	}

	public void addOne(Consult consult) {
		consultdao.save(consult);
	}

	public void delById(Integer id) {
		consultdao.delete(id);
	}

	public void finishByCid(Integer id) {
		Consult consult = consultdao.findOne(id);
		consult.setIsfinish(true);
		consult.setFinishtime(new Date());
		consultdao.save(consult);
	}

	// 可能需要添加 提交人的更改 todo
	public void update(Consult consult) {
		Consult old = consultdao.findOne(consult.getConsultid());
		//old.setUser(consult.getUser());
		old.setConsulttype(consult.getConsulttype());
		old.setContent(consult.getContent());
		old.setToask(consult.getToask());
		consultdao.save(old);
	}

	public Page<Consult> findUndoByUid(Integer id, Integer num, Integer size) {
		PageRequest request = this.buildPageRequest(num, size);
		Page<Consult> page = consultdao.findUndoByUid(id, request);
		return page;
	}

	public Page<Consult> findDoneByUid(Integer id, Integer num, Integer size) {
		PageRequest request = this.buildPageRequest(num, size);
		Page<Consult> page = consultdao.findDoneByUid(id, request);
		return page;
	}

	private PageRequest buildPageRequest(int pageNumber, int pagzSize) {
		return new PageRequest(pageNumber - 1, pagzSize, null);
	}

	public Page<Consult> findByKey(Integer communityid, String key, Integer num, Integer size, Integer isfinish) {
		PageRequest request = this.buildPageRequest(num, size);
		Page<Consult> pages=null;
		if (isfinish == 1) {
			pages = consultdao.findPageByKeyAndCid(communityid, key, request);
		}
		if (isfinish == 0) {
			pages = consultdao.findPageByKeyAndCidfinish(communityid, key, request);
		}
		return pages;
	}

}
