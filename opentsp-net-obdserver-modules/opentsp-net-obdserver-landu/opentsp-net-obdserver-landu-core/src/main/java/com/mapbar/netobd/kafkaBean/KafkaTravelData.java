package com.mapbar.netobd.kafkaBean;

import java.io.Serializable;

/**
 * @description: 行程数据
 * @author xubh
 * @date: 2016年4月29日
 */
public class KafkaTravelData implements Serializable {
	private static final long serialVersionUID = -1632146009192005783L;
	private String _id;
	private String _user_id;
	private String _car_id;
	private String _car_model_id;
	private String _obd_serial;// OBD串号
	private String _vin;// VIN码
	private String _vid;// vind
	private String _travel_id;// 行程ID
	private String _travel_date;// 行程日期yyyyMMdd
	private long _start_time;// 行程开始时间
	private long _end_time;// 行程结束时间
	private long _travel_time;// 行程耗时
	private String _start_point;// 开始点
	private String _start_lon;// 开始经度
	private String _start_lat;// 开始纬度
	private String _end_point;// 结束点
	private String _end_lon;// 结束经度
	private String _end_lat;// 结束纬度
	private double _this_travel_len;// 本次行程里程 米
	private double _this_avg_oil;// 本次平均油耗
	private double _carmodel_avg_oil;// 同车百公里油耗，默认去综合油耗，没有取9
	private double _this_oil_ware;// 行程油耗L
	private double _travel_oil_price;// 行程油价
	private int _avg_speed;// 平均速度
	private int _max_speed;// 最高速度
	private String _speed_info;// 速度统计 range:距离小计 speed:设置速度 sum_time:时间小计
	private int _speed_sum;// 速度总个数
	private int _speed_60;// 速度60个数
	private int _speed_90;// 速度90个数
	private int _speed_120;// 速度120个数
	private int _speed_121;// 速度大于120的个数
	private int _this_fastup_count;// 急加速次数
	private int _this_fastlow_count;// 急减速次数
	private int _this_crook_count;// 急转弯次数
	private int _this_idling_num;// 怠速次数
	private long _this_idling_time;// 怠速时长
	private String _track_info;// 轨迹信息
	private double _sum_drive_range;// 累计行驶里程
	private double _sum_avg_oil;// 累加平均油耗
	private double _travel_score;// 行程得分
	private String _gas_consum_des;// 油耗分析建议
	private String _drive_des;// 驾驶分析建议
	private String _speed_des;// 速度部分
	private String _oil_ware;// 油耗部分
	private double _travel_cost;// 行程花费

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String get_obd_serial() {
		return _obd_serial;
	}

	public void set_obd_serial(String _obd_serial) {
		this._obd_serial = _obd_serial;
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

	public String get_travel_id() {
		return _travel_id;
	}

	public void set_travel_id(String _travel_id) {
		this._travel_id = _travel_id;
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

	public long get_travel_time() {
		return _travel_time;
	}

	public void set_travel_time(long _travel_time) {
		this._travel_time = _travel_time;
	}

	public String get_start_point() {
		return _start_point;
	}

	public void set_start_point(String _start_point) {
		this._start_point = _start_point;
	}

	public String get_start_lon() {
		return _start_lon;
	}

	public void set_start_lon(String _start_lon) {
		this._start_lon = _start_lon;
	}

	public String get_start_lat() {
		return _start_lat;
	}

	public void set_start_lat(String _start_lat) {
		this._start_lat = _start_lat;
	}

	public String get_end_point() {
		return _end_point;
	}

	public void set_end_point(String _end_point) {
		this._end_point = _end_point;
	}

	public String get_end_lon() {
		return _end_lon;
	}

	public void set_end_lon(String _end_lon) {
		this._end_lon = _end_lon;
	}

	public String get_end_lat() {
		return _end_lat;
	}

	public void set_end_lat(String _end_lat) {
		this._end_lat = _end_lat;
	}

	public double get_this_travel_len() {
		return _this_travel_len;
	}

	public void set_this_travel_len(double _this_travel_len) {
		this._this_travel_len = _this_travel_len;
	}

	public double get_this_avg_oil() {
		return _this_avg_oil;
	}

	public void set_this_avg_oil(double _this_avg_oil) {
		this._this_avg_oil = _this_avg_oil;
	}

	public double get_carmodel_avg_oil() {
		return _carmodel_avg_oil;
	}

	public void set_carmodel_avg_oil(double _carmodel_avg_oil) {
		this._carmodel_avg_oil = _carmodel_avg_oil;
	}

	public double get_this_oil_ware() {
		return _this_oil_ware;
	}

	public void set_this_oil_ware(double _this_oil_ware) {
		this._this_oil_ware = _this_oil_ware;
	}

	public int get_avg_speed() {
		return _avg_speed;
	}

	public void set_avg_speed(int _avg_speed) {
		this._avg_speed = _avg_speed;
	}

	public int get_max_speed() {
		return _max_speed;
	}

	public void set_max_speed(int _max_speed) {
		this._max_speed = _max_speed;
	}

	public String get_speed_info() {
		return _speed_info;
	}

	public void set_speed_info(String _speed_info) {
		this._speed_info = _speed_info;
	}

	public int get_speed_sum() {
		return _speed_sum;
	}

	public void set_speed_sum(int _speed_sum) {
		this._speed_sum = _speed_sum;
	}

	public int get_speed_60() {
		return _speed_60;
	}

	public void set_speed_60(int _speed_60) {
		this._speed_60 = _speed_60;
	}

	public int get_speed_90() {
		return _speed_90;
	}

	public void set_speed_90(int _speed_90) {
		this._speed_90 = _speed_90;
	}

	public int get_speed_120() {
		return _speed_120;
	}

	public void set_speed_120(int _speed_120) {
		this._speed_120 = _speed_120;
	}

	public int get_speed_121() {
		return _speed_121;
	}

	public void set_speed_121(int _speed_121) {
		this._speed_121 = _speed_121;
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

	public int get_this_idling_num() {
		return _this_idling_num;
	}

	public void set_this_idling_num(int _this_idling_num) {
		this._this_idling_num = _this_idling_num;
	}

	public long get_this_idling_time() {
		return _this_idling_time;
	}

	public void set_this_idling_time(long _this_idling_time) {
		this._this_idling_time = _this_idling_time;
	}

	public String get_track_info() {
		return _track_info;
	}

	public void set_track_info(String _track_info) {
		this._track_info = _track_info;
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

	public double get_travel_score() {
		return _travel_score;
	}

	public void set_travel_score(double _travel_score) {
		this._travel_score = _travel_score;
	}

	public String get_gas_consum_des() {
		return _gas_consum_des;
	}

	public void set_gas_consum_des(String _gas_consum_des) {
		this._gas_consum_des = _gas_consum_des;
	}

	public String get_drive_des() {
		return _drive_des;
	}

	public void set_drive_des(String _drive_des) {
		this._drive_des = _drive_des;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

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

	public String get_car_model_id() {
		return _car_model_id;
	}

	public void set_car_model_id(String _car_model_id) {
		this._car_model_id = _car_model_id;
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

	public String get_speed_des() {
		return _speed_des;
	}

	public void set_speed_des(String _speed_des) {
		this._speed_des = _speed_des;
	}

	public double get_travel_cost() {
		return _travel_cost;
	}

	public void set_travel_cost(double _travel_cost) {
		this._travel_cost = _travel_cost;
	}

	public String get_oil_ware() {
		return _oil_ware;
	}

	public void set_oil_ware(String _oil_ware) {
		this._oil_ware = _oil_ware;
	}
}
