package com.mapbar.netobd.server.business.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mapbar.netobd.bean.dto.NeObdPushBindTo;
import com.mapbar.netobd.bean.dto.NeObdUserCarInfoTo;
import com.mapbar.netobd.bean.dto.UserCenterInfo;
import com.mapbar.netobd.dao.UserDao;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.InputGetPushRemind;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.InputGetVehicleFile;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.InputRemoveSnBinding;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.InputSetBindDrive;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.InputSetCarInfo;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.InputSetPushBind;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.InputSetPushRemind;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.InputSetVehicleFile;
import com.mapbar.netobd.server.business.NeObdUserServer;
import com.mapbar.netobd.units.CustomUnits;
import com.mapbar.netobd.units.SNUtils;
import com.mapbar.netobd.units.VinUtil;

/**
 * @description: 用户信息业务层
 * @author xubh
 * @date: 2016年4月29日
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class NeObdUserServerImpl implements NeObdUserServer {
	private static final Logger logger = LoggerFactory.getLogger(NeObdUserServerImpl.class);

	@Value("${redis.name.sn_userId:neobd_sn_userid}")
	private String sn_user_redis;

	@Value("${getphone:true}")
	private boolean isGetPhone;
	
	@Value("{sntrue:false}")
	private boolean isSnTrue;

	@Autowired
	private UserDao userDao;
	@Autowired
	private RedisTemplate<String, Map<String, String>> redisTemplate;
	@Autowired
	private CustomUnits customUnits;
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
	public NeObdUserCarInfoTo saveUserInfo(InputSetCarInfo info, String guid) throws Exception {

		// 根据VIN查询用户数据
		List<Map<String, Object>> user_list = userDao.findUserIdFromVin(info.getVin());

		if (VinUtil.checkVinTrue(user_list, info.getVin(), info.getUserId()) == 2) {
			return null;
		}

		NeObdUserCarInfoTo qnoucit = userDao.findUserInfoFromUserId(info.getUserId());
		if (qnoucit.get_user_id() != null && !qnoucit.get_user_id().equals("")) {
			return qnoucit;
		} else {
			NeObdUserCarInfoTo noucit = new NeObdUserCarInfoTo();
			noucit.set_user_id(info.getUserId());
			noucit.set_guid(guid);
			noucit.set_car_code(info.getCarCode());
			noucit.set_car_model_id(info.getCarModelId());
			noucit.set_id(customUnits.getCarId(info.getCarModelId(), info.getCarCode()));// car_id
			noucit.set_set_oil_type(customUnits.getOiltype(info.getCarModelId()));// 默认油价类型
			noucit.set_set_oil_area("北京");// 默认油价
			noucit.set_set_oil_price(customUnits.getOilPrice("北京", noucit.get_set_oil_type()));// 默认北京的油价
			noucit.set_is_custom_oil(0);
			noucit.set_is_delete(0);
			noucit.set_obd_state(1);
			noucit.set_vin_code(info.getVin());
			noucit.set_sim_state(1);
			UserCenterInfo user_info = customUnits.getUserInfo(info.getUserId());
			noucit.set_phone(user_info.getPhone());
			noucit.set_account(user_info.getPhone());
			long time = System.currentTimeMillis();
			noucit.set_create_time(time);
			noucit.set_modify_time(time);
			noucit.set_product(info.getProduct());

			int z = userDao.saveUserInfo(noucit);
			userDao.userPushBind2(noucit);
			if (z == 1) {
				logger.info("添加用户信息：" + noucit.get_id() + " " + noucit.get_user_id() + " " + noucit.get_phone());
				return userDao.findUserInfoFromId(noucit);
			}
		}
		return null;
	}

	@Override
	public NeObdUserCarInfoTo findUserInfoFromUserId(InputGetVehicleFile info) throws Exception {
		NeObdUserCarInfoTo noucit = userDao.findUserInfoFromUserId(info.getUserId());
		if (noucit.get_user_id() != null && !noucit.get_user_id().equals("")) {
			if (noucit.get_engine_number() == null) {
				noucit.set_engine_number("");
			}
			if (noucit.get_sn() == null) {
				noucit.set_sn("");
			}
			return noucit;
		}
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
	public String setUserVehicleFile(InputSetVehicleFile info, String guid) throws Exception {

		// 根据VIN查询用户数据
		List<Map<String, Object>> user_list = userDao.findUserIdFromVin(info.getVin());

		if (VinUtil.checkVinTrue(user_list, info.getVin(), info.getUserId()) == 2) {
			return "2";
		}

		// 用user_id查询最新的一条记录
		NeObdUserCarInfoTo query_user = userDao.findUserVehicleFile(info);
		if (query_user.get_account() != null) {
			// 更新数据的_is_delete为1
			userDao.copyUserVehicleFile(query_user);

			query_user.set_car_model_id(info.getCarModelId());
			query_user.set_car_code(info.getCarCode());
			query_user.set_vin_code(info.getVin());
			query_user.set_engine_number(info.getEngineNumber());
			query_user.set_is_custom_oil(info.getIsCustomeOil());
			query_user.set_set_oil_area(info.getOilArea());
			query_user.set_set_oil_type(info.getOilType());
			query_user.set_set_oil_price(info.getOilPrice());
			query_user.set_modify_time(System.currentTimeMillis());
			query_user.set_id(customUnits.getCarId(info.getCarModelId(), info.getCarCode()));
			query_user.set_guid(guid);
			query_user.set_product(info.getProduct());

			Integer x = userDao.saveUserVehicleFile(query_user);
			if (x == 1) {
				return query_user.get_id();
			} else {
				return null;
			}
		} else {
			// 2016-02-24,应sdk要求，废弃添加车辆信息接口
			NeObdUserCarInfoTo noucit = new NeObdUserCarInfoTo();
			noucit.set_engine_number(info.getEngineNumber());
			noucit.set_user_id(info.getUserId());
			noucit.set_guid(guid);
			noucit.set_car_code(info.getCarCode());
			noucit.set_car_model_id(info.getCarModelId());
			noucit.set_id(customUnits.getCarId(info.getCarModelId(), info.getCarCode()));// car_id
			noucit.set_set_oil_type(customUnits.getOiltype(info.getCarModelId()));// 默认油价类型
			noucit.set_set_oil_area("北京");// 默认油价
			noucit.set_set_oil_price(customUnits.getOilPrice("北京", noucit.get_set_oil_type()));// 默认北京的油价
			noucit.set_is_custom_oil(0);
			noucit.set_is_delete(0);
			noucit.set_obd_state(0);
			noucit.set_vin_code(info.getVin());
			noucit.set_sim_state(1);

			if (isGetPhone) {
				logger.info("获取用户的手机号等信息");
				UserCenterInfo user_info = customUnits.getUserInfo(info.getUserId());
				noucit.set_phone(user_info.getPhone());
				noucit.set_account(user_info.getPhone());
			} else {
				noucit.set_phone("");
				noucit.set_account("");
			}

			long time = System.currentTimeMillis();
			noucit.set_create_time(time);
			noucit.set_modify_time(time);
			noucit.set_product(info.getProduct());
			noucit.set_hd_type(0);

			userDao.userPushBind2(noucit);
			Integer x = userDao.saveUserVehicleFile(noucit);
			if (x == 1) {
				return noucit.get_id();
			} else {
				return null;
			}
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
	public Integer setUserBindDrive(InputSetBindDrive info, String guid) throws Exception {
		// 验证SN是否存在
		if (isSnTrue) {
			if (SNUtils.getSnInfo(info.getSn()).getInt("code") != 200) {
				return 3;
			}
		}

		// 验证SN是否被绑定
		Integer x = userDao.findUserInfoFromSn(info.getSn());
		if (x > 0) {
			return 2;
		}

		// 用user_id查询最新的一条记录
		NeObdUserCarInfoTo query_user = userDao.findUserBindDrive(info);
		if (query_user.get_account() != null) {
			if (query_user.get_sn() != null && query_user.get_sn().length() > 0) {
				return 2;
			}

			// 更新数据的_is_delete为1
			userDao.copyUserVehicleFile(query_user);

			query_user.set_sn(info.getSn());
			query_user.set_modify_time(System.currentTimeMillis());
			query_user.set_bind_time(System.currentTimeMillis());
			query_user.set_guid(guid);
			query_user.set_product(info.getProduct());

			// 设置sn与user_id的关系写入redis
			// RedisDBUtil.setHashValue(sn_user_redis, info.getSn(), info.getUserId());
			redisTemplate.opsForHash().put(sn_user_redis, info.getSn(), info.getUserId());
			return userDao.saveUserVehicleFile(query_user);
		}
		return 0;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
	public void setUserPushBind(InputSetPushBind info, String guid) throws Exception {
		userDao.userPushBind(info);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
	public Integer setPushOnOff(InputSetPushRemind info) throws Exception {
		return userDao.setPushOnoff(info);
	}

	@Override
	public NeObdPushBindTo findUserPushRemind(InputGetPushRemind info) throws Exception {
		return userDao.findUserPushRemind(info);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
	public Integer RemoveSnBinding(InputRemoveSnBinding info) throws Exception {
		// 删除sn与user的关系
		// RedisDBUtil.delHashValue(sn_user_redis, info.getSn());
		redisTemplate.opsForHash().delete(sn_user_redis, info.getSn());
		return userDao.removeSnBinding(info);
	}

	@Override
	public String findUserVehicleFileFromUserId(InputSetVehicleFile info) throws Exception {
		NeObdUserCarInfoTo qnoucit = userDao.findUserInfoFromUserId(info.getUserId());
		if (qnoucit.get_id() != null) {
			return qnoucit.get_id();
		}
		return null;
	}

}
