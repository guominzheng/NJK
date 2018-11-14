package com.fh.controller.app.appuser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.net.ssl.HttpsURLConnection;

import com.fh.service.system.token.TokenService;
import com.fh.util.DateUtil;
import com.fh.util.PageData;
import com.fh.util.WeiXin;
import com.fh.util.weixinm.sslk;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import sun.util.logging.resources.logging;

public class Wtuishong implements Runnable{
	
	private String first;
	private String keyword1;
	private String keyword2;
	private String keyword3;
	private String keyword4;
	private String Remark;
	private String openId;
	private String url;
	private String MoBanId;
	@Resource(name="tokenService")
	private TokenService tokenService;
	
	public Wtuishong(String first,String keyword1,String keyword2,String keyword3,String keyword4,String Remark,String openId,String url,TokenService tokenService,String MoBanId){
		this.first=first;
		this.keyword1=keyword1;
		this.keyword2=keyword2;
		this.keyword3=keyword3;
		this.keyword4=keyword4;
		this.Remark=Remark;
		this.openId=openId;
		this.url=url;
		this.tokenService=tokenService;
		this.MoBanId=MoBanId;
	}
	
	
	 public static JSONObject packJsonmsg1(String first, String keyword1, String keyword2,String keyword3,String keyword4,String remark){
	        JSONObject json = new JSONObject();
	        try {
	            JSONObject jsonFirst = new JSONObject();
	            jsonFirst.put("value", first);
	            jsonFirst.put("color", "#173177");
	            json.put("first", jsonFirst);
	            
	            
	            JSONObject jsonOrderMoneySum = new JSONObject();
	            jsonOrderMoneySum.put("value", keyword1);
	            jsonOrderMoneySum.put("color", "#173177");
	            json.put("keyword1", jsonOrderMoneySum);
	            
	            
	            JSONObject jsonOrderProductName = new JSONObject();
	            jsonOrderProductName.put("value", keyword2);
	            jsonOrderProductName.put("color", "#173177");
	            json.put("keyword2", jsonOrderProductName);
	            
	            
	            JSONObject jsonOrderProductName1 = new JSONObject();
	            jsonOrderProductName1.put("value", keyword3);
	            jsonOrderProductName1.put("color", "#173177");
	            json.put("keyword3", jsonOrderProductName1);
	            
	            
	            JSONObject jsonRemark = new JSONObject();
	            jsonRemark.put("value", remark);
	            jsonRemark.put("color", "#173177");
	            json.put("remark", jsonRemark);
	        } catch (JSONException e) {
	            e.printStackTrace();
	        }
	        return json;
	    }
	
	 public static JSONObject packJsonmsg(String first, String keyword1, String keyword2,String keyword3,String keyword4,String remark){
	        JSONObject json = new JSONObject();
	        try {
	            JSONObject jsonFirst = new JSONObject();
	            jsonFirst.put("value", first);
	            jsonFirst.put("color", "#173177");
	            json.put("first", jsonFirst);
	            
	            
	            JSONObject jsonOrderMoneySum = new JSONObject();
	            jsonOrderMoneySum.put("value", keyword1);
	            jsonOrderMoneySum.put("color", "#173177");
	            json.put("orderno", jsonOrderMoneySum);
	            
	            
	            JSONObject jsonOrderProductName = new JSONObject();
	            jsonOrderProductName.put("value", keyword2);
	            jsonOrderProductName.put("color", "#173177");
	            json.put("refundno", jsonOrderProductName);
	            
	            
	            JSONObject jsonOrderProductName1 = new JSONObject();
	            jsonOrderProductName1.put("value", keyword3);
	            jsonOrderProductName1.put("color", "#173177");
	            json.put("refundproduct", jsonOrderProductName1);
	            
	            JSONObject jsonOrderProductName2 = new JSONObject();
	            jsonOrderProductName2.put("value", keyword4);
	            jsonOrderProductName2.put("color", "#173177");
	            json.put("keyword4", jsonOrderProductName2);
	            
	            JSONObject jsonOrderProductName3 = new JSONObject();
	            jsonOrderProductName3.put("value", "暂未发货");
	            jsonOrderProductName3.put("color", "#173177");
	            json.put("keyword4", jsonOrderProductName3);
	            
	            JSONObject jsonRemark = new JSONObject();
	            jsonRemark.put("value", remark);
	            jsonRemark.put("color", "#173177");
	            json.put("remark", jsonRemark);
	        } catch (JSONException e) {
	            e.printStackTrace();
	        }
	        return json;
	    }
	 
