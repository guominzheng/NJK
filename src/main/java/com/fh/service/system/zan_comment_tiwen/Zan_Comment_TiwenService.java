package com.fh.service.system.zan_comment_tiwen;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;

@Service
@Resource(name="zan_Comment_TiwenService")
public class Zan_Comment_TiwenService {

	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.save("Zan_Comment_TiwenMapper.save", pd);
	}
	
	public PageData findById(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Zan_Comment_TiwenMapper.findById", pd);
	}
	
	public void delete(PageData pd) throws Exception{
		dao.delete("Zan_Comment_TiwenMapper.delete", pd);
	}
	
	public PageData findById1(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Zan_Comment_TiwenMapper.findById1", pd);
	}
	
	public PageData findCount(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Zan_Comment_TiwenMapper.findCount", pd);
	}
	
}
