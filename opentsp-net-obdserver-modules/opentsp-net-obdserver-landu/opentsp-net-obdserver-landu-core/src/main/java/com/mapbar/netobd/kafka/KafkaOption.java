package com.mapbar.netobd.kafka;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.mapbar.netobd.bean.dto.NeObdUserCarInfoTo;
import com.mapbar.netobd.dao.CarConditionDao;
import com.mapbar.netobd.dao.CheckInfoDao;
import com.mapbar.netobd.dao.DriveInfoDao;
import com.mapbar.netobd.dao.UserDao;
import com.mapbar.netobd.kafkaBean.CheckInfoTemp;
import com.mapbar.netobd.kafkaBean.KafkaTravelData;
import com.mapbar.netobd.push.PushUnits;
import com.mapbar.netobd.units.CustomUnits;
import com.mapbar.netobd.units.DateUtil;
import com.mapbar.netobd.units.LonLatUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Component
public class KafkaOption {

	private static final Logger logger = LoggerFactory.getLogger(KafkaOption.class);
	@Value("gecodeing:http://wireless.mapbar.com/reverse/getInverseGeocoding.json?detail=1&zoom=12&inGb=02&outGb=02&")
	private String gecodeing;
	@Value("redis.name.v:neobd_car_condition_v")
	private String redisName_v;
	@Value("redis.name.p:neobd_car_condition_p")
	private String redisName_p;
	@Value("neobd.fault.code:neobd_fault_code")
	private String neobd_fault_code;
	private static Map<String, Object> speed_map = new HashMap<String, Object>();

	@Resource
	private DriveInfoDao driveInfoDao;

	@Resource
	private CheckInfoDao checkInfoDao;

	@Resource
	private UserDao userDao;

	@Resource
	private CarConditionDao carConditionDao;

	@Resource
	private RedisTemplate<String, Map<String, String>> redisTemplate;
	@Autowired
	private CustomUnits customUnits;

