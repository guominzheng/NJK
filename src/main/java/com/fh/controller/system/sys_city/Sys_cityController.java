package com.fh.controller.system.sys_city;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fh.controller.base.BaseController;
import com.fh.service.system.sys_city.Sys_cityService;
import com.fh.util.PageData;

@Controller
@RequestMapping(value="sys_city",produces="text/html;charset=UTF-8")
public class Sys_cityController extends BaseController{

	@Resource(name="sys_cityService")
	private Sys_cityService sys_cityService;
	
	/**
	 * 根据一级分类id查二级分类
	 * @throws Exception 
	 */
	@RequestMapping(value="/findCity",produces="text/html;charset=UTF-8")
	public List<PageData> findCity() throws Exception{
		logBefore(logger, "查询市");
		PageData pd = new PageData();
		pd=this.getPageData();
		PageData pd1=sys_cityService.findProvince(pd);
		pd.put("ID", pd1.getString("ID"));
		List<PageData>	varList1 =sys_cityService.listAll(pd);
		return varList1;
	}
	
	/**
	 * 根据一级分类id查二级分类
	 * @throws Exception 
	 */
	@RequestMapping(value="/findTYPE2")
	@ResponseBody
	public List<PageData> findPort() throws Exception{
		logBefore(logger, "查询二级分类");
		PageData pd = new PageData();
		/*PageData pd1 = new PageData();
		pd1 = this.getPageData();
		pd.put("ID", pd1.getString("ZD_ID"));*/
		List<PageData>	varList =sys_cityService.listAll(pd);
		return varList;
	}
}
