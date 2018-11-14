// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2018/11/13 13:44:30
// Home Page:  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   WxClassRoomUserService.java
package com.fh.service.wx_classRoom;

import com.alibaba.fastjson.JSONArray;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.dto.MessageDto;
import com.fh.entity.vo.User;
import com.fh.service.system.appuser.AppuserService;
import com.fh.service.system.appuserInfo.AppUserInfoService;
import com.fh.util.DateUtil;
import com.fh.util.PageData;
import com.fh.util.SaveSysUserUtil;
import com.fh.util.ServiceUtil;
import com.fh.util.jdeis.JedisClient;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value="wxClassRoomUserService")
public class WxClassRoomUserService {
    @Resource(name="daoSupport")
    private DaoSupport dao;
    @Autowired
    private JedisClient jedisClient;
    private static final String USERINFO = "USERINFO";

    public Map findUserByCondition(PageData pd, String sql) {
        return ServiceUtil.serviceQuery((String)sql, (Page)null, (DaoSupport)this.dao, (String)"object", (PageData)pd);
    }

    public Map findUserList(PageData pd) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        try {
            map.put("data", (List)this.dao.findForList("ClassRoom_UserMapper.findUserByCondition", pd));
            map.put("code", "1");
        }
        catch (Exception e) {
            e.printStackTrace();
            map.put("message", "\u7a0b\u5e8f\u51fa\u9519\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458\uff01");
            map.put("code", "2");
        }
        return map;
    }

    public User findUserByUninonId(User user, String sql) throws Exception {
        return (User)this.dao.findForObject(sql, user);
    }

    public Map updateMoney(PageData pd, String sql) {
        return ServiceUtil.serviceUpdate((String)sql, (PageData)pd, (DaoSupport)this.dao, (String)"update");
    }

    public Map save(PageData pd, String sql) {
        return ServiceUtil.serviceUpdate((String)sql, (PageData)pd, (DaoSupport)this.dao, (String)"insert");
    }

    public void editUsser(PageData pd) throws Exception {
        this.dao.update("ClassRoom_UserMapper.editUsser", pd);
    }

    public PageData findUser(MessageDto messageDto) throws Exception {
        PageData pd = new PageData();
        pd.put("openid", messageDto.getOpenid());
        return (PageData)this.dao.findForObject("ClassRoom_UserMapper.findUserAllData", pd);
    }

    public Map login(User user, String sql) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        User users = null;
        PageData pd = new PageData();
        List sys_users = null;
        Object sys_user = null;
        try {
            users = this.findUserByUninonId(user, sql);
            if (user.getCr_unionid() != null) {
                pd.put("UNIONID", user.getCr_unionid());
                sys_users = (List)this.dao.findForList("AppuserMapper.findUnionId", pd);
            }
            if (users == null) {
                pd.put("cr_userName", "");
                pd.put("cr_userImg", "");
                pd.put("cr_userBalance", "0.0");
                pd.put("cr_teacherStatus", "0");
                pd.put("cr_userPhone", "");
                pd.put("cr_createTime", DateUtil.getTime());
                pd.put("cr_updataTime", DateUtil.getTime());
                pd.put("cr_openId", user.getCr_openId());
                pd.put("cr_session_key", user.getCr_session_key());
                if (sys_users != null && sys_users.size() > 0) {
                    pd.put("cr_bind", ((PageData)sys_users.get(0)).get("USER_ID").toString());
                } else {
                    pd.put("cr_bind", "");
                }
                if (null == user.getCr_unionid()) {
                    pd.put("cr_unionid", "");
                } else {
                    pd.put("cr_unionid", user.getCr_unionid());
                }
                this.save(pd, "ClassRoom_UserMapper.save");
                pd.clear();
                users = this.findUserByUninonId(user, sql);
            }
            if (sys_users != null && sys_users.size() > 0) {
                users.setPageData((PageData)sys_users.get(0));
            }
            map.put("code", "1");
            map.put("data", users);
            this.jedisClient.hset("USERINFO", users.getCr_openId(), JSONArray.toJSONString(users));
            this.jedisClient.expire(users.getCr_openId(), 18000);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.err.println("==========================>login\u51fa\u932f\uff01");
            map.put("code", "2");
            map.put("message", "\u7a0b\u5e8f\u51fa\u9519\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458\uff01");
        }
        return map;
    }

    public String loginCach(User user, String sql) {
        Map map = new HashMap();
        String returnStr = this.jedisClient.hget("USERINFO", user.getCr_openId());
        String returns = "";
        if (null != returnStr) {
            returns = "{\"code\":\"1\",\"data\":" + returnStr + "}";
            System.out.println(returns);
            return returns;
        }
        map = this.login(user, sql);
        return JSONArray.toJSONString(map);
    }

    public Map bind(String sql, PageData pd, AppuserService appuserService, AppUserInfoService appUserInfoService) {
        Map map = new HashMap<String, String>();
        List yzms = null;
        PageData yzm = null;
        PageData user = null;
        try {
            pd.put("USERNAME", pd.get("phone").toString());
            yzms = (List)this.dao.findForList("YzmMapper.findByPhone", pd);
            if (yzms != null && yzms.size() > 0) {
                if (pd.getString("yzm").equals((yzm = (PageData)yzms.get(0)).getString("YZM"))) {
                    if ((user = (PageData)this.dao.findForObject("AppuserMapper.findName", pd)) != null) {
                        pd.put("cr_bind", user.get("USER_ID").toString());
                        this.dao.update(sql, pd);
                    } else {
                        pd.put("UNIONID", pd.getString("unionid "));
                        user = (PageData)this.dao.findForObject("AppuserMapper.findUnionId", pd);
                        if (user != null) {
                            pd.put("cr_bind", user.get("USER_ID").toString());
                            this.dao.update(sql, pd);
                        } else {
                            SaveSysUserUtil.saveUser((PageData)pd, (AppuserService)appuserService, (AppUserInfoService)appUserInfoService);
                            pd.put("cr_bind", pd.get("USER_ID").toString());
                            this.dao.update(sql, pd);
                        }
                    }
                    map = ServiceUtil.serviceQuery((String)"ClassRoom_UserMapper.findUserAllData", (Page)null, (DaoSupport)this.dao, (String)"object", (PageData)pd);
                } else {
                    map.put("code", "2");
                    map.put("message", "\u9a8c\u8bc1\u7801\u586b\u5199\u9519\u8bef\uff01");
                }
            } else {
                map.put("code", "2");
                map.put("message", "\u8bf7\u5148\u83b7\u53d6\u9a8c\u8bc1\u7801\uff01");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            map.put("code", "2");
            map.put("message", "\u7a0b\u5e8f\u51fa\u9519,\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458");
        }
        return map;
    }

    public PageData getUserById(PageData pd) throws Exception {
        return (PageData)this.dao.findForObject("ClassRoom_UserMapper.findUserByCondition", pd);
    }
}
