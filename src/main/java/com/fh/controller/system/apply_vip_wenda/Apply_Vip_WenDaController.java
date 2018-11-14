package com.fh.controller.system.apply_vip_wenda;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.system.apply_vip_wenda.Apply_Vip_WenService;
import com.fh.service.system.appuser.AppuserService;
import com.fh.service.system.p_quanxian.P_QuanXuanService;
import com.fh.service.system.product.ProductService;
import com.fh.util.*;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping(value = "apply_vip_wenda", produces = "text/html;charset=UTF-8")
public class Apply_Vip_WenDaController extends BaseController {
    @Resource(name="apply_vip_WenService")
    private Apply_Vip_WenService apply_vip_wenService;
    @Resource(name="p_QuanXuanService")
    private P_QuanXuanService p_quanXuanService;
    @Resource(name="appuserService")
    private AppuserService appuserService;
    @Resource(name="productService")
    private ProductService productService;
    /**
     * 删除
     */
    @RequestMapping(value = "/delete")
    public void delete(PrintWriter out) {
        logBefore(logger, "删除用户申请信息");
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            apply_vip_wenService.delete(pd);
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
        logBefore(logger, "查询用户申请VIP列表");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            String KEYWORD = pd.getString("KEYWORD");
            if (null != KEYWORD && !"".equals(KEYWORD)) {
                KEYWORD = KEYWORD.trim();
                pd.put("KEYWORD", KEYWORD);
            }
            String VIP = pd.getString("VIP");
            if (null != VIP && !"".equals(VIP)) {
                VIP = VIP.trim();
                pd.put("VIP", VIP);
            }
            page.setPd(pd);
            List<PageData> varList = apply_vip_wenService.findUserList(page);          //列出Order_Info列表
            mv.setViewName("system/apply_vip/apply_vipwenda_list");
            mv.addObject("varList", varList);
            mv.addObject("page", page);
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }


    /**
     * 去修改页面
     */
    @RequestMapping(value = "/goEdit")
    public ModelAndView goEdit() {
        logBefore(logger, "去修改用户申请VIP页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            pd = apply_vip_wenService.findById(pd);          //根据ID读取
            mv.setViewName("system/apply_vip/apply_vipwenda_edit");
            mv.addObject("msg", "edit");
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
        logBefore(logger, "修改用户申请VIP");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        appuserService.editVip(pd);
        //发送短信
        PageData pd1 = apply_vip_wenService.findById(pd);
        if (pd.getString("NEIRONG") != null) {
            SmsBao smsBao = new SmsBao();
            String result = smsBao.sendSMS(pd1.getString("PHONE"), pd.getString("NEIRONG"));
        }
        System.out.println("------------------------"+pd.get("PRODUCT_ID").toString());
        System.out.println("------------------------"+pd.get("USER_ID").toString());
        PageData pa = new PageData();
        pa.put("P_QUANXIANID", this.get32UUID());
        pa.put("USER_ID", pd.getString("USER_ID"));
        pa.put("PRODUCT_ID", pd.getString("PRODUCT_ID"));
        p_quanXuanService.save(pa);
        mv.addObject("msg", "success");
        mv.setViewName("save_result");
        return mv;
    }


    /*
     * 导出到excel
     * @return
     */
    @RequestMapping(value = "/excel")
    public ModelAndView exportExcel() {
        logBefore(logger, "导出Apply_vip_Wen到excel");
        ModelAndView mv = new ModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            Map<String, Object> dataMap = new HashMap<String, Object>();
            List<String> titles = new ArrayList<String>();
            titles.add("申请人");          //1
            titles.add("联系方式");          //2
            titles.add("地址");//3s
            titles.add("申请商品");          //4
            titles.add("是否为VIP");          //5
            dataMap.put("titles", titles);
            List<PageData> varOList= apply_vip_wenService.findList(pd);
            List<PageData> varList = new ArrayList<PageData>();
            for (int i = 0; i < varOList.size(); i++) {
                PageData vpd = new PageData();
                vpd.put("var1", varOList.get(i).getString("NAME"));          //1
                vpd.put("var2", varOList.get(i).getString("PHONE"));//2
                vpd.put("var3", varOList.get(i).getString("ADDRESS"));          //3
                vpd.put("var4", varOList.get(i).getString("PRODUCT_NAME"));          //4
                String method = "";
                if ("1".equals(varOList.get(i).getString("VIP"))||"3".equals(varOList.get(i).getString("VIP"))) {
                    method = "是";
                }else{
                    method = "否";
                }
                vpd.put("var5", method);          //5

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
