package com.fh.service.system.wancheng;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service
@Resource(name="wanChengService")
public class WanChengService {
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.save("WanChengMapper.save", pd);
	}
	
	public List<PageData> findCount(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("WanChengMapper.findCount", pd);
	}
	
	public List<PageData> datalistPage(Page page) throws Exception{
		return (List<PageData>) dao.findForList("WanChengMapper.datalistPage", page);
	}
}
