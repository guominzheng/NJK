package com.fh.service.gameService;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class GameCouponService {

    @Resource(name = "daoSupport")
    private DaoSupport dao;

    public List<PageData> getAllCoupon(PageData pd)throws Exception{
        return (List<PageData>) dao.findForList("GameCouponMapper.findCouponByUser",pd);
    }
    public void save(PageData pd)throws Exception{
         dao.save("GameCouponMapper.save",pd);
    }
    public void update(PageData pd)throws Exception{
        dao.update("GameCouponMapper.update",pd);
    }
    public List<PageData> findReturnCoupon(PageData pd)throws Exception{
        return (List<PageData>) dao.findForList("GameCouponMapper.findReturnCoupon",pd);
    }
}
