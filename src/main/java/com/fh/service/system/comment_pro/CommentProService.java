package com.fh.service.system.comment_pro;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service
@Resource(name="commentProService")
public class CommentProService {
	@Resource(name="daoSupport")
	public DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.save("CommentProMapper.save", pd);
	}
	
	public List<PageData> list(Page page) throws Exception{
		return (List<PageData>) dao.findForList("CommentProMapper.datalistPage", page);
	}
	
	public void delete(PageData pd) throws Exception{
		dao.delete("CommentProMapper.delete", pd);
	}
	
	public void deleteAll(String p[]) throws Exception{
		dao.delete("CommentProMapper.deleteAll", p);
	}
	
	public void edit(PageData pd) throws Exception{
		dao.update("CommentProMapper.edit", pd);
	}

	public List<PageData> findcomproById(PageData pd) throws Exception{
		return (List<PageData>)dao.findForList("CommentProMapper.findcomproById", pd);
	}
}

