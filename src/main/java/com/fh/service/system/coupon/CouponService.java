package com.fh.service.system.coupon;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.fh.entity.Page;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;

@Service
@Resource(name="CouponService")
public class CouponService {
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.save("CouponMapper.save", pd);
	}
	
	public List<PageData> findList(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("CouponMapper.findList", pd);
	}
	
	public void deleteAll(String p[]) throws Exception{
		dao.delete("CouponMapper.deleteAll", p);
	}
	
	public void delete(PageData pd) throws Exception{
		dao.delete("CouponMapper.delete", pd);
	}

	public List<PageData> findUserId(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("CouponMapper.findUserId",pd);
	}

	public List<PageData> dataslistPage(Page page)throws Exception{
		return (List<PageData>) dao.findForList("CouponMapper.dataslistPage",page);

	}
	public PageData findCouponId(PageData pd)throws Exception{
		return (PageData) dao.findForObject("CouponMapper.findCouponId",pd);
	}
	public  void edit(PageData pd)throws Exception{
		dao.update("CouponMapper.edit",pd);
	}
	
	public PageData findZstatus(PageData pd) throws Exception{
		return (PageData) dao.findForObject("CouponMapper.findZstatus", pd);
	}
	public PageData findCopyUser(PageData pd) throws Exception{
		return (PageData) dao.findForObject("CouponMapper.findCopyUser", pd);
	}
	
	public void editZStatus(PageData pd) throws Exception {
		dao.update("CouponMapper.editZStatus", pd);
	}

	public PageData findCount(PageData pd) throws Exception{
		return (PageData) dao.findForObject("CouponMapper.findCount", pd);
	}
	
	public PageData findById(PageData pd) throws Exception{
		return (PageData) dao.findForObject("CouponMapper.findById", pd);
	}
	
	public void editStatus(PageData pd) throws Exception{
		dao.update("CouponMapper.editStatus", pd);
	}
	
	public void saves(List<PageData> list) throws Exception{
		dao.save("CouponMapper.saves", list);
	}

	public  void batchSave(Map map)throws Exception{
		 dao.save("CouponMapper.batchSave",map);
	}
	
	
	
}
