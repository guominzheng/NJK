package com.fh.service.system.activity_post1;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service
@Resource(name="activity_Post1Service")
public class Activity_Post1Service {
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.save("Activity_Post1Mapper.save", pd);
	}
	
	public List<PageData> list(Page page) throws Exception{
		return (List<PageData>) dao.findForList("Activity_Post1Mapper.datalistPage", page);
	}
	
	public List<PageData> findList(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("Activity_Post1Mapper.findList", pd);
	}
	
	public void delete(PageData pd) throws Exception{
		dao.delete("Activity_Post1Mapper.delete", pd);
	}
	
	public void deleteAll(PageData pd) throws Exception{
		dao.delete("Activity_Post1Mapper.deleteAll", pd);
	}
	
	public PageData findCount(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Activity_Post1Mapper.findCount", pd);
	}
}
