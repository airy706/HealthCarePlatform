package com.nirvana.bll.service;

import com.nirvana.dal.po.Consulttype;

public interface ConsulttypeService {

	/**
	 * 删除咨询类型
	 * @param typeid
	 */
	void delById(Integer typeid);

	/**
	 * 添加咨询类型
	 * @param consulttype
	 */
	void add(Consulttype consulttype);

}
