package com.fh.service.system.yzm;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;

@Service
@Resource(name="YzmService")
public class YzmService {
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	
	public void save(PageData pd) throws Exception{
		dao.findForObject("YzmMapper.save", pd);
	}
	
	public PageData findByPhone(PageData pd) throws Exception{
		return (PageData) dao.findForObject("YzmMapper.findByPhone", pd);
	}
	
	public void edit(PageData pd) throws Exception{
		dao.update("YzmMapper.edit", pd);
	}
	
	public List<PageData> findByPhoneYue(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("YzmMapper.findByPhoneYue", pd);
	}
	
	public List<PageData> findByPhoneIp(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("YzmMapper.findByPhoneIp", pd);
	}
	
}
