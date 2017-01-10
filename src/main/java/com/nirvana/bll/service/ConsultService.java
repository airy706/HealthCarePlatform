package com.nirvana.bll.service;

import java.util.List;

import com.nirvana.app.vo.ConsultVO;
import com.nirvana.app.vo.UserVO;
import com.nirvana.dal.po.Consult;
import com.nirvana.dal.po.Consulttype;

public interface ConsultService {

	List<Consulttype> findAllTypeByCid(Integer communityId);

	List<UserVO> findAskByCid(Integer communityId);

	void addOne(Consult consult);

	void delById(Integer id);

	void finishByCid(Integer id);

	void update(Consult consult);

	List<ConsultVO> findUndoByUid(Integer id);

	List<ConsultVO> findDoneByUid(Integer id);

}
