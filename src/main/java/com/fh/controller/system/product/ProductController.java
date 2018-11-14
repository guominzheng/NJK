package com.fh.controller.system.product;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.fh.service.system.comment_research.Comment_ResearchService;
import com.fh.service.system.coupon.CouponService;
import com.fh.service.system.remark.RemarkService;
import com.fh.service.system.zan_comment_research.Zan_CommentResearchService;
import com.fh.service.system.zan_comment_research_youke.Zan_CommentResearchYoukeService;
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
import com.fh.entity.system.User;
import com.fh.service.system.cart.CartService;
import com.fh.service.system.gys.GysService;
import com.fh.service.system.post.PostService;
import com.fh.service.system.product.ProductService;
import com.fh.service.system.product_type.Product_typeService;
import com.fh.service.system.research.ResearchService;
import com.fh.service.system.research_img.Research_ImgService;
import com.fh.service.system.taocan.TaoCanService;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.DateUtil;
import com.fh.util.DelAllFile;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.fh.util.PathUtil;

@Controller
@RequestMapping(value = "product", produces = "text/html;charset=UTF-8")
public class ProductController extends BaseController {
    String menuUrl = "product/list.do"; //菜单地址(权限用)
    @Resource(name = "productService")
    private ProductService productService;
    @Resource(name = "gysService")
    private GysService gysService;
    @Resource(name = "postService")
    private PostService postService;
    @Resource(name = "product_typeService")
    private Product_typeService product_typeService;
    @Resource(name = "cartService")
    private CartService cartService;
    @Resource(name = "taoCanService")
    private TaoCanService taoCanService;
    @Resource(name = "researchService")
    private ResearchService researchService;
    @Resource(name = "research_ImgService")
    private Research_ImgService research_ImgService;
    @Resource(name = "couponService")
    private CouponService couponService;
    @Resource(name = "comment_ResearchService")
    private Comment_ResearchService comment_researchService;
    @Resource(name = "remarkService")
    private RemarkService remark;
    @Resource(name = "zan_CommentResearchYoukeService")
    private Zan_CommentResearchYoukeService zan_CommentResearchYoukeService;
    @Resource(name = "zan_CommentResearchService")
    private Zan_CommentResearchService zan_CommentResearchService;


    /**
     * 修改是否未精华
     */
    @RequestMapping(value = "/editTstatus")
    public void editTstatus(PrintWriter out) {
        logBefore(logger, "修改精华");
        PageData pd = this.getPageData();
        try {
            researchService.editTSTATUS(pd);
            PageData pd_r = researchService.findById(pd);
			/*List<PageData> list=couponService.findUserId(pd);
			if(list.size()==0){
                PageData pd_c=new PageData();
                pd_c.put("MAX","0");
                pd_c.put("JIAN","30");
                pd_c.put("DATE",DateUtil.getTime());
                pd_c.put("USER_ID",pd_r.getString("USER_ID"));
                pd_c.put("END",DateUtil.getTime());
                pd_c.put("STATUS","0");
                pd_c.put("IMG", "");
                pd_c.put("BEIZHU", "精华帖子优惠卷");
                couponService.save(pd_c);
            }*/
            out.write("success");
            out.close();
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }

    }

