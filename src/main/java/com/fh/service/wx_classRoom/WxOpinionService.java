// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2018/11/13 13:42:51
// Home Page:  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   WxOpinionService.java
package com.fh.service.wx_classRoom;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WxOpinionService {
    @Autowired
    private DaoSupport dao;

    public void save(PageData pd) throws Exception {
        this.dao.save("ClassRoom_OpinionMapper.save", pd);
    }

    public List<PageData> findAll(Page page) throws Exception {
        return (List<PageData>)this.dao.findForList("ClassRoom_OpinionMapper.findOpinionlistPage", page);
    }

    public void update(PageData pd) throws Exception {
        this.dao.update("ClassRoom_OpinionMapper.update", pd);
    }
}
