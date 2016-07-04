package com.mapbar.netobd.dao;

import java.util.List;
import java.util.Map;

import com.mapbar.netobd.bean.dto.DayTravelTo;
import com.mapbar.netobd.bean.dto.DriveMonthTo;
import com.mapbar.netobd.bean.dto.TravelDataTo;
import com.mapbar.netobd.kafkaBean.KafkaTravelData;
import com.mapbar.netobd.kafkaBean.KafkaTravelInfoTo;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.InputGetDayTravel;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.InputGetMonthTravel;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.InputGetNowestTravel;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.InputGetTravelDesc;

public interface DriveInfoDao {
	/**
	 * 根据用户id和月份查询行程信息
	 * 
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public List<DriveMonthTo> findDriveFromMonth(InputGetMonthTravel info) throws Exception;

	/**
	 * 根据 sn和行程id查询，车辆id
	 * 
	 * @param dit
	 * @return
	 * @throws Exception
	 */
	public KafkaTravelInfoTo findTravelInfo(KafkaTravelInfoTo dit) throws Exception;

	/**
	 * 保存行程数据，并写入行程日期数据，并更新用户最后一次使用时间
	 * 
	 * @param dit
	 * @throws Exception
	 */
	public void saveTravelData(KafkaTravelData dit) throws Exception;

	/**
	 * 根据用户id查询最后一条行程
	 * 
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public TravelDataTo findTravelData(InputGetNowestTravel info) throws Exception;

	/**
	 * 根据id查询一条行程
	 * 
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public TravelDataTo findTravelDataFromId(InputGetTravelDesc info) throws Exception;

	/**
	 * 根据用户id和日期查询行程
	 * 
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public List<DayTravelTo> findDayTravel(InputGetDayTravel info) throws Exception;

	/**
	 * 获取速度分段相关信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getSpeedSet() throws Exception;
}
