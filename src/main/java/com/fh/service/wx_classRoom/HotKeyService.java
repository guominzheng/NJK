// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2018/11/13 13:42:16
// Home Page:  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   HotKeyService.java
package com.fh.service.wx_classRoom;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class HotKeyService {
    @Resource(name="daoSupport")
    private DaoSupport dao;

    public List<PageData> list(Page page) throws Exception {
        return (List<PageData>)this.dao.findForList("ClassRoom_KeyMapper.findAllKeylistPage", page);
    }

    public PageData one(PageData pd) throws Exception {
        return (PageData)this.dao.findForObject("ClassRoom_KeyMapper.findKeyOne", pd);
    }

    public void update(PageData pd) throws Exception {
        this.dao.update("ClassRoom_KeyMapper.update", pd);
    }

    public void save(PageData pd) throws Exception {
        this.dao.save("ClassRoom_KeyMapper.save", pd);
    }
}
