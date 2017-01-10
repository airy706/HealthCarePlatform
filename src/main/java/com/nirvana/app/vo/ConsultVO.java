package com.nirvana.app.vo;

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

	public ConsultVO() {
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
