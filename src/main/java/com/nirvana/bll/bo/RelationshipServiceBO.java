package com.nirvana.bll.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nirvana.app.vo.LinkManVO;
import com.nirvana.bll.service.RelationshipService;
import com.nirvana.dal.api.RelationshipDao;
import com.nirvana.dal.po.Relationship;

@Service
@Transactional
public class RelationshipServiceBO implements RelationshipService {
	@Autowired
	private RelationshipDao relationshipdao;

	@Override
	public void add(Relationship ship) {
		relationshipdao.save(ship);
	}

	@Override
	public void delById(Integer id) {
		relationshipdao.delete(id);
	}

	@Override
	public List<LinkManVO> findAllByUid(Integer userid) {
		List<Relationship> list = relationshipdao.findALLByUid(userid);
		List<LinkManVO> volist = new ArrayList<LinkManVO>();
		for (Relationship ship : list) {
			LinkManVO vo = new LinkManVO();
			vo.setRelationid(ship.getRelationid());
			vo.setRelationname(ship.getRelationname());
			vo.setRelationaccount(ship.getRelationaccount());
			//vo.setRelationpassword(ship.getRelationpassword());
			vo.setRelationtel(ship.getRelationtel());
			volist.add(vo);
		}
		return volist;
	}

	@Override
	public Relationship findOneByAccountAndPsd(String account, String password) {
		Relationship ship = relationshipdao.findOneByAccountAndPsd(account,password);
		if(ship==null){
			return null;
		}
		return ship;
	}
}
