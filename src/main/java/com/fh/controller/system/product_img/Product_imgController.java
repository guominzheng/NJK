package com.fh.controller.system.product_img;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.service.system.product_img.Product_imgService;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.DateUtil;
import com.fh.util.DelAllFile;
import com.fh.util.FileUpload;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.fh.util.PathUtil;

@Controller
@RequestMapping(value="pro_images")
public class Product_imgController extends BaseController{

	@Resource(name="product_imgService")
	private Product_imgService product_imgService;
	
	
	
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
				product_imgService.deleteAll(path);
				if(path.length > 1){
					String DPath = "uploadFiles"+path[1];
					DelAllFile.delFolder(PathUtil.getClasspath() + DPath); // 删除后台图片
				}
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
				product_imgService.deleteAll(ArrayDATA_IDS);
				for(int i=0;i<ArrayDATA_IDS.length;i++){
					String DPath = "uploadFiles"+ArrayDATA_IDS[i];
					DelAllFile.delFolder(PathUtil.getClasspath() + DPath); // 删除后台图片
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
	 * 上传滚动图片
	 */
	@RequestMapping(value="/savePricture")
	@ResponseBody
	public Object savePricture(@RequestParam(required = false) MultipartFile file) throws Exception{
		logBefore(logger, "上传滚动图片");
		Map<String, String> map = new HashMap<String, String>();
		String ffile = DateUtil.getDays(), fileName = "";
		PageData pd = new PageData();
		String id = this.get32UUID();
		pd=this.getPageData();
			if (null != file && !file.isEmpty()) {
				String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + ffile; // 文件上传路径
				System.out.println("filePath------------------:"+filePath);
				fileName = FileUpload.fileUp(file, filePath, id); // 执行上传
				System.out.println("fileName------------------:"+fileName);
			} else {
				System.out.println("上传失败");
			}
			pd.put("PRODUCT_IMG", Const.SERVERPATH+Const.FILEPATHIMG+ffile+"/"+fileName);
			pd.put("PRODUCT_IMG_ID", this.get32UUID());
			product_imgService.save(pd);
		map.put("result", "ok");
		return AppUtil.returnObject(pd, map);
	}

	/**
	 * 上传研究院回复图片
	 * @param file 图片
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/saveComRes")
	@ResponseBody
	public Object saveComRes(@RequestParam(required = false) MultipartFile file) throws Exception{
		logBefore(logger, "上传滚动图片");
		Map<String, String> map = new HashMap<String, String>();
		String ffile = DateUtil.getDays(), fileName = "";
		PageData pd = new PageData();
		String id = this.get32UUID();
		pd=this.getPageData();
		if (null != file && !file.isEmpty()) {
			String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + ffile; // 文件上传路径
			System.out.println("filePath------------------:"+filePath);
			fileName = FileUpload.fileUp(file, filePath, id); // 执行上传
			System.out.println("fileName------------------:"+fileName);
		} else {
			System.out.println("上传失败");
		}
		pd.put("PRODUCT_IMG", Const.SERVERPATH+Const.FILEPATHIMG+ffile+"/"+fileName);
		pd.put("PRODUCT_IMG_ID", this.get32UUID());
		product_imgService.save(pd);
		map.put("result", "ok");
		return AppUtil.returnObject(pd, map);
	}
	
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd(){
		logBefore(logger, "去新增商品页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd=new PageData();
		pd=this.getPageData();
		try {
			mv.setViewName("system/product_img/pro_images_edit");
			mv.addObject("msg", "save");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}
	
	@RequestMapping(value="show")
	public ModelAndView show(){
		logBefore(logger, "显示滚动图片");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			List<PageData>	varList = product_imgService.findList(pd);	//根据ID读取
			mv.addObject("varList", varList);
			mv.setViewName("system/product_img/product_show");
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
