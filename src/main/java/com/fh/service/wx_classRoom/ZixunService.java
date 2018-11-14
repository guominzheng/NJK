// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2018/11/13 13:42:31
// Home Page:  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ZixunService.java
package com.fh.service.wx_classRoom;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.fh.util.ServiceUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class ZixunService {
    @Resource(name="daoSupport")
    private DaoSupport dao;

    public Map save(PageData pd, String sql) {
        return ServiceUtil.serviceUpdate(sql, (PageData)pd, this.dao, "insert");
    }

    public Map list(Page page, String sql) {
        return ServiceUtil.serviceQuery(sql, page, this.dao, "list", (PageData)null);
    }

    public Map one(PageData pd, String sql) {
        HashMap<String, String> map = new HashMap<String, String>();
        PageData pds = null;
        PageData zixun = null;
        try {
            pds = (PageData)this.dao.findForObject("ClassRoom_ZixunMapper.findZixunUser", pd);
            if (pds != null) return ServiceUtil.serviceQuery(sql, null, this.dao, "object", (PageData)pd);
            this.dao.save("ClassRoom_ZixunMapper.saveUser", pd);
            zixun = (PageData)this.dao.findForObject("ClassRoom_ZixunMapper.findZixunById", pd);
            Integer view = Integer.parseInt(zixun.get("zixun_view").toString());
            pd.put("zixun_view", (view + 1));
            this.dao.update("ClassRoom_ZixunMapper.update", pd);
        }
        catch (Exception e) {
            map.put("code", "2");
            map.put("message", "\u7a0b\u5e8f\u51fa\u9519\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458\uff01");
            return map;
        }
        return ServiceUtil.serviceQuery(sql, null, this.dao, "object", (PageData)pd);
    }

    public Map update(PageData pd, String sql) {
        return ServiceUtil.serviceUpdate(sql, (PageData)pd, this.dao, "update");
    }

    public void update(PageData pd) throws Exception {
        this.dao.update("ClassRoom_ZixunMapper.update", pd);
    }

    public Map delete(PageData pd, String sql) {
        return ServiceUtil.serviceUpdate(sql, (PageData)pd, this.dao, "delete");
    }

    public void delete(PageData pd) throws Exception {
        this.dao.delete("ClassRoom_ZixunMapper.delete", pd);
    }

    public List<PageData> findZixun(Page page) throws Exception {
        return (List<PageData>)this.dao.findForList("ClassRoom_ZixunMapper.findZixunlistPage", page);
    }

    public PageData findZixunOne(PageData pd) throws Exception {
        return (PageData)this.dao.findForObject("ClassRoom_ZixunMapper.findZixunById", pd);
    }
}
