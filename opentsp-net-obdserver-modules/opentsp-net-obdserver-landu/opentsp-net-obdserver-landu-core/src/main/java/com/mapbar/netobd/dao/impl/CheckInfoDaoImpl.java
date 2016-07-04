package com.mapbar.netobd.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.mapbar.netobd.bean.dto.CheckInfoTo;
import com.mapbar.netobd.dao.CheckInfoDao;
import com.mapbar.netobd.kafkaBean.CheckInfoTemp;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.InputGetCheckInfo;

/**
 * @description: 体检信息持久层
 * @author xubh
 * @date: 2016年4月28日
 */
@Repository
public class CheckInfoDaoImpl implements CheckInfoDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public void InsertCheckInfo(CheckInfoTemp cit) throws Exception {
		String sql = "INSERT INTO check_info (" + "_id, _obd_serial, _vin, _control_voltage, _coolant_temper, " + "_short_term, _long_term, _oxygen_voltage, _engine_load, _intake_perssure, " + "_engine_rpm, _ignition_angle, _inlet_temper, _relative_position, _check_time, " + "_check_score, _create_time, _fault_code, _car_id, _user_id, _car_model_id, _fault_code_num)"
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		jdbcTemplate.update(sql, new Object[] { cit.get_id(), cit.get_obd_serial(), cit.get_vin(), cit.get_control_voltage(), cit.get_coolant_temper(), cit.get_short_term(), cit.get_long_term(), cit.get_oxygen_voltage(), cit.get_engine_load(), cit.get_intake_perssure(), cit.get_engine_rpm(), cit.get_ignition_angle(), cit.get_inlet_temper(), cit.get_relative_position(), cit.get_check_time(),
				cit.get_check_score(), System.currentTimeMillis(), cit.get_fault_code(), cit.get_car_id(), cit.get_user_id(), cit.get_car_model_id(), cit.get_fault_code_num() });
	}

	@Override
	public CheckInfoTo findCheckInfo(InputGetCheckInfo info) throws Exception {
		String sql = "SELECT model._m_f_brand, model._m_t_brand, model._m_model, model._m_year , info.* FROM check_info info, base_carmodel model where info._car_model_id = model._id and _user_id = ? ORDER BY _create_time DESC limit 1";
		return jdbcTemplate.query(sql, new Object[] { info.getUserId() }, new ResultSetExtractor<CheckInfoTo>() {

			@Override
			public CheckInfoTo extractData(ResultSet rs) throws SQLException, DataAccessException {
				CheckInfoTo cito = new CheckInfoTo();
				if (rs.next()) {
					cito.set_id(rs.getString("_id"));
					cito.set_fault_code(rs.getString("_fault_code"));
					cito.set_control_voltage(rs.getString("_control_voltage"));
					cito.set_coolant_temper(rs.getString("_coolant_temper"));
					cito.set_short_term(rs.getString("_short_term"));
					cito.set_long_term(rs.getString("_long_term"));
					cito.set_oxygen_voltage(rs.getString("_oxygen_voltage"));
					cito.set_engine_load(rs.getString("_engine_load"));
					cito.set_intake_perssure(rs.getString("_intake_perssure"));
					cito.set_engine_rpm(rs.getString("_engine_rpm"));
					cito.set_ignition_angle(rs.getString("_ignition_angle"));
					cito.set_inlet_temper(rs.getString("_inlet_temper"));
					cito.set_relative_position(rs.getString("_relative_position"));
					cito.set_check_time(rs.getString("_check_time"));
					cito.set_check_score(rs.getString("_check_score"));
					cito.set_fault_code_num(rs.getString("_fault_code_num"));
					cito.set_car_model_info(rs.getString("_m_f_brand") + " " + rs.getString("_m_t_brand") + " " + rs.getString("_m_model") + " " + rs.getString("_m_year"));
				}
				return cito;
			}

		});
	}
}
