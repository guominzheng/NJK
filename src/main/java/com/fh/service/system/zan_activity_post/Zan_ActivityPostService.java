package com.fh.service.system.zan_activity_post;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;

@Service
@Resource(name="zan_ActivityPostService")
public class Zan_ActivityPostService {
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.save("Zan_ActivityPostMapper.save", pd);
	}
	
	public PageData findById(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Zan_ActivityPostMapper.findById", pd);
	}
	
	public void delete(PageData pd) throws Exception{
		dao.delete("Zan_ActivityPostMapper.delete", pd);
	}
	
	public PageData findCount(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Zan_ActivityPostMapper.findCount", pd);
	}
}