	 public static JSONObject packJsonmsg2(String first, String keyword1, String keyword2,String keyword3,String keyword4,String remark){
	        JSONObject json = new JSONObject();
	        try {
	            JSONObject jsonFirst = new JSONObject();
	            jsonFirst.put("value", first);
	            jsonFirst.put("color", "#173177");
	            json.put("first", jsonFirst);
	            
	            
	            JSONObject jsonOrderMoneySum = new JSONObject();
	            jsonOrderMoneySum.put("value", keyword1);
	            jsonOrderMoneySum.put("color", "#173177");
	            json.put("keyword1", jsonOrderMoneySum);
	            
	            
	            JSONObject jsonOrderProductName = new JSONObject();
	            jsonOrderProductName.put("value", keyword2);
	            jsonOrderProductName.put("color", "#173177");
	            json.put("keyword2", jsonOrderProductName);
	            
	            
	            JSONObject jsonOrderProductName1 = new JSONObject();
	            jsonOrderProductName1.put("value", keyword3);
	            jsonOrderProductName1.put("color", "#173177");
	            json.put("keyword3", jsonOrderProductName1);
	            
	            JSONObject jsonOrderProductName2 = new JSONObject();
	            jsonOrderProductName2.put("value", keyword4);
	            jsonOrderProductName2.put("color", "#173177");
	            json.put("keyword4", jsonOrderProductName2);
	            
	            JSONObject jsonRemark = new JSONObject();
	            jsonRemark.put("value", remark);
	            jsonRemark.put("color", "#173177");
	            json.put("remark", jsonRemark);
	        } catch (JSONException e) {
	            e.printStackTrace();
	        }
	        return json;
	    }
	 
	 public static JSONObject packJsonmsg4(String first, String keyword1, String keyword2,String keyword3,String keyword4,String remark){
	        JSONObject json = new JSONObject();
	        try {
	            JSONObject jsonFirst = new JSONObject();
	            jsonFirst.put("value", first);
	            jsonFirst.put("color", "#173177");
	            json.put("first", jsonFirst);
	            
	            
	            JSONObject jsonOrderMoneySum = new JSONObject();
	            jsonOrderMoneySum.put("value", keyword1);
	            jsonOrderMoneySum.put("color", "#173177");
	            json.put("keyword1", jsonOrderMoneySum);
	            
	            
	            JSONObject jsonOrderProductName = new JSONObject();
	            jsonOrderProductName.put("value", keyword2);
	            jsonOrderProductName.put("color", "#173177");
	            json.put("keyword2", jsonOrderProductName);
	            
	            JSONObject jsonRemark = new JSONObject();
	            jsonRemark.put("value", remark);
	            jsonRemark.put("color", "#173177");
	            json.put("remark", jsonRemark);
	        } catch (JSONException e) {
	            e.printStackTrace();
	        }
	        return json;
	    }
	 
	 public static JSONObject packJsonmsg5(String first, String keyword1, String keyword2,String keyword3,String keyword4,String remark){
	        JSONObject json = new JSONObject();
	        try {
	            JSONObject jsonFirst = new JSONObject();
	            jsonFirst.put("value", first);
	            jsonFirst.put("color", "#173177");
	            json.put("first", jsonFirst);
	            
	            
	            JSONObject jsonOrderMoneySum = new JSONObject();
	            jsonOrderMoneySum.put("value", keyword1);
	            jsonOrderMoneySum.put("color", "#173177");
	            json.put("keyword1", jsonOrderMoneySum);
	            
	            
	            JSONObject jsonOrderProductName = new JSONObject();
	            jsonOrderProductName.put("value", keyword2);
	            jsonOrderProductName.put("color", "#173177");
	            json.put("keyword2", jsonOrderProductName);
	            
	            
	            JSONObject jsonOrderProductName1 = new JSONObject();
	            jsonOrderProductName1.put("value", keyword3);
	            jsonOrderProductName1.put("color", "#173177");
	            json.put("keyword3", jsonOrderProductName1);
	            
	            JSONObject jsonOrderProductName2 = new JSONObject();
	            jsonOrderProductName2.put("value", keyword4);
	            jsonOrderProductName2.put("color", "#173177");
	            json.put("keyword4", jsonOrderProductName2);
	            
	            JSONObject jsonRemark = new JSONObject();
	            jsonRemark.put("value", remark);
	            jsonRemark.put("color", "#173177");
	            json.put("remark", jsonRemark);
	        } catch (JSONException e) {
	            e.printStackTrace();
	        }
	        return json;
	    }
	 
