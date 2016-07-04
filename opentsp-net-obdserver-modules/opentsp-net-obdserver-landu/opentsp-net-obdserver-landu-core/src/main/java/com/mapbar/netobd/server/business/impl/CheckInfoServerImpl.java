package com.mapbar.netobd.server.business.impl;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mapbar.netobd.bean.dto.CheckInfoTo;
import com.mapbar.netobd.dao.CheckInfoDao;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.InputGetCheckInfo;
import com.mapbar.netobd.server.business.CheckInfoServer;
import com.mapbar.netobd.units.CustomUnits;

/**
 * @description: 体检信息服务层
 * @author xubh
 * @date: 2016年4月29日
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class CheckInfoServerImpl implements CheckInfoServer {

	@Autowired
	private CheckInfoDao checkInfoDao;
	@Autowired
	private CustomUnits customUnits;

	@Override
	public JSONObject findCheckInfo(InputGetCheckInfo info) throws Exception {
		CheckInfoTo checkInfoTo = checkInfoDao.findCheckInfo(info);
		if (checkInfoTo.get_id() == null) {
			return null;
		}

		double check_scoure = 100;

		JSONObject json = new JSONObject();

		json.put("carModelInfo", checkInfoTo.get_car_model_info());
		json.put("checkTime", checkInfoTo.get_check_time());

		JSONArray jsona = new JSONArray();
		// 故障码
		JSONObject json0 = new JSONObject();
		json0.put("item_title", "故障码");
		json0.put("item_refe", "0");

		if (checkInfoTo.get_fault_code() == null) {
			json0.put("item_explain", "");
		} else {
			json0.put("item_explain", checkInfoTo.get_fault_code());
		}
		json0.put("item_value", checkInfoTo.get_fault_code_num());
		if (!checkInfoTo.get_fault_code_num().equals("0")) {
			check_scoure = check_scoure - 20;
		}
		json0.put("colour", customUnits.getCheckValueColour(json0.getString("item_value"), 0, 0));
		jsona.add(json0);

		// if(!checkInfoTo.get_control_voltage().equals("0.0")){
		// jsona.add(getCheckInfoItemData("control_voltage", checkInfoTo.get_control_voltage()));
		// check_scoure = getCheckInfoScoure("control_voltage", check_scoure, checkInfoTo.get_control_voltage());
		// }

		if (!checkInfoTo.get_coolant_temper().equals("0.0")) {
			jsona.add(getCheckInfoItemData("coolant_temper", checkInfoTo.get_coolant_temper()));
			check_scoure = getCheckInfoScoure("coolant_temper", check_scoure, checkInfoTo.get_coolant_temper());
		}

		if (!checkInfoTo.get_short_term().equals("0.0")) {
			jsona.add(getCheckInfoItemData("short_term", checkInfoTo.get_short_term()));
			check_scoure = getCheckInfoScoure("short_term", check_scoure, checkInfoTo.get_short_term());
		}

		if (!checkInfoTo.get_long_term().equals("0.0")) {
			jsona.add(getCheckInfoItemData("long_term", checkInfoTo.get_long_term()));
			check_scoure = getCheckInfoScoure("long_term", check_scoure, checkInfoTo.get_long_term());
		}

		if (!checkInfoTo.get_oxygen_voltage().equals("0.0")) {
			jsona.add(getCheckInfoItemData("oxygen_voltage", checkInfoTo.get_oxygen_voltage()));
			check_scoure = getCheckInfoScoure("oxygen_voltage", check_scoure, checkInfoTo.get_oxygen_voltage());
		}

		if (!checkInfoTo.get_engine_load().equals("0.0")) {
			jsona.add(getCheckInfoItemData("engine_load", checkInfoTo.get_engine_load()));
			check_scoure = getCheckInfoScoure("engine_load", check_scoure, checkInfoTo.get_engine_load());
		}

		// if(!checkInfoTo.get_intake_perssure().equals("0.0")){
		// jsona.add(getCheckInfoItemData("intake_perssure", checkInfoTo.get_intake_perssure()));
		// check_scoure = getCheckInfoScoure("intake_perssure", check_scoure, checkInfoTo.get_intake_perssure());
		// }

		if (!checkInfoTo.get_engine_rpm().equals("0.0")) {
			jsona.add(getCheckInfoItemData("engine_rpm", checkInfoTo.get_engine_rpm()));
			check_scoure = getCheckInfoScoure("engine_rpm", check_scoure, checkInfoTo.get_engine_rpm());
		}

		if (!checkInfoTo.get_ignition_angle().equals("0.0")) {
			jsona.add(getCheckInfoItemData("ignition_angle", checkInfoTo.get_ignition_angle()));
			check_scoure = getCheckInfoScoure("ignition_angle", check_scoure, checkInfoTo.get_ignition_angle());
		}

		if (!checkInfoTo.get_inlet_temper().equals("0.0")) {
			jsona.add(getCheckInfoItemData("inlet_temper", checkInfoTo.get_inlet_temper()));
			check_scoure = getCheckInfoScoure("inlet_temper", check_scoure, checkInfoTo.get_inlet_temper());
		}

		if (!checkInfoTo.get_relative_position().equals("0.0")) {
			jsona.add(getCheckInfoItemData("relative_position", checkInfoTo.get_relative_position()));
			check_scoure = getCheckInfoScoure("relative_position", check_scoure, checkInfoTo.get_relative_position());
		}

		json.put("checkItem", jsona);
		if (check_scoure < 25) {
			check_scoure = 25;
		}
		json.put("checkScore", check_scoure);

		return json;
	}

	private double getCheckInfoScoure(String string, double check_scoure, String item_value) {
		JSONObject scoure_json = JSONObject.fromObject(CustomUnits.item_scoure.get(string));// 获取最大，最小区间
		check_scoure = customUnits.getCheckScoure(check_scoure, item_value, scoure_json.getDouble("refe_max"), scoure_json.getDouble("refe_min"), scoure_json.getDouble("item_scoure"));
		return check_scoure;
	}

	private JSONObject getCheckInfoItemData(String item_name, String item_value) {
		JSONObject value_json = JSONObject.fromObject(CustomUnits.check_item.get(item_name));// 获取附加值
		JSONObject scoure_json = JSONObject.fromObject(CustomUnits.item_scoure.get(item_name));// 获取最大，最小区间

		value_json.put("item_value", item_value);
		value_json.put("colour", customUnits.getCheckValueColour(value_json.getString("item_value"), scoure_json.getDouble("refe_max"), scoure_json.getDouble("refe_min")));
		return value_json;
	}
}
