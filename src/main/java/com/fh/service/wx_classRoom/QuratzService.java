package com.fh.service.wx_classRoom;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class QuratzService {

    @Resource(name = "daoSupport")
    private DaoSupport dao;

    public List<PageData> getAllQuratz(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("ClassRoom_QuratzMapper.findAll", pd);
    }
    public void save(PageData pd) {
        try {
            dao.save("ClassRoom_QuratzMapper.save", pd);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
