package com.mapbar.netobd.push;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.mapbar.netobd.bean.dto.NeObdPushBindTo;
import com.mapbar.netobd.dao.UserDao;
import com.mapbar.netobd.units.CustomUnits;
import com.mapbar.netobd.units.DateUtil;
import com.mapbar.netobd.units.LonLatUtil;

import net.sf.json.JSONObject;

/**
 * @description: 推送相关
 * @author xubh
 * @date: 2016年4月29日
 */
@Component
public class PushUnits {

	private static final Logger logger = LoggerFactory.getLogger(PushUnits.class);

	private static String voltage_push = "voltage_push";
	private static String point_push = "point_push";

	@Value("${redis.name.sn_userId:neobd_sn_userid}")
	private String sn;
	private static String sn_user_redis = "";

	@Autowired
	private RedisTemplate<String, Map<String, String>> redisTemplate;
	private static RedisTemplate<String, Map<String, String>> staticRedisTemplate;
	@Autowired
	private CustomUnits customUnits;
	private static CustomUnits staticustomUnits;
	@PostConstruct
	public void init() {
		sn_user_redis = sn;
		staticRedisTemplate = this.redisTemplate;
		staticustomUnits=customUnits;
	}

	/**
	 * 点火提醒推送
	 * 
	 * @param sn
	 * @param staticUserDao
	 * @throws Exception
	 */
	public static void pushTripState(String sn, UserDao userDao) throws Exception {
		// 查询sn对应的推送设置
		NeObdPushBindTo nopbt = userDao.findPushSetInfo(sn);
		if (nopbt.get_ignition_push() == 1) {
			new TuiSong().IXTuiSong(nopbt.get_product(), "您的车辆于" + DateUtil.currentTime() + " 点火启动,请注意是否为本人操作！，您的车辆已点火,请注意。", nopbt.get_push_token());
		}
	}


	public static void pushVoltage(JSONObject json, UserDao userDao) throws Exception {
		logger.info("调用电压推送服务" + json);
		// String voltage_value = RedisDBUtil.getHashValue(voltage_push, json.getString("obd_serial"));
		String voltage_value = (String) staticRedisTemplate.opsForHash().get(voltage_push, json.getString("obd_serial"));
		System.out.println(voltage_value);
		if (voltage_value != null) {// 判断电压推送记录是否有数据
			logger.info("判断电压》设置电压:");
			if (json.getDouble("voltage") >= Double.parseDouble(voltage_value)) {// 判断电压>设置电压
				logger.info("删除推送电压提醒:" + json);
				// RedisDBUtil.delHashValue(voltage_push, json.getString("obd_serial"));// 删除电压推送记录
				staticRedisTemplate.opsForHash().delete(voltage_push, json.getString("obd_serial"));
			}
		} else {
			NeObdPushBindTo nopbt = userDao.findPushSetInfo(json.getString("obd_serial"));// 获取推送设置记录
			if (nopbt.get_voltages_push() == 1 && json.getDouble("voltage") < nopbt.get_set_voltages()) {// 判断是否允许推送，并且电压小于设置电压
				logger.info("推送电压提醒" + nopbt.get_voltages_push());
				new TuiSong().IXTuiSong(nopbt.get_product(), "您的车辆于" + DateUtil.currentTime() + " 发生电瓶电压异常,请尽快检查车辆以免影响车辆启动！，您的车辆电压低于设定值,请注意。", nopbt.get_push_token());
				// RedisTemplate<?, ?> template = SpringContextUtil.getBean("redistemplate", RedisTemplate.class);
				staticRedisTemplate.opsForHash().put(voltage_push, json.getString("obd_serial"), nopbt.get_set_voltages() + "");
				// RedisDBUtil.setHashValue(voltage_push, json.getString("obd_serial"), nopbt.get_set_voltages() + "");
			}
		}
	}

	/**
	 * 安全区域提醒
	 * 
	 * @param json
	 * @param userDao
	 * @throws Exception
	 */
	public static void pushPoint(JSONObject json, UserDao userDao) throws Exception {
		// 获取sn与user关系,在用户绑定sn和解绑sn接口中进行修改
		// String user_id = RedisDBUtil.getHashValue(sn_user_redis, json.getString("obd_serial"));
		String user_id = (String) staticRedisTemplate.opsForHash().get(sn_user_redis, json.getString("obd_serial"));
		if (user_id != null) {// 如果为空表示没有人绑定这个sn
			// 84转02
			double[] xy = LonLatUtil.encode(json.getDouble("x"), json.getDouble("y"));
			// 验证是否在安全区域有内
			Integer judge = staticustomUnits.getElectricFenceJudge(xy[0], xy[1], user_id);
			if (judge == 1) {// 如果在安全区域内上传点推送中的sn
				// RedisDBUtil.delHashValue(point_push, json.getString("obd_serial"));
				staticRedisTemplate.opsForHash().delete(point_push, json.getString("obd_serial"));
			} else {
				// 如果不在安全范围内，判断是否已经在点推送redis中，如果不在推送并写入redis，如果在跳过
				// if (RedisDBUtil.getHashValue(point_push, json.getString("obd_serial")) == null) {
				if (staticRedisTemplate.opsForHash().get(point_push, json.getString("obd_serial")) == null) {
					logger.info("推送不在安全区域内");
					NeObdPushBindTo nopbt = userDao.findPushSetInfo(json.getString("obd_serial"));// 获取推送设置记录
					// 您的车辆于2016/3/18 18:12:33 离开了您指定的安全区域，请注意是否为本人操作！
					new TuiSong().IXTuiSong(nopbt.get_product(), "您的车辆于" + DateUtil.currentTime() + " 离开了您指定的安全区域,请注意是否为本人操作！，您的车辆离开安全区域,请注意。", nopbt.get_push_token());
					// RedisDBUtil.setHashValue(point_push, json.getString("obd_serial"), "false");
					staticRedisTemplate.opsForHash().put(point_push, json.getString("obd_serial"), "false");
				}
			}
		}
	}

	/**
	 * 熄火后发生碰撞提醒
	 * 
	 * @param json
	 * @param userDao
	 * @throws Exception
	 */
	public static void pushCollision(JSONObject json, UserDao userDao) throws Exception {
		NeObdPushBindTo nopbt = userDao.findPushSetInfo(json.getString("obd_serial"));// 获取推送设置记录
		if (nopbt.get_shock_push() == 1 && nopbt.get_shock_active() == 1) {
			new TuiSong().IXTuiSong(nopbt.get_product(), "异常震动提醒，您的车辆于" + DateUtil.currentTime() + " 发生异常震动,请注意是否为本人操作！，您的车辆发生震动,请注意。", nopbt.get_push_token());
		}
	}

	/**
	 * OBD拔出推送
	 * 
	 * @param json
	 * @param userDao
	 * @throws Exception
	 */
	public static void pushObdPullOut(JSONObject json, UserDao userDao) throws Exception {
		NeObdPushBindTo nopbt = userDao.findPushSetInfo(json.getString("obd_serial"));// 获取推送设置记录
		if (nopbt.get_offline_push() == 1 && nopbt.get_shock_active() == 1) {
			// 您的硬件盒子于2016/3/18 18:12:33 停止工作，可能被异常拔出或发生异常，请确认检查！
			new TuiSong().IXTuiSong(nopbt.get_product(), "您的硬件盒子于" + DateUtil.currentTime() + " 停止工作,可能被异常拔出或发生异常,请确认检查！，您的OBD盒子被拔出,请注意。", nopbt.get_push_token());
		}

	}
}
