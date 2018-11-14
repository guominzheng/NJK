package com.fh.util;

import com.fh.service.system.appuser.AppuserService;

import java.util.List;

public class SendSmsThread implements Runnable{

    private List<PageData> pdList;
    private AppuserService appuserService;

    public List<PageData> getPdList() {
        return pdList;
    }

    public void setPdList(List<PageData> pdList) {
        this.pdList = pdList;
    }

    public AppuserService getAppuserService() {
        return appuserService;
    }

    public void setAppuserService(AppuserService appuserService) {
        this.appuserService = appuserService;
    }

    public SendSmsThread(List<PageData> pdList, AppuserService appuserService) {
        this.appuserService = appuserService;
        this.pdList = pdList;
    }

    public void run() {
        PageData user = null;
        SmsBao smsBao = new SmsBao();
       /* try{
            smsBao.sendSMS(phone,"短信来了！");
        }catch (Exception e){
            e.printStackTrace();
        }*/
        for(PageData pd :getPdList()){
            try{
                user = getAppuserService().findById(pd);
                if(user != null){
                    smsBao.sendSMS(user.get("USERNAME").toString(),"【农极客】“发钱了！”\n" +
                            "尊敬的农极客VIP用户，为了感谢猫友一直以来对农极客的支持，“国庆代金券大放送”，现在赠送您一张50元代金券！\n" +
                            "该代金券，农极客商城所有产品通用，无门槛，有效期6个月，想啥时候用就啥时候用~\n" +
                            "点击链接 http://a.app.qq.com/o/simple.jsp?pkgname=com.ylsoft.njk  即可进入农极客APP查看详情。");
                }
            }catch (Exception e){
                e.printStackTrace();
                continue;
            }
        }
    }
}
