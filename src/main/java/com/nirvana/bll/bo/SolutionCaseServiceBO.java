package com.nirvana.bll.bo;

import java.util.List;

import com.nirvana.app.vo.SolutionCaseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nirvana.bll.service.SolutionCaseService;
import com.nirvana.dal.api.SolutionCaseDao;
import com.nirvana.dal.po.SolutionCase;

@Service
@Transactional
public class SolutionCaseServiceBO implements SolutionCaseService {
	@Autowired
	private SolutionCaseDao solutioncasedao;

	public void add(SolutionCase solutionCase) {
		solutioncasedao.save(solutionCase);
	}

	public void delById(Integer id) {
		solutioncasedao.delete(id);
	}

	public List<SolutionCase> findAll() {
		return solutioncasedao.findAll();
	}

	public List<SolutionCaseVO> findShowSolutionCase() {
		return SolutionCaseVO.toListVO(solutioncasedao.findShowSolutionCase());
	}
}
