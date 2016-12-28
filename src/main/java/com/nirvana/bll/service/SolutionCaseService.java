package com.nirvana.bll.service;

import com.nirvana.dal.po.SolutionCase;

public interface SolutionCaseService {
	void add(SolutionCase solutionCase);
	
	void delById(Integer id);
}
