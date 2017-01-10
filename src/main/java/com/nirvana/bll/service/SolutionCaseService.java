package com.nirvana.bll.service;

import java.util.List;

import com.nirvana.app.vo.SolutionCaseVO;
import com.nirvana.dal.po.SolutionCase;

public interface SolutionCaseService {
	void add(SolutionCase solutionCase);
	
	void delById(Integer id);
	
	List<SolutionCase> findAll();

	List<SolutionCaseVO> findShowSolutionCase();
}
