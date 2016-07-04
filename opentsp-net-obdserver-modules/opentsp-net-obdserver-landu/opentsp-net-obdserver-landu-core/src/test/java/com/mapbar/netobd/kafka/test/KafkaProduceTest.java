package com.mapbar.netobd.kafka.test;

import java.util.Properties;
import java.util.Random;
import java.util.UUID;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.Test;

import com.mapbar.netobd.configuration.KafkaConfiguration;
import com.mapbar.netobd.units.DateUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class KafkaProduceTest extends KafkaConfiguration {
	private final Producer<String, String> producer;
	public String topic;

	public KafkaProduceTest(String topic) {
		this.topic = topic;
		Properties props = getProcederProperties();
		this.producer = new KafkaProducer<>(props);
	}
	
	public void close() {
		producer.close();
	}

	public void flush() {
		producer.flush();
	}


	public synchronized void produce(String key, String value) {
		ProducerRecord<String, String> data = new ProducerRecord<String, String>(topic, key, value);
		this.producer.send(data);
	}

	/**
	 * { "all_param": [ { "code": 257, "content": "317" }, { "code": 74,
	 * "content": "EOBD" } ], "arry_length": 31, "obd_serial": "LANDU000009",
	 * "trip_id": "7", "upload_time": "2016-01-2215: 21: 35" }
	 * 
	 * @return
	 */

	public static String getDetailDriveInfo() {
		JSONObject json = new JSONObject();
		json.put("id", "");
		json.put("obd_serial", "311411003043c");
		json.put("trip_id", "84");

		JSONArray jsona = new JSONArray();
		JSONObject jsonp = new JSONObject();
		jsonp.put("code", 13);
		jsonp.put("content", "50");
		jsona.add(jsonp);
		jsonp.put("code", 0);
		jsonp.put("content", "12");
		jsona.add(jsonp);

		json.put("array_length", jsona.size());
		json.put("all_param", jsona);
		json.put("upload_time", System.currentTimeMillis());
		return json.toString();
	}

	/**
	 * landu_position_info-15-{ "locations": [ { "latitude": "N39.936509",
	 * "longitude": "E116.429694", "position_time": "2016-01-22 08:09:45" }, {
	 * "latitude": "N39.936495", "longitude": "E116.429571", "position_time":
	 * "2016-01-22 08:13:15" } ], "obd_serial": "BJTB0005", "position_count": 2,
	 * "trip_id": "15", "vin": "LFPM4ACC1B1E13630" }
	 * 
	 * @return
	 */
	static Random r = new Random();

	public static String getPositionInfo() throws InterruptedException {
		JSONObject json = new JSONObject();
		json.put("obd_serial", "311411003043c");
		json.put("trip_id", "13");
		json.put("vin", "LFPM4ACC1B1E13630");
		json.put("vid", "");

		JSONArray jsona = new JSONArray();
		for (int i = 0; i < 8; i++) {
			JSONObject jsonp = new JSONObject();
			jsonp.put("latitude", "N39." + (936138 + r.nextInt(10)));
			jsonp.put("longitude", "E116." + (429154 + r.nextInt(10)));
			jsonp.put("position_time", DateUtil.currentTime());
			jsonp.put("speed", 30 + r.nextInt(10));
			jsonp.put("drive_distance", "200");
			jsonp.put("direction_angle", "0");
			jsona.add(jsonp);
			Thread.sleep(200);
		}

		json.put("position_count", jsona.size());
		json.put("locations", jsona);

		return json.toString();
	}

	/**
	 * landu_vehicle_travel_start-84-{ "ignition_voltage": "8.9 ", "obd_serial":
	 * "BJTB0007 ", "start_check_time": "1970-01-01 00:00:02 ",
	 * "start_latitude": "E000.000000", "start_location_time":
	 * "1970-01-01 00:00:05", "start_location_type": "0 ", "start_longitude":
	 * "E000.000000", "start_operating_range": "28 ", "start_speed": "35 ",
	 * "trip_id": "84", "vid": " ", "vin": "1A1JC5444R7252367" }
	 * @param sn 
	 * 
	 * @return
	 */
	public static String getTravelState(String sn) {
		JSONObject json = new JSONObject();
		json.put("ignition_voltage", "8.9");
		json.put("obd_serial", sn);
		json.put("start_check_time", DateUtil.currentTime());
		json.put("start_latitude", "N39.936138");
		json.put("start_location_time", DateUtil.currentTime());
		json.put("start_location_type", "2");
		json.put("start_longitude", "E116.429154");
		json.put("start_operating_range", "28");
		json.put("start_speed", "0");
		json.put("trip_id", "13");
		json.put("vid", "05");
		json.put("vin", "LFPM4ACC1B1E13630");
		return json.toString();
	}

	/**
	 * 生成轨迹数据
	 * 
	 * @return
	 */
	public static String getTravelData(int i, String day, String sn) {
		JSONObject json = new JSONObject();
		json.put("obd_serial", sn);// OBD串号
		json.put("travel_id", i);// 行程ID
		json.put("vid", "05");// vind
		json.put("vin", "LGBH53E06FY043100");// VIN码
		// json.put("start_time", DateUnits.getNowStrDate());//行程开始时间
		// json.put("end_time", DateUnits.getNowStrDate() + 1000 * 60
		// *30);//行程结束时间
		json.put("start_time", day + " " + (0 + i) + ":00:00");// 行程开始时间
		json.put("end_time", day + " " + (0 + i) + ":30:00");// 行程结束时间
		json.put("travel_time", 1000 * 60 * 30);// 行程耗时
		json.put("start_lon", "E" + lon);// 开始经度
		json.put("start_lat", "N" + lat);// 开始纬度
		json.put("end_lon", "E116.450154");// 结束经度
		json.put("end_lat", "N39.993138");// 结束纬度
		json.put("this_travel_len", 30000);// 本次行程里程米
		json.put("this_avg_oil", 11);// 本次平均油耗
		json.put("avg_rage_speed", 40);// 平均速度
		json.put("max_speed", 90);// 最高速度

		// 生成speedDOList
		JSONArray jsona = new JSONArray();
		JSONObject jsons = new JSONObject();

		jsons.put("range", 0);// 距离小计
		jsons.put("speed", 0);// 设置速度
		jsons.put("sum_time", 600 + random2.nextInt(100));// 时间小计 秒
		jsona.add(jsons);

		jsons.put("range", 10000 + random2.nextInt(100));
		jsons.put("speed", 30);
		jsons.put("sum_time", 300 + random2.nextInt(100));
		jsona.add(jsons);

		jsons.put("range", 10000 + random2.nextInt(100));
		jsons.put("speed", 60);
		jsons.put("sum_time", 600 + random2.nextInt(100));
		jsona.add(jsons);

		jsons.put("range", 10000 + random2.nextInt(100));
		jsons.put("speed", 90);
		jsons.put("sum_time", 300 + random2.nextInt(100));
		jsona.add(jsons);

		jsons.put("range", random2.nextInt(100));
		jsons.put("speed", 120);
		jsons.put("sum_time", 200 + random2.nextInt(100));
		jsona.add(jsons);

		jsons.put("range", random2.nextInt(100));
		jsons.put("speed", 255);
		jsons.put("sum_time", 300 + random2.nextInt(100));
		jsona.add(jsons);

		json.put("speed_info", jsona);
		json.put("speed_sum", 1500);// 速度总个数
		json.put("speed_60", 200);// 速度60个数
		json.put("speed_90", 300);// 速度90个数
		json.put("speed_120", 250);// 速度120个数
		json.put("speed_121", 500);// 速度大于120的个数
		json.put("this_fastup_count", 5);// 急加速次数
		json.put("this_fastlow_count", 10);// 急减速次数
		json.put("this_crook_count", 7);// 急转弯次数
		json.put("this_idling_num", 250);// 怠速次数
		json.put("this_idling_time", 1000 * 60 * 7);// 怠速时长
		json.put("track_info", makeTrackInfo());// 轨迹信息
		json.put("sum_drive_range", 30000);// 累计行驶里程
		json.put("sum_avg_oil", 10);// 累加平均油耗
		return json.toString();
	}

	static Random random1 = new Random(1);
	static Random random2 = new Random();

	static double lon = 116.429154 + random1.nextDouble() / 100;
	static double lat = 39.936138 + random1.nextDouble() / 100;

	private static String makeTrackInfo() {
		JSONArray jsona = new JSONArray();

		JSONObject json = new JSONObject();
		double lon2 = lon;
		double lat2 = lat;

		for (int i = 0; i < 50; i++) {
			lon2 = lon2 + random1.nextDouble() / 1000;
			lat2 = lat2 + random1.nextDouble() / 1000;
			json.put("x", lon2);
			json.put("y", lat2);
			json.put("v", random2.nextInt(100));
			jsona.add(json);
		}
		return jsona.toString();
	}

	/**
	 * 生成体检数据
	 * 
	 * @param sn
	 * @return
	 */
	public static String getCheckInfo(int i, String sn) {
		
		String data = "{\"obd_serial\":\"8366020084045\",\"vin\":\"\",\"fault_code\":\"P1055\",\"control_voltage\":\"65.535\",\"coolant_temper\":\"86\",\"short_term\":\"-1.6\",\"long_term\":\"10.0\",\"oxygen_voltage\":\"\",\"engine_load\":\"5.1\",\"intake_perssure\":\"2550\",\"engine_rpm\":\"759\",\"ignition_angle\":\"8\",\"inlet_temper\":\"52\",\"relative_position\":\"100.0\",\"check_time\":\"2016-04-07 17:40:12\"}";
		return data;
		
//		
//		JSONObject json = new JSONObject();
//		json.put("obd_serial", sn);// obd串号3114110028746
//		json.put("vin", "LFPM4ACC1B1E13630");// vin码
//		json.put("fault_code", "P00" + 9);// 故障码
//		json.put("control_voltage", "15.3");// 控制模块电压
//		json.put("coolant_temper", 85);// 发动机冷冻液温度
//		json.put("short_term", 9);// 短期燃油调整
//		json.put("long_term", 15.0);// 长期燃油调整
//		json.put("oxygen_voltage", 0.7);// 氧传感器电压
//		json.put("engine_load", 31.8);// 计算发动机负荷值
//		json.put("intake_perssure", 41);// 进气管绝对压力
//		json.put("engine_rpm", 1200);// 发动机转数
//		json.put("ignition_angle", 6);// 点火正时提前角
//		json.put("inlet_temper", 60);// 进气温度
//		json.put("relative_position", 4);// 节气门相对位置
//		json.put("check_time", "2016-03-30 11:50:00");
//		return json.toString();
	}

	/**
	 * 生成车况数据
	 * 
	 * @param sn
	 * @return
	 */
	public static String getCarCondition(String sn) {
		JSONObject json = new JSONObject();
		json.put("obd_serial", sn);
		// if(2 == 1){
//		if (random2.nextInt(2) == 1) {
//			json.put("voltage", 10);
//			json.put("type", 1);
//		} else {
			json.put("x", 116.83529);
			json.put("y", 39.93833);
			json.put("v", 40);
			json.put("type", 0);
//		}
		return json.toString();
	}

	/**
	 * 生成碰撞提醒数据
	 * 
	 * @param sn
	 * @return
	 */
	public static String getCollisionAlert(String sn) {
		JSONObject json = new JSONObject();
		json.put("obd_serial", sn);// obd串号
		json.put("trip_id", 13);
		json.put("vid", "111111");
		json.put("vin", "2222222222222222");
		json.put("alarm_code", 3);// 1:新故障码报警，2：碰撞报警，3：防盗报警，4：水温报警，5：充电电压报警，240：拔下OBD报警
		json.put("speed", "40");
		json.put("drive_distance", "100");
		json.put("direction_angle", "50");
		json.put("latitude", "E113");
		json.put("longitude", "E39");
		json.put("position_time", "2016-02-29 14:22:00");
		json.put("position_type", "gps");
		json.put("unplug_obd_time", "P002,");
//		json.put("unplug_obd_time", "2016-02-29 14:22:00");
		json.put("upload_date", "2016-02-29");
		return json.toString();
	}

	public static void CreateTestData(String sn) throws Exception {
		
		 productTravelData(sn);

//		KafkaProceder proc = new KafkaProceder(PropertiesUtil.getPropertiesByKey("kafka.properties", "trip_state"));
//		KafkaProceder proc = new KafkaProceder(PropertiesUtil.getPropertiesByKey("kafka.properties", "check_info"));
//		KafkaProceder proc = new KafkaProceder(PropertiesUtil.getPropertiesByKey("kafka.properties", "car_condition"));
//		KafkaProceder proc = new KafkaProceder(PropertiesUtil.getPropertiesByKey("kafka.properties", "collision_alert"));
		for (int i = 1; i < 3; i++) {
			// 行程开始点火
//			proc.produce(UUID.randomUUID().toString(), getTravelState(sn));
			
			// 点信息
			// for (int j = 0; j < 200; j++) {
			// new KafkaProceder(PropertiesUtil.getPropertiesByKey(
			// "kafka.properties", "position_info")).produce(UUID
			// .randomUUID().toString(), getPositionInfo());

			// 体检数据
//			proc.produce(UUID.randomUUID().toString(), getCheckInfo(i, sn));
//			
			// 车况数据
//			proc.produce(UUID.randomUUID().toString(), getCarCondition(sn));

			// 碰撞提醒
//			proc.produce(UUID.randomUUID().toString(), getCollisionAlert(sn));
		}
		
//		proc.close();
	}

	public static void productTravelData(String sn) {
//		String[] days = { "2015-11-01", "2015-11-02", "2015-11-03",
//				"2015-11-04", "2015-11-06", "2015-11-11", "2015-11-12",
//				"2015-11-25", "2015-11-26", "2015-12-01", "2015-12-02",
//				"2015-12-05", "2015-12-06", "2015-12-07", "2015-12-08",
//				"2015-12-21", "2015-12-22", "2015-12-31", "2016-01-03",
//				"2016-01-04", "2016-01-05", "2016-01-06", "2016-01-11",
//				"2016-01-12", "2016-01-13", "2016-01-29", "2016-01-30",
//				"2016-02-03", "2016-02-04", "2016-02-05", "2016-02-06",
//				"2016-02-11", "2016-02-12", "2016-02-13", "2016-02-27",
//				"2016-02-29", "2016-03-01", "2016-03-02", "2016-03-03",
//				"2016-03-04", "2016-03-05", "2016-03-10", "2016-03-11",
//				"2016-03-12", "2016-03-13", "2016-03-15", "2016-03-16", 
//				"2016-03-19", "2016-03-20", "2016-03-21", "2016-03-21", "2016-03-22", "2016-03-23" };
		// String[] days = {"2016-03-30"};
		// 行程数据
		String str = "{\"obd_serial\":\"836602006663f\",\"vin\":\"\",\"vid\":\"020084045\",\"travel_id\":\"2\",\"start_time\":\"2016-04-20 17:35:41\",\"end_time\":\"2016-04-20 17:53:31\",\"travel_time\":1070000,\"start_lon\":\"E116.454498\",\"start_lat\":\"N39.95167\",\"end_lon\":\"E116.429859\",\"end_lat\":\"N39.936614\",\"this_travel_len\":4861,\"this_avg_oil\":1238,\"avg_rage_speed\":16.354767,\"max_speed\":49,\"speed_sum\":16,\"speed_60\":11,\"speed_90\":0,\"speed_120\":0,\"speed_121\":0,\"this_fastup_count\":0,\"this_fastlow_count\":0,\"this_crook_count\":0,\"this_idling_num\":5,\"this_idling_time\":0,\"sum_drive_range\":5,\"sum_avg_oil\":1238,\"speed_info\":[{\"range\":284,\"speed\":0,\"sum_time\":0},{\"range\":445,\"speed\":30,\"sum_time\":2198},{\"range\":259,\"speed\":60,\"sum_time\":2662},{\"range\":0,\"speed\":90,\"sum_time\":0},{\"range\":0,\"speed\":120,\"sum_time\":0},{\"range\":0,\"speed\":255,\"sum_time\":0}],\"track_info\":[{\"x\":116.4545,\"y\":39.95167,\"v\":0},{\"x\":116.454506,\"y\":39.95169,\"v\":0},{\"x\":116.45449,\"y\":39.95171,\"v\":0},{\"x\":116.4545,\"y\":39.951687,\"v\":0},{\"x\":116.45467,\"y\":39.951473,\"v\":22},{\"x\":116.45457,\"y\":39.951572,\"v\":17},{\"x\":116.455124,\"y\":39.950424,\"v\":26},{\"x\":116.45521,\"y\":39.949787,\"v\":26},{\"x\":116.45528,\"y\":39.94907,\"v\":23},{\"x\":116.45526,\"y\":39.948788,\"v\":2},{\"x\":116.455246,\"y\":39.94874,\"v\":0},{\"x\":116.45523,\"y\":39.94872,\"v\":0},{\"x\":116.45519,\"y\":39.9487,\"v\":0},{\"x\":116.45519,\"y\":39.9487,\"v\":0},{\"x\":116.455185,\"y\":39.948677,\"v\":0},{\"x\":116.45517,\"y\":39.94865,\"v\":0},{\"x\":116.45516,\"y\":39.94864,\"v\":0},{\"x\":116.455185,\"y\":39.948555,\"v\":11},{\"x\":116.455185,\"y\":39.948154,\"v\":21},{\"x\":116.45519,\"y\":39.947495,\"v\":28},{\"x\":116.45516,\"y\":39.94678,\"v\":31},{\"x\":116.45513,\"y\":39.946087,\"v\":31},{\"x\":116.45517,\"y\":39.94528,\"v\":35},{\"x\":116.45519,\"y\":39.944588,\"v\":36},{\"x\":116.4552,\"y\":39.943886,\"v\":31},{\"x\":116.455215,\"y\":39.943066,\"v\":27},{\"x\":116.455215,\"y\":39.942432,\"v\":24},{\"x\":116.45523,\"y\":39.94181,\"v\":18},{\"x\":116.455215,\"y\":39.941193,\"v\":27},{\"x\":116.45521,\"y\":39.940563,\"v\":17},{\"x\":116.45517,\"y\":39.940205,\"v\":13},{\"x\":116.45497,\"y\":39.940117,\"v\":23},{\"x\":116.453835,\"y\":39.940083,\"v\":39},{\"x\":116.45281,\"y\":39.940052,\"v\":42},{\"x\":116.451614,\"y\":39.94004,\"v\":38},{\"x\":116.450516,\"y\":39.94003,\"v\":42},{\"x\":116.44956,\"y\":39.940025,\"v\":42},{\"x\":116.44871,\"y\":39.940014,\"v\":47},{\"x\":116.44757,\"y\":39.939987,\"v\":46},{\"x\":116.4465,\"y\":39.939964,\"v\":34},{\"x\":116.44537,\"y\":39.939922,\"v\":28},{\"x\":116.44485,\"y\":39.93991,\"v\":4},{\"x\":116.44475,\"y\":39.939903,\"v\":1},{\"x\":116.44473,\"y\":39.939907,\"v\":0},{\"x\":116.44468,\"y\":39.93989,\"v\":0},{\"x\":116.444626,\"y\":39.939884,\"v\":7},{\"x\":116.44422,\"y\":39.93966,\"v\":24},{\"x\":116.44422,\"y\":39.93966,\"v\":24},{\"x\":116.44419,\"y\":39.938766,\"v\":34},{\"x\":116.44421,\"y\":39.937965,\"v\":43},{\"x\":116.444214,\"y\":39.937153,\"v\":39},{\"x\":116.44425,\"y\":39.936436,\"v\":36},{\"x\":116.44416,\"y\":39.935593,\"v\":24},{\"x\":116.44416,\"y\":39.93478,\"v\":34},{\"x\":116.444115,\"y\":39.934017,\"v\":16},{\"x\":116.44408,\"y\":39.933945,\"v\":0},{\"x\":116.44405,\"y\":39.93394,\"v\":0},{\"x\":116.44404,\"y\":39.933804,\"v\":12},{\"x\":116.444046,\"y\":39.93331,\"v\":16},{\"x\":116.44404,\"y\":39.932747,\"v\":21},{\"x\":116.443985,\"y\":39.932384,\"v\":7},{\"x\":116.443924,\"y\":39.932354,\"v\":15},{\"x\":116.442894,\"y\":39.93237,\"v\":36},{\"x\":116.44179,\"y\":39.932377,\"v\":36},{\"x\":116.440636,\"y\":39.93237,\"v\":40},{\"x\":116.439606,\"y\":39.932373,\"v\":38},{\"x\":116.43846,\"y\":39.93239,\"v\":37},{\"x\":116.43785,\"y\":39.932457,\"v\":2},{\"x\":116.437836,\"y\":39.932495,\"v\":0},{\"x\":116.43782,\"y\":39.932507,\"v\":0},{\"x\":116.43782,\"y\":39.932487,\"v\":0},{\"x\":116.43782,\"y\":39.932518,\"v\":0},{\"x\":116.437805,\"y\":39.9325,\"v\":0},{\"x\":116.43779,\"y\":39.932495,\"v\":0},{\"x\":116.437805,\"y\":39.932484,\"v\":0},{\"x\":116.43767,\"y\":39.932476,\"v\":11},{\"x\":116.43705,\"y\":39.932453,\"v\":34},{\"x\":116.43603,\"y\":39.932438,\"v\":40},{\"x\":116.43495,\"y\":39.93242,\"v\":40},{\"x\":116.43393,\"y\":39.9324,\"v\":37},{\"x\":116.43281,\"y\":39.9324,\"v\":38},{\"x\":116.431946,\"y\":39.932438,\"v\":15},{\"x\":116.43114,\"y\":39.93247,\"v\":33},{\"x\":116.43024,\"y\":39.93247,\"v\":35},{\"x\":116.42922,\"y\":39.932453,\"v\":25},{\"x\":116.42857,\"y\":39.93267,\"v\":20},{\"x\":116.42826,\"y\":39.933357,\"v\":32},{\"x\":116.42822,\"y\":39.934105,\"v\":26},{\"x\":116.4281,\"y\":39.93454,\"v\":19},{\"x\":116.42803,\"y\":39.935337,\"v\":35},{\"x\":116.42797,\"y\":39.936066,\"v\":18},{\"x\":116.427925,\"y\":39.93676,\"v\":29},{\"x\":116.42794,\"y\":39.93729,\"v\":10},{\"x\":116.42804,\"y\":39.93733,\"v\":11},{\"x\":116.42842,\"y\":39.937347,\"v\":15},{\"x\":116.428696,\"y\":39.93729,\"v\":10},{\"x\":116.42897,\"y\":39.937252,\"v\":8},{\"x\":116.42897,\"y\":39.937252,\"v\":8},{\"x\":116.42901,\"y\":39.936897,\"v\":4},{\"x\":116.42924,\"y\":39.936775,\"v\":0},{\"x\":116.42944,\"y\":39.93671,\"v\":0},{\"x\":116.429565,\"y\":39.936714,\"v\":0},{\"x\":116.42974,\"y\":39.93665,\"v\":0},{\"x\":116.42983,\"y\":39.936626,\"v\":0},{\"x\":116.42988,\"y\":39.93663,\"v\":0}]}";
		KafkaProduceTest proc5 = new KafkaProduceTest("obd_result_landu_travel_data2");
		proc5.produce(UUID.randomUUID().toString(), str);
		
		
//		for (int k = 0; k < days.length; k++) {
//			String day = days[k];
//			int x = random2.nextInt(5);
//			for (int i = 0; i < x; i++) {
//				proc5.produce(UUID.randomUUID().toString(), getTravelData(i, day, sn));
//			}
//		}

		proc5.close();
	}
   
	
	@Test
	public void test() {
		try {
//			KafkaProceder.CreateTestData("6135060003842");
			KafkaProduceTest.CreateTestData("836602006663f");
//			KafkaProceder.CreateTestData("8366020084045");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}