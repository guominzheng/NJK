package com.fh.service.system.cart;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;
@Service
@Resource(name="cartService")
public class CartService {
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.save("CartMapper.save", pd);
	}
	
	public void edit(PageData pd) throws Exception{
		dao.update("CartMapper.edit", pd);
	}
	
	public List<PageData> findList(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("CartMapper.findList", pd);
	}
	
	public void delete(PageData pd) throws Exception{
		dao.delete("CartMapper.delete", pd);
	}
	
	public void deletePId(PageData pd) throws Exception{
		dao.delete("CartMapper.deletePId", pd);
	}
	
	public void deleteAll(PageData pd) throws Exception{
		dao.delete("CartMapper.deleteAll", pd);
	}
	
	public PageData findCount(PageData pd) throws Exception{
		return (PageData) dao.findForObject("CartMapper.findCount", pd);
	}
	
	public PageData findById(PageData pd) throws Exception{
		return (PageData) dao.findForObject("CartMapper.findById", pd);
	}
	
	public void editStatus(PageData pd) throws Exception{
		dao.update("CartMapper.editStatus", pd);
	}
	
	public List<PageData> findListStatus(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("CartMapper.findListStatus", pd);
	}
	
	public void editStatusAll(PageData pd) throws Exception{
		dao.update("CartMapper.editStatusAll", pd);
	}
	
	public PageData findRemarkId(PageData pd) throws Exception{
		return (PageData) dao.findForObject("CartMapper.findRemarkId", pd);
	}
	
	public void deleteStatus(PageData pd) throws Exception{
		dao.delete("CartMapper.delete", pd);
	}
	
	public void delete1(PageData pd) throws Exception{
		dao.delete("CartMapper.delete1", pd);
	}
	public void delUserCart(PageData pd) throws Exception{
		dao.delete("CartMapper.delUserCart", pd);
	}
}
