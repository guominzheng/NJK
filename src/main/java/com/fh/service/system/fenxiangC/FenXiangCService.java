package com.fh.service.system.fenxiangC;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service
@Resource(name="fenXiangCService")
public class FenXiangCService {
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.save("FenXiangCMapper.save", pd);
	}
	
	public PageData findCount(PageData pd) throws Exception{
		return (PageData) dao.findForObject("FenXiangCMapper.findCount", pd);
	}
	
	public List<PageData> datalistPage(Page page) throws Exception{
		return (List<PageData>) dao.findForList("FenXiangCMapper.datalistPage", page);
	}
	
	public PageData findYueCount(PageData pd) throws Exception{
		return (PageData) dao.findForObject("FenXiangCMapper.findYueCount", pd);
	}
}
