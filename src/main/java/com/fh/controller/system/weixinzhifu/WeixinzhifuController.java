package com.fh.controller.system.weixinzhifu;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.system.weixinzhifu.WeixinzhifuService;
import com.fh.util.Const;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;

@Controller
@RequestMapping(value="weixinzhifu",produces="text/html;charset=UTF-8")
public class WeixinzhifuController extends BaseController{
	String menuUrl = "weixinzhifu/list.do"; //菜单地址(权限用)
	@Resource(name="weixinzhifuService")
	private WeixinzhifuService weixinzhifuService;
	
	
	/**
	 * 修改置顶
	 */
	@RequestMapping(value="/edit")
	public void editTop(PrintWriter out){
		logBefore(logger, "修改微信状态");
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			weixinzhifuService.edit1(pd);
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		
	}
	
	/**
	 * 修改置顶
	 */
	@RequestMapping(value="/edit1")
	public void edit(PrintWriter out){
		logBefore(logger, "修改微信状态");
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			weixinzhifuService.edit2(pd);
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		logBefore(logger, "查询微信支付列表");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} 
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		
		try{
			pd = this.getPageData();
			page.setPd(pd);
			pd = this.getPageData();
			List<PageData> list=weixinzhifuService.datalistPage(page);
			mv.setViewName("system/weixinzhifu/weixinzhifu_list");
			mv.addObject("varList", list);
			mv.addObject("pd", pd);
			mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/* ===============================权限================================== */
	public Map<String, String> getHC(){
		Subject currentUser = SecurityUtils.getSubject();  //shiro管理的session
		Session session = currentUser.getSession();
		Map<String,String> map=new HashMap();
		if(session.getAttribute(Const.SESSION_QX)!=null){
			map=(Map<String, String>)session.getAttribute(Const.SESSION_QX);
		}
		return map;
	}
	/* ===============================权限================================== */
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
