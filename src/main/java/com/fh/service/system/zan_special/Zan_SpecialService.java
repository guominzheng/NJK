package com.fh.service.system.zan_special;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;

@Service
@Resource(name="zan_SpecialService")
public class Zan_SpecialService {
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.save("Zan_SpecialMapper.save", pd);
	}
	
	public PageData findById(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Zan_SpecialMapper.findById", pd);
	}
	
	public void delete(PageData pd) throws Exception{
		dao.delete("Zan_SpecialMapper.delete", pd);
	}
}
