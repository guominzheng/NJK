package com.fh.service.system.appuser;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service
@Resource(name="appuserService")
public class AppuserService {
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public List<PageData> list(Page page) throws Exception{
		return (List<PageData>) dao.findForList("AppuserMapper.datalistPage", page);
	}
	
	public List<PageData> findList(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("AppuserMapper.findList", pd);
	}
	
	public PageData login(PageData pd) throws Exception{
		return (PageData) dao.findForObject("AppuserMapper.login", pd);
	}
	
	public PageData findName(PageData pd) throws Exception{
		return (PageData) dao.findForObject("AppuserMapper.findName", pd);
	}
	
	public void editIp(PageData pd) throws Exception{
		dao.update("AppuserMapper.editIp", pd);
	}
	
	public void save(PageData pd) throws Exception{
		dao.save("AppuserMapper.save", pd);
	}
	
	public synchronized void editCHANNELID(PageData pd) throws Exception{
		dao.update("AppuserMapper.editCHANNELID", pd);
	}
	
	public void editPassword(PageData pd) throws Exception{
		dao.update("AppuserMapper.editPassword", pd);
	}
	
	public PageData findById(PageData pd) throws Exception{
		return (PageData) dao.findForObject("AppuserMapper.findById", pd);
	}
	
	public PageData findById1(PageData pd) throws Exception{
		return (PageData) dao.findForObject("AppuserMapper.findById1", pd);
	}
	
	public void editName(PageData pd) throws Exception{
		dao.update("AppuserMapper.editName", pd);
	}
	
	public PageData findBySId(PageData pd) throws Exception{
		return (PageData) dao.findForObject("AppuserMapper.findBySId", pd);
	}
	
	public void editEXCLU_ID(PageData pd) throws Exception{
		dao.update("AppuserMapper.editEXCLU_ID", pd);
	}
	
	public void editTianChou(PageData pd) throws Exception{
		dao.update("AppuserMapper.editTianChou", pd);
	}
	
	public void editVip(PageData pd) throws Exception{
		dao.update("AppuserMapper.editVip", pd);
	}
	
	public void editloginDate(PageData pd) throws Exception{
		dao.update("AppuserMapper.editloginDate", pd);
	}
	
	public List<PageData> findListName(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("AppuserMapper.findListName", pd);
	}
	
	
	public PageData findByStatus(PageData pd) throws Exception{
		return (PageData) dao.findForObject("AppuserMapper.findByStatus", pd);
	}
	
	public void editStatus(PageData pd) throws Exception{
		dao.update("AppuserMapper.editStatus", pd);
	}
	
	/*
	 * 通过loginname获取数据
	 */
	public PageData findByUId(PageData pd) throws Exception {
		return (PageData) dao.findForObject("AppuserMapper.findByUId", pd);
	}
	
	public PageData findById111(PageData pd) throws Exception{
		return (PageData) dao.findForObject("AppuserMapper.findById111", pd);
	}
	
	public PageData findById222(PageData pd) throws Exception{
		return (PageData) dao.findForObject("AppuserMapper.findById222", pd);
	}
	
	public PageData findByUserName(PageData pd) throws Exception{
		return (PageData) dao.findForObject("AppuserMapper.findByUserName",pd);
	}
	
	public void editOpenId(PageData pd) throws Exception{
		dao.update("AppuserMapper.editOpenId", pd);
	}
	
	public List<PageData> findAppUserVip(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("AppuserMapper.findAppUserVip", pd);
	}
	
	public PageData findUnionId(PageData pd) throws Exception{
		return (PageData) dao.findForObject("AppuserMapper.findUnionId", pd);
	}
	
	public void editUnionId(PageData pd) throws Exception{
		dao.update("AppuserMapper.editUnionId", pd);
	}
	
	public void editUnionIds(PageData pd) throws Exception{
		dao.update("AppuserMapper.editUnionIds", pd);
	}
	
	public void delete(PageData pd) throws Exception{
		dao.delete("AppuserMapper.delete", pd);
	}
	
	public void editunionIDss(PageData pd) throws Exception{
		dao.update("AppuserMapper.editunionIDss", pd);
	}
	
	public void editType(PageData pd) throws Exception{
		dao.update("AppuserMapper.editType", pd);
	}
	
	public void editJifen(PageData pd) throws Exception{
		dao.update("AppuserMapper.editJifen", pd);
	}
	public void editJifenJ(PageData pd) throws Exception{
		dao.update("AppuserMapper.editJifenJ", pd);
	}
	
	public PageData findJiFen(PageData pd) throws Exception{
		return (PageData) dao.findForObject("AppuserMapper.findJiFen", pd);
	}
	
	public PageData findJiFens(PageData pd) throws Exception{
		return (PageData) dao.findForObject("AppuserMapper.findJiFens", pd);
	}
	
	public List<PageData> findVipAppUser(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("AppuserMapper.findVipAppUser", pd);
	}

	public void addPhoneAddress(PageData pd) throws Exception{
		 dao.update("AppuserMapper.addPhoneAddress", pd);
	}

	public void addZJIFEN(PageData pd) throws Exception{
		dao.update("AppuserMapper.addZJIFEN", pd);
	}
	public void updateUserInfoGame(PageData pd) throws Exception{
		dao.update("AppuserMapper.updateUserInfoGame", pd);
	}
	public PageData findOpenId(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AppuserMapper.findOpenId",pd);
	}
}
