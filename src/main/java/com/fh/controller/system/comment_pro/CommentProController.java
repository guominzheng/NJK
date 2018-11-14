package com.fh.controller.system.comment_pro;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.fh.entity.system.User;
import com.fh.service.system.comment_pro_img.CommentProIMGService;
import com.fh.service.system.research.ResearchService;
import com.fh.util.DateUtil;
import com.fh.util.Jurisdiction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.system.comment_pro.CommentProService;
import com.fh.util.AppUtil;
import com.fh.util.PageData;

@Controller
@RequestMapping(value="comment_pro",produces="text/html;charset=UTF-8")
public class CommentProController extends BaseController{

	String menuUrl = "comment_pro/list.do";
	@Resource(name="commentProService")
	private CommentProService commentProService;

	@Resource(name="commentProIMGService")
	private CommentProIMGService commentProIMGService;

	
	/**
	 * 去修改页面单价
	 */
	@RequestMapping(value="/goEditp")
	@ResponseBody
	public String goEditp(){
		logBefore(logger, "去回复页面");
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			commentProService.edit(pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return "success";
	}
	/**
	 * 去添加页面单价
	 */
	@RequestMapping(value="/goSaveUR")
	@ResponseBody
	public ModelAndView goSaveUR(){
		logBefore(logger, "去回复页面");
		ModelAndView modelAndView = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		System.out.println(pd.getString("PRODUCT_ID"));
		modelAndView.addObject("pd",pd);
		modelAndView.setViewName("system/comment_pro/comment_pro_edit");
		modelAndView.addObject("msg","saveUR");
		return modelAndView;
	}

	/**
	 * 去新增套餐的评论
	 */
	@RequestMapping(value = "/gosaveCompro")
	public ModelAndView gosaveCompro() {
		logBefore(logger, "去新增套餐的评论");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd=this.getPageData();
		try {
			System.out.println("-------------------->go中"+pd.getString("PRODUCT_ID"));
			mv.setViewName("system/comment_pro/comment_pro_edit");
			mv.addObject("pd",pd);
			mv.addObject("msg", "saveCompro");
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}
	/**
	 * 新增套餐的评论
	 */
	@RequestMapping(value = "/saveCompro")
	public ModelAndView saveCompro() throws Exception {
		logBefore(logger, "新增套餐的评论");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "save")) {
			return null;
		} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		User u = Jurisdiction.getCurrentUserID();//获取当前的USER_ID
		String COMMENT_PRO_ID = this.get32UUID();//生成32位ID
		pd.put("HUIFU",pd.getString("COMMENT_PRO_ID"));
		pd.put("COMMENT_PRO_ID",COMMENT_PRO_ID);//将ID封装
		pd.put("USER_ID",u.getUSER_ID());
		pd.put("NAME",u.getNAME());
		pd.put("DATE",DateUtil.getTime());
		String str  = DateUtil.delHTMLTag(pd.getString("editorValue"));
		String  DETALLS1 = str.replace("\r","");
		String  DETALLS2 =DETALLS1.replace("\n","");
		String  DETALLS3 =DETALLS2.replace("&nbsp;","");
		pd.put("MESSAGE",DETALLS3);
		String a[] = pd.getString("editorValue").toString().split("src");
		commentProService.save(pd);
		if (a.length > 1) {
			System.err.println(a[1].substring(2, 95));
			PageData pd3 = new PageData();
			pd3.put("COMMENT_PRO_ID", COMMENT_PRO_ID);
			pd3.put("COMMENT_PRO_IMG_ID",this.get32UUID());
			pd3.put("IMG", a[1].substring(2, 95).toString().split("\"")[0]);
			pd3.put("DATE",DateUtil.getTime());
			commentProIMGService.save(pd3);
		}
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}


	/**
	 * 新增主留言
	 */
	@RequestMapping(value = "/saveUR")
	public ModelAndView saveUR() throws Exception {
		logBefore(logger, "新增套餐的主评论");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "save")) {
			return null;
		} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		User u = Jurisdiction.getCurrentUserID();//获取当前的USER_ID
		String str = this.get32UUID();//生成32位ID
		pd.put("COMMENT_PRO_ID",str);//将ID封装
		pd.put("USER_ID",u.getUSER_ID());
		pd.put("NAME",u.getNAME());
		pd.put("DATE",DateUtil.getTime());
		commentProService.save(pd);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() {
		logBefore(logger, "批量商品评论");
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				commentProService.deleteAll(ArrayDATA_IDS);
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
	 * 列表
	 */
	@RequestMapping(value="/show")
	public ModelAndView show(Page page){
		logBefore(logger, "显示商品评论");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			List<PageData>	varList = commentProService.findcomproById(pd);	//列出Pro_Info列表
			//List<PageData>	list=pro_infoService.findByShopId(pd);//产品
			mv.setViewName("system/comment_pro/research_show");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}




}
