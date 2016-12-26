package com.nirvana.bll.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nirvana.bll.service.AlarmDataService;
import com.nirvana.dal.api.AlarmDataDao;
@Service
@Transactional
public class AlarmDataServiceBO implements AlarmDataService{
	
	@Autowired
	private AlarmDataDao alarmdatadao;
}
