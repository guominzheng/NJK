package com.fh.service.system.order_info;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
@Service
@Resource(name="order_infoService")
public class Order_infoService {

	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.save("OrderInfoMapper.save", pd);
	}
	
	public void edit(PageData pd) throws Exception{
		dao.update("OrderInfoMapper.edit", pd);
	}
	public void updateSTATUS(PageData pd) throws Exception{
		dao.update("OrderInfoMapper.updateSTATUS", pd);
	}
	public PageData findById(PageData pd) throws Exception{
		return (PageData) dao.findForObject("OrderInfoMapper.findById", pd);
	}
	
	public List<PageData> list(Page page) throws Exception{
		return (List<PageData>) dao.findForList("OrderInfoMapper.datalistPage", page);
	}
	
	public PageData findMoney(PageData pd) throws Exception{
		return (PageData) dao.findForObject("OrderInfoMapper.findMoney", pd);
	}
	
	public void delete(PageData pd) throws Exception{
		dao.delete("OrderInfoMapper.delete", pd);
	}
	
	public void deleteAll(String P[]) throws Exception{
		dao.delete("OrderInfoMapper.deleteAll", P);
	}
	
	public void editStatus(PageData pd) throws Exception{
		dao.update("OrderInfoMapper.editStatus", pd);
	}
	
	public List<PageData> findDuiBi(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("OrderInfoMapper.findDuiBi", pd);
	}
	
	public PageData findTDuiBi(PageData pd) throws Exception{
		return (PageData) dao.findForObject("OrderInfoMapper.findTDuiBi", pd);
	}
	
	public PageData findListDate(PageData pd) throws Exception{
		return (PageData) dao.findForObject("OrderInfoMapper.findListDate", pd);
	}
	
	public List<PageData> findList(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("OrderInfoMapper.findList", pd);
	}
	
	public void editCtatus(PageData pd) throws Exception{
		dao.update("OrderInfoMapper.editCtatus", pd);
	}
	
	public PageData findByNumber(PageData pd) throws Exception{
		return (PageData) dao.findForObject("OrderInfoMapper.findByNumber", pd);
	}
	
	
	public void deleteOrderNumber(PageData pd) throws Exception{
		dao.delete("OrderInfoMapper.deleteOrderNumber", pd);
	}
	
	public void editPayDate(PageData pd) throws Exception{
		dao.update("OrderInfoMapper.editPayDate", pd);
	}
	
	public void editUserId(PageData pd) throws Exception{
		dao.update("OrderInfoMapper.editUserId", pd);
	}

	public List<PageData> findOrderlistPage(Page page)throws Exception{
		return (List<PageData>) dao.findForList("OrderInfoMapper.findOrderlistPage",page);
	}

	public void editCtatusAndEndDate(PageData pd)throws Exception{
		dao.update("OrderInfoMapper.editCtatusAndEndDate",pd);
	}
	/**
	 * 获取某个用下的所有订单信息
	 * @param page USERNAME
	 * @return
	 * @throws Exception
	 */
	public List<PageData> findUserOrderList(Page page) throws Exception{
		return (List<PageData>) dao.findForList("OrderInfoMapper.userOrderlistPage", page);
	}

	public PageData findHOrder(PageData pd) throws Exception{
		return (PageData) dao.findForObject("OrderInfoMapper.findHOrder", pd);
	}
	public List<PageData> findOrderC(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("OrderInfoMapper.findOrderC", pd);
	}

	public PageData findOrderUserId(PageData pd) throws Exception {
		return (PageData) dao.findForObject("OrderInfoMapper.findOrderUserId",pd);
	}

	public List<PageData> findAllData(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("OrderInfoMapper.findAllData", pd);
	}


}
