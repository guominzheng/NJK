package com.fh.service.system.post_forim_postcomment;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service
@Resource(name="post_forim_postcommentService")
public class Post_forim_postcommentService {
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public List<PageData> list(Page page) throws Exception{
		return (List<PageData>) dao.findForList("PostForimPostcommentMapper.datalistPage", page);
	}
	
	public void save(PageData pd) throws Exception{
		dao.save("PostForimPostcommentMapper.save", pd);
	}
}
