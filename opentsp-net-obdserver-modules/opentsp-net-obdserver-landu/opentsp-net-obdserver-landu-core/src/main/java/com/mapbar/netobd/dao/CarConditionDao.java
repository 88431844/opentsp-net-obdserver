package com.mapbar.netobd.dao;

import com.mapbar.netobd.bean.dto.CarConditionInfoTo;

public interface CarConditionDao {
	/**
	 * 插入碰撞记录信息
	 * 
	 * @param _sn
	 * @throws Exception
	 */
	public void insertCollisionAlert(String _sn) throws Exception;

	/**
	 * 根据sn查询是否有碰撞记录
	 * 
	 * @param string
	 * @return
	 * @throws Exception
	 */
	public Integer findCollisionCountFromSN(String string) throws Exception;

	/**
	 * 更新碰撞记录次数加1
	 * 
	 * @param string
	 * @throws Exception
	 */
	public void updateCollisionAlert(String string) throws Exception;

	/**
	 * 清空碰撞记录
	 * 
	 * @param string
	 * @throws Exception
	 */
	public void deleteCollisionAlert(String string) throws Exception;

	/**
	 * 根据sn查询碰撞记录信息
	 * 
	 * @param get_sn
	 * @return
	 * @throws Exception
	 */
	public CarConditionInfoTo findCarConditionInfo(String get_sn) throws Exception;

}
