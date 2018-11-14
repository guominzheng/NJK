package com.fh.controller.system.coupon;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.system.appuser.AppuserService;
import com.fh.service.system.appuserInfo.AppUserInfoService;
import com.fh.service.system.coupon.CouponService;
import com.fh.util.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 用户优惠controller
 */
@Controller
@RequestMapping(value = "coupon", produces = "text/html;charset=UTF-8")
public class couponController extends BaseController {
    String menuUrl = "coupon/list.do"; //菜单地址(权限用)
    @Resource(name = "couponService")
    private CouponService couponService;
    @Resource(name = "appUserInfoService")
    private AppUserInfoService appUserInfoService;
    @Resource(name = "appuserService")
    private AppuserService appuserService;

    /**
     * 获取全部用户优惠信息
     *
     * @param page
     * @return
     */
    @RequestMapping(value = "/list", produces = "text/html;charset=UTF-8")
    public ModelAndView list(Page page) {
        ModelAndView modelAndView = this.getModelAndView();
        logBefore(logger, "查询用户优惠列表");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) return null; //判断当前用户是否有权限查询内容
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            String USERNAME = pd.getString("USERNAME");//添加用户提交数据
            if (null != USERNAME && !"".equals(USERNAME)) {
                USERNAME = USERNAME.trim();
                pd.put("USERNAME", USERNAME);
            }
            String STATUS = pd.getString("STATUS");//添加用户提交数据
            if (null != STATUS && !"".equals(STATUS)) {
                STATUS = STATUS.trim();
                pd.put("STATUS", STATUS);
            }
            System.out.println("==============>" + STATUS);
            page.setPd(pd);//封装提交数据
            List<PageData> varList = couponService.dataslistPage(page);  //获取用户想获得的数据
            modelAndView.setViewName("system/coupon/coupon_list");
            modelAndView.addObject("varList", varList);          //封装数据
            modelAndView.addObject("pd", pd);                    //封装map对象
            modelAndView.addObject(Const.SESSION_QX, this.getHC());           //按钮权限

        } catch (Exception e) {
            logger.error(e.toString(), e);//logger打印异常
        }
        return modelAndView;
    }

    /**
     * 去一键发送页面
     */
    @RequestMapping(value = "/goOne")
    public ModelAndView goOne() {
        logBefore(logger, "去发送优惠券页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            mv.setViewName("system/coupon/coupon_oneKey");
            mv.addObject("msg", "save");
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    /**
     * 一键发送
     *
     * @return
     */
    @RequestMapping("/oneKey")
    public ModelAndView oneKey() throws Exception {
        logBefore(logger, "一键发送优惠券");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
            return null;
        } //校验权限
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        PageData user =null;
        List<PageData> USERList = appuserService.findAppUserVip(pd);//获取全部会员用户
        try {
            PageData dto = new PageData();
            dto.put("DATE",DateUtil.getDay());
            for(int i =0;i<USERList.size();i++){
                dto.put("USER_ID",USERList.get(i).get("USER_ID").toString());
                if(couponService.findCopyUser(dto)!=null){
                    USERList.remove(i);
                }
            }
            Map<String, Object> map = new HashMap();
            if(pd.getString("BEIZHU").equals("10月1VIP50卷")){
                send(USERList,appuserService);
            }
            map.put("list", USERList);
            pd.put("DATE", DateUtil.getTime());
            map.put("pd", pd);
            couponService.batchSave(map);
            mv.addObject("msg", "success");
            mv.setViewName("save_result");
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    /**
     * 页面跳转至优惠信息列表
     *
     * @param page
     * @return
     * @throws Exception
     */


    /**
     * 去新增页面
     */
    @RequestMapping(value = "/goAdd")
    public ModelAndView goAdd() {
        logBefore(logger, "去新增优惠信息页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        try {
            mv.setViewName("system/coupon/coupon_save");
            mv.addObject("msg", "save");
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    /**
     * 新增
     */
    @RequestMapping(value = "/save", produces = "text/html;charset=UTF-8")
    public ModelAndView save() throws Exception {
        logBefore(logger, "新增商品类型");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
            return null;
        } //校验权限
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            String IMG = pd.getString("IMG");
            String BEIZHU = pd.getString("BEIZHU");
            PageData User = appUserInfoService.findUserByID(pd);
            pd.put("USER_ID", User.get("USER_ID").toString());
            pd.put("DATE", DateUtil.getDay());
            PageData coupon = couponService.findCopyUser(pd);
            if(null==coupon){
                if (null != IMG && !"".equals(IMG)) {
                    IMG = IMG.trim();
                    pd.put("IMG", IMG);
                } else {
                    pd.put("IMG", "");
                }
                if (null != BEIZHU && !"".equals(BEIZHU)) {
                    BEIZHU = BEIZHU.trim();
                    pd.put("BEIZHU", BEIZHU);
                } else {
                    pd.put("BEIZHU", "");
                }
                pd.put("STATUS", 0);//活动状态
                pd.put("ZSTATUS", "0");
                pd.remove("DATE");
                pd.put("DATE", DateUtil.getTime());
                couponService.save(pd);
            }
            mv.addObject("msg", "success");
            mv.setViewName("save_result");
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete")
    public void delete(PrintWriter out) throws Exception {
        logBefore(logger, "删除用户优惠信息");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
            return;
        } //校验权限
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            String COUPON_ID = pd.getString("COUPON_ID");//添加用户提交数据
            if (null != COUPON_ID && !"".equals(COUPON_ID)) {
                COUPON_ID = COUPON_ID.trim();
                pd.put("COUPON_ID", COUPON_ID);
            }
            couponService.delete(pd);
            out.write("success");
            out.close();
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
    }

    /**
     * 去修改页面
     */
    @RequestMapping(value = "/goEdit")
    public ModelAndView goEdit() {
        logBefore(logger, "去修改优惠信息页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            pd = couponService.findCouponId(pd);//根据ID读取
            mv.setViewName("system/coupon/coupon_edit");
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
    public ModelAndView edit(HttpServletRequest request) throws Exception {
        logBefore(logger, "修改优惠信息");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
            return null;
        } //校验权限
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
//		pd.put("CONTENT", "");	//产品描述 默认为空
        couponService.edit(pd);
        mv.addObject("msg", "success");
        mv.setViewName("save_result");
        return mv;
    }


    /**
     * 批量删除
     */
    @RequestMapping(value = "/deleteAll")
    @ResponseBody
    public Object deleteAll() {
        logBefore(logger, "批量删除商品类型");
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
                couponService.deleteAll(ArrayDATA_IDS);
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
     * 验证用输入账号是否存在
     *
     * @param page
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/selectUserName")
    @ResponseBody
    public String selectUserName(Page page) throws Exception {
        logBefore(logger, "查询用户是否存在");
        ObjectMapper mapper = new ObjectMapper();
        PageData pageData = new PageData();
        pageData = this.getPageData();
        String USERNAME = pageData.getString("USERNAME");//添加用户提交数据
        if (null != USERNAME && !"".equals(USERNAME)) {
            USERNAME = USERNAME.trim();
            pageData.put("USERNAME", USERNAME);
        }
        if (null == (appUserInfoService.findUserByID(pageData))) {
            pageData.put("result", "true");
        } else {
            pageData.put("NAME", appUserInfoService.findUserByID(pageData).getString("NAME"));
        }
        return mapper.writeValueAsString(pageData);
    }

    /* ===============================权限================================== */
    public Map<String, String> getHC() {
        Subject currentUser = SecurityUtils.getSubject();  //shiro管理的session
        Session session = currentUser.getSession();
        Map<String, String> map = new HashMap();
        if (session.getAttribute(Const.SESSION_QX) != null)
            map = (Map<String, String>) session.getAttribute(Const.SESSION_QX);
        return map;
    }
    /* ===============================权限================================== */

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(format, true));
    }


    public static void main(String[] args) {
        SmsBao sms = new SmsBao();
        try{
            sms.sendSMS("18736040966","“发钱了！”\n" +
                    "尊敬的农极客VIP用户，为了感谢猫友一直以来对农极客的支持，“国庆代金券大放送”，现在赠送您一张50元代金券！\n" +
                    "该代金券，农极客商城所有产品通用，无门槛，有效期6个月，想啥时候用就啥时候用~\n" +
                    "点击链接http://a.app.qq.com/o/simple.jsp?pkgname=com.ylsoft.njk\n  即可进入农极客APP查看详情。");
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void send(List<PageData> pdList,AppuserService appuserService){
            new Thread(new SendSmsThread(pdList,appuserService));
    }

}
