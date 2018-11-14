package com.fh.service.system.comment_leaving_message;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service
@Resource(name="comment_LeavingMessageService")
public class Comment_LeavingMessageService {
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.save("Comment_LeavingMessageMapper.save", pd);
	}
	
	public List<PageData> datalistPage(Page page) throws Exception{
		return (List<PageData>) dao.findForList("Comment_LeavingMessageMapper.datalistPage", page);
	}
	
	public void editHStatus(PageData pd) throws Exception{
		dao.update("Comment_LeavingMessageMapper.editHStatus", pd);
	}
	
	public List<PageData> dataslistPage(Page page) throws Exception{
		return (List<PageData>) dao.findForList("Comment_LeavingMessageMapper.dataslistPage", page);
	}
	
	public List<PageData> findBList(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("Comment_LeavingMessageMapper.findBList", pd);
	}
	
	public List<PageData> findLists(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("Comment_LeavingMessageMapper.findLists", pd);
	}
	
	public List<PageData> datasslistPage(Page page) throws Exception{
		return (List<PageData>) dao.findForList("Comment_LeavingMessageMapper.datasslistPage", page);
	}
	public PageData findById(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Comment_LeavingMessageMapper.findById", pd);
	}
	public void editHUIFUSjia(PageData pd) throws Exception{
		dao.update("Comment_LeavingMessageMapper.editHUIFUSjia", pd);
	}

	public void editViewsJia(PageData pd) throws Exception{
		dao.update("Comment_LeavingMessageMapper.editViewsJia", pd);
	}
	public void editViewsJian(PageData pd) throws Exception{
		dao.update("Comment_LeavingMessageMapper.editViewsJian", pd);
	}
}
