package com.fh.service.system.token;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;

@Service
@Resource(name="TokenService")
public class TokenService {
	
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.save("TokenMapper.save", pd);
	}
	
	public PageData findById(PageData pd) throws Exception{
		return (PageData) dao.findForObject("TokenMapper.findById", pd);
	}
	
	public void edit(PageData pd) throws Exception{
		dao.update("TokenMapper.edit", pd);
	}
}
