package com.fh.service.system.research;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service
@Resource(name="researchService")
public class ResearchService {
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.save("ResearchMapper.save", pd);
	}
	
	public List<PageData> datalistPage(Page page) throws Exception{
		return (List<PageData>) dao.findForList("ResearchMapper.datalistPage", page);
	}
	
	public List<PageData> dataslistPage(Page page) throws Exception{
		return (List<PageData>) dao.findForList("ResearchMapper.dataslistPage", page);
	}
	
	public List<PageData> findList(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("ResearchMapper.findList", pd);
	}
	
	public PageData findById(PageData pd) throws Exception{
		return (PageData) dao.findForObject("ResearchMapper.findById", pd);
	}
	
	public void edit(PageData pd) throws Exception{
		dao.update("ResearchMapper.edit", pd);
	}
	
	public void delete(PageData pd) throws Exception{
		dao.delete("ResearchMapper.delete", pd);
	}
	
	public void deleteAll(String a[]) throws Exception{
		dao.delete("ResearchMapper.deleteAll", a);
	}
	
	public PageData findlimit(PageData pd) throws Exception{
		return (PageData) dao.findForObject("ResearchMapper.findlimit", pd);
	}
	
	public List<PageData> findlimitTou(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("ResearchMapper.findlimitTou", pd);
	}
	
	
	
	
	public List<PageData> findlimits(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("ResearchMapper.findlimits",pd);
	}
	
	public List<PageData> findZResearchTop(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("ResearchMapper.findZResearchTop", pd);
	}
	
	public PageData findCount(PageData pd) throws Exception{
		return (PageData) dao.findForObject("ResearchMapper.findCount", pd);
	}

	
	public void deleteAlls(PageData pd) throws Exception{
		dao.delete("ResearchMapper.deleteAlls", pd);
	}
	public void updateAll(String p[]) throws Exception{
		dao.update("ResearchMapper.updateAll", p);
	}
	public void editSHENCHA(PageData pd) throws Exception{
		dao.update("ResearchMapper.editSHENCHA", pd);
	}
	public void editHuiFu(PageData pd) throws Exception{
		dao.update("ResearchMapper.editHuiFu", pd);
	}

	public void editJiaViews(PageData pd) throws Exception{
		dao.update("ResearchMapper.editJiaViews", pd);
	}
	
	public void editJianViews(PageData pd) throws Exception{
		dao.update("ResearchMapper.editJianViews", pd);
	}
	
	public void editFaBu(PageData pd) throws Exception{
		dao.update("ResearchMapper.editFaBu", pd);
	}
	
	public void editDates(PageData pd) throws Exception{
		dao.update("ResearchMapper.editDates", pd);
	}

	public void editTSTATUS(PageData pd) throws Exception {
		dao.update("ResearchMapper.editTSTATUS",pd);
	}
	public List<PageData> findRelist(PageData pd)throws  Exception{
		return (List<PageData>)dao.findForList("ResearchMapper.findRelist",pd);
	}

	public PageData findHUODONG(PageData pd) throws Exception{
		return (PageData) dao.findForObject("ResearchMapper.findHUODONG", pd);
	}
	public PageData findProHuo(PageData pd) throws Exception{
		return (PageData) dao.findForObject("ResearchMapper.findProHuo", pd);
	}
	public List<PageData> dataAlllistPage(Page page)throws  Exception{
		return (List<PageData>)dao.findForList("ResearchMapper.dataAlllistPage",page);
	}
	public void editHuoDong(PageData pd)throws  Exception{
		 dao.update("ResearchMapper.editHuoDong",pd);
	}
	public void updataBHuoDong(PageData pd)throws  Exception{
		dao.update("ResearchMapper.updataBHuoDong",pd);
	}
	public void updataSHuoDong(PageData pd)throws  Exception{
		dao.update("ResearchMapper.updataSHuoDong",pd);
	}
	public List<PageData> AlllistPage(Page page)throws  Exception{
		return (List<PageData>)dao.findForList("ResearchMapper.AlllistPage",page);
	}
	public void editHuiFuJian(PageData pd)throws  Exception{
		dao.update("ResearchMapper.editHuiFuJian",pd);
	}
}
