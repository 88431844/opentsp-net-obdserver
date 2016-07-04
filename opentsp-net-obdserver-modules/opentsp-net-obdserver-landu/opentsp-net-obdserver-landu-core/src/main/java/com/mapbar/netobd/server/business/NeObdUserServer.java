package com.mapbar.netobd.server.business;

import com.mapbar.netobd.bean.dto.NeObdPushBindTo;
import com.mapbar.netobd.bean.dto.NeObdUserCarInfoTo;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.InputGetPushRemind;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.InputGetVehicleFile;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.InputRemoveSnBinding;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.InputSetBindDrive;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.InputSetCarInfo;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.InputSetPushBind;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.InputSetPushRemind;
import com.mapbar.netobd.protocolBuffer.ProtoBufferClass.InputSetVehicleFile;

public interface NeObdUserServer {
	/**
	 * 保存用户信息
	 * 
	 * @param info
	 * @param guid
	 * @return
	 * @throws Exception
	 */
	public NeObdUserCarInfoTo saveUserInfo(InputSetCarInfo info, String guid) throws Exception;

	/**
	 * 根据用户id查询用户信息
	 * 
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public NeObdUserCarInfoTo findUserInfoFromUserId(InputGetVehicleFile info) throws Exception;

	public String setUserVehicleFile(InputSetVehicleFile info, String guid) throws Exception;

	/**
	 * 保存用户绑定关系
	 * 
	 * @param info
	 * @param guid
	 * @return
	 * @throws Exception
	 */
	public Integer setUserBindDrive(InputSetBindDrive info, String guid) throws Exception;

	/**
	 * 根据用户id查询推送绑定关系,维护用户绑定关系
	 * 
	 * @param info
	 * @param guid
	 * @throws Exception
	 */
	public void setUserPushBind(InputSetPushBind info, String guid) throws Exception;

	/**
	 * 根据用户id修改绑定开关
	 * 
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public Integer setPushOnOff(InputSetPushRemind info) throws Exception;

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
	public Integer RemoveSnBinding(InputRemoveSnBinding info) throws Exception;

	/**
	 * 根据用户id查询用户信息
	 * 
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public String findUserVehicleFileFromUserId(InputSetVehicleFile info) throws Exception;

}
