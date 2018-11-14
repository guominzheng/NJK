package com.fh.service.system.zuoguo_shijuan;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service
@Resource(name="zuoGuo_ShiJuanService")
public class ZuoGuo_ShiJuanService {
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.save("ZuoGuo_WendaMapper.save", pd);
	}
	
	public PageData findYiBai(PageData pd) throws Exception{
		return (PageData) dao.findForObject("ZuoGuo_WendaMapper.findYiBai", pd);
	}
	
	public List<PageData> yizuolistPage(Page page) throws Exception{
		return (List<PageData>) dao.findForList("ZuoGuo_WendaMapper.yizuolistPage", page);
	}
	
	public List<PageData> FractionpageList(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("ZuoGuo_WendaMapper.FractionpageList", pd);
	}
	
	public PageData findFractionPaiMing(PageData pd) throws Exception{
		return (PageData) dao.findForObject("ZuoGuo_WendaMapper.findFractionPaiMing", pd);
	}
	
	public PageData ZuoTilistPaiMing(PageData pd) throws Exception{
		return (PageData) dao.findForObject("ZuoGuo_WendaMapper.findZuoTiZiJiPaiMing", pd);
	}
	
	public PageData findNianPaiMing(PageData pd) throws Exception{
		return (PageData) dao.findForObject("ZuoGuo_WendaMapper.findNianPaiMing", pd);
	}
	
	public List<PageData> ZuoTilist(PageData pd) throws Exception{
		return  (List<PageData>) dao.findForList("ZuoGuo_WendaMapper.ZuoTilist", pd);
	}
	
	public PageData findZiJiDeFei(PageData pd) throws Exception{
		return (PageData) dao.findForObject("ZuoGuo_WendaMapper.findZiJiDeFei", pd);
	}
	
	public void edit(PageData pd) throws Exception{
		dao.update("ZuoGuo_WendaMapper.edit", pd);
	}
	
	public PageData findById(PageData pd) throws Exception{
		return (PageData) dao.findForObject("ZuoGuo_WendaMapper.findById", pd);
	}
	
	public PageData findCount(PageData pd) throws Exception{
		return (PageData) dao.findForObject("ZuoGuo_WendaMapper.findCount", pd);
	}
	
	public PageData findCounts(PageData pd) throws Exception{
		return (PageData) dao.findForObject("ZuoGuo_WendaMapper.findCounts", pd);
	}
	
	public void editStatus(PageData pd) throws Exception{
		dao.update("ZuoGuo_WendaMapper.editStatus", pd);
	}
	
	public void editUserId(PageData pd) throws Exception{
		dao.update("ZuoGuo_WendaMapper.editUserId", pd);
	}
	
	public PageData findZuiXinDaTi(PageData pd) throws Exception{
		return (PageData) dao.findForObject("ZuoGuo_WendaMapper.findZuiXinDaTi", pd);
	}
	
	public PageData findByIds(PageData pd) throws Exception{
		return (PageData) dao.findForObject("ZuoGuo_WendaMapper.findByIds", pd);
	}
	
	public List<PageData> findPaoMaDing(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("ZuoGuo_WendaMapper.findPaoMaDing", pd);
	}
	
	public PageData findYueCount(PageData pd) throws Exception{
		return (PageData) dao.findForObject("ZuoGuo_WendaMapper.findYueCount", pd);
	}
	
	public PageData findListDate(PageData pd) throws Exception{
		return (PageData) dao.findForObject("ZuoGuo_WendaMapper.findListDate", pd);
	}
	
	public List<PageData> findGenJuYue(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("ZuoGuo_WendaMapper.findGenJuYue", pd);
	}
	
	public PageData findYueGCount(PageData pd) throws Exception{
		return (PageData) dao.findForObject("ZuoGuo_WendaMapper.findYueGCount", pd);
	}
	
	public PageData findListGDate(PageData pd) throws Exception{
		return (PageData) dao.findForObject("ZuoGuo_WendaMapper.findListGDate", pd);
	}
	
	public List<PageData> findGenJuGYue(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("ZuoGuo_WendaMapper.findGenJuGYue", pd);
	}
	
	public List<PageData> dataSuiJilistPage(Page page) throws Exception{
		return (List<PageData>) dao.findForList("ZuoGuo_WendaMapper.dataSuiJilistPage", page);
	}
	
	public List<PageData> findZuoPaiHang(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("ZuoGuo_WendaMapper.findZuoPaiHang", pd);
	}
	
	public PageData findZhouPaiMing(PageData pd) throws Exception{
		return (PageData) dao.findForObject("ZuoGuo_WendaMapper.findZhouPaiMing", pd);
	}
	
	
	public PageData findDateZuoGuoPaiHangBang(PageData pd) throws Exception{
		return (PageData) dao.findForObject("ZuoGuo_WendaMapper.findDateZuoGuoPaiHangBang", pd);
	}
	
	public List<PageData> findZhouPaiMings(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("ZuoGuo_WendaMapper.findZhouPaiMings", pd);
	}
}
