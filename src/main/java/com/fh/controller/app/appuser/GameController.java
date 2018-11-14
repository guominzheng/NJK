package com.fh.controller.app.appuser;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.gameService.*;
import com.fh.service.system.appuser.AppuserService;
import com.fh.service.system.appuserInfo.AppUserInfoService;
import com.fh.service.system.coupon.CouponService;
import com.fh.service.system.yzm.YzmService;
import com.fh.util.DateUtil;
import com.fh.util.MD5;
import com.fh.util.PageData;
import com.fh.util.SmsBao;
import com.fh.util.drawUtil.Gift;
import com.fh.util.drawUtil.LotteryUtil;
import com.sun.javafx.collections.MappingChange;
import com.sun.xml.internal.ws.util.QNameMap;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("app/gameController")
public class GameController extends BaseController {

    @Autowired
    private AppuserService appuserService;
    @Autowired
    private GamePrizeService gamePrizeService;
    @Autowired
    private GameCountService gameCountService;
    @Autowired
    private GameRankService gameRankService;
    @Autowired
    private GameUserService gameUserService;
    @Autowired
    private GameBidRecordService gameBidRecordService;
    @Autowired
    private AppUserInfoService appUserInfoService;
    @Autowired
    private GameInviteService gameInviteService;
    @Autowired
    private YzmService yzmService;
    @Autowired
    private GameLuckService gameLuckService;
    @Autowired
    private CouponService couponService;
    @Autowired
    private GameCouponService gameCouponService;

    /**
     * +1 游戏游玩人数
     *
     * @return
     */
    @RequestMapping(value = "/saveCount", produces = "text/html;charset=utf-8")
    public synchronized String saveCount() {
        logger.info("============================>+1 游戏游玩人数");
        PageData pd = this.getPageData();
        java.util.Map map = new HashMap();
        try {
            pd.put("createTime", DateUtil.getTime());
            gameCountService.save(pd);
            map.put("code", "1");
            map.put("message", "正確返回數據！");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", "2");
            map.put("message", "程序出错请联系管理员！");
        }
        return JSONArray.toJSONString(map);
    }

