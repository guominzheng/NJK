package com.fh.service.gameService;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class GameCountService {

    @Resource(name="daoSupport")
    private DaoSupport dao;

    public void save(PageData pd)throws Exception{
        dao.save("GameCountMapper.save",pd);
    }

    public PageData getCount(PageData pd)throws Exception{
        return (PageData) dao.findForObject("GameCountMapper.getGameCount",pd);
    }

}
