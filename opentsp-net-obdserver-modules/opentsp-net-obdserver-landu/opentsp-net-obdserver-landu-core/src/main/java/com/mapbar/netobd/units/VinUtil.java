package com.mapbar.netobd.units;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class VinUtil {

	public static Map<Character, Integer> kv = new HashMap<Character, Integer>();

	public static Map<Integer, Integer> wv = new HashMap<Integer, Integer>();

	public static Map<String, String> obd_type_map = new HashMap<String, String>();

	static {

		for (int i = 0; i < 10; i++) {
			kv.put(String.valueOf(i).charAt(0), i);
		}

		kv.put('a', 1);
		kv.put('b', 2);
		kv.put('c', 3);
		kv.put('d', 4);
		kv.put('e', 5);
		kv.put('f', 6);
		kv.put('g', 7);
		kv.put('h', 8);
		kv.put('j', 1);
		kv.put('k', 2);
		kv.put('l', 3);
		kv.put('m', 4);
		kv.put('n', 5);
		kv.put('p', 7);
		kv.put('q', 8);
		kv.put('r', 9);
		kv.put('s', 2);
		kv.put('t', 3);
		kv.put('u', 4);
		kv.put('v', 5);
		kv.put('w', 6);
		kv.put('x', 7);
		kv.put('y', 8);
		kv.put('z', 9);

		wv.put(1, 8);
		wv.put(2, 7);
		wv.put(3, 6);
		wv.put(4, 5);
		wv.put(5, 4);
		wv.put(6, 3);
		wv.put(7, 2);
		wv.put(8, 10);
		wv.put(10, 9);
		wv.put(11, 8);
		wv.put(12, 7);
		wv.put(13, 6);
		wv.put(14, 5);
		wv.put(15, 4);
		wv.put(16, 3);
		wv.put(17, 2);

	}

	public final static boolean isLegal(String vin) {
		if (null == vin) {
			return false;
		} else if (vin.trim().length() == 17) {
			vin = vin.trim().toLowerCase();
			char[] codes = vin.toCharArray();

			/**
			 * if the 9th is not number ,return false
			 */
			int resultInCode = 0;
			if ("0123456789x".contains(vin.subSequence(8, 9))) {
				if (vin.subSequence(8, 9).toString().equals("x")) {
					resultInCode = 10;
				} else {
					resultInCode = Integer.valueOf(vin.subSequence(8, 9).toString());
				}
			} else {
				return false;
			}

			int total = 0;
			for (int i = 1; i < codes.length + 1; i++) {
				char code = codes[i - 1];

				if (kv.containsKey(code)) {
					if (9 == i) {
						continue;
					} else {
						total += kv.get(code) * wv.get(i);
					}
				} else {
					return false;
				}
			}
			return resultInCode == total % 11;
		} else {
			return false;
		}
	}

	/**
	 * 验证VIN是否被绑定 如果
	 */
	public static Integer checkVinTrue(List<Map<String, Object>> userList, String vin, String user_id) {
		if (userList.size() == 0) {
			return 0;
		}

		// 判断查到用户是否以前绑定过VIN，如果是允许绑定，如果不是不能绑定
		for (int i = 0; i < userList.size(); i++) {
			Map<String, Object> map = userList.get(i);
			if (map.get("_user_id").equals(user_id)) {
				return 0;
			}
		}
		return 2;
	}

}