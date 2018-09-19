package com.utils_max;

import java.util.ResourceBundle;

//No1 第一次修改
//No2 第二此 修改
/**
 * 获取配置信息 配置文件 config.xml
 * 
 * @author Administrator
 *
 */
public class ConfigUtil {

	private ConfigUtil() {
	}

	/**
	 * 获取配置文件 commons.property 参数值
	 * @param key
	 * @return
	 */
	public static String getPropertyVal(String key) {
		ResourceBundle bundle = ResourceBundle.getBundle("commons");// 获取当前的域名
		if (bundle.containsKey(key))
			return bundle.getString(key);
		return null;
	}
	
	/**
	 * 读取配置文件的 某个值
	 * @param key 字段名
	 * @param fileName 配置文件名称（config.txt 则填config）
	 * @return
	 */
	public static String getPropertyVal(String key,String fileName) {
		if(ParseUtils.isEmpty(fileName)){
			return getPropertyVal(key);
		}
		ResourceBundle bundle = ResourceBundle.getBundle(fileName);// 获取当前的域名
		if (bundle.containsKey(key))
			return bundle.getString(key);
		return null;
	}
	
}
