// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2018/11/13 13:58:59
// Home Page:  http://www.neshkov.com/dj.html - Check often for new version!
// Source File Name:   LiveRecord.java
package com.fh.entity.vo;

import com.fh.util.PageData;
import java.util.List;

public class LiveRecord {
    private String record_id;
    private String userName;
    private String img;
    private String userId;
    private String message;
    private Integer audio_text;
    private Integer size;
    private String createTime;
    private Boolean teacher;
    private Boolean doom;
    private List<PageData> calList;


    public Boolean getDoom() {
        return doom;
    }

    public void setDoom(Boolean doom) {
        this.doom = doom;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRecord_id() {
        return this.record_id;
    }

    public void setRecord_id(String record_id) {
        this.record_id = record_id;
    }

    public List<PageData> getCalList() {
        return this.calList;
    }

    public void setCalList(List<PageData> calList) {
        this.calList = calList;
    }

    public Integer getAudio_text() {
        return this.audio_text;
    }

    public void setAudio_text(Integer audio_text) {
        this.audio_text = audio_text;
    }

    public Boolean getTeacher() {
        return this.teacher;
    }

    public void setTeacher(Boolean teacher) {
        this.teacher = teacher;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getImg() {
        return this.img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getSize() {
        return this.size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
