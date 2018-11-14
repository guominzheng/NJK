package com.fh.service.system.home_follow;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service
@Resource(name="home_FollowService")
public class Home_FollowService {
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.save("Home_FollowMapper.save", pd);
	}
	
	public List<PageData> list(Page page) throws Exception{
		return (List<PageData>) dao.findForList("Home_FollowMapper.datalistPage", page);
	}
	
	public List<PageData> list1(Page page) throws Exception{
		return (List<PageData>) dao.findForList("Home_FollowMapper.datalistPage", page);
	}
	
	public void delete(PageData pd) throws Exception{
		dao.delete("Home_FollowMapper.delete", pd);
	}
	
	public PageData findById(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Home_FollowMapper.findById", pd);
	}
}
