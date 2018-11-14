package com.fh.controller.app.appuser;

import com.fh.util.utils.CommonUtil;
import net.sf.json.JSONObject;

public class WeixinInfo{
	
	/**
	   * 获取用户信息
	   * 
	   * @param accessToken 接口访问凭证
	   * @param openId 用户标识
	   * @return WeixinUserInfo
	   */
	  public static JSONObject getUserInfo(String accessToken, String openId) {
	    WeixinUserInfo weixinUserInfo = null;
	    // 拼接请求地址
	    String requestUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID";
	    requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
	    // 获取用户信息
	    JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);
	    if (null != jsonObject) {
	      try {
	        weixinUserInfo = new WeixinUserInfo();
	        // 用户的标识
	        weixinUserInfo.setOpenId(jsonObject.getString("openid"));
	        // 关注状态（1是关注，0是未关注），未关注时获取不到其余信息
	        weixinUserInfo.setSubscribe(jsonObject.getInt("subscribe"));
	        // 用户关注时间
	        weixinUserInfo.setSubscribeTime(jsonObject.getString("subscribe_time"));
	        // 昵称
	        weixinUserInfo.setNickname(jsonObject.getString("nickname"));
	        // 用户的性别（1是男性，2是女性，0是未知）
	        weixinUserInfo.setSex(jsonObject.getInt("sex"));
	        // 用户所在国家
	        weixinUserInfo.setCountry(jsonObject.getString("country"));
	        // 用户所在省份
	        weixinUserInfo.setProvince(jsonObject.getString("province"));
	        // 用户所在城市
	        weixinUserInfo.setCity(jsonObject.getString("city"));
	        // 用户的语言，简体中文为zh_CN
	        weixinUserInfo.setLanguage(jsonObject.getString("language"));
	        // 用户头像
	        weixinUserInfo.setHeadImgUrl(jsonObject.getString("headimgurl"));
	        //用户unionid
	        weixinUserInfo.setUnionid(jsonObject.getString("unionid"));
	      }catch(Exception e){
	    	  jsonObject.put("subscribe", "0");
	      }

	    }
	    return jsonObject;
	    }
	  
		/**
	   * 获取用户信息
	   * 
	   * @param accessToken 接口访问凭证
	   * @param openId 用户标识
	   * @return WeixinUserInfo
	   */
	  public static JSONObject getUserInfo1(String accessToken, String openId) {
	    WeixinUserInfo weixinUserInfo = null;
	    // 拼接请求地址
	    String requestUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	    requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
	    // 获取用户信息
	    JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);
	    if (null != jsonObject) {
	      try {
	        weixinUserInfo = new WeixinUserInfo();
	        // 用户的标识
	        weixinUserInfo.setOpenId(jsonObject.getString("openid"));
	        // 昵称
	        weixinUserInfo.setNickname(jsonObject.getString("nickname"));
	        // 用户的性别（1是男性，2是女性，0是未知）
	        weixinUserInfo.setSex(jsonObject.getInt("sex"));
	        // 用户头像
	        weixinUserInfo.setHeadImgUrl(jsonObject.getString("headimgurl"));
	      }catch(Exception e){
	    	  jsonObject.put("subscribe", "0");
	      }

	    }
	    return jsonObject;
	    }
	  
	  public static void main(String args[]) {
		    // 获取接口访问凭证
		    //String accessToken = CommonUtil.getToken("xxxx", "xxxx").getAccessToken();
		    /**
		     * 获取用户信息
		     */
		    //WeixinUserInfo user = getUserInfo("M1HGzORWQCHOv9kuVTOEj3vyMlMKel6zoPTj2jvOQY-ULs-D6ObYCCatccAo2hK4cVRaKnyKHihcjnsEEEUo3Jh2gUIpSVQaIw88xydIh8uhfBKhI3jynXvO9XpST9NyUFUfAIAGRG", "o6_H-wP9ExmdaQigf_s4kG1QGYNc");
/*		    System.out.println("OpenID：" + user.getOpenId());
		    System.out.println("关注状态：" + user.getSubscribe());
		    System.out.println("关注时间：" + user.getSubscribeTime());
		    System.out.println("昵称：" + user.getNickname());
		    System.out.println("性别：" + user.getSex());
		    System.out.println("国家：" + user.getCountry());
		    System.out.println("省份：" + user.getProvince());
		    System.out.println("城市：" + user.getCity());
		    System.out.println("语言：" + user.getLanguage());
		    System.out.println("头像：" + user.getHeadImgUrl());*/
		  }

}
