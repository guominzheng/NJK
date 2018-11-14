package com.fh.service.system.qiandao;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;

@Service
@Resource(name="qianDaoService")
public class QianDaoService {
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	
	public void save(PageData pd) throws Exception{
		dao.save("QianDaoMapper.save", pd);
	}
	
	public PageData findUserId(PageData pd) throws Exception{
		return (PageData) dao.findForObject("QianDaoMapper.findUserId", pd);
	}
	
	public PageData findUserIdDate(PageData pd) throws Exception{
		return (PageData) dao.findForObject("QianDaoMapper.findUserIdDate", pd);
	}
	
	public void edit(PageData pd) throws Exception{
		dao.update("QianDaoMapper.edit", pd);
	}
	
	public void editS(PageData pd) throws Exception{
		dao.findForObject("QianDaoMapper.editS", pd);
	}
	
	public PageData findUser(PageData pd) throws Exception{
		return (PageData) dao.findForObject("QianDaoMapper.findUser", pd);
	}
}
