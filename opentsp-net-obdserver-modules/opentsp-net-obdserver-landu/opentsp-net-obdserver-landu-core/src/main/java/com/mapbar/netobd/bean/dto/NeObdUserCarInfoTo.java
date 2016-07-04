package com.mapbar.netobd.bean.dto;

import java.io.Serializable;

/**
 * @description: 用户与设备绑定dto
 * @author xubh
 * @date: 2016年4月28日
 */
public class NeObdUserCarInfoTo implements Serializable {
	private static final long serialVersionUID = -6959948383504653130L;
	private String _id;// car_id
	private String _user_id;// 用户ID
	private String _guid;// guid
	private String _sn;// sn码
	private int _obd_state;// obd状态
	private int _hd_type;// 硬件类型
	private String _vin_code;// vin码
	private String _car_model_id;// 车型ID
	private String _phone;// 电话
	private String _account;// 登录ID
	private String _car_code;// 车牌号
	private String _engine_number;// 发动机号
	private long _bind_time;// 绑定时间
	private long _modify_time;// 修改时间
	private long _create_time;// 创建时间
	private int _is_custom_oil;// 是否是自定义油价
	private String _set_oil_area;// 设置油价区域
	private int _set_oil_type;// 设置油价类型
	private double _set_oil_price;// 设置油价价格
	private long _last_user_time;// 最后一次使用时间
	private int _sim_state;// sim卡状态
	private int _is_delete;// 是否是历史记录
	private String _product;// 客户端类型

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String get_user_id() {
		return _user_id;
	}

	public void set_user_id(String _user_id) {
		this._user_id = _user_id;
	}

	public String get_guid() {
		return _guid;
	}

	public void set_guid(String _guid) {
		this._guid = _guid;
	}

	public String get_sn() {
		return _sn;
	}

	public void set_sn(String _sn) {
		this._sn = _sn;
	}

	public int get_obd_state() {
		return _obd_state;
	}

	public void set_obd_state(int _obd_state) {
		this._obd_state = _obd_state;
	}

	public int get_hd_type() {
		return _hd_type;
	}

	public void set_hd_type(int _hd_type) {
		this._hd_type = _hd_type;
	}

	public String get_vin_code() {
		return _vin_code;
	}

	public void set_vin_code(String _vin_code) {
		this._vin_code = _vin_code;
	}

	public String get_car_model_id() {
		return _car_model_id;
	}

	public void set_car_model_id(String _car_model_id) {
		this._car_model_id = _car_model_id;
	}

	public String get_phone() {
		return _phone;
	}

	public void set_phone(String _phone) {
		this._phone = _phone;
	}

	public String get_account() {
		return _account;
	}

	public void set_account(String _account) {
		this._account = _account;
	}

	public String get_car_code() {
		return _car_code;
	}

	public void set_car_code(String _car_code) {
		this._car_code = _car_code;
	}

	public String get_engine_number() {
		return _engine_number;
	}

	public void set_engine_number(String _engine_number) {
		this._engine_number = _engine_number;
	}

	public long get_bind_time() {
		return _bind_time;
	}

	public void set_bind_time(long _bind_time) {
		this._bind_time = _bind_time;
	}

	public long get_modify_time() {
		return _modify_time;
	}

	public void set_modify_time(long _modify_time) {
		this._modify_time = _modify_time;
	}

	public long get_create_time() {
		return _create_time;
	}

	public void set_create_time(long _create_time) {
		this._create_time = _create_time;
	}

	public int get_is_custom_oil() {
		return _is_custom_oil;
	}

	public void set_is_custom_oil(int _is_custom_oil) {
		this._is_custom_oil = _is_custom_oil;
	}

	public String get_set_oil_area() {
		return _set_oil_area;
	}

	public void set_set_oil_area(String _set_oil_area) {
		this._set_oil_area = _set_oil_area;
	}

	public int get_set_oil_type() {
		return _set_oil_type;
	}

	public void set_set_oil_type(int _set_oil_type) {
		this._set_oil_type = _set_oil_type;
	}

	public double get_set_oil_price() {
		return _set_oil_price;
	}

	public void set_set_oil_price(double _set_oil_price) {
		this._set_oil_price = _set_oil_price;
	}

	public long get_last_user_time() {
		return _last_user_time;
	}

	public void set_last_user_time(long _last_user_time) {
		this._last_user_time = _last_user_time;
	}

	public int get_sim_state() {
		return _sim_state;
	}

	public void set_sim_state(int _sim_state) {
		this._sim_state = _sim_state;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int get_is_delete() {
		return _is_delete;
	}

	public void set_is_delete(int _is_delete) {
		this._is_delete = _is_delete;
	}

	public String get_product() {
		return _product;
	}

	public void set_product(String _product) {
		this._product = _product;
	}
}
