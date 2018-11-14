package com.fh.service.system.zan_comment_post;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;

@Service
@Resource(name="zan_CommentPostService")
public class Zan_CommentPostService {

	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.save("Zan_CommentPostMapper.save", pd);
	}
	
	public PageData findById(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Zan_CommentPostMapper.findById", pd);
	}
	
	public PageData findById1(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Zan_CommentPostMapper.findById1", pd);
	}
	
	public void delete(PageData pd) throws Exception{
		dao.delete("Zan_CommentPostMapper.delete", pd);
	}
	
	public PageData findcount(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Zan_CommentPostMapper.findcount", pd);
	}
}
