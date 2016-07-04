package com.mapbar.netobd.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.mapbar.netobd.bean.dto.DayTravelTo;
import com.mapbar.netobd.bean.dto.DriveMonthTo;
import com.mapbar.netobd.bean.dto.RowMappers;
import com.mapbar.netobd.bean.dto.TravelDataTo;
import com.mapbar.netobd.dao.DriveInfoDao;
import com.mapbar.netobd.kafkaBean.KafkaTravelData;
import com.mapbar.netobd.kafkaBean.KafkaTravelInfoTo;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.InputGetDayTravel;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.InputGetMonthTravel;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.InputGetNowestTravel;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.InputGetTravelDesc;

/**
 * @description: 行程日期分布持久层
 * @author xubh
 * @date: 2016年4月28日
 */
@Repository
public class DriveInfoDaoImpl extends RowMappers implements DriveInfoDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<DriveMonthTo> findDriveFromMonth(InputGetMonthTravel info) throws Exception {
		String sql = "SELECT * FROM travel_calendar WHERE _user_id = ? and _travel_month = ? ORDER BY _travel_day";
		return jdbcTemplate.query(sql, new Object[] { info.getUserId(), info.getMonth() }, new RowMapper<DriveMonthTo>() {

			@Override
			public DriveMonthTo mapRow(ResultSet rs, int rowNum) throws SQLException {
				DriveMonthTo dmt = new DriveMonthTo();
				dmt.set_car_id(rs.getString("_car_id"));
				dmt.set_user_id(rs.getString("_user_id"));
				dmt.set_travel_day(rs.getInt("_travel_day"));
				dmt.set_trip_month(rs.getString("_travel_month"));
				dmt.set_sn(rs.getString("_sn"));
				dmt.set_travel_number(rs.getInt("_travel_number"));
				return dmt;
			}

		});
	}

	@Override
	public KafkaTravelInfoTo findTravelInfo(KafkaTravelInfoTo dit) throws Exception {
		String sql = "SELECT * FROM travel_info WHERE _sn = ? and _trip_id = ?;";
		return jdbcTemplate.query(sql, new Object[] { dit.get_sn(), dit.get_trip_id() }, new ResultSetExtractor<KafkaTravelInfoTo>() {

			@Override
			public KafkaTravelInfoTo extractData(ResultSet rs) throws SQLException, DataAccessException {
				KafkaTravelInfoTo ktit = new KafkaTravelInfoTo();
				if (rs.next()) {
					ktit.set_car_model_id(rs.getString("_car_model_id"));
				}
				return ktit;
			}

		});
	}

	@Override
	public void saveTravelData(KafkaTravelData dit) throws Exception {
		// 插入行程数据
		String sql = "INSERT INTO travel_info (" + "_id, _car_id, _user_id, _car_model_id, _obd_serial, " + "_vid, _vin, _travel_id, _travel_date, _start_time, " + "_end_time, _travel_time, _start_point, _start_lon, _start_lat, " + "_end_point, _end_lon, _end_lat, _this_travel_len, _this_avg_oil, " + "_carmodel_avg_oil, _this_oil_ware, _travel_oil_price, _avg_speed, _max_speed, "
				+ "_speed_info, _speed_sum, _speed_60, _speed_90, _speed_120, " + "_speed_121, _this_fastup_count, _this_fastlow_count, _this_crook_count, _this_idling_num, " + "_this_idling_time, _track_info, _sum_drive_range, _sum_avg_oil, _gas_consum_des, " + "_drive_des, _speed_des, _travel_cost, _travel_score, _create_time, _oil_ware) " + "VALUES (" + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
				+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " + "?, ?, ?, ?, ?, ?)";
		jdbcTemplate.update(sql,
				new Object[] { dit.get_id(), dit.get_car_id(), dit.get_user_id(), dit.get_car_model_id(), dit.get_obd_serial(), dit.get_vid(), dit.get_vin(), dit.get_travel_id(), dit.get_travel_date(), dit.get_start_time(), dit.get_end_time(), dit.get_travel_time(), dit.get_start_point(), dit.get_start_lon(), dit.get_start_lat(), dit.get_end_point(), dit.get_end_lon(), dit.get_end_lat(),
						dit.get_this_travel_len(), dit.get_this_avg_oil(), dit.get_carmodel_avg_oil(), dit.get_this_oil_ware(), dit.get_travel_oil_price(), dit.get_avg_speed(), dit.get_max_speed(), dit.get_speed_info(), dit.get_speed_sum(), dit.get_speed_60(), dit.get_speed_90(), dit.get_speed_120(), dit.get_speed_121(), dit.get_this_fastup_count(), dit.get_this_fastlow_count(),
						dit.get_this_crook_count(), dit.get_this_idling_num(), dit.get_this_idling_time(), dit.get_track_info(), dit.get_sum_drive_range(), dit.get_sum_avg_oil(), dit.get_gas_consum_des(), dit.get_drive_des(), dit.get_speed_des(), dit.get_travel_cost(), dit.get_travel_score(), System.currentTimeMillis(), dit.get_oil_ware() });

		// 写入行程日历数据
		String query = "SELECT count(*) FROM travel_calendar WHERE _user_id = ? and _travel_day = ?";
		Integer x = jdbcTemplate.queryForObject(query, new Object[] { dit.get_user_id(), dit.get_travel_date() }, Integer.class);

		if (x > 0) {
			String update = "UPDATE travel_calendar SET _travel_number = _travel_number + 1 WHERE _user_id = ? and _travel_day = ?";
			jdbcTemplate.update(update, new Object[] { dit.get_user_id(), dit.get_travel_date() });
		} else {
			String insert = "INSERT INTO travel_calendar (_user_id, _car_id, _sn, _travel_month, _travel_day, _travel_number) VALUES (?, ?, ?, ?, ?, ?)";
			jdbcTemplate.update(insert, new Object[] { dit.get_user_id(), dit.get_car_id(), dit.get_obd_serial(), dit.get_travel_date().subSequence(0, 6), dit.get_travel_date(), 1 });
		}

		// 更新最后一次使用时间
		String upLastUserTime = "UPDATE neobd_user_car_info SET _last_user_time = ? where _user_id = ? and _is_delete = 0";
		jdbcTemplate.update(upLastUserTime, new Object[] { dit.get_end_time(), dit.get_user_id() });

	}

	@Override
	public TravelDataTo findTravelData(InputGetNowestTravel info) throws Exception {
		String sql = "SELECT * FROM travel_info WHERE _user_id = ? ORDER BY _start_time DESC limit 1";
		return jdbcTemplate.query(sql, new Object[] { info.getUserId() }, new ResultSetExtractor<TravelDataTo>() {

			@Override
			public TravelDataTo extractData(ResultSet rs) throws SQLException, DataAccessException {
				TravelDataTo td = new TravelDataTo();
				if (rs.next()) {
					td = getTravelData(rs);
				}
				return td;
			}
		});
	}

	@Override
	public TravelDataTo findTravelDataFromId(InputGetTravelDesc info) throws Exception {
		String sql = "SELECT * FROM travel_info WHERE _id = ?";
		return jdbcTemplate.query(sql, new Object[] { info.getTravelId() }, new ResultSetExtractor<TravelDataTo>() {

			@Override
			public TravelDataTo extractData(ResultSet rs) throws SQLException, DataAccessException {
				TravelDataTo td = new TravelDataTo();
				if (rs.next()) {
					td = getTravelData(rs);
				}
				return td;
			}
		});
	}

	@Override
	public List<DayTravelTo> findDayTravel(InputGetDayTravel info) throws Exception {
		String sql = "SELECT _id, _start_point, _end_point, _start_time, _end_time FROM neobd_info.travel_info WHERE _user_id = ? and _travel_date = ? order by _start_time desc";
		return jdbcTemplate.query(sql, new Object[] { info.getUserId(), info.getTravelDay() }, new RowMapper<DayTravelTo>() {

			@Override
			public DayTravelTo mapRow(ResultSet rs, int rowNum) throws SQLException {
				DayTravelTo dt = new DayTravelTo();
				dt.set_id(rs.getString("_id"));
				dt.set_start_point(rs.getString("_start_point"));
				dt.set_end_point(rs.getString("_end_point"));
				dt.set_start_time(rs.getLong("_start_time"));
				dt.set_end_time(rs.getLong("_end_time"));
				return dt;
			}

		});
	}

	@Override
	public List<Map<String, Object>> getSpeedSet() throws Exception {
		String sql = "SELECT * FROM speed_range_set";
		return jdbcTemplate.queryForList(sql);
	}
}
