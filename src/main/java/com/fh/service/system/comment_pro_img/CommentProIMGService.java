package com.fh.service.system.comment_pro_img;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Resource(name="commentProIMGService")
public class CommentProIMGService {
	@Resource(name="daoSupport")
	public DaoSupport dao;
	public void save(PageData pd) throws Exception{
		dao.update("CommentProImgMapper.save", pd);
	}
}

