package com.fh.service.system.cartnest;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service
@Resource(name="cartNestService")
public class CartNestService {
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.save("CartNestMapper.save", pd);
	}
	
	public List<PageData> datalistPage(Page page) throws Exception{
		return (List<PageData>) dao.findForList("CartNestMapper.datalistPage", page);
	}
	
	public List<PageData> dataslistPage(Page page) throws Exception{
		return (List<PageData>) dao.findForList("CartNestMapper.dataslistPage", page);
	}
	
	public PageData findById(PageData pd) throws Exception{
		return (PageData) dao.findForObject("CartNestMapper.findById", pd);
	}
	
	public void delete(PageData pd) throws Exception{
		dao.delete("CartNestMapper.delete", pd);
	}
	
	public void edit(PageData pd) throws Exception{
		dao.update("CartNestMapper.edit", pd);
	}
	
	public void editViews(PageData pd) throws Exception{
		dao.update("CartNestMapper.editViews", pd);
	}
	
	public void editViewss(PageData pd) throws Exception{
		dao.update("CartNestMapper.editViewss", pd);
	}
	
	public void editHuiFu(PageData pd) throws Exception{
		dao.update("CartNestMapper.editHuiFu", pd);
	}
	
	public void editDates(PageData pd) throws Exception{
		dao.update("CartNestMapper.editDates", pd);
	}
	
	public PageData findClick(PageData pd) throws Exception{
		return (PageData) dao.findForObject("CartNestMapper.findClick", pd);
	}
	
	public void editCick(PageData pd) throws Exception{
		dao.update("CartNestMapper.editCick", pd);
	}
	
	public List<PageData> userlistPage(Page page) throws Exception{
		return (List<PageData>) dao.findForList("CartNestMapper.userlistPage", page);
	}
}