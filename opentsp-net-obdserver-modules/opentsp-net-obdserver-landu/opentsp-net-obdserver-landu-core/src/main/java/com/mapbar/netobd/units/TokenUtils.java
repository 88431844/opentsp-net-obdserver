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
public class TokenUtils {

	private static String tokenUrl;

	@Value("${tokenurl:https://obduc.mapbar.com/user/auth_lock/android_obd}")
	public void setTokenUrl(String tokenUrl) {
		TokenUtils.tokenUrl = tokenUrl;
	}

	public static int checkToken(String token) {
		HttpURLConnection conn = null;
		try {
			if (token == null || "".equals(token))
				return 1080;
			token = token.replace(" ", "+");
			URL url = new URL(tokenUrl);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestProperty("user_token", token);
			conn.setRequestProperty("Connection", "close");
			conn.connect();
			int stateCode = conn.getResponseCode();
			if (stateCode == 200) {
				StringBuffer sb = new StringBuffer("");
				BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				while (reader.ready()) {
					sb.append(reader.readLine() + "\r\n");
				}
				reader.close();
				JSONObject obj = JSONObject.fromObject(sb.toString());
				if (String.valueOf(obj.get("code")).equals("200")) {
					return 200;
				} else {
					return 1080;
				}
			} else {
				return stateCode;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return 500;
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
	}
}
