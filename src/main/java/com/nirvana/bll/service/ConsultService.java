package com.nirvana.bll.service;

import java.util.List;

import com.nirvana.app.vo.UserVO;
import com.nirvana.dal.po.Consulttype;

public interface ConsultService {

	List<Consulttype> findAllTypeByCid(Integer communityId);

	List<UserVO> findAskByCid(Integer communityId);

}
