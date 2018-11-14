package com.fh.service.system.user_daily;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
@Service
@Resource(name="user_DailyService")
public class User_DailyService {
    @Resource(name="daoSupport")
    private DaoSupport dao;
    public void save(PageData pd) throws Exception{
        dao.save("User_DailyMapper.save", pd);
    }

    public PageData findDailyByUserId(PageData pd) throws  Exception{
        return (PageData) dao.findForObject("User_DailyMapper.findDailyByUserId", pd);
    }
}
