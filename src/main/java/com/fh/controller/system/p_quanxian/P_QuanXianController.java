package com.fh.controller.system.p_quanxian;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;

import com.fh.service.system.appuser.AppuserService;
import com.fh.service.system.cart.CartService;
import com.fh.util.AppUtil;
import com.fh.util.Jurisdiction;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
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
import com.fh.service.system.p_quanxian.P_QuanXuanService;
import com.fh.service.system.product.ProductService;
import com.fh.util.Const;
import com.fh.util.PageData;

@Controller
@RequestMapping(value = "p_quanxian")
public class P_QuanXianController extends BaseController {
    @Resource(name = "p_QuanXuanService")
    private P_QuanXuanService p_QuanXuanService;
    @Resource(name = "productService")
    private ProductService productService;
    @Resource(name = "appuserService")
    private AppuserService appuserService;
    @Resource(name = "cartService")
    private CartService cartService;


    /**
     * 新增
     */
    @RequestMapping(value = "/save")
    public ModelAndView save() throws Exception {
        logBefore(logger, "新增商品");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        PageData pageData = appuserService.findById(pd);
        pd.put("USER_ID", pageData.get("USER_ID").toString());
        List<PageData> paa = null;
        if("110".equals(pd.getString("PRODUCT_ID"))){
            List<PageData> proList = productService.findList(pd);
            System.out.println(proList.size());
            PageData pro_dto = null;
            for(int i =0;i<proList.size();i++){
                pro_dto = new PageData();
                pro_dto.put("USER_ID",pd.get("USER_ID").toString());
                pro_dto.put("PRODUCT_ID",proList.get(i).get("PRODUCT_ID").toString());
                paa = p_QuanXuanService.findProRoleByVipName(pro_dto);
                if (null == paa || paa.size() == 0) {
                    pro_dto.put("P_QUANXIANID", this.get32UUID());
                    p_QuanXuanService.save(pro_dto);
                }
            }
        }else{
            paa = p_QuanXuanService.findProRoleByVipName(pd);
            if (null == paa || paa.size() == 0) {
                pd.put("P_QUANXIANID", this.get32UUID());          //主键
                p_QuanXuanService.save(pd);
            }
        }
        if (!pageData.getString("VIP").equals("1") || !pageData.getString("VIP").equals("3")) {
            PageData pa = new PageData();
            pa.put("USER_ID", pd.get("USER_ID").toString());
            pa.put("VIP", "1");
            appuserService.editVip(pa);
        }
        mv.addObject("msg", "success");
        mv.setViewName("save_result");
        return mv;
    }


    /**
     * 新增
     */
    @RequestMapping(value = "/saveup")
    public ModelAndView saveup() throws Exception {
        logBefore(logger, "新增用户商品权限");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        PageData pageData = appuserService.findByUId(pd);
        pd.put("USER_ID", pageData.get("USER_ID").toString());
        List<PageData> paa = null;
        paa = p_QuanXuanService.findProRoleByVipName(pd);
        if (null == paa || paa.size() == 0) {
            pd.put("P_QUANXIANID", this.get32UUID());          //主键
            p_QuanXuanService.save(pd);
            if (!pageData.getString("VIP").equals("1") || !pageData.getString("VIP").equals("3")) {
                PageData pa = new PageData();
                pa.put("USER_ID", pd.get("USER_ID").toString());
                pa.put("VIP", "1");
                appuserService.editVip(pa);
            }
        }
        mv.addObject("msg", "success");
        mv.setViewName("save_result");
        return mv;
    }


