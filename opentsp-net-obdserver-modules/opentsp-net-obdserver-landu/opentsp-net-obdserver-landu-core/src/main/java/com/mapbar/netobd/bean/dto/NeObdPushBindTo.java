package com.mapbar.netobd.bean.dto;

import java.io.Serializable;

/**
 * @description: 用户推送提醒设置表
 * @author xubh
 * @date: 2016年4月28日
 */
public class NeObdPushBindTo implements Serializable {
	private static final long serialVersionUID = -183833990325186384L;
	private String _id;
	private String _car_id;// 用户车型ID
	private String _user_id;// 用户ID
	private int _offline_push;// 离线提醒开关
	private int _ignition_push;// 点火提醒开关
	private int _voltages_push;// 电压提醒开关
	private double _set_voltages;// 设置提醒打压
	private int _shock_push;// 碰撞提醒开关
	private int _shock_active;// 碰撞提醒激活
	private String _push_token;// 推送token
	private String _product;// 客户端类型

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String get_car_id() {
		return _car_id;
	}

	public void set_car_id(String _car_id) {
		this._car_id = _car_id;
	}

	public String get_user_id() {
		return _user_id;
	}

	public void set_user_id(String _user_id) {
		this._user_id = _user_id;
	}

	public int get_offline_push() {
		return _offline_push;
	}

	public void set_offline_push(int _offline_push) {
		this._offline_push = _offline_push;
	}

	public int get_ignition_push() {
		return _ignition_push;
	}

	public void set_ignition_push(int _ignition_push) {
		this._ignition_push = _ignition_push;
	}

	public int get_voltages_push() {
		return _voltages_push;
	}

	public void set_voltages_push(int _voltages_push) {
		this._voltages_push = _voltages_push;
	}

	public double get_set_voltages() {
		return _set_voltages;
	}

	public void set_set_voltages(double _set_voltages) {
		this._set_voltages = _set_voltages;
	}

	public int get_shock_push() {
		return _shock_push;
	}

	public void set_shock_push(int _shock_push) {
		this._shock_push = _shock_push;
	}

	public String get_push_token() {
		return _push_token;
	}

	public void set_push_token(String _push_token) {
		this._push_token = _push_token;
	}

	public String get_product() {
		return _product;
	}

	public void set_product(String _product) {
		this._product = _product;
	}

	public int get_shock_active() {
		return _shock_active;
	}

	public void set_shock_active(int _shock_active) {
		this._shock_active = _shock_active;
	}
}
