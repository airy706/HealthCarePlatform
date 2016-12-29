package com.nirvana.bll.service;

import java.util.List;

import com.nirvana.dal.po.ProductIntro;

public interface ProductIntroService {
	void add(ProductIntro intro);
	 
	void delById(Integer id);
	
	List<ProductIntro> findAll();
}
