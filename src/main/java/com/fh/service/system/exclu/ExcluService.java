package com.fh.service.system.exclu;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service
@Resource(name="excluService")
public class ExcluService {
 
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public List<PageData> list(Page page) throws Exception{
		return (List<PageData>) dao.findForList("ExcluMapper.datalistPage", page);
	}
	
	public void save(PageData pd) throws Exception{
		dao.save("ExcluMapper.save", pd);
	}
	
	public void delete(PageData pd) throws Exception{
		dao.delete("ExcluMapper.delete",pd);
	}
	
	public PageData findById(PageData pd) throws Exception{
		return (PageData) dao.findForObject("ExcluMapper.findById", pd);
	}
	
	public void edit(PageData pd) throws Exception{
		dao.update("ExcluMapper.edit", pd);
	}
	
	public List<PageData> findList(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("ExcluMapper.findList", pd);
	}



	public List<PageData> getTCount(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("ExcluMapper.getTCount", pd);
	}
	public List<PageData> getZCount(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("ExcluMapper.getZCount", pd);
	}
	public List<PageData> getYCount(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("ExcluMapper.getYCount", pd);
	}
}
