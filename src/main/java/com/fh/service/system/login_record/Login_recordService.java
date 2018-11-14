package com.fh.service.system.login_record;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service
@Resource(name="login_record")
public class Login_recordService {

	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	
	public void save(PageData pd) throws Exception{
		dao.save("Login_recordMapper.save", pd);
	}
	
	
	public List<PageData> list(Page page) throws Exception{
		return (List<PageData>) dao.findForList("Login_recordMapper.datalistPage", page);
	}
	
	public List<PageData> userlistPage(Page page) throws Exception{
		return (List<PageData>) dao.findForList("Login_recordMapper.userlistPage", page);
	}
	
	public List<PageData> renshulistPage(Page page) throws Exception{
		return (List<PageData>) dao.findForList("Login_recordMapper.renshulistPage", page);
	}
	
	public PageData findListDate(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Login_recordMapper.findListDate", pd);
	}
	
	public PageData findTDate(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Login_recordMapper.findTDate", pd);
	}
	
	public List<PageData> findYDuiBi(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("Login_recordMapper.findYDuiBi", pd);
	}
}
