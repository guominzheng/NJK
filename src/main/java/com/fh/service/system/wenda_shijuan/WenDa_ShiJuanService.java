package com.fh.service.system.wenda_shijuan;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service
@Resource(name="wenDa_ShiJuanService")
public class WenDa_ShiJuanService {
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.save("WenDa_ShiJuanMapper.save", pd);
	}
	
	public List<PageData> list(Page page) throws Exception{
		return (List<PageData>) dao.findForList("WenDa_ShiJuanMapper.datalistPage", page);
	}
	
	public PageData findById(PageData pd) throws Exception{
		return (PageData) dao.findForObject("WenDa_ShiJuanMapper.findById", pd);
	}
	
	public void edit(PageData pd) throws Exception{
		dao.update("WenDa_ShiJuanMapper.edit", pd);
	}
	
	public void delete(PageData pd) throws Exception{
		dao.delete("WenDa_ShiJuanMapper.delete", pd);
	}
	
	public List<PageData> weidalistPage(Page page) throws Exception{
		return (List<PageData>) dao.findForList("WenDa_ShiJuanMapper.weidalistPage", page);
	}
	
	public List<PageData> yizuolistPage(Page page) throws Exception{
		return (List<PageData>) dao.findForList("WenDa_ShiJuanMapper.yizuolistPage", page);
	}
	
	public PageData findZuiXin(PageData pd) throws Exception{
		return (PageData) dao.findForObject("WenDa_ShiJuanMapper.findZuiXin", pd);
	}
	
	public PageData findWeiDuCount(PageData pd) throws Exception{
		return (PageData) dao.findForObject("WenDa_ShiJuanMapper.findWeiDuCount", pd);
	}
	
	public void editZhiDing(PageData pd) throws Exception{
		dao.update("WenDa_ShiJuanMapper.editZhiDing", pd);
	}
	
	public void editZhiDings(PageData pd) throws Exception{
		dao.update("WenDa_ShiJuanMapper.editZhiDings", pd);
	}
	
	public List<PageData> findweizuoList(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("WenDa_ShiJuanMapper.findweizuoList", pd);
	}
	
	public void editXin(PageData pd) throws Exception{
		dao.update("WenDa_ShiJuanMapper.editXin", pd);
	}
	
	public PageData findPage(PageData pd) throws Exception{
		return (PageData) dao.findForObject("WenDa_ShiJuanMapper.findPage", pd);
	}
}
