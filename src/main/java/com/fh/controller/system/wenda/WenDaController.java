package com.fh.controller.system.wenda;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.system.collection_wenda.Collection_WendaService;
import com.fh.service.system.error_wenda.Error_WendaService;
import com.fh.service.system.fenxiang.FenXiangService;
import com.fh.service.system.fenxiangC.FenXiangCService;
import com.fh.service.system.wancheng.WanChengService;
import com.fh.service.system.wenda.WenDaService;
import com.fh.service.system.wenda_info.WenDa_InfnService;
import com.fh.service.system.wenda_shijuan.WenDa_ShiJuanService;
import com.fh.service.system.wenda_type.WenDa_TypeService;
import com.fh.service.system.zuoguo_shijuan.ZuoGuo_ShiJuanService;
import com.fh.util.Const;
import com.fh.util.DateUtil;
import com.fh.util.DelAllFile;
import com.fh.util.Jurisdiction;
import com.fh.util.MapDistance;
import com.fh.util.PageData;
import com.fh.util.PathUtil;

@Controller
@RequestMapping(value="wenda",produces="text/html;charset=UTF-8")
public class WenDaController extends BaseController{

	String menuUrl = "wenda/list.do"; //菜单地址(权限用)
	@Resource(name="wenDa_TypeService")
	private WenDa_TypeService wenDa_TypeService;
	@Resource(name="wenDa_ShiJuanService")
	private WenDa_ShiJuanService wenDa_ShiJuanService;
	@Resource(name="wenDaService")
	private WenDaService wenDaService;
	@Resource(name="wenDa_InfnService")
	private WenDa_InfnService wenDa_InfnService;
	@Resource(name="fenXiangService")
	private FenXiangService fenXiangService;
	@Resource(name="zuoGuo_ShiJuanService")
	private ZuoGuo_ShiJuanService zuoGuo_ShiJuanService;
	@Resource(name="error_WendaService")
	private Error_WendaService error_WendaService;
	@Resource(name="collection_WendaService")
	private Collection_WendaService collection_WendaService;
	@Resource(name="wanChengService")
	private WanChengService wanChengService;
	@Resource(name="fenXiangCService")
	private FenXiangCService fenXiangCService;
	
	
	/**
	 * 修改置顶
	 */
	@RequestMapping(value="/editXin")
	public void editXin(PrintWriter out){
		logBefore(logger, "修改是否未新");
		PageData pd = new PageData();
		pd=this.getPageData();
		try{
			wenDa_ShiJuanService.editXin(pd);
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value="/findWanCheng")
	public ModelAndView findWanCheng(Page page){
		logBefore(logger, "查询分享列表");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			page.setPd(pd);
			List<PageData>	varList = fenXiangService.list(page);
			mv.setViewName("system/wenda/fenxiang_list");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
			mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	@RequestMapping(value="Collectionlist")
	public ModelAndView Collectionlist(Page page){
		logBefore(logger, "查询收藏考题");
		ModelAndView mv=this.getModelAndView();
		PageData pd=new PageData();
		try{
			pd=this.getPageData();
				pd=this.getPageData();
				page.setPd(pd);
				List<PageData> varList=collection_WendaService.findCollectionlistPage(page);
				mv.setViewName("system/wenda/collection_wenda_list");
				mv.addObject("varList", varList);
				mv.addObject("pd", pd);
				mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
		}catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	@RequestMapping(value="/Zlist")
	public ModelAndView Zlist(Page page){
		logBefore(logger, "查询");
		ModelAndView mv=this.getModelAndView();
		PageData pd=new PageData();
		try{
			pd=this.getPageData();
			page.setPd(pd);
			List<PageData> varList=error_WendaService.findZuoTilistPage(page);
			mv.setViewName("system/wenda/zuoguo_wenda_list");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
			mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
		}catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	@RequestMapping(value="/Clist")
	public ModelAndView Clist(Page page){
		logBefore(logger, "查询错题排行榜");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			page.setPd(pd);
			List<PageData>	varList = error_WendaService.dataCuoTilistPage(page);	//列出Order_Info列表
			mv.setViewName("system/wenda/error_wenda_list");
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
	@RequestMapping(value="/WenDaYshujuduibi")
	public ModelAndView WenDaYshujuduibi(){
		logBefore(logger, "进行数据对比");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		String[] bArray = {"AFD8F8","F6BD0F","8BBA00", "FF8E46", "008E8E","D64646","8E468E","588526","B3AA00","008ED6","9D080D","A186BE"};  
		try{
			pd = this.getPageData();
			pd.put("MY", DateUtil.getYear());
			List<PageData>	varList = zuoGuo_ShiJuanService.findGenJuYue(pd);	//列出Order_Info列表
			String XML="<graph caption='次数对比表' xAxisName='月份' yAxisName='值' decimalPrecision='0' formatNumberScale='0'>";
			for(int i=0;i<varList.size();i++){
				XML+="<set name='"+varList.get(i).getString("YUE1")+"' value='"+varList.get(i).get("count1")+"' color='"+bArray[i]+"'/>";
			}
			XML+="</graph>";
			List<PageData> varLists=zuoGuo_ShiJuanService.findGenJuGYue(pd);
			String XML1="<graph caption='人数对比表' xAxisName='月份' yAxisName='值' decimalPrecision='0' formatNumberScale='0'>";
			for(int i=0;i<varLists.size();i++){
				XML1+="<set name='"+varLists.get(i).getString("YUE1")+"' value='"+varLists.get(i).get("count1")+"' color='"+bArray[i]+"'/>";
			}
			XML1+="</graph>";
			pd.put("strXML", XML);
			pd.put("strXML1", XML1);
			mv.setViewName("system/wenda/wenda_DuiBi3");
			mv.addObject("pd", pd);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	@RequestMapping(value="WenDaZshujuduibi")
	public ModelAndView WenDaZshujuduibi(){
		logBefore(logger, "进行数据对比");
		ModelAndView mv = this.getModelAndView();
		PageData pd=new PageData();
		pd=this.getPageData();
		try{
			String monday=MapDistance.getLastWeekSunday2();	
			String monday1=MapDistance.getLastWeekSunday();	
			String monday3=MapDistance.getLastWeekSunday3();
			String monday4=MapDistance.getLastWeekSunday4();
			String[] bArray = {"AFD8F8","F6BD0F","8BBA00", "FF8E46", "008E8E","D64646","8E468E","588526","B3AA00","008ED6","9D080D","A186BE"};
			pd.put("YUE", DateUtil.getDay());
			PageData pd_duibi1=new PageData();
			PageData pd_duibi2=new PageData();
			PageData pd_duibi3=new PageData();
			PageData pd_duibi4=new PageData();
			pd_duibi1.put("KAISHI", monday);
			pd_duibi1.put("END", monday1);
			pd_duibi2.put("KAISHI", monday3);
			pd_duibi2.put("END", monday4);
			pd_duibi3.put("KAISHI", monday);
			pd_duibi3.put("END", monday1);
			pd_duibi4.put("KAISHI", monday3);
			pd_duibi4.put("END", monday4);
			pd_duibi1=zuoGuo_ShiJuanService.findListDate(pd_duibi1);
			pd_duibi3=zuoGuo_ShiJuanService.findListGDate(pd_duibi3);
			pd_duibi2=zuoGuo_ShiJuanService.findListDate(pd_duibi2);
			pd_duibi4=zuoGuo_ShiJuanService.findListGDate(pd_duibi4);
			if(pd_duibi1==null){
				pd_duibi1=new PageData();
				pd_duibi1.put("count1", "0");
			}
			if(pd_duibi2==null){
				pd_duibi2=new PageData();
				pd_duibi2.put("count1", "0");
			}
			if(pd_duibi3==null){
				pd_duibi3=new PageData();
				pd_duibi3.put("count1", "0");
			}
			if(pd_duibi4==null){
				pd_duibi4=new PageData();
				pd_duibi4.put("count1", "0");
			}
			String XML="<graph caption='对比表' xAxisName='日期' yAxisName='值' decimalPrecision='0' formatNumberScale='0'>";
			XML+="<set name='"+monday4+"——"+monday3+"' value='"+pd_duibi1.get("count1")+"' color='"+bArray[0]+"'/>";
			XML+="<set name='"+monday+"——"+monday1+"' value='"+pd_duibi2.get("count1")+"' color='"+bArray[1]+"'/>";
			XML+="</graph>";
			pd.put("strXML", XML);
			String XML1="<graph caption='对比表' xAxisName='日期' yAxisName='值' decimalPrecision='0' formatNumberScale='0'>";
			XML1+="<set name='"+monday4+"——"+monday3+"' value='"+pd_duibi3.get("count1")+"' color='"+bArray[0]+"'/>";
			XML1+="<set name='"+monday+"——"+monday1+"' value='"+pd_duibi4.get("count1")+"' color='"+bArray[1]+"'/>";
			XML1+="</graph>";
			pd.put("strXML1", XML1);
			mv.setViewName("system/wenda/wenda_DuiBi2");
			mv.addObject("pd", pd);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	
	
	@RequestMapping(value="WenDaRshujuduibi")
	public ModelAndView WenDaRshujuduibi(){
		logBefore(logger, "进行数据对比");
		ModelAndView mv = this.getModelAndView();
		PageData pd=new PageData();
		pd=this.getPageData();
		try{
			String[] bArray = {"AFD8F8","F6BD0F","8BBA00", "FF8E46", "008E8E","D64646","8E468E","588526","B3AA00","008ED6","9D080D","A186BE"};
			PageData pd_duibi1=new PageData();
			PageData pd_duibi2=new PageData();
			PageData pd_duibi3=new PageData();
			PageData pd_duibi4=new PageData();
			if(pd.getString("lastLoginStart")!=null&&pd.getString("lastLoginEnd")!=null){
				pd.put("YUE", pd.getString("lastLoginStart"));
				pd_duibi1=zuoGuo_ShiJuanService.findYueCount(pd);
				pd_duibi3=zuoGuo_ShiJuanService.findYueGCount(pd);
				pd.put("YUE", pd.getString("lastLoginEnd"));
				pd_duibi2=zuoGuo_ShiJuanService.findYueCount(pd);
				pd_duibi4=zuoGuo_ShiJuanService.findYueGCount(pd);
			}else{
				pd.put("YUE", DateUtil.getDay());
				pd_duibi1=zuoGuo_ShiJuanService.findYueCount(pd);
				pd_duibi3=zuoGuo_ShiJuanService.findYueGCount(pd);
				pd.put("YUE", DateUtil.getSpecifiedDayBefore(DateUtil.getDay()));
				pd_duibi2=zuoGuo_ShiJuanService.findYueCount(pd);
				pd_duibi4=zuoGuo_ShiJuanService.findYueGCount(pd);
			}
			if(pd_duibi1==null){
				pd_duibi1.put("count1", "0");
				pd_duibi1.put("YUE", "0");
			}
			if(pd_duibi2==null){
				pd_duibi2=new PageData();
				pd_duibi2.put("count1", "0");
			}
			if(pd_duibi3==null){
				pd_duibi3=new PageData();
				pd_duibi3.put("count1", "0");
			}
			if(pd_duibi4==null){
				pd_duibi4=new PageData();
				pd_duibi4.put("count1", "0");
			}
			String XML="<graph caption='次数对比表' xAxisName='日期' yAxisName='值' decimalPrecision='0' formatNumberScale='0'>";
			XML+="<set name='"+pd_duibi2.getString("YUE")+"' value='"+pd_duibi2.get("count1")+"' color='"+bArray[0]+"'/>";
			XML+="<set name='"+pd_duibi1.getString("YUE")+"' value='"+pd_duibi1.get("count1")+"' color='"+bArray[1]+"'/>";
			XML+="</graph>";
			pd.put("strXML", XML);
			String XML1="<graph caption='人数对比表' xAxisName='日期' yAxisName='值' decimalPrecision='0' formatNumberScale='0'>";
			XML1+="<set name='"+pd_duibi4.getString("YUE")+"' value='"+pd_duibi4.get("count1")+"' color='"+bArray[0]+"'/>";
			XML1+="<set name='"+pd_duibi3.getString("YUE")+"' value='"+pd_duibi3.get("count1")+"' color='"+bArray[1]+"'/>";
			XML1+="</graph>";
			pd.put("strXML1", XML1);
			mv.setViewName("system/wenda/wenda_DuiBi");
			mv.addObject("pd", pd);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * 修改置顶
	 */
	@RequestMapping(value="/zhiding")
	public void zhiding(PrintWriter out){
		logBefore(logger, "修改问答置顶");
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			wenDa_ShiJuanService.editZhiDing(pd);
			wenDa_ShiJuanService.editZhiDings(pd);
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		
	}
	
	// 删除问答问题图片
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
					PageData pd_w=wenDa_InfnService.findById(pd);
					pd_w.put("IMG", "");
					wenDa_InfnService.edit(pd_w);
				}
				
				//out.write("success");
				out.close();
				return "success";
			} catch (Exception e) {
				logger.error(e.toString(), e);
				return "fail";
			}
		}
		
		// 删除试卷图片
				@RequestMapping(value = "/wenda_deltp")
				@ResponseBody
				public String wenda_deltp(PrintWriter out) {
					logBefore(logger, "删除试卷图片");
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
							PageData pd_w=wenDaService.findById(pd);
							pd_w.put("IMG", "");
							wenDaService.edit(pd_w);
						}
						//out.write("success");
						out.close();
						return "success";
					} catch (Exception e) {
						logger.error(e.toString(), e);
						return "fail";
					}
				}
		
		// 删除试卷图片
		@RequestMapping(value = "/shijuan_deltp")
		@ResponseBody
		public String shijuan_deltp(PrintWriter out) {
			logBefore(logger, "删除试卷图片");
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
					PageData pd_w=wenDa_ShiJuanService.findById(pd);
					pd_w.put("IMG", "");
					wenDa_ShiJuanService.edit(pd_w);
				}
				//out.write("success");
				out.close();
				return "success";
			} catch (Exception e) {
				logger.error(e.toString(), e);
				return "fail";
			}
		}
		
		
		// 删除试卷图片
				@RequestMapping(value = "/shijuan_deltpp")
				@ResponseBody
				public String shijuan_deltpp(PrintWriter out) {
					logBefore(logger, "删除试卷图片");
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
							PageData pd_w=wenDa_ShiJuanService.findById(pd);
							pd_w.put("FIMG", "");
							wenDa_ShiJuanService.edit(pd_w);
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
	@RequestMapping(value="/info_delete")
	public void info_delete(PrintWriter out){
		logBefore(logger, "删除答题试卷");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			wenDa_InfnService.delete(pd);
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/ShiJuan_delete")
	public void ShiJuan_delete(PrintWriter out){
		logBefore(logger, "删除答题试卷");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			wenDa_ShiJuanService.delete(pd);
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/type_delete")
	public void type_delete(PrintWriter out){
		logBefore(logger, "删除答题类型");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			wenDa_TypeService.delete(pd);
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		
	}
	
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/WenDa_delete")
	public void WenDa_delete(PrintWriter out){
		logBefore(logger, "删除答题类型");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			wenDaService.delete(pd);
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		
	}
	
	/**
	 * 修改
	 */
	@RequestMapping(value="/ShiJuanedit")
	public ModelAndView ShiJuanedit() throws Exception{
		logBefore(logger, "修改答题试卷");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		if(pd.getString("STATUS").equals("1")){
			pd.put("DATE", DateUtil.getTime());
			wenDa_ShiJuanService.edit(pd);
		}else{
			wenDa_ShiJuanService.edit(pd);
		}
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 修改
	 */
	@RequestMapping(value="/Typeedit")
	public ModelAndView Typeedit() throws Exception{
		logBefore(logger, "修改问答类型");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		wenDa_TypeService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 修改
	 */
	@RequestMapping(value="/WenDaedit")
	public ModelAndView WenDaedit() throws Exception{
		logBefore(logger, "修改问题");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		wenDaService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 修改
	 */
	@RequestMapping(value="/Infoedit")
	public ModelAndView Infoedit() throws Exception{
		logBefore(logger, "修改问题答案");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		wenDa_InfnService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 去修改页面
	 */
	@RequestMapping(value="/goShiJuanEdit")
	public ModelAndView goShiJuanEdit(){
		logBefore(logger, "去修改答题试卷页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {	
			pd = wenDa_ShiJuanService.findById(pd);	//根据ID读取	
			mv.setViewName("system/wenda/wenda_shijuan_edit");
			mv.addObject("msg", "ShiJuanedit");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}
	
	/**
	 * 去修改页面
	 */
	@RequestMapping(value="/goInfoEdit")
	public ModelAndView goInfoEdit(){
		logBefore(logger, "去修改答题答案页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {	
			pd = wenDa_InfnService.findById(pd);	//根据ID读取	
			mv.setViewName("system/wenda/wenda_info_edit");
			mv.addObject("msg", "Infoedit");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}
	
	
	/**
	 * 去修改页面
	 */
	@RequestMapping(value="/goWenDaEdit")
	public ModelAndView goWenDaEdit(){
		logBefore(logger, "去修改答题类型页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {	
			pd = wenDaService.findById(pd);	//根据ID读取	
			mv.setViewName("system/wenda/wenda_wenda_edit");
			mv.addObject("msg", "WenDaedit");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}
	
	/**
	 * 去修改页面
	 */
	@RequestMapping(value="/goTypeEdit")
	public ModelAndView goTypeEdit(){
		logBefore(logger, "去修改答题类型页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {	
			pd = wenDa_TypeService.findById(pd);	//根据ID读取	
			mv.setViewName("system/wenda/wenda_type_edit");
			mv.addObject("msg", "Typeedit");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}
	
	/**
	 * 新增
	 */
	@RequestMapping(value="/shijuan_add")
	public ModelAndView shijuan_add() throws Exception{
		logBefore(logger, "新增问答试卷");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("WENDA_SHIJUAN_ID", this.get32UUID());
		pd.put("DATE", DateUtil.getTime());
		pd.put("ZHIDING", "0");
		wenDa_ShiJuanService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 新增
	 */
	@RequestMapping(value="/wenda_add")
	public ModelAndView wenda_add() throws Exception{
		logBefore(logger, "新增问答试卷");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("WENDA_ID", this.get32UUID());
		pd.put("DATE", DateUtil.getTime());
		pd.put("ORDE_BY", "");
		wenDaService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 新增
	 */
	@RequestMapping(value="/type_add")
	public ModelAndView type_add() throws Exception{
		logBefore(logger, "新增问答类型");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("WENDA_TYPE_ID", this.get32UUID());
		wenDa_TypeService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 新增
	 */
	@RequestMapping(value="/info_add")
	public ModelAndView info_add() throws Exception{
		logBefore(logger, "新增问答类型");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("WENDA_INFO_ID", this.get32UUID());
		wenDa_InfnService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	
	/**
	 * 去修改页面
	 */
	@RequestMapping(value="/goWenDaAdd")
	public ModelAndView goWenDaAdd(){
		logBefore(logger, "去修改问题页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {	
			mv.setViewName("system/wenda/wenda_wenda_edit");
			mv.addObject("msg", "wenda_add");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}
	
	/**
	 * 去修改页面
	 */
	@RequestMapping(value="/goShiJuanAdd")
	public ModelAndView goShiJuanAdd(){
		logBefore(logger, "去修改问答试卷页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {	
			mv.setViewName("system/wenda/wenda_shijuan_edit");
			mv.addObject("msg", "shijuan_add");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}
	
	/**
	 * 去修改页面
	 */
	@RequestMapping(value="/goTypeAdd")
	public ModelAndView goTypeAdd(){
		logBefore(logger, "去修改问答类型页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {	
			//pd = wenDa_TypeService.findById(pd);	//根据ID读取	
			mv.setViewName("system/wenda/wenda_type_edit");
			mv.addObject("msg", "type_add");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}
	
	/**
	 * 去修改页面
	 */
	@RequestMapping(value="/goInfoAdd")
	public ModelAndView goInfoAdd(){
		logBefore(logger, "去修改问题答案页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {	
			mv.setViewName("system/wenda/wenda_info_edit");
			mv.addObject("msg", "info_add");
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
		logBefore(logger, "查询订单列表");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			page.setPd(pd);
			List<PageData>	varList = wenDa_TypeService.list(page);	//列出Order_Info列表
			mv.setViewName("system/wenda/wenda_type_list");
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
	@RequestMapping(value="/shijuan_list")
	public ModelAndView shijuan_list(Page page){
		logBefore(logger, "查询问答试卷列表");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			String KEYWORD = pd.getString("KEYWORD");
			if (null != KEYWORD && !"".equals(KEYWORD)) {
				KEYWORD = KEYWORD.trim();
				pd.put("KEYWORD", KEYWORD);
			}
			page.setPd(pd);
			List<PageData>	varList = wenDa_ShiJuanService.list(page);	//列出Order_Info列表
			mv.setViewName("system/wenda/wenda_shijuan_list");
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
	@RequestMapping(value="/wenda_list")
	public ModelAndView wenda_list(Page page){
		logBefore(logger, "查询问题列表");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			String KEYWORD = pd.getString("KEYWORD");
			if (null != KEYWORD && !"".equals(KEYWORD)) {
				KEYWORD = KEYWORD.trim();
				pd.put("KEYWORD", KEYWORD);
			}
			page.setPd(pd);
			List<PageData>	varList = wenDaService.list(page);	//列出Order_Info列表
			mv.setViewName("system/wenda/wenda_wenda_list");
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
	@RequestMapping(value="/info_list")
	public ModelAndView info_list(Page page){
		logBefore(logger, "查询问题选项列表");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			String KEYWORD = pd.getString("KEYWORD");
			if (null != KEYWORD && !"".equals(KEYWORD)) {
				KEYWORD = KEYWORD.trim();
				pd.put("KEYWORD", KEYWORD);
			}
			page.setPd(pd);
			List<PageData>	varList = wenDa_InfnService.list(page);	//列出Order_Info列表
			mv.setViewName("system/wenda/wenda_info_list");
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
	@RequestMapping(value="/fenxiang_list")
	public ModelAndView fenxiang_list(Page page){
		logBefore(logger, "查询分享列表");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			page.setPd(pd);
			List<PageData>	varList = fenXiangCService.datalistPage(page);
			mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
			mv.setViewName("system/wenda/fenxiang_list");
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
	@RequestMapping(value="/wancheng_list")
	public ModelAndView wancheng_list(Page page){
		logBefore(logger, "查询中间退出列表");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			page.setPd(pd);
			List<PageData>	varList = wanChengService.datalistPage(page);
			mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
			mv.setViewName("system/wenda/fenxiang_list");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	@RequestMapping(value="/fenxiang_lists")
	public ModelAndView fenxiang_lists(Page page){
		logBefore(logger, "查询分享列表");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		try{
			mv.setViewName("system/wenda/wenda_fenxiang");
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
