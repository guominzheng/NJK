package com.fh.service.system.comment_tiwen;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service
@Resource(name="comment_TiwenService")
public class Comment_TiwenService {
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.save("Comment_TiWenMapper.save", pd);
	}
	
	public List<PageData> findList(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("Comment_TiWenMapper.findList", pd);
	}
	
	public List<PageData> findList1(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("Comment_TiWenMapper.findList1", pd);
	}
	
	public List<PageData> datalistPage(Page page) throws Exception{
		return (List<PageData>) dao.findForList("Comment_TiWenMapper.datalistPage", page);
	}
	
	public PageData findById(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Comment_TiWenMapper.findById", pd);
	}
	public PageData findCount(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Comment_TiWenMapper.findCount", pd);
	}
	public void editView(PageData pd) throws Exception{
		dao.update("Comment_TiWenMapper.editView", pd);
	}
	
	public void delete(PageData pd) throws Exception{
		dao.delete("Comment_TiWenMapper.delete", pd);
	}
	
	public void deleteA(PageData pd) throws Exception{
		dao.delete("Comment_TiWenMapper.deleteA", pd);
	}
	
	public List<PageData> findListTo(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("Comment_TiWenMapper.findListTo", pd);
	}
}
