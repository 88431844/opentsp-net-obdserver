package com.mapbar.netobd.units;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mapbar.netobd.bean.dto.CarModelInfoTo;
import com.mapbar.netobd.bean.dto.CheckItemTo;
import com.mapbar.netobd.bean.dto.OilDataTo;
import com.mapbar.netobd.bean.dto.UserCenterInfo;
import com.mapbar.netobd.kafkaBean.KafkaTravelData;
import com.mapbar.netobd.server.base.BaseDateServer;

import net.sf.json.JSONObject;

@Component
public class CustomUnits {

	private static final Logger logger = LoggerFactory.getLogger(CustomUnits.class);

	public static Map<String, Object> car_info_map = new ConcurrentHashMap<>();
	public static Map<String, Object> car_oil_type = new ConcurrentHashMap<>();
	public static Map<String, Object> car_oil_price = new ConcurrentHashMap<>();
	public static Map<String, Object> car_oil_ware = new ConcurrentHashMap<>();
	public static Map<String, Object> check_item = new ConcurrentHashMap<>();// 体检附加值
	public static Map<String, Object> item_scoure = new ConcurrentHashMap<>();// 体检区间最大值，最小值

	@Value("${electric_fence_judge:http://wedrive.mapbar.com/opentsp/gis/api/fence/search?inGb=g02&ak=0de2c8377cee6d17aa881ad0ec0401c2&resType=json&lonlat=}")
	private String electric_fence_judge;
	@Value("${max_value:#FF0000}")
	private String max_value;
	@Value("${min_value:#00FF00}")
	private String min_value;
	@Value("${normal_value:#0000FF}")
	private String normal_value;

	@Resource
	private BaseDateServer baseDateServer;

	// 通过该标签以及该方法实现给static属性注入
	@PostConstruct
	public void init() throws Exception {
		logger.info("加载体检显示颜色值.....");
		logger.info("查询获取体检项目列表................");
		List<CheckItemTo> check_item_list = baseDateServer.findCheckItem();
		for (int i = 0; i < check_item_list.size(); i++) {
			CheckItemTo item = check_item_list.get(i);
			JSONObject json = new JSONObject();
			json.put("item_title", item.get_item_title());
			json.put("item_explain", item.get_item_explain());
			json.put("item_refe", item.get_item_refe());
			json.put("item_refe", item.get_item_refe());
			check_item.put(item.get_item_name(), json);

			JSONObject json2 = new JSONObject();
			json2.put("refe_max", item.get_refe_max());
			json2.put("refe_min", item.get_refe_min());
			json2.put("item_scoure", item.get_item_scoure());
			item_scoure.put(item.get_item_name(), json2);
		}
		logger.info("check_item is {}", check_item);
		logger.info("item_scoure is {}", item_scoure);
	}

	/**
	 * 通过请求的车型数据获取用户车型的ID，其他表中的car_id
	 * 
	 * @param car_model_id
	 * @param car_code
	 * @return
	 * @throws Exception
	 */
	public String getCarId(String car_model_id, String car_code) throws Exception {
		if (!car_info_map.containsKey(car_model_id)) {
			CarModelInfoTo cmif = baseDateServer.getCarFormId(car_model_id);
			car_info_map.put(car_model_id, cmif.get_m_f_brand() + cmif.get_m_t_brand() + cmif.get_m_model() + cmif.get_m_year());
		}
		String car_info = car_info_map.get(car_model_id) + car_code;
		String md5str = "";
		try {
			md5str = new MD5Util2().GetMD5Code(car_info);
		} catch (Exception e) {
			logger.info("生成CarId出错:" + car_info);
		}
		return md5str;
	}

	/**
	 * 通过car_model_id获取官网油耗
	 * 
	 * @param car_model_id
	 * @param car_code
	 * @return
	 * @throws Exception
	 */
	public double getCarWare(String car_model_id) throws Exception {
		if (!car_oil_ware.containsKey(car_model_id)) {
			CarModelInfoTo cmif = baseDateServer.getCarFormId(car_model_id);
			car_info_map.put(car_model_id, cmif.get_complex_consum());
		}
		double ware = 9;
		if (car_info_map.get(car_model_id) != null && !car_info_map.get(car_model_id).equals(0.0)) {
			ware = Double.parseDouble(car_info_map.get(car_model_id).toString());
			if (ware <= 0) {
				ware = 9;
			}
		}
		return ware;
	}

