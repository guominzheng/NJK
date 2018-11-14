package com.fh.util.quratz;

import com.fh.service.wx_classRoom.QuratzService;
import com.fh.util.PageData;

import java.util.List;

public class ScheduleJob {
    private Long jobId;
    private String jobName;
    private Long creTime;
    private String jobTime;
    private String jobCron;
    private String jobGroup;
    private String jobDesc;
    private String liveId;
    private QuratzService quratzService;
    private List<PageData> pds;

    public List<PageData> getPds() {
        return pds;
    }

    public void setPds(List<PageData> pds) {
        this.pds = pds;
    }

    public QuratzService getQuratzService() {
        return quratzService;
    }

    public void setQuratzService(QuratzService quratzService) {
        this.quratzService = quratzService;
    }

    public String getLiveId() {
        return liveId;
    }

    public void setLiveId(String liveId) {
        this.liveId = liveId;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }


    public Long getCreTime() {
        return creTime;
    }

    public void setCreTime(Long creTime) {
        this.creTime = creTime;
    }

    public String getJobTime() {
        return jobTime;
    }

    public void setJobTime(String jobTime) {
        this.jobTime = jobTime;
    }

    public String getJobCron() {
        return jobCron;
    }

    public void setJobCron(String jobCron) {
        this.jobCron = jobCron;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getJobDesc() {
        return jobDesc;
    }

    public void setJobDesc(String jobDesc) {
        this.jobDesc = jobDesc;
    }
}
