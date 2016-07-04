package com.mapbar.netobd.util.test;

import org.junit.Test;

import com.mapbar.netobd.units.CustomUnits;

public class CustomUtilTest {
	/**
	 * 判断用户是否在电子围栏内
	 * 
	 * @param xy
	 * @param get_user_id
	 * @return
	 */
	@Test
	public void test01(String[] args) {
		new CustomUnits().getElectricFenceJudge(0.0, 0.0, "5555");
	}
}
