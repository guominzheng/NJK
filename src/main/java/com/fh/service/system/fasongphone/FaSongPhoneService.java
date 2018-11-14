package com.fh.service.system.fasongphone;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;

@Service
@Resource(name="faSongPhoneService")
public class FaSongPhoneService {

	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public List<PageData> findList(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("FaSongPhoneMapper.findPhone", pd);
	}
}
