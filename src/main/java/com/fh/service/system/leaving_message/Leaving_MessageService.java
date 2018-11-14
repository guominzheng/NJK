package com.fh.service.system.leaving_message;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service
@Resource(name="leaving_MessageService")
public class Leaving_MessageService {
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.save("Leaving_MessageMapper.save", pd);
	}
	
	public List<PageData> datalistPage(Page page) throws Exception{
		return (List<PageData>) dao.findForList("Leaving_MessageMapper.datalistPage", page);
	}
	
	public List<PageData> dataslistPage(Page page) throws Exception{
		return (List<PageData>) dao.findForList("Leaving_MessageMapper.dataslistPage", page);
	}
	
	public void editStatus(PageData pd) throws Exception{
		dao.update("Leaving_MessageMapper.editStatus", pd);
	}

	public void editHUIFU(PageData pd) throws Exception{
		dao.update("Leaving_MessageMapper.editHUIFU", pd);
	}
	
	public void editHStatus(PageData pd) throws Exception{
		dao.update("Leaving_MessageMapper.editHStatus", pd);
	}
	public void editViewsJia(PageData pd) throws Exception{
		dao.update("Leaving_MessageMapper.editViewsJia", pd);
	}
	public void editViewsJian(PageData pd) throws Exception{
		dao.update("Leaving_MessageMapper.editViewsJian", pd);
	}
	public PageData findById(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Leaving_MessageMapper.findById", pd);
	}

	public void delete(PageData pd) throws Exception {
		dao.delete("Leaving_MessageMapper.delete",pd);
	}
}
