package com.fh.controller.system.banner;

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

import com.fh.service.system.research.ResearchService;
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
import com.fh.util.AppUtil;
import com.fh.util.DateUtil;
import com.fh.util.DelAllFile;
import com.fh.util.ObjectExcelView;
import com.fh.util.Const;
import com.fh.util.PageData;
import com.fh.util.PathUtil;
import com.fh.util.Tools;
import com.fh.util.Jurisdiction;
import com.fh.service.system.activity.ActivityService;
import com.fh.service.system.banner.BannerService;
import com.fh.service.system.post.PostService;
import com.fh.service.system.post_special.Post_SpecialService;
import com.fh.service.system.product.ProductService;
import com.fh.service.system.wenda_shijuan.WenDa_ShiJuanService;

/** 
 * 类名称：BannerController
 * 创建人：FH 
 * 创建时间：2016-09-08
 */
@Controller
@RequestMapping(value="/banner")
public class BannerController extends BaseController {
	
	String menuUrl = "banner/list.do"; //菜单地址(权限用)
	@Resource(name="bannerService")
	private BannerService bannerService;
	@Resource(name="postService")
	private PostService postService;
	@Resource(name="post_SpecialService")
	private Post_SpecialService post_SpecialService;
	@Resource(name="activityService")
	private ActivityService activityService;
	@Resource(name="productService")
	private ProductService productService;
	@Resource(name="wenDa_ShiJuanService")
	private WenDa_ShiJuanService wenDa_ShiJuanService;
	@Resource(name="researchService")
	private ResearchService researchService;
	
