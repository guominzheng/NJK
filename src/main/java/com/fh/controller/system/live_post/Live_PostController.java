package com.fh.controller.system.live_post;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.fh.entity.system.User;
import com.fh.service.system.live_post.Live_PostService;
import com.fh.service.system.post.PostService;
import com.fh.service.system.post_img.Post_ImgService;
import com.fh.service.system.post_info.Post_InfoService;
import com.fh.service.system.user.UserService;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.DateUtil;
import com.fh.util.DelAllFile;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.fh.util.PathUtil;

@Controller
@RequestMapping(value="live_post",produces="text/html;charset=UTF-8")
public class Live_PostController extends BaseController{
	
	String menuUrl = "live_post/list.do"; //菜单地址(权限用)
	@Resource(name="postService")
	private PostService postService;
	@Resource(name="post_InfoService")
	private Post_InfoService post_InfoService;
	@Resource(name="userService")
	private UserService userService;
	@Resource(name="post_ImgService")
	private Post_ImgService post_ImgService;
	
	
	/**
	 * 批量删除
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() {
		logBefore(logger, "批量删除帖子");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "dell")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				postService.deleteAll(ArrayDATA_IDS);
				post_InfoService.deleteAll(ArrayDATA_IDS);
				pd.put("msg", "ok");
			}else{
				pd.put("msg", "no");
			}
			pdList.add(pd);
			map.put("list", pdList);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		} finally {
			logAfter(logger);
		}
		return AppUtil.returnObject(pd, map);
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
			postService.delete(pd);
			post_InfoService.delete(pd);
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		
	}
	
	/**
	 * 修改
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, "修改帖子");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
//		pd.put("CONTENT", "");	//产品描述 默认为空
		//String DETAILS1= pd.getString("editorValue").toString().replace("localhost", "192.168.31.193");
		//String DETAILS1= pd.getString("editorValue").toString().replace("localhost", "101.200.130.60");
		pd.put("MESSAGE","<style>img{max-width:100%;}</style>"+pd.getString("editorValue").toString());
		postService.edit(pd);
		post_InfoService.edit(pd);
		String a[]=pd.getString("editorValue").toString().split("src");
		post_ImgService.delete(pd);
		for(int i=1;i<a.length;i++){
			System.err.println(a[i].substring(2, 95));
			PageData pd1=new PageData();
			pd1.put("TID", pd.getString("TID"));
			pd1.put("IMG", a[i].substring(2, 95).toString().split("\"")[0]);
			post_ImgService.save(pd1);
		}
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	
	/**
	 * 去修改页面
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit(){
		logBefore(logger, "去今日活动帖子页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {	
			pd = postService.findById(pd);	//根据ID读取	
			mv.setViewName("system/live_post/live_post_edit");
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
		logBefore(logger, "新增商品");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		PageData pd1=new PageData();
		pd = this.getPageData();
		User u = Jurisdiction.getCurrentUserID();//获取当前的USER_ID
		String TID=this.get32UUID();
		pd1.put("USER_ID", u.getUSER_ID());
		pd1=userService.findByUiId(pd1);
		pd.put("TID", TID);
		pd.put("FID", "0");
		pd.put("NAME", pd1.getString("NAME"));
		pd.put("DATE", DateUtil.getTime());
		pd.put("VIEWS", "0");
		pd.put("USER_ID", u.getUSER_ID());
		pd.put("HUIFU", "0");
		pd.put("TSTATUS", "0");
		pd.put("PRODUCT_ID", "0");
		pd.put("ESTATUS", "0");
		pd.put("ACTIVITY_ID", "");
		pd.put("MSTATUS", "0");
		if(pd.getString("URL")==null){
			pd.put("URL", "");
		}
		pd.put("HUODONG", "0");
		pd.put("HUODONG1", "0");
		pd.put("BROWSE", "0");
		postService.save(pd);
		PageData pd2=new PageData();
		pd2.put("PID", this.get32UUID());
		pd2.put("TID", TID);
		pd2.put("FID", "0");
		pd2.put("FIRST", "1");
		pd2.put("NAME", pd1.getString("NAME"));
		pd2.put("USER_ID", "0");
		pd2.put("DATE", DateUtil.getTime());
		//String DETAILS1= pd.getString("editorValue").toString().replace("localhost", "192.168.31.193");
		//String DETAILS1= pd.getString("editorValue").toString().replace("localhost", "101.200.130.60");
		pd2.put("MESSAGE", "<style>img{max-width:100%;}</style>"+pd.getString("editorValue").toString());
		pd2.put("SUBJECT", pd.getString("SUBJECT"));
		pd2.put("YUE", DateUtil.getDay3());
		post_InfoService.save(pd2);
		String a[]=pd.getString("editorValue").toString().split("src");
		for(int i=1;i<a.length;i++){
			System.err.println(a[i].substring(2, 95));
			PageData pd3=new PageData();
			pd3.put("TID", pd.getString("TID"));
			pd3.put("IMG", a[i].substring(2, 95).toString().split("\"")[0]);
			post_ImgService.save(pd3);
		}
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 去新增页面
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd(){
		logBefore(logger, "去新增商品页面");
		ModelAndView mv = this.getModelAndView();
		try {
			mv.setViewName("system/live_post/live_post_edit");
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
		logBefore(logger, "查询帖子列表");
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
			List<PageData>	varList = postService.data1listPage(page);	//列出Pro_Info列表
			//List<PageData>	list=pro_infoService.findByShopId(pd);//产品
			mv.setViewName("system/live_post/live_post_list");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
			mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*	@Resource(name="live_PostService")
	private Live_PostService live_PostService;
	
	@RequestMapping(value="show")
	public ModelAndView show(){
		logBefore(logger, "显示滚动图片");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			pd = live_PostService.findById(pd);	//根据ID读取	
			mv.addObject("pd", pd);
			mv.setViewName("system/live_post/live_post_show");
			mv.addObject("pd", pd);
			mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	// 删除首页图片
				@RequestMapping(value = "/deltpp")
				@ResponseBody
				public String deltpp(PrintWriter out) {
					logBefore(logger, "删除图片");
					try {
						PageData pd = new PageData();
						pd = this.getPageData();
						
						String PATH = pd.getString("COVER_IMG"); // 图片路径
						String path[] = PATH.split("uploadFiles");
						if(path.length > 1){
							String DPath = "uploadFiles"+path[1];
							DelAllFile.delFolder(PathUtil.getClasspath() + DPath); // 删除后台图片
						}
						
						if (PATH != null) {
							pd=live_PostService.findById(pd);
							pd.put("COVER_IMG", "");
							live_PostService.edit(pd);
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
						String path[] = PATH.split("uploadFiles");
						if(path.length > 1){
							String DPath = "uploadFiles"+path[1];
							DelAllFile.delFolder(PathUtil.getClasspath() + DPath); // 删除后台图片
						}
						
						if (PATH != null) {
							pd=live_PostService.findById(pd);
							pd.put("IMG", "");
							live_PostService.edit(pd);
						}
						//out.write("success");
						out.close();
						return "success";
					} catch (Exception e) {
						logger.error(e.toString(), e);
						return "fail";
					}
				}
	
	*//**
	 * 新增
	 *//*
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, "新增活动帖子");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String DETAILS=pd.getString("editorValue");
		String DETAILS1=DETAILS.replace("localhost", "192.168.31.193");
		pd.put("MESSAGE", "<style>img{max-width:100%;}</style>"+DETAILS1);
		pd.put("LIVE_POST_ID", this.get32UUID());	//主键
		pd.put("VIEWS", "0");
		pd.put("DATE", DateUtil.getTime());
		pd.put("HUIFU", "0");
		live_PostService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	*//**
	 * 删除
	 *//*
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
		logBefore(logger, "删除活动帖子");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			live_PostService.delete(pd);
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		
	}
	
	*//**
	 * 修改
	 *//*
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, "修改活动帖子");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String DETAILS=pd.getString("editorValue");
		String DETAILS1=DETAILS.replace("localhost", "192.168.31.193");
		pd.put("MESSAGE", "<style>img{max-width:100%;}</style>"+DETAILS1);
//		pd.put("CONTENT", "");	//产品描述 默认为
		live_PostService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	*//**
	 * 去新增页面
	 *//*
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd(){
		logBefore(logger, "去新增活动帖子页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd=new PageData();
		try {
			mv.setViewName("system/live_post/live_post_edit");
			mv.addObject("msg", "save");
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}	
	
	*//**
	 * 去修改页面
	 *//*
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit(){
		logBefore(logger, "去修改活动帖子页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {	
			pd = live_PostService.findById(pd);	//根据ID读取	
			mv.setViewName("system/live_post/live_post_edit");
			mv.addObject("msg", "edit");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}
	
	
	*//**
	 * 列表
	 *//*
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		logBefore(logger, "查询活动帖子列表");
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
			List<PageData>	varList = live_PostService.list(page);	//列出Pro_Info列表
			mv.setViewName("system/live_post/live_post_list");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
			mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	
	*//**
	 * 批量删除
	 *//*
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() {
		logBefore(logger, "批量删除Pro_Info");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "dell")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				live_PostService.deleteAll(ArrayDATA_IDS);
				pd.put("msg", "ok");
			}else{
				pd.put("msg", "no");
			}
			pdList.add(pd);
			map.put("list", pdList);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		} finally {
			logAfter(logger);
		}
		return AppUtil.returnObject(pd, map);
	}*/
	
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
