package com.nirvana.bll.service;

import java.util.List;

import com.nirvana.app.vo.SolutionCaseVO;
import com.nirvana.dal.po.SolutionCase;

public interface SolutionCaseService {
	/**
	 * 添加案例
	 * @param solutionCase
	 */
	void add(SolutionCase solutionCase);
	
	/**
	 * 删除案例
	 * @param id
	 */
	void delById(Integer id);
	
	/**
	 * 查询所有
	 * @return
	 */
	List<SolutionCase> findAll();

	/**
	 * 查询所有可显示的
	 * @return
	 */
	List<SolutionCaseVO> findShowSolutionCase();
}
