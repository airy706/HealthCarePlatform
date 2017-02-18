package com.nirvana.bll.bo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nirvana.app.vo.DataVO;
import com.nirvana.app.vo.NodeDataVO;
import com.nirvana.bll.service.NodeDataService;
import com.nirvana.dal.api.NodeDataDao;
import com.nirvana.dal.api.UserDao;
import com.nirvana.dal.po.NodeData;

@Service
@Transactional
public class NodeDataServiceBO implements NodeDataService {
	@Autowired
	private NodeDataDao nodedatadao;

	@Autowired
	private UserDao userdao;

	private String[] posture = { "正常", "长期不动", "跌倒" };
	private String[] help = { "正常", "求救" };
	private String[] wheelchair = { "正常", "右翻", "前翻", "后翻", "左翻" };
	private String[] cushion = { "直坐", "左倾", "右倾", "前倾", "后倾" };

	@Override
	public void addData(NodeData data) {
		data = nodedatadao.save(data);

	}

	@Override
	public NodeDataVO findByUidAndType(Integer userid, Integer sensortype, Date start, Date end) {
		// 需要返回的vo
		NodeDataVO vo = new NodeDataVO();
		// 准备数据data
		String did = userdao.findOne(userid).getUseridentity();
		System.out.println(did);
		List<NodeData> nodedatas = nodedatadao.findByDidAndTypeinWeek(did, sensortype, start, end);
		List<DataVO> data = new ArrayList<DataVO>();
		System.out.println(nodedatas.size());
		for (NodeData nodedata : nodedatas) {
			DataVO datavo = new DataVO();
			System.out.println(nodedata.getData());
			datavo.setTime(nodedata.getStatus_change_time());
			datavo.setValue(nodedata.getData());
			data.add(datavo);
		}
		vo.setData(data);
		// 准备数据name和valueset
		List<String> valueset = null;
		String name = null;
		if (sensortype == 4) {
			name = "血压";
		} else if (sensortype == 12) {
			name = "心率";
		} else if (sensortype == 6) {
			name = "人体状态";
			valueset = Arrays.asList(posture);
		} else if (sensortype == 99) {
			name = "一键求救";
			valueset = Arrays.asList(help);
		} else if (sensortype == 7) {
			name = "轮椅状态";
			valueset = Arrays.asList(wheelchair);
		} else if (sensortype == 3) {
			name = "压力坐垫";
			valueset = Arrays.asList(cushion);
		} else if(sensortype == 66){
			name="小米手环";
		}else{
			name="其他";
		}
		vo.setName(name);
		vo.setValueset(valueset);
		return vo;
	}

}
