package com.fh.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.mail.Header;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

/**
 * 通过短信接口发送短信
 */
public class SmsBao {

/*	public String sendSMS(String testPhone, String testContent){
		String result="0";
		HttpClient client = new HttpClient();  
	       PostMethod post = new PostMethod("http://web.wasun.cn/asmx/smsservice.aspx/?");  
	        post.addRequestHeader("Content-Type",  
	                "application/x-www-form-urlencoded;charset=gbk");// 在头文件中设置转码  
	        NameValuePair[] data = { new NameValuePair("name", "13526524092"), // 注册的用户名  
	                new NameValuePair("pwd", "6ACD05E026F8945A23A86ACC906D"), // 注册成功后,登录网站使用的密钥  
	                new NameValuePair("mobile", testPhone), // 手机号码  
	                new NameValuePair("content", testContent), 
	                new NameValuePair("sign", "农极客"),
	                new NameValuePair("type", "pt")};//设置短信内容  
	        post.setRequestBody(data);  
	        
	        try {
				client.executeMethod(post);
				result="1";
			} catch (HttpException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
	        org.apache.commons.httpclient.Header[] headers = post.getResponseHeaders();  
	        int statusCode = post.getStatusCode();  
	        System.out.println("statusCode:" + statusCode);  
	        for (org.apache.commons.httpclient.Header h : headers) {  
	            System.out.println(h.toString());  
	        }  
	        return result;
	}*/

    public static void main(String[] args) throws Exception {
        SmsBao smsBao = new SmsBao();
		/*//登录账号密码13526524092     821143
		// 用户名
		String name="13526524092"; 
		// 密码
		String pwd="6ACD05E026F8945A23A86ACC906D"; 
		// 电话号码字符串，中间用英文逗号间隔
		StringBuffer mobileString=new StringBuffer("");
		// 内容字符串
		StringBuffer contextString=new StringBuffer("短信内容");
		// 签名
		String sign="签名";
		// 追加发送时间，可为空，为空为及时发送
		String stime="";
		// 扩展码，必须为数字 可为空
		StringBuffer extno=new StringBuffer();

        System.out.println(doPost(name, pwd, mobileString, contextString, sign, stime, extno));*/
        smsBao.sendSMS("18736040966", "“发钱了！”\n" +
                "尊敬的农极客VIP用户，为了感谢猫友一直以来对农极客的支持，“国庆代金券大放送”，现在赠送您一张50元代金券！\n" +
                "该代金券，农极客商城所有产品通用，无门槛，有效期6个月，想啥时候用就啥时候用~\n" +
                "点击链接 http://a.app.qq.com/o/simple.jsp?pkgname=com.ylsoft.njk 即可进入农极客APP查看详情。");

    }


    public String sendSMS(String testPhone, String testContent) throws Exception {

        // 用户名
        String name = "13526524092";
        // 密码
        String pwd = "6ACD05E026F8945A23A86ACC906D";
        // 电话号码字符串，中间用英文逗号间隔
        StringBuffer mobileString = new StringBuffer(testPhone);
        // 内容字符串
        StringBuffer contextString = new StringBuffer(testContent);
        // 签名
        String sign = "农极客";
        // 追加发送时间，可为空，为空为及时发送
        String stime = "";
        // 扩展码，必须为数字 可为空
        StringBuffer extno = new StringBuffer();
        return doPost(name, pwd, mobileString, contextString, sign, stime, extno);
    }


    public static String request(String httpUrl, String httpArg) {
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();
        httpUrl = httpUrl + "?" + httpArg;

        try {
            URL url = new URL(httpUrl);
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

    public static String md5(String plainText) {
        StringBuffer buf = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();
            int i;
            buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return buf.toString();
    }

    public static String encodeUrlString(String str, String charset) {
        String strret = null;
        if (str == null)
            return str;
        try {
            strret = java.net.URLEncoder.encode(str, charset);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return strret;
    }


    /**
     * 发送短信
     *
     * @param name          用户名
     * @param pwd           密码
     * @param mobileString  电话号码字符串，中间用英文逗号间隔
     * @param contextString 内容字符串
     * @param sign          签名
     * @param stime         追加发送时间，可为空，为空为及时发送
     * @param extno         扩展码，必须为数字 可为空
     * @return
     * @throws Exception
     */
    public static String doPost(String name, String pwd,
                                StringBuffer mobileString, StringBuffer contextString,
                                String sign, String stime, StringBuffer extno) throws Exception {
        StringBuffer param = new StringBuffer();
        param.append("name=" + name);
        param.append("&pwd=" + pwd);
        param.append("&mobile=").append(mobileString);
        param.append("&content=").append(URLEncoder.encode(contextString.toString(), "UTF-8"));
        param.append("&stime=" + stime);
        param.append("&sign=").append(URLEncoder.encode(sign, "UTF-8"));
        param.append("&type=pt");
        param.append("&extno=").append(extno);

        URL localURL = new URL("http://web.wasun.cn/asmx/smsservice.aspx?");
        URLConnection connection = localURL.openConnection();
        HttpURLConnection httpURLConnection = (HttpURLConnection) connection;

        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Accept-Charset", "utf-8");
        httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        httpURLConnection.setRequestProperty("Content-Length", String.valueOf(param.length()));

        OutputStream outputStream = null;
        OutputStreamWriter outputStreamWriter = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        String resultBuffer = "";

        try {
            outputStream = httpURLConnection.getOutputStream();
            outputStreamWriter = new OutputStreamWriter(outputStream);

            outputStreamWriter.write(param.toString());
            outputStreamWriter.flush();

            if (httpURLConnection.getResponseCode() >= 300) {
                throw new Exception("HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
            }

            inputStream = httpURLConnection.getInputStream();
            resultBuffer = convertStreamToString(inputStream);

        } finally {

            if (outputStreamWriter != null) {
                outputStreamWriter.close();
            }

            if (outputStream != null) {
                outputStream.close();
            }

            if (reader != null) {
                reader.close();
            }

            if (inputStreamReader != null) {
                inputStreamReader.close();
            }

            if (inputStream != null) {
                inputStream.close();
            }

        }

        return resultBuffer;
    }


    /**
     * 转换返回值类型为UTF-8格式.
     *
     * @param is
     * @return
     */
    public static String convertStreamToString(InputStream is) {
        StringBuilder sb1 = new StringBuilder();
        byte[] bytes = new byte[4096];
        int size = 0;

        try {
            while ((size = is.read(bytes)) > 0) {
                String str = new String(bytes, 0, size, "UTF-8");
                sb1.append(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb1.toString();
    }

}
