package com.fh.controller.system.order_info;

import com.fh.controller.base.BaseController;
import com.fh.service.system.exclu.ExcluService;
import com.fh.service.system.order_info.Order_infoService;
import com.fh.util.MapDistance;
import com.fh.util.PageData;
import com.fh.util.XiaoYiDateT;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping(value = "demo")
public class NewViewController extends BaseController {
    @Resource(name = "order_infoService")
    private Order_infoService order_infoService;
    @Resource(name = "excluService")
    private ExcluService excluService;

    /**
     * @return
     * @throws Exception
     */
    @RequestMapping("/demo.do")
    public ModelAndView xiaoyi() throws Exception {
        logBefore(logger, "demo查询日订单效益对比");
        Calendar calendar = Calendar.getInstance();
        ObjectMapper mapper = new ObjectMapper();
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        List<PageData> pageList = new ArrayList<PageData>();
        try {
            List<String> dateList = XiaoYiDateT.getDateList(new Date());
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        String a = mapper.writeValueAsString(pageList).replaceAll("\"", "'");
        mv.setViewName("system/order_info/new_view");
        mv.addObject("list", a);
        mv.addObject("message", pageList.get(0).get("TIAN") + "到" + pageList.get(pageList.size() - 1).get("TIAN") + "每天订单总额");
        return mv;
    }


    /**
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/sdemo.do")
    public String sxiaoyi() throws Exception {
        logBefore(logger, "demo查询日订单效益对比");
        Calendar calendar = Calendar.getInstance();
        ObjectMapper mapper = new ObjectMapper();
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        List<PageData> pageList = new ArrayList<PageData>();
        try {
            List<String> dateList = XiaoYiDateT.getDateList(new Date());
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("list", pageList);
        map.put("message", pageList.get(pageList.size() - 1).get("TIAN") + "到" + pageList.get(0).get("TIAN") + "每天订单总额");
        return mapper.writeValueAsString(map);
    }


    /**
     * @return
     * @throws Exception
     */
    @RequestMapping("/demo2.do")
    public ModelAndView xiaoyi2() throws Exception {
        logBefore(logger, "demo查询周订单效益对比");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        ObjectMapper mapper = new ObjectMapper();
        try {
            String monday = MapDistance.getLastWeekSunday2();          //上周周一
            String monday1 = MapDistance.getLastWeekSunday();          //上周周日
            String monday3 = MapDistance.getLastWeekSunday3();          //上上周周日
            String monday4 = MapDistance.getLastWeekSunday4();          //上上周周一
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
            List<PageData> plis = new ArrayList<PageData>();
            pd1.put("SHIJIAN", monday3 + "~~" + monday4);
            plis.add(pd1);
            pd2.put("SHIJIAN", monday + "~~" + monday1);
            plis.add(pd2);
            pd3.put("SHIJIAN", monday5 + "~~" + monday6);
            plis.add(pd3);
            pd4.put("SHIJIAN", monday7 + "~~" + monday8);
            plis.add(pd4);
            mv.setViewName("system/order_info/order_new_list_view");
            mv.addObject("list", mapper.writeValueAsString(plis).replaceAll("\"", "'"));
            mv.addObject("message", "上四周的单量总额查询");
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    /**
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/ademo2.do")
    public String axiaoyi2() throws Exception {
        logBefore(logger, "demo查询周订单效益对比");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        ObjectMapper mapper = new ObjectMapper();
        List<PageData> plis = new ArrayList<PageData>();
        try {
            String monday = MapDistance.getLastWeekSunday2();          //上周周一
            String monday1 = MapDistance.getLastWeekSunday();          //上周周日
            String monday3 = MapDistance.getLastWeekSunday3();          //上上周周日
            String monday4 = MapDistance.getLastWeekSunday4();          //上上周周一
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
            pd1.put("SHIJIAN", monday3 + "~~" + monday4);
            plis.add(pd1);
            pd2.put("SHIJIAN", monday + "~~" + monday1);
            plis.add(pd2);
            pd3.put("SHIJIAN", monday5 + "~~" + monday6);
            plis.add(pd3);
            pd4.put("SHIJIAN", monday7 + "~~" + monday8);
            plis.add(pd4);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("list", plis);
        map.put("message", "上四周的单量总额查询");
        return mapper.writeValueAsString(map);
    }

    /**
     * @param pro
     * @param date
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/demo1.do")
    public String xiaoyi1(String pro, String date) throws Exception {
        logBefore(logger, "demo1查询日订单效益对比");
        ObjectMapper mapper = new ObjectMapper();
        System.out.println("------------------->" + pro);
        System.out.println("------------------->" + date);
        if ("t".equals(pro)) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("message", date + "日的每小时单量查询");
            map.put("data", xiaoyi3(date));
            return mapper.writeValueAsString(map);
        }
        Calendar calendar = Calendar.getInstance();


        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        List<PageData> pageList = new ArrayList<PageData>();
        try {
            List<String> dateList = XiaoYiDateT.getDateList(new Date());
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mapper.writeValueAsString(pageList);
    }

    /**
     * @param date
     * @return
     * @throws Exception
     */
    public Object xiaoyi3(String date) throws Exception {
        logBefore(logger, "查询小时订单效益对比");
        PageData pd = new PageData();
        List<PageData> pageList = null;
        try {
            pageList = new ArrayList<PageData>();
            PageData pageData = null;
            pd = this.getPageData();
            pd.put("TIAN", date);
            for (int i = 0; i < 24; i++) {
                pd.put("SHI", i + 1);
                pageData = order_infoService.findHOrder(pd);
                if (null != pageData) {
                    pageData.put("count", pageData.get("cunt"));
                    pageData.put("SHI", pd.get("SHI"));
                } else {
                    pageData.put("count", "0");
                    pageData.put("SHI", pd.get("SHI"));
                }
                pageList.add(pageData);
            }
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        System.out.println(pageList.toString());
        return pageList;
    }

    /**
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/sddemo.do")
    public String sddemo() throws Exception {
        logBefore(logger, "demo查询阶段订单效益对比");
        ObjectMapper mapper = new ObjectMapper();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");

        PageData pd = new PageData();
        List<PageData> pageList = new ArrayList<PageData>();
        try {
            PageData pageData = null;
            pd = this.getPageData();
            pageList = order_infoService.findOrderC(pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        Map<String, Object> map = new HashMap<String, Object>();
        String k = pd.getString("BEGIN");
        String e = pd.getString("END");
        map.put("message", k + "到" + e + "之间每天的单量金额查询");
        map.put("list", pageList);
        return mapper.writeValueAsString(map);
    }


    /**
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/zdemo1.do")
    public String zdemo1(String date) throws Exception {
        logBefore(logger, "demo查询阶段订单效益对比");
        String[] a = date.split("~~");
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = new HashMap<String, Object>();
        PageData pd = new PageData();
        System.out.println("BEGIN------------------------------------------------"+a[1]);
        System.out.println("END-----------------------------------------------"+a[0]);
        pd.put("BEGIN", a[1]);
        pd.put("END", a[0]);
        // 时间表示格式可以改变，yyyyMMdd需要写例如20160523这种形式的时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("BEGIN-------------------------" + pd.getString("BEGIN"));
        String str = pd.getString("BEGIN");
        // 将字符串的日期转为Date类型，ParsePosition(0)表示从第一个字符开始解析
        Date date1 = sdf.parse(str, new ParsePosition(0));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date1);
        // add方法中的第二个参数n中，正数表示该日期后n天，负数表示该日期的前n天
        List<PageData> pageList = new ArrayList<PageData>();
        PageData pageData = new PageData();
        try {
           /* pageList = order_infoService.findOrderC(pd);*/
        for (int i = 0; i < 7; i++) {
            if (i == 0) {
                calendar.add(Calendar.DATE, +0);
            }else{
                calendar.add(Calendar.DATE, +1);
            }
            Date date2 = calendar.getTime();
            String out = sdf.format(date2);
            System.out.println("------------------" + out);
            pageData.put("TIAN", out);
            PageData pad = null;
            pad = order_infoService.findTDuiBi(pageData);
            if (null == pad) {
                PageData pddd = new PageData();
                pddd.put("TIAN", out);
                pddd.put("cunt", "0");
                pageList.add(pddd);
            }else{
                PageData pddd = new PageData();
                pddd.put("TIAN", out);
                pddd.put("cunt", pad.get("MONEY").toString());
                pageList.add(pddd);
            }

        }
        for(PageData pp : pageList){
            System.err.println("TIAN"+pp.getString("TIAN"));
        }
        System.out.println("--------------------------" + pageList.size());
        String k = pd.getString("BEGIN");
        String e = pd.getString("END");
        map.put("message", k + "到" + e + "之间每天的单量金额查询");
        map.put("list", pageList);
        map.put("flag", "z");
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mapper.writeValueAsString(map);
    }
}
