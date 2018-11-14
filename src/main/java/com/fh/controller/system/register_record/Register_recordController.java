package com.fh.controller.system.register_record;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

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
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.system.register_record.Register_recordService;

@Controller
@RequestMapping(value="register_record")
public class Register_recordController extends BaseController{

	String menuUrl = "register_record/list.do"; //菜单地址(权限用)
	@Resource(name="register_recordService")
	private Register_recordService register_recordService;
	
	
	/**
	 * 列表
	 */
	@RequestMapping(value="/xiaoyi")
	public ModelAndView xiaoyi(){
		logBefore(logger, "查询月订单效益对比");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		String[] bArray = {"AFD8F8","F6BD0F","8BBA00", "FF8E46", "008E8E","D64646","8E468E","588526","B3AA00","008ED6","9D080D","A186BE"};  
		try{
			pd = this.getPageData();
			pd.put("NIAN1", DateUtil.getYear());
			List<PageData>	varList = register_recordService.findYDuiBi(pd);	//列出Order_Info列表
			String XML="<graph caption='月比对' xAxisName='月份' yAxisName='值' decimalPrecision='0' formatNumberScale='0'>";
			for(int i=0;i<varList.size();i++){
				XML+="<set name='"+varList.get(i).getString("YUE")+"' value='"+varList.get(i).get("cont1")+"' color='"+bArray[i]+"'/>";
			}
			XML+="</graph>";
			System.out.println(XML);
			pd.put("strXML", XML);
			mv.setViewName("system/register_record/register_record_Ydui");
			mv.addObject("pd", pd);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	
	/**
	 * 列表
	 */
	@RequestMapping(value="/xiaoyi2")
	public ModelAndView xiaoyi2(){
		logBefore(logger, "查询周订单效益对比");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		String[] bArray = {"AFD8F8","F6BD0F","8BBA00", "FF8E46", "008E8E","D64646","8E468E","588526","B3AA00","008ED6","9D080D","A186BE"};  
		try{
			String monday= MapDistance.getLastWeekSunday2();	//上周周一
			String monday1=MapDistance.getLastWeekSunday();	//上周周日
			String monday3=MapDistance.getLastWeekSunday3();	//上上周周日
			String monday4=MapDistance.getLastWeekSunday4();	//上上周周一
			String monday5 = MapDistance.getLastWeekSundaysss();         //end
			String monday6 = MapDistance.getLastWeekSundaysss1();       //start
			String monday7 = MapDistance.getLastWeekSunday41();         //end
			String monday8 = MapDistance.getLastWeekSunday411();
			System.out.println(monday);
			System.out.println(monday1);
			System.out.println(monday3);
			System.out.println(monday4);
			PageData pd1 = new PageData();
			PageData pd2 = new PageData();
			PageData pd3 = new PageData();
			PageData pd4 = new PageData();
			pd1.put("KAISHI", monday4);
			pd1.put("END", monday3);
			pd2.put("KAISHI", monday);
			pd2.put("END", monday1);
			pd3.put("KAISHI", monday6);
			pd3.put("END", monday5);
			pd4.put("KAISHI", monday8);
			pd4.put("END", monday7);
			pd1=register_recordService.findListDate(pd1);
			pd2=register_recordService.findListDate(pd2);
			pd3=register_recordService.findListDate(pd3);
			pd4=register_recordService.findListDate(pd4);
			if (pd1 == null) {
				pd1 = new PageData();
				pd1.put("cont1", "0");
			}
			if (pd2 == null) {
				pd2 = new PageData();
				pd2.put("cont1", "0");
			}
			if (pd3 == null) {
				pd3 = new PageData();
				pd3.put("cont1", "0");
			}
			if (pd4 == null) {
				pd4 = new PageData();
				pd4.put("cont1", "0");
			}
			String XML="<graph caption='周对比' xAxisName='日期' yAxisName='值' decimalPrecision='0' formatNumberScale='0'>";
			XML+="<set name='"+monday8+"——"+monday7+"' value='"+pd1.get("cont1")+"' color='"+bArray[0]+"'/>";
			XML+="<set name='"+monday6+"——"+monday5+"' value='"+pd2.get("cont1")+"' color='"+bArray[1]+"'/>";
			XML+="<set name='"+monday4+"——"+monday3+"' value='"+pd3.get("cont1")+"' color='"+bArray[2]+"'/>";
			XML+="<set name='"+monday+"——"+monday1+"' value='"+pd4.get("cont1")+"' color='"+bArray[3]+"'/>";
			XML+="</graph>";
			pd.put("strXML", XML);
			mv.setViewName("system/register_record/register_record_Zdui");
			mv.addObject("pd", pd);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value="/xiaoyi1")
	public ModelAndView xiaoyi1(){
		logBefore(logger, "查询日注册效益对比");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		String[] bArray = {"AFD8F8","F6BD0F","8BBA00", "FF8E46", "008E8E","D64646","8E468E","588526","B3AA00","008ED6","9D080D","A186BE"};  
		try{
			/*Calendar   cal   =   Calendar.getInstance();
			cal.add(Calendar.DATE,   -2);
			String QT = new SimpleDateFormat( "yyyy-MM-dd").format(cal.getTime());
			Calendar   cal1   =   Calendar.getInstance();
			cal1.add(Calendar.DATE,   -1);
			String ZT = new SimpleDateFormat( "yyyy-MM-dd").format(cal1.getTime());
			PageData pd1=new PageData();
			pd1.put("TIAN", QT.trim());
			pd1=register_recordService.findTDate(pd1);
			PageData pd2=new PageData();
			pd2.put("TIAN", ZT.trim());
			pd2=register_recordService.findTDate(pd2);
			if(pd1.get("cont1").toString().equals("0")){
				pd1=new PageData();
				pd1.put("DATE", QT);
				pd1.put("MONEY", "0");
			}
			if(pd2.get("cont1").toString().equals("0")){
				pd2=new PageData();
				pd2.put("DATE", ZT);
				pd2.put("MONEY", "0");
			}*/
			List<String> dateList = XiaoYiDateT.getDateList(new Date());
			List<PageData> pageList = new ArrayList<PageData>();
			PageData pageData = null;
			for (String str : dateList) {
				pageData = new PageData();
				pageData.put("DATE", str.trim());
				pageData = register_recordService.findTDate(pageData);
				if (pageData == null) {
					pageData = new PageData();
					pageData.put("DATE", str);
					pageData.put("cont1", "0");
				}
				pageList.add(pageData);
			}
			String XML="<graph caption='日对比' xAxisName='日期' yAxisName='值' decimalPrecision='0' formatNumberScale='0'>";
			int i,size = pageList.size();
			for (i=0;i<size;i++) {
				XML += "<set name='" + pageList.get(i).getString("DATE") + "' value='" + pageList.get(i).get("cont1") + "' color='" + bArray[i] + "'/>";
			}
			XML+="</graph>";
			pd.put("strXML", XML);
			mv.setViewName("system/register_record/register_record_Zdui");
			mv.addObject("pd", pd);
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
		logBefore(logger, "查询订单列表");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			String KEYWORD = pd.getString("KEYWORD");
			if (null != KEYWORD && !"".equals(KEYWORD)) {
				KEYWORD = KEYWORD.trim();
				pd.put("KEYWORD", KEYWORD);
			}
			//时间查询
			String lastLoginStart = pd.getString("lastLoginStart");
			String lastLoginEnd = pd.getString("lastLoginEnd");

			if (lastLoginStart != null && !"".equals(lastLoginStart)) {
				pd.put("lastLoginStart", lastLoginStart);
			}
			if (lastLoginEnd != null && !"".equals(lastLoginEnd)) {
				pd.put("lastLoginEnd", lastLoginEnd);
			}
			page.setPd(pd);
			List<PageData>	varList = register_recordService.list(page);	//列出Order_Info列表
			mv.setViewName("system/register_record/register_record_list");
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
