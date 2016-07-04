package com.mapbar.netobd.server.base.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mapbar.netobd.bean.dto.CarModelInfoTo;
import com.mapbar.netobd.bean.dto.CheckItemTo;
import com.mapbar.netobd.bean.dto.OilDataTo;
import com.mapbar.netobd.dao.BaseDateDao;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.InputGetOilPrice;
import com.mapbar.netobd.server.base.BaseDateServer;

/**
 * @description: 油价相关业务层
 * @author xubh
 * @date: 2016年4月28日
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class BaseDateServerImpl implements BaseDateServer {

	@Autowired
	private BaseDateDao baseDateDao;

	@Override
	public CarModelInfoTo getCarFormId(String car_model_id) throws Exception {
		return baseDateDao.getCarFormId(car_model_id);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
	public void saveOilPrice(List<OilDataTo> oil_list) throws Exception {
		baseDateDao.saveOilPrice(oil_list);
	}

	@Override
	public OilDataTo findOilPrice(String area, int type) throws Exception {
		return baseDateDao.findOilPrice(area, type);
	}

	@Override
	public List<OilDataTo> findOilAreaList() throws Exception {
		return baseDateDao.findOilAreaList();
	}

	@Override
	public List<OilDataTo> findOilFromArea(InputGetOilPrice info) throws Exception {
		return baseDateDao.findOilFromArea(info);
	}

	@Override
	public List<CheckItemTo> findCheckItem() throws Exception {
		return baseDateDao.findCheckItem();
	}

}
