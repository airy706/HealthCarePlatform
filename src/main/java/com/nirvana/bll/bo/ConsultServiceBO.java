package com.nirvana.bll.bo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public List<Consulttype> findAllTypeByCid(Integer communityId) {
		List<Consulttype> list = typedao.findAllTypeByCid(communityId);
		for (Consulttype type : list) {
			type.setCommunity(null);
		}
		return list;
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

}
