package com.fh.service.system.apply_vip;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service
@Resource(name="apply_vipService")
public class Apply_vipService {

	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.save("ApplyVipMapper.save", pd);
	}
	
	public void edit(PageData pd) throws Exception{
		dao.update("ApplyVipMapper.edit", pd);
	}
	
	public PageData findById(PageData pd) throws Exception{
		return (PageData) dao.findForObject("ApplyVipMapper.findById", pd);
	}
	
	public List<PageData> list(Page page) throws Exception{
		return (List<PageData>) dao.findForList("ApplyVipMapper.datalistPage", page);
	}
	
	public void delete(PageData pd) throws Exception{
		dao.delete("ApplyVipMapper.delete", pd);
	}
	
	public void deleteAll(String p[]) throws Exception{
		dao.delete("ApplyVipMapper.deleteAll", p);
	}
	
	public List<PageData> findList(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("ApplyVipMapper.findList", pd);
	}
	
	public void deleteA(PageData pd) throws Exception{
		dao.delete("ApplyVipMapper.deleteA", pd);
	}
}
