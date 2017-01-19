package com.nirvana.app.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ExceptionHandler;

import com.google.gson.Gson;
import com.nirvana.app.exception.BizException;
import com.nirvana.app.exception.BizLoggedException;
import com.nirvana.app.vo.Result;

public class BaseController {
	@ExceptionHandler
	public void handleException(HttpServletResponse response, Exception e) throws Exception {
		if (e instanceof BizException || e instanceof BizLoggedException) {
			BizException be = (BizException) e;
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print(new Gson().toJson(Result.getFailInstance(be.getMessage(), null)));
			return;
		}
		e.printStackTrace(response.getWriter());
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(Result.getFailInstance("失败", e.getMessage())));
	}

}
