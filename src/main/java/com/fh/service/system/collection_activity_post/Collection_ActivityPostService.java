package com.fh.service.system.collection_activity_post;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service
@Resource(name="collection_ActivityPostService")
public class Collection_ActivityPostService {

	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.save("Collection_ActivityPostMapper.save", pd);
	}
	
	public PageData findById(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Collection_ActivityPostMapper.findById", pd);
	}
	
	public void delete(PageData pd) throws Exception{
		dao.delete("Collection_ActivityPostMapper.delete", pd);
	}
	
	public List<PageData> list(Page page) throws Exception{
		return (List<PageData>) dao.findForList("Collection_ActivityPostMapper.datalistPage", page);
	}
}
