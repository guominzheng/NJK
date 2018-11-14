package com.fh.service.system.cartnest_img;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;

@Service
@Resource(name="cartnest_ImgService")
public class Cartnest_ImgService {
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.save("Cartnest_ImgMapper.save", pd);
	}
	
	public void saves(Map<String,Object> map) throws Exception{
		dao.save("Cartnest_ImgMapper.saves", map);
	}
	
	public List<PageData> findList(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("Cartnest_ImgMapper.findList", pd);
	}
	
	public void delete(PageData pd) throws Exception{
		dao.delete("Cartnest_ImgMapper.delete", pd);
	}
}
