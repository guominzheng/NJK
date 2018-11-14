package com.fh.controller.system.post_special;

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
import com.fh.service.system.collection_special.Collection_specialService;
import com.fh.service.system.comment_special.Comment_specialService;
import com.fh.service.system.comment_specialimg.Comment_SpecialImgService;
import com.fh.service.system.post.PostService;
import com.fh.service.system.post_img.Post_ImgService;
import com.fh.service.system.post_info.Post_InfoService;
import com.fh.service.system.post_special.Post_SpecialService;
import com.fh.service.system.post_special_type.Post_Special_TypeService;
import com.fh.service.system.user.UserService;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.DateUtil;
import com.fh.util.DelAllFile;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.fh.util.PathUtil;

@Controller
@RequestMapping(value="post_special",produces="text/htnl;charset=UTF-8")
public class Post_specialController extends BaseController{

	String menuUrl="post_special/list.do";
	
	@Resource(name="post_SpecialService")
	private Post_SpecialService post_SpecialService;
	@Resource(name="userService")
	private UserService userService;
	@Resource(name="comment_specialService")
	private Comment_specialService comment_specialService;
	@Resource(name="comment_SpecialImgService")
	private Comment_SpecialImgService comment_SpecialImgService;
	@Resource(name="post_Special_TypeService")
	private Post_Special_TypeService post_Special_TypeService;
	@Resource(name="postService")
	private PostService postService;
	@Resource(name="post_InfoService")
	private Post_InfoService post_InfoService;
	@Resource(name="post_ImgService")
	private Post_ImgService post_ImgService;
	@Resource(name="collection_specialService")
	private Collection_specialService collection_specialService;
	
	
	/**
	 * 修改
	 */
	@RequestMapping(value="/editTidF")
	public ModelAndView editTidF() throws Exception{
		logBefore(logger, "修改帖子");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
//		pd.put("CONTENT", "");	//产品描述 默认为空
		//String DETAILS1= pd.getString("editorValue").toString().replace("localhost", "101.200.130.60");
		//String DETAILS=pd.getString("editorValue").toString().replace("src=\"", "src=\"http://read.html5.qq.com/image?src=forum&q=5&r=0&imgflag=7&imageUrl=");
		//pd.getString("editorValue").toString().replace("src=\"", "src=\"http://read.html5.qq.com/image?src=forum&q=5&r=0&imgflag=7&imageUrl=");
		//String DETAILS1= DETAILS.replace("http://read.html5.qq.com/image?src=forum&q=5&r=0&imgflag=7&imageUrl=http://read.html5.qq.com/image?src=forum&q=5&r=0&imgflag=7&imageUrl=", "src=\"http://read.html5.qq.com/image?src=forum&q=5&r=0&imgflag=7&imageUrl=");
		pd.put("MESSAGE","<style>img{max-width:100%;}</style>"+pd.getString("editorValue").toString());
		postService.edit(pd);
		post_InfoService.edit(pd);
		String a[]=pd.getString("editorValue").toString().split("src");
		post_ImgService.delete(pd);
		/*if(a.length>1){
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
	@RequestMapping(value="/goEditTidF")
	public ModelAndView goEditTidF(){
		logBefore(logger, "去修改商品页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {	
			List<PageData> list=post_ImgService.findList(pd);
			
			pd = postService.findById(pd);	//根据ID读取	
			if(list!=null&&list.size()!=0){
				pd.put("IMG1", list.get(0).getString("IMG"));
			}
			mv.setViewName("system/post_special/post_special_type_post_edit");
			mv.addObject("msg", "editTidF");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}
	
	/**
	 * 新增
	 */
	@RequestMapping(value="/saveTidF")
	public ModelAndView saveTidF() throws Exception{
		logBefore(logger, "根据专题类型添加帖子");
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
		pd.put("KEYWORD1", "");
		pd.put("KEYWORD2", "");
		pd.put("URL", "");
		pd.put("HUODONG", "0");
		pd.put("HUODONG1", "");
		pd.put("BROWSE", "1");
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
		/*String a[]=pd.getString("editorValue").toString().split("src");
		post_ImgService.delete(pd);
		for(int i=1;i<a.length;i++){
			System.err.println(a[i].substring(2, 95));
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
		collection_specialService.editStatus1(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 去新增页面
	 */
	@RequestMapping(value="/goAddTidF")
	public ModelAndView goAddTidF(){
		logBefore(logger, "去新增帖子");
		ModelAndView mv = this.getModelAndView();
		PageData pd=new PageData();
		pd=this.getPageData();
		try {
			mv.setViewName("system/post_special/post_special_type_post_edit");
			mv.addObject("msg", "saveTidF");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}
	/**
	 * 增加专题详情
	 */
	@RequestMapping(value="/listTidF")
	public ModelAndView listTidF(Page page){
		logBefore(logger, "查询专题类型帖子");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} 
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			page.setPd(pd);
			List<PageData>	varList = postService.data4listPage(page);	//列出Pro_Info列表
			//List<PageData>	list=pro_infoService.findByShopId(pd);//产品
			mv.setViewName("system/post_special/post_special_type_post_list");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
			mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/deleteFid")
	public void deleteFid(PrintWriter out){
		logBefore(logger, "删除专题类型");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			post_Special_TypeService.delete(pd);
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/fabu")
	public void fabu(PrintWriter out){
		logBefore(logger, "发布专题帖子");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			postService.editBrowse(pd);
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		
	}
	/**
	 * 修改
	 */
	@RequestMapping(value="/editFid")
	public ModelAndView editFid() throws Exception{
		logBefore(logger, "修改专题类型");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		post_Special_TypeService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	
	/**
	 * 去修改专题类型页面
	 */
	@RequestMapping(value="/goEditFid")
	public ModelAndView goEditFid(){
		logBefore(logger, "去修改专题类型页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {	
			pd = post_Special_TypeService.findById(pd);	//根据ID读取	
			mv.setViewName("system/post_special/post_special_type_edit");
			mv.addObject("msg", "editFid");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}
	/**
	 * 新增
	 */
	@RequestMapping(value="/saveFid")
	public ModelAndView saveFid() throws Exception{
		logBefore(logger, "新增专题类型");
		ModelAndView mv = this.getModelAndView();
		PageData pd=new PageData();
		pd=this.getPageData();
		pd.put("POST_SPECIAL_TYPE_ID", this.get32UUID());
		post_Special_TypeService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 去新增页面
	 */
	@RequestMapping(value="/goAddFid")
	public ModelAndView goAddFid(){
		logBefore(logger, "去新增帖子专题类型页面");
		ModelAndView mv = this.getModelAndView();
		try {
			PageData pd=new PageData();
			pd=this.getPageData();
			mv.setViewName("system/post_special/post_special_type_edit");
			mv.addObject("msg", "saveFid");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}
	
	/**
	 * 增加专题详情
	 */
	@RequestMapping(value="/showFid")
	public ModelAndView showFid(Page page){
		logBefore(logger, "查询帖子专题列表");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} 
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			page.setPd(pd);
			List<PageData>	varList = post_Special_TypeService.datalistPage(page);	//列出Pro_Info列表
			//List<PageData>	list=pro_infoService.findByShopId(pd);//产品
			mv.setViewName("system/post_special/post_special_type_list");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
			mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	
	/**
	 * 去新增页面
	 */
	@RequestMapping(value="/Cshow")
	public ModelAndView Cshow(Page page){
		logBefore(logger, "查询专题评论");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} 
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			page.setPd(pd);
			List<PageData>	varList = comment_specialService.list(page);	//列出Pro_Info列表
			//List<PageData>	list=pro_infoService.findByShopId(pd);//产品
			mv.setViewName("system/post_special/post_special_show");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
			mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	
	// 删除首页图片
		@RequestMapping(value = "/deltppp")
		@ResponseBody
		public String deltppp(PrintWriter out) {
			logBefore(logger, "删除图片");
			try {
				PageData pd = new PageData();
				pd = this.getPageData();
				
				String PATH = pd.getString("COVER_IMG2"); // 图片路径
				if(PATH!=null){
					String path[] = PATH.split("uploadFiles");
					if(path.length > 1){
						String DPath = "uploadFiles"+path[1];
						DelAllFile.delFolder(PathUtil.getClasspath() + DPath); // 删除后台图片
					}
					
						pd=post_SpecialService.findById(pd);
						pd.put("COVER_IMG2", "");
						post_SpecialService.edit(pd);
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
	@RequestMapping(value = "/deltpp")
	@ResponseBody
	public String deltpp(PrintWriter out) {
		logBefore(logger, "删除图片");
		try {
			PageData pd = new PageData();
			pd = this.getPageData();
			
			String PATH = pd.getString("COVER_IMG"); // 图片路径
			if(PATH!=null&&PATH!=""){
				String path[] = PATH.split("uploadFiles");
				if(path.length > 1){
					String DPath = "uploadFiles"+path[1];
					DelAllFile.delFolder(PathUtil.getClasspath() + DPath); // 删除后台图片
				}
				
					pd=post_SpecialService.findById(pd);
					pd.put("COVER_IMG", "");
					post_SpecialService.edit(pd);
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
							
								pd=post_SpecialService.findById(pd);
								pd.put("IMG", "");
								post_SpecialService.edit(pd);
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
	 * 修改置顶
	 */
	@RequestMapping(value="/editTstatus")
	public void editTstatus(PrintWriter out){
		logBefore(logger, "修改推荐帖子");
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			if(pd.getString("TSTATUS").equals("0")){
				post_SpecialService.editTStatus(pd);
			}else{
				PageData pd1=post_SpecialService.findCount(pd);
				Integer count=Integer.valueOf(pd1.get("count").toString());
				if(count<1){
					post_SpecialService.editTStatus(pd);
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
		logBefore(logger, "新增专题");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		PageData pd1=new PageData();
		User u = Jurisdiction.getCurrentUserID();//获取当前的USER_ID
		pd1.put("USER_ID", u.getUSER_ID());
		pd1=userService.findByUiId(pd1);
		pd = this.getPageData();
		pd.put("FID", this.get32UUID());
		pd.put("POST_SPECIAL_ID", this.get32UUID());
		pd.put("VIEWS", "0");
		pd.put("HUIFU", "0");
		pd.put("TSTATUS", "0");
		pd.put("USER_ID", u.getUSER_ID());
		pd.put("SHUZI", "0");
		post_SpecialService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 去新增页面
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd(){
		logBefore(logger, "去新增帖子专题页面");
		ModelAndView mv = this.getModelAndView();
		try {
			mv.setViewName("system/post_special/post_special_edit");
			mv.addObject("msg", "save");
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
		logBefore(logger, "批量删除专题");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "dell")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				post_SpecialService.deleteAll(ArrayDATA_IDS);
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
	@RequestMapping(value="/Cdelete")
	public void Cdelete(PrintWriter out){
		logBefore(logger, "删除帖子专题");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			comment_specialService.delete(pd);
			comment_SpecialImgService.delete(pd);
			comment_specialService.deleteA(pd);
			PageData pd_c=post_SpecialService.findById(pd);
			pd_c.put("HUIFU", Integer.valueOf(pd_c.getString("HUIFU"))-1);
			post_SpecialService.editHuiFu(pd_c);
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
		logBefore(logger, "删除帖子专题");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			post_SpecialService.delete(pd);
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
		logBefore(logger, "修改帖子专题");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("KEYWORD2", "");
		post_SpecialService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 去修改页面
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit(){
		logBefore(logger, "去修改帖子专题页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {	
			pd = post_SpecialService.findById(pd);	//根据ID读取	
			mv.setViewName("system/post_special/post_special_edit");
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
		logBefore(logger, "查询帖子专题列表");
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
			List<PageData>	varList = post_SpecialService.list(page);	//列出Pro_Info列表
			//List<PageData>	list=pro_infoService.findByShopId(pd);//产品
			mv.setViewName("system/post_special/post_special_list");
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
