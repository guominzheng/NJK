package com.fh.controller.app.appuser;

import javax.annotation.Resource;

import com.fh.service.system.appuser.AppuserService;
import com.fh.service.system.appuserInfo.AppUserInfoService;
import com.fh.util.DateUtil;
import com.fh.util.MD5;
import com.fh.util.PageData;
import com.fh.util.SmsBao;
import com.fh.util.UuidUtil;

public class Register implements Runnable {

    private String USER_ID;
    private String USERNAME;
    private String IMG;
    private String OPENID;
    private String NAME;
    @Resource(name = "appuserService")
    private AppuserService appuserService;
    @Resource(name = "appUserInfoService")
    private AppUserInfoService appUserInfoService;

    public Register(String USER_ID, String USERNAME, String IMG, String OPENID, String NAME, AppuserService appuserService, AppUserInfoService appUserInfoService) {
        this.USER_ID = USER_ID;
        this.USERNAME = USERNAME;
        this.IMG = IMG;
        this.OPENID = OPENID;
        this.NAME = NAME;
        this.appuserService = appuserService;
        this.appUserInfoService = appUserInfoService;
    }

    public synchronized void run() {
        PageData pd = new PageData();
        try {
            String yzm = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
            pd.put("USER_ID", USER_ID);
            pd.put("USERNAME", USERNAME);
            pd.put("NAME", NAME);
            pd.put("DATE", DateUtil.getTime());// 注册时间
            pd.put("PASSWORD", MD5.md5(MD5.md5("123456") + yzm));
            pd.put("ROLE_ID", "77"); // 角色
            pd.put("LAST_LOGIN", "");
            pd.put("IP", ""); // IP
            pd.put("EMAIL", "");
            pd.put("CHANNELID", "");
            pd.put("SALT", yzm);
            pd.put("EXCLU_ID", "5d18274e708c472ca679622f2c964ce0");
            pd.put("CROP", "");
            pd.put("PROVINCE", "");
            pd.put("CITY", "");
            pd.put("DISTRICT", "");
            pd.put("CUSTOMER_NAME", "");
            pd.put("PHONE", "");
            pd.put("VIP", "0");
            pd.put("STATUS", "0");
            pd.put("ADDRESS", "");
            pd.put("OPENID", OPENID);
            pd.put("JIFEN", "0");
            pd.put("ZJIFEN", "0");
            pd.put("IMG", "");
            pd.put("TYPE", "");
            pd.put("PHONEADDRESS", "");
            appuserService.save(pd);
            PageData pd2 = new PageData();
            pd2.put("SYS_APP_USERINFO_ID", UuidUtil.get32UUID());
            pd2.put("USER_ID", pd.getString("USER_ID"));
            pd2.put("SEX", "0");
            pd2.put("VIP", "0");
            pd2.put("IMG", IMG);
            pd2.put("QQ", "");
            pd2.put("NIAN1", "");
            pd2.put("YUE", "");
            pd2.put("RI", "");
            pd2.put("ZHIYE", "");
            pd2.put("ADDRESS", "");
            pd2.put("PHONE", "");
            pd2.put("QIANMING", "每天农资,祝你好心情!");
            pd2.put("WEIXIN", "");
            appUserInfoService.save(pd2);
            SmsBao sms = new SmsBao();
            String context = "您的农极客APP初始密码为【123456】,欢迎各大应用商店下载,学习更多农技专题.";
            String result = sms.sendSMS(USERNAME, context);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
