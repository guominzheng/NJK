package com.fh.service.system.error_wenda;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service
@Resource(name="error_WendaService")
public class Error_WendaService {
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.save("Error_WendaMapper.save", pd);
	}
	
	public List<PageData> list(Page page) throws Exception{
		return (List<PageData>) dao.findForList("Error_WendaMapper.datalistPage", page);
	}
	
	public PageData findById(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Error_WendaMapper.findById", pd);
	}
	
	public void delete(PageData pd) throws Exception{
		dao.delete("Error_WendaMapper.delete", pd);
	}
	
	public void deleteAll(List<PageData> list,String USER_ID) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();  
	    map.put("list1111", list);  
	    map.put("USER_ID", USER_ID);
		dao.delete("Error_WendaMapper.deleteAll", map);
	}
	
	public void editAll(List<PageData> list,String USER_ID) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();  
	    map.put("list1111", list);  
	    map.put("USER_ID", USER_ID);
	    dao.update("Error_WendaMapper.editAll", map);
	}
	
	public void saveAll(List<PageData> list) throws Exception{
		dao.batchSave("Error_WendaMapper.saveAll", list);
	}
	
	public void saveAlls(List<PageData> list) throws Exception{
		dao.batchSave("Error_WendaMapper.saveAlls", list);
	}
	
	public void edita(String a[]) throws Exception{
		dao.update("Error_WendaMapper.edita", a);
	}
	
	public void editaa(PageData pd) throws Exception{
		dao.update("Error_WendaMapper.editaa", pd);
	}
	
	public void editUserId(PageData pd) throws Exception{
		dao.update("Error_WendaMapper.editUserId", pd);
	}
	
	public List<PageData> dataCuoTilistPage(Page page) throws Exception{
		return (List<PageData>) dao.findForList("Error_WendaMapper.dataCuoTilistPage", page);
	}
	
	public List<PageData> findZuoTilistPage(Page page) throws Exception{
		return (List<PageData>) dao.findForList("Error_WendaMapper.findZuoTilistPage", page);
	}
	
	public PageData findZhouZuoTi(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Error_WendaMapper.findZhouZuoTi", pd);
	}
	
	public PageData findNianZuoTi(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Error_WendaMapper.findNianZuoTi", pd);
	}
	
}
