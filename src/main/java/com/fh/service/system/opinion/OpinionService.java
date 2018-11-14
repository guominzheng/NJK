package com.fh.service.system.opinion;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service
@Resource(name="opinionService")
public class OpinionService {
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.save("OpinionMapper.save", pd);
	}
	
	public List<PageData> list(Page page) throws Exception{
		return (List<PageData>) dao.findForList("OpinionMapper.datalistPage", page);
	}
	
	public void deleteAll(String a[]) throws Exception{
		dao.delete("OpinionMapper.deleteAll", a);
	}
	
	public void edit(PageData pd) throws Exception{
		dao.update("OpinionMapper.edit", pd);
	}

	/**
	 * 根据建议信息ID查询信息
	 * @param pd	ID
	 * @return
	 * @throws Exception
	 */
	public PageData findOpinionById(PageData pd) throws Exception{
		return  (PageData)dao.findForObject("OpinionMapper.findOpinionById", pd);
	}


}
