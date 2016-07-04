package com.mapbar.netobd.bean.dto;

import java.io.Serializable;

/**
 * @description: 接口查询行程数据
 * @author xubh
 * @date: 2016年4月28日
 */
public class TravelDataTo implements Serializable {
	private static final long serialVersionUID = -1632146009192005783L;
	private String _travel_id;// 行程ID
	private long _start_time;// 行程开始时间
	private long _end_time;// 行程结束时间
	private long _travel_time;// 行程耗时
	private String _start_point;// 开始点
	private String _end_point;// 结束点
	private double _this_travel_len;// 本次行程里程 米
	private double _this_avg_oil;// 本次平均油耗
	private double _carmodel_avg_oil;// 同车百公里油耗，默认去综合油耗，没有取9
	private double _this_oil_ware;// 行程油耗L
	private int _avg_speed;// 平均速度
	private int _max_speed;// 最高速度
	private String _speed_info;// 速度统计 range:距离小计 speed:设置速度 sum_time:时间小计
	private int _this_fastup_count;// 急加速次数
	private int _this_fastlow_count;// 急减速次数
	private int _this_crook_count;// 急转弯次数
	private String _track_info;// 轨迹信息
	private double _travel_score;// 行程得分
	private String _gas_consum_des;// 油耗分析建议
	private String _drive_des;// 驾驶分析建议
	private String _speed_des;// 速度部分
	private String _oil_ware;// 油耗部分
	private double _travel_cost;// 行程花费
	private double _sum_avg_oil;// 累计平均油耗

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

	public String get_end_point() {
		return _end_point;
	}

	public void set_end_point(String _end_point) {
		this._end_point = _end_point;
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

	public void set_carmodel_avg_oil(double _carmodel_vag_oil) {
		this._carmodel_avg_oil = _carmodel_vag_oil;
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

	public String get_track_info() {
		return _track_info;
	}

	public void set_track_info(String _track_info) {
		this._track_info = _track_info;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public double get_sum_avg_oil() {
		return _sum_avg_oil;
	}

	public void set_sum_avg_oil(double _sum_avg_oil) {
		this._sum_avg_oil = _sum_avg_oil;
	}

	public String get_oil_ware() {
		return _oil_ware;
	}

	public void set_oil_ware(String _oil_ware) {
		this._oil_ware = _oil_ware;
	}
}
