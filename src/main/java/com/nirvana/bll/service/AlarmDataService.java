package com.nirvana.bll.service;

import java.util.Date;
import java.util.List;

import com.nirvana.app.vo.AlarmFilterVO;
import com.nirvana.app.vo.ExceptionVO;
import com.nirvana.dal.po.AlarmData;
/**
 * 报警数据接口定义
 * @author Bin
 *
 */
public interface AlarmDataService {

	/**
	 * 添加一条报警数据
	 * @param data
	 */
	void addData(AlarmData data);
	
	/**
	 * 根据用户id 查找其所有未解决的报警数据
	 * @param id
	 * @return
	 */
	List<ExceptionVO> findAllRedo(Integer id);

	/**
	 * 检测社区有没有最新的报警数据
	 * @param id 最新一条报警数据的id
	 * @param communityid 社区id
	 * @return
	 */
	List<ExceptionVO> detect(Integer id,Integer communityid);

	/**
	 * 查找所有异常数据的所有可能类型id
	 * @return
	 */
	List<ExceptionVO> findAlltype();

	/**
	 * 查找所有异常的出现的次数
	 * @return
	 */
	List<ExceptionVO> findAllTimes(Integer communityid);

	/**
	 * 报警数据过滤器 根据条件筛选异常
	 * @param ids 社区id数组
	 * @param types 报警类型id数组
	 * @param start 开始日期
	 * @param end 结束日期
	 * @return
	 */
	AlarmFilterVO findByFilter(String[] ids, String[] types, Date start, Date end);

	/**
	 * 解决某一个报警数据
	 * @param id
	 */
	void sloveByAid(Integer id);

	/**
	 * 根据身份证查找所有未解决的报警数据
	 * @param did
	 * @return
	 */
	List<AlarmData> findUndoByDid(String did);

	/**
	 * 社区内报警数据筛选器
	 * @param communityid 社区id
	 * @param ids 用户id数组
	 * @param types 类型数组
	 * @param start 开始日期
	 * @param end 结束日期
	 * @return
	 */
	AlarmFilterVO findPeopleByFilter(String communityid, String[] ids, String[] types, Date start, Date end);

	/**
	 * 清楚社区内所有的报警数据
	 * @param communityId
	 */
	void rmall(Integer communityId);
}
