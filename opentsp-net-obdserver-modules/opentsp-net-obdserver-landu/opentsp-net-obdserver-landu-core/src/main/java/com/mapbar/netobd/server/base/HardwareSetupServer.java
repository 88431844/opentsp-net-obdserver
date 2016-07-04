package com.mapbar.netobd.server.base;

import java.util.List;
import java.util.Map;

public interface HardwareSetupServer {

	public List<Map<String, Object>> findCarModelFromSn(String sn) throws Exception;

}
