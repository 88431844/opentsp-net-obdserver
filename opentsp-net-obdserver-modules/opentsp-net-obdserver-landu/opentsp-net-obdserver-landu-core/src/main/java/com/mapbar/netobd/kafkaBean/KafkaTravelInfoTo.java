package com.mapbar.netobd.kafkaBean;

import java.io.Serializable;

public class KafkaTravelInfoTo implements Serializable {
	private static final long serialVersionUID = 7516076945360784339L;
	private String _id;
	private String _car_id;
	private String _user_id;
	private String _car_model_id;
	private String _sn;
	private String _vid;
	private String _vin;
	private String _trip_id;
	private long _start_check_time;
	private String _start_voltage;
	private String _start_speed;
	private String _start_operating_range;
	private String _start_longitude;
	private String _start_latitude;
	private String _starting_point;
	private long _start_location_time;
	private String _start_location_type;
	private long _end_check_time;
	private int _engine_run_time;
	private double _this_drive_range;
	private double _this_avg_oil;
	private double _sum_drive_range;
	private double _sum_avg_oil;
	private String _speed_array_length;
	private String _speedDoList;
	private int _this_fastup_count;
	private int _this_fastlow_count;
	private int _this_crook_count;
	private long _this_overspeed_time;
	private int _max_speed;
	private String _speed;
	private String _current_drive_range;
	private String _end_longitude;
	private String _end_latitude;
	private String _end_point;
	private long _end_location_time;
	private String _end_location_type;
	private double _travel_oil_price;
	private String _travel_date;
	private double _travel_score;
	private long _create_time;

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

	public String get_car_model_id() {
		return _car_model_id;
	}

	public void set_car_model_id(String _car_model_id) {
		this._car_model_id = _car_model_id;
	}

	public String get_sn() {
		return _sn;
	}

	public void set_sn(String _sn) {
		this._sn = _sn;
	}

	public String get_vid() {
		return _vid;
	}

	public void set_vid(String _vid) {
		this._vid = _vid;
	}

	public String get_vin() {
		return _vin;
	}

	public void set_vin(String _vin) {
		this._vin = _vin;
	}

	public String get_trip_id() {
		return _trip_id;
	}

	public void set_trip_id(String _trip_id) {
		this._trip_id = _trip_id;
	}

	public long get_start_check_time() {
		return _start_check_time;
	}

	public void set_start_check_time(long _start_check_time) {
		this._start_check_time = _start_check_time;
	}

	public String get_start_voltage() {
		return _start_voltage;
	}

	public void set_start_voltage(String _start_voltage) {
		this._start_voltage = _start_voltage;
	}

	public String get_start_speed() {
		return _start_speed;
	}

	public void set_start_speed(String _start_speed) {
		this._start_speed = _start_speed;
	}

	public String get_start_operating_range() {
		return _start_operating_range;
	}

	public void set_start_operating_range(String _start_operating_range) {
		this._start_operating_range = _start_operating_range;
	}

	public String get_start_longitude() {
		return _start_longitude;
	}

	public void set_start_longitude(String _start_longitude) {
		this._start_longitude = _start_longitude;
	}

	public String get_start_latitude() {
		return _start_latitude;
	}

	public void set_start_latitude(String _start_latitude) {
		this._start_latitude = _start_latitude;
	}

	public String get_starting_point() {
		return _starting_point;
	}

	public void set_starting_point(String _starting_point) {
		this._starting_point = _starting_point;
	}

	public long get_start_location_time() {
		return _start_location_time;
	}

	public void set_start_location_time(long _start_location_time) {
		this._start_location_time = _start_location_time;
	}

	public String get_start_location_type() {
		return _start_location_type;
	}

	public void set_start_location_type(String _start_location_type) {
		this._start_location_type = _start_location_type;
	}

	public long get_end_check_time() {
		return _end_check_time;
	}

	public void set_end_check_time(long _end_check_time) {
		this._end_check_time = _end_check_time;
	}

	public int get_engine_run_time() {
		return _engine_run_time;
	}

