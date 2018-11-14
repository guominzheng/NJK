package com.fh.controller.system.appuser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.fh.service.system.cart.CartService;
import com.fh.service.system.p_quanxian.P_QuanXuanService;
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
import com.fh.entity.system.User;
import com.fh.service.system.apply_vip.Apply_vipService;
import com.fh.service.system.appuser.AppuserService;
import com.fh.service.system.appuserInfo.AppUserInfoService;
import com.fh.service.system.exclu.ExcluService;
import com.fh.service.system.order_info.Order_infoService;
import com.fh.service.system.sys_city.Sys_cityService;

@Controller
@RequestMapping(value = "appuser")
public class AppuserController extends BaseController {

    String menuUrl = "appuser/list.do"; //菜单地址(权限用)
    @Resource(name = "appuserService")
    private AppuserService appuserService;
    @Resource(name = "apply_vipService")
    private Apply_vipService apply_vipService;
    @Resource(name = "appUserInfoService")
    private AppUserInfoService appUserInfoService;
    @Resource(name = "excluService")
    private ExcluService excluService;
    @Resource(name = "sys_cityService")
    private Sys_cityService sys_cityService;
    @Resource(name = "order_infoService")
    private Order_infoService order_infoService;
    @Resource(name="p_QuanXuanService")
    private P_QuanXuanService p_quanXuanService;
    @Resource(name="cartService")
    private CartService cartService;


