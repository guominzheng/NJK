package com.fh.service.system.comment_catnest;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service
@Resource(name="comment_CatnestService")
public class Comment_CatnestService {

	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.save("Comment_CatnestMapper.save", pd);
	}
	
	public List<PageData> datalistPage(Page page) throws Exception{
		return (List<PageData>) dao.findForList("Comment_CatnestMapper.datalistPage", page);
	}
	
	public List<PageData> findList(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("Comment_CatnestMapper.findList", pd);
	}
	
	public PageData findById(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Comment_CatnestMapper.findById", pd);
	}
	
	public void delete(PageData pd) throws Exception{
		dao.delete("Comment_CatnestMapper.delete", pd);
	}
	
	public PageData findCount(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Comment_CatnestMapper.findCount", pd);
	}
	
	public PageData findCount1(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Comment_CatnestMapper.findCount1", pd);
	}
	
	public void editJiaViews(PageData pd) throws Exception{
		dao.update("Comment_CatnestMapper.editJiaViews", pd);
	}
	
	public List<PageData> findList1(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("Comment_CatnestMapper.findList1", pd);
	}
	
	public void editViews(PageData pd) throws Exception{
		dao.update("Comment_CatnestMapper.editViews", pd);
	}
	
	public void editViewss(PageData pd) throws Exception{
		dao.update("Comment_CatnestMapper.editViewss", pd);
	}
	
	public List<PageData> findLists(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("Comment_CatnestMapper.findLists", pd);
	}
}
