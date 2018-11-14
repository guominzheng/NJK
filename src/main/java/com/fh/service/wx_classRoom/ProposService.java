package com.fh.service.wx_classRoom;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.fh.util.ServiceUtil;
import com.fh.util.SqlCentre;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.Map;
import javax.annotation.Resource;

@Service
public class ProposService {

    @Resource(name = "daoSupport")
    private DaoSupport dao;

    public Map findProposList(Page page, String sql){
        return ServiceUtil.serviceQuery(sql,page,dao,SqlCentre.SQL_TYPE_LIST,null);
    }
    public Map findProposById(PageData pd, String sql){
        return ServiceUtil.serviceQuery(sql,null,dao,SqlCentre.SQL_TYPE_OBJECT,pd);
    }
    public Map update(PageData pd, String sql){
        return ServiceUtil.serviceUpdate(sql,pd,dao,SqlCentre.SQL_TYPE_UPDATE);
    }
    public Map save(PageData pd, String sql){
        return ServiceUtil.serviceUpdate(sql,pd,dao,SqlCentre.SQL_TYPE_INSERT);
    }
}
