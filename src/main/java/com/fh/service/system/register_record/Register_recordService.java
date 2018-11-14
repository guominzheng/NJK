package com.fh.service.system.register_record;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service
@Resource(name="register_recordService")
public class Register_recordService {
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.save("Register_recordMapper.save", pd);
	}
	
	public List<PageData> list(Page page) throws Exception{
		return (List<PageData>) dao.findForList("Register_recordMapper.datalistPage", page);
	}
	
	public List<PageData> findYDuiBi(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("Register_recordMapper.findYDuiBi", pd);
	}
	
	
	public PageData findListDate(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Register_recordMapper.findListDate", pd);
	}
	
	public PageData findTDate(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Register_recordMapper.findTDate", pd);
	}
}
