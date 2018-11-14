package com.fh.service.system.count_community;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Resource(name="count_communityService")
public class Count_communityService {

    @Resource(name="daoSupport")
    private DaoSupport dao;


    public void save(PageData pd) throws Exception{
        dao.save("Count_communityMapper.save", pd);
    }

    public List<PageData> datalistPage(Page page) throws Exception{
        return (List<PageData>) dao.findForList("Count_communityMapper.datalistPage", page);
    }
    public List<PageData> USERdatalistPage(Page page) throws Exception{
        return (List<PageData>) dao.findForList("Count_communityMapper.USERdatalistPage", page);
    }

    public List<PageData> UdatalistPage(Page page) throws Exception{
        return (List<PageData>) dao.findForList("Count_communityMapper.UdatalistPage", page);
    }
    public PageData findById(PageData pd) throws Exception{
        return (PageData)dao.findForObject("Count_communityMapper.findById", pd);
    }
    public void delete(PageData pd) throws Exception{
        dao.delete("Count_communityMapper.delete", pd);
    }
    public void edit (PageData pd) throws Exception{
        dao.update("Count_communityMapper.edit",pd);
    }
}
