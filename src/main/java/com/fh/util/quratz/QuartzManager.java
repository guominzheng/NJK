package com.fh.util.quratz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.JobBuilder.newJob;

public class QuartzManager {

    private static SchedulerFactory gSchedulerFactory = new StdSchedulerFactory();
    private static String JOB_GROUP_NAME = "MY_JOBGROUP_NAME";
    private static String TRIGGER_GROUP_NAME = "MY_TRIGGERGROUP_NAME";

    /**
     * @param jobName 任务名
     * @param cls     任务
     * @param time    时间设置，参考quartz说明文档
     * @Description: 添加一个定时任务，使用默认的任务组名，触发器名，触发器组名
     */
    @SuppressWarnings("unchecked")
    public static void addJob(String jobName, Class cls, String time, Object scheduleJob) {
        try {
            Scheduler sched = gSchedulerFactory.getScheduler();
            JobDetail job = newJob(cls)
                    .withIdentity(jobName, JOB_GROUP_NAME)
                    .build();
            // 添加具体任务方法
            job.getJobDataMap().put("scheduleJob", scheduleJob);
            // 表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(time);
            // 按新的cronExpression表达式构建一个新的trigger
            Trigger trigger = TriggerBuilder
                    .newTrigger()
                    .withIdentity(jobName, TRIGGER_GROUP_NAME)
                    .withSchedule(scheduleBuilder).build();

            //交给scheduler去调度
            sched.scheduleJob(job, trigger);

            // 启动
            if (!sched.isShutdown()) {
                sched.start();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param jobName          任务名
     * @param jobGroupName     任务组名
     * @param triggerName      触发器名
     * @param triggerGroupName 触发器组名
     * @param jobClass         任务
     * @param time             时间设置，参考quartz说明文档
     * @Description: 添加一个定时任务
     */
    @SuppressWarnings("unchecked")
    public static void addJob(String jobName, String jobGroupName,
                              String triggerName, String triggerGroupName, Class jobClass,
                              String time) {
        try {
            Scheduler sched = gSchedulerFactory.getScheduler();
            JobDetail job = newJob(jobClass)
                    .withIdentity(jobName, jobGroupName)
                    .build();
            // 表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(time);
            // 按新的cronExpression表达式构建一个新的trigger
            Trigger trigger = TriggerBuilder
                    .newTrigger()
                    .withIdentity(triggerName, triggerGroupName)
                    .withSchedule(scheduleBuilder).build();
            sched.scheduleJob(job, trigger);
            // 启动
            if (!sched.isShutdown()) {
                sched.start();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param jobName
     * @param time    qgw 2016年1月21日 下午3:28:34 ^_^
     * @Description: 修改一个任务的触发时间(使用默认的任务组名 ， 触发器名 ， 触发器组名)
     */
    @SuppressWarnings("unchecked")
    public static void modifyJobTime(String jobName, String time) {
        TriggerKey triggerKey = TriggerKey.triggerKey(
                jobName, TRIGGER_GROUP_NAME);

        try {
            Scheduler sched = gSchedulerFactory.getScheduler();
            CronTrigger trigger = (CronTrigger) sched.getTrigger(triggerKey);
            if (trigger == null) {
                return;
            }
            String oldTime = trigger.getCronExpression();
            if (!oldTime.equalsIgnoreCase(time)) {
                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(time);
                //按新的cronExpression表达式重新构建trigger
                trigger = trigger.getTriggerBuilder().withIdentity(triggerKey)
                        .withSchedule(scheduleBuilder).build();
                //按新的trigger重新设置job执行
                sched.rescheduleJob(triggerKey, trigger);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param triggerName
     * @param triggerGroupName
     * @param time
     * @Description: 修改一个任务的触发时间
     * @author qgw
     * @date 2016年1月27日 下午4:45:15 ^_^
     */
    public static void modifyJobTime(String triggerName,
                                     String triggerGroupName, String time) {
        TriggerKey triggerKey = TriggerKey.triggerKey(
                triggerName, triggerGroupName);
        try {
            Scheduler sched = gSchedulerFactory.getScheduler();
            CronTrigger trigger = (CronTrigger) sched.getTrigger(triggerKey);
            if (trigger == null) {
                return;
            }
            String oldTime = trigger.getCronExpression();
            if (!oldTime.equalsIgnoreCase(time)) {
                // trigger已存在，则更新相应的定时设置
                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder
                        .cronSchedule(time);
                // 按新的cronExpression表达式重新构建trigger
                trigger = trigger.getTriggerBuilder().withIdentity(triggerKey)
                        .withSchedule(scheduleBuilder).build();
                // 按新的trigger重新设置job执行
                sched.resumeTrigger(triggerKey);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param jobName
     * @Description 移除一个任务(使用默认的任务组名 ， 触发器名 ， 触发器组名)
     * @author qgw
     * @date 2016年1月29日 下午2:21:16 ^_^
     */
    public static void removeJob(String jobName) {
        TriggerKey triggerKey = TriggerKey.triggerKey(
                jobName, TRIGGER_GROUP_NAME);
        JobKey jobKey = JobKey.jobKey(jobName, JOB_GROUP_NAME);
        try {
            Scheduler sched = gSchedulerFactory.getScheduler();
            Trigger trigger = (Trigger) sched.getTrigger(triggerKey);
            if (trigger == null) {
                return;
            }
            sched.pauseTrigger(triggerKey);
            ;// 停止触发器
            sched.unscheduleJob(triggerKey);// 移除触发器
            sched.deleteJob(jobKey);// 删除任务
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param jobName
     * @param jobGroupName
     * @param triggerName
     * @param triggerGroupName
     * @Description: 移除一个任务
     * @author qgw
     * @date 2016年1月29日 下午2:21:16 ^_^
     */
    public static void removeJob(String jobName, String jobGroupName,
                                 String triggerName, String triggerGroupName) {
        TriggerKey triggerKey = TriggerKey.triggerKey(
                jobName, triggerGroupName);
        JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
        try {
            Scheduler sched = gSchedulerFactory.getScheduler();
            sched.pauseTrigger(triggerKey);// 停止触发器
            sched.unscheduleJob(triggerKey);// 移除触发器
            sched.deleteJob(jobKey);// 删除任务
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param jobName
     * @param jobGroupName qgw 2016年1月22日 下午4:24:55 ^_^
     * @Description:暂停一个任务
     */
    public static void pauseJob(String jobName, String jobGroupName) {
        JobKey jobKey = JobKey.jobKey(jobName, jobName);
        try {
            Scheduler sched = gSchedulerFactory.getScheduler();
            sched.pauseJob(jobKey);
        } catch (SchedulerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * @param jobName qgw 2016年1月22日 下午4:24:55 ^_^
     * @Description:暂停一个任务(使用默认组名)
     */
    public static void pauseJob(String jobName) {
        JobKey jobKey = JobKey.jobKey(jobName, JOB_GROUP_NAME);
        try {
            Scheduler sched = gSchedulerFactory.getScheduler();
            sched.pauseJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Description:启动所有定时任务
     * @author qgw
     * @date 2016年1月29日 下午2:21:16 ^_^
     */
    public static void startJobs() {
        try {
            Scheduler sched = gSchedulerFactory.getScheduler();
            sched.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @Description 关闭所有定时任务
     * @author qgw
     * @date 2016年1月25日 下午2:26:54 ^_^
     */
    public static void shutdownJobs() {
        try {
            Scheduler sched = gSchedulerFactory.getScheduler();
            if (!sched.isShutdown()) {
                sched.shutdown();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String getQuartzTime(String time) {
        String result="";
        String year="";
        String month="";
        String day="";
        String hour="";
        String minute="";
        Integer resultMinute=0;
        String[]times = time.split(" ");
        String [] days = times[0].split("-");
        year=days[0];
        month=days[1].replaceAll("0","");
        day=days[2];
        String []tim = times[1].split(":");
        hour=tim[0];
        minute=tim[1];
        resultMinute = Integer.parseInt(minute)-20;
        if(resultMinute < 0){
            resultMinute = 60+resultMinute;
            hour = Integer.parseInt(hour)-1+"";
        }
        result = "0 "+resultMinute+" "+hour+" "+day+" "+month+" ? "+year;
        System.out.println(result);
        return result;
    }
}
