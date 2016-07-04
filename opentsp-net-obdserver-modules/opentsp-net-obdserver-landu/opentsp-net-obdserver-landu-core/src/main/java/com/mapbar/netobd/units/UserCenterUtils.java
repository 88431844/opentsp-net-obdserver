package com.mapbar.netobd.units;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mapbar.netobd.bean.dto.UserCenterInfo;

import net.sf.json.JSONObject;

@Component
public class UserCenterUtils {

	private static String usercenterurl;

	@Value("${usercenterurl:https://obduc.mapbar.com/user/info}")
	public void setUsercenterurl(String usercenterurl) {
		UserCenterUtils.usercenterurl = usercenterurl;
	}

	public static UserCenterInfo getUserInfo(String param) {
		UserCenterInfo info = new UserCenterInfo();
		HttpURLConnection conn = null;
		try {
			URL url = new URL(usercenterurl + "/" + param);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestProperty("token", "obd_server_user");
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
				String code = String.valueOf(obj.get("code"));
				if ("200".equals(code)) {
					String phone = String.valueOf(obj.getJSONObject("data").get("phone"));
					info.setCode("200");
					info.setPhone((phone == null || "".equals(phone)) ? "" : phone);

					String account = String.valueOf(obj.getJSONObject("data").get("account"));
					info.setAccount((account == null || "".equals(account)) ? "" : phone);
				} else if ("1401".equals(code)) {
					info.setCode("1401");
					info.setPhone("");
					info.setAccount("");
					// token为验证
				} else if ("1500".equals(code)) {
					info.setCode("1500");
					info.setPhone("");
					info.setAccount("");
				} else {
					info.setCode("1400");
					info.setPhone("");
					info.setAccount("");
				}
			} else {
				info.setCode("404");
				info.setPhone("");
				info.setAccount("");
			}
		} catch (IOException e) {
			info.setCode("1400");
			info.setPhone("");
			info.setAccount("");
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
		return info;
	}
}
