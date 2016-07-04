package com.mapbar.netobd.push;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

/**
 * @description: 爱心推送工具类
 * @author xubh
 * @date: 2016年4月29日
 */
@Component
public class TuiSong {

	private static final Logger logger = LoggerFactory.getLogger(TuiSong.class);

	// 查看是否推送成功url
	private static String ackInfoAndroid = "http://androidmis.ixintui.com:8001/getAckInfo";
	private static String ackInfoIos = "http://iosmis.ixintui.com:8001/getAckInfo";

	// 推送 url
	private static String pushUrlAndroid = "http://androidmis.ixintui.com:8001/push";
	private static String pushUrlIos = "http://iosmis.ixintui.com:8200/push";

	private static String ios_apiKey;
	private static String ios_secretKey;
	private static String android_apiKey;
	private static String android_secretKey;

	@Value("${ios_apiKey:1753235436}")
	public void setIos_apiKey(String ios_apiKey) {
		TuiSong.ios_apiKey = ios_apiKey;
	}

	@Value("${ios_secretKey:KDvq7EjGgxXf4WTnLDyzEwz6wCAZnMxWqAIf5FHRWnahcuOcRD2PNqq66CBuFAYi}")
	public void setIos_secretKey(String ios_secretKey) {
		TuiSong.ios_secretKey = ios_secretKey;
	}

	@Value("${android_apiKey:1196129778}")
	public void setAndroid_apiKey(String android_apiKey) {
		TuiSong.android_apiKey = android_apiKey;
	}

	@Value("${android_secretKey:Q3ZYy7SQ0nqIWhGZNfXmLwP7XhOllGa0XWuvkLgitBWL0hv7V2FJ706GVdlO27Ww}")
	public void setAndroid_secretKey(String android_secretKey) {
		TuiSong.android_secretKey = android_secretKey;
	}

