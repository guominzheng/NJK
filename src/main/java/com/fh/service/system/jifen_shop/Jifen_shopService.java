package com.fh.service.system.jifen_shop;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
@Resource(name="jifen_shopService")
public class Jifen_shopService {

    @Resource(name="daoSupport")
    private DaoSupport dao;

    public void save(PageData pd) throws Exception{
        dao.save("Jifen_shopMapper.save", pd);
    }

    public List<PageData> datalistPage(Page page) throws Exception{
        return (List<PageData>) dao.findForList("Jifen_shopMapper.datalistPage", page);
    }
    public List<PageData> data1listPage(Page page) throws Exception{
        return (List<PageData>) dao.findForList("Jifen_shopMapper.data1listPage", page);
    }

    public void editInfo(PageData pd) throws Exception{
        dao.update("Jifen_shopMapper.editInfo", pd);
    }
    public void editNotInfo(PageData pd) throws Exception{
        dao.update("Jifen_shopMapper.editNotInfo", pd);
    }

    public PageData findById(PageData pd) throws Exception{
        return (PageData) dao.findForObject("Jifen_shopMapper.findDataById", pd);
    }

    public PageData findShopById(PageData pd) throws Exception{
        return (PageData) dao.findForObject("Jifen_shopMapper.findShopById", pd);
    }

    public void delete(PageData pd) throws Exception{
        dao.delete("Jifen_shopMapper.delete", pd);
    }
    public void deleteAll(String[] str) throws Exception{
        dao.delete("Jifen_shopMapper.deleteAll", str);
    }
}
