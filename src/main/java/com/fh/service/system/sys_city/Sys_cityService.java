package com.fh.service.system.sys_city;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;


@Service("sys_cityService")
public class Sys_cityService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("Sys_cityMapper.save", pd);
	}
	
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("Sys_cityMapper.datalistPage", page);
	}
	
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("Sys_cityMapper.listAll", pd);
	}
	public List<PageData> listAll1(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("Sys_cityMapper.listAll1", pd);
	}
	public List<PageData> listAll2(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("Sys_cityMapper.listAll2", pd);
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("Sys_cityMapper.findById", pd);
	}
	/*
	 * 通过id获取数据
	 */
	public List<PageData> findByPId(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("Sys_cityMapper.findById", pd);
	}
	/*
	* 通过id获取数据
	*/
	public PageData findId(PageData pd)throws Exception{
		return (PageData)dao.findForObject("Sys_cityMapper.findId", pd);
	}
	
	
	public PageData findProvince(PageData pd) throws Exception{
		return  (PageData) dao.findForObject("Sys_cityMapper.findProvince", pd);
	}
}

