package com.nirvana.app.vo;

import java.util.Date;

import com.nirvana.dal.po.AlarmData;
import com.nirvana.dal.po.User;

public class ExceptionVO {
	private Integer exceptionId;
	private String exceptionName;
	private Integer exceptionLevel;
	private Date exceptionTime;
	private String exceptionContent;
	
	public ExceptionVO(){
		
	}
	
	public ExceptionVO(AlarmData data,User user){
		this.exceptionId=data.getDataid();
		this.exceptionLevel=data.getLevel();
		this.exceptionTime = data.getStatus_change_time();
		Integer type = data.getReasontype();
		if(type==4){
			this.exceptionName="血压异常";
			this.exceptionContent=user.getCommunity().getCommunityname()+"的"+user.getUsername()+"血压异常，请立即查看并采取措施！";
		}else if(type==6){
			this.exceptionName="坐姿异常";
			this.exceptionContent=user.getCommunity().getCommunityname()+"的"+user.getUsername()+"坐姿异常，请立即查看并采取措施！";
		}else if(type==99){
			this.exceptionName="一键急救";
			this.exceptionContent=user.getCommunity().getCommunityname()+"的"+user.getUsername()+"一键求救，请立即查看并采取措施！";
		}else if(type==12){
			this.exceptionName="心率异常";
			this.exceptionContent=user.getCommunity().getCommunityname()+"的"+user.getUsername()+"心率异常，请立即查看并采取措施！";
		}else if(type==7){
			this.exceptionName="轮椅异常";
			this.exceptionContent=user.getCommunity().getCommunityname()+"的"+user.getUsername()+"轮椅异常，请立即查看并采取措施！";
		}else if(type==3){
			this.exceptionName="坐垫异常";
			this.exceptionContent=user.getCommunity().getCommunityname()+"的"+user.getUsername()+"坐垫异常，请立即查看并采取措施！";
		}else{
			
		}
		
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
