package com.fh.util.Websocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fh.entity.dto.MessageDto;
import com.fh.entity.vo.LiveRecord;
import com.fh.service.wx_classRoom.RecordService;
import com.fh.util.PageData;
import com.fh.util.SqlCentre;
import com.fh.util.jdeis.JedisClient;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class WebSocketRunable implements Runnable {
    private String message;
    private RecordService recordService;
    private JedisClient jedisClient;

    public JedisClient getJedisClient() {
        return jedisClient;
    }

    public void setJedisClient(JedisClient jedisClient) {
        this.jedisClient = jedisClient;
    }

    public RecordService getRecordService() {
        return recordService;
    }

    public void setRecordService(RecordService recordService) {
        this.recordService = recordService;
    }

    private static final ReentrantLock lock = new ReentrantLock();

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public WebSocketRunable(String message, RecordService recordService, JedisClient jedisClient) {
        this.message = message;
        this.recordService = recordService;
        this.jedisClient = jedisClient;
    }

    public void run() {
        try {
            lock.lock();
            JSONObject json = JSON.parseObject(this.message);
            String liveId = json.get("liveId").toString();
            String teacher = null;
            if (json.get("teacher") == null) {
                teacher = "false";
            } else {
                teacher = json.get("teacher").toString();
            }
            String doom = null;
            if (json.get("doom") == null) {
                doom = "false";
            } else {
                doom = json.get("doom").toString();
            }
            String messages = json.get("message").toString();
            String openid = json.get("openid").toString();
            String audio_index = json.get("audio_index").toString();
            String size = "";
            if (json.get("size") == null) {
                size = "0";
            } else {
                size = json.get("size").toString();
            }
            MessageDto messageDto = new MessageDto();
            messageDto.setTeacher(Boolean.valueOf(teacher));
            if(messages.length()<10&&!messageDto.getTeacher()){
                return ;
            }
           /* if (messageDto.getTeacher()) {
                List<LiveRecord> reList = null;
                PageData pd = new PageData();
                pd.put("liveId", liveId);
              *//*  reList = recordService.findLiveRecordList(pd, SqlCentre.RECORD_FIND_LIST);
                if (null != reList && reList.size() > 0) {
                    jedisClient.hset("CONTENTCATEGORYID", liveId, JSONArray.toJSONString(reList));
                    jedisClient.expire(liveId, 60 * 60 * 5);
                }*//*
            }*/
            messageDto.setLiveId(liveId);
            messageDto.setAudio_index(Integer.parseInt(audio_index));
            messageDto.setMessage(messages);
            messageDto.setOpenid(openid);
            messageDto.setDoom(Boolean.valueOf(doom));
            messageDto.setSize(Integer.parseInt(size));
            if(!(messages.indexOf("进入了直播间")>0)){
           // if (!"进入了直播间".equals(messages)) {
                this.recordService.save(messageDto, SqlCentre.RECORD_SAVE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("=============================》异步执行添加");
        } finally {
            lock.unlock();
        }

    }
}
