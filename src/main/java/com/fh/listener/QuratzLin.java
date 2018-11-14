// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2018/11/13 13:59:30
// Home Page:  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   QuratzLin.java
package com.fh.listener;

import com.fh.service.gameService.GameUserService;
import com.fh.service.wx_classRoom.BoomService;
import com.fh.service.wx_classRoom.LiveService;
import com.fh.service.wx_classRoom.QuratzService;
import com.fh.util.GameUserUtil;
import com.fh.util.PageData;
import com.fh.util.quratz.BoomQuratz;
import com.fh.util.quratz.QuratzRun;
import java.io.PrintStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class QuratzLin
implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private GameUserService gameUserService;
    @Autowired
    private LiveService liveService;
    @Autowired
    private QuratzService quratzService;
    @Autowired
    private BoomService boomService;

    public void onApplicationEvent(ContextRefreshedEvent event) {
        block8 : {
            System.out.println("============================\u6bcf\u65e5\u91cd\u7f6equratz\u542f\u52a8");
            PageData pd = null;
            try {
                pd = this.liveService.quratzSelectLive();
                if (pd != null) {
                    String id = pd.get("live_id").toString();
                    String beginTime = pd.getString("beginTime");
                    new QuratzRun(this.quratzService, id, beginTime, "5", this.liveService, 5).start();
                    break block8;
                }
                System.out.println("===========================\u6ca1\u6709\u6b63\u5728\u76f4\u64ad\u7684\u65f6\u5019\uff01");
            }
            catch (NullPointerException n) {
                n.printStackTrace();
                System.out.println("===========================\u6ca1\u6709\u6b63\u5728\u76f4\u64ad\u7684\u65f6\u5019\uff01");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                new Thread(new GameUserUtil(this.gameUserService)).start();
                new Thread(new BoomQuratz(this.boomService, "6")).start();
            }
        }
    }
}
