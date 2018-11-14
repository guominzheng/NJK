package com.fh.service.system.research_img;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;

@Service
@Resource(name="research_ImgService")
public class Research_ImgService {

	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.save("Research_ImgMapper.save", pd);
	}
	
	public void saves(Map<String,Object> map) throws Exception{
		dao.save("Research_ImgMapper.saves", map);
	}
	
	public List<PageData> findList(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("Research_ImgMapper.findList", pd);
	}
	
	public void delete(PageData pd) throws Exception{
		dao.delete("Research_ImgMapper.delete", pd);
	}

	public List<PageData> findHuoDongImg(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("Research_ImgMapper.findHuoDongImg",pd);
	}
	public void deleteHuoDong(PageData pd) throws Exception {
		 dao.delete("Research_ImgMapper.deleteHuoDong",pd);
	}
	public void editHuoDongImg(PageData pd) throws Exception {
		dao.update("Research_ImgMapper.editHuoDongImg",pd);
	}
	public void deleteImg(PageData pd) throws Exception {
		dao.delete("Research_ImgMapper.deleteImg",pd);
	}
}
