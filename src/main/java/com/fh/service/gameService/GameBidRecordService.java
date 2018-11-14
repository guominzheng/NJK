package com.fh.service.gameService;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class GameBidRecordService {
    @Resource(name = "daoSupport")
    private DaoSupport dao;

    public void save(PageData pd) throws Exception {
        dao.save("GameBidRecordMapper.save", pd);
    }
    public List<PageData> findPrizeByUser(Page page ) throws Exception{
        return (List<PageData>) dao.findForList("GameBidRecordMapper.findPrizeByUserlistPage",page);
    }
    public void update(PageData pd) throws Exception {
        dao.update("GameBidRecordMapper.update", pd);
    }
    public PageData findPrizeById(PageData pd ) throws Exception{
        return (PageData) dao.findForObject("GameBidRecordMapper.findPrizeById",pd);
    }
}
