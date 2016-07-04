package com.mapbar.netobd.bean.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class RowMappers {

	public ResultSetExtractor<NeObdUserCarInfoTo> resultSetExtrUserInfo() {
		ResultSetExtractor<NeObdUserCarInfoTo> result = new ResultSetExtractor<NeObdUserCarInfoTo>() {

			@Override
			public NeObdUserCarInfoTo extractData(ResultSet arg0) throws SQLException, DataAccessException {
				NeObdUserCarInfoTo noucif = new NeObdUserCarInfoTo();
				if (arg0.next()) {
					noucif.set_id(arg0.getString("_id"));
					noucif.set_user_id(arg0.getString("_user_id"));
					noucif.set_sn(arg0.getString("_sn"));
					noucif.set_obd_state(arg0.getInt("_obd_state"));
					noucif.set_hd_type(arg0.getInt("_hd_type"));
					noucif.set_vin_code(arg0.getString("_vin_code"));
					noucif.set_car_model_id(arg0.getString("_car_model_id"));
					noucif.set_phone(arg0.getString("_phone"));
					noucif.set_account(arg0.getString("_account"));
					noucif.set_car_code(arg0.getString("_car_code"));
					noucif.set_engine_number(arg0.getString("_engine_number"));
					noucif.set_bind_time(arg0.getLong("_bind_time"));
					noucif.set_modify_time(arg0.getLong("_modify_time"));
					noucif.set_create_time(arg0.getLong("_create_time"));
					noucif.set_is_custom_oil(arg0.getInt("_is_custom_oil"));
					noucif.set_set_oil_area(arg0.getString("_set_oil_area"));
					noucif.set_set_oil_type(arg0.getInt("_set_oil_type"));
					noucif.set_set_oil_price(arg0.getDouble("_set_oil_price"));
					noucif.set_last_user_time(arg0.getLong("_last_user_time"));
					noucif.set_sim_state(arg0.getInt("_sim_state"));
					noucif.set_is_delete(arg0.getInt("_is_delete"));
					noucif.set_guid(arg0.getString("_guid"));
				}
				return noucif;
			}
		};
		return result;
	}

	public ResultSetExtractor<CarModelInfoTo> resultSetExtrCarInfoTo() {
		ResultSetExtractor<CarModelInfoTo> result = new ResultSetExtractor<CarModelInfoTo>() {

			@Override
			public CarModelInfoTo extractData(ResultSet rs) throws SQLException, DataAccessException {
				CarModelInfoTo rs_cmit = new CarModelInfoTo();
				if (rs.next()) {
					rs_cmit.set_id(rs.getString("_id"));
					rs_cmit.set_m_f_brand(rs.getString("_m_f_brand"));
					rs_cmit.set_m_t_brand(rs.getString("_m_s_brand"));
					rs_cmit.set_m_model(rs.getString("_m_t_brand"));
					rs_cmit.set_m_year(rs.getString("_m_year"));
					rs_cmit.set_fuel_type(rs.getString("_fuel_type"));
				}
				return rs_cmit;
			}
		};
		return result;
	}

	public ResultSetExtractor<NeObdUserCarInfoTo> resultSetExtrUserVehicle() {
		ResultSetExtractor<NeObdUserCarInfoTo> result = new ResultSetExtractor<NeObdUserCarInfoTo>() {

			@Override
			public NeObdUserCarInfoTo extractData(ResultSet rs) throws SQLException, DataAccessException {
				NeObdUserCarInfoTo noucif = new NeObdUserCarInfoTo();
				if (rs.next()) {
					noucif.set_id(rs.getString("_id"));
					noucif.set_user_id(rs.getString("_user_id"));
					noucif.set_sn(rs.getString("_sn"));
					noucif.set_obd_state(rs.getInt("_obd_state"));
					noucif.set_hd_type(rs.getInt("_hd_type"));
					noucif.set_vin_code(rs.getString("_vin_code"));
					noucif.set_car_model_id(rs.getString("_car_model_id"));
					noucif.set_phone(rs.getString("_phone"));
					noucif.set_account(rs.getString("_account"));
					noucif.set_car_code(rs.getString("_car_code"));
					noucif.set_engine_number(rs.getString("_engine_number"));
					noucif.set_bind_time(rs.getLong("_bind_time"));
					noucif.set_modify_time(rs.getLong("_modify_time"));
					noucif.set_create_time(rs.getLong("_create_time"));
					noucif.set_is_custom_oil(rs.getInt("_is_custom_oil"));
					noucif.set_set_oil_area(rs.getString("_set_oil_area"));
					noucif.set_set_oil_type(rs.getInt("_set_oil_type"));
					noucif.set_set_oil_price(rs.getDouble("_set_oil_price"));
					noucif.set_last_user_time(rs.getLong("_last_user_time"));
					noucif.set_sim_state(rs.getInt("_sim_state"));
					noucif.set_is_delete(rs.getInt("_is_delete"));
				}
				return noucif;
			}

		};
		return result;
	}

	public TravelDataTo getTravelData(ResultSet rs) {
		TravelDataTo td = new TravelDataTo();
		try {
			td.set_travel_id(rs.getString("_id"));
			td.set_start_time(rs.getLong("_start_time"));
			td.set_end_time(rs.getLong("_end_time"));
			td.set_travel_time(rs.getLong("_travel_time"));
			td.set_start_point(rs.getString("_start_point"));
			td.set_end_point(rs.getString("_end_point"));
			td.set_this_travel_len(rs.getDouble("_this_travel_len"));
			td.set_this_avg_oil(rs.getDouble("_this_avg_oil"));
			td.set_carmodel_avg_oil(rs.getDouble("_carmodel_avg_oil"));
			td.set_this_oil_ware(rs.getDouble("_this_oil_ware"));
			td.set_sum_avg_oil(rs.getDouble("_sum_avg_oil"));
			td.set_avg_speed(rs.getInt("_avg_speed"));
			td.set_max_speed(rs.getInt("_max_speed"));
			td.set_speed_info(rs.getString("_speed_info"));
			td.set_this_fastup_count(rs.getInt("_this_fastup_count"));
			td.set_this_fastlow_count(rs.getInt("_this_fastlow_count"));
			td.set_this_crook_count(rs.getInt("_this_crook_count"));
			td.set_track_info(rs.getString("_track_info"));
			td.set_travel_score(rs.getDouble("_travel_score"));
			td.set_gas_consum_des(rs.getString("_gas_consum_des"));
			td.set_drive_des(rs.getString("_drive_des"));
			td.set_speed_des(rs.getString("_speed_des"));
			td.set_travel_cost(rs.getDouble("_travel_cost"));
			td.set_oil_ware(rs.getString("_oil_ware"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return td;
	}
}
