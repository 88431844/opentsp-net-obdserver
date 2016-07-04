package com.mapbar.netobd.bean.dto;

import java.io.Serializable;

/**
 * @Description: 车辆基础信息
 * @author xubh
 * @date: 2016年4月28日
 */
public class CarModelInfoTo implements Serializable {

	private static final long serialVersionUID = 7501626606946788867L;

	private String _id; // objectid
	private String _m_f_brand; // 一级品牌
	private String _logo; // 牌标志(车标url的path)
	private String _m_s_brand; // 二级品牌
	private String _m_t_brand; // 三级品牌
	private String _m_year; // 年款（包括停售年款）2013款
	private String _m_model; // 型号(30 TFSI 手动舒适型 2013款)
	private String _transmission; // 变速器型式(值域：自动挡、手动挡
	private String _displacement_liter; // 排量（1.8L）
	private double _complex_consum; // 综合工况油耗(6.50L/100km)
	private double _downtown_consum; // 市区工况油耗(8.50L/100km)
	private double _highwayoil_consum; // 市郊工况油耗(5.30L/100km)
	private double _fuel_users; // 网友油耗(10.3L/100km)
	private double _minturn_radius; // 最小转弯半径(5.85m)
	private int _curb_weight; // 整备质量(1550kg)
	private int _load_quality; // 满载质量(1980kg)
	private double _fuel_capacity; // 燃油箱容积(65L)
	private String _fuel_type; // 燃油类型(汽油 97号(北京95号))
	private String _fuel_way; // 供油方式(直喷)
	private String _displacement_milliliter; // 排量（毫升1798mL）
	private double _maxpower; // 最大功率(118kW)
	private String _maxpower_speed; // 最大功率转速(4500-6200r/min(rpm))
	private double _maxtorque; // 最大扭矩(250Nm)
	private String _maxtorque_speed; // 最大扭矩转速（1500-4500r/min(rpm)）
	private String _innsrgas_mode; // 进气型式（涡轮增压）
	private int _innercylinder_count; // 汽缸数（4）
	private String _percylinder_num; // 每缸气门数（4）
	private double _bore; // 缸径（82.50mm）
	private double _stroke; // 行程（84.10mm）
	private String _compres_ratio; // 压缩比（9.60:1）
	private String _environ_standard; // 环保标准（国4）
	private int _max_horsepower; // 最大马力（161Ps）
	private String _transmission_type; // 变速箱类型（手动）
	private String _stalls_count; // 档位个数（6）
	private double _max_speed; // 最高车速（220km/h）
	private String _driver_computer; // 行车电脑 (有、无)
	private String _warranty_policy; // 保修政策（两年不限公里）
	private String _factory_price; // 厂家指导价（27.28万）
	private String _gps_sys; // gps导航系统(有、无)
	private String _cruise; // 定速巡航(有、无)
	private String _reversing_radar; // 倒车雷达（车后）(有、无)
	private String _pressmonitor_sys; // ̥胎压监测装置(有、无)
	private String _fast_time; // 加速时间(8.20s)
	private String _braking_dist; // 制动距离(40.65m)
	private String _measured_consum; // 实测油耗(11.03L/100km)
	private String _small_pic; // 官方缩略图
	private String _big_pic; // 官方大图
	private String _url; // 网站url
	private long _batch; // 时间戳

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String get_m_f_brand() {
		return _m_f_brand;
	}

	public void set_m_f_brand(String _m_f_brand) {
		this._m_f_brand = _m_f_brand;
	}

	public String get_logo() {
		return _logo;
	}

	public void set_logo(String _logo) {
		this._logo = _logo;
	}

	public String get_m_s_brand() {
		return _m_s_brand;
	}

	public void set_m_s_brand(String _m_s_brand) {
		this._m_s_brand = _m_s_brand;
	}

	public String get_m_t_brand() {
		return _m_t_brand;
	}

	public void set_m_t_brand(String _m_t_brand) {
		this._m_t_brand = _m_t_brand;
	}

	public String get_m_year() {
		return _m_year;
	}

	public void set_m_year(String _m_year) {
		this._m_year = _m_year;
	}

	public String get_m_model() {
		return _m_model;
	}

	public void set_m_model(String _m_model) {
		this._m_model = _m_model;
	}

	public String get_transmission() {
		return _transmission;
	}

	public void set_transmission(String _transmission) {
		this._transmission = _transmission;
	}

	public String get_displacement_liter() {
		return _displacement_liter;
	}

	public void set_displacement_liter(String _displacement_liter) {
		this._displacement_liter = _displacement_liter;
	}

	public double get_complex_consum() {
		return _complex_consum;
	}

	public void set_complex_consum(double _complex_consum) {
		this._complex_consum = _complex_consum;
	}

	public double get_downtown_consum() {
		return _downtown_consum;
	}

	public void set_downtown_consum(double _downtown_consum) {
		this._downtown_consum = _downtown_consum;
	}

	public double get_highwayoil_consum() {
		return _highwayoil_consum;
	}

	public void set_highwayoil_consum(double _highwayoil_consum) {
		this._highwayoil_consum = _highwayoil_consum;
	}

	public double get_fuel_users() {
		return _fuel_users;
	}

	public void set_fuel_users(double _fuel_users) {
		this._fuel_users = _fuel_users;
	}

	public double get_minturn_radius() {
		return _minturn_radius;
	}

	public void set_minturn_radius(double _minturn_radius) {
		this._minturn_radius = _minturn_radius;
	}

	public int get_curb_weight() {
		return _curb_weight;
	}

	public void set_curb_weight(int _curb_weight) {
		this._curb_weight = _curb_weight;
	}

	public int get_load_quality() {
		return _load_quality;
	}

