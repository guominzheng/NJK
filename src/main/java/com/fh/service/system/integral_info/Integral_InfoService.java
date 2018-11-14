package com.fh.service.system.integral_info;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service
@Resource(name="integral_InfoService")
public class Integral_InfoService {
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.save("Integral_InfoMapper.save", pd);
	}
	
	public List<PageData> datalistPage(Page page) throws Exception{
		return (List<PageData>) dao.findForList("Integral_InfoMapper.datalistPage", page);
	}
	
	public void edit(PageData pd) throws Exception{
		dao.update("Integral_InfoMapper.edit", pd);
	}
	
	public PageData findById(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Integral_InfoMapper.findById", pd);
	}
	
	public void delete(PageData pd) throws Exception{
		dao.delete("Integral_InfoMapper.delete", pd);
	}
	
	public List<PageData> findList(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("Integral_InfoMapper.findList", pd);
	}
}
