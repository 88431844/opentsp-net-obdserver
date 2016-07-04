package com.mapbar.netobd.bean.dto;

/**
 * @description: 城市油价信息
 * @author xubh
 * @date: 2016年4月28日
 */
public class OilDataTo {
	private String _id;
	private String _area;// 城市名称
	private String _oilType;// 油价类型
	private String _price;// 价格
	private String _first_initials;// 首字母

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String get_area() {
		return _area;
	}

	public void set_area(String _area) {
		this._area = _area;
	}

	public String get_oilType() {
		return _oilType;
	}

	public void set_oilType(String _oilType) {
		this._oilType = _oilType;
	}

	public String get_price() {
		return _price;
	}

	public void set_price(String _price) {
		this._price = _price;
	}

	public String get_first_initials() {
		return _first_initials;
	}

	public void set_first_initials(String _first_initials) {
		this._first_initials = _first_initials;
	}

}
