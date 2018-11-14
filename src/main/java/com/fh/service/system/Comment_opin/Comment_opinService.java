package com.fh.service.system.Comment_opin;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

/**
 * 客户建议回复信息Service   table_comment_opin
 */
@Service
@Resource(name = "comment_opinService")
public class Comment_opinService {
    //dao基类注入
    @Resource(name = "daoSupport")
    private DaoSupport dao;

    /**
     * 按 查询 返回列表
     * @param page   客户信息 /客服信息  /以及回复表主键
     * @return
     * @throws Exception
     */
    public List<PageData> findlistPage(Page page)throws Exception{
        return (List<PageData>)dao.findForList("Comment_opinMapper.findlistPage",page);
    }

    /**
     * 添加回复信息     用户ID 客服ID 回复内容 创建时间 以及建议表外键
     * @param pd
     * @throws Exception
     */
    public void save (PageData pd) throws  Exception{
            dao.save("Comment_opinMapper.save",pd);
    }

    /**
     * 删除建议回复表信息      BY ID
     * @param pd
     * @throws Exception
     */
    public void del(PageData pd)throws Exception{
            dao.delete("Comment_opinMapper.del",pd);
    }

    /**
     * 批量删除建议回复标新   BY IDarray
     * @param p
     * @throws Exception
     */
    public void deleteAll(String[] p)throws Exception{
        dao.delete("Comment_opinMapper.deleteAll",p);
    }
}
