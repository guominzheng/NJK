package com.fh.service.system.comment_proimg;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service
@Resource(name="commentProImgService")
public class CommentProImgService {

	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public List<PageData> findList(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("CommentProImgMapper.findList", pd);
	}
	
	public void save(PageData pd) throws Exception{
		dao.save("CommentProImgMapper.save", pd);
	}
}
