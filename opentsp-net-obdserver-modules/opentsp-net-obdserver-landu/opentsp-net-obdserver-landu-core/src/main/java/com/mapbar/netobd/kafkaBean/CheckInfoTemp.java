package com.mapbar.netobd.kafkaBean;

import java.io.Serializable;

/**
 * @description: 瞬时数据
 * @author xubh
 * @date: 2016年4月29日
 */
public class CheckInfoTemp implements Serializable {
	private static final long serialVersionUID = 7947226230636436487L;
	private String _id;
	private String _car_id;
	private String _user_id;
	private String _car_model_id;
	private String _obd_serial;
	private String _vin;
	private String _fault_code;// 故障码
	private int _fault_code_num;//
	private double _control_voltage;// 控制模块电压
	private double _coolant_temper;// 发动机冷冻液温度
	private double _short_term;// 短期燃油调整
	private double _long_term;// 长期燃油调整
	private double _oxygen_voltage;// 氧传感器电压
	private double _engine_load;// 计算发动机负荷值
	private double _intake_perssure;// 进气管绝对压力
	private double _engine_rpm;// 发动机转数
	private double _ignition_angle;// 点火正时提前角
	private double _inlet_temper;// 进气温度
	private double _relative_position;// 节气门相对位置
	private long _check_time;
	private double _check_score;

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

	public String get_fault_code() {
		return _fault_code;
	}

	public void set_fault_code(String _fault_code) {
		this._fault_code = _fault_code;
	}

	public int get_fault_code_num() {
		return _fault_code_num;
	}

	public void set_fault_code_num(int _fault_code_num) {
		this._fault_code_num = _fault_code_num;
	}

	public double get_control_voltage() {
		return _control_voltage;
	}

	public void set_control_voltage(double _control_voltage) {
		this._control_voltage = _control_voltage;
	}

	public double get_coolant_temper() {
		return _coolant_temper;
	}

	public void set_coolant_temper(double _coolant_temper) {
		this._coolant_temper = _coolant_temper;
	}

	public double get_short_term() {
		return _short_term;
	}

	public void set_short_term(double _short_term) {
		this._short_term = _short_term;
	}

	public double get_long_term() {
		return _long_term;
	}

	public void set_long_term(double _long_term) {
		this._long_term = _long_term;
	}

	public double get_oxygen_voltage() {
		return _oxygen_voltage;
	}

	public void set_oxygen_voltage(double _oxygen_voltage) {
		this._oxygen_voltage = _oxygen_voltage;
	}

	public double get_engine_load() {
		return _engine_load;
	}

	public void set_engine_load(double _engine_load) {
		this._engine_load = _engine_load;
	}

	public double get_intake_perssure() {
		return _intake_perssure;
	}

	public void set_intake_perssure(double _intake_perssure) {
		this._intake_perssure = _intake_perssure;
	}

	public double get_engine_rpm() {
		return _engine_rpm;
	}

	public void set_engine_rpm(double _engine_rpm) {
		this._engine_rpm = _engine_rpm;
	}

	public double get_ignition_angle() {
		return _ignition_angle;
	}

	public void set_ignition_angle(double _ignition_angle) {
		this._ignition_angle = _ignition_angle;
	}

	public double get_inlet_temper() {
		return _inlet_temper;
	}

	public void set_inlet_temper(double _inlet_temper) {
		this._inlet_temper = _inlet_temper;
	}

	public double get_relative_position() {
		return _relative_position;
	}

	public void set_relative_position(double _relative_position) {
		this._relative_position = _relative_position;
	}

	public long get_check_time() {
		return _check_time;
	}

	public void set_check_time(long _check_time) {
		this._check_time = _check_time;
	}

	public double get_check_score() {
		return _check_score;
	}

	public void set_check_score(double _check_score) {
		this._check_score = _check_score;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
