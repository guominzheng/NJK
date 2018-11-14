package com.fh.service.system.post_img;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;

@Service
@Resource(name="post_ImgService")
public class Post_ImgService {
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public List<PageData> findList(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("Post_imgMapper.findList", pd);
	}
	
	public void save(PageData pd) throws Exception{
		dao.save("Post_imgMapper.save", pd);
	}
	
	public void delete(PageData pd) throws Exception{
		dao.delete("Post_imgMapper.delete",pd);
	}
	
	public List<PageData> findTop(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("Post_imgMapper.findTop", pd);
	}
	
	
}
