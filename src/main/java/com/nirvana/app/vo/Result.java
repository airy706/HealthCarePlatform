package com.nirvana.app.vo;


public class Result {
	private String state;
	private String msg;
	private Object result;
	
	public static final String SUCCESS="success";
	public static final String FAIL="failed";
	
	public static Result getFailInstance(String msg,Object result){
		Result r=new Result();
		r.state=FAIL;
		r.msg=msg;
		r.result=result;
		return r;
	}
	
	public static Result getSuccessInstance(Object result){
		Result r=new Result();
		r.state=SUCCESS;
		r.msg="";
		r.result=result;
		return r;
	}
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}
	
	
}
