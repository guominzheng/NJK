// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2018/11/13 13:49:15
// Home Page:  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   WeiXin.java
package com.fh.util;

import com.fh.util.MD5;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class WeiXin {
    private static final char[] HEX_DIGITS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static void main(String[] args) throws Exception {
        System.err.println(WeiXin.access_token());
    }

    public static String access_token() {
        String inputLine = "";
        try {
            URL url = null;
            url = new URL("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx6b912be972e69695&secret=439928f1c906167f8f87436e6bb8f682");
            BufferedReader in = null;
            in = new BufferedReader(new InputStreamReader(url.openStream()));
            inputLine = new String(in.readLine());
        }
        catch (Exception e) {
            System.out.println("\u9519\u8bef");
            inputLine = "-1";
        }
        String access_token = "";
        try {
            JSONObject json = new JSONObject(inputLine);
            access_token = json.getString("access_token");
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return access_token;
    }

    public static String refresh_token(String access_token) {
        String inputLine = "";
        try {
            URL url = null;
            url = new URL("https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=wx3f6627be4ed503c9&grant_type=refresh_token&refresh_token=" + access_token);
            BufferedReader in = null;
            in = new BufferedReader(new InputStreamReader(url.openStream()));
            inputLine = new String(in.readLine());
        }
        catch (Exception e) {
            System.out.println("\u9519\u8bef");
            inputLine = "-1";
        }
        String access_token1 = "";
        try {
            JSONObject json = new JSONObject(inputLine);
            System.err.println(json.toString());
            access_token1 = json.getString("refresh_token");
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return access_token1;
    }

    public static String access_token1() {
        String inputLine = "";
        try {
            URL url = null;
            url = new URL("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx3f6627be4ed503c9&secret=5080ea88e4197c91844e2aa76c971cb5");
            BufferedReader in = null;
            in = new BufferedReader(new InputStreamReader(url.openStream()));
            inputLine = new String(in.readLine());
        }
        catch (Exception e) {
            System.out.println("\u9519\u8bef");
            inputLine = "-1";
        }
        String access_token = "";
        try {
            JSONObject json = new JSONObject(inputLine);
            System.err.println(json.toString());
            access_token = json.getString("access_token");
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return access_token;
    }

    public static String jsapiTicket(String access_token) throws IOException, JSONException {
        String inputLine = "";
        try {
            URL url = null;
            url = new URL("https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + access_token + "&type=jsapi");
            BufferedReader in = null;
            in = new BufferedReader(new InputStreamReader(url.openStream()));
            inputLine = new String(in.readLine());
            JSONObject json = new JSONObject(inputLine);
            inputLine = json.getString("ticket");
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return inputLine;
    }

    public static String createNonceStr(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; ++i) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    private static String getFormattedText(byte[] bytes) {
        int len = bytes.length;
        StringBuilder buf = new StringBuilder(len * 2);
        for (int j = 0; j < len; ++j) {
            buf.append(WeiXin.HEX_DIGITS[bytes[j] >> 4 & 15]);
            buf.append(WeiXin.HEX_DIGITS[bytes[j] & 15]);
        }
        return buf.toString();
    }

    public static String encode(String str) {
        if (str == null) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
            messageDigest.update(str.getBytes());
            return WeiXin.getFormattedText(messageDigest.digest());
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String byteToHex(byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", Byte.valueOf(b));
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }

    /*public static String getSignature(String jsapi_ticket, String timestamp, String nonce, String jsurl) throws IOException {
        Object[] paramArr = new String[]{"jsapi_ticket=" + jsapi_ticket, "timestamp=" + timestamp, "noncestr=" + nonce, "url=" + jsurl};
        Arrays.sort(paramArr);
        String content = paramArr[0].concat("&" + (String)paramArr[1]).concat("&" + (String)paramArr[2]).concat("&" + (String)paramArr[3]);
        System.out.println("\u62fc\u63a5\u4e4b\u540e\u7684content\u4e3a:" + content);
        String gensignature = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] digest = md.digest(content.toString().getBytes());
            gensignature = WeiXin.byteToStr(digest);
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        if (gensignature == null) return "false";
        return gensignature;
    }*/

    private static String byteToStr(byte[] byteArray) {
        String strDigest = "";
        for (int i = 0; i < byteArray.length; ++i) {
            strDigest = strDigest + WeiXin.byteToHexStr(byteArray[i]);
        }
        return strDigest;
    }

    private static String byteToHexStr(byte mByte) {
        char[] Digit = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] tempArr = new char[]{Digit[mByte >>> 4 & 15], Digit[mByte & 15]};
        String s = new String(tempArr);
        return s;
    }

    public static Map<String, String> sign1(String jsapi_ticket, String url) {
        HashMap<String, String> ret = new HashMap<String, String>();
        String nonce_str = WeiXin.createNonceStr(16);
        String timestamp = WeiXin.create_timestamp();
        String signature = "";
        String string1 = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonce_str + "&timestamp=" + timestamp + "&url=" + url;
        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = WeiXin.byteToHex(crypt.digest());
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        ret.put("url", url);
        ret.put("jsapi_ticket", jsapi_ticket);
        ret.put("nonceStr", nonce_str);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);
        return ret;
    }

    public static String genAppSign(Map<String, String> map) throws NoSuchAlgorithmException {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            sb.append(entry.getKey());
            sb.append("=");
            sb.append(entry.getValue());
            sb.append("&");
        }
        sb.append("key=");
        sb.append("5BCB0B1B9455219FF9628FA9DED938A2");
        System.err.println(sb.toString());
        String appSign = MD5.getMd51(sb.toString().getBytes());
        return appSign;
    }

    public static String genAppSign1(Map<String, String> map) throws NoSuchAlgorithmException {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            sb.append(entry.getKey());
            sb.append("=");
            sb.append(entry.getValue());
            sb.append("&");
        }
        sb.append("partnerId");
        sb.append("=");
        sb.append(map.get("partnerId"));
        sb.append("&");
        sb.append("prepayId");
        sb.append("=");
        sb.append(map.get("prepayId"));
        sb.append("&");
        sb.append("package");
        sb.append("=");
        sb.append(map.get("package"));
        sb.append("&");
        sb.append("nonceStr");
        sb.append("=");
        sb.append(map.get("nonceStr"));
        sb.append("&");
        sb.append("timeStamp");
        sb.append("=");
        sb.append(map.get("timeStamp"));
        sb.append("&");
        sb.append("key=");
        sb.append("5BCB0B1B9455219FF9628FA9DED938A2");
        System.err.println(sb.toString());
        String appSign = MD5.getMd51(sb.toString().getBytes());
        return appSign;
    }

    public static String getUrl(String url) {
        String result = null;
        try {
            HttpGet request = new HttpGet(url);
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpResponse response = httpClient.execute((HttpUriRequest)request);
            if (response.getStatusLine().getStatusCode() != 200) return result;
            result = EntityUtils.toString((HttpEntity)response.getEntity(), (String)"UTF-8");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