    /**
     * 删除
     */
    @RequestMapping(value = "/delete")
    public void delete(PrintWriter out) {
        logBefore(logger, "删除商品");
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            PageData pageData = p_QuanXuanService.findById1(pd);
            if (null != pageData) {
                cartService.delUserCart(pageData);
                p_QuanXuanService.delete(pageData);

                if (null == p_QuanXuanService.findList(pageData) || 0 == p_QuanXuanService.findList(pageData).size()) {
                    pageData.put("VIP", "0");
                    appuserService.editVip(pageData);
                }
            }
            out.write("success");
            out.close();
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }

    }

    /**
     * 去新增页面
     */
    @RequestMapping(value = "/goAdd")
    public ModelAndView goAdd() {
        logBefore(logger, "去新增商品权限页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        try {
            List<PageData> varList = productService.findList(pd);
            pd = this.getPageData();
            mv.setViewName("system/p_quanxian/p_quanxian_edit");
            mv.addObject("msg", "save");
            mv.addObject("varList", varList);
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    /**
     * 去新增页面
     */
    @RequestMapping(value = "/goAddup")
    public ModelAndView goAddup() {
        logBefore(logger, "去新增商品权限页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        try {
            List<PageData> varList = productService.findList(pd);
            pd = this.getPageData();
            mv.setViewName("system/p_quanxian/p_product_quanxian_save");
            mv.addObject("msg", "save");
            mv.addObject("varList", varList);
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
        logBefore(logger, "去修改商品页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            pd = p_QuanXuanService.findById1(pd);          //根据ID读取
            mv.setViewName("system/p_quanxian/p_quanxian_edit");
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
    @RequestMapping(value = "/list")
    public ModelAndView list(Page page) {
        logBefore(logger, "查询商品列表");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            page.setPd(pd);
            List<PageData> varList = p_QuanXuanService.list(page);          //列出Pro_Info列表
            mv.setViewName("system/p_quanxian/p_quanxian_list");
            mv.addObject("varList", varList);
            mv.addObject("pd", pd);
            mv.addObject(Const.SESSION_QX, this.getHC());          //按钮权限
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    /**
     * 列表
     */
    @RequestMapping(value = "/uplist")
    public ModelAndView uplist(Page page) {
        logBefore(logger, "查询商品下用户列表");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            page.setPd(pd);
            List<PageData> varList = p_QuanXuanService.findUserByProductlistPage(page);          //列出Pro_Info列表
            mv.setViewName("system/p_quanxian/p_product_quanxian_list");
            mv.addObject("varList", varList);
            mv.addObject("pd", pd);
            mv.addObject(Const.SESSION_QX, this.getHC());          //按钮权限
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }


    /**
     * 删除
     */
    @RequestMapping(value = "/deleteup")
    public void deleteup(PrintWriter out) {
        logBefore(logger, "删除商品");
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            //先获取此权限表中的 product 和 user 对象
            PageData pageData = p_QuanXuanService.findById1(pd);
            if (null != pageData) {
                //根据product和user删除对应的购物车信息
                cartService.delUserCart(pageData);
                //根据权限ID删除此用户权限信息
                p_QuanXuanService.delete(pd);
                if (null == p_QuanXuanService.findList(pageData) || 0 == p_QuanXuanService.findList(pageData).size()) {
                    pageData.put("VIP", "0");
                    appuserService.editVip(pageData);
                }
            }
            out.write("success");
            out.close();
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }

    }


    /**
     * 批量删除
     */
    @RequestMapping(value = "/deleteAll")
    @ResponseBody
    public Object deleteAll() {
        logBefore(logger, "批量删除商品类型");
        PageData pd = new PageData();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            pd = this.getPageData();
            List<PageData> pdList = new ArrayList<PageData>();
            String DATA_IDS = pd.getString("DATA_IDS");
            if (null != DATA_IDS && !"".equals(DATA_IDS)) {
                String ArrayDATA_IDS[] = DATA_IDS.split(",");
                for (int i = 0; i < ArrayDATA_IDS.length; i++) {
                    pd.put("P_QUANXIANID", ArrayDATA_IDS[i]);
                    PageData pageData = p_QuanXuanService.findById1(pd);
                    if (null != pageData) {
                        cartService.delUserCart(pageData);
                        if (null == p_QuanXuanService.findList(pageData) || 0 == p_QuanXuanService.findList(pd).size()) {
                            pageData.put("VIP", "0");
                            appuserService.editVip(pageData);
                        }
                    }
                }
                p_QuanXuanService.deleteAll(ArrayDATA_IDS);
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
