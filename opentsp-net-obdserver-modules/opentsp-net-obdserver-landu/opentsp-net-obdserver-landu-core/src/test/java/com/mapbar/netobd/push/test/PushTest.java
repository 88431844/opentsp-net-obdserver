package com.mapbar.netobd.push.test;

import org.junit.Test;

import com.mapbar.netobd.push.PushUnits;
import com.mapbar.netobd.push.TuiSong;

import net.sf.json.JSONObject;

public class PushTest {
	/**
	 * 电压提醒
	 * 
	 * @param json
	 * @param staticUserDao
	 * @throws Exception
	 */

	public void test01() {
		try {
			PushUnits.pushVoltage(JSONObject.fromObject("{\"obd_serial\":\"BJTB0005\",\"voltage\":14,\"type\":1}"), null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test02() {
		// new TuiSong().IXTuiSong("iOS_online_obd", "点火提醒测试，您的车辆已经点火1", "0a5de8c555b07108a5f11bc7ca5fbfe3dea22a17f1cc3236c638083d2ff80b16");
		new TuiSong().IXTuiSong("android_online_obd", "点火提醒，您的车辆已经点火", "7739360629839940736");
	}
}
