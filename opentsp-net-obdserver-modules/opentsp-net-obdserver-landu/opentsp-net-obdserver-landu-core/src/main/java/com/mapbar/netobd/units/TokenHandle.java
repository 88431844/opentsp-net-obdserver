package com.mapbar.netobd.units;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 
 * @author 喻龙 token验证
 */
@Component
public class TokenHandle {

	@Value("${token:true}")
	private boolean token;
	// 通过配置文件来初始化是否需要进行token验证
	private static boolean isToken;

	@PostConstruct
	public void init() {
		isToken = token;
	}

	/**
	 * 验证token
	 * 
	 * @param token
	 * @return
	 */
	public static int checkToken(String token) {
		if (token != null && !token.equals("")) {
			int count = 200;
			if (isToken) {
				count = TokenUtils.checkToken(token);
			}
			return count;
		}
		return 1401;
	}

	/**
	 * 获取m_guid
	 * 
	 * @param mck
	 * @return
	 */
	public static String getMguid(String mck) {
		// m_guid解析
		String uuid = "";
		if (mck != null && !mck.equals("m_guid=") && !mck.equals("")) {
			uuid = mck.split("=")[1];
			if (uuid.indexOf("m_guid") > 0) {
				uuid = uuid.replace(", m_guid", "");
			}
		}
		return uuid;
	}

}
