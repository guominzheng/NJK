package com.fh.controller.system.post;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;

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
import com.fh.service.system.comment_post.Comment_PostService;
import com.fh.service.system.comment_post_img.Comment_PostImgService;
import com.fh.service.system.news.NewsService;
import com.fh.service.system.post.PostService;
import com.fh.service.system.post_img.Post_ImgService;
import com.fh.service.system.post_info.Post_InfoService;
import com.fh.service.system.post_special.Post_SpecialService;
import com.fh.service.system.product.ProductService;
import com.fh.service.system.user.UserService;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.DateUtil;
import com.fh.util.DelAllFile;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.fh.util.PathUtil;
import com.fh.util.WeiXin;
import com.fh.util.utils.TenpayUtil;

@Controller
@RequestMapping(value="post",produces="text/html;charset=UTF-8")
public class PostController extends BaseController{
	String menuUrl="post/list.do";
	@Resource(name="postService")
	private PostService postService;
	@Resource(name="post_InfoService")
	private Post_InfoService post_InfoService;
	@Resource(name="userService")
	private UserService userService;
	@Resource(name="post_SpecialService")
	private Post_SpecialService post_SpecialService;
	@Resource(name="productService")
	private ProductService productService;
	@Resource(name="post_ImgService")
	private Post_ImgService post_ImgService;
	@Resource(name="activityService")
	private ActivityService activityService;
	@Resource(name="comment_PostService")
	private Comment_PostService comment_PostService;
	@Resource(name="comment_PostImgService")
	private Comment_PostImgService comment_PostImgService;
	@Resource(name="newsService")
	private NewsService newsService;
	
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
					postService.editFimg(pd);
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
				post_ImgService.delete(pd);
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
	 * 列表
	 *//*
@RequestMapping(value="WeiXin2",produces="text/html;charset=UTF-8")
	public ModelAndView WeiXin2(String url,String TID){
		logBefore(logger, "查询商品列表");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd=this.getPageData();
		try{
			String access_token=WeiXin.access_token();
			String nonceStr=WeiXin.createNonceStr(16);
			//获取openId后调用统一支付接口https://api.mch.weixin.qq.com/pay/unifiedorder
			String currTime = TenpayUtil.getCurrTime();
			//8位日期
			String strTime = currTime.substring(8, currTime.length());
			String Signature=WeiXin.signature(,nonceStr, strTime, "http://101.200.130.60:8080/NJK/post/WeiXin2.do?TID="+TID);
			PageData pd1=postService.findById(pd);
			List<PageData> list1=post_ImgService.findList(pd);
			if(list1!=null&&list1.size()!=0){
				pd1.put("PIMG", list1.get(0).getString("IMG"));
			}else{
				pd1.put("PIMG", "http://localhost:8888/NJK/uploadFiles/uploadImgs/logo.png");
			}
			String str=DateUtil.delHTMLTag(pd1.getString("MESSAGE"));
			String DETAILS1=str.replace("\r", "");
			String DETAILS2=DETAILS1.replace("\n", "");
			int qian=56;
			if(str.length()<56){
				pd1.put("JIANJIE", DETAILS2);
			}else{
				str=DETAILS2.substring(0,qian);
				pd1.put("JIANJIE", str+"...");
			}
			pd.put("STATUS", "0");
			List<PageData> list=comment_PostService.findList1(pd);
			pd1.put("signature", Signature);
			pd1.put("nonceStr", nonceStr);
			pd1.put("timestamp", strTime);
			pd1.put("URL", "http://localhost:8888/NJK/post/WeiXin2.do?TID="+TID);
		//List<PageData>	list=pro_infoService.findByShopId(pd);//产品
		mv.setViewName("system/fenxiang/post");
		mv.addObject("varList", list);
		mv.addObject("pd", pd1);
	} catch(Exception e){
		logger.error(e.toString(), e);
	}
		return mv;
	}*/
	
