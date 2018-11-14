package com.fh.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fh.service.wx_classRoom.AttendService;
import com.fh.service.wx_classRoom.LiveService;
import com.fh.service.wx_classRoom.WxClassRoomUserService;
import com.fh.util.utils.*;
import com.fh.util.weixinm.ClientCustomSSL;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class WxPayUtil {


    private static final String partner = "1482116722";
    private static final String app_id = "wxd423e43af7937dc0";
    private static final String appsecret = "0592aeccefe34e7cb4dc93b079160c0b";
    private static final String partnerkey = "5BCB0B1B9455219FF9628FA9DED938A2";
    private static final String notify = "http://m.nongjike.cn/NJK/app/classRoom/wxPayNotify";


    public static String wx_pay(HttpServletRequest request, HttpServletResponse response, String orderNum) {
        /*String MONEY = request.getParameter("money");
        String userId = request.getParameter("userId");
        String liveId = request.getParameter("liveId");
        String openid = request.getParameter("openid");
        String code = "1";
        //String flag = "{\"userId\":\"" + userId + "\",\"liveId\":\"" + liveId + "\"}";
        String flag =userId+","+liveId ;
        int money = (int)(Double.valueOf(MONEY)*100);
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String s = String.valueOf((int) ((Math.random() * 9 + 1) * 100));
        Date now = new Date();
        String no = s + sdf1.format(now).substring(2); // 订单号

        // 获取openId后调用统一支付接口https://api.mch.weixin.qq.com/pay/unifiedorder
        String currTime = TenpayUtil.getCurrTime();
        // 8位日期
        String strTime = currTime.substring(8, currTime.length());
        // 四位随机数
        String strRandom = TenpayUtil.buildRandom(4) + "";
        // 10位序列号,可以自行调整。
        String strReq = strTime + strRandom;

        // 商户号
        String mch_id = partner;
        // 随机数
        String nonce_str = strReq;
        // 商品描述根据情况修改
        String body = "小程序";
        // 商户订单号
        String out_trade_no = no;
        // 总金额以分为单位，不带小数点
        // Integer total_fee1=Integer.valueOf(MONEY)*100;
        DecimalFormat df = new DecimalFormat("######0"); //四色五入转换成整数
        System.out.println(String.valueOf(df.format(money)));
        String total_fee=String.valueOf(df.format(money));
        // 订单生成的机器 IP
        String spbill_create_ip = request.getRemoteAddr();
        // 这里notify_url是 支付完成后微信发给该链接信息，可以判断会员是否支付成功，改变订单状态等。
        String notify_url = notify;
        String trade_type = "JSAPI";

        SortedMap<String, String> packageParams = new TreeMap<String, String>();
        packageParams.put("appid", appid);
        packageParams.put("attach", flag);
        packageParams.put("mch_id", mch_id);
        packageParams.put("nonce_str", nonce_str);
        packageParams.put("body", body);
        packageParams.put("out_trade_no", out_trade_no);
        packageParams.put("total_fee", total_fee);
        packageParams.put("spbill_create_ip", spbill_create_ip);
        packageParams.put("notify_url", notify_url);
        packageParams.put("trade_type", trade_type);
        packageParams.put("openid", openid);

        RequestHandler reqHandler = new RequestHandler(request, response);
        reqHandler.init(appid, appsecret, partnerkey);

        String sign = reqHandler.createSign(packageParams);
        String xml = "<xml>" + "<appid>" + appid + "</appid>" + "<attach>" + flag + "</attach>" + "<mch_id>" + mch_id
                + "</mch_id>" + "<nonce_str>" + nonce_str + "</nonce_str>" + "<sign>" + sign + "</sign>" +
                // "<body>"+body+"</body>"+
                "<body><![CDATA[" + body + "]]></body>" + "<out_trade_no>" + out_trade_no + "</out_trade_no>"
                + "<total_fee>" + total_fee + "</total_fee>" + "<spbill_create_ip>" + spbill_create_ip
                + "</spbill_create_ip>" + "<notify_url>" + notify_url + "</notify_url>" + "<trade_type>" + trade_type
                + "</trade_type>" + "<openid>" + openid + "</openid>" + "</xml>";
        System.out.println(xml);
        String createOrderURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
        String prepay_id = "";
        try {
            prepay_id = new GetWxOrderno().getPayNo(createOrderURL, xml);
            System.out.println(prepay_id);
            if (prepay_id.equals("")) {
                // request.setAttribute("ErrorMsg", "统一支付接口获取预支付订单出错");
                System.out.println("ErrorMsg  统一支付接口获取预支付订单出错");
                // response.sendRedirect("error.jsp");
                code = "2";
            }
        } catch (Exception e1) {
            //
            e1.printStackTrace();
        }

        SortedMap<String, String> finalpackage = new TreeMap<String, String>();
        String appid2 = appid;
        String timestamp = Sha1Util.getTimeStamp();
        String nonceStr2 = nonce_str;
        String prepay_id2 = "prepay_id=" + prepay_id;
        String packages = prepay_id;
        finalpackage.put("appId", appid2);
        finalpackage.put("timeStamp", timestamp);
        finalpackage.put("nonceStr", nonceStr2);
        finalpackage.put("total_fee", total_fee);
        finalpackage.put("package", prepay_id2);
        finalpackage.put("signType", "MD5");
        String finalsign = reqHandler.createSign(finalpackage);
        System.out.println(finalsign);
        finalpackage.put("sign", finalsign);
        finalpackage.put("code", code);
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(finalpackage);
        } catch (JsonGenerationException e) {
            //
            e.printStackTrace();
        } catch (JsonMappingException e) {
            //
            e.printStackTrace();
        } catch (IOException e) {
            //
            e.printStackTrace();
        }
        return str;*/
        String partner = "1482116722";
        String appid = "wxd423e43af7937dc0";
        String appsecret = "0592aeccefe34e7cb4dc93b079160c0b";
        String partnerkey = "5BCB0B1B9455219FF9628FA9DED938A2";
        String money = request.getParameter("money");
        String openId = request.getParameter("openid");
        String liveid = request.getParameter("liveId");
        String userid = request.getParameter("userId");
        String flag = openId + "," + liveid + "," + userid + "," + money;
        String code = "1";
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String s = String.valueOf((int) ((Math.random() * 9 + 1) * 100));
        Date now = new Date();
        String no = s + sdf1.format(now).substring(2); // 订单号

        // 获取openId后调用统一支付接口https://api.mch.weixin.qq.com/pay/unifiedorder
        String currTime = TenpayUtil.getCurrTime();
        // 8位日期
        String strTime = currTime.substring(8, currTime.length());
        // 四位随机数
        String strRandom = TenpayUtil.buildRandom(4) + "";
        // 10位序列号,可以自行调整。
        String strReq = strTime + strRandom;

        // 商户号
        String mch_id = partner;
        // 随机数
        String nonce_str = strReq;
        // 商品描述根据情况修改
        String body = "小程序";
        // 商户订单号
        String out_trade_no = no;
        // 总金额以分为单位，不带小数点
        // Integer total_fee1=Integer.valueOf(MONEY)*100;
        Double moneys = Double.valueOf(money) * 100;
        DecimalFormat df = new DecimalFormat("######0"); //四色五入转换成整数
        System.out.println(String.valueOf(df.format(moneys)));
        String total_fee = String.valueOf(df.format(moneys));
        // 订单生成的机器 IP
        String spbill_create_ip = request.getRemoteAddr();
        // 这里notify_url是 支付完成后微信发给该链接信息，可以判断会员是否支付成功，改变订单状态等。
        String notify_url = notify;
        String trade_type = "JSAPI";

        SortedMap<String, String> packageParams = new TreeMap<String, String>();
        packageParams.put("appid", appid);
        packageParams.put("attach", flag);
        packageParams.put("mch_id", mch_id);
        packageParams.put("nonce_str", nonce_str);
        packageParams.put("body", body);
        packageParams.put("out_trade_no", out_trade_no);
        packageParams.put("total_fee", total_fee);
        packageParams.put("spbill_create_ip", spbill_create_ip);
        packageParams.put("notify_url", notify_url);
        packageParams.put("trade_type", trade_type);
        packageParams.put("openid", openId);

        RequestHandler reqHandler = new RequestHandler(request, response);
        reqHandler.init(appid, appsecret, partnerkey);

        String sign = reqHandler.createSign(packageParams);
        String xml = "<xml>" + "<appid>" + appid + "</appid>" + "<attach>" + flag + "</attach>" + "<mch_id>" + mch_id
                + "</mch_id>" + "<nonce_str>" + nonce_str + "</nonce_str>" + "<sign>" + sign + "</sign>" +
                // "<body>"+body+"</body>"+
                "<body><![CDATA[" + body + "]]></body>" + "<out_trade_no>" + out_trade_no + "</out_trade_no>"
                + "<total_fee>" + total_fee + "</total_fee>" + "<spbill_create_ip>" + spbill_create_ip
                + "</spbill_create_ip>" + "<notify_url>" + notify_url + "</notify_url>" + "<trade_type>" + trade_type
                + "</trade_type>" + "<openid>" + openId + "</openid>" + "</xml>";
        System.out.println(xml);
        String createOrderURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
        String prepay_id = "";
        try {
            prepay_id = new GetWxOrderno().getPayNo(createOrderURL, xml);
            System.out.println(prepay_id);
            if (prepay_id.equals("")) {
                // request.setAttribute("ErrorMsg", "统一支付接口获取预支付订单出错");
                System.out.println("ErrorMsg  统一支付接口获取预支付订单出错");
                // response.sendRedirect("error.jsp");
                code = "2";
            }
        } catch (Exception e1) {
            //
            e1.printStackTrace();
        }

        SortedMap<String, String> finalpackage = new TreeMap<String, String>();
        String appid2 = appid;
        String timestamp = Sha1Util.getTimeStamp();
        String nonceStr2 = nonce_str;
        String prepay_id2 = "prepay_id=" + prepay_id;
        String packages = prepay_id;
        finalpackage.put("appId", appid2);
        finalpackage.put("timeStamp", timestamp);
        finalpackage.put("nonceStr", nonceStr2);
        finalpackage.put("package", prepay_id2);
        finalpackage.put("signType", "MD5");
        String finalsign = reqHandler.createSign(finalpackage);
        System.out.println(finalsign);
        finalpackage.put("sign", finalsign);
        finalpackage.put("code", code);
        org.codehaus.jackson.map.ObjectMapper mapper = new org.codehaus.jackson.map.ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(finalpackage);
        } catch (JsonGenerationException e) {
            //
            e.printStackTrace();
        } catch (JsonMappingException e) {
            //
            e.printStackTrace();
        } catch (IOException e) {
            //
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 小程序的课程购买回调
     *
     * @param request
     * @param response
     * @param attendService
     * @param pd
     * @param wxClassRoomUserService
     * @return
     * @throws Exception
     */
    public static String wxPayNotify(HttpServletRequest request, HttpServletResponse response, AttendService attendService, PageData pd, WxClassRoomUserService wxClassRoomUserService, LiveService liveService) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream()));
        String line = null;
        StringBuilder sb = new StringBuilder();
        PageData user = new PageData();
        SmsBao sms = new SmsBao();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        System.out.println(sb);
        Map<String, String> map = GetWxOrderno.doXMLParse(sb.toString());
        String attach = map.get("attach");
        String result_code = map.get("result_code");
        String ORDER_NUMBER = map.get("out_trade_no");
        String xml = "";
        boolean index = WxPayUtil.payState(result_code);
        // String xml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[签名失败]]></return_msg>" + "</xml>";
        xml = WxPayUtil.getPayXml(index);
        String[] strs = attach.split(",");
        PageData pds = new PageData();
        String oldMoney = "";
        pd.put("userId", strs[2]);
        pd.put("liveId", strs[1]);
        pds = (PageData) liveService.findLiveOne(SqlCentre.LIVE_findLiveOne, pd).get("data");
        pd.put("look_pay", "2");
        user.put("cr_userid", pds.get("cr_userid").toString());
        user.put("cr_userBalance", strs[3]);
        if (index) {
            attendService.save(pd, SqlCentre.ATTEND_SAVE);
            wxClassRoomUserService.updateMoney(user, SqlCentre.USER_UPDATE_MONEY);
        }
        return xml;
    }

    public static boolean payState(String result_code) {
        boolean index = false;
        if ("SUCCESS".equals(result_code)) {
            index = true;
        }
        return index;
    }


    public static String getPayXml(Boolean payState) {
        String xml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[签名失败]]></return_msg>" + "</xml>";
        if (payState) {
            xml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>" + "<return_msg><![CDATA[OK]]></return_msg>"
                    + "</xml>";
        }
        return xml;
    }


    public static PageData tixian(HttpServletRequest request, HttpServletResponse response){
        //1.0 拼凑企业支付需要的参数
        ObjectMapper mapper = new ObjectMapper();
        PageData pd=new PageData();
        try{
            String currTime = TenpayUtil.getCurrTime();
            // 8位日期
            String strTime = currTime.substring(8, currTime.length());
            // 四位随机数
            String strRandom = TenpayUtil.buildRandom(4) + "";
            // 10位序列号,可以自行调整。
            String strReq = strTime + strRandom;
            String openid = request.getParameter("openid");
            String money = request.getParameter("money");
            int amount=(int) (Double.valueOf(money)*100);
            // 随机数
            Date now = new Date();
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            String s = String.valueOf((int) ((Math.random() * 9 + 1) * 100));
            String no = s + sdf1.format(now).substring(2); // 订单号
            String nonce_str = strReq;
            String appid = app_id;  //微信公众号的appid
            String mch_id = partner ; //商户号
            String partner_trade_no =  no; //生成商户订单号
            String check_name = "NO_CHECK"; //是否验证真实姓名呢
            //String re_user_name = "小郑";   //收款用户姓名            //企业付款金额，单位为分
            String desc = "提现";   //企业付款操作说明信息。必填。
            String spbill_create_ip = PayUtil.getLocalIp(request);     //

            //2.0 生成map集合
            SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
            packageParams.put("mch_appid", appid);         //微信公众号的appid
            packageParams.put("mchid", mch_id);       //商务号
            packageParams.put("nonce_str",nonce_str);  //随机生成后数字，保证安全性

            packageParams.put("partner_trade_no",partner_trade_no); //生成商户订单号
            packageParams.put("openid",openid);            // 支付给用户openid
            packageParams.put("check_name",check_name);    //是否验证真实姓名呢
            //packageParams.put("re_user_name",re_user_name);//收款用户姓名
            packageParams.put("amount",amount-200);            //企业付款金额，单位为分
            packageParams.put("desc",desc);                //企业付款操作说明信息。必填。
            packageParams.put("spbill_create_ip",spbill_create_ip); //调用接口的机器Ip地址


            //3.0 生成自己的签名
            String sign  = createSign("utf-8",packageParams);

            //4.0 封装退款对象
            packageParams.put("sign", sign);

            //5.0将当前的map结合转化成xml格式
            String xml = "<xml>"
                    + "<mch_appid>" + appid + "</mch_appid>"
                    + "<mchid>1482116722</mchid>"
                    + "<nonce_str>" + nonce_str + "</nonce_str>"
                    + "<partner_trade_no>" + partner_trade_no + "</partner_trade_no>"
                    + "<openid>" + openid + "</openid>"
                    + "<check_name>NO_CHECK</check_name>"
                    //+ "<re_user_name>"+re_user_name+"</re_user_name>"
                    + "<amount>"+(amount-200)+"</amount>"
                    + "<desc>"+desc+"</desc>"
                    + "<spbill_create_ip>" + spbill_create_ip + "</spbill_create_ip>"
                    + "<sign>" + sign  + "</sign>"
                    + "</xml>";
            System.out.println(xml);
            //6.0获取需要发送的url地址
            String wxUrl = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers"; //获取退款的api接口
            String weixinPost = ClientCustomSSL.doRefund1(wxUrl, xml).toString();
            System.out.println(weixinPost);
            Map<String, String> map = GetWxOrderno.doXMLParse(weixinPost);
            if(map.get("result_code").toString().equals("SUCCESS")){
                pd.clear();
                pd.put("code", "1");
                pd.put("message", "正确返回数据!");
            }else{
                pd.clear();
                pd.put("code", "2");
                pd.put("message", "提现失败!");
            }
        }catch(Exception e){
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序错误,请联系系统管理员!");
            e.printStackTrace();
        }
        return pd;
    }



    public static String createSign(String characterEncoding,SortedMap<Object,Object> parameters){
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();//所有参与传参的参数按照accsii排序（升序）
        Iterator it = es.iterator();
        while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            String k = (String)entry.getKey();
            Object v = entry.getValue();
            if(null != v && !"".equals(v)
                    && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=5BCB0B1B9455219FF9628FA9DED938A2");
        System.out.println("签名字符串:"+sb.toString());
        System.out.println("签名MD5未变大写：" + MD5Util.MD5Encode(sb.toString(), characterEncoding));
        String sign = MD5Util.MD5Encode(sb.toString(), characterEncoding).toUpperCase();
        //String sign = MD5Util.MD5Encode(sb.toString(), this.charset).toUpperCase();
        return sign;


    }
}