	// 通过该标签以及该方法实现给static属性注入
	@PostConstruct
	private void init() {

		List<Map<String, Object>> speed_set = null;
		try {
			speed_set = driveInfoDao.getSpeedSet();
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (int i = 0; i < speed_set.size(); i++) {
			Map<String, Object> map = speed_set.get(i);
			speed_map.put(map.get("speed_key").toString(), map);
		}
		logger.info("加载行程数据中的速度区间:" + speed_map);
	}

	/**
	 * 处理行程数据
	 * 
	 * @param json
	 * @throws Exception
	 */
	public void optionTravelData(JSONObject json) throws Exception {
		KafkaTravelData dit = new KafkaTravelData();
		dit.set_id(UUID.randomUUID().toString());
		dit.set_obd_serial(json.getString("obd_serial"));// sn
		dit.set_travel_id(json.getString("travel_id"));// 行程ID

		// 通过硬件的SN找到用户信息
		NeObdUserCarInfoTo noucit = userDao.getUserInfo(dit.get_obd_serial());
		if (noucit.get_user_id() != null) {
			dit.set_user_id(noucit.get_user_id());// user_id
			dit.set_car_id(noucit.get_id());// car_id
			dit.set_car_model_id(noucit.get_car_model_id());// car_model_id
			dit.set_travel_oil_price(noucit.get_set_oil_price());// 用户设置的油价
		}

		dit.set_vid(json.getString("vid"));// vid
		dit.set_vin(json.getString("vin"));// vin
		dit.set_start_time(DateUtil.getLongFromStr(json.getString("start_time")));// 行程开始时间
		dit.set_end_time(DateUtil.getLongFromStr(json.getString("end_time")));// 行程结束时间
		dit.set_travel_date(DateUtil.getStrDate(dit.get_start_time()));// 行程日期yyyyMMdd
		dit.set_travel_time(json.getLong("travel_time"));// 行程耗时

		double[] start_lonlat = LonLatUtil.encodeString(json.getString("start_lon"), json.getString("start_lat"));
		double[] end_lonlat = LonLatUtil.encodeString(json.getString("end_lon"), json.getString("end_lat"));
		dit.set_start_lon(start_lonlat[0] + "");// 开始经度
		dit.set_start_lat(start_lonlat[1] + "");// 开始纬度
		dit.set_end_lon(end_lonlat[0] + "");// 结束纬度
		dit.set_end_lat(end_lonlat[1] + "");// 结束经度
		logger.info("转换开始经纬度和结束经纬度:" + json.getString("start_lon") + "," + json.getString("start_lat") + "_____" + dit.get_start_lon() + "," + dit.get_start_lat());
		logger.info("转换开始经纬度和结束经纬度:" + json.getString("end_lon") + "," + json.getString("end_lat") + "_____" + dit.get_end_lon() + "," + dit.get_end_lat());

		dit.set_this_travel_len(json.getDouble("this_travel_len"));// 行程距离，单位米
		dit.set_this_avg_oil(json.getDouble("this_avg_oil") / 100);// 本次百公里油耗
		dit.set_sum_avg_oil(json.getDouble("sum_avg_oil") / 100);// 累加平均油耗
		dit.set_avg_speed(json.getInt("avg_rage_speed"));// 平均速度
		dit.set_max_speed(json.getInt("max_speed"));// 最大速度

		// 处理数度信息
		String speedInfo = getSpeedInfo(json.getString("speed_info"));
		dit.set_speed_info(speedInfo);// 速度信息
		dit.set_speed_sum(json.getInt("speed_sum"));// 速度总和
		dit.set_speed_60(json.getInt("speed_60"));// 速度60次数
		dit.set_speed_90(json.getInt("speed_90"));// 速度90次数
		dit.set_speed_120(json.getInt("speed_120"));// 速度120次数
		dit.set_speed_121(json.getInt("speed_121"));// 速度大于120次数
		dit.set_this_fastup_count(json.getInt("this_fastup_count"));// 急加速次数
		dit.set_this_fastlow_count(json.getInt("this_fastlow_count"));// 急减速次数
		dit.set_this_crook_count(json.getInt("this_crook_count"));// 急转弯次数
		dit.set_this_idling_num(json.getInt("this_idling_num"));// 怠速数量

		logger.info("处理消费到的轨迹点数据开始：" + json.getString("track_info"));
		dit.set_track_info(LonLatUtil.encodeJSONArray(json.getString("track_info")));// 轨迹信息
		logger.info("处理消费到的轨迹点数据结束：" + dit.get_track_info());

		dit.set_sum_drive_range(json.getDouble("sum_drive_range"));// 累计行驶里程

		// 自计算部分
		dit.set_this_idling_time(getIdlingTime(dit.get_speed_info()));// 怠速时长分钟
		dit.set_carmodel_avg_oil(customUnits.getCarWare(dit.get_car_model_id()));// 官网百公里油耗
		dit.set_this_oil_ware((dit.get_this_avg_oil() / 100) * (dit.get_this_travel_len() / 1000));// 行程总油耗,平均油耗*行程公里数

		// 获取起始点位置
		String url = gecodeing + "lat=" + dit.get_start_lat() + "&lon=" + dit.get_start_lon();
		String url2 = gecodeing + "lat=" + dit.get_end_lat() + "&lon=" + dit.get_end_lon();
		if (dit.get_start_lon().equals("0.0") || dit.get_start_lat().equals("0.0")) {
			dit.set_start_point("");
		} else {
			logger.info("调用逆地理：" + url);
			dit.set_start_point(customUnits.getAddressInfo(url));// 起始点调用逆地理
		}

		if (dit.get_end_lat().equals("0.0") || dit.get_end_lat().equals("0.0")) {
			dit.set_end_point("");
		} else {
			logger.info("调用逆地理：" + url2);
			dit.set_end_point(customUnits.getAddressInfo(url2));// 结束点调用逆地理
		}

		dit.set_travel_cost(dit.get_this_oil_ware() * dit.get_travel_oil_price());// 行程花费，行程总油耗*行程油价
		dit.set_travel_score(customUnits.getTravelScore(dit));// 行程得分
		dit.set_speed_des(customUnits.getSpeedDes(dit.get_avg_speed()));// 速度部分
		dit.set_oil_ware(customUnits.getOilWareDes(dit));// 油耗部分
		dit.set_gas_consum_des(customUnits.getGasConsumDes(dit));// 油耗建议
		dit.set_drive_des(customUnits.getDriveDes(dit));// 驾驶建议

		// 进行入库操作
		driveInfoDao.saveTravelData(dit);
		logger.info("入库完成");

		// 激活碰撞提醒
		userDao.updateShockActive(1, json.getString("obd_serial"));

		// 更新 T运营，车辆状态
		// RedisDBUtil.setHashValue("tb_" + json.getString("obd_serial"), "vs", "3");
		// RedisDBUtil.setHashValue("tb_" + json.getString("obd_serial"), "lc", dit.get_sum_drive_range() + "");
		redisTemplate.opsForHash().put("tb_" + json.getString("obd_serial"), "vs", "3");
		redisTemplate.opsForHash().put("tb_" + json.getString("obd_serial"), "lc", dit.get_sum_drive_range() + "");
	}

	// 处理数度数据
	private static String getSpeedInfo(String speedInfo) {
		JSONArray r_jsona = new JSONArray();

		JSONArray jsona = JSONArray.fromObject(speedInfo);
		for (int i = 0; i < jsona.size(); i++) {
			JSONObject jsons = JSONObject.fromObject(jsona.get(i));
			JSONObject appJson = JSONObject.fromObject(speed_map.get(jsons.get("speed").toString()));

			// jsons.put("speed", appJson.get("speed_name"));
			jsons.put("speed", appJson.get("min") + "-" + appJson.get("max"));
			jsons.put("colour", appJson.get("colour"));
			// jsons.put("max", appJson.get("max"));
			// jsons.put("min", appJson.get("min"));

			r_jsona.add(jsons);
		}

		return r_jsona.toString();
	}

	public void optionTravelState(JSONObject json) throws Exception {
		// 清空碰撞提醒
		carConditionDao.deleteCollisionAlert(json.getString("obd_serial"));

		// 修改OBD状态
		userDao.updateObdState(1, json.getString("obd_serial"));

		// 关闭碰撞提醒
		userDao.updateShockActive(0, json.getString("obd_serial"));
		// 执行点火推送
		PushUnits.pushTripState(json.getString("obd_serial"), userDao);
	}

	public void optionCarCondition(JSONObject json) throws Exception {
		logger.info(json.toString());

		// 用户车况：电压、实时位置，写入到redis中,type=1为电压，0为点
		if (json.getInt("type") == 1) {
			logger.info("上传的电压数据写入到redis中");
			// RedisDBUtil.setHashValue(redisName_v, json.getString("obd_serial"), json.toString());// 写入redis
			redisTemplate.opsForHash().put(redisName_v, json.getString("obd_serial"), json.toString());
			PushUnits.pushVoltage(json, userDao);// 调用电压报警
		} else {
			json = LonLatUtil.encodeJsonXY(json);
			logger.info("转换经纬度后" + json);
			// RedisDBUtil.setHashValue(redisName_p, json.getString("obd_serial"), json.toString());// 写入redis
			redisTemplate.opsForHash().put(redisName_p, json.getString("obd_serial"), json.toString());
			PushUnits.pushPoint(json, userDao);// 出安全范围报警

			// 写入T运营redis
			if (json.get("v") != null) {

				int s = 1; // 车辆状态，1：行驶，速度>0, 2静止，速度==0， 3离线，行程上传时
				int v = json.getInt("v");
				if (v == 0) {
					s = 2;
				}

				// RedisDBUtil.setHashValue("tb_" + json.getString("obd_serial"), "flon", json.getString("x"));
				// RedisDBUtil.setHashValue("tb_" + json.getString("obd_serial"), "flat", json.getString("y"));
				// RedisDBUtil.setHashValue("tb_" + json.getString("obd_serial"), "vs", s + "");
				redisTemplate.opsForHash().put("tb_" + json.getString("obd_serial"), "flon", json.getString("x"));
				redisTemplate.opsForHash().put("tb_" + json.getString("obd_serial"), "flat", json.getString("y"));
				redisTemplate.opsForHash().put("tb_" + json.getString("obd_serial"), "vs", s + "");
			}
		}
	}

	/**
	 * 获取怠速时长
	 * 
	 * @param get_speedDoList
	 * @return
	 */
	private static long getIdlingTime(String get_speedDoList) {
		JSONArray jsona = JSONArray.fromObject(get_speedDoList);
		for (int i = 0; i < jsona.size(); i++) {
			JSONObject json = jsona.getJSONObject(i);
			if (json.get("speed").equals("0")) {
				return json.getLong("sum_time");
			}
		}
		return 0;
	}

	/**
	 * 处理体检数据
	 * 
	 * json.put("obd_serial","311411003043c");//obd串号 json.put("vin","LFPM4ACC1B1E13630");//vin码 json.put("fault_code","P001");//故障码 json.put("control_voltage",9);//控制模块电压 json.put("coolant_temper",10);//发动机冷冻液温都 json.put("short_term",15.0);//短期燃油调整 json.put("long_term",20.0);//长期燃油调整 json.put("oxygen_voltage",0.7);//氧传感器电压 json.put("engine_load",31.8);//计算发动机负荷值
	 * json.put("intake_perssure",41);//进气管绝对压力 json.put("engine_rpm",810);//发动机转数 json.put("ignition_angle",4);//点火正时提前角 json.put("inlet_temper",60);//进气温度 json.put("relative_position",15.7);//节气门相对位置 json.put("check_time", System.currentTimeMillis());
	 * 
	 * @param json
	 * @throws Exception
	 */
	public void optionCheckInfo(JSONObject json) throws Exception {
		CheckInfoTemp cit = new CheckInfoTemp();
		cit.set_id(UUID.randomUUID().toString());
		cit.set_obd_serial(json.getString("obd_serial"));
		cit.set_vin(json.getString("vin"));

		// 获取Redis上存储的故障码信息
		// String fault_code = RedisDBUtil.getHashValue(neobd_fault_code, cit.get_obd_serial());
		String fault_code = (String) redisTemplate.opsForHash().get(neobd_fault_code, cit.get_obd_serial());
		if (fault_code != null) {
			String fault_codes = fault_code.substring(0, fault_code.length() - 1);
			// 判重故障码
			cit.set_fault_code(fault_codes);
			// 上传体检数据后，清除本次行程中的故障码存储，清除redis
			// RedisDBUtil.delHashValue(neobd_fault_code, cit.get_obd_serial());
			redisTemplate.opsForHash().delete(neobd_fault_code, cit.get_obd_serial());
		}

		if (cit.get_fault_code() != null && !cit.get_fault_code().equals("")) {
			cit.set_fault_code_num(cit.get_fault_code().split(",").length);
		} else {
			cit.set_fault_code_num(0);
		}

		if (json.get("control_voltage").equals("")) {
			cit.set_control_voltage(0);
		} else {
			cit.set_control_voltage(json.getDouble("control_voltage"));
		}

		if (json.get("coolant_temper").equals("")) {
			cit.set_coolant_temper(0);
		} else {
			cit.set_coolant_temper(json.getDouble("coolant_temper"));
		}

		if (json.get("short_term").equals("")) {
			cit.set_short_term(0);
		} else {
			cit.set_short_term(json.getDouble("short_term"));
		}

		if (json.get("long_term").equals("")) {
			cit.set_long_term(0);
		} else {
			cit.set_long_term(json.getDouble("long_term"));
		}

		if (json.get("oxygen_voltage").equals("")) {
			cit.set_oxygen_voltage(0);
		} else {
			cit.set_oxygen_voltage(json.getDouble("oxygen_voltage"));
		}

		if (json.get("engine_load").equals("")) {
			cit.set_engine_load(0);
		} else {
			cit.set_engine_load(json.getDouble("engine_load"));
		}

		if (json.get("intake_perssure").equals("")) {
			cit.set_intake_perssure(0);
		} else {
			cit.set_intake_perssure(json.getDouble("intake_perssure"));
		}

		if (json.get("engine_rpm").equals("")) {
			cit.set_engine_rpm(0);
		} else {
			cit.set_engine_rpm(json.getDouble("engine_rpm"));
		}

		if (json.get("ignition_angle").equals("")) {
			cit.set_ignition_angle(0);
		} else {
			cit.set_ignition_angle(json.getDouble("ignition_angle"));
		}

		if (json.get("inlet_temper").equals("")) {
			cit.set_inlet_temper(0);
		} else {
			cit.set_inlet_temper(json.getDouble("inlet_temper"));
		}

		if (json.get("relative_position").equals("")) {
			cit.set_relative_position(0);
		} else {
			cit.set_relative_position(json.getDouble("relative_position"));
		}

		cit.set_check_time(DateUtil.getLongFromStr(json.getString("check_time")));

		cit.set_check_score(0);// 需要计算方法,接口访问的时候进行计算

		NeObdUserCarInfoTo noucit = userDao.getUserInfo(cit.get_obd_serial());// 通过硬件的SN找到用户信息
		if (noucit.get_user_id() != null) {
			cit.set_user_id(noucit.get_user_id());
			cit.set_car_id(noucit.get_id());
			cit.set_car_model_id(noucit.get_car_model_id());
		}

		checkInfoDao.InsertCheckInfo(cit);
	}

	public void optionCollisionAlert(JSONObject json) throws Exception {
		// 判断是否是碰撞报警
		Integer alarm_code = json.getInt("alarm_code");
		if (alarm_code == 3) {
			// 更新碰撞提醒库
			Integer x = carConditionDao.findCollisionCountFromSN(json.getString("obd_serial"));
			if (x > 0) {
				carConditionDao.updateCollisionAlert(json.getString("obd_serial"));
			} else {
				carConditionDao.insertCollisionAlert(json.getString("obd_serial"));
			}
			// 碰撞推送
			PushUnits.pushCollision(json, userDao);
		} else if (alarm_code == 240) {
			userDao.updateObdState(0, json.getString("obd_serial"));
			PushUnits.pushObdPullOut(json, userDao);// OBD拔下提醒
		} else if (alarm_code == 1) {
			// 处理故障码数据，写入到redis中
			// RedisDBUtil.setHashValue(neobd_fault_code, json.getString("obd_serial"), json.getString("unplug_obd_time"));
			redisTemplate.opsForHash().put(neobd_fault_code, json.getString("obd_serial"), json.getString("unplug_obd_time"));
		}

	}
}
