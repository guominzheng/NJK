package com.fh.service.system.dingdanchucuo;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;

@Service
@Resource(name="dingdanchucuoService")
public class DingdanchucuoService {
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.save("DingdanchucuoMapper.save", pd);
	}
}
