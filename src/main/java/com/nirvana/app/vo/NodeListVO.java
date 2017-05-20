package com.nirvana.app.vo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;

import com.nirvana.dal.po.Node;
import com.nirvana.dal.po.User;

public class NodeListVO {
	private Long count;
	private List<UserVO> data;

	public NodeListVO(Long count, List<UserVO> data) {
		super();
		this.count = count;
		this.data = data;
	}

	public NodeListVO(Page<User> pages) {
		super();
		this.count = pages.getTotalElements();
		List<User> content = pages.getContent();
		List<UserVO> list = new ArrayList<UserVO>();
		for (int i = 0; i < content.size(); i++) {
			User user = content.get(i);
			Set<Node> nodes = user.getNodes();
			ArrayList<Node> nodelist = new ArrayList<Node>(nodes);
			Collections.sort(nodelist, new Comparator<Node>() {

				@Override
				public int compare(Node o1, Node o2) {
					if(o1.getNodeid()>o2.getNodeid()){
						return 1;
					}else{
						return -1;
					}
					
				}
				
			});
			List<NodeVO> nodevos = new ArrayList<NodeVO>();
			for (Node node : nodelist) {
				nodevos.add(new NodeVO(node));
			}
			UserVO uservo = new UserVO(user,1);
			uservo.setNodes(nodevos);
			list.add(uservo);
		}
		this.data = list;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public List<UserVO> getData() {
		return data;
	}

	public void setData(List<UserVO> data) {
		this.data = data;
	}

}
