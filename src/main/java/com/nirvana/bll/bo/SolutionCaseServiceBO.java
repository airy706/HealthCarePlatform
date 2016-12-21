package com.nirvana.bll.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nirvana.bll.service.SolutionCaseService;
import com.nirvana.dal.api.SolutionCaseDao;

@Service
@Transactional
public class SolutionCaseServiceBO implements SolutionCaseService {
	@Autowired
	private SolutionCaseDao solutioncasedao;
}
