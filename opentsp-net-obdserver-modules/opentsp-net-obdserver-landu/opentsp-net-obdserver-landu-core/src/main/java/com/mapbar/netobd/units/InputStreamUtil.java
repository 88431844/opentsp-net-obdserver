package com.mapbar.netobd.units;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.log4j.Logger;
import security.OBD2Security;

/**
 * 
 * @author lichao
 * 用于接收流数据
 */
public class InputStreamUtil {
	private static Logger logger = Logger.getLogger(InputStreamUtil.class);
	/**
	 * 
	 * @param stream	输入的流
	 * @return
	 */
	public static byte[] getByte(InputStream stream){
		byte return_content[] = null;
		if(stream == null){
			logger.info("上传的数据流为null");
			return return_content;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			byte bs[] = new byte[1024];
			int count = stream.read(bs);
			while(count!=-1){
				out.write(bs, 0, count);
				count = stream.read(bs);
			}
			byte content_byte[] = out.toByteArray(); 
			if(OBD2Security.isValid(content_byte)){
				return_content= OBD2Security.OBDDecode(content_byte);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				out.close();
				stream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return return_content;
	}
}
