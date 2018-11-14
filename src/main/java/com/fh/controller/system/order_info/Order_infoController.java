package com.fh.controller.system.order_info;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import javax.annotation.Resource;

import com.fh.service.system.exclu.ExcluService;
import com.fh.util.*;
import org.apache.poi.ss.usermodel.Workbook;
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

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.system.order_info.Order_infoService;
import com.fh.service.system.sys_city.Sys_cityService;
import com.fh.util.alipay.AlipayConfig;
import com.fh.util.weixinm.Refund;

@Controller
@RequestMapping(value = "order_info")
public class Order_infoController extends BaseController {

    String menuUrl = "order_info/list.do"; //菜单地址(权限用)
    @Resource(name = "order_infoService")
    private Order_infoService order_infoService;
    @Resource(name = "sys_cityService")
    private Sys_cityService sys_cityService;
    @Resource(name = "excluService")
    private ExcluService excluService;


    /**
     * 删除
     */
    @RequestMapping(value = "/tuikuan")
    public void tuikuan(PrintWriter out) {
        logBefore(logger, "团购退款!");
        PageData pd = new PageData();
        Refund re = new Refund();
        try {
            pd = this.getPageData();
            PageData od = order_infoService.findById(pd);
            if (od.getString("PAY_METHOD").equals("wx")) {
                double money1 = Double.valueOf(od.getString("MONEY")) * 100;
                int money2 = (int) money1;
                String aa = re.wechatRefund1(od.getString("ORDER_NUMBER"), String.valueOf(money2));
                if (aa.equals("SUCCESS")) {
                    od.put("STATUS", "7");
                    order_infoService.editStatus(od);
                } else {
                    od.put("STATUS", "8");
                    order_infoService.editStatus(od);
                }
            } else if (od.getString("PAY_METHOD").equals("alipay")) {
                AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",
                        AlipayConfig.app_id, AlipayConfig.private_key, "json", "utf-8", AlipayConfig.alipay_public_key, "RSA");
                AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
                String ORDER_NUMBER = od.getString("ORDER_NUMBER");
                String MONEY = od.getString("MONEY");
                request.setBizContent("{" +
                        "\"out_trade_no\":\"" + ORDER_NUMBER + "\"," +
                        "\"refund_amount\":" + MONEY + "," +
                        "\"refund_reason\":\"正常退款\"," +
                        "\"out_request_no\":\"HZ01RF001\"," +
                        "\"operator_id\":\"OP001\"," +
                        "\"store_id\":\"NJ_S_001\"," +
                        "\"terminal_id\":\"NJ_T_001\"" +
                        "  }");
                AlipayTradeRefundResponse response = alipayClient.execute(request);
                if (response.isSuccess()) {
                    od.put("STATUS", "7");
                    order_infoService.editStatus(od);
                } else {
                    od.put("STATUS", "8");
                    order_infoService.editStatus(od);
                }
            }
            out.write("success");
            out.close();
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }

    }

    /**
     * 列表
     */
    @RequestMapping(value = "/xiaoyi")
    public ModelAndView xiaoyi() {
        logBefore(logger, "查询月订单效益对比");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        String[] bArray = {"AFD8F8", "F6BD0F", "8BBA00", "FF8E46", "008E8E", "D64646", "8E468E", "588526", "B3AA00", "008ED6", "9D080D", "A186BE"};
        try {
            pd = this.getPageData();
            pd.put("NIAN1", DateUtil.getYear());
            List<PageData> varList = order_infoService.findDuiBi(pd);          //列出Order_Info列表
            String XML = "<graph caption='本月对比表' xAxisName='月份' yAxisName='值' decimalPrecision='0' formatNumberScale='0'>";
            for (int i = 0; i < varList.size(); i++) {
                XML += "<set name='" + varList.get(i).getString("YUE") + "' value='" + varList.get(i).get("MONEY") + "' color='" + bArray[i] + "'/>";
            }
            XML += "</graph>";
            System.out.println(XML);
            pd.put("strXML", XML);
            mv.setViewName("system/order_info/order_info_dui");
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }


    /**
     * 列表
     */
    @RequestMapping(value = "/xiaoyi1")
    public ModelAndView xiaoyi1() {
        logBefore(logger, "查询日订单效益对比");
        Calendar calendar = Calendar.getInstance();
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        String[] bArray = {"AFD8F8", "F6BD0F", "8BBA00", "FF8E46", "008E8E", "D64646", "8E468E", "588526", "B3AA00", "008ED6", "9D080D", "A186BE"};
        try {
			/*Calendar   cal   =   Calendar.getInstance();
			cal.add(Calendar.DATE,   -2);
			String QT = new SimpleDateFormat( "yyyy-MM-dd").format(cal.getTime());
			Calendar   cal1   =   Calendar.getInstance();
			cal1.add(Calendar.DATE,   -1);
			String ZT = new SimpleDateFormat( "yyyy-MM-dd").format(cal1.getTime());
			PageData pd1=new PageData();
			pd1.put("TIAN", QT.trim());
			pd1=order_infoService.findTDuiBi(pd1);
			PageData pd2=new PageData();
			pd2.put("TIAN", ZT.trim());
			pd2=order_infoService.findTDuiBi(pd2);*/
            List<String> dateList = XiaoYiDateT.getDateList(new Date());
            List<PageData> pageList = new ArrayList<PageData>();
            PageData pageData = null;
            for (String str : dateList) {
                pageData = new PageData();
                pageData.put("TIAN", str.trim());
                pageData = order_infoService.findTDuiBi(pageData);
                if (pageData == null) {
                    pageData = new PageData();
                    pageData.put("TIAN", str);
                    pageData.put("MONEY", "0");
                }
                pageList.add(pageData);
            }
            String XML = "<graph caption='昨天天对比' xAxisName='日期' yAxisName='值' decimalPrecision='0' formatNumberScale='0'>";
            int i,size = pageList.size();
            for (i=size-1;i>=0;i--) {
                XML += "<set name='" + pageList.get(i).getString("TIAN") + "' value='" + pageList.get(i).get("MONEY") + "' color='" + bArray[i] + "'/>";
            }
            XML += "</graph>";
            pd.put("strXML", XML);
            mv.setViewName("system/order_info/order_info_Tdui");
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }
    /**
     * 列表
     */
    @RequestMapping(value = "/xiaoyi3")
    public ModelAndView xiaoyi3() {
        logBefore(logger, "查询小时订单效益对比");
        Calendar calendar = Calendar.getInstance();
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        Random ran  = new Random();
        String[] bArray = {"AFD8F8", "F6BD0F", "8BBA00", "FF8E46", "008E8E", "D64646", "8E468E", "588526", "B3AA00", "008ED6", "9D080D", "A186BE","AFD8F8", "F6BD0F", "8BBA00", "FF8E46", "008E8E", "D64646", "8E468E", "588526", "B3AA00", "008ED6", "9D080D", "A186BE"};
        try {
            List<PageData> pageList = new ArrayList<PageData>();
            PageData pageData = null;
            pd=this.getPageData();
            String date  = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            String BEGIN = pd.getString("BEGIN");
            String XML ="";
            if(null !=BEGIN && !"".equals(BEGIN)){
                pd.put("TIAN",BEGIN);
                String END = pd.getString("END");
                    if(null !=END && !"".equals(END)){
                        pd.put("ENDTIAN",END);
                        END = "-"+END;
                        XML="<graph caption='"+BEGIN+END+"对比' xAxisName='小时' yAxisName='值' decimalPrecision='0' formatNumberScale='0'>";
                    }else{
                        XML="<graph caption='"+BEGIN+"对比' xAxisName='小时' yAxisName='值' decimalPrecision='0' formatNumberScale='0'>";
                    }
            }else{
                pd.put("TIAN",date);
                XML="<graph caption='"+date+"对比' xAxisName='小时' yAxisName='值' decimalPrecision='0' formatNumberScale='0'>";
            }
            if(null !=pd.getString("ENDTIAN") && !"".equals(pd.getString("ENDTIAN"))){
                pageList =order_infoService.findOrderC(pd);
                int siz = 0;
                if(0 < pageList.size()){
                    siz = pageList.size();
                }else{
                    siz =1;
                }
                String[] bArrays = new String[siz];
                for(int i=0;i<bArrays.length;i++){
                    bArrays[i]=bArray[ran.nextInt(23)];
                }
                for (int j=0;j<pageList.size();j++) {
                    XML += "<set name='" + pageList.get(j).get("TIAN").toString() + "' value='" + pageList.get(j).get("cunt") + "' color='" + bArrays[j] + "'/>";
                }
            }else{
                String[] bArrays=null;
                for(int i=0;i<24;i++){
                    pd.put("SHI",i+1);
                    pageData=  order_infoService.findHOrder(pd);
                    if(null!=pageData){
                        pageData.put("count",pageData.get("cunt"));
                        pageData.put("SHI",pd.get("SHI"));
                    }else{
                        pageData.put("count","0");
                        pageData.put("SHI",pd.get("SHI"));
                    }
                    pageList.add(pageData);
                    int siz = 0;
                    if(0 < pageList.size()){
                        siz = pageList.size();
                    }else{
                        siz =1;
                    }
                    bArrays = new String[siz];
                    for(int k=0;k<bArrays.length;k++){
                        bArrays[k]=bArray[ran.nextInt(23)];
                    }
                }
                    for (int i=0;i<pageList.size();i++) {
                        XML += "<set name='" + pageList.get(i).get("SHI").toString() + "' value='" + pageList.get(i).get("count") + "' color='" + bArrays[i] + "'/>";
                    }
            }
            XML += "</graph>";
            pd.put("strXML", XML);
            mv.setViewName("system/order_info/order_info_Tdui");
            pd.put("SELECT","H");
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

/*
    @RequestMapping(value = "/xiaoyi4")
    public ModelAndView xiaoyi4() {
        logBefore(logger, "XIAOYI4小时订单效益对比");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("system/order_info/new_view");
        return modelAndView;
    }*/

    /**
     * 列表
     */
    @RequestMapping(value = "/xiaoyi2")
    public ModelAndView xiaoyi2() {
        logBefore(logger, "查询周订单效益对比");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        String[] bArray = {"AFD8F8", "F6BD0F", "8BBA00", "FF8E46", "008E8E", "D64646", "8E468E", "588526", "B3AA00", "008ED6", "9D080D", "A186BE"};
        try {
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
            pd1 = order_infoService.findListDate(pd1);
            pd2 = order_infoService.findListDate(pd2);
            pd3 = order_infoService.findListDate(pd3);
            pd4 = order_infoService.findListDate(pd4);
            if (pd1 == null) {
                pd1 = new PageData();
                pd1.put("MONEY", "0");
            }
            if (pd2 == null) {
                pd2 = new PageData();
                pd2.put("MONEY", "0");
            }
            if (pd3 == null) {
                pd3 = new PageData();
                pd3.put("MONEY", "0");
            }
            if (pd4 == null) {
                pd4 = new PageData();
                pd4.put("MONEY", "0");
            }
            String XML = "<graph caption='周对比表' xAxisName='日期' yAxisName='值' decimalPrecision='0' formatNumberScale='0'>";
            XML += "<set name='" + monday8 + "——" + monday7 + "' value='" + pd4.get("MONEY") + "' color='" + bArray[0] + "'/>";
            XML += "<set name='" + monday6 + "——" + monday5 + "' value='" + pd3.get("MONEY") + "' color='" + bArray[1] + "'/>";
            XML += "<set name='" + monday4 + "——" + monday3 + "' value='" + pd1.get("MONEY") + "' color='" + bArray[2] + "'/>";
            XML += "<set name='" + monday + "——" + monday1 + "' value='" + pd2.get("MONEY") + "' color='" + bArray[3] + "'/>";
            XML += "</graph>";
            pd.put("strXML", XML);
            mv.setViewName("system/order_info/order_info_Zdui");
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }


    /**
     * 修改
     */
    @RequestMapping(value = "/edit")
    public ModelAndView edit() throws Exception {
        logBefore(logger, "修改商品");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
            return null;
        } //校验权限
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        order_infoService.editStatus(pd);
        mv.addObject("msg", "success");
        mv.setViewName("save_result");
        return mv;
    }

    /**
     * 去修改页面
     */
    @RequestMapping(value = "/goEdit")
    public ModelAndView goEdit() {
        logBefore(logger, "去修改商品页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            pd = order_infoService.findById(pd);          //根据ID读取
            mv.setViewName("system/order_info/order_info_edit");
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
    @RequestMapping(value = "/deleteAll")
    @ResponseBody
    public Object deleteAll() {
        logBefore(logger, "批量删除订单");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
            return null;
        } //校验权限
        PageData pd = new PageData();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            pd = this.getPageData();
            List<PageData> pdList = new ArrayList<PageData>();
            String DATA_IDS = pd.getString("DATA_IDS");
            if (null != DATA_IDS && !"".equals(DATA_IDS)) {
                String ArrayDATA_IDS[] = DATA_IDS.split(",");
                for(int i=0;0<ArrayDATA_IDS.length;i++){
                    pd.put("ORDER_INFO_ID",ArrayDATA_IDS[i]);
                    order_infoService.updateSTATUS(pd);
                }
                pd.put("msg", "ok");
            } else {
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
    @RequestMapping(value = "/delete")
    public void delete(PrintWriter out) {
        logBefore(logger, "删除订单");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
            return;
        } //校验权限
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            order_infoService.updateSTATUS(pd);
            out.write("success");
            out.close();
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }

    }

    /**
     * 删除
     */
    @RequestMapping(value = "/cedit")
    public void cedit(PrintWriter out) {
        logBefore(logger, "删除订单");
        //if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return;} //校验权限
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            System.out.println("-------------------->" + pd.getString("ORDER_INFO_ID"));
            order_infoService.editCtatusAndEndDate(pd);
            out.write("success");
            out.close();
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }

    }

    /**
     * 列表
     */
    @RequestMapping(value = "/list")
    public ModelAndView list(Page page) {
        logBefore(logger, "查询订单列表");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
            return null;
        } //校验权限
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        User u = Jurisdiction.getCurrentUserID();//获取当前的USER_ID
        try {
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
                lastLoginStart = lastLoginStart + " 00:00:00";
                pd.put("lastLoginStart", lastLoginStart);
            }
            if (lastLoginEnd != null && !"".equals(lastLoginEnd)) {
                lastLoginEnd = lastLoginEnd + " 00:00:00";
                pd.put("lastLoginEnd", lastLoginEnd);
            }
            //支付方式查询
            String PAY_METHOD = pd.getString("PAY_METHOD");
            if (null != PAY_METHOD && !"".equals(PAY_METHOD)) {
                PAY_METHOD = PAY_METHOD.trim();
                pd.put("PAY_METHOD", PAY_METHOD);
            }
            //状态查询
            String STATUS = pd.getString("STATUS");
            if (null != STATUS && !"".equals(STATUS)) {
                STATUS = STATUS.trim();
                pd.put("STATUS", STATUS);
            }
            //客服人员信息查询
            String EXCLU_ID = pd.getString("EXCLU_ID");
            if (null != EXCLU_ID && !"".equals(EXCLU_ID)) {
                EXCLU_ID = EXCLU_ID.trim();
                pd.put("EXCLU_ID", EXCLU_ID);
            }
            double sum = 0;
            PageData pd1 = new PageData();
            pd1 = order_infoService.findMoney(pd);//价格
            List<PageData> ECList = excluService.findList(pd);//获取全部客服人员信息
            if (pd1 != null) {
                sum = Double.valueOf(pd1.get("MONEY").toString());
            }
            pd.put("sum", sum);
            pd.put("PID", "0");
            List<PageData> list_a = sys_cityService.listAll(pd);
            page.setPd(pd);
            List<PageData> varList = order_infoService.findOrderlistPage(page);          //列出Order_Info列表
            mv.setViewName("system/order_info/order_info_list");
            mv.addObject("varList", varList);
            mv.addObject("list_a", list_a);
            mv.addObject("eclist", ECList);
            mv.addObject("pd", pd);
            pd.put("GUANLI", u.getUSERNAME());
            mv.addObject(Const.SESSION_QX, this.getHC());          //按钮权限
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }


    /*
     * 导出到excel
     * @return
     */
    @RequestMapping(value = "/excel")
    public ModelAndView exportExcel() {
        logBefore(logger, "导出Order_Info到excel");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
            return null;
        }
        ModelAndView mv = new ModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            Map<String, Object> dataMap = new HashMap<String, Object>();
            List<String> titles = new ArrayList<String>();
            titles.add("订单号");          //1
            titles.add("用户名");          //2
            titles.add("下单时间");          //3
            titles.add("收货人");          //4
            titles.add("收货人电话");          //5
            titles.add("收货地址");          //6
            titles.add("支付金额");          //7
            titles.add("支付方式");          //8
            titles.add("支付时间");          //9
            titles.add("订单完成时间");          //10
            titles.add("负责人");          //11
            titles.add("状态");          //12
            dataMap.put("titles", titles);
            List<PageData> varOList = order_infoService.findList(pd);
            List<PageData> varList = new ArrayList<PageData>();
            for (int i = 0; i < varOList.size(); i++) {
                PageData vpd = new PageData();
                vpd.put("var1", varOList.get(i).getString("ORDER_NUMBER"));          //1
                vpd.put("var2", varOList.get(i).getString("USERNAME"));//2
                vpd.put("var3", varOList.get(i).getString("ORDER_DATE"));          //3
                vpd.put("var4", varOList.get(i).getString("NAME"));          //4
                vpd.put("var5", varOList.get(i).getString("PHONE"));          //5
                vpd.put("var6", varOList.get(i).getString("ADDRESS"));          //6
                vpd.put("var7", varOList.get(i).getString("PAY_MONEY"));          //7
                String method = "";
                if ("wx".equals(varOList.get(i).getString("PAY_METHOD"))) {
                    method = "微信支付";
                }
                if ("xx".equals(varOList.get(i).getString("PAY_METHOD"))) {
                    method = "线下支付";
                }
                if ("alipay".equals(varOList.get(i).getString("PAY_METHOD"))) {
                    method = "支付宝支付";
                }
                vpd.put("var8", method);          //8
                vpd.put("var9", varOList.get(i).getString("PAY_DATE"));          //9
                vpd.put("var10", varOList.get(i).getString("END_DATE"));          //10
                String status = "";
                if ("0".equals(varOList.get(i).getString("STATUS"))) {
                    status = "未处理";
                }
                if ("1".equals(varOList.get(i).getString("STATUS"))) {
                    status = "已处理";
                }
                vpd.put("var11", varOList.get(i).getString("EXCLUNAME"));          //11
                vpd.put("var12", status);          //12
                varList.add(vpd);
            }
            dataMap.put("varList", varList);
            ObjectExcelView erv = new ObjectExcelView();
            mv = new ModelAndView(erv, dataMap);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }


    /**
     * 用户的订单列表
     */
    @RequestMapping(value = "/findUserOrder")
    public ModelAndView findUserOrder(Page page) throws Exception {
        logBefore(logger, "查询用户订单列表");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            page.setPd(pd);
            List<PageData> varList = order_infoService.findUserOrderList(page);          //列出Order_Info列表
            mv.setViewName("system/order_user/order_user_list");
            mv.addObject("varList", varList);
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    /* ===============================权限================================== */
    public Map<String, String> getHC() {
        Subject currentUser = SecurityUtils.getSubject();  //shiro管理的session
        Session session = currentUser.getSession();
        Map<String, String> map = new HashMap();
        if (session.getAttribute(Const.SESSION_QX) != null) {
            map = (Map<String, String>) session.getAttribute(Const.SESSION_QX);
        }
        return map;
    }
    /* ===============================权限================================== */

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(format, true));
    }
}
