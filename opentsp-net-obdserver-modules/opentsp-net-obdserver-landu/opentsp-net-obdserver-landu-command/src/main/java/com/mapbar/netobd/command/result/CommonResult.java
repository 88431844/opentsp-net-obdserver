package com.mapbar.netobd.command.result;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.navinfo.opentsp.common.messaging.Command;

public class CommonResult extends JSONObject implements Command.Result {

	private static final long serialVersionUID = 5743817500139455636L;

	private int status = 0;
	private String msg = "成功";

	public CommonResult() {
		super();
		this.put("status", status);
		this.put("msg", msg);
	}

	public CommonResult(boolean ordered) {
		super(ordered);
	}

	public CommonResult(int initialCapacity, boolean ordered) {
		super(initialCapacity, ordered);
	}

	public CommonResult(int initialCapacity) {
		super(initialCapacity);
	}

	public CommonResult(Map<String, Object> map) {
		super(map);
	}
	

}