    /**
     * 获取游戏游玩总人数
     *
     * @return
     */
    @RequestMapping(value = "/getCount", produces = "text/html;charset=utf-8")
    public String getCount() {
        logger.info("============================>获取游戏游玩总人数");
        java.util.Map map = new HashMap();
        try {
            PageData pd = gameCountService.getCount(this.getPageData());
            if (pd != null && pd.size() > 0) {
                map.put("code", "1");
                map.put("count", pd.get("count(1)").toString());
            } else {
                map.put("code", "2");
                map.put("message", "无数据！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", "2");
            map.put("message", "程序出错请联系管理员！");
        }
        return JSONArray.toJSONString(map);
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    @RequestMapping(value = "/getUserInfo", produces = "text/html;charset=utf-8")
    public String getUserInfo(String UNIONID) {
        logger.info("============================>尝试获取用户信息");
        PageData pd = this.getPageData();
        PageData user = null;
        java.util.Map<Object, Object> map = new HashMap();
        try {
            user = appuserService.findUnionId(pd);
            if (user != null && user.size() > 0) {
                PageData dtoUser = new PageData();
                dtoUser.put("userName", user.getString("NAME"));
                dtoUser.put("userImg", user.getString("IMG"));
                dtoUser.put("address", user.getString("ADDRESS"));
                dtoUser.put("phone", user.getString("USERNAME"));
                dtoUser.put("draw_count", "3");
                dtoUser.put("recommend", "0");
                dtoUser.put("createTime", DateUtil.getTime());
                gameUserService.save(dtoUser);
                PageData pp = new PageData();
                dtoUser.clear();
                dtoUser.put("phone", user.getString("USERNAME"));
                PageData returnpd = gameUserService.findUser(dtoUser);
                map.put("code", "1");
                map.put("user", returnpd);
            } else {
                map.put("code", "2");
                map.put("message", "无此用户信息！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", "2");
            map.put("message", "程序出错请联系管理员！");
        }

        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(map);
        } catch (JsonGenerationException e) {
            //
            e.printStackTrace();
        } catch (JsonMappingException e) {
            //
            e.printStackTrace();
        } catch (IOException e) {
            //
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 获取用户信息
     *
     * @param openId
     * @return
     */
    @RequestMapping(value = "/getUser", produces = "text/html;charset=utf-8")
    public String getUser(String openId) {
        logger.info("============================>获取用户信息");
        PageData pd = this.getPageData();
        PageData user = null;
        java.util.Map<Object, Object> map = new HashMap();
        try {
            user = gameUserService.findUser(pd);
            if (user != null && user.size() > 0) {
                map.put("code", "1");
                map.put("user", user);
            } else {
                map.put("code", "2");
                map.put("message", "无此用户信息！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", "2");
            map.put("message", "程序出错请联系管理员！");
        }

        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(map);
        } catch (JsonGenerationException e) {
            //
            e.printStackTrace();
        } catch (JsonMappingException e) {
            //
            e.printStackTrace();
        } catch (IOException e) {
            //
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 獲取排行榜
     *
     * @param openId 用戶Id
     * @return
     */
    @RequestMapping(value = "/getRankScore", produces = "text/html;charset=utf-8")
    public String getRankScore(String openId, Integer pageNum) {
        logger.info("============================>獲取排行榜");
        java.util.Map map = new HashMap();
        List<PageData> returnList = new ArrayList<PageData>();
        PageData returnDate = new PageData();
        List<PageData> invList = new ArrayList<PageData>();
        try {
            List<PageData> paList = gameRankService.getAll(this.getPageData());
            if (paList != null && paList.size() > 0) {
                int index = 0;
                if (paList.size() < 20) {
                    index = paList.size();
                } else {
                    index = 20;
                }
                for (int i = 0; i < index; i++) {
                    PageData dtoPd = paList.get(i);
                    returnList.add(dtoPd);
                    if (dtoPd.get("openId").toString().equals(openId)) {
                        returnDate = dtoPd;
                        invList = gameInviteService.findInviteByOpenId(returnDate);
                    }
                }
            }
            map.put("code", "1");
            map.put("list", returnList);
            map.put("user", returnDate);
            map.put("invList", invList);
            map.put("message", "正確返回數據！");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", "2");
            map.put("message", "程序出错请联系管理员!");
        }
        return JsonMapper(map);
    }

    /**
     * 修改用户积分，如果不存在则自动添加一条数据
     * param userId
     *
     * @return
     */
    @RequestMapping(value = "/modifyScore", produces = "text/html;charset=utf-8")
    public synchronized String modifyScore(String openId, String score) {
        logger.info("============================>修改排行榜分数");
        java.util.Map map = new HashMap();
        PageData dtoRank = null;
        PageData user = null;
        PageData pds = null;
        try {
            pds = this.getPageData();
            user = gameUserService.findUser(pds);
            Integer draw_count = Integer.parseInt(user.get("draw_count").toString());
            if (draw_count <= 0) {
                map.put("code", "2");
                map.put("message", "已无游戏次数！");
                return JsonMapper(map);
            }
            dtoRank = gameRankService.findRankByUserId(this.getPageData());
            if (dtoRank != null && dtoRank.size() > 0) {
                String scores = dtoRank.get("score").toString();
                if (Integer.parseInt(score) > Integer.parseInt(scores)) {
                    gameRankService.update(this.getPageData());
                }
            } else {
                PageData pd = new PageData();
                pd.put("openId", openId);
                pd.put("score", score);
                pd.put("createTime", DateUtil.getTime());
                gameRankService.save(pd);
            }
            draw_count--;
            PageData dtouser = new PageData();
            dtouser.put("draw_count", draw_count);
            dtouser.put("openId", openId);
            gameUserService.update(dtouser);
            List<PageData> paList = gameRankService.getAll(this.getPageData());
            if (paList != null && paList.size() > 0) {
                for (int i = 0; i < paList.size(); i++) {
                    PageData dtoPd = paList.get(i);
                    if (dtoPd.get("openId").toString().equals(openId)) {
                        user = dtoPd;
                    }
                }
            }
            user.put("win", getRowNo(Integer.parseInt(user.get("rowno").toString()), gameRankService.getAll(this.getPageData()).size()) + "%");
            map.put("code", "1");
            map.put("data", user);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", "2");
            map.put("message", "程序出错，请联系管理员！");
        }
        return JsonMapper(map);
    }

    /**
     * 抽奖
     * openId
     *
     * @return
     */
    @RequestMapping(value = "/drawing", produces = "text/html;charset=utf-8")
    public synchronized String drawing() {
        logger.info("============================>抽奖ing");
        List<Gift> gifts = new ArrayList<Gift>();
        java.util.Map map = new HashMap();
        PageData pd = null;
        PageData user = null;
        Gift reGift = null;
        try {
            pd = this.getPageData();
            List<PageData> pdList = gamePrizeService.getAllPrize(this.getPageData());
            if (pdList != null && pdList.size() > 0) {
                for (int i = 0; i < pdList.size(); i++) {
                    Integer id = Integer.parseInt(pdList.get(i).get("prizeId").toString());
                    Integer count = Integer.parseInt(pdList.get(i).get("count").toString());
                    if (id == 1 && count >= 100) {
                        continue;
                    }
                    if (id == 2 && count >= 90) {
                        continue;
                    }
                    if (id == 4 && count >= 50) {
                        continue;
                    }
                    if (id == 5 && count >= 50) {
                        continue;
                    }
                    if (id == 3 && count >= 10) {
                        continue;
                    }
                    Double probability = Double.valueOf(pdList.get(i).get("probability").toString());
                    String prizeName = pdList.get(i).getString("prizeName");
                    String prizeImg = pdList.get(i).getString("prizeImg");
                    String couponImg = pdList.get(i).getString("couponImg");
                    Integer type = Integer.parseInt(pdList.get(i).get("type").toString());
                    gifts.add(new Gift((i + 1), id.toString(), prizeName, probability, prizeImg, type, couponImg));
                }
            }

            List<Double> orignalRates = new ArrayList<Double>(gifts.size());
            for (Gift gift : gifts) {
                double probability = gift.getProbability();
                if (probability < 0) {
                    probability = 0;
                }
                orignalRates.add(probability);
            }
            int orignalIndex = LotteryUtil.lottery(orignalRates);
            //java.util.Map<Integer, Integer> count = new HashMap<Integer, Integer>();
            // Integer value = count.get(orignalIndex);
            //count.put(orignalIndex, value == null ? 1 : value + 1);
            reGift = gifts.get(orignalIndex);
            pd.put("prizeId", reGift.getGitfId());
            gamePrizeService.update(pd);
            pd.put("createTime", DateUtil.getTime());
            pd.put("get_status", "0");
            pd.put("type", reGift.getType());
            pd.put("prizeName", reGift.getGiftName());
            pd.put("prizeImg", reGift.getGuftImg());
            pd.put("couponImg", reGift.getCouponImg());
            gameLuckService.save(pd);
            map.put("code", "1");
            map.put("data", reGift);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", "2");
            map.put("mssage", "程序出错请联系管理员！");
        }
        return JsonMapper(map);
    }

    /**
     * 转发累计
     * param openId openId_p
     *
     * @return
     */
    @RequestMapping(value = "/superposition", produces = "text/html;charset=utf-8")
    public synchronized String superposition(String openId, String openId_p) {
        logger.info("============================>转发累计");
        java.util.Map map = new HashMap();
        PageData pd = null;
        List<PageData> invList = null;
        try {
            if (openId.equals(openId_p)) {
                map.put("code", "2");
                map.put("message", "自己无法助力自己！");
                return JsonMapper(map);
            }
            pd = this.getPageData();
            /*PageData ps = gameUserService.updateRecommend(this.getPageData());
            Integer count = Integer.parseInt(ps.get("recommend").toString());*/
            invList = gameInviteService.findInviteByOpenId(pd);
            if (invList != null && invList.size() > 0) {
                for (int i = 0; i < invList.size(); i++) {
                    if (openId_p.equals(invList.get(i).get("openId_p").toString())) {
                        map.put("code", "2");
                        map.put("message", "您无法当天助力两次以上同一个人！");
                        return JsonMapper(map);
                    }
                }
            }
            Integer count = Integer.parseInt(gameInviteService.findInviteByUser(pd).get("count").toString());
            if (count >= 4) {
                map.put("code", "2");
                map.put("message", "好友目标已达成！");
                return JsonMapper(map);
            }
            pd.put("createTime", DateUtil.getTime());
            gameInviteService.save(pd);
            Integer count1 = Integer.parseInt(gameInviteService.findInviteByUser(pd).get("count").toString());
            if (count1 == 3) {
                PageData user = gameUserService.findUser(pd);
                String recommend = user.get("recommend").toString();
                if (recommend.equals("0")) {
                    pd.put("recommend", "1");
                    gameUserService.updateDraw(pd);
                    map.put("code", "1");
                    map.put("message", "您的好友成功获得一次游戏机会！");
                    SmsBao sms = new SmsBao();
                    PageData aaaa = new PageData();
                    aaaa.put("OPENID", pd.getString("openId"));
                    PageData users = appuserService.findOpenId(aaaa);
                    if (null != users && users.size() > 0) {
                        sms.sendSMS(users.get("USERNAME").toString(), "恭喜您，通过好友助力，复活成功！成功获赠1次“国庆农资人下地记”游戏机会，可以直接进入“每天农资”微信公众号底部菜单栏，点击参与游戏，赢取10万好礼！");
                    }
                    return JsonMapper(map);
                }
            }
            map.put("code", "1");
            map.put("message", "助力成功！");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", "2");
            map.put("message", "程序出错，请联系管理员！");
        }
        return JsonMapper(map);
    }

    @RequestMapping(value = "/findInviteByOpenId", produces = "text/html;charset=utf-8")
    public String findInviteByOpenId() {
        logger.info("============================>获取助力列表");
        java.util.Map map = new HashMap();
        PageData pd = null;
        PageData user = null;
        try {
            pd = this.getPageData();
            List<PageData> pdList = gameInviteService.findInviteByOpenId(pd);
            user = gameUserService.findUser(pd);
            map.put("code", "1");
            map.put("list", pdList);
            map.put("user", user);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", "2");
            map.put("message", "程序出错，请联系管理员！");
        }
        return JsonMapper(map);
    }

    /**
     * 添加中奖记录
     * param userId prizeId phone address
     *
     * @return
     */
    @RequestMapping(value = "/saveBidRecord", produces = "text/html;charset=utf-8")
    public synchronized String saveBidRecord(String phone, String img, String name, String openId) {
        logger.info("============================>添加中奖记录");
        java.util.Map map = new HashMap();
        PageData pd = this.getPageData();
        try {
            pd.put("createTime", DateUtil.getTime());
            gameBidRecordService.save(pd);
            map.put("code", "1");
            map.put("message", "正确返回数据！");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", "2");
            map.put("message", "程序出错，请联系管理员！");
        }
        return JSONArray.toJSONString(map);
    }

    /**
     * userName userImg openId
     *
     * @return
     */
    @RequestMapping(value = "/register", produces = "text/html;charset=utf-8")
    public synchronized String register() {
        logger.info("============================>注册游戏");
        java.util.Map map = new HashMap();
        PageData pd = this.getPageData();
        PageData user = new PageData();
        try {
            user = gameUserService.findUser(pd);
            if (user == null || user.size() <= 0) {
                pd.put("phone", "");
                pd.put("address", "");
                pd.put("draw_count", "3");
                pd.put("recommend", "0");
                pd.put("createTime", DateUtil.getTime());
                gameUserService.save(pd);
            }
            map.put("code", "1");
            map.put("message", "正确返回数据！");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", "2");
            map.put("message", "程序出错，请联系管理员！");
        }
        return JsonMapper(map);
    }


    /**
     * 注册农极客
     *
     * @return
     */
    @RequestMapping(value = "/registerNJK", produces = "text/html;charset=utf-8")
    public synchronized String registerNJK(String UNIONID, String nickname, String sex, String city, String openid,
                                           String headImageUrl, String USERNAME, String PASSWORD, String YZM) {
        logger.info("============================>注册农极客");
        java.util.Map map = new HashMap();
        PageData pd = this.getPageData();
        String code = "";
        String message = "";
        try {
            PageData pd1 = appuserService.findName(pd);
            PageData pd_y = yzmService.findByPhone(pd);
            if (pd_y != null) {
                if (pd_y.getString("YZM").equals(YZM)) {
                    SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    long TIME = sdfTime.parse(DateUtil.getTime()).getTime();
                    long ENDTIME = sdfTime.parse(pd_y.getString("ENDTIME")).getTime();
                    if (TIME < ENDTIME) {
                        if (pd1 == null) {
                            String yzm = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
                            pd.put("USER_ID", this.get32UUID());
                            pd.put("USERNAME", USERNAME);
                            pd.put("DATE", DateUtil.getTime());// 注册时间
                            pd.put("PASSWORD", MD5.md5(MD5.md5(PASSWORD) + yzm));
                            pd.put("ROLE_ID", "77"); // 角色
                            pd.put("NAME", nickname);
                            pd.put("LAST_LOGIN", "");
                            pd.put("IP", ""); // IP
                            pd.put("EMAIL", "");
                            pd.put("CHANNELID", "");
                            pd.put("SALT", yzm);
                            pd.put("EXCLU_ID", "5d18274e708c472ca679622f2c964ce0");
                            pd.put("CROP", "");
                            pd.put("PROVINCE", "");
                            pd.put("CITY", "");
                            pd.put("DISTRICT", "");
                            pd.put("CUSTOMER_NAME", "");
                            pd.put("PHONE", "");
                            pd.put("VIP", "0");
                            pd.put("STATUS", "0");
                            pd.put("ADDRESS", "");
                            pd.put("OPENID", openid);
                            pd.put("UNIONID", UNIONID);
                            pd.put("ZJIFEN", "0");
                            pd.put("JIFEN", "0");
                            pd.put("IMG", "");
                            pd.put("TYPE", "");
                            pd.put("PHONEADDRESS", "");
                            appuserService.save(pd);
                            PageData pd2 = new PageData();
                            pd2.put("SYS_APP_USERINFO_ID", this.get32UUID());
                            pd2.put("USER_ID", pd.getString("USER_ID"));
                            pd2.put("SEX", sex);
                            pd2.put("VIP", "0");
                            pd2.put("IMG", headImageUrl);
                            pd2.put("QQ", "");
                            pd2.put("NIAN1", "");
                            pd2.put("YUE", "");
                            pd2.put("RI", "");
                            pd2.put("ZHIYE", "");
                            pd2.put("ADDRESS", city);
                            pd2.put("PHONE", "");
                            pd2.put("QIANMING", "每天农资,祝你好心情!");
                            pd2.put("WEIXIN", "");
                            appUserInfoService.save(pd2);
                            SmsBao sms = new SmsBao();
                            String context = "您的农极客APP初始密码为【" + PASSWORD + "】,欢迎各大应用商店下载,学习更多农技专题.";
                            String result = sms.sendSMS(USERNAME, context);
                            map.put("NAME", nickname);
                            map.put("IMG", headImageUrl);
                            map.put("USER_ID", pd.getString("USER_ID"));
                            map.put("UNIONID", UNIONID);
                            code = "1";
                        } else {
                            code = "2";          //已存在改账号
                            message = "已存在该账号";
                            String opnid = pd1.getString("OPENID");
                            String unionid = pd1.getString("UNIONID");
                            PageData dto = new PageData();
                            if (null == opnid || "".equals(openid)) {
                                dto.put("OPENID", openid);
                            }
                            if (null == unionid || "".equals(unionid)) {
                                dto.put("UNIONID", unionid);
                            }
                            dto.put("USER_ID", pd1.get("USER_ID").toString());
                            appuserService.updateUserInfoGame(dto);
                        }
                    } else {
                        code = "2"; // 验证码过期
                        message = "验证码过期";
                    }

                } else {
                    code = "2"; // 验证码过期
                    message = "验证码不正确";
                }
            } else {
                code = "2"; // 验证码不存在
                message = "验证码不存在";


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("code", code);
        map.put("message", message);
        return JsonMapper(map);
    }


    @RequestMapping(value = "/getAllPrize", produces = "text/html;charset=utf-8")
    public String getAllPrize() {
        logger.info("============================>添加中奖记录");
        java.util.Map map = new HashMap();
        List<PageData> pdList = null;
        try {
            pdList = gamePrizeService.getAllPrize(this.getPageData());
            map.put("code", "1");
            map.put("list", pdList);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", "2");
            map.put("message", "程序出错，请联系管理员！");
        }
        return JsonMapper(map);
    }

    /**
     * 领取奖励
     *
     * @param USER_ID
     * @param openId
     * @return
     */
    @RequestMapping(value = "/saveCoupon", produces = "text/html;charset=utf-8")
    public synchronized String saveCoupon(String USER_ID, String openId) {
        logger.info("============================>添加优惠卷图片");
        java.util.Map<Object, Object> map = new HashMap();
        PageData pd = null;
        List<PageData> luckList = null;
        PageData dtoins = null;
        PageData dtoUpdate = null;
        PageData dtoBid = null;
        try {
            pd = this.getPageData();
            pd.put("get_status", "0");
            luckList = gameLuckService.getLuckList(pd);
            if (null != luckList && luckList.size() > 0) {
                for (int i = 0; i < luckList.size(); i++) {
                    dtoins = new PageData();
                    dtoins.put("USER_ID", USER_ID);
                    dtoins.put("prizeName", luckList.get(i).getString("prizeName"));
                    dtoins.put("prizeImg", luckList.get(i).getString("couponImg"));
                    dtoins.put("createTime", DateUtil.getTime());
                    dtoins.put("STATUS", "0");
                    dtoins.put("RESTATUS", "0");
                    dtoins.put("TYPE", luckList.get(i).get("type").toString());
                    gameCouponService.save(dtoins);
                    PageData dto = luckList.get(i);
                    dtoUpdate = new PageData();
                    dtoUpdate.put("luckId", dto.get("luckId").toString());
                    dtoUpdate.put("get_status", "1");
                    gameLuckService.update(dtoUpdate);
                    dtoBid = new PageData();
                    dtoBid.put("openId", openId);
                    dtoBid.put("prizeImg", dto.getString("prizeImg"));
                    dtoBid.put("prizeName", dto.getString("prizeName"));
                    dtoBid.put("status", "0");
                    dtoBid.put("giveTime", "00-00-00 00:00:00");
                    dtoBid.put("type", luckList.get(i).get("type").toString());
                    dtoBid.put("createTime", DateUtil.getTime());
                    dtoBid.put("USER_ID", USER_ID);
                    gameBidRecordService.save(dtoBid);
                }
                map.put("code", "1");
            } else {
                map.put("code", "2");
                map.put("message", "请勿重复领取奖品。");
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", "2");
            map.put("message", "程序出错请联系管理员！");
        }
        return JsonMapper(map);
    }

    /**
     * openId
     *
     * @return
     */
    @RequestMapping(value = "/findPrizeByUser", produces = "text/html;charset=utf-8")
    public String findPrizeByUser() {
        logger.info("============================>查询助力列表");
        java.util.Map map = new HashMap();
        List<PageData> pdList = null;
        PageData pd = null;
        try {
            pd = this.getPageData();
            pd.put("get_status", "1");
            pdList = gameLuckService.getLuckList(this.getPageData());
            map.put("code", "1");
            map.put("data", pdList);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", "2");
            map.put("message", "程序出错，请联系管理员！");
        }
        return JsonMapper(map);
    }

    /**
     * json toString
     *
     * @param object
     * @return
     */
    public static String JsonMapper(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(object);
        } catch (JsonGenerationException e) {

            e.printStackTrace();
        } catch (JsonMappingException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
        return str;
    }

    public static String JsonArray(Object object) {
        return JSONArray.toJSONString(object);
    }

    /**
     * 获取概率
     *
     * @param no
     * @param size
     * @return
     */
    public Integer getRowNo(Integer no, Integer size) {
        Integer rsl = null;
        if (no == 1) {
            return 99;
        }
        if (no == size) {
            return 9;
        }
        if (size == 0) {
            return 99;
        }
        Double a = Double.valueOf(no) / size;
        Double b = a * 100;
        rsl = 100 - ((new Double(b)).intValue());
        if (rsl == 100) {
            rsl = 99;
        }
        if (size < 300 && rsl > 50 && rsl < 90) {
            rsl = 90;
        }
        if (rsl < 10) {
            rsl = 9;
        }
        return rsl;
    }
}
