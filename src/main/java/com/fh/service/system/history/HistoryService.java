package com.fh.service.system.history;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service
@Resource(name="historyService")
public class HistoryService {
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.save("HistoryMapper.save", pd);
	}
	
	public List<PageData> list(Page page) throws Exception{
		return (List<PageData>) dao.findForList("HistoryMapper.datalistPage", page);
	}
	
	public void delete(PageData pd) throws Exception{
		dao.delete("HistoryMapper.delete", pd);
	}
	
	public void deletes(String a[]) throws Exception{
		dao.delete("HistoryMapper.delete", a);
	}
	
	public void deleteAll(PageData pd) throws Exception{
		dao.delete("HistoryMapper.deleteAll", pd);
	}
}
