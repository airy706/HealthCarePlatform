package com.nirvana.bll.bo;

import java.util.List;

import com.nirvana.app.vo.ProductIntroVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nirvana.bll.service.ProductIntroService;
import com.nirvana.dal.api.ProductIntroDao;
import com.nirvana.dal.po.ProductIntro;

@Service
@Transactional
public class ProductIntroServiceBO implements ProductIntroService{
	@Autowired
	private ProductIntroDao productintrodao;

	@Override
	public void add(ProductIntro intro) {
		productintrodao.save(intro);
	}

	@Override
	public void delById(Integer id) {
		productintrodao.delete(id);
	}

	@Override
	public List<ProductIntro> findAll() {
		return productintrodao.findAll();
	}

	@Override
	public List<ProductIntroVO> findShowProductIntro() {
		return ProductIntroVO.toListVO(productintrodao.findShowProductIntro());
	}

}
