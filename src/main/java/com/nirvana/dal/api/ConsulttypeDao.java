package com.nirvana.dal.api;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nirvana.dal.po.Consulttype;
@Repository
public interface ConsulttypeDao extends JpaRepository<Consulttype, Integer>{

}
