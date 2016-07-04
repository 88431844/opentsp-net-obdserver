package com.mapbar.netobd.server.business.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mapbar.netobd.bean.dto.DayTravelTo;
import com.mapbar.netobd.bean.dto.DriveMonthTo;
import com.mapbar.netobd.bean.dto.TravelDataTo;
import com.mapbar.netobd.dao.DriveInfoDao;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.InputGetDayTravel;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.InputGetMonthTravel;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.InputGetNowestTravel;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.InputGetTravelDesc;
import com.mapbar.netobd.server.business.DriveInfoServer;

/**
 * @description: 行程日期分布业务层
 * @author xubh
 * @date: 2016年4月29日
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class DriveInfoServerImpl implements DriveInfoServer {

	@Autowired
	private DriveInfoDao driveInfoDao;

	@Override
	public List<DriveMonthTo> findDriveFromMonth(InputGetMonthTravel info) throws Exception {
		return driveInfoDao.findDriveFromMonth(info);
	}

	@Override
	public TravelDataTo findTravelData(InputGetNowestTravel info) throws Exception {
		TravelDataTo travel = driveInfoDao.findTravelData(info);
		if (travel.get_this_travel_len() < 100) {
			travel.set_this_avg_oil(0.0);
			travel.set_this_oil_ware(0.0);
			travel.set_sum_avg_oil(0.0);
			travel.set_travel_cost(0.0);
		}
		return travel;
	}

	@Override
	public TravelDataTo findTravelDataFromId(InputGetTravelDesc info) throws Exception {
		TravelDataTo travel = driveInfoDao.findTravelDataFromId(info);
		if (travel.get_this_travel_len() < 100) {
			travel.set_this_avg_oil(0.0);
			travel.set_this_oil_ware(0.0);
			travel.set_sum_avg_oil(0.0);
			travel.set_travel_cost(0.0);
		}
		return travel;
	}

	@Override
	public List<DayTravelTo> findDayTravel(InputGetDayTravel info) throws Exception {
		return driveInfoDao.findDayTravel(info);
	}

}
