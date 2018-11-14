package com.fh.service.system.post_info;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service
@Resource(name="post_InfoService")
public class Post_InfoService {
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public List<PageData> list(Page page) throws Exception{
		return (List<PageData>) dao.findForList("PostInfoMapper.datalistPage", page);
	}
	
	public void edit(PageData pd) throws Exception{
		dao.update("PostInfoMapper.edit", pd);
	}
	
	public void delete(PageData pd) throws Exception{
		dao.delete("PostInfoMapper.delete", pd);
	}
	
	public void deleteAll(String tid[]) throws Exception{
		dao.delete("PostInfoMapper.deleteAll", tid);
	}
	
	public void save(PageData pd) throws Exception{
		dao.save("PostInfoMapper.save", pd);
	}
	
	public PageData findById(PageData pd) throws Exception{
		return (PageData) dao.findForObject("PostInfoMapper.findById", pd);
	}
	
	public List<PageData> userlistPage(Page page) throws Exception{
		return (List<PageData>) dao.findForList("PostInfoMapper.userlistPage", page);
	}
	
	public void deleteA(PageData pd) throws Exception{
		dao.delete("PostInfoMapper.deleteA", pd);
	}
	
	public List<PageData> findList(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("PostInfoMapper.findList", pd);
	}
	
	public void editDate(PageData pd) throws Exception{
		dao.update("PostInfoMapper.editDate", pd);
	}
}
