package com.fh.controller.system.activity;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.fh.util.*;
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
import com.fh.service.system.activity_room.Activity_RoomService;
import com.fh.service.system.user.UserService;
import com.fh.service.system.views_activity.Views_ActivityService;

@Controller
@RequestMapping(value="activity")
public class ActivityComtroller extends BaseController{

	String menuUrl = "activity/list.do"; //菜单地址(权限用)
	@Resource(name="activityService")
	private ActivityService activityService;
	@Resource(name="userService")
	private UserService userService;
	@Resource(name="views_ActivityService")
	private Views_ActivityService views_ActivityService;
	@Resource(name="activity_RoomService")
	private Activity_RoomService activity_RoomService;


	/**
	 * 列表
	 */
	@RequestMapping(value="/show")
	public ModelAndView show(){
		logBefore(logger, "查询用户直播前五名");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		String[] bArray = {"AFD8F8","F6BD0F","8BBA00", "FF8E46", "008E8E","D64646","8E468E","588526","B3AA00","008ED6","9D080D","A186BE"};  
		try{
			pd = this.getPageData();
			List<PageData>	varList = views_ActivityService.findList(pd);	//列出Order_Info列表
			String XML="<graph caption='对比表' xAxisName='用户' yAxisName='点赞数' decimalPrecision='0' formatNumberScale='0'>";
			for(int i=0;i<varList.size();i++){
				XML+="<set name='"+varList.get(i).getString("NAME")+"' value='"+varList.get(i).get("count")+"' color='"+bArray[i]+"'/>";
			}
			XML+="</graph>";
			System.out.println(XML);
			pd.put("strXML", XML);
			mv.setViewName("system/activity/activity_duibi");
			mv.addObject("pd", pd);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
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
				
				pd=activityService.findById(pd);
				pd.put("IMG", "");
				activityService.edit(pd);
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
	 * 删除
	 */
	@RequestMapping(value="/editTop")
	public void editTop(PrintWriter out){
		logBefore(logger, "更改状态");
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			activityService.editStatus(pd);
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/editSpecial_status")
	public void editSpecial_status(PrintWriter out){
		logBefore(logger, "是否为独立直播");
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			activityService.editSpecialStatus(pd);
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		
	}
	
	
	/**
	 * 批量删除
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() {
		logBefore(logger, "批量删除活动直播");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "dell")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				activityService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, "删除活动直播");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			activityService.delete(pd);
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
		logBefore(logger, "新增商品");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		User u = Jurisdiction.getCurrentUserID();//获取当前的USER_ID
		pd.put("ACTIVITY_ID", this.get32UUID());
		pd.put("USER_ID", u.getUSER_ID());
		pd.put("DATE", DateUtil.getTime());
		pd.put("STATUS", "0");
		pd.put("VIEWS", "0");
		pd.put("HUIFU", "0");
		pd.put("SPECIAL_STATUS", "0");
		activityService.save(pd);
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
			mv.setViewName("system/activity/activity_edit");
			mv.addObject("msg", "save");
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}
	
	/**
	 * 去新增页面
	 */
	@RequestMapping(value="/chakan")
	public ModelAndView chakan(){
		logBefore(logger, "去新增商品页面");
		ModelAndView mv = this.getModelAndView();
		try {
			mv.setViewName("system/activity/aa");
			mv.addObject("msg", "save");
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
		logBefore(logger, "修改活动直播");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
//		pd.put("CONTENT", "");	//产品描述 默认为空
		activityService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	
	/**
	 * 去修改页面
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit(){
		logBefore(logger, "去修改活动直播页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {	
			pd = activityService.findById(pd);	//根据ID读取	
			mv.setViewName("system/activity/activity_edit");
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
		logBefore(logger, "查询活动直播列表");
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
			List<PageData>	varList = activityService.list(page);	//列出Pro_Info列表
			//List<PageData>	list=pro_infoService.findByShopId(pd);//产品
			mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
			mv.setViewName("system/activity/activity_list");
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
