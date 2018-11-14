package com.fh.service.system.product;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service
@RequestMapping(value="productService")
public class ProductService {
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public List<PageData> list(Page page) throws Exception{
		return (List<PageData>) dao.findForList("ProductMapper.datalistPage", page);
	}
	
	public List<PageData> list2(Page page) throws Exception{
		return (List<PageData>) dao.findForList("ProductMapper.dataslistPage", page);
	}
	
	public PageData findById(PageData pd) throws Exception{
		return (PageData) dao.findForObject("ProductMapper.findById", pd);
	}
	
	public void save(PageData pd) throws Exception{
		dao.save("ProductMapper.save", pd);
	}
	
	public void edit(PageData pd) throws Exception{
		dao.update("ProductMapper.edit", pd);
	}
	
	public void delete(PageData pd) throws Exception{
		dao.delete("ProductMapper.delete", pd);
	}
	
	public void deleteAll(String PRODUCT[]) throws Exception{
		dao.delete("ProductMapper.deleteAll", PRODUCT);
	}
	
	public List<PageData> findList(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("ProductMapper.findList", pd);
	}
	
	public List<PageData> findListName(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("ProductMapper.findListName", pd);
	}
	
	
	public PageData findRimg(PageData pd) throws Exception{
		return (PageData) dao.findForObject("ProductMapper.findRimg", pd);
	}

	public void edtiHuoDong(PageData pd) throws Exception{
		dao.update("ProductMapper.edtiHuoDong", pd);
	}


}
