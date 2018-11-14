package com.fh.service.system.weixinzhifu;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service
@Resource(name="weixinzhifuService")
public class WeixinzhifuService {
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.save("WeixinzhifuMapper.save", pd);
	}
	
	public List<PageData> datalistPage(Page page) throws Exception{
		return  (List<PageData>) dao.findForList("WeixinzhifuMapper.datalistPage", page);
	}
	
	public void edit1(PageData pd) throws Exception{
		dao.update("WeixinzhifuMapper.edit1", pd);
	}
	
	public void edit2(PageData pd) throws Exception{
		dao.update("WeixinzhifuMapper.edit2", pd);
	}
	
	public PageData findById(PageData pd) throws Exception{
		return (PageData) dao.findForObject("WeixinzhifuMapper.findById", pd);
	}
}