	/**
	 * 修改置顶
	 */
	@RequestMapping(value="/editMstatus")
	public void editMstatus(PrintWriter out){
		logBefore(logger, "修改每日一读");
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			if(pd.getString("MSTATUS").equals("0")){
				postService.editMStatus(pd);
			}else{
				postService.editMStatus(pd);
			}
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		
	}
	
	
	@RequestMapping(value="/editEstatus")
	public void editEstatus(PrintWriter out){
		logBefore(logger, "修改帖子精华");
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			postService.editEStatus(pd);
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
			if(pd.getString("TSTATUS").equals("0")){
				postService.editTop(pd);
			}else{
				PageData pd1=postService.findCount(pd);
				Integer count=Integer.valueOf(pd1.get("count").toString());
				if(count<3){
					postService.editTop(pd);
				}
			}
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
		logBefore(logger, "新增帖子");
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
		pd.put("USER_ID",u.getUSER_ID());
		pd.put("HUIFU", "0");
		pd.put("TSTATUS", "0");
		pd.put("PRODUCT_ID", "0");
		pd.put("ESTATUS", "0");
		pd.put("MSTATUS", "0");
		pd.put("URL", "");
		pd.put("HUODONG", "0");
		pd.put("HUODONG1", "");
		pd.put("BROWSE", "1");
		pd.put("ACTIVITY_ID", "");
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
/*		String a[]=pd.getString("editorValue").toString().split("src");
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
	 * 去新增页面
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd(){
		logBefore(logger, "去新增帖子页面");
		ModelAndView mv = this.getModelAndView();
		try {
			mv.setViewName("system/post/post_edit");
			mv.addObject("msg", "save");
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}
	
	
	
	/**
	 * 批量添加直播
	 */
	@RequestMapping(value="/saveActivity")
	@ResponseBody
	public Object saveActivity() {
		logBefore(logger, "批量把帖子添加商品下");
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				for(int i=0;i<ArrayDATA_IDS.length;i++){
					PageData pd2=new PageData();
					pd2.put("TID", ArrayDATA_IDS[i]);
					pd2.put("ACTIVITY_ID", pd.getString("ACTIVITY_ID"));
					postService.editAID(pd2);
				}
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
	 * 批量添加商品
	 */
	@RequestMapping(value="/saveProduct")
	@ResponseBody
	public Object saveProduct() {
		logBefore(logger, "批量把帖子添加商品下");
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				for(int i=0;i<ArrayDATA_IDS.length;i++){
					PageData pd2=new PageData();
					pd2.put("TID", ArrayDATA_IDS[i]);
					pd2.put("PRODUCT_ID", pd.getString("PRODUCT_ID"));
					postService.editPID(pd2);
				}
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
	 * 批量添加专题
	 */
	@RequestMapping(value="/saveSpecial")
	@ResponseBody
	public Object saveSpecial() {
		logBefore(logger, "批量添加专题");
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				for(int i=0;i<ArrayDATA_IDS.length;i++){
					PageData pd2=new PageData();
					pd2.put("TID", ArrayDATA_IDS[i]);
					pd2.put("FID", pd.getString("FID"));
					postService.editFID(pd2);
				}
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
	 * 批量添加专题
	 */
	@RequestMapping(value="/saveEssence")
	@ResponseBody
	public Object saveEssence() {
		logBefore(logger, "批量添加精华");
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				for(int i=0;i<ArrayDATA_IDS.length;i++){
					PageData pd2=new PageData();
					pd2.put("TID", ArrayDATA_IDS[i]);
					postService.editEStatus(pd2);
				}
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
				newsService.deleteAll(ArrayDATA_IDS);
				comment_PostService.deleteAll1(pd);
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
		logBefore(logger, "删除帖子");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			postService.delete(pd);
			post_ImgService.delete(pd);
			post_InfoService.delete(pd);
			comment_PostService.deleteAll(pd);
			newsService.delete(pd);
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/Cdelete")
	public void Cdelete(PrintWriter out){
		logBefore(logger, "删除商品");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			comment_PostService.delete(pd);
			PageData pd_p=postService.findById(pd);
			pd_p.put("HUIFU", Integer.valueOf(pd_p.getString("HUIFU"))-1);
			postService.editHuiFu(pd_p);
			comment_PostImgService.delete(pd);
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
		//String DETAILS1= pd.getString("editorValue").toString().replace("localhost", "101.200.130.60");
		//String DETAILS=pd.getString("editorValue").toString().replace("src=\"", "src=\"http://read.html5.qq.com/image?src=forum&q=5&r=0&imgflag=7&imageUrl=");
		//pd.getString("editorValue").toString().replace("src=\"", "src=\"http://read.html5.qq.com/image?src=forum&q=5&r=0&imgflag=7&imageUrl=");
		//String DETAILS1= DETAILS.replace("http://read.html5.qq.com/image?src=forum&q=5&r=0&imgflag=7&imageUrl=http://read.html5.qq.com/image?src=forum&q=5&r=0&imgflag=7&imageUrl=", "http://read.html5.qq.com/image?src=forum&q=5&r=0&imgflag=7&imageUrl=");
		pd.put("MESSAGE","<style>img{max-width:100%;}</style>"+pd.getString("editorValue").toString());
		postService.edit(pd);
		post_InfoService.edit(pd);
		String a[]=pd.getString("editorValue").toString().split("src");
		post_ImgService.delete(pd);
/*		if(a.length>1){
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
		logBefore(logger, "去修改帖子页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {	
			List<PageData> list=post_ImgService.findList(pd);
			
			pd = postService.findById(pd);	//根据ID读取	
			if(list!=null&&list.size()!=0){
				pd.put("IMG1", list.get(0).getString("IMG"));
			}
			//pd.put("MESSAGE", pd.getString("MESSAGE"));
			mv.setViewName("system/post/post_edit");
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
		logBefore(logger, "查询商品列表");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			pd.put("STATUS", "0");
			page.setShowCount(10);
			page.setPd(pd);
			List<PageData>	varList = comment_PostService.list(page);	//列出Order_Info列表
			mv.setViewName("system/post/post_show");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
			mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
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
			List<PageData>	varList = postService.list(page);	//列出Pro_Info列表
			List<PageData> Slist=post_SpecialService.findList(pd);
			List<PageData> Plist=productService.findList(pd);
			List<PageData> Alist=activityService.findList(pd);
			//List<PageData>	list=pro_infoService.findByShopId(pd);//产品
			mv.setViewName("system/post/post_list");
			mv.addObject("varList", varList);
			mv.addObject("Slist", Slist);
			mv.addObject("Plist", Plist);
			mv.addObject("Alist", Alist);
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
