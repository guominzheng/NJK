package com.fh.service.system.comment_post;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service
@Resource(name = "comment_PostService")
public class Comment_PostService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;

	public void save(PageData pd) throws Exception {
		dao.save("Comment_PostMapper.save", pd);
	}

	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>) dao.findForList("Comment_PostMapper.datalistPage", page);
	}

	public List<PageData> findList(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("Comment_PostMapper.findList", pd);
	}
	
	public PageData findById(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Comment_PostMapper.findById", pd);
	}
	
	public void editView(PageData pd) throws Exception{
		dao.update("Comment_PostMapper.editView", pd);
	}
	
	public List<PageData> findList1(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("Comment_PostMapper.findList1", pd);
	}
	
	public List<PageData> data1listPage(Page page) throws Exception{
		return (List<PageData>) dao.findForList("Comment_PostMapper.data1listPage", page);
	}
	
	public List<PageData> data12listPage(Page page) throws Exception{
		return (List<PageData>) dao.findForList("Comment_PostMapper.data12listPage", page);
	}
	
	public void delete(PageData pd) throws Exception{
		dao.delete("Comment_PostMapper.delete", pd);
	}
	
	public void deleteAll(PageData pd) throws Exception{
		dao.delete("Comment_PostMapper.deleteAll", pd);
	}
	
	public void deleteAll1(PageData pd) throws Exception{
		dao.delete("Comment_PostMapper.deleteAll1", pd);
	}
	
	public PageData findById111(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Comment_PostMapper.findById111", pd);
	}
	
	public PageData findCount(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Comment_PostMapper.findCount", pd);
	}
	
	public PageData findCount1(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Comment_PostMapper.findCount1", pd);
	}
}
