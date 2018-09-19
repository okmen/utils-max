package com.utils_max.sms;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.utils_max.ConfigUtil;
import com.utils_max.HttpRequestHelper;

/**
 * 短信发送类
 * @author Administrator
 *
 */
public class SendSMSByMobile {
	
	/**
	 * 参考api文档 https://www.yunpian.com/api2.0/sms.html
	 * 
	 * 云片网 单条短信发送
	 */
	private  static String SINGER_URL=ConfigUtil.getPropertyVal("yp_single_url");
	/**
	 * 新增模板
	 */
//	private static String ADD_URL=ConfigUtil.getSingleValue("yp_add_url");
	/**
	 * key
	 */
	private static String APIKEY=ConfigUtil.getPropertyVal("yp_apikey");

	public static Logger loger = Logger.getLogger(SendSMSByMobile.class);
	
	
	/**
	 * 单条短信发送（验证短信）
	 * @param mobile
	 * @param content
	 * @return
	 */
	public static String sendSMS_yunpian(String mobile, String content) {
		String returnMsg = "";
		Map<String, String> map = new HashMap<String, String>();
		map.put("apikey", APIKEY);
		try {
			map.put("mobile", mobile);
			map.put("text",content);
			returnMsg = HttpRequestHelper.post_httpClient(SINGER_URL, map);
		} catch (Exception e) {
			loger.error(e);
		}
		return returnMsg;
	}
	
	/**
	 * 通知类短信发送（多个相同类容）
	 * @param mobiles 多个手机号以,隔开，一次不要超过1000条且短信内容条数必须与手机号个数相等
	 * @param content 短信内容，多个短信内容请使用UTF-8做urlencode；使用逗号分隔，一次不要超过1000条且短信内容条数必须与手机号个数相等
	 * @return
	 */
	public static String batchSend(String mobiles, String contents) {
		String returnMsg = "";
		Map<String, String> map = new HashMap<String, String>();
		map.put("apikey", APIKEY);
		try {
			map.put("mobile", mobiles);
			map.put("text",contents);
			returnMsg = HttpRequestHelper.post_httpClient("https://sms.yunpian.com/v2/sms/batch_send.json", map);
		} catch (Exception e) {
			loger.error(e); 
		}
		return returnMsg;
	}
}
