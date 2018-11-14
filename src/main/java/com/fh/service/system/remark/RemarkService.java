package com.fh.service.system.remark;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service
@Resource(name="remarkService")
public class RemarkService {

	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public List<PageData> findList(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("RemarkMapper.findList", pd);
	}
	
	public void save(PageData pd) throws Exception{
		dao.save("RemarkMapper.save", pd);
	}
	
	public void deleteAll(String p[]) throws Exception{
		dao.delete("RemarkMapper.deleteAll", p);
	}
	
	public void edit(PageData pd) throws Exception{
		dao.update("RemarkMapper.edit", pd);
	}
	
	public List<PageData> list(Page page) throws Exception{
		return (List<PageData>) dao.findForList("RemarkMapper.datalistPage", page);
	}
	
	public void delete(PageData pd) throws Exception{
		dao.delete("RemarkMapper.delete", pd);
	}
	
	public PageData findById(PageData pd) throws Exception{
		return (PageData) dao.findForObject("RemarkMapper.findById", pd);
	}
	public PageData selectCount(PageData pd) throws Exception{
		return (PageData) dao.findForObject("RemarkMapper.selectCount", pd);
	}
	public void editKUCUN(PageData pd) throws Exception{
		 dao.update("RemarkMapper.editKUCUN", pd);
	}
}
