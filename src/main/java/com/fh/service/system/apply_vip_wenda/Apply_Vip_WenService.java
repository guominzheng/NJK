package com.fh.service.system.apply_vip_wenda;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("apply_vip_WenService")
public class Apply_Vip_WenService {

    @Resource(name="daoSupport")
    private DaoSupport dao;

    public void save(PageData pd) throws Exception{
        dao.save("ApplyVipWenDaMapper.save", pd);
    }
    public void deleteA(PageData pd) throws Exception{
        dao.delete("ApplyVipWenDaMapper.deleteA", pd);
    }
    public void delete(PageData pd) throws Exception{
        dao.delete("ApplyVipWenDaMapper.delete", pd);
    }

    public List<PageData> findUserList(Page pa)throws Exception{
        return (List<PageData>)dao.findForList("ApplyVipWenDaMapper.datalistPage",pa);
    }
    public PageData findById(PageData pd) throws Exception{
        return (PageData) dao.findForObject("ApplyVipWenDaMapper.findById", pd);
    }
    public List<PageData> findList(PageData pd)throws Exception{
        return (List<PageData>)dao.findForList("ApplyVipWenDaMapper.findList",pd);
    }
}
