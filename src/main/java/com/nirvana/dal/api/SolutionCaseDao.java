package com.nirvana.dal.api;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nirvana.dal.po.SolutionCase;

import java.util.List;

@Repository
public interface SolutionCaseDao extends JpaRepository<SolutionCase, Integer>{
    @Query("SELECT s FROM SolutionCase s WHERE s.isshow = true")
    List<SolutionCase> findShowSolutionCase();
}
