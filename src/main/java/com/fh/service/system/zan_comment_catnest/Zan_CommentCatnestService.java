package com.fh.service.system.zan_comment_catnest;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;

@Service
@Resource(name="zan_CommentCatnestService")
public class Zan_CommentCatnestService {
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.save("Zan_Comment_CatnestMapper.save", pd);
	}
	
	public PageData findById(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Zan_Comment_CatnestMapper.findById", pd);
	}
	
	public PageData findByIds(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Zan_Comment_CatnestMapper.findByIds", pd);
	}
	
	public PageData findById1(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Zan_Comment_CatnestMapper.findById1", pd);
	}
	
	public void delete(PageData pd) throws Exception{
		dao.delete("Zan_Comment_CatnestMapper.delete", pd);
	}
	
	public PageData findcount(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Zan_Comment_CatnestMapper.findcount", pd);
	}
}
