package com.fh.service.system.post_special;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service
@Resource(name="post_SpecialService")
public class Post_SpecialService {
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.save("Post_SpecialMapper.save", pd);
	}
	
	public void edit(PageData pd) throws Exception{
		dao.update("Post_SpecialMapper.edit", pd);
	}
	
	public void delete(PageData pd) throws Exception{
		dao.delete("Post_SpecialMapper.delete", pd);
	}
	
	public void deleteAll(String o[]) throws Exception{
		dao.delete("Post_SpecialMapper.deleteAll", o);
	}
	
	public PageData findById(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Post_SpecialMapper.findById", pd);
	}
	
	public PageData findById1(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Post_SpecialMapper.findById1", pd);
	}
	
	public PageData findById2(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Post_SpecialMapper.findById2", pd);
	}
	
	public List<PageData> data4listPage(Page page) throws Exception{
		return (List<PageData>) dao.findForList("Post_SpecialMapper.data4listPage", page);
	}
	

	public List<PageData> list(Page page) throws Exception{
		return (List<PageData>) dao.findForList("Post_SpecialMapper.datalistPage", page);
	}
	
	public List<PageData> findList(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("Post_SpecialMapper.findList", pd);
	}
	
	public void editTStatus(PageData pd) throws Exception{
		dao.update("Post_SpecialMapper.editTStatus", pd);
	}
	
	public PageData findCount(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Post_SpecialMapper.findCount", pd);
	}
	
	public void editViews(PageData pd) throws Exception{
		dao.update("Post_SpecialMapper.editViews", pd);
	}
	
	public void editHuiFu(PageData pd) throws Exception{
		dao.update("Post_SpecialMapper.editHuiFu", pd);
	}
	
	public List<PageData> findNotIn(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("Post_SpecialMapper.findNotIn",pd);
	}
	
	public List<PageData> findlistIn(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("Post_SpecialMapper.findlistIn",pd);
	}
	
	public void editShuZi(PageData pd) throws Exception{
		dao.update("Post_SpecialMapper.editShuZi", pd);
	}
}
