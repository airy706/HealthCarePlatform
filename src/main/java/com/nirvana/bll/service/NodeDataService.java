package com.nirvana.bll.service;

import java.util.Date;

import com.nirvana.app.vo.NodeDataVO;
import com.nirvana.dal.po.NodeData;

public interface NodeDataService {
	/**
	 * 添加数据
	 * @param data
	 */
	void addData(NodeData data);

	/**
	 * 查询时间段内某类型的数据
	 * @param userid
	 * @param sensortype
	 * @param start
	 * @param end
	 * @return
	 */
	NodeDataVO findByUidAndType(Integer userid, Integer sensortype, Date start, Date end);
}
