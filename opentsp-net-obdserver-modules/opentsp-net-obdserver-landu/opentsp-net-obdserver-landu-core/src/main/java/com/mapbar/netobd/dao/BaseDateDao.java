package com.mapbar.netobd.dao;

import java.util.List;

import com.mapbar.netobd.bean.dto.CarModelInfoTo;
import com.mapbar.netobd.bean.dto.CheckItemTo;
import com.mapbar.netobd.bean.dto.OilDataTo;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.InputGetOilPrice;

public interface BaseDateDao {
	/**
	 * 根据id获取车辆基本信息
	 * 
	 * @param car_model_id
	 * @return
	 * @throws Exception
	 */
	public CarModelInfoTo getCarFormId(String car_model_id) throws Exception;

	/**
	 * 添加城市油价信息
	 * 
	 * @param oil_list
	 * @throws Exception
	 */
	public void saveOilPrice(List<OilDataTo> oil_list) throws Exception;

	/**
	 * 查询城市油价信息
	 * 
	 * @param area
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public OilDataTo findOilPrice(String area, int type) throws Exception;

	/**
	 * 查询地区和首字母集合
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<OilDataTo> findOilAreaList() throws Exception;

	/**
	 * 根据城市查询油价信息
	 * 
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public List<OilDataTo> findOilFromArea(InputGetOilPrice info) throws Exception;

	/**
	 * 查询所有体检项
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<CheckItemTo> findCheckItem() throws Exception;

}
