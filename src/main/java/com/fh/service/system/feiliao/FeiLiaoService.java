package com.fh.service.system.feiliao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service
@Resource(name="feiLiaoService")
public class FeiLiaoService {
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public List<PageData> list(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("FeiLiaoMapper.findList", pd);
	}
	
	public PageData findById(PageData pd) throws Exception{
		return (PageData) dao.findForObject("FeiLiaoMapper.findById", pd);
	}
	
	public List<PageData> findList1(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("FeiLiaoMapper.findList1", pd);
	}
	
	public void edit(PageData pd) throws Exception{
		dao.update("FeiLiaoMapper.edit", pd);
	}
	public List<PageData> findList2(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("FeiLiaoMapper.findList2", pd);
	}
	
	public void save(PageData pd) throws Exception{
		dao.save("FeiLiaoMapper.save", pd);
	}
	
	public List<PageData> findList3(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("FeiLiaoMapper.findList3", pd);
	}
	
	public void delete(PageData pd) throws Exception{
		dao.delete("FeiLiaoMapper.delete", pd);
	}

}
