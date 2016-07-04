package com.mapbar.netobd.handler;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mapbar.netobd.bean.dto.NeObdUserCarInfoTo;
import com.mapbar.netobd.protocolBuffer.ProBufUnits;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.InputGetVehicleFile;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.InputRemoveSnBinding;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.InputSetBindDrive;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.InputSetCarInfo;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.InputSetVehicleFile;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.OutputGetVehicleFile;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.OutputGetVehicleFile.VehicleFile;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.OutputRemoveSnBinding;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.OutputSetBindDrive;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.OutputSetCarInfo;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.OutputSetVehicleFile;
import com.mapbar.netobd.server.business.NeObdUserServer;
import com.mapbar.netobd.units.CustomUnits;
import com.mapbar.netobd.units.InputStreamUtil;
import com.mapbar.netobd.units.TokenHandle;

import security.OBD2Security;

/**
 * 用户相关 用户车型档案设置、获取 用户设备绑定设置、获取
 * 
 * @author yulong
 *
 */

@Controller
@RequestMapping(value = "/neobd/userInfo")
public class NeObdUserController {

	private static final Logger logger = LoggerFactory.getLogger(NeObdUserController.class);

	@Autowired
	private NeObdUserServer neObdUserServer;
	@Autowired
	private CustomUnits customUnits;
	/**
	 * 获取用户车辆档案
	 * 
	 * @param requestEntity
	 * @param request
	 * @param respones
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/getVehicleFile", method = RequestMethod.POST)
	public @ResponseBody Object queryCarDom(HttpServletRequest request, HttpServletResponse respones) throws UnsupportedEncodingException {
		respones.setHeader("Access-Control-Allow-Origin", "*");
		respones.setHeader("Content-Type", "application/x-protobuf");
		respones.setHeader("Accept", "application/x-protobuf");
		respones.setCharacterEncoding("utf-8");

		OutputGetVehicleFile.Builder rInfo = OutputGetVehicleFile.newBuilder().setSevTime(ProBufUnits.getServerTime());

		try {
			byte[] b = InputStreamUtil.getByte(request.getInputStream());// 解密
			InputGetVehicleFile vehicel_file = InputGetVehicleFile.parseFrom(b);// 获取请求参数
			int checkToken = TokenHandle.checkToken(request.getHeader("token"));
			String guid = TokenHandle.getMguid(request.getHeader("mck"));// 获取guid
			logger.info("获取用户车辆档案:" + guid + "," + vehicel_file.getUserId());

			if (checkToken == 200) {
				if (customUnits.checkRequestValue(new Object[] { vehicel_file.getUserId() })) {
					NeObdUserCarInfoTo tnoci = neObdUserServer.findUserInfoFromUserId(vehicel_file);

					int code = 200;
					String msg = "获取用户车辆档案";

					VehicleFile.Builder value = VehicleFile.newBuilder();
					if (tnoci == null) {
						// 20160218 sdk要求，在没有车辆数据的时候，返回的数据也有字段，并且字段为空
						code = 212;
						msg = "没有车辆档案";
						value.setBindTime("").setCarCode("").setCarId("").setCarModelId("").setEngineNumber("").setIsCustomOil("").setLastUserTime("").setPhone("").setSetOilArea("").setSetOilPrice(0).setSetOilType(0).setSimState(0).setSn("").setVinCode("").setObdState(0).build();
					} else {
						value.setBindTime(tnoci.get_bind_time() + "").setCarCode(tnoci.get_car_code()).setCarId(tnoci.get_id()).setCarModelId(tnoci.get_car_model_id()).setEngineNumber(tnoci.get_engine_number()).setIsCustomOil(tnoci.get_is_custom_oil() + "").setLastUserTime(tnoci.get_last_user_time() + "").setPhone(tnoci.get_phone()).setSetOilArea(tnoci.get_set_oil_area())
								.setSetOilPrice(tnoci.get_set_oil_price()).setSetOilType(tnoci.get_set_oil_type()).setSimState(tnoci.get_sim_state()).setSn(tnoci.get_sn()).setVinCode(tnoci.get_vin_code()).setObdState(tnoci.get_obd_state()).build();
					}
					rInfo.setVehicleFile(value);
					rInfo.setCode(code).setMsg(msg);
				} else {
					rInfo.setCode(1402).setMsg("请求参数不能为空");
				}
			} else {
				rInfo.setCode(1401).setMsg("验证token失败").build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			rInfo.setCode(506).setMsg("服务器内部错误").build();
		}
		logger.info("接口正常返回" + rInfo.getSevTime() + "\t" + rInfo.getCode() + "\t" + rInfo.getMsg());
		return OBD2Security.OBDEncode(rInfo.build().toByteArray());
	}

	/**
	 * 注册时调用，设置车辆信息，生成用户车型数据
	 * 
	 * @param requestEntity
	 * @param request
	 * @param respones
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/setCarInfo", method = RequestMethod.POST)
	public @ResponseBody Object setCarInfo(HttpServletRequest request, HttpServletResponse respones) throws UnsupportedEncodingException {
		respones.setHeader("Access-Control-Allow-Origin", "*");
		respones.setHeader("Content-Type", "application/x-protobuf");
		respones.setHeader("Accept", "application/x-protobuf");
		respones.setCharacterEncoding("utf-8");

		OutputSetCarInfo.Builder rInfo = OutputSetCarInfo.newBuilder().setSevTime(ProBufUnits.getServerTime());

		try {
			byte[] b = InputStreamUtil.getByte(request.getInputStream());// 解密
			InputSetCarInfo info = InputSetCarInfo.parseFrom(b);// 获取请求参数
			int checkToken = TokenHandle.checkToken(request.getHeader("token"));
			String guid = TokenHandle.getMguid(request.getHeader("mck"));// 获取guid
			logger.info("添加车辆信息:" + guid + "," + info.getUserId() + "," + info.getVin() + "," + info.getCarCode() + "," + info.getCarModelId() + ", " + info.getProduct());

			if (checkToken == 200) {
				NeObdUserCarInfoTo tnoci = neObdUserServer.saveUserInfo(info, guid);
				int code = 200;
				String msg = "添加车型完成";

				// 判断VIN是否被绑定
				if (tnoci == null) {
					code = 208;
					msg = "VIN已经被绑定,请联系客服！";
					rInfo.setCode(code).setMsg(msg);
				} else {
					rInfo.setCode(code).setMsg(msg).setCarId(tnoci.get_id());
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
	 * 设置用户车辆档案
	 * 
	 * @param requestEntity
	 * @param request
	 * @param respones
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/setVehicleFile", method = RequestMethod.POST)
	public @ResponseBody Object setUserVehicleFile(HttpServletRequest request, HttpServletResponse respones) throws UnsupportedEncodingException {
		respones.setHeader("Access-Control-Allow-Origin", "*");
		respones.setHeader("Content-Type", "application/x-protobuf");
		respones.setHeader("Accept", "application/x-protobuf");
		respones.setCharacterEncoding("utf-8");

		OutputSetVehicleFile.Builder rInfo = OutputSetVehicleFile.newBuilder().setSevTime(ProBufUnits.getServerTime()).setCarId("");

		try {
			byte[] b = InputStreamUtil.getByte(request.getInputStream());// 解密
			InputSetVehicleFile info = InputSetVehicleFile.parseFrom(b);// 获取请求参数
			int checkToken = TokenHandle.checkToken(request.getHeader("token"));
			String guid = TokenHandle.getMguid(request.getHeader("mck"));// 获取guid
			logger.info("设置用户车辆档案:" + guid + "," + info.getUserId());

			if (checkToken == 200) {
				if (customUnits.checkRequestValue(new Object[] { info.getUserId(), info.getCarCode(), info.getCarModelId(), info.getVin() })) {
					String tnoci = neObdUserServer.setUserVehicleFile(info, guid);

					int code = 200;
					String msg = "设置用户车辆档案完成";

					if (tnoci != null) {
						if (tnoci.equals("2")) {
							code = 208;
							msg = "VIN被绑定，请与客服联系";
						} else {
							rInfo.setCarId(tnoci);
						}
					}

					rInfo.setCode(code).setMsg(msg);
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
	 * 设置用户绑定设备
	 * 
	 * @param requestEntity
	 * @param request
	 * @param respones
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/setUserBindDrive", method = RequestMethod.POST)
	public @ResponseBody Object setBindDrive(HttpServletRequest request, HttpServletResponse respones) throws UnsupportedEncodingException {
		respones.setHeader("Access-Control-Allow-Origin", "*");
		respones.setHeader("Content-Type", "application/x-protobuf");
		respones.setHeader("Accept", "application/x-protobuf");
		respones.setCharacterEncoding("utf-8");

		OutputSetBindDrive.Builder rInfo = OutputSetBindDrive.newBuilder().setSevTime(ProBufUnits.getServerTime());

		try {

			byte[] b = InputStreamUtil.getByte(request.getInputStream());// 解密
			InputSetBindDrive info = InputSetBindDrive.parseFrom(b);// 获取请求参数
			int checkToken = TokenHandle.checkToken(request.getHeader("token"));
			String guid = TokenHandle.getMguid(request.getHeader("mck"));// 获取guid
			logger.info("设置用户绑定设备:" + guid + "," + info.getUserId() + ", " + info.getCarId() + ", " + info.getSn());

			if (checkToken == 200) {
				if (customUnits.checkRequestValue(new Object[] { info.getUserId(), info.getCarId(), info.getSn() })) {
					Integer res = neObdUserServer.setUserBindDrive(info, guid);
					int code = 200;
					String msg = "设备绑定成功";
					switch (res) {
					case 0:
						code = 212;
						msg = "用户车辆不存在";
						break;
					case 2:
						code = 210;
						msg = "SN已被绑定,或用户已绑定其他SN";
						break;
					case 3:
						code = 209;
						msg = "SN不存在";
						break;
					}
					rInfo.setCode(code).setMsg(msg);
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
	 * 解绑SN
	 * 
	 * @param requestEntity
	 * @param request
	 * @param respones
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/removeSnBinding", method = RequestMethod.POST)
	public @ResponseBody Object removeSnBinding(HttpServletRequest request, HttpServletResponse respones) throws UnsupportedEncodingException {
		respones.setHeader("Access-Control-Allow-Origin", "*");
		respones.setHeader("Content-Type", "application/x-protobuf");
		respones.setHeader("Accept", "application/x-protobuf");
		respones.setCharacterEncoding("utf-8");

		OutputRemoveSnBinding.Builder rInfo = OutputRemoveSnBinding.newBuilder().setSevTime(ProBufUnits.getServerTime());

		try {

			byte[] b = InputStreamUtil.getByte(request.getInputStream());// 解密
			InputRemoveSnBinding info = InputRemoveSnBinding.parseFrom(b);// 获取请求参数
			int checkToken = TokenHandle.checkToken(request.getHeader("token"));
			String guid = TokenHandle.getMguid(request.getHeader("mck"));// 获取guid
			logger.info("设置用户绑定设备:" + guid + "," + info.getUserId() + ", " + info.getCarId() + ", " + info.getSn());

			if (checkToken == 200) {
				if (customUnits.checkRequestValue(new Object[] { info.getUserId(), info.getCarId(), info.getSn() })) {
					Integer res = neObdUserServer.RemoveSnBinding(info);
					int code = 200;
					String msg = "设备解绑成功";

					if (res < 1) {
						code = 211;
						msg = "SN没有被绑定";
					}
					rInfo.setCode(code).setMsg(msg);
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
