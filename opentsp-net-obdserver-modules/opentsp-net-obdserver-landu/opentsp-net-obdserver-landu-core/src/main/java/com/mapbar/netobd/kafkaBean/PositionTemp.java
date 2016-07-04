package com.mapbar.netobd.kafkaBean;

/**
 * OBD上传点信息临时表BEAN
 * 
 * @author yulong
 *
 */
public class PositionTemp {
	private String _sn;
	private String _trip_id;
	private String _vin;
	private String _vid;
	private String _latitude;
	private String _longitude;
	private String _position_time;
	private String _speed;
	private String _drive_distance;
	private String _direction_angle;
	private String _car_id;
	private String _user_id;
	private long _create_time;

	public String get_sn() {
		return _sn;
	}

	public void set_sn(String _sn) {
		this._sn = _sn;
	}

	public String get_trip_id() {
		return _trip_id;
	}

	public void set_trip_id(String _trip_id) {
		this._trip_id = _trip_id;
	}

	public String get_vin() {
		return _vin;
	}

	public void set_vin(String _vin) {
		this._vin = _vin;
	}

	public String get_vid() {
		return _vid;
	}

	public void set_vid(String _vid) {
		this._vid = _vid;
	}

	public String get_latitude() {
		return _latitude;
	}

	public void set_latitude(String _latitude) {
		this._latitude = _latitude;
	}

	public String get_longitude() {
		return _longitude;
	}

	public void set_longitude(String _longitude) {
		this._longitude = _longitude;
	}

	public String get_position_time() {
		return _position_time;
	}

	public void set_position_time(String _position_time) {
		this._position_time = _position_time;
	}

	public String get_speed() {
		return _speed;
	}

	public void set_speed(String _speed) {
		this._speed = _speed;
	}

	public String get_drive_distance() {
		return _drive_distance;
	}

	public void set_drive_distance(String _drive_distance) {
		this._drive_distance = _drive_distance;
	}

	public String get_direction_angle() {
		return _direction_angle;
	}

	public void set_direction_angle(String _direction_angle) {
		this._direction_angle = _direction_angle;
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

	public long get_create_time() {
		return _create_time;
	}

	public void set_create_time(long _create_time) {
		this._create_time = _create_time;
	}
}
