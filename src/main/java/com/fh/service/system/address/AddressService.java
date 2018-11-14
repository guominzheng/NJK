package com.fh.service.system.address;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;
@Service
@Resource(name="addressService")
public class AddressService {

	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public PageData findDefault(PageData pd) throws Exception{
		return (PageData) dao.findForObject("AddressMapper.findDefault", pd);
	}
	
	public void save(PageData pd) throws Exception{
		dao.save("AddressMapper.save", pd);
	}
	
	public void delete(PageData pd) throws Exception{
		dao.delete("AddressMapper.delete", pd);
	}
	
	public List<PageData> findList(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("AddressMapper.findList", pd);
	}
	
	public void edit(PageData pd) throws Exception{
		dao.update("AddressMapper.edit", pd);
	}
	
	public void editUser(PageData pd) throws Exception{
		dao.update("AddressMapper.editUser", pd);
	}
	
	public void editId(PageData pd) throws Exception{
		dao.update("AddressMapper.editId", pd);
	}
	
	public PageData findById(PageData pd) throws Exception{
		return (PageData) dao.findForObject("AddressMapper.findById", pd);
	}
	
}
