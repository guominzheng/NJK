package com.fh.controller.system.wenda_tiezi;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.ws.rs.core.Response.Status;

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
import com.fh.entity.system.User;
import com.fh.service.system.activity.ActivityService;
import com.fh.service.system.post.PostService;
import com.fh.service.system.post_special.Post_SpecialService;
import com.fh.service.system.post_special_type.Post_Special_TypeService;
import com.fh.service.system.product.ProductService;
import com.fh.service.system.wenda_shijuan.WenDa_ShiJuanService;
import com.fh.service.system.wenda_tiezi.WenDaTieZiService;
import com.fh.util.Const;
import com.fh.util.DateUtil;
import com.fh.util.DelAllFile;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.fh.util.PathUtil;

@Controller
@RequestMapping(value="wendaTieZi",produces="text/html;charset=UTF-8")
public class WenDaTieZiController extends BaseController{
	String menuUrl = "wendaTieZi/list.do"; //菜单地址(权限用)
	@Resource(name="wenDaTieZiService")
	private WenDaTieZiService wenDaTieZiService;
	@Resource(name="postService")
	private PostService postService;
	@Resource(name="productService")
	private ProductService productService;
	@Resource(name="post_SpecialService")
	private Post_SpecialService post_SpecialService;
	@Resource(name="activityService")
	private ActivityService activityService;
	@Resource(name="wenDa_ShiJuanService")
	private WenDa_ShiJuanService wenDa_ShiJuanService;
	
	
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
		logBefore(logger, "删除问答帖子");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			wenDaTieZiService.delete(pd);
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		
	}
	
	/**
	 * 修改置顶
	 */
	@RequestMapping(value="/editTop")
	public void editTop(PrintWriter out){
		logBefore(logger, "修改置顶");
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			wenDaTieZiService.editGStatus(pd);
			wenDaTieZiService.editGStatuss(pd);
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		
	}
	
	
	// 删除首页图片
			@RequestMapping(value = "/deltpp")
			@ResponseBody
			public String deltpp(PrintWriter out) {
				logBefore(logger, "删除帖子分享图");
				try {
					PageData pd = new PageData();
					pd = this.getPageData();
					String PATH = pd.getString("FIMG"); // 图片路径
					if(PATH!=null&&PATH!=""){
						String path[] = PATH.split("uploadFiles");
						if(path.length > 1){
							String DPath = "uploadFiles"+path[1];
							DelAllFile.delFolder(PathUtil.getClasspath() + DPath); // 删除后台图片
						}
						PageData pd_w=wenDaTieZiService.findById(pd);
						pd_w.put("FIMG", "");
						wenDaTieZiService.edit(pd_w);
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
				
				String PATH = pd.getString("IMG"); // 图片路径
				if(PATH!=null&&PATH!=""){
					String path[] = PATH.split("uploadFiles");
					if(path.length > 1){
						String DPath = "uploadFiles"+path[1];
						DelAllFile.delFolder(PathUtil.getClasspath() + DPath); // 删除后台图片
					}
					PageData pd_w=wenDaTieZiService.findById(pd);
					pd_w.put("IMG", "");
					wenDaTieZiService.edit(pd_w);
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
		logBefore(logger, "修改问答帖子");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
			pd.put("DATE", DateUtil.getTime());	//日期
			pd.put("MESSAGE","<style>img{max-width:100%;}</style>"+pd.getString("editorValue"));//获取编辑器的内容
				if(pd.getString("STATUS").equals("2")){
					pd.put("FID", pd.getString("ID"));
					PageData pd1=post_SpecialService.findById(pd);
					pd.put("URL", pd1.getString("USER_ID"));
				}else if(pd.getString("STATUS").equals("4")){
					pd.put("WENDA_SHIJUAN_ID", pd.getString("ID"));
					PageData pd1=wenDa_ShiJuanService.findById(pd);
					pd.put("URL",pd1.getString("WENDA_SHIJUAN_NAME"));
				}else if(pd.getString("STATUS").equals("3")){
					pd.put("ACTIVITY_ID", pd.getString("ID"));
					PageData pd1=activityService.findById(pd);
					pd.put("URL",pd1.getString("STATUS"));
				}else if(pd.getString("STATUS").equals("5")){
					
				}else{
					pd.put("URL", "");
				}
				System.err.println(pd.getString("STATUS"));
		wenDaTieZiService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 去修改页面
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit(){
		logBefore(logger, "去修改问答帖子页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			Page page=new Page();
			page.setPd(pd);
			page.setShowCount(20);
			List<PageData> varList=new ArrayList();
			pd = wenDaTieZiService.findById(pd);	//根据ID读取
			if(pd.getString("STATUS").equals("0")){
				varList=postService.list(page);
			}else if(pd.getString("STATUS").equals("1")){
				varList=productService.list2(page);
			}else if(pd.getString("STATUS").equals("2")){
				varList=post_SpecialService.list(page);
			}else if(pd.getString("STATUS").equals("3")){
				varList=activityService.list(page);
			}else if(pd.getString("STATUS").equals("4")){
				varList=wenDa_ShiJuanService.list(page);
			}
			mv.addObject("varList", varList);
			mv.setViewName("system/wendaTieZi/wendaTieZi_edit");
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
		logBefore(logger, "新增问答帖子");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("DATE", DateUtil.getTime());
		pd.put("GSTATUS", "0");
		pd.put("SHUZI", "0");
		pd.put("MESSAGE", "<style>img{max-width:100%;}</style>"+pd.getString("editorValue").toString());
		wenDaTieZiService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	/**
	 * 去新增页面
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd(){
		logBefore(logger, "去新增问答帖子页面");
		ModelAndView mv = this.getModelAndView();
		try {
			mv.setViewName("system/wendaTieZi/wendaTieZi_edit");
			mv.addObject("msg", "save");
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value="/list",produces="text/html;charset=UTF-8")
	public ModelAndView list(Page page){
		logBefore(logger, "查询问答帖子");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			page.setPd(pd);
			List<PageData>	varList = wenDaTieZiService.list(page);	//列出Order_Info列表
			mv.setViewName("system/wendaTieZi/wendaTieZi_list");
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
