package com.fh.entity.vo;

public class MessageVo {

    private String userName;
    private String message;
    private String img;
    private Integer audio_index;
    private Integer size;
    private String createTime;
    private Boolean teacher=false;

    public Boolean getTeacher() {
        return teacher;
    }

    public void setTeacher(Boolean teacher) {
        this.teacher = teacher;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Integer getAudio_index() {
        return audio_index;
    }

    public void setAudio_index(Integer audio_index) {
        this.audio_index = audio_index;
    }
}
