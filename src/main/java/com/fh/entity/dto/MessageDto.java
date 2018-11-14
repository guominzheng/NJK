package com.fh.entity.dto;

public class MessageDto {

    private String liveId;
    private Boolean teacher=false;
    private Boolean doom =false;
    private String message;
    private String openid;
    private Integer audio_index;
    private Integer size;

    public Boolean getDoom() {
        return doom;
    }

    public void setDoom(Boolean doom) {
        this.doom = doom;
    }

    public Boolean getTeacher() {
        return teacher;
    }

    public void setTeacher(Boolean teacher) {
        this.teacher = teacher;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getOpenid() {
        return openid;
    }
    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getLiveId() {
        return liveId;
    }

    public void setLiveId(String liveId) {
        this.liveId = liveId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getAudio_index() {
        return audio_index;
    }

    public void setAudio_index(Integer audio_index) {
        this.audio_index = audio_index;
    }
}
