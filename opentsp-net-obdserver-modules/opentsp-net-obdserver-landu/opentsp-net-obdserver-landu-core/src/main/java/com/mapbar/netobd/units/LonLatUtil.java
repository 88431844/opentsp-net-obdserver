package com.mapbar.netobd.units;

import java.awt.Point;
import java.math.BigDecimal;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.mapbar.maplet.codec.PointDecoder;
import com.mapbar.maplet.codec.PointEncoder;

/** * 02 84
 * @author yulong
 *
 */
public class LonLatUtil {
	/**
	 * 将JSON中的x, y 由84转成02
	 * @param json
	 * @return
	 */
	
	public static JSONObject encodeJsonXY(JSONObject json){
		double x = json.getDouble("x");
		double y = json.getDouble("y");
		if(x == 0.0 || y == 0.0){
			return null;
		}
		
		double[] xy = encode(x, y);
		json.put("x", new BigDecimal(xy[0]).setScale(5, BigDecimal.ROUND_HALF_UP).doubleValue());
		json.put("y", new BigDecimal(xy[1]).setScale(5, BigDecimal.ROUND_HALF_UP).doubleValue());
		return json;
	}
	
	/**
	 *  84转02加密
	 * @param x
	 * @param y
	 * @return
	 */
	public static double[] encode(double x, double y) {
		double[] xy = { x, y };
		return com.mapbar.home.codec.MapCodec.encode(xy);
	}

	/**
	 *  02转84加密
	 * @param x
	 * @param y
	 * @return
	 */
	public static double[] decode(double x, double y) {
		double[] xy = { x, y };
		return com.mapbar.home.codec.MapCodec.decode(xy);
	}
	
	/**
	 * 明文转偏移 
	 * @param lon double类型
	 * @param lat double类型
	 * @return double数组
	 */
	public static double[] plaintextToOffset(double lon, double lat){
		double[] lonlat = new double[2];
		Point poiLatLon = PointEncoder.encode(getLatLonFormPoint(lat,lon));
//		Point poiLatLon = PointDecoder.decode(getLatLonFormPoint(lat, lon));
		lonlat[1] = poiLatLon.y / 100000D;//纬度
		lonlat[0] = poiLatLon.x / 100000D;//经度
		return lonlat;
	}
	
	/**
	 * 偏移转明文
	 * @param lon double类型
	 * @param lat double类型
	 * @return double数组
	 */
	public static double[] offsetToPlaintext(double lon, double lat){
		double[] lonlat = new double[2];
//		Point poiLatLon = PointEncoder.encode(getLatLonFormPoint(lat,lon));
		Point poiLatLon = PointDecoder.decode(getLatLonFormPoint(lat, lon));
		lonlat[0] = poiLatLon.x / 100000D;
		lonlat[1] = poiLatLon.y / 100000D;
		return lonlat; 	
	}
	
	/**
	 * 明文转偏移 
	 * @param String lonlat  字符型 116.33333,39.55555
	 * @return String
	 */
	public static String plaintextToOffset(String lonlat){
		String lon = lonlat.split(",")[0];
		String lat = lonlat.split(",")[1];
		Point poiLatLon = PointEncoder.encode(getLatLonFormPoint(Double.parseDouble(lat), Double.parseDouble(lon)));
//		Point poiLatLon = PointDecoder.decode(getLatLonFormPoint(Double.parseDouble(lat), Double.parseDouble(lon)));
		return poiLatLon.x / 100000D + "," + poiLatLon.y / 100000D;
	}
	
	/**
	 * 偏移转明文
	 * @param String lonlat  字符型 116.33333,39.55555
	 * @return String
	 */
	public static String offsetToPlaintext(String lonlat){
		String lon = lonlat.split(",")[0];
		String lat = lonlat.split(",")[1];
//		Point poiLatLon = PointEncoder.encode(getLatLonFormPoint(Double.parseDouble(lat), Double.parseDouble(lon)));
		Point poiLatLon = PointDecoder.decode(getLatLonFormPoint(Double.parseDouble(lat), Double.parseDouble(lon)));
		return poiLatLon.x / 100000D + "," + poiLatLon.y / 100000D; 	
	}
	
