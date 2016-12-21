package com.nirvana.dal.api;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nirvana.dal.po.ProductIntro;

@Repository
public interface ProductIntroDao extends JpaRepository<ProductIntro, Integer>{

}
