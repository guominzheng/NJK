package com.fh.service.gameService;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class GameLuckService {
    @Resource(name="daoSupport")
    private DaoSupport dao;

    public List<PageData> getLuckList(PageData pd)throws Exception{
        return (List<PageData>)dao.findForList("GameLuckMapper.findLuckList",pd);
    }

    public void save(PageData pd)throws Exception{
       dao.save("GameLuckMapper.save",pd);
    }


    public void update(PageData pd)throws Exception{
        dao.update("GameLuckMapper.update",pd);
    }
}
