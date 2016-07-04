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

import com.mapbar.netobd.bean.dto.OilDataTo;
import com.mapbar.netobd.protocolBuffer.ProBufUnits;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.InputGetOilPrice;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.OutputGetAreaList;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.OutputGetAreaList.AreaList;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.OutputGetOilPrice;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.OutputGetOilPrice.OilPrice;
import com.mapbar.netobd.server.base.BaseDateServer;
import com.mapbar.netobd.units.CustomUnits;
import com.mapbar.netobd.units.InputStreamUtil;
import com.mapbar.netobd.units.TokenHandle;

import security.OBD2Security;

@Controller
@RequestMapping(value = "/neobd/baseInfo")
public class NeObdBaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(NeObdBaseController.class);
	
	@Autowired
	private BaseDateServer basesDateServer;
	
	@Autowired
	private CustomUnits customUnits;
	
	
	/**
	 * 获取油价相关城市列表
	 * @param request
	 * @param respones
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/getArealist", method= RequestMethod.POST)
	public @ResponseBody Object queryList(HttpServletRequest request, HttpServletResponse respones) throws UnsupportedEncodingException {
		respones.setHeader("Access-Control-Allow-Origin", "*");
		respones.setHeader("Content-Type", "application/x-protobuf");
		respones.setHeader("Accept", "application/x-protobuf");
		respones.setCharacterEncoding("utf-8");
		
		OutputGetAreaList.Builder rInfo = OutputGetAreaList.newBuilder().setSevTime(ProBufUnits.getServerTime());
		logger.info("获取油价城市列表:" + request.getHeader("token"));
		
		int checkToken = TokenHandle.checkToken(request.getHeader("token"));
		
		if (checkToken == 200) {
			List<OilDataTo> areaTo = null;
			try {
				areaTo = basesDateServer.findOilAreaList();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			int code = 200;
			String msg = "获取油价城市列表成功";
			
			rInfo.setCode(code).setMsg(msg);
			
			for (int i = 0; i < areaTo.size(); i++) {
				AreaList value = AreaList.newBuilder().setArea(areaTo.get(i).get_area()).setFirst(areaTo.get(i).get_first_initials()).build(); 
				rInfo.addAreaList(value);
			}
		}else{
			rInfo.setCode(1401).setMsg("获取油价列表失败");
		}
		logger.info("接口正常返回" + rInfo.getSevTime() + "\t" + rInfo.getCode() + "\t" + rInfo.getMsg());
		return OBD2Security.OBDEncode(rInfo.build().toByteArray());
	}
	
	/**
	 * 通过城市获取油价
	 * @param requestEntity
	 * @param request
	 * @param respones
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/getOilFromArea", method= RequestMethod.POST)
	public @ResponseBody Object queryOilFromArea(HttpServletRequest request, HttpServletResponse respones) throws UnsupportedEncodingException {
		respones.setHeader("Access-Control-Allow-Origin", "*");
		respones.setHeader("Content-Type", "application/x-protobuf");
		respones.setHeader("Accept", "application/x-protobuf");
		respones.setCharacterEncoding("utf-8");

		OutputGetOilPrice.Builder rInfo = OutputGetOilPrice.newBuilder().setSevTime(ProBufUnits.getServerTime());
		
		try {
			byte[] b = InputStreamUtil.getByte(request.getInputStream());//解密
			InputGetOilPrice info = InputGetOilPrice.parseFrom(b);//获取请求参数
			int checkToken = TokenHandle.checkToken(request.getHeader("token"));
			
			logger.info("获取指定城市油价:" + info.getCityName());

			if(checkToken == 200){
				if (customUnits.checkRequestValue(new Object[] { info.getCityName() })) {// 验证请求参数不能为空
					
					List<OilDataTo> areaTo = basesDateServer.findOilFromArea(info);
					
					int code = 200;
					String msg = "获取" + info.getCityName() + "城市的油价成功";
					
					rInfo.setCode(code).setMsg(msg);
					
					for (int i = 0; i < areaTo.size(); i++) {
						OilPrice value = OilPrice.newBuilder().setId(areaTo.get(i).get_id()).setOilType(areaTo.get(i).get_oilType()).setPrice(areaTo.get(i).get_price()).build();
						rInfo.addOilPrice(value);
					}
				}else{
					rInfo.setCode(1402).setMsg("请求参数不能为空");
				}
			}else{
				rInfo.setCode(1401).setMsg("验证token失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			rInfo.setCode(506).setMsg("服务器内部错误").build();
		}
		logger.info("接口正常返回" + rInfo.getSevTime() + "\t" + rInfo.getCode() + "\t" + rInfo.getMsg());
		return OBD2Security.OBDEncode(rInfo.build().toByteArray());
	}

}
