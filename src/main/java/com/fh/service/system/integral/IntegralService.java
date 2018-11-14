package com.fh.service.system.integral;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service
@Resource(name="integralService")
public class IntegralService {
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.save("IntegralMapper.save", pd);
	}
	
	public List<PageData> datalistPage(Page page) throws Exception{
		return (List<PageData>) dao.findForList("IntegralMapper.datalistPage", page);
	}
	
	public PageData findById(PageData pd) throws Exception{
		return (PageData) dao.findForObject("IntegralMapper.findById", pd);
	}
	
	public void edit(PageData pd) throws Exception{
		dao.delete("IntegralMapper.edit", pd);
	}
	
	public void delete(PageData pd) throws Exception{
		dao.delete("IntegralMapper.delete", pd);
	}

}
