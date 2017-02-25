package com.nirvana.bll.service;

import java.util.List;

import com.nirvana.app.vo.ProductIntroVO;
import com.nirvana.dal.po.ProductIntro;

public interface ProductIntroService {
	/**
	 * 添加产品介绍
	 * @param intro
	 */
	void add(ProductIntro intro);
	 
	/**
	 * 删除介绍
	 * @param id
	 */
	void delById(Integer id);
	
	/**
	 * 查询所有产品介绍
	 * @return
	 */
	List<ProductIntro> findAll();

	/**
	 * 查询所有可以显示的产品介绍
	 * @return
	 */
	List<ProductIntroVO> findShowProductIntro();
}
