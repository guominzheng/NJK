package com.fh.service.wx_classRoom;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.ServiceUtil;
import com.fh.util.SqlCentre;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class RoomService {

    @Resource(name = "daoSupport")
    private DaoSupport dao;

    public Map findAll (Page page,String sql) throws Exception{
        return ServiceUtil.serviceQuery(sql,page,dao, SqlCentre.SQL_TYPE_LIST,null);
    }
}
