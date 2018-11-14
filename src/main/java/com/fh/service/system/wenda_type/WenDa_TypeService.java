package com.fh.service.system.wenda_type;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service
@Resource(name="wenDa_TypeService")
public class WenDa_TypeService {
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.save("WenDa_TypeMapper.save", pd);
	}
	
	public List<PageData> list(Page page) throws Exception{
		return (List<PageData>) dao.findForList("WenDa_TypeMapper.datalistPage", page);
	}
	
	public PageData findById(PageData pd) throws Exception{
		return (PageData) dao.findForObject("WenDa_TypeMapper.findById", pd);
	}
	
	public void edit(PageData pd) throws Exception{
		dao.update("WenDa_TypeMapper.edit", pd);
	}
	
	public void delete(PageData pd) throws Exception{
		dao.delete("WenDa_TypeMapper.delete", pd);
	}
}