	public void set_engine_run_time(int _engine_run_time) {
		this._engine_run_time = _engine_run_time;
	}

	public double get_this_drive_range() {
		return _this_drive_range;
	}

	public void set_this_drive_range(double _this_drive_range) {
		this._this_drive_range = _this_drive_range;
	}

	public double get_this_avg_oil() {
		return _this_avg_oil;
	}

	public void set_this_avg_oil(double _this_avg_oil) {
		this._this_avg_oil = _this_avg_oil;
	}

	public double get_sum_drive_range() {
		return _sum_drive_range;
	}

	public void set_sum_drive_range(double _sum_drive_range) {
		this._sum_drive_range = _sum_drive_range;
	}

	public double get_sum_avg_oil() {
		return _sum_avg_oil;
	}

	public void set_sum_avg_oil(double _sum_avg_oil) {
		this._sum_avg_oil = _sum_avg_oil;
	}

	public String get_speed_array_length() {
		return _speed_array_length;
	}

	public void set_speed_array_length(String _speed_array_length) {
		this._speed_array_length = _speed_array_length;
	}

	public String get_speedDoList() {
		return _speedDoList;
	}

	public void set_speedDoList(String _speedDoList) {
		this._speedDoList = _speedDoList;
	}

	public int get_this_fastup_count() {
		return _this_fastup_count;
	}

	public void set_this_fastup_count(int _this_fastup_count) {
		this._this_fastup_count = _this_fastup_count;
	}

	public int get_this_fastlow_count() {
		return _this_fastlow_count;
	}

	public void set_this_fastlow_count(int _this_fastlow_count) {
		this._this_fastlow_count = _this_fastlow_count;
	}

	public int get_this_crook_count() {
		return _this_crook_count;
	}

	public void set_this_crook_count(int _this_crook_count) {
		this._this_crook_count = _this_crook_count;
	}

	public long get_this_overspeed_time() {
		return _this_overspeed_time;
	}

	public void set_this_overspeed_time(long _this_overspeed_time) {
		this._this_overspeed_time = _this_overspeed_time;
	}

	public int get_max_speed() {
		return _max_speed;
	}

	public void set_max_speed(int max_speed) {
		this._max_speed = max_speed;
	}

	public String get_speed() {
		return _speed;
	}

	public void set_speed(String _speed) {
		this._speed = _speed;
	}

	public String get_current_drive_range() {
		return _current_drive_range;
	}

	public void set_current_drive_range(String _current_drive_range) {
		this._current_drive_range = _current_drive_range;
	}

	public String get_end_longitude() {
		return _end_longitude;
	}

	public void set_end_longitude(String _end_longitude) {
		this._end_longitude = _end_longitude;
	}

	public String get_end_latitude() {
		return _end_latitude;
	}

	public void set_end_latitude(String _end_latitude) {
		this._end_latitude = _end_latitude;
	}

	public long get_end_location_time() {
		return _end_location_time;
	}

	public void set_end_location_time(long _end_location_time) {
		this._end_location_time = _end_location_time;
	}

	public String get_end_location_type() {
		return _end_location_type;
	}

	public void set_end_location_type(String _end_location_type) {
		this._end_location_type = _end_location_type;
	}

	public double get_travel_oil_price() {
		return _travel_oil_price;
	}

	public void set_travel_oil_price(double _travel_oil_price) {
		this._travel_oil_price = _travel_oil_price;
	}

	public String get_travel_date() {
		return _travel_date;
	}

	public void set_travel_date(String _travel_date) {
		this._travel_date = _travel_date;
	}

	public long get_create_time() {
		return _create_time;
	}

	public void set_create_time(long _create_time) {
		this._create_time = _create_time;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String get_end_point() {
		return _end_point;
	}

	public void set_end_point(String _end_point) {
		this._end_point = _end_point;
	}

	public double get_travel_score() {
		return _travel_score;
	}

	public void set_travel_score(double _travel_score) {
		this._travel_score = _travel_score;
	}
}
