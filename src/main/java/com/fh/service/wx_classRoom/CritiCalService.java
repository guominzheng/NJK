// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2018/11/13 13:44:17
// Home Page:  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   CritiCalService.java
package com.fh.service.wx_classRoom;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class CritiCalService {
    @Resource(name="daoSupport")
    private DaoSupport dao;

    public List<PageData> getAllCal(PageData pd) throws Exception {
        return (List<PageData>)this.dao.findForList("ClassRoom_CriticalMapper.findAllCal", pd);
    }

    public List<PageData> getAllCalListPage(Page page) throws Exception {
        return (List<PageData>)this.dao.findForList("ClassRoom_CriticalMapper.findAllCallistPage", page);
    }

    public void save(PageData pd) throws Exception {
        this.dao.save("ClassRoom_CriticalMapper.save", pd);
    }

    public void delete(PageData pd) throws Exception {
        this.dao.delete("ClassRoom_CriticalMapper.delete", pd);
    }

    public PageData findCalById(PageData pd) throws Exception {
        return (PageData)this.dao.findForObject("ClassRoom_CriticalMapper.findCalById", pd);
    }

    public void updateAll(PageData pd) throws Exception {
        this.dao.update("ClassRoom_CriticalMapper.updateAll", pd);
    }

    public PageData findCalCount(PageData pd) throws Exception {
        return (PageData)this.dao.findForObject("ClassRoom_CriticalMapper.findCalCount", pd);
    }
}
