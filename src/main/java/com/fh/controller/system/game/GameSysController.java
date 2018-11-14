package com.fh.controller.system.game;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.gameService.GameBidRecordService;
import com.fh.service.gameService.GameCouponService;
import com.fh.service.gameService.GamePrizeService;
import com.fh.service.system.address.AddressService;
import com.fh.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.util.List;

@Controller
@RequestMapping("/gameSysContr")
public class GameSysController extends BaseController {
    String menuUrl = "coupon/list.do"; //菜单地址(权限用)
    @Autowired
    private GameBidRecordService gameBidRecordService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private GameCouponService gameCouponService;
    @Autowired
    private GamePrizeService gamePrizeService;


    @RequestMapping(value = "/list.do", produces = "text/html;charset=utf-8")
    public String list(HttpServletRequest request, Page page) {
        logBefore(logger, "=======================》中奖列表");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) return null; //判断当前用户是否有权限查询内容
        PageData pd = null;
        List<PageData> pdList = null;
        List<PageData> prizeList=null;
        try {
            pd = this.getPageData();
            if(null != pd.getString("lastEndStart")){
                if(pd.getString("lastEndStart").trim().equals("")){
                    pd.remove("lastEndStart");
                }
            }
            if(null != pd.getString("prizeName")){
                if(pd.getString("prizeName").trim().equals("")){
                    pd.remove("prizeName");
                }
            }
            if(null != pd.getString("lastLoginStart")){
                if(pd.getString("lastLoginStart").trim().equals("")){
                    pd.remove("lastLoginStart");
                }
            }
            page.setPd(pd);
            page.setShowCount(10);
            PageData add = null;
            prizeList=gamePrizeService.getAllPrize(new PageData());
            pdList = gameBidRecordService.findPrizeByUser(page);
            if (pdList != null && pdList.size() > 0) {
                for (int i = 0; i < pdList.size(); i++) {
                    add = addressService.findDefault(pdList.get(i));
                    if (add != null && add.size() > 0) {
                        pdList.get(i).put("address", add.getString("CITY") + add.getString("ADDRESS"));
                    }
                }
            }
            request.setAttribute("varList", pdList);
            request.setAttribute("prizeList", prizeList);
            request.setAttribute("page", page);
            request.setAttribute("pd", pd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "system/game/luckList";
    }

    @RequestMapping(value = "/give", produces = "text/html;charset=utf-8")
    public String give(HttpServletRequest request) {
        logBefore(logger, "=======================》发放奖励");
        PageData pd = null;
        try {
            pd = this.getPageData();
            pd.put("status", "1");
            gameBidRecordService.update(pd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }


    @RequestMapping(value = "/giveAll", produces = "text/html;charset=utf-8")
    public String giveAll() {
        logBefore(logger, "=======================》发放奖励All");
        PageData pd = null;
        PageData ad = null;
        SmsBao sms = new SmsBao();
        try {
            pd = this.getPageData();
            pd.put("status", "1");
            pd.put("giveTime", DateUtil.getTime());
            String DATA_IDS = pd.getString("DATA_IDS");
            if (null != DATA_IDS && !"".equals(DATA_IDS)) {
                String ArrayDATA_IDS[] = DATA_IDS.split(",");
                for (int i = 0; i < ArrayDATA_IDS.length; i++) {
                    pd.put("bidId", ArrayDATA_IDS[i]);
                    gameBidRecordService.update(pd);
                    ad = gameBidRecordService.findPrizeById(pd);
                    ad.put("STATUS","1");
                    gameCouponService.update(pd);
                    sms.sendSMS(ad.getString("phone"),"您参加的国庆七天乐，十万豪礼大发送，获取的"+ad.getString("prizeName")+"已寄出，请注意查收！");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }
}
