package com.mapbar.netobd.bean.dto;

import java.io.Serializable;

/**
 * @description: 每天的行程列表
 * @author xubh
 * @date: 2016年4月28日
 */
public class DayTravelTo implements Serializable {
	private static final long serialVersionUID = 6399986673255019179L;
	private String _id;
	private String _start_point;
	private String _end_point;
	private long _start_time;
	private long _end_time;

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String get_start_point() {
		return _start_point;
	}

	public void set_start_point(String _start_point) {
		this._start_point = _start_point;
	}

	public String get_end_point() {
		return _end_point;
	}

	public void set_end_point(String _end_point) {
		this._end_point = _end_point;
	}

	public long get_start_time() {
		return _start_time;
	}

	public void set_start_time(long _start_time) {
		this._start_time = _start_time;
	}

	public long get_end_time() {
		return _end_time;
	}

	public void set_end_time(long _end_time) {
		this._end_time = _end_time;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
