package com.fh.service.system.comment_research_img;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;

@Service
@Resource(name="comment_ResearchImgService")
public class Comment_ResearchImgService {
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.save("Comment_ResearchImgMapper.save", pd);
	}
	
	public List<PageData> findList(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("Comment_ResearchImgMapper.findList", pd);
	}
	
	public void delete(PageData pd) throws Exception{
		dao.delete("Comment_ResearchImgMapper.delete", pd);
	}
}
