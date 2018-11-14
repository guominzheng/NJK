package com.fh.service.wx_classRoom;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.fh.util.ServiceUtil;
import com.fh.util.SqlCentre;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Map;



@Service
public class WxBannerService {
    @Resource(name = "daoSupport")
    private DaoSupport dao;

    public Map findBannerList(Page page, String sql) {
        return ServiceUtil.serviceQuery(sql, page, dao, SqlCentre.SQL_TYPE_LIST,null);
    }
    public Map findBannerOne(PageData pd, String sql) {
        return ServiceUtil.serviceQuery(sql, null, dao, SqlCentre.SQL_TYPE_OBJECT,pd);
    }
    public Map save(PageData pd, String sql) {
        return ServiceUtil.serviceUpdate(sql, pd, dao, SqlCentre.SQL_TYPE_INSERT);
    }
    public Map update(PageData pd, String sql) {
        return ServiceUtil.serviceUpdate(sql, pd, dao, SqlCentre.SQL_TYPE_UPDATE);
    }
    public Map delete(PageData pd, String sql) {
        return ServiceUtil.serviceUpdate(sql, pd, dao, SqlCentre.SQL_TYPE_DELETE);
    }

}
