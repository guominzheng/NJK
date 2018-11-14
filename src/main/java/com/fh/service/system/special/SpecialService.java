package com.fh.service.system.special;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service
@Resource(name="specialService")
public class SpecialService {

	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.save("SpecialMapper.save", pd);
	}
	
	public void edit(PageData pd) throws Exception{
		dao.update("SpecialMapper.edit", pd);
	}
	
	public List<PageData> list(Page page) throws Exception{
		return (List<PageData>) dao.findForList("SpecialMapper.datalistPage", page);
	}
	
	public PageData findById(PageData pd) throws Exception{
		return (PageData) dao.findForObject("SpecialMapper.findById", pd);
	}
	
	public void delete(PageData pd) throws Exception{
		dao.delete("SpecialMapper.delete", pd);
	}
	
	public void deleteAll(String s[]) throws Exception{
		dao.delete("SpecialMapper.deleteAll", s);
	}
	
	public List<PageData> findList(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("SpecialMapper.findList", pd);
	}
	
}
