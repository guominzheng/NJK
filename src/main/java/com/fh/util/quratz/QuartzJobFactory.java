package com.fh.util.quratz;


import com.fh.service.wx_classRoom.QuratzService;
import com.fh.util.ModeUtil;
import com.fh.util.PageData;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.List;

public class QuartzJobFactory implements Job {

    private static final Logger log = Logger.getLogger("");

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("任务运行开始-------- start --------");
        try {
            //ScheduleJob任务运行时具体参数，可自定义
            ScheduleJob scheduleJob = (ScheduleJob) jobExecutionContext.getMergedJobDataMap().get(
                    "scheduleJob");
            QuratzService quratzService = scheduleJob.getQuratzService();
            PageData pd = new PageData();
            pd.put("liveId",scheduleJob.getLiveId());
            List<PageData> pds = quratzService.getAllQuratz(pd);
            if(null != pds && pds.size()>0){
                for(int i=0;i<pds.size();i++){
                    PageData p = pds.get(i);
                    String [] strs = {p.getString("key1"),p.getString("key2"),p.getString("key3"),p.getString("key4"),};
                    JSONObject jsonObject=ModeUtil.XiaoChengXun(strs);
                    ModeUtil.fasong3(p.getString("openId"),p.getString("page"),p.getString("form_id"),jsonObject);
                }
            }
            System.out.println("====================我完美执行完毕！");
        } catch (Exception e) {
            log.info("捕获异常===" + e);
        }
        log.info("任务运行结束-------- end --------");
    }

}
