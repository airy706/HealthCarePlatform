package com.nirvana.dal.api;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nirvana.dal.po.SolutionCase;

@Repository
public interface SolutionCaseDao extends JpaRepository<SolutionCase, Integer>{

}
