package com.fh.controller.system.exclu_record;

import com.fh.controller.base.BaseController;
import com.fh.service.system.exclu.ExcluService;
import com.fh.service.system.register_record.Register_recordService;
import com.fh.util.DateUtil;
import com.fh.util.MapDistance;
import com.fh.util.PageData;
import com.fh.util.XiaoYiDateT;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
@Controller
@RequestMapping(value="exclu_recordController")
public class Exclu_recordController extends BaseController {

    String menuUrl = "exclu_recordController/list.do"; //菜单地址(权限用)
    @Resource(name="excluService")
    private ExcluService excluService;

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
            /*pd.put("NIAN1", DateUtil.getYear());
            pd.put("YUE",DateUtil.getYue());*/
            pd.put("NIAN1",DateUtil.getYear());
            pd.put("YUE",DateUtil.getOneYue());
            List<PageData> varList = excluService.getYCount(pd);	//列出Order_Info列表
            if(null != varList){
                String XML="<graph caption='本月绩效' xAxisName='姓名' yAxisName='绩效' decimalPrecision='0' formatNumberScale='0'>";
                for(int i=0;i<varList.size();i++){
                    XML+="<set name='"+varList.get(i).getString("NAME")+"' value='"+varList.get(i).get("ZMONEY")+"' color='"+bArray[i]+"'/>";
                }
                XML+="</graph>";
                System.out.println(XML);
                pd.put("strXML", XML);
            }else{
                String XML="<graph caption='本月绩效' xAxisName='姓名' yAxisName='绩效' decimalPrecision='0' formatNumberScale='0'>";
                    XML+="<set name='无数据' value='无数据' color='"+bArray[0]+"'/>";
                XML+="</graph>";
            }
            mv.setViewName("system/exclu/exclu_Tdui");
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
          /*  String monday3=MapDistance.getLastWeekSunday3();	//上上周周日
            String monday4=MapDistance.getLastWeekSunday4();	//上上周周一
            String monday5 = MapDistance.getLastWeekSundaysss();         //end
            String monday6 = MapDistance.getLastWeekSundaysss1();       //start
            String monday7 = MapDistance.getLastWeekSunday41();         //end
            String monday8 = MapDistance.getLastWeekSunday411();*/
            System.out.println(monday);
            System.out.println(monday1);
           /* System.out.println(monday3);
            System.out.println(monday4);*/
            PageData pd2 = new PageData();
   /*         PageData pd2 = new PageData();
            PageData pd3 = new PageData();
            PageData pd4 = new PageData();*/
           /* pd1.put("KAISHI", monday4);
            pd1.put("END", monday3);*/
            pd2.put("KAISHI", monday);
            pd2.put("END", monday1);
           /* pd3.put("KAISHI", monday6);
            pd3.put("END", monday5);
            pd4.put("KAISHI", monday8);
            pd4.put("END", monday7);*/
            List<PageData> pdList=excluService.getZCount(pd2);
            if(null != pdList || pdList.size()!=0){
                String XML="<graph caption='本周绩效' xAxisName='姓名' yAxisName='值' decimalPrecision='0' formatNumberScale='0'>";
                for(int i=0;i<pdList.size();i++){
                    XML+="<set name='"+pdList.get(i).getString("NAME")+"' value='"+pdList.get(i).get("ZMONEY")+"' color='"+bArray[i]+"'/>";
                }
                XML+="</graph>";
                pd.put("strXML", XML);
            }
            mv.setViewName("system/exclu/exclu_Tdui");
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
    public ModelAndView xiaoyi1() throws Exception{
        logBefore(logger, "查询日订单效益对比");
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
            PageData pageData = new PageData();
            Calendar cal1   =   Calendar.getInstance();
            cal1.add(Calendar.DATE,   -1);
            String ZT = new SimpleDateFormat( "yyyy-MM-dd").format(cal1.getTime());
            pageData.put("TIAN",ZT);
            List<PageData> pageList = excluService.getTCount(pageData);
            String XML = "<graph caption='' xAxisName='姓名' yAxisName='绩效' decimalPrecision='0' formatNumberScale='0'>";
            int i,size = pageList.size();
            for (i=0;i<size;i++) {
                XML += "<set name='" + pageList.get(i).getString("NAME") + "' value='" + pageList.get(i).get("ZMONEY") + "' color='" + bArray[i] + "'/>";
            }
            XML+="</graph>";
            pd.put("strXML", XML);
            mv.setViewName("system/exclu/exclu_Tdui");
            mv.addObject("pd", pd);
        } catch(Exception e){
            logger.error(e.toString(), e);
        }
        return mv;
    }
}
