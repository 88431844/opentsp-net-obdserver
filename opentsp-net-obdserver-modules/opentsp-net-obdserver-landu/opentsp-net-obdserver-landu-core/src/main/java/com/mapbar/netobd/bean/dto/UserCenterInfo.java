package com.mapbar.netobd.bean.dto;

import java.io.Serializable;

/**
 * 从用户中心获取的用户信息（目前只有电话号码。后期可能会新增很多属性）
 */
public class UserCenterInfo implements Serializable {
	private static final long serialVersionUID = -6333621132192027586L;
	// 访问的状态码
	private String code;
	// 电话号码
	private String phone;
	// 正式账号名称
	private String account;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "UserCenterInfo [code=" + code + ", phone=" + phone + ", account=" + account + "]";
	}
}
