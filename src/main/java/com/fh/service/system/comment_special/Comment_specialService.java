package com.fh.service.system.comment_special;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service
@Resource(name="comment_specialService")
public class Comment_specialService {

	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public void save(PageData pd) throws Exception {
		dao.save("Comment_specialMapper.save", pd);
	}

	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>) dao.findForList("Comment_specialMapper.datalistPage", page);
	}

	public List<PageData> findList(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("Comment_specialMapper.findList", pd);
	}
	
	public PageData findById(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Comment_specialMapper.findById", pd);
	}
	
	public void editView(PageData pd) throws Exception{
		dao.update("Comment_specialMapper.editView", pd);
	}
	
	public List<PageData> findList1(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("Comment_specialMapper.findList1", pd);
	}
	
	public void delete(PageData pd) throws Exception{
		dao.delete("Comment_specialMapper.delete", pd);
	}
	
	public void deleteA(PageData pd) throws Exception{
		dao.delete("Comment_specialMapper.deleteA", pd);
	}
}
