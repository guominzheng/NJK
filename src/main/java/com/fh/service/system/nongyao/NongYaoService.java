package com.fh.service.system.nongyao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service
@Resource(name="nongYaoService")
public class NongYaoService {
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public List<PageData> list(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("NongYaoMapper.findList", pd);
	}
	
	public PageData findById(PageData pd) throws Exception{
		return (PageData) dao.findForObject("NongYaoMapper.findById", pd);
	}
}
