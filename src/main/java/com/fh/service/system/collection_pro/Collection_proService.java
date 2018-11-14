package com.fh.service.system.collection_pro;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service
@Resource(name="collection_proService")
public class Collection_proService {

	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public PageData findCollection(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Collection_proMapper.findCollection", pd);
	}
	
	public void save(PageData pd) throws Exception{
		dao.save("Collection_proMapper.save", pd);
	}
	
	public void delete(PageData pd) throws Exception{
		dao.delete("Collection_proMapper.delete", pd);
	}
	
	public List<PageData> list(Page page) throws Exception{
		return (List<PageData>) dao.findForList("Collection_proMapper.datalistPage", page);
	}
}
