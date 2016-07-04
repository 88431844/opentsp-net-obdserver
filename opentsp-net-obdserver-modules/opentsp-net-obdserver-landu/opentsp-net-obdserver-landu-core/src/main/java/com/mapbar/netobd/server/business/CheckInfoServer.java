package com.mapbar.netobd.server.business;

import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.InputGetCheckInfo;

import net.sf.json.JSONObject;

public interface CheckInfoServer {
	/**
	 * 获取最新体检信息
	 * 
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public JSONObject findCheckInfo(InputGetCheckInfo info) throws Exception;

}
