package com.fh.service.system.content;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service
@Resource(name="contentService")
public class ContentService {
	
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.save("ContentMapper.save", pd);
	}
	
	public List<PageData> list(Page page) throws Exception{
		return (List<PageData>) dao.findForList("ContentMapper.datalistPage", page);
	}
	
	public List<PageData> findContent(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("ContentMapper.findContent", pd);
	}
	
	public void edit(PageData pd) throws Exception{
		dao.update("ContentMapper.edit", pd);
	}
}
