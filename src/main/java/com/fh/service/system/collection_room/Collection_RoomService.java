package com.fh.service.system.collection_room;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service
@Resource(name="collection_RoomService")
public class Collection_RoomService {
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.save("Collection_RoomMapper.save", pd);
	}
	
	public PageData findById(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Collection_RoomMapper.findById", pd);
	}
	
	public void delete(PageData pd) throws Exception{
		dao.delete("Collection_RoomMapper.delete", pd);
	}
	
	public PageData findCount(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Collection_RoomMapper.findCount", pd);
	}
	
	public PageData findListStatus(PageData pd) throws Exception{
		return (PageData) dao.findForObject("Collection_RoomMapper.findListStatus", pd);
	}
	
	public void editStatus(PageData pd) throws Exception{
		dao.update("Collection_RoomMapper.editStatus", pd);
	}
	
	public List<PageData> list(Page page) throws Exception{
		return (List<PageData>) dao.findForList("Collection_RoomMapper.datalistPage", page);
	}
	
	public void editStatus1(PageData pd) throws Exception{
		dao.update("Collection_RoomMapper.editStatus1", pd);
	}
}
