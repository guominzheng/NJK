package com.fh.service.system.news;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service
@Resource(name="newsService")
public class NewsService {
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.save("NewsMapper.save", pd);
	}
	
	public List<PageData> list(Page page) throws Exception{
		return (List<PageData>) dao.findForList("NewsMapper.datalistPage", page);
	}

	public List<PageData> Maolist(Page page) throws Exception{
		return (List<PageData>) dao.findForList("NewsMapper.maolistPage", page);
	}
	
	public void deleteAll(String a[]) throws Exception{
		dao.delete("NewsMapper.deleteAll", a);
	}
	
	public void delete(PageData pd) throws Exception{
		dao.delete("NewsMapper.delete", pd);
	}
}
