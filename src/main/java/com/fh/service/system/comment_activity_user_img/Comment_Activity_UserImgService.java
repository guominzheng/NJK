package com.fh.service.system.comment_activity_user_img;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;

@Service
@Resource(name="comment_Activity_UserImgService")
public class Comment_Activity_UserImgService {
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.save("Comment_Activity_UserImgMapper.save",pd);
	}
	
	public List<PageData> findList(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("Comment_Activity_UserImgMapper.findList", pd);
	}
}
