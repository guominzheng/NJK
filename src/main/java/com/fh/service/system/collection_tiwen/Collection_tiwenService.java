package com.fh.service.system.collection_tiwen;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service
@Resource
public class Collection_tiwenService {
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.save("Collection_tiwenMapper.save", pd);
	}
	
	public void delete(PageData pd) throws Exception{
		dao.delete("Collection_tiwenMapper.delete", pd);
	}
	
	public PageData findCollection(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Collection_tiwenMapper.findCollection", pd);
	}
	
	public List<PageData> list(Page page) throws Exception{
		return (List<PageData>) dao.findForList("Collection_tiwenMapper.datalistPage", page);
	}
	
	public PageData findCollection1(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Collection_tiwenMapper.findCollection1", pd);
	}
	
	public List<PageData> userlistPage(Page page) throws Exception{
		return (List<PageData>) dao.findForList("Collection_tiwenMapper.userlistPage", page);
	}
	
	public PageData findListStatus(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Collection_tiwenMapper.findListStatus", pd);
	}
	
	public void editStatus1(PageData pd) throws Exception{
		dao.update("Collection_tiwenMapper.editStatus1", pd);
	}
	
	public void editStatus2(PageData pd) throws Exception{
		dao.update("Collection_tiwenMapper.editStatus2", pd);
	}

}
