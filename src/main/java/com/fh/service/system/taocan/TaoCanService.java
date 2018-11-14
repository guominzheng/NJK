package com.fh.service.system.taocan;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service
@Resource(name="taoCanService")
public class TaoCanService {
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.save("TaoCanMapper.save", pd);
	}
	
	public List<PageData> findList(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("TaoCanMapper.findList", pd);
	}
	
	public List<PageData> datalistPage(Page page) throws Exception{
		return (List<PageData>) dao.findForList("TaoCanMapper.datalistPage", page);
	}
	
	public PageData findById(PageData pd) throws Exception{
		return (PageData) dao.findForObject("TaoCanMapper.findById",pd);
	}
	
	public void edit(PageData pd) throws Exception{
		dao.update("TaoCanMapper.edit",pd);
	}
	
	public void delete(PageData pd) throws Exception{
		dao.delete("TaoCanMapper.delete", pd);
	}
	
	public List<PageData> findLists(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("TaoCanMapper.findLists", pd);
	}
}
