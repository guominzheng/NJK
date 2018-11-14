package com.fh.service.system.wenda;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service
@Resource(name="wenDaService")
public class WenDaService {
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.save("WendaMapper.save", pd);
	}
	
	public List<PageData> list(Page page) throws Exception{
		return (List<PageData>) dao.findForList("WendaMapper.datalistPage", page);
	}
	
	public PageData findById(PageData pd) throws Exception{
		return (PageData) dao.findForObject("WendaMapper.findById", pd);
	}
	
	public void edit(PageData pd) throws Exception{
		dao.update("WendaMapper.edit", pd);
	}
	
	public void delete(PageData pd) throws Exception{
		dao.delete("WendaMapper.delete", pd);
	}
	
	public List<PageData> findList(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("WendaMapper.findList", pd);
	}
	
	public List<PageData> findSuiJi(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("WendaMapper.findSuiJi", pd);
	}
	
	public List<PageData> findSuiJis(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("WendaMapper.findSuiJis", pd);
	}
}
