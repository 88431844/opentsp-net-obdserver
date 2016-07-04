package com.mapbar.netobd.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value = NetObdException.class)
	@ResponseBody
	public ErrorInfo jsonErrorHandler(HttpServletRequest req, NetObdException e) throws Exception {
		ErrorInfo r = new ErrorInfo();
		r.setMessage(e.getMessage());
		r.setCode(ErrorInfo.ERROR);
		r.setUrl(req.getRequestURL().toString());
		return r;
	}

}