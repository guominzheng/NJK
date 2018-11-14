// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2018/11/13 13:43:50
// Home Page:  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   WxSocketTypeService.java
package com.fh.service.wx_classRoom;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class WxSocketTypeService {
    @Resource(name="daoSupport")
    private DaoSupport dao;

    public void save(PageData pd) throws Exception {
        this.dao.save("ClassRoom_TypeMapper.save", pd);
    }

    public List<PageData> findAllType(PageData pd) throws Exception {
        return (List<PageData>)this.dao.findForList("ClassRoom_TypeMapper.findAllType", pd);
    }

    public List<PageData> findAll(Page page) throws Exception {
        return (List<PageData>)this.dao.findForList("ClassRoom_TypeMapper.findAlllistPage", page);
    }

    public void update(PageData pd) throws Exception {
        this.dao.update("ClassRoom_TypeMapper.update", pd);
    }

    public void delete(PageData pd) throws Exception {
        this.dao.delete("ClassRoom_TypeMapper.delete", pd);
    }

    public PageData findOne(PageData pd) throws Exception {
        return (PageData)this.dao.findForObject("ClassRoom_TypeMapper.findOne", pd);
    }
}
