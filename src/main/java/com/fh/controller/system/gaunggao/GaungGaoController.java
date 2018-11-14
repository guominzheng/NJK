package com.fh.controller.system.gaunggao;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

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
import com.fh.service.system.guanggao.GuangGaoService;
import com.fh.util.Const;
import com.fh.util.DateUtil;
import com.fh.util.DelAllFile;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.fh.util.PathUtil;

@Controller
@RequestMapping(value = "guanggao", produces = "text/html;charset=UTF-8")
public class GaungGaoController extends BaseController {

    String menuUrl = "guanggao/list.do"; //菜单地址(权限用)

    @Resource(name = "guangGaoService")
    private GuangGaoService guangGaoService;


    // 删除首页图片
    @RequestMapping(value = "/deltp")
    @ResponseBody
    public String deltp(PrintWriter out) {
        logBefore(logger, "删除图片");
        try {
            PageData pd = new PageData();
            pd = this.getPageData();

            String PATH = pd.getString("IMG"); // 图片路径
            if (PATH != null) {
                String path[] = PATH.split("uploadFiles");
                if (path.length > 1) {
                    String DPath = "uploadFiles" + path[1];
                    DelAllFile.delFolder(PathUtil.getClasspath() + DPath); // 删除后台图片
                }

                pd = guangGaoService.findById(pd);
                pd.put("IMG", "");
                guangGaoService.edit(pd);
            }

            //out.write("success");
            out.close();
            return "success";
        } catch (Exception e) {
            logger.error(e.toString(), e);
            return "fail";
        }
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete")
    public void delete(PrintWriter out) {
        logBefore(logger, "删除问答广告");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
            return;
        } //校验权限
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            PageData pd_c = guangGaoService.findById(pd);
            String PATH = pd_c.getString("IMG"); // 图片路径
            String path[] = PATH.split("uploadFiles");
            if (path.length > 1) {
                String DPath = "uploadFiles" + path[1];
                DelAllFile.delFolder(PathUtil.getClasspath() + DPath); // 删除后台图片
            }
            guangGaoService.delete(pd);
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
        logBefore(logger, "修改问答广告");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
            return null;
        } //校验权限
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        guangGaoService.edit(pd);
        mv.addObject("msg", "success");
        mv.setViewName("save_result");
        return mv;
    }

    /**
     * 去修改页面
     */
    @RequestMapping(value = "/goEdit")
    public ModelAndView goEdit() {
        logBefore(logger, "去修改问答广告");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            pd = guangGaoService.findById(pd);          //根据ID读取
            mv.setViewName("system/guanggao/guanggao_edit");
            mv.addObject("msg", "edit");
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    /**
     * 新增
     */
    @RequestMapping(value = "/save")
    public ModelAndView save() throws Exception {
        logBefore(logger, "新增问答广告");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
            return null;
        } //校验权限
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        pd.put("GUANGGAO_ID", this.get32UUID());          //主键
        pd.put("DATE", DateUtil.getTime());
        guangGaoService.save(pd);
        mv.addObject("msg", "success");
        mv.setViewName("save_result");
        return mv;
    }

    /**
     * 去新增页面
     */
    @RequestMapping(value = "/goAdd")
    public ModelAndView goAdd() {
        logBefore(logger, "去新增问答广告页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        try {
            mv.setViewName("system/guanggao/guanggao_edit");
            mv.addObject("msg", "save");
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    @RequestMapping(value = "/list")
    public ModelAndView list(Page page) {
        logBefore(logger, "显示问答广告");
        //if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            page.setPd(pd);
            List<PageData> varList = guangGaoService.datalistPage(page);          //列出Pro_Info列表
            //List<PageData>	list=pro_infoService.findByShopId(pd);//产品
            mv.setViewName("system/guanggao/guanggao_list");
            mv.addObject("varList", varList);
            mv.addObject("pd", pd);
            mv.addObject(Const.SESSION_QX, this.getHC());          //按钮权限
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
