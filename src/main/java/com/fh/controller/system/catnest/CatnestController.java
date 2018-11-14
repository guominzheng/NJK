package com.fh.controller.system.catnest;

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
import com.fh.entity.system.User;
import com.fh.service.system.cartnest.CartNestService;
import com.fh.service.system.cartnest_img.Cartnest_ImgService;
import com.fh.util.Const;
import com.fh.util.DateUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;


@Controller
@RequestMapping(value="catenst",produces="text/html;charset=UTF-8")
public class CatnestController extends BaseController{
	String menuUrl = "catenst/list.do"; //菜单地址(权限用)	
	
	@Resource(name="cartNestService")
	private CartNestService cartNestService;
	@Resource(name="cartnest_ImgService")
	private Cartnest_ImgService cartnest_ImgService;
	
	
	
	
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
			cartNestService.delete(pd);
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
		logBefore(logger, "修改猫屋帖子");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("MESSAGE", pd.getString("editorValue"));
		cartNestService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 去修改页面
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit(){
		logBefore(logger, "去修改猫屋帖子页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {	
			pd = cartNestService.findById(pd);	//根据ID读取	
			mv.setViewName("system/catenst/catenst_edit");
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
	public ModelAndView rsave() throws Exception{
		logBefore(logger, "新增猫屋帖子");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		User u = Jurisdiction.getCurrentUserID();//获取当前的USER_ID
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("DATE", DateUtil.getTime());
		pd.put("USER_ID", u.getUSER_ID());
		pd.put("VIEWS", "0");
		pd.put("HUIFU", "0");
		pd.put("FABU", "0");
		pd.put("NAME", "");
		pd.put("ZSTATUS", "0");
		pd.put("MESSAGE", "<style>img{max-width:100%;}</style>"+pd.getString("editorValue").toString());
		pd.put("DATES", DateUtil.getTime());
		pd.put("STATUS", "0");
		String a[]=pd.getString("editorValue").toString().split("src");
		cartNestService.save(pd);
		String CATNEST_ID=pd.get("CATNEST_ID").toString();
		if(a.length>1){
			System.err.println(a[1].substring(2, 95));
			PageData pd3=new PageData();
			pd3.put("CATNEST_ID", CATNEST_ID);
			pd3.put("IMG", a[1].substring(2, 95).toString().split("\"")[0]);
			pd3.put("ORDE_BY", 1);
			pd3.put("DATE", DateUtil.getTime());
			cartnest_ImgService.save(pd3);	
		}
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 去新增页面
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goRAdd(){
		logBefore(logger, "去新增猫屋帖子");
		ModelAndView mv = this.getModelAndView();
		PageData pd=new PageData();
		try {
			pd=this.getPageData();
			mv.setViewName("system/catenst/catenst_edit");
			mv.addObject("msg", "save");
			mv.addObject("pd",pd);
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
		logBefore(logger, "查询猫屋帖子列表");
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
			List<PageData> varList=cartNestService.datalistPage(page);
			mv.setViewName("system/catenst/catenst_list");
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
