package com.fh.entity.vo;
import com.fh.util.PageData;

import java.util.List;

public class HomePage {
    private List<PageData> lives;
    private List<PageData> types;
    private List<PageData> banners;
    private PageData  live;
    private PageData  zixun;

    public PageData getZixun() {
        return zixun;
    }

    public void setZixun(PageData zixun) {
        this.zixun = zixun;
    }

    public PageData getLive() {
        return live;
    }

    public void setLive(PageData live) {
        this.live = live;
    }

    public List<PageData> getLives() {
        return lives;
    }

    public void setLives(List<PageData> lives) {
        this.lives = lives;
    }

    public List<PageData> getTypes() {
        return types;
    }

    public void setTypes(List<PageData> types) {
        this.types = types;
    }

    public List<PageData> getBanners() {
        return banners;
    }

    public void setBanners(List<PageData> banners) {
        this.banners = banners;
    }
}
