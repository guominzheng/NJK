package com.fh.service.system.comment_research;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service
@Resource(name="comment_ResearchService")
public class Comment_ResearchService {

	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.save("Comment_ResearchMapper.save", pd);
	}
	
	public List<PageData> datalistPage(Page page) throws Exception{
		return (List<PageData>) dao.findForList("Comment_ResearchMapper.findlists", page);
	}
	
	public List<PageData> findList(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("Comment_ResearchMapper.findList", pd);
	}
	
	public PageData findById(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Comment_ResearchMapper.findById", pd);
	}
	
	
	public void editView(PageData pd) throws Exception{
		dao.update("Comment_ResearchMapper.editView", pd);
	}
	
	public void delete(PageData pd) throws Exception{
		dao.delete("Comment_ResearchMapper.delete", pd);
	}
	
	public PageData findCount(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Comment_ResearchMapper.findCount", pd);
	}
	
	public PageData findCount1(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Comment_ResearchMapper.findCount1", pd);
	}
	
	public void editJiaViewss(PageData pd) throws Exception{
		dao.update("Comment_ResearchMapper.editJiaViewss", pd);
	}
	
	public void editJianViewss(PageData pd) throws Exception{
		dao.update("Comment_ResearchMapper.editJianViewss", pd);
	}
	
	public List<PageData> findList1(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("Comment_ResearchMapper.findList1", pd);
	}

	public List<PageData> findCommentByResId(Page page) throws Exception{
		return (List<PageData>) dao.findForList("Comment_ResearchMapper.findCommentByResIdlistPage", page);
	}

	public void deleteAll(String p[]) throws Exception{
		dao.delete("Comment_ResearchMapper.deleteAll", p);
	}
	public List<PageData> PDatalistPage(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("Comment_ResearchMapper.PData", pd);
	}
	public List<PageData> findCommentsByResId(Page page) throws Exception{
		return (List<PageData>) dao.findForList("Comment_ResearchMapper.findCommentsByResIdlistPage", page);
	}
}
