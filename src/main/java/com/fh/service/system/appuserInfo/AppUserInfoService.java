package com.fh.service.system.appuserInfo;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;

@Service
@Resource(name = "appUserInfoService")
public class AppUserInfoService {

    @Resource(name = "daoSupport")
    private DaoSupport dao;

    public void editVip(PageData pd) throws Exception {
        dao.update("AppUserInfoMapper.editVip", pd);
    }

    public void save(PageData pd) throws Exception {
        dao.save("AppUserInfoMapper.save", pd);
    }

    public void editQianMing(PageData pd) throws Exception {
        dao.update("AppUserInfoMapper.editQianMing", pd);
    }

    public void edit(PageData pd) throws Exception {
        dao.update("AppUserInfoMapper.edit", pd);
    }

    public void editImg(PageData pd) throws Exception {
        dao.update("AppUserInfoMapper.editImg", pd);
    }

    public void editAVIP(PageData pd) throws Exception {
        dao.update("AppUserInfoMapper.editAVIP", pd);
    }

    public List<PageData> findList(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("AppUserInfoMapper.findList", pd);
    }

    public PageData findUserByID(PageData pd) throws Exception {
        return (PageData) dao.findForObject("AppUserInfoMapper.findUserById", pd);
    }
    public PageData findUserIMGById(PageData pd) throws Exception {
        return (PageData) dao.findForObject("AppUserInfoMapper.findUserIMGById", pd);
    }
}
