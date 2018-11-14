package com.fh.service.system.user_WendaQuanxian;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("user_WendaQuanxianService")
public class User_WendaQuanxianService {

    @Resource(name = "daoSupport")
    private DaoSupport dao;


    public void save(PageData pd) throws Exception {
         dao.save("User_WendaQuanxianMapper.save", pd);
    }

    public List<PageData> findAll(PageData pd) throws Exception {
       return (List<PageData>)dao.findForList("User_WendaQuanxianMapper.findAll", pd);
    }

    public PageData findQByUserId(PageData pd) throws Exception {
        return (PageData)dao.findForObject("User_WendaQuanxianMapper.findQByUserId",pd);
    }
    public void editPAYSTA(PageData pd) throws Exception {
       dao.update("User_WendaQuanxianMapper.editPAYSTA",pd);
    }
    public void editFEN(PageData pd) throws Exception {
        dao.update("User_WendaQuanxianMapper.editFEN",pd);
    }
}
