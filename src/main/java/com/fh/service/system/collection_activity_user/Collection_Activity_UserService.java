package com.fh.service.system.collection_activity_user;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;

@Service
@Resource(name="collection_Activity_UserService")
public class Collection_Activity_UserService {
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.save("Collection_Activity_UserMapper.save", pd);
	}
	
	public PageData findById(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Collection_Activity_UserMapper.findById", pd);
	}
	
	public PageData findCount(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Collection_Activity_UserMapper.findCount", pd);
	}
	
	
	public void delete(PageData pd) throws Exception{
		dao.delete("Collection_Activity_UserMapper.delete", pd);
	}
}
