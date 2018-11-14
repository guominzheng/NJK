package com.fh.service.system.activity_post_img;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;

@Service
@Resource(name="activity_Post_ImgService")
public class Activity_Post_ImgService {
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.save("ActivityPostImgMapper.save", pd);
	}
	
	
	public List<PageData> findList(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("ActivityPostImgMapper.findList", pd);
	}
	
	public List<PageData> findLists(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("ActivityPostImgMapper.findLists", pd);
	}
	
}
