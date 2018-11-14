package com.fh.controller.system.integral;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.system.integral.IntegralService;
import com.fh.service.system.integral_info.Integral_InfoService;
import com.fh.util.Const;
import com.fh.util.DelAllFile;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.fh.util.PathUtil;

@Controller
@RequestMapping(value="integral",produces="text/html;charset=UTF-8")
public class IntegralController extends BaseController{

	String menuUrl = "integral/list.do"; //菜单地址(权限用)
	@Resource(name="integralService")
	private IntegralService integralService;
	@Resource(name="integral_InfoService")
	private Integral_InfoService integral_InfoService;
	
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/delete1")
	public void delete1(PrintWriter out){
		logBefore(logger, "删除谱图列表");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			integral_InfoService.delete(pd);
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		
	}
	/**
	 * 修改
	 */
	@RequestMapping(value="/edit1")
	public ModelAndView edit1() throws Exception{
		logBefore(logger, "修改商品");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		integral_InfoService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	/**
	 * 去修改页面
	 */
	@RequestMapping(value="/goEdit1")
	public ModelAndView goEdit1(){
		logBefore(logger, "去修改商品页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {	
			pd = integral_InfoService.findById(pd);	//根据ID读取	
			mv.setViewName("system/integral/integral_info_edit");
			mv.addObject("msg", "edit1");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}
	
	// 删除首页图片
	@RequestMapping(value = "/deltp1")
	@ResponseBody
	public String deltp1(PrintWriter out) {
		logBefore(logger, "删除图片");
		try {
			PageData pd = new PageData();
			pd = this.getPageData();
			
			String PATH = pd.getString("IMG"); // 图片路径
			if(PATH!=null){
				String path[] = PATH.split("uploadFiles");
				if(path.length > 1){
					String DPath = "uploadFiles"+path[1];
					DelAllFile.delFolder(PathUtil.getClasspath() + DPath); // 删除后台图片
				}
				
					pd=integral_InfoService.findById(pd);
					pd.put("IMG", "");
					integral_InfoService.edit(pd);
			}
			
			//out.write("success");
			out.close();
			return "success";
		} catch (Exception e) {
			logger.error(e.toString(), e);
			return "fail";
		}
	}
	
	
	/**
	 * 新增
	 */
	@RequestMapping(value="/save1")
	public ModelAndView save1() throws Exception{
		logBefore(logger, "新增图谱详情");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("INTEGRAL_INFO_ID", this.get32UUID());	//主键
		pd.put("STATUS", "0");
		integral_InfoService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 去新增页面
	 */
	@RequestMapping(value="/goAdd1")
	public ModelAndView goAdd1(){
		logBefore(logger, "新增图谱页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			mv.addObject("pd", pd);
			mv.setViewName("system/integral/integral_info_edit");
			mv.addObject("msg", "save1");
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}	
	
	/**
	 * 列表
	 */
	@RequestMapping(value="/show")
	public ModelAndView show(Page page){
		logBefore(logger, "查询图谱详情列表");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} 
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			page.setPd(pd);
			List<PageData> varList=integral_InfoService.datalistPage(page);
			mv.setViewName("system/integral/integral_info_list");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
			mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
		logBefore(logger, "删除商品");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			integralService.delete(pd);
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		
	}
	// 删除首页图片
	@RequestMapping(value = "/deltp")
	@ResponseBody
	public String deltp(PrintWriter out) {
		logBefore(logger, "删除图片");
		try {
			PageData pd = new PageData();
			pd = this.getPageData();
			
			String PATH = pd.getString("IMG"); // 图片路径
			if(PATH!=null){
				String path[] = PATH.split("uploadFiles");
				if(path.length > 1){
					String DPath = "uploadFiles"+path[1];
					DelAllFile.delFolder(PathUtil.getClasspath() + DPath); // 删除后台图片
				}
				
					pd=integralService.findById(pd);
					pd.put("IMG", "");
					integralService.edit(pd);
			}
			
			//out.write("success");
			out.close();
			return "success";
		} catch (Exception e) {
			logger.error(e.toString(), e);
			return "fail";
		}
	}
	
	/**
	 * 修改
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, "修改商品");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		integralService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 去修改页面
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit(){
		logBefore(logger, "去修改商品页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {	
			pd = integralService.findById(pd);	//根据ID读取	
			mv.setViewName("system/integral/integral_edit");
			mv.addObject("msg", "edit");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}
	
	/**
	 * 新增
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, "新增图谱");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("INTEGRAL_ID", this.get32UUID());	//主键
		pd.put("STATUS", "0");
		integralService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 去新增页面
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd(){
		logBefore(logger, "新增图谱页面");
		ModelAndView mv = this.getModelAndView();
		try {
			mv.setViewName("system/integral/integral_edit");
			mv.addObject("msg", "save");
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}

	
	/**
	 * 列表
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		logBefore(logger, "查询图谱列表");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} 
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			page.setPd(pd);
			List<PageData> varList=integralService.datalistPage(page);
			mv.setViewName("system/integral/integral_list");
			mv.addObject("varList", varList);
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
