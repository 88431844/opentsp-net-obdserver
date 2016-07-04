package com.mapbar.netobd.util.test;

import org.junit.Test;

import com.mapbar.netobd.units.VinUtil;

public class VinUtilTest {
	@Test
	public void test() {
		System.out.println(VinUtil.isLegal("1A1JC5444R7252367"));
	}
}
