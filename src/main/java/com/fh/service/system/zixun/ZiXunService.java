package com.fh.service.system.zixun;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service
@Resource(name="ziXunService")
public class ZiXunService {
	
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public List<PageData> list(Page page) throws Exception{
		return (List<PageData>) dao.findForList("ZiXunMapper.datalistPage", page);
	}
	
	public void edit(PageData pd) throws Exception{
		dao.update("ZiXunMapper.edit", pd);
	}
	
	public PageData findById(PageData pd) throws Exception{
		return (PageData) dao.findForObject("ZiXunMapper.findById", pd);
	}
	
}
