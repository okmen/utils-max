package com.utils_max.wechat;

import com.utils_max.HttpRequestHelper;
import com.utils_max.ParseUtils;
import com.utils_max.wechat.vo.WxUserInfo;

import net.sf.json.JSONObject;

public class WxUserInfoUtils {

	/**
	 * 获取用户微信信息
	 * @param openId
	 * @return
	 */
	public static WxUserInfo getUserInfo(String openId,String appId,String appSecret){
		String userInfoUrl = "https://api.weixin.qq.com/cgi-bin/user/info";
		String dataParam = "access_token=" + AccessTokenUtils.getAccessToken(appId,appSecret) + "&openid=" + openId;
		String resultStr= HttpRequestHelper.sendPost(userInfoUrl, dataParam);
		
		JSONObject userJson = JSONObject.fromObject(resultStr);
		if (userJson != null) {
			WxUserInfo resultInfo=new WxUserInfo();
			int subscribe=ParseUtils.parseInt(userJson.getString("subscribe"));
			resultInfo.setSubscribe(subscribe);
			resultInfo.setOpenid(openId);
		
			if(!ParseUtils.isEmpty(userJson.get("nickname"))){
				String nickname=ParseUtils.filterEmojiNew(String.valueOf(userJson.get("nickname")));
				resultInfo.setNickname(nickname);
			}
			if(!ParseUtils.isEmpty(userJson.get("headimgurl"))){
				resultInfo.setHeadimgurl(userJson.getString("headimgurl"));  
			}
			if(!ParseUtils.isEmpty(userJson.get("unionid"))){
				resultInfo.setUnionid(userJson.getString("unionid"));
			}
			if(!ParseUtils.isEmpty(userJson.get("remark"))){
				resultInfo.setRemark(userJson.getString("remark"));
			}
			if(subscribe==1){ 
				resultInfo.setSex(ParseUtils.parseInt(String.valueOf(userJson.get("sex"))));
				resultInfo.setProvince(String.valueOf(userJson.get("province"))); 
				resultInfo.setCountry(String.valueOf(userJson.get("country")));
				resultInfo.setCity(String.valueOf(userJson.get("city")));
				resultInfo.setLanguage(String.valueOf(userJson.get("language")));
				resultInfo.setSubscribe_time(ParseUtils.parseLong(String.valueOf(userJson.get("subscribe_time"))));  
			} 
			return resultInfo;
		}
		return null;
	}
	
	
	/**
	 * 用户是否关注 咿呀科技 公众号
	 * @param openId
	 * @return
	 */
	public static boolean  booleanAttention(String openId,String appId,String appSecret){
		WxUserInfo userInfo= getUserInfo(openId,appId,appSecret);
		if(userInfo!=null&&userInfo.getSubscribe()==1){
			return true;
		}
		return false;
	}
}
