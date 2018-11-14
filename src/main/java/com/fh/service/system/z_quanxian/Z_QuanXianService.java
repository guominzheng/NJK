package com.fh.service.system.z_quanxian;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service
@Resource(name="z_QuanXianService")
public class Z_QuanXianService {
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.save("Z_QuanXianMapper.save", pd);
	}
	
	public List<PageData> list(Page page) throws Exception{
		return (List<PageData>) dao.findForList("Z_QuanXianMapper.datalistPage", page);
	}
	
	public List<PageData> findList(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("Z_QuanXianMapper.findList", pd);
	}
	
	public PageData findById(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Z_QuanXianMapper.findById", pd);
	}
	
	public void delete(PageData pd) throws Exception{
		dao.delete("Z_QuanXianMapper.delete", pd);
	}
	
	public PageData findById1(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Z_QuanXianMapper.findById1", pd);
	}
	
	public void deleteAll(PageData pd) throws Exception{
		dao.delete("Z_QuanXianMapper.deleteAll", pd);
	}
	
	public PageData findById2(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Z_QuanXianMapper.findById2", pd);
	}
}
