package com.nirvana.app.exception;

@SuppressWarnings("serial")
public class BizException extends RuntimeException {
	
	public static final String SYNTAX_ERROR="语法错误";
	
	
	private String error_code;
	private String error_msg;

	public String getError_code() {
		return error_code;
	}

	public void setError_code(String error_code) {
		this.error_code = error_code;
	}

	public String getError_msg() {
		return error_msg;
	}

	public void setError_msg(String error_msg) {
		this.error_msg = error_msg;
	}

	public BizException(String error_code,String error_msg){
		this.error_code=error_code;
		this.error_msg=error_msg;
	}
	
	
	
}
