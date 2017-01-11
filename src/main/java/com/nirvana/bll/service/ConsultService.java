package com.nirvana.bll.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.nirvana.app.vo.ConsultVO;
import com.nirvana.app.vo.ConsulttypeVO;
import com.nirvana.app.vo.UserVO;
import com.nirvana.dal.po.Consult;

public interface ConsultService {

	List<ConsulttypeVO> findAllTypeByCid(Integer communityId,String key);

	List<UserVO> findAskByCid(Integer communityId);

	void addOne(Consult consult);

	void delById(Integer id);

	void finishByCid(Integer id);

	void update(Consult consult);

	List<ConsultVO> findUndoByUid(Integer id);

	List<ConsultVO> findDoneByUid(Integer id);
	
	Page<Consult> findByKey(Integer communityid,String key,Integer num,Integer size);

}
