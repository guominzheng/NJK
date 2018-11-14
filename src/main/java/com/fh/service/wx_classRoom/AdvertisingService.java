// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2018/11/13 13:45:12
// Home Page:  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   AdvertisingService.java
package com.fh.service.wx_classRoom;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class AdvertisingService {
    @Resource(name="daoSupport")
    private DaoSupport dao;

    public void save(PageData pd) throws Exception {
        this.dao.save("ClassRoom_AdvertisingMapper.save", pd);
    }

    public void update(PageData pd) throws Exception {
        this.dao.save("ClassRoom_AdvertisingMapper.update", pd);
    }

    public void delete(PageData pd) throws Exception {
        this.dao.save("ClassRoom_AdvertisingMapper.delete", pd);
    }

    public PageData findDataById(PageData pd) throws Exception {
        return (PageData)this.dao.findForObject("ClassRoom_AdvertisingMapper.findById", pd);
    }

    public List<PageData> findAllData(Page page) throws Exception {
        return (List)this.dao.findForList("ClassRoom_AdvertisingMapper.findAllDatalistPage", page);
    }
}
