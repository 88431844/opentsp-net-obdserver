package com.mapbar.netobd.web.test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;
import java.util.UUID;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.InputGetCheckInfo;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.InputGetConditionInfo;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.InputGetDayTravel;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.InputGetMonthTravel;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.InputGetNowestTravel;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.InputGetOilPrice;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.InputGetPushRemind;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.InputGetTravelDesc;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.InputGetVehicleFile;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.InputRemoveSnBinding;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.InputSetBindDrive;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.InputSetPushBind;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.InputSetPushRemind;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.InputSetVehicleFile;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.OutputGetCheckInfo;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.OutputGetConditionInfo;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.OutputGetDayTravel;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.OutputGetMonthTravel;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.OutputGetNowestTravel;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.OutputGetOilPrice;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.OutputGetPushRemind;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.OutputGetTravelDesc;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.OutputGetVehicleFile;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.OutputRemoveSnBinding;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.OutputSetBindDrive;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.OutputSetPushBind;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.OutputSetPushRemind;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.OutputSetVehicleFile;

import security.OBD2Security;

public class WebTest {
	// static String www = "http://10.10.23.95:8008/Ne_obd_server";
	// static String www = "http://192.168.85.29:8010";
	// public static String www = "http://119.255.37.167:8010";
	// public static String www = "http://116.213.115.109:8021";
	public static String www = "http://obdgsm.mapbar.com/t_services";
	public static String baseUri = www + "/neobd/userInfo/setCarInfo";
	public static String oilAreaUrl = www + "/neobd/baseInfo/getArealist";
	public static String getOilUrl = www + "/neobd/baseInfo/getOilFromArea";
	public static String getUserInfo = www + "/neobd/userInfo/getVehicleFile";
	public static String setUserVehicle = www + "/neobd/userInfo/setVehicleFile";
	public static String setUserBindDrive = www + "/neobd/userInfo/setUserBindDrive";
	public static String setUserPushBind = www + "/neobd/push/setPushBind";
	public static String getPushRemind = www + "/neobd/push/getPushRemind";
	public static String setPushRemind = www + "/neobd/push/setPushRemind";
	public static String getMonthTrip = www + "/neobd/drive/getMonthTrip";
	public static String removeSnBinding = www + "/neobd/userInfo/removeSnBinding";
	public static String getNowestTravel = www + "/neobd/drive/getNewestTravel";
	public static String getDayTravel = www + "/neobd/drive/getDayTravel";
	public static String getTravelFromId = www + "/neobd/drive/getTravelFromId";
	public static String getCheckInfo = www + "/neobd/check/getCheckInfo";
	public static String getCondition = www + "/neobd/CarCondition/getCarCondition";

	public static String car_id = "b46438faf90dd205c0065a1611ee9eab";
	public static String product = "ios";

	String token;

	public WebTest(String token) {
		this.token = token;
	}

