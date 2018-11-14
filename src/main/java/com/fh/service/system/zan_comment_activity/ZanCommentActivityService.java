package com.fh.service.system.zan_comment_activity;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;

@Service
@Resource(name="zanCommentActivityService")
public class ZanCommentActivityService {

	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.save("Zan_CommentActivityMapper.save", pd);
	}
	
	public PageData findById(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Zan_CommentActivityMapper.findById", pd);
	}
	
	public PageData findById1(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Zan_CommentActivityMapper.findById1", pd);
	}
	
	public void delete(PageData pd) throws Exception{
		dao.delete("Zan_CommentActivityMapper.delete", pd);
	}
	
	public PageData findCount(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Zan_CommentActivityMapper.findCount", pd);
	}
	
	public PageData findCounts(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Zan_CommentActivityMapper.findCounts", pd);
	}
}
