package com.mapbar.netobd.exception;

public class NetObdException extends Exception {

	private static final long serialVersionUID = -9170208684596321277L;
	private String code; // 异常对应的返回码
	private String msg; // 异常对应的描述信息

	public NetObdException() {
		super();
	}

	public NetObdException(String msg) {
		super(msg);
		this.msg = msg;
	}

	public NetObdException(String code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}
}