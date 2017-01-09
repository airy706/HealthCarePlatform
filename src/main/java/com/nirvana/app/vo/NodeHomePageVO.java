package com.nirvana.app.vo;

public class NodeHomePageVO {

	private Integer nodeType;

	private String nodeName;

	private String latestData;

	private String high;

	private String low;

	public String getHigh() {
		return high;
	}

	public void setHigh(String high) {
		this.high = high;
	}

	public String getLow() {
		return low;
	}

	public void setLow(String low) {
		this.low = low;
	}

	public Integer getNodeType() {
		return nodeType;
	}

	public void setNodeType(Integer nodeType) {
		this.nodeType = nodeType;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getLatestData() {
		return latestData;
	}

	public void setLatestData(String latestData) {
		this.latestData = latestData;
	}

}
