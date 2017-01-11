package com.nirvana.bll.service;

import com.nirvana.dal.po.Consulttype;

public interface ConsulttypeService {

	void delById(Integer typeid);

	void add(Consulttype consulttype);

}
