package com.fh.service.system.zan_comment_research_youke;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Resource(name="zan_CommentResearchYoukeService")
public class Zan_CommentResearchYoukeService {
    @Resource(name="daoSupport")
    private DaoSupport dao;

    public void save(PageData pd) throws Exception{
        dao.save("Zan_Comment_reserach_youkeMapper.save", pd);
    }
    public PageData findById(PageData pd) throws Exception{
        return (PageData) dao.findForObject("Zan_Comment_reserach_youkeMapper.findById", pd);
    }
    public void delete(PageData pd) throws Exception{
        dao.delete("Zan_Comment_reserach_youkeMapper.deleteZan", pd);
    }

    public void deletes(PageData pd) throws Exception {
        dao.delete("Zan_Comment_reserach_youkeMapper.deletes",pd);
    }
}
