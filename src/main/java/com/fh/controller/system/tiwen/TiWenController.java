package com.fh.controller.system.tiwen;

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
import com.fh.service.system.comment_tiwen.Comment_TiwenService;
import com.fh.service.system.comment_tiwen_img.Comment_TiWenImgService;
import com.fh.service.system.tiwen.TiWenService;
import com.fh.service.system.tiwen_img.TiWen_ImgService;
import com.fh.util.Const;
import com.fh.util.DateUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;

@Controller
@RequestMapping(value = "tiwen")
public class TiWenController extends BaseController {
	String menuUrl="tiwen/list.do";
	@Resource(name = "tiWenService")
	private TiWenService tiWenService;
	@Resource(name="tiWen_ImgService")
	private TiWen_ImgService tiWen_ImgService;
	@Resource(name="comment_TiwenService")
	private Comment_TiwenService comment_TiwenService;
	@Resource(name="comment_TiWenImgService")
	private Comment_TiWenImgService comment_TiWenImgService;

	
	
	/**
	 * 新增
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, "评论提问");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String COMMENT_TIWEN_ID= this.get32UUID();
		User u = Jurisdiction.getCurrentUserID();//获取当前的USER_ID
		pd.put("COMMENT_TIWEN_ID",COMMENT_TIWEN_ID);	//主键
		pd.put("USER_ID", u.getUSER_ID());
		pd.put("NAME", "");
		String str=DateUtil.delHTMLTag(pd.getString("editorValue").toString());
		String DETAILS1=str.replace("\r", "");
		String DETAILS2=DETAILS1.replace("\n", "");
		String DETAILS3=DETAILS2.replace("&nbsp;", "");
		pd.put("MESSAGE", DETAILS3);
		pd.put("PUSER_ID", "");
		pd.put("PNAME", "");
		pd.put("DATE", DateUtil.getTime());
		pd.put("STATUS", "0");
		pd.put("VIEWS", "0");
		pd.put("YUE", DateUtil.getDay3());
		pd.put("PID", "");
		comment_TiwenService.save(pd);
		String a[]=pd.getString("editorValue").toString().split("src");
		for(int i=1;i<a.length;i++){
			System.err.println(a[i].substring(2, 95));
			PageData pd3=new PageData();
			pd3.put("COMMENT_TIWEN_ID", COMMENT_TIWEN_ID);
			pd3.put("IMG", a[i].substring(2, 95).toString().split("\"")[0]);
			pd3.put("ORDE_BY", i);
			pd3.put("DATE", DateUtil.getTime());
			comment_TiWenImgService.save(pd3);
		}
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 去新增页面
	 */
	@RequestMapping(value="/show2")
	public ModelAndView show2(){
		logBefore(logger, "去新增商品页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd=new PageData();
		pd=this.getPageData();
		try {
			mv.setViewName("system/tiwen/tiwen_huifu");
			mv.addObject("msg", "save");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}	
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/Cdelete")
	public void Cdelete(PrintWriter out){
		logBefore(logger, "删除提问评论");
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			comment_TiwenService.delete(pd);
			comment_TiwenService.deleteA(pd);
			comment_TiWenImgService.delete(pd);
			PageData pd_c=tiWenService.findById(pd);
			pd_c.put("HUIFU", Integer.valueOf(pd_c.getString("HUIFU"))-1);
			tiWenService.editHuiFu(pd_c);
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value = "/show1")
	public ModelAndView show1(Page page) {
		logBefore(logger, "查询提问评论列表");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			// 页面检索
			String KEYWORD = pd.getString("KEYWORD");
			if (null != KEYWORD && !"".equals(KEYWORD)) {
				KEYWORD = KEYWORD.trim();
				pd.put("KEYWORD", KEYWORD);
			}
			pd.put("STATUS", "0");
			page.setPd(pd);
			List<PageData> varList = comment_TiwenService.datalistPage(page); // 列出Pro_Info列表
			// List<PageData> list=pro_infoService.findByShopId(pd);//产品
			mv.setViewName("system/tiwen/tiwen_show");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
			mv.addObject(Const.SESSION_QX, this.getHC()); // 按钮权限
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
			tiWenService.delete(pd);
			tiWen_ImgService.delete(pd);
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
	}
	
	
	/**
	 * 列表
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) {
		logBefore(logger, "查询提问列表");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			// 页面检索
			String KEYWORD = pd.getString("KEYWORD");
			if (null != KEYWORD && !"".equals(KEYWORD)) {
				KEYWORD = KEYWORD.trim();
				pd.put("KEYWORD", KEYWORD);
			}
			page.setPd(pd);
			List<PageData> varList = tiWenService.datalistPage(page); // 列出Pro_Info列表
			// List<PageData> list=pro_infoService.findByShopId(pd);//产品
			mv.setViewName("system/tiwen/tiwen_list");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
			mv.addObject(Const.SESSION_QX, this.getHC()); // 按钮权限
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	
	
	/**
	 * 新增
	 */
	@RequestMapping(value="/saves")
	public ModelAndView saves() throws Exception{
		logBefore(logger, "新增商品");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd=this.getPageData();
		pd.put("TIWEN_ID", this.get32UUID());
		pd.put("USER_ID", "17");
		pd.put("HUIFU", "0");
		pd.put("VIEWS", "0");
		pd.put("YUE", "");
		pd.put("STATUS", "1");
		pd.put("SHENCHA", "0");
		tiWenService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	@RequestMapping(value="/goAdds")
	public ModelAndView goAdds(){
		logBefore(logger, "去新增提问广告");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			mv.setViewName("system/tiwen/tiwens_edit");
			mv.addObject("msg", "saves");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}	
	
	/**
	 * 列表
	 */
	@RequestMapping(value = "/lists")
	public ModelAndView lists(Page page) {
		logBefore(logger, "查询提问广告列表");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			// 页面检索
			String KEYWORD = pd.getString("KEYWORD");
			if (null != KEYWORD && !"".equals(KEYWORD)) {
				KEYWORD = KEYWORD.trim();
				pd.put("KEYWORD", KEYWORD);
			}
			page.setPd(pd);
			List<PageData> varList = tiWenService.dataslistPage(page); // 列出Pro_Info列表
			// List<PageData> list=pro_infoService.findByShopId(pd);//产品
			mv.setViewName("system/tiwen/tiwens_list");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
			mv.addObject(Const.SESSION_QX, this.getHC()); // 按钮权限
		} catch (Exception e) {
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
