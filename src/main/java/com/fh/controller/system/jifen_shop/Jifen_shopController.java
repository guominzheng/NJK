package com.fh.controller.system.jifen_shop;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.system.count_community.Count_communityService;
import com.fh.service.system.integral.IntegralService;
import com.fh.service.system.jifen_shop.Jifen_shopService;
import com.fh.util.*;
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

import javax.annotation.Resource;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping(value="jifen_shopController",produces="text/html;charset=UTF-8")
public class Jifen_shopController extends BaseController {

    String menuUrl = "jifen_shopController/list.do"; //菜单地址(权限用)

    @Resource(name="jifen_shopService")
    private Jifen_shopService jifenShopService;
    @Resource
    private Count_communityService countCommunityService;


    /**
     * 列表
     */
    @RequestMapping(value="/list")
    public ModelAndView list(Page page){
        logBefore(logger, "积分商品list");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        try{
            pd=this.getPageData();
            page.setPd(pd);
            List<PageData> varList = jifenShopService.data1listPage(page);
            mv.setViewName("system/jifen_shop/jifen_shop_list");
            mv.addObject("varList", varList);
            mv.addObject("pd", pd);
            mv.addObject("page", page);
            mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
        }catch (Exception e){
            e.printStackTrace();
        }
        return mv;
    }

    /**
     * 列表
     */
    @RequestMapping(value="/goAdd")
    public ModelAndView goAdd(Page page){
        logBefore(logger, "去积分商品新增");
        ModelAndView mv = this.getModelAndView();
        try {
            mv.setViewName("system/jifen_shop/jifen_shop_edit");
            mv.addObject("msg", "save");
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }



    @RequestMapping(value="/goEditCount")
    public ModelAndView goEditCount(Page page){
        logBefore(logger, "去修改兑换列表");
        ModelAndView mv = this.getModelAndView();
        try {
            PageData pd = this.getPageData();
            pd = countCommunityService.findById(pd);
            mv.setViewName("system/jifen_shop/paycount_edit");
            mv.addObject("msg", "editpay");
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }
    /**
     * 列表
     */
    @RequestMapping(value="/goEdit")
    public ModelAndView goEdit(Page page){
        logBefore(logger, "去积分商品新增");
        ModelAndView mv = this.getModelAndView();
        try {
            PageData pd = this.getPageData();
            pd = jifenShopService.findById(pd);
            mv.setViewName("system/jifen_shop/jifen_shop_edit");
            mv.addObject("msg", "edit");
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    @RequestMapping(value="/save")
    public ModelAndView save (){
        logBefore(logger, "添加积分商品");
        PageData pd = new PageData();
        ModelAndView modelAndView = new ModelAndView();
        try{
            pd=this.getPageData();
            System.out.println(pd.getString("editorValue"));
            pd.put("JIFEN_MIAOSHU", "<style>img{max-width:100%;}</style>" + pd.getString("editorValue"));
            System.out.println(pd.getString("JIFEN_MIAOSHU"));
            pd.put("STATUS_SHANG","1");
            pd.put("STATUS_WU","0");
            pd.put("CREATE_DATE", DateUtil.getDay());
            pd.put("UPDATE_DATE",DateUtil.getDay());
            jifenShopService.save(pd);
            modelAndView.addObject("msg","save");
            modelAndView.setViewName("save_result");
        }catch (Exception e){
            e.printStackTrace();
        }
        return modelAndView;
    }
    @RequestMapping(value="/countList")
    public  ModelAndView countList(Page page){
        logBefore(logger, "商品兑换记录");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        try{
            pd=this.getPageData();
            page.setPd(pd);
            List<PageData> varList = countCommunityService.UdatalistPage(page);
            for(PageData p:varList){
                String s = p.getString("ORDER_NUM");
                s= s.replace(" ","");
                if(null != s&&!"".equals(s)){
                    String[]a = s.split(",");
                    p.put("STATUSH","1");
                    p.put("ORDERNUM",a[0]);
                    p.put("PRISE",a[1]);
                }else {
                    p.put("STATUSH","0");
                    p.put("ORDERNUM","");
                    p.put("PRISE","");
                }
            }
            mv.setViewName("system/jifen_shop/payList");
            mv.addObject("varList", varList);
            mv.addObject("pd", pd);
            mv.addObject("page", page);
            mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
        }catch (Exception e){
            e.printStackTrace();
        }
        return mv;
    }


    @RequestMapping(value="/editpay")
    public ModelAndView editpay (){
        logBefore(logger, "修改物流状态");
        PageData pd = new PageData();
        ModelAndView modelAndView = new ModelAndView();
        try{
            pd=this.getPageData();
            String ORDER_NUM =pd.getString("ORDER")+","+pd.getString("PRISE");
            pd.put("ORDER_NUM",ORDER_NUM);
            countCommunityService.edit(pd);
            modelAndView.addObject("msg","success");
            modelAndView.setViewName("save_result");
        }catch (Exception e){
            e.printStackTrace();
        }
        return modelAndView;
    }



    @RequestMapping(value="/edit")
    public ModelAndView edit (){
        logBefore(logger, "修改积分商品");
        PageData pd = new PageData();
        ModelAndView modelAndView = new ModelAndView();
        try{
            pd=this.getPageData();
            int a = Integer.parseInt(pd.get("STATUS_WU").toString());
            if(a == 1){
                jifenShopService.editNotInfo(pd);
            }
            pd.put("UPDATE_DATE",DateUtil.getDay());
            pd.put("JIFEN_MIAOSHU", pd.getString("editorValue"));
            jifenShopService.editInfo(pd);
            modelAndView.addObject("msg","success");
            modelAndView.setViewName("save_result");
        }catch (Exception e){
            e.printStackTrace();
        }
        return modelAndView;
    }


    /**
     * 删除
     */
    @RequestMapping(value = "/delete")
    public void delete(PrintWriter out) throws Exception {
        logBefore(logger, "删除积分商品");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
            return;
        } //校验权限
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            String JIFENSHOP_ID = pd.getString("JIFENSHOP_ID");
            if (null != JIFENSHOP_ID && !"".equals(JIFENSHOP_ID)) {
                JIFENSHOP_ID = JIFENSHOP_ID.trim();
                pd.put("JIFENSHOP_ID", JIFENSHOP_ID);
            }
            jifenShopService.delete(pd);
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
        logBefore(logger, "批量删除积分商品");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "dell")) {
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
                jifenShopService.deleteAll(ArrayDATA_IDS);
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
