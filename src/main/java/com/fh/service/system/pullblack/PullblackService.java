package com.fh.service.system.pullblack;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/*
    Created by xxj on 2018\5\4 0004.  
 */
@Service
@Resource(name = "pullblackService")
public class PullblackService {
    @Resource(name = "daoSupport")
    public DaoSupport daoSupport;

    public void save(PageData pd) throws Exception {
        daoSupport.save("PullblackMapper.save",pd);
    }

    public List<PageData> findList(PageData pd) throws Exception {
        return (List<PageData>) daoSupport.findForList("PullblackMapper.findByUserId",pd);
    }

    public void delete(PageData pd) throws Exception {
        daoSupport.delete("PullblackMapper.delete",pd);
    }
    public PageData findByUserId(PageData pd) throws Exception {
        return (PageData) daoSupport.findForObject("PullblackMapper.findByUserId",pd);
    }

    public List<PageData> blackList(Page page) throws Exception {
        return (List<PageData>) daoSupport.findForList("PullblackMapper.blackListlistPage",page);
    }
}
