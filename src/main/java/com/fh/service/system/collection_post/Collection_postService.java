package com.fh.service.system.collection_post;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service
@Resource(name="collection_postService")
public class Collection_postService {
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.save("Collection_postMapper.save", pd);
	}
	
	public void delete(PageData pd) throws Exception{
		dao.delete("Collection_postMapper.delete", pd);
	}
	
	public PageData findCollection(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Collection_postMapper.findCollection", pd);
	}
	
	public List<PageData> datalistPage(Page page) throws Exception{
		return (List<PageData>) dao.findForList("Collection_postMapper.datalistPage", page);
	}
}