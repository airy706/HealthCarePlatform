package com.nirvana.bll.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.nirvana.app.vo.ConsultVO;
import com.nirvana.app.vo.ConsulttypeVO;
import com.nirvana.app.vo.UserVO;
import com.nirvana.dal.po.Consult;

public interface ConsultService {

	/**
	 * 模糊查询社区所有咨询类型
	 * @param communityId
	 * @param key
	 * @return
	 */
	List<ConsulttypeVO> findAllTypeByCid(Integer communityId,String key);

	/**
	 * 查询社区内可以所有被咨询的人
	 * @param communityId
	 * @return
	 */
	List<UserVO> findAskByCid(Integer communityId);

	
	/**
	 *添加咨询 
	 * @param consult
	 */
	void addOne(Consult consult);

	
	/**
	 * 删除咨询
	 * @param id
	 */
	void delById(Integer id);

	/**
	 * 完成某一个咨询
	 * @param id
	 */
	void finishByCid(Integer id);

	/**
	 * 更新咨询
	 * @param consult
	 */
	void update(Consult consult);

	
	/**
	 * 查询用户为完成的咨询
	 * @param id
	 * @param num
	 * @param size
	 * @return
	 */
	Page<Consult> findUndoByUid(Integer id,Integer num,Integer size);

	/**
	 * 查询完成的咨询
	 * @param id
	 * @param num
	 * @param size
	 * @return
	 */
	Page<Consult> findDoneByUid(Integer id,Integer num,Integer size);
	
	/**
	 * 模糊查询所有咨询
	 * @param communityid
	 * @param key
	 * @param num
	 * @param size
	 * @return
	 */
	Page<Consult> findByKey(Integer communityid,String key,Integer num,Integer size);

}
