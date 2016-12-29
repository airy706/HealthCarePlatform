package com.nirvana.app.vo;

import java.util.ArrayList;
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
			List<NodeVO> nodevos = new ArrayList<NodeVO>();
			for (Node node : nodes) {
				nodevos.add(new NodeVO(node));
			}
			UserVO uservo = new UserVO(user);
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
