package com.nirvana.app.vo;

import java.util.Date;

//获取所有未完成咨询服务/consult/undo
//request:
//	userId
//response:
//	[{
//		consultId
//		consultUserId //咨询人id
//		consultType //咨询类型
//		content //咨询内容
//	}]
public class ConsultVO {
	private Integer consultId;
	private Integer consultUserId;
	private String consultType;
	private String content;
	private Integer typeId;
	private String toaskName;
	private Integer toaskId;
	private String username;
	private Date commintTime;
	private boolean isFinish;
	private Date finishTime;

	public ConsultVO() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getCommintTime() {
		return commintTime;
	}

	public void setCommintTime(Date commintTime) {
		this.commintTime = commintTime;
	}

	public boolean isFinish() {
		return isFinish;
	}

	public void setFinish(boolean isFinish) {
		this.isFinish = isFinish;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public String getToaskName() {
		return toaskName;
	}

	public void setToaskName(String toaskName) {
		this.toaskName = toaskName;
	}

	public Integer getToaskId() {
		return toaskId;
	}

	public void setToaskId(Integer toaskId) {
		this.toaskId = toaskId;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public Integer getConsultId() {
		return consultId;
	}

	public void setConsultId(Integer consultId) {
		this.consultId = consultId;
	}

	public Integer getConsultUserId() {
		return consultUserId;
	}

	public void setConsultUserId(Integer consultUserId) {
		this.consultUserId = consultUserId;
	}

	public String getConsultType() {
		return consultType;
	}

	public void setConsultType(String consultType) {
		this.consultType = consultType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
