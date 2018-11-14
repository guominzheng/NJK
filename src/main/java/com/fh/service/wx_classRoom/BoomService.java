// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2018/11/13 13:44:44
// Home Page:  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   BoomService.java
package com.fh.service.wx_classRoom;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class BoomService {
    @Resource(name="daoSupport")
    private DaoSupport dao;

    public List<PageData> findBoomByOpenId(PageData pd) throws Exception {
        return (List<PageData>)this.dao.findForList("ClassRoom_BoomMapper.findBoomByOpenId", pd);
    }

    public void save(PageData pd) throws Exception {
        this.dao.save("ClassRoom_BoomMapper.save", pd);
    }

    public void modifyState(PageData pd) throws Exception {
        this.dao.update("ClassRoom_BoomMapper.modifyState", pd);
    }

    public void delete(PageData pd) throws Exception {
        this.dao.delete("ClassRoom_BoomMapper.delete", pd);
    }

    public List<PageData> findAll(PageData pd) throws Exception {
        return (List<PageData>)this.dao.findForList("ClassRoom_BoomMapper.findAll", pd);
    }

    public void deleteQuratz(PageData pd) throws Exception {
        this.dao.delete("ClassRoom_BoomMapper.deleteQuratz", pd);
    }
}
