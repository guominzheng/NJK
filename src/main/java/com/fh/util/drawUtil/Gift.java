package com.fh.util.drawUtil;

public class Gift {
    private int index;
    private String gitfId;
    private String giftName;
    private String guftImg;
    private double probability;
    private Integer type;// 1是事物
    private String couponImg;
    public Gift(int index, String gitfId, String giftName, double probability, String guftImg,Integer type,String couponImg) {
        this.index = index;
        this.gitfId = gitfId;
        this.giftName = giftName;
        this.guftImg = guftImg;
        this.probability = probability;
        this.type = type;
        this.couponImg = couponImg;
    }
    public Gift(){

    }

    public String getCouponImg() {
        return couponImg;
    }

    public void setCouponImg(String couponImg) {
        this.couponImg = couponImg;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getGuftImg() {
        return guftImg;
    }

    public void setGuftImg(String guftImg) {
        this.guftImg = guftImg;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getGitfId() {
        return gitfId;
    }

    public void setGitfId(String gitfId) {
        this.gitfId = gitfId;
    }

    public String getGiftName() {
        return giftName;
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }
}
