package com.utils_max.logistics;

import java.util.HashMap;

import com.utils_max.HttpRequestHelper;
import com.utils_max.encrypt.MD5Encrypt;

public class Kd100Utils {
	
//	/**
//	 * 实时快递查询接口的公司编号(customer )
//	 */
//	public static String customer = "EBB2665063CB8D9E2E69C5C580531EBC"; //
//	/**
//	 * 实时快递查询接口的授权密匙(Key)
//	 */
//	public static String key = "PDaDCgoA1580"; //
	
	/**
	 * 根据物流公司编号、快递单号 查询物流信息
	 * @param com 快递公司
	 * @param num 物流单号
	 * @param customerId
	 * @param key
	 * @return
	 */
	public static String getLogisticsInfo(String com,String num,String customerId,String key){
		String param ="{\"com\":\""+com+"\",\"num\":\""+num+"\"}";
		String sign = MD5Encrypt.encrypt(param+key+customerId);
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("param",param);
		params.put("sign",sign);
		params.put("customer",customerId);
		String resp="";
		try {
			resp=HttpRequestHelper.post_httpClient("http://poll.kuaidi100.com/poll/query.do", params);		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
		
	}
}
