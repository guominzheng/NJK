package com.fh.service.system.zan_catnest;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;

@Service
@Resource(name="can_CatnestService")
public class Zan_CatnestService {
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.save("Zan_CatnestMapper.save", pd);
	}
	
	public PageData findById(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Zan_CatnestMapper.findById", pd);
	}
	
	public PageData findById1(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Zan_CatnestMapper.findById1", pd);
	}
	
	public void delete(PageData pd) throws Exception{
		dao.delete("Zan_CatnestMapper.delete", pd);
	}
	
	public PageData findCount(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Zan_CatnestMapper.findCount", pd);
	}
}
