package com.nirvana.bll.service;

import com.nirvana.dal.po.ProductIntro;

public interface ProductIntroService {
	void add(ProductIntro intro);
	 
	void delById(Integer id);
}
