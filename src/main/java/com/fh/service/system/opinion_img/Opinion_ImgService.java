package com.fh.service.system.opinion_img;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;


@Service
@Resource(name="opinion_ImgService")
public class Opinion_ImgService {
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.save("Opinion_ImgMapper.save", pd);
	}
	
	public List<PageData> findList(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("Opinion_ImgMapper.findList", pd);
	}
}
