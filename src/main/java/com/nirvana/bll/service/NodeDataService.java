package com.nirvana.bll.service;

import java.util.Date;

import com.nirvana.app.vo.NodeDataVO;
import com.nirvana.dal.po.NodeData;

public interface NodeDataService {
	void addData(NodeData data);

	NodeDataVO findByUidAndType(Integer userid, Integer sensortype, Date start, Date end);
}
