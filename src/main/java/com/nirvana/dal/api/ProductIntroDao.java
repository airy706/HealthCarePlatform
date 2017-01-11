package com.nirvana.dal.api;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nirvana.dal.po.ProductIntro;

import java.util.List;

@Repository
public interface ProductIntroDao extends JpaRepository<ProductIntro, Integer>{
    @Query("SELECT p FROM ProductIntro p WHERE p.isshow = true")
    public List<ProductIntro> findShowProductIntro();
}
