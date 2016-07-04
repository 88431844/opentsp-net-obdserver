package com.mapbar.netobd.dao;

import java.util.List;
import java.util.Map;

import com.mapbar.netobd.bean.dto.NeObdPushBindTo;
import com.mapbar.netobd.bean.dto.NeObdUserCarInfoTo;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.InputGetPushRemind;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.InputRemoveSnBinding;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.InputSetBindDrive;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.InputSetPushBind;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.InputSetPushRemind;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.InputSetVehicleFile;

public interface UserDao {
	/**
	 * 查询指定的VIN数据条数
	 * 
	 * @param vin
	 * @return
	 * @throws Exception
	 */
	public Integer findFromVin(String vin) throws Exception;

	/**
	 * 查询指定_user_id并且可用的数据条数
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public Integer findFormUserId(String userId) throws Exception;

	/**
	 * 插入用户信息
	 * 
	 * @param noucit
	 * @return
	 * @throws Exception
	 */
	public int saveUserInfo(NeObdUserCarInfoTo noucit) throws Exception;

	/**
	 * 根据 sn查询用户信息
	 * 
	 * @param get_sn
	 * @return
	 * @throws Exception
	 */
	public NeObdUserCarInfoTo getUserInfo(String get_sn) throws Exception;

	/**
	 * 根据id查询用户信息
	 * 
	 * @param noucit
	 * @return
	 * @throws Exception
	 */
	public NeObdUserCarInfoTo findUserInfoFromId(NeObdUserCarInfoTo noucit) throws Exception;

	/**
	 * 根据用户id查询用户信息
	 * 
	 * @param user_id
	 * @return
	 * @throws Exception
	 */
	public NeObdUserCarInfoTo findUserInfoFromUserId(String user_id) throws Exception;

	/**
	 * 根据用户id查询用户信息
	 * 
	 * @param user_id
	 * @return
	 * @throws Exception
	 */
	public NeObdUserCarInfoTo findUserVehicleFile(InputSetVehicleFile info) throws Exception;

	/**
	 * 关闭用户信息
	 * 
	 * @param query_user
	 * @throws Exception
	 */
	public void copyUserVehicleFile(NeObdUserCarInfoTo query_user) throws Exception;

	/**
	 * 保存用户信息
	 * 
	 * @param query_user
	 * @return
	 * @throws Exception
	 */
	public Integer saveUserVehicleFile(NeObdUserCarInfoTo query_user) throws Exception;

	/**
	 * 根据用户id查询用户信息
	 * 
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public NeObdUserCarInfoTo findUserBindDrive(InputSetBindDrive info) throws Exception;

	/**
	 * 根据用户id查询推送绑定关系,维护用户绑定关系
	 * 
	 * @param info
	 * @throws Exception
	 */
	public void userPushBind(InputSetPushBind info) throws Exception;

	/**
	 * 修改绑定开关
	 * 
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public Integer setPushOnoff(InputSetPushRemind info) throws Exception;

	/**
	 * 根据 vin查询用户信息
	 * 
	 * @param vin
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> findUserIdFromVin(String vin) throws Exception;

	/**
	 * 根据 sn 查询用户数量
	 * 
	 * @param sn
	 * @return
	 * @throws Exception
	 */
	public Integer findUserInfoFromSn(String sn) throws Exception;

	/**
	 * 根据 用户id查询绑定信息
	 * 
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public NeObdPushBindTo findUserPushRemind(InputGetPushRemind info) throws Exception;

	/**
	 * 清空sn 根据用户id和sn
	 * 
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public Integer removeSnBinding(InputRemoveSnBinding info) throws Exception;

	public void userPushBind2(NeObdUserCarInfoTo noucit) throws Exception;

	/**
	 * 根据sn查询推送信息
	 * 
	 * @param sn
	 * @return
	 * @throws Exception
	 */
	public NeObdPushBindTo findPushSetInfo(String sn) throws Exception;

	/**
	 * 更新obd状态信息,OBD是否被拔出
	 * 
	 * @param i
	 * @param sn
	 * @throws Exception
	 */
	public void updateObdState(int i, String sn) throws Exception;

	/**
	 * 更新震动提醒激活状态
	 * 
	 * @param i
	 * @param sn
	 */
	public void updateShockActive(int i, String sn);
}
