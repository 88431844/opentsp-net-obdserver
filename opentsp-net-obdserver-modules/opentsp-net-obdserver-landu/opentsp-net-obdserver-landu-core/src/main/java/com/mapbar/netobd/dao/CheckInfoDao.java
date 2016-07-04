package com.mapbar.netobd.dao;

import com.mapbar.netobd.bean.dto.CheckInfoTo;
import com.mapbar.netobd.kafkaBean.CheckInfoTemp;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.InputGetCheckInfo;

public interface CheckInfoDao {
	/**
	 * 插入体检信息
	 * 
	 * @param cit
	 * @throws Exception
	 */
	public void InsertCheckInfo(CheckInfoTemp cit) throws Exception;

	/**
	 * 根据id查询最新的一条体检信息
	 * 
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public CheckInfoTo findCheckInfo(InputGetCheckInfo info) throws Exception;
}
