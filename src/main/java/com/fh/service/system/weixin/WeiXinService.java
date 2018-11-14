package com.fh.service.system.weixin;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;

@Service
@Resource(name="weiXinService")
public class WeiXinService {
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.save("WeixinMapper.save", pd);
	}
	
	public PageData findById(PageData pd) throws Exception{
		return (PageData) dao.findForObject("WeixinMapper.findById", pd);
	}
	
	public void edit(PageData pd) throws Exception{
		dao.update("WeixinMapper.edit", pd);
	}
}