	/**
	 * 请求URL
	 * 
	 * @param seturl
	 * @return
	 */
	public JSONObject getJsonFromUrl(String seturl) {
		HttpURLConnection conn = null;
		try {
			URL url = new URL(seturl);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestProperty("Access-Control-Allow-Origin:", "*");
			conn.connect();
			int stateCode = conn.getResponseCode();
			if (stateCode == 200) {
				StringBuffer sb = new StringBuffer("");
				BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
				while (reader.ready()) {
					sb.append(reader.readLine());
				}
				reader.close();
				JSONObject obj = JSONObject.fromObject(sb.toString());
				return obj;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
		return null;
	}

	/**
	 * 获取官网车型指定的燃油类型
	 * 
	 * @param carModelId
	 * @return
	 * @throws Exception
	 */
	public int getOiltype(String carModelId) throws Exception {
		if (!car_oil_type.containsKey(carModelId)) {
			CarModelInfoTo cmif = baseDateServer.getCarFormId(carModelId);
			car_oil_type.put(carModelId, cmif.get_fuel_type());
		}

		if (car_oil_type.get(carModelId) != null && !car_oil_type.get(carModelId).equals("")) {

			String oil_type = car_oil_type.get(carModelId).toString();

			oil_type = oil_type.replace("汽油 待查", "").replace("油电混合动力 93号(北京92号)", "92").replace("油电混合动力 97号(北京95号)", "95").replace("汽油 93号(北京92号)", "92").replace("汽油 97号(北京95号)", "95");
			if (oil_type.indexOf("柴油") > 0) {
				oil_type = "0";
			}

			if (oil_type.matches("\\d+")) {
				return Integer.parseInt(oil_type);
			} else {
				return 92;
			}
		}
		return 92;
	}

	public void saveOilType(List<OilDataTo> oil_list) throws Exception {
		baseDateServer.saveOilPrice(oil_list);
		logger.info("更新油价完成");
	}

	public double getOilPrice(String area, int type) throws Exception {
		String key = area + type;
		if (!car_oil_price.containsKey(key)) {
			OilDataTo oil = baseDateServer.findOilPrice(area, type);
			car_oil_price.put(key, oil.get_price());
		}
		return Double.parseDouble(car_oil_price.get(key).toString());
	}

	public UserCenterInfo getUserInfo(String userId) {
		return UserCenterUtils.getUserInfo(userId);
	}

	/**
	 * 逆地理
	 * 
	 * @param url
	 * @return
	 */
	public String getAddressInfo(String url) {
		StringBuffer sb = new StringBuffer();
		JSONObject json = getJsonFromUrl(url);

		sb.append(JSONObject.fromObject(json.getString("province")).getString("value").replace("直辖市", ""));
		sb.append(JSONObject.fromObject(json.getString("city")).getString("value"));
		sb.append(JSONObject.fromObject(json.getString("dist")).getString("value"));
		sb.append(JSONObject.fromObject(json.getString("area")).getString("value"));
		sb.append(JSONObject.fromObject(json.getString("town")).getString("value"));
		sb.append(JSONObject.fromObject(json.getString("village")).getString("value"));

		return sb.toString();
	}

	/**
	 * 安全性评分规则
	 * 
	 * @param fastup_count
	 *            急加速次数
	 * @param fastlow_count
	 *            急加速次数
	 * @param idling_num
	 *            怠速次数
	 * @param idling_time
	 *            怠速时长
	 * @return
	 */
	private double getAqScore(double fastup_count, double fastlow_count, double idling_num, double idling_time) {
		double score1 = 0;
		double score2 = 0;
		double score3 = 0;
		double score4 = 0;
		if (fastup_count < 49) {
			score1 = (100 - fastup_count * 2) * 0.3;
		}
		if (fastlow_count < 49) {
			score2 = (100 - fastlow_count * 2) * 0.3;
		}
		if (idling_num < 49) {
			score3 = (100 - idling_num * 2) * 0.2;
		}

		if (idling_time > 0) {
			idling_time = idling_time / 1000 / 60;
			if (idling_time < 49) {
				score4 = (100 - idling_time * 2) * 0.2;
			}
		} else {
			score4 = 100 * 0.2;
		}
		return score1 + score2 + score3 + score4;
	}

	/**
	 * 计算行程得分
	 * 
	 * @param dit
	 * @param fastup_count
	 *            急加速次数
	 * @param fastlow_count
	 *            急减速次数
	 * @param idling_num
	 *            怠速次数
	 * @param idling_time
	 *            怠速时长
	 * @param speed_sum
	 *            瞬时数据总数
	 * @param speed_60
	 *            2速
	 * @param speed_90
	 *            3速
	 * @param speed_120
	 *            4速
	 * @param speed_121
	 *            5速
	 * @param this_avg_oil
	 * @param car_ware
	 * 
	 *            double fastup_count, double fastlow_count, double idling_num, double idling_time, double speed_sum, double speed_60, double speed_90, double speed_120, double speed_121, double this_avg_oil, double car_ware
	 * @return
	 */
	public double getTravelScore(KafkaTravelData dit) {
		double aq_score = getAqScore(dit.get_this_fastup_count(), dit.get_this_fastlow_count(), dit.get_this_idling_num(), dit.get_this_idling_time()); // 计算安全性分数
		double jj_score = getJjScore(dit.get_speed_sum(), dit.get_speed_60(), dit.get_speed_90(), dit.get_speed_120(), dit.get_speed_121(), dit.get_this_idling_num());// 计算经济性评分
		double ware_score = getWareScore(dit.get_this_avg_oil(), dit.get_carmodel_avg_oil());
		// logger.info("安全性得分:" + aq_score + "\t经济性得分:" + jj_score * 0.6 + "\t油耗得分:" + ware_score);
		return (aq_score * 0.4 + jj_score * 0.6) * 0.4 + ware_score * 0.6;
	}

	/**
	 * 计算油耗得分
	 * 
	 * @param this_avg_oil
	 * @param car_ware
	 * @return
	 */
	private double getWareScore(double this_avg_oil, double car_ware) {
		double s = this_avg_oil / car_ware;
		if (s > 2.0) {
			return 0;
		}
		if (s > 1.9) {
			return 30;
		}
		if (s > 1.7) {
			return 40;
		}
		if (s > 1.5) {
			return 50;
		}
		if (s > 1.3) {
			return 60;
		}
		if (s > 1.0) {
			return 70;
		}
		if (s > 0.8) {
			return 80;
		}
		if (s > 0.7) {
			return 90;
		}
		return 100;
	}

	/**
	 * 计算经济性评分
	 * 
	 * @param speed_sum
	 * @param speed_60
	 * @param speed_90
	 * @param speed_120
	 * @param speed_121
	 * @param idling_num
	 * @return
	 */
	private double getJjScore(double speed_sum, double speed_60, double speed_90, double speed_120, double speed_121, double idling_num) {
		double level1 = getJjScoreNext(idling_num, speed_sum) * 0.2;
		double level2 = getJjScoreNext(speed_60, speed_sum) * 0.2;
		double level3 = getJjScoreNext(speed_90, speed_sum) * 0.2;
		double level4 = getJjScoreNext(speed_120, speed_sum) * 0.2;
		double level5 = getJjScoreNext(speed_121, speed_sum) * 0.2;
		return level1 + level2 + level3 + level4 + level5;
	}

	// 计算经济性评分规则
	private double getJjScoreNext(double speed_unit, double speed_sum) {
		double level = speed_unit / speed_sum;
		return 100 - level * 100 * 2;
	}

	/**
	 * 速度部分
	 * 
	 * @param avg_speed
	 * @return
	 */
	public String getSpeedDes(int avg_speed) {
		if (avg_speed > 89) {
			return "速度偏高";
		}
		if (avg_speed > 59) {
			return "速度稳定，路况通畅";
		}
		return "速度偏低，部分路段不通畅";
	}

	/**
	 * 油耗部分
	 * 
	 * @param dit
	 * @return
	 */
	public String getOilWareDes(KafkaTravelData dit) {
		double s = dit.get_this_avg_oil() / dit.get_carmodel_avg_oil();
		if (s > 1.9) {
			return "油耗较高";
		}
		if (s > 1.5) {
			return "油耗偏高";
		}
		if (s > 1.3) {
			return "油耗略高";
		}
		if (s > 1.0) {
			return "油耗正常";
		}
		return "油耗低";
	}

	/**
	 * 油耗建议
	 * 
	 * @param dit
	 * @return
	 */
	public String getGasConsumDes(KafkaTravelData dit) {
		double s = dit.get_this_avg_oil() / dit.get_carmodel_avg_oil();
		if (s > 1.9) {
			return "油耗较高,请注意养成良好驾驶习惯";
		}
		if (s > 1.5) {
			return "油耗偏高,请注意养成良好驾驶习惯";
		}
		if (s > 1.3) {
			return "油耗略高,请注意养成良好驾驶习惯";
		}
		if (s > 1.0) {
			return "油耗正常,请保持良好驾驶习惯";
		}
		return "油耗低,请保持良好驾驶习惯";
	}

	/**
	 * 驾驶建议
	 * 
	 * @param dit
	 * @return
	 */
	public String getDriveDes(KafkaTravelData dit) {
		Integer x = dit.get_this_fastup_count() + dit.get_this_fastlow_count();
		if (x > 30) {
			return "驾驶习惯急待改进";
		}
		if (x > 10) {
			return "注意不良驾驶习惯";
		}
		return "驾驶习惯良好";
	}

	public int getElectricFenceJudge(double x, double y, String user_id) {
		String url = electric_fence_judge + x + "," + y + "&useId=" + user_id;
		logger.info("验证是否在电子围栏内" + url);
		JSONObject json = getJsonFromUrl(url);
		logger.info("json is {}", json);
		// 如果用户没有设置围栏或者不再围栏内，status=602,在围栏内,status=200
		if (json != null && json.getInt("status") == 200) {
			return 1;
		}

		if (json != null && json.getInt("status") == 602) {
			JSONObject fence = json.getJSONObject("fence");
			if (fence.getInt("count") == 0) {
				return 1;
			} else {
				return 0;
			}
		}
		return 1;
	}

	/**
	 * 获取体检值的显示颜色
	 */
	public String getCheckValueColour(String value, double max, double min) {
		String colour = normal_value;
		if (value.matches("\\d+.\\d+")) {
			double d = Double.parseDouble(value);
			if (d > max) {
				colour = max_value;
			}
			if (d < min) {
				colour = min_value;
			}
		} else if (value.matches("\\d+")) {
			int d = Integer.parseInt(value);
			if (d > max) {
				colour = max_value;
			}
			if (d < min) {
				colour = min_value;
			}
		}
		return colour;
	}

	public double getCheckScoure(double check_scoure, String value, double max, double min, double item_scoure) {
		if (value.matches("\\d+.\\d+")) {
			double d = Double.parseDouble(value);
			if (d > max) {
				check_scoure = check_scoure - item_scoure;
			}
			if (d < min) {
				check_scoure = check_scoure - item_scoure;
			}
		} else if (value.matches("\\d+")) {
			int d = Integer.parseInt(value);
			if (d > max) {
				check_scoure = check_scoure - item_scoure;
			}
			if (d < min) {
				check_scoure = check_scoure - item_scoure;
			}
		}
		return check_scoure;
	}

	/**
	 * 验证请求参数是否为""
	 * 
	 * @param objects
	 * @return
	 */
	public boolean checkRequestValue(Object[] objects) {
		for (int i = 0; i < objects.length; i++) {
			if (objects[i].equals("")) {
				return false;
			}
		}
		return true;
	}

}
