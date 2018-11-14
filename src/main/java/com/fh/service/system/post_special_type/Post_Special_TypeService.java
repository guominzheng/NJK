package com.fh.service.system.post_special_type;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service
@Resource(name="post_Special_TypeService")
public class Post_Special_TypeService {
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.save("Post_Special_TypeMapper.save", pd);
	}
	
	public List<PageData> datalistPage(Page page) throws Exception{
		return (List<PageData>) dao.findForList("Post_Special_TypeMapper.datalistPage", page);
	}
	
	public void edit(PageData pd) throws Exception{
		dao.update("Post_Special_TypeMapper.edit", pd);
	}
	
	public PageData findById(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Post_Special_TypeMapper.findById", pd);
	}
	
	public List<PageData> findList(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("Post_Special_TypeMapper.findList", pd);
	}
	
	public void delete(PageData pd) throws Exception{
		dao.delete("Post_Special_TypeMapper.delete", pd);
	}
}
