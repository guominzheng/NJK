package com.fh.service.system.comment_mail;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service
@Resource(name="comment_MailService")
public class Comment_MailService {
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.save("Comment_MailMapper.save", pd);
	}
	
	public List<PageData> datalistPage(Page page) throws Exception{
		return (List<PageData>) dao.findForList("Comment_MailMapper.datalistPage", page);
	}
	
	public List<PageData> findList(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("Comment_MailMapper.findList", pd);
	}
	
	public PageData findCount(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Comment_MailMapper.findCount", pd);
	}
	
	public PageData findCount1(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Comment_MailMapper.findCount1", pd);
	}
	
	public void editJiaViewss(PageData pd) throws Exception{
		dao.update("Comment_MailMapper.editJiaViewss", pd);
	}
	
	public void editJianViewss(PageData pd) throws Exception{
		dao.update("Comment_MailMapper.editJianViewss", pd);
	}
	
	public PageData findById(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Comment_MailMapper.findById", pd);
	}
}
