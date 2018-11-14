package com.fh.util;

import com.fh.service.system.appuser.AppuserService;
import com.fh.service.system.appuserInfo.AppUserInfoService;

public class SaveSysUserUtil {

    public static  void saveUser(PageData pd, AppuserService appuserService,AppUserInfoService appUserInfoService) throws Exception{
        //需要传值
        //phone  passWord  nickname  openid  unionid  sex headImageUrl city
        String yzm = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
        pd.put("USER_ID", pd.get("USER_ID").toString());
        pd.put("USERNAME",pd.get("phone").toString());
        pd.put("DATE", DateUtil.getTime());// 注册时间
        pd.put("PASSWORD", MD5.md5(MD5.md5(pd.get("passWord").toString()) + yzm));
        pd.put("ROLE_ID", "77"); // 角色
        pd.put("NAME", pd.get("nickname").toString());
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
        pd.put("OPENID", pd.get("openid").toString());
        pd.put("UNIONID", pd.get("unionid").toString());
        pd.put("ZJIFEN", "0");
        pd.put("JIFEN", "0");
        pd.put("IMG", "");
        pd.put("TYPE", "");
        pd.put("PHONEADDRESS", "");
        appuserService.save(pd);
        PageData pd2 = new PageData();
        pd2.put("SYS_APP_USERINFO_ID", pd.get("USERINFO_ID"));
        pd2.put("USER_ID", pd.getString("USER_ID"));
        pd2.put("SEX", pd.getString("sex"));
        pd2.put("VIP", "0");
        pd2.put("IMG", pd.getString("headImageUrl"));
        pd2.put("QQ", "");
        pd2.put("NIAN1", "");
        pd2.put("YUE", "");
        pd2.put("RI", "");
        pd2.put("ZHIYE", "");
        pd2.put("ADDRESS", pd.getString("city"));
        pd2.put("PHONE", "");
        pd2.put("QIANMING", "每天农资,祝你好心情!");
        pd2.put("WEIXIN", "");
        appUserInfoService.save(pd2);
    }
}
