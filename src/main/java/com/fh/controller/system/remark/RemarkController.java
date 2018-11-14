package com.fh.controller.system.remark;

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
import com.fh.service.system.remark.RemarkService;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.DelAllFile;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.fh.util.PathUtil;

@Controller
@RequestMapping(value="remark",produces="text/html;charset=UTF-8")
public class RemarkController extends BaseController{
	String menuUrl = "remark/list.do"; //菜单地址(权限用)	
	
	@Resource(name="remarkService")
	private RemarkService remarkService;
	
	
	// 删除首页图片
	@RequestMapping(value = "/deltpp")
	@ResponseBody
	public String deltpp(PrintWriter out) {
		logBefore(logger, "删除图片");
		try {
			PageData pd = new PageData();
			pd = this.getPageData();
			
			String PATH = pd.getString("TIMG"); // 图片路径
			if(PATH!=null&&PATH!=""){
				String path[] = PATH.split("uploadFiles");
				if(path.length > 1){
					String DPath = "uploadFiles"+path[1];
					DelAllFile.delFolder(PathUtil.getClasspath() + DPath); // 删除后台图片
				}
					pd=remarkService.findById(pd);
					pd.put("TIMG", "");
					remarkService.edit(pd);
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
								pd=remarkService.findById(pd);
								pd.put("IMG", "");
								remarkService.edit(pd);
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
	 * 去修改页面单价
	 */
	@RequestMapping(value="/goEditp")
	@ResponseBody
	public String goEditp(){
		logBefore(logger, "去修改页面单价");
		PageData pd = new PageData();
		pd = this.getPageData();
		String PRICE= pd.getString("PRICE");
		try {
			pd = remarkService.findById(pd);	//根据ID读取
			pd.put("PRICE", PRICE);
			remarkService.edit(pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return "success";
	}
	/**
	 * 快速修改库存
	 */
	@RequestMapping(value="/goEditK")
	@ResponseBody
	public String goEditK(){
		logBefore(logger, "快速修改库存");
		PageData pd = new PageData();
		pd = this.getPageData();
		String KUCUN= pd.getString("KUCUN");
		try {
			pd = remarkService.findById(pd);	//根据ID读取
			pd.put("KUCUN", KUCUN);
			remarkService.edit(pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return "success";
	}
	
	/**
	 * 去修改页面单价
	 */
	@RequestMapping(value="/goEditpp")
	@ResponseBody
	public String goEditpp(){
		logBefore(logger, "去修改页面种植户单价");
		PageData pd = new PageData();
		pd = this.getPageData();
		String PLANT_PRICE= pd.getString("PLANT_PRICE");
		try {
			pd = remarkService.findById(pd);	//根据ID读取
			pd.put("PLANT_PRICE", PLANT_PRICE);
			remarkService.edit(pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return "success";
	}
	
	/**
	 * 修改
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, "修改商品规格");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		remarkService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 去修改页面
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit(){
		logBefore(logger, "去修改商品规格");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {	
			pd = remarkService.findById(pd);	//根据ID读取	
			mv.setViewName("system/remark/remark_edit");
			mv.addObject("msg", "edit");
			mv.addObject("pd", pd);
		} catch (Exception e) {
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
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			remarkService.delete(pd);
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
		logBefore(logger, "批量删除Pro_Info");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "dell")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				remarkService.deleteAll(ArrayDATA_IDS);
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
	 * 新增
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, "新增商品");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("REMARK_ID", this.get32UUID());	//主键
		remarkService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 去新增页面
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd(){
		logBefore(logger, "去新增商品规格页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd=new PageData();
		pd=this.getPageData();
		try {
			mv.setViewName("system/remark/remark_edit");
			mv.addObject("msg", "save");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}
	
	
	@RequestMapping(value="show")
	public ModelAndView show(){
		logBefore(logger, "显示商品规格");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			Page page=new Page();
			page.setPd(pd);
			List<PageData>	varList = remarkService.findList(pd);	//根据ID读取
			mv.addObject("varList", varList);
			mv.setViewName("system/remark/remark_list");
			mv.addObject("pd", pd);
			mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		logBefore(logger, "显示商品规格");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} 
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			page.setPd(pd);
			List<PageData>	varList = remarkService.list(page);	//列出Pro_Info列表
			//List<PageData>	list=pro_infoService.findByShopId(pd);//产品
			mv.setViewName("system/remark/remark_list");
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
