package com.mapbar.netobd.task;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.mapbar.netobd.bean.dto.OilDataTo;
import com.mapbar.netobd.units.CustomUnits;
import com.mapbar.netobd.units.PinYin2Abbreviation;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @description: 计划任务
 * @author xubh
 * @date: 2016年5月3日
 */
@Component
public class SynBaseData {

	private static final Logger logger = LoggerFactory.getLogger(SynBaseData.class);

	@Value("${oil_data:http://obdms.mapbar.com/obd_web/agency/oil/query?}")
	private String synOilUrl;

	@Autowired
	private CustomUnits customUnits;

	/**
	 * 同步油价数据
	 */
	@Scheduled(cron = "0 */30 *  * * * ")
	public void synOilData() {
		String url = synOilUrl + "page=1&rows=1";
		JSONObject json = customUnits.getJsonFromUrl(url);
		int total = json.getInt("total");

		logger.info("油价数据总条数：" + total);

		List<OilDataTo> oil_list = new ArrayList<OilDataTo>();

		String url2 = synOilUrl + "page=" + 1 + "&rows=" + total;

		logger.info("url:" + url2);

		JSONObject json2 = customUnits.getJsonFromUrl(url2);

		JSONArray jsona = json2.getJSONArray("rows");
		for (int j = 0; j < jsona.size(); j++) {
			OilDataTo od = new OilDataTo();
			JSONObject jsona_j = jsona.getJSONObject(j);

			od.set_id(jsona_j.getString("id"));
			od.set_area(jsona_j.getString("area"));
			od.set_oilType(jsona_j.getString("oilType"));
			od.set_price(jsona_j.getString("price"));

			if (jsona_j.getString("area").contains("重庆")) {
				od.set_first_initials("C");
			} else if (jsona_j.getString("area").contains("衢州")) {
				od.set_first_initials("Q");
			} else {
				String pinyin = PinYin2Abbreviation.cn2py(jsona_j.getString("area"));
				if (pinyin != null && !pinyin.equals("")) {
					od.set_first_initials(pinyin.substring(0, 1).toUpperCase());
				}
			}

			oil_list.add(od);
		}
		try {
			customUnits.saveOilType(oil_list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
