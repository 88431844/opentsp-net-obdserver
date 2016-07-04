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

import com.mapbar.netobd.protocolBuffer.ProBufUnits;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.InputGetCheckInfo;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.OutputGetCheckInfo;
import com.mapbar.netobd.server.business.CheckInfoServer;
import com.mapbar.netobd.units.CustomUnits;
import com.mapbar.netobd.units.InputStreamUtil;
import com.mapbar.netobd.units.TokenHandle;

import net.sf.json.JSONObject;
import security.OBD2Security;

@Controller
@RequestMapping(value = "/neobd/check")
public class NeObdCheckController {

	private static final Logger logger = LoggerFactory.getLogger(NeObdCheckController.class);

	@Autowired
	private CheckInfoServer checkInfoServer;
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
	@RequestMapping(value = "/getCheckInfo", method = RequestMethod.POST)
	public @ResponseBody Object getCheckInfo(HttpServletRequest request, HttpServletResponse respones) throws UnsupportedEncodingException {
		respones.setHeader("Access-Control-Allow-Origin", "*");
		respones.setHeader("Content-Type", "application/x-protobuf");
		respones.setHeader("Accept", "application/x-protobuf");
		respones.setCharacterEncoding("utf-8");

		OutputGetCheckInfo.Builder rInfo = OutputGetCheckInfo.newBuilder().setSevTime(ProBufUnits.getServerTime());

		try {
			byte[] b = InputStreamUtil.getByte(request.getInputStream());
			InputGetCheckInfo info = InputGetCheckInfo.parseFrom(b);
			int checkToken = TokenHandle.checkToken(request.getHeader("token"));
			String guid = TokenHandle.getMguid(request.getHeader("mcak"));// 获取guid
			logger.info("获取用户体检数据:" + guid + "," + info.getUserId() + ", " + info.getCarId());

			if (checkToken == 200) {
				if (customUnits.checkRequestValue(new Object[] { info.getUserId(), info.getCarId() })) {

					int code = 200;
					String msg = "查询最新一条体检成功";

					JSONObject checkInfo = checkInfoServer.findCheckInfo(info);
					rInfo.setCode(code).setMsg(msg);

					if (checkInfo != null) {
						rInfo.setCheckInfo(checkInfo.toString()).setCheckTime(checkInfo.getString("checkTime"));
					} else {
						rInfo.setCode(207).setMsg("没有体检数据").setCheckTime("");
					}
				} else {
					rInfo.setCode(1402).setMsg("请求参数不能为空").setCheckTime("");
				}
			} else {
				rInfo.setCode(1401).setMsg("用户验证失败").setCheckTime("");
			}
		} catch (Exception e) {
			e.printStackTrace();
			rInfo.setCode(506).setMsg("服务器内部错误");
		}

		logger.info("接口正常返回" + rInfo.getSevTime() + "\t" + rInfo.getCode() + "\t" + rInfo.getMsg());
		return OBD2Security.OBDEncode(rInfo.build().toByteArray());
	}

}
