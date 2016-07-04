package com.mapbar.netobd.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.mapbar.netobd.bean.dto.NeObdPushBindTo;
import com.mapbar.netobd.bean.dto.NeObdUserCarInfoTo;
import com.mapbar.netobd.bean.dto.RowMappers;
import com.mapbar.netobd.dao.UserDao;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.InputGetPushRemind;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.InputRemoveSnBinding;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.InputSetBindDrive;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.InputSetPushBind;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.InputSetPushRemind;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.InputSetVehicleFile;

/**
 * @description: 用户信息
 * @author xubh
 * @date: 2016年4月28日
 */
@Repository
public class UserDaoImpl extends RowMappers implements UserDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public Integer findFromVin(String vin) throws Exception {
		String sql = "SELECT count(*) FROM neobd_user_car_info where _vin_code = ?";
		return jdbcTemplate.queryForObject(sql, new Object[] { vin }, Integer.class);
	}

	@Override
	public Integer findFormUserId(String userId) throws Exception {
		String sql_1 = "SELECT count(*) FROM neobd_user_car_info where _user_id = ? and _is_delete = 0";
		return jdbcTemplate.queryForObject(sql_1, new Object[] { userId }, Integer.class);
	}

	@Override
	public int saveUserInfo(NeObdUserCarInfoTo noucit) throws Exception {
		String sql = "INSERT INTO neobd_user_car_info(_id, _user_id, _obd_state, _vin_code, _car_model_id, " + "_phone, _account, _car_code, _modify_time, _create_time, _is_custom_oil, " + "_set_oil_area, _set_oil_type, _set_oil_price, _sim_state, _is_delete, _guid, _product, _hd_type) values" + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		return jdbcTemplate.update(sql, new Object[] { noucit.get_id(), noucit.get_user_id(), noucit.get_obd_state(), noucit.get_vin_code(), noucit.get_car_model_id(), noucit.get_phone(), noucit.get_account(), noucit.get_car_code(), noucit.get_modify_time(), noucit.get_create_time(), noucit.get_is_custom_oil(), noucit.get_set_oil_area(), noucit.get_set_oil_type(), noucit.get_set_oil_price(),
				noucit.get_sim_state(), noucit.get_is_delete(), noucit.get_guid(), noucit.get_product(), noucit.get_hd_type() });
	}

	@Override
	public NeObdUserCarInfoTo getUserInfo(String sn) throws Exception {
		String sql = "SELECT * FROM neobd_user_car_info where _sn = ?";
		return jdbcTemplate.query(sql, new Object[] { sn }, new ResultSetExtractor<NeObdUserCarInfoTo>() {

			@Override
			public NeObdUserCarInfoTo extractData(ResultSet rs) throws SQLException, DataAccessException {
				NeObdUserCarInfoTo noucit = new NeObdUserCarInfoTo();
				if (rs.next()) {
					noucit.set_car_model_id(rs.getString("_car_model_id"));
					noucit.set_id(rs.getString("_id"));
					noucit.set_user_id(rs.getString("_user_id"));
					noucit.set_set_oil_price(rs.getDouble("_set_oil_price"));
				}
				return noucit;
			}

		});
	}

	@Override
	public NeObdUserCarInfoTo findUserInfoFromId(NeObdUserCarInfoTo noucit) throws Exception {
		String sql = "SELECT * FROM neobd_user_car_info WHERE _id = ?";
		return jdbcTemplate.query(sql, new Object[] { noucit.get_id() }, resultSetExtrUserInfo());
	}

	public List<Map<String, Object>> findCarmodelFromSn(String sn) throws Exception {
		String sql = "SELECT nuci._car_model_id, bc._m_f_brand, bc._m_t_brand, bc._m_model, bc._m_year, bc._displacement_liter FROM neobd_user_car_info nuci, base_carmodel bc WHERE nuci._car_model_id = bc._id and _sn = ? and _is_delete = 0 ORDER BY _create_time desc";
		return jdbcTemplate.queryForList(sql, new Object[] { sn });
	}

	@Override
	public NeObdUserCarInfoTo findUserInfoFromUserId(String user_id) throws Exception {
		String sql = "SELECT * FROM neobd_user_car_info WHERE _user_id = ? and _is_delete = 0";
		return jdbcTemplate.query(sql, new Object[] { user_id }, resultSetExtrUserInfo());
	}

	@Override
	public NeObdUserCarInfoTo findUserVehicleFile(InputSetVehicleFile info) throws Exception {
		String sql = "SELECT * FROM neobd_user_car_info WHERE _user_id = ? and _is_delete = 0";
		return jdbcTemplate.query(sql, new Object[] { info.getUserId() }, resultSetExtrUserVehicle());
	}

	@Override
	public void copyUserVehicleFile(NeObdUserCarInfoTo query_user) throws Exception {
		String sql = "UPDATE neobd_user_car_info set _is_delete = 1 WHERE _user_id = ? and _id = ?";
		jdbcTemplate.update(sql, new Object[] { query_user.get_user_id(), query_user.get_id() });
	}

	@Override
	public Integer saveUserVehicleFile(NeObdUserCarInfoTo query_user) throws Exception {
		String sql = "INSERT INTO neobd_user_car_info(" + "_id, _user_id, _sn, _obd_state," + "_hd_type, _vin_code, _car_model_id," + "_phone, _account, _car_code, " + "_engine_number, _bind_time, _modify_time," + "_create_time, _is_custom_oil, _set_oil_area, " + "_set_oil_type, _set_oil_price, _last_user_time,"
				+ "_sim_state, _is_delete, _guid, _product) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		return jdbcTemplate.update(sql, new Object[] { query_user.get_id(), query_user.get_user_id(), query_user.get_sn(), query_user.get_obd_state(), query_user.get_hd_type(), query_user.get_vin_code(), query_user.get_car_model_id(), query_user.get_phone(), query_user.get_account(), query_user.get_car_code(), query_user.get_engine_number(), query_user.get_bind_time(),
				query_user.get_modify_time(), query_user.get_create_time(), query_user.get_is_custom_oil(), query_user.get_set_oil_area(), query_user.get_set_oil_type(), query_user.get_set_oil_price(), query_user.get_last_user_time(), query_user.get_sim_state(), query_user.get_is_delete(), query_user.get_guid(), query_user.get_product() });
	}

	@Override
	public NeObdUserCarInfoTo findUserBindDrive(InputSetBindDrive info) throws Exception {
		String sql = "SELECT * FROM neobd_user_car_info WHERE _user_id = ? and _is_delete = 0";
		return jdbcTemplate.query(sql, new Object[] { info.getUserId() }, resultSetExtrUserVehicle());
	}

	@Override
	public void userPushBind(InputSetPushBind info) throws Exception {
		String sql = "SELECT count(*) FROM push_bind WHERE _user_id = ?";
		Integer x = jdbcTemplate.queryForObject(sql, new Object[] { info.getUserId() }, Integer.class);

		String update = "";
		if (x > 0) {
			update = "UPDATE push_bind SET _push_token = ?, _modify_time = ?, _product = ? WHERE _user_id = ?";
			jdbcTemplate.update(update, new Object[] { info.getPushToken(), System.currentTimeMillis(), info.getProduct(), info.getUserId() });
		} else {
			update = "INSERT INTO push_bind(_car_id, _user_id, _push_token, _product, _create_time, _modify_time)" + "VALUES(?, ?, ?, ?, ?, ?)";
			jdbcTemplate.update(update, new Object[] { info.getCarId(), info.getUserId(), info.getPushToken(), info.getProduct(), System.currentTimeMillis(), System.currentTimeMillis() });
		}

	}

	@Override
	public void userPushBind2(NeObdUserCarInfoTo noucit) throws Exception {
		String sql = "SELECT count(*) FROM push_bind WHERE _user_id = ?";
		Integer x = jdbcTemplate.queryForObject(sql, new Object[] { noucit.get_user_id() }, Integer.class);

		String update = "";
		if (x > 0) {
			update = "UPDATE push_bind SET _modify_time = ? WHERE _user_id = ?";
			jdbcTemplate.update(update, new Object[] { System.currentTimeMillis(), noucit.get_user_id() });
		} else {
			update = "INSERT INTO push_bind(_car_id, _user_id, _push_token, _product, _create_time, _modify_time)" + "VALUES(?, ?, ?, ?, ?, ?)";
			jdbcTemplate.update(update, new Object[] { noucit.get_id(), noucit.get_user_id(), "", noucit.get_product(), System.currentTimeMillis(), System.currentTimeMillis() });
		}

	}

	@Override
	public Integer setPushOnoff(InputSetPushRemind info) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("UPDATE push_bind set");

		sb.append(" _offline_push = " + info.getOfflinePush());

		sb.append(", _ignition_push = " + info.getIgnitionPush());

		sb.append(", _voltages_push = " + info.getVoltagesPush() + ", _set_voltages = " + info.getSetVoltages());

		sb.append(",  _shock_push = " + info.getShockPush());

		sb.append(", _modify_time = ? WHERE _user_id = ?");

		return jdbcTemplate.update(sb.toString(), new Object[] { System.currentTimeMillis(), info.getUserId() });
	}

	@Override
	public List<Map<String, Object>> findUserIdFromVin(String vin) throws Exception {
		String sql = "SELECT _user_id FROM neobd_user_car_info WHERE _vin_code = ?";
		return jdbcTemplate.queryForList(sql, new Object[] { vin });
	}

	@Override
	public Integer findUserInfoFromSn(String sn) throws Exception {
		String sql = "SELECT count(*) FROM neobd_user_car_info WHERE _sn = ?";
		return jdbcTemplate.queryForObject(sql, new Object[] { sn },Integer.class);
	}

	@Override
	public NeObdPushBindTo findUserPushRemind(InputGetPushRemind info) throws Exception {
		String sql = "SELECT * FROM push_bind WHERE _user_id = ?";
		return jdbcTemplate.query(sql, new Object[] { info.getUserId() }, new ResultSetExtractor<NeObdPushBindTo>() {

			@Override
			public NeObdPushBindTo extractData(ResultSet rs) throws SQLException, DataAccessException {
				NeObdPushBindTo nopbt = new NeObdPushBindTo();
				if (rs.next()) {
					nopbt.set_car_id(rs.getString("_car_id"));
					nopbt.set_user_id(rs.getString("_user_id"));
					nopbt.set_id(rs.getString("_id"));
					nopbt.set_ignition_push(rs.getInt("_ignition_push"));
					nopbt.set_offline_push(rs.getInt("_offline_push"));
					nopbt.set_product(rs.getString("_product"));
					nopbt.set_push_token(rs.getString("_push_token"));
					nopbt.set_set_voltages(rs.getDouble("_set_voltages"));
					nopbt.set_shock_push(rs.getInt("_shock_push"));
					nopbt.set_voltages_push(rs.getInt("_voltages_push"));
				}
				return nopbt;
			}

		});
	}

	@Override
	public Integer removeSnBinding(InputRemoveSnBinding info) throws Exception {
		String sql = "UPDATE neobd_user_car_info set _sn = '' WHERE _sn = ? and _user_id = ?";
		return jdbcTemplate.update(sql, new Object[] { info.getSn(), info.getUserId() });
	}

	@Override
	public NeObdPushBindTo findPushSetInfo(String sn) throws Exception {
		String sql = "SELECT pb.* FROM push_bind pb, neobd_user_car_info info WHERE pb._user_id = info._user_id and info._is_delete = 0 and info._sn = ?";
		return jdbcTemplate.query(sql, new Object[] { sn }, new ResultSetExtractor<NeObdPushBindTo>() {

			@Override
			public NeObdPushBindTo extractData(ResultSet rs) throws SQLException, DataAccessException {
				NeObdPushBindTo nopbt = new NeObdPushBindTo();
				if (rs.next()) {
					nopbt.set_car_id(rs.getString("_car_id"));
					nopbt.set_user_id(rs.getString("_user_id"));
					nopbt.set_id(rs.getString("_id"));
					nopbt.set_ignition_push(rs.getInt("_ignition_push"));
					nopbt.set_offline_push(rs.getInt("_offline_push"));
					nopbt.set_product(rs.getString("_product"));
					nopbt.set_push_token(rs.getString("_push_token"));
					nopbt.set_set_voltages(rs.getDouble("_set_voltages"));
					nopbt.set_shock_push(rs.getInt("_shock_push"));
					nopbt.set_voltages_push(rs.getInt("_voltages_push"));
					nopbt.set_shock_active(rs.getInt("_shock_active"));
				}
				return nopbt;
			}

		});
	}

	@Override
	public void updateObdState(int i, String sn) throws Exception {
		String sql = "UPDATE neobd_user_car_info SET _obd_state = ? WHERE _sn = ?";
		jdbcTemplate.update(sql, new Object[] { i, sn });
	}

	@Override
	public void updateShockActive(int i, String sn) {
		String sql = "UPDATE push_bind SET _shock_active = ? WHERE _user_id = (SELECT distinct _user_id FROM neobd_user_car_info where _sn = ? and _is_delete = 0)";
		jdbcTemplate.update(sql, new Object[] { i, sn });
	}
}
