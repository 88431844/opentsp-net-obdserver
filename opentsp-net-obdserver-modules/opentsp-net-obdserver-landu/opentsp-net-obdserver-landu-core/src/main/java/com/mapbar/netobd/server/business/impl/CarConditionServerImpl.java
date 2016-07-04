package com.mapbar.netobd.server.business.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mapbar.netobd.bean.dto.CarConditionInfoTo;
import com.mapbar.netobd.bean.dto.NeObdUserCarInfoTo;
import com.mapbar.netobd.dao.CarConditionDao;
import com.mapbar.netobd.dao.UserDao;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.InputGetConditionInfo;
import com.mapbar.netobd.server.business.CarConditionServer;
import com.mapbar.netobd.units.CustomUnits;

import net.sf.json.JSONObject;

/**
 * @description: 车况信息服务层
 * @author xubh
 * @date: 2016年4月29日
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class CarConditionServerImpl implements CarConditionServer {

	@Value("${redis.name.v:neobd_car_condition_v}")
	private String redisName_v;
	@Value("${redis.name.p:neobd_car_condition_p}")
	private String redisName_p;
	@Value("${gecodeing:http://wireless.mapbar.com/reverse/getInverseGeocoding.json?detail=1&zoom=12&inGb=02&outGb=02&}")
	private String gecodeing;

	@Autowired
	private UserDao userDao;

	@Autowired
	private CarConditionDao carConditionDao;

	@Autowired
	private RedisTemplate<String, Map<String, String>> redisTemplate;
	@Autowired
	private CustomUnits customUnits;

	@Override
	public CarConditionInfoTo findCarConditionInfo(InputGetConditionInfo info) throws Exception {
		// 获取当前用户的sn
		NeObdUserCarInfoTo nocit = userDao.findUserInfoFromUserId(info.getUserId());
		if (nocit.get_sn() != null && !nocit.get_sn().equals("")) {
			// 查询当前位置及电压
			CarConditionInfoTo ccit = new CarConditionInfoTo();
			// String v_json = RedisDBUtil.getHashValue(redisName_v, nocit.get_sn());// 获取电压
			String v_json = (String) redisTemplate.opsForHash().get(redisName_v, nocit.get_sn());
			if (v_json != null && !v_json.equals("")) {
				ccit.setVoltager(JSONObject.fromObject(v_json).getDouble("voltage"));
			} else {
				ccit.setVoltager(0);
			}

			// String p_json = RedisDBUtil.getHashValue(redisName_p, nocit.get_sn());// 获取点信息
			String p_json = (String) redisTemplate.opsForHash().get(redisName_p, nocit.get_sn());
			if (p_json != null && !p_json.equals("")) {
				JSONObject json = JSONObject.fromObject(p_json);

				// 84转02
				ccit.setX(json.getDouble("x"));
				ccit.setY(json.getDouble("y"));

				// 调用逆地理
				String url = gecodeing + "lon=" + ccit.getX() + "&lat=" + ccit.getY();
				ccit.setAddress(customUnits.getAddressInfo(url));

				// 判断是否在安全区域内
				ccit.setRange(customUnits.getElectricFenceJudge(ccit.getX(), ccit.getY(), nocit.get_user_id()));
			} else {
				ccit.setX(0);
				ccit.setY(0);
				ccit.setAddress("");
				ccit.setRange(0);
			}

			// 查询碰撞次数
			CarConditionInfoTo ccit2 = carConditionDao.findCarConditionInfo(nocit.get_sn());
			ccit.setCollision(ccit2.getCollision());
			return ccit;
		}
		return null;
	}
}
