package com.mapbar.netobd.bean.dto;

import java.io.Serializable;

/**
 * @description: 行程日期分布
 * @author xubh
 * @date: 2016年4月28日
 */
public class DriveMonthTo implements Serializable {

	private static final long serialVersionUID = 5898295377810340770L;
	private String _user_id;
	private String _car_id;
	private String _sn;
	private String _trip_month;
	private int _travel_day;
	private int _travel_number;

	public String get_user_id() {
		return _user_id;
	}

	public void set_user_id(String _user_id) {
		this._user_id = _user_id;
	}

	public String get_car_id() {
		return _car_id;
	}

	public void set_car_id(String _car_id) {
		this._car_id = _car_id;
	}

	public String get_sn() {
		return _sn;
	}

	public void set_sn(String _sn) {
		this._sn = _sn;
	}

	public String get_trip_month() {
		return _trip_month;
	}

	public void set_trip_month(String _trip_month) {
		this._trip_month = _trip_month;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int get_travel_day() {
		return _travel_day;
	}

	public void set_travel_day(int _travel_day) {
		this._travel_day = _travel_day;
	}

	public int get_travel_number() {
		return _travel_number;
	}

	public void set_travel_number(int _travel_number) {
		this._travel_number = _travel_number;
	}
}
