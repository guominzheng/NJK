package com.fh.service.system.activity;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service
@Resource(name="activity")
public class ActivityService {

	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.save("ActivityMapper.save", pd);
	}
	
	public void edit(PageData pd) throws Exception{
		dao.update("ActivityMapper.edit", pd);
	}
	
	public List<PageData> list(Page page) throws Exception{
		return (List<PageData>) dao.findForList("ActivityMapper.datalistPage", page);
	}
	
	public PageData findById(PageData pd) throws Exception{
		return (PageData) dao.findForObject("ActivityMapper.findById", pd);
	}
	
	public void delete(PageData pd) throws Exception{
		dao.delete("ActivityMapper.delete", pd);
	}
	
	public void deleteAll(String i[]) throws Exception{
		dao.delete("ActivityMapper.deleteAll", i);
	}
	
	public void editStatus(PageData pd) throws Exception{
		dao.update("ActivityMapper.editStatus", pd);
	}
	
	public void editSpecialStatus(PageData pd) throws Exception{
		dao.update("ActivityMapper.editSpecialStatus", pd);
	}
	
	public List<PageData> findList(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("ActivityMapper.findList", pd);
	}
	
	public void editViews(PageData pd) throws Exception{
		dao.update("ActivityMapper.editViews", pd);
	}
	
	public void editFangWen(PageData pd) throws Exception{
		dao.update("ActivityMapper.editFangWen", pd);
	}
}