    /**
     * 删除
     */
    @RequestMapping(value = "/fabu")
    public void fabu(PrintWriter out) {
        logBefore(logger, "发布专题帖子");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
            return;
        } //校验权限
        PageData pd = this.getPageData();
        try {
            researchService.editFaBu(pd);
            out.write("success");
            out.close();
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }

    }

    @RequestMapping(value = "tshow")
    public ModelAndView tshow(Page page) {
        logBefore(logger, "显示套餐商品");
        //if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            pd.put("PRODUCT_ID1", pd.getString("PRODUCT_ID"));
            page.setPd(pd);
            List<PageData> varList = taoCanService.datalistPage(page);          //根据ID读取
            mv.addObject("varList", varList);
            mv.setViewName("system/product/product_tshow");
            mv.addObject("pd", pd);
            mv.addObject(Const.SESSION_QX, this.getHC());          //按钮权限
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }


    @RequestMapping(value = "/rshow")
    public ModelAndView rshow(Page page, HttpServletRequest request) {
        logBefore(logger, "显示商品研究院");
        //if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            String PRODUCT_ID = pd.getString("PRODUCT_ID");
            if (null != PRODUCT_ID && !"".equals(PRODUCT_ID)) {
                PRODUCT_ID = PRODUCT_ID.trim();
                pd.put("PRODUCT_ID", PRODUCT_ID);
            } else {
                pd.put("PRODUCT_ID", request.getSession().getAttribute("PRODUCT_ID"));
                request.getSession().removeAttribute("PRODUCT_ID");
            }
            page.setPd(pd);
            List<PageData> varList = researchService.datalistPage(page);          //根据ID读取
            mv.addObject("varList", varList);
            mv.setViewName("system/product/product_rshow");
            mv.addObject("pd", pd);
            mv.addObject(Const.SESSION_QX, this.getHC());          //按钮权限
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    // 删除首页图片
    @RequestMapping(value = "/deltp")
    @ResponseBody
    public String deltp(PrintWriter out) {
        logBefore(logger, "删除图片");
        try {
            PageData pd = new PageData();
            pd = this.getPageData();

            String PATH = pd.getString("IMG"); // 图片路径
            if (PATH != null && PATH != "") {
                String path[] = PATH.split("uploadFiles");
                if (path.length > 1) {
                    String DPath = "uploadFiles" + path[1];
                    DelAllFile.delFolder(PathUtil.getClasspath() + DPath); // 删除后台图片
                }
                pd = productService.findById(pd);
                pd.put("IMG", "");
                productService.edit(pd);
            }

            //out.write("success");
            out.close();
            return "success";
        } catch (Exception e) {
            logger.error(e.toString(), e);
            return "fail";
        }
    }


    // 删除首页图片
    @RequestMapping(value = "/deltpp")
    @ResponseBody
    public String deltpp(PrintWriter out) {
        logBefore(logger, "删除图片");
        try {
            PageData pd = new PageData();
            pd = this.getPageData();

            String PATH = pd.getString("RIMG"); // 图片路径
            if (PATH != null && PATH != "") {
                String path[] = PATH.split("uploadFiles");
                if (path.length > 1) {
                    String DPath = "uploadFiles" + path[1];
                    DelAllFile.delFolder(PathUtil.getClasspath() + DPath); // 删除后台图片
                }

                if (PATH != null) {
                    pd = productService.findById(pd);
                    pd.put("RIMG", "");
                    productService.edit(pd);
                }
            }

            //out.write("success");
            out.close();
            return "success";
        } catch (Exception e) {
            logger.error(e.toString(), e);
            return "fail";
        }
    }

    // 删除首页图片
    @RequestMapping(value = "/deltppp")
    @ResponseBody
    public String deltppp(PrintWriter out) {
        logBefore(logger, "删除图片");
        try {
            PageData pd = new PageData();
            pd = this.getPageData();

            String PATH = pd.getString("TIMG"); // 图片路径
            if (PATH != null && PATH != "") {
                String path[] = PATH.split("uploadFiles");
                if (path.length > 1) {
                    String DPath = "uploadFiles" + path[1];
                    DelAllFile.delFolder(PathUtil.getClasspath() + DPath); // 删除后台图片
                }

                if (PATH != null) {
                    pd = productService.findById(pd);
                    pd.put("TIMG", "");
                    productService.edit(pd);
                }
            }
            //out.write("success");
            out.close();
            return "success";
        } catch (Exception e) {
            logger.error(e.toString(), e);
            return "fail";
        }
    }


    // 删除首页图片
    @RequestMapping(value = "/deltps")
    @ResponseBody
    public String deltps(PrintWriter out) {
        logBefore(logger, "删除图片");
        try {
            PageData pd = new PageData();
            pd = this.getPageData();

            String PATH = pd.getString("IMG"); // 图片路径
            if(PATH!=null&&PATH!=""){
                String path[] = PATH.split("uploadFiles");
                if(path.length > 1){
                    String DPath = "uploadFiles"+path[1];
                    DelAllFile.delFolder(PathUtil.getClasspath() + DPath); // 删除后台图片
                }
                research_ImgService.deleteImg(pd);
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
     * 新增
     */
    @RequestMapping(value = "/save")
    public ModelAndView save() throws Exception {
        logBefore(logger, "新增商品");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
            return null;
        } //校验权限
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        pd.put("PRODUCT_ID", this.get32UUID());          //主键
        //String DETAILS=pd.getString("editorValue");
        //String DETAILS1=DETAILS.replace("localhost", "192.168.31.193");
        //String DETAILS1=DETAILS.replace("localhost", "101.200.130.60");
        pd.put("DETAILS", "<style>img{max-width:100%;}</style>" + pd.getString("editorValue"));
        pd.put("VIEW", "0");
        pd.put("GGIMG", "");
        pd.put("CREATE_DATE",DateUtil.getTime());
        productService.save(pd);
        mv.addObject("msg", "success");
        mv.setViewName("save_result");
        return mv;
    }


    /**
     * 新增
     */
    @RequestMapping(value = "/tsave")
    public ModelAndView tsave() throws Exception {
        logBefore(logger, "新增套餐商品");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
            return null;
        } //校验权限
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        taoCanService.save(pd);
        mv.addObject("msg", "success");
        mv.setViewName("save_result");
        return mv;
    }

    /**
     * 新增研究院从评论
     */
    @RequestMapping(value = "/saveRes")
    public ModelAndView saveRes(HttpServletRequest request) throws Exception {
        logBefore(logger, "新增研究院从评论");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "save")) {
            return null;
        } //校验权限
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        User u = Jurisdiction.getCurrentUserID();//获取当前的USER_ID
        String COMMENT_RESEARCH_ID = pd.getString("COMMENT_RESEARCH_ID");
        //获取当前评论信息
        PageData pageData = comment_researchService.findById(pd);
        if (pageData != null) {
            pd.put("RESEARCH_ID", request.getSession().getAttribute("RESEARCH_ID"));
            pd.put("USER_ID", u.getUSER_ID());
            pd.put("NAME", u.getNAME());
            pd.put("PUSER_ID", pageData.getString("USER_ID"));
            pd.put("PNAME", pageData.getString("NAME"));
            pd.put("DATE", DateUtil.getTime());
            pd.put("VIEWS", "0");
            pd.put("STATUS", "0");
            pd.put("YUE", DateUtil.getYue());
            pd.put("PID", COMMENT_RESEARCH_ID);
            String str = DateUtil.delHTMLTag(pd.getString("editorValue"));
            String DETALLS1 = str.replace("\r", "");
            String DETALLS2 = DETALLS1.replace("\n", "");
            String DETALLS3 = DETALLS2.replace("&nbsp;", "");
            pd.put("MESSAGE", DETALLS3);
            comment_researchService.save(pd);
            String a[] = pd.getString("editorValue").toString().split("src");
            String RESEARCH_ID = pd.get("RESEARCH_ID").toString();
            if (a.length > 1) {
                System.err.println(a[1].substring(2, 95));
                PageData pd3 = new PageData();
                pd3.put("RESEARCH_ID", RESEARCH_ID);
                pd3.put("IMG", a[1].substring(2, 95).toString().split("\"")[0]);
                pd3.put("ORDE_BY", 1);
                pd3.put("DATE", DateUtil.getTime());
                research_ImgService.save(pd3);
            }

        }
        mv.addObject("msg", "success");
        mv.setViewName("save_result");
        return mv;
    }

    /**
     * 新增研究院主评论
     */
    @RequestMapping(value = "/saveZComRs")
    public ModelAndView saveZComRs() throws Exception {
        logBefore(logger, "新增研究院主评论");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
            return null;
        } //校验权限
        ModelAndView mv = this.getModelAndView();
        User u = Jurisdiction.getCurrentUserID();//获取当前的USER_ID
        PageData pd = new PageData();
        pd = this.getPageData();
        PageData pageData =  researchService.findById(pd);
        researchService.editHuiFu(pageData);
        pd.put("PUSER_ID", "0");
        pd.put("PNAME", "");
        pd.put("PID", "0");
        pd.put("USER_ID", u.getUSER_ID());
        pd.put("NAME", u.getNAME());
        pd.put("DATE", DateUtil.getTime());
        pd.put("STATUS", "0");
        pd.put("VIEWS", "0");
        pd.put("YUE", DateUtil.getYue());
        String str = DateUtil.delHTMLTag(pd.getString("editorValue"));
        String DETALLS1 = str.replace("\r", "");
        String DETALLS2 = DETALLS1.replace("\n", "");
        String DETALLS3 = DETALLS2.replace("&nbsp;", "");
        pd.put("MESSAGE", DETALLS3);
        String a[] = pd.getString("editorValue").toString().split("src");
        comment_researchService.save(pd);
        String RESEARCH_ID = pd.get("RESEARCH_ID").toString();
        if (a.length > 1) {
            System.err.println(a[1].substring(2, 95));
            PageData pd3 = new PageData();
            pd3.put("RESEARCH_ID", RESEARCH_ID);
            pd3.put("IMG", a[1].substring(2, 95).toString().split("\"")[0]);
            pd3.put("ORDE_BY", 1);
            pd3.put("DATE", DateUtil.getTime());
            research_ImgService.save(pd3);
        }
        mv.addObject("msg", "success");
        mv.setViewName("save_result");
        return mv;












        /*if (!Jurisdiction.buttonJurisdiction(menuUrl, "save")) {
            return null;
        } //校验权限
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        User u = Jurisdiction.getCurrentUserID();//获取当前的USER_ID
        pd.put("PUSER_ID","0");
        pd.put("PNAME","");
        pd.put("PID","0");
        pd.put("USER_ID",u.getUSER_ID());
        pd.put("NAME",u.getNAME());
        pd.put("DATE",DateUtil.getTime());
        pd.put("STATUS","0");
        pd.put("VIEWS","0");
        pd.put("YUE",DateUtil.getYue());
        comment_researchService.save(pd);
        request.getSession().setAttribute("PRODUCT_ID",pd.getString("PRODUCT_ID"));
        mv.addObject("msg", "success");
        mv.setViewName("save_result");
        return mv;*/
    }

    /**
     * 新增
     */
    @RequestMapping(value = "/rsave")
    public ModelAndView rsave() throws Exception {
        logBefore(logger, "新增研究院");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
            return null;
        } //校验权限
        ModelAndView mv = this.getModelAndView();
        User u = Jurisdiction.getCurrentUserID();//获取当前的USER_ID
        PageData pd = new PageData();
        pd = this.getPageData();
        pd.put("DATE", DateUtil.getTime());
        pd.put("USER_ID", u.getUSER_ID());
        pd.put("VIEWS", "0");
        pd.put("HUIFU", "0");
        pd.put("FABU", "0");
        pd.put("NAME", "");
       /* String str = DateUtil.delHTMLTag(pd.getString("editorValue"));
        String DETALLS1 = str.replace("\r", "");
        String DETALLS2 = DETALLS1.replace("\n", "");
        String DETALLS3 = DETALLS2.replace("&nbsp;", "");*/
        pd.put("MESSAGE", pd.getString("editorValue"));
        pd.put("DATES", DateUtil.getTime());
        pd.put("TSTATUS", "0");
        String a[] = pd.getString("editorValue").toString().split("src");
        pd.put("LSTATUS", "0");
        pd.put("JIANJIE", "");
        pd.put("PINGSHEN", "0");
        pd.put("SHENCHA", "0");
        pd.put("ADDRESS", "农极客");
        researchService.save(pd);
        String RESEARCH_ID = pd.get("RESEARCH_ID").toString();
        if (a.length > 1) {
            System.err.println(a[1].substring(2, 95));
            PageData pd3 = new PageData();
            pd3.put("RESEARCH_ID", RESEARCH_ID);
            pd3.put("ORDE_BY", 1);
            pd3.put("DATE", DateUtil.getTime());
            research_ImgService.save(pd3);
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
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
            return;
        } //校验权限
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            productService.delete(pd);
            cartService.deletePId(pd);
            out.write("success");
            out.close();
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }

    }

    /**
     * 删除
     */
    @RequestMapping(value = "/rdelete")
    public void rdelete(PrintWriter out) {
        logBefore(logger, "删除研究院帖子");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
            return;
        } //校验权限
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            researchService.delete(pd);
            out.write("success");
            out.close();
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/comRsdelete")
    public void comRsdelete(PrintWriter out) {
        logBefore(logger, "删除某个评论");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
            return;
        } //校验权限
        PageData pd = null;
        try {
            pd = this.getPageData();
            PageData pageData = comment_researchService.findById(pd);
            if("0".equals(pageData.getString("PID")) || "".equals(pageData.getString("PID"))){
                PageData dto = researchService.findById(pageData);
                int hui = Integer.valueOf(dto.get("HUIFU").toString());
                if(hui>0){
                    researchService.editHuiFuJian(dto);
                }
            }
            comment_researchService.delete(pd);
            out.write("success");
            out.close();
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/tdelete")
    public void tdelete(PrintWriter out) {
        logBefore(logger, "删除套餐商品");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
            return;
        } //校验权限
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            taoCanService.delete(pd);
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
        logBefore(logger, "修改商品");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
            return null;
        } //校验权限
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
//		pd.put("CONTENT", "");	//产品描述 默认为空
        //String DETAILS=pd.getString("editorValue");
        //String DETAILS1=DETAILS.replace("localhost", "192.168.31.193");
        //String DETAILS1=DETAILS.replace("localhost", "101.200.130.60");
        pd.put("DETAILS", "<style>img{max-width:100%;}</style>" + pd.getString("editorValue"));
        if (pd.getString("STATUS").equals("1")) {
            cartService.deletePId(pd);
        }
        productService.edit(pd);
        mv.addObject("msg", "success");
        mv.setViewName("save_result");
        return mv;
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/tedit")
    public ModelAndView tedit() throws Exception {
        logBefore(logger, "修改商品");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
            return null;
        } //校验权限
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        taoCanService.edit(pd);
        mv.addObject("msg", "success");
        mv.setViewName("save_result");
        return mv;
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/redit")
    public ModelAndView redit() throws Exception {
        logBefore(logger, "修改研究院帖子");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
            return null;
        } //校验权限
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
//		pd.put("CONTENT", "");	//产品描述 默认为空
        //String DETAILS1= pd.getString("editorValue").toString().replace("localhost", "101.200.130.60");
        //String DETAILS=pd.getString("editorValue").toString().replace("src=\"", "src=\"http://read.html5.qq.com/image?src=forum&q=5&r=0&imgflag=7&imageUrl=");
        //pd.getString("editorValue").toString().replace("src=\"", "src=\"http://read.html5.qq.com/image?src=forum&q=5&r=0&imgflag=7&imageUrl=");
        //String DETAILS1= DETAILS.replace("http://read.html5.qq.com/image?src=forum&q=5&r=0&imgflag=7&imageUrl=http://read.html5.qq.com/image?src=forum&q=5&r=0&imgflag=7&imageUrl=", "http://read.html5.qq.com/image?src=forum&q=5&r=0&imgflag=7&imageUrl=");
        pd.put("MESSAGE", "<style>img{max-width:100%;}</style>" + pd.getString("editorValue").toString());
        researchService.edit(pd);
        String a[] = pd.getString("editorValue").toString().split("src");
        research_ImgService.delete(pd);
        if (a.length > 1) {
            PageData pd3 = new PageData();
            pd3.put("RESEARCH_ID", pd.getString("RESEARCH_ID"));
            pd3.put("IMG", a[1].substring(2, 95).toString().split("\"")[0]);
            pd3.put("ORDE_BY", 1);
            pd3.put("DATE", DateUtil.getTime());
            research_ImgService.save(pd3);
        }
        mv.addObject("msg", "success");
        mv.setViewName("save_result");
        return mv;
    }

    /**
     * 去新增页面
     */
    @RequestMapping(value = "/goAdd")
    public ModelAndView goAdd() {
        logBefore(logger, "去新增商品页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        try {
            List<PageData> list1 = product_typeService.findList(pd);
            List<PageData> list = gysService.findList(pd);
            mv.addObject("varList", list);
            mv.addObject("varList1", list1);
            mv.setViewName("system/product/product_edit");
            mv.addObject("msg", "save");
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }


    /**
     * 去修改活动页面
     */
    @RequestMapping(value = "/updateLgo")
    public ModelAndView updateLgo() {
        logBefore(logger, "去修改活动类型页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = null;
        try {
            pd = this.getPageData();
            PageData pds = researchService.findById(pd);
            PageData prod = productService.findById(pds);
            List<PageData> imgs =  research_ImgService.findHuoDongImg(pds);
            String str ="<p>"+pds.getString("JIANJIE")+"<img src="+prod.getString("GGIMG")+" title=\"touxiaoxiao.png\"/></p>";
                if(imgs.size()>0){
                    if(null != imgs.get(0).getString("IMG")){
                        pds.put("IMG1",imgs.get(0).getString("IMG"));
                    }
                }
            pds.put("evalues",str);
            mv.addObject("pd", pds);
            mv.setViewName("system/product/product_updateL");
            mv.addObject("msg", "updateL");
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    /**
     * 去新增页面
     */
    @RequestMapping(value = "/updateL")
    public ModelAndView updateL(PrintWriter out) {
        logBefore(logger, "修改活动类型页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            String str = DateUtil.delHTMLTag(pd.getString("editorValue"));
            String DETAILS1 = str.replace("\r", "");
            String DETAILS2 = DETAILS1.replace("\n", "");
            String DETAILS3 = DETAILS2.replace("&nbsp;", "");
            pd.put("JIANJIE", DETAILS3);
            String a[] = pd.getString("editorValue").toString().split("src");
            if (a.length > 1) {
                pd.put("GGIMG", a[1].substring(2, 95).toString().split("\"")[0]);
            }
            String ind = pd.getString("LSTATUS");
            PageData prod = researchService.findById(pd);
            if (null != prod) {
                if ("0".equals(ind)) {
                    pd.put("LSTATUS", "0");
                    if ("1".equals(prod.get("LSTATUS").toString())) {
                        pd.put("JIANJIE", "");
                        pd.put("GGIMG", "");
                    }
                    productService.edtiHuoDong(pd);
                    researchService.editHuoDong(pd);
                    if ("2".equals(prod.get("LSTATUS").toString())) {
                        research_ImgService.deleteHuoDong(pd);
                    }
                } else if ("1".equals(ind)) {
                    pd.put("LSTATUS", "1");
                    pd.put("PRODUCT_ID", researchService.findById(pd).get("PRODUCT_ID").toString());
                    researchService.updataSHuoDong(pd);
                    productService.edtiHuoDong(pd);
                    researchService.editHuoDong(pd);
                    if ("2".equals(prod.get("LSTATUS").toString())) {
                        research_ImgService.deleteHuoDong(pd);
                    }
                } else if ("2".equals(ind)) {
                    pd.put("LSTATUS", "2");
                    pd.put("ORDE_BY", "100");
                    pd.put("DATE", DateUtil.getTime());
                    pd.put("PRODUCT_ID", researchService.findById(pd).get("PRODUCT_ID").toString());
                    productService.edtiHuoDong(pd);
                    researchService.editHuoDong(pd);
                    researchService.updataBHuoDong(pd);
                    if (null != research_ImgService.findHuoDongImg(pd)&& research_ImgService.findHuoDongImg(pd).size()>0) {
                        research_ImgService.editHuoDongImg(pd);
                    } else {
                        research_ImgService.save(pd);
                    }
                }
            }
            /*PageData pds = remark.findById(pd);*/
            mv.addObject("msg", "success");
            mv.setViewName("save_result");
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    /**
     * 去回复list
     */
    @RequestMapping(value = "/goComR")
    public ModelAndView goComR() {
        logBefore(logger, "去回复页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            mv.setViewName("system/product/product_showRes_save");
            mv.addObject("pd", pd);
            mv.addObject("msg", "save");
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    /**
     * 去新增页面
     */
    @RequestMapping(value = "/goTAdd")
    public ModelAndView goTAdd() {
        logBefore(logger, "去新增套餐商品页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            pd.put("STATUS", "0");
            List<PageData> list = productService.findList(pd);
            mv.addObject("varList", list);
            mv.setViewName("system/product/product_tshow_edit");
            mv.addObject("msg", "tsave");
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    /**
     * 去新增页面
     */
    @RequestMapping(value = "/goZComRs")
    public ModelAndView goZComRs(HttpSession session) {
        logBefore(logger, "去主评论下");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        try {
            if (null == pd.get("RESEARCH_ID") || "" == pd.get("RESEARCH_ID")) {
                pd.put("RESEARCH_ID", session.getAttribute("RESEARCH_ID"));
            }
            pd = this.getPageData();
            mv.setViewName("system/product/product_ZComRs_save");
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    /**
     * 去新增页面
     */
    @RequestMapping(value = "/goRAdd")
    public ModelAndView goRAdd() {
        logBefore(logger, "去新增研究院帖子");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            List<PageData> list = productService.findList(pd);
            mv.addObject("varList", list);
            mv.setViewName("system/product/product_rshow_edit");
            mv.addObject("msg", "rsave");
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
            List<PageData> list1 = product_typeService.findList(pd);
            List<PageData> list = gysService.findList(pd);
            mv.addObject("varList", list);
            pd = productService.findById(pd);          //根据ID读取
            mv.setViewName("system/product/product_edit");
            mv.addObject("msg", "edit");
            mv.addObject("varList", list);
            mv.addObject("varList1", list1);
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    /**
     * 去修改页面
     */
    @RequestMapping(value = "/goTEdit")
    public ModelAndView goTEdit() {
        logBefore(logger, "去修改商品页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            pd.put("STATUS", "0");
            List<PageData> list = productService.findList(pd);
            pd = taoCanService.findById(pd);          //根据ID读取
            mv.setViewName("system/product/product_tshow_edit");
            mv.addObject("msg", "tedit");
            mv.addObject("varList", list);
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    /**
     * 去研究院主评论下的副评论
     * 这个地方的RESEARCH_ID是不可能为空的
     */
    @RequestMapping(value = "/goLookRes")
    public ModelAndView goLookRes(Page page, HttpServletRequest request) {
        logBefore(logger, "去查看评论页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();

        //==============================begin
        String RESEARCH_ID = pd.getString("RESEARCH_ID");
        if (null != RESEARCH_ID && !"".equals(RESEARCH_ID)) {
            RESEARCH_ID = RESEARCH_ID.trim();
            pd.put("RESEARCH_ID", RESEARCH_ID);
            request.getSession().setAttribute("RESEARCH_ID", RESEARCH_ID);
        } else {
            pd.put("RESEARCH_ID", request.getSession().getAttribute("RESEARCH_ID"));
            request.getSession().removeAttribute("RESEARCH_ID");
        }
        //==============================end
        page.setShowCount(10);
        page.setPd(pd);
        try {
            List<PageData> list = comment_researchService.findCommentsByResId(page);
            request.getSession().setAttribute("RESEARCH_ID", pd.getString("RESEARCH_ID"));
            mv.setViewName("system/product/product_showRes_list");
            mv.addObject("msg", "show");
            mv.addObject("varList", list);
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }


    /**
     * 去修改页面
     */
    @RequestMapping(value = "/goREdit")
    public ModelAndView goREdit() {
        logBefore(logger, "去修改研究院帖子");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            pd = researchService.findById(pd);          //根据ID读取
            mv.setViewName("system/product/product_rshow_edit");
            mv.addObject("msg", "redit");
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
            List<PageData> list1 = product_typeService.findList(pd);
            List<PageData> varList1 = gysService.findList(pd);
            List<PageData> varList = productService.list(page);        //列出Pro_Info列表
            for (PageData var : varList) {
                PageData pa = remark.selectCount(var);
                var.remove("KUCUN");
                var.put("KUCUN", pa.get("KUCUN").toString());
            }
            mv.setViewName("system/product/product_list");
            mv.addObject("varList", varList);
            mv.addObject("varList1", varList1);
            mv.addObject("varList2", list1);
            mv.addObject("pd", pd);
            mv.addObject(Const.SESSION_QX, this.getHC());          //按钮权限
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
    public Object deleteAll() {
        logBefore(logger, "批量删除Pro_Info");
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
                productService.deleteAll(ArrayDATA_IDS);
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
     * 批量研究院下的所有评论删除
     */
    @RequestMapping(value = "/comRsdeleteAll")
    @ResponseBody
    public Object comRsdeleteAll() {
        logBefore(logger, "批量删除com_research");
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
                PageData dto =null;
                for(int i=0;i<ArrayDATA_IDS.length;i++){
                    dto= new PageData();
                    dto.put("COMMENT_RESEARCH_ID",ArrayDATA_IDS[i]);
                    dto=comment_researchService.findById(dto);
                    if("0".equals(dto.getString("PID")) || "".equals(dto.getString("PID"))){
                        PageData pageData = researchService.findById(dto);
                        int hui = Integer.valueOf(pageData.get("HUIFU").toString());
                        if(hui>0){
                            researchService.editHuiFuJian(pageData);
                        }
                    }
                }
                comment_researchService.deleteAll(ArrayDATA_IDS);
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


    public void execute(){
        System.out.println("---------------------");
        PageData pd=new PageData();
        try {
            zan_CommentResearchYoukeService.deletes(pd);
            zan_CommentResearchService.deletes(pd);
        }catch (Exception e){
            e.printStackTrace();
        }
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
