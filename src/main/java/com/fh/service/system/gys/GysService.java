package com.fh.service.system.gys;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;

@Service
@Resource(name="gysService")
public class GysService {
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public List<PageData> findList(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("GysMapper.findList", pd);
	}
}
