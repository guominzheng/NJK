package com.fh.service.wx_classRoom;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.DateUtil;
import com.fh.util.PageData;
import com.fh.util.ServiceUtil;
import com.fh.util.SqlCentre;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AttendService {
    @Resource(name = "daoSupport")
    private DaoSupport dao;

    /**
     * 查询观看课程列表和购买课程列表
     *
     * @param page pageNum 页码  userId 用户  look_pay 1：观看 2：购买
     * @param
     * @return
     */
    public List<PageData> findAttendList(Page page)throws Exception {
        return (List<PageData>)dao.findForList(SqlCentre.ATTEND_FIND_LIST,page);
    }

    public Map save(PageData pd, String sql) throws Exception {
        Map map = new HashMap();
        PageData pds = (PageData) dao.findForObject(SqlCentre.ATTEND_FIND_USER_ATTEND, pd);
        if (null != pds && pds.size() > 0) {
            pds.put("createTime", DateUtil.getTime());
            map = ServiceUtil.serviceUpdate(SqlCentre.ATTEND_UPDATE, pds, dao, SqlCentre.SQL_TYPE_UPDATE);
        } else {
            map = ServiceUtil.serviceUpdate(sql, pd, dao, SqlCentre.SQL_TYPE_INSERT);
        }
        return map;
    }
}
