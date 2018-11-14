package com.fh.util.quratz;

import com.fh.service.wx_classRoom.LiveService;
import com.fh.service.wx_classRoom.QuratzService;
import com.fh.util.ModeUtil;
import com.fh.util.PageData;
import com.fh.util.SqlCentre;
import net.sf.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class QuratzRun extends Thread {
    private String liveId;
    private QuratzService quratzService;
    private LiveService liveService;
    private String beginTime;
    private String beforTime;
    private Integer num;


    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static ReentrantLock lock = new ReentrantLock();

    public void run() {
        System.out.println("=======================任务添加成功！");
        try {
            String indexTime = getTime(this.beginTime, this.beforTime);
            long bef = QuratzRun.getFiveTime(this.num,this.beginTime);
            System.out.println("结束时间是======================》"+sdf.format(new Date(bef)));
            System.out.println("我开始执行啦============================》");
            while (true) {
                Thread.sleep(60 * 1000);
                System.out.println("我睡醒啦============================》");
                if (sdf.parse(indexTime).getTime() < System.currentTimeMillis()) {
                    break;
                }
            }
            PageData pd = new PageData();
            pd.put("liveId", this.liveId);
            List<PageData> pds = this.quratzService.getAllQuratz(pd);
            if (null != pds && pds.size() > 0) {
                for (int i = 0; i < pds.size(); i++) {
                    PageData p = pds.get(i);
                    QuratzRun.doSend(p);
                }
            }
            System.out.println("消息模板执行完毕啦============================》");

            while (true){
                Thread.sleep(60 * 1000);
                System.out.println("自动关闭直播间睡醒啦============================》");
                if (bef < System.currentTimeMillis()) {
                    break;
                }
            }
            PageData p = new PageData();
            p.put("live_id",this.liveId);
            p.put("comeGo","0");
            this.liveService.updateLive(p, SqlCentre.LIVE_UPDATE);
            System.out.println("我是quratz  我完美执行完毕===========================》");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getLiveId() {
        return liveId;
    }

    public void setLiveId(String liveId) {
        this.liveId = liveId;
    }

    public QuratzService getQuratzService() {
        return quratzService;
    }

    public void setQuratzService(QuratzService quratzService) {
        this.quratzService = quratzService;
    }

    public LiveService getLiveService() {
        return liveService;
    }

    public void setLiveService(LiveService liveService) {
        this.liveService = liveService;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getBeforTime() {
        return beforTime;
    }

    public void setBeforTime(String beforTime) {
        this.beforTime = beforTime;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public QuratzRun(QuratzService quratzService, String liveId, String beginTime, String beforTime,LiveService liveService,Integer num) {
        this.liveId = liveId;
        this.quratzService = quratzService;
        this.beginTime = beginTime;
        this.beforTime = beforTime;
        this.liveService = liveService;
        this.num = num;
    }

    /**
     *
     * @param time
     * @param befor
     * @return
     */
    public String getTime(String time, String befor) {
        String result = "";
        String year = "";
        String month = "";
        String day = "";
        String hour = "";
        String minute = "";
        Integer resultMinute = 0;
        String[] times = time.split(" ");
        String[] days = times[0].split("-");
        year = days[0];
        month = days[1];
        day = days[2];
        String[] tim = times[1].split(":");
        hour = tim[0];
        minute = tim[1];
        int be = Integer.parseInt(befor);
        resultMinute = Integer.parseInt(minute) - be;
        if (resultMinute < 0) {
            resultMinute = 60 + resultMinute;
            hour = Integer.parseInt(hour) - 1 + "";
        }
        result = year + "-" + month + "-" + day + " " + hour + ":" + resultMinute + ":00";
        System.out.println("我是返回时间================>" + result);
        return result;
    }

    /**
     * @param num 关闭的小时
     * @param beginTime 直播开始时间
     * @return 结束时间的long类型
     */
    public static long getFiveTime(Integer num, String beginTime) {
        if(num == null){
            num=5;
        }
        long time = 1000 * 60 * 60 * num;//获得延时小时数
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long timess=0;
        try {
            lock.lock();
            Date t = null;
            t = sdf.parse(beginTime);
           timess = t.getTime()+time;
        } catch (ParseException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
        return timess;
    }

    /**
     * 发送消息模板
     *
     * @param p 发送数据
     */
    public static void doSend(PageData p) {
        try {
            lock.lock();
            String[] strs = {p.getString("key1"), p.getString("key2"), p.getString("key4"), p.getString("key3"),};
            JSONObject jsonObject = ModeUtil.XiaoChengXun(strs);
            ModeUtil.fasong3(p.getString("openId"), p.getString("page"), p.getString("form_id"), jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    public static void main(String[] args) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       long l =QuratzRun.getFiveTime(8,"2018-09-05 10:30:00");
        System.out.println(l<System.currentTimeMillis());
        System.out.println(sdf.format(new Date(l)));
    }
}