	public void set_load_quality(int _load_quality) {
		this._load_quality = _load_quality;
	}

	public double get_fuel_capacity() {
		return _fuel_capacity;
	}

	public void set_fuel_capacity(double _fuel_capacity) {
		this._fuel_capacity = _fuel_capacity;
	}

	public String get_fuel_type() {
		return _fuel_type;
	}

	public void set_fuel_type(String _fuel_type) {
		this._fuel_type = _fuel_type;
	}

	public String get_fuel_way() {
		return _fuel_way;
	}

	public void set_fuel_way(String _fuel_way) {
		this._fuel_way = _fuel_way;
	}

	public String get_displacement_milliliter() {
		return _displacement_milliliter;
	}

	public void set_displacement_milliliter(String _displacement_milliliter) {
		this._displacement_milliliter = _displacement_milliliter;
	}

	public double get_maxpower() {
		return _maxpower;
	}

	public void set_maxpower(double _maxpower) {
		this._maxpower = _maxpower;
	}

	public String get_maxpower_speed() {
		return _maxpower_speed;
	}

	public void set_maxpower_speed(String _maxpower_speed) {
		this._maxpower_speed = _maxpower_speed;
	}

	public double get_maxtorque() {
		return _maxtorque;
	}

	public void set_maxtorque(double _maxtorque) {
		this._maxtorque = _maxtorque;
	}

	public String get_maxtorque_speed() {
		return _maxtorque_speed;
	}

	public void set_maxtorque_speed(String _maxtorque_speed) {
		this._maxtorque_speed = _maxtorque_speed;
	}

	public String get_innsrgas_mode() {
		return _innsrgas_mode;
	}

	public void set_innsrgas_mode(String _innsrgas_mode) {
		this._innsrgas_mode = _innsrgas_mode;
	}

	public int get_innercylinder_count() {
		return _innercylinder_count;
	}

	public void set_innercylinder_count(int _innercylinder_count) {
		this._innercylinder_count = _innercylinder_count;
	}

	public String get_percylinder_num() {
		return _percylinder_num;
	}

	public void set_percylinder_num(String _percylinder_num) {
		this._percylinder_num = _percylinder_num;
	}

	public double get_bore() {
		return _bore;
	}

	public void set_bore(double _bore) {
		this._bore = _bore;
	}

	public double get_stroke() {
		return _stroke;
	}

	public void set_stroke(double _stroke) {
		this._stroke = _stroke;
	}

	public String get_compres_ratio() {
		return _compres_ratio;
	}

	public void set_compres_ratio(String _compres_ratio) {
		this._compres_ratio = _compres_ratio;
	}

	public String get_environ_standard() {
		return _environ_standard;
	}

	public void set_environ_standard(String _environ_standard) {
		this._environ_standard = _environ_standard;
	}

	public int get_max_horsepower() {
		return _max_horsepower;
	}

	public void set_max_horsepower(int _max_horsepower) {
		this._max_horsepower = _max_horsepower;
	}

	public String get_transmission_type() {
		return _transmission_type;
	}

	public void set_transmission_type(String _transmission_type) {
		this._transmission_type = _transmission_type;
	}

	public String get_stalls_count() {
		return _stalls_count;
	}

	public void set_stalls_count(String _stalls_count) {
		this._stalls_count = _stalls_count;
	}

	public double get_max_speed() {
		return _max_speed;
	}

	public void set_max_speed(double _max_speed) {
		this._max_speed = _max_speed;
	}

	public String get_driver_computer() {
		return _driver_computer;
	}

	public void set_driver_computer(String _driver_computer) {
		this._driver_computer = _driver_computer;
	}

	public String get_warranty_policy() {
		return _warranty_policy;
	}

	public void set_warranty_policy(String _warranty_policy) {
		this._warranty_policy = _warranty_policy;
	}

	public String get_factory_price() {
		return _factory_price;
	}

	public void set_factory_price(String _factory_price) {
		this._factory_price = _factory_price;
	}

	public String get_gps_sys() {
		return _gps_sys;
	}

	public void set_gps_sys(String _gps_sys) {
		this._gps_sys = _gps_sys;
	}

	public String get_cruise() {
		return _cruise;
	}

	public void set_cruise(String _cruise) {
		this._cruise = _cruise;
	}

	public String get_reversing_radar() {
		return _reversing_radar;
	}

	public void set_reversing_radar(String _reversing_radar) {
		this._reversing_radar = _reversing_radar;
	}

	public String get_pressmonitor_sys() {
		return _pressmonitor_sys;
	}

	public void set_pressmonitor_sys(String _pressmonitor_sys) {
		this._pressmonitor_sys = _pressmonitor_sys;
	}

	public String get_fast_time() {
		return _fast_time;
	}

	public void set_fast_time(String _fast_time) {
		this._fast_time = _fast_time;
	}

	public String get_braking_dist() {
		return _braking_dist;
	}

	public void set_braking_dist(String _braking_dist) {
		this._braking_dist = _braking_dist;
	}

	public String get_measured_consum() {
		return _measured_consum;
	}

	public void set_measured_consum(String _measured_consum) {
		this._measured_consum = _measured_consum;
	}

	public String get_small_pic() {
		return _small_pic;
	}

	public void set_small_pic(String _small_pic) {
		this._small_pic = _small_pic;
	}

	public String get_big_pic() {
		return _big_pic;
	}

	public void set_big_pic(String _big_pic) {
		this._big_pic = _big_pic;
	}

	public String get_url() {
		return _url;
	}

	public void set_url(String _url) {
		this._url = _url;
	}

	public long get_batch() {
		return _batch;
	}

	public void set_batch(long _batch) {
		this._batch = _batch;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}