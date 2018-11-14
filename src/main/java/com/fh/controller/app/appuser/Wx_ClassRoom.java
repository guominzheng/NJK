// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2018/11/13 14:15:59
// Home Page:  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   Wx_ClassRoom.java
package com.fh.controller.app.appuser;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.dto.MessageDto;
import com.fh.entity.vo.LiveRecord;
import com.fh.entity.vo.User;
import com.fh.service.system.appuser.AppuserService;
import com.fh.service.system.appuserInfo.AppUserInfoService;
import com.fh.service.wx_classRoom.AdvertisingService;
import com.fh.service.wx_classRoom.AttendService;
import com.fh.service.wx_classRoom.BoomService;
import com.fh.service.wx_classRoom.CodeService;
import com.fh.service.wx_classRoom.CritiCalService;
import com.fh.service.wx_classRoom.HomePageService;
import com.fh.service.wx_classRoom.HotKeyService;
import com.fh.service.wx_classRoom.LiveService;
import com.fh.service.wx_classRoom.ProposService;
import com.fh.service.wx_classRoom.QuratzService;
import com.fh.service.wx_classRoom.RecordService;
import com.fh.service.wx_classRoom.StatisticsService;
import com.fh.service.wx_classRoom.WxClassRoomUserService;
import com.fh.service.wx_classRoom.WxOpinionService;
import com.fh.service.wx_classRoom.ZixunService;
import com.fh.util.DateUtil;
import com.fh.util.ImageAnd64Binary;
import com.fh.util.Logger;
import com.fh.util.ModeUtil;
import com.fh.util.PageData;
import com.fh.util.PathUtil;
import com.fh.util.SmallLoginUtil;
import com.fh.util.SocketSendUtil;
import com.fh.util.Websocket.WebSocketRunable;
import com.fh.util.WxPayUtil;
import com.fh.util.jdeis.JedisClient;
import com.fh.util.netty.constant.Constant;
import com.fh.util.netty.server.BaseWebSocketServerHandler;
import io.netty.channel.group.ChannelGroup;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.HashedMap;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = {"app/classRoom"})
public class Wx_ClassRoom
        extends BaseController {
    @Autowired
    private WxClassRoomUserService wxClassRoomUserService;
    @Autowired
    private HomePageService homePageService;
    @Autowired
    private LiveService liveService;
    @Autowired
    private ZixunService zixunService;
    @Autowired
    private AttendService attendService;
    @Autowired
    private ProposService proposService;
    @Autowired
    private AppuserService appuserService;
    @Autowired
    private AppUserInfoService appUserInfoService;
    @Autowired
    private RecordService recordService;
    @Autowired
    private QuratzService quratzService;
    @Autowired
    private JedisClient jedisClient;
    private static ReentrantLock lock = new ReentrantLock();
    @Autowired
    private CodeService codeService;
    @Autowired
    private CritiCalService critiCalService;
    @Autowired
    private HotKeyService hotKeyService;
    @Autowired
    private WxOpinionService opinionService;
    @Autowired
    private BoomService boomService;
    @Autowired
    private StatisticsService statisticsService;
    @Autowired
    private AdvertisingService advertisingService;

    @RequestMapping(value = {"findUserliveList"}, produces = {"text/html;charset=UTF-8"})
    @ResponseBody
    public String findUserliveList(String userId) {
        Wx_ClassRoom.logBefore( this.logger,  "\u67e5\u8be2\u6536\u85cf\u95ee\u9898");
        PageData pd = new PageData();
        Page page = new Page();
        pd = this.getPageData();
        try {
            PageData pdDate = this.liveService.findComeGolive(pd);
            HashedMap map = new HashedMap();
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "\u6b63\u786e\u8fd4\u56de\u6570\u636e!");
            pd.put("data", pdDate);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "\u7a0b\u5e8f\u51fa\u9519,\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }

    @RequestMapping(value = {"editUser"}, produces = {"text/html;charset=UTF-8"})
    @ResponseBody
    public String editUser(String userId, String cr_userName, String cr_userImg) {
        this.logger.info("\u4fee\u6539");
        PageData pd = this.getPageData();
        try {
            this.wxClassRoomUserService.editUsser(pd);
            User user = new User();
            user.setCr_userid(Integer.valueOf(Integer.parseInt(userId)));
            user = this.wxClassRoomUserService.findUserByUninonId(user, "ClassRoom_UserMapper.findUserByUninonId");
            this.jedisClient.hdel("USERINFO", user.getCr_openId());
            this.jedisClient.hset("USERINFO", user.getCr_openId(), JSONArray.toJSONString(user));
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
        } catch (Exception e) {
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
            e.printStackTrace();
        }
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(pd);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }

    @ResponseBody
    @RequestMapping(value = {"/login"}, produces = {"text/html;charset=utf-8"})
    public String login(String code) throws Exception {
        this.logger.info("login======================>");
        HashMap<String, Object> map = new HashMap<String, Object>();
        if (code == null || code.length() == 0) {
            map.put("coed", 2);
            map.put("message", "code\u4e0d\u80fd\u4e3a\u7a7a");
            return JSONArray.toJSONString(map);
        }
        Object pageData = null;
        User user = SmallLoginUtil.loginCode( code);
        return this.wxClassRoomUserService.loginCach(user, "ClassRoom_UserMapper.findUserByUninonId");
    }

    @ResponseBody
    @RequestMapping(value = {"/getFirstPage"}, produces = {"text/html;charset=utf-8"})
    public String getFirstPage() {
        PageData pd = this.getPageData();
        pd.put("comeGo", "0");
        Page page = this.getThisPage(this.getPage(), pd);
        return JSONArray.toJSONString(this.homePageService.getFirstPage(page));
    }

    @ResponseBody
    @RequestMapping(value = {"/findUserByCondition"}, produces = {"text/html;charset=utf-8"})
    public String findUserByCondition() {
        this.logger.info("findUserByCondition======================>");
        return JSONArray.toJSONString(this.wxClassRoomUserService.findUserByCondition(this.getPageData(), "ClassRoom_UserMapper.findUserByCondition"));
    }

    @ResponseBody
    @RequestMapping(value = {"/findLiveList"}, produces = {"text/html;charset=utf-8"})
    public String findLiveList(String pageNum) {
        this.logger.info("findLiveList======================>");
        HashMap<String, Object> map = new HashMap<String, Object>();
        PageData pd = new PageData();
        Page page = new Page();
        pd = this.getPageData();
        try {
            page.setPd(pd);
            page.setShowCount(10);
            page.setCurrentPage(Integer.parseInt(pageNum));
            List blackList = this.liveService.findLiveList(page, "ClassRoom_LiveMapper.findLiveListlistPage");
            if (page.getCurrentPage() == Integer.parseInt(pageNum)) {
                map.put("critical", blackList);
            } else {
                ArrayList list3 = new ArrayList();
                map.put("critical", list3);
            }
            map.put("code", "1");
            map.put("message", "数据已经加载完毕");
        } catch (Exception e) {
            e.printStackTrace();
            map.clear();
            map.put("code", "2");
            map.put("message", "程序出错请联系管理员！");
        }
        return JSONArray.toJSONString(map);
    }

    @ResponseBody
    @RequestMapping(value = {"/searchLive"}, produces = {"text/html;charset=utf-8"})
    public String searchLive(String searchKey) {
        this.logger.info("searchLive======================>");
        HashMap<String, Object> map = new HashMap<String, Object>();
        PageData pd = null;
        try {
            pd = this.getPageData();
            if (!(null == searchKey || "".equals(searchKey))) {
                PageData key;
                if (null != (key = this.hotKeyService.one(pd)) && key.size() > 0) {
                    this.hotKeyService.update(key);
                } else {
                    PageData dtoKey = new PageData();
                    dtoKey.put("hotKey", searchKey);
                    dtoKey.put("createTime", DateUtil.getTime());
                    dtoKey.put("num", "0");
                    this.hotKeyService.save(dtoKey);
                }
            }
            map.put("code", "1");
            map.put("data", this.liveService.seache(pd));
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", "2");
            map.put("message", "程序出错请联系管理员！");
        }
        return this.JsonMapper(map);
    }

    @ResponseBody
    @RequestMapping(value = {"/findLiveById"}, produces = {"text/html;charset=utf-8"})
    public String findLiveById(String pageNum) {
        this.logger.info("findLiveById======================>");
        Map map = this.liveService.one(this.getThisPage(this.getPage(), this.getPageData()), "ClassRoom_LiveMapper.findLiveById", "0");
        System.out.println(map.get("live"));
        PageData pd = new PageData();
        Page page = new Page();
        List calList = null;
        pd = this.getPageData();
        try {
            map.put("advertising", this.advertisingService.findDataById(pd));
            page.setPd(pd);
            page.setShowCount(10);
            page.setCurrentPage(Integer.parseInt(pageNum));
            List<LiveRecord> blackList = this.liveService.getRecordList(page);
            if (page.getCurrentPage() == Integer.parseInt(pageNum)) {
                for (int i = 0; i < blackList.size(); ++i) {
                    LiveRecord black = (LiveRecord) blackList.get(i);
                    String record_id = black.getRecord_id();
                    calList = new ArrayList();
                    PageData pageData = new PageData();
                    pageData.put("record_id", record_id);
                    calList = this.critiCalService.getAllCal(pageData);
                     blackList.get(i).setCalList(calList);
                }
                map.put("critical", blackList);
            } else {
                ArrayList list3 = new ArrayList();
                map.put("critical", list3);
            }
            map.put("code", "1");
            map.put("message", "数据已经加载完毕");
        } catch (Exception e) {
            e.printStackTrace();
            map.clear();
            map.put("code", "2");
            map.put("message", "程序出错请联系管理员！");
        }
        return JSONArray.toJSONString(map);
    }

    @ResponseBody
    @RequestMapping(value = {"/findCalList"}, produces = {"text/html;charset=utf-8"})
    public String findCalList() {
        this.logger.info("findCalList======================>");
        return JSONArray.toJSONString(this.liveService.findCalList(this.getThisPage(this.getPage(), this.getPageData()), "ClassRoom_LiveMapper.findCalByLiveIdlistPage"));
    }

    @ResponseBody
    @RequestMapping(value = {"/findAttendList"}, produces = {"text/html;charset=utf-8"})
    public String findAttendList(String pageNum) {
        this.logger.info("findAttendList======================>");
        PageData pd = new PageData();
        Page page = new Page();
        pd = this.getPageData();
        HashedMap map = new HashedMap();
        try {
            page.setPd(pd);
            page.setShowCount(10);
            page.setCurrentPage(Integer.parseInt(pageNum));
            List blackList = this.attendService.findAttendList(page);
            if (page.getCurrentPage() == Integer.parseInt(pageNum)) {
                map.put("data", blackList);
            } else {
                ArrayList list3 = new ArrayList();
                map.put("data", list3);
            }
            pd.clear();
            map.put("code", "1");
            map.put("message", "数据已经加载完毕");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", "2");
            map.put("message", "程序出错请联系管理员！");
        }
        return this.JsonMapper(map);
    }

    private String JsonMapper(Object obj) {
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(obj);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }

    @ResponseBody
    @RequestMapping(value = {"/saveLook"}, produces = {"text/html;charset=utf-8"})
    public String saveLook() throws Exception {
        this.logger.info("saveLook======================>");
        PageData pd = this.getPageData();
        pd.put("createTime", DateUtil.getTime());
        pd.put("look_pay", "1");
        return JSONArray.toJSONString(this.attendService.save(pd, "ClassRoom_AttendMapper.save"));
    }

    @ResponseBody
    @RequestMapping(value = {"/findZixunList"}, produces = {"text/html;charset=utf-8"})
    public String findZixunList() {
        this.logger.info("findZixunList======================>");
        return JSONArray.toJSONString(this.zixunService.list(this.getThisPage(this.getPage(), this.getPageData()), "ClassRoom_ZixunMapper.findAllZixunlistPage"));
    }

    @ResponseBody
    @RequestMapping(value = {"/findZixunOne"}, produces = {"text/html;charset=utf-8"})
    public String findZixunOne() {
        this.logger.info("findZixunOne======================>");
        return JSONArray.toJSONString(this.zixunService.one(this.getPageData(), "ClassRoom_ZixunMapper.findZixunById"));
    }

    @ResponseBody
    @RequestMapping(value = {"/proposSave"}, produces = {"text/html;charset=utf-8"})
    public String proposSave(String propos_userName, HttpServletRequest request) {
        this.logger.info("proposSave======================>");
        PageData pd = this.getPageData();
        pd.put("createTime", DateUtil.getTime());
        pd.put("propos_status", "1");
        return JSONArray.toJSONString(this.proposService.save(pd, "ClassRoom_ProposMapper.save"));
    }

    @ResponseBody
    @RequestMapping(value = {"/send"}, produces = {"text/html;charset=utf-8"})
    public void send(MessageDto messageDto) {
        this.logger.info("send======================>");
        if (!"进入了直播间".equals(messageDto.getMessage())) {
            this.recordService.save(messageDto, "ClassRoom_RecordMapper.save");
        }
        System.out.println(messageDto);
        SocketSendUtil.send(messageDto, new JSONArray(), this.wxClassRoomUserService);
    }

    @ResponseBody
    @RequestMapping(value = {"/findLiveRecordList"}, produces = {"text/html;charset=utf-8"})
    public String findLiveRecordList() throws Exception {
        this.logger.info("findLiveRecord======================>");
        PageData pd = this.getPageData();
        String liveId = pd.get("liveId").toString();
        return this.recordService.getRedisRecord(liveId);
    }

    @ResponseBody
    @RequestMapping(value = {"/findRecord"}, produces = {"text/html;charset=utf-8"})
    public String findRecord() throws Exception {
        this.logger.info("findLiveRecord======================>");
        HashMap<String, Object> map = new HashMap<String, Object>();
        try {
            List list = this.recordService.findRecord(this.getPageData());
            map.put("code", "1");
            map.put("data", list);
        } catch (Exception e) {
            map.put("code", "1");
            map.put("message", "程序出错,请联系管理员！");
        }
        return JSONArray.toJSONString(map);
    }

    @ResponseBody
    @RequestMapping(value = {"/bindNJK"}, produces = {"text/html;charset=utf-8"})
    public String bindNJK() {
        this.logger.info("bindNJK======================>");
        PageData pd = this.getPageData();
        pd.put("USER_ID", this.get32UUID());
        pd.put("USERINFO_ID", this.get32UUID());
        return JSONArray.toJSONString(this.wxClassRoomUserService.bind("ClassRoom_UserMapper.update", pd, this.appuserService, this.appUserInfoService));
    }

    @ResponseBody
    @RequestMapping(value = {"/saveCal"}, produces = {"text/html;charset=utf-8"})
    public String saveCal() {
        this.logger.info("proposSave======================>");
        PageData pd = this.getPageData();
        return JSONArray.toJSONString(this.liveService.saveCal(pd, "ClassRoom_RecordMapper.save"));
    }

    @ResponseBody
    @RequestMapping(value = {"/payLive"}, produces = {"text/html;charset=utf-8"})
    public String payLive(HttpServletRequest request, HttpServletResponse response) {
        this.logger.info("payLive======================>");
        return JSONArray.toJSONString(WxPayUtil.wx_pay(request, response, this.get32UUID()));
    }

    @ResponseBody
    @RequestMapping(value = {"/wxPayNotify"}, produces = {"text/html;charset=utf-8"})
    public String wxPayNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        this.logger.info("wxPayNotify======================>");
        PageData pd = this.getPageData();
        pd.put("createTime", DateUtil.getTime());
        pd.put("look_pay", "2");
        return JSONArray.toJSONString(WxPayUtil.wxPayNotify(request, response, this.attendService, pd, this.wxClassRoomUserService, this.liveService));
    }

    @ResponseBody
    @RequestMapping(value = {"/saveSubscribe"}, produces = {"text/html;charset=utf-8"})
    public String saveSubscribe() {
        this.logger.info("saveSubscribe======================>");
        return JSONArray.toJSONString(this.homePageService.saveSubs("ClassRoom_SubscribeMapper.save", this.getPageData()));
    }

    @ResponseBody
    @RequestMapping(value = {"/tixian"}, produces = {"text/html;charset=utf-8"})
    public String tixian(HttpServletRequest request, HttpServletResponse response) {
        this.logger.info("saveSubscribe======================>");
        return JSONArray.toJSONString(WxPayUtil.tixian( request, response));
    }

    @ResponseBody
    @RequestMapping(value = {"/sendMode"}, produces = {"text/html;charset=utf-8"})
    public String sendMode() {
        this.logger.info("sendMode======================>");
        HashMap<String, String> map = new HashMap<String, String>();
        PageData pd = this.getPageData();
        try {
            Wx_ClassRoom.lock.lock();
            List pds = this.quratzService.getAllQuratz(pd);
            if (null != pds && pds.size() > 0) {
                for (int i = 0; i < pds.size(); ++i) {
                    String[] strs = new String[4];
                    PageData p = (PageData) pds.get(i);
                    strs[0] = p.getString("key1");
                    strs[1] = p.getString("key2");
                    strs[2] = p.getString("key4");
                    strs[3] = p.getString("key3");
                    net.sf.json.JSONObject jsonObject = ModeUtil.XiaoChengXun((String[]) strs);
                    ModeUtil.fasong3((String) p.getString("openId"), (String) p.getString("page"), (String) p.getString("form_id"), (net.sf.json.JSONObject) jsonObject);
                }
            }
            map.clear();
            map.put("code", "1");
            map.put("message", "正确执行程序！");
        } catch (Exception e) {
            e.printStackTrace();
            map.clear();
            map.put("code", "2");
            map.put("message", "程序出现错误，请联系管理员！");
        } finally {
            Wx_ClassRoom.lock.unlock();
        }
        return JSONArray.toJSONString(map);
    }


    @ResponseBody
    @RequestMapping(value = {"/testloginCach"}, produces = {"text/html;charset=utf-8"})
    public String testloginCach(User user) {
        return this.wxClassRoomUserService.loginCach(user, "ClassRoom_UserMapper.findUserByUninonId");
    }

    @ResponseBody
    @RequestMapping(value = {"/baseUp"}, produces = {"text/html;charset=utf-8"})
    public String baseUp(String imgStr) {
        HashMap<String, String> map = new HashMap<String, String>();
        String ffile = this.get32UUID() + ".jpg";
        String filePath = PathUtil.getClasspath() + "uploadFiles/uploadImgs/" + ffile;
        System.out.println(filePath);
        boolean flag = ImageAnd64Binary.generateImage((String) imgStr, (String) filePath);
        String TOUR_IMG1 = "https://m.nongjike.cn/NJK/uploadFiles/uploadImgs/" + ffile;
        if (flag) {
            map.put("code", "1");
            map.put("returnStr", TOUR_IMG1);
        } else {
            map.put("code", "2");
            map.put("message", "程序出现错误，请联系管理员！");
        }
        return JSONArray.toJSONString(map);
    }
    @ResponseBody
    @RequestMapping(value = {"/sendSocket"}, produces = {"text/html;charset=utf-8"})
    public String sendSocket(String jsonStr) {
        this.logger.info("sendSocket======================>");
        Map<String, String> maps = new HashMap<String, String>();
        try {
            JSONObject jsonObject = null;
            jsonObject = JSONObject.parseObject(jsonStr);
            new Thread(new WebSocketRunable(jsonStr, this.recordService, this.jedisClient)).start();
            Map map = Constant.liveCtxMap;
            ChannelGroup group = (ChannelGroup) map.get(jsonObject.getString("liveId"));
            BaseWebSocketServerHandler.push(group, jsonStr);
            maps.put("code", "1");
            maps.put("message", "\u7a0b\u5e8f\u6b63\u786e\u5904\u7406\uff01");
        } catch (Exception e) {
            e.printStackTrace();
            maps.put("code", "2");
            maps.put("message", "程序出错请联系管理员！");
        }
        return JSONArray.toJSONString(maps);
    }

    @ResponseBody
    @RequestMapping(value = {"/findCodeUrl"}, produces = {"text/html;charset=utf-8"})
    public String findCodeUrl(String path) {
        this.logger.info("findCodeUrl======================>");
        HashMap<String, String> maps = new HashMap<String, String>();
        PageData pd = null;
        PageData dto = null;
        try {
            pd = this.getPageData();
            if (path == null || "".equals(path)) return JSONArray.toJSONString(maps);
            if (null != (dto = this.codeService.findUrlByLiveId(pd)) && dto.size() > 0) {
                maps.put("code", "1");
                maps.put("url", dto.getString("coedUrl"));
            } else {
                maps.put("code", "1");
                pd.put("coedUrl", ModeUtil.getCodeUrl(path));
                pd.put("creatTime", DateUtil.getTime());
                this.codeService.save(pd);
                maps.put("url", ModeUtil.getCodeUrl(path));
            }
        } catch (Exception e) {
            e.printStackTrace();
            maps.put("code", "2");
            maps.put("message", "程序出错请联系管理员！\uff01");
        }
        return JSONArray.toJSONString(maps);
    }

    @ResponseBody
    @RequestMapping(value = {"/saveCritical"}, produces = {"text/html;charset=utf-8"})
    public String saveCritical(String calId, String userId, String cal_message) {
        this.logger.info("saveCritical======================>");
        HashMap<String, String> maps = new HashMap<String, String>();
        PageData pd = null;
        String p_messgae = "";
        String liveName = "";
        try {
            PageData dto;
            String puserId;
            pd = this.getPageData();
            if (!(null == calId || "".equals(calId))) {
                pd.put("cal_pcalId", calId);
                pd.put("cal_type", "1");
                dto = new PageData();
                dto.put("cal_id", calId);
                PageData dtoCallll = critiCalService.findCalById(dto);
                puserId = dtoCallll.get("cal_userId").toString();
                pd.put("cal_puserId", puserId);
                p_messgae = dtoCallll.getString("message");
                PageData dtoRec = new PageData();
                dtoRec.put("record_id", dtoCallll.get("cal_recordId").toString());
                PageData dtoRecord = recordService.findRecordById(dtoRec);
                PageData dtoLive = new PageData();
                dtoLive.put("live_id", dtoRecord.getString("record_liveId"));
                pd.put("cal_liveId", dtoRecord.getString("record_liveId"));
                PageData live = liveService.findLiveByIdWX(dtoLive);
                liveName = live.getString("live_name");
            } else {
                pd.put("cal_pcalId", "0");
                pd.put("cal_type", "0");
                dto = new PageData();
                dto.put("record_id", pd.get("cal_recordId").toString());
                PageData dtoRecord = recordService.findRecordById(dto);
                puserId = dtoRecord.get("record_userId").toString();
                pd.put("cal_puserId", puserId);
                p_messgae = dtoRecord.getString("message");
                PageData dtoLive = new PageData();
                dtoLive.put("live_id", dtoRecord.getString("record_liveId"));
                pd.put("cal_liveId", dtoRecord.getString("record_liveId"));
                PageData live = this.liveService.findLiveByIdWX(dtoLive);
                liveName = live.getString("live_name");
            }
            pd.put("createTime", DateUtil.getTime());
            pd.put("cal_userId", userId);
            pd.put("cal_good", "0");
            pd.put("cal_gap", "0");
            pd.put("cal_state", "1");
            this.critiCalService.save(pd);
            String user_Id = pd.getString("cal_puserId");
            pd.clear();
            pd.put("userId", user_Id);
            PageData user = this.wxClassRoomUserService.getUserById(pd);
            String popenId = user.getString("cr_openId");
            PageData dtoform = new PageData();
            dtoform.put("openId", popenId);
            List formIdList = this.boomService.findBoomByOpenId(dtoform);
            if (null != formIdList && formIdList.size() > 0) {
                pd.put("userId", userId);
                PageData users = this.wxClassRoomUserService.getUserById(pd);
                PageData voForm = (PageData) formIdList.get(0);
                String formIds = voForm.getString("formId");
                String openIds = voForm.getString("openId");
                String[] strs = new String[]{users.getString("cr_userName"), cal_message, DateUtil.getTime(), p_messgae, "\u6765\u6e90\uff1a" + liveName};
                net.sf.json.JSONObject jsonObject = ModeUtil.XiaoChengXun((String[]) strs);
                ModeUtil.wxMessageModeSend( "BjMacTlr4CBkKl_n2a4jc0qU4G5uIgHQPieBvYd3c8w",  openIds,  "pages/project/my/message/message",  formIds,  jsonObject);
                this.boomService.modifyState(voForm);
            }
            maps.put("code", "1");
            maps.put("message", "\u6b63\u786e\u8fd4\u56de\u6570\u636e\uff01");
        } catch (Exception e) {
            e.printStackTrace();
            maps.put("code", "2");
            maps.put("message", "程序出错请联系管理员！\uff01");
        }
        return JSONArray.toJSONString(maps);
    }

    @ResponseBody
    @RequestMapping(value = {"/saveBoom"}, produces = {"text/html;charset=utf-8"})
    public String saveBoom(String openId, String formId) {
        this.logger.info("saveBoom======================>");
        HashMap<String, String> maps = new HashMap<String, String>();
        try {
            PageData dtoBoom = new PageData();
            String form = formId.trim();
            String eqForm = "theformIdisamockone";
            if (eqForm.trim().equals(form)) {
                return "";
            }
            dtoBoom.put("openId", openId);
            dtoBoom.put("formId", formId);
            dtoBoom.put("createTime", DateUtil.getTime());
            dtoBoom.put("state", "1");
            this.boomService.save(dtoBoom);
            maps.put("code", "1");
        } catch (Exception e) {
            e.printStackTrace();
            maps.put("code", "2");
            maps.put("message", "程序出错请联系管理员！\uff01");
        }
        return JSONArray.toJSONString(maps);
    }

    @ResponseBody
    @RequestMapping(value = {"/findCriListByUser"}, produces = {"text/html;charset=utf-8"})
    public String findCriListByUser(String pageNum) {
        this.logger.info("findCriListByUser======================>");
        HashMap<String, Object> map = new HashMap<String, Object>();
        PageData pd = null;
        List<PageData> calList = null;
        Page page = null;
        try {
            page = this.getPage();
            pd = this.getPageData();
            page.setPd(pd);
            page.setShowCount(10);
            page.setCurrentPage(Integer.parseInt(pageNum));
            PageData redto = null;
            PageData caldto = null;
            PageData lidto = null;
            critiCalService.updateAll(pd);
            calList = critiCalService.getAllCalListPage(page);
            if (page.getCurrentPage() != Integer.parseInt(pageNum)) {
                calList = new ArrayList();
            }
            if (calList != null && calList.size() > 0) {
                for (int i = 0; i < calList.size(); ++i) {
                    redto = new PageData();
                    redto.put("record_id",  calList.get(i).get("cal_recordId").toString());
                    PageData record = this.recordService.findRecordById(redto);
                    String cal_type =  calList.get(i).get("cal_type").toString();
                    if ("0".equals(cal_type)) {
                       calList.get(i).put("pCal", record);
                    } else if ("1".equals(cal_type)) {
                        caldto = new PageData();
                        caldto.put("cal_id", calList.get(i).get("cal_pcalId").toString());
                        ( calList.get(i)).put("pCal", this.critiCalService.findCalById(caldto));
                    }
                    lidto = new PageData();
                    lidto.put("live_id", record.getString("record_liveId"));
                    PageData live = this.liveService.findLiveByIdWX(lidto);
                    calList.get(i).put("live", live);
                }
            }
            map.put("code", "1");
            map.put("data", calList);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", "2");
            map.put("message", "程序出错请联系管理员！\uff01");
        }
        return this.JsonMapper(map);
    }

    @ResponseBody
    @RequestMapping(value = {"/hotTop"}, produces = {"text/html;charset=utf-8"})
    public String hotTop(String showCount) {
        this.logger.info("hotTop======================>");
        PageData pd = new PageData();
        HashedMap map = new HashedMap();
        Page page = new Page();
        try {
            pd = this.getPageData();
            page.setPd(pd);
            page.setShowCount(Integer.parseInt(showCount));
            page.setCurrentPage(1);
            map.put("code", "1");
            map.put("data", this.hotKeyService.list(page));
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", "2");
            map.put("message", "程序出错请联系管理员！\uff01");
        }
        return this.JsonMapper(map);
    }


    @ResponseBody
    @RequestMapping(value = {"/saveOpinion"}, produces = {"text/html;charset=utf-8"})
    public String saveOpinion() {
        this.logger.info("saveOpinion======================>");
        PageData pd = null;
        HashedMap map = new HashedMap();
        try {
            pd = this.getPageData();
            pd.put("createTime", DateUtil.getTime());
            pd.put("audit", "0");
            this.opinionService.save(pd);
            map.put("code", "1");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", "2");
            map.put("message", "程序出错请联系管理员！\uff01");
        }
        return this.JsonMapper(map);
    }

    @ResponseBody
    @RequestMapping(value = {"/saveRecordMessage"}, produces = {"text/html;charset=utf-8"})
    public void saveRecordMessage(String message) {
        new WebSocketRunable(message, recordService,  null).run();
    }

    @ResponseBody
    @RequestMapping(value = {"/saveStatistics"}, produces = {"text/html;charset=utf-8"})
    public String saveStatistics() {
        this.logger.info("saveStatistics======================>");
        PageData pd = null;
        PageData voPd = null;
        HashedMap map = new HashedMap();
        try {
            pd = this.getPageData();
            voPd = this.statisticsService.findDataByOpenId(pd);
            pd.put("updateTime", DateUtil.getTime());
            synchronized (Wx_ClassRoom.class) {
                if (voPd != null && voPd.size() > 0) {
                    this.statisticsService.update(pd);
                } else {
                    pd.put("createTime", DateUtil.getTime());
                    this.statisticsService.save(pd);
                }
                map.put("code", "1");
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", "2");
            map.put("message", "程序出错请联系管理员！");
        }
        return this.JsonMapper(map);
    }
    @ResponseBody
    @RequestMapping(value = {"/findDataByOpenId"}, produces = {"text/html;charset=utf-8"})
    public String findDataByOpenId() {
        this.logger.info("findDataByOpenId======================>");
        PageData pd = null;
        PageData voPd = null;
        HashedMap map = new HashedMap();
        try {
            pd = this.getPageData();
            voPd = this.statisticsService.findDataByOpenId(pd);
            if (voPd != null && voPd.size() > 0) {
                map.put("code", "1");
                map.put("data", voPd);
            } else {
                map.put("code", "3");
                map.put("message", "用户不存在！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", "2");
            map.put("message", "程序出错请联系管理员！");
        }
        return this.JsonMapper(map);
    }

    private int getPageNum(PageData pd) {
        int num = 0;
        try {
            num = Integer.parseInt(pd.get("pageNum").toString());
        } catch (NullPointerException e) {
            num = 1;
        }
        return num;
    }

    private int getShowPageNum(PageData pd) {
        int num = 0;
        try {
            num = Integer.parseInt(pd.get("showCount").toString());
        } catch (NullPointerException e) {
            num = 5;
        }
        return num;
    }

    private Page getThisPage(Page page, PageData pd) {
        page.setCurrentPage(this.getPageNum(pd));
        page.setShowCount(this.getShowPageNum(pd));
        page.setPd(pd);
        return page;
    }
}
