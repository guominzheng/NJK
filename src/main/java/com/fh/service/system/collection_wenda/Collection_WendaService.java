package com.fh.service.system.collection_wenda;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service
@Resource(name="collection_WendaService")
public class Collection_WendaService {
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.save("Collection_WendaMapper.save", pd);
	}
	
	public List<PageData> list(Page page) throws Exception{
		return (List<PageData>) dao.findForList("Collection_WendaMapper.datalistPage", page);
	}
	
	public PageData findById(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Collection_WendaMapper.findById", pd);
	}
	
	public void delete(String a[]) throws Exception{
		dao.delete("Collection_WendaMapper.delete", a);
	}
	
	public void deleteAll(PageData pd) throws Exception{
		dao.delete("Collection_WendaMapper.deleteAll", pd);
	}
	
	public void deletes(PageData pd) throws Exception{
		dao.delete("Collection_WendaMapper.deletes", pd);
	}
	
	public List<PageData> findCollectionlistPage(Page page) throws Exception{
		return (List<PageData>) dao.findForList("Collection_WendaMapper.findCollectionlistPage", page);
	}
}
