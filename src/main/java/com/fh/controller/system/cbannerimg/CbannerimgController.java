package com.fh.controller.system.cbannerimg;

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
import com.fh.service.system.cbannerimg.CbannerimgService;
import com.fh.util.Const;
import com.fh.util.DelAllFile;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.fh.util.PathUtil;

@Controller
@RequestMapping(value="cbannerimg")
public class CbannerimgController extends BaseController{
	String menuUrl = "cbannerimg/list.do"; //菜单地址(权限用)	
	@Resource(name="cbannerimgService")
	private CbannerimgService cbannerimgService;
	
	
	
	// 删除首页图片
			@RequestMapping(value = "/deltpppp")
			@ResponseBody
			public String deltpppp(PrintWriter out) {
				logBefore(logger, "删除图片");
				try {
					PageData pd = new PageData();
					pd = this.getPageData();
					
					String PATH = pd.getString("IMG4"); // 图片路径
					if(PATH!=null&&PATH!=""){
						String path[] = PATH.split("uploadFiles");
						if(path.length > 1){
							String DPath = "uploadFiles"+path[1];
							DelAllFile.delFolder(PathUtil.getClasspath() + DPath); // 删除后台图片
						}
						
							pd=cbannerimgService.findById(pd);
							pd.put("IMG4", "");
							cbannerimgService.edit(pd);
					}
					
					//out.write("success");
					out.close();
					return "success";
				} catch (Exception e) {
					logger.error(e.toString(), e);
					return "fail";
				}
			}
	
	// 删除首页图片
		@RequestMapping(value = "/deltppp")
		@ResponseBody
		public String deltppp(PrintWriter out) {
			logBefore(logger, "删除图片");
			try {
				PageData pd = new PageData();
				pd = this.getPageData();
				
				String PATH = pd.getString("IMG3"); // 图片路径
				if(PATH!=null&&PATH!=""){
					String path[] = PATH.split("uploadFiles");
					if(path.length > 1){
						String DPath = "uploadFiles"+path[1];
						DelAllFile.delFolder(PathUtil.getClasspath() + DPath); // 删除后台图片
					}
					
						pd=cbannerimgService.findById(pd);
						pd.put("IMG3", "");
						cbannerimgService.edit(pd);
				}
				
				//out.write("success");
				out.close();
				return "success";
			} catch (Exception e) {
				logger.error(e.toString(), e);
				return "fail";
			}
		}
	
	// 删除首页图片
	@RequestMapping(value = "/deltpp")
	@ResponseBody
	public String deltpp(PrintWriter out) {
		logBefore(logger, "删除图片");
		try {
			PageData pd = new PageData();
			pd = this.getPageData();
			
			String PATH = pd.getString("IMG2"); // 图片路径
			if(PATH!=null&&PATH!=""){
				String path[] = PATH.split("uploadFiles");
				if(path.length > 1){
					String DPath = "uploadFiles"+path[1];
					DelAllFile.delFolder(PathUtil.getClasspath() + DPath); // 删除后台图片
				}
				
					pd=cbannerimgService.findById(pd);
					pd.put("IMG2", "");
					cbannerimgService.edit(pd);
			}
			
			//out.write("success");
			out.close();
			return "success";
		} catch (Exception e) {
			logger.error(e.toString(), e);
			return "fail";
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
						
						String PATH = pd.getString("IMG1"); // 图片路径
						if(PATH!=null&&PATH!=""){
							String path[] = PATH.split("uploadFiles");
							if(path.length > 1){
								String DPath = "uploadFiles"+path[1];
								DelAllFile.delFolder(PathUtil.getClasspath() + DPath); // 删除后台图片
							}
							
								pd=cbannerimgService.findById(pd);
								pd.put("IMG1", "");
								cbannerimgService.edit(pd);
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
		logBefore(logger, "修改猫屋图片");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		cbannerimgService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 去修改页面
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit(){
		logBefore(logger, "去修改猫屋图片");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {	
			pd = cbannerimgService.findById(pd);	//根据ID读取	
			mv.setViewName("system/cbannerimg/cbannerimg_edit");
			mv.addObject("msg", "edit");
			mv.addObject("pd", pd);
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
		logBefore(logger, "查询猫屋帖子图片");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} 
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			//页面检索
			String KEYWORD = pd.getString("KEYWORD");
			if (null != KEYWORD && !"".equals(KEYWORD)) {
				KEYWORD = KEYWORD.trim();
				pd.put("KEYWORD", KEYWORD);
			}
			page.setPd(pd);
			List<PageData> varList=cbannerimgService.datalistPage(page);
			mv.setViewName("system/cbannerimg/cbannerimg_list");
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
