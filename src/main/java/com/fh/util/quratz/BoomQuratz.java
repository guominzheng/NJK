// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2018/11/13 13:50:21
// Home Page:  http://www.neshkov.com/dj.html - Check often for new version!
// Source File Name:   BoomQuratz.java
package com.fh.util.quratz;

import com.fh.service.wx_classRoom.BoomService;
import com.fh.util.PageData;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BoomQuratz
implements Runnable {
    private BoomService boomService;
    private String times;
    private SimpleDateFormat sdf = new SimpleDateFormat("HH");

    public BoomService getBoomService() {
        return this.boomService;
    }

    public void setBoomService(BoomService boomService) {
        this.boomService = boomService;
    }

    public String getTimes() {
        return this.times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public BoomQuratz(BoomService boomService, String times) {
        this.boomService = boomService;
        this.times = times;
    }

    public void run() {
        PageData pd = new PageData();
        System.out.println("======================\u542f\u52a8\u6bcf\u65e512\u70b9\u6e05\u9664formId\u8ba1\u5212\uff01");
        pd.put("times", this.getTimes());
        String nowTime = "";
        try {
            do {
                Thread.sleep(30000);
                if (!(nowTime = this.sdf.format(new Date())).equals("00")) continue;
                this.boomService.deleteQuratz(pd);
            } while (true);
        }
        catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
}
