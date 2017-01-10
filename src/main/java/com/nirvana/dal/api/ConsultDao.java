package com.nirvana.dal.api;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nirvana.dal.po.Consult;

@Repository
public interface ConsultDao extends JpaRepository<Consult, Integer>{

}
