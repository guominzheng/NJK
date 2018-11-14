package com.fh.service.gameService;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class GameRankService {

    @Resource(name = "daoSupport")
    private DaoSupport dao;

    public List<PageData> getAll(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("GameRankMapper.getAllRankList", pd);
    }

    public void save(PageData pd) throws Exception {
        dao.save("GameRankMapper.save", pd);
    }

    public void update(PageData pd) throws Exception {
        dao.update("GameRankMapper.update", pd);
    }

    public PageData findRankByUserId(PageData pd) throws Exception {
        return (PageData) dao.findForObject("GameRankMapper.findRankByUserId", pd);
    }
}
