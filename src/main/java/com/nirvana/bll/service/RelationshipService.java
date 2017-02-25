package com.nirvana.bll.service;

import java.util.List;

import com.nirvana.app.vo.LinkManVO;
import com.nirvana.dal.po.Relationship;

public interface RelationshipService {
	/**
	 * 添加联系人
	 * @param ship
	 */
	void add(Relationship ship);
	
	/**
	 * 删除联系人
	 * @param id
	 */
	void delById(Integer id);

	/**
	 * 查询用户的所有联系人
	 * @param userid
	 * @return
	 */
	List<LinkManVO> findAllByUid(Integer userid);

	/**
	 * 用于联系人登录
	 * @param account
	 * @param password
	 * @return
	 */
	Relationship findOneByAccountAndPsd(String account, String password);
}
