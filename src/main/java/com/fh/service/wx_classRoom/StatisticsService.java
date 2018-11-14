// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2018/11/13 13:44:59
// Home Page:  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   StatisticsService.java
package com.fh.service.wx_classRoom;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;


@Service
public class StatisticsService {
    @Resource(name="daoSupport")
    private DaoSupport dao;

    public void save(PageData pd) throws Exception {
        this.dao.save("ClassRoom_StatisticsMapper.save", pd);
    }

    public void delete(PageData pd) throws Exception {
        this.dao.delete("ClassRoom_StatisticsMapper.delete", pd);
    }

    public List<PageData> findAllData(PageData pd) throws Exception {
        return (List<PageData>)this.dao.findForList("ClassRoom_StatisticsMapper.findAllDataList", pd);
    }

    public PageData findDataByOpenId(PageData pd) throws Exception {
        return (PageData)this.dao.findForObject("ClassRoom_StatisticsMapper.findDataByOpenId", pd);
    }

    public void update(PageData pd) throws Exception {
        this.dao.update("ClassRoom_StatisticsMapper.update", pd);
    }
}
