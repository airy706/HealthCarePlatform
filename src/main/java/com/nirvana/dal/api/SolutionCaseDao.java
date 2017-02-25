package com.nirvana.dal.api;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nirvana.dal.po.SolutionCase;

import java.util.List;
/**
 * 
 * @author Bin
 * 案例数据访问层
 */
@Repository
public interface SolutionCaseDao extends JpaRepository<SolutionCase, Integer>{
	/**
	 *  查找所有的可显示的案例
	 * @return
	 */
    @Query("SELECT s FROM SolutionCase s WHERE s.isshow = true")
    List<SolutionCase> findShowSolutionCase();
}
