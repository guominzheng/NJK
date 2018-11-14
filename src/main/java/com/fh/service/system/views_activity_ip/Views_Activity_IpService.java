package com.fh.service.system.views_activity_ip;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;

@Service
@Resource(name="views_Activity_IpService")
public class Views_Activity_IpService {
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.save("Views_Activity_IpMapper.save", pd);
	}
	
	public PageData findById(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Views_Activity_IpMapper.findById", pd);
	}
	
	public PageData findCount(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Views_Activity_IpMapper.findCount", pd);
	}
}
