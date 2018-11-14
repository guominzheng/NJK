package com.fh.service.system.guanggao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service
@Resource(name="guangGaoService")
public class GuangGaoService {
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.save("GaungGaoMapper.save", pd);
	}
	
	public PageData findById(PageData pd) throws Exception{
		return (PageData) dao.findForObject("GaungGaoMapper.findById", pd);
	}
	
	public List<PageData> datalistPage(Page page) throws Exception{
		return (List<PageData>) dao.findForList("GaungGaoMapper.datalistPage", page);
	}
	
	public void edit(PageData pd) throws Exception{
		dao.update("GaungGaoMapper.edit", pd);
	}
	
	public void delete(PageData pd) throws Exception{
		dao.delete("GaungGaoMapper.delete", pd);
	}
	
	public PageData findByIdS(PageData pd) throws Exception{
		return (PageData) dao.findForObject("GaungGaoMapper.findByIdS", pd);
	}
}
