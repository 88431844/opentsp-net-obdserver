package com.mapbar.netobd.server.business;

import java.util.List;

import com.mapbar.netobd.bean.dto.DayTravelTo;
import com.mapbar.netobd.bean.dto.DriveMonthTo;
import com.mapbar.netobd.bean.dto.TravelDataTo;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.InputGetDayTravel;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.InputGetMonthTravel;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.InputGetNowestTravel;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.InputGetTravelDesc;

public interface DriveInfoServer {
	/**
	 * 根据用户id和月份查询行程信息
	 * 
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public List<DriveMonthTo> findDriveFromMonth(InputGetMonthTravel info) throws Exception;

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

}
