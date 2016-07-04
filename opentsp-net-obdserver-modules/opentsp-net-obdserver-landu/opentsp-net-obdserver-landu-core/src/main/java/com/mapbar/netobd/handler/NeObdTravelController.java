package com.mapbar.netobd.handler;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mapbar.netobd.bean.dto.DayTravelTo;
import com.mapbar.netobd.bean.dto.DriveMonthTo;
import com.mapbar.netobd.bean.dto.TravelDataTo;
import com.mapbar.netobd.protocolBuffer.ProBufUnits;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.InputGetDayTravel;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.InputGetMonthTravel;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.InputGetNowestTravel;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.InputGetTravelDesc;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.OutputGetDayTravel;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.OutputGetDayTravel.TravelList;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.OutputGetMonthTravel;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.OutputGetMonthTravel.DayArray;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.OutputGetNowestTravel;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.OutputGetNowestTravel.NowTravelDesc;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.OutputGetTravelDesc;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.OutputGetTravelDesc.TravelDesc;
import com.mapbar.netobd.server.business.DriveInfoServer;
import com.mapbar.netobd.units.CustomUnits;
import com.mapbar.netobd.units.InputStreamUtil;
import com.mapbar.netobd.units.TokenHandle;

import security.OBD2Security;

@Controller
@RequestMapping(value = "/neobd/drive")
public class NeObdTravelController {

	private static final Logger logger = LoggerFactory.getLogger(NeObdTravelController.class);

