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

	@Override
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

	@Override
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

	@Override
	public void addOne(Consult consult) {
		consultdao.save(consult);
	}

	@Override
	public void delById(Integer id) {
		consultdao.delete(id);
	}

	@Override
	public void finishByCid(Integer id) {
		Consult consult = consultdao.findOne(id);
		consult.setIsfinish(true);
		consult.setFinishtime(new Date());
		consultdao.save(consult);
	}

	// 可能需要添加 提交人的更改 todo
	@Override
	public void update(Consult consult) {
		Consult old = consultdao.findOne(consult.getConsultid());
		old.setConsulttype(consult.getConsulttype());
		old.setContent(consult.getContent());
		old.setToask(consult.getToask());
		consultdao.save(old);
	}

	@Override
	public List<ConsultVO> findUndoByUid(Integer id) {
		List<Consult> list = consultdao.findUndoByUid(id);
		List<ConsultVO> volist = new ArrayList<ConsultVO>();
		for (Consult consult : list) {
			ConsultVO vo = new ConsultVO();
			vo.setConsultId(consult.getConsultid());
			vo.setConsultType(consult.getConsulttype().getTypename());
			vo.setContent(consult.getContent());
			vo.setTypeId(consult.getConsulttype().getTypeid());
			vo.setToaskId(consult.getToask().getUserid());
			vo.setToaskName(consult.getUser().getUsername());
			// todo commituserid
			volist.add(vo);
		}
		return volist;
	}

	@Override
	public List<ConsultVO> findDoneByUid(Integer id) {
		List<Consult> list = consultdao.findDoneByUid(id);
		List<ConsultVO> volist = new ArrayList<ConsultVO>();
		for (Consult consult : list) {
			ConsultVO vo = new ConsultVO();
			vo.setConsultId(consult.getConsultid());
			vo.setConsultType(consult.getConsulttype().getTypename());
			vo.setContent(consult.getContent());
			// todo commituserid
			vo.setTypeId(consult.getConsulttype().getTypeid());
			vo.setToaskId(consult.getToask().getUserid());
			vo.setToaskName(consult.getUser().getUsername());
			volist.add(vo);
		}
		return volist;
	}

	private PageRequest buildPageRequest(int pageNumber, int pagzSize) {
		return new PageRequest(pageNumber - 1, pagzSize, null);
	}

	@Override
	public Page<Consult> findByKey(Integer communityid, String key, Integer num, Integer size) {
		PageRequest request = this.buildPageRequest(num, size);
		Page<Consult> pages = consultdao.findPageByKeyAndCid(communityid, key, request);
		return pages;
	}

}
