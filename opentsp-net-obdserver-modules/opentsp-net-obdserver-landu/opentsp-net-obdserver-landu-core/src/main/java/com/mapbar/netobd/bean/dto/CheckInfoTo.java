package com.mapbar.netobd.bean.dto;

import java.io.Serializable;

/**
 * @Description: 体检信息
 * @author xubh
 * @date: 2016年4月28日
 */
public class CheckInfoTo implements Serializable {
	private static final long serialVersionUID = 3857438247679119291L;
	private String _id;
	private String _fault_code;// 故障码
	private String _fault_code_num;// 故障码个数
	private String _control_voltage;// 控制模块电压(V)
	private String _coolant_temper;// 发动机冷冻液温度(℃)
	private String _short_term;// 短期燃油调整(%)
	private String _long_term;// 长期燃油调整(%)
	private String _oxygen_voltage;// 氧传感器电压(v)
	private String _engine_load;// 计算发动机负荷值(%)
	private String _intake_perssure;// 进气管绝对压力(kPa)
	private String _engine_rpm;// 发动机转数(rpm)
	private String _ignition_angle;// 点火正时提前角(°)
	private String _inlet_temper;// 进气温度(℃)
	private String _relative_position;// 节气门相对位置(%)
	private String _check_time;// 体检时间
	private String _check_score;// 体检得分
	private String _car_model_info;//

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String get_fault_code() {
		return _fault_code;
	}

	public void set_fault_code(String _fault_code) {
		this._fault_code = _fault_code;
	}

	public String get_control_voltage() {
		return _control_voltage;
	}

	public void set_control_voltage(String _control_voltage) {
		this._control_voltage = _control_voltage;
	}

	public String get_coolant_temper() {
		return _coolant_temper;
	}

	public void set_coolant_temper(String _coolant_temper) {
		this._coolant_temper = _coolant_temper;
	}

	public String get_short_term() {
		return _short_term;
	}

	public void set_short_term(String _short_term) {
		this._short_term = _short_term;
	}

	public String get_long_term() {
		return _long_term;
	}

	public void set_long_term(String _long_term) {
		this._long_term = _long_term;
	}

	public String get_oxygen_voltage() {
		return _oxygen_voltage;
	}

	public void set_oxygen_voltage(String _oxygen_voltage) {
		this._oxygen_voltage = _oxygen_voltage;
	}

	public String get_engine_load() {
		return _engine_load;
	}

	public void set_engine_load(String _engine_load) {
		this._engine_load = _engine_load;
	}

	public String get_intake_perssure() {
		return _intake_perssure;
	}

	public void set_intake_perssure(String _intake_perssure) {
		this._intake_perssure = _intake_perssure;
	}

	public String get_engine_rpm() {
		return _engine_rpm;
	}

	public void set_engine_rpm(String _engine_rpm) {
		this._engine_rpm = _engine_rpm;
	}

	public String get_ignition_angle() {
		return _ignition_angle;
	}

	public void set_ignition_angle(String _ignition_angle) {
		this._ignition_angle = _ignition_angle;
	}

	public String get_inlet_temper() {
		return _inlet_temper;
	}

	public void set_inlet_temper(String _inlet_temper) {
		this._inlet_temper = _inlet_temper;
	}

	public String get_relative_position() {
		return _relative_position;
	}

	public void set_relative_position(String _relative_position) {
		this._relative_position = _relative_position;
	}

	public String get_check_time() {
		return _check_time;
	}

	public void set_check_time(String _check_time) {
		this._check_time = _check_time;
	}

	public String get_check_score() {
		return _check_score;
	}

	public void set_check_score(String _check_score) {
		this._check_score = _check_score;
	}

	public String get_fault_code_num() {
		return _fault_code_num;
	}

	public void set_fault_code_num(String _fault_code_num) {
		this._fault_code_num = _fault_code_num;
	}

	public String get_car_model_info() {
		return _car_model_info;
	}

	public void set_car_model_info(String _car_model_info) {
		this._car_model_info = _car_model_info;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
