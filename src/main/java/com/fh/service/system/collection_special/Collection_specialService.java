package com.fh.service.system.collection_special;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service
@Resource(name="collection_specialService")
public class Collection_specialService {

	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.save("Collection_specialMapper.save", pd);
	}
	
	public List<PageData> findList(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("Collection_specialMapper.findList", pd);
	}
	
	
	public void delete(PageData pd) throws Exception{
		dao.delete("Collection_specialMapper.delete", pd);
	}
	
	public List<PageData> findCollentionSpecial(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("Collection_specialMapper.findCollentionSpecial", pd);
	}
	
	public PageData findById(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Collection_specialMapper.findById", pd);
	}
	
	public PageData findById1(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Collection_specialMapper.findById1", pd);
	}
	
	public List<PageData> datalistPage(Page page) throws Exception{
		return (List<PageData>) dao.findForList("Collection_specialMapper.datalistPage", page);
	}
	
	public void editStatus1(PageData pd) throws Exception{
		dao.update("Collection_specialMapper.editStatus1", pd);
	}
	
	public void editStatus2(PageData pd) throws Exception{
		dao.update("Collection_specialMapper.editStatus2", pd);
	}
	
	public PageData findListStatus(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Collection_specialMapper.findListStatus", pd);
	}
}