	public void testGetCarCondition(String user_id) throws ClientProtocolException, IOException {
		System.out.println(getCondition);
		try {
			InputGetConditionInfo info = InputGetConditionInfo.newBuilder().setCarId(car_id).setUserId(user_id).build();

			byte[] b = OBD2Security.OBDEncode(info.toByteArray());

			URL url = new URL(getCondition);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestMethod("POST"); // 设置请求方式
			connection.setRequestProperty("Accept", "application/x-protobuf"); // 设置接收数据的格式
			connection.setRequestProperty("Content-Type", "application/x-protobuf"); // 设置发送数据的格式
			connection.setRequestProperty("Access-Control-Allow-Origin", "*"); // 跨域
			connection.setRequestProperty("token", token);
			connection.setRequestProperty("mck", "m_guid=aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

			connection.connect();
			OutputStream out = connection.getOutputStream();
			out.write(b, 0, b.length);
			out.flush();
			out.close();

			int stateCode = connection.getResponseCode();
			System.out.println("==============" + stateCode);
			if (stateCode == 200) {
				byte[] x = getByte(connection.getInputStream());
				OutputGetConditionInfo rInfo = OutputGetConditionInfo.parseFrom(x);
				System.out.println(rInfo.getCode());
				System.out.println(new String(rInfo.getMsg()));
				System.out.println(rInfo.getAllFields());
			}
			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void testGetCheckInfo(String user_id) throws ClientProtocolException, IOException {
		System.out.println(getCheckInfo);
		try {
			InputGetCheckInfo info = InputGetCheckInfo.newBuilder().setCarId(car_id).setUserId(user_id).build();

			byte[] b = OBD2Security.OBDEncode(info.toByteArray());

			URL url = new URL(getCheckInfo);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestMethod("POST"); // 设置请求方式
			connection.setRequestProperty("Accept", "application/x-protobuf"); // 设置接收数据的格式
			connection.setRequestProperty("Content-Type", "application/x-protobuf"); // 设置发送数据的格式
			connection.setRequestProperty("Access-Control-Allow-Origin", "*"); // 跨域
			connection.setRequestProperty("token", token);
			connection.setRequestProperty("mck", "m_guid=aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

			connection.connect();
			OutputStream out = connection.getOutputStream();
			out.write(b, 0, b.length);
			out.flush();
			out.close();

			int stateCode = connection.getResponseCode();
			System.out.println("==============" + stateCode);
			if (stateCode == 200) {
				byte[] x = getByte(connection.getInputStream());
				OutputGetCheckInfo rInfo = OutputGetCheckInfo.parseFrom(x);
				System.out.println(rInfo.getCode());
				System.out.println(rInfo.getCheckTime());

				System.out.println(new String(rInfo.getMsg()));
				System.out.println(new String(rInfo.getCheckInfo()));
			}
			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testGetTravelFromId(String id) throws ClientProtocolException, IOException {
		System.out.println(getTravelFromId);
		try {
			InputGetTravelDesc info = InputGetTravelDesc.newBuilder().setTravelId(id).build();

			byte[] b = OBD2Security.OBDEncode(info.toByteArray());

			URL url = new URL(getTravelFromId);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestMethod("POST"); // 设置请求方式
			connection.setRequestProperty("Accept", "application/x-protobuf"); // 设置接收数据的格式
			connection.setRequestProperty("Content-Type", "application/x-protobuf"); // 设置发送数据的格式
			connection.setRequestProperty("Access-Control-Allow-Origin", "*"); // 跨域
			connection.setRequestProperty("token", token);
			connection.setRequestProperty("mck", "m_guid=aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

			connection.connect();
			OutputStream out = connection.getOutputStream();
			out.write(b, 0, b.length);
			out.flush();
			out.close();

			int stateCode = connection.getResponseCode();
			System.out.println("==============" + stateCode);
			if (stateCode == 200) {
				byte[] x = getByte(connection.getInputStream());
				OutputGetTravelDesc rInfo = OutputGetTravelDesc.parseFrom(x);
				System.out.println(rInfo.getCode());
				System.out.println(new String(rInfo.getMsg()));
				System.out.println(rInfo.getAllFields());
			}
			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testGetDayTravelList(String user_id, String day) throws ClientProtocolException, IOException {
		System.out.println(getDayTravel);
		try {
			InputGetDayTravel info = InputGetDayTravel.newBuilder().setCarId(car_id).setUserId(user_id).setTravelDay(day).build();

			byte[] b = OBD2Security.OBDEncode(info.toByteArray());

			URL url = new URL(getDayTravel);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestMethod("POST"); // 设置请求方式
			connection.setRequestProperty("Accept", "application/x-protobuf"); // 设置接收数据的格式
			connection.setRequestProperty("Content-Type", "application/x-protobuf"); // 设置发送数据的格式
			connection.setRequestProperty("Access-Control-Allow-Origin", "*"); // 跨域
			connection.setRequestProperty("token", token);
			connection.setRequestProperty("mck", "m_guid=aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

			connection.connect();
			OutputStream out = connection.getOutputStream();
			out.write(b, 0, b.length);
			out.flush();
			out.close();

			int stateCode = connection.getResponseCode();
			System.out.println("==============" + stateCode);
			if (stateCode == 200) {
				byte[] x = getByte(connection.getInputStream());
				OutputGetDayTravel rInfo = OutputGetDayTravel.parseFrom(x);
				System.out.println(rInfo.getCode());
				System.out.println(new String(rInfo.getMsg()));
				System.out.println(rInfo.getAllFields());
			}
			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void testGetNewestTravel(String user_id, String sn) throws ClientProtocolException, IOException {
		System.out.println(getNowestTravel);
		try {
			InputGetNowestTravel info = InputGetNowestTravel.newBuilder().setCarId(car_id).setUserId(user_id).setSn(sn).build();

			byte[] b = OBD2Security.OBDEncode(info.toByteArray());

			URL url = new URL(getNowestTravel);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestMethod("POST"); // 设置请求方式
			connection.setRequestProperty("Accept", "application/x-protobuf"); // 设置接收数据的格式
			connection.setRequestProperty("Content-Type", "application/x-protobuf"); // 设置发送数据的格式
			connection.setRequestProperty("Access-Control-Allow-Origin", "*"); // 跨域
			connection.setRequestProperty("token", token);
			connection.setRequestProperty("mck", "m_guid=aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

			connection.connect();
			OutputStream out = connection.getOutputStream();
			out.write(b, 0, b.length);
			out.flush();
			out.close();

			int stateCode = connection.getResponseCode();
			System.out.println("==============" + stateCode);
			if (stateCode == 200) {
				byte[] x = getByte(connection.getInputStream());
				OutputGetNowestTravel rInfo = OutputGetNowestTravel.parseFrom(x);
				System.out.println(rInfo.getCode());
				System.out.println(new String(rInfo.getMsg()));
				System.out.println(rInfo.getAllFields());
			}
			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testRemoveSnBinding(String user_id, String sn) throws ClientProtocolException, IOException {
		System.out.println(removeSnBinding);
		try {
			InputRemoveSnBinding info = InputRemoveSnBinding.newBuilder().setCarId(car_id).setUserId(user_id).setSn(sn).build();

			byte[] b = OBD2Security.OBDEncode(info.toByteArray());

			URL url = new URL(removeSnBinding);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestMethod("POST"); // 设置请求方式
			connection.setRequestProperty("Accept", "application/x-protobuf"); // 设置接收数据的格式
			connection.setRequestProperty("Content-Type", "application/x-protobuf"); // 设置发送数据的格式
			connection.setRequestProperty("Access-Control-Allow-Origin", "*"); // 跨域
			connection.setRequestProperty("token", token);
			connection.setRequestProperty("mck", "m_guid=aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

			connection.connect();
			OutputStream out = connection.getOutputStream();
			out.write(b, 0, b.length);
			out.flush();
			out.close();

			int stateCode = connection.getResponseCode();
			System.out.println("==============" + stateCode);
			if (stateCode == 200) {
				byte[] x = getByte(connection.getInputStream());
				OutputRemoveSnBinding rInfo = OutputRemoveSnBinding.parseFrom(x);
				System.out.println(rInfo.getCode());
				System.out.println(new String(rInfo.getMsg()));
				System.out.println(rInfo.getAllFields());
			}
			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testGetMonthTrip(String user_id, String month) throws ClientProtocolException, IOException {
		System.out.println(getMonthTrip);
		try {
			InputGetMonthTravel info = InputGetMonthTravel.newBuilder().setCarId(car_id).setUserId(user_id).setMonth(month).build();

			byte[] b = OBD2Security.OBDEncode(info.toByteArray());

			URL url = new URL(getMonthTrip);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestMethod("POST"); // 设置请求方式
			connection.setRequestProperty("Accept", "application/x-protobuf"); // 设置接收数据的格式
			connection.setRequestProperty("Content-Type", "application/x-protobuf"); // 设置发送数据的格式
			connection.setRequestProperty("Access-Control-Allow-Origin", "*"); // 跨域
			connection.setRequestProperty("token", token);
			connection.setRequestProperty("mck", "m_guid=aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

			connection.connect();
			OutputStream out = connection.getOutputStream();
			out.write(b, 0, b.length);
			out.flush();
			out.close();

			int stateCode = connection.getResponseCode();
			System.out.println("==============" + stateCode);
			if (stateCode == 200) {
				byte[] x = getByte(connection.getInputStream());
				OutputGetMonthTravel rInfo = OutputGetMonthTravel.parseFrom(x);
				System.out.println(rInfo.getCode());
				System.out.println(new String(rInfo.getMsg()));
				System.out.println(rInfo.getAllFields());
			}
			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	static Random r = new Random();

	public void testSetPushRemind(String user_id) throws ClientProtocolException, IOException {
		System.out.println(setPushRemind);
		try {
			InputSetPushRemind info = InputSetPushRemind.newBuilder().setCarId(car_id).setUserId(user_id).setOfflinePush(r.nextInt(2) - 1).setIgnitionPush(r.nextInt(2) - 1).setVoltagesPush(r.nextInt(2) - 1).setSetVoltages(12.2).setShockPush(r.nextInt(2) - 1).build();

			byte[] b = OBD2Security.OBDEncode(info.toByteArray());

			URL url = new URL(setPushRemind);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestMethod("POST"); // 设置请求方式
			connection.setRequestProperty("Accept", "application/x-protobuf"); // 设置接收数据的格式
			connection.setRequestProperty("Content-Type", "application/x-protobuf"); // 设置发送数据的格式
			connection.setRequestProperty("Access-Control-Allow-Origin", "*"); // 跨域
			connection.setRequestProperty("token", token);
			connection.setRequestProperty("mck", "m_guid=aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

			connection.connect();
			OutputStream out = connection.getOutputStream();
			out.write(b, 0, b.length);
			out.flush();
			out.close();

			int stateCode = connection.getResponseCode();
			System.out.println("==============" + stateCode);
			if (stateCode == 200) {
				byte[] x = getByte(connection.getInputStream());
				OutputSetPushRemind rInfo = OutputSetPushRemind.parseFrom(x);
				System.out.println(rInfo.getCode());
				System.out.println(new String(rInfo.getMsg()));
			}
			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testGetPushRemind(String user_id) throws IOException {
		System.out.println(getPushRemind);
		try {
			InputGetPushRemind info = InputGetPushRemind.newBuilder().setCarId(car_id).setUserId(user_id).build();

			byte[] b = OBD2Security.OBDEncode(info.toByteArray());

			URL url = new URL(getPushRemind);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestMethod("POST"); // 设置请求方式
			connection.setRequestProperty("Accept", "application/x-protobuf"); // 设置接收数据的格式
			connection.setRequestProperty("Content-Type", "application/x-protobuf"); // 设置发送数据的格式
			connection.setRequestProperty("Access-Control-Allow-Origin", "*"); // 跨域
			connection.setRequestProperty("token", token);
			connection.setRequestProperty("mck", "m_guid=aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

			connection.connect();
			OutputStream out = connection.getOutputStream();
			out.write(b, 0, b.length);
			out.flush();
			out.close();

			int stateCode = connection.getResponseCode();
			System.out.println("==============" + stateCode);
			if (stateCode == 200) {
				byte[] x = getByte(connection.getInputStream());
				OutputGetPushRemind rInfo = OutputGetPushRemind.parseFrom(x);
				System.out.println(rInfo.getCode());
				System.out.println(new String(rInfo.getMsg()));
			}
			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testSetUserPushBind(String user_id) throws ClientProtocolException, IOException {
		System.out.println(setUserPushBind);
		try {
			InputSetPushBind info = InputSetPushBind.newBuilder().setCarId(car_id).setUserId(user_id).setPushToken(UUID.randomUUID().toString()).setProduct("ios").build();

			byte[] b = OBD2Security.OBDEncode(info.toByteArray());

			URL url = new URL(setUserPushBind);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestMethod("POST"); // 设置请求方式
			connection.setRequestProperty("Accept", "application/x-protobuf"); // 设置接收数据的格式
			connection.setRequestProperty("Content-Type", "application/x-protobuf"); // 设置发送数据的格式
			connection.setRequestProperty("Access-Control-Allow-Origin", "*"); // 跨域
			connection.setRequestProperty("token", token);
			connection.setRequestProperty("mck", "m_guid=aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

			connection.connect();
			OutputStream out = connection.getOutputStream();
			out.write(b, 0, b.length);
			out.flush();
			out.close();

			int stateCode = connection.getResponseCode();
			System.out.println("==============" + stateCode);
			if (stateCode == 200) {
				byte[] x = getByte(connection.getInputStream());
				OutputSetPushBind rInfo = OutputSetPushBind.parseFrom(x);
				System.out.println(rInfo.getCode());
				System.out.println(new String(rInfo.getMsg()));
			}
			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testSetUserBindDrive(String user_id) throws ClientProtocolException, IOException {
		System.out.println(setUserBindDrive);
		try {
			InputSetBindDrive info = InputSetBindDrive.newBuilder().setCarId(car_id).setUserId(user_id).setSn("8366020084045").setProduct(product).build();

			byte[] b = OBD2Security.OBDEncode(info.toByteArray());

			URL url = new URL(setUserBindDrive);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestMethod("POST"); // 设置请求方式
			connection.setRequestProperty("Accept", "application/x-protobuf"); // 设置接收数据的格式
			connection.setRequestProperty("Content-Type", "application/x-protobuf"); // 设置发送数据的格式
			connection.setRequestProperty("Access-Control-Allow-Origin", "*"); // 跨域
			connection.setRequestProperty("token", token);
			connection.setRequestProperty("mck", "m_guid=aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

			connection.connect();
			OutputStream out = connection.getOutputStream();
			out.write(b, 0, b.length);
			out.flush();
			out.close();

			int stateCode = connection.getResponseCode();
			System.out.println("==============" + stateCode);
			if (stateCode == 200) {
				byte[] x = getByte(connection.getInputStream());
				OutputSetBindDrive rInfo = OutputSetBindDrive.parseFrom(x);
				System.out.println(rInfo.getCode());
				System.out.println(new String(rInfo.getMsg()));
			}
			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testSetUserVehicle(String car_model_id, String car_code) throws ClientProtocolException, IOException {
		System.out.println(setUserVehicle);
		try {
			InputSetVehicleFile info = InputSetVehicleFile.newBuilder().setCarId(car_id).setUserId(UUID.randomUUID().toString()).setCarModelId(car_model_id).setCarCode(car_code).setVin(UUID.randomUUID().toString()).setEngineNumber("6866465").setIsCustomeOil(0).setOilArea("北京").setOilType(92).setOilPrice(6.5).setProduct(product).build();

			byte[] b = OBD2Security.OBDEncode(info.toByteArray());

			URL url = new URL(setUserVehicle);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestMethod("POST"); // 设置请求方式
			connection.setRequestProperty("Accept", "application/x-protobuf"); // 设置接收数据的格式
			connection.setRequestProperty("Content-Type", "application/x-protobuf"); // 设置发送数据的格式
			connection.setRequestProperty("Access-Control-Allow-Origin", "*"); // 跨域
			connection.setRequestProperty("token", token);
			connection.setRequestProperty("mck", "m_guid=aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

			connection.connect();
			OutputStream out = connection.getOutputStream();
			out.write(b, 0, b.length);
			out.flush();
			out.close();

			int stateCode = connection.getResponseCode();
			System.out.println("==============" + stateCode);
			if (stateCode == 200) {
				byte[] x = getByte(connection.getInputStream());
				OutputSetVehicleFile rInfo = OutputSetVehicleFile.parseFrom(x);
				System.out.println(rInfo.getCarId());
			}
			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testGetUserInfo(String user_id) throws ClientProtocolException, IOException {
		System.out.println(getUserInfo);
		try {
			InputGetVehicleFile info = InputGetVehicleFile.newBuilder().setUserId(user_id).setCarId(car_id).build();

			byte[] b = OBD2Security.OBDEncode(info.toByteArray());

			URL url = new URL(getUserInfo);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestMethod("POST"); // 设置请求方式
			connection.setRequestProperty("Accept", "application/x-protobuf"); // 设置接收数据的格式
			connection.setRequestProperty("Content-Type", "application/x-protobuf"); // 设置发送数据的格式
			connection.setRequestProperty("Access-Control-Allow-Origin", "*"); // 跨域
			connection.setRequestProperty("token", token);
			connection.setRequestProperty("mck", "m_guid=aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

			connection.connect();
			OutputStream out = connection.getOutputStream();
			out.write(b, 0, b.length);
			out.flush();
			out.close();

			int stateCode = connection.getResponseCode();
			System.out.println("==============" + stateCode);
			if (stateCode == 200) {
				byte[] x = getByte(connection.getInputStream());
				OutputGetVehicleFile rInfo = OutputGetVehicleFile.parseFrom((x));
				System.out.println(rInfo.getAllFields());
			}
			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testGetOilFromArea(String city) throws ClientProtocolException, IOException {
		System.out.println(getOilUrl);
		try {
			InputGetOilPrice info = InputGetOilPrice.newBuilder().setCityName(city).build();

			byte[] b = OBD2Security.OBDEncode(info.toByteArray());

			URL url = new URL(getOilUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestMethod("POST"); // 设置请求方式
			connection.setRequestProperty("Accept", "application/x-protobuf"); // 设置接收数据的格式
			connection.setRequestProperty("Content-Type", "application/x-protobuf"); // 设置发送数据的格式
			connection.setRequestProperty("Access-Control-Allow-Origin", "*"); // 跨域
			connection.setRequestProperty("token", token);
			connection.setRequestProperty("mck", "m_guid=aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

			connection.connect();
			OutputStream out = connection.getOutputStream();
			out.write(b, 0, b.length);
			out.flush();
			out.close();

			int stateCode = connection.getResponseCode();
			System.out.println("==============" + stateCode);
			if (stateCode == 200) {
				byte[] x = getByte(connection.getInputStream());
				OutputGetOilPrice rInfo = OutputGetOilPrice.parseFrom((x));
				System.out.println(rInfo.getAllFields());
			}
			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void testOilArea() throws ClientProtocolException, IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(3000).build();// 设置请求和传输超时时间

		System.out.println(oilAreaUrl);
		HttpPost httpPost = new HttpPost(oilAreaUrl);
		httpPost.setConfig(requestConfig);

		// 这两行很重要的，是告诉springmvc客户端请求和响应的类型，指定application/x-protobuf类型,spring会用ProtobufHttpMessageConverter类来解析请求和响应的实体
		httpPost.addHeader("Content-Type", "application/x-protobuf");
		httpPost.addHeader("Accept", "application/x-protobuf");
		httpPost.addHeader("Access-Control-Allow-Origin", "*");
		httpPost.setHeader("token", "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
		httpPost.setHeader("mck", "m_guid=yyyyyyyyyyyyyyyyy");

		CloseableHttpResponse response2 = httpclient.execute(httpPost);

		System.out.println(response2.getStatusLine());
		HttpEntity entity2 = response2.getEntity();

		ByteArrayOutputStream buf = new ByteArrayOutputStream();
		entity2.writeTo(buf);

		buf.close();
		response2.close();
	}

	public static byte[] getByte(InputStream stream) {
		byte return_content[] = null;
		if (stream == null) {
			return return_content;
		}
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte bs[] = new byte[1024];
			int count = stream.read(bs);
			while (count != -1) {
				out.write(bs, 0, count);
				count = stream.read(bs);
			}
			byte content_byte[] = out.toByteArray();
			if (OBD2Security.isValid(content_byte)) {
				return_content = OBD2Security.OBDDecode(content_byte);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				stream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return return_content;
	}
}
