package com.mapbar.netobd.bean.dto;

import java.io.Serializable;

/**
 * @Description: 车况信息
 * @author xubh
 * @date: 2016年4月28日
 */
public class CarConditionInfoTo implements Serializable {
	private static final long serialVersionUID = -1952533228294570042L;
	private int range;// 是否在范围内
	private double voltager;// 电压
	private int collision;// 碰撞提醒次数
	private double x;// 点经度
	private double y;// 点纬度
	private String address;// 点地址

	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}

	public double getVoltager() {
		return voltager;
	}

	public void setVoltager(double voltager) {
		this.voltager = voltager;
	}

	public int getCollision() {
		return collision;
	}

	public void setCollision(int collision) {
		this.collision = collision;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
