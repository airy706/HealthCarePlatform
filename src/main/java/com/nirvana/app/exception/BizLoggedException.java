package com.nirvana.app.exception;

@SuppressWarnings("serial")
public class BizLoggedException extends BizException{

	public BizLoggedException(String error_code, String error_msg) {
		super(error_code, error_msg);
		//TODO 将错误信息打印到日志
	}
	
}
