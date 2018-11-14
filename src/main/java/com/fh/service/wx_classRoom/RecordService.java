// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2018/11/13 13:41:45
// Home Page:  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   RecordService.java
package com.fh.service.wx_classRoom;

import com.alibaba.fastjson.JSONArray;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.dto.MessageDto;
import com.fh.entity.vo.LiveRecord;
import com.fh.util.DateUtil;
import com.fh.util.PageData;
import com.fh.util.ServiceUtil;
import com.fh.util.jdeis.JedisClient;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecordService {
    @Resource(name="daoSupport")
    private DaoSupport dao;
    @Autowired
    private JedisClient jedisClient;

    public Map save(MessageDto messageDto, String sql) {
        Map map = new HashMap<String, String>();
        PageData user = null;
        PageData pd = new PageData();
        pd.put("openid", messageDto.getOpenid());
        try {
            user = (PageData)this.dao.findForObject("ClassRoom_UserMapper.findUserByCondition", pd);
            pd.put("record_message", messageDto.getMessage());
            pd.put("record_userId", user.get("cr_userid").toString());
            pd.put("createTime", DateUtil.getTime());
            pd.put("updateTime", DateUtil.getTime());
            pd.put("record_liveId", messageDto.getLiveId());
            if (messageDto.getSize() != null) {
                pd.put("record_size", messageDto.getSize());
            } else {
                pd.put("record_size", "0");
            }
            pd.put("audio_text", messageDto.getAudio_index());
            if (messageDto.getTeacher().booleanValue()) {
                pd.put("teacher", messageDto.getTeacher().toString());
            } else {
                pd.put("teacher", "false");
            }
            if (messageDto.getDoom().booleanValue()) {
                pd.put("doom", messageDto.getDoom().toString());
            } else {
                pd.put("doom", "false");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            map.put("code", "2");
            map.put("message", "程序出错请联系管理员！");
            return map;
        }
        map = ServiceUtil.serviceUpdate(sql, pd, dao, "insert");
        if (!messageDto.getTeacher().booleanValue() || !"1".equals(map.get("code"))) return map;
        try {
            ObjectMapper mapper = new ObjectMapper();
            pd.clear();
            pd.put("liveId", messageDto.getLiveId());
            List<LiveRecord> recordList = this.findLiveRecordList(pd, "ClassRoom_RecordMapper.findLiveRecordList");
            this.jedisClient.hset("CONTENTCATEGORYID", messageDto.getLiveId(), mapper.writeValueAsString(recordList));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    public void saveRe(PageData pd) throws Exception {
        this.dao.save("ClassRoom_RecordMapper.save", pd);
    }

    public List<LiveRecord> findLiveRecordList(PageData pd, String sql) throws Exception {
        return (List<LiveRecord>)this.dao.findForList(sql, pd);
    }

    public String getRedisRecord(String liveId) throws Exception {
        List<LiveRecord> reList = null;
        PageData pd = new PageData();
        pd.put("liveId", liveId);
        reList = this.findLiveRecordList(pd, "ClassRoom_RecordMapper.findLiveRecordList");
        return JSONArray.toJSONString(reList);
    }

    public List<LiveRecord> findLiveList(Page page, String sql) throws Exception {
        return (List<LiveRecord>)this.dao.findForList(sql, page);
    }

    public List<LiveRecord> findRecord(PageData pd) throws Exception {
        return (List<LiveRecord>)this.dao.findForList("ClassRoom_RecordMapper.findTeacherRecordList", pd);
    }

    public PageData findRecordById(PageData pd) throws Exception {
        return (PageData)this.dao.findForObject("ClassRoom_RecordMapper.findRecordById", pd);
    }
}
