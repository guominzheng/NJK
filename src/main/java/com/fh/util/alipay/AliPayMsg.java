package com.fh.util.alipay;

public class AliPayMsg {
	
	public static String code = "";
	public static String msg="";
	public static String data = "";
	public static String getCode() {
		return code;
	}
	public static void setCode(String code) {
		AliPayMsg.code = code;
	}
	public static String getMsg() {
		return msg;
	}
	public static void setMsg(String msg) {
		AliPayMsg.msg = msg;
	}
	public static String getData() {
		return data;
	}
	public static void setData(String data) {
		AliPayMsg.data = data;
	}
}
