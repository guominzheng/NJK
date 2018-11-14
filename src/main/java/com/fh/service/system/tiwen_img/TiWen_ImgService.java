package com.fh.service.system.tiwen_img;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;

@Service
@Resource(name="tiWen_ImgService")
public class TiWen_ImgService {
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.save("TiWen_imgMapper.save", pd);
	}
	
	public List<PageData> findList(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("TiWen_imgMapper.findList", pd);
	}
	
	public List<PageData> findTop(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("TiWen_imgMapper.findTop", pd);
	}
	
	public void delete(PageData pd) throws Exception{
		dao.delete("TiWen_imgMapper.delete", pd);
	}
}
