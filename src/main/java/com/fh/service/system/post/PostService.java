package com.fh.service.system.post;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service
@Resource(name="postService")
public class PostService {

	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.save("PostMapper.save", pd);
	}
	
	
	public List<PageData> list(Page page) throws Exception{
		return (List<PageData>) dao.findForList("PostMapper.datalistPage", page);
	}
	
	public List<PageData> userlistPage(Page page) throws Exception{
		return (List<PageData>) dao.findForList("PostMapper.userlistPage", page);
	}
	
	public List<PageData> findList(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("PostMapper.findList", pd);
	}
	
	public PageData findPostMstatus(PageData pd) throws Exception{
		return (PageData) dao.findForObject("PostMapper.findPostMstatus", pd);
	}
	
	public List<PageData> meirilistPage(Page page) throws Exception{
		return (List<PageData>) dao.findForList("PostMapper.meirilistPage", page);
	}
	
	public void editDate(PageData pd) throws Exception{
		dao.update("PostMapper.editDate", pd);
	}
	
	public PageData findById(PageData pd) throws Exception{
		return (PageData) dao.findForObject("PostMapper.findById", pd);
	}
	
	public PageData findById111(PageData pd) throws Exception{
		return (PageData) dao.findForObject("PostMapper.findById111",pd);
	}
	
	public void delete(PageData pd) throws Exception{
		dao.delete("PostMapper.delete", pd);
	}
	
	public void deleteAll(String p[]) throws Exception{
		dao.delete("PostMapper.deleteAll", p);
	}
	
	public void edit(PageData pd) throws Exception{
		dao.update("PostMapper.edit", pd);
	}
	
	public void editFID(PageData pd) throws Exception{
		dao.update("PostMapper.editFID", pd);
	}
	
	public void editTop(PageData pd) throws Exception{
		dao.update("PostMapper.editTop", pd);
	}
	
	public void editHuiFu(PageData pd) throws Exception{
		dao.update("PostMapper.editHuiFu", pd);
	}
	
	public void editViews(PageData pd) throws Exception{
		dao.update("PostMapper.editViews", pd);
	}
	
	public PageData findCount(PageData pd) throws Exception{
		return (PageData) dao.findForObject("PostMapper.findCount", pd);
	}
	
	public PageData findCount1(PageData pd) throws Exception{
		return (PageData) dao.findForObject("PostMapper.findCount1", pd);
	}
	
	public void editPID(PageData pd) throws Exception{
		dao.update("PostMapper.editPID", pd);
	}
	
	public void editEStatus(PageData pd) throws Exception{
		dao.update("PostMapper.editEStatus", pd);
	}
	public List<PageData> findPostPid(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("PostMapper.findPostPid", pd);
	}
	
	public List<PageData> selectPost(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("PostMapper.selectPost", pd);
	}
	
	public PageData findFIDCount(PageData pd) throws Exception{
		return (PageData) dao.findForObject("PostMapper.findFIDCount", pd);
	}
	
	public List<PageData> postlistPage(Page page) throws Exception{
		return (List<PageData>) dao.findForList("PostMapper.postlistPage", page);
	}
	
	public void editAID(PageData pd) throws Exception{
		dao.update("PostMapper.editAID", pd);
	}
	
	public List<PageData> RelistPage(Page page) throws Exception{
		return (List<PageData>) dao.findForList("PostMapper.RelistPage", page);
	}
	
	public void editMStatus(PageData pd) throws Exception{
		dao.update("PostMapper.editMStatus", pd);
	}
	
	public List<PageData> selectActivity(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("PostMapper.selectActivity", pd);
	}
	
	public List<PageData> KelistPage(Page page) throws Exception{
		return (List<PageData>) dao.findForList("PostMapper.KelistPage", page);
	}
	
	public List<PageData> ReNamelistPage(Page page) throws Exception{
		return (List<PageData>) dao.findForList("PostMapper.ReNamelistPage", page);
	}
	
	
	public List<PageData> ZhuanlistPage(Page page) throws Exception{
		return (List<PageData>) dao.findForList("PostMapper.ZhuanlistPage", page);
	}
	
	public List<PageData> findZhuan(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("PostMapper.findZhuan", pd);
	}
	
	public List<PageData> findZhuanTi(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("PostMapper.findZhuanTi", pd);
	}
	
	
	
	
	
	
	
	
	
	
	
	public List<PageData> data1listPage(Page page) throws Exception{
		return (List<PageData>) dao.findForList("PostMapper.data1listPage", page);
	}
	
	public List<PageData> ReelistPage(Page page) throws Exception{
		return (List<PageData>) dao.findForList("PostMapper.ReelistPage", page);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public List<PageData> data2listPage(Page page) throws Exception{
		return (List<PageData>) dao.findForList("PostMapper.data2listPage", page);
	}
	
	public List<PageData> ZhuanTi(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("PostMapper.ZhuanTi", pd);
	}
	
	public List<PageData> findTList(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("PostMapper.findTList", pd);
	}
	
	public List<PageData> data3listPage(Page page) throws Exception{
		return (List<PageData>) dao.findForList("PostMapper.data3listPage", page);
	}
	
	public List<PageData> data4listPage(Page page) throws Exception{
		return (List<PageData>) dao.findForList("PostMapper.data4listPage", page);
	}
	
	public List<PageData> findListFid(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("PostMapper.findListFid", pd);
	}
	
	public List<PageData> findListFid2(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("PostMapper.findListFid2", pd);
	}
	
	
	public void editBrowse(PageData pd) throws Exception{
		dao.update("PostMapper.editBrowse", pd);
	}
	
	public void editShuZi(PageData pd) throws Exception{
		dao.update("PostMapper.editShuZi", pd);
	}
	
	public void editUserId(PageData pd) throws Exception{
		dao.update("PostMapper.editUserId", pd);
	}
	
	public void editFimg(PageData pd) throws Exception{
		dao.update("PostMapper.editFimg", pd);
	}
}
