package com.mapbar.netobd.bean.dto;

/**
 * @description: 体检项目列表
 * @author xubh
 * @date: 2016年4月28日
 */
public class CheckItemTo {
	private String _item_name;// 项目对应字段名称
	private String _item_title;// 项目名称
	private String _item_explain;// 项目说明
	private String _item_refe;// 正常值区间
	private double _refe_max;// 区间最大值
	private double _refe_min;// 区间最小值
	private double _item_scoure;// 项目分数

	public String get_item_name() {
		return _item_name;
	}

	public void set_item_name(String _item_name) {
		this._item_name = _item_name;
	}

	public String get_item_explain() {
		return _item_explain;
	}

	public void set_item_explain(String _item_explain) {
		this._item_explain = _item_explain;
	}

	public String get_item_refe() {
		return _item_refe;
	}

	public void set_item_refe(String _item_refe) {
		this._item_refe = _item_refe;
	}

	public String get_item_title() {
		return _item_title;
	}

	public void set_item_title(String _item_title) {
		this._item_title = _item_title;
	}

	public double get_refe_max() {
		return _refe_max;
	}

	public void set_refe_max(double _refe_max) {
		this._refe_max = _refe_max;
	}

	public double get_refe_min() {
		return _refe_min;
	}

	public void set_refe_min(double _refe_min) {
		this._refe_min = _refe_min;
	}

	public double get_item_scoure() {
		return _item_scoure;
	}

	public void set_item_scoure(double _item_scoure) {
		this._item_scoure = _item_scoure;
	}
}