	private String postJson(String url, String json) {
		HttpURLConnection con;
		try {
			// Create connection
			URL u = new URL(url);
			con = (HttpURLConnection) u.openConnection();
			con.setRequestMethod("POST");

			con.setDoInput(true);
			con.setDoOutput(true);
			con.setUseCaches(false);
			con.setRequestProperty("Content-Type", "application/json; charset=utf-8");
			byte[] jsonbytes = json.getBytes("UTF-8");
			con.setRequestProperty("Content-Length", "" + jsonbytes.length);
			// Create I/O streams
			OutputStream os = con.getOutputStream();
			DataOutputStream outStream = new DataOutputStream(os);
			// Post request
			outStream.write(jsonbytes);
			outStream.flush();
			outStream.close();

			int responseCode = con.getResponseCode();
			if (responseCode != 200) {
				return null;
			}
			return getResponse(con);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private static String getResponse(URLConnection con) {
		InputStream inStream;
		String json = null;
		try {
			// 从链接中获取一个输入流对象
			inStream = con.getInputStream();
			// 格式样例:{"desc": "OK","msgid": 51,"result": 0}
			json = inputStream2String(inStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json;
	}

	private static String inputStream2String(InputStream input) {
		StringBuffer sb = new StringBuffer();
		byte[] b = new byte[4096];
		try {
			for (int n; (n = input.read(b)) != -1;) {
				sb.append(new String(b, 0, n));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	private String sign(JSONObject json, String secretkey) {
		int len = json.size();
		String[] keys = new String[len];
		Iterator<?> it = json.keys();
		int index = 0;
		while (it.hasNext()) {
			keys[index++] = (String) it.next();
		}
		Arrays.sort(keys);
		String rtn = "{";
		try {
			Object o = null;
			for (int i = 0; i < len; i++) {
				o = json.get(keys[i]);
				if (o instanceof JSONArray) {
					JSONArray jsona = (JSONArray) o;
					rtn += "\"" + keys[i] + "\":[" + jsona.join(",") + "],";
				} else if (o instanceof String) {
					rtn += "\"" + keys[i] + "\":\"" + o.toString() + "\",";
				} else {
					rtn += "\"" + keys[i] + "\":" + o + ",";
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		String sign = signWithMd5(rtn.replaceFirst(",$", "}") + secretkey);
		rtn += "\"sign\":\"" + sign + "\"}";
		return rtn;
	}

	private byte[] getMd5SignedBytes(String input) {
		MessageDigest md5 = null;
		byte[] messageDigest = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
			md5.update(input.getBytes("UTF-8"));
			messageDigest = md5.digest();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return messageDigest;
	}

	private String signWithMd5(String input) {
		byte[] messageDigest = getMd5SignedBytes(input);
		return getString(messageDigest);
	}

	private String getString(byte[] messageDigest) {
		if (messageDigest == null) {
			return null;
		}
		// Create Hex String
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < messageDigest.length; i++) {
			String h = Integer.toHexString(0xFF & messageDigest[i]);
			while (h.length() < 2)
				h = "0" + h;
			hexString.append(h);
		}
		return hexString.toString();
	}

	/**
	 * 查看appkey下推送成功的token列表
	 * 
	 * @param pos
	 * @param iden
	 * @return
	 */
	public static String getQuery(String iden, String pos) {
		if (iden.equals("ios")) {
			String secretkey = ios_secretKey;// 1963160765 ios
			TuiSong ts = new TuiSong();
			JSONObject json = new JSONObject();
			try {
				json.put("appkey", ios_apiKey);
				json.put("pos", pos);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			String jsonstring = ts.sign(json, secretkey);
			String rtn = ts.sendBroadcast(ackInfoIos, jsonstring);
			return rtn;
		} else {
			String secretkey = android_secretKey;// 1471874614 android
			TuiSong ts = new TuiSong();
			JSONObject json = new JSONObject();
			try {
				json.put("appkey", android_apiKey);
				json.put("pos", pos);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			String jsonstring = ts.sign(json, secretkey);
			String rtn = ts.sendBroadcast(ackInfoAndroid, jsonstring);
			return rtn;
		}
	}

	/**
	 * 调用推送
	 * 
	 * @param str
	 *            推送的json字符串
	 * @return
	 */
	private String sendBroadcast(String url, String str) {
		return postJson(url, str);
	}

	/**
	 * Android推送方法
	 * 
	 * @param peccancy_info
	 * @param channel_id
	 * @param plate_number
	 * @return
	 */

	private static int IXTuiSongAndroid(String peccancy_info, String token, String iden) {
		// For example
		TuiSong ts = new TuiSong();

		/* 增加要推送的信息与配置 */
		JSONObject json = new JSONObject();
		try {
			json.put("appkey", android_apiKey);
			json.put("message", peccancy_info.split("，")[1]);
			json.put("period", 100000);// 不知道什么意思
			json.put("is_notif", 1);// 0为消息，1为通知
			// JSONArray jsona = new JSONArray();
			// jsona.put(111);
			// json.put("filter", jsona); // 组播
			json.put("token", token); // 点播
			json.put("title", peccancy_info.split("，")[0]);
			json.put("sound", false);
			json.put("vibrate", false);
			json.put("unremovable", false);
			json.put("click_action", "open_app");
			json.put("open_app", true);

			/**
			 * 自定义字段
			 */
			JSONObject addjson = new JSONObject();
			addjson.put("appStart", true);
			addjson.put("page", "NetRemindBox");
			addjson.put("sevTime", System.currentTimeMillis());
			json.put("extra", addjson.toString().replaceAll("\"", "\\\\\""));
		} catch (JSONException e) {
			e.printStackTrace();
		}

		String jsonstring = ts.sign(json, android_secretKey);// 增加sign字段
		String rtn = ts.sendBroadcast(pushUrlAndroid, jsonstring);// 调用推送返回json串
		logger.info(token + "," + iden + "," + json + "推送结果:\r" + rtn);
		/* 判断是否把信息发送到爱心推服务器 */
		JSONObject resJson = JSONObject.fromObject(rtn);

		return resJson.getInt("result");
	}

	/**
	 * ios推送方法
	 * 
	 * @param peccancy_info
	 * @param channel_id
	 * @param plate_number
	 * @return
	 */
	private static int IXTuiSongIos(String peccancy_info, String token, String iden) {
		// For example
		TuiSong ts = new TuiSong();

		/* 增加要推送的信息与配置 */
		JSONObject json = new JSONObject();
		try {
			json.put("appkey", ios_apiKey);
			json.put("message", peccancy_info.split("，")[1]);
			json.put("period", 100000);// 不知道什么意思
			json.put("is_notif", 1);// 0为消息，1为通知
			// JSONArray jsona = new JSONArray();
			// jsona.put(111);
			// json.put("filter", jsona); // 组播
			json.put("token", token); // 点播
			json.put("title", peccancy_info.split("，")[0]);
			json.put("sound", false);
			json.put("vibrate", false);
			json.put("unremovable", false);
			json.put("click_action", "open_app");
			json.put("open_app", true);

			JSONObject addjson = new JSONObject();
			addjson.put("actionType", 1);
			addjson.put("actionAddition", 101);
			addjson.put("sevTime", System.currentTimeMillis());
			json.put("extra", addjson.toString().replaceAll("\"", "\\\\\""));
		} catch (JSONException e) {
			e.printStackTrace();
		}

		String jsonstring = ts.sign(json, ios_secretKey);// 增加sign字段
		String rtn = ts.sendBroadcast(pushUrlIos, jsonstring);// 调用推送返回json串
		logger.info(token + "\t" + pushUrlIos + "\t" + iden + "推送结果:\r" + rtn);

		/* 判断是否把信息发送到爱心推服务器 */
		JSONObject resJson = JSONObject.fromObject(rtn);

		return resJson.getInt("result");
	}

	/**
	 * 选择推送方式
	 * 
	 * @return
	 */
	public int IXTuiSong(String iden, String info, String token) {
		int z = 0;
		/* 判断客户端类型 */
		if (iden.equals("android_online_obd")) {
			z = IXTuiSongAndroid(info, token, iden); /* 选择Android推送 */
		} else {
			z = IXTuiSongIos(info, token, iden); /* 选择ios推送 */
		}
		return z;
	}

}
