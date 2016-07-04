package com.mapbar.netobd.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.mapbar.netobd.bean.dto.CarConditionInfoTo;
import com.mapbar.netobd.dao.CarConditionDao;

/**
 * @description: 碰撞记录持久层
 * @author xubh
 * @date: 2016年4月28日
 */
@Repository
public class CarConditionDaoImpl implements CarConditionDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public void insertCollisionAlert(String _sn) throws Exception {
		String sql = "INSERT INTO collision_alert (_sn, _coll_num) values (?, ?)";
		jdbcTemplate.update(sql, new Object[] { _sn, 1 });
	}

	@Override
	public Integer findCollisionCountFromSN(String _sn) throws Exception {
		String sql = "SELECT count(*) FROM collision_alert WHERE _sn = ?";
		return jdbcTemplate.queryForObject(sql, new Object[] { _sn }, Integer.class);
	}

	@Override
	public void updateCollisionAlert(String _sn) throws Exception {
		String sql = "UPDATE collision_alert SET _coll_num = _coll_num+1 WHERE _sn = ?";
		jdbcTemplate.update(sql, new Object[] { _sn });

	}

	@Override
	public void deleteCollisionAlert(String _sn) throws Exception {
		String sql = "UPDATE collision_alert set _coll_num = 0 WHERE _sn = ?";
		jdbcTemplate.update(sql, new Object[] { _sn });
	}

	@Override
	public CarConditionInfoTo findCarConditionInfo(String _sn) throws Exception {
		String sql = "SELECT * FROM collision_alert WHERE _sn = ?";
		return jdbcTemplate.query(sql, new Object[] { _sn }, new ResultSetExtractor<CarConditionInfoTo>() {

			@Override
			public CarConditionInfoTo extractData(ResultSet rs) throws SQLException, DataAccessException {
				CarConditionInfoTo ccit = new CarConditionInfoTo();
				if (rs.next()) {
					ccit.setCollision(rs.getInt("_coll_num"));
				}
				return ccit;
			}

		});
	}
}
