package com.fh.controller.system.fight;

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
import org.codehaus.jackson.map.ser.StdSerializers.IntegerSerializer;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.app.appuser.Wtuishong;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.system.dingdanchucuo.DingdanchucuoService;
import com.fh.service.system.fight.FightService;
import com.fh.service.system.fight_info.Fight_InfoService;
import com.fh.service.system.integral.IntegralService;
import com.fh.service.system.token.TokenService;
import com.fh.util.Const;
import com.fh.util.DateUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.weixinm.Refund;

@Controller
@RequestMapping(value="fight",produces="text/html;charset=UTF-8")
public class FightController extends BaseController{
	String menuUrl="fight/list.do";
	@Resource(name="fightService")
	private FightService fightService;
	@Resource(name="fight_InfoService")
	private Fight_InfoService fight_InfoService;
	@Resource(name="integralService")
	private IntegralService integralService;
	@Resource(name="tokenService")
	private TokenService tokenService;
	@Resource(name="dingdanchucuoService")
	private DingdanchucuoService dingdanchucuoService;
	
	
	/*
	 * 导出到excel
	 * @return
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel(){
		logBefore(logger, "导出Order_Info到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("订单号");	//0
			titles.add("商品");	//1
			titles.add("下单时间");	//2
			titles.add("收货人");	//3
			titles.add("收货人电话");	//4
			titles.add("收货地址");	//5
			titles.add("支付金额");	//6
			titles.add("快递公司");	//7
			titles.add("快递单号");	//8
			titles.add("分配");	//9
			titles.add("所属谁的团");	//10
			dataMap.put("titles", titles);
			List<PageData> varOList = fight_InfoService.findList2(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).getString("ORDER_NUMBER"));	//0
				vpd.put("var2", varOList.get(i).getString("PRODUCT"));
				vpd.put("var3", varOList.get(i).getString("DATE"));
				vpd.put("var4", varOList.get(i).getString("NAME"));
				vpd.put("var5", varOList.get(i).getString("PHONE"));
				vpd.put("var6", varOList.get(i).getString("ADDRESS"));
				vpd.put("var7", varOList.get(i).getString("MONEY"));
				vpd.put("var8", varOList.get(i).getString("KUAIDI"));
				vpd.put("var9", varOList.get(i).getString("NUMBER"));
				vpd.put("var10", varOList.get(i).getString("STATUS1"));
				vpd.put("var11", varOList.get(i).getString("NAME1"));
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
	
	
	@RequestMapping(value="Dtixing")
	public void Dtixing(PrintWriter out){
		logBefore(logger, "团购到期提醒!");
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			PageData pd_f=fightService.findById(pd);
			pd.put("STATUS", "1");
			List<PageData> list=fight_InfoService.findList1(pd);
			for(int i=0;i<list.size();i++){
				System.err.println(list.get(i).getString("KUAIDI"));
				System.err.println(list.get(i).getString("NUMBER"));
				new Thread(new Wtuishong("本次团购已成功,物流已发送", list.get(i).getString("ORDER_NUMBER"), list.get(i).getString("NAME"), list.get(i).getString("KUAIDI"), list.get(i).getString("NUMBER"),"点开此链接，可查看详细物流信息", list.get(i).getString("OPENID"),"https://m.kuaidi100.com/index_all.html?type="+list.get(i).getString("KUAIDI")+"&postid="+list.get(i).getString("NUMBER"),tokenService,"D_SRohcaAfJAecjbcYaMmP-ACFoyd8gDrehiECHT-eA")).start();
			}
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/tixing")
	public void tixing(PrintWriter out){
		logBefore(logger, "团购提醒!");
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			pd.put("STATUS", "1");
			List<PageData> list=fight_InfoService.findList(pd);
			for(int i=0;i<list.size();i++){			
				new Thread(new Wtuishong("你的团购还没有成功,赶快邀请好友来参团!(活动2017-08-17 12:00:00中午结束!)", list.get(i).getString("NAME"),list.get(i).getString("PHONE"),"团购还没有成功。", "","你的团购还没有成功,赶快邀请好友来参团!(活动2017-08-17 12:00:00中午结束!)", list.get(i).getString("OPENID"),"http://www.meitiannongzi.com/nongjike/middle.html?FIGHT_ID="+list.get(i).getString("FIGHT_ID"),tokenService,"yATpiH3KWG4nUhRZYoLsi8GjG8zOxUSX0kO0AuaJUec")).start();
			}
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		
	}
	
	/**
	 * 删除
	 */
/*	@RequestMapping(value="Dtixing")
	public void Dtixing(PrintWriter out){
		logBefore(logger, "团购到期提醒!");
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			PageData pd_f=fightService.findById(pd);
			pd.put("STATUS", "1");
			List<PageData> list=fight_InfoService.findList1(pd);
			for(int i=0;i<list.size();i++){
				if(!pd_f.getString("STATUS").equals("1")){
					new Thread(new Wtuishong("您好，您的订单"+list.get(i).getString("ORDER_NUMBER")+"未成功！", "农极客病害图谱挂图",list.get(i).getString("MONEY"),"很遗憾，开团人数未达3人！", "","截止到今天中午12:00，“病害图谱挂图1元抢”的活动就要结束了。开团没有够人数的，要赶紧啦！点击本连接，分享到朋友圈邀请好友来参加。", list.get(i).getString("OPENID"),"http://www.meitiannongzi.com/nongjike/middle.html?FIGHT_ID="+list.get(i).getString("FIGHT_ID"),tokenService,"4x0kUXJmi-mdFFJYwyY0G50JKDH7debIU5--HQcfvSE")).start();
				}else{
					new Thread(new Wtuishong("您好，您的订单"+list.get(i).getString("ORDER_NUMBER")+"已经成功！","农极客病害图谱挂图",list.get(i).getString("MONEY"),"开团人数已成功达到3人！", "","截止到今天中午12:00，“病害图谱挂图1元抢”的活动就要结束了。查看公众号菜单栏“我的订单”，可即时了解物流发货信息。", list.get(i).getString("OPENID"),"",tokenService,"4x0kUXJmi-mdFFJYwyY0G50JKDH7debIU5--HQcfvSE")).start();
				}
				
			}
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		
	}*/
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/tuikuan")
	public void tuikuan(PrintWriter out){
		logBefore(logger, "团购退款!");
		PageData pd = new PageData();
		Refund re= new Refund();
		try{
			pd = this.getPageData();
			PageData pd_f=fightService.findById(pd);
			List<PageData> list=fight_InfoService.findList(pd_f);
			for(int i=0;i<list.size();i++){
				//Integer money=Integer.valueOf(list.get(i).getString("MONEY"))*100;
				String aa=re.wechatRefund(list.get(i).getString("ORDER_NUMBER"),list.get(i).getString("MONEY"));			
				if(aa.equals("SUCCESS")){
					list.get(i).put("STATUS", "3");
					fight_InfoService.editStatus(list.get(i));
					list.get(i).put("DATE", DateUtil.getTime());
					dingdanchucuoService.save(list.get(i));
					pd_f.put("STATUS", "3");
					fightService.edit(pd_f);
					new Thread(new Wtuishong("农极客团购退款通知", list.get(i).getString("MONEY"),DateUtil.getTime(),list.get(i).getString("ORDER_NUMBER"), "","您的订单过期没有成功，点击查看订单详情。", list.get(i).getString("OPENID"),"http://www.meitiannongzi.com/nongjike/group.html",tokenService,"W5g6JIL81GRG1zd63OpFMfSeiZtnF6P4Dyb5lLkQg20")).start();
				}else{
					list.get(i).put("STATUS", "4");
					fight_InfoService.editStatus(list.get(i));
					list.get(i).put("DATE", DateUtil.getTime());
					dingdanchucuoService.save(list.get(i));
					pd_f.put("STATUS", "4");
					fightService.edit(pd_f);
				}
			}
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
		logBefore(logger, "修改团购订单详情");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		PageData pd_1=fight_InfoService.findById(pd);
		fight_InfoService.edit(pd);
		if(!pd_1.getString("KUAIDI").equals(pd.getString("KUAIDI"))||!pd_1.getString("NUMBER").equals(pd.getString("NUMBER"))){
			new Thread(new Wtuishong("本次团购已成功,物流已发送", pd_1.getString("ORDER_NUMBER"), pd_1.getString("NAME"), pd.getString("KUAIDI"), pd.getString("NUMBER"),"点击此信息进入页面", pd_1.getString("OPENID"),"https://m.kuaidi100.com/index_all.html?type="+pd.getString("KUAIDI")+"&postid="+pd.getString("NUMBER"),tokenService,"D_SRohcaAfJAecjbcYaMmP-ACFoyd8gDrehiECHT-eA")).start();
		}
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 去修改页面
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit(){
		logBefore(logger, "去修改团购订单详情页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {	
			pd = fight_InfoService.findById(pd);	//根据ID读取	
			mv.setViewName("system/fight/fight_info_edit");
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
		logBefore(logger, "查询团购订单详情列表");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			page.setPd(pd);
			List<PageData>	varList = fight_InfoService.datalistPage(page);	//列出Pro_Info列表
			mv.setViewName("system/fight/fight_info_list");
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
		logBefore(logger, "查询团购订单列表");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		User u = Jurisdiction.getCurrentUserID();//获取当前的USER_ID
		try{
			PageData pd_i=integralService.findById(pd);
			pd = this.getPageData();
			page.setPd(pd);
			List<PageData>	varList = fightService.datalistPage(page);	//列出Pro_Info列表
/*			for(int i=0;i<varList.size();i++){
				List<PageData> list=fight_InfoService.findList(varList.get(i));
				if(varList.get(i).getString("STATUS")!=1)
			}*/
			pd.put("DATE1", DateUtil.getTime());
			fightService.editTu(pd);
			mv.setViewName("system/fight/fight_list");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
			pd.put("GUANLI", u.getUSERNAME());
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
