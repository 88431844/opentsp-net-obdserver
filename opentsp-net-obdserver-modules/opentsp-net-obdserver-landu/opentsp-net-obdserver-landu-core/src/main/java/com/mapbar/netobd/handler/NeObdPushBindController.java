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

import com.mapbar.netobd.bean.dto.NeObdPushBindTo;
import com.mapbar.netobd.protocolBuffer.ProBufUnits;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.InputGetPushRemind;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.InputSetPushBind;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.InputSetPushRemind;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.OutputGetPushRemind;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.OutputGetPushRemind.PushRemind;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.OutputSetPushBind;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.OutputSetPushRemind;
import com.mapbar.netobd.server.business.NeObdUserServer;
import com.mapbar.netobd.units.CustomUnits;
import com.mapbar.netobd.units.InputStreamUtil;
import com.mapbar.netobd.units.TokenHandle;

import security.OBD2Security;

@Controller
@RequestMapping(value = "/neobd/push")
public class NeObdPushBindController {

	private static final Logger logger = LoggerFactory.getLogger(NeObdPushBindController.class);

	@Autowired
	private NeObdUserServer neObdUserServer;
	@Autowired
	private CustomUnits customUnits;
	/**
	 * 用户推送绑定
	 * 
	 * @param requestEntity
	 * @param request
	 * @param respones
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/setPushBind", method = RequestMethod.POST)
	public @ResponseBody Object setPushBind(HttpServletRequest request, HttpServletResponse respones) throws UnsupportedEncodingException {
		respones.setHeader("Access-Control-Allow-Origin", "*");
		respones.setHeader("Content-Type", "application/x-protobuf");
		respones.setHeader("Accept", "application/x-protobuf");
		respones.setCharacterEncoding("utf-8");

		OutputSetPushBind.Builder rInfo = OutputSetPushBind.newBuilder().setSevTime(ProBufUnits.getServerTime());
		try {
			byte[] b = InputStreamUtil.getByte(request.getInputStream());// 解密
			InputSetPushBind info = InputSetPushBind.parseFrom(b);// 获取请求参数
			int checkToken = TokenHandle.checkToken(request.getHeader("token"));
			String guid = TokenHandle.getMguid(request.getHeader("mck"));// 获取guid
			logger.info("设置推送绑定:" + guid + "," + info.getUserId() + ", " + info.getCarId() + ", " + info.getPushToken() + ", " + info.getProduct());

			if (checkToken == 200) {
				if (customUnits.checkRequestValue(new Object[] { info.getUserId(), info.getCarId(), info.getProduct(), info.getPushToken() })) {

					neObdUserServer.setUserPushBind(info, guid);
					int code = 200;
					String msg = "设置推送绑定完成";

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
	 * 获取用户推送绑定
	 * 
	 * @param requestEntity
	 * @param request
	 * @param respones
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/getPushRemind", method = RequestMethod.POST)
	public @ResponseBody Object getPushRemind(HttpServletRequest request, HttpServletResponse respones) throws UnsupportedEncodingException {
		respones.setHeader("Access-Control-Allow-Origin", "*");
		respones.setHeader("Content-Type", "application/x-protobuf");
		respones.setHeader("Accept", "application/x-protobuf");
		respones.setCharacterEncoding("utf-8");

		OutputGetPushRemind.Builder rInfo = OutputGetPushRemind.newBuilder().setSevTime(ProBufUnits.getServerTime());
		try {
			byte[] b = InputStreamUtil.getByte(request.getInputStream());// 解密
			InputGetPushRemind info = InputGetPushRemind.parseFrom(b);// 获取请求参数
			int checkToken = TokenHandle.checkToken(request.getHeader("token"));
			String guid = TokenHandle.getMguid(request.getHeader("mck"));// 获取guid
			logger.info("获取推送绑定设置:" + guid + "," + info.getUserId() + ", " + info.getCarId());

			if (checkToken == 200) {
				if (customUnits.checkRequestValue(new Object[] { info.getUserId(), info.getCarId() })) {

					NeObdPushBindTo nopb = neObdUserServer.findUserPushRemind(info);

					int code = 200;
					String msg = "获取推送设置成功";

					if (nopb.get_user_id() != null) {
						rInfo.setCode(code).setMsg(msg);
						rInfo.setPushRemind(PushRemind.newBuilder().setUserId(nopb.get_user_id()).setCarId(nopb.get_car_id()).setId(nopb.get_id()).setIgnitionPush(nopb.get_ignition_push()).setOfflinePush(nopb.get_offline_push()).setVoltagesPush(nopb.get_voltages_push()).setSetVoltages(nopb.get_set_voltages()).setShockPush(nopb.get_shock_push()));
					} else {
						rInfo.setCode(207).setMsg("没有推送绑定关系");
					}
				} else {
					rInfo.setCode(1402).setMsg("请求参数不能为空");
				}
			} else {
				rInfo.setCode(1401).setMsg("用户验证失败").build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			rInfo.setCode(506).setMsg("服务器内部错误").build();
		}
		logger.info("接口正常返回" + rInfo.getSevTime() + "\t" + rInfo.getCode() + "\t" + rInfo.getMsg());
		return OBD2Security.OBDEncode(rInfo.build().toByteArray());
	}

	/**
	 * 设置推送开关
	 * 
	 * @param requestEntity
	 * @param request
	 * @param respones
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/setPushRemind", method = RequestMethod.POST)
	public @ResponseBody Object setPushRemind(HttpServletRequest request, HttpServletResponse respones) throws UnsupportedEncodingException {
		respones.setHeader("Access-Control-Allow-Origin", "*");
		respones.setHeader("Content-Type", "application/x-protobuf");
		respones.setHeader("Accept", "application/x-protobuf");
		respones.setCharacterEncoding("utf-8");

		OutputSetPushRemind.Builder rInfo = OutputSetPushRemind.newBuilder().setSevTime(ProBufUnits.getServerTime());
		try {
			byte[] b = InputStreamUtil.getByte(request.getInputStream());// 解密
			InputSetPushRemind info = InputSetPushRemind.parseFrom(b);// 获取请求参数
			int checkToken = TokenHandle.checkToken(request.getHeader("token"));
			String guid = TokenHandle.getMguid(request.getHeader("mck"));// 获取guid
			logger.info("获取推送绑定设置:" + guid + "," + info.getUserId() + ", " + info.getCarId());

			if (checkToken == 200) {
				if (customUnits.checkRequestValue(new Object[] { info.getUserId(), info.getCarId() })) {

					int code = 200;
					String msg = "设置推送开关完成";

					Integer x = neObdUserServer.setPushOnOff(info);
					if (x > 0) {
						rInfo.setCode(code).setMsg(msg);
					} else {
						rInfo.setCode(207).setMsg("没有对应的数据");
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
