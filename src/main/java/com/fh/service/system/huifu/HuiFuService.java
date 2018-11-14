package com.fh.service.system.huifu;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service
@Resource(name="huiFuService")
public class HuiFuService {
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.save("HuiFuMapper.save", pd);
	}
	
	public List<PageData> datalistPage(Page page) throws Exception{
		return (List<PageData>) dao.findForList("HuiFuMapper.datalistPage", page);
	}
}
