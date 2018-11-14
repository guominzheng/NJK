package com.fh.service.gameService;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class GameInviteService {

    @Resource(name="daoSupport")
    private DaoSupport dao;

    public void save(PageData pd)throws Exception{
            dao.save("GameInviteMapper.save",pd);
    }

    public PageData findInviteByUser(PageData pd)throws Exception{
        return (PageData)dao.findForObject("GameInviteMapper.findInviteByUser",pd);
    }
    public List<PageData> findInviteByOpenId(PageData pd)throws Exception{
        return (List<PageData>)dao.findForList("GameInviteMapper.findInviteByOpenId",pd);
    }
}
