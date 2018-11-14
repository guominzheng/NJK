package com.fh.service.system.activity_room;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service
@Resource(name="activity_RoomService")
public class Activity_RoomService {
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.save("Activity_RoomMapper.save", pd);
	}
	
	public void edit(PageData pd) throws Exception{
		dao.update("Activity_RoomMapper.edit",pd);
	}
	
	public List<PageData> list(Page page) throws Exception{
		return (List<PageData>) dao.findForList("Activity_RoomMapper.datalistPage", page);
	}
	
	public PageData findById(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Activity_RoomMapper.findById", pd);
	}
	
	public PageData findById2(PageData pd) throws Exception{
		return  (PageData) dao.findForObject("Activity_RoomMapper.findById2", pd);
	}
	
	public List<PageData> findList(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("Activity_RoomMapper.findList", pd);
	}
	
	public void delete(PageData pd) throws Exception{
		dao.delete("Activity_RoomMapper.delete", pd);
	}
	
	public void deleteAll(PageData pd) throws Exception{
		dao.delete("Activity_RoomMapper.deleteAll", pd);
	}
	
	public List<PageData> userlistPage(Page page) throws Exception{
		return (List<PageData>) dao.findForList("Activity_RoomMapper.userlistPage", page);
	}
	
	public PageData findById1(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Activity_RoomMapper.findById1", pd);
	}
	
	public PageData findById3(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Activity_RoomMapper.findById3", pd);
	}
	
	public void editS(PageData pd) throws Exception{
		dao.update("Activity_RoomMapper.editS", pd);
	}
	
	public void delete1(PageData pd) throws Exception{
		dao.delete("Activity_RoomMapper.delete1", pd);
	}
	
	public List<PageData> findList1(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("Activity_RoomMapper.findList1", pd);
	}
	
	public void editUserId(PageData pd) throws Exception{
		dao.update("Activity_RoomMapper.editUserId", pd);
	}
}
