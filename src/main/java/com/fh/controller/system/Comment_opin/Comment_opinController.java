package com.fh.controller.system.Comment_opin;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.system.Comment_opin.Comment_opinService;
import com.fh.service.system.opinion.OpinionService;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 用户建议信息Controller
 *
 * 继承Controller基类，便于获取form表单数据
 */
@Controller
@RequestMapping(value = "comment_opin", produces = "text/html;charset=UTF-8")
public class Comment_opinController extends BaseController {
    String menuUrl = "comment_opin/list.do"; //菜单地址(权限用)
    //注入 user回复信息表 Service
    @Resource(name="comment_opinService")
    private Comment_opinService comment_opinService;
    @Resource(name="opinionService")
    private OpinionService opinionService;

    /**
     * 按条件查询 返回用户建议信息列表
     * @param page
     * @return model  view
     */
    @RequestMapping(value="/list")
    public ModelAndView list(Page page,HttpServletRequest request) throws  Exception{
        logBefore(logger, "---------->查询用户建议回复列表");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) return null; //判断当前用户是否有权限查询内容
        ModelAndView modelAndView = new ModelAndView();
        modelAndView =  this.getModelAndView(); //Controller基类中的创建方法
        PageData pageData = new PageData();
        pageData=this.getPageData();            //Controller基类中获取pageData(接收数据对象)， 封装了提取FORM表单数据方法 自动获取数据

        //=====================判断form表单 是否提交了用户建议表ID的信息  然后进行封装
        System.out.println("------------------------->list-->"+pageData.getString("OPINION_ID"));
        String OPINION_ID = pageData.getString("OPINION_ID");//添加用户提交数据
        if (null != OPINION_ID && !"".equals(OPINION_ID)) {
            OPINION_ID = OPINION_ID.trim();
            pageData.put("OPINION_ID", OPINION_ID);
        }else{
            pageData.put("OPINION_ID",request.getSession().getAttribute("OPINION_ID"));
            request.getSession().removeAttribute("OPINION_ID");
        }
       // =====================end=======
/*
       // =====================判断form表单 是否提交了客服ID的信息  然后进行封装
        String EXCLU_ID = pageData.getString("EXCLU_ID");//添加用户提交数据
        if (null != EXCLU_ID && !"".equals(EXCLU_ID)) {
            EXCLU_ID = EXCLU_ID.trim();
            pageData.put("EXCLU_ID", EXCLU_ID);
        }
        //=====================end=======

       // =====================判断form表单 是否提交了用户建议回复信息表ID的信息  然后进行封装
        String COMMENT_OPIN_ID = pageData.getString("COMMENT_OPIN_ID");//添加用户提交数据
        if (null != COMMENT_OPIN_ID && !"".equals(COMMENT_OPIN_ID)) {
            COMMENT_OPIN_ID = COMMENT_OPIN_ID.trim();
            pageData.put("COMMENT_OPIN_ID", COMMENT_OPIN_ID);
        }
       // =====================end=======*/

        PageData pd= opinionService.findOpinionById(pageData);
        if(pd!=null){pageData.put("USER_ID",pd.getString("USER_ID"));}
        page.setPd(pageData);// page封装 Data 进行传参
        try {
        List<PageData> varList = comment_opinService.findlistPage(page);//获取集合
        modelAndView.addObject("varList",varList);
        modelAndView.setViewName("system/comment_opin/comment_opin_list");//封装视图
        modelAndView.addObject("pd", pageData);              //封装map对象
        modelAndView.addObject(Const.SESSION_QX, this.getHC());           //按钮权限
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return modelAndView;
    }
/*
    *//**
     * 列表
     *//*
    @RequestMapping(value = "/list")
    public ModelAndView list(Page page) {
        logBefore(logger, "查询商品列表");
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
            String S_STATUS = pd.getString("S_STATUS");
            if (null != S_STATUS && !"".equals(S_STATUS)) {
                S_STATUS = S_STATUS.trim();
                pd.put("S_STATUS", S_STATUS);
            }
            String STATUS = pd.getString("STATUS");
            if (null != STATUS && !"".equals(STATUS)) {
                STATUS = STATUS.trim();
                pd.put("STATUS", STATUS);
            }
            page.setPd(pd);
            List<PageData> varList = comment_opinService.findlistPage(page);//获取集合
            mv.setViewName("system/comment_opin/comment_opin_list");
            mv.addObject("varList", varList);
            mv.addObject("pd", pd);
            mv.addObject(Const.SESSION_QX, this.getHC());          //按钮权限
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }*/

    /**
     * 去增加评论
     * @return
     */

    @RequestMapping(value = "/gosave")
    public ModelAndView goOne() {
        logBefore(logger, "去添加评论页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            mv.setViewName("system/comment_opin/coment_opin_save");
            mv.addObject("pd",pd);
            mv.addObject("msg", "save");
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    /**
     * 添加评论
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/save", produces = "text/html;charset=UTF-8")
    public ModelAndView save(HttpServletRequest request) throws Exception {
        logBefore(logger, "添加评论");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
            return null;
        } //校验权限
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd=this.getPageData();
        try {
            pd.put("CREATE_DATE", DateUtil.getTime());
            pd.put("EXCLU_ID", Jurisdiction.getCurrentUserID().getUSER_ID()); //获取当前的USER对象获取ID并封装
            String str  = DateUtil.delHTMLTag(pd.getString("editorValue"));
            String  DETALLS1 = str.replace("\r","");
            String  DETALLS2 =DETALLS1.replace("\n","");
            String  DETALLS3 =DETALLS2.replace("&nbsp;","");
            pd.put("INFO",DETALLS3);
            String a[] = pd.getString("editorValue").toString().split("src");
            comment_opinService.save(pd);
            /*if (a.length > 1) {
                System.err.println(a[1].substring(2, 95));
                PageData pd3 = new PageData();
                pd3.put("IMG", a[1].substring(2, 95).toString().split("\"")[0]);
                pd3.put("DATE",DateUtil.getTime());
                commentProIMGService.save(pd3);
            }*/
            request.getSession().setAttribute("OPINION_ID",pd.getString("OPINION_ID"));
            mv.addObject("msg", "success");
            mv.addObject("pd",pd);
            mv.setViewName("save_result");
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
    public Object deleteAll(HttpServletRequest request) {
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
                comment_opinService.deleteAll(ArrayDATA_IDS);
                pd.put("msg", "ok");
            } else {
                pd.put("msg", "no");
            }
            pdList.add(pd);
            map.put("list", pdList);
            request.getSession().setAttribute("OPINION_ID",pd.getString("OPINION_ID"));
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

}
