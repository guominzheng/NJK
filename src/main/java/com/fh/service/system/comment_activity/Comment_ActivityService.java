package com.fh.service.system.comment_activity;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service
@Resource(name="comment_ActivityService")
public class Comment_ActivityService {

	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.save("Comment_ActivityMapper.save", pd);
	}
	
	public List<PageData> list(Page page) throws Exception{
		return (List<PageData>) dao.findForList("Comment_ActivityMapper.datalistPage", page);
	}
	
	public List<PageData> findList(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("Comment_ActivityMapper.findList", pd);
	}
	
	public PageData findById(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Comment_ActivityMapper.findById", pd);
	}
	
	public void editViews(PageData pd) throws Exception{
		dao.update("Comment_ActivityMapper.editViews", pd);
	}
	
	public void deleteAll(PageData pd) throws Exception{
		dao.delete("Comment_ActivityMapper.delete",pd);
	}
	
	public void delete(PageData pd) throws Exception{
		dao.delete("Comment_ActivityMapper.delete", pd);
	}
	
	public void deletePid(PageData pd) throws Exception{
		dao.delete("Comment_ActivityMapper.deletePid", pd);
	}

	public List<PageData> findList1(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("Comment_ActivityMapper.findList1", pd);
	}
	
	public PageData findByPid(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Comment_ActivityMapper.findByPid", pd);
	}
}
