package com.fh.service.system.IntegrationService;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class IntegrationService {
    @Resource(name="daoSupport")
    private DaoSupport dao;


    public List<PageData> getAllData(Page page)throws Exception{
        return (List<PageData>)dao.findForList("IntegrationMapper.findAllDatalistPage",page);
    }
    public PageData findDataById(PageData pd)throws Exception{
        return (PageData)dao.findForObject("IntegrationMapper.fdinDataById",pd);
    }

    public void save(PageData pd)throws Exception{
        dao.save("IntegrationMapper.save",pd);
    }
    public void update(PageData pd)throws Exception{
        dao.update("IntegrationMapper.update",pd);
    }
    public void delete(PageData pd)throws Exception{
        dao.delete("IntegrationMapper.delete",pd);
    }
}
