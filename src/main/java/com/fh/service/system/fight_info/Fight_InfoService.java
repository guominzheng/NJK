package com.fh.service.system.fight_info;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service
@Resource(name="fight_InfoService")
public class Fight_InfoService {
	@Resource(name="daoSupport")
	public DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.save("Fight_InfoMapper.save", pd);
	}
	
	public List<PageData> findList(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("Fight_InfoMapper.findList", pd);
	}
	
	public List<PageData> findListUserId(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("Fight_InfoMapper.findListUserId", pd);
	}
	
	public PageData findNumber(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Fight_InfoMapper.findNumber", pd);
	}
	
	public void editStatus(PageData pd) throws Exception{
		dao.update("Fight_InfoMapper.editStatus", pd);
	}
	
	public List<PageData> findListOpenId(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("Fight_InfoMapper.findListOpenId", pd);
	}
	
	public List<PageData> datalistPage(Page page) throws Exception{
		return (List<PageData>) dao.findForList("Fight_InfoMapper.datalistPage", page);
	}
	
	public PageData findById(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Fight_InfoMapper.findById", pd);
	}
	
	public void edit(PageData pd) throws Exception{
		dao.update("Fight_InfoMapper.edit", pd);
	}
	
	public PageData findByUIdOId(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Fight_InfoMapper.findByUIdOId", pd);
	}
	
	public PageData findOpenId(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Fight_InfoMapper.findOpenId", pd);
	}
	
	public PageData findCount(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Fight_InfoMapper.findCount", pd);
	}
	
	public List<PageData> findList1(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("Fight_InfoMapper.findList1", pd);
	}
	
	public List<PageData> findListFid2(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("Fight_InfoMapper.findListFid2", pd);
	}
	
	public List<PageData> findList2(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("Fight_InfoMapper.findList2", pd);
	}

}
