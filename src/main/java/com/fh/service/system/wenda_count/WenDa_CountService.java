package com.fh.service.system.wenda_count;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;

@Service
@Resource(name="wenDa_CountService")
public class WenDa_CountService {
	
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	
	public List<PageData> FractionpageList(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("WenDa_CountMapper.FractionpageList", pd);
	}
	
	public PageData findFractionPaiMing(PageData pd) throws Exception{
		return (PageData) dao.findForObject("WenDa_CountMapper.findFractionPaiMing", pd);
	}
	
	public PageData ZuoTilistPaiMing(PageData pd) throws Exception{
		return (PageData) dao.findForObject("WenDa_CountMapper.findZuoTiZiJiPaiMing", pd);
	}
	
	public List<PageData> ZuoTilist(PageData pd) throws Exception{
		return  (List<PageData>) dao.findForList("WenDa_CountMapper.ZuoTilist", pd);
	}
	
}
