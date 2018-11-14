package com.fh.service.system.wenda_suiji;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;

@Service
@Resource(name="wenDa_SuiJiService")
public class WenDa_SuiJiService {
	
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.save("WenDa_SuiJiMapper.save", pd);
	}
	
	public PageData findCount(PageData pd) throws Exception{
		return (PageData) dao.findForObject("WenDa_SuiJiMapper.findCount", pd);
	}
}
