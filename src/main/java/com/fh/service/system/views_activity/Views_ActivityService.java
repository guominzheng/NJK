package com.fh.service.system.views_activity;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;

@Service
@Resource(name="views_ActivityService")
public class Views_ActivityService {
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.save("Views_ActivityMapper.save", pd);
	}
	
	public PageData findById(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Views_ActivityMapper.findById", pd);
	}
	
	public PageData findCount(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Views_ActivityMapper.findCount", pd);
	}
	
	public List<PageData> findList(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("Views_ActivityMapper.findList", pd);
	}
}
