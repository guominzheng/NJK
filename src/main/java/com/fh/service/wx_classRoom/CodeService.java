// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2018/11/13 13:40:57
// Home Page:  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   CodeService.java
package com.fh.service.wx_classRoom;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class CodeService {
    @Resource(name="daoSupport")
    private DaoSupport dao;

    public List<PageData> findAllUrl(PageData pd) throws Exception {
        return (List<PageData>)this.dao.findForList("ClassRoom_CodeMapper.findAllUrl", pd);
    }

    public PageData findUrlByLiveId(PageData pd) throws Exception {
        return (PageData)this.dao.findForObject("ClassRoom_CodeMapper.findUrlByLiveId", pd);
    }

    public void save(PageData pd) throws Exception {
        this.dao.save("ClassRoom_CodeMapper.save", pd);
    }
}
