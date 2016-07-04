package com.mapbar.netobd.web.test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import org.junit.Test;

import com.mapbar.netobd.kafka.test.KafkaProduceTest;

import net.sf.json.JSONObject;

public class WebTestMain {

	static String user_id = "56ceab39150ca0d365000013";
	static String car_id = "0ea93f629324816e628c15820fef9025";
	static String car_code = "京LFF696";
	static String car_model_id = "52d3e9d00a36483d2ceeb978";
	static String sn = "311411003043c";
	static String product = "ios";
	static String vin = "LFPM4ACC1B1E1FFFF";
	static String travel_month = "201602";

	@Test
	public void test() {

		// opetData();
		// opetData2();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		System.out.println(sdf.format(new Date(1460468490000L)));

		String token = "O7IGCce/BTE9qPHUfQOXcxJhESSQm+CVBRFLTASNgRAaB/RrTOVZOZz7IxQZLYy1";

		try {
			for (int i = 0; i < 1; i++) {
				// new test(token).testOilArea();//获取油价城市列表
				// new test(token).testGetOilFromArea("北京");//获取指定城市的油价
				// new test(token).testSetUserVehicle("56cea36a150ca0d365000011", "car_code" + i);//设置车辆档案
				// new test(token).testGetUserInfo("55e5a1aa162eb1f906000192");//获取用户车辆档案 user_id
				// new test(token).testSetUserBindDrive("55e5a1aa162eb1f906000192");//设置用户设备绑定
				// new test(token).testRemoveSnBinding("55e5a1aa162eb1f906000192", "8366020084045");//解绑SN
				// new test(token).testSetUserPushBind("b6d539d1-5628-4788-a090-094df827447a");//设置推送绑定
				// new test(token).testSetPushRemind("b6d539d1-5628-4788-a090-094df827447a");//设置推送提醒
				// new test(token).testGetPushRemind("b6d539d1-5628-4788-a090-094df827447a");//获取推送提醒
				// new test(token).testGetMonthTrip("56ebcd458be202e81400000e", "201602");//获取指定月份的行程记录
				// new test(token).testGetDayTravelList("56ebcd458be202e81400000e", "20160201");//获取指定天行程列表
				// new test(token).testGetNewestTravel("55e5a1aa162eb1f906000192", "");//获取最新一条行程
				// new test(token).testGetTravelFromId("a296446b-64c6-4066-91de-79439f6bba81");//根据行程数据ID获取行程数据
				new WebTest(token).testGetCheckInfo("570b5bd4f5b5485121000b23");// 获取体检数据
				// new test(token).testGetCarCondition("54657482b7b61d6237001daf");//获取车况信息
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// try {
		// BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("e:/122228.txt"))));
		// while(br.ready()){
		// String sn = br.readLine();
		// Thread t = new Create(sn);
		// t.start();
		//
		// while(Thread.activeCount() > 5){
		// Thread.sleep(3000);
		// continue;
		// }
		//
		// }
		// br.close();
		// } catch (FileNotFoundException e) {
		// e.printStackTrace();
		// } catch (IOException e) {
		// e.printStackTrace();
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
	}

	public static void opetData() {
		int x = 1;
		String file = "D:/60-OBD/数据导出/轨迹点数据.json";
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			while (br.ready()) {
				String line = br.readLine();
				JSONObject json = JSONObject.fromObject(line);

				Iterator<?> it = json.keys();

				StringBuffer sb = new StringBuffer();
				StringBuffer sb2 = new StringBuffer();
				while (it.hasNext()) {
					String key = it.next().toString();
					if (x == 1) {
						sb2.append(key + ",");
					}
					sb.append(json.get(key).toString().replaceAll("\r\r", "").replaceAll("\r\n", "").replaceAll("\r", "").replaceAll("\n", "").replace(",", "，") + ",");
				}

				if (x == 1) {
					writerToFile(sb2.toString(), "D:/60-OBD/数据导出/轨迹点数据.csv");
				}
				x++;
				writerToFile(sb.toString(), "D:/60-OBD/数据导出/轨迹点数据.csv");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void opetData2() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		int x = 1;
		String file = "D:/60-OBD/数据导出/瞬时数据.json";
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			while (br.ready()) {
				String line = br.readLine();
				JSONObject json = JSONObject.fromObject(line);

				Iterator<?> it = json.keys();

				StringBuffer sb = new StringBuffer();
				StringBuffer sb2 = new StringBuffer();
				while (it.hasNext()) {
					String key = it.next().toString();
					if (x == 1) {
						sb2.append(key + ",");
					}
					String value = json.get(key).toString().replaceAll("\r\r", "").replaceAll("\r\n", "").replaceAll("\r", "").replaceAll("\n", "").replace(",", "，") + ",";
					if (key.equals("_time")) {
						JSONObject a = JSONObject.fromObject(value.replace(",", ""));
						value = sdf.format(new Date(Long.parseLong(a.getString("$numberLong")))) + ",";
					}

					if (key.equals("_drive_tag")) {
						JSONObject a = JSONObject.fromObject(value.replace(",", ""));
						value = sdf.format(new Date(Long.parseLong(a.getString("$numberLong")))) + ",";
					}

					if (key.equals("_upload_time")) {
						JSONObject a = JSONObject.fromObject(value.replace(",", ""));
						value = sdf.format(new Date(Long.parseLong(a.getString("$numberLong")))) + ",";
					}

					sb.append(value);
				}

				if (x == 1) {
					writerToFile(sb2.toString(), "D:/60-OBD/数据导出/瞬时数据.csv");
				}
				x++;
				writerToFile(sb.toString(), "D:/60-OBD/数据导出/瞬时数据.csv");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void writerToFile(String line, String filename) {
		try {
			FileOutputStream fos = new FileOutputStream(filename, true);
			Writer out = new OutputStreamWriter(fos, "GB2312");
			out.write(line + "\r\n");
			out.close();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

class Create extends Thread {
	String sn;

	public Create(String sn) {
		this.sn = sn;
	}

	public void run() {
		try {
			KafkaProduceTest.CreateTestData(sn);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
