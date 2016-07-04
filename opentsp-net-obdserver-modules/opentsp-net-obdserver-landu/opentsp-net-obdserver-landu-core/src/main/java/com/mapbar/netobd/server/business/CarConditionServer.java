package com.mapbar.netobd.server.business;

import com.mapbar.netobd.bean.dto.CarConditionInfoTo;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.InputGetConditionInfo;

public interface CarConditionServer {
	/**
	 * 查询车况信息
	 * 
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public CarConditionInfoTo findCarConditionInfo(InputGetConditionInfo info) throws Exception;

}
