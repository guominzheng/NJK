package com.fh.service.system.activity_post;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service
@Resource(name="activity_PostService")
public class Activity_PostService {

	
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.save("ActivityPostMapper.save", pd);
	}
	
	public void edit(PageData pd) throws Exception{
		dao.update("ActivityPostMapper.edit", pd);
	}
	
	public List<PageData> list(Page page) throws Exception{
		return (List<PageData>) dao.findForList("ActivityPostMapper.datalistPage", page);
	}
	
	public List<PageData> list1(Page page) throws Exception{
		return (List<PageData>) dao.findForList("ActivityPostMapper.userlistPage", page);
	}
	
	public PageData findById(PageData pd) throws Exception{
		return (PageData) dao.findForObject("ActivityPostMapper.findById", pd);
	}
	
	public void delete(PageData pd) throws Exception{
		dao.delete("ActivityPostMapper.delete", pd);
	}
	
	public void deleteAll(String s[]) throws Exception{
		dao.delete("ActivityPostMapper.deleteAll", s);
	}
	
	public List<PageData> findList(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("ActivityPostMapper.findList", pd);
	}
	
	public List<PageData> findTop(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("ActivityPostMapper.findTop", pd);
	}
	
	public PageData findData(PageData pd) throws Exception{
		return (PageData) dao.findForObject("ActivityPostMapper.findData", pd);
	}
	
	public void editHuiFu(PageData pd) throws Exception{
		dao.update("ActivityPostMapper.editHuiFu", pd);
	}
	
	public void editViews(PageData pd) throws Exception{
		dao.update("ActivityPostMapper.editViews", pd);
	}
	
	public List<PageData> ActivitylistPage(Page page) throws Exception{
		return (List<PageData>) dao.findForList("ActivityPostMapper.ActivitylistPage", page);
	}
	
	public PageData findNumViews(PageData pd) throws Exception{
		return (PageData) dao.findForObject("ActivityPostMapper.findNumViews", pd);
	}
	
	
	public List<PageData> findTop3(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("ActivityPostMapper.findTop3", pd);
	}
	
	public void editRoom(PageData pd) throws Exception{
		dao.findForObject("ActivityPostMapper.editRoom", pd);
	}
	
	public List<PageData> findList3(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("ActivityPostMapper.findList3", pd);
	}
	
	public PageData findCount(PageData pd) throws Exception{
		return (PageData) dao.findForObject("ActivityPostMapper.findCount", pd);
	}
	
	public void delete1(PageData pd) throws Exception{
		dao.delete("ActivityPostMapper.delete1", pd);
	}
	
	public List<PageData> userlistPage1(Page page) throws Exception{
		return (List<PageData>) dao.findForList("ActivityPostMapper.userlistPage1", page);
	}
	
	public void editUserId(PageData pd) throws Exception{
		dao.update("ActivityPostMapper.editUserId", pd);
	}
}
