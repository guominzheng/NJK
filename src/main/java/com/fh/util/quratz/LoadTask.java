package com.fh.util.quratz;

import org.apache.log4j.Logger;

public class LoadTask {
    private static final Logger log = Logger.getLogger("");
    /**
     * @param sendTime 发送时间
     */
    public static boolean timerTask(String sendTime,long msgId) {
        String cron = QuartzManager.getQuartzTime(sendTime);//获得quartz时间表达式，此方法自己写
        ScheduleJob job = new ScheduleJob();
        String jobName = msgId+"_job";
        job.setJobId(msgId);
        job.setJobName(jobName);
        job.setCreTime(System.currentTimeMillis());
        job.setJobCron(cron);
        job.setJobTime(sendTime);
        job.setJobGroup("MY_JOBGROUP_NAME");
        job.setJobDesc("desc");
        try {
            //删除已有的定时任务
          //  QuartzManager.removeJob(jobName);
            //添加定时任务
            QuartzManager.addJob(jobName, QuartzJobFactory.class, cron,job);
            return true;
        } catch (Exception e) {
            log.info("加载定时器错误："+e);
            return false;
        }
    }


    public static void main(String[] args) {
        LoadTask.timerTask("2018-08-30 18:25:30",78915);
        System.out.println("======哟吼");
    }
}

