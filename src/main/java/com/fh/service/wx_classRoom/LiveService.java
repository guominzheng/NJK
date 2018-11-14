// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2018/11/13 13:44:03
// Home Page:  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   LiveService.java
package com.fh.service.wx_classRoom;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.vo.LiveRecord;
import com.fh.util.DateUtil;
import com.fh.util.PageData;
import com.fh.util.ServiceUtil;
import com.fh.util.jdeis.JedisClient;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LiveService {
    @Resource(name="daoSupport")
    private DaoSupport dao;
    @Autowired
    private JedisClient jedisClient;
    private static final String LIVEINFOONE = "LIVEINFOONE";

    public List<PageData> findLiveList(Page page, String sql) throws Exception {
        return (List<PageData>)this.dao.findForList(sql, page);
    }

    public List<PageData> seache(PageData pd) throws Exception {
        return (List<PageData>)this.dao.findForList("ClassRoom_LiveMapper.seache", pd);
    }

    public Map one(Page page, String sql, String type) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        PageData pds = null;
        PageData live = null;
        Integer view = null;
        PageData liveDto = null;
        Object pdList = null;
        try {
            liveDto = new PageData();
            live = (PageData)this.dao.findForObject("ClassRoom_LiveMapper.findLiveById", page);
            if (live != null) {
                view = Integer.parseInt(live.get("live_view").toString());
                view = view + 1;
                liveDto.put("live_view", view);
                liveDto.put("live_id", live.get("live_id").toString());
                this.dao.update("ClassRoom_LiveMapper.uptateImg", liveDto);
            }
            pds = (PageData)this.dao.findForObject("ClassRoom_LiveMapper.findLiveById", page);
            map.put("code", "1");
            map.put("message", "\u6b63\u786e\u8fd4\u56de\u6570\u636e\uff01");
            map.put("live", pds);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.err.println("LIVE\u67e5\u8be2\u51fa\u73b0\u9519\u8bef===================\u300b");
            map.put("code", "2");
            map.put("message", "\u7a0b\u5e8f\u51fa\u9519\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458\uff01");
        }
        return map;
    }

    public List<LiveRecord> getRecordList(Page page) throws Exception {
        return (List<LiveRecord>)this.dao.findForList("ClassRoom_RecordMapper.findLiveRecordListByLivelistPage", page);
    }

    public Map findCalList(Page page, String sql) {
        return ServiceUtil.serviceQuery((String)sql, (Page)page, (DaoSupport)this.dao, (String)"list", (PageData)null);
    }

    public Map updateLive(PageData pd, String sql) {
        return ServiceUtil.serviceUpdate((String)sql, (PageData)pd, (DaoSupport)this.dao, (String)"update");
    }

    public Map saveLive(PageData pd, String sql) {
        return ServiceUtil.serviceUpdate((String)sql, (PageData)pd, (DaoSupport)this.dao, (String)"insert");
    }

    public Map deleteLive(PageData pd, String sql) {
        return ServiceUtil.serviceUpdate((String)sql, (PageData)pd, (DaoSupport)this.dao, (String)"delete");
    }

    public PageData findComeGolive(PageData pd) throws Exception {
        return (PageData)this.dao.findForObject("ClassRoom_LiveMapper.findComeGolive", pd);
    }

    public Map saveCal(PageData pd, String sql) {
        pd.put("createTime", DateUtil.getTime());
        pd.put("record_message", pd.getString("message"));
        pd.put("record_userId", pd.getString("userId"));
        pd.put("updateTime", DateUtil.getTime());
        pd.put("audio_text", "2");
        pd.put("record_liveId", pd.getString("liveId"));
        pd.put("record_size", "0");
        pd.put("teacher", "false");
        return ServiceUtil.serviceUpdate((String)sql, (PageData)pd, (DaoSupport)this.dao, (String)"insert");
    }

    public Map findLiveOne(String sql, PageData pd) {
        return ServiceUtil.serviceQuery((String)sql, (Page)null, (DaoSupport)this.dao, (String)"object", (PageData)pd);
    }

    public PageData quratzSelectLive() throws Exception {
        return (PageData)this.dao.findForObject("ClassRoom_LiveMapper.quratzSelectLive", null);
    }

    public PageData findLiveByIdWX(PageData pd) throws Exception {
        return (PageData)this.dao.findForObject("ClassRoom_LiveMapper.findLiveByIdWX", pd);
    }

    public List<PageData> findAllLive() throws Exception {
        return (List<PageData>)this.dao.findForList("ClassRoom_LiveMapper.findAllLive", null);
    }
}
