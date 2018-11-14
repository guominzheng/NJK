package com.fh.service.system.p_quanxian;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service
@Resource(name="p_QuanXuanService")
public class P_QuanXuanService {

	@Resource(name="daoSupport")
	public DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.save("P_QuanXianMapper.save", pd);
	}
	
	public List<PageData> list(Page page) throws Exception{
		return (List<PageData>) dao.findForList("P_QuanXianMapper.datalistPage", page);
	}
	
	public PageData findById(PageData pd) throws Exception{
		return (PageData) dao.findForObject("P_QuanXianMapper.findById", pd);
	}
	
	public void delete(PageData pd) throws Exception{
		dao.delete("P_QuanXianMapper.delete", pd);
	}
	
	public PageData findById1(PageData pd) throws Exception{
		return (PageData) dao.findForObject("P_QuanXianMapper.findById1", pd);
	}
	
	public List<PageData> findList(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("P_QuanXianMapper.findList", pd);
	}

	public List<PageData> findUserByProductlistPage(Page page) throws Exception{
		return (List<PageData>) dao.findForList("P_QuanXianMapper.findUserByProductlistPage", page);
	}

	public void deleteAll(String p[]) throws Exception{
		dao.delete("P_QuanXianMapper.deleteAll", p);
	}

	public List<PageData> findProRoleByVipName(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("P_QuanXianMapper.findProRoleByVipName", pd);
	}
	public void batchSave(Map map) throws Exception{
		dao.save("P_QuanXianMapper.batchSave", map);
	}

	public void deleteUser(PageData pd) throws Exception{
		dao.delete("P_QuanXianMapper.deleteUser", pd);
	}
}
