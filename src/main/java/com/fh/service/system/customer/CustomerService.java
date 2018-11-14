package com.fh.service.system.customer;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service
@Resource(name="customerService")
public class CustomerService {
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.save("CustomerMapper.save", pd);
	}
	
	public List<PageData> list(Page page) throws Exception{
		return (List<PageData>) dao.findForList("CustomerMapper.datalistPage", page);
	}
	
	public void edit(PageData pd) throws Exception{
		dao.update("CustomerMapper.edit", pd);
	}
	
	public void delete(PageData pd) throws Exception{
		dao.delete("CustomerMapper.delete", pd);
	}
	
	public void deleteAll(String s[]) throws Exception{
		dao.delete("CustomerMapper.deleteAll", s);
	}
	
	public PageData findById(PageData pd) throws Exception{
		return (PageData) dao.findForObject("CustomerMapper.findById", pd);
	}
}
