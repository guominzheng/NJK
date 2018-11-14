package com.fh.service.system.live_post;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service
@Resource(name="live_PostService")
public class Live_PostService {
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.save("Live_PostMapper.save", pd);
	}
	
	public List<PageData> list(Page page) throws Exception{
		return (List<PageData>) dao.findForList("Live_PostMapper.datalistPage", page);
	}
	
	public void edit(PageData pd) throws Exception{
		dao.update("Live_PostMapper.edit", pd);
	}
	
	public PageData findById(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Live_PostMapper.findById", pd);
	}
	
	public void delete(PageData pd) throws Exception{
		dao.delete("Live_PostMapper.delete", pd);
	}
	
	public void deleteAll(String a[]) throws Exception{
		dao.delete("Live_PostMapper.deleteAll", a);
	}
}
