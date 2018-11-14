package com.fh.util.weixinm;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;

import javax.annotation.Resource;
import javax.net.ssl.HttpsURLConnection;

import com.fh.service.system.token.TokenService;
import com.fh.util.PageData;
import com.fh.util.WeiXin;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

public class sslk {
	
	@Resource(name="tokenService")
	private TokenService tokenService;
	/**
     * @method packJsonmsg
     * @描述: TODO(封装微信模板:订单支付成功) 
     * @参数@param first  头部
     * @参数@param orderMoneySum  总金额
     * @参数@param orderProductName  商品信息
     * @参数@param remark  说明
     * @参数@return
     * @返回类型：JSONObject
     * @添加时间 2016-1-5下午03:38:54
     * @作者：***
     */
    public static JSONObject packJsonmsg(String first, String orderMoneySum, String orderProductName, String remark){
        JSONObject json = new JSONObject();
        try {
            JSONObject jsonFirst = new JSONObject();
            jsonFirst.put("value", "购买成功");
            jsonFirst.put("color", "#173177");
            json.put("first", jsonFirst);
            JSONObject jsonOrderMoneySum = new JSONObject();
            jsonOrderMoneySum.put("value", "产品名称");
            jsonOrderMoneySum.put("color", "#173177");
            json.put("keyword1", jsonOrderMoneySum);
            
            
            JSONObject jsonOrderProductName = new JSONObject();
            jsonOrderProductName.put("value", "订单号");
            jsonOrderProductName.put("color", "#173177");
            json.put("keyword2", jsonOrderProductName);
            
            
            JSONObject jsonOrderProductName1 = new JSONObject();
            jsonOrderProductName1.put("value", "订单价格");
            jsonOrderProductName1.put("color", "#173177");
            json.put("keyword3", jsonOrderProductName1);
            
            JSONObject jsonOrderProductName2 = new JSONObject();
            jsonOrderProductName2.put("value", "订单时间");
            jsonOrderProductName2.put("color", "#173177");
            json.put("keyword3", jsonOrderProductName2);
            
            JSONObject jsonOrderProductName3 = new JSONObject();
            jsonOrderProductName2.put("value", "订单时间");
            jsonOrderProductName2.put("color", "#173177");
            json.put("keyword4", jsonOrderProductName3);
            
            JSONObject jsonOrderProductName4 = new JSONObject();
            jsonOrderProductName2.put("value", "订单时间");
            jsonOrderProductName2.put("color", "#173177");
            json.put("keyword5", jsonOrderProductName4);
            
            JSONObject jsonRemark = new JSONObject();
            jsonRemark.put("value", "点击进入团购页面");
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
    
    
    public static String fasong(String touser, String templat_id, String clickurl, String topcolor, JSONObject data){
	 	System.out.println("==============发送通知=========================");
    	PageData pd=new PageData();
    	String tmpurl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
	 	String token = WeiXin.access_token1();
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
        	System.err.println(resultJson.toString()+"返回值==================================");
            String errmsg = (String) resultJson.get("errmsg");
            System.out.println(errmsg+"==============发送通知=========================");
            if(!"ok".equals(errmsg)){  //如果为errmsg为ok，则代表发送成功，公众号推送信息给用户了。
                System.err.println("error==============发送失败=========================");
                return "error";
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "success";
}
    
	 public static String fasong1(String token,String OPENID){
	    	String tmpurl = "http请求方式: GET https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	    	System.err.println(token);
		 	String url = tmpurl.replace("ACCESS_TOKEN", token);
		 	url = tmpurl.replace("OPENID", OPENID);
	        JSONObject json = new JSONObject();
	        String result =sslk.httpsRequest(url, "POST", null);
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
    
    public static void main(String[] args) throws Exception {
    	JSONObject ss=packJsonmsg("测试","100","名字","备注");
    	String aa=fasong("o6_H-wP9ExmdaQigf_s4kG1QGYNc","l8H7onG55__iNB4TJyHNHb1rYU0bsF4DD4uCwvwKhnM","111","#173177",ss);
    	System.err.println(aa);
    }
}
