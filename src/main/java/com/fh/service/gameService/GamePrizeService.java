package com.fh.service.gameService;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class GamePrizeService {

    @Resource(name = "daoSupport")
    private DaoSupport dao;

    /**
     * 获取全部奖品列表
     * @param pd
     * @return
     * @throws Exception
     */
    public List<PageData> getAllPrize(PageData pd ) throws Exception{
        return (List<PageData>) dao.findForList("GamePrizeMapper.getAllPrizeList",pd);
    }

    public void update(PageData pd )throws Exception{
        dao.update("GamePrizeMapper.update",pd);
    }
}