	 public static JSONObject packJsonmsg6(String first, String keyword1, String keyword2,String keyword3,String keyword4,String remark){
	        JSONObject json = new JSONObject();
	        try {
	            JSONObject jsonFirst = new JSONObject();
	            jsonFirst.put("value", first);
	            jsonFirst.put("color", "#173177");
	            json.put("first", jsonFirst);
	            
	            
	            JSONObject jsonOrderMoneySum = new JSONObject();
	            jsonOrderMoneySum.put("value", keyword1);
	            jsonOrderMoneySum.put("color", "#173177");
	            json.put("keyword1", jsonOrderMoneySum);
	            
	            
	            JSONObject jsonOrderProductName = new JSONObject();
	            jsonOrderProductName.put("value", keyword2);
	            jsonOrderProductName.put("color", "#173177");
	            json.put("keyword2", jsonOrderProductName);
	            
	            
	            JSONObject jsonOrderProductName1 = new JSONObject();
	            jsonOrderProductName1.put("value", keyword3);
	            jsonOrderProductName1.put("color", "#173177");
	            json.put("keyword3", jsonOrderProductName1);
	            
	            JSONObject jsonOrderProductName2 = new JSONObject();
	            jsonOrderProductName2.put("value", keyword4);
	            jsonOrderProductName2.put("color", "#173177");
	            json.put("keyword4", jsonOrderProductName2);
	            
	            JSONObject jsonRemark = new JSONObject();
	            jsonRemark.put("value", remark);
	            jsonRemark.put("color", "#173177");
	            json.put("remark", jsonRemark);
	        } catch (JSONException e) {
	            e.printStackTrace();
	        }
	        return json;
	    }
	 
	 
	 public static String httpsRequest(String requestUrl, String requestMethod, String outputStr){
	        try {
	            URL url = new URL(requestUrl);
	            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
	            conn.setDoOutput(true);
	            conn.setDoInput(true);
	            conn.setUseCaches(false);
	            // 设置请求方式（GET/POST）
	            conn.setRequestMethod(requestMethod);
	            conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
	            // 当outputStr不为null时向输出流写数据
	            if (null != outputStr) {
	                OutputStream outputStream = conn.getOutputStream();
	                // 注意编码格式
	                outputStream.write(outputStr.getBytes("UTF-8"));
	                outputStream.close();
	            }
	            // 从输入流读取返回内容
	            InputStream inputStream = conn.getInputStream();
	            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
	            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
	            String str = null;
	            StringBuffer buffer = new StringBuffer();
	            while ((str = bufferedReader.readLine()) != null) {
	                buffer.append(str);
	            }
	            // 释放资源
	            bufferedReader.close();
	            inputStreamReader.close();
	            inputStream.close();
	            inputStream = null;
	            conn.disconnect();
	            return buffer.toString();
	        } catch (ConnectException ce) {
	            System.out.println("连接超时：{}");
	        } catch (Exception e) {
	            System.out.println("https请求异常：{}");
	        }
	        return null;
	    }
	 
	 
	 
	 public static String fasong(String token,String touser, String templat_id, String clickurl, String topcolor, JSONObject data){
	    	String tmpurl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
	    	System.err.println(token);
		 	String url = tmpurl.replace("ACCESS_TOKEN", token);
	        JSONObject json = new JSONObject();
	        try {
	            json.put("touser", touser);
	            json.put("template_id", templat_id);
	            json.put("url", clickurl);
	            json.put("topcolor", topcolor);
	            json.put("data", data);
	        } catch (JSONException e) {
	            e.printStackTrace();
	        }
	        String result =sslk.httpsRequest(url, "POST", json.toString());
	        try {
	        	JSONObject resultJson = JSONObject.fromObject(result);
	            //JSONObject resultJson = new net.sf.json.JSONObject(result);
	            String errmsg = (String) resultJson.get("errmsg");
	            if(!"ok".equals(errmsg)){  //如果为errmsg为ok，则代表发送成功，公众号推送信息给用户了。
	                return "error";
	            }
	        } catch (JSONException e) {
	            e.printStackTrace();
	        }
	        return "success";
	}

	public synchronized void run() {
		System.err.println("================微信通知=======================");
		try { 
			String token="";
			PageData pd=new PageData();
			PageData pd12 = tokenService.findById(pd);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			long difference = format.parse(format.format(new Date())).getTime()
					- format.parse(pd12.getString("DATE")).getTime();
			if (difference > 160000) { // 标签超时,重新到微信服务器请求标签超时时间为7200秒（7200000毫秒）
				token = WeiXin.access_token1();
				pd12.put("token", token);
				pd12.put("DATE", DateUtil.getTime());
				tokenService.edit(pd12);
			} else {
				token = pd12.getString("token");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		JSONObject josn=null;
		if(MoBanId.equals("W5g6JIL81GRG1zd63OpFMfSeiZtnF6P4Dyb5lLkQg20")){
			josn=packJsonmsg1(first,keyword1,keyword2,keyword3,keyword4,Remark);
		}else if(MoBanId.equals("hc_GlELQc2wGsWLVvh0gukuI765UrFSVG4wgJx38jYg")){
			josn=packJsonmsg(first,keyword1,keyword2,keyword3,keyword4,Remark);
		}else if(MoBanId.equals("TjafAft8Z2gBWt9K-VzjKCuSUHqJ9u_0cwfVeS-r52w")){
			josn=packJsonmsg4(first,keyword1,keyword2,keyword3,keyword4,Remark);
		}else if(MoBanId.equals("yATpiH3KWG4nUhRZYoLsi8GjG8zOxUSX0kO0AuaJUec")){
			josn=packJsonmsg5(first,keyword1,keyword2,keyword3,keyword4,Remark);
		}else if(MoBanId.equals("4x0kUXJmi-mdFFJYwyY0G50JKDH7debIU5--HQcfvSE")){
			josn=packJsonmsg6(first,keyword1,keyword2,keyword3,keyword4,Remark);
		}else{
			josn=packJsonmsg2(first,keyword1,keyword2,keyword3,keyword4,Remark);
		}
		String aa=sslk.fasong(openId, MoBanId, url, "#173177",josn);
		System.err.println(aa);
	}



}
