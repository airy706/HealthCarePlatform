package com.nirvana.bll.service;

import java.util.Date;
import java.util.List;

import com.nirvana.app.vo.AlarmFilterVO;
import com.nirvana.app.vo.ExceptionVO;
import com.nirvana.dal.po.AlarmData;

public interface AlarmDataService {

	void addData(AlarmData data);
	
	List<ExceptionVO> findAllRedo(Integer id);

	List<ExceptionVO> detect(Integer id,Integer communityid);

	List<ExceptionVO> findAlltype();

	List<ExceptionVO> findAllTimes();

	AlarmFilterVO findByFilter(String[] ids, String[] types, Date start, Date end);

	void sloveByAid(Integer id);
}
