package com.fh.service.system.cbannerimg;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service
@Resource(name="cbannerimgService")
public class CbannerimgService {
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.save("CbannerimgMapper.save", pd);
	}
	
	public void edit(PageData pd) throws Exception{
		dao.update("CbannerimgMapper.edit", pd);
	}
	
	public PageData findById(PageData pd) throws Exception{
		return (PageData) dao.findForObject("CbannerimgMapper.findById", pd);
	}
	
	public List<PageData> datalistPage(Page page) throws Exception{
		return (List<PageData>) dao.findForList("CbannerimgMapper.datalistPage", page);
	}
}
