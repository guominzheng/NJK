package com.fh.controller.system.Integration;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.system.IntegrationService.IntegrationService;
import com.fh.service.system.appuser.AppuserService;
import com.fh.service.system.count_community.Count_communityService;
import com.fh.util.DateUtil;
import com.fh.util.PageData;
import com.fh.util.SmsBao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value="integration",produces="text/html;charset=UTF-8")
public class IntegrationController extends BaseController {

    @Autowired
    private AppuserService appuserService;
    @Autowired
    private IntegrationService integrationService;
    @Autowired
    private Count_communityService countCommunityService;

    @RequestMapping(value="/list",produces="text/html;charset=UTF-8")
    public String list(HttpServletRequest request,Page page){
        logBefore(logger, "积分管理的LIST------------------>");
        PageData pd= null;
        try{
            pd = this.getPageData();
            page.setPd(pd);
            page.setShowCount(10);
            request.setAttribute("page",page);
            request.setAttribute("pd",pd);
            request.setAttribute("varList",integrationService.getAllData(page));
        }catch(Exception e){
            e.printStackTrace();
        }
        return "system/Integration/list";
    }
    @RequestMapping(value="/after_save",produces="text/html;charset=UTF-8")
    public String after_save(HttpServletRequest request){
        request.setAttribute("msg","save");
        return "system/Integration/edit";
    }

    @RequestMapping(value="/after_update",produces="text/html;charset=UTF-8")
    public String after_update(HttpServletRequest request){
        PageData pd= null;
        try{
            pd = this.getPageData();
            request.setAttribute("pd",integrationService.findDataById(pd));
            request.setAttribute("msg","update");
        }catch (Exception e){
            e.printStackTrace();
        }
        return "system/Integration/edit";
    }

    @RequestMapping(value="/update",produces="text/html;charset=UTF-8")
    public String update(){
        logBefore(logger, "积分管理的UPDATE------------------>");
        PageData pd= null;
        try{
            pd = this.getPageData();
            String money = pd.getString("money");
            int Jif = getJiFen(Double.valueOf(money));
            pd.put("JIFEN",Jif);
            integrationService.update(pd);
        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }
        return "success";
    }

    @RequestMapping(value="/delete",produces="text/html;charset=UTF-8")
    public String delete(){
        logBefore(logger, "积分管理的DELETE------------------>");
        PageData pd= null;
        try{
            pd = this.getPageData();
            integrationService.delete(pd);
        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }
        return "success";
    }

    @RequestMapping(value="/save",produces="text/html;charset=UTF-8")
    public String save(){
        logBefore(logger, "积分管理的SAVE------------------>");
        PageData pd= null;
        try{
            synchronized(IntegrationController.class){
                pd = this.getPageData();
                String money = pd.getString("money");
                PageData user = appuserService.findName(pd);
                pd.put("USER_ID",user.getString("USER_ID"));
                int JIF = getJiFen(Double.valueOf(money));
                pd.put("JIFEN", JIF);
                appuserService.editJifen(pd);
                appuserService.addZJIFEN(pd);
                PageData comDto = new PageData();
                comDto.put("community_type", "4");
                comDto.put("create_date", DateUtil.getTime());
                comDto.put("USER_ID", user.get("USER_ID").toString());
                comDto.put("community_id", "7");
                comDto.put("NUM", JIF);
                comDto.put("MIAOSHU", "10月份冬储线下活动积分");
                comDto.put("ORDER_NUM", "");
                countCommunityService.save(comDto);
                String str = "猫友您好，您参加10月份冬储活动线下已付商品金额所积攒的"+JIF+"积分已经添加到农极客app积分商城，请登录商城查看积分，详情请咨询您的专属客服";
                SmsBao sms  = new SmsBao();
                sms.sendSMS(pd.getString("USERNAME"),str);
                PageData dtoInte=new PageData();
                dtoInte.put("USER_ID",user.getString("USER_ID"));
                dtoInte.put("money",money);
                dtoInte.put("JIFEN",JIF);
                dtoInte.put("USERNAME",pd.getString("USERNAME"));
                dtoInte.put("NAME",user.getString("NAME"));
                dtoInte.put("EXCLUName",user.getString("ENAME"));
                dtoInte.put("createTime", DateUtil.getTime());
                dtoInte.put("text", pd.getString("text"));
                integrationService.save(dtoInte);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return "save_result";
    }


    public int getJiFen(Double money){
        int JIF = 0;
        if (money < 5000) {
            JIF = (int) (money * 0.03);
        } else if (money >= 5000 && money < 10000) {
            JIF = (int) (money * 0.04);
        } else if (money >= 10000) {
            JIF = (int) (money * 0.05);
        }
        return JIF;
    }
}