    /**
     * 列表
     */
    @RequestMapping(value = "/show")
    public ModelAndView show(Page page) {
        logBefore(logger, "查询订单列表");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
            return null;
        } //校验权限
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            page.setPd(pd);
            List<PageData> varList = order_infoService.list(page);          //列出Order_Info列表
            mv.setViewName("system/appuser/order_info_list");
            mv.addObject("varList", varList);
            mv.addObject("pd", pd);
            mv.addObject(Const.SESSION_QX, this.getHC());          //按钮权限
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
        logBefore(logger, "修改用户VIP信息");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
            return null;
        } //校验权限
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        if("0".equals(pd.get("VIP").toString())){
                p_quanXuanService.deleteUser(pd);
                cartService.deleteAll(pd);
        }
        appuserService.editEXCLU_ID(pd);
        mv.addObject("msg", "success");
        mv.setViewName("save_result");
        return mv;
    }

    /**
     * 去修改页面
     */
    @RequestMapping(value = "/goEdit")
    public ModelAndView goEdit() {
        logBefore(logger, "去修改用户的VIP登记");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            User u = Jurisdiction.getCurrentUserID();//获取当前的USER_ID
            pd.put("PID", "0");
            List<PageData> provinces = sys_cityService.listAll(pd);
            pd = appuserService.findById(pd);//根据ID读取
            List<PageData> evarList = excluService.findList(pd);          //列出Pro_Info列表
            mv.addObject("varList1", evarList);
            mv.addObject("provinces", provinces);
            mv.setViewName("system/appuser/appuser_edit");
            mv.addObject("msg", "edit");
            pd.put("GUANLI", u.getUSERNAME());
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }


    /**
     * 修改
     */
    @RequestMapping(value = "/SendVIP1")
    public ModelAndView SendVIP1() throws Exception {
        logBefore(logger, "给用户发送短信");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        pd.put("VIP", "1");
        List<PageData> list = apply_vipService.findList(pd);
        SmsBao smsBao = new SmsBao();
        for (int i = 0; i < list.size(); i++) {
            String result = smsBao.sendSMS(list.get(i).getString("PHONE"), pd.getString("content"));
        }
        mv.addObject("msg", "success");
        mv.setViewName("save_result");
        return mv;
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/Send1")
    public ModelAndView Send1() throws Exception {
        logBefore(logger, "给用户发送短信");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        List<PageData> list = appuserService.findAppUserVip(pd);
        SmsBao smsBao = new SmsBao();
        for (int i = 0; i < list.size(); i++) {
            String result = smsBao.sendSMS(list.get(i).getString("USERNAME"), pd.getString("content"));
        }
        mv.addObject("msg", "success");
        mv.setViewName("save_result");
        return mv;
    }

    /**
     * 去修改页面
     */
    @RequestMapping(value = "/Send")
    public ModelAndView goSend() {
        logBefore(logger, "给全部用户发送短信");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            mv.setViewName("system/appuser/appuser_Send");
            mv.addObject("msg", "Send1");
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    /**
     * 去修改页面
     */
    @RequestMapping(value = "/SendVIP")
    public ModelAndView SendVIP() {
        logBefore(logger, "给VIP用户发送短信");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            mv.setViewName("system/appuser/appuser_Send");
            mv.addObject("msg", "SendVIP1");
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    /**
     * 列表
     */
    @RequestMapping(value = "/list")
    public ModelAndView list(Page page) {
        logBefore(logger, "查询用户列表");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
            return null;
        }
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            //页面检索
            String KEYWORD = pd.getString("KEYWORD");
            if (null != KEYWORD && !"".equals(KEYWORD)) {
                KEYWORD = KEYWORD.trim();
                pd.put("KEYWORD", KEYWORD);
            }
            String VIP = pd.getString("VIP");
            if (null != VIP && !"".equals(VIP)) {
                VIP = VIP.trim();
                pd.put("S_STATUS", VIP);
            }
            page.setPd(pd);
            pd.put("PID", "0");
            List<PageData> provinces = sys_cityService.listAll(pd);
            List<PageData> dishi = sys_cityService.listAll1(pd);
            List<PageData> dishi1 = sys_cityService.listAll2(pd);
            List<PageData> varList = appuserService.list(page);          //列出Pro_Info列表
            List<PageData> evarList = excluService.findList(pd);          //列出Pro_Info列表
            //List<PageData>	list=pro_infoService.findByShopId(pd);//产品
            //判断账号的归属地是否为空，如果不存在则进行数据更新
            for (PageData pageData : varList) {
                String address = pageData.getString("PHONEADDRESS");//获取每个对象的手机号归属地
                if (null == address) {
                    String str = GetMobileMessage.getMobilePlace(pageData.getString("USERNAME"));
                    if (null != str && !"".equals(str)) {
                        pageData.put("PHONEADDRESS", str);
                        pd.put("USER_ID",pageData.getString("USER_ID"));
                        pd.put("PHONEADDRESS",str);
                        appuserService.addPhoneAddress(pd);
                    }
                }
            }
            mv.setViewName("system/appuser/appuser_list");
            mv.addObject("varList", varList);
            mv.addObject("varList1", evarList);
            mv.addObject("provinces", provinces);
            mv.addObject("dizhi", dishi);
            mv.addObject("dizhi1", dishi1);
            mv.addObject("pd", pd);
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
        //if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
        ModelAndView mv = new ModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            Map<String, Object> dataMap = new HashMap<String, Object>();
            List<String> titles = new ArrayList<String>();
            titles.add("用户名");          //1
            titles.add("客服");          //2
            titles.add("隶属");          //3
            titles.add("名称");          //4
            titles.add("是否为VIP");          //5
            titles.add("客户姓名");          //6
            titles.add("电话");          //7
            titles.add("当地作物");          //8
            dataMap.put("titles", titles);
            List<PageData> varOList = appuserService.findList(pd);
            List<PageData> varList = new ArrayList<PageData>();
            for (int i = 0; i < varOList.size(); i++) {
                PageData vpd = new PageData();
                vpd.put("var1", varOList.get(i).getString("USERNAME"));          //1
                vpd.put("var2", varOList.get(i).getString("ENAME"));//2
                vpd.put("var3", varOList.get(i).getString("PHONEADDRESS"));//3
                vpd.put("var4", varOList.get(i).getString("NAME"));          //4
                if (varOList.get(i).getString("VIP").equals("0")) {
                    vpd.put("var5", "不是VIP");          //5
                } else if (varOList.get(i).getString("VIP").equals("1")) {
                    vpd.put("var5", "VIP");          //4
                } else if (varOList.get(i).getString("VIP").equals("2")) {
                    vpd.put("var5", "审核中");          //4
                } else if (varOList.get(i).getString("VIP").equals("3")) {
                    vpd.put("var5", "超级超级VIP");
                } else if (varOList.get(i).getString("VIP").equals("4")) {
                    vpd.put("var5", "拒绝");
                } else if (varOList.get(i).getString("VIP").equals("5")) {
                    vpd.put("var5", "超级VIP");
                }
                vpd.put("var6", varOList.get(i).getString("CUSTOMER_NAME"));          //6
                vpd.put("var7", varOList.get(i).getString("PHONE"));          //7
                vpd.put("var8", varOList.get(i).getString("CROP"));          //8
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
