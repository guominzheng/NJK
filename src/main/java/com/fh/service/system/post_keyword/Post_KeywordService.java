package com.fh.service.system.post_keyword;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service
@Resource(name="post_KeywordService")
public class Post_KeywordService {
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.save("Post_KeywordMapper.save", pd);
	}
	
	public void delete(PageData pd) throws Exception{
		dao.delete("Post_KeywordMapper.delete",pd);
	}
	
	public List<PageData> findList(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("Post_KeywordMapper.findList", pd);
	}
	
	public void edit(PageData pd) throws Exception{
		dao.update("Post_KeywordMapper.edit", pd);
	}
	
	
	public PageData findById(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Post_KeywordMapper.findById", pd);
	}
	
	public List<PageData> list(Page page) throws Exception{
		return (List<PageData>) dao.findForList("Post_KeywordMapper.datalistPage", page);
	}
	
	public void deleteAll(String a[]) throws Exception{
		dao.delete("Post_KeywordMapper.deleteAll", a);
	}
	
	public PageData findName(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Post_KeywordMapper.findName", pd);
	}
}
