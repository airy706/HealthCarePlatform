package com.nirvana.app.vo;

import java.util.Date;

import com.nirvana.dal.po.AlarmData;
import com.nirvana.dal.po.User;

public class ExceptionVO {
	private Integer exceptionId;
	private Integer userid;
	private String exceptionName;
	private Integer exceptionLevel;
	private Date exceptionTime;
	private String exceptionContent;
	private Integer alarmType;
	private String alarmName;
	private Integer alarmTimes;

	public ExceptionVO() {

	}

	public ExceptionVO(Integer type) {
		this.alarmType = type;
		if (type == 4) {
			this.alarmName = "血压异常";
		} else if (type == 6) {
			this.alarmName = "活动状态异常";
		} else if (type == 99) {
			this.alarmName = "一键急救";
		} else if (type == 12) {
			this.alarmName = "心率异常";
		} else if (type == 7) {
			this.alarmName = "轮椅异常";
		} else if (type == 3) {
			this.alarmName = "坐垫异常";
		} else {

		}
	}

	public ExceptionVO(AlarmData data, User user) {
		this.userid = user.getUserid();
		this.exceptionId = data.getDataid();
		this.exceptionLevel = data.getLevel();
		this.exceptionTime = data.getStatus_change_time();
		Integer type = data.getReasontype();
		if (type == 4) {
			this.exceptionName = "血压异常";
		} else if (type == 6) {
			this.exceptionName = "活动状态异常";		
		} else if (type == 99) {
			this.exceptionName = "一键急救";	
		} else if (type == 12) {
			this.exceptionName = "心率异常";		
		} else if (type == 7) {
			this.exceptionName = "轮椅异常";			
		} else if (type == 3) {
			this.exceptionName = "坐垫异常";
		} else {

		}
		if(user.getCommunity()!=null){
		this.exceptionContent = user.getCommunity().getCommunityname() + "的" + user.getUsername()
		+ this.exceptionName+data.getData()+"，请立即查看并采取措施！";
		}else{
			this.exceptionContent = "个体用户" + user.getUsername()
			+ this.exceptionName+data.getData()+"，请立即查看并采取措施！";
		}
	}

	public Integer getAlarmTimes() {
		return alarmTimes;
	}

	public void setAlarmTimes(Integer alarmTimes) {
		this.alarmTimes = alarmTimes;
	}

	public Integer getAlarmType() {
		return alarmType;
	}

	public void setAlarmType(Integer alarmType) {
		this.alarmType = alarmType;
	}

	public String getAlarmName() {
		return alarmName;
	}

	public void setAlarmName(String alarmName) {
		this.alarmName = alarmName;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public Integer getExceptionId() {
		return exceptionId;
	}

	public void setExceptionId(Integer exceptionId) {
		this.exceptionId = exceptionId;
	}

	public String getExceptionName() {
		return exceptionName;
	}

	public void setExceptionName(String exceptionName) {
		this.exceptionName = exceptionName;
	}

	public Integer getExceptionLevel() {
		return exceptionLevel;
	}

	public void setExceptionLevel(Integer exceptionLevel) {
		this.exceptionLevel = exceptionLevel;
	}

	public Date getExceptionTime() {
		return exceptionTime;
	}

	public void setExceptionTime(Date exceptionTime) {
		this.exceptionTime = exceptionTime;
	}

	public String getExceptionContent() {
		return exceptionContent;
	}

	public void setExceptionContent(String exceptionContent) {
		this.exceptionContent = exceptionContent;
	}

}
