package com.fh.service.system.collection_activity;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service
@Resource(name="collection_activityService")
public class Collection_activityService {
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.save("Collection_activityMapper.save", pd);
	}
	
	public PageData findById(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Collection_activityMapper.findById", pd);
	}
	
	public void delete(PageData pd) throws Exception{
		dao.delete("Collection_activityMapper.delete", pd);
	}
	
	public List<PageData> findCollectionActivity(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("Collection_activityMapper.findCollectionActivity", pd);
	}
	
	public List<PageData> datalistPage(Page page) throws Exception{
		return (List<PageData>) dao.findForList("Collection_activityMapper.datalistPage", page);
	}
 }
