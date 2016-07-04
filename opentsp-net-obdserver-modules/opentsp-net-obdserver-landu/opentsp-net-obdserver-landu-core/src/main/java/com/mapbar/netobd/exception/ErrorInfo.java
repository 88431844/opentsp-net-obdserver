package com.mapbar.netobd.exception;

public class ErrorInfo {
	public static final Integer OK = 0;
	public static final Integer ERROR = 100;

	private Integer code;
	private String message;
	private String url;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
