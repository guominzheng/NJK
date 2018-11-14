package com.fh.util.weixinm;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

import com.fh.util.utils.GetWxOrderno;
import com.fh.util.utils.TenpayUtil;


public class Refund {
	public String wechatRefund(String ORDER_NUMBER,String MONEY) {
		String out_refund_no = UUID.randomUUID().toString().substring(0, 32);
		String out_trade_no = ORDER_NUMBER;// 订单号
		Integer aa=(int) (Double.valueOf(MONEY)*100);
		String total_fee = String.valueOf(aa);// 总金额 
		String refund_fee = String.valueOf(aa);;// 退款金额
		// 获取openId后调用统一支付接口https://api.mch.weixin.qq.com/pay/unifiedorder
		String currTime = TenpayUtil.getCurrTime();
		// 8位日期
		String strTime = currTime.substring(8, currTime.length());
		// 四位随机数
		String strRandom = TenpayUtil.buildRandom(4) + "";
		// 10位序列号,可以自行调整。
		String strReq = strTime + strRandom;
		String nonce_str = strReq;// 随机字符串
		String appid = "wx3f6627be4ed503c9";
		String appsecret = "5080ea88e4197c91844e2aa76c971cb5";
		String mch_id = "1484798722";
		String op_user_id = "1484798722";//就是MCHID
		String partnerkey = "edc60a975209ebf1d3e308a36d5397d1";//商户平台上的那个KEY
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		packageParams.put("appid", appid);
		packageParams.put("mch_id", mch_id);
		packageParams.put("nonce_str", nonce_str);
		packageParams.put("out_trade_no", out_trade_no);
		packageParams.put("out_refund_no", out_refund_no);
		packageParams.put("total_fee", total_fee);
		packageParams.put("refund_fee", refund_fee);
		packageParams.put("op_user_id", op_user_id);

		RequestHandler reqHandler = new RequestHandler(
				null, null);
		reqHandler.init(appid, appsecret, partnerkey);

		String sign = reqHandler.createSign(packageParams);
		String xml = "<xml>" + "<appid>" + appid + "</appid>" + "<mch_id>"
				+ mch_id + "</mch_id>" + "<nonce_str>" + nonce_str
				+ "</nonce_str>" + "<sign><![CDATA[" + sign + "]]></sign>"
				+ "<out_trade_no>" + out_trade_no + "</out_trade_no>"
				+ "<out_refund_no>" + out_refund_no + "</out_refund_no>"
				+ "<total_fee>" + total_fee + "</total_fee>"
				+ "<refund_fee>" + refund_fee + "</refund_fee>"
				+ "<op_user_id>" + op_user_id + "</op_user_id>" + "</xml>";
		String createOrderURL = "https://api.mch.weixin.qq.com/secapi/pay/refund";
		String return_code="";
		try {
			String s= ClientCustomSSL.doRefund(createOrderURL, xml);
			System.err.println("------------------"+s);
			Map map = GetWxOrderno.doXMLParse(s);
			return_code= (String) map.get("return_code");
			System.out.println(return_code);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return return_code;
	}
	
	
	
	
	
	
	
	public String wechatRefund1(String ORDER_NUMBER,String MONEY) {
		String out_refund_no = UUID.randomUUID().toString().substring(0, 32);
		String out_trade_no = ORDER_NUMBER;// 订单号
		String total_fee = MONEY;// 总金额
		String refund_fee = MONEY;// 退款金额
		// 获取openId后调用统一支付接口https://api.mch.weixin.qq.com/pay/unifiedorder
		String currTime = TenpayUtil.getCurrTime();
		// 8位日期
		String strTime = currTime.substring(8, currTime.length());
		// 四位随机数
		String strRandom = TenpayUtil.buildRandom(4) + "";
		// 10位序列号,可以自行调整。
		String strReq = strTime + strRandom;
		String nonce_str = strReq;// 随机字符串
		String appid = "wx3e8ef0db80a0580f";
		String appsecret = "278fc286264c9d8a96571752f88c3eb7";
		String mch_id = "1482116722";
		String op_user_id = "1482116722";//就是MCHID
		String partnerkey = "5BCB0B1B9455219FF9628FA9DED938A2";//商户平台上的那个KEY
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		packageParams.put("appid", appid);
		packageParams.put("mch_id", mch_id);
		packageParams.put("nonce_str", nonce_str);
		packageParams.put("out_trade_no", out_trade_no);
		packageParams.put("out_refund_no", out_refund_no);
		packageParams.put("total_fee", total_fee);
		packageParams.put("refund_fee", refund_fee);
		packageParams.put("op_user_id", op_user_id);

		RequestHandler reqHandler = new RequestHandler(
				null, null);
		reqHandler.init(appid, appsecret, partnerkey);

		String sign = reqHandler.createSign(packageParams);
		String xml = "<xml>" + "<appid>" + appid + "</appid>" + "<mch_id>"
				+ mch_id + "</mch_id>" + "<nonce_str>" + nonce_str
				+ "</nonce_str>" + "<sign><![CDATA[" + sign + "]]></sign>"
				+ "<out_trade_no>" + out_trade_no + "</out_trade_no>"
				+ "<out_refund_no>" + out_refund_no + "</out_refund_no>"
				+ "<total_fee>" + total_fee + "</total_fee>"
				+ "<refund_fee>" + refund_fee + "</refund_fee>"
				+ "<op_user_id>" + op_user_id + "</op_user_id>" + "</xml>";
		String createOrderURL = "https://api.mch.weixin.qq.com/secapi/pay/refund";
		String return_code="";
		try {
			String s= ClientCustomSSL.doRefund1(createOrderURL, xml);
			Map map = GetWxOrderno.doXMLParse(s);
			return_code= (String) map.get("return_code");
			System.out.println(return_code);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return return_code;
	}
}
