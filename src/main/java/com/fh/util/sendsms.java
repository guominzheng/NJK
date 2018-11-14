package com.fh.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Properties;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

import org.dom4j.Document;   
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;   
import org.dom4j.Element;


public class sendsms {

	
	public static String se(String name,String phone) throws UnsupportedEncodingException{
		String name1=new String(name.getBytes("UTF-8"),"utf-8");
		//String name1=new String(name.getBytes("UTF-8"),"utf-8");
		System.err.println(name1);
		BufferedReader reader = null;
	        String result = null;
	        StringBuffer sbf = new StringBuffer();
	        try {
	            URL url = new URL("http://web.wasun.cn/asmx/smsservice.aspx?name=13526524092&pwd=6ACD05E026F8945A23A86ACC906D&mobile="+phone+"&content="+name1+"&sign=农极客&type=pt");
	            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	            connection.setRequestMethod("GET");
	            connection.connect();
	            InputStream is = connection.getInputStream();
	            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
	            String strRead = reader.readLine();
	            if (strRead != null) {
	                sbf.append(strRead);
	                while ((strRead = reader.readLine()) != null) {
	                    sbf.append("\n");
	                    sbf.append(strRead);
	                }
	            }
	            reader.close();
	            result = sbf.toString();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return result;
	}
	
	public static void main(String [] args) throws UnsupportedEncodingException, MalformedURLException {	
		sendsms se=new sendsms();
		String s=se.se("王翔", "18736040966");
		System.out.println(s);
	}
	
}