	@Autowired
	private DriveInfoServer driveInfoServer;
	@Autowired
	private CustomUnits customUnits;
	/**
	 * 获取最新的一条行程
	 * 
	 * @param request
	 * @param respones
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/getNewestTravel", method = RequestMethod.POST)
	public @ResponseBody Object getNewestTravel(HttpServletRequest request, HttpServletResponse respones) throws UnsupportedEncodingException {
		respones.setHeader("Access-Control-Allow-Origin", "*");
		respones.setHeader("Content-Type", "application/x-protobuf");
		respones.setHeader("Accept", "application/x-protobuf");
		respones.setCharacterEncoding("utf-8");

		OutputGetNowestTravel.Builder rInfo = OutputGetNowestTravel.newBuilder().setSevTime(ProBufUnits.getServerTime());

		try {
			byte[] b = InputStreamUtil.getByte(request.getInputStream());
			InputGetNowestTravel info = InputGetNowestTravel.parseFrom(b);
			int checkToken = TokenHandle.checkToken(request.getHeader("token"));
			String guid = TokenHandle.getMguid(request.getHeader("mcak"));// 获取guid
			logger.info("获取用户行程每月分布情况:" + guid + "," + info.getUserId() + ", " + info.getCarId() + ", " + info.getSn());

			if (checkToken == 200) {
				if (customUnits.checkRequestValue(new Object[] { info.getUserId(), info.getCarId() })) {
					int code = 200;
					String msg = "查询最新一条行程成功";

					TravelDataTo td = driveInfoServer.findTravelData(info);

					rInfo.setCode(code).setMsg(msg);

					if (td.get_travel_id() != null) {
						NowTravelDesc ktd = NowTravelDesc.newBuilder().setTravelId(td.get_travel_id()).setStartTime(td.get_start_time() + "").setEndTime(td.get_end_time() + "").setTravelTime(td.get_travel_time() + "").setStartPoint(td.get_start_point()).setEndPoint(td.get_end_point()).setThisTravelLen(td.get_this_travel_len()).setThisAvgOil(td.get_this_avg_oil())
								.setCarmodelAvgOil(td.get_carmodel_avg_oil()).setThisOilWare(td.get_this_oil_ware()).setHistoryOilWare(td.get_sum_avg_oil()).setAvgSpeed(td.get_avg_speed()).setMaxSpeed(td.get_max_speed()).setSpeedInfo(td.get_speed_info()).setThisFastupCount(td.get_this_fastup_count()).setThisFastlowCount(td.get_this_fastlow_count()).setThisCrookCount(td.get_this_crook_count())
								.setTrackInfo(td.get_track_info()).setTravelScore(td.get_travel_score()).setGasConsumDes(td.get_gas_consum_des()).setDriveDes(td.get_speed_des() + "," + td.get_oil_ware() + "," + td.get_drive_des()).setTravelCost(td.get_travel_cost()).build();
						rInfo.setNowtravelDesc(ktd);
					} else {
						rInfo.setCode(207).setMsg("没有行程数据");
					}
				} else {
					rInfo.setCode(1402).setMsg("请求参数不能为空");
				}
			} else {
				rInfo.setCode(1401).setMsg("用户验证失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			rInfo.setCode(506).setMsg("服务器内部错误");
		}
		logger.info("接口正常返回" + rInfo.getSevTime() + "\t" + rInfo.getCode() + "\t" + rInfo.getMsg());
		return OBD2Security.OBDEncode(rInfo.build().toByteArray());
	}

	/**
	 * 获取指定日期的行程列表
	 * 
	 * @param request
	 * @param respones
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/getMonthTrip", method = RequestMethod.POST)
	public @ResponseBody Object getMonthTrip(HttpServletRequest request, HttpServletResponse respones) throws UnsupportedEncodingException {
		respones.setHeader("Access-Control-Allow-Origin", "*");
		respones.setHeader("Content-Type", "application/x-protobuf");
		respones.setHeader("Accept", "application/x-protobuf");
		respones.setCharacterEncoding("utf-8");

		OutputGetMonthTravel.Builder rInfo = OutputGetMonthTravel.newBuilder().setSevTime(ProBufUnits.getServerTime());
		try {
			byte[] b = InputStreamUtil.getByte(request.getInputStream());
			InputGetMonthTravel info = InputGetMonthTravel.parseFrom(b);
			int checkToken = TokenHandle.checkToken(request.getHeader("token"));
			String guid = TokenHandle.getMguid(request.getHeader("mcak"));// 获取guid
			logger.info("获取用户行程每月分布情况:" + guid + "," + info.getUserId() + ", " + info.getCarId() + ", " + info.getMonth());

			if (checkToken == 200) {
				if (customUnits.checkRequestValue(new Object[] { info.getUserId(), info.getCarId(), info.getMonth() })) {
					List<DriveMonthTo> dmt = driveInfoServer.findDriveFromMonth(info);
					int code = 200;
					String msg = "获取用户行程每月分布成功";
					if (dmt.size() > 0) {
						rInfo.setCode(code).setMsg(msg).setYearMonth(info.getMonth());
						for (int i = 0; i < dmt.size(); i++) {
							DayArray da = DayArray.newBuilder().setTravelDay(dmt.get(i).get_travel_day()).setTravelNumber(dmt.get(i).get_travel_number()).build();
							rInfo.addDayArray(da);
						}
					} else {
						rInfo.setCode(207).setMsg("本月没有行程记录").setYearMonth(info.getMonth());
					}
				} else {
					rInfo.setCode(1402).setMsg("请求参数不能为空").setYearMonth(info.getMonth());
				}
			} else {
				rInfo.setCode(1401).setMsg("用户验证失败").setYearMonth(info.getMonth());
			}
		} catch (Exception e) {
			e.printStackTrace();
			rInfo.setCode(506).setMsg("服务器内部错误").setYearMonth("");
		}
		logger.info("接口正常返回" + rInfo.getSevTime() + "\t" + rInfo.getCode() + "\t" + rInfo.getMsg());
		return OBD2Security.OBDEncode(rInfo.build().toByteArray());
	}

	/**
	 * 获取指定年月日的行程列表
	 * 
	 * @param request
	 * @param respones
	 * @return
	 * @throws UnsupportedEncodingException
	 */

