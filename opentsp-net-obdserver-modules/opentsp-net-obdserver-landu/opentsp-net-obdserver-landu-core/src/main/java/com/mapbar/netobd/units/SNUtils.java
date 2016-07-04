package com.mapbar.netobd.units;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import net.sf.json.JSONObject;
@Component
public class SNUtils {

	private static String snQueryUtil;

	@Value("${snQueryUtil:http://obdms.mapbar.com/obd_sn/obd/device/sn/getSnInfo?}")
	public String getSnQueryUtil() {
		return snQueryUtil;
	}

	public static JSONObject getSnInfo(String snCode) {
		HttpURLConnection conn = null;
		JSONObject obj = null;
		try {
			URL url = new URL(snQueryUtil + "snCode=" + snCode);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestProperty("Connection", "close");
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(5000);
			conn.connect();
			int stateCode;
			stateCode = conn.getResponseCode();
			if (stateCode == 200) {
				StringBuffer sb = new StringBuffer("");
				BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
				while (reader.ready()) {
					sb.append(reader.readLine() + "\r\n");
				}
				reader.close();
				obj = JSONObject.fromObject(sb.toString());
				return obj;
			} else {
				obj = new JSONObject();
				obj.put("code", "500");
				return obj;
			}
		} catch (IOException e) {
			e.printStackTrace();
			obj = new JSONObject();
			obj.put("code", "400");
			return obj;
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
	}

}
