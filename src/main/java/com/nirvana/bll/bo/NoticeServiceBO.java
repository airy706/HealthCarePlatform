package com.nirvana.bll.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nirvana.bll.service.NoticeService;
import com.nirvana.dal.api.NoticeDao;

@Service
@Transactional
public class NoticeServiceBO implements NoticeService{
	@Autowired
	private NoticeDao noticedao;
}
