package com.fh.service.system.price;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service
@Resource(name="priceService")
public class PriceService {

	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.save("PriceMapper.save", pd);
	}
	
	public void edit(PageData pd) throws Exception{
		dao.update("PriceMapper.edit", pd);
	}
	
	public List<PageData> list(Page page) throws Exception{
		return (List<PageData>) dao.findForList("PriceMapper.datalistPage", page);
	}
	
	public PageData findById(PageData pd) throws Exception{
		return (PageData) dao.findForObject("PriceMapper.findById", pd);
	}
	
	public void delete(PageData pd) throws Exception{
		dao.delete("PriceMapper.delete", pd);
	}
	
	public void deleteAll(String s[]) throws Exception{
		dao.delete("PriceMapper.deleteAll", s);
	}
	
	public List<PageData> findList(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("PriceMapper.findList", pd);
	}
}
