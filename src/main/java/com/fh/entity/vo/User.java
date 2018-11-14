package com.fh.entity.vo;

import com.fh.util.PageData;

public class User {

    private Integer cr_userid;
    private Double cr_userBalance;
    private String cr_userName;
    private String cr_userImg;
    private Integer cr_teacherStatus;
    private String cr_userPhone;
    private String cr_openId;
    private String cr_unionid;
    private String cr_session_key;
    private PageData pageData;

    public String getCr_session_key() {
        return cr_session_key;
    }

    public void setCr_session_key(String cr_session_key) {
        this.cr_session_key = cr_session_key;
    }

    public Integer getCr_userid() {
        return cr_userid;
    }

    public void setCr_userid(Integer cr_userid) {
        this.cr_userid = cr_userid;
    }

    public Double getCr_userBalance() {
        return cr_userBalance;
    }

    public void setCr_userBalance(Double cr_userBalance) {
        this.cr_userBalance = cr_userBalance;
    }

    public String getCr_userName() {
        return cr_userName;
    }

    public void setCr_userName(String cr_userName) {
        this.cr_userName = cr_userName;
    }

    public String getCr_userImg() {
        return cr_userImg;
    }

    public void setCr_userImg(String cr_userImg) {
        this.cr_userImg = cr_userImg;
    }

    public Integer getCr_teacherStatus() {
        return cr_teacherStatus;
    }

    public void setCr_teacherStatus(Integer cr_teacherStatus) {
        this.cr_teacherStatus = cr_teacherStatus;
    }

    public String getCr_userPhone() {
        return cr_userPhone;
    }

    public void setCr_userPhone(String cr_userPhone) {
        this.cr_userPhone = cr_userPhone;
    }

    public String getCr_openId() {
        return cr_openId;
    }

    public void setCr_openId(String cr_openId) {
        this.cr_openId = cr_openId;
    }

    public String getCr_unionid() {
        return cr_unionid;
    }

    public void setCr_unionid(String cr_unionid) {
        this.cr_unionid = cr_unionid;
    }

    public PageData getPageData() {
        return pageData;
    }

    public void setPageData(PageData pageData) {
        this.pageData = pageData;
    }
}
