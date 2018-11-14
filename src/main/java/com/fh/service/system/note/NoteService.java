package com.fh.service.system.note;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;

@Service
@Resource(name="noteService")
public class NoteService {
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.findForObject("NoteMapper.save", pd);
	}
	
	public void edit(PageData pd) throws Exception{
		dao.update("NoteMapper.edit", pd);
	}
	
	public PageData findById(PageData pd) throws Exception{
		return (PageData) dao.findForObject("NoteMapper.findById", pd);
	}
	
	public PageData findByUId(PageData pd) throws Exception{
		return (PageData) dao.findForObject("NoteMapper.findByUId", pd);
	}
}
