package com.fh.service.system.zan_leaving_messageService;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

@Service
@Resource(name = "zan_leaving_MessageService")
public class Zan_leaving_MessageService {
    @Resource(name = "daoSupport")
    private DaoSupport dao;

    public void save(PageData pd) throws Exception {
        dao.save("Zan_Leaving_MessageMapper.save", pd);
    }

    public PageData findById(PageData pd) throws Exception {
        return (PageData) dao.findForObject("Zan_Leaving_MessageMapper.findById", pd);
    }

    public void delete(PageData pd) throws Exception {
        dao.delete("Zan_Leaving_MessageMapper.delete", pd);
    }
}