	/**
	 * 明文转偏移 
	 * @param String lonlats  字符型 116.33333,39.55555;116.33333,39.55555;116.33333,39.55555
	 * @return String
	 */
	public static String plaintextToOffsets(String lonlats){
		String[] lls = lonlats.split(";");
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < lls.length; i++) {
			String lon = lls[i].split(",")[0];
			String lat = lls[i].split(",")[1];
			
			Point poiLatLon = PointEncoder.encode(getLatLonFormPoint(Double.parseDouble(lat), Double.parseDouble(lon)));
//			Point poiLatLon = PointDecoder.decode(getLatLonFormPoint(Double.parseDouble(lat), Double.parseDouble(lon)));
			if(i == lls.length -1){
				sb.append(poiLatLon.x / 100000D + "," + poiLatLon.y / 100000D);
			}else{
				sb.append(poiLatLon.x / 100000D + "," + poiLatLon.y / 100000D + ";");
			}
		}
		return sb.toString();
	}
	
	/**
	 * 偏移转明文
	 * @param String lonlats  字符型 116.33333,39.55555;116.33333,39.55555;116.33333,39.55555
	 * @return String
	 */
	public static String offsetToPlaintexts(String lonlats){
		String[] lls = lonlats.split(";");
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < lls.length; i++) {
			String lon = lls[i].split(",")[0];
			String lat = lls[i].split(",")[1];
//			Point poiLatLon = PointEncoder.encode(getLatLonFormPoint(Double.parseDouble(lat), Double.parseDouble(lon)));
			Point poiLatLon = PointDecoder.decode(getLatLonFormPoint(Double.parseDouble(lat), Double.parseDouble(lon)));
			if(i == lls.length -1){
				sb.append(poiLatLon.x / 100000D + "," + poiLatLon.y / 100000D);
			}else{
				sb.append(poiLatLon.x / 100000D + "," + poiLatLon.y / 100000D + ";");
			}
		}
		return sb.toString();
	}
	
	/**
	 * 将明文经纬度转换为point对象
	 * 
	 * @param dLat
	 * @param dLon
	 * @return
	 */
	public static Point getLatLonFormPoint(double dLat, double dLon) {
		Point pt = new Point();
		pt.y = (int) (dLat * 100000D);
		pt.x = (int) (dLon * 100000D);
		return pt;
	}
	
	/**
	 * 转换jsonArray中的 ，x y的经纬度，84to02
	 * @param str
	 * @return
	 */
	static int speed0 = 0;
	
	public static String encodeJSONArray(String str) {
		if(str != null && !str.equals("")){
			JSONArray array = JSONArray.fromObject(str);
			JSONArray rel = new JSONArray();
			JSONObject json = null;
			for (int i = 0; i < array.size(); i++) {
				json = array.getJSONObject(i);
				
				if(json.getInt("v") == 0){
					if(speed0 > 1){
						continue;
					}else{
						speed0++;
					}
					
				}else{
					speed0 = 0;
				}
				
				JSONObject reljson = encodeJsonXY(json);
				if(reljson != null){
					rel.add(reljson);
				}
			}
			return rel.toString();
		}
		return str;
	}

	/**
	 * 字符串经纬度84转02
	 * @param string
	 * @param string2
	 * @return
	 */
	public static double[] encodeString(String lon, String lat) {
		lon = lon.replace("E", "").replace("N", "");
		lat = lat.replace("E", "").replace("N", "");
		if(lon.equals("0.0") || lat.equals("0.0")){
			double[] xy = {0.0, 0.0};
			return xy;
		}
		double x = Double.parseDouble(lon);
		double y = Double.parseDouble(lat);
		return encode(x, y);
	}
}
