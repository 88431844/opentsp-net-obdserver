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

import com.mapbar.netobd.bean.dto.CarConditionInfoTo;
import com.mapbar.netobd.protocolBuffer.ProBufUnits;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.InputGetConditionInfo;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.OutputGetConditionInfo;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.OutputGetConditionInfo.ConditionInfo;
import com.mapbar.netobd.server.business.CarConditionServer;
import com.mapbar.netobd.units.CustomUnits;
import com.mapbar.netobd.units.InputStreamUtil;
import com.mapbar.netobd.units.TokenHandle;

import security.OBD2Security;

@Controller
@RequestMapping(value = "/neobd/CarCondition")
public class NeOdbCarConditionController {

	private static final Logger logger = LoggerFactory.getLogger(NeOdbCarConditionController.class);

	@Autowired
	private CarConditionServer carConditionServer;
	@Autowired
	private CustomUnits customUnits;
	/**
	 * 获取用户车况信息
	 * 
	 * @param request
	 * @param respones
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/getCarCondition", method = RequestMethod.POST)
	public @ResponseBody Object getCheckInfo(HttpServletRequest request, HttpServletResponse respones) throws UnsupportedEncodingException {
		respones.setHeader("Access-Control-Allow-Origin", "*");
		respones.setHeader("Content-Type", "application/x-protobuf");
		respones.setHeader("Accept", "application/x-protobuf");
		respones.setCharacterEncoding("utf-8");

		OutputGetConditionInfo.Builder rInfo = OutputGetConditionInfo.newBuilder().setSevTime(ProBufUnits.getServerTime());

		try {
			byte[] b = InputStreamUtil.getByte(request.getInputStream());
			InputGetConditionInfo info = InputGetConditionInfo.parseFrom(b);
			int checkToken = TokenHandle.checkToken(request.getHeader("token"));
			String guid = TokenHandle.getMguid(request.getHeader("mcak"));// 获取guid
			logger.info("获取用户车况数据:" + guid + "," + info.getUserId() + ", " + info.getCarId());

			if (checkToken == 200) {
				if (customUnits.checkRequestValue(new Object[] { info.getUserId(), info.getCarId() })) {
					int code = 200;
					String msg = "车况信息成功";
					CarConditionInfoTo carCondition = carConditionServer.findCarConditionInfo(info);
					rInfo.setCode(code).setMsg(msg);
					if (carCondition != null) {
						ConditionInfo.Builder condition = ConditionInfo.newBuilder().setAddress(carCondition.getAddress()).setCollision(carCondition.getCollision()).setX(carCondition.getX()).setY(carCondition.getY()).setVoltager(carCondition.getVoltager()).setRange(carCondition.getRange());
						rInfo.setConditionInfo(condition);
					} else {
						rInfo.setCode(207).setMsg("没有车况数据");
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
