package com.fh.service.gameService;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class GameUserService {

    @Resource(name = "daoSupport")
    private DaoSupport dao;

    public void save(PageData pd) throws Exception {
        dao.save("GameUserMapper.save", pd);
    }

    public List<PageData> findAllUserList(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("GameUserMapper.findAllUserList", pd);
    }
    public PageData findUser(PageData pd) throws Exception {
        return (PageData) dao.findForObject("GameUserMapper.findUser", pd);
    }
    public void update(PageData pd) throws Exception {
        dao.update("GameUserMapper.update", pd);
    }

    public PageData updateRecommend(PageData pd) throws Exception {
        dao.update("GameUserMapper.updateRecommend", pd);
        return (PageData) dao.findForObject("GameUserMapper.findAllUserList", pd);
    }

    public void updateDraw(PageData pd) throws Exception {
        dao.update("GameUserMapper.updateDraw", pd);
    }

    public void updateAllDraw(PageData pd) throws Exception {
        dao.update("GameUserMapper.updateAllDraw", pd);
    }
}
