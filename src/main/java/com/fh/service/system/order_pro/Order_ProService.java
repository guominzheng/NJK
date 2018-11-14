package com.fh.service.system.order_pro;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service
@Resource(name="order_ProService")
public class Order_ProService {
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	public void save(PageData pd) throws Exception{
		dao.save("OrderproMapper.save", pd);
	}
	
	public List<PageData> list(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("OrderproMapper.findList", pd);
	}
	
	public List<PageData> list1(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("OrderproMapper.findList1", pd);
	}
	public List<PageData> findOrderProList(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("OrderproMapper.findOrderProList", pd);
	}

	public List<PageData> findList2(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("OrderproMapper.findList2",pd);
	}
}
