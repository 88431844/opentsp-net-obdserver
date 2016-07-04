package com.mapbar.netobd.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.mapbar.netobd.bean.dto.CarModelInfoTo;
import com.mapbar.netobd.bean.dto.CheckItemTo;
import com.mapbar.netobd.bean.dto.OilDataTo;
import com.mapbar.netobd.bean.dto.RowMappers;
import com.mapbar.netobd.dao.BaseDateDao;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.InputGetOilPrice;

/**
 * @description: 油价相关访问数据库层
 * @author xubh
 * @date: 2016年4月28日
 */
@Repository
public class BaseDateDaoImpl extends RowMappers implements BaseDateDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public CarModelInfoTo getCarFormId(String car_model_id) throws Exception {
		String sql = "SELECT * FROM base_carmodel where _id = ?";
		return jdbcTemplate.query(sql, new Object[] { car_model_id }, resultSetExtrCarInfoTo());
	}

	@Override
	public void saveOilPrice(List<OilDataTo> oil_list) throws Exception {
		String del = "DELETE FROM oil_price";
		jdbcTemplate.update(del);

		final List<OilDataTo> list = oil_list;
		String sql = "INSERT INTO oil_price(_id, _area, _oilType, _price, _first_initials) VALUES (?,?,?,?,?)";

		jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				OilDataTo odt = list.get(i);
				ps.setString(1, odt.get_id());
				ps.setString(2, odt.get_area());
				ps.setString(3, odt.get_oilType());
				ps.setString(4, odt.get_price());
				ps.setString(5, odt.get_first_initials());
			}

			@Override
			public int getBatchSize() {
				return list.size();
			}
		});
		// logger.info("同步油价入库完成");
	}

	@Override
	public OilDataTo findOilPrice(String area, int type) throws Exception {
		// logger.info("查询油价信息:" + area + " " + type);
		String sql = "SELECT * FROM oil_price WHERE _area = ? and _oilType = ?";
		return jdbcTemplate.query(sql, new Object[] { area, type }, new ResultSetExtractor<OilDataTo>() {

			@Override
			public OilDataTo extractData(ResultSet rs) throws SQLException, DataAccessException {
				OilDataTo odt = new OilDataTo();
				if (rs.next()) {
					odt.set_id(rs.getString("_id"));
					odt.set_area(rs.getString("_area"));
					odt.set_oilType(rs.getString("_oilType"));
					odt.set_price(rs.getString("_price"));
				}
				return odt;
			}
		});
	}

	@Override
	public List<OilDataTo> findOilAreaList() throws Exception {
		String sql = "SELECT distinct _area, _first_initials FROM oil_price ORDER BY _first_initials";
		return jdbcTemplate.query(sql, new RowMapper<OilDataTo>() {

			@Override
			public OilDataTo mapRow(ResultSet rs, int rowNum) throws SQLException {
				OilDataTo odt = new OilDataTo();
				odt.set_area(rs.getString("_area"));
				odt.set_first_initials(rs.getString("_first_initials"));
				return odt;
			}
		});
	}

	@Override
	public List<OilDataTo> findOilFromArea(InputGetOilPrice info) throws Exception {
		String sql = "SELECT * FROM oil_price WHERE _area = ?";
		return jdbcTemplate.query(sql, new Object[] { info.getCityName() }, new RowMapper<OilDataTo>() {

			@Override
			public OilDataTo mapRow(ResultSet rs, int rowNum) throws SQLException {
				OilDataTo odt = new OilDataTo();
				odt.set_id(rs.getString("_id"));
				odt.set_area(rs.getString("_area"));
				odt.set_first_initials(rs.getString("_first_initials"));
				odt.set_oilType(rs.getString("_oilType"));
				odt.set_price(rs.getString("_price"));
				return odt;
			}

		});
	}

	@Override
	public List<CheckItemTo> findCheckItem() throws Exception {
		String sql = "SELECT * FROM check_item";
		return jdbcTemplate.query(sql, new RowMapper<CheckItemTo>() {

			@Override
			public CheckItemTo mapRow(ResultSet rs, int rowNum) throws SQLException {
				CheckItemTo checkItemTo = new CheckItemTo();
				checkItemTo.set_item_name(rs.getString("_item_name"));
				checkItemTo.set_item_explain(rs.getString("_item_explain"));
				checkItemTo.set_item_refe(rs.getString("_item_refe"));
				checkItemTo.set_item_title(rs.getString("_item_title"));
				checkItemTo.set_refe_max(rs.getDouble("_refe_max"));
				checkItemTo.set_refe_min(rs.getDouble("_refe_min"));
				checkItemTo.set_item_scoure(rs.getDouble("_item_scoure"));
				return checkItemTo;
			}

		});
	}

}
