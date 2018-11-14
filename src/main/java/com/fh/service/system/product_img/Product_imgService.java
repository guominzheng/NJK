package com.fh.service.system.product_img;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;

@Service
@Resource(name="product_imgService")
public class Product_imgService {
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.save("Product_imgMapper.save", pd);
	}
	
	public List<PageData> findList(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("Product_imgMapper.findList", pd);
	}
	
	public void delete(PageData pd) throws Exception{
		dao.delete("Product_imgMapper.delete", pd);
	}
	
	public void deleteAll(String q[]) throws Exception{
		dao.delete("Product_imgMapper.deleteAll", q);
	}
}
