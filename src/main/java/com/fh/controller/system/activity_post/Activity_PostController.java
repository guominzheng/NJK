package com.fh.controller.system.activity_post;

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
import com.fh.service.system.activity.ActivityService;
import com.fh.service.system.activity_post.Activity_PostService;
import com.fh.service.system.activity_room.Activity_RoomService;
import com.fh.service.system.comment_activity.Comment_ActivityService;
import com.fh.service.system.comment_activity_user.Comment_Activity_UserService;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.DateUtil;
import com.fh.util.DelAllFile;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.fh.util.PathUtil;

@Controller
@RequestMapping(value="activity_post")
public class Activity_PostController extends BaseController{

	
	String menuUrl = "activity/list.do"; //菜单地址(权限用)
	
	@Resource(name="activity_PostService")
	private Activity_PostService activity_PostService;
	@Resource(name="activityService")
	private ActivityService activityService;
	@Resource(name="comment_ActivityService")
	private Comment_ActivityService comment_ActivityService;
	@Resource(name="comment_Activity_UserService")
	private Comment_Activity_UserService comment_Activity_UserService;
	@Resource(name="activity_RoomService")
	private Activity_RoomService activity_RoomService;
	
	
	
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
				pd=activity_RoomService.findById1(pd);
				pd.put("IMG", "");
				activity_RoomService.edit(pd);
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
	@RequestMapping(value="/Usave")
	public ModelAndView Usave() throws Exception{
		logBefore(logger, "新增直播室");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String ACTIVITY_ROOM_ID=this.get32UUID();
		pd.put("ACTIVITY_ROOM_ID", ACTIVITY_ROOM_ID);
		pd.put("BEIYONG1", "");
		pd.put("BEIYONG2", "");
		pd.put("BEIYONG3", "");
		pd.put("BEIYONG4", "");
		pd.put("BEIYONG5", "");
		pd.put("DATE", DateUtil.getTime());
		pd.put("VIEWS", "0");
		pd.put("HUIFU", "0");
		activity_RoomService.save(pd);
		List<PageData> list=activity_PostService.findList3(pd);
		for(int i=0;i<list.size();i++){
			list.get(i).put("ACTIVITY_ROOM_ID", ACTIVITY_ROOM_ID);
			activity_PostService.editRoom(list.get(i));
		}
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	
	
	/**
	 * 去新增页面
	 */
	@RequestMapping(value="/AddUser")
	public ModelAndView AddUser(){
		logBefore(logger, "去新增商品页面");
		PageData pd=new PageData();
		pd=this.getPageData();
		ModelAndView mv = this.getModelAndView();
		try {
			mv.setViewName("system/activity_post/activity_AddUser");
			mv.addObject("msg", "Usave");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}
	
	/**
	 * 新增
	 */
	@RequestMapping(value="/Uedit")
	public ModelAndView Uedit() throws Exception{
		logBefore(logger, "新增直播室");
		ModelAndView mv = this.getModelAndView();
		PageData pd=new PageData();
		pd = this.getPageData();
		activity_RoomService.editS(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 去新增页面
	 */
	@RequestMapping(value="/editUser")
	public ModelAndView editUser(){
		logBefore(logger, "去新增商品页面");
		PageData pd=new PageData();
		pd=this.getPageData();
		ModelAndView mv = this.getModelAndView();
		try {
			PageData pd1=activity_RoomService.findById1(pd);
			mv.setViewName("system/activity_post/activity_AddUser");
			mv.addObject("msg", "Uedit");
			mv.addObject("pd", pd1);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}
	
	
	/**
	 * 批量删除
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() {
		logBefore(logger, "批量删除活动帖子");
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				activity_PostService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, "删除活动帖子");
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			PageData pd2=activity_PostService.findById(pd);
			PageData pd1=activityService.findById(pd2);
			pd1.put("VIEWS", Integer.valueOf(pd1.getString("VIEWS"))-1);
			activityService.editViews(pd1);
			activity_PostService.delete(pd);
			comment_ActivityService.deleteAll(pd);
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		
	}
	
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/Csdelete")
	public void Csdelete(PrintWriter out){
		logBefore(logger, "删除直播室");
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			activity_RoomService.delete1(pd);
			activity_PostService.delete1(pd);
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		
	}
	
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/deleteA")
	public void deleteA(PrintWriter out){
		logBefore(logger, "删除活动帖子");
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			PageData pd2=comment_ActivityService.findById(pd);
			PageData pd1=activity_PostService.findById(pd2);
			pd1.put("VIEWS", Integer.valueOf(pd1.getString("VIEWS"))-1);
			activity_PostService.editViews(pd1);
			comment_ActivityService.delete(pd);
			comment_ActivityService.deletePid(pd);
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/deleteC")
	public void deleteC(PrintWriter out){
		logBefore(logger, "删除活动帖子");
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			comment_Activity_UserService.delete(pd);
			comment_Activity_UserService.deletePid(pd);
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		
	}
	
	/**
	 * 新增
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, "新增活动帖子");
		User u = Jurisdiction.getCurrentUserID();//获取当前的USER_ID
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("ACTIVITY_POST_ID", this.get32UUID());	//主键
		pd.put("MESSAGE", /*"<style>img {max-width: 100%;}</style>"+*/pd.getString("editorValue"));
		pd.put("DATE", DateUtil.getTime());
		pd.put("USER_ID", u.getUSER_ID());
		pd.put("SUBJECT", "");
		pd.put("PROMPT", "");
		pd.put("STATUS", "1");
		pd.put("YUE", DateUtil.getDay3());
		pd.put("VIEWS", "0");
		pd.put("HUIFU", "0");
		activity_PostService.save(pd);
		PageData pd1=activityService.findById(pd);
		pd1.put("VIEWS", Integer.valueOf(pd1.getString("VIEWS"))+1);
		activityService.editViews(pd1);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd(){
		logBefore(logger, "去新增活动帖子页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd=new PageData();
		pd=this.getPageData();
		try {
			mv.setViewName("system/activity_post/activity_post_edit");
			mv.addObject("msg", "save");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}
	
	
	/**
	 * 修改
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, "修改活动专题");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("MESSAGE", /*"<style>img {max-width: 100%;}</style>"+*/pd.getString("editorValue"));
		activity_PostService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 去修改页面
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit(){
		logBefore(logger, "去修改活动帖子页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {	
			pd = activity_PostService.findById(pd);	//根据ID读取	
			mv.setViewName("system/activity_post/activity_post_edit");
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
	@RequestMapping(value="/show")
	public ModelAndView show(Page page){
		logBefore(logger, "查询直播帖子评论");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			pd.put("STATUS", "0");
			page.setPd(pd);
			List<PageData>	varList = comment_ActivityService.list(page);	//列出Pro_Info列表
			//List<PageData>	list=pro_infoService.findByShopId(pd);//产品
			mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
			mv.setViewName("system/activity_post/activity_post_show");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	
	
	
	/**
	 * 列表
	 */
	@RequestMapping(value="/Pshow")
	public ModelAndView Pshow(Page page){
		logBefore(logger, "查询用户发布活动帖子列表");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			page.setPd(pd);
			List<PageData>	varList = activity_PostService.list(page);	//列出Pro_Info列表
			//List<PageData>	list=pro_infoService.findByShopId(pd);//产品
			mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
			mv.setViewName("system/activity_post/activity_post_list1");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value="/Cshow")
	public ModelAndView Cshow(Page page){
		logBefore(logger, "查询直播评论用户列表");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			pd.put("STATUS", "0");
			page.setPd(pd);
			List<PageData>	varList = comment_Activity_UserService.list(page);	//列出Pro_Info列表
			mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
			mv.setViewName("system/activity_post/activity_post_Cshow");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	
	/**
	 * 列表
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		logBefore(logger, "查询活动帖子列表");
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
			pd.put("STATUS", "1");
			page.setPd(pd);
			List<PageData>	varList = activity_PostService.userlistPage1(page);	//列出Pro_Info列表
			//List<PageData>	list=pro_infoService.findByShopId(pd);//产品
			mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
			mv.setViewName("system/activity_post/activity_post_list");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
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
