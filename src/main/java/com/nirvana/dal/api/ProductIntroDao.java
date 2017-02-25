package com.nirvana.dal.api;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nirvana.dal.po.ProductIntro;

import java.util.List;
/**
 * 
 * @author Bin
 * 产品介绍数据库访问层
 */
@Repository
public interface ProductIntroDao extends JpaRepository<ProductIntro, Integer>{
	/**
	 * 查找所有的可显示的产品介绍类
	 * @return 产品介绍集合
	 */
    @Query("SELECT p FROM ProductIntro p WHERE p.isshow = true")
    public List<ProductIntro> findShowProductIntro();
}