	@RequestMapping(value = "/getDayTravel", method = RequestMethod.POST)
	public @ResponseBody Object getDayTravelList(HttpServletRequest request, HttpServletResponse respones) throws UnsupportedEncodingException {
		respones.setHeader("Access-Control-Allow-Origin", "*");
		respones.setHeader("Content-Type", "application/x-protobuf");
		respones.setHeader("Accept", "application/x-protobuf");
		respones.setCharacterEncoding("utf-8");

		OutputGetDayTravel.Builder rInfo = OutputGetDayTravel.newBuilder().setSevTime(ProBufUnits.getServerTime()).setTravelDay("");
		try {
			logger.info("获取指定年月日的行程列表:+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			byte[] b = InputStreamUtil.getByte(request.getInputStream());
			InputGetDayTravel info = InputGetDayTravel.parseFrom(b);
			int checkToken = TokenHandle.checkToken(request.getHeader("token"));
			String guid = TokenHandle.getMguid(request.getHeader("mcak"));// 获取guid
			logger.info("获取指定年月日的行程列表:" + guid + "," + info.getUserId() + ", " + info.getCarId() + ", " + info.getTravelDay() + ", " + request.getHeader("token"));

			if (checkToken == 200) {
				if (customUnits.checkRequestValue(new Object[] { info.getUserId(), info.getCarId(), info.getTravelDay() })) {
					List<DayTravelTo> dt_list = driveInfoServer.findDayTravel(info);
					int code = 200;
					String msg = "获取" + info.getTravelDay() + "的行程成功";
					if (dt_list.size() > 0) {
						for (int i = 0; i < dt_list.size(); i++) {
							DayTravelTo dt = dt_list.get(i);
							TravelList tl = TravelList.newBuilder().setTravelId(dt.get_id()).setStartPoint(dt.get_start_point()).setStartTime(dt.get_start_time() + "").setEndPoint(dt.get_end_point()).setEndTime(dt.get_end_time() + "").build();
							rInfo.addTravelList(tl);
						}
					} else {
						code = 207;
						msg = info.getTravelDay() + "没有行程";
					}
					rInfo.setCode(code).setMsg(msg).setTravelDay(info.getTravelDay());
				} else {
					rInfo.setCode(1402).setMsg("请求参数不能为空").setTravelDay(info.getTravelDay());
				}
			} else {
				rInfo.setCode(1401).setMsg("用户验证失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			rInfo.setCode(506).setMsg("服务器内部错误");
		}
		logger.info("接口正常返回" + rInfo.getSevTime() + "\t" + rInfo.getCode() + "\t" + rInfo.getMsg());
		return OBD2Security.OBDEncode(rInfo.build().toByteArray());
	}

	@RequestMapping(value = "/getTravelFromId", method = RequestMethod.POST)
	public @ResponseBody Object getTravelFromId(HttpServletRequest request, HttpServletResponse respones) throws UnsupportedEncodingException {
		respones.setHeader("Access-Control-Allow-Origin", "*");
		respones.setHeader("Content-Type", "application/x-protobuf");
		respones.setHeader("Accept", "application/x-protobuf");
		respones.setCharacterEncoding("utf-8");

		OutputGetTravelDesc.Builder rInfo = OutputGetTravelDesc.newBuilder().setSevTime(ProBufUnits.getServerTime());

		try {
			byte[] b = InputStreamUtil.getByte(request.getInputStream());
			InputGetTravelDesc info = InputGetTravelDesc.parseFrom(b);
			int checkToken = TokenHandle.checkToken(request.getHeader("token"));
			String guid = TokenHandle.getMguid(request.getHeader("mcak"));// 获取guid
			logger.info("查询指定ID的行程:" + guid + "," + info.getTravelId());

			if (checkToken == 200) {
				if (customUnits.checkRequestValue(new Object[] { info.getTravelId() })) {
					int code = 200;
					String msg = "查询指定ID的行程成功";

					TravelDataTo td = driveInfoServer.findTravelDataFromId(info);

					rInfo.setCode(code).setMsg(msg);

					if (td.get_travel_id() != null) {
						TravelDesc ktd = TravelDesc.newBuilder().setTravelId(td.get_travel_id()).setStartTime(td.get_start_time() + "").setEndTime(td.get_end_time() + "").setTravelTime(td.get_travel_time() + "").setStartPoint(td.get_start_point()).setEndPoint(td.get_end_point()).setThisTravelLen(td.get_this_travel_len()).setThisAvgOil(td.get_this_avg_oil())
								.setCarmodelAvgOil(td.get_carmodel_avg_oil()).setThisOilWare(td.get_this_oil_ware()).setHistoryOilWare(td.get_sum_avg_oil()).setAvgSpeed(td.get_avg_speed()).setMaxSpeed(td.get_max_speed()).setSpeedInfo(td.get_speed_info()).setThisFastupCount(td.get_this_fastup_count()).setThisFastlowCount(td.get_this_fastlow_count()).setThisCrookCount(td.get_this_crook_count())
								.setTrackInfo(td.get_track_info()).setTravelScore(td.get_travel_score()).setGasConsumDes(td.get_gas_consum_des()).setDriveDes(td.get_speed_des() + "," + td.get_oil_ware() + "," + td.get_drive_des()).setTravelCost(td.get_travel_cost()).build();
						rInfo.setTravelDesc(ktd);
					} else {
						rInfo.setCode(207).setMsg("没有行程数据");
					}
				} else {
					rInfo.setCode(1402).setMsg("请求参数不能为空");
				}
			} else {
				rInfo.setCode(1401).setMsg("用户验证失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			rInfo.setCode(506).setMsg("服务器内部错误");
		}
		logger.info("接口正常返回" + rInfo.getSevTime() + "\t" + rInfo.getCode() + "\t" + rInfo.getMsg());
		return OBD2Security.OBDEncode(rInfo.build().toByteArray());
	}

}
