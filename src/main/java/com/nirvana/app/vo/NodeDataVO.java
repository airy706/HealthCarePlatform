package com.nirvana.app.vo;

import java.util.List;

public class NodeDataVO {
	private String name;
	private List<String> valueset = null;
	private List<DataVO> data = null;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getValueset() {
		return valueset;
	}

	public void setValueset(List<String> valueset) {
		this.valueset = valueset;
	}

	public List<DataVO> getData() {
		return data;
	}

	public void setData(List<DataVO> data) {
		this.data = data;
	}

}
