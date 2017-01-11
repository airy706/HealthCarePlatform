package com.nirvana.bll.bo;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nirvana.bll.service.ConsulttypeService;
import com.nirvana.dal.api.ConsulttypeDao;
import com.nirvana.dal.po.Consulttype;

@Service
@Transactional
public class ConsulttypeServiceBO implements ConsulttypeService {

	@Autowired
	private ConsulttypeDao typedao;

	@Override
	public void delById(Integer typeid) {
		typedao.delete(typeid);
	}

	@Override
	public void add(Consulttype consulttype) {
		typedao.save(consulttype);
	}

}
