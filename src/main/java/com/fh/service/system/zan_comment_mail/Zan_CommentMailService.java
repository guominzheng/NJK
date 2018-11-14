package com.fh.service.system.zan_comment_mail;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;

@Service
@Resource(name="zan_CommentMailService")
public class Zan_CommentMailService {
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.save("Zan_Comment_MailMapper.save", pd);
	}
	
	public PageData findById(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Zan_Comment_MailMapper.findById", pd);
	}
	
	public PageData findByIds(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Zan_Comment_MailMapper.findByIds", pd);
	}
	
	public PageData findById1(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Zan_Comment_MailMapper.findById1", pd);
	}
	
	public void delete(PageData pd) throws Exception{
		dao.delete("Zan_Comment_MailMapper.delete", pd);
	}
	
	public PageData findcount(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Zan_Comment_MailMapper.findcount", pd);
	}
}
