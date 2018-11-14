package com.fh.service.system.zan_comment_research;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;

@Service
@Resource(name="zan_CommentResearchService")
public class Zan_CommentResearchService {
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.save("Zan_Comment_ResearchMapper.save", pd);
	}
	
	public PageData findById(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Zan_Comment_ResearchMapper.findById", pd);
	}
	
	public PageData findByIds(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Zan_Comment_ResearchMapper.findByIds", pd);
	}
	
	public PageData findById1(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Zan_Comment_ResearchMapper.findById1", pd);
	}
	
	public void delete(PageData pd) throws Exception{
		dao.delete("Zan_Comment_ResearchMapper.delete", pd);
	}
	
	public PageData findcount(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Zan_Comment_ResearchMapper.findcount", pd);
	}

	public void deletes(PageData pd) throws Exception {
		dao.delete("Zan_Comment_ResearchMapper.deletes",pd);
	}
}