	// 删除图片
	@RequestMapping(value = "/deltp")
	@ResponseBody
	public String deltp(PrintWriter out) {
		logBefore(logger, "删除图片");
		try {
			PageData pd = new PageData();
			pd = this.getPageData();
			
			String PATH = pd.getString("PICTURE"); // 图片路径
			if(PATH!=null){
				String path[] = PATH.split("uploadFiles");
				
				if(path.length > 1){
					String DPath = "uploadFiles"+path[1];
					DelAllFile.delFolder(PathUtil.getClasspath() + DPath); // 删除后台图片
				}
				
					pd=bannerService.findById(pd);
					pd.put("PICTURE", "");
					bannerService.edit(pd);
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
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, "新增Banner");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("BANNER_ID", this.get32UUID());	//主键
		pd.put("DATE", DateUtil.getTime());	//日期
		pd.put("WENDA_TYPE_ID", "");
		bannerService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}


	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
		logBefore(logger, "删除Banner");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			PageData pd1 = bannerService.findById(pd);
			String PATH = pd1.getString("PICTURE"); // 展示图片路径
			String path[] = PATH.split("uploadFiles");
			if(path.length > 1){
				String DPath = "uploadFiles"+path[1];
				DelAllFile.delFolder(PathUtil.getClasspath() + DPath); // 删除后台图片
			}
			bannerService.delete(pd);
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
		logBefore(logger, "修改Banner");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		if(!pd.getString("BANNER_ID").equals("52df6ede350b40ba9eaa9aaabae6cc5f")){
			pd.put("DATE", DateUtil.getTime());	//日期
			pd.put("CONTENT",pd.getString("editorValue"));//获取编辑器的内容
			if(pd.getString("STATUS").equals("0")){
				PageData pd1=postService.findById111(pd);
				pd.put("NAME", pd1.getString("SUBJECT"));
				pd.put("url", "http://m.nongjike.cn/NJK/static/jsp/111.html?tid=" + pd.getString("TID"));
			}else if(pd.getString("STATUS").equals("1")){
				pd.put("PRODUCT_ID", pd.getString("TID"));
				PageData pd1=productService.findById(pd);
				pd.put("NAME", pd1.getString("PRODUCT_NAME"));
				pd.put("url", "http://m.nongjike.cn/NJK/static/jsp/product.html?PRODUCT_ID=" + pd.getString("TID"));
			}else if(pd.getString("STATUS").equals("2")){
				pd.put("FID", pd.getString("TID"));
				PageData pd1=post_SpecialService.findById(pd);
				pd.put("NAME", pd1.getString("SPECIAL"));
				pd.put("url", "http://m.nongjike.cn/NJK/static/jsp/special.html?FID=" + pd.getString("TID"));
			}else if(pd.getString("STATUS").equals("3")){
				pd.put("ACTIVITY_ID", pd.getString("TID"));
				PageData pd1=activityService.findById(pd);
				pd.put("NAME", pd1.getString("SPECIAL"));
				pd.put("url", "http://m.nongjike.cn/NJK/static/jsp/special.html?FID=" + pd.getString("TID"));
			}else if(pd.getString("STATUS").equals("8")){
				pd.put("RESEARCH_ID", pd.getString("TID"));
				PageData pd1=researchService.findById(pd);
				pd.put("NAME", pd1.getString("SUBJECT"));
				if("2".equals(pd1.get("LSTATUS").toString())){
					pd.put("url", "https://www.meitiannongzi.com/nongjike/static/jsp/activity.html?RESEARCH_ID="+ pd1.get("RESEARCH_ID"));
				}else{
					pd.put("url", "http://www.meitiannongzi.com/nongjike/static/jsp/Researc.html?RESEARCH_ID=" + pd1.get("RESEARCH_ID"));
				}
			}
		}else{
			pd.put("NAME", "1");
			if(!pd.getString("STATUS").equals("5")){
				if(pd.getString("STATUS").equals("2")){
					pd.put("FID", pd.getString("TID"));
					PageData pd1=post_SpecialService.findById(pd);
					pd.put("url", pd1.getString("USER_ID"));
					pd.put("WENDA_TYPE_ID","");
				}else if(pd.getString("STATUS").equals("4")){
					pd.put("WENDA_SHIJUAN_ID", pd.getString("TID"));
					PageData pd1=wenDa_ShiJuanService.findById(pd);
					pd.put("url",pd1.getString("WENDA_SHIJUAN_NAME"));
					pd.put("WENDA_TYPE_ID", pd1.getString("WENDA_TYPE_ID"));
				}else if(pd.getString("STATUS").equals("3")){
					pd.put("ACTIVITY_ID", pd.getString("TID"));
					PageData pd1=activityService.findById(pd);
					pd.put("url",pd1.getString("STATUS"));
					pd.put("WENDA_TYPE_ID","");
				}else if(pd.getString("STATUS").equals("8")){
					pd.put("ACTIVITY_ID", pd.getString("TID"));
					PageData pd1=researchService.findById(pd);
					pd.put("url","");
					pd.put("WENDA_TYPE_ID","");
				}else{
					pd.put("WENDA_TYPE_ID","");
					pd.put("url", "");
				}
			}
		}
		bannerService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		logBefore(logger, "列表Banner");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
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
			List<PageData>	varList = bannerService.list(page);	//列出Banner列表
			
			mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
			mv.setViewName("system/banner/banner_list");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * 去新增页面
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd(){
		logBefore(logger, "去新增Banner页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			
			mv.setViewName("system/banner/banner_edit");
			mv.addObject("msg", "save");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}	
	
	/**
	 * 根据一级分类id查二级分类
	 * @throws Exception 
	 */
	@RequestMapping(value="/findList")
	@ResponseBody
	public List<PageData> findList() throws Exception{
		logBefore(logger, "查询二级分类");
		PageData pd = new PageData();
		pd = this.getPageData();
		Page page=new Page();
		page.setPd(pd);
		page.setShowCount(20);
		List<PageData> varList=new ArrayList();
		if(pd.getString("STATUS").equals("0")){
			varList=postService.list(page);
		}else if(pd.getString("STATUS").equals("1")){
			varList=productService.list2(page);
		}else if(pd.getString("STATUS").equals("2")){
			varList=post_SpecialService.list(page);
		}else if(pd.getString("STATUS").equals("3")){
			varList=activityService.list(page);
		}else if(pd.getString("STATUS").equals("4")){
			page.getPd().put("STATUS", "1");
			varList=wenDa_ShiJuanService.list(page);
		}else if(pd.getString("STATUS").equals("8")){
			varList=researchService.dataAlllistPage(page);
		}
		return varList;
	}
	
	/**
	 * 去修改页面
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit(){
		logBefore(logger, "去修改Banner页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			Page page=new Page();
			page.setPd(pd);
			page.setShowCount(20);
			List<PageData> varList=new ArrayList();
			pd = bannerService.findById(pd);	//根据ID读取
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
			} else if(pd.getString("STATUS").equals("8")){
				varList=researchService.dataAlllistPage(page);
			}
			mv.addObject("varList", varList);
			if(pd.getString("BANNER_ID").equals("52df6ede350b40ba9eaa9aaabae6cc5f")){
				mv.setViewName("system/banner/banners_edit");
			}else{
				mv.setViewName("system/banner/banner_edit");
			}
			mv.addObject("msg", "edit");
			mv.addObject("pd", pd);
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
		logBefore(logger, "批量删除Banner");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "dell")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				for(int i=0;i<ArrayDATA_IDS.length;i++){
					pd.put("BANNER_ID", ArrayDATA_IDS[i]);
					pd = bannerService.findById(pd);
					
					String PATH = pd.getString("PICTURE"); // 图片路径
					String path[] = PATH.split("uploadFiles");
					if(path.length > 1){
						String DPath = "uploadFiles"+path[1];
						DelAllFile.delFolder(PathUtil.getClasspath() + DPath); // 删除后台图片
					}
				}
				bannerService.deleteAll(ArrayDATA_IDS);
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
	
	/*
	 * 导出到excel
	 * @return
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel(){
		logBefore(logger, "导出Banner到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("图片");	//1
			titles.add("标题");	//2
			titles.add("日期");	//3
			titles.add("发布者");	//4
			titles.add("内容");	//5
			dataMap.put("titles", titles);
			List<PageData> varOList = bannerService.listAll(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).getString("PICTURE"));	//1
				vpd.put("var2", varOList.get(i).getString("TITLE"));	//2
				vpd.put("var3", varOList.get(i).getString("DATE"));	//3
				vpd.put("var4", varOList.get(i).getString("AUTHOR"));	//4
				vpd.put("var5", varOList.get(i).getString("CONTENT"));	//5
				varList.add(vpd);
			}
			dataMap.put("varList", varList);
			ObjectExcelView erv = new ObjectExcelView();
			mv = new ModelAndView(erv,dataMap);
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
