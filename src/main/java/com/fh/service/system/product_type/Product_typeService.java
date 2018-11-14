package com.fh.service.system.product_type;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service
@Resource(name="product_typeService")
public class Product_typeService {

	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.save("Product_typeMapper.save", pd);
	}
	
	public List<PageData> list(Page page) throws Exception{
		return (List<PageData>) dao.findForList("Product_typeMapper.datalistPage", page);
	}
	
	public List<PageData> findList(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("Product_typeMapper.findList", pd);
	}
	
	public PageData findById(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Product_typeMapper.findById", pd);
	}
	
	public void edit(PageData pd) throws Exception{
		dao.update("Product_typeMapper.edit", pd);
	}
	
	public void delete(PageData pd) throws Exception{
		dao.delete("Product_typeMapper.delete", pd);
	}
	
	public void deleteAll(String s[]) throws Exception{
		dao.delete("Product_typeMapper.deleteAll", s);
	}
}
