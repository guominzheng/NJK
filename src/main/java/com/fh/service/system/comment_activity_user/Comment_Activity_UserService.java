package com.fh.service.system.comment_activity_user;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service
@Resource(name="comment_Activity_UserService")
public class Comment_Activity_UserService {
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public void save(PageData pd) throws Exception {
		dao.save("Comment_Activity_UserMapper.save", pd);
	}

	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>) dao.findForList("Comment_Activity_UserMapper.datalistPage", page);
	}

	public List<PageData> findList(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("Comment_Activity_UserMapper.findList", pd);
	}
	
	public PageData findById(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Comment_Activity_UserMapper.findById", pd);
	}
	
	public void editViews(PageData pd) throws Exception{
		dao.update("Comment_Activity_UserMapper.editViews", pd);
	}
	
	public List<PageData> findList1(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("Comment_Activity_UserMapper.findList1", pd);
	}
	
	public void deletePid(PageData pd) throws Exception{
		dao.delete("Comment_Activity_UserMapper.deletePid",pd);
	}
	
	public void delete(PageData pd) throws Exception{
		dao.delete("Comment_Activity_UserMapper.delete", pd);
	}
	
	public PageData findCount(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Comment_Activity_UserMapper.findCount", pd);
	}
}
