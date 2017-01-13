package com.nirvana.app.vo;

import java.util.Date;

public class NodeHomePageVO {

	private Integer nodeType;

	private String nodeName;

	private String latestData;

	private String high;

	private String low;

	private Date lastestTime;

	private Date highTime;

	private Date lowTime;

	public Date getLastestTime() {
		return lastestTime;
	}

	public void setLastestTime(Date lastestTime) {
		this.lastestTime = lastestTime;
	}

	public Date getHighTime() {
		return highTime;
	}

	public void setHighTime(Date highTime) {
		this.highTime = highTime;
	}

	public Date getLowTime() {
		return lowTime;
	}

	public void setLowTime(Date lowTime) {
		this.lowTime = lowTime;
	}

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
