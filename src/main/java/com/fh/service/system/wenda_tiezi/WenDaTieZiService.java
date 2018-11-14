package com.fh.service.system.wenda_tiezi;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service
@Resource(name="wenDaTieZiService")
public class WenDaTieZiService {
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.save("WenDaTieZiMapper.save", pd);
	}
	
	public List<PageData> findList(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("WenDaTieZiMapper.findList", pd);
	}
	
	public PageData findById(PageData pd) throws Exception{
		return (PageData) dao.findForObject("WenDaTieZiMapper.findById", pd);
	}
	
	public void edit(PageData pd) throws Exception{
		dao.update("WenDaTieZiMapper.edit", pd);
	}
	
	public List<PageData> list(Page page) throws Exception{
		return (List<PageData>) dao.findForList("WenDaTieZiMapper.datalistPage", page);
	}
	
	public void editShuZi(PageData pd) throws Exception{
		dao.update("WenDaTieZiMapper.editShuZi", pd);
	}
	
	public void editGStatus(PageData pd) throws Exception{
		dao.update("WenDaTieZiMapper.editGStatus", pd);
	}
	
	public void editGStatuss(PageData pd) throws Exception{
		dao.update("WenDaTieZiMapper.editGStatuss", pd);
	}
	
	public void delete(PageData pd) throws Exception{
		dao.delete("WenDaTieZiMapper.delete", pd);
	}
	
	public PageData findGuang(PageData pd) throws Exception{
		return (PageData) dao.findForObject("WenDaTieZiMapper.findGuang", pd);
	}
}
