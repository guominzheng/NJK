package com.fh.service.system.zan_comment_activity_user;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;

@Service
@Resource(name="zan_Comment_ActivityUserService")
public class Zan_Comment_ActivityUserService {
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.save("Zan_Comment_ActivityUserMapper.save", pd);
	}
	
	public PageData findById(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Zan_Comment_ActivityUserMapper.findById", pd);
	}
	
	public PageData findById1(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Zan_Comment_ActivityUserMapper.findById1", pd);
	}
	
	public void delete(PageData pd) throws Exception{
		dao.delete("Zan_Comment_ActivityUserMapper.delete", pd);
	}
	
	public PageData findCount(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Zan_Comment_ActivityUserMapper.findCount",pd);
	}
	
}
