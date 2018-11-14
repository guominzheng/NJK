package com.fh.service.system.comment_mail_img;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;

@Service
@Resource(name="comment_MailImgService")
public class Comment_MailImgService {
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.save("Comment_MailImgMapper.save", pd);
	}
	
	public List<PageData> findList(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("Comment_MailImgMapper.findList", pd);
	}
	
	public void delete(PageData pd) throws Exception{
		dao.delete("Comment_MailImgMapper.delete", pd);
	}
}
