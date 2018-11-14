package com.fh.controller.system.special;

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
import com.fh.service.system.post.PostService;
import com.fh.service.system.post_img.Post_ImgService;
import com.fh.service.system.post_info.Post_InfoService;
import com.fh.service.system.special.SpecialService;
import com.fh.service.system.user.UserService;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.DateUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;

@Controller
@RequestMapping(value="special",produces="text/html;charset=UTF-8")
public class SpecialController extends BaseController{
	
	@Resource(name="specialService")
	private SpecialService specialService;
	@Resource(name="postService")
	private PostService postService;
	@Resource(name="post_InfoService")
	private Post_InfoService post_InfoService;
	@Resource(name="post_ImgService")
	private Post_ImgService post_ImgService;
	@Resource(name="userService")
	private UserService userService;
	
	

	/**
	 * 批量删除
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() {
		logBefore(logger, "批量删除帖子");
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
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
//		pd.put("CONTENT", "");	//产品描述 默认为空
		//String DETAILS1= pd.getString("editorValue").toString().replace("localhost", "192.168.31.193");
		//String DETAILS1= pd.getString("editorValue").toString().replace("localhost", "101.200.130.60");
		pd.put("MESSAGE","<style>img{max-width:100%;}</style>"+pd.getString("editorValue").toString());
		pd.put("KEYWORD2", "");
		postService.edit(pd);
		post_InfoService.edit(pd);
		post_ImgService.delete(pd);
/*		String a[]=pd.getString("editorValue").toString().split("src");
		post_ImgService.delete(pd);
		if(a.length>1){
			System.err.println(a[1].substring(2, 95));
			PageData pd3=new PageData();
			pd3.put("TID", pd.getString("TID"));
			pd3.put("IMG", a[1].substring(2, 95).toString().split("\"")[0]);
			pd3.put("ORDE_BY", 1);
			pd3.put("DATE", DateUtil.getTime());
			post_ImgService.save(pd3);	
		}*/
		PageData pd3=new PageData();
		pd3.put("TID", pd.getString("TID"));
		pd3.put("IMG",pd.getString("IMG"));
		pd3.put("ORDE_BY", 1);
		pd3.put("DATE", DateUtil.getTime());
		post_ImgService.save(pd3);
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
			List<PageData> list=post_ImgService.findList(pd);
			
			pd = postService.findById(pd);	//根据ID读取	
			if(list!=null&&list.size()!=0){
				pd.put("IMG1", list.get(0).getString("IMG"));
			}
			mv.setViewName("system/special/special_edit");
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
		logBefore(logger, "新增帖子");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		PageData pd1=new PageData();
		pd = this.getPageData();
		User u = Jurisdiction.getCurrentUserID();//获取当前的USER_ID
		String TID=this.get32UUID();
		pd1.put("USER_ID", u.getUSER_ID());
		pd1=userService.findByUiId(pd1);
		pd.put("TID", TID);
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
		pd.put("KEYWORD2", "");
		pd.put("URL", "");
		pd.put("HUODONG", "2");
		pd.put("HUODONG1", "");
		pd.put("BROWSE", "1");
		pd.put("POST_SPECIAL_TYPE_ID", "");
		pd.put("ORDE_BY", "0");
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
		//String DETAILS=pd.getString("editorValue").toString().replace("src=\"", "src=\"http://read.html5.qq.com/image?src=forum&q=5&r=0&imgflag=7&imageUrl=");
		pd2.put("MESSAGE", "<style>img{max-width:100%;}</style>"+pd.getString("editorValue").toString());
		pd2.put("SUBJECT", pd.getString("SUBJECT"));
		pd2.put("YUE", DateUtil.getDay3());
		post_InfoService.save(pd2);
		PageData pd3=new PageData();
		pd3.put("TID", pd.getString("TID"));
		pd3.put("IMG",pd.getString("IMG"));
		pd3.put("ORDE_BY", 1);
		pd3.put("DATE", DateUtil.getTime());
		post_ImgService.save(pd3);
	/*	String a[]=pd.getString("editorValue").toString().split("src");
		if(a.length>1){
			System.err.println(a[1].substring(2, 95));
			PageData pd3=new PageData();
			pd3.put("TID", pd.getString("TID"));
			pd3.put("IMG", a[1].substring(2, 95).toString().split("\"")[0]);
			pd3.put("ORDE_BY", 1);
			pd3.put("DATE", DateUtil.getTime());
			post_ImgService.save(pd3);	
		}*/
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
		PageData pd=new PageData();
		pd=this.getPageData();
		try {
			mv.setViewName("system/special/special_edit");
			mv.addObject("msg", "save");
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
		logBefore(logger, "查询帖子列表");
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
			List<PageData>	varList = postService.data2listPage(page);	//列出Pro_Info列表
			//List<PageData>	list=pro_infoService.findByShopId(pd);//产品
			mv.setViewName("system/special/special_list");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
			mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	
	/**
	 * 批量删除
	 *//*
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() {
		logBefore(logger, "批量删除专题帖子");
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				specialService.deleteAll(ArrayDATA_IDS);
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
	
	*//**
	 * 删除
	 *//*
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
		logBefore(logger, "删除专题帖子");
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			specialService.delete(pd);
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		
	}
	
	*//**
	 * 新增
	 *//*
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, "新增专题帖子");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("SPECIAL_ID", this.get32UUID());	//主键
		pd.put("MESSAGE", "<style>img {max-width: 100%;}</style>"+pd.getString("editorValue"));
		pd.put("DATE", DateUtil.getTime());
		pd.put("TSTATUS", "0");
		specialService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd(){
		logBefore(logger, "去新增专题帖子页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd=new PageData();
		pd=this.getPageData();
		try {
			mv.setViewName("system/special/special_edit");
			mv.addObject("msg", "save");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}
	
	
	*//**
	 * 修改
	 *//*
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, "修改帖子专题");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("MESSAGE", "<style>img {max-width: 100%;}</style>"+pd.getString("editorValue"));
		specialService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	*//**
	 * 去修改页面
	 *//*
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit(){
		logBefore(logger, "去修改专题帖子页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {	
			pd = specialService.findById(pd);	//根据ID读取	
			mv.setViewName("system/special/special_edit");
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
		logBefore(logger, "查询专题帖子列表");
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
			List<PageData>	varList = specialService.list(page);	//列出Pro_Info列表
			//List<PageData>	list=pro_infoService.findByShopId(pd);//产品
			mv.setViewName("system/special/special_list");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
			mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
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
