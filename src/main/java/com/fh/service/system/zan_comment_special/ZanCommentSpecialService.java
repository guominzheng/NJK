package com.fh.service.system.zan_comment_special;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;

@Service
@Resource(name="zanCommentSpecialService")
public class ZanCommentSpecialService {
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.save("Zan_CommentSpecialMapper.save", pd);
	}
	
	public PageData findById(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Zan_CommentSpecialMapper.findById", pd);
	}
	
	public void delete(PageData pd) throws Exception{
		dao.delete("Zan_CommentSpecialMapper.delete", pd);
	}
	
	public PageData findById1(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Zan_CommentSpecialMapper.findById1", pd);
	}
	
	public PageData findCount(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Zan_CommentSpecialMapper.findCount", pd);
	}
}
