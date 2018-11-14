package com.fh.service.system.wenda_info;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service
@Resource(name="wenDa_InfnService")
public class WenDa_InfnService {
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.save("WenDa_InfoMapper.save", pd);
	}
	

	public List<PageData> list(Page page) throws Exception{
		return (List<PageData>) dao.findForList("WenDa_InfoMapper.datalistPage", page);
	}
	
	public PageData findById(PageData pd) throws Exception{
		return (PageData) dao.findForObject("WenDa_InfoMapper.findById", pd);
	}
	
	public void edit(PageData pd) throws Exception{
		dao.update("WenDa_InfoMapper.edit", pd);
	}
	
	public void delete(PageData pd) throws Exception{
		dao.delete("WenDa_InfoMapper.delete", pd);
	}
	
	public List<PageData> findList(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("WenDa_InfoMapper.findList", pd);
	}
}
