package com.fh.service.wx_classRoom;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.vo.HomePage;
import com.fh.util.*;
import com.fh.util.quratz.LoadTask;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HomePageService {
    @Resource(name = "daoSupport")
    private DaoSupport dao;

    public Map getFirstPage(Page page){
        HomePage homePage = new HomePage();
        Map<Object,Object> map = new HashMap();
        try{
            homePage.setBanners((List<PageData>) dao.findForList("ClassRoom_BannerMapper.findBannerList", null)); //获取头图list
            homePage.setLives((List<PageData>) dao.findForList("ClassRoom_LiveMapper.findLiveListlistPage", page)); //获取往期直播list
            homePage.setTypes((List<PageData>) dao.findForList("ClassRoom_TypeMapper.findAllType", null)); //获取直播类型
            homePage.setZixun(((List<PageData>) dao.findForList("ClassRoom_ZixunMapper.findAllZixunFirst", page.getPd())).get(0));//获取firstPage展示咨询
            try {
                homePage.setLive(((List<PageData>) dao.findForList("ClassRoom_LiveMapper.findLiveOneGo", page.getPd())).get(0));//获取现在正在直播
            }catch (IndexOutOfBoundsException  e){
                System.err.println("=====================》不存在正在直播的直播间");
            }
            map.put("code","1");
            map.put("data",homePage);
        }catch (Exception e){
            e.printStackTrace();
            map.put("code","2");
            map.put("message","is no!");
        }
        return map;
    }

    /**
     * 添加quratz表，以及预约表
     * @param sql
     * @param pd
     * @return
     */
    public Map saveSubs(String sql ,PageData pd){
        PageData subs = null;
        Map<Object,Object> map = new HashMap();
        try {
            subs = (PageData) dao.findForObject("ClassRoom_SubscribeMapper.getSubsCountById",pd);
            Integer num =Integer.parseInt(subs.get("count").toString());
            if(num>0){

                map.put("code","2");
                map.put("message","您已预定，无需再次预定！");
                return map;
            }
            dao.save("ClassRoom_QuratzMapper.save",pd);
        }catch (Exception e){
            e.printStackTrace();
            map.put("code","2");
            map.put("message","程序出错，请联系管理员！");
            return map;
        }
        pd.put("createTime",DateUtil.getTime());
        return ServiceUtil.serviceUpdate(sql,pd,dao,SqlCentre.SQL_TYPE_INSERT);
    }
}
