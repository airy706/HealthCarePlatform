package com.nirvana.bll.service;

import java.util.List;

import com.nirvana.app.vo.CommunityVO;
import com.nirvana.dal.po.Community;
/**
 * 社区业务层接口
 * @author Bin
 *
 */
public interface CommunityService {
	/**
	 * 添加社区
	 * @param community
	 */
	void add(Community community);
	/**
	 * 删除社区根据社区id
	 * @param id
	 */
	void delById(Integer id);
	/**
	 * 模糊查询社区集合
	 * @param key 查询值社区名 等
	 * @return
	 */
	List<CommunityVO> findFuzzy(String key);
	/**
	 * 根据社区id查询社区
	 * @param id
	 * @return
	 */
	Community findById(Integer id);
	/**
	 * 查询所有社区
	 * @return
	 */
	List<CommunityVO> findAll();
	/**
	 * 查询所有经纬度不为空的社区
	 * @return
	 */
	List<CommunityVO> findAllNotEmpty();
}
