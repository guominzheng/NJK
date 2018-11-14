package com.fh.controller.system.apply_vip;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;

import com.fh.controller.system.p_quanxian.P_QuanXianController;
import com.fh.service.system.collection_pro.Collection_proService;
import com.fh.service.system.p_quanxian.P_QuanXuanService;
import com.fh.service.system.product.ProductService;
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
import com.fh.service.system.apply_vip.Apply_vipService;
import com.fh.service.system.appuser.AppuserService;
import com.fh.service.system.appuserInfo.AppUserInfoService;
import com.fh.service.system.mentou_img.MenTouImfService;
import com.sun.xml.internal.ws.api.ha.HaInfo;

@Controller
@RequestMapping(value = "pply_vip", produces = "text/html;charset=UTF-8")
public class Apply_vipController extends BaseController {

    String menuUrl = "order_info/list.do"; //菜单地址(权限用) //菜单地址(权限用)
    @Resource(name = "apply_vipService")
    private Apply_vipService apply_vipService;
    @Resource(name = "appUserInfoService")
    private AppUserInfoService appUserInfoService;
    @Resource(name = "menTouImfService")
    private MenTouImfService menTouImfService;
    @Resource(name = "appuserService")
    private AppuserService appuserService;
    @Resource(name = "productService")
    private ProductService productService;
    @Resource(name = "collection_proService")
    private Collection_proService collection_proService;
    @Resource(name = "p_QuanXuanService")
    private P_QuanXuanService p_quanXuanService;


    @RequestMapping(value = "show")
    public ModelAndView show() {
        logBefore(logger, "显示滚动图片");
        //if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            List<PageData> varList = menTouImfService.findList(pd);          //根据ID读取
            mv.addObject(Const.SESSION_QX, this.getHC());          //按钮权限
            mv.addObject("varList", varList);
            mv.setViewName("system/apply_vip/apply_img_show");
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete")
    public void delete(PrintWriter out) {
        logBefore(logger, "删除用户申请信息");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
            return;
        } //校验权限
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            apply_vipService.delete(pd);
            out.write("success");
            out.close();
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }

    }

    /**
     * 修改
     */
    @RequestMapping(value = "/edit")
    public ModelAndView edit() throws Exception {
        logBefore(logger, "修改用户申请VIP");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
            return null;
        } //校验权限
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        appuserService.editVip(pd);
        //发送短信
        PageData pd1 = apply_vipService.findById(pd);
        if (pd.getString("NEIRONG") != null) {
            SmsBao smsBao = new SmsBao();
            String result = smsBao.sendSMS(pd1.getString("PHONE"), pd.getString("NEIRONG"));
        }
        if (null != pd.get("PRODUCT_ID") && !"0".equals(pd.get("PRODUCT_ID").toString())) {
            List<PageData> ppa = null;
            ppa = p_quanXuanService.findProRoleByVipName(pd);
            if (null == ppa || ppa.size() == 0) {
                System.out.println("");
                PageData pa = new PageData();
                pa.put("P_QUANXIANID", this.get32UUID());
                pa.put("USER_ID", pd.getString("USER_ID"));
                pa.put("PRODUCT_ID", pd.getString("PRODUCT_ID"));
                p_quanXuanService.save(pa);
            }
        }else {
            List<PageData> palis = productService.findList(new PageData());
            if (null != palis && 0 < palis.size()) {
                int i, pindex = palis.size();
                PageData pa =null;
                List<PageData> list = new ArrayList<PageData>();
                for (i = 0; i < pindex; i++) {
                    String PRODUCT=palis.get(i).get("PRODUCT_ID").toString();
                    List<PageData> ppa = null;
                    pd.put("PRODUCT_ID",PRODUCT);
                    ppa = p_quanXuanService.findProRoleByVipName(pd);
                    if (null == ppa || ppa.size() == 0){
                        pa = new PageData();
                        pa.put("P_QUANXIANID", this.get32UUID());
                        pa.put("PRODUCT_ID",PRODUCT);
                        list.add(pa);
                    }
                }
                Map<String,Object> map = new HashMap<String,Object>();
                map.put("pd",pd);
                map.put("list",list);
                p_quanXuanService.batchSave(map);
            }
        }
        mv.addObject("msg", "success");
        mv.setViewName("save_result");
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
            pd = apply_vipService.findById(pd);          //根据ID读取
            List<PageData> varList = productService.findList(pd);
            mv.setViewName("system/apply_vip/apply_vip_edit");
            mv.addObject("msg", "edit");
            mv.addObject("varList", varList);
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
        logBefore(logger, "查询用户申请VIP列表");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
            return null;
        } //校验权限
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
            page.setPd(pd);
            List<PageData> varList = apply_vipService.list(page);          //列出Order_Info列表
            mv.addObject(Const.SESSION_QX, this.getHC());          //按钮权限
            mv.setViewName("system/apply_vip/apply_vip_list");
            mv.addObject("varList", varList);
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }


    public static String se(String name, String phone) throws UnsupportedEncodingException {
        //String name1=new String(name.getBytes("UTF-8"),"utf-8");
        //String name1=new String(name.getBytes("UTF-8"),"utf-8");
        //System.err.println(name1);
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();
        try {
            URL url = new URL("http://web.wasun.cn/asmx/smsservice.aspx?name=13526524092&pwd=6ACD05E026F8945A23A86ACC906D&mobile=" + phone + "&content=" + name + "&sign=农极客&type=pt");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = reader.readLine();
            if (strRead != null) {
                sbf.append(strRead);
                while ((strRead = reader.readLine()) != null) {
                    sbf.append("\n");
                    sbf.append(strRead);
                }
            }
            reader.close();
            result = sbf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
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
