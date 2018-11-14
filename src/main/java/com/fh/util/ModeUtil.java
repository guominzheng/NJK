// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2018/11/13 13:51:05
// Home Page:  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ModeUtil.java
package com.fh.util;

import com.fh.util.PageData;
import com.fh.util.SmallLoginUtil;
import com.fh.util.weixinm.sslk;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import net.sf.json.JSONObject;
import org.json.JSONException;

public class ModeUtil {
    private static final String wxspAppid = "wxd423e43af7937dc0";
    private static final String wxspSecret = "0592aeccefe34e7cb4dc93b079160c0b";
    private static final String template_id = "xuQl8WS24f4E7DTTUEouZn-IuYFiYSMYuYbDZ2mp8W0";
    public static final String critic_template_id = "BjMacTlr4CBkKl_n2a4jc0qU4G5uIgHQPieBvYd3c8w";
    public static final String begin_template_id = "cdNsmR2lazieOKzZmHXhUBJaViwkJonX59EffsbSX1c";
    public static final String critic_page = "pages/project/my/message/message";

    public static  JSONObject XiaoChengXun(String ... keyword) {
        JSONObject json = new JSONObject();
        try {
            for (int i = 0; i < keyword.length; ++i) {
                JSONObject jsonOrderMoneySum = new JSONObject();
                jsonOrderMoneySum.put("value", keyword[i]);
                json.put(("keyword" + (i + 1)), jsonOrderMoneySum);
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    public static String fasong3(String touser, String page, String form_id, JSONObject data) {
        System.out.println("==============\u53d1\u9001\u901a\u77e5=========================");
        PageData pd = new PageData();
        String tmpurl = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=ACCESS_TOKEN";
        String token = ModeUtil.getToken();
        String url = tmpurl.replace((CharSequence)"ACCESS_TOKEN", (CharSequence)token);
        JSONObject json = new JSONObject();
        try {
            json.put("touser", touser);
            json.put("template_id", "xuQl8WS24f4E7DTTUEouZn-IuYFiYSMYuYbDZ2mp8W0");
            json.put("page", page);
            json.put("form_id", form_id);
            json.put("data", data);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        String result = sslk.httpsRequest(url, "POST", json.toString());
        try {
            JSONObject resultJson = JSONObject.fromObject(result);
            System.err.println(resultJson.toString() + "\u8fd4\u56de\u503c==================================");
            String errmsg = (String)resultJson.get("errmsg");
            System.out.println(errmsg + "==============\u53d1\u9001\u901a\u77e5=========================");
            if ("ok".equals(errmsg)) return "success";
            System.err.println("error==============\u53d1\u9001\u5931\u8d25=========================");
            return "error";
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return "success";
    }

    public static String wxMessageModeSend(String template, String touser, String page, String form_id, JSONObject data) {
        System.out.println("==============\u53d1\u9001\u901a\u77e5=========================");
        PageData pd = new PageData();
        String tmpurl = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=ACCESS_TOKEN";
        String token = ModeUtil.getToken();
        String url = tmpurl.replace("ACCESS_TOKEN", token);
        JSONObject json = new JSONObject();
        try {
            json.put("touser", touser);
            json.put("template_id", template);
            json.put("page", page);
            json.put("form_id", form_id);
            json.put("data", data);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        String result = sslk.httpsRequest(url, "POST", json.toString());
        try {
            JSONObject resultJson = JSONObject.fromObject(result);
            System.err.println(resultJson.toString() + "\u8fd4\u56de\u503c==================================");
            String errmsg = (String)resultJson.get("errmsg");
            System.out.println(errmsg + "==============\u53d1\u9001\u901a\u77e5=========================");
            if ("ok".equals(errmsg)) return "success";
            System.err.println("error==============\u53d1\u9001\u5931\u8d25=========================");
            return "error";
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return "success";
    }

    public static String getCodeUrl(String path) {
        String name = "" + System.currentTimeMillis() + "";
        String reStr = "";
        try {
            int len;
            String tmpurl = "https://api.weixin.qq.com/wxa/getwxacode?access_token=ACCESS_TOKEN";
            String token = ModeUtil.getToken();
            System.out.println("==================" + token);
            String urls = tmpurl.replace((CharSequence)"ACCESS_TOKEN", (CharSequence)token);
            URL url = new URL(urls);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());
            JSONObject paramJson = new JSONObject();
            paramJson.put("path", path);
            paramJson.put("width", 430);
            printWriter.write(paramJson.toString());
            printWriter.flush();
            BufferedInputStream bis = new BufferedInputStream(httpURLConnection.getInputStream());
            FileOutputStream os = new FileOutputStream(new File("D:\\tomcat1\\webapps\\NJK\\uploadFiles\\uploadImgs\\" + name + ".png"));
            byte[] arr = new byte[1024];
            while ((len = bis.read(arr)) != -1) {
                os.write(arr, 0, len);
                os.flush();
            }
            os.close();
            reStr = "https://m.nongjike.cn/NJK/uploadFiles/uploadImgs/" + name + ".png";
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return reStr;
    }

    public static void main(String[] args) throws Exception {
        try {
            int len;
            String tmpurl = "https://api.weixin.qq.com/wxa/getwxacode?access_token=ACCESS_TOKEN";
            String token = ModeUtil.getToken();
            System.out.println("==================" + token);
            String urls = tmpurl.replace((CharSequence)"ACCESS_TOKEN", (CharSequence)token);
            JSONObject json = new JSONObject();
            json.element("path", "pages/project/index/index");
            json.element("width", 430);
            System.out.println(sslk.httpsRequest(urls, "POST", json.toString()));
            URL url = new URL(urls);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());
            JSONObject paramJson = new JSONObject();
            paramJson.put("path", "pages/project/index/index");
            paramJson.put("width", 430);
            printWriter.write(paramJson.toString());
            printWriter.flush();
            BufferedInputStream bis = new BufferedInputStream(httpURLConnection.getInputStream());
            FileOutputStream os = new FileOutputStream(new File("C:\\Users\\xshel\\Desktop\\abc.png"));
            byte[] arr = new byte[1024];
            while ((len = bis.read(arr)) != -1) {
                os.write(arr, 0, len);
                os.flush();
            }
            os.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getToken() {
        String url = "https://api.weixin.qq.com/cgi-bin/token";
        String param = "grant_type=client_credential&appid=wxd423e43af7937dc0&secret=0592aeccefe34e7cb4dc93b079160c0b";
        String result = SmallLoginUtil.sendGet(url, param);
        JSONObject josn = JSONObject.fromObject(result);
        return josn.getString("access_token");
    }

    public static int saveToImgByInputStream(InputStream instreams, String imgPath, String imgName) {
        int stateInt = 1;
        if (instreams == null) return stateInt;
        try {
            File file = new File(imgPath, imgName);
            FileOutputStream fos = new FileOutputStream(file);
            byte[] b = new byte[1024];
            int nRead = 0;
            while ((nRead = instreams.read(b)) != -1) {
                fos.write(b, 0, nRead);
            }
            fos.flush();
            fos.close();
        }
        catch (Exception e) {
            stateInt = 0;
            e.printStackTrace();
        }
        return stateInt;
    }
}
