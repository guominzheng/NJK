package com.fh.controller.system.postaudit;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.system.appuser.AppuserService;
import com.fh.service.system.count_community.Count_communityService;
import com.fh.service.system.product.ProductService;
import com.fh.service.system.research.ResearchService;
import com.fh.service.system.tiwen.TiWenService;
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
@RequestMapping(value = "/postAudit", produces = "text/html;charset=UTF-8")
public class postauditController extends BaseController {
    @Resource
    private TiWenService tiWenService;
    @Resource
    private ResearchService researchService;
    @Resource
    private Count_communityService countCommunityService;
    @Resource
    private ProductService productService;
    @Resource
    private AppuserService appuserService;


    @RequestMapping(value = "/list")
    public ModelAndView list(Page page) {
        logBefore(logger, "查询未审查帖子");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            if (null == pd.getString("pro")) {
                pd.put("pro", "0");
            }
            System.out.println(pd.getString("SHENCHA"));
            List<PageData> varList = null;
            page.setPd(pd);
            page.setShowCount(10);
            if ("0".equals(pd.getString("pro"))) {
                varList = researchService.AlllistPage(page);
            } else {
                varList = tiWenService.datalistPage(page);
            }
            for (PageData var : varList) {
                String str = DateUtil.delHTMLTag(var.getString("MESSAGE"));
                String DETAILS1 = str.replace("\r", "");
                String DETAILS2 = DETAILS1.replace("\n", "");
                String DETAILS3 = DETAILS2.replace("&nbsp;", "");
                if ("".equals(DETAILS3)) {
                    var.put("MESSAGE", "无详细文字描述 ......");
                } else {
                    if (DETAILS3.length() > 40) {
                        String tr = DETAILS3.substring(0, 40);
                        var.put("MESSAGE", tr + "&nbsp;...");
                    } else {
                        var.put("MESSAGE", DETAILS3);
                    }
                }
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date date = format.parse(DateUtil.getTime());
                Date dataDate = format.parse(var.getString("DATE"));
                int days = (int) ((date.getTime() - dataDate.getTime()) / (1000 * 3600 * 24));
                if (days <= 1) {
                    var.put("dateF", "1");
                }
            }
            mv.setViewName("system/postaudit/postaudit_list");
            mv.addObject("pd", pd);
            mv.addObject("varList", varList);
            mv.addObject("page", page);
            mv.addObject(Const.SESSION_QX, this.getHC());          //按钮权限
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    /**
     * 批量修改研究院
     *
     * @return
     */
    @RequestMapping(value = "/updateAll.do")
    @ResponseBody
    public Object comRsdeleteAll() {
        logBefore(logger, "批量修改SHENCHA");
        PageData pd = new PageData();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            pd = this.getPageData();
            List<PageData> pdList = new ArrayList<PageData>();
            String DATA_IDS = pd.getString("DATA_IDS");
            if (null != DATA_IDS && !"".equals(DATA_IDS)) {
                String ArrayDATA_IDS[] = DATA_IDS.split(",");
                if ("0".equals(pd.getString("pro"))) {
                    researchService.updateAll(ArrayDATA_IDS);
                } else {
                    tiWenService.updateAll(ArrayDATA_IDS);
                }
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
     * 修改
     */
    @RequestMapping(value = "/edit")
    public ModelAndView edit() throws Exception {
        logBefore(logger, "修改SHENCHA");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        if ("0".equals(pd.getString("pro"))) {
            pd.put("RESEARCH_ID", pd.getString("id"));
            researchService.editSHENCHA(pd);
            PageData reser = researchService.findById(pd);
            pd.put("USER_ID", reser.get("USER_ID").toString());
            //*****************增加用户积分*****************
            String today = DateUtil.getDay();
            PageData comDto = new PageData();
            comDto.put("community_type", "3");
            comDto.put("create_date", today);
            comDto.put("USER_ID", reser.get("USER_ID").toString());
            PageData product1 = productService.findById(pd);
            pd.put("JIFEN", 2);
            appuserService.editJifen(pd);
            comDto.put("NUM", "2");
            comDto.put("community_id", pd.get("RESEARCH_ID").toString());
            comDto.put("MIAOSHU", "审核" + product1.getString("PRODUCT_NAME") + "商品研究院帖子位为精华帖");
            comDto.put("ORDER_NUM", "");
            countCommunityService.save(comDto);
            //*****************end********************
        } else {
            pd.put("TIWEN_ID", pd.getString("id"));
            tiWenService.editSHENCHA(pd);
            /*PageData reser = tiWenService.findById(pd);
            pd.put("USER_ID",reser.get("USER_ID").toString());
            //*****************增加用户积分*****************
            String today = DateUtil.getDay();
            PageData comDto = new PageData();
            comDto.put("community_type", "2");
            comDto.put("create_date", today);
            comDto.put("USER_ID", reser.get("USER_ID").toString());
            PageData flag = countCommunityService.findById(comDto);
            if (null == flag) {
                pd.put("JIFEN", 2);
                appuserService.editJifen(pd);
                comDto.put("NUM", "2");
                comDto.put("community_id", pd.get("TIWEN_ID").toString());
                comDto.put("MIAOSHU", "发布提问");
                comDto.put("ORDER_NUM", "");
                countCommunityService.save(comDto);
            }
            //*****************end********************/
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
        logBefore(logger, "删除帖子或者研究院");
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            if ("0".equals(pd.getString("pro"))) {
                pd.put("RESEARCH_ID", pd.getString("id"));
                researchService.delete(pd);
            } else {
                pd.put("TIWEN_ID", pd.getString("id"));
                tiWenService.delete(pd);
            }
            out.write("success");
            out.close();
        } catch (Exception e) {
            logger.error(e.toString(), e);
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
