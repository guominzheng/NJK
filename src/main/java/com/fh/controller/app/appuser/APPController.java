package com.fh.controller.app.appuser;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.soap.Addressing;

import com.alibaba.fastjson.JSONArray;
import com.fh.service.gameService.GameCouponService;
import com.fh.service.system.apply_vip_wenda.Apply_Vip_WenService;
import com.fh.service.system.count_community.Count_communityService;
import com.fh.service.system.jifen_shop.Jifen_shopService;
import com.fh.service.system.pullblack.PullblackService;
import com.fh.service.system.user_WendaQuanxian.User_WendaQuanxianService;
import com.fh.service.system.user_daily.User_DailyService;
import com.fh.service.system.zan_comment_leaving_message.Zan_Comment_leaving_MessageService;
import com.fh.service.system.zan_comment_research_youke.Zan_CommentResearchYoukeService;
import com.fh.service.system.zan_leaving_messageService.Zan_leaving_MessageService;
import com.fh.util.*;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.io.FilenameUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.fh.controller.app.appuser.different.CommentTiwen;
import com.fh.controller.app.appuser.different.CommentTiwenHuiFu;
import com.fh.controller.app.appuser.different.CommetnTiwenHuiFu2;
import com.fh.controller.app.appuser.different.CommentActivity;
import com.fh.controller.app.appuser.different.CommentActivityHui;
import com.fh.controller.app.appuser.different.CommentActivityHui2;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.system.activity.ActivityService;
import com.fh.service.system.activity_post.Activity_PostService;
import com.fh.service.system.activity_post1.Activity_Post1Service;
import com.fh.service.system.activity_post_img.Activity_Post_ImgService;
import com.fh.service.system.activity_room.Activity_RoomService;
import com.fh.service.system.address.AddressService;
import com.fh.service.system.apply_vip.Apply_vipService;
import com.fh.service.system.appuser.AppuserService;
import com.fh.service.system.appuserInfo.AppUserInfoService;
import com.fh.service.system.banner.BannerService;
import com.fh.service.system.cart.CartService;
import com.fh.service.system.cartnest.CartNestService;
import com.fh.service.system.cartnest_img.Cartnest_ImgService;
import com.fh.service.system.cbannerimg.CbannerimgService;
import com.fh.service.system.collection_activity.Collection_activityService;
import com.fh.service.system.collection_activity_post.Collection_ActivityPostService;
import com.fh.service.system.collection_activity_user.Collection_Activity_UserService;
import com.fh.service.system.collection_post.Collection_postService;
import com.fh.service.system.collection_pro.Collection_proService;
import com.fh.service.system.collection_room.Collection_RoomService;
import com.fh.service.system.collection_special.Collection_specialService;
import com.fh.service.system.collection_tiwen.Collection_tiwenService;
import com.fh.service.system.collection_wenda.Collection_WendaService;
import com.fh.service.system.comment_activity.Comment_ActivityService;
import com.fh.service.system.comment_activity_img.Comment_ActivityImgService;
import com.fh.service.system.comment_activity_user.Comment_Activity_UserService;
import com.fh.service.system.comment_activity_user_img.Comment_Activity_UserImgService;
import com.fh.service.system.comment_catnest.Comment_CatnestService;
import com.fh.service.system.comment_catnest_img.Comment_CatnestImgService;
import com.fh.service.system.comment_leaving_message.Comment_LeavingMessageService;
import com.fh.service.system.comment_mail.Comment_MailService;
import com.fh.service.system.comment_mail_img.Comment_MailImgService;
import com.fh.service.system.comment_post.Comment_PostService;
import com.fh.service.system.comment_post_img.Comment_PostImgService;
import com.fh.service.system.comment_pro.CommentProService;
import com.fh.service.system.comment_proimg.CommentProImgService;
import com.fh.service.system.comment_research.Comment_ResearchService;
import com.fh.service.system.comment_research_img.Comment_ResearchImgService;
import com.fh.service.system.comment_special.Comment_specialService;
import com.fh.service.system.comment_specialimg.Comment_SpecialImgService;
import com.fh.service.system.comment_tiwen.Comment_TiwenService;
import com.fh.service.system.comment_tiwen_img.Comment_TiWenImgService;
import com.fh.service.system.content.ContentService;
import com.fh.service.system.coupon.CouponService;
import com.fh.service.system.error_wenda.Error_WendaService;
import com.fh.service.system.exclu.ExcluService;
import com.fh.service.system.fasongphone.FaSongPhoneService;
import com.fh.service.system.feiliao.FeiLiaoService;
import com.fh.service.system.feiqi.FeiQiService;
import com.fh.service.system.fenxiang.FenXiangService;
import com.fh.service.system.fenxiangC.FenXiangCService;
import com.fh.service.system.fight.FightService;
import com.fh.service.system.fight_info.Fight_InfoService;
import com.fh.service.system.guanggao.GuangGaoService;
import com.fh.service.system.history.HistoryService;
import com.fh.service.system.home_follow.Home_FollowService;
import com.fh.service.system.integral.IntegralService;
import com.fh.service.system.integral_info.Integral_InfoService;
import com.fh.service.system.leaving_message.Leaving_MessageService;
import com.fh.service.system.login_record.Login_recordService;
import com.fh.service.system.mentou_img.MenTouImfService;
import com.fh.service.system.news.NewsService;
import com.fh.service.system.nongyao.NongYaoService;
import com.fh.service.system.opinion.OpinionService;
import com.fh.service.system.opinion_img.Opinion_ImgService;
import com.fh.service.system.order_info.Order_infoService;
import com.fh.service.system.order_pro.Order_ProService;
import com.fh.service.system.p_quanxian.P_QuanXuanService;
import com.fh.service.system.post.PostService;
import com.fh.service.system.post_forim_postcomment.Post_forim_postcommentService;
import com.fh.service.system.post_img.Post_ImgService;
import com.fh.service.system.post_info.Post_InfoService;
import com.fh.service.system.post_keyword.Post_KeywordService;
import com.fh.service.system.post_special.Post_SpecialService;
import com.fh.service.system.post_special_type.Post_Special_TypeService;
import com.fh.service.system.price.PriceService;
import com.fh.service.system.product.ProductService;
import com.fh.service.system.product_img.Product_imgService;
import com.fh.service.system.qiandao.QianDaoService;
import com.fh.service.system.register_record.Register_recordService;
import com.fh.service.system.remark.RemarkService;
import com.fh.service.system.research.ResearchService;
import com.fh.service.system.research_img.Research_ImgService;
import com.fh.service.system.special.SpecialService;
import com.fh.service.system.taocan.TaoCanService;
import com.fh.service.system.tiwen.TiWenService;
import com.fh.service.system.tiwen_img.TiWen_ImgService;
import com.fh.service.system.token.TokenService;
import com.fh.service.system.views_activity.Views_ActivityService;
import com.fh.service.system.views_activity_ip.Views_Activity_IpService;
import com.fh.service.system.wancheng.WanChengService;
import com.fh.service.system.weixin.WeiXinService;
import com.fh.service.system.weixinzhifu.WeixinzhifuService;
import com.fh.service.system.wenda.WenDaService;
import com.fh.service.system.wenda_count.WenDa_CountService;
import com.fh.service.system.wenda_info.WenDa_InfnService;
import com.fh.service.system.wenda_shijuan.WenDa_ShiJuanService;
import com.fh.service.system.wenda_suiji.WenDa_SuiJiService;
import com.fh.service.system.wenda_tiezi.WenDaTieZiService;
import com.fh.service.system.yzm.YzmService;
import com.fh.service.system.z_quanxian.Z_QuanXianService;
import com.fh.service.system.zan_activity_post.Zan_ActivityPostService;
import com.fh.service.system.zan_catnest.Zan_CatnestService;
import com.fh.service.system.zan_comment_activity.ZanCommentActivityService;
import com.fh.service.system.zan_comment_activity_user.Zan_Comment_ActivityUserService;
import com.fh.service.system.zan_comment_catnest.Zan_CommentCatnestService;
import com.fh.service.system.zan_comment_mail.Zan_CommentMailService;
import com.fh.service.system.zan_comment_post.Zan_CommentPostService;
import com.fh.service.system.zan_comment_research.Zan_CommentResearchService;
import com.fh.service.system.zan_comment_special.ZanCommentSpecialService;
import com.fh.service.system.zan_comment_tiwen.Zan_Comment_TiwenService;
import com.fh.service.system.zan_post.Zan_PostService;
import com.fh.service.system.zan_research.Zan_ResearchService;
import com.fh.service.system.zan_special.Zan_SpecialService;
import com.fh.service.system.zan_tiwen.Zan_tiwenService;
import com.fh.service.system.zixun.ZiXunService;
import com.fh.service.system.zuoguo_shijuan.ZuoGuo_ShiJuanService;
import com.fh.util.alipay.AlipayConfig;
import com.fh.util.utils.CommonUtil;
import com.fh.util.utils.GetWxOrderno;
import com.fh.util.utils.RequestHandler;
import com.fh.util.utils.Sha1Util;
import com.fh.util.utils.TenpayUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/app", produces = "text/html;charset=UTF-8")
public class APPController extends BaseController {

    @Autowired
    private GameCouponService gameCouponService;
    @Resource(name = "appuserService")
    private AppuserService appuserService; // 用户
    @Resource(name = "appUserInfoService")
    private AppUserInfoService appUserInfoService; // 用户信息
    @Resource(name = "yzmService")
    private YzmService yzmService; // 验证码
    @Resource(name = "productService")
    private ProductService productService; // 商品
    @Resource(name = "product_imgService")
    private Product_imgService product_imgService; // 商品图片
    @Resource(name = "postService")
    private PostService postService; // 帖子
    @Resource(name = "post_ImgService")
    private Post_ImgService post_ImgService;
    @Resource(name = "bannerService")
    private BannerService bannerService;// 首页版图
    @Resource(name = "login_recordService")
    private Login_recordService login_recordService; // 登录统计
    @Resource(name = "register_recordService")
    private Register_recordService register_recordService; // 注册统计
    @Resource(name = "remarkService")
    private RemarkService remarkService; // 规格
    @Resource(name = "cartService")
    private CartService cartService; // 购物车
    @Resource(name = "addressService")
    private AddressService addressService; // 地址
    @Resource(name = "order_infoService")
    private Order_infoService order_infoService;// 订单
    @Resource(name = "order_ProService")
    private Order_ProService order_ProService; // 订单详情
    @Resource(name = "collection_proService")
    private Collection_proService collection_proService;// 收藏商品
    @Resource(name = "contentService")
    private ContentService contentService;// 登录查看记录
    @Resource(name = "apply_vipService")
    private Apply_vipService apply_vipService; // 申请VIP
    @Resource(name = "menTouImfService")
    private MenTouImfService menTouImfService; // 门头照ID
    @Resource(name = "commentProService")
    private CommentProService commentProService;// 商品评论
    @Resource(name = "priceService")
    private PriceService priceService; // 商品价格
    @Resource(name = "ziXunService")
    private ZiXunService ziXunService; // 咨询电话
    @Resource(name = "commentProImgService")
    private CommentProImgService commentProImgService; // 评论图片
    @Resource(name = "post_InfoService")
    private Post_InfoService post_InfoService; // 帖子详情
    @Resource(name = "post_forim_postcommentService")
    private Post_forim_postcommentService post_forim_postcommentService; // 帖子回复
    @Resource(name = "collection_postService")
    private Collection_postService collection_postService; // 帖子收藏
    @Resource(name = "post_SpecialService")
    private Post_SpecialService post_SpecialService; // 帖子专题
    @Resource(name = "collection_specialService")
    private Collection_specialService collection_specialService; // 关注帖子
    @Resource(name = "comment_specialService")
    private Comment_specialService comment_specialService; // 评论专题
    @Resource(name = "comment_SpecialImgService")
    private Comment_SpecialImgService comment_SpecialImgService;
    @Resource(name = "specialService")
    private SpecialService specialService; // 专题下的帖子
    @Resource(name = "home_FollowService")
    private Home_FollowService home_FollowService; // 关注
    @Resource(name = "activityService")
    private ActivityService activityService; // 直播
    @Resource(name = "activity_PostService")
    private Activity_PostService activity_PostService; // 直播帖子
    @Resource(name = "activity_Post_ImgService")
    private Activity_Post_ImgService activity_Post_ImgService; // 直播帖子图片
    @Resource(name = "collection_activityService")
    private Collection_activityService collection_activityService; // 关注直播
    @Resource(name = "couponService")
    private CouponService couponService; // 优惠卷
    @Resource(name = "opinionService")
    private OpinionService opinionService; // 提交建议
    @Resource(name = "opinion_ImgService")
    private Opinion_ImgService opinion_ImgService; // 提交建议图片
    @Resource(name = "comment_ActivityService")
    private Comment_ActivityService comment_ActivityService; // 直播帖子评论
    @Resource(name = "comment_ActivityImgService")
    private Comment_ActivityImgService comment_ActivityImgService;// 直播帖子评论图片
    @Resource(name = "comment_PostService")
    private Comment_PostService comment_PostService; // 评论帖子
    @Resource(name = "comment_PostImgService")
    private Comment_PostImgService comment_PostImgService;// 评论帖子图片
    @Resource(name = "zan_PostService")
    private Zan_PostService zan_PostService; // 是否已经赞了帖子
    @Resource(name = "zan_CommentPostService")
    private Zan_CommentPostService zan_CommentPostService; // 是否以经咱过帖子评论
    @Resource(name = "zan_ActivityPostService")
    private Zan_ActivityPostService zan_ActivityPostService; // 是否已经赞过直播帖子
    @Resource(name = "collection_ActivityPostService")
    private Collection_ActivityPostService collection_ActivityPostService; // 是否关注直播帖子
    @Resource(name = "zanCommentActivityService")
    private ZanCommentActivityService zanCommentActivityService; // 是否赞过直播评论
    @Resource(name = "zanCommentSpecialService")
    private ZanCommentSpecialService zanCommentSpecialService; // 是否赞过专题评论
    @Resource(name = "post_KeywordService")
    private Post_KeywordService post_KeywordService; // 帖子热词
    @Resource(name = "zan_SpecialService")
    private Zan_SpecialService zan_SpecialService; // 是否已经赞过专题
    @Resource(name = "newsService")
    private NewsService newsService; // 消息
    @Resource(name = "comment_Activity_UserService")
    private Comment_Activity_UserService comment_Activity_UserService; // 评论直播用户
    @Resource(name = "comment_Activity_UserImgService")
    private Comment_Activity_UserImgService comment_Activity_UserImgService;// 评论直播用户图片
    @Resource(name = "zan_Comment_ActivityUserService")
    private Zan_Comment_ActivityUserService zan_Comment_ActivityUserService;// 赞直播用户
    @Resource(name = "views_ActivityService")
    private Views_ActivityService views_ActivityService;// 赞直播用户
    @Resource(name = "weiXinService")
    private WeiXinService weiXinService; // 微信分享
    @Resource(name = "collection_Activity_UserService")
    private Collection_Activity_UserService collection_Activity_UserService; // 收藏
    @Resource(name = "tiWenService")
    private TiWenService tiWenService; // 提问
    @Resource(name = "tiWen_ImgService")
    private TiWen_ImgService tiWen_ImgService; // 提问图片
    @Resource(name = "comment_TiwenService")
    private Comment_TiwenService comment_TiwenService; // 评论提问
    @Resource(name = "comment_TiWenImgService")
    private Comment_TiWenImgService comment_TiWenImgService;// 评论提问图片
    @Resource(name = "zan_tiwenService")
    private Zan_tiwenService zan_tiwenService; // 赞提问
    @Resource(name = "collection_tiwenService")
    private Collection_tiwenService collection_tiwenService; // 收藏提问
    @Resource(name = "zan_Comment_TiwenService")
    private Zan_Comment_TiwenService zan_Comment_TiwenService; // 点赞提问
    @Resource(name = "excluService")
    private ExcluService excluService; // 客服
    @Resource(name = "activity_Post1Service")
    private Activity_Post1Service activity_Post1Service; //
    @Resource(name = "activity_RoomService")
    private Activity_RoomService activity_RoomService; // 直播间
    @Resource(name = "views_Activity_IpService")
    private Views_Activity_IpService views_Activity_IpService; // 查询直播次数
    @Resource(name = "feiLiaoService")
    private FeiLiaoService feiLiaoService; // 肥料
    @Resource(name = "nongYaoService")
    private NongYaoService nongYaoService;// 农药
    @Resource(name = "p_QuanXuanService")
    private P_QuanXuanService p_QuanXuanService;// 商品权限
    @Resource(name = "z_QuanXianService")
    private Z_QuanXianService z_QuanXianService;// 直播权限
    @Resource(name = "fightService")
    private FightService fightService;// 团购
    @Resource(name = "fight_InfoService")
    private Fight_InfoService fight_InfoService;// 团购详情
    @Resource(name = "integralService")
    private IntegralService integralService;// 团购介绍
    @Resource(name = "integral_InfoService")
    private Integral_InfoService integral_InfoService;// 团购商品
    @Resource(name = "weixinzhifuService")
    private WeixinzhifuService weixinzhifuService;// 微信支付状态
    @Resource(name = "tokenService")
    private TokenService tokenService;// 微信token
    @Resource(name = "post_Special_TypeService")
    private Post_Special_TypeService post_Special_TypeService;// 微信类型
    @Resource(name = "historyService")
    private HistoryService historyService;// 用户看过
    @Resource(name = "collection_RoomService")
    private Collection_RoomService collection_RoomService;// 收藏直播间
    @Resource(name = "wenDa_ShiJuanService")
    private WenDa_ShiJuanService wenDa_ShiJuanService;// 问答试卷
    @Resource(name = "wenDaService")
    private WenDaService wenDaService; // 问答问题
    @Resource(name = "wenDa_InfnService")
    private WenDa_InfnService wenDa_InfnService; // 问答选项
    @Resource(name = "collection_WendaService")
    private Collection_WendaService collection_WendaService; // 从藏问题
    @Resource(name = "error_WendaService")
    private Error_WendaService error_WendaService; // 错误问题
    @Resource(name = "zuoGuo_ShiJuanService")
    private ZuoGuo_ShiJuanService zuoGuo_ShiJuanService; // 做过的试卷
    @Resource(name = "guangGaoService")
    private GuangGaoService guangGaoService;// 广告
    @Resource(name = "wenDa_SuiJiService")
    private WenDa_SuiJiService wenDa_SuiJiService; // 问答随机
    @Resource(name = "wenDa_CountService")
    private WenDa_CountService wenDa_CountService;// 问答
    @Resource(name = "fenXiangService")
    private FenXiangService fenXiangService;          //分享
    @Resource(name = "feiQiService")
    private FeiQiService feiQiService;
    @Resource(name = "fenXiangCService")
    private FenXiangCService fenXiangCService;          //分享
    @Resource(name = "wanChengService")
    private WanChengService wanChengService;          //答题完成的统计
    @Resource(name = "qianDaoService")
    private QianDaoService qianDaoService;          //签到信息
    @Resource(name = "wenDaTieZiService")
    private WenDaTieZiService wenDaTieZiService;          //问答帖子
    @Resource(name = "taoCanService")
    private TaoCanService taoCanService;          //套餐
    @Resource(name = "researchService")
    private ResearchService researchService;          //研究院帖子
    @Resource(name = "zan_ResearchService")
    private Zan_ResearchService zan_ResearchService;          //点赞研究院帖子
    @Resource(name = "research_ImgService")
    private Research_ImgService research_ImgService;          //研究院帖子图片
    @Resource(name = "comment_ResearchService")
    private Comment_ResearchService comment_ResearchService;          //评论研究院帖子
    @Resource(name = "zan_CommentResearchService")
    private Zan_CommentResearchService zan_CommentResearchService;          //点赞研究院评论
    @Resource(name = "comment_ResearchImgService")
    private Comment_ResearchImgService comment_ResearchImgService;//评论研究院帖子图片
    @Resource(name = "cartNestService")
    private CartNestService cartNestService;          //猫窝帖子
    @Resource(name = "zan_CatnestService")
    private Zan_CatnestService zan_CatnestService;          //点赞猫窝帖子
    @Resource(name = "comment_CatnestService")
    private Comment_CatnestService comment_CatnestService; //评论猫窝帖子
    @Resource(name = "cartnest_ImgService")
    private Cartnest_ImgService cartnest_ImgService;          //猫窝帖子图片
    @Resource(name = "comment_CatnestImgService")
    private Comment_CatnestImgService comment_CatnestImgService;//评论猫窝帖图片
    @Resource(name = "zan_CommentCatnestService")
    private Zan_CommentCatnestService zan_CommentCatnestService;//点赞猫窝评论
    @Resource(name = "leaving_MessageService")
    private Leaving_MessageService leaving_MessageService;//猫窝留言
    @Resource(name = "comment_LeavingMessageService")
    private Comment_LeavingMessageService comment_LeavingMessageService;          //评论猫窝留言
    @Resource(name = "cbannerimgService")
    private CbannerimgService cbannerimgService;          //猫窝显示图
    @Resource(name = "comment_MailService")
    private Comment_MailService comment_MailService;          //套餐评论
    @Resource(name = "comment_MailImgService")
    private Comment_MailImgService comment_MailImgService; //套餐评论图片
    @Resource(name = "zan_CommentMailService")
    private Zan_CommentMailService zan_CommentMailService;
    @Resource(name = "faSongPhoneService")
    private FaSongPhoneService faSongPhoneService;
    @Resource(name = "pullblackService")
    private PullblackService pullblackService;
    @Resource(name = "user_DailyService")
    private User_DailyService user_dailyService;
    @Resource(name = "zan_CommentResearchYoukeService")
    private Zan_CommentResearchYoukeService zan_commentResearchYoukeService;
    @Resource(name = "zan_leaving_MessageService")
    private Zan_leaving_MessageService zan_leaving_messageService;
    @Resource(name = "zan_Comment_leaving_MessageService")
    private Zan_Comment_leaving_MessageService zan_comment_leaving_messageService;
    @Resource(name = "user_WendaQuanxianService")
    private User_WendaQuanxianService user_wendaQuanxianService;
    @Resource(name = "apply_vip_WenService")
    private Apply_Vip_WenService apply_vip_wenService;
    @Resource(name = "count_communityService")
    private Count_communityService countCommunityService;
    @Resource(name = "jifen_shopService")
    private Jifen_shopService jifenShopService;

    /**
     * 查询图片
     *
     * @return
     */
    @RequestMapping(value = "findPicture", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findAChuan() {
        logBefore(logger, "查询图片");
        PageData pd = this.getPageData();
        try {
            PageData pd_b = bannerService.findName(pd);
            PageData pd_w = wenDa_ShiJuanService.findZuiXin(pd);
            PageData pd_g = guangGaoService.findByIdS(pd);
            pd.clear();
            pd.put("data", pd_b);
            pd.put("wenda", pd_w);
            pd.put("guanggao", pd_g);
        } catch (Exception e) {
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序错误,请联系系统管理员!");
            e.printStackTrace();
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }

    @ResponseBody
    @RequestMapping("/getPicture")
    public String getPicture(@RequestParam(value = "file", required = false) CommonsMultipartFile attach,
                             HttpServletRequest request) {
        String picture = null;
        // 判断文件是否为空
        if (!attach.isEmpty()) {
            String path = request.getSession().getServletContext()
                    .getRealPath("statics" + File.separator + "images");
            System.out.println("=============================" + path);
            String oldFileName = attach.getOriginalFilename();// 原文件名
            String prefix = FilenameUtils.getExtension(oldFileName);// 原文件后缀
            int filesize = 5000000;
            if (attach.getSize() > filesize) {// 上传大小不得超过 500k
                return "1";// 1上传文件大于设定大小
            } else if (prefix.equalsIgnoreCase("mp3")
                    || prefix.equalsIgnoreCase("wma")
                    || prefix.equalsIgnoreCase("wav")
                    ) {// 上传图片格式不正确
                Random random = new Random(10000000);
                String fileName = System.currentTimeMillis()
                        + random.nextInt(1000000) + "_Personal.jpg";
                File targetFile = new File(path, fileName);
                if (!targetFile.exists()) {
                    targetFile.mkdirs();
                }
                // 保存
                try {
                    attach.transferTo(targetFile);
                } catch (Exception e) {
                    e.printStackTrace();
                    return "2";// 2为上传失败
                }
                picture = fileName;
            } else {
                System.out
                        .println("-------------------格式错误--------------------------");
                request.setAttribute("uploadFileError", " * 上传图片格式不正确");
                return "3";// 3为上传格式不对
            }
            return picture;// 图片路径
        }
        return "4";// 3上传内容为空
    }

    @RequestMapping(value = "pullblack", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String pullblack(String USER_ID, String STATUS) {
        logBefore(logger, "拉黑用户");
        PageData pd = new PageData();
        try {
            if (STATUS.equals("1")) {
                pullblackService.save(pd);
            } else {
                pullblackService.delete(pd);
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
        } catch (Exception e) {
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序错误,请联系系统管理员!");
            e.printStackTrace();
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }


    @RequestMapping(value = "access_tokenUnionid", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String access_tokenUnionid(HttpServletRequest request) {
        logger.info("获取微信信息");
        String get_access_token_url = "https://api.weixin.qq.com/sns/oauth2/access_token?" +
                "appid=APPID" +
                "&secret=SECRET&" +
                "code=CODE&grant_type=authorization_code";
        String get_userinfo = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

        String openid = null;
        String access_token = null;
        WeixinUserInfo weixinUserInfo = null;
        String json = "";
        PageData pd = new PageData();
        try {
            request.setCharacterEncoding("utf-8");
            String appid = "wx3f6627be4ed503c9";
            String appsecret = "5080ea88e4197c91844e2aa76c971cb5";
            String code = request.getParameter("code");

            // 1.微信用户信息
            get_access_token_url = get_access_token_url.replace("APPID", appid).replace("SECRET", appsecret).replace("CODE", code);
            json = WeiXin.getUrl(get_access_token_url);
            JSONObject jsonObject = JSONObject.fromObject(json);
			/*if(jsonObject.getString("openid")==null){
				if (jsonObject.getString("errcode").equals("40029")){  //是否为code错误
					 get_access_token_url=   get_access_token_url.substring(0, get_access_token_url.indexOf("&code=")).concat("&code=").concat(code); //去除错误的code加入正确的
		             json = WeiXin.getUrl(get_access_token_url);
		             jsonObject = JSONObject.fromObject(json);
				}
			}

			openid = jsonObject.getString("openid");
			logger.info("openid"+openid);
			access_token=jsonObject.getString("access_token");

			get_userinfo=get_userinfo.replace("ACCESS_TOKEN", access_token);
	        get_userinfo=get_userinfo.replace("OPENID", openid);

	        String userInfoJson=WeiXin.getUrl(get_userinfo);
	        logger.info("用户信息"+userInfoJson);
	        JSONObject wxUser=JSONObject.fromObject(userInfoJson);
	         weixinUserInfo = new WeixinUserInfo();
	        // 用户的标识
	         weixinUserInfo.setOpenId(jsonObject.getString("openid"));
	        // 昵称
	        weixinUserInfo.setNickname(wxUser.getString("nickname"));
	        // 用户的性别（1是男性，2是女性，0是未知）
	        weixinUserInfo.setSex(wxUser.getInt("sex"));
	        // 用户所在省份
	        weixinUserInfo.setProvince(wxUser.getString("province"));
	        // 用户所在国家
	        weixinUserInfo.setCountry(wxUser.getString("country"));
	        // 用户所在城市
	        weixinUserInfo.setCity(wxUser.getString("city"));
	        // 用户头像
	        weixinUserInfo.setHeadImgUrl(wxUser.getString("headimgurl"));


	        weixinUserInfo.setUnionid(wxUser.getString("unionid"));*/
//			System.out.println("OpenID：" + weixinUserInfo.getOpenId());
//		    System.out.println("关注状态：" + weixinUserInfo.getSubscribe());
//		    System.out.println("关注时间：" + weixinUserInfo.getSubscribeTime());
//		    System.out.println("昵称：" + weixinUserInfo.getNickname());
//		    System.out.println("性别：" + weixinUserInfo.getSex());
//		    System.out.println("国家：" + weixinUserInfo.getCountry());
//		    System.out.println("省份：" + weixinUserInfo.getProvince());
//		    System.out.println("城市：" + weixinUserInfo.getCity());
//		    System.out.println("语言：" + weixinUserInfo.getLanguage());
//		    System.out.println("头像：" + weixinUserInfo.getHeadImgUrl());
            logger.info("返回数据: " + json);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("data", jsonObject);
        } catch (Exception e) {
            logger.info("获取微信openid出错 ");
            logger.info("返回数据: " + json);
            pd.clear();
            pd.put("code", 2);
            pd.put("message", "程序错误,请联系系统管理员!");
            e.printStackTrace();
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 通过code换取网页授权access_token
     *
     * @param code state 重定向后会带上state参数，开发者可以填写a-zA-Z0-9的参数值，最多128字节
     * @return
     */
    @RequestMapping(value = "/access_tokenss", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public synchronized String access_tokenss(String code, String state) {
        logBefore(logger, "code值" + code);
        PageData pd = new PageData();
        pd = this.getPageData();
        String openid = "";
        String message = "正确返回数据!";

        String unionid = "";
        if (code == null || code.length() == 0) { // 缺少参数值
            pd.clear();
            pd.put("code", 0);
            pd.put("message", "缺少参数值code");
        } else {
            try {
                String requestUrl = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";
                requestUrl = requestUrl.replace("APPID", "wx1e84b85211c6b5b5").replace("SECRET", "0dee39eeee65f30d0bb796c4a98443ab").replace("JSCODE", code);
                JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);
                if (null != jsonObject) {
                    System.out.println(jsonObject);
                    openid = jsonObject.getString("openid");
                    unionid = jsonObject.getString("unionid");
                } else {
                    message = "获取失败";
                }
                pd.clear();
                pd.put("code", "1");
                pd.put("message", message);
                pd.put("openid", openid);
                pd.put("unionid", unionid);
            } catch (Exception e) {
                e.printStackTrace();
                pd.clear();
                pd.put("code", 2);
                pd.put("message", "程序错误,请联系系统管理员!");
            }
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
        } catch (JsonGenerationException e) {

            e.printStackTrace();
        } catch (JsonMappingException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
        return str;
    }

    @RequestMapping(value = "deleteCatnest", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String deleteCatnest(String CATNEST_ID) {
        logBefore(logger, "删除猫窝帖子");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            cartNestService.delete(pd);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
        } catch (Exception e) {
            e.printStackTrace();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
        } catch (JsonGenerationException e) {

            e.printStackTrace();
        } catch (JsonMappingException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
        return str;
    }


    @RequestMapping(value = "findUserName", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findUserName(String USERNAME) {
        logBefore(logger, "根据手机号判断");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            PageData pd_u = appuserService.findByUserName(pd);
            pd.clear();
            pd.put("data", pd_u);
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错请联系管理员");
        }
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(pd);
        } catch (JsonGenerationException e) {

            e.printStackTrace();
        } catch (JsonMappingException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
        return str;
    }

    @RequestMapping(value = "XiaoRegister", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String XiaoRegister(String UNIONID, String nickname, String sex, String city, String openid,
                               String headImageUrl, String USERNAME, String PASSWORD, String YZM, String STATUS) {
        logBefore(logger, "小程序登录");
        PageData pd = new PageData();
        pd = this.getPageData();
        String code = "1";
        String message = "关联成功!";
        Map<String, Object> map = new HashMap();
        try {
            PageData pd1 = appuserService.findName(pd);
            if (STATUS.equals("1")) {
                if (pd1 != null) {
                    pd.put("PASSWORD", new SimpleHash("SHA-1", PASSWORD, pd1.getString("SALT")).toString());
                    String pad = MD5.md5(MD5.md5(PASSWORD) + pd1.getString("SALT"));
                    pd.put("PASSWORD", pad);
                    PageData pd2 = appuserService.login(pd);
                    if (pd2 != null) {
                        pd1.put("UNIONID", UNIONID);
                        pd1.put("NAME", nickname);
                        pd1.put("OPENID", openid);
                        appuserService.editunionIDss(pd1);
                        map.put("NAME", pd1.getString("NAME"));
                        /* map.put("SEX", pd2.getString("SEX")); */
                        map.put("IMG", pd1.getString("IMG"));
                        map.put("USER_ID", pd1.getString("USER_ID"));
                        map.put("UNIONID", UNIONID);
                    } else {
                        code = "13";//账号密码不正确
                        message = "账号密码不正确";
                    }
                } else {
                    code = "11";          //没有改账号
                    message = "没有改账号";
                }
            } else {
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
                            } else {
                                code = "12";          //已存在改账号
                                message = "已存在该账号";
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
            }
            pd.clear();
            pd.put("data", map);
            pd.put("code", code);
            pd.put("message", message);
        } catch (Exception e) {
            e.printStackTrace();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
        } catch (JsonGenerationException e) {

            e.printStackTrace();
        } catch (JsonMappingException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
        return str;
    }


    @RequestMapping(value = "findUnionid", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findUnionid(String UNIONID) {
        logBefore(logger, "根据UNIONID查询用户");
        PageData pd = new PageData();
        pd = this.getPageData();
        String status = "0";
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            PageData pd_u = appuserService.findUnionId(pd);
            if (null == pd_u) {
                pd.clear();
                pd.put("code", "1");
                pd.put("message", "用户信息不存在！");
                pd.put("status", status);
                str = mapper.writeValueAsString(pd);
                return str;
            }
            List<PageData> list = pullblackService.findList(pd_u);
            if (list != null && list.size() != 0) {
                status = "1";
            }
            if ("17".equals(pd_u.get("USER_ID").toString()) || "1545".equals(pd_u.get("USER_ID").toString())) {
                pd.clear();
                pd.put("code", "1");
                pd.put("message", "正确返回数据!");
                pd.put("data", pd_u);
                pd.put("status", status);
                pd.put("flag", "true");
            } else {
                pd.clear();
                pd.put("code", "1");
                pd.put("message", "正确返回数据!");
                pd.put("data", pd_u);
                pd.put("status", status);
            }

        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错请联系管理员");
        }

        try {
            str = mapper.writeValueAsString(pd);
        } catch (JsonGenerationException e) {

            e.printStackTrace();
        } catch (JsonMappingException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
        return str;
    }

    /**
     * @param USER_ID
     * @return
     */
    @RequestMapping(value = "saveblack", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String saveblack(String USER_ID) {
        logBefore(logger, "拉入黑名单");
        PageData pd = new PageData();
        pd = this.getPageData();
        String message = "正确返回数据!";
        try {
            pullblackService.save(pd);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", message);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错请联系管理员");
        }
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(pd);
        } catch (JsonGenerationException e) {

            e.printStackTrace();
        } catch (JsonMappingException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
        return str;
    }

    @RequestMapping(value = "blackList", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String blackList(String pageNum) {
        logBefore(logger, "获取黑名单列表");
        PageData pd = new PageData();
        Page page = new Page();
        pd = this.getPageData();
        String message = "正确返回数据!";
        try {
            page.setPd(pd);
            page.setShowCount(10);
            page.setCurrentPage(Integer.parseInt(pageNum));
            List<PageData> blackList = pullblackService.blackList(page);
            Map<String, Object> map = new HashedMap();
            if (page.getCurrentPage() == Integer.parseInt(pageNum)) {
                map.put("object", blackList);
            } else {
                List<PageData> list3 = new ArrayList();
                map.put("object", list3);
                message = "已加载全部数据!";
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("data", map);
            pd.put("message", message);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错请联系管理员");
        }
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(pd);
        } catch (JsonGenerationException e) {

            e.printStackTrace();
        } catch (JsonMappingException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
        return str;
    }

    @RequestMapping(value = "deleteBlack", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String deleteBlack(String USER_ID) {
        logBefore(logger, "取消某用户黑名单");
        PageData pd = new PageData();
        Page page = new Page();
        pd = this.getPageData();
        String message = "正确返回数据!";
        try {
            pullblackService.delete(pd);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", message);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错请联系管理员");
        }
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(pd);
        } catch (JsonGenerationException e) {

            e.printStackTrace();
        } catch (JsonMappingException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
        return str;
    }


    @RequestMapping(value = "findUserLeavingMessage", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findUserLeavingMessage(String USER_ID, String pageNum) {
        logBefore(logger, "查询我的留言");
        PageData pd = new PageData();
        pd = this.getPageData();
        Page page = new Page();
        String message = "正确返回数据!";
        try {
            page.setPd(pd);
            page.setShowCount(10);
            page.setCurrentPage(Integer.parseInt(pageNum));
            List<PageData> list = leaving_MessageService.dataslistPage(page);
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getString("USER_ID").equals("17")) {
                    list.get(i).put("STATUS", "1");
                } else if (appuserService.findJiFen(list.get(i)) != null) {
                    list.get(i).put("STATUS", "2");
                } else {
                    list.get(i).put("STATUS", "3");
                }
                List<PageData> comm = comment_LeavingMessageService.findLists(list.get(i));
                list.get(i).put("COMMENT", comm);
                List<PageData> comm1 = comment_LeavingMessageService.findBList(list.get(i));
                list.get(i).put("LCOMMENT", comm1);
            }
            PageData pd_cd = cbannerimgService.findById(pd);
            Map<String, Object> map = new HashedMap();
            if (page.getCurrentPage() == Integer.parseInt(pageNum)) {
                map.put("object", list);
            } else {
                List<PageData> list3 = new ArrayList();
                map.put("object", list3);
                message = "已加载全部数据!";
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("message", message);
            pd.put("data", map);
            pd.put("IMG", pd_cd.getString("IMG4"));
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错请联系管理员");
        }
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(pd);
        } catch (JsonGenerationException e) {

            e.printStackTrace();
        } catch (JsonMappingException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
        return str;
    }


    @RequestMapping(value = "saveCommentLeavingMessage", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String saveCommentLeavingMessage(String USER_ID, String MESSAGE, String LEAVING_MESSAGE_ID) {
        logBefore(logger, "添加留言评论");
        PageData pd = new PageData();
        pd = this.getPageData();
        ObjectMapper mapper = new ObjectMapper();
        try {
            PageData user = pullblackService.findByUserId(pd);
            if (null != user) {
                pd.clear();
                pd.put("code", "2");
                pd.put("message", "发送失败，请联系管理员！");
                return mapper.writeValueAsString(pd);
            }
            pd.put("NAME", "");
            pd.put("PUSER_ID", "");
            pd.put("PNAME", "");
            pd.put("DATE", DateUtil.getTime());
            pd.put("STATUS", "0");
            pd.put("VIEWS", "0");
            pd.put("YUE", DateUtil.getDay());
            pd.put("PID", "");
            pd.put("HUIFUS", "0");
            if (USER_ID.equals("17")) {
                pd.put("BSTATUS", "1");
            } else {
                pd.put("BSTATUS", "0");
            }
            pd.put("HSTATUS", "");
            comment_LeavingMessageService.save(pd);
            PageData leavl = leaving_MessageService.findById(pd);
            int HUIFU = Integer.parseInt(leavl.get("HUIFU").toString());
            HUIFU = HUIFU + 1;
            leavl.put("HUIFU", HUIFU);
            leaving_MessageService.editHUIFU(leavl);
            if (USER_ID.equals("17")) {          //判断是否为版主
                pd.put("HSTATUS", "1");
                leaving_MessageService.editHStatus(pd);
            }
            PageData pd_t = leaving_MessageService.findById(pd);
            PageData pd_u = appuserService.findById111(pd);
            PageData pd1 = new PageData();
            pd1.put("NEWS_ID", UuidUtil.get32UUID());
            pd1.put("ID", pd.getString("LEAVING_MESSAGE_ID"));
            pd1.put("MESSAGE", pd.getString("MESSAGE"));
            pd1.put("NAME", pd_u.getString("NAME"));
            pd1.put("IMG", pd_u.getString("IMG"));
            pd1.put("DATE", DateUtil.getTime());
            pd1.put("USER_ID", pd_t.getString("USER_ID"));
            pd1.put("SUBJECT", pd_t.getString("MESSAGE"));
            pd1.put("STATUS", "4");
            pd1.put("USER_ID1", pd.getString("USER_ID"));
            pd1.put("url", "");
            newsService.save(pd1);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错请联系管理员");
        }
        String str = "";
        try {
            str = mapper.writeValueAsString(pd);
        } catch (JsonGenerationException e) {

            e.printStackTrace();
        } catch (JsonMappingException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
        return str;
    }

    @RequestMapping(value = "saveCommentLeavingMessages", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String saveCommentLeavingMessages(String USER_ID, String MESSAGE, String LEAVING_MESSAGE_ID) {
        logBefore(logger, "添加留言评论的评论");
        PageData pd = new PageData();
        pd = this.getPageData();
        ObjectMapper mapper = new ObjectMapper();
        try {
            PageData user = pullblackService.findByUserId(pd);
            if (null != user) {
                pd.clear();
                pd.put("code", "2");
                pd.put("message", "发送失败，请联系管理员！");
                return mapper.writeValueAsString(pd);
            }
            PageData users = leaving_MessageService.findById(pd);
            pd.put("NAME", "");
            pd.put("PUSER_ID", users.get("USER_ID").toString());
            pd.put("PNAME", users.getString("NAME"));
            pd.put("DATE", DateUtil.getTime());
            pd.put("STATUS", "0");
            pd.put("VIEWS", "0");
            pd.put("YUE", DateUtil.getDay());
            pd.put("PID", "");
            pd.put("HUIFUS", "0");
            if (USER_ID.equals("17")) {
                pd.put("BSTATUS", "1");
            } else {
                pd.put("BSTATUS", "0");
            }
            pd.put("HSTATUS", "");
            comment_LeavingMessageService.save(pd);
            PageData pd_t = leaving_MessageService.findById(pd);
            PageData pd_u = appuserService.findById111(pd);
            PageData pd1 = new PageData();
            pd1.put("NEWS_ID", UuidUtil.get32UUID());
            pd1.put("ID", pd.getString("LEAVING_MESSAGE_ID"));
            pd1.put("MESSAGE", pd.getString("MESSAGE"));
            pd1.put("NAME", pd_u.getString("NAME"));
            pd1.put("IMG", pd_u.getString("IMG"));
            pd1.put("DATE", DateUtil.getTime());
            pd1.put("USER_ID", pd_t.getString("USER_ID"));
            pd1.put("SUBJECT", pd_t.getString("MESSAGE"));
            pd1.put("STATUS", "4");
            pd1.put("USER_ID1", pd.getString("USER_ID"));
            pd1.put("url", "");
            newsService.save(pd1);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
        } catch (Exception e) {
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        return "";
    }


    @RequestMapping(value = "saveCommentLeavingMessagess", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String saveCommentLeavingMessagess(String USER_ID, String MESSAGE, String COMMENT_LEAVING_MESSAGE_ID) {
        logBefore(logger, "添加留言评论的评论");
        PageData pd = new PageData();
        pd = this.getPageData();
        ObjectMapper mapper = new ObjectMapper();
        try {
            PageData user = pullblackService.findByUserId(pd);
            if (null != user) {
                pd.clear();
                pd.put("code", "2");
                pd.put("message", "发送失败，请联系管理员！");
                return mapper.writeValueAsString(pd);
            }
            PageData users = comment_LeavingMessageService.findById(pd);
            pd.put("NAME", "");
            pd.put("PUSER_ID", users.get("USER_ID").toString());
            pd.put("PNAME", users.getString("NAME"));
            pd.put("DATE", DateUtil.getTime());
            pd.put("STATUS", "0");
            pd.put("VIEWS", "0");
            pd.put("LEAVING_MESSAGE_ID", users.get("LEAVING_MESSAGE_ID").toString());
            pd.put("YUE", DateUtil.getDay());
            pd.put("PID", COMMENT_LEAVING_MESSAGE_ID);
            pd.put("HUIFUS", "0");
            if (USER_ID.equals("17")) {
                pd.put("BSTATUS", "1");
            } else {
                pd.put("BSTATUS", "0");
            }
            pd.put("HSTATUS", "");
            comment_LeavingMessageService.save(pd);
            comment_LeavingMessageService.editHUIFUSjia(pd);
            PageData pd_t = leaving_MessageService.findById(pd);
            PageData pd_u = appuserService.findById111(pd);
            PageData pd1 = new PageData();
            pd1.put("NEWS_ID", UuidUtil.get32UUID());
            pd1.put("ID", pd.getString("LEAVING_MESSAGE_ID"));
            pd1.put("MESSAGE", pd.getString("MESSAGE"));
            pd1.put("NAME", pd_u.getString("NAME"));
            pd1.put("IMG", pd_u.getString("IMG"));
            pd1.put("DATE", DateUtil.getTime());
            pd1.put("USER_ID", pd_t.getString("USER_ID"));
            pd1.put("SUBJECT", pd_t.getString("MESSAGE"));
            pd1.put("STATUS", "4");
            pd1.put("USER_ID1", pd.getString("USER_ID"));
            pd1.put("url", "");
            newsService.save(pd1);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
        } catch (Exception e) {
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        String str = "";
        try {
            str = mapper.writeValueAsString(pd);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }

    @RequestMapping(value = "deleteLeavingMessage", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String deleteLeavingMessage(String LEAVING_MESSAGE_ID) {
        logBefore(logger, "删除留言");
        PageData pd = this.getPageData();
        if (LEAVING_MESSAGE_ID == null || LEAVING_MESSAGE_ID.length() == 0) {
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "缺少参数!");
        } else {
            try {
                leaving_MessageService.delete(pd);
                pd.clear();
                pd.put("code", "1");
                pd.put("message", "正确返回数据!");
            } catch (Exception e) {
                pd.clear();
                pd.put("code", "2");
                pd.put("message", "程序出错请联系管理员");
                e.printStackTrace();
            }
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = "";
        try {
            str = mapper.writeValueAsString(pd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    @RequestMapping(value = "saveleaving_Message", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String saveleaving_Message(String USER_ID, String MESSAGE) {
        logBefore(logger, "添加留言");
        PageData pd = new PageData();
        pd = this.getPageData();
        ObjectMapper mapper = new ObjectMapper();
        try {
            PageData user = pullblackService.findByUserId(pd);
            if (null != user) {
                pd.clear();
                pd.put("code", "2");
                pd.put("message", "发送失败，请联系管理员！");
                return mapper.writeValueAsString(pd);
            }
            pd.put("SUBJECT", "");
            pd.put("DATE", DateUtil.getTime());
            pd.put("USER_ID", USER_ID);
            pd.put("VIEWS", "0");
            pd.put("HUIFU", "0");
            pd.put("STATUS", "0");
            pd.put("NAME", "0");
            pd.put("DATES", "0");
            if (USER_ID.equals("17")) {
                pd.put("BSTATUS", "1");
            } else {
                pd.put("BSTATUS", "0");
            }
            pd.put("HSTATUS", "0");
            leaving_MessageService.save(pd);
            pd.clear();
            pd.put("message", "正确返回数据!");
            pd.put("code", "1");
        } catch (Exception e) {
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错请联系管理员");
            e.printStackTrace();
        }
        String str = "";
        try {
            str = mapper.writeValueAsString(pd);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }

    //========================================================================
    @RequestMapping(value = "findleaving_MessageId", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findleaving_MessageId(String LEAVING_MESSAGE_ID, String USER_ID) {
        logBefore(logger, "查询留言详情");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            PageData pd_l = leaving_MessageService.findById(pd);
            if (pd_l.getString("USER_ID").equals("17")) {
                pd_l.put("STATUS", "1");
            } else if (appuserService.findJiFen(pd_l) != null) {
                pd_l.put("STATUS", "2");
            } else {
                pd_l.put("STATUS", "3");
            }
            if (zan_leaving_messageService.findById(pd) != null) {
                pd_l.put("ZAN", "1");
            } else {
                pd_l.put("ZAN", "0");
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("data", pd_l);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错请联系管理员");
        }
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(pd);
        } catch (JsonGenerationException e) {

            e.printStackTrace();
        } catch (JsonMappingException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
        return str;
    }

    @RequestMapping(value = "findCommentleaving_Message", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findCommentleaving_Message(String LEAVING_MESSAGE_ID, String pageNum, String USER_ID) {
        logBefore(logger, "查询留言评论");
        PageData pd = new PageData();
        pd = this.getPageData();
        Page page = new Page();
        String message = "正确返回数据!";
        try {
            List<PageData> lists = comment_LeavingMessageService.findBList(pd);
            page.setPd(pd);
            page.setShowCount(10);
            page.setCurrentPage(Integer.parseInt(pageNum));
            List<PageData> list = comment_LeavingMessageService.datasslistPage(page);
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getString("USER_ID").equals("17")) {
                    list.get(i).put("STATUS", "1");
                } else if (appuserService.findJiFen(list.get(i)) != null) {
                    list.get(i).put("STATUS", "2");
                } else {
                    list.get(i).put("STATUS", "3");
                }
                PageData pddd = new PageData();
                pddd.put("COMMENT_LEAVING_MESSAGE_ID", list.get(i).get("COMMENT_LEAVING_MESSAGE_ID").toString());
                pddd.put("USER_ID", USER_ID);
                if (zan_comment_leaving_messageService.findById(pddd) != null) {
                    list.get(i).put("ZAN", "1");
                } else {
                    list.get(i).put("ZAN", "0");
                }
            }

            Map<String, Object> map = new HashedMap();
            if (page.getCurrentPage() == Integer.parseInt(pageNum)) {
                map.put("object", list);
            } else {
                List<PageData> list3 = new ArrayList();
                map.put("object", list3);
                message = "已加载全部数据!";
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("message", message);
            pd.put("data", map);
            pd.put("object", lists);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错请联系管理员");
        }
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(pd);
        } catch (JsonGenerationException e) {

            e.printStackTrace();
        } catch (JsonMappingException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
        return str;
    }

    @RequestMapping(value = "findleaving_Message", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findleaving_Message(String USER_ID, String pageNum, String YSTATUS) {
        logBefore(logger, "查询留言管理");
        PageData pd = new PageData();
        pd = this.getPageData();
        Page page = new Page();
        String message = "正确返回数据!";
        try {
            page.setPd(pd);
            page.setShowCount(10);
            page.setCurrentPage(Integer.parseInt(pageNum));
            List<PageData> list = leaving_MessageService.datalistPage(page);
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getString("USER_ID").equals("17")) {
                    list.get(i).put("STATUS", "1");
                } else if (appuserService.findJiFen(list.get(i)) != null) {
                    list.get(i).put("STATUS", "2");
                } else {
                    list.get(i).put("STATUS", "3");
                }
                if (zan_leaving_messageService.findById(list.get(i)) != null) {
                    list.get(i).put("ZAN", "1");
                } else {
                    list.get(i).put("ZAN", "0");
                }
                List<PageData> comm = comment_LeavingMessageService.findLists(list.get(i));
                list.get(i).put("COMMENT", comm);
                List<PageData> comm1 = comment_LeavingMessageService.findBList(list.get(i));
                list.get(i).put("LCOMMENT", comm1);
            }
            Map<String, Object> map = new HashedMap();
            if (page.getCurrentPage() == Integer.parseInt(pageNum)) {
                map.put("object", list);
            } else {
                List<PageData> list3 = new ArrayList();
                map.put("object", list3);
                message = "已加载全部数据!";
            }
            PageData pd_cd = cbannerimgService.findById(pd);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", message);
            pd.put("data", map);
            pd.put("IMG", pd_cd.getString("IMG4"));
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错请联系管理员");
        }
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(pd);
        } catch (JsonGenerationException e) {

            e.printStackTrace();
        } catch (JsonMappingException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
        return str;
    }

    @RequestMapping(value = "findLaoCartNest", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findLaoCartNest(String pageNum) {
        logBefore(logger, "查询老猫的所有帖子");
        PageData pd = new PageData();
        pd = this.getPageData();
        Page page = new Page();
        String message = "正确返回数据!";
        try {
            page.setPd(pd);
            page.setShowCount(10);
            page.setCurrentPage(Integer.parseInt(pageNum));
            List<PageData> list = cartNestService.dataslistPage(page);
            for (int i = 0; i < list.size(); i++) {
                list.get(i).put("STATUS", "1");
                if (cartNestService.findClick(list.get(i)) != null) {
                    list.get(i).put("RSTATUS", "1");
                } else {
                    list.get(i).put("RSTATUS", "0");
                }
                List<PageData> list_img = cartnest_ImgService.findList(list.get(i));
                list.get(i).put("FIMG", list_img);
                String str = DateUtil.delHTMLTag(list.get(i).getString("MESSAGE"));
                String DETAILS1 = str.replace("\r", "");
                String DETAILS2 = DETAILS1.replace("\n", "");
                String DETAILS3 = DETAILS2.replace("&nbsp;", "");
                int qian = 20;
                if (DETAILS3.length() < 20) {
                    list.get(i).put("MESSAGE", DETAILS3);
                } else {
                    str = DETAILS2.substring(0, qian);
                    list.get(i).put("MESSAGE", str + "...");
                }
            }
            Map<String, Object> map = new HashedMap();
            if (page.getCurrentPage() == Integer.parseInt(pageNum)) {
                map.put("object", list);
            } else {
                List<PageData> list3 = new ArrayList();
                map.put("object", list3);
                message = "已加载全部数据!";
            }
            PageData pd_cd = cbannerimgService.findById(pd);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", message);
            pd.put("data", map);
            pd.put("IMG", pd_cd.getString("IMG3"));
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
        return str;
    }

    @RequestMapping(value = "findCartNestId", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findCartNestId(String CATNEST_ID, String USER_ID) {
        logBefore(logger, "查询猫窝帖子");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            PageData pd1 = cartNestService.findById(pd);
            if (pd1.getString("USER_ID").equals("17")) {
                pd1.put("STATUS", "1");
            } else if (appuserService.findJiFen(pd1) != null) {
                pd1.put("STATUS", "2");
            } else {
                pd1.put("STATUS", "3");
            }
            if (zan_CatnestService.findById(pd) != null) {
                pd1.put("ZAN", "1");
            } else {
                pd1.put("ZAN", "0");
            }
            String str = DateUtil.delHTMLTag(pd1.getString("MESSAGE"));
            String DETAILS1 = str.replace("\r", "");
            String DETAILS2 = DETAILS1.replace("\n", "");
            String DETAILS3 = DETAILS2.replace("&nbsp;", "");
            int qian = 20;
            if (DETAILS3.length() < 20) {
                pd1.put("JIANJIE", DETAILS3);
            } else {
                str = DETAILS2.substring(0, qian);
                pd1.put("JIANJIE", str + "...");
            }
            pd1.put("VIEWS", pd1.get("VIEWS").toString());
            pd1.put("HUIFU", pd1.get("HUIFU").toString());
            List<PageData> list_img = cartnest_ImgService.findList(pd);
            pd1.put("FIMG", list_img);
            cartNestService.editCick(pd);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("data", pd1);
            pd.put("url", "http://m.nongjike.cn/NJK/static/jsp/catnest.html?CATNEST_ID=" + CATNEST_ID);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错请联系管理员");
        }
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(pd);
        } catch (JsonGenerationException e) {

            e.printStackTrace();
        } catch (JsonMappingException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
        return str;
    }

    @RequestMapping(value = "findCartNestComment", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findCartNestComment(String CATNEST_ID, String pageNum, String USER_ID) {
        logBefore(logger, "查询猫窝帖子评论");
        PageData pd = new PageData();
        Page page = new Page();
        pd = this.getPageData();
        String message = "正确返回数据!";
        try {
            if (pageNum == null || pageNum.length() == 0) {
                pageNum = "1";
            }
            pd.put("STATUS", "0");
            page.setPd(pd);
            page.setShowCount(10);
            page.setCurrentPage(Integer.parseInt(pageNum));
            List<PageData> list = comment_CatnestService.datalistPage(page);
            for (int i = 0; i < list.size(); i++) {
                list.get(i).put("USER_ID1", USER_ID);
                if (zan_CommentCatnestService.findById(list.get(i)) != null) {
                    list.get(i).put("ZAN", "1");
                } else {
                    list.get(i).put("ZAN", "0");
                }
                List<PageData> list1 = comment_CatnestImgService.findList(list.get(i));
                list.get(i).put("CIMG", list1);
                list.get(i).put("STATUS", "1");
                List<PageData> list2 = comment_CatnestService.findList(list.get(i));
                for (int f = 0; f < list2.size(); f++) {
                    if (list2.get(f).getString("PNAME") == null) {
                        list2.get(f).put("PNAME", "");
                    }
                }
                list.get(i).put("COMMENT", list2);
                if (list.get(i).getString("USER_ID").equals("17")) {
                    list.get(i).put("STATUS", "1");
                } else if (appuserService.findJiFen(list.get(i)) != null) {
                    list.get(i).put("STATUS", "2");
                } else {
                    list.get(i).put("STATUS", "3");
                }
            }
            Map<String, Object> map = new HashedMap();
            if (page.getCurrentPage() == Integer.parseInt(pageNum)) {
                map.put("object", list);
            } else {
                List<PageData> list3 = new ArrayList();
                map.put("object", list3);
                message = "已加载全部数据!";
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("message", message);
            pd.put("data", map);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错请联系管理员");
        }
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(pd);
        } catch (JsonGenerationException e) {

            e.printStackTrace();
        } catch (JsonMappingException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
        return str;
    }

    //=====================================猫窝回复的回复=================================
    @RequestMapping(value = "CommentCartNestHuiFus", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String CommentCartNestHuiFus(String USER_ID, String CATNEST_ID, String MESSAGE, String PUSER_ID, String PID) {
        logBefore(logger, "回复评论回复");
        PageData pd = new PageData();
        pd = this.getPageData();
        ObjectMapper mapper = new ObjectMapper();
        try {
            PageData user = pullblackService.findByUserId(pd);
            if (null != user) {
                pd.clear();
                pd.put("code", "2");
                pd.put("message", "发送失败，请联系管理员！");
                return mapper.writeValueAsString(pd);
            }
            pd.put("NAME", "");
            pd.put("PNAME", "");
            pd.put("DATE", DateUtil.getTime());
            pd.put("STATUS", "1");
            pd.put("YUE", DateUtil.getDay());
            pd.put("PID", PID);
            pd.put("VIEWS", "0");
            comment_CatnestService.save(pd);
            pd.put("DATES", DateUtil.getTime());
            cartNestService.editDates(pd);
            pd.put("JIFEN", 0);
            appuserService.editJifen(pd);
            PageData pd_p = cartNestService.findById(pd);
            PageData pd_c = appuserService.findById111(pd);
            PageData pd_u = appuserService.findById222(pd);
            PageData pd1 = new PageData();
            pd1.put("NEWS_ID", this.get32UUID());
            pd1.put("ID", pd.getString("CATNEST_ID"));
            pd1.put("MESSAGE", pd.getString("MESSAGE"));
            pd1.put("NAME", pd_c.getString("NAME"));
            pd1.put("IMG", pd_c.getString("IMG"));
            pd1.put("DATE", DateUtil.getTime());
            pd1.put("USER_ID", pd.getString("PUSER_ID"));
            pd1.put("SUBJECT", pd_p.getString("SUBJECT"));
            pd1.put("STATUS", "3");
            pd1.put("USER_ID1", pd.getString("USER_ID"));
            pd1.put("url", "");
            newsService.save(pd1);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("message", "程序出错,请联系管理员!");
            pd.put("code", "2");
        }

        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
        } catch (JsonGenerationException e) {

            e.printStackTrace();
        } catch (JsonMappingException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
        return str;
    }

    //======================================猫窝的回复============================
    @RequestMapping(value = "CommentCartNestHuiFu", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String CommentCartNestHuiFu(String USER_ID, String CATNEST_ID, String MESSAGE, String PUSER_ID,
                                       String COMMENT_CATNEST_ID, String img1, String img2, String img3, String count) {
        logBefore(logger, "回复评论");
        PageData pd = new PageData();
        pd = this.getPageData();
        ObjectMapper mapper = new ObjectMapper();
        try {
            PageData user = pullblackService.findByUserId(pd);
            if (null != user) {
                pd.clear();
                pd.put("code", "2");
                pd.put("message", "发送失败，请联系管理员！");
                return mapper.writeValueAsString(pd);
            }
            pd.put("NAME", "");
            pd.put("PNAME", "");
            pd.put("DATE", DateUtil.getTime());
            pd.put("STATUS", "1");
            pd.put("YUE", DateUtil.getDay());
            pd.put("PID", COMMENT_CATNEST_ID);
            pd.put("VIEWS", "0");
            comment_CatnestService.save(pd);
            pd.put("DATES", DateUtil.getTime());
            cartNestService.editDates(pd);
            pd.put("JIFEN", 0);
            appuserService.editJifen(pd);
            pd.put("COMMENT_CATNEST_ID", COMMENT_CATNEST_ID);
            PageData pd_com = comment_CatnestService.findById(pd);
            PageData pd_c = cartNestService.findById(pd_com);
            PageData pd1 = new PageData();
            pd1.put("NEWS_ID", this.get32UUID());
            pd1.put("ID", CATNEST_ID);
            pd1.put("MESSAGE", pd.getString("MESSAGE"));
            pd1.put("NAME", pd_c.getString("NAME"));
            pd1.put("IMG", pd_c.getString("IMG"));
            pd1.put("DATE", DateUtil.getTime());
            pd1.put("USER_ID", pd_c.getString("USER_ID"));
            pd1.put("SUBJECT", pd_c.getString("SUBJECT"));
            pd1.put("STATUS", "3");
            pd1.put("USER_ID1", pd.getString("USER_ID"));
            pd1.put("url", "");
            newsService.save(pd1);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("message", "程序出错,请联系管理员!");
            pd.put("code", "2");
        }
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
        } catch (JsonGenerationException e) {

            e.printStackTrace();
        } catch (JsonMappingException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
        return str;
    }

    @RequestMapping(value = "CommentCartNest", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String CommentCartNest(String USER_ID, String CATNEST_ID, String MESSAGE, String img1, String img2, String img3,
                                  String count, String STATUS) {
        logBefore(logger, "评论猫窝帖子");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            pd.put("NAME", "");
            pd.put("PUSER_ID", "");
            pd.put("PNAME", "");
            pd.put("DATE", DateUtil.getTime());
            pd.put("STATUS", "0");
            pd.put("YUE", DateUtil.getDay());
            pd.put("PID", "");
            pd.put("VIEWS", "0");
            comment_CatnestService.save(pd);
            Integer COMMENT_CATNEST_ID = Integer.valueOf(pd.get("COMMENT_CATNEST_ID").toString());
            Integer count1 = Integer.valueOf(count);
            String ffile1 = this.get32UUID() + ".jpg";
            String filePath2 = PathUtil.getClasspath() + Const.FILEPATHIMG + "mpinglun/" + DateUtil.getDays(); // 文件上传路径
            File file = new File(filePath2, ffile1);
            if (!file.exists()) {
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
            }
            for (int i = 0; i < count1; i++) {
                String ffile = this.get32UUID() + ".jpg";
                String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + "mpinglun/" + DateUtil.getDays() + "/"
                        + ffile; // 文件上传路径
                String TOUR_IMG1 = Const.SERVERPATH + Const.FILEPATHIMG + "mpinglun/" + DateUtil.getDays() + "/" + ffile;
                String ii = String.valueOf(i + 1);
                PageData pd_i = new PageData();
                pd_i.put("COMMENT_CATNEST_ID", COMMENT_CATNEST_ID);
                pd_i.put("IMG", TOUR_IMG1);
                pd_i.put("DATE", DateUtil.getTime());
                comment_CatnestImgService.save(pd_i);
                boolean flag = ImageAnd64Binary.generateImage(pd.getString("img" + ii), filePath);
            }
            pd.put("DATES", DateUtil.getTime());
            cartNestService.editHuiFu(pd);
            pd.put("JIFEN", 0);
            appuserService.editJifen(pd);
            PageData pd_c = cartNestService.findById(pd);
            PageData pd1 = new PageData();
            pd1.put("NEWS_ID", this.get32UUID());
            pd1.put("ID", pd.getString("CATNEST_ID"));
            pd1.put("MESSAGE", pd.getString("MESSAGE"));
            pd1.put("NAME", pd_c.getString("NAME"));
            pd1.put("IMG", pd_c.getString("IMG"));
            pd1.put("DATE", DateUtil.getTime());
            pd1.put("USER_ID", pd_c.getString("USER_ID"));
            pd1.put("SUBJECT", pd_c.getString("MESSAGE"));
            pd1.put("STATUS", "3");
            pd1.put("USER_ID1", pd.getString("USER_ID"));
            pd1.put("url", "");
            newsService.save(pd1);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "评论成功");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(pd);
        } catch (JsonGenerationException e) {

            e.printStackTrace();
        } catch (JsonMappingException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
        return str;
    }

    @RequestMapping(value = "CommentXCartNest", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String CommentXCartNest(String USER_ID, String CATNEST_ID, String MESSAGE, String img1, String img2, String img3,
                                   String count, String STATUS) {
        logBefore(logger, "评论猫窝帖子");
        PageData pd = new PageData();
        pd = this.getPageData();
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            PageData user = pullblackService.findByUserId(pd);
            if (null != user) {
                pd.clear();
                pd.put("code", "2");
                pd.put("message", "发送失败，请联系管理员！");
                return mapper.writeValueAsString(pd);
            }
            pd.put("NAME", "");
            pd.put("PUSER_ID", "");
            pd.put("PNAME", "");
            pd.put("DATE", DateUtil.getTime());
            pd.put("STATUS", "0");
            pd.put("YUE", DateUtil.getDay());
            pd.put("PID", "");
            pd.put("VIEWS", "0");
            comment_CatnestService.save(pd);
            Integer COMMENT_CATNEST_ID = Integer.valueOf(pd.get("COMMENT_CATNEST_ID").toString());
            Integer count1 = Integer.valueOf(count);
            for (int i = 0; i < count1; i++) {
                String ii = String.valueOf(i + 1);
                PageData pd_i = new PageData();
                pd_i.put("COMMENT_CATNEST_ID", COMMENT_CATNEST_ID);
                pd_i.put("IMG", pd.getString("img" + ii));
                pd_i.put("DATE", DateUtil.getTime());
                comment_CatnestImgService.save(pd_i);
            }
            pd.put("DATES", DateUtil.getTime());
            cartNestService.editHuiFu(pd);
            pd.put("JIFEN", 0);
            appuserService.editJifen(pd);
            PageData pd_c = cartNestService.findById(pd);
            PageData pd1 = new PageData();
            pd1.put("NEWS_ID", this.get32UUID());
            pd1.put("ID", pd.getString("CATNEST_ID"));
            pd1.put("MESSAGE", pd.getString("MESSAGE"));
            pd1.put("NAME", pd_c.getString("NAME"));
            pd1.put("IMG", pd_c.getString("IMG"));
            pd1.put("DATE", DateUtil.getTime());
            pd1.put("USER_ID", pd_c.getString("USER_ID"));
            pd1.put("SUBJECT", pd_c.getString("SUBJECT"));
            pd1.put("STATUS", "3");
            pd1.put("USER_ID1", pd.getString("USER_ID"));
            pd1.put("url", "");
            newsService.save(pd1);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "评论成功");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        try {
            str = mapper.writeValueAsString(pd);
        } catch (JsonGenerationException e) {

            e.printStackTrace();
        } catch (JsonMappingException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
        return str;
    }


    @RequestMapping(value = "saveXCartNest", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String saveXCartNest(String MESSAGE, String USER_ID, String SUBJECT, String img1, String img2,
                                String img3, String img4, String img5, String img6, String count) {
        logBefore(logger, "添加猫窝帖子");
        PageData pd = new PageData();
        pd = this.getPageData();
        List<PageData> list_rimg = new ArrayList();
        try {
            PageData user = pullblackService.findByUserId(pd);
            if (null == user) {
                PageData pd1 = appuserService.findById111(pd);
                pd.put("NAME", pd1.getString("NAME"));
                pd.put("DATE", DateUtil.getTime());
                pd.put("STATUS", "0");
                pd.put("FABU", "1");
                String MESSAGE1 = MESSAGE.replace("!==!", "<br/>");
                MESSAGE1 = MESSAGE1.replace("\n", "<br/>");
                Integer count1 = Integer.valueOf(count);
                for (int i = 0; i < count1; i++) {
                    String ii = String.valueOf(i + 1);
                    MESSAGE1 += "<br/><img src=" + pd.getString("img" + ii) + "/><br/>";
                    PageData pd_a = new PageData();
                    pd_a.put("IMG", pd.getString("img" + ii));
                    pd_a.put("ORDE_BY", ii);
                    pd_a.put("DATE", DateUtil.getTime());
                    list_rimg.add(pd_a);
                }
                pd.put("MESSAGE", "<style>img{max-width:100%;}</style><p>" + MESSAGE1 + "</p>");
                pd.put("DATES", pd.getString("DATE"));
                pd.put("ZSTATUS", "0");
                pd.put("CLICK", "0");
                pd.put("VIEWS", "0");
                pd.put("HUIFU", "0");
                cartNestService.save(pd);
                if (!count.equals("0")) {
                    Integer CATNEST_ID = Integer.valueOf(pd.get("CATNEST_ID").toString());
                    Map<String, Object> map = new HashedMap();
                    map.put("list", list_rimg);
                    map.put("CATNEST_ID", CATNEST_ID);
                    cartnest_ImgService.saves(map);
                }
                pd.put("JIFEN", 0);
                appuserService.editJifen(pd);
                pd.clear();
                pd.put("code", "1");
                pd.put("message", "正确返回数据!");
            } else {
                pd.clear();
                pd.put("code", "2");
                pd.put("message", "发送失败，请联系管理员！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("message", "程序出错,请联系管理员!");
            pd.put("code", "2");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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


    @RequestMapping(value = "saveCartNest", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String saveCartNest(String MESSAGE, String USER_ID, String SUBJECT, String img1, String img2,
                               String img3, String img4, String img5, String img6, String count) {
        logBefore(logger, "添加猫窝帖子");
        PageData pd = new PageData();
        pd = this.getPageData();
        List<PageData> list_rimg = new ArrayList();
        try {
            PageData pd1 = appuserService.findById111(pd);
            pd.put("NAME", pd1.getString("NAME"));
            pd.put("DATE", DateUtil.getTime());
            pd.put("STATUS", "0");
            pd.put("FABU", "1");
            String MESSAGE1 = MESSAGE.replace("!==!", "<br/>");
            MESSAGE1 = MESSAGE1.replace("\n", "<br/>");
            Integer count1 = Integer.valueOf(count);
            String ffile1 = this.get32UUID() + ".jpg";
            String filePath2 = PathUtil.getClasspath() + Const.FILEPATHIMG + "mtiezi/" + DateUtil.getDays(); // 文件上传路径
            File file = new File(filePath2, ffile1);
            if (!file.exists()) {
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
            }
            for (int i = 0; i < count1; i++) {
                String ffile = this.get32UUID() + ".jpg";
                String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + "mtiezi/" + DateUtil.getDays() + "/"
                        + ffile; // 文件上传路径
                String TOUR_IMG1 = Const.SERVERPATH + Const.FILEPATHIMG + "mtiezi/" + DateUtil.getDays() + "/" + ffile;
                MESSAGE1 += "<br/><img src=" + TOUR_IMG1 + "/><br/>";
                String ii = String.valueOf(i + 1);
                PageData pd_a = new PageData();
                pd_a.put("IMG", TOUR_IMG1);
                pd_a.put("ORDE_BY", ii);
                pd_a.put("DATE", DateUtil.getTime());
                list_rimg.add(pd_a);
                boolean flag = ImageAnd64Binary.generateImage(pd.getString("img" + ii), filePath);
            }
            pd.put("MESSAGE", "<style>img{max-width:100%;}</style><p>" + MESSAGE1 + "</p>");
            pd.put("DATES", pd.getString("DATE"));
            pd.put("ZSTATUS", "0");
            pd.put("CLICK", "0");
            pd.put("VIEWS", "0");
            pd.put("HUIFU", "0");
            cartNestService.save(pd);
            if (!count.equals("0")) {
                Integer CATNEST_ID = Integer.valueOf(pd.get("CATNEST_ID").toString());
                Map<String, Object> map = new HashedMap();
                map.put("list", list_rimg);
                map.put("CATNEST_ID", CATNEST_ID);
                cartnest_ImgService.saves(map);
            }
            pd.put("JIFEN", 0);
            appuserService.editJifen(pd);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("message", "程序出错,请联系管理员!");
            pd.put("code", "2");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
        } catch (JsonGenerationException e) {

            e.printStackTrace();
        } catch (JsonMappingException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
        return str;
    }

    @RequestMapping(value = "ZanComentCatnest", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String ZanComentCatnest(String USER_ID, String COMMENT_CATNEST_ID, String STATUS) {
        logBefore(logger, "点赞猫窝帖子");
        PageData pd = new PageData();
        pd = this.getPageData();
        String STATUS1 = "0";
        try {
            if (STATUS.equals("1")) {
                if (zan_CommentCatnestService.findById(pd) == null) {
                    zan_CommentCatnestService.save(pd);
                    comment_CatnestService.editViews(pd);
                    pd.put("JIFEN", 0);
                    appuserService.editJifen(pd);
                }
                STATUS1 = "1";
            } else {
                if (zan_CommentCatnestService.findByIds(pd) != null) {
                    zan_CommentCatnestService.delete(pd);
                    comment_CatnestService.editViewss(pd);
                }
            }
            PageData pd_r = comment_CatnestService.findById(pd);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("STATUS", STATUS1);
            pd.put("VIEWS", pd_r.get("VIEWS").toString());
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("message", "程序出错,请联系管理员!");
            pd.put("code", "2");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
        } catch (JsonGenerationException e) {

            e.printStackTrace();
        } catch (JsonMappingException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
        return str;
    }


    @RequestMapping(value = "ZanCatnest", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String ZanCatnest(String USER_ID, String CATNEST_ID, String STATUS) {
        logBefore(logger, "点赞猫窝帖子");
        PageData pd = new PageData();
        pd = this.getPageData();
        String STATUS1 = "0";
        try {
            if (STATUS.equals("1")) {
                if (zan_CatnestService.findById(pd) == null) {
                    zan_CatnestService.save(pd);
                    cartNestService.editViews(pd);
                    pd.put("JIFEN", 0);
                    appuserService.editJifen(pd);
                }
                STATUS1 = "1";
            } else {
                if (zan_CatnestService.findById(pd) != null) {
                    zan_CatnestService.delete(pd);
                    cartNestService.editViewss(pd);
                }
            }
            PageData pd_r = cartNestService.findById(pd);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("STATUS", STATUS1);
            pd.put("VIEWS", pd_r.get("VIEWS").toString());
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("message", "程序出错,请联系管理员!");
            pd.put("code", "2");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
        } catch (JsonGenerationException e) {

            e.printStackTrace();
        } catch (JsonMappingException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
        return str;
    }

    @RequestMapping(value = "ZanLeavingMessage", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String ZanLeavingMessage(String USER_ID, String LEAVING_MESSAGE_ID, String STATUS) {
        logBefore(logger, "点赞帖子");
        PageData pd = new PageData();
        pd = this.getPageData();
        String STATUS1 = "0";
        pd.put("DATE", DateUtil.getTime());
        try {
            if (STATUS.equals("1")) {
                if (zan_leaving_messageService.findById(pd) == null) {
                    zan_leaving_messageService.save(pd);
                    leaving_MessageService.editViewsJia(pd);
                    pd.put("JIFEN", 0);
                    appuserService.editJifen(pd);
                }
                STATUS1 = "1";
            } else {
                if (zan_leaving_messageService.findById(pd) != null) {
                    zan_leaving_messageService.delete(pd);
                    leaving_MessageService.editViewsJian(pd);
                }
            }
            PageData pd_r = leaving_MessageService.findById(pd);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("STATUS", STATUS1);
            pd.put("VIEWS", pd_r.get("VIEWS").toString());
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("message", "程序出错,请联系管理员!");
            pd.put("code", "2");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
        } catch (JsonGenerationException e) {

            e.printStackTrace();
        } catch (JsonMappingException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
        return str;
    }

    @RequestMapping(value = "ZanCommentLeavingMessage", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String ZanCommentLeavingMessage(String USER_ID, String COMMENT_LEAVING_MESSAGE_ID, String STATUS) {
        logBefore(logger, "点赞帖子评论");
        PageData pd = new PageData();
        pd = this.getPageData();
        String STATUS1 = "0";
        pd.put("DATE", DateUtil.getTime());
        try {
            if (STATUS.equals("1")) {
                if (zan_comment_leaving_messageService.findById(pd) == null) {
                    zan_comment_leaving_messageService.save(pd);
                    comment_LeavingMessageService.editViewsJia(pd);
                    pd.put("JIFEN", 0);
                    appuserService.editJifen(pd);
                }
                STATUS1 = "1";
            } else {
                if (zan_comment_leaving_messageService.findById(pd) != null) {
                    zan_comment_leaving_messageService.delete(pd);
                    comment_LeavingMessageService.editViewsJian(pd);
                }
            }
            PageData pd_r = comment_LeavingMessageService.findById(pd);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("STATUS", STATUS1);
            pd.put("VIEWS", pd_r.get("VIEWS").toString());
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("message", "程序出错,请联系管理员!");
            pd.put("code", "2");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
        } catch (JsonGenerationException e) {

            e.printStackTrace();
        } catch (JsonMappingException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
        return str;
    }

    @RequestMapping(value = "WeiXinResearcs", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String WeiXinResearcs(String PRODUCT_ID, String url) {
        logBefore(logger, "微信分享查询商品研究院");
        PageData pd = new PageData();
        pd = this.getPageData();
        String jsapiTicket = "";
        try {
            PageData pd12 = weiXinService.findById(pd);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long difference = format.parse(format.format(new Date())).getTime()
                    - format.parse(pd12.getString("DATE")).getTime();
            if (difference > 360000) { // 标签超时,重新到微信服务器请求标签超时时间为7200秒（7200000毫秒）
                String access_token = WeiXin.access_token();
                jsapiTicket = WeiXin.jsapiTicket(access_token);
                pd12.put("ticket", jsapiTicket);
                pd12.put("DATE", DateUtil.getTime());
                weiXinService.edit(pd12);
            } else {
                jsapiTicket = pd12.getString("ticket");
            }
            Map<String, String> cf = WeiXin.sign1(jsapiTicket, url);
            PageData pd_r = productService.findById(pd);
            List<PageData> list = researchService.findZResearchTop(pd);
            List<PageData> lists = researchService.findList(pd);
            for (int i = 0; i < lists.size(); i++) {
                String str = DateUtil.delHTMLTag(lists.get(i).getString("MESSAGE"));
                String DETAILS1 = str.replace("\r", "");
                String DETAILS2 = DETAILS1.replace("\n", "");
                String DETAILS3 = DETAILS2.replace("&nbsp;", "");
                lists.get(i).put("JIANJIE", DETAILS3);
                List<PageData> list_img = research_ImgService.findList(lists.get(i));
                lists.get(i).put("FIMG", list_img);
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("signature", cf.get("signature"));
            pd.put("nonceStr", cf.get("nonceStr"));
            pd.put("timestamp", cf.get("timestamp"));
            pd.put("url", cf.get("url"));
            pd.put("appId", "wx6b912be972e69695");
            pd.put("list", lists);
            pd.put("top", list);
            pd.put("research", pd_r);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
        } catch (JsonGenerationException e) {

            e.printStackTrace();
        } catch (JsonMappingException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
        return str;
    }


    @RequestMapping(value = "findUserCatnest", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findUserCatnest(String USER_ID, String pageNum, String YSTATUS) {
        logBefore(logger, "查询自己的猫窝帖子");
        PageData pd = new PageData();
        pd = this.getPageData();
        Page page = new Page();
        String message = "正确返回数据!";
        try {
            PageData pd_ci = cbannerimgService.findById(pd);
            page.setPd(pd);
            page.setShowCount(6);
            page.setCurrentPage(Integer.parseInt(pageNum));
            List<PageData> list = cartNestService.userlistPage(page);
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getString("USER_ID").equals("17")) {
                    list.get(i).put("STATUS", "1");
                } else if (appuserService.findJiFen(list.get(i)) != null) {
                    list.get(i).put("STATUS", "2");
                } else {
                    list.get(i).put("STATUS", "3");
                }
                if (cartNestService.findClick(list.get(i)) != null) {
                    list.get(i).put("RSTATUS", "1");
                } else {
                    list.get(i).put("RSTATUS", "0");
                }
                List<PageData> f_list = cartnest_ImgService.findList(list.get(i));
                list.get(i).put("FIMG", f_list);

                String str = DateUtil.delHTMLTag(list.get(i).getString("MESSAGE"));
                String DETAILS1 = str.replace("\r", "");
                String DETAILS2 = DETAILS1.replace("\n", "");
                String DETAILS3 = DETAILS2.replace("&nbsp;", "");
                int qian = 20;
                if (DETAILS3.length() < 20) {
                    list.get(i).put("MESSAGE", DETAILS3);
                } else {
                    str = DETAILS2.substring(0, qian);
                    list.get(i).put("MESSAGE", str + "...");
                }

            }
            Map<String, Object> map = new HashedMap();
            if (page.getCurrentPage() == Integer.parseInt(pageNum)) {
                map.put("object", list);
            } else {
                List<PageData> list3 = new ArrayList();
                map.put("object", list3);
                message = "已加载全部数据!";
            }
            PageData user = appuserService.findById(pd);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", message);
            pd.put("user", user);
            pd.put("data", map);
            pd.put("IMG", pd_ci.getString("IMG4"));
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
        } catch (JsonGenerationException e) {

            e.printStackTrace();
        } catch (JsonMappingException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
        return str;
    }

    @RequestMapping(value = "editUserName", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String editUserName(String USER_ID, String NAME, String QIANMING) {
        logBefore(logger, "修改个人信息name和qianming");
        PageData pd = new PageData();
        pd = this.getPageData();
        Page page = new Page();
        String message = "正确返回数据!";

        try {
            if (null != pd.getString("NAME")) {
                appuserService.editName(pd);
            } else if (null != pd.getString("QIANMING")) {
                appUserInfoService.editQianMing(pd);
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("message", message);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    @RequestMapping(value = "findCatnest", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findCatnest(String USER_ID, String pageNum, String YSTATUS) {
        logBefore(logger, "查询猫窝帖子");
        PageData pd = new PageData();
        pd = this.getPageData();
        Page page = new Page();
        String message = "正确返回数据!";
        try {
            PageData pd_ci = cbannerimgService.findById(pd);
            page.setPd(pd);
            page.setShowCount(6);
            page.setCurrentPage(Integer.parseInt(pageNum));
            List<PageData> list = cartNestService.datalistPage(page);
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getString("USER_ID").equals("17")) {
                    list.get(i).put("STATUS", "1");
                } else if (appuserService.findJiFen(list.get(i)) != null) {
                    list.get(i).put("STATUS", "2");
                } else {
                    list.get(i).put("STATUS", "3");
                }
                if (cartNestService.findClick(list.get(i)) != null) {
                    list.get(i).put("RSTATUS", "1");
                } else {
                    list.get(i).put("RSTATUS", "0");
                }
                List<PageData> f_list = cartnest_ImgService.findList(list.get(i));
                list.get(i).put("FIMG", f_list);

                String str = DateUtil.delHTMLTag(list.get(i).getString("MESSAGE"));
                String DETAILS1 = str.replace("\r", "");
                String DETAILS2 = DETAILS1.replace("\n", "");
                String DETAILS3 = DETAILS2.replace("&nbsp;", "");
                int qian = 20;
                if (DETAILS3.length() < 20) {
                    list.get(i).put("MESSAGE", DETAILS3);
                } else {
                    str = DETAILS2.substring(0, qian);
                    list.get(i).put("MESSAGE", str + "...");
                }

            }
            Map<String, Object> map = new HashedMap();
            if (page.getCurrentPage() == Integer.parseInt(pageNum)) {
                map.put("object", list);
            } else {
                List<PageData> list3 = new ArrayList();
                map.put("object", list3);
                message = "已加载全部数据!";
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("message", message);
            pd.put("data", map);
            pd.put("IMG1", pd_ci.getString("IMG1"));
            pd.put("IMG2", pd_ci.getString("IMG2"));
            pd.put("url", "http://m.nongjike.cn/NJK/static/jsp/findCatnest.html?USER_ID=" + USER_ID);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    @RequestMapping(value = "WeiXinResearc", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String WeiXinResearc(String RESEARCH_ID, String url) {
        logBefore(logger, "微信分享查询研究院帖子");
        PageData pd = new PageData();
        pd = this.getPageData();
        String jsapiTicket = "";
        try {
            PageData pd12 = weiXinService.findById(pd);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long difference = format.parse(format.format(new Date())).getTime()
                    - format.parse(pd12.getString("DATE")).getTime();
            if (difference > 360000) { // 标签超时,重新到微信服务器请求标签超时时间为7200秒（7200000毫秒）
                String access_token = WeiXin.access_token();
                jsapiTicket = WeiXin.jsapiTicket(access_token);
                pd12.put("ticket", jsapiTicket);
                pd12.put("DATE", DateUtil.getTime());
                weiXinService.edit(pd12);
            } else {
                jsapiTicket = pd12.getString("ticket");
            }
            Map<String, String> cf = WeiXin.sign1(jsapiTicket, url);
            PageData pd_r = researchService.findById(pd);
            String str = DateUtil.delHTMLTag(pd_r.getString("MESSAGE"));
            String DETAILS1 = str.replace("\r", "");
            String DETAILS2 = DETAILS1.replace("\n", "");
            int qian = 56;
            if ("1".equals(pd_r.get("LSTATUS").toString())) {
                pd_r.put("PIMG", productService.findById(pd).getString("IMG"));
            } else if ("2".equals(pd_r.get("LSTATUS").toString())) {
                //pd.put("ORDE_BY","1");
                pd_r.put("PIMG", research_ImgService.findHuoDongImg(pd).get(0).getString("IMG"));
            } else {
                if (str.length() < 56) {
                    pd_r.put("JIANJIE", DETAILS2);
                } else {
                    str = DETAILS2.substring(0, qian);
                    pd_r.put("JIANJIE", str + "...");
                }
            }
            pd.put("STATUS", "0");
            List<PageData> list = comment_ResearchService.findList1(pd);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("signature", cf.get("signature"));
            pd.put("nonceStr", cf.get("nonceStr"));
            pd.put("timestamp", cf.get("timestamp"));
            pd.put("url", cf.get("url"));
            pd.put("researc", pd_r);
            pd.put("appId", "wx6b912be972e69695");
            pd.put("comm", list);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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
     * 获取当前用户的积分获取以及消费的记录
     *
     * @param USER_ID 用户ID
     * @param pageNum 分页页码
     * @return
     */
    @RequestMapping(value = "getCountByUser", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getCountByUser(String USER_ID, String pageNum, String STATUS) {
        logBefore(logger, "获取当前用户积分统计信息");
        PageData pd = new PageData();
        List<PageData> counts = new ArrayList<PageData>();
        Page page = new Page();
        String message = "";
        Map<String, Object> map = new HashedMap();
        try {
            pd = this.getPageData();
            page.setPd(pd);
            page.setShowCount(10);
            page.setCurrentPage(Integer.parseInt(pageNum));
            if ("0".equals(STATUS)) {
                counts = countCommunityService.datalistPage(page);
                if (page.getCurrentPage() == Integer.parseInt(pageNum)) {
                    for (PageData p : counts) {
                        p.put("ID", p.get("ID").toString());
                        p.put("PRISE", "");
                    }
                    map.put("object", counts);
                    message = "正确返回数据!";
                } else {
                    List<PageData> list3 = new ArrayList();
                    map.put("object", list3);
                    message = "已加载全部数据!";
                }
            } else if ("1".equals(STATUS)) {
                counts = countCommunityService.USERdatalistPage(page);
                if (page.getCurrentPage() == Integer.parseInt(pageNum)) {
                    for (PageData p : counts) {
                        p.put("ID", p.get("ID").toString());
                    }
                    for (PageData p : counts) {
                        String[] a = p.getString("ORDER_NUM").split(",");
                        p.put("ORDER_NUM", a[0]);
                        if (a.length >= 2) {
                            p.put("PRISE", a[1]);
                        } else {
                            p.put("PRISE", "");
                        }
                    }
                    map.put("object", counts);
                    message = "正确返回数据!";
                } else {
                    List<PageData> list3 = new ArrayList();
                    map.put("object", list3);
                    message = "已加载全部数据!";
                }
            }
            PageData user = appuserService.findById(pd);
            map.put("user", user);
            pd.clear();
            pd.put("code", "1");
            pd.put("data", map);
            pd.put("message", message);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("message", "程序出错,请联系管理员!");
            pd.put("code", "2");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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
     * 查询积分商品详情
     *
     * @param JIFENSHOP_ID 商品ID
     * @return
     */
    @RequestMapping(value = "findShopById", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findShopById(String JIFENSHOP_ID) {
        logBefore(logger, "查询积分商品详情");
        PageData pd = new PageData();
        String message = "";
        try {
            pd = this.getPageData();
            PageData shop = jifenShopService.findById(pd);
            String str = DateUtil.delHTMLTag(shop.getString("JIFEN_MIAOSHU"));
            String DETALLS1 = str.replace("\r", "");
            String DETALLS2 = DETALLS1.replace("\n", "");
            String DETALLS3 = DETALLS2.replace("&nbsp;", "");
            shop.put("JIESHAO", DETALLS3);
            String nstr = shop.getString("JIFEN_MIAOSHU").replace(DETALLS3, "");
            shop.put("JIFEN_MIAOSHU", nstr);
            pd.clear();
            pd.put("code", "1");
            pd.put("shop", shop);
            pd.put("message", "正确返回数据！");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "" +
                    "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }


    /**
     * 查询个人极客币
     *
     * @param USER_ID 用户ID
     * @return
     */
    @RequestMapping(value = "getUserJifen", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getUserJifen(String USER_ID) {
        logBefore(logger, "查询个人极客币");
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            PageData user = appuserService.findById(pd);
            pd.clear();
            pd.put("code", "1");
            pd.put("user", user);
            pd.put("message", "正确返回数据!");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "程序出错,请联系管理员!");

        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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
     * 兑换积分商城商品
     *
     * @param USER_ID      兑换的用户ID
     * @param JIFENSHOP_ID 兑换的商品ID
     * @return
     */
    @RequestMapping(value = "payShoping", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String payShoping(String USER_ID, String JIFENSHOP_ID) {
        logBefore(logger, "积分兑换商品");
        PageData pd = new PageData();
        String message = "";
        try {
            pd = this.getPageData();
            PageData shop = jifenShopService.findById(pd);
            int soc = Integer.parseInt(shop.get("JIFENSHOP_SCO").toString());
            PageData user = appuserService.findById(pd);
            int soc1 = Integer.parseInt(user.get("JIFEN").toString());
            int num = 0;
            PageData pd_a = addressService.findById(pd);
            if (null != pd_a.getString("ADDRESS") && !"".equals(pd_a.getString("ADDRESS"))) {
                if (soc1 >= soc) {
                    num = soc1 - soc;
                    user.put("JIFEN", num + "");
                    pd.put("community_id", JIFENSHOP_ID);
                    pd.put("community_type", "6");
                    pd.put("NUM", soc + "");
                    pd.put("create_date", DateUtil.getDay());
                    pd.put("MIAOSHU", "兑换商品" + shop.getString("JIFENSHOP_NAME"));
                    pd.put("ORDER_NUM", "");
                    countCommunityService.save(pd);
                    appuserService.editJifenJ(user);
                    message = "正确返回数据!";
                    pd.clear();
                    pd.put("code", "1");
                    pd.put("message", message);
                } else {
                    pd.clear();
                    pd.put("code", "2");
                    message = "您的积分不足以兑换此物品!";
                    pd.put("message", message);
                }
            } else {
                pd.clear();
                pd.put("code", "2");
                message = "请在个人中心设置中填写收货地址!";
                pd.put("message", message);
            }
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("message", "程序出错,请联系管理员!");
            pd.put("code", "2");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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


    /** 获取积分商品列表
     *
     * @param pageNum 分页页码
     * @return
     */
  /*  @RequestMapping(value="getShop",produces ="text/html;charset=UTF-8" )
    @ResponseBody
    public String getShop(String pageNum,String USER_ID){
        logBefore(logger, "获取积分商品列表");
        PageData pd = new PageData();
        List<PageData> shop = new ArrayList<PageData>();
        Page page = new Page();
        String message ="";
        try{
            pd = this.getPageData();
            page.setPd(pd);
            page.setShowCount(10);
            page.setCurrentPage(Integer.parseInt(pageNum));
            shop=jifenShopService.datalistPage(page);
            Map<String, Object> map = new HashedMap();
            if (page.getCurrentPage() == Integer.parseInt(pageNum)) {
                map.put("object", shop);
                message = "正确返回数据!";
            } else {
                List<PageData> list3 = new ArrayList();
                map.put("object", list3);
                message = "已加载全部数据!";
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("data", map);
            pd.put("message", message);
        }catch (Exception e){
            e.printStackTrace();
            pd.clear();
            pd.put("message", "程序出错,请联系管理员!");
            pd.put("code", "2");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }*/


    /**
     * 获取积分商品列表
     *
     * @param pageNum 分页页码
     * @return
     */
    @RequestMapping(value = "getShopGoodsList", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getShopGoodsList(String pageNum, String USER_ID) {
        logBefore(logger, "获取积分商品列表");
        PageData pd = new PageData();
        List<PageData> shop = new ArrayList<PageData>();
        PageData banner = new PageData();
        Page page = new Page();
        String message = "";
        try {
            pd = this.getPageData();
            page.setPd(pd);
            page.setShowCount(10);
            page.setCurrentPage(Integer.parseInt(pageNum));
            shop = jifenShopService.datalistPage(page);
            banner = jifenShopService.findShopById(pd);
            Map<String, Object> map = new HashedMap();
            if (page.getCurrentPage() == Integer.parseInt(pageNum)) {
                map.put("object", shop);
                message = "正确返回数据!";
            } else {
                List<PageData> list3 = new ArrayList();
                map.put("object", list3);
                message = "已加载全部数据!";
            }
            map.put("banner", banner);
            pd.clear();
            pd.put("code", "1");
            pd.put("data", map);
            pd.put("message", message);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("message", "程序出错,请联系管理员!");
            pd.put("code", "2");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }


    @RequestMapping(value = "saveResearch", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String saveResearch(String MESSAGE, String USER_ID, String SUBJECT, String img1, String img2,
                               String img3, String img4, String img5, String img6, String PRODUCT_ID, String count, String LAT, String LNG) {
        logBefore(logger, "添加研究院帖子");
        PageData pd = new PageData();
        pd = this.getPageData();
        List<PageData> list_rimg = new ArrayList();
        String inform = "";
        try {
            PageData pd1 = appuserService.findById111(pd);
            pd.put("NAME", pd1.getString("NAME"));
            pd.put("DATE", DateUtil.getTime());
            pd.put("STATUS", "0");
            pd.put("FABU", "1");
            pd.put("TSTATUS", "0");
            String MESSAGE1 = MESSAGE.replace("!==!", "<br/>");
            MESSAGE1 = MESSAGE1.replace("\n", "<br/>");
            Integer count1 = Integer.valueOf(count);
            String ffile1 = this.get32UUID() + ".jpg";
            String filePath2 = PathUtil.getClasspath() + Const.FILEPATHIMG + "ttiezi/" + DateUtil.getDays(); // 文件上传路径
            File file = new File(filePath2, ffile1);
            if (!file.exists()) {
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
            }
            for (int i = 0; i < count1; i++) {
                String ffile = this.get32UUID() + ".jpg";
                String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + "ttiezi/" + DateUtil.getDays() + "/"
                        + ffile; // 文件上传路径
                String TOUR_IMG1 = Const.SERVERPATH + Const.FILEPATHIMG + "ttiezi/" + DateUtil.getDays() + "/" + ffile;
                MESSAGE1 += "<br/><img src=" + TOUR_IMG1 + "/><br/>";
                String ii = String.valueOf(i + 1);
                PageData pd_a = new PageData();
                pd_a.put("IMG", TOUR_IMG1);
                pd_a.put("ORDE_BY", ii);
                pd_a.put("DATE", DateUtil.getTime());
                list_rimg.add(pd_a);
                boolean flag = ImageAnd64Binary.generateImage(pd.getString("img" + ii), filePath);
            }
            pd.put("MESSAGE", "<style>img{max-width:100%;}</style><p>" + MESSAGE1 + "</p>");
            pd.put("DATES", pd.getString("DATE"));
            pd.put("LSTATUS", "0");
            pd.put("JIANJIE", "");
            pd.put("SHENCHA", "0");
            String ss = "";
            if (LAT != null && LAT.length() > 4 && LNG != null && LNG.length() > 4) {
                ss = MapDistance.getCoordinate3(LNG, LAT);
            } else {
                ss = "农极客";
            }
            pd.put("ADDRESS", ss);
            researchService.save(pd);

            //*****************增加用户积分*****************
            String today = DateUtil.getDay();
            PageData comDto = new PageData();
            comDto.put("community_type", "3");
            comDto.put("create_date", today);
            comDto.put("USER_ID", USER_ID);
            PageData product1 = productService.findById(pd);
            pd.put("JIFEN", 3);
            appuserService.editJifen(pd);
            appuserService.addZJIFEN(pd);
            comDto.put("NUM", "3");
            comDto.put("community_id", pd.get("RESEARCH_ID").toString());
            comDto.put("MIAOSHU", "发布" + product1.getString("PRODUCT_NAME") + "商品研究院帖子");
            comDto.put("ORDER_NUM", "");
            countCommunityService.save(comDto);
            inform = "发布成功，获取有效积分+3";
            //*****************end********************

            if (!count.equals("0")) {
                Integer RESEARCH_ID = Integer.valueOf(pd.get("RESEARCH_ID").toString());
                Map<String, Object> map = new HashedMap();
                map.put("list", list_rimg);
                map.put("RESEARCH_ID", RESEARCH_ID);
                research_ImgService.saves(map);
            }
            PageData product = productService.findById(pd);
            String message = "请注意，用户\"" + pd1.getString("NAME") + "\"添加了" + product.getString("PRODUCT_NAME") + "研究院帖子，请尽快处理审查！";
            pd.clear();
            pd.put("code", "1");
            pd.put("inform", inform);
            pd.put("message", "正确返回数据!");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("message", "程序出错,请联系管理员!");
            pd.put("code", "2");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    @RequestMapping(value = "ZanCommentResearch", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String ZanCommentResearch(String USER_ID, String COMMENT_RESEARCH_ID, String STATUS) {
        logBefore(logger, "点赞评论(app)");
        PageData pd = new PageData();
        pd = this.getPageData();
        String STATUS1 = "0";
        try {
            if (STATUS.equals("1")) {
                if (zan_CommentResearchService.findByIds(pd) == null) {
                    zan_CommentResearchService.save(pd);
                    comment_ResearchService.editJiaViewss(pd);
                }
                STATUS1 = "1";
            } else {
                if (zan_CommentResearchService.findByIds(pd) != null) {
                    zan_CommentResearchService.delete(pd);
                    comment_ResearchService.editJianViewss(pd);
                }
            }

            PageData pd_r = comment_ResearchService.findById(pd);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("STATUS", STATUS1);
            pd.put("VIEWS", pd_r.get("VIEWS").toString());
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("message", "程序出错,请联系管理员!");
            pd.put("code", "2");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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


    @RequestMapping(value = "ZanCommentResearchS", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String ZanCommentResearchS(String USER_ID, String COMMENT_RESEARCH_ID, String STATUS, String UNIONID) {
        logBefore(logger, "点赞评论UNIONID(bin)");
        PageData pd = new PageData();
        pd = this.getPageData();
        String STATUS1 = "0";
        try {
            if (STATUS.equals("1")) {
                if (null == USER_ID || "".equals(USER_ID)) {
                    if (zan_commentResearchYoukeService.findById(pd) == null) {
                        zan_commentResearchYoukeService.save(pd);
                        comment_ResearchService.editJiaViewss(pd);
                    }
                } else {
                    if (zan_CommentResearchService.findByIds(pd) == null) {
                        zan_CommentResearchService.save(pd);
                        comment_ResearchService.editJiaViewss(pd);
                    }
                }

                STATUS1 = "1";
            } else {
                String views = comment_ResearchService.findById(pd).get("VIEWS").toString();
                if (null == USER_ID || "".equals(USER_ID)) {
                    if (zan_commentResearchYoukeService.findById(pd) != null) {
                        zan_commentResearchYoukeService.delete(pd);
                        int vie = Integer.parseInt(views);
                        if (vie > 0) {
                            comment_ResearchService.editJianViewss(pd);
                        }
                    }
                } else {
                    if (zan_CommentResearchService.findByIds(pd) != null) {
                        zan_CommentResearchService.delete(pd);
                        int vie = Integer.parseInt(views);
                        if (vie > 0) {
                            comment_ResearchService.editJianViewss(pd);
                        }
                    }
                }

            }

            PageData pd_r = comment_ResearchService.findById(pd);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("STATUS", STATUS1);
            pd.put("VIEWS", pd_r.get("VIEWS").toString());
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("message", "程序出错,请联系管理员!");
            pd.put("code", "2");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    @RequestMapping(value = "ZanResearch", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String ZanResearch(String USER_ID, String RESEARCH_ID, String STATUS) {
        logBefore(logger, "点赞研究院帖子");
        PageData pd = new PageData();
        pd = this.getPageData();
        String STATUS1 = "0";
        try {
            if (STATUS.equals("1")) {
                if (zan_ResearchService.findById(pd) == null) {
                    zan_ResearchService.save(pd);
                    researchService.editJiaViews(pd);
                }
                STATUS1 = "1";
            } else {
                if (zan_ResearchService.findById(pd) != null) {
                    zan_ResearchService.delete(pd);
                    researchService.editJianViews(pd);
                }
            }
            PageData pd_r = researchService.findById(pd);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("STATUS", STATUS1);
            pd.put("VIEWS", pd_r.get("VIEWS").toString());
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("message", "程序出错,请联系管理员!");
            pd.put("code", "2");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    @RequestMapping(value = "CommentResearchHuiFus", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String CommentResearchHuiFus(String USER_ID, String RESEARCH_ID, String MESSAGE, String PUSER_ID, String PID) {
        logBefore(logger, "回复评论回复");
        PageData pd = new PageData();
        pd = this.getPageData();
        String inform = "";
        try {
            pd.put("NAME", "");
            pd.put("PNAME", "");
            pd.put("DATE", DateUtil.getTime());
            pd.put("STATUS", "1");
            pd.put("YUE", DateUtil.getDay());
            pd.put("PID", PID);
            pd.put("VIEWS", "0");
            comment_ResearchService.save(pd);
            pd.put("DATES", DateUtil.getTime());
            researchService.editDates(pd);
            PageData pd_p = researchService.findById(pd);
            PageData pd_c = appuserService.findById111(pd);
            PageData pd1 = new PageData();
            pd1.put("NEWS_ID", this.get32UUID());
            pd1.put("ID", pd.getString("RESEARCH_ID"));
            pd1.put("MESSAGE", pd.getString("MESSAGE"));
            pd1.put("NAME", pd_c.getString("NAME"));
            pd1.put("IMG", pd_c.getString("IMG"));
            pd1.put("DATE", DateUtil.getTime());
            pd1.put("USER_ID", pd.getString("PUSER_ID"));
            pd1.put("SUBJECT", pd_p.getString("SUBJECT"));
            pd1.put("STATUS", "8");
            pd1.put("USER_ID1", pd.getString("USER_ID"));
            pd1.put("url", "");
            newsService.save(pd1);
            pd_c.put("STATUS", "1");
            appuserService.editStatus(pd_c);
            if (MESSAGE.length() >= 15) {
                String today = DateUtil.getDay();
                PageData comDto = new PageData();
                comDto.put("community_type", "1");
                comDto.put("create_date", today);
                comDto.put("USER_ID", USER_ID);
                pd.put("JIFEN", 1);
                appuserService.editJifen(pd);
                appuserService.addZJIFEN(pd);
                comDto.put("NUM", "1");
                comDto.put("community_id", RESEARCH_ID);
                comDto.put("MIAOSHU", "评论研究院帖子");
                comDto.put("ORDER_NUM", "");
                countCommunityService.save(comDto);
                inform = "回复完成，获取有效积分+1";
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("inform", inform);
            pd.put("message", "正确返回数据!");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("message", "程序出错,请联系管理员!");
            pd.put("code", "2");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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


    @RequestMapping(value = "CommentResearchHuiFu", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String CommentResearchHuiFu(String USER_ID, String RESEARCH_ID, String MESSAGE, String PUSER_ID,
                                       String COMMENT_RESEARCH_ID) {
        logBefore(logger, "回复评论");
        PageData pd = new PageData();
        pd = this.getPageData();
        String inform = "";
        try {
            PageData research = comment_ResearchService.findById(pd);
            pd.put("NAME", "");
            pd.put("RESEARCH_ID", RESEARCH_ID);
            pd.put("PNAME", "");
            pd.put("DATE", DateUtil.getTime());
            pd.put("STATUS", "1");
            pd.put("YUE", DateUtil.getDay());
            pd.put("PID", COMMENT_RESEARCH_ID);
            pd.put("VIEWS", "0");
            comment_ResearchService.save(pd);
            pd.put("DATES", DateUtil.getTime());
            researchService.editDates(pd);
            //PageData pd_r = comment_ResearchService.findById(pd);
            PageData pd_rc = researchService.findById(research);
            PageData pd_c = appuserService.findById111(pd);
            PageData pd1 = new PageData();
            pd1.put("NEWS_ID", this.get32UUID());
            pd1.put("ID", pd.getString("RESEARCH_ID"));
            pd1.put("MESSAGE", pd.getString("MESSAGE"));
            pd1.put("NAME", pd_c.getString("NAME"));
            pd1.put("IMG", pd_c.getString("IMG"));
            pd1.put("DATE", DateUtil.getTime());
            pd1.put("USER_ID", research.get("USER_ID").toString());
            pd1.put("SUBJECT", pd_rc.getString("SUBJECT"));
            pd1.put("STATUS", "8");
            pd1.put("USER_ID1", pd.getString("USER_ID"));
            pd1.put("url", "");
            newsService.save(pd1);
            pd_c.put("STATUS", "1");
            appuserService.editStatus(pd_c);
            if (MESSAGE.length() >= 15) {
                String today = DateUtil.getDay();
                PageData comDto = new PageData();
                comDto.put("community_type", "1");
                comDto.put("create_date", today);
                comDto.put("USER_ID", USER_ID);
                pd.put("JIFEN", 1);
                appuserService.editJifen(pd);
                appuserService.addZJIFEN(pd);
                comDto.put("NUM", "1");
                comDto.put("community_id", RESEARCH_ID);
                comDto.put("MIAOSHU", "评论研究院帖子");
                comDto.put("ORDER_NUM", "");
                countCommunityService.save(comDto);
                inform = "回复完成，获取有效积分+1";
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("inform", inform);
            pd.put("message", "正确返回数据!");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("message", "程序出错,请联系管理员!");
            pd.put("code", "2");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    @RequestMapping(value = "CommentResearch", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String saveCommentResearch(String USER_ID, String RESEARCH_ID, String MESSAGE, String img1, String img2, String img3,
                                      String count, String STATUS) {
        logBefore(logger, "评论研究员帖子");
        PageData pd = new PageData();
        pd = this.getPageData();
        String inform = "";
        try {
            pd.put("NAME", "");
            pd.put("PUSER_ID", "");
            pd.put("PNAME", "");
            pd.put("DATE", DateUtil.getTime());
            pd.put("STATUS", "0");
            pd.put("YUE", DateUtil.getDay());
            pd.put("PID", "");
            pd.put("VIEWS", "0");
            comment_ResearchService.save(pd);

            //*****************增加用户积分*****************
            if (MESSAGE.length() >= 15) {
                String today = DateUtil.getDay();
                PageData comDto = new PageData();
                comDto.put("community_type", "1");
                comDto.put("create_date", today);
                comDto.put("USER_ID", USER_ID);
                pd.put("JIFEN", 1);
                appuserService.editJifen(pd);
                appuserService.addZJIFEN(pd);
                comDto.put("NUM", "1");
                comDto.put("community_id", RESEARCH_ID);
                comDto.put("MIAOSHU", "评论研究院帖子");
                comDto.put("ORDER_NUM", "");
                countCommunityService.save(comDto);
                inform = "回复完成，获取有效积分+1";
            }
            //*****************end*******************

            Integer COMMENT_RESEARCH_ID = Integer.valueOf(pd.get("COMMENT_RESEARCH_ID").toString());
            Integer count1 = Integer.valueOf(count);
            String ffile1 = this.get32UUID() + ".jpg";
            String filePath2 = PathUtil.getClasspath() + Const.FILEPATHIMG + "Rpinglun/" + DateUtil.getDays(); // 文件上传路径
            File file = new File(filePath2, ffile1);
            if (!file.exists()) {
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
            }
            for (int i = 0; i < count1; i++) {
                String ffile = this.get32UUID() + ".jpg";
                String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + "Rpinglun/" + DateUtil.getDays() + "/"
                        + ffile; // 文件上传路径
                String TOUR_IMG1 = Const.SERVERPATH + Const.FILEPATHIMG + "Rpinglun/" + DateUtil.getDays() + "/" + ffile;
                String ii = String.valueOf(i + 1);
                PageData pd_i = new PageData();
                pd_i.put("COMMENT_RESEARCH_ID", COMMENT_RESEARCH_ID);
                pd_i.put("IMG", TOUR_IMG1);
                pd_i.put("DATE", DateUtil.getTime());
                comment_ResearchImgService.save(pd_i);
                boolean flag = ImageAnd64Binary.generateImage(pd.getString("img" + ii), filePath);
            }
            pd.put("DATES", DateUtil.getTime());
            researchService.editHuiFu(pd);
            PageData pd_c = researchService.findById(pd);
            PageData pd_u = appuserService.findById111(pd);
            PageData pd1 = new PageData();
            pd1.put("NEWS_ID", this.get32UUID());
            pd1.put("ID", pd.getString("RESEARCH_ID"));
            pd1.put("MESSAGE", pd.getString("MESSAGE"));
            pd1.put("NAME", pd_u.getString("NAME"));
            pd1.put("IMG", pd_u.getString("IMG"));
            pd1.put("DATE", DateUtil.getTime());
            pd1.put("USER_ID", pd_c.getString("USER_ID"));
            pd1.put("SUBJECT", pd_c.getString("SUBJECT"));
            pd1.put("STATUS", "8");
            pd1.put("USER_ID1", pd.getString("USER_ID"));
            pd1.put("url", "");
            newsService.save(pd1);
            pd_c.put("STATUS", "1");
            appuserService.editStatus(pd_c);
            pd.clear();
            pd.put("code", "1");
            pd.put("inform", inform);
            pd.put("message", "评论成功");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(pd);
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


    @RequestMapping(value = "ZanCommentMail", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String ZanCommentMail(String USER_ID, String COMMENT_MAIL_ID, String STATUS) {
        logBefore(logger, "点赞套餐评论");
        PageData pd = new PageData();
        pd = this.getPageData();
        String STATUS1 = "0";
        try {
            if (STATUS.equals("1")) {
                if (zan_CommentMailService.findByIds(pd) == null) {
                    zan_CommentMailService.save(pd);
                    comment_MailService.editJiaViewss(pd);
                }
                STATUS1 = "1";
            } else {
                if (zan_CommentMailService.findByIds(pd) != null) {
                    zan_CommentMailService.delete(pd);
                    comment_MailService.editJianViewss(pd);
                }
            }

            PageData pd_r = comment_MailService.findById(pd);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("STATUS", STATUS1);
            pd.put("VIEWS", pd_r.get("VIEWS").toString());
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("message", "程序出错,请联系管理员!");
            pd.put("code", "2");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // ==========================查询帖子评论=====================================
    @RequestMapping(value = "findCommentMail", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findCommentMail(String PRODUCT_ID, String pageNum, String USER_ID) {
        logBefore(logger, "查询套餐评论");
        PageData pd = new PageData();
        pd = this.getPageData();
        Page page = new Page();
        String message = "正确返回数据!";
        try {
            if (pageNum == null || pageNum.length() == 0) {
                pageNum = "1";
            }
            pd.put("STATUS", "0");
            page.setPd(pd);
            page.setShowCount(5);
            page.setCurrentPage(Integer.parseInt(pageNum));
            List<PageData> list = comment_MailService.datalistPage(page);
            for (int i = 0; i < list.size(); i++) {
                list.get(i).put("USER_ID1", USER_ID);
                if (zan_CommentMailService.findById(list.get(i)) != null) {
                    list.get(i).put("ZAN", "1");
                } else {
                    list.get(i).put("ZAN", "0");
                }
                list.get(i).put("YUE", list.get(i).getString("DATE"));
                List<PageData> list1 = comment_MailImgService.findList(list.get(i));
                list.get(i).put("CIMG", list1);
                list.get(i).put("STATUS", "1");
                List<PageData> list2 = comment_MailService.findList(list.get(i));
                for (int f = 0; f < list2.size(); f++) {
                    if (list2.get(f).getString("PNAME") == null) {
                        list2.get(f).put("PNAME", "");
                    }
                }
                list.get(i).put("COMMENT", list2);
            }
            Map<String, Object> map = new HashedMap();
            if (page.getCurrentPage() == Integer.parseInt(pageNum)) {
                map.put("object", list);
            } else {
                List<PageData> list3 = new ArrayList();
                map.put("object", list3);
                message = "已加载全部数据!";
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("message", message);
            pd.put("data", map);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(pd);
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

    // ===============================回复帖子评论==================================
    @RequestMapping(value = "CommentMailHui2", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String CommentMailHui2(String USER_ID, String PRODUCT_ID, String MESSAGE, String PUSER_ID, String PID,
                                  String STATUS) {
        logBefore(logger, "回复评论");
        PageData pd = new PageData();
        pd = this.getPageData();
        String inform = "";
        try {
            pd.put("NAME", "");
            pd.put("PNAME", "");
            pd.put("DATE", DateUtil.getTime());
            pd.put("STATUS", "1");
            pd.put("VIEWS", "0");
            pd.put("YUE", DateUtil.getDay());
            pd.put("PID", PID);

            //*****************增加用户积分*****************
            if (MESSAGE.length() >= 15) {
                String today = DateUtil.getDay();
                PageData comDto = new PageData();
                comDto.put("community_type", "1");
                comDto.put("create_date", today);
                comDto.put("USER_ID", USER_ID);
                pd.put("JIFEN", 1);
                appuserService.editJifen(pd);
                appuserService.addZJIFEN(pd);
                comDto.put("NUM", "1");
                comDto.put("community_id", PRODUCT_ID);
                comDto.put("MIAOSHU", "回复商品帖子");
                comDto.put("ORDER_NUM", "");
                countCommunityService.save(comDto);
                inform = "回复完成，获取有效积分+1";
            }
            //*****************end*******************

            comment_MailService.save(pd);
            pd.clear();
            pd.put("code", "1");
            pd.put("inform", inform);
            pd.put("message", "正确返回数据!");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("message", "程序出错,请联系管理员!");
            pd.put("code", "2");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // ==========================回复帖子评论========================================
    @RequestMapping(value = "CommentMailHui", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String CommentMailHui(String USER_ID, String PRODUCT_ID, String MESSAGE, String PUSER_ID, String COMMENT_MAIL_ID,
                                 String STATUS) {
        logBefore(logger, "回复套餐评论");
        PageData pd = new PageData();
        pd = this.getPageData();
        String inform = "";
        try {
            pd.put("NAME", "");
            pd.put("PNAME", "");
            pd.put("DATE", DateUtil.getTime());
            pd.put("STATUS", "1");
            pd.put("VIEWS", "0");
            pd.put("YUE", DateUtil.getDay());
            pd.put("PID", COMMENT_MAIL_ID);
            comment_MailService.save(pd);

            //*****************增加用户积分*****************
            if (MESSAGE.length() >= 15) {
                String today = DateUtil.getDay();
                PageData comDto = new PageData();
                comDto.put("community_type", "1");
                comDto.put("create_date", today);
                comDto.put("USER_ID", USER_ID);
                pd.put("JIFEN", 1);
                appuserService.editJifen(pd);
                appuserService.addZJIFEN(pd);
                comDto.put("NUM", "1");
                comDto.put("community_id", PRODUCT_ID);
                comDto.put("MIAOSHU", "回复套餐评论");
                comDto.put("ORDER_NUM", "");
                countCommunityService.save(comDto);
                inform = "回复完成，获取有效积分+1";
            }
            //*****************end*******************

            pd.clear();
            pd.put("code", "1");
            pd.put("inform", "inform");
            pd.put("message", "正确返回数据!");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("message", "程序出错,请联系管理员!");
            pd.put("code", "2");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
        } catch (JsonGenerationException e) {

            e.printStackTrace();
        } catch (JsonMappingException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
        return str;
    }

    @RequestMapping(value = "CommentMail", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String CommentMail(String USER_ID, String PRODUCT_ID, String MESSAGE, String img1, String img2, String img3,
                              String count, String STATUS) {
        logBefore(logger, "评论研究员帖子");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            pd.put("NAME", "");
            pd.put("PUSER_ID", "");
            pd.put("PNAME", "");
            pd.put("DATE", DateUtil.getTime());
            pd.put("STATUS", "0");
            pd.put("YUE", DateUtil.getDay());
            pd.put("PID", "");
            pd.put("VIEWS", "0");
            comment_MailService.save(pd);
            Integer COMMENT_MAIL_ID = Integer.valueOf(pd.get("COMMENT_MAIL_ID").toString());
            Integer count1 = Integer.valueOf(count);
            String ffile1 = this.get32UUID() + ".jpg";
            String filePath2 = PathUtil.getClasspath() + Const.FILEPATHIMG + DateUtil.getDays(); // 文件上传路径
            File file = new File(filePath2, ffile1);
            if (!file.exists()) {
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
            }
            for (int i = 0; i < count1; i++) {
                String ffile = this.get32UUID() + ".jpg";
                String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + DateUtil.getDays() + "/"
                        + ffile; // 文件上传路径
                String TOUR_IMG1 = Const.SERVERPATH + Const.FILEPATHIMG + DateUtil.getDays() + "/" + ffile;
                String ii = String.valueOf(i + 1);
                PageData pd_i = new PageData();
                pd_i.put("COMMENT_MAIL_ID", COMMENT_MAIL_ID);
                pd_i.put("IMG", TOUR_IMG1);
                pd_i.put("DATE", DateUtil.getTime());
                comment_MailImgService.save(pd_i);
                boolean flag = ImageAnd64Binary.generateImage(pd.getString("img" + ii), filePath);
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "评论成功");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(pd);
        } catch (JsonGenerationException e) {

            e.printStackTrace();
        } catch (JsonMappingException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
        return str;
    }

    @RequestMapping(value = "findResearchComment", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findResearchComment(String RESEARCH_ID, String pageNum, String USER_ID) {
        logBefore(logger, "查询研究院帖子评论");
        PageData pd = new PageData();
        Page page = new Page();
        pd = this.getPageData();
        String message = "正确返回数据!";
        try {
            if (pageNum == null || pageNum.length() == 0) {
                pageNum = "1";
            }
            pd.put("STATUS", "0");
            Integer SHU1 = Integer.valueOf(pageNum) * 10;
            pd.put("SHU1", SHU1 - 10);
            page.setPd(pd);
            page.setShowCount(10);
            page.setCurrentPage(Integer.parseInt(pageNum));
            List<PageData> list = comment_ResearchService.datalistPage(page);
            for (int i = 0; i < list.size(); i++) {
                list.get(i).put("USER_ID1", USER_ID);
                if (zan_CommentResearchService.findById(list.get(i)) != null) {
                    list.get(i).put("ZAN", "1");
                } else {
                    list.get(i).put("ZAN", "0");
                }
                list.get(i).put("VIEWS", list.get(i).get("VIEWS").toString());
                List<PageData> list1 = comment_ResearchImgService.findList(list.get(i));
                list.get(i).put("CIMG", list1);
                list.get(i).put("STATUS", "1");
                List<PageData> list2 = comment_ResearchService.findList(list.get(i));
                for (int f = 0; f < list2.size(); f++) {
                    if (list2.get(f).getString("PNAME") == null) {
                        list2.get(f).put("PNAME", "");
                    }
                    list2.get(f).put("VIEWS", list2.get(f).get("VIEWS").toString());
                }
                list.get(i).put("COMMENT", list2);
            }
            Map<String, Object> map = new HashedMap();
            if (list != null && list.size() != 0) {
                map.put("object", list);
            } else {
                List<PageData> list3 = new ArrayList();
                map.put("object", list3);
                message = "已加载全部数据!";
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("message", message);
            pd.put("data", map);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错请联系管理员");
        }
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(pd);
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


    @RequestMapping(value = "findResearchCommentSs", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findResearchCommentSs(String RESEARCH_ID, String pageNum, String USER_ID, String UNIONID) {
        logBefore(logger, "阿斌 查询研究院帖子评论");
        PageData pd = new PageData();
        Page page = new Page();
        pd = this.getPageData();
        String message = "正确返回数据!";
        try {
            if (pageNum == null || pageNum.length() == 0) {
                pageNum = "1";
            }
            pd.put("STATUS", "0");
            Integer SHU1 = Integer.valueOf(pageNum) * 10;
            pd.put("SHU1", SHU1 - 10);
            page.setPd(pd);
            page.setShowCount(10);
            page.setCurrentPage(Integer.parseInt(pageNum));
            List<PageData> list = comment_ResearchService.datalistPage(page);
            for (int i = 0; i < list.size(); i++) {
                if (null != UNIONID && !"".equals(UNIONID)) {
                    list.get(i).put("UNIONID", UNIONID);
                    if (zan_commentResearchYoukeService.findById(list.get(i)) != null) {
                        list.get(i).put("ZAN", "1");
                    } else {
                        list.get(i).put("ZAN", "0");
                    }
                } else if (null != USER_ID && !"".equals(USER_ID)) {
                    list.get(i).put("USER_ID1", USER_ID);
                    if (zan_CommentResearchService.findById(list.get(i)) != null) {
                        list.get(i).put("ZAN", "1");
                    } else {
                        list.get(i).put("ZAN", "0");
                    }
                }
                list.get(i).put("VIEWS", list.get(i).get("VIEWS").toString());
                List<PageData> list1 = comment_ResearchImgService.findList(list.get(i));
                list.get(i).put("CIMG", list1);
                list.get(i).put("STATUS", "1");
                List<PageData> list2 = comment_ResearchService.findList(list.get(i));
                for (int f = 0; f < list2.size(); f++) {
                    if (list2.get(f).getString("PNAME") == null) {
                        list2.get(f).put("PNAME", "");
                    }
                    list2.get(f).put("VIEWS", list2.get(f).get("VIEWS").toString());
                }
                list.get(i).put("COMMENT", list2);
            }
            Map<String, Object> map = new HashedMap();
            if (list != null && list.size() != 0) {
                map.put("object", list);
            } else {
                List<PageData> list3 = new ArrayList();
                map.put("object", list3);
                message = "已加载全部数据!";
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("message", message);
            pd.put("data", map);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错请联系管理员");
        }
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(pd);
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

    @RequestMapping(value = "findCommentResearchList", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findCommentResearchList(String COMMENT_RESEARCH_ID, String USER_ID, String UNIONID) {
        logBefore(logger, "查询研究院帖子评论的列表");
        PageData pd = new PageData();
        pd = this.getPageData();
        String message = "正确返回数据!";
        try {
            PageData coment = comment_ResearchService.findById(pd);
            if (null != USER_ID && !"".equals(USER_ID)) {
                pd.put("USER_ID1", USER_ID);
                if (zan_CommentResearchService.findById(pd) != null) {
                    coment.put("ZAN", "1");
                } else {
                    coment.put("ZAN", "0");
                }
            } else if (null != UNIONID && !"".equals(UNIONID)) {
                if (zan_commentResearchYoukeService.findById(pd) != null) {
                    coment.put("ZAN", "1");
                } else {
                    coment.put("ZAN", "0");
                }
            }
            List<PageData> list12 = comment_ResearchImgService.findList(coment);
            coment.put("CIMG", list12);
            coment.put("STATUS", "1");
            pd.put("STATUS", "1");
            List<PageData> list = comment_ResearchService.findList(pd);
            for (int i = 0; i < list.size(); i++) {
                if (null != USER_ID && !"".equals(USER_ID)) {
                    list.get(i).put("USER_ID1", USER_ID);
                    if (zan_CommentResearchService.findById(list.get(i)) != null) {
                        list.get(i).put("ZAN", "1");
                    } else {
                        list.get(i).put("ZAN", "0");
                    }
                } else if (null != UNIONID && !"".equals(UNIONID)) {
                    if (zan_commentResearchYoukeService.findById(list.get(i)) != null) {
                        list.get(i).put("ZAN", "1");
                    } else {
                        list.get(i).put("ZAN", "0");
                    }
                }
                list.get(i).put("VIEWS", list.get(i).get("VIEWS").toString());
                List<PageData> list1 = comment_ResearchImgService.findList(list.get(i));
                list.get(i).put("CIMG", list1);
                list.get(i).put("STATUS", "1");
                List<PageData> list2 = comment_ResearchService.findList(list.get(i));
                for (int f = 0; f < list2.size(); f++) {
                    if (list2.get(f).getString("PNAME") == null) {
                        list2.get(f).put("PNAME", "");
                    }
                    list2.get(f).put("VIEWS", list2.get(f).get("VIEWS").toString());
                }
                list.get(i).put("COMMENT", list2);
            }
            Map<String, Object> map = new HashedMap();
            map.put("comment", coment);
            map.put("object", list);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", message);
            pd.put("data", map);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错请联系管理员");
        }
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(pd);
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

    @RequestMapping(value = "findResearchId", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findResearchId(String RESEARCH_ID, String USER_ID) {
        logBefore(logger, "查询研究院帖子");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            PageData pd1 = researchService.findById(pd);
            if (zan_ResearchService.findById(pd) != null) {
                pd1.put("ZAN", "1");
            } else {
                pd1.put("ZAN", "0");
            }
            List<PageData> list_img = new ArrayList<PageData>();
            if (!pd1.get("LSTATUS").toString().equals("2")) {
                String str = DateUtil.delHTMLTag(pd1.getString("MESSAGE"));
                String DETAILS1 = str.replace("\r", "");
                String DETAILS2 = DETAILS1.replace("\n", "");
                String DETAILS3 = DETAILS2.replace("&nbsp;", "");
                String DETAILS4 = DETAILS3.replace("", "");
                int qian = 20;
                if (DETAILS4.length() < 20) {
                    pd1.put("JIANJIE", DETAILS4);
                } else {
                    str = DETAILS4.substring(0, qian);
                    pd1.put("JIANJIE", str + "...");
                }
                list_img = research_ImgService.findList(pd);
            } else {
                list_img = research_ImgService.findHuoDongImg(pd);
            }
            pd1.put("VIEWS", pd1.get("VIEWS").toString());
            pd1.put("HUIFU", pd1.get("HUIFU").toString());
            // = research_ImgService.findList(pd);
            pd1.put("FIMG", list_img);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("data", pd1);
            if ("2".equals(pd1.get("LSTATUS").toString()) || "1".equals(pd1.get("LSTATUS").toString())) {
                pd.put("url", "https://www.meitiannongzi.com/nongjike/static/jsp/activity.html?RESEARCH_ID=" + RESEARCH_ID + "&uid=" + USER_ID);
            } else {
                pd.put("url", "https://www.meitiannongzi.com/nongjike/static/jsp/Researc.html?RESEARCH_ID=" + RESEARCH_ID + "&uid=" + USER_ID);
            }
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错请联系管理员");
        }
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(pd);
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


    @RequestMapping(value = "findResearch", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findResearch(String PRODUCT_ID, String USER_ID, String pageNum, String STATUS) {
        logBefore(logger, "查询商品研究院");
        PageData pd = new PageData();
        Page page = new Page();
        pd = this.getPageData();
        String message = "正确返回数据!";
        try {
            if (pageNum == null || pageNum.length() == 0) {
                pageNum = "1";
            }
            pd.put("STATUS1", STATUS);
            page.setPd(pd);
            page.setShowCount(10);
            page.setCurrentPage(Integer.parseInt(pageNum));
            PageData pd_r = productService.findRimg(pd);
            List<PageData> list = researchService.findZResearchTop(pd);
            List<PageData> lists = new ArrayList();
            if (STATUS.equals("0")) {
                lists = researchService.dataslistPage(page);
            } else {
                lists = researchService.dataslistPage(page);
            }
            for (int i = 0; i < lists.size(); i++) {
                String str = DateUtil.delHTMLTag(lists.get(i).getString("MESSAGE"));
                String DETAILS1 = str.replace("\r", "");
                String DETAILS2 = DETAILS1.replace("\n", "");
                String DETAILS3 = DETAILS2.replace("&nbsp;", "");
                if (DETAILS3.length() > 30) {
                    DETAILS3 = DETAILS3.substring(0, 30);
                }
                lists.get(i).put("JIANJIE", DETAILS3);
                List<PageData> list_img = research_ImgService.findList(lists.get(i));
                lists.get(i).put("FIMG", list_img);
            }
            Map<String, Object> map = new HashedMap();
            if (page.getCurrentPage() == Integer.parseInt(pageNum)) {
                map.put("object", lists);
            } else {
                List<PageData> list4 = new ArrayList();
                message = "已加载全部数据!";
                map.put("object", list4);
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("message", message);
            pd.put("img", pd_r.getString("RIMG"));
            pd.put("top", list);
            pd.put("data", map);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错请联系管理员");
        }
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(pd);
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
     * @param USER_ID //用户ID
     * @param pageNum //分页s
     * @return t_o_r 判断是套餐/研究院/提问 (c/r/t)
     */
    @RequestMapping(value = "findTIRE", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findTIRE(String USER_ID, String pageNum) {
        logBefore(logger, "查询提问和所有研究院列表");
        PageData pd = new PageData();
        Page page = new Page();
        pd = this.getPageData();
        String message = "正确返回数据!";
        try {
            PageData pd_special = new PageData();
            pd_special.put("TSTATUS", "1");
            pd_special = post_SpecialService.findById2(pd_special);
            pd.put("TSTATUS", "1");
            List<PageData> postList = postService.findList(pd);
            for (int i = 0; i < postList.size(); i++) {
                postList.get(i).put("VIEWS", postList.get(i).get("VIEWS").toString());
            }
            PageData pd_m = postService.findPostMstatus(pd);
            if (pageNum == null || pageNum.length() == 0) {
                pageNum = "1";
            }
            page.setPd(pd);
            page.setShowCount(10);
            page.setCurrentPage(Integer.parseInt(pageNum));
            List<PageData> tiwenReList = tiWenService.TIRElistPage(page);
            PageData dto_reserImg = null;
            PageData dto_tiwen = null;
            for (PageData tiwenRe : tiwenReList) {
                if ("t".equals(tiwenRe.getString("t_o_r"))) {
                    dto_tiwen = new PageData();
                    dto_tiwen.put("TIWEN_ID", tiwenRe.get("ID").toString());
                    dto_tiwen.put("USER_ID1", USER_ID);
                    dto_tiwen.put("USER_ID", tiwenRe.get("USER_ID").toString());
                    PageData pd1 = zan_tiwenService.findById1(dto_tiwen);
                    if (pd1 != null) {
                        tiwenRe.put("ZAN", "1");
                    } else {
                        tiwenRe.put("ZAN", "0");
                    }
                    PageData user = appuserService.findById(dto_tiwen);
                    PageData pd2 = collection_tiwenService.findCollection1(dto_tiwen);
                    if (pd2 != null) {
                        tiwenRe.put("COLLECTION", "1");
                    } else {
                        tiwenRe.put("COLLECTION", "0");
                    }
                    List<PageData> list3 = tiWen_ImgService.findTop(dto_tiwen);
                    tiwenRe.put("T_IMG", list3);
                    dto_tiwen.put("STATUS", "0");
                    tiwenRe.put("IMG", "");
                    tiwenRe.put("STATUS", "0");
                    tiwenRe.put("FIMG", "");
                    tiwenRe.put("VIEWS", zan_tiwenService.findCount(dto_tiwen).get("count1"));
                    tiwenRe.put("HUIFU", comment_TiwenService.findCount(dto_tiwen).get("count"));
                    tiwenRe.put("USERNAME", user.getString("NAME"));
                    tiwenRe.put("USERIMG", user.getString("IMG"));
                    List<PageData> Clist = comment_TiwenService.findListTo(dto_tiwen);
                    tiwenRe.put("COMMENT", Clist);
                    tiwenRe.put("url",
                            "http://www.meitiannongzi.com/nongjike/static/jsp/share_tiwen.html?TIWEN_ID=" + tiwenRe.getString("ID") + "&uid=" + USER_ID);
                    tiwenRe.put("JIANJIE", "");
                    tiwenRe.put("PRO_NAME", "");
                } else if ("c".equals(tiwenRe.getString("t_o_r"))) {
                    tiwenRe.put("ZAN", "");
                    tiwenRe.put("T_IMG", "");
                    tiwenRe.put("COLLECTION", "");
                    tiwenRe.put("COMMENT", "");
                    tiwenRe.put("url", "");
                    tiwenRe.put("JIANJIE", "");
                    tiwenRe.put("FIMG", "");
                    tiwenRe.put("STATUS", "");
                    tiwenRe.put("IMG", tiwenRe.getString("MESSAGE"));
                    tiwenRe.put("PRO_NAME", "");
                    tiwenRe.put("MESSAGE", "");
                    tiwenRe.put("USERNAME", "");
                    tiwenRe.put("USERIMG", "");
                } else if ("r".equals(tiwenRe.getString("t_o_r"))) {
                    PageData reser = new PageData();
                    reser.put("RESEARCH_ID", tiwenRe.get("ID"));
                    reser.put("USER_ID", USER_ID);
                    PageData pd1 = researchService.findById(reser);
                    if (zan_ResearchService.findById(reser) != null) {
                        tiwenRe.put("ZAN", "1");
                    } else {
                        tiwenRe.put("ZAN", "0");
                    }
                    PageData user = appuserService.findById(pd1);
                    List<PageData> list_img = new ArrayList<PageData>();
                    if (!pd1.get("LSTATUS").toString().equals("2")) {
                        String str = DateUtil.delHTMLTag(pd1.getString("MESSAGE"));
                        String DETAILS1 = str.replace("\r", "");
                        String DETAILS2 = DETAILS1.replace("\n", "");
                        String DETAILS3 = DETAILS2.replace("&nbsp;", "");
                        int qian = 20;
                        if (DETAILS3.length() < 20) {
                            tiwenRe.put("JIANJIE", DETAILS3);
                        } else {
                            str = DETAILS2.substring(0, qian);
                            tiwenRe.put("JIANJIE", str + "...");
                        }
                        list_img = research_ImgService.findList(reser);
                    } else {
                        list_img = research_ImgService.findHuoDongImg(reser);
                    }
                    tiwenRe.put("FIMG", list_img);
                    tiwenRe.put("IMG", "");
                    tiwenRe.put("T_IMG", "");
                    tiwenRe.put("USERNAME", user.getString("NAME"));
                    tiwenRe.put("USERIMG", user.getString("IMG"));
                    tiwenRe.put("COLLECTION", pd1.get("PRODUCT_ID").toString());
                    tiwenRe.put("COMMENT", "");
                    tiwenRe.put("STATUS", "");
                    tiwenRe.put("PRO_NAME", tiwenRe.getString("PRO_NAME") + "讨论区");
                    if ("2".equals(pd1.get("LSTATUS").toString()) || "1".equals(pd1.get("LSTATUS").toString())) {
                        tiwenRe.put("url", "https://www.meitiannongzi.com/nongjike/static/jsp/activity.html?RESEARCH_ID=" + reser.get("RESEARCH_ID").toString() + "&uid=" + USER_ID);
                    } else {
                        tiwenRe.put("url", "http://www.meitiannongzi.com/nongjike/static/jsp/Researc.html?RESEARCH_ID=" + reser.get("RESEARCH_ID").toString() + "&uid=" + USER_ID);
                    }
                    //---------------------------------------------------------------------------------
                      /*  String str = DateUtil.delHTMLTag(tiwenRe.getString("MESSAGE"));
                        String DETAILS1 = str.replace("\r", "");
                        String DETAILS2 = DETAILS1.replace("\n", "");
                        String DETAILS3 = DETAILS2.replace("&nbsp;", "");
                        if (DETAILS3.length() > 30) {
                            DETAILS3 = DETAILS3.substring(0, 30);
                        }
                         tiwenRe.put("JIANJIE", DETAILS3);
                        dto_reserImg = new PageData();
                        dto_reserImg.put("RESEARCH_ID",tiwenRe.getString("ID"));
                        List<PageData> list_img = research_ImgService.findList(dto_reserImg);
                        tiwenRe.put("FIMG", list_img);
                        tiwenRe.put("t_o_r", "r");*/
                }
            }
            Map<String, Object> map = new HashMap();
            if (page.getCurrentPage() == Integer.parseInt(pageNum)) {
                map.put("object", tiwenReList);
            } else {
                List<PageData> list4 = new ArrayList();
                message = "已加载全部数据!";
                map.put("object", list4);
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("message", message);
            pd.put("data", map);
            pd.put("special", pd_special);
            pd.put("top", postList);
            pd.put("meiri", pd_m);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错请联系管理员");
        }
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(pd);
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

    @RequestMapping(value = "saveCartTaoCan", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String saveCartTaoCan(String json, String USER_ID) {
        logBefore(logger, "套餐添加购物车");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            json = DateUtil.delHTMLTag(json);
            json = json.replace("\r", "");
            json = json.replace("\n", "");
            json = json.replace("\\", "");
            Gson gson = new Gson();
            List<PageData> list = gson.fromJson(json, new TypeToken<List<PageData>>() {
            }.getType());
            for (int i = 0; i < list.size(); i++) {
                if (!list.get(i).getString("NUM").equals("0")) {
                    PageData pd_r = remarkService.findById(list.get(i));
                    PageData pd_ca = cartService.findRemarkId(list.get(i));
                    Integer int1 = Integer.valueOf(list.get(i).getString("NUM"));
                    if (pd_ca != null) {
                        if (int1 < Integer.valueOf(pd_r.getString("NUM"))) {
                            int1 = Integer.valueOf(pd_r.getString("NUM"));
                        }
                        Integer int2 = Integer.valueOf(pd_ca.getString("NUM"));
                        Integer num3 = int1 + int2;
                        pd_ca.put("NUM", num3);
                        cartService.edit(pd_ca);
                    } else {
                        if (int1 < Integer.valueOf(pd_r.getString("NUM"))) {
                            list.get(i).put("NUM", pd_r.getString("NUM"));
                        }
                        list.get(i).put("CART_ID", this.get32UUID());
                        list.get(i).put("STATUS", "1");
                        cartService.save(list.get(i));
                    }
                }
            }
            List<PageData> list1 = cartService.findList(pd);
            pd.clear();
            pd.put("COUNT", list1.size());
            pd.put("code", "1");
            pd.put("message", "添加购物车成功!");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错请联系管理员");
        }
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(pd);
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


    @RequestMapping(value = "saveWendaQuanxian", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String saveWendaQuanxian(String PRODUCT_ID, String USER_ID, String FENSHU) {
        logBefore(logger, "添加考试的商品权限");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            int fen = Integer.parseInt(FENSHU);
            if (fen >= 90) {
                PageData wenda = null;
                wenda = user_wendaQuanxianService.findQByUserId(pd);
                pd.put("DATE", DateUtil.getTime());
                if (null == wenda) {
                    user_wendaQuanxianService.save(pd);
                } else {
                    int oldf = Integer.parseInt(wenda.get("FENSHU").toString());
                    if (oldf <= fen) {
                        user_wendaQuanxianService.editFEN(pd);
                    }
                }
                PageData user = appuserService.findById(pd);
                List<PageData> pro_L = p_QuanXuanService.findList(pd);
                pd.put("P_QUANXIANID", get32UUID());
                if ("1".equals(user.get("VIP").toString()) || "3".equals(user.get("VIP").toString())) {
                    if (null != pro_L && pro_L.size() != 0) {
                        if (null == p_QuanXuanService.findById(pd)) {
                            p_QuanXuanService.save(pd);
                        }
                    }
                }
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    @RequestMapping(value = "selectWendaQuanxian", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String selectWendaQuanxian(String PRODUCT_ID, String USER_ID) {
        logBefore(logger, "查询考试的商品权限");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            PageData user = appuserService.findById(pd);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据！");
            if ("1".equals(user.get("VIP").toString()) || "3".equals(user.get("VIP").toString())) {
                pd.put("SVIP", "1");
            } else {
                pd.put("SVIP", "0");
            }
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    @RequestMapping(value = "findProducess", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findProducess(String PRODUCT_ID, String USER_ID) {
        logBefore(logger, "查询商品");
        PageData pd = new PageData();
        pd = this.getPageData();
        Map<String, Object> map = new HashMap();
        try {
            PageData pd1 = productService.findById(pd);
            PageData userQuan = user_wendaQuanxianService.findQByUserId(pd);
            if ("1".equals(pd1.getString("HSTATUS"))) {
                if (null == userQuan) {
                    pd1.put("KAOSHI", "1");//没考过
                    pd1.put("PAYSTA", "0");
                } else {
                    pd1.put("KAOSHI", "0");//考过
                    pd1.put("PAYSTA", userQuan.getString("PAYSTA"));
                }
            } else {
                pd1.put("KAOSHI", "0");
                pd1.put("PAYSTA", "0");
            }
            pd1.put("HURL", pd1.getString("HURL") + "?USER_ID=" + USER_ID + "&PRODUCT_ID=" + PRODUCT_ID);
            PageData user = appuserService.findById(pd);
            pd1.put("VIP", user.get("VIP").toString());
            PageData re = researchService.findProHuo(pd1);
            if (null != re) {
                if ("2".equals(re.get("LSTATUS").toString())) {
                    pd1.put("RESEARCH_ID", re.get("RESEARCH_ID").toString());
                } else {
                    pd1.put("RESEARCH_ID", "");
                }
            } else {
                pd1.put("RESEARCH_ID", "");
            }
            List<PageData> list1 = remarkService.findList(pd1);
            List<PageData> list = p_QuanXuanService.findList(pd);
            if (list != null && list.size() != 0) {
                PageData pd_p = p_QuanXuanService.findById(pd);
                if (pd_p == null) {
                    if (!"1".equals(pd1.get("HSTATUS").toString())) {
                        for (int i = 0; i < list1.size(); i++) {
                            list1.get(i).put("EXPLAIN1", "点击咨询价格!");
                        }
                        pd1.put("MIN", "点击咨询价格!");
                    }
                }
            }
            pd1.put("COLLECTION", "0");
            PageData pd_ca = cartService.findCount(pd);
            pd1.put("CART", pd_ca.get("count").toString());
            map.put("object", pd1);
            map.put("list2", list1);
            List<PageData> lists = researchService.findlimitTou(pd);
            for (int i = 0; i < lists.size(); i++) {
                String str = DateUtil.delHTMLTag(lists.get(i).getString("MESSAGE"));
                String DETAILS1 = str.replace("\r", "");
                String DETAILS2 = DETAILS1.replace("\n", "");
                String DETAILS3 = DETAILS2.replace("&nbsp;", "");
                if (DETAILS3.length() < 20) {
                    lists.get(i).put("MESSAGE", DETAILS3);
                } else {
                    str = DETAILS3.substring(0, 20);
                    lists.get(i).put("MESSAGE", DETAILS3);
                }
            }
            List<PageData> list11 = researchService.findlimits(pd);
            PageData pd_r = researchService.findCount(pd);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("data", map);
            pd.put("COUNT", pd_r.get("count1").toString());
            pd.put("list", list11);
            pd.put("plist", lists);
            pd.put("url", "http://www.meitiannongzi.com/nongjike/static/jsp/product.html?PRODUCT_ID=" + PRODUCT_ID);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    @RequestMapping(value = "findProduces", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findProduces(String PRODUCT_ID, String USER_ID) {
        logBefore(logger, "查询商品");
        PageData pd = new PageData();
        pd = this.getPageData();
        Map<String, Object> map = new HashMap();
        try {
            PageData pd1 = productService.findById(pd);
            PageData userQuan = user_wendaQuanxianService.findQByUserId(pd);
            if ("1".equals(pd1.getString("HSTATUS"))) {
                if (null == userQuan) {
                    pd1.put("KAOSHI", "1");//没考过
                    pd1.put("PAYSTA", "0");
                } else {
                    pd1.put("KAOSHI", "0");//考过
                    pd1.put("PAYSTA", userQuan.getString("PAYSTA"));
                }
            } else {
                pd1.put("KAOSHI", "0");
                pd1.put("PAYSTA", "0");
            }
            pd1.put("HURL", pd1.getString("HURL") + "?USER_ID=" + USER_ID + "&PRODUCT_ID=" + PRODUCT_ID);
            PageData user = appuserService.findById(pd);
            pd1.put("VIP", user.get("VIP").toString());
            PageData re = researchService.findProHuo(pd1);
            if (null != re) {
                if ("2".equals(re.get("LSTATUS").toString())) {
                    pd1.put("RESEARCH_ID", re.get("RESEARCH_ID").toString());
                } else {
                    pd1.put("RESEARCH_ID", "");
                }
            } else {
                pd1.put("RESEARCH_ID", "");
            }
            List<PageData> list1 = remarkService.findList(pd1);
            List<PageData> list = p_QuanXuanService.findList(pd);
            if (list != null && list.size() != 0) {
                PageData pd_p = p_QuanXuanService.findById(pd);
                if (pd_p == null) {
                    if (!"1".equals(pd1.get("HSTATUS").toString())) {
                        for (int i = 0; i < list1.size(); i++) {
                            list1.get(i).put("EXPLAIN1", "点击咨询价格!");
                        }
                    }
                }
            }
            pd1.put("COLLECTION", "0");
            PageData pd_ca = cartService.findCount(pd);
            pd1.put("CART", pd_ca.get("count"));
            map.put("object", pd1);
            map.put("list2", list1);
            List<PageData> lists = researchService.findlimitTou(pd);
            for (int i = 0; i < lists.size(); i++) {
                String str = DateUtil.delHTMLTag(lists.get(i).getString("MESSAGE"));
                String DETAILS1 = str.replace("\r", "");
                String DETAILS2 = DETAILS1.replace("\n", "");
                String DETAILS3 = DETAILS2.replace("&nbsp;", "");
                if (DETAILS3.length() < 20) {
                    lists.get(i).put("MESSAGE", DETAILS3);
                } else {
                    str = DETAILS3.substring(0, 20);
                    lists.get(i).put("MESSAGE", DETAILS3);
                }
            }
            List<PageData> list11 = researchService.findlimits(pd);
            PageData pd_r = researchService.findCount(pd);

            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("data", map);
            pd.put("COUNT", pd_r.get("count1").toString());
            pd.put("list", list11);
            pd.put("plist", lists);
            pd.put("url", "http://www.meitiannongzi.com/nongjike/static/jsp/product.html?PRODUCT_ID=" + PRODUCT_ID);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    //查询套餐
    @RequestMapping(value = "findTaoCan", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findTaoCan(String PRODUCT_ID, String USER_ID) {
        logBefore(logger, "查询套餐");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            PageData pd1 = productService.findById(pd);
            List<PageData> list = taoCanService.findLists(pd);
            PageData pd_ca = cartService.findCount(pd);
            for (int i = 0; i < list.size(); i++) {
                list.get(i).put("USER_ID", USER_ID);
                List<PageData> list_p = p_QuanXuanService.findList(list.get(i));
                PageData pd_p = new PageData();
                if (list_p != null && list_p.size() != 0) {
                    pd_p = p_QuanXuanService.findById(list.get(i));
                }
                List<PageData> list1 = remarkService.findList(list.get(i));
                if (pd_p == null) {
                    list1.get(0).put("EXPLAIN1", "点击咨询价格!");
                }
                list.get(i).put("COLLECTION", "1");
                list.get(i).put("remark", list1);
            }
            PageData pd_c = comment_MailService.findCount1(pd);
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("data", list);
            pd.put("pro", pd1);
            pd.put("CART", pd_ca.get("count").toString());
            pd.put("url", "http://www.meitiannongzi.com/nongjike/static/jsp/taocan.html?PRODUCT_ID=" + PRODUCT_ID);
            pd.put("TIMG", pd1.getString("TIMG"));
            pd.put("count", pd_c.get("count1").toString());
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    @RequestMapping(value = "findCouponByUser", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findCouponByUser(String USER_ID) {
        logBefore(logger, "=====================获取活动");
        Map<Object, Object> map = new HashMap();
        List<PageData> pdList = null;
        PageData pd = null;
        try {
            pd = this.getPageData();
            pdList = gameCouponService.getAllCoupon(pd);
            map.put("code", "1");
            map.put("data", pdList);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", "2");
            map.put("message", "程序出错请联系管理员！");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(map);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }

    //查询全部商品
    @RequestMapping(value = "findProducesAll", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findProducesAll(String USER_ID) {
        logBefore(logger, "查询全部商品");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            pd.put("STATUS", "0");
            List<PageData> list = productService.findList(pd);
            for (PageData pro : list) {
                if (null == pro.getString("GGIMG")) {
                    pro.put("GGIMG", "");
                }
                PageData re = researchService.findProHuo(pro);
                if (null != re) {
                    if (null != re.get("LSTATUS") && 2 == Integer.parseInt(re.get("LSTATUS").toString())) {
                        pro.put("RESEARCH_ID", re.get("RESEARCH_ID").toString());
                    } else {
                        pro.put("RESEARCH_ID", "");
                    }
                } else {
                    pro.put("RESEARCH_ID", "");
                }
            }
            PageData pd_c = cartService.findCount(pd);
            PageData pd5 = couponService.findZstatus(pd);
            couponService.editZStatus(pd5);
            String STATUS5 = "0";
            if (pd5 != null) {
                STATUS5 = "1";
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("data", list);
            pd.put("count", pd_c.get("count").toString());
            pd.put("STATUS5", STATUS5);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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


    @RequestMapping(value = "WeiXinWenDaTieZi", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String WeiXinWenDaTieZi(String WENDA_TIEZI_ID, String url) {
        logBefore(logger, "微信查询问答帖子");
        PageData pd = new PageData();
        pd = this.getPageData();
        String jsapiTicket = "";
        try {
            PageData pd12 = weiXinService.findById(pd);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long difference = format.parse(format.format(new Date())).getTime()
                    - format.parse(pd12.getString("DATE")).getTime();
            if (difference > 360000) { // 标签超时,重新到微信服务器请求标签超时时间为7200秒（7200000毫秒）
                String access_token = WeiXin.access_token();
                jsapiTicket = WeiXin.jsapiTicket(access_token);
                pd12.put("ticket", jsapiTicket);
                pd12.put("DATE", DateUtil.getTime());
                weiXinService.edit(pd12);
            } else {
                jsapiTicket = pd12.getString("ticket");
            }
            Map<String, String> cf = WeiXin.sign1(jsapiTicket, url);
            PageData pd_wenda = wenDaTieZiService.findById(pd);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("signature", cf.get("signature"));
            pd.put("nonceStr", cf.get("nonceStr"));
            pd.put("timestamp", cf.get("timestamp"));
            pd.put("url", cf.get("url"));
            pd.put("appId", "wx6b912be972e69695");
            pd.put("data", pd_wenda);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    @RequestMapping(value = "WeiXinfindZPaiHangBang", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String WeiXinfindZPaiHangBang(String USER_ID, String STATUS, String url) {
        logBefore(logger, "查询周排行榜");
        PageData pd = new PageData();
        pd = this.getPageData();
        SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, Object> map = DateUtil.ThisMonth();
        String jsapiTicket = "";
        try {
            PageData pd12 = weiXinService.findById(pd);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long difference = format.parse(format.format(new Date())).getTime()
                    - format.parse(pd12.getString("DATE")).getTime();
            if (difference > 360000) { // 标签超时,重新到微信服务器请求标签超时时间为7200秒（7200000毫秒）
                String access_token = WeiXin.access_token();
                jsapiTicket = WeiXin.jsapiTicket(access_token);
                pd12.put("ticket", jsapiTicket);
                pd12.put("DATE", DateUtil.getTime());
                weiXinService.edit(pd12);
            } else {
                jsapiTicket = pd12.getString("ticket");
            }
            Map<String, String> cf = WeiXin.sign1(jsapiTicket, url);
            List<PageData> list = new ArrayList();
            PageData pd_e = new PageData();
            if (STATUS.equals("0")) {
                pd.put("DATE", sdfDay.format(DateUtil.getThisWeekMonday(new Date())) + " 00:00:00");
                list = zuoGuo_ShiJuanService.findZuoPaiHang(pd);
                pd_e = zuoGuo_ShiJuanService.findZhouPaiMing(pd);
            } else if (STATUS.equals("1")) {
                pd.put("DATE", map.get("first").toString());
                list = zuoGuo_ShiJuanService.findZuoPaiHang(pd);
                pd_e = zuoGuo_ShiJuanService.findZhouPaiMing(pd);
            } else {
                pd.put("NIAN", DateUtil.getYear());
                list = zuoGuo_ShiJuanService.FractionpageList(pd);
                pd_e = zuoGuo_ShiJuanService.findFractionPaiMing(pd);
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("data", list);
            pd.put("paiming", pd_e);
            pd.put("signature", cf.get("signature"));
            pd.put("nonceStr", cf.get("nonceStr"));
            pd.put("timestamp", cf.get("timestamp"));
            pd.put("url", cf.get("url"));
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(pd);
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

    @RequestMapping(value = "WenXinFenXiang", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String WenXinFenXiang(String url, String USER_ID) {
        logBefore(logger, "微信分享个人信息");
        PageData pd = new PageData();
        pd = this.getPageData();
        SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
        String jsapiTicket = "";
        pd.put("NIAN", DateUtil.getYear());
        try {
            //本周排行榜
            pd.put("DATE", sdfDay.format(DateUtil.getThisWeekMonday(new Date())) + " 00:00:00");
            PageData pd_e = zuoGuo_ShiJuanService.findZhouPaiMing(pd);          //查询周排行
            PageData pd_ee = error_WendaService.findZhouZuoTi(pd);          //查询周排行榜正确率
            Map<String, Object> map = DateUtil.ThisMonth();
            pd.put("DATE", map.get("first").toString());
            PageData pd_y = zuoGuo_ShiJuanService.findZhouPaiMing(pd);          //查询月排行
            PageData pd_yy = error_WendaService.findZhouZuoTi(pd);          //查询月排行榜正确率
            PageData pd_f = zuoGuo_ShiJuanService.findNianPaiMing(pd);//查询年排行

            PageData pd_ff = error_WendaService.findNianZuoTi(pd);//查询年排行榜正确率
            pd.put("first", map.get("firstDay").toString());
            pd.put("DATE", map.get("lastDay").toString());
            PageData pd_Qy = zuoGuo_ShiJuanService.findDateZuoGuoPaiHangBang(pd);          //查询上月排行
            Map<String, Object> map1 = DateUtil.ThisZhou();
            pd.put("first", map1.get("firstZhou").toString());
            pd.put("DATE", map1.get("lastZhou").toString());
            PageData pd_Qz = zuoGuo_ShiJuanService.findDateZuoGuoPaiHangBang(pd);          //查询上周排行
            PageData pd_d = qianDaoService.findUser(pd);
            if (pd_e == null) {
                pd_e = new PageData();
                pd_e.put("count1", "null");
            }
            if (pd_y == null) {
                pd_y = new PageData();
                pd_y.put("count1", "null");
            }
            if (pd_f == null) {
                pd_f = new PageData();
                pd_f.put("count1", "null");
            }
            if (pd_Qz == null) {
                pd_Qz = new PageData();
                pd_Qz.put("count1", "null");
            }
            if (pd_Qy == null) {
                pd_Qy = new PageData();
                pd_Qy.put("count1", "null");
            }
            PageData pd12 = weiXinService.findById(pd);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long difference = format.parse(format.format(new Date())).getTime()
                    - format.parse(pd12.getString("DATE")).getTime();
            if (difference > 360000) { // 标签超时,重新到微信服务器请求标签超时时间为7200秒（7200000毫秒）
                String access_token = WeiXin.access_token();
                jsapiTicket = WeiXin.jsapiTicket(access_token);
                pd12.put("ticket", jsapiTicket);
                pd12.put("DATE", DateUtil.getTime());
                weiXinService.edit(pd12);
            } else {
                jsapiTicket = pd12.getString("ticket");
            }
            Map<String, String> cf = WeiXin.sign1(jsapiTicket, url);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("ThisWeek", pd_e);          //本周
            pd.put("ThisMonth", pd_y);          //本月
            pd.put("ThisYear", pd_f);          //本年
            pd.put("LastWeek", pd_Qz);          //上周
            pd.put("LastYear", pd_Qy);          //上月
            pd.put("ZhouCorrect", pd_ee);          //周排行榜正确率
            pd.put("YueCorrect", pd_yy);          //查询月排行榜正确率
            pd.put("NianCorrect", pd_ff);          //查询年排行榜正确率
            pd.put("data", pd_d);                    //签到信息
            pd.put("signature", cf.get("signature"));
            pd.put("nonceStr", cf.get("nonceStr"));
            pd.put("timestamp", cf.get("timestamp"));
            pd.put("url", cf.get("url"));
            pd.put("appId", "wx6b912be972e69695");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "正确返回数据!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    @RequestMapping(value = "findShiFuWeiXin", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findShiFuWeiXin(String USER_ID) {
        logBefore(logger, "查询是否未新");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            pd.put("WENDA_TYPE_ID", "1");
            PageData pd_e0 = wenDa_ShiJuanService.findPage(pd);          //肥料
            pd.put("WENDA_TYPE_ID", "2");
            PageData pd_e2 = wenDa_ShiJuanService.findPage(pd);          //农药
            pd.put("WENDA_TYPE_ID", "3");
            PageData pd_e3 = wenDa_ShiJuanService.findPage(pd);          //病虫害
            pd.put("WENDA_TYPE_ID", "5");
            PageData pd_e5 = wenDa_ShiJuanService.findPage(pd);          //管理
            String STATUS0 = "0";
            String STATUS1 = "0";
            String STATUS2 = "0";
            String STATUS3 = "0";
            if (pd_e0 != null) {
                STATUS0 = "1";
            }
            if (pd_e2 != null) {
                STATUS1 = "1";
            }
            if (pd_e3 != null) {
                STATUS2 = "1";
            }
            if (pd_e5 != null) {
                STATUS3 = "1";
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("STATUS0", STATUS0);
            pd.put("STATUS1", STATUS1);
            pd.put("STATUS2", STATUS2);
            pd.put("STATUS3", STATUS3);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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


    @RequestMapping(value = "findWenDaTieZiShuZi", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public void findWenDaTieZi(String WENDA_TIEZI_ID) {
        logBefore(logger, "记录数字");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            wenDaTieZiService.editShuZi(pd);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
    }

    // 查询已做试卷
    @RequestMapping(value = "findYiZuoWenDas", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findYiZuoWenDas(String USER_ID, String pageNum, String WENDA_TYPE_ID) {
        logBefore(logger, "查询已做试卷");
        PageData pd = new PageData();
        pd = this.getPageData();
        Page page = new Page();
        String message = "正确返回数据!";
        try {
            if (pageNum == null || pageNum.length() == 0) {
                pageNum = "1";
            }
            pd.put("YUE", DateUtil.getDay());
            page.setPd(pd);
            page.setShowCount(20);
            page.setCurrentPage(Integer.parseInt(pageNum));
            List<PageData> list = zuoGuo_ShiJuanService.yizuolistPage(page);
            Map<String, Object> map = new HashedMap();
            if (page.getCurrentPage() == Integer.parseInt(pageNum)) {
                map.put("object", list);
            } else {
                List<PageData> list4 = new ArrayList();
                message = "已加载全部数据!";
                map.put("object", list4);
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("message", message);
            pd.put("data", map);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    @RequestMapping(value = "findWenDaTieZi", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findWenDaTieZi() {
        logBefore(logger, "查询问答帖子");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            pd.put("GSTATUS", "1");
            PageData pd_g = wenDaTieZiService.findGuang(pd);
            List<PageData> list = wenDaTieZiService.findList(pd);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("GongGao", pd_g);
            pd.put("data", list);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(pd);
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

    @RequestMapping(value = "findSuiJis", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findSuiJis(String USER_ID, String WENDA_TYPE_ID) {
        logBefore(logger, "查询随机考题");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            List<PageData> list = wenDaService.findSuiJis(pd);
            for (int i = 0, len = list.size(); i < len; i++) {
                list.get(i).put("CORRECT", "");
                list.get(i).put("XUANZE", "");
                list.get(i).put("USER_ID", USER_ID);
                list.get(i).put("DATE", DateUtil.getTime());
                List<PageData> info = wenDa_InfnService.findList(list.get(i));
                if (collection_WendaService.findById(list.get(i)) != null) {
                    list.get(i).put("COLLECTION", "1");
                } else {
                    list.get(i).put("COLLECTION", "0");
                }
                list.get(i).put("INFO", info);
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("data", list);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(pd);
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

    @RequestMapping(value = "findShiJuans", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findShiJuans(String USER_ID, String WENDA_TYPE_ID) {
        logBefore(logger, "查询试卷");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            List<PageData> list = wenDa_ShiJuanService.findweizuoList(pd);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("data", list);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(pd);
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

    @RequestMapping(value = "findPaiHangBangXinXi", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findPaiHangBangXinXi(String USER_ID) {
        logBefore(logger, "查询排行榜自己信息");
        PageData pd = new PageData();
        pd = this.getPageData();
        SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
        pd.put("NIAN", DateUtil.getYear());
        try {
            //本周排行榜
            pd.put("DATE", sdfDay.format(DateUtil.getThisWeekMonday(new Date())) + " 00:00:00");
            PageData pd_e = zuoGuo_ShiJuanService.findZhouPaiMing(pd);          //查询周排行
            PageData pd_ee = error_WendaService.findZhouZuoTi(pd);          //查询周排行榜正确率
            Map<String, Object> map = DateUtil.ThisMonth();
            pd.put("DATE", map.get("first").toString());
            PageData pd_y = zuoGuo_ShiJuanService.findZhouPaiMing(pd);          //查询月排行
            PageData pd_yy = error_WendaService.findZhouZuoTi(pd);          //查询月排行榜正确率
            PageData pd_f = zuoGuo_ShiJuanService.findNianPaiMing(pd);//查询年排行
            PageData pd_ff = error_WendaService.findNianZuoTi(pd);//查询年排行榜正确率
            pd.put("first", map.get("firstDay").toString());
            pd.put("DATE", map.get("lastDay").toString());
            PageData pd_Qy = zuoGuo_ShiJuanService.findDateZuoGuoPaiHangBang(pd);          //查询上月排行
            Map<String, Object> map1 = DateUtil.ThisZhou();
            pd.put("first", map1.get("firstZhou").toString());
            pd.put("DATE", map1.get("lastZhou").toString());
            PageData pd_Qz = zuoGuo_ShiJuanService.findDateZuoGuoPaiHangBang(pd);          //查询上周排行
            PageData pd_d = qianDaoService.findUserId(pd);
            if (pd_e == null) {
                pd_e = new PageData();
                pd_e.put("count1", "null");
            }
            if (pd_y == null) {
                pd_y = new PageData();
                pd_y.put("count1", "null");
            }
            if (pd_f == null) {
                pd_f = new PageData();
                pd_f.put("count1", "null");
            }
            if (pd_Qz == null) {
                pd_Qz = new PageData();
                pd_Qz.put("count1", "null");
            }
            if (pd_Qy == null) {
                pd_Qy = new PageData();
                pd_Qy.put("count1", "null");
            }

            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("ThisWeek", pd_e);          //本周
            pd.put("ThisMonth", pd_y);          //本月
            pd.put("ThisYear", pd_f);          //本年
            pd.put("LastWeek", pd_Qz);          //上周
            pd.put("LastYear", pd_Qy);          //上月
            pd.put("ZhouCorrect", pd_ee);          //周排行榜正确率
            pd.put("YueCorrect", pd_yy);          //查询月排行榜正确率
            pd.put("NianCorrect", pd_ff);          //查询年排行榜正确率
            pd.put("data", pd_d);                    //签到信息
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(pd);
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

    @RequestMapping(value = "findZPaiHangBang", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findZPaiHangBang(String USER_ID, String STATUS) {
        logBefore(logger, "查询周排行榜");
        PageData pd = new PageData();
        pd = this.getPageData();
        SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, Object> map = DateUtil.ThisMonth();
        try {
            List<PageData> list = new ArrayList();
            PageData pd_e = new PageData();
            if (STATUS.equals("0")) {
                pd.put("DATE", sdfDay.format(DateUtil.getThisWeekMonday(new Date())) + " 00:00:00");
                list = zuoGuo_ShiJuanService.findZuoPaiHang(pd);
                pd_e = zuoGuo_ShiJuanService.findZhouPaiMing(pd);
            } else if (STATUS.equals("1")) {
                pd.put("DATE", map.get("first").toString());
                list = zuoGuo_ShiJuanService.findZuoPaiHang(pd);
                pd_e = zuoGuo_ShiJuanService.findZhouPaiMing(pd);
            } else {
                pd.put("NIAN", DateUtil.getYear());
                list = zuoGuo_ShiJuanService.FractionpageList(pd);
                pd_e = zuoGuo_ShiJuanService.findFractionPaiMing(pd);
            }
            List<PageData> list2 = new ArrayList();
            if (!DateUtil.getDay().equals(sdfDay.format(DateUtil.getThisWeekMonday(new Date())))) {
                pd.put("DATE2", DateUtil.getDay() + " 00:00:00");
                pd.put("DATE", DateUtil.getSpecifiedDayBefore(DateUtil.getTime()) + " 00:00:00");
                list2 = zuoGuo_ShiJuanService.findZhouPaiMings(pd);
            }
            pd.put("GSTATUS", "1");
            PageData pd_g = wenDaTieZiService.findGuang(pd);
            if (pd_e == null) {
                PageData pd_u = appuserService.findById(pd);
                pd_e = new PageData();
                pd_e.put("WENDA_COUNT_ID", "");
                pd_e.put("USER_ID", USER_ID);
                pd_e.put("count1", 0);
                pd_e.put("rowno", 0);
                pd_e.put("NAME", pd_u.getString("NAME"));
                pd_e.put("IMG", pd_u.getString("IMG"));
                pd_e.put("paiming", "");
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("object", list);
            pd.put("data", pd_e);
            pd.put("GongGao", pd_g);
            pd.put("objects", list2);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(pd);
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

    //删除浏览记录
    @RequestMapping(value = "deleteHistory", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String deleteHistory(String STATUS, String USER_ID, String HISTORY_ID[]) {
        logBefore(logger, "删除自己记录");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            if (STATUS.equals("1")) {
                historyService.deleteAll(pd);
            } else {
                historyService.deletes(HISTORY_ID);
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(pd);
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


    @RequestMapping(value = "saveWanCheng", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String saveWanCheng(String USER_ID, String STATUS) {
        logBefore(logger, "统计");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            pd.put("DATE", DateUtil.getTime());
            wanChengService.save(pd);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(pd);
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

    @RequestMapping(value = "saveFenXiang", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String saveFenXiang(String USER_ID, String STATUS) {
        logBefore(logger, "分享统计");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            pd.put("DATE", DateUtil.getTime());
            fenXiangCService.save(pd);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(pd);
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

    // ==============================查询我关注的专题===================================
    @RequestMapping(value = "findCollectionSpecial2", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findCollectionSpecial2(String USER_ID, String pageNum) {
        logBefore(logger, "查询我关注的专题");
        PageData pd = new PageData();
        pd = this.getPageData();
        Page page = new Page();
        String message = "正确返回数据";
        try {
            if (pageNum == null || pageNum.length() == 0) {
                pageNum = "1";
            }
            page.setPd(pd);
            page.setShowCount(10);
            page.setCurrentPage(Integer.parseInt(pageNum));
            List<PageData> list = collection_specialService.datalistPage(page);
            Map<String, Object> map = new HashedMap();
            if (page.getCurrentPage() == Integer.parseInt(pageNum)) {
                map.put("object", list);
            } else {
                List<PageData> list4 = new ArrayList();
                message = "已加载全部数据!";
                map.put("object", list4);
            }
            collection_specialService.editStatus2(pd);
            pd.clear();
            pd.put("code", "1");
            pd.put("data", map);
            pd.put("message", message);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(pd);
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

    // ===========================查询关注的直播间=================================
    @RequestMapping(value = "findCollectionActivityRoome", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findCollectionActivityRoome(String USER_ID, String pageNum) {
        logBefore(logger, "查询关注的直播间");
        PageData pd = new PageData();
        pd = this.getPageData();
        Page page = new Page();
        String message = "正确返回数据!";
        try {
            if (pageNum == null || pageNum.length() == 0) {
                pageNum = "1";
            }
            page.setPd(pd);
            page.setShowCount(10);
            page.setCurrentPage(Integer.parseInt(pageNum));
            List<PageData> list = collection_RoomService.list(page);
            Map<String, Object> map = new HashedMap();
            if (page.getCurrentPage() == Integer.parseInt(pageNum)) {
                map.put("object", list);
            } else {
                List<PageData> list4 = new ArrayList();
                message = "已加载全部数据!";
                map.put("object", list4);
            }
            PageData pd1 = new PageData();
            pd1.put("STATUS", "0");
            pd1.put("USER_ID", USER_ID);
            collection_RoomService.editStatus1(pd1);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", message);
            pd.put("data", map);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // ===========================查询状态======================================
    @RequestMapping(value = "findWoDeStatus", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findWoDeStatus(String USER_ID) {
        logBefore(logger, "查询状态");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            String STATUS2 = "0";
            String STATUS3 = "0";
            String STATUS4 = "0";
            String STATUS6 = "0";
            PageData pd1 = appuserService.findById111(pd); // 消息
            PageData pd2 = collection_tiwenService.findListStatus(pd); // 关注问题
            PageData pd3 = collection_RoomService.findListStatus(pd); // 关注直播间
            PageData pd4 = collection_specialService.findListStatus(pd);// 关注专题
            PageData pd5 = couponService.findZstatus(pd);
            List<PageData> pd6 = gameCouponService.findReturnCoupon(pd);
            couponService.editZStatus(pd5);
            String STATUS5 = "0";
            System.out.println("================================>pd5"+pd5);
            System.out.println("================================>pd6"+pd6);
            if (pd5 != null) {
                STATUS5 = "1";
            } else {
                pd.put("RESTATUS", "1");
                gameCouponService.update(pd);
                if (pd6 != null && pd6.size() > 0) {
                    STATUS6 = "1";
                }
            }
            if (pd2 != null) {
                STATUS2 = "1";
            }
            if (pd3 != null) {
                STATUS3 = "1";
            }
            if (pd4 != null) {
                STATUS4 = "1";
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("STATUS1", pd1.getString("STATUS")); // 消息
            pd.put("STATUS2", STATUS2); // 关注的提问
            pd.put("STATUS3", STATUS3); // 关注的直播间
            pd.put("STATUS4", STATUS4); // 关注的专题
            pd.put("STATUS5", STATUS5);
            pd.put("STATUS6", STATUS6);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // ==========================发布提问IOS=======================================

    /**
     * @param
     * @param
     * @param
     * @param
     * @return
     */
    @RequestMapping(value = "saveTiWen3", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String saveTiWen3(String MESSAGE, String USER_ID, String img1, String img2, String img3, String img4,
                             String img5, String img6, String ADDRESS, String count) {
        logBefore(logger, "发布提问(IOS)");
        PageData pd = new PageData();
        pd = this.getPageData();
        String inform = "";
        try {
            if (MESSAGE == null && MESSAGE.length() == 0) {
                pd.clear();
                pd.put("code", "2");
                pd.put("message", "发布失败!");
            } else {
                String TIWEN_ID = this.get32UUID();
                pd.put("TIWEN_ID", TIWEN_ID);
                pd.put("DATE", DateUtil.getTime());
                pd.put("VIEWS", "0");
                pd.put("HUIFU", "0");
                pd.put("YUE", "");
                pd.put("SHENCHA", "0");
                pd.put("STATUS", "0");
                tiWenService.save(pd);
                String ffile1 = this.get32UUID() + ".jpg";
                String filePath2 = PathUtil.getClasspath() + Const.FILEPATHIMG + "tiwen/" + DateUtil.getDays(); // 文件上传路径
                File file = new File(filePath2, ffile1);
                if (!file.exists()) {
                    if (!file.getParentFile().exists()) {
                        file.getParentFile().mkdirs();
                    }
                }
                Integer count1 = Integer.valueOf(count);
                for (int i = 0; i < count1; i++) {
                    String ffile = this.get32UUID() + ".jpg";
                    String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + "tiwen/" + DateUtil.getDays() + "/"
                            + ffile; // 文件上传路径
                    String TOUR_IMG1 = Const.SERVERPATH + Const.FILEPATHIMG + "tiwen/" + DateUtil.getDays() + "/"
                            + ffile;
                    String ii = String.valueOf(i + 1);
                    pd.put("TIWEN_ID", TIWEN_ID);
                    pd.put("IMG", TOUR_IMG1);
                    pd.put("ORDE_BY", ii);
                    tiWen_ImgService.save(pd);
                    new Thread(new Thread2(pd.getString("img" + ii), TOUR_IMG1, filePath, TIWEN_ID, ffile,
                            post_ImgService, i)).start();
                }
                //*****************增加用户积分*****************
                String today = DateUtil.getDay();
                PageData comDto = new PageData();
                comDto.put("community_type", "2");
                comDto.put("create_date", today);
                comDto.put("USER_ID", USER_ID);
                pd.put("JIFEN", 1);
                appuserService.editJifen(pd);
                appuserService.addZJIFEN(pd);
                comDto.put("NUM", "1");
                comDto.put("community_id", pd.get("TIWEN_ID").toString());
                comDto.put("MIAOSHU", "发布提问");
                comDto.put("ORDER_NUM", "");
                countCommunityService.save(comDto);
                inform = "提问发布完成，获取有效积分+1";
                pd.clear();
                pd.put("code", "1");
                pd.put("inform", inform);
                pd.put("message", "正确返回数据!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // ==========================发布提问=======================================

    /**
     * @param
     * @param
     * @param
     * @param
     * @return
     */
    @RequestMapping(value = "saveTiWen2", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String saveTiWen2(String MESSAGE, String USER_ID, String img1, String img2, String img3, String img4,
                             String img5, String img6, String LAT, String LNG, String count) {
        logBefore(logger, "发布提问");
        PageData pd = new PageData();
        pd = this.getPageData();
        String ss = "";
        String inform = "";
        try {
            if (MESSAGE == null && MESSAGE.length() == 0) {
                pd.clear();
                pd.put("code", "2");
                pd.put("message", "发布失败!");
            } else {
                if (LAT != null && LAT.length() > 4 && LNG != null && LNG.length() > 4) {
                    ss = MapDistance.getCoordinate3(LNG, LAT);
                } else {
                    ss = "农极客";
                }
                System.out.println(ss);
                String TIWEN_ID = this.get32UUID();
                pd.put("TIWEN_ID", TIWEN_ID);
                pd.put("DATE", DateUtil.getTime());
                pd.put("VIEWS", "0");
                pd.put("HUIFU", "0");
                pd.put("YUE", "");
                pd.put("ADDRESS", ss);
                pd.put("SHENCHA", "0");
                pd.put("STATUS", "0");
                pd.put("MESSAGE", MESSAGE.replace("!==!", "\n"));
                tiWenService.save(pd);
                String ffile1 = this.get32UUID() + ".jpg";
                String filePath2 = PathUtil.getClasspath() + Const.FILEPATHIMG + "tiwen/" + DateUtil.getDays(); // 文件上传路径
                File file = new File(filePath2, ffile1);
                if (!file.exists()) {
                    if (!file.getParentFile().exists()) {
                        file.getParentFile().mkdirs();
                    }
                }
                Integer count1 = Integer.valueOf(count);
                for (int i = 0; i < count1; i++) {
                    String ffile = this.get32UUID() + ".jpg";
                    String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + "tiwen/" + DateUtil.getDays() + "/"
                            + ffile; // 文件上传路径
                    String TOUR_IMG1 = Const.SERVERPATH + Const.FILEPATHIMG + "tiwen/" + DateUtil.getDays() + "/"
                            + ffile;
                    String ii = String.valueOf(i + 1);
                    pd.put("TIWEN_ID", TIWEN_ID);
                    pd.put("IMG", TOUR_IMG1);
                    pd.put("ORDE_BY", ii);
                    tiWen_ImgService.save(pd);
                    new Thread(new Thread2(pd.getString("img" + ii), TOUR_IMG1, filePath, TIWEN_ID, ffile,
                            post_ImgService, i)).start();
                }

                //*****************增加用户积分*****************
                String today = DateUtil.getDay();
                PageData comDto = new PageData();
                comDto.put("community_type", "2");
                comDto.put("create_date", today);
                comDto.put("USER_ID", USER_ID);
                pd.put("JIFEN", 1);
                appuserService.editJifen(pd);
                appuserService.addZJIFEN(pd);
                comDto.put("NUM", "1");
                comDto.put("community_id", pd.get("TIWEN_ID").toString());
                comDto.put("MIAOSHU", "发布提问");
                comDto.put("ORDER_NUM", "");
                countCommunityService.save(comDto);
                inform = "提问发布完成，获取有效积分+1";
                pd.clear();
                pd.put("code", "1");
                pd.put("inform", inform);
                pd.put("message", "正确返回数据!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // ==========================查询直播间详情==================================
    @RequestMapping(value = "findActivity_RoomId", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findActivity_RoomId(String USER_ID, String ACTIVITY_ID, String QUSER_ID) {
        logBefore(logger, "查询直播间详情");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            PageData pd_a = activity_RoomService.findById2(pd);
            if (collection_Activity_UserService.findById(pd) != null) {
                pd_a.put("ZAN", "1");
            } else {
                pd_a.put("ZAN", "0");
            }
            if (collection_RoomService.findById(pd) != null) {
                pd_a.put("COLLECTION", "1");
            } else {
                pd_a.put("COLLECTION", "0");
            }
            pd_a.put("url", "");
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("data", pd_a);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "正确返回数据!");
        }
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(pd);
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

    // ===========================查询收藏问题=================================
    @RequestMapping(value = "findWendaUser", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findWendaUser(String USER_ID, String pageNum) {
        logBefore(logger, "查询收藏问题");
        PageData pd = new PageData();
        Page page = new Page();
        pd = this.getPageData();
        String message = "正确返回数据!";
        try {
            if (pageNum == null || pageNum.length() == 0) {
                pageNum = "1";
            }
            page.setPd(pd);
            page.setShowCount(10);
            page.setCurrentPage(Integer.parseInt(pageNum));
            List<PageData> list = collection_tiwenService.userlistPage(page);
            for (int i = 0; i < list.size(); i++) {
                list.get(i).put("url",
                        "http://m.nongjike.cn/NJK/static/jsp/tiwen.html?TIWEN_ID=" + list.get(i).getString("TIWEN_ID"));
            }
            Map<String, Object> map = new HashedMap();
            if (page.getCurrentPage() == Integer.parseInt(pageNum)) {
                map.put("object", list);
            } else {
                List<PageData> list4 = new ArrayList();
                message = "已加载全部数据!";
                map.put("object", list4);
            }
            collection_tiwenService.editStatus2(pd);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", message);
            pd.put("data", map);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(pd);
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

    // ===========================查询历史记录=====================================
    @RequestMapping(value = "findHistory", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findHistory(String USER_ID, String pageNum) {
        logBefore(logger, "查询历史记录");
        PageData pd = new PageData();
        Page page = new Page();
        pd = this.getPageData();
        String message = "正确返回数据!";
        try {
            if (pageNum == null || pageNum.length() == 0) {
                pageNum = "1";
            }
            page.setPd(pd);
            page.setShowCount(10);
            page.setCurrentPage(Integer.parseInt(pageNum));
            List<PageData> list = historyService.list(page);
            Map<String, Object> map = new HashedMap();
            if (page.getCurrentPage() == Integer.parseInt(pageNum)) {
                map.put("object", list);
            } else {
                List<PageData> list4 = new ArrayList();
                message = "已加载全部数据!";
                map.put("object", list4);
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("message", message);
            pd.put("data", map);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(pd);
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

    // ==============================关注专题取消关注================================
    @RequestMapping(value = "CollectionSpecial", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String CollectionSpecial(String STATUS, String USER_ID, String FID) {
        logBefore(logger, "关注专题取消关注");
        PageData pd = new PageData();
        pd = this.getPageData();
        String STATUS1 = "0";
        try {
            if (STATUS.equals("1")) {
                if (collection_specialService.findById(pd) == null) {
                    pd.put("COLLECTION_SPECIAL_ID", this.get32UUID());
                    pd.put("DATE", DateUtil.getTime());
                    pd.put("STATUS", "0");
                    collection_specialService.save(pd);
                }
                STATUS1 = "1";
            } else {
                collection_specialService.delete(pd);
            }
            pd.clear();
            pd.put("message", "正确返回数据!");
            pd.put("code", "1");
            pd.put("STATUS", STATUS1);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(pd);
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

    // ===========================根据专题查询评论===================================
    @RequestMapping(value = "findSpecialPingL", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findSpecialPingL(String FID, String pageNum, String USER_ID) {
        logBefore(logger, "根据专题查询评论");
        PageData pd = new PageData();
        Page page = new Page();
        pd = this.getPageData();
        String message = "正确返回数据!";
        try {
            if (pageNum == null || pageNum.length() == 0) {
                pageNum = "1";
            }
            pd.put("STATUS", "0");
            page.setPd(pd);
            page.setShowCount(10);
            page.setCurrentPage(Integer.parseInt(pageNum));
            List<PageData> list = comment_specialService.list(page);
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getString("PNAME") == null) {
                    list.get(i).put("PNAME", "");
                }
                list.get(i).put("USER_ID1", USER_ID);
                if (zanCommentSpecialService.findById(list.get(i)) != null) {
                    list.get(i).put("ZAN", "1");
                } else {
                    list.get(i).put("ZAN", "0");
                }
                list.get(i).put("YUE", list.get(i).getString("DATE"));
                List<PageData> list1 = comment_SpecialImgService.findList(list.get(i));
                list.get(i).put("CIMG", list1);
                list.get(i).put("STATUS", "1");
                List<PageData> list2 = comment_specialService.findList(list.get(i));
                for (int f = 0; f < list2.size(); f++) {
                    if (list2.get(f).getString("PNAME") == null) {
                        list2.get(f).put("PNAME", "");
                    }
                }
                list.get(i).put("COMMENT", list2);
                list.get(i).put("VIEWS", zanCommentSpecialService.findCount(list.get(i)).get("count1").toString());
            }
            Map<String, Object> map = new HashedMap();
            if (page.getCurrentPage() == Integer.parseInt(pageNum)) {
                map.put("object", list);
            } else {
                List<PageData> list4 = new ArrayList();
                message = "已加载全部数据!";
                map.put("object", list4);
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("message", message);
            pd.put("data", map);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(pd);
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

    // ===========================根据专题类型查询帖子===========================
    @RequestMapping(value = "findpecial_typeId", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findpecial_typeId(String FID, String POST_SPECIAL_TYPE_ID, String pageNum) {
        logBefore(logger, "根据专题类型查询帖子");
        PageData pd = new PageData();
        Page page = new Page();
        pd = this.getPageData();
        String message = "正确返回数据!";
        try {
            if (pageNum == null || pageNum.length() == 0) {
                pageNum = "1";
            }
            page.setPd(pd);
            page.setShowCount(7);
            page.setCurrentPage(Integer.parseInt(pageNum));
            List<PageData> list = postService.data3listPage(page);
            for (int i = 0, len = list.size(); i < len; i++) {
                String str = DateUtil.delHTMLTag(list.get(i).getString("MESSAGE"));
                String DETAILS1 = str.replace("\r", "");
                String DETAILS2 = DETAILS1.replace("\n", "");
                String DETAILS3 = DETAILS2.replace("&nbsp;", "");
                int qian = 56;
                if (DETAILS3.length() < 56) {
                    list.get(i).put("MESSAGE", DETAILS3);
                } else {
                    str = DETAILS3.substring(0, qian);
                    list.get(i).put("MESSAGE", str + "...");
                }
                List<PageData> list2 = post_ImgService.findList(list.get(i));
                list.get(i).put("POST_IMG", list2);
            }
            Map<String, Object> map = new HashedMap();
            if (page.getCurrentPage() == Integer.parseInt(pageNum)) {
                map.put("object", list);
            } else {
                List<PageData> list4 = new ArrayList();
                message = "已加载全部数据!";
                map.put("object", list4);
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("message", message);
            pd.put("data", map);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // ===========================查询精华=================================
    @RequestMapping(value = "findJIngHua", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findJIngHua(String pageNum) {
        logBefore(logger, "查询精华");
        PageData pd = new PageData();
        pd = this.getPageData();
        Page page = new Page();
        String message = "正确返回数据!";
        try {
            if (pageNum == null || pageNum.length() == 0) {
                pageNum = "1";
            }
            page.setPd(pd);
            page.setShowCount(6);
            page.setCurrentPage(Integer.parseInt(pageNum));
            List<PageData> list = postService.meirilistPage(page);
            for (int i = 0, len = list.size(); i < len; i++) {
                List<PageData> list1 = post_ImgService.findList(list.get(i));
                list1.get(i).put("P_IMG", list1);
            }
            Map<String, Object> map = new HashedMap();
            if (page.getCurrentPage() == Integer.parseInt(pageNum)) {
                map.put("object", list);
            } else {
                List<PageData> list4 = new ArrayList();
                message = "已加载全部数据!";
                map.put("object", list4);
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("message", message);
            pd.put("data", map);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // ==========================查询每日一读=================================
    @RequestMapping(value = "findMeiRi", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findMeiRi(String USER_ID, String pageNum) {
        logBefore(logger, "查询每日一读");
        PageData pd = new PageData();
        pd = this.getPageData();
        Page page = new Page();
        String message = "正确返回数据!";
        try {
            if (pageNum == null || pageNum.length() == 0) {
                pageNum = "1";
            }
            page.setPd(pd);
            page.setShowCount(10);
            page.setCurrentPage(Integer.parseInt(pageNum));
            List<PageData> list = postService.meirilistPage(page);
            Map<String, Object> map = new HashedMap();
            if (page.getCurrentPage() == Integer.parseInt(pageNum)) {
                map.put("object", list);
            } else {
                List<PageData> list4 = new ArrayList();
                message = "已加载全部数据!";
                map.put("object", list4);
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("message", message);
            pd.put("data", map);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // ==========================查询问答评论===============================
    @RequestMapping(value = "findCommentTiWwen", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findCommentTiWwen(String USER_ID, String TIWEN_ID, String pageNum) {
        logBefore(logger, "查询问答评论");
        PageData pd = new PageData();
        Page page = new Page();
        pd = this.getPageData();
        String message = "正确返回数据!";
        try {
            pd.put("STATUS", "0");
            if (pageNum == null || pageNum.length() == 0) {
                pageNum = "1";
            }
            page.setPd(pd);
            page.setShowCount(10);
            page.setCurrentPage(Integer.parseInt(pageNum));
            List<PageData> list = comment_TiwenService.datalistPage(page);
            for (int i = 0; i < list.size(); i++) {
                list.get(i).put("USER_ID1", USER_ID);
                if (zan_Comment_TiwenService.findById1(list.get(i)) != null) {
                    list.get(i).put("ZAN", "1");
                } else {
                    list.get(i).put("ZAN", "0");
                }
                list.get(i).put("YUE", list.get(i).getString("DATE"));
                List<PageData> list1 = comment_TiWenImgService.findList(list.get(i));
                list.get(i).put("CIMG", list1);
                list.get(i).put("STATUS", "1");
                List<PageData> list2 = comment_TiwenService.findList(list.get(i));
                for (int f = 0; f < list2.size(); f++) {
                    if (list2.get(f).getString("PNAME") == null) {
                        list2.get(f).put("PNAME", "");
                    }
                }
                list.get(i).put("COMMENT", list2);
                list.get(i).put("VIEWS", zan_Comment_TiwenService.findCount(list.get(i)).get("count1").toString());
            }
            Map<String, Object> map = new HashedMap();
            if (page.getCurrentPage() == Integer.parseInt(pageNum)) {
                map.put("object", list);
            } else {
                List<PageData> list4 = new ArrayList();
                message = "已加载全部数据!";
                map.put("object", list4);
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("data", map);
            pd.put("message", message);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // ==========================查询问答详情================================
    @RequestMapping(value = "findWenDaTi", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findWenDaTi(String USER_ID, String TIWEN_ID, String pageNum) {
        logBefore(logger, "查询问答详情");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            PageData pd_t = tiWenService.findById(pd);
            if (zan_tiwenService.findById(pd) != null) {
                pd_t.put("ZAN", "1");
            } else {
                pd_t.put("ZAN", "0");
            }
            if (collection_tiwenService.findCollection(pd) != null) {
                pd_t.put("COLLECTION", "1");
            } else {
                pd_t.put("COLLECTION", "0");
            }
            List<PageData> list = tiWen_ImgService.findList(pd);
            new Thread(new History(pd_t.getString("MESSAGE"), TIWEN_ID, "1", "问答", USER_ID,
                    "http://m.nongjike.cn/NJK/static/jsp/tiwen.html?TIWEN_ID=" + TIWEN_ID, historyService)).start();
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("tiwen", pd_t);
            pd.put("list", list);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // ==========================查询我的提问================================
    @RequestMapping(value = "findUserWenDa", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findUserWenDa(String USER_ID, String pageNum) {
        logBefore(logger, "查询我的提问");
        PageData pd = new PageData();
        Page page = new Page();
        pd = this.getPageData();
        String message = "正确返回数据!";
        try {
            if (pageNum == null || pageNum.length() == 0) {
                pageNum = "1";
            }
            page.setPd(pd);
            page.setShowCount(5);
            page.setCurrentPage(Integer.parseInt(pageNum));
            List<PageData> list = tiWenService.userlistPage(page);
            for (int i = 0; i < list.size(); i++) {
                List<PageData> list3 = tiWen_ImgService.findTop(list.get(i));
                list.get(i).put("T_IMG", list3);
                list.get(i).put("url",
                        "http://m.nongjike.cn/NJK/static/jsp/tiwen.html?TIWEN_ID=" + list.get(i).getString("TIWEN_ID"));
            }
            Map<String, Object> map = new HashedMap();
            if (page.getCurrentPage() == Integer.parseInt(pageNum)) {
                map.put("object", list);
            } else {
                List<PageData> list4 = new ArrayList();
                message = "已加载全部数据!";
                map.put("object", list4);
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("message", message);
            pd.put("data", map);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // ==========================查询提问======================================
    @RequestMapping(value = "findWenDa", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findWenDa(String USER_ID, String pageNum) {
        logBefore(logger, "查询提问");
        PageData pd = new PageData();
        pd = this.getPageData();
        Page page = new Page();
        String message = "正确返回数据!";
        try {
            PageData pd_special = new PageData();
            pd_special.put("TSTATUS", "1");
            pd_special = post_SpecialService.findById2(pd_special);
            pd.put("TSTATUS", "1");
            List<PageData> list1 = postService.findList(pd);
            for (int i = 0; i < list1.size(); i++) {
                list1.get(i).put("VIEWS", list1.get(i).get("VIEWS").toString());
            }
            PageData pd_m = postService.findPostMstatus(pd);
            if (pageNum == null || pageNum.length() == 0) {
                pageNum = "1";
            }
            page.setPd(pd);
            page.setShowCount(5);
            page.setCurrentPage(Integer.parseInt(pageNum));
            List<PageData> list = tiWenService.data2listPage(page);
            for (int i = 0; i < list.size(); i++) {
                list.get(i).put("USER_ID1", USER_ID);
                PageData pd1 = zan_tiwenService.findById1(list.get(i));
                if (pd1 != null) {
                    list.get(i).put("ZAN", "1");
                } else {
                    list.get(i).put("ZAN", "0");
                }
                PageData pd2 = collection_tiwenService.findCollection1(list.get(i));
                if (pd2 != null) {
                    list.get(i).put("COLLECTION", "1");
                } else {
                    list.get(i).put("COLLECTION", "0");
                }
                List<PageData> list3 = tiWen_ImgService.findTop(list.get(i));
                list.get(i).put("T_IMG", list3);
                list.get(i).put("STATUS", "0");
                List<PageData> Clist = comment_TiwenService.findListTo(list.get(i));
                list.get(i).put("COMMENT", Clist);
                list.get(i).put("url",
                        "http://www.meitiannongzi.com/nongjike/static/jsp/share_tiwen.html?TIWEN_ID=" + list.get(i).getString("TIWEN_ID"));
            }
            Map<String, Object> map = new HashedMap();
            if (page.getCurrentPage() == Integer.parseInt(pageNum)) {
                map.put("object", list);
                if (list.size() < 5) {
                    message = "已加载全部数据!";
                }
            } else {
                List<PageData> list4 = new ArrayList();
                message = "已加载全部数据!";
                map.put("object", list4);
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("message", message);
            pd.put("data", map);
            pd.put("special", pd_special);
            pd.put("top", list1);
            pd.put("meiri", pd_m);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    @RequestMapping(value = "findShouYeGuangGao", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findShouYeGuangGao() {
        logBefore(logger, "查询首页广告");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            PageData pd_g = bannerService.findName(pd);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("data", pd_g);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    @RequestMapping(value = "findTiWens", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findTiWens(String USER_ID, String pageNum) {
        logBefore(logger, "查询提问");
        PageData pd = new PageData();
        pd = this.getPageData();
        Page page = new Page();
        String message = "正确返回数据!";
        try {
            pd.put("TSTATUS", "1");
            List<PageData> list1 = postService.findList(pd);
            for (int i = 0; i < list1.size(); i++) {
                list1.get(i).put("VIEWS", list1.get(i).get("VIEWS").toString());
            }
            PageData pd_m = postService.findPostMstatus(pd);
            if (pageNum == null || pageNum.length() == 0) {
                pageNum = "1";
            }
            page.setPd(pd);
            page.setShowCount(5);
            page.setCurrentPage(Integer.parseInt(pageNum));
            List<PageData> list = tiWenService.data2listPage(page);
            for (int i = 0; i < list.size(); i++) {
                list.get(i).put("USER_ID1", USER_ID);
                PageData pd1 = zan_tiwenService.findById1(list.get(i));
                if (pd1 != null) {
                    list.get(i).put("ZAN", "1");
                } else {
                    list.get(i).put("ZAN", "0");
                }
                PageData pd2 = collection_tiwenService.findCollection1(list.get(i));
                if (pd2 != null) {
                    list.get(i).put("COLLECTION", "1");
                } else {
                    list.get(i).put("COLLECTION", "0");
                }
                List<PageData> list3 = tiWen_ImgService.findTop(list.get(i));
                list.get(i).put("T_IMG", list3);
                list.get(i).put("STATUS", "0");
                List<PageData> Clist = comment_TiwenService.findListTo(list.get(i));
                list.get(i).put("COMMENT", Clist);
                list.get(i).put("url",
                        "http://www.meitiannongzi.com/nongjike/static/jsp/share_tiwen.html?TIWEN_ID=" + list.get(i).getString("TIWEN_ID"));
            }
            Map<String, Object> map = new HashedMap();
            if (page.getCurrentPage() == Integer.parseInt(pageNum)) {
                map.put("object", list);
                if (list.size() < 5) {
                    message = "已加载全部数据!";
                }
            } else {
                List<PageData> list4 = new ArrayList();
                message = "已加载全部数据!";
                map.put("object", list4);
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("message", message);
            pd.put("data", map);
            pd.put("top", list1);
            pd.put("meiri", pd_m);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // ==========================查询专题列表===================================
    @RequestMapping(value = "findSepcialList", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findSepcialList(String pageNum, String USER_ID) {
        logBefore(logger, "查询专题列表");
        PageData pd = new PageData();
        Page page = new Page();
        pd = this.getPageData();
        String message = "正确返回数据!";
        try {
            if (pageNum == null || pageNum.length() == 0) {
                pageNum = "1";
            }
            page.setPd(pd);
            page.setShowCount(5);
            page.setCurrentPage(Integer.parseInt(pageNum));
            List<PageData> list = post_SpecialService.data4listPage(page);
            for (int i = 0, len = list.size(); i < len; i++) {
                list.get(i).put("USER_ID1", USER_ID);
                if (collection_specialService.findById1(list.get(i)) != null) {
                    list.get(i).put("COLLECTION", "1");
                } else {
                    list.get(i).put("COLLECTION", "0");
                }
            }
            Map<String, Object> map = new HashedMap();
            pd.clear();
            pd.put("code", "1");
            if (page.getCurrentPage() == Integer.parseInt(pageNum)) {
                pd.put("data", list);
                if (list.size() < 5) {
                    message = "已加载全部数据!";
                }
            } else {
                List<PageData> list4 = new ArrayList();
                message = "已加载全部数据!";
                pd.put("data", list4);
            }
            pd.put("message", message);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // ==========================查询专题详情=======================================
    @RequestMapping(value = "findSpecial", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findSpecial(String FID, String USER_ID) {
        logBefore(logger, "查询专题");
        PageData pd = new PageData();
        pd = this.getPageData();
        Page page = new Page();
        try {
            PageData pd1 = post_SpecialService.findById1(pd);
            if (zan_SpecialService.findById(pd) != null) {
                pd1.put("ZAN", "1");
            } else {
                pd1.put("ZAN", "0");
            }
            if (collection_specialService.findById(pd) != null) {
                pd1.put("COLLECTION", "1");
            } else {
                pd1.put("COLLECTION", "0");
            }
            List<PageData> top = postService.ZhuanTi(pd);// 查询专题最上方4个帖子
            List<PageData> list = post_Special_TypeService.findList(pd);
            for (int i = 0; i < list.size(); i++) {
                List<PageData> list2 = postService.findListFid(list.get(i));
                for (int j = 0; j < list2.size(); j++) {
                    List<PageData> list3 = post_ImgService.findList(list2.get(j));
                    list2.get(j).put("POST_IMG", list3);
                    if (list2.get(j).getString("IMG") == null) {
                        list2.get(j).put("IMG", "http://obmmqs3o7.bkt.clouddn.com/dftheader.jpg");
                    }
                    String str = DateUtil.delHTMLTag(list2.get(j).getString("MESSAGE"));
                    String DETAILS1 = str.replace("\r", "");
                    String DETAILS2 = DETAILS1.replace("\n", "");
                    String DETAILS3 = DETAILS2.replace("&nbsp;", "");
                    int qian = 40;
                    if (DETAILS3.length() < 40) {
                        list2.get(j).put("MESSAGE", DETAILS3);
                    } else {
                        str = DETAILS3.substring(0, qian);
                        list2.get(j).put("MESSAGE", str + "...");
                    }
                    list2.get(j).put("YUE", list.get(i).getString("TYPE_NAME"));
                }
                list.get(i).put("POST", list2);

            }
            pd.put("STATUS", "0");
            page.setPd(pd);
            page.setShowCount(5);
            page.setCurrentPage(1);
            List<PageData> list3 = comment_specialService.list(page);
            for (int i = 0; i < list3.size(); i++) {
                if (list3.get(i).getString("PNAME") == null) {
                    list3.get(i).put("PNAME", "");
                }
                list3.get(i).put("USER_ID1", USER_ID);
                if (zanCommentSpecialService.findById(list3.get(i)) != null) {
                    list3.get(i).put("ZAN", "1");
                } else {
                    list3.get(i).put("ZAN", "0");
                }
                list3.get(i).put("YUE", list3.get(i).getString("DATE"));
                List<PageData> list1 = comment_SpecialImgService.findList(list3.get(i));
                list3.get(i).put("CIMG", list1);
                list3.get(i).put("STATUS", "1");
                List<PageData> list2 = comment_specialService.findList(list3.get(i));
                for (int f = 0; f < list2.size(); f++) {
                    if (list2.get(f).getString("PNAME") == null) {
                        list2.get(f).put("PNAME", "");
                    }
                }
                list3.get(i).put("COMMENT", list2);
                list3.get(i).put("VIEWS", zanCommentSpecialService.findCount(list3.get(i)).get("count1").toString());
            }
            pd1.put("SHUZI", Integer.valueOf(pd1.getString("SHUZI")) + 1);
            post_SpecialService.editShuZi(pd1);
            new Thread(new History(pd1.getString("SPECIAL"), FID, "2", "专题", USER_ID, "2", historyService)).start();
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("TOP", top);
            pd.put("POST", list);
            pd.put("COMM", list3);
            pd.put("SPECIAL", pd1);
            pd.put("URL", "http://m.nongjike.cn/NJK/static/jsp/special.html?FID=" + FID);
            pd.put("SPECIAL_NAME", pd1.getString("SPECIAL"));
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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


    // ==========================查询专题详情=======================================
    @RequestMapping(value = "findSpecial2", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findSpecial2(String FID, String USER_ID) {
        logBefore(logger, "查询专题");
        PageData pd = new PageData();
        pd = this.getPageData();
        Page page = new Page();
        try {
            PageData pd1 = post_SpecialService.findById1(pd);
            if (zan_SpecialService.findById(pd) != null) {
                pd1.put("ZAN", "1");
            } else {
                pd1.put("ZAN", "0");
            }
            if (collection_specialService.findById(pd) != null) {
                pd1.put("COLLECTION", "1");
            } else {
                pd1.put("COLLECTION", "0");
            }
            List<PageData> top = postService.ZhuanTi(pd);// 查询专题最上方4个帖子
            List<PageData> list = post_Special_TypeService.findList(pd);
            for (int i = 0; i < list.size(); i++) {
                List<PageData> list2 = new ArrayList();
                if (list.size() > 1) {
                    list2 = postService.findListFid(list.get(i));
                } else {
                    list2 = postService.findListFid2(list.get(i));
                }
                for (int j = 0; j < list2.size(); j++) {
                    List<PageData> list3 = post_ImgService.findList(list2.get(j));
                    list2.get(j).put("POST_IMG", list3);
                    if (list2.get(j).getString("IMG") == null) {
                        list2.get(j).put("IMG", "http://obmmqs3o7.bkt.clouddn.com/dftheader.jpg");
                    }
                    String str = DateUtil.delHTMLTag(list2.get(j).getString("MESSAGE"));
                    String DETAILS1 = str.replace("\r", "");
                    String DETAILS2 = DETAILS1.replace("\n", "");
                    String DETAILS3 = DETAILS2.replace("&nbsp;", "");
                    int qian = 40;
                    if (DETAILS3.length() < 40) {
                        list2.get(j).put("MESSAGE", DETAILS3);
                    } else {
                        str = DETAILS3.substring(0, qian);
                        list2.get(j).put("MESSAGE", str + "...");
                    }
                    list2.get(j).put("YUE", list.get(i).getString("TYPE_NAME"));
                }
                list.get(i).put("POST", list2);

            }
            pd.put("STATUS", "0");
            page.setPd(pd);
            page.setShowCount(500);
            page.setCurrentPage(1);
            List<PageData> list3 = comment_specialService.list(page);
            for (int i = 0; i < list3.size(); i++) {
                if (list3.get(i).getString("PNAME") == null) {
                    list3.get(i).put("PNAME", "");
                }
                list3.get(i).put("USER_ID1", USER_ID);
                if (zanCommentSpecialService.findById(list3.get(i)) != null) {
                    list3.get(i).put("ZAN", "1");
                } else {
                    list3.get(i).put("ZAN", "0");
                }
                list3.get(i).put("YUE", list3.get(i).getString("DATE"));
                List<PageData> list1 = comment_SpecialImgService.findList(list3.get(i));
                list3.get(i).put("CIMG", list1);
                list3.get(i).put("STATUS", "1");
                List<PageData> list2 = comment_specialService.findList(list3.get(i));
                for (int f = 0; f < list2.size(); f++) {
                    if (list2.get(f).getString("PNAME") == null) {
                        list2.get(f).put("PNAME", "");
                    }
                }
                list3.get(i).put("COMMENT", list2);
                list3.get(i).put("VIEWS", zanCommentSpecialService.findCount(list3.get(i)).get("count1").toString());
            }
            pd1.put("SHUZI", Integer.valueOf(pd1.getString("SHUZI")) + 1);
            post_SpecialService.editShuZi(pd1);
            new Thread(new History(pd1.getString("SPECIAL"), FID, "2", "专题", USER_ID, "2", historyService)).start();
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("TOP", top);
            pd.put("POST", list);
            pd.put("COMM", list3);
            pd.put("SPECIAL", pd1);
            pd.put("URL", "http://m.nongjike.cn/NJK/static/jsp/special.html?FID=" + FID);
            pd.put("SPECIAL_NAME", pd1.getString("SPECIAL"));
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // =========================修改推送===================================
    @RequestMapping(value = "editTuiSong", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String editTuiSong(String USER_ID, String STATUS) {
        logBefore(logger, "修改推送");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            appuserService.editStatus(pd);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // =========================查询推送======================================
    @RequestMapping(value = "findTuiSong", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findTuiSong(String USER_ID) {
        logBefore(logger, "查询推送");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            PageData pd1 = appuserService.findById111(pd);
            pd.clear();
            pd.put("data", pd1);
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
        } catch (Exception e) {
            e.printStackTrace();
            logBefore(logger, e.toString());
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // =========================查询肥料详情==================================
    @RequestMapping(value = "findFeiLiaoId", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findFeiLiaoId(String FEILIAO_ID) {
        logBefore(logger, "查询肥料详情");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            PageData pd1 = feiLiaoService.findById(pd);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("FEILIAO", pd1);
            pd.put("url", "http://m.nongjike.cn/NJK/static/jsp/feiliao.html?FEILIAO_ID=" + FEILIAO_ID);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // =========================查询肥料===========================================
    @RequestMapping(value = "findFeiLiao", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findFeiLiao(String KEYWORD1, String KEYWORD2, String KEYWORD3, String pageNum) {
        logBefore(logger, "查询肥料");
        PageData pd = new PageData();
        pd = this.getPageData();
        String message = "正确返回数据!";
        try {
            if (pageNum == null || pageNum.length() == 0) {
                pageNum = "1";
            }
            Integer SHU1 = Integer.valueOf(pageNum) * 10;
            pd.put("SHU1", SHU1 - 10);
            pd.put("SHUZI", StringUtil.get_StringNum(KEYWORD1));
            List<PageData> list = feiLiaoService.list(pd);
            Map<String, Object> map = new HashedMap();
            if (list != null && list.size() != 0) {
                map.put("object", list);
            } else {
                List<PageData> list4 = new ArrayList();
                message = "已加载全部数据!";
                map.put("object", list4);
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("data", map);
            pd.put("message", message);
            pd.put("url", "http://m.nongjike.cn/NJK/static/jsp/feiliaoList.html");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // =========================查询农药详情=======================================
    @RequestMapping(value = "findNongYaoId", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findNongYaoId(String NONGYAO_ID) {
        logBefore(logger, "查询农药详情");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            PageData pd1 = nongYaoService.findById(pd);
            if (pd1.getString("USAGE1") != "") {
                String usage1 = pd1.getString("USAGE1").replace("width=567", "width=330");
                // usage1=usage1.replace("82%", "");
                usage1 = usage1.replace(
                        "BORDER-BOTTOM: medium none; BORDER-LEFT: medium none; WIDTH: 15cm; BORDER-COLLAPSE: collapse; BORDER-TOP: medium none; BORDER-RIGHT: medium none; mso-border-alt: solid windowtext .5pt; mso-padding-alt: 0cm 5.4pt 0cm 5.4pt; mso-yfti-tbllook: 480; mso-border-insideh: .5pt solid windowtext; mso-border-insidev: .5pt solid windowtext",
                        "BORDER-BOTTOM: medium none; BORDER-LEFT: medium none; BORDER-COLLAPSE: collapse; BORDER-TOP: medium none; BORDER-RIGHT: medium none; mso-border-alt: solid windowtext .5pt; mso-padding-alt: 0cm 5.4pt 0cm 5.4pt; mso-border-insideh: .5pt solid windowtext; mso-border-insidev: .5pt solid windowtext; mso-table-layout-alt: fixed");
                usage1 = usage1.replace(
                        "BORDER-RIGHT: medium none; BORDER-TOP: medium none; BORDER-LEFT: medium none; WIDTH: 15cm; BORDER-BOTTOM: medium none; BORDER-COLLAPSE: collapse; mso-border-alt: solid windowtext .5pt; mso-padding-alt: 0cm 5.4pt 0cm 5.4pt; mso-yfti-tbllook: 480; mso-border-insideh: .5pt solid windowtext; mso-border-insidev: .5pt solid windowtext",
                        "BORDER-BOTTOM: medium none; BORDER-LEFT: medium none; BORDER-COLLAPSE: collapse; BORDER-TOP: medium none; BORDER-RIGHT: medium none; mso-border-alt: solid windowtext .5pt; mso-padding-alt: 0cm 5.4pt 0cm 5.4pt; mso-border-insideh: .5pt solid windowtext; mso-border-insidev: .5pt solid windowtext; mso-table-layout-alt: fixed");
                usage1 = usage1.replace(
                        "BORDER-BOTTOM: medium none; BORDER-LEFT: medium none; WIDTH: 15cm; BORDER-COLLAPSE: collapse; BORDER-TOP: medium none; BORDER-RIGHT: medium none; mso-border-alt: solid windowtext .5pt; mso-yfti-tbllook: 480; mso-padding-alt: 0cm 5.4pt 0cm 5.4pt; mso-border-insideh: .5pt solid windowtext; mso-border-insidev: .5pt solid windowtext",
                        "BORDER-BOTTOM: medium none; BORDER-LEFT: medium none; BORDER-COLLAPSE: collapse; BORDER-TOP: medium none; BORDER-RIGHT: medium none; mso-border-alt: solid windowtext .5pt; mso-padding-alt: 0cm 5.4pt 0cm 5.4pt; mso-border-insideh: .5pt solid windowtext; mso-border-insidev: .5pt solid windowtext; mso-table-layout-alt: fixed");
                pd1.put("USAGE1", usage1.subSequence(77, usage1.length()));
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("NONGYAO", pd1);
            pd.put("url", "http://m.nongjike.cn/NJK/static/jsp/nongyao.html?NONGYAO_ID=" + NONGYAO_ID);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // =========================查询农药===========================================
    @RequestMapping(value = "findNongYao", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findNongYao(String KEYWORD1, String KEYWORD2, String KEYWORD3, String pageNum) {
        logBefore(logger, "查询农药");
        PageData pd = new PageData();
        pd = this.getPageData();
        String message = "正确返回数据!";
        try {
            if (pageNum == null || pageNum.length() == 0) {
                pageNum = "1";
            }
            Integer SHU1 = Integer.valueOf(pageNum) * 10;
            pd.put("SHU1", SHU1 - 10);
            List<PageData> list = nongYaoService.list(pd);
            Map<String, Object> map = new PageData();
            if (list != null && list.size() != 0) {
                map.put("object", list);
            } else {
                List<PageData> list4 = new ArrayList();
                message = "已加载全部数据!";
                map.put("object", list4);
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("data", map);
            pd.put("message", message);
            pd.put("url", "http://m.nongjike.cn/NJK/static/jsp/nongyaoList.html");
        } catch (Exception e) {
            pd.clear();
            e.printStackTrace();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // =========================申请直播室============================================
    @RequestMapping(value = "saveActivityRomm", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String saveActivityRomm(String USER_ID, String CROP, String ACTIVITY_ID, String ADDRESS, String IMG) {
        logBefore(logger, "申请直播室");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            String ACTIVITY_ROOM_ID = this.get32UUID();
            String ffile = this.get32UUID() + ".jpg";
            String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + ffile; // 文件上传路径
            boolean flag = ImageAnd64Binary.generateImage(IMG, filePath);
            String TOUR_IMG1 = Const.SERVERPATH + Const.FILEPATHIMG + ffile;
            pd.put("IMG", TOUR_IMG1);
            pd.put("ACTIVITY_ROOM_ID", ACTIVITY_ROOM_ID);
            pd.put("BEIYONG1", "");
            pd.put("BEIYONG2", "");
            pd.put("BEIYONG3", "");
            pd.put("BEIYONG4", "");
            pd.put("BEIYONG5", "");
            pd.put("DATE", DateUtil.getTime());
            pd.put("VIEWS", "0");
            pd.put("HUIFU", "0");
            activity_RoomService.save(pd);
            pd.put("ACTIVITY_POST_ID", this.get32UUID());
            pd.put("SUBJECT", "");
            pd.put("MESSAGE", "");
            pd.put("DATE", DateUtil.getTime());
            pd.put("PROMPT", "");
            pd.put("STATUS", "0");
            pd.put("YUE", DateUtil.getDay3());
            pd.put("VIEWS", "0");
            pd.put("HUIFU", "0");
            pd.put("ACTIVITY_ROOM_ID", ACTIVITY_ROOM_ID);
            activity_PostService.save(pd);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "申请成功");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // =========================查询直播室详情=======================================
    @RequestMapping(value = "findActivityRoomPost", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findActivityRoomPost(String ACTIVITY_ROOM_ID, String pageNum) {
        logBefore(logger, "查询直播室详情");
        PageData pd = new PageData();
        Page page = new Page();
        String message = "正确返回数据!";
        try {
            if (pageNum == null || pageNum.length() == 0) {
                pageNum = "1";
            }
            page.setPd(pd);
            page.setShowCount(5);
            page.setCurrentPage(Integer.parseInt(pageNum));
            List<PageData> list = activity_Post1Service.list(page);
            Map<String, Object> map = new HashedMap();
            if (page.getCurrentPage() == Integer.parseInt(pageNum)) {
                map.put("object", list);
            } else {
                List<PageData> list4 = new ArrayList();
                message = "已加载全部数据!";
                map.put("object", list4);
            }

            pd.clear();
            pd.put("code", "1");
            pd.put("data", map);
            pd.put("message", message);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // =========================查询直播室========================================
    @RequestMapping(value = "findActivityRoom", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findActivityRoom(String ACTIVITY_ID, String pageNum, String USER_ID) {
        logBefore(logger, "查询直播室");
        PageData pd = new PageData();
        Page page = new Page();
        String message = "正确返回数据!";
        pd = this.getPageData();
        try {
            if (pageNum == null || pageNum.length() == 0) {
                pageNum = "1";
            }
            page.setPd(pd);
            page.setShowCount(5);
            page.setCurrentPage(Integer.parseInt(pageNum));
            List<PageData> list = activity_RoomService.userlistPage(page);
            for (int i = 0, len = list.size(); i < len; i++) {
                list.get(i).put("COUNT", activity_PostService.findCount(list.get(i)).get("count").toString());
                PageData pd4 = views_ActivityService.findCount(list.get(i));
                Integer fangwen = (Integer.valueOf(pd4.get("count").toString()));
                PageData pd5 = views_Activity_IpService.findCount(list.get(i));
                Integer fangwen1 = (Integer.valueOf(pd5.get("count").toString()));
                Integer fangwen2 = fangwen + fangwen1;
                list.get(i).put("FANGWEN", fangwen2.toString());
                list.get(i).put("HUIFU", comment_Activity_UserService.findCount(list.get(i)).get("count").toString());
                list.get(i).put("VIEWS",
                        collection_Activity_UserService.findCount(list.get(i)).get("count").toString());
            }
            Map<String, Object> map = new HashedMap();
            if (page.getCurrentPage() == Integer.parseInt(pageNum)) {
                map.put("object", list);
            } else {
                List<PageData> list4 = new ArrayList();
                message = "已加载全部数据!";
                map.put("object", list4);
            }
            String STATUS = "0";
            String ACTIVITY_ROOM_ID = "";
            PageData pd_p = activity_RoomService.findById1(pd);
            if (pd_p != null) {
                STATUS = "1";
                ACTIVITY_ROOM_ID = pd_p.getString("ACTIVITY_ROOM_ID");
            }
            String STATUS1 = "0";
            if (z_QuanXianService.findById(pd) != null) {
                STATUS1 = "1";
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("data", map);
            pd.put("message", message);
            pd.put("STATUS", STATUS);
            pd.put("ACTIVITY_ROOM_ID", ACTIVITY_ROOM_ID);
            pd.put("ZQUANXIAN", STATUS1);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // =========================查询我的提问=================================
    @RequestMapping(value = "findTiWenUser", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findTiWenUser(String USER_ID, String pageNum) {
        logBefore(logger, "查询我的提问");
        PageData pd = new PageData();
        pd = this.getPageData();
        Page page = new Page();
        String message = "正确返回数据!";
        try {
            if (pageNum == null || pageNum.length() == 0) {
                pageNum = "1";
            }
            page.setPd(pd);
            page.setShowCount(5);
            page.setCurrentPage(Integer.parseInt(pageNum));
            List<PageData> list = tiWenService.userlistPage(page);
            for (int i = 0; i < list.size(); i++) {
                list.get(i).put("url",
                        "http://m.nongjike.cn/NJK/static/jsp/tiwen.html?TIWEN_ID=" + list.get(i).getString("TIWEN_ID"));
                list.get(i).put("ZAN", "");
                list.get(i).put("COLLECTION", "");
                List<PageData> list3 = tiWen_ImgService.findTop(list.get(i));
                list.get(i).put("T_IMG", list3);
                String str = DateUtil.delHTMLTag(list.get(i).getString("MESSAGE"));
                String DETAILS1 = str.replace("\r", "");
                String DETAILS2 = DETAILS1.replace("\n", "");
                String DETAILS3 = DETAILS2.replace("&nbsp;", "");
                list.get(i).put("MESSAGE", DETAILS3);
            }
            Map<String, Object> map = new HashedMap();
            if (page.getCurrentPage() == Integer.parseInt(pageNum)) {
                map.put("object", list);
            } else {
                List<PageData> list4 = new ArrayList();
                message = "已加载全部数据!";
                map.put("object", list4);
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("data", map);
            pd.put("message", message);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // =========================查询专属客服==============================
    @RequestMapping(value = "findExclu", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findExclu(String USER_ID) {
        logBefore(logger, "查询专属客服");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            PageData pd1 = appuserService.findById(pd);
            PageData pd2 = excluService.findById(pd1);
            pd.clear();
            pd.put("EXCLU", pd2);
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // =========================查询提问详情=====================================
    @RequestMapping(value = "findTiwenId", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findTiwenId(String TIWEN_ID, String pageNum, String USER_ID) {
        logBefore(logger, "查询提问详情");
        PageData pd = new PageData();
        Page page = new Page();
        String message = "正确返回数据!";
        try {
            pd = this.getPageData();
            PageData pd1 = tiWenService.findById(pd);
            if (zan_tiwenService.findById(pd) != null) {
                pd1.put("ZAN", "1");
            } else {
                pd1.put("ZAN", "0");
            }
            if (collection_tiwenService.findCollection(pd) != null) {
                pd1.put("COLLECTION", "1");
            } else {
                pd1.put("COLLECTION", "0");
            }
            List<PageData> list5 = tiWen_ImgService.findList(pd);
            if (list5 != null) {
                pd1.put("P_IMG", list5);
            } else {
                List<PageData> list55 = new ArrayList();
                pd1.put("P_IMG", list55);
            }
            if (pageNum == null || pageNum.length() == 0) {
                pageNum = "1";
            }
            page.setPd(pd);
            page.setShowCount(5);
            pd.put("STATUS", "0");
            page.setPd(pd);
            page.setCurrentPage(Integer.parseInt(pageNum));
            List<PageData> list = comment_TiwenService.datalistPage(page);
            for (int i = 0; i < list.size(); i++) {
                list.get(i).put("USER_ID1", USER_ID);
                if (zan_Comment_TiwenService.findById1(list.get(i)) != null) {
                    list.get(i).put("ZAN", "1");
                } else {
                    list.get(i).put("ZAN", "0");
                }
                list.get(i).put("YUE", list.get(i).getString("DATE"));
                List<PageData> list1 = comment_TiWenImgService.findList(list.get(i));
                list.get(i).put("CIMG", list1);
                list.get(i).put("STATUS", "1");
                List<PageData> list2 = comment_TiwenService.findList(list.get(i));
                for (int f = 0; f < list2.size(); f++) {
                    if (list2.get(f).getString("PNAME") == null) {
                        list2.get(f).put("PNAME", "");
                    }
                }
                list.get(i).put("COMMENT", list2);
            }
            Map<String, Object> map = new HashedMap();
            if (page.getCurrentPage() == Integer.parseInt(pageNum)) {
                map.put("object", list);
            } else {
                List<PageData> list4 = new ArrayList();
                message = "已加载全部数据!";
                map.put("object", list4);
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("data", map);
            pd.put("TIWEN", pd1);
            pd.put("message", message);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // =========================点赞提问评论===================================
    @RequestMapping(value = "ZanCommentTiWen", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String ZanCommentTiWen(String COMMENT_TIWEN_ID, String USER_ID, String STATUS) {
        logBefore(logger, "点赞提问和取消点赞");
        PageData pd = new PageData();
        pd = this.getPageData();
        String VIEWS = "";
        String STATUS1 = "0";
        try {
            if (STATUS.equals("1")) {
                if (zan_Comment_TiwenService.findById(pd) == null) {
                    zan_Comment_TiwenService.save(pd);
                }
                STATUS1 = "1";
            } else {
                zan_Comment_TiwenService.delete(pd);
                STATUS1 = "0";
            }
            VIEWS = zan_Comment_TiwenService.findCount(pd).get("count1").toString();
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("VIEWS", VIEWS);
            pd.put("STATUS", STATUS1);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("message", "程序出错,请联系管理员!");
            pd.put("code", "2");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // =========================关注提问和取消关注========================================
    @RequestMapping(value = "collectionTiWen", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String collectionTiWen(String STATUS, String USER_ID, String TIWEN_ID) {
        logBefore(logger, "关注提问取消关注");
        PageData pd = new PageData();
        pd = this.getPageData();
        String STATUS1 = "0";
        try {
            if (STATUS.equals("1")) {
                if (collection_tiwenService.findCollection(pd) == null) {
                    pd.put("COLLECTION_TIWEN_ID", this.get32UUID());
                    pd.put("DATE", DateUtil.getTime());
                    pd.put("STATUS", "0");
                    collection_tiwenService.save(pd);
                }
                STATUS1 = "1";
            } else {
                collection_tiwenService.delete(pd);
            }
            pd.clear();
            pd.put("message", "正确返回数据!");
            pd.put("code", "1");
            pd.put("STATUS", STATUS1);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(pd);
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

    // =========================点赞提问和取消提问=========================================
    @RequestMapping(value = "ZanTiWen", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String ZanTiWen(String STATUS, String TIWEN_ID, String USER_ID) {
        logBefore(logger, "点赞提问和取消点赞");
        PageData pd = new PageData();
        pd = this.getPageData();
        String VIEWS = "";
        String STATUS1 = "0";
        try {
            if (STATUS.equals("1")) {
                if (zan_tiwenService.findById(pd) == null) {
                    zan_tiwenService.save(pd);
                }
                STATUS1 = "1";
            } else {
                zan_tiwenService.delete(pd);
                STATUS1 = "0";
            }
            VIEWS = zan_tiwenService.findCount(pd).get("count1").toString();
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("VIEWS", VIEWS);
            pd.put("STATUS", STATUS1);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("message", "程序出错,请联系管理员!");
            pd.put("code", "2");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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


    // =========================回复专题回复=======================================
    @RequestMapping(value = "CommentTiWenHui2", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String CommentTiWenHui2(String USER_ID, String TIWEN_ID, String MESSAGE, String PUSER_ID, String PID) {
        logBefore(logger, "回复评论");
        PageData pd = new PageData();
        pd = this.getPageData();
        String inform = "";
        try {
            pd.put("COMMENT_TIWEN_ID", this.get32UUID());
            pd.put("NAME", "");
            pd.put("PNAME", "");
            pd.put("DATE", DateUtil.getTime());
            pd.put("STATUS", "1");
            pd.put("VIEWS", "0");
            pd.put("YUE", DateUtil.getDay());
            pd.put("PID", PID);
            comment_TiwenService.save(pd);

            //*****************增加用户积分*****************
            if (MESSAGE.length() >= 15) {
                String today = DateUtil.getDay();
                PageData comDto = new PageData();
                comDto.put("community_type", "1");
                comDto.put("create_date", today);
                comDto.put("USER_ID", USER_ID);
                pd.put("JIFEN", 1);
                appuserService.editJifen(pd);
                appuserService.addZJIFEN(pd);
                comDto.put("NUM", "1");
                comDto.put("community_id", TIWEN_ID);
                comDto.put("MIAOSHU", "回复评论");
                comDto.put("ORDER_NUM", "");
                countCommunityService.save(comDto);
                inform = "回复完成，获取有效积分+1";
            }
            //*****************end******************

            new Thread(new CommetnTiwenHuiFu2(USER_ID, TIWEN_ID, MESSAGE, PUSER_ID, tiWenService, appuserService,
                    newsService, "http://www.meitiannongzi.com/nongjike/static/jsp/share_tiwen.html?TIWEN_ID=" + TIWEN_ID)).start();

            pd.clear();
            pd.put("code", "1");
            pd.put("inform", inform);
            pd.put("message", "正确返回数据!");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("message", "程序出错,请联系管理员!");
            pd.put("code", "2");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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


    //**********************************app分享增加用户积分***********************************

    /**
     * @param FENXIANG_ID
     * @return
     */
    @RequestMapping(value = "modifUserJifen", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String modifUserJifen(String FENXIANG_ID) {
        logBefore(logger, "app分享增加用户积分");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            //*****************增加用户积分*****************
            String today = DateUtil.getDay();
            PageData comDto = new PageData();
            comDto.put("community_type", "5");
            comDto.put("create_date", today);
            comDto.put("USER_ID", FENXIANG_ID);
            pd.put("USER_ID", FENXIANG_ID);
            PageData flag = countCommunityService.findById(comDto);
            if (null == flag) {
                pd.put("JIFEN", 2);
                appuserService.editJifen(pd);
                appuserService.addZJIFEN(pd);
                comDto.put("community_id", "");
                comDto.put("NUM", "2");
                comDto.put("MIAOSHU", "分享农极客链接");
                comDto.put("ORDER_NUM", "");
                countCommunityService.save(comDto);
            }
            //*****************end******************/
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("message", "程序出错,请联系管理员!");
            pd.put("code", "2");
        }


        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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


    // ==========================回复帖子评论========================================
    @RequestMapping(value = "CommentTiWenHui", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String CommentTiWenHui(String USER_ID, String TIWEN_ID, String MESSAGE, String PUSER_ID,
                                  String COMMENT_TIWEN_ID) {
        logBefore(logger, "回复评论");
        PageData pd = new PageData();
        pd = this.getPageData();
        String inform = "";
        try {
            pd.put("COMMENT_TIWEN_ID", this.get32UUID());
            pd.put("NAME", "");
            pd.put("PNAME", "");
            pd.put("DATE", DateUtil.getTime());
            pd.put("STATUS", "1");
            pd.put("VIEWS", "0");
            pd.put("YUE", DateUtil.getDay());
            pd.put("PID", COMMENT_TIWEN_ID);
            comment_TiwenService.save(pd);

            //*****************增加用户积分*****************
            if (MESSAGE.length() >= 15) {
                String today = DateUtil.getDay();
                PageData comDto = new PageData();
                comDto.put("community_type", "1");
                comDto.put("create_date", today);
                comDto.put("USER_ID", USER_ID);
                pd.put("JIFEN", 1);
                appuserService.editJifen(pd);
                appuserService.addZJIFEN(pd);
                comDto.put("community_id", TIWEN_ID);
                comDto.put("NUM", "1");
                comDto.put("MIAOSHU", "回复评论");
                comDto.put("ORDER_NUM", "");
                countCommunityService.save(comDto);
                inform = "回复完成，获取有效积分+1";
            }
            //*****************end******************/

            new Thread(new CommentTiwenHuiFu(USER_ID, TIWEN_ID, MESSAGE, COMMENT_TIWEN_ID, comment_TiwenService,
                    appuserService, newsService, "http://www.meitiannongzi.com/nongjike/static/jsp/share_tiwen.html?TIWEN_ID=" + TIWEN_ID))
                    .start();
            pd.clear();
            pd.put("code", "1");
            pd.put("inform", inform);
            pd.put("message", "正确返回数据!");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("message", "程序出错,请联系管理员!");
            pd.put("code", "2");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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


    // ==============================评论提问帖子======================================
    @RequestMapping(value = "CommentTiWen", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String CommentTiWen(String USER_ID, String TIWEN_ID, String MESSAGE, String IMG, String STATUS) {
        logBefore(logger, "评论提问");
        PageData pd = new PageData();
        pd = this.getPageData();
        String inform = "";
        try {
            String COMMENT_TIWEN_ID = this.get32UUID();
            pd.put("COMMENT_TIWEN_ID", COMMENT_TIWEN_ID);
            pd.put("NAME", "");
            pd.put("PUSER_ID", "");
            pd.put("PNAME", "");
            pd.put("DATE", DateUtil.getTime());
            pd.put("STATUS", "0");
            pd.put("VIEWS", "0");
            pd.put("YUE", DateUtil.getDay());
            pd.put("PID", "");
            comment_TiwenService.save(pd);
            // MESSAGE.replaceAll("!==!", "\n");
            if (IMG != "") {
                String TOUR_IMG[] = IMG.split(",");
                for (int i = 0; i < TOUR_IMG.length; i++) {
                    String ffile = this.get32UUID() + ".jpg";
                    String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + ffile; // 文件上传路径
                    boolean flag = ImageAnd64Binary.generateImage(TOUR_IMG[i], filePath);
                    String TOUR_IMG1 = Const.SERVERPATH + Const.FILEPATHIMG + ffile;
                    PageData pd_i = new PageData();
                    pd_i.put("COMMENT_TIWEN_ID", COMMENT_TIWEN_ID);
                    pd_i.put("IMG", TOUR_IMG1);
                    pd_i.put("DATE", DateUtil.getTime());
                    comment_TiWenImgService.save(pd_i);
                }
            }
            collection_tiwenService.editStatus1(pd); // 修改关注这问题的状态
            new Thread(new CommentTiwen(TIWEN_ID, MESSAGE, USER_ID, tiWenService, appuserService, newsService,
                    "http://www.meitiannongzi.com/nongjike/static/jsp/share_tiwen.html?TIWEN_ID=" + TIWEN_ID)).start();
            //*****************增加用户积分*****************
            if (MESSAGE.length() >= 15) {
                String today = DateUtil.getDay();
                PageData comDto = new PageData();
                comDto.put("community_type", "1");
                comDto.put("create_date", today);
                comDto.put("USER_ID", USER_ID);
                pd.put("JIFEN", 1);
                appuserService.editJifen(pd);
                appuserService.addZJIFEN(pd);
                comDto.put("NUM", "1");
                comDto.put("community_id", pd.get("TIWEN_ID").toString());
                comDto.put("MIAOSHU", "回复提问");
                comDto.put("ORDER_NUM", "");
                countCommunityService.save(comDto);
                inform = "回复完成，获取有效积分+1";
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("inform", inform);
            pd.put("message", "评论成功");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(pd);
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

    // ======================查询我关注的===================================
    @RequestMapping(value = "findCollectionTiWen", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findCollectionTiWen(String USER_ID, String pageNum) {
        logBefore(logger, "查询我关注的提问");
        PageData pd = new PageData();
        pd = this.getPageData();
        Page page = new Page();
        String message = "正确返回数据!";
        try {
            if (pageNum == null || pageNum.length() == 0) {
                pageNum = "1";
            }
            page.setPd(pd);
            page.setShowCount(5);
            page.setCurrentPage(Integer.parseInt(pageNum));
            List<PageData> list = collection_tiwenService.list(page);
            for (int i = 0; i < list.size(); i++) {
                list.get(i).put("USER_ID1", USER_ID);
                if (zan_tiwenService.findById1(list.get(i)) != null) {
                    list.get(i).put("ZAN", "1");
                } else {
                    list.get(i).put("ZAN", "0");
                }
                list.get(i).put("url",
                        "http://m.nongjike.cn/NJK/static/jsp/tiwen.html?TIWEN_ID=" + list.get(i).getString("TIWEN_ID"));
                list.get(i).put("COLLECTION", "1");
                List<PageData> list3 = tiWen_ImgService.findTop(list.get(i));
                list.get(i).put("T_IMG", list3);
                String str = DateUtil.delHTMLTag(list.get(i).getString("MESSAGE"));
                String DETAILS1 = str.replace("\r", "");
                String DETAILS2 = DETAILS1.replace("\n", "");
                String DETAILS3 = DETAILS2.replace("&nbsp;", "");
                int qian = 28;
                if (DETAILS3.length() < 56) {
                    list.get(i).put("MESSAGE", DETAILS3);
                } else {
                    str = DETAILS3.substring(0, qian);
                    list.get(i).put("MESSAGE", str + "...");
                }
                list.get(i).put("MESSAGE", "");
            }
            Map<String, Object> map = new HashedMap();
            if (page.getCurrentPage() == Integer.parseInt(pageNum)) {
                map.put("object", list);
            } else {
                List<PageData> list4 = new ArrayList();
                message = "已加载全部数据!";
                map.put("object", list4);
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("data", map);
            pd.put("message", message);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // ==========================查询提问===================================
    @RequestMapping(value = "findTiWen", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findTiWen(String pageNum, String USER_ID) {
        logBefore(logger, "查询提问");
        PageData pd = new PageData();
        pd = this.getPageData();
        Page page = new Page();
        String message = "正确返回数据!";
        try {
            if (pageNum == null || pageNum.length() == 0) {
                pageNum = "1";
            }
            page.setPd(pd);
            page.setShowCount(5);
            page.setCurrentPage(Integer.parseInt(pageNum));
            List<PageData> list = tiWenService.datalistPage(page);
            for (int i = 0; i < list.size(); i++) {
                list.get(i).put("USER_ID1", USER_ID);
                list.get(i).put("url",
                        "http://m.nongjike.cn/NJK/static/jsp/tiwen.html?TIWEN_ID=" + list.get(i).getString("TIWEN_ID"));
                if (zan_tiwenService.findById1(list.get(i)) != null) {
                    list.get(i).put("ZAN", "1");
                } else {
                    list.get(i).put("ZAN", "0");
                }
                if (collection_tiwenService.findCollection1(list.get(i)) != null) {
                    list.get(i).put("COLLECTION", "1");
                } else {
                    list.get(i).put("COLLECTION", "0");
                }
                List<PageData> list3 = tiWen_ImgService.findTop(list.get(i));
                list.get(i).put("T_IMG", list3);
                String str = DateUtil.delHTMLTag(list.get(i).getString("MESSAGE"));
                String DETAILS1 = str.replace("\r", "");
                String DETAILS2 = DETAILS1.replace("\n", "");
                String DETAILS3 = DETAILS2.replace("&nbsp;", "");
                list.get(i).put("MESSAGE", DETAILS3);
            }
            Map<String, Object> map = new HashedMap();
            if (page.getCurrentPage() == Integer.parseInt(pageNum)) {
                map.put("object", list);
            } else {
                List<PageData> list4 = new ArrayList();
                message = "已加载全部数据!";
                map.put("object", list4);
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("data", map);
            pd.put("message", message);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // ==========================发布提问=======================================

    /**
     * @param
     * @param
     * @param
     * @param
     * @return
     */
    @RequestMapping(value = "saveTiWen", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String saveTiWen(String MESSAGE, String USER_ID, String IMG) {
        logBefore(logger, "发布提问");
        PageData pd = new PageData();
        pd = this.getPageData();
        String inform = "";
        try {
            String TIWEN_ID = this.get32UUID();
            pd.put("TIWEN_ID", TIWEN_ID);
            pd.put("DATE", DateUtil.getTime());
            pd.put("VIEWS", "0");
            pd.put("HUIFU", "0");
            pd.put("YUE", "");
            pd.put("MESSAGE", MESSAGE.replace("!==!", ""));
            pd.put("STATUS", "0");
            pd.put("SHENCHA", "0");
            tiWenService.save(pd);
            String ffile1 = this.get32UUID() + ".jpg";
            String filePath2 = PathUtil.getClasspath() + Const.FILEPATHIMG + "tiwen/" + DateUtil.getDays(); // 文件上传路径
            File file = new File(filePath2, ffile1);
            if (!file.exists()) {
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
            }
            if (IMG != null && !IMG.equals("")) {
                String TOUR_IMG[] = IMG.split(",");
                for (int i = 0; i < TOUR_IMG.length; i++) {
                    String ffile = this.get32UUID() + ".jpg";
                    String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + "tiwen/" + DateUtil.getDays() + "/"
                            + ffile; // 文件上传路径
                    String TOUR_IMG1 = Const.SERVERPATH + Const.FILEPATHIMG + "tiwen/" + DateUtil.getDays() + "/"
                            + ffile;
                    boolean flag = ImageAnd64Binary.generateImage(TOUR_IMG[i], filePath);
                    new Thread(new TiWen(TOUR_IMG[i], filePath, tiWen_ImgService)).start();

                    PageData pd_i = new PageData();
                    pd_i.put("TIWEN_ID", TIWEN_ID);
                    pd_i.put("IMG", TOUR_IMG1);
                    tiWen_ImgService.save(pd_i);
                }
            }
            //*****************增加用户积分*****************
            String today = DateUtil.getDay();
            PageData comDto = new PageData();
            comDto.put("community_type", "2");
            comDto.put("create_date", today);
            comDto.put("USER_ID", USER_ID);
            pd.put("JIFEN", 1);
            appuserService.editJifen(pd);
            appuserService.addZJIFEN(pd);
            comDto.put("NUM", "1");
            comDto.put("community_id", pd.get("TIWEN_ID").toString());
            comDto.put("MIAOSHU", "发布提问");
            comDto.put("ORDER_NUM", "");
            countCommunityService.save(comDto);
            inform = "提问发布完成，获取有效积分+1";
            //*****************end*****************
            pd.clear();
            pd.put("code", "1");
            pd.put("inform", inform);
            pd.put("message", "正确返回数据!");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // ==============================收藏直播用户==========================================
    @RequestMapping(value = "CollectionRoot", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String CollectionRoot(String ACTIVITY_ID, String USER_ID, String QUSER_ID, String STATUS) {
        logBefore(logger, "收藏直播用户");
        PageData pd = new PageData();
        pd = this.getPageData();
        String STATUS1 = "0";
        try {
            if (STATUS.equals("1")) {
                if (collection_RoomService.findById(pd) == null) {
                    pd.put("DATE", DateUtil.getTime());
                    pd.put("STATUS", "0");
                    collection_RoomService.save(pd);
                }
                STATUS1 = "1";
            } else {
                collection_RoomService.delete(pd);
            }
            pd.clear();
            pd.put("STATUS", STATUS1);
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("message", "程序出错,请联系管理员!");
            pd.put("code", "2");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // ==============================点赞直播用户==========================================
    @RequestMapping(value = "CollectionActivityUser", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String CollectionActivityUser(String ACTIVITY_ID, String USER_ID, String QUSER_ID, String STATUS) {
        logBefore(logger, "点赞直播用户");
        PageData pd = new PageData();
        pd = this.getPageData();
        String STATUS1 = "0";
        try {
            if (STATUS.equals("1")) {
                if (collection_Activity_UserService.findById(pd) == null) {
                    collection_Activity_UserService.save(pd);
                }
                STATUS1 = "1";
            } else {
                collection_Activity_UserService.delete(pd);
            }
            PageData pd_a = collection_Activity_UserService.findCount(pd);
            pd.clear();
            pd.put("STATUS", STATUS1);
            pd.put("ZANSHU", pd_a.get("count").toString());
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("message", "程序出错,请联系管理员!");
            pd.put("code", "2");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // ==============================点赞直播帖子评论==========================================
    @RequestMapping(value = "ZanCommentActivityUser", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String ZanCommentActivityUser(String COMMENT_ACTIVITY_USER_ID, String USER_ID, String STATUS) {
        logBefore(logger, "点赞直播帖子评论");
        PageData pd = new PageData();
        pd = this.getPageData();
        String VIEWS = "";
        String STATUS1 = "0";
        try {
            if (STATUS.equals("1")) {
                if (zan_Comment_ActivityUserService.findById1(pd) == null) {
                    zan_Comment_ActivityUserService.save(pd);
                }
                STATUS1 = "1";
            } else {
                zan_Comment_ActivityUserService.delete(pd);
                STATUS1 = "0";
            }
            VIEWS = zan_Comment_ActivityUserService.findCount(pd).get("count1").toString();
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("VIEWS", VIEWS);
            pd.put("STATUS", STATUS1);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("message", "程序出错,请联系管理员!");
            pd.put("code", "2");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // ==============================查询直播帖子的评论=================================
    @RequestMapping(value = "findCommentActivityUser", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findCommentActivityUser(String ACTIVITY_ID, String QUSER_ID, String pageNum, String USER_ID) {
        logBefore(logger, "查询直播帖子的评论");
        PageData pd = new PageData();
        pd = this.getPageData();
        Page page = new Page();
        String message = "正确返回数据!";
        try {
            if (pageNum == null || pageNum.length() == 0) {
                pageNum = "1";
            }
            pd.put("STATUS", "0");
            page.setPd(pd);
            page.setShowCount(50);
            page.setCurrentPage(Integer.parseInt(pageNum));
            List<PageData> list = comment_Activity_UserService.list(page);
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getString("PNAME") == null) {
                    list.get(i).put("PNAME", "");
                }
                list.get(i).put("USER_ID1", USER_ID);
                if (zan_Comment_ActivityUserService.findById(list.get(i)) != null) {
                    list.get(i).put("ZAN", "1");
                } else {
                    list.get(i).put("ZAN", "0");
                }
                list.get(i).put("YUE", list.get(i).getString("DATE"));
                List<PageData> list1 = comment_Activity_UserImgService.findList(list.get(i));
                list.get(i).put("CIMG", list1);
                list.get(i).put("STATUS", "1");
                List<PageData> list2 = comment_Activity_UserService.findList(list.get(i));
                for (int f = 0; f < list2.size(); f++) {
                    if (list2.get(f).getString("PNAME") == null) {
                        list2.get(f).put("PNAME", "");
                    }
                }
                list.get(i).put("COMMENT", list2);
                list.get(i).put("VIEWS",
                        zan_Comment_ActivityUserService.findCount(list.get(i)).get("count1").toString());
            }
            Map<String, Object> map = new HashedMap();
            if (page.getCurrentPage() == Integer.parseInt(pageNum)) {
                map.put("object", list);
            } else {
                List<PageData> list4 = new ArrayList();
                message = "已加载全部数据!";
                map.put("object", list4);
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("message", message);
            pd.put("data", map);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(pd);
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

    // =========================回复专题回复=======================================
    @RequestMapping(value = "CommentActivityUserHui2", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String CommentActivityUserHui2(String QUSER_ID, String USER_ID, String ACTIVITY_ID, String MESSAGE,
                                          String PUSER_ID, String PID) {
        logBefore(logger, "回复评论");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            pd.put("COMMENT_ACTIVITY_USER_ID", this.get32UUID());
            pd.put("NAME", "");
            pd.put("PNAME", "");
            pd.put("DATE", DateUtil.getTime());
            pd.put("STATUS", "1");
            pd.put("VIEWS", "0");
            pd.put("YUE", DateUtil.getDay());
            pd.put("PID", PID);
            comment_Activity_UserService.save(pd);
            // new Thread(new CommetnTiwenHuiFu2(USER_ID, TIWEN_ID, MESSAGE,
            // PUSER_ID, tiWenService, appuserService,
            // newsService,"http://m.nongjike.cn/NJK/static/jsp/tiwen.html?TIWEN_ID="
            // +TIWEN_ID)).start();
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("message", "程序出错,请联系管理员!");
            pd.put("code", "2");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // ==========================回复帖子评论========================================
    @RequestMapping(value = "CommentActivityUserHui", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String CommentActivityUserHui(String QUSER_ID, String USER_ID, String ACTIVITY_ID, String MESSAGE,
                                         String PUSER_ID, String COMMENT_ACTIVITY_USER_ID) {
        logBefore(logger, "回复评论");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            pd.put("COMMENT_ACTIVITY_USER_ID", this.get32UUID());
            pd.put("NAME", "");
            pd.put("PNAME", "");
            pd.put("DATE", DateUtil.getTime());
            pd.put("STATUS", "1");
            pd.put("VIEWS", "0");
            pd.put("YUE", DateUtil.getDay());
            pd.put("PID", COMMENT_ACTIVITY_USER_ID);
            comment_Activity_UserService.save(pd);
            // new Thread(new CommentTiwenHuiFu(USER_ID, TIWEN_ID, MESSAGE,
            // COMMENT_TIWEN_ID,
            // comment_TiwenService,appuserService,newsService,"http://m.nongjike.cn/NJK/static/jsp/tiwen.html?TIWEN_ID="
            // +TIWEN_ID)).start();
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("message", "程序出错,请联系管理员!");
            pd.put("code", "2");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // ===========================评论直播的用户=========================================
    @RequestMapping(value = "CommentActivityUser", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String CommentActivityUser(String QUSER_ID, String USER_ID, String MESSAGE, String ACTIVITY_ID, String IMG) {
        logBefore(logger, "评论直播的用户");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            String COMMENT_ACTIVITY_USER_ID = this.get32UUID();
            pd.put("COMMENT_ACTIVITY_USER_ID", COMMENT_ACTIVITY_USER_ID);
            pd.put("NAME", "");
            pd.put("PUSER_ID", "");
            pd.put("PNAME", "");
            pd.put("DATE", DateUtil.getTime());
            pd.put("STATUS", "0");
            pd.put("VIEWS", "0");
            pd.put("YUE", DateUtil.getDay());
            pd.put("PID", "");
            comment_Activity_UserService.save(pd);
            if (IMG != "" && IMG != null) {
                String TOUR_IMG[] = IMG.split(",");
                for (int i = 0; i < TOUR_IMG.length; i++) {
                    String ffile = this.get32UUID() + ".jpg";
                    String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + ffile; // 文件上传路径
                    boolean flag = ImageAnd64Binary.generateImage(TOUR_IMG[i], filePath);
                    String TOUR_IMG1 = Const.SERVERPATH + Const.FILEPATHIMG + ffile;
                    PageData pd1 = new PageData();
                    pd1.put("COMMENT_ACTIVITY_USER_ID", COMMENT_ACTIVITY_USER_ID);
                    pd1.put("IMG", TOUR_IMG1);
                    pd1.put("DATE", DateUtil.getTime());
                    comment_Activity_UserImgService.save(pd1);
                }
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("message", "程序出错,请联系管理员!");
            pd.put("code", "2");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // ===========================用户修改CHANNELID====================================
    @RequestMapping(value = "updateChannelid", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String updateChannelid(String USER_ID, String CHANNELID) {
        logBefore(logger, "用户修改CHANNELID");
        PageData pd = new PageData();
        pd = this.getPageData();
        pd.put("CHANNELID", pd.getString("CHANNELID").trim());
        try {
            appuserService.editCHANNELID(pd);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("message", "程序出错,请联系管理员!");
            pd.put("code", "2");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // ===========================查询我的直播===============================
    @RequestMapping(value = "findActivityPostList", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findActivityPostList(String USER_ID, String pageNum) {
        logBefore(logger, "查询我的直播");
        PageData pd = new PageData();
        pd = this.getPageData();
        Page page = new Page();
        String message = "正确返回数据!";
        try {
            pd.put("STATUS", "1");
            if (pageNum == null || pageNum.length() == 0) {
                pageNum = "1";
            }
            page.setPd(pd);
            page.setShowCount(5);
            page.setCurrentPage(Integer.parseInt(pageNum));
            List<PageData> list = activity_PostService.ActivitylistPage(page);
            for (int i = 0; i < list.size(); i++) {
                List<PageData> list3 = activity_PostService.findTop(list.get(i));
                Long day = DateUtil.TimeDiff2(list3.get(0).getString("DATE"), DateUtil.getTime());
                Long hour = DateUtil.TimeDiff1(list3.get(0).getString("DATE"), DateUtil.getTime());
                Long fenzhong = DateUtil.TimeDiff(list3.get(0).getString("DATE"), DateUtil.getTime());
                if (day == 0) {
                    if (hour > 1) {
                        list.get(i).put("YUE", hour + "小时前");
                    } else {
                        list.get(i).put("YUE", fenzhong + "分钟前");
                    }
                }
                // list.get(i).put("YUE", list.get(i).getString("DATE"));
                list.get(i).put("VIEWS", list3.get(0).getString("VIEWS"));
                list.get(i).put("HUIFU", list3.get(0).getString("HUIFU"));
                list.get(i).put("TITLE", list3.get(0).getString("TITLE"));
                String str1 = DateUtil.delHTMLTag(list3.get(0).getString("MESSAGE"));
                String DETAILS1 = str1.replace("\r", "");
                String DETAILS2 = DETAILS1.replace("\n", "");
                String DETAILS3 = DETAILS2.replace("&nbsp;", "");
                int qian = 70;
                if (DETAILS3.length() < 70) {
                    list.get(i).put("MESSAGE1", DETAILS3);
                } else {
                    str1 = DETAILS3.substring(0, qian);
                    list.get(i).put("MESSAGE1", str1 + "...");
                }
                if (list3.size() == 2) {
                    String str11 = DateUtil.delHTMLTag(list3.get(1).getString("MESSAGE"));
                    String DETAILS11 = str11.replace("\r", "");
                    String DETAILS22 = DETAILS11.replace("\n", "");
                    int qian1 = 40;
                    if (DETAILS22.length() < 40) {
                        list.get(i).put("MESSAGE2", DETAILS22);
                    } else {
                        str11 = DETAILS22.substring(0, qian1);
                        list.get(i).put("MESSAGE2", str11 + "...");
                    }
                } else {
                    list.get(i).put("MESSAGE2", "");
                }
                List<PageData> list4 = activity_Post_ImgService.findList(list3.get(0));
                list.get(i).put("POST_IMG", list4);
            }
            Map<String, Object> map = new HashedMap();
            if (page.getCurrentPage() == Integer.parseInt(pageNum)) {
                map.put("object", list);
            } else {
                List<PageData> list4 = new ArrayList();
                message = "已加载全部数据!";
                map.put("object", list4);
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("message", message);
            pd.put("data", map);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("message", "程序出错,请联系管理员!");
            pd.put("code", "2");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // ===========================查询消息================================================
    @RequestMapping(value = "findNews", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findNews(String pageNum, String USER_ID) {
        logBefore(logger, "查询消息");
        PageData pd = new PageData();
        pd = this.getPageData();
        Page page = new Page();
        String message = "正确返回数据!";
        try {
            if (pageNum == null || pageNum.length() == 0) {
                pageNum = "1";
            }
            page.setPd(pd);
            page.setShowCount(10);
            page.setCurrentPage(Integer.parseInt(pageNum));
            List<PageData> list = newsService.list(page);
            for (int i = 0; i < list.size(); i++) {
                Long day = DateUtil.TimeDiff2(list.get(i).getString("DATE"), DateUtil.getTime());
                Long hour = DateUtil.TimeDiff1(list.get(i).getString("DATE"), DateUtil.getTime());
                Long fenzhong = DateUtil.TimeDiff(list.get(i).getString("DATE"), DateUtil.getTime());
                if (day == 0) {
                    if (hour < 1) {
                        list.get(i).put("DATE", fenzhong + "分钟前");
                    } else {
                        list.get(i).put("DATE", hour + "小时前");
                    }
                }
            }
            Map<String, Object> map = new HashedMap();
            if (page.getCurrentPage() == Integer.parseInt(pageNum)) {
                map.put("object", list);
            } else {
                List<PageData> list4 = new ArrayList();
                message = "已加载全部数据!";
                map.put("object", list4);
            }
            pd.put("STATUS", "0");
            appuserService.editStatus(pd);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", message);
            pd.put("data", map);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("message", "程序出错,请联系管理员!");
            pd.put("code", "2");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // ===========================查询猫窝消息================================================
    @RequestMapping(value = "MfindNews", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String MfindNews(String pageNum, String USER_ID) {
        logBefore(logger, "查询消息");
        PageData pd = new PageData();
        pd = this.getPageData();
        Page page = new Page();
        String message = "正确返回数据!";
        try {
            if (pageNum == null || pageNum.length() == 0) {
                pageNum = "1";
            }
            page.setPd(pd);
            page.setShowCount(10);
            page.setCurrentPage(Integer.parseInt(pageNum));
            List<PageData> list = newsService.Maolist(page);
            for (int i = 0; i < list.size(); i++) {
                Long day = DateUtil.TimeDiff2(list.get(i).getString("DATE"), DateUtil.getTime());
                Long hour = DateUtil.TimeDiff1(list.get(i).getString("DATE"), DateUtil.getTime());
                Long fenzhong = DateUtil.TimeDiff(list.get(i).getString("DATE"), DateUtil.getTime());
                if (day == 0) {
                    if (hour < 1) {
                        list.get(i).put("DATE", fenzhong + "分钟前");
                    } else {
                        list.get(i).put("DATE", hour + "小时前");
                    }
                }
            }
            Map<String, Object> map = new HashedMap();
            if (page.getCurrentPage() == Integer.parseInt(pageNum)) {
                map.put("object", list);
            } else {
                List<PageData> list4 = new ArrayList();
                message = "已加载全部数据!";
                map.put("object", list4);
            }
            pd.put("STATUS", "0");
            appuserService.editStatus(pd);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", message);
            pd.put("data", map);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("message", "程序出错,请联系管理员!");
            pd.put("code", "2");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // ==========================点赞和取消点赞专题评论===================================
    @RequestMapping(value = "ZanCommentSpecialService", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String ZanCommentSpecialService(String USER_ID, String COMMENT_SPECIAL_ID, String STATUS) {
        logBefore(logger, "点赞和取消点赞专题评论");
        PageData pd = new PageData();
        pd = this.getPageData();
        String VIEWS = "";
        String STATUS1 = "";
        try {
            if (STATUS.equals("1")) {
                if (zanCommentSpecialService.findById1(pd) == null) {
                    zanCommentSpecialService.save(pd);
                }
                STATUS1 = "1";
            } else {
                zanCommentSpecialService.delete(pd);
                STATUS1 = "0";
            }
            VIEWS = zanCommentSpecialService.findCount(pd).get("count1").toString();
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("VIEWS", VIEWS);
            pd.put("STATUS", STATUS1);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("message", "程序出错,请联系管理员!");
            pd.put("code", "2");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // =========================回复专题回复=======================================
    @RequestMapping(value = "CommentSpecialHui2", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String CommentSpecialHui2(String USER_ID, String FID, String MESSAGE, String PUSER_ID, String PID) {
        logBefore(logger, "回复评论");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            pd.put("COMMENT_SPECIAL_ID", this.get32UUID());
            pd.put("NAME", "");
            pd.put("PNAME", "");
            pd.put("DATE", DateUtil.getTime());
            pd.put("STATUS", "1");
            pd.put("VIEWS", "0");
            pd.put("YUE", DateUtil.getDay());
            pd.put("PID", PID);
            comment_specialService.save(pd);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("message", "程序出错,请联系管理员!");
            pd.put("code", "2");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // ==========================回复专题评论========================================
    @RequestMapping(value = "CommentSpecialHui", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String CommentSpecialHui(String USER_ID, String FID, String MESSAGE, String PUSER_ID,
                                    String COMMENT_SPECIAL_ID) {
        logBefore(logger, "回复专题评论");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            pd.put("COMMENT_SPECIAL_ID", this.get32UUID());
            pd.put("NAME", "");
            pd.put("PNAME", "");
            pd.put("DATE", DateUtil.getTime());
            pd.put("STATUS", "1");
            pd.put("VIEWS", "0");
            pd.put("YUE", DateUtil.getDay());
            pd.put("PID", COMMENT_SPECIAL_ID);
            comment_specialService.save(pd);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("message", "程序出错,请联系管理员!");
            pd.put("code", "2");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // ==============================评论专题======================================
    @RequestMapping(value = "CommentSpecial", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String CommentSpecial(String USER_ID, String FID, String MESSAGE, String IMG) {
        logBefore(logger, "评论专题");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            String COMMENT_SPECIAL_ID = this.get32UUID();
            pd.put("COMMENT_SPECIAL_ID", COMMENT_SPECIAL_ID);
            pd.put("NAME", "");
            pd.put("PUSER_ID", "");
            pd.put("PNAME", "");
            pd.put("DATE", DateUtil.getTime());
            pd.put("STATUS", "0");
            pd.put("VIEWS", "0");
            pd.put("YUE", DateUtil.getDay());
            pd.put("PID", "");
            comment_specialService.save(pd);
            // MESSAGE.replaceAll("!==!", "\n");
            if (IMG != "") {
                String TOUR_IMG[] = IMG.split(",");
                for (int i = 0; i < TOUR_IMG.length; i++) {
                    String ffile = this.get32UUID() + ".jpg";
                    String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + ffile; // 文件上传路径
                    boolean flag = ImageAnd64Binary.generateImage(TOUR_IMG[i], filePath);
                    String TOUR_IMG1 = Const.SERVERPATH + Const.FILEPATHIMG + ffile;
                    PageData pd_i = new PageData();
                    pd_i.put("COMMENT_SPECIAL_ID", COMMENT_SPECIAL_ID);
                    pd_i.put("IMG", TOUR_IMG1);
                    pd_i.put("DATE", DateUtil.getTime());
                    comment_SpecialImgService.save(pd_i);
                }
            }
            PageData pd_c = post_SpecialService.findById(pd);
            pd_c.put("HUIFU", Integer.valueOf(pd_c.getString("HUIFU")) + 1);
            post_SpecialService.editHuiFu(pd_c);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "评论成功");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(pd);
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

    // ==============================查询帖子热词======================================
    @RequestMapping(value = "findKeyword", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findKeyword() {
        logBefore(logger, "查询帖子热词");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            List<PageData> list = post_KeywordService.findList(pd);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("data", list);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员");
        }
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(pd);
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

    // ==============================查询商品=========================================
    @RequestMapping(value = "findProducet", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findProducet(String pageNum) {
        logBefore(logger, "查询商品");
        PageData pd = new PageData();
        Page page = new Page();
        pd = this.getPageData();
        try {
            if (pageNum == null || pageNum.length() == 0) {
                pageNum = "1";
            }
            page.setPd(pd);
            page.setShowCount(5);
            page.setCurrentPage(Integer.parseInt(pageNum));
            List<PageData> list = productService.findListName(pd);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("data", list);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员");
        }
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(pd);
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

    // ==============================提交建议==========================================
    @RequestMapping(value = "Opinion", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String Opinion(String USER_ID, String IMG, String MESSAGE, String PHONE) {
        logBefore(logger, "提交建议");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            String OPINION_ID = this.get32UUID();
            pd.put("OPINION_ID", OPINION_ID);
            pd.put("DATE", DateUtil.getTime());
            pd.put("STATUS", "0");
            opinionService.save(pd);
            if (IMG != "") {
                String TOUR_IMG[] = IMG.split(",");
                for (int i = 0; i < TOUR_IMG.length; i++) {
                    String ffile = this.get32UUID() + ".jpg";
                    String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + ffile; // 文件上传路径
                    boolean flag = ImageAnd64Binary.generateImage(TOUR_IMG[i], filePath);
                    String TOUR_IMG1 = Const.SERVERPATH + Const.FILEPATHIMG + ffile;
                    PageData pd_i = new PageData();
                    pd_i.put("OPINION_ID", OPINION_ID);
                    pd_i.put("IMG", TOUR_IMG1);
                    opinion_ImgService.save(pd_i);
                }
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(pd);
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

    // ==============================查询收藏商品列表==================================
    @RequestMapping(value = "findCollectionPro", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findCollectionPro(String USER_ID, String pageNum) {
        logBefore(logger, "查询收藏商品列表");
        PageData pd = new PageData();
        pd = this.getPageData();
        Page page = new Page();
        String message = "正确返回数据!";
        try {
            if (pageNum == null || pageNum.length() == 0) {
                pageNum = "1";
            }
            page.setPd(pd);
            page.setShowCount(5);
            page.setCurrentPage(Integer.parseInt(pageNum));
            List<PageData> list = collection_proService.list(page);
            Map<String, Object> map = new HashedMap();
            if (page.getCurrentPage() == Integer.parseInt(pageNum)) {
                map.put("object", list);
            } else {
                List<PageData> list4 = new ArrayList();
                message = "已加载全部数据!";
                map.put("object", list4);
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("message", message);
            pd.put("data", map);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(pd);
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

    // ==============================查询我的收藏帖子============================
    @RequestMapping(value = "findCollectionPost", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findCollectionPost(String USER_ID, String pageNum) {
        logBefore(logger, "查询我的收藏帖子");
        PageData pd = new PageData();
        pd = this.getPageData();
        Page page = new Page();
        String message = "正确返回数据!";
        try {
            if (pageNum == null || pageNum.length() == 0) {
                pageNum = "1";
            }
            page.setPd(pd);
            page.setShowCount(10);
            page.setCurrentPage(Integer.parseInt(pageNum));
            List<PageData> list = collection_postService.datalistPage(page);
            for (int i = 0; i < list.size(); i++) {
                Long day = DateUtil.TimeDiff2(list.get(i).getString("DATE"), DateUtil.getTime());
                Long hour = DateUtil.TimeDiff1(list.get(i).getString("DATE"), DateUtil.getTime());
                Long fenzhong = DateUtil.TimeDiff(list.get(i).getString("DATE"), DateUtil.getTime());
                if (day == 0) {
                    if (hour < 1) {
                        list.get(i).put("TIME", fenzhong + "分钟前");
                    } else {
                        list.get(i).put("TIME", hour + "小时前");
                    }
                } else {
                    String YUE1[] = list.get(i).getString("DATE").split(" ");
                    list.get(i).put("TIME", YUE1[0]);
                }
                String YUE[] = list.get(i).getString("YUE").split("-");
                list.get(i).put("YUE", YUE[0]);
                list.get(i).put("RI", YUE[1]);
                list.get(i).put("VIEWS", list.get(i).get("VIEWS").toString());
                List<PageData> list3 = post_ImgService.findTop(list.get(i));
                list.get(i).put("POST_IMG", list3);
                String str = DateUtil.delHTMLTag(list.get(i).getString("MESSAGE"));
                String DETAILS1 = str.replace("\r", "");
                String DETAILS2 = DETAILS1.replace("\n", "");
                String DETAILS3 = DETAILS2.replace("&nbsp;", "");
                int qian = 28;
                if (DETAILS3.length() < 56) {
                    list.get(i).put("MESSAGE", DETAILS3);
                } else {
                    str = DETAILS3.substring(0, qian);
                    list.get(i).put("MESSAGE", str + "...");
                }
                if (list.get(i).getString("IMG") == null) {
                    list.get(i).put("IMG", "http://obmmqs3o7.bkt.clouddn.com/dftheader.jpg");
                }
            }
            Map<String, Object> map = new HashedMap();
            if (page.getCurrentPage() == Integer.parseInt(pageNum)) {
                map.put("object", list);
            } else {
                List<PageData> list4 = new ArrayList();
                message = "已加载全部数据!";
                map.put("object", list4);
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("message", message);
            pd.put("data", map);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(pd);
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

    // ==============================查询我的发帖===============================
    @RequestMapping(value = "findPostUser", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findPostUser(String USER_ID, String pageNum) {
        logBefore(logger, "查询我的发帖");
        PageData pd = new PageData();
        pd = this.getPageData();
        Page page = new Page();
        String message = "正确返回数据!";
        try {
            if (pageNum == null || pageNum.length() == 0) {
                pageNum = "1";
            }
            pd.put("FIRST", "1");
            page.setPd(pd);
            page.setShowCount(5);
            page.setCurrentPage(Integer.parseInt(pageNum));
            List<PageData> list = postService.userlistPage(page);
            for (int i = 0; i < list.size(); i++) {
                list.get(i).put("TIME", list.get(i).getString("DATE"));
                String YUE[] = list.get(i).getString("YUE").split("-");
                list.get(i).put("YUE", YUE[0]);
                list.get(i).put("RI", YUE[1]);
                List<PageData> list3 = post_ImgService.findTop(list.get(i));
                list.get(i).put("IMG", list3);
                String str = DateUtil.delHTMLTag(list.get(i).getString("MESSAGE"));
                String DETAILS1 = str.replace("\r", "");
                String DETAILS2 = DETAILS1.replace("\n", "");
                int qian = 28;
                if (str.length() < 56) {
                    list.get(i).put("MESSAGE", DETAILS2);
                } else {
                    str = DETAILS2.substring(0, qian);
                    list.get(i).put("MESSAGE", str + "...");
                }
                list.get(i).put("VIEWS", zan_PostService.findCount(list.get(i)).get("count").toString());
            }
            Map<String, Object> map = new HashedMap();
            if (page.getCurrentPage() == Integer.parseInt(pageNum)) {
                map.put("object", list);
            } else {
                List<PageData> list4 = new ArrayList();
                message = "已加载全部数据!";
                map.put("object", list4);
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("message", message);
            pd.put("data", map);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(pd);
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

    // ==============================删除我评论内容============================
    @RequestMapping(value = "deletePostInfo", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String deletePostInfo(String COMMENT_POST_ID) {
        logBefore(logger, "删除我评论内容");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            comment_PostService.delete(pd);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(pd);
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

    // ==============================查询我评论的内容==========================
    @RequestMapping(value = "findPostInfo", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findPostInfo(String USER_ID, String pageNum) {
        logBefore(logger, "查询我评论的内容");
        PageData pd = new PageData();
        pd = this.getPageData();
        Page page = new Page();
        String message = "正确返回数据!";
        try {
            pd.put("USER_ID", pd.getString("USER_ID").trim());
            if (pageNum == null || pageNum.length() == 0) {
                pageNum = "1";
            }
            page.setPd(pd);
            page.setShowCount(5);
            page.setCurrentPage(Integer.parseInt(pageNum));
            List<PageData> list = comment_PostService.data12listPage(page);
            Map<String, Object> map = new HashedMap();
            if (page.getCurrentPage() == Integer.parseInt(pageNum)) {
                map.put("object", list);
            } else {
                List<PageData> list4 = new ArrayList();
                message = "已加载全部数据!";
                map.put("object", list4);
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("message", message);
            pd.put("data", map);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(pd);
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

    // ==============================查询优惠卷===============================
    @RequestMapping(value = "findCoupon", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findCoupon(String USER_ID) {
        logBefore(logger, "查询优惠卷");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            List<PageData> list = couponService.findList(pd);
            for (int i = 0; i < list.size(); i++) {
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                Date dt1 = df.parse(list.get(i).getString("END"));
                Date dt2 = df.parse(DateUtil.getDay());
                if (dt1.getTime() < dt2.getTime()) {
                    couponService.delete(list.get(i));
                    list.remove(i);
                }
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("data", list);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(pd);
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

    // ==============================直播发帖====================================
    @RequestMapping(value = "CctivityPost", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String CctivityPost(String ACTIVITY_ID, String USER_ID, String MESSAGE, String IMG,
                               String ACTIVITY_ROOM_ID) {
        logBefore(logger, "直播发帖");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            String ACTIVITY_ROOM_ID1 = this.get32UUID();
            if (ACTIVITY_ROOM_ID != null && ACTIVITY_ROOM_ID != "") {
                ACTIVITY_ROOM_ID1 = ACTIVITY_ROOM_ID;
            } else {
                PageData pd_a = activity_RoomService.findById1(pd);
                if (pd_a != null) {
                    ACTIVITY_ROOM_ID1 = pd_a.getString("ACTIVITY_ROOM_ID");
                } else {
                    PageData pd_b = new PageData();
                    pd_b.put("ACTIVITY_ROOM_ID", ACTIVITY_ROOM_ID1);
                    pd_b.put("CROP", "待定");
                    pd_b.put("ADDRESS", "未知");
                    pd_b.put("USER_ID", USER_ID);
                    pd_b.put("ACTIVITY_ID", ACTIVITY_ID);
                    pd_b.put("BEIYONG1", "");
                    pd_b.put("BEIYONG2", "");
                    pd_b.put("BEIYONG3", "");
                    pd_b.put("BEIYONG4", "");
                    pd_b.put("BEIYONG5", "");
                    pd_b.put("DATE", DateUtil.getTime());
                    pd_b.put("VIEWS", "0");
                    pd_b.put("HUIFU", "0");
                    pd_b.put("IMG", "http://m.nongjike.cn/NJK/uploadFiles/uploadImgs/logo.png");
                    activity_RoomService.save(pd_b);
                }
            }
            String ffile1 = this.get32UUID() + ".jpg";
            String filePath2 = PathUtil.getClasspath() + Const.FILEPATHIMG + "zhibo/" + DateUtil.getDays(); // 文件上传路径
            File file = new File(filePath2, ffile1);
            if (!file.exists()) {
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
            }
            String ACTIVITY_POST_ID = this.get32UUID();
            String MESSAGE1 = MESSAGE.replace("!==!", "<br/>");
            MESSAGE1 = MESSAGE1.replace("\n", "<br/>");
            // MESSAGE.replaceAll("!==!", "\n");
            if (IMG != "") {
                String TOUR_IMG[] = IMG.split(",");
                for (int i = 0; i < TOUR_IMG.length; i++) {
                    String ffile = this.get32UUID() + ".jpg";
                    String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + "zhibo/" + DateUtil.getDays() + "/"
                            + ffile; // 文件上传路径
                    boolean flag = ImageAnd64Binary.generateImage(TOUR_IMG[i], filePath);
                    String TOUR_IMG1 = Const.SERVERPATH + Const.FILEPATHIMG + "zhibo/" + DateUtil.getDays() + "/"
                            + ffile;
                    MESSAGE1 += "<br/><img src=" + TOUR_IMG1 + "/><br/><br/>";
                    PageData pd_i = new PageData();
                    pd_i.put("ACTIVITY_POST_ID", ACTIVITY_POST_ID);
                    pd_i.put("IMG", TOUR_IMG1);
                    pd_i.put("ACTIVITY_POST_IMG_ID", this.get32UUID());
                    pd_i.put("ORDE_BY", i + 1);
                    activity_Post_ImgService.save(pd_i);
                }
            }
            pd.put("MESSAGE", "<style>img{max-width:100%;}</style><p>" + MESSAGE1 + "</p>");
            pd.put("ACTIVITY_POST_ID", ACTIVITY_POST_ID);
            pd.put("SUBJECT", "");
            pd.put("DATE", DateUtil.getTime());
            pd.put("PROMPT", "");
            pd.put("STATUS", "1");
            pd.put("YUE", DateUtil.getDay3());
            pd.put("VIEWS", "0");
            pd.put("HUIFU", "0");
            pd.put("ACTIVITY_ROOM_ID", ACTIVITY_ROOM_ID1);
            activity_PostService.save(pd);
            PageData pd1 = activityService.findById(pd);
            pd1.put("VIEWS", Integer.valueOf(pd1.getString("VIEWS")) + 1);
            activityService.editViews(pd1);
            pd.put("STATUS", "1");
            collection_RoomService.editStatus(pd);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(pd);
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

    // ==============================直播发帖====================================
    @RequestMapping(value = "CctivityPost2", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String CctivityPost2(String ACTIVITY_ID, String USER_ID, String MESSAGE, String img1, String img2,
                                String img3, String img4, String img5, String img6, String img7, String img8, String img9, String count,
                                String ACTIVITY_ROOM_ID) {
        logBefore(logger, "直播发帖");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            String ACTIVITY_ROOM_ID1 = this.get32UUID();
            if (ACTIVITY_ROOM_ID != null && ACTIVITY_ROOM_ID != "") {
                ACTIVITY_ROOM_ID1 = ACTIVITY_ROOM_ID;
            } else {
                PageData pd_a = activity_RoomService.findById1(pd);
                if (pd_a != null) {
                    ACTIVITY_ROOM_ID1 = pd_a.getString("ACTIVITY_ROOM_ID");
                } else {
                    PageData pd_b = new PageData();
                    pd_b.put("ACTIVITY_ROOM_ID", ACTIVITY_ROOM_ID1);
                    pd_b.put("CROP", "待定");
                    pd_b.put("ADDRESS", "未知");
                    pd_b.put("USER_ID", USER_ID);
                    pd_b.put("ACTIVITY_ID", ACTIVITY_ID);
                    pd_b.put("BEIYONG1", "");
                    pd_b.put("BEIYONG2", "");
                    pd_b.put("BEIYONG3", "");
                    pd_b.put("BEIYONG4", "");
                    pd_b.put("BEIYONG5", "");
                    pd_b.put("DATE", DateUtil.getTime());
                    pd_b.put("VIEWS", "0");
                    pd_b.put("HUIFU", "0");
                    pd_b.put("IMG", "http://m.nongjike.cn/NJK/uploadFiles/uploadImgs/logo.png");
                    activity_RoomService.save(pd_b);
                }
            }
            String ffile1 = this.get32UUID() + ".jpg";
            String filePath2 = PathUtil.getClasspath() + Const.FILEPATHIMG + "zhibo/" + DateUtil.getDays(); // 文件上传路径
            File file = new File(filePath2, ffile1);
            if (!file.exists()) {
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
            }
            String ACTIVITY_POST_ID = this.get32UUID();
            String MESSAGE1 = MESSAGE.replace("!==!", "<br/>");
            MESSAGE1 = MESSAGE1.replace("\n", "<br/>");
            // MESSAGE.replaceAll("!==!", "\n");
            Integer count1 = Integer.valueOf(count);
            for (int i = 0; i < count1; i++) {
                String ffile = this.get32UUID() + ".jpg";
                String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + "zhibo/" + DateUtil.getDays() + "/"
                        + ffile; // 文件上传路径
                String TOUR_IMG1 = Const.SERVERPATH + Const.FILEPATHIMG + "zhibo/" + DateUtil.getDays() + "/" + ffile;
                String ii = String.valueOf(i + 1);
                MESSAGE1 += "<br/><img src=" + TOUR_IMG1 + "/><br/>";
                PageData pd_i = new PageData();
                pd_i.put("ACTIVITY_POST_ID", ACTIVITY_POST_ID);
                pd_i.put("IMG", TOUR_IMG1);
                pd_i.put("ACTIVITY_POST_IMG_ID", this.get32UUID());
                pd_i.put("ORDE_BY", i + 1);
                activity_Post_ImgService.save(pd_i);
                new Thread(new Thread2(pd.getString("img" + ii), TOUR_IMG1, filePath, TOUR_IMG1, ffile, post_ImgService,
                        i)).start();
                System.err.println(TOUR_IMG1);
                System.err.println(TOUR_IMG1);
            }
            pd.put("MESSAGE", "<style>img{max-width:100%;}</style><p>" + MESSAGE1 + "</p>");
            pd.put("ACTIVITY_POST_ID", ACTIVITY_POST_ID);
            pd.put("SUBJECT", "");
            pd.put("DATE", DateUtil.getTime());
            pd.put("PROMPT", "");
            pd.put("STATUS", "1");
            pd.put("YUE", DateUtil.getDay3());
            pd.put("VIEWS", "0");
            pd.put("HUIFU", "0");
            pd.put("ACTIVITY_ROOM_ID", ACTIVITY_ROOM_ID1);
            activity_PostService.save(pd);
            PageData pd1 = activityService.findById(pd);
            pd1.put("VIEWS", Integer.valueOf(pd1.getString("VIEWS")) + 1);
            activityService.editViews(pd1);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(pd);
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

    // ==========================点赞和取消点赞直播帖子评论===================================
    @RequestMapping(value = "ZanCommentPostActivity", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String ZanCommentPostActivity(String USER_ID, String COMMENT_ACTIVITY_ID, String STATUS) {
        logBefore(logger, "点赞和取消点赞直播帖子评论");
        PageData pd = new PageData();
        pd = this.getPageData();
        String VIEWS = "";
        String STATUS1 = "";
        try {
            if (STATUS.equals("1")) {
                if (zanCommentActivityService.findById1(pd) == null) {
                    zanCommentActivityService.save(pd);
                } else {
                    VIEWS = comment_ActivityService.findById(pd).getString("VIEWS");
                }
                STATUS1 = "1";
            } else {
                zanCommentActivityService.delete(pd);
                STATUS1 = "0";
            }
            VIEWS = zanCommentActivityService.findCount(pd).get("count").toString();
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("VIEWS", VIEWS);
            pd.put("STATUS", STATUS1);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("message", "程序出错,请联系管理员!");
            pd.put("code", "2");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // ==========================回复直播评论========================================
    @RequestMapping(value = "CommentActivityHui", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String CommentActivityHui(String USER_ID, String ACTIVITY_POST_ID, String MESSAGE, String PUSER_ID,
                                     String COMMENT_ACTIVITY_ID, String STATUS) {
        logBefore(logger, "回复直播评论");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            pd.put("COMMENT_ACTIVITY_ID", this.get32UUID());
            pd.put("NAME", "");
            pd.put("PNAME", "");
            pd.put("DATE", DateUtil.getTime());
            pd.put("STATUS", "1");
            pd.put("VIEWS", "0");
            pd.put("YUE", DateUtil.getDay());
            pd.put("PID", COMMENT_ACTIVITY_ID);
            comment_ActivityService.save(pd);
            new Thread(new CommentActivityHui(USER_ID, ACTIVITY_POST_ID, MESSAGE, COMMENT_ACTIVITY_ID,
                    comment_ActivityService, appuserService, newsService)).start();
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("message", "程序出错,请联系管理员!");
            pd.put("code", "2");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    @RequestMapping(value = "CommentActivityHui2", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String CommentActivityHui2(String USER_ID, String ACTIVITY_POST_ID, String MESSAGE, String PUSER_ID,
                                      String PID, String STATUS) {
        logBefore(logger, "回复评论");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            pd.put("COMMENT_ACTIVITY_ID", this.get32UUID());
            pd.put("NAME", "");
            pd.put("PNAME", "");
            pd.put("DATE", DateUtil.getTime());
            pd.put("STATUS", "1");
            pd.put("VIEWS", "0");
            pd.put("YUE", DateUtil.getDay());
            pd.put("PID", PID);
            comment_ActivityService.save(pd);
            new Thread(new CommentActivityHui2(USER_ID, ACTIVITY_POST_ID, MESSAGE, PUSER_ID, activity_PostService,
                    appuserService, newsService)).start();
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("message", "程序出错,请联系管理员!");
            pd.put("code", "2");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // ==============================评论直播帖子======================================
    @RequestMapping(value = "CommentActivity", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String CommentActivity(String USER_ID, String ACTIVITY_POST_ID, String MESSAGE, String IMG, String STATUS) {
        logBefore(logger, "评论直播帖子");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            String COMMENT_ACTIVITY_ID = this.get32UUID();
            pd.put("COMMENT_ACTIVITY_ID", COMMENT_ACTIVITY_ID);
            pd.put("NAME", "");
            pd.put("PUSER_ID", "");
            pd.put("PNAME", "");
            pd.put("DATE", DateUtil.getTime());
            pd.put("STATUS", "0");
            pd.put("VIEWS", "0");
            pd.put("YUE", DateUtil.getDay());
            pd.put("PID", "");
            comment_ActivityService.save(pd);
            // MESSAGE.replaceAll("!==!", "\n");
            if (IMG != "") {
                String TOUR_IMG[] = IMG.split(",");
                for (int i = 0; i < TOUR_IMG.length; i++) {
                    String ffile = this.get32UUID() + ".jpg";
                    String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + ffile; // 文件上传路径
                    boolean flag = ImageAnd64Binary.generateImage(TOUR_IMG[i], filePath);
                    String TOUR_IMG1 = Const.SERVERPATH + Const.FILEPATHIMG + ffile;
                    PageData pd_i = new PageData();
                    pd_i.put("COMMENT_ACTIVITY_ID", COMMENT_ACTIVITY_ID);
                    pd_i.put("IMG", TOUR_IMG1);
                    pd_i.put("DATE", DateUtil.getTime());
                    comment_ActivityImgService.save(pd_i);
                }
            }
            PageData pd_c = activity_PostService.findById(pd);
            pd_c.put("HUIFU", Integer.valueOf(pd_c.getString("HUIFU")) + 1);
            activity_PostService.editHuiFu(pd_c);
            new Thread(new CommentActivity(ACTIVITY_POST_ID, USER_ID, MESSAGE, activity_PostService, appuserService,
                    newsService)).start();
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(pd);
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

    // ==============================查询直播帖子的评论=================================
    @RequestMapping(value = "findCommentActivity", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findCommentActivity(String ACTIVITY_POST_ID, String pageNum, String USER_ID) {
        logBefore(logger, "查询直播帖子的评论");
        PageData pd = new PageData();
        pd = this.getPageData();
        Page page = new Page();
        String message = "正确返回数据!";
        try {
            if (pageNum == null || pageNum.length() == 0) {
                pageNum = "1";
            }
            pd.put("STATUS", "0");
            page.setPd(pd);
            page.setShowCount(5);
            page.setCurrentPage(Integer.parseInt(pageNum));
            List<PageData> list = comment_ActivityService.list(page);
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getString("PNAME") == null) {
                    list.get(i).put("PNAME", "");
                }
                list.get(i).put("USER_ID1", USER_ID);
                if (zanCommentActivityService.findById(list.get(i)) != null) {
                    list.get(i).put("ZAN", "1");
                } else {
                    list.get(i).put("ZAN", "0");
                }
                PageData pd_z = zanCommentActivityService.findCounts(list.get(i));
                list.get(i).put("VIEWS", pd_z.get("count").toString());
                /*
                 * Long
                 * day=DateUtil.TimeDiff2(list.get(i).getString("DATE"),DateUtil
                 * .getTime()); Long
                 * hour=DateUtil.TimeDiff1(list.get(i).getString("DATE"),
                 * DateUtil.getTime()); Long
                 * fenzhong=DateUtil.TimeDiff(list.get(i).getString("DATE"),
                 * DateUtil.getTime()); if(day==0){ if(hour>1){
                 * list.get(i).put("YUE", hour+"小时前"); }else{
                 * list.get(i).put("YUE", fenzhong+"分钟前"); } }
                 */
                list.get(i).put("YUE", list.get(i).getString("DATE"));
                List<PageData> list1 = comment_ActivityImgService.findList(list.get(i));
                list.get(i).put("CIMG", list1);
                list.get(i).put("STATUS", "1");
                List<PageData> list2 = comment_ActivityService.findList(list.get(i));
                for (int f = 0; f < list2.size(); f++) {
                    if (list2.get(f).getString("PNAME") == null) {
                        list2.get(f).put("PNAME", "");
                    }
                }
                list.get(i).put("COMMENT", list2);
            }
            Map<String, Object> map = new HashedMap();
            if (page.getCurrentPage() == Integer.parseInt(pageNum)) {
                map.put("object", list);
            } else {
                List<PageData> list4 = new ArrayList();
                message = "已加载全部数据!";
                map.put("object", list4);
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("message", message);
            pd.put("data", map);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(pd);
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

    // ==============================点赞直播帖子======================================
    @RequestMapping(value = "ZanCommentActivity", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String ZanCommentActivity(String USER_ID, String ACTIVITY_POST_ID, String STATUS) {
        logBefore(logger, "点赞和取消点赞直播帖子");
        PageData pd = new PageData();
        pd = this.getPageData();
        String VIEWS = "";
        String STATUS1 = "";
        try {
            if (STATUS.equals("1")) {
                if (zan_ActivityPostService.findById(pd) == null) {
                    PageData pd1 = activity_PostService.findById(pd);
                    String VIEWS1 = pd1.getString("VIEWS");
                    pd1.put("VIEWS", Integer.valueOf(pd1.getString("VIEWS")) + 1);
                    activity_PostService.editViews(pd1);
                    int a = Integer.valueOf(VIEWS1) + 1;
                    VIEWS = String.valueOf(a);
                    zan_ActivityPostService.save(pd);
                } else {
                    VIEWS = activity_PostService.findById(pd).getString("VIEWS");
                }
                STATUS1 = "1";
            } else {
                PageData pd1 = activity_PostService.findById(pd);
                String VIEWS1 = pd1.getString("VIEWS");
                pd1.put("VIEWS", Integer.valueOf(pd1.getString("VIEWS")) - 1);
                int a = Integer.valueOf(VIEWS1) - 1;
                VIEWS = String.valueOf(a);
                if (zan_ActivityPostService.findById(pd) != null) {
                    activity_PostService.editViews(pd1);
                }
                zan_ActivityPostService.delete(pd);
                STATUS1 = "0";
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("VIEWS", VIEWS);
            pd.put("STATUS", STATUS1);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(pd);
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

    // ==============================查询关注的直播帖子====================================
    @RequestMapping(value = "findCollectionActivity1", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findCollectionActivity1(String USER_ID, String pageNum) {
        logBefore(logger, "查询关注的直播帖子");
        PageData pd = new PageData();
        pd = this.getPageData();
        Page page = new Page();
        String message = "正确返回数据!";
        try {
            if (pageNum == null || pageNum.length() == 0) {
                pageNum = "1";
            }
            page.setPd(pd);
            page.setShowCount(6);
            page.setCurrentPage(Integer.parseInt(pageNum));
            List<PageData> list = collection_ActivityPostService.list(page);
            for (int i = 0; i < list.size(); i++) {
                list.get(i).put("YUE", list.get(i).getString("DATE"));
                String str1 = DateUtil.delHTMLTag(list.get(i).getString("MESSAGE"));
                String DETAILS1 = str1.replace("\r", "");
                String DETAILS2 = DETAILS1.replace("\n", "");
                String DETAILS3 = DETAILS2.replace("&nbsp;", "");
                int qian = 200;
                if (DETAILS2.length() < 200) {
                    list.get(i).put("MESSAGE", DETAILS3);
                } else {
                    str1 = DETAILS3.substring(0, qian);
                    list.get(i).put("MESSAGE", str1 + "...");
                }
                List<PageData> list4 = activity_Post_ImgService.findList(list.get(i));
                list.get(i).put("POST_IMG", list4);
            }
            Map<String, Object> map = new HashedMap();
            if (page.getCurrentPage() == Integer.parseInt(pageNum)) {
                map.put("object", list);
            } else {
                List<PageData> list4 = new ArrayList();
                message = "已加载全部数据!";
                map.put("object", list4);
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("message", message);
            pd.put("data", map);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(pd);
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

    // ==============================查询直播帖子详情===============================
    @RequestMapping(value = "findActivityPostId", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findActivityPostId(String ACTIVITY_POST_ID, String USER_ID) {
        logBefore(logger, "查询直播帖子详情");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            PageData pd1 = new PageData();
            pd1 = activity_PostService.findById(pd);
            pd1.put("URL",
                    "http://m.nongjike.cn/NJK/static/jsp/activity_post.html?ACTIVITY_POST_ID=" + ACTIVITY_POST_ID);
            if (zan_ActivityPostService.findById(pd) != null) {
                pd1.put("ZAN", "1");
            } else {
                pd1.put("ZAN", "0");
            }
            if (collection_ActivityPostService.findById(pd) != null) {
                pd1.put("COLLECTION", "1");
            } else {
                pd1.put("COLLECTION", "0");
            }
            List<PageData> list1 = activity_Post_ImgService.findLists(pd);
            pd1.put("PIMG", list1);
            String aa = "";
            String str = DateUtil.delHTMLTag(pd1.getString("MESSAGE"));
            String DETAILS1 = str.replace("\r", "");
            String DETAILS2 = DETAILS1.replace("\n", "");
            String DETAILS3 = DETAILS2.replace("&nbsp;", "");
            int qian = 56;
            if (DETAILS3.length() < 56) {
                pd1.put("JIANJIE", DETAILS3);
                aa = DETAILS3;
            } else {
                str = DETAILS3.substring(0, qian);
                pd1.put("JIANJIE", str + "...");
                aa = str + "...";
            }
            if (pd1.getString("IMG") == null) {
                pd1.put("IMG", "http://obmmqs3o7.bkt.clouddn.com/dftheader.jpg");
            }
            new Thread(new History(aa, ACTIVITY_POST_ID, "4", "直播", USER_ID, "", historyService)).start();
            pd.clear();
            pd.put("data", pd1);
            pd.put("message", "正确返回数据!");
            pd.put("code", "1");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(pd);
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

    // =============================关注直播帖子=======================================
    @RequestMapping(value = "CollectionActivityPost", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String CollectionActivityPost(String ACTIVITY_POST_ID, String USER_ID, String STATUS) {
        logBefore(logger, "关注直播帖子");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            if (STATUS.equals("0")) {
                pd.put("COLLECTION_ACTIVITY_POST_ID", this.get32UUID());
                pd.put("DATE", DateUtil.getTime());
                collection_ActivityPostService.save(pd);
            } else {
                collection_ActivityPostService.delete(pd);
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(pd);
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

    // ==============================关注直播和取消关注==================================
    @RequestMapping(value = "CollectionActivity", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String CollectionActivity(String ACTIVITY_ID, String USER_ID, String STATUS) {
        logBefore(logger, "关注和取消直播");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            if (STATUS.equals("0")) {
                PageData pd_c = collection_activityService.findById(pd);
                if (pd_c == null) {
                    pd.put("COLLECTION_ACTIVITY_ID", this.get32UUID());
                    pd.put("DATE", DateUtil.getTime());
                    pd.put("STATUS", "0");
                    collection_activityService.save(pd);
                }
            } else {
                collection_activityService.delete(pd);
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(pd);
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

    // ==============================查询直播上半部分==============================
    @RequestMapping(value = "findActivityTop", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findActivityTop(String ACTIVITY_ID, String USER_ID) {
        logBefore(logger, "查询直播上半部分");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            PageData pd2 = collection_activityService.findById(pd);
            PageData pd1 = activityService.findById(pd);
            if (pd2 != null) {
                pd1.put("COLLECTION", "1");
            } else {
                pd1.put("COLLECTION", "0");
            }
            List<PageData> list1 = postService.selectActivity(pd);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("ACTIVITY", pd1);
            pd.put("TOP", list1);
            pd.put("url", "http://m.nongjike.cn/NJK/static/jsp/activity.html?ACTIVITY_ID=" + ACTIVITY_ID);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(pd);
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

    // ==============================查询用户自己发的帖子直播=========================
    @RequestMapping(value = "findActivityList", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findActivityList(String USER_ID, String ACTIVITY_ID, String pageNum, String QUSER_ID) {
        logBefore(logger, "查询用户自己发的帖子直播");
        PageData pd = new PageData();
        Page page = new Page();
        pd = this.getPageData();
        String message = "正确返回数据!";
        Integer fangwen2 = 0;
        try {
            if (views_ActivityService.findById(pd) == null) {
                views_ActivityService.save(pd);
            }
            if (pageNum == null || pageNum.length() == 0) {
                pageNum = "1";
            }
            pd.put("STATUS", "1");
            page.setPd(pd);
            page.setShowCount(30);
            page.setCurrentPage(Integer.parseInt(pageNum));
            List<PageData> list = activity_PostService.list(page);
            for (int i = 0; i < list.size(); i++) {
                list.get(i).put("YUE", list.get(i).getString("DATE"));
                String str1 = DateUtil.delHTMLTag(list.get(i).getString("MESSAGE"));
                String DETAILS1 = str1.replace("\r", "");
                String DETAILS2 = DETAILS1.replace("\n", "");
                String DETAILS3 = DETAILS2.replace("&nbsp;", "");
                int qian = 200;
                if (DETAILS3.length() < 200) {
                    list.get(i).put("MESSAGE", DETAILS3);
                } else {
                    str1 = DETAILS3.substring(0, qian);
                    list.get(i).put("MESSAGE", str1 + "...");
                }
                List<PageData> list4 = activity_Post_ImgService.findList(list.get(i));
                list.get(i).put("POST_IMG", list4);
            }
            PageData pd4 = views_ActivityService.findCount(pd);
            Integer fangwen = (Integer.valueOf(pd4.get("count").toString()));
            PageData pd6 = views_Activity_IpService.findCount(pd);
            Integer fangwen1 = (Integer.valueOf(pd6.get("count").toString()));
            fangwen2 = fangwen + fangwen1;
            PageData pd5 = collection_Activity_UserService.findCount(pd);
            String ZAN = "0";
            if (collection_Activity_UserService.findById(pd) != null) {
                ZAN = "1";
            }
            Map<String, Object> map = new HashedMap();
            if (page.getCurrentPage() == Integer.parseInt(pageNum)) {
                map.put("object", list);
            } else {
                List<PageData> list4 = new ArrayList();
                message = "已加载全部数据!";
                map.put("object", list4);
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("message", message);
            pd.put("data", map);
            pd.put("FANGWEN", fangwen2.toString());
            pd.put("ZANSHU", pd5.get("count").toString());
            pd.put("ZAN", ZAN);
            pd.put("url", "http://m.nongjike.cn/NJK/static/jsp/activity_list.html?ACTIVITY_ID=" + ACTIVITY_ID
                    + "&USER_ID=" + USER_ID);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(pd);
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

    // ==============================查询是否为直播嘉宾=================================
    @RequestMapping(value = "findZQuanXian", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findZQuanXian(String USER_ID, String ACTIVITY_ID) {
        PageData pd = new PageData();
        pd = this.getPageData();
        String STATUS = "0";
        try {
            PageData pd1 = z_QuanXianService.findById2(pd);
            if (pd1 != null) {
                STATUS = "1";
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("STATUS", STATUS);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(pd);
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

    // ==============================查询直播详情==============================
    @RequestMapping(value = "findActivityId", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findActivityId(String ACTIVITY_ID, String pageNum) {
        logBefore(logger, "查询直播详情");
        PageData pd = new PageData();
        pd = this.getPageData();
        Page page = new Page();
        String message = "正确返回数据!";
        try {
            if (pageNum == null || pageNum.length() == 0) {
                pageNum = "1";
            }
            pd.put("STATUS", "1");
            page.setPd(pd);
            page.setShowCount(6);
            page.setCurrentPage(Integer.parseInt(pageNum));
            List<PageData> list2 = activity_PostService.list1(page);
            for (int i = 0; i < list2.size(); i++) {
                List<PageData> list3 = activity_PostService.findTop(list2.get(i));
                list2.get(i).put("YUE", list3.get(0).getString("DATE"));
                PageData pd_v = activity_PostService.findNumViews(list3.get(0));
                list2.get(i).put("VIEWS", list3.get(0).getString("VIEWS"));
                list2.get(i).put("HUIFU", list3.get(0).getString("HUIFU"));
                double d = Double.valueOf(pd_v.get("views").toString());
                int ii = (int) d;
                PageData pd5 = collection_Activity_UserService.findCount(list3.get(0));
                list2.get(i).put("ZVIEWS", String.valueOf(ii + Integer.valueOf(pd5.get("count").toString())));
                String str1 = DateUtil.delHTMLTag(list3.get(0).getString("MESSAGE"));
                String DETAILS1 = str1.replace("\r", "");
                String DETAILS2 = DETAILS1.replace("\n", "");
                String DETAILS3 = DETAILS2.replace("&nbsp;", "");
                int qian = 70;
                if (DETAILS3.length() < 70) {
                    list2.get(i).put("MESSAGE1", DETAILS3);
                } else {
                    str1 = DETAILS3.substring(0, qian);
                    list2.get(i).put("MESSAGE1", str1 + "...");
                }
                if (list3.size() == 2) {
                    String str11 = DateUtil.delHTMLTag(list3.get(1).getString("MESSAGE"));
                    String DETAILS11 = str11.replace("\r", "");
                    String DETAILS22 = DETAILS11.replace("\n", "");
                    int qian1 = 40;
                    if (DETAILS22.length() < 40) {
                        list2.get(i).put("MESSAGE2", DETAILS22);
                    } else {
                        str11 = DETAILS22.substring(0, qian1);
                        list2.get(i).put("MESSAGE2", str11 + "...");
                    }
                } else {
                    list2.get(i).put("MESSAGE2", "");
                }
                List<PageData> list4 = activity_Post_ImgService.findList(list3.get(0));
                list2.get(i).put("POST_IMG", list4);
                PageData pd4 = views_ActivityService.findCount(list3.get(i));
                Integer fangwen = (Integer.valueOf(pd4.get("count").toString()));
                PageData pd6 = views_Activity_IpService.findCount(list3.get(i));
                Integer fangwen1 = (Integer.valueOf(pd6.get("count").toString()));
                Integer fangwen2 = fangwen + fangwen1;
                list2.get(i).put("FANGWEN", fangwen2.toString());
                list2.get(i).put("ZANSHU", pd5.get("count").toString());
                if (collection_Activity_UserService.findById(list3.get(0)) != null) {
                    list2.get(i).put("ZAN", "1");
                } else {
                    list2.get(i).put("ZAN", "0");
                }

            }
            Map<String, Object> map = new HashedMap();
            if (page.getCurrentPage() == Integer.parseInt(pageNum)) {
                map.put("object", list2);
            } else {
                List<PageData> list4 = new ArrayList();
                message = "已加载全部数据!";
                map.put("object", list4);
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("message", message);
            pd.put("data", map);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(pd);
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

    // ==============================查询关注过的直播============================
    @RequestMapping(value = "findCollectionActivity", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findCollectionActivity(String USER_ID, String pageNum) {
        logBefore(logger, "查询关注过的直播");
        PageData pd = new PageData();
        pd = this.getPageData();
        Page page = new Page();
        String message = "正确返回数据!";
        try {
            if (pageNum == null || pageNum.length() == 0) {
                pageNum = "1";
            }
            page.setPd(pd);
            page.setShowCount(10);
            page.setCurrentPage(Integer.parseInt(pageNum));
            List<PageData> list = collection_activityService.datalistPage(page);
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getString("SPECIAL_STATUS").equals("1")) {
                    list.get(i).put("ACTIVITY_ROOM_ID",
                            activity_RoomService.findById3(list.get(i)).getString("ACTIVITY_ROOM_ID"));
                } else {
                    list.get(i).put("ACTIVITY_ROOM_ID", "");
                }
            }
            pd.clear();
            pd.put("code", "1");
            if (page.getCurrentPage() == Integer.parseInt(pageNum)) {
                pd.put("data", list);
            } else {
                List<PageData> list4 = new ArrayList();
                message = "已加载全部数据!";
                pd.put("data", list4);
            }
            pd.put("message", message);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(pd);
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

    // ==============================查询直播=================================
    @RequestMapping(value = "findActivity", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findActivity() {
        logBefore(logger, "查询直播");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            pd.put("STATUS", "0");
            List<PageData> list = activityService.findList(pd);
            pd.clear();
            pd.put("STATUS", "1");
            List<PageData> list1 = activityService.findList(pd);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("ACTIVITY", list);
            pd.put("ACTIVITY1", list1);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(pd);
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

    @RequestMapping(value = "findActivity2", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findActivity2() {
        logBefore(logger, "查询直播");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            List<PageData> list = activityService.findList(pd);
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getString("SPECIAL_STATUS").equals("1")) {
                    list.get(i).put("ACTIVITY_ROOM_ID",
                            activity_RoomService.findById1(list.get(i)).getString("ACTIVITY_ROOM_ID"));
                } else {
                    list.get(i).put("ACTIVITY_ROOM_ID", "");
                }
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("ACTIVITY", list);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(pd);
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

    // ==============================查询我关注的专题===================================
    @RequestMapping(value = "findCollectionSpecial", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findCollectionSpecial(String USER_ID, String pageNum) {
        logBefore(logger, "查询我关注的专题");
        PageData pd = new PageData();
        pd = this.getPageData();
        Page page = new Page();
        String message = "正确返回数据";
        try {
            if (pageNum == null || pageNum.length() == 0) {
                pageNum = "1";
            }
            page.setPd(pd);
            page.setShowCount(10);
            page.setCurrentPage(Integer.parseInt(pageNum));
            List<PageData> list = collection_specialService.datalistPage(page);
            Map<String, Object> map = new HashedMap();
            pd.clear();
            pd.put("code", "1");
            if (page.getCurrentPage() == Integer.parseInt(pageNum)) {
                pd.put("data", list);
            } else {
                List<PageData> list4 = new ArrayList();
                message = "已加载全部数据!";
                pd.put("data", list4);
            }
            pd.put("message", message);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(pd);
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

    // ==============================专题点赞和取消点赞==============================
    @RequestMapping(value = "ZanSpecial", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String ZanSpecial(String USER_ID, String FID, String STATUS) {
        logBefore(logger, "专题点赞和取消点赞");
        PageData pd = new PageData();
        pd = this.getPageData();
        String VIEWS = "";
        String STATUS1 = "";
        try {
            if (STATUS.equals("1")) {
                if (zan_SpecialService.findById(pd) == null) {
                    PageData pd1 = post_SpecialService.findById(pd);
                    String VIEWS1 = pd1.getString("VIEWS");
                    pd1.put("VIEWS", Integer.valueOf(pd1.getString("VIEWS")) + 1);
                    post_SpecialService.editViews(pd1);
                    int a = Integer.valueOf(VIEWS1) + 1;
                    VIEWS = String.valueOf(a);
                    zan_SpecialService.save(pd);
                } else {
                    VIEWS = post_SpecialService.findById(pd).getString("VIEWS");
                }
                STATUS1 = "1";
            } else {
                PageData pd1 = post_SpecialService.findById(pd);
                String VIEWS1 = pd1.getString("VIEWS");
                pd1.put("VIEWS", Integer.valueOf(pd1.getString("VIEWS")) - 1);
                int a = Integer.valueOf(VIEWS1) - 1;
                VIEWS = String.valueOf(a);
                if (zan_SpecialService.findById(pd) != null) {
                    post_SpecialService.editViews(pd1);
                }
                zan_SpecialService.delete(pd);
                STATUS1 = "0";
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("VIEWS", VIEWS);
            pd.put("STATUS", STATUS1);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("message", "程序出错,请联系管理员!");
            pd.put("code", "2");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // =============================查询专题===================================
    @RequestMapping(value = "findSpecialId2", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findSpecialId2(String FID, String pageNum, String USER_ID) {
        logBefore(logger, "查询专题详情");
        PageData pd = new PageData();
        pd = this.getPageData();
        Page page1 = new Page();
        Page page2 = new Page();
        try {
            if (pageNum == null || pageNum.length() == 0) {
                pageNum = "1";
            }
            /*
             * page1.setPd(pd); page1.setShowCount(100);
             * page1.setCurrentPage(Integer.parseInt(pageNum));
             */
            List<PageData> list = postService.findZhuanTi(pd);
            for (int i = 0; i < list.size(); i++) {
                list.get(i).put("TIME", list.get(i).getString("DATE"));
                String YUE[] = list.get(i).getString("YUE").split("-");
                list.get(i).put("YUE", YUE[0]);
                list.get(i).put("RI", YUE[1]);
                list.get(i).put("VIEWS", zan_PostService.findCount(pd).get("count").toString());
                /*
                 * List<PageData> list3=post_ImgService.findList(list.get(i));
                 */
                List<PageData> list3 = post_ImgService.findList(list.get(i));
                list.get(i).put("POST_IMG", list3);
                String str = DateUtil.delHTMLTag(list.get(i).getString("MESSAGE"));
                String DETAILS1 = str.replace("\r", "");
                String DETAILS2 = DETAILS1.replace("\n", "");
                String DETAILS3 = DETAILS2.replace("&nbsp;", "");
                int qian = 56;
                if (DETAILS3.length() < 56) {
                    list.get(i).put("MESSAGE", DETAILS3);
                } else {
                    str = DETAILS2.substring(0, qian);
                    list.get(i).put("MESSAGE", str + "...");
                }
                if (list.get(i).getString("IMG") == null) {
                    list.get(i).put("IMG", "http://obmmqs3o7.bkt.clouddn.com/dftheader.jpg");
                }
            }
            pd.put("STATUS", "0");
            page2.setPd(pd);
            page2.setShowCount(10);
            page2.setCurrentPage(Integer.parseInt(pageNum));
            List<PageData> list3 = comment_specialService.list(page2);
            for (int i = 0; i < list3.size(); i++) {
                if (list3.get(i).getString("PNAME") == null) {
                    list3.get(i).put("PNAME", "");
                }
                list3.get(i).put("USER_ID1", USER_ID);
                if (zanCommentSpecialService.findById(list3.get(i)) != null) {
                    list3.get(i).put("ZAN", "1");
                } else {
                    list3.get(i).put("ZAN", "0");
                }
                list3.get(i).put("YUE", list3.get(i).getString("DATE"));
                List<PageData> list1 = comment_SpecialImgService.findList(list3.get(i));
                list3.get(i).put("CIMG", list1);
                list3.get(i).put("STATUS", "1");
                List<PageData> list2 = comment_specialService.findList(list3.get(i));
                for (int f = 0; f < list2.size(); f++) {
                    if (list2.get(f).getString("PNAME") == null) {
                        list2.get(f).put("PNAME", "");
                    }
                }
                list3.get(i).put("COMMENT", list2);
            }
            List<PageData> list2 = postService.ZhuanTi(pd);
            PageData pd1 = post_SpecialService.findById(pd);
            if (zan_SpecialService.findById(pd) != null) {
                pd1.put("ZAN", "1");
            } else {
                pd1.put("ZAN", "0");
            }
            /*
             * PageData pd_co=new PageData(); pd_co.put("CENSUS_ID",
             * this.get32UUID()); pd_co.put("ID", FID); pd_co.put("CONTENT",
             * "查询专题("+pd1.getString("SPECIAL")+")"); pd_co.put("DATE",
             * DateUtil.getTime()); pd_co.put("USER_ID", USER_ID);
             * pd_co.put("STATUS", "0"); pd_co.put("STOP_DATE", "");
             * pd_co.put("YUE", DateUtil.getDay()); contentService.save(pd_co);
             */
            pd1.put("SHUZI", Integer.valueOf(pd1.getString("SHUZI")) + 1);
            post_SpecialService.editShuZi(pd1);
            pd.clear();
            if (pageNum.equals("1")) {
                pd.put("POST", list);
            } else {
                List<PageData> list4 = new ArrayList();
                pd.put("POST", list4);
            }
            if (page2.getCurrentPage() == Integer.parseInt(pageNum)) {
                pd.put("object", list3);
            } else {
                List<PageData> list4 = new ArrayList();
                pd.put("object", list4);
            }
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("TOP", list2);
            pd.put("SPECIAL", pd1);
            pd.put("URL", "http://m.nongjike.cn/NJK/static/jsp/special.html?FID=" + FID);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(pd);
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

    // ==============================查询专题详情===================================
    @RequestMapping(value = "findSpecialId", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findSpecialId(String FID, String pageNum, String USER_ID) {
        logBefore(logger, "查询专题详情");
        PageData pd = new PageData();
        pd = this.getPageData();
        Page page = new Page();
        try {
            if (pageNum == null || pageNum.length() == 0) {
                pageNum = "1";
            }
            page.setPd(pd);
            page.setShowCount(10);
            page.setCurrentPage(Integer.parseInt(pageNum));
            List<PageData> list = postService.ZhuanlistPage(page);
            for (int i = 0; i < list.size(); i++) {
                list.get(i).put("TIME", list.get(i).getString("DATE"));
                String YUE[] = list.get(i).getString("YUE").split("-");
                list.get(i).put("YUE", YUE[0]);
                list.get(i).put("RI", YUE[1]);
                list.get(i).put("VIEWS", list.get(i).get("VIEWS").toString());
                /*
                 * List<PageData> list3=post_ImgService.findList(list.get(i));
                 */
                List<PageData> list3 = post_ImgService.findList(list.get(i));
                list.get(i).put("POST_IMG", list3);
                String str = DateUtil.delHTMLTag(list.get(i).getString("MESSAGE"));
                String DETAILS1 = str.replace("\r", "");
                String DETAILS2 = DETAILS1.replace("\n", "");
                String DETAILS3 = DETAILS2.replace("&nbsp;", "");
                int qian = 56;
                if (DETAILS3.length() < 56) {
                    list.get(i).put("MESSAGE", DETAILS3);
                } else {
                    str = DETAILS2.substring(0, qian);
                    list.get(i).put("MESSAGE", str + "...");
                }
                if (list.get(i).getString("IMG") == null) {
                    list.get(i).put("IMG", "http://obmmqs3o7.bkt.clouddn.com/dftheader.jpg");
                }
            }
            List<PageData> list2 = postService.ZhuanTi(pd);
            PageData pd1 = post_SpecialService.findById(pd);
            if (zan_SpecialService.findById(pd) != null) {
                pd1.put("ZAN", "1");
            } else {
                pd1.put("ZAN", "0");
            }
            /*
             * PageData pd_co=new PageData(); pd_co.put("CENSUS_ID",
             * this.get32UUID()); pd_co.put("ID", FID); pd_co.put("CONTENT",
             * "查询专题("+pd1.getString("SPECIAL")+")"); pd_co.put("DATE",
             * DateUtil.getTime()); pd_co.put("USER_ID", USER_ID);
             * pd_co.put("STATUS", "0"); pd_co.put("STOP_DATE", "");
             * pd_co.put("YUE", DateUtil.getDay()); contentService.save(pd_co);
             */
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            if (page.getCurrentPage() == Integer.parseInt(pageNum)) {
                pd.put("POST", list);
            } else {
                List<PageData> list4 = new ArrayList();
                pd.put("POST", list4);
            }
            pd.put("TOP", list2);
            pd.put("SPECIAL", pd1);
            pd.put("URL", "http://m.nongjike.cn/NJK/static/jsp/special.html?FID=" + FID);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(pd);
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

    // ==============================查询专题评论==================================
    @RequestMapping(value = "findCommentSpecial", produces = "text/hml;charset=UTF-8")
    @ResponseBody
    public String findCommentSpecial(String FID, String pageNum, String USER_ID) {
        logBefore(logger, "查询专题评论");
        PageData pd = new PageData();
        pd = this.getPageData();
        Page page = new Page();
        String message = "正确返回数据!";
        try {
            if (pageNum == null || pageNum.length() == 0) {
                pageNum = "1";
            }
            pd.put("STATUS", "0");
            page.setPd(pd);
            page.setShowCount(10);
            page.setCurrentPage(Integer.parseInt(pageNum));
            List<PageData> list = comment_specialService.list(page);
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getString("PNAME") == null) {
                    list.get(i).put("PNAME", "");
                }
                list.get(i).put("USER_ID1", USER_ID);
                if (zanCommentSpecialService.findById(list.get(i)) != null) {
                    list.get(i).put("ZAN", "1");
                } else {
                    list.get(i).put("ZAN", "0");
                }
                list.get(i).put("YUE", list.get(i).getString("DATE"));
                List<PageData> list1 = comment_SpecialImgService.findList(list.get(i));
                list.get(i).put("CIMG", list1);
                list.get(i).put("STATUS", "1");
                List<PageData> list2 = comment_specialService.findList(list.get(i));
                for (int f = 0; f < list2.size(); f++) {
                    if (list2.get(f).getString("PNAME") == null) {
                        list2.get(f).put("PNAME", "");
                    }
                }
                list.get(i).put("COMMENT", list2);
                list.get(i).put("VIEWS", zanCommentSpecialService.findCount(list.get(i)).get("count1").toString());
            }
            Map<String, Object> map = new HashedMap();
            if (page.getCurrentPage() == Integer.parseInt(pageNum)) {
                map.put("object", list);
            } else {
                List<PageData> list4 = new ArrayList();
                message = "已加载全部数据!";
                map.put("object", list4);
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("message", message);
            pd.put("data", map);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(pd);
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

    // ==============================关注专题取消关注================================
    @RequestMapping(value = "PostSpecial", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String PostSpecial(String STATUS, String USER_ID, String FID) {
        logBefore(logger, "关注专题取消关注");
        PageData pd = new PageData();
        pd = this.getPageData();

        try {
            if (STATUS.equals("0")) {
                if (collection_specialService.findById(pd) == null) {
                    pd.put("COLLECTION_SPECIAL_ID", this.get32UUID());
                    pd.put("DATE", DateUtil.getTime());
                    pd.put("STATUS", "0");
                    collection_specialService.save(pd);
                }
            } else {
                collection_specialService.delete(pd);
            }
            pd.clear();
            pd.put("message", "正确返回数据!");
            pd.put("code", "1");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(pd);
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

    // ==============================查询专题===================================
    @RequestMapping(value = "findPostSpecial", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findPostSpecial(String USER_ID) {
        logBefore(logger, "查询帖子专题");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            List<PageData> list = post_SpecialService.findNotIn(pd);
            List<PageData> list1 = post_SpecialService.findlistIn(pd);
            for (int i = 0; i < list1.size(); i++) {
                list1.get(i).put("STATUS", "0");
            }
            for (int i = 0; i < list.size(); i++) {
                list.get(i).put("STATUS", "1");
            }

            pd.clear();
            pd.put("TOP", list1);
            pd.put("WEIGUAN", list);
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(pd);
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

    // ==============================查询订单详情================================
    @RequestMapping(value = "findOrderId", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findOrderId(String ORDER_INFO_ID) {
        logBefore(logger, "查询订单详情");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            PageData pd1 = order_infoService.findById(pd);
            pd.clear();
            pd.put("object", pd1);
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(pd);
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

    // ==============================查询订单列表=================================
    @RequestMapping(value = "findOrderInfo", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findOrderInfo(String USER_ID, String pageNum) {
        logBefore(logger, "查询订单列表");
        PageData pd = new PageData();
        pd = this.getPageData();
        Page page = new Page();
        String message = "正确返回数据!";
        try {
            if (pageNum == null || pageNum.length() == 0) {
                pageNum = "1";
            }
            pd.put("YONGHU", "YONGHU");
            page.setPd(pd);
            page.setShowCount(10);
            page.setCurrentPage(Integer.parseInt(pageNum));
            List<PageData> list = order_infoService.list(page);
            PageData pd1 = ziXunService.findById(pd);
            for (int i = 0; i < list.size(); i++) {
                List<PageData> list2 = order_ProService.list1(list.get(i));
                list.get(i).put("DETAILS", list2);
                list.get(i).put("ZIXUN", pd1.getString("ZIXUN"));
            }
            Map<String, Object> map = new HashedMap();
            if (page.getCurrentPage() == Integer.parseInt(pageNum)) {
                map.put("object", list);
            } else {
                List<PageData> list4 = new ArrayList();
                message = "已加载全部数据!";
                map.put("object", list4);
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("message", message);
            pd.put("data", map);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(pd);
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


    // ==============================添加订单====================================

    /**
     * @param
     * @param ADDRESS_ID 地址ID
     * @param BEIZHU     备注
     * @param PAY_METHOD 支付方式
     * @return
     */
    @RequestMapping(value = "saveOrderInfos", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public synchronized String saveOrderInfos(String USER_ID, String ADDRESS_ID, String BEIZHU, String PAY_METHOD, String COUPON_ID) {
        logBefore(logger, "添加订单");
        PageData pd = new PageData();
        pd = this.getPageData();
        double money = 0;
        double PAY_MONEY = 0;
        DecimalFormat df = new DecimalFormat("#0.00");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ObjectMapper mapper = new ObjectMapper();
        try {
            PageData pd_ou = order_infoService.findOrderUserId(pd);
            long interval = 11;
            if (pd_ou != null) {
                Date date = format.parse(pd_ou.getString("ORDER_DATE"));
                Date date2 = format.parse(DateUtil.getTime());
                interval = (date2.getTime() - date.getTime()) / 1000;
            }
            if (interval < 10) {
                pd.clear();
                pd.put("code", "2");
                pd.put("message", "提交订单失败,请稍后重试!");
                return mapper.writeValueAsString(pd);
            }
            List<PageData> list2 = cartService.findListStatus(pd);
            for (int i = 0; i < list2.size(); i++) {
                List<PageData> listr = priceService.findList(list2.get(i));
                for (int j = 0; j < listr.size(); j++) {
                    if (Integer.valueOf(list2.get(i).getString("NUM")) >= Integer.valueOf(listr.get(j).getString("MIN"))
                            && Integer.valueOf(list2.get(i).getString("NUM")) <= Integer
                            .valueOf(listr.get(j).getString("MAX"))) {
                        money += Double.valueOf(list2.get(i).getString("NUM"))
                                * Double.valueOf(listr.get(j).getString("PRICE"));
                    }
                }

            }
            PAY_MONEY = money;
            PageData pd_co = couponService.findById(pd);
            if (pd_co != null) {
                money = money - Double.parseDouble(pd_co.getString("JIAN"));
            }
            PageData pd_a = new PageData();
            if (!"".equals(ADDRESS_ID)) {
                pd_a = addressService.findById(pd); // 获取默认地址
            } else {
                pd_a = addressService.findDefault(pd); // 获取默认地址
            }
            Date now = new Date();
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            String s = String.valueOf((int) ((Math.random() * 9 + 1) * 100));
            String no = s + sdf1.format(now).substring(2); // 订单号
            PageData od = new PageData();
            String ORDER_INFO_ID = this.get32UUID();
            od.put("ORDER_INFO_ID", ORDER_INFO_ID); // ID
            od.put("ORDER_NUMBER", no); // 订单号
            od.put("ORDER_DATE", DateUtil.getTime()); // 订单时间
            od.put("PAY_MONEY", df.format(PAY_MONEY)); // 金额
            od.put("MONEY", df.format(money)); // 实付金额
            od.put("PAY_METHOD", PAY_METHOD);// 支付方式
            od.put("PAY_DATE", "");// 支付时间
            od.put("ADDRESS", pd_a.getString("ADDRESS"));// 地址
            od.put("USER_ID", USER_ID);// 用户ID
            od.put("PHONE", pd_a.getString("PHONE"));// 电话
            od.put("NAME", pd_a.getString("NAME"));// 姓名
            od.put("END_DATE", ""); // 订单结束
            od.put("STATUS", "0");// 状态
            od.put("PROVINCE", pd_a.getString("PROVINCE"));// 省
            od.put("CITY", pd_a.getString("CITY"));// 市
            od.put("BEIZHU", BEIZHU);
            Calendar calendar = Calendar.getInstance();
            // 获得当前时间的月份，月份从0开始所以结果要加1
            int month = calendar.get(Calendar.MONTH) + 1;
            od.put("YUE", month);// 月份
            od.put("NIAN1", DateUtil.getYear());// 年
            od.put("TIAN", DateUtil.getDay());// 天
            od.put("CSTATUS", "0"); // 是否处理
            od.put("COUPON_ID", COUPON_ID);
            List<PageData> list = cartService.findListStatus(pd);
            if (null != list && list.size() > 0) {
                order_infoService.save(od);
                for (int i = 0; i < list.size(); i++) {
                    PageData md = new PageData();
                    md.put("ORDER_PRO_ID", this.get32UUID());
                    md.put("ORDER_INFO_ID", ORDER_INFO_ID);
                    md.put("PRO_NAME", list.get(i).getString("PRODUCT_NAME"));
                    md.put("REMARK", list.get(i).getString("REMARK"));
                    md.put("PRICE", list.get(i).getString("PRICE"));
                    md.put("NUM", list.get(i).getString("NUM"));
                    md.put("URL", list.get(i).getString("IMG"));
                    md.put("PRODUCT_ID", list.get(i).getString("PRODUCT_ID"));
                    order_ProService.save(md);
                }
            }

            if (PAY_METHOD.equals("xx")) {
                PageData pd3 = new PageData();
                pd3.put("USER_ID", USER_ID);
                pd3.put("STATUS", "1");
                cartService.deleteStatus(pd3);
            }
            /* cartService.deleteStatus(pd); */
            Map<String, Object> map = new HashedMap();
            map.put("ORDER_NUMBER", no); // 订单号
            String monet = df.format(money);
            map.put("MONEY", monet);
            PageData pd_c = cartService.findCount(pd);
            PageData user = appuserService.findById(pd);
            String message = "用户\"" + user.getString("NAME") + "\"提交了一份订单，请及时处理。";
            pd.clear();
            pd.put("code", "1");
            pd.put("data", map);
            pd.put("message", "提交订单成功!");
            pd.put("COUNT", pd_c.get("count").toString());
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        String str = "";
        try {
            str = mapper.writeValueAsString(pd);
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


    // ==============================添加订单====================================

    /**
     * @param
     * @param ADDRESS_ID 地址ID
     * @param BEIZHU     备注
     * @param PAY_METHOD 支付方式
     * @return
     */
    @RequestMapping(value = "saveOrderInfo", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String saveOrderInfo(String USER_ID, String ADDRESS_ID, String BEIZHU, String PAY_METHOD) {
        logBefore(logger, "添加订单");
        PageData pd = new PageData();
        pd = this.getPageData();
        double money = 0;
        DecimalFormat df = new DecimalFormat("#0.00");
        try {
            List<PageData> list2 = cartService.findListStatus(pd);
            for (int i = 0; i < list2.size(); i++) {
                List<PageData> listr = priceService.findList(list2.get(i));
                for (int j = 0; j < listr.size(); j++) {
                    if (Integer.valueOf(list2.get(i).getString("NUM")) >= Integer.valueOf(listr.get(j).getString("MIN"))
                            && Integer.valueOf(list2.get(i).getString("NUM")) <= Integer
                            .valueOf(listr.get(j).getString("MAX"))) {
                        money += Double.valueOf(list2.get(i).getString("NUM"))
                                * Double.valueOf(listr.get(j).getString("PRICE"));
                    }
                }

            }
            PageData pd_a = new PageData();
            if (ADDRESS_ID != "") {
                pd_a = addressService.findById(pd); // 获取默认地址
            } else {
                pd_a = addressService.findDefault(pd); // 获取默认地址
            }
            Date now = new Date();
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            String s = String.valueOf((int) ((Math.random() * 9 + 1) * 100));
            String no = s + sdf1.format(now).substring(2); // 订单号
            PageData od = new PageData();
            String ORDER_INFO_ID = this.get32UUID();
            od.put("ORDER_INFO_ID", ORDER_INFO_ID); // ID
            od.put("ORDER_NUMBER", no); // 订单号
            od.put("ORDER_DATE", DateUtil.getTime()); // 订单时间
            od.put("PAY_MONEY", df.format(money)); // 金额
            od.put("MONEY", df.format(money)); // 实付金额
            od.put("PAY_METHOD", PAY_METHOD);// 支付方式
            od.put("PAY_DATE", "");// 支付时间
            od.put("ADDRESS", pd_a.getString("ADDRESS"));// 地址
            od.put("USER_ID", USER_ID);// 用户ID
            od.put("PHONE", pd_a.getString("PHONE"));// 电话
            od.put("NAME", pd_a.getString("NAME"));// 姓名
            od.put("END_DATE", ""); // 订单结束
            od.put("STATUS", "0");// 状态
            od.put("PROVINCE", pd_a.getString("PROVINCE"));// 省
            od.put("CITY", pd_a.getString("CITY"));// 市
            od.put("BEIZHU", BEIZHU);
            Calendar calendar = Calendar.getInstance();
            // 获得当前时间的月份，月份从0开始所以结果要加1
            int month = calendar.get(Calendar.MONTH) + 1;
            od.put("YUE", month);// 月份
            od.put("NIAN1", DateUtil.getYear());// 年
            od.put("TIAN", DateUtil.getDay());// 天
            od.put("CSTATUS", "0"); // 是否处理
            od.put("COUPON_ID", "0");
            order_infoService.save(od);
            List<PageData> list = cartService.findListStatus(pd);
            for (int i = 0; i < list.size(); i++) {
                PageData md = new PageData();
                md.put("ORDER_PRO_ID", this.get32UUID());
                md.put("ORDER_INFO_ID", ORDER_INFO_ID);
                md.put("PRO_NAME", list.get(i).getString("PRODUCT_NAME"));
                md.put("REMARK", list.get(i).getString("REMARK"));
                md.put("PRICE", list.get(i).getString("PRICE"));
                md.put("NUM", list.get(i).getString("NUM"));
                md.put("URL", list.get(i).getString("IMG"));
                md.put("PRODUCT_ID", list.get(i).getString("PRODUCT_ID"));
                order_ProService.save(md);
            }
            if (PAY_METHOD.equals("xx")) {
                PageData pd3 = new PageData();
                pd3.put("USER_ID", USER_ID);
                pd3.put("STATUS", "1");
                cartService.deleteStatus(pd3);
            }
            /* cartService.deleteStatus(pd); */
            Map<String, Object> map = new HashedMap();
            map.put("ORDER_NUMBER", no); // 订单号
            String monet = df.format(money);
            map.put("MONEY", monet);
            PageData pd_c = cartService.findCount(pd);
            PageData user = appuserService.findById(pd);
            String message = "用户\"" + user.getString("NAME") + "\"提交了一份订单，请及时处理。";
            pd.clear();
            pd.put("code", "1");
            pd.put("data", map);
            pd.put("message", "提交订单成功!");
            pd.put("COUNT", pd_c.get("count").toString());
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(pd);
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


    // ==============================提交订单====================================
    @RequestMapping(value = "SubmitOrderInfos", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String SubmitOrderInfos(String USER_ID, String COUPON_ID) {
        logBefore(logger, "提交订单");
        PageData pd = new PageData();
        pd = this.getPageData();
        DecimalFormat df = new DecimalFormat("#0.00");
        double money = 0;
        try {
            PageData pd_c = couponService.findById(pd);
            PageData pd_z = ziXunService.findById(pd);
            List<PageData> list = cartService.findListStatus(pd);
            for (int i = 0; i < list.size(); i++) {
                List<PageData> listr = priceService.findList(list.get(i));
                for (int j = 0; j < listr.size(); j++) {
                    if (Integer.valueOf(list.get(i).getString("NUM")) >= Integer.valueOf(listr.get(j).getString("MIN"))
                            && Integer.valueOf(list.get(i).getString("NUM")) <= Integer
                            .valueOf(listr.get(j).getString("MAX"))) {
                        money += Double.valueOf(list.get(i).getString("NUM"))
                                * Double.valueOf(listr.get(j).getString("PRICE"));
                    }
                }

            }
            if (pd_c != null) {
                money = money - Double.parseDouble(pd_c.getString("JIAN"));
            }
            PageData pd_a = addressService.findDefault(pd);
            if (pd_a == null) {
                pd_a = new PageData();
                pd_a.put("NAME", "");
                pd_a.put("ADDRESS", "");
                pd_a.put("PHONE", "");
                pd_a.put("ADDRESS_ID", "");
            }
            PageData pd_cc = couponService.findCount(pd);
            pd.clear();
            pd.put("data", list);
            pd.put("MONEY", Double.parseDouble(df.format(money)));
            pd.put("ADDTESS", pd_a);
            pd.put("ZIXUN", pd_z.getString("ZIXUN"));
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("count", pd_cc.get("count1").toString());
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(pd);
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

    // ==============================提交订单====================================
    @RequestMapping(value = "SubmitOrderInfo", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String SubmitOrderInfo(String USER_ID) {
        logBefore(logger, "提交订单");
        PageData pd = new PageData();
        pd = this.getPageData();
        DecimalFormat df = new DecimalFormat("#0.00");
        double money = 0;
        try {
            PageData pd_z = ziXunService.findById(pd);
            List<PageData> list = cartService.findListStatus(pd);
            for (int i = 0; i < list.size(); i++) {
                List<PageData> listr = priceService.findList(list.get(i));
                for (int j = 0; j < listr.size(); j++) {
                    if (Integer.valueOf(list.get(i).getString("NUM")) >= Integer.valueOf(listr.get(j).getString("MIN"))
                            && Integer.valueOf(list.get(i).getString("NUM")) <= Integer
                            .valueOf(listr.get(j).getString("MAX"))) {
                        money += Double.valueOf(list.get(i).getString("NUM"))
                                * Double.valueOf(listr.get(j).getString("PRICE"));
                    }
                }

            }
            PageData pd_a = addressService.findDefault(pd);
            if (pd_a == null) {
                pd_a = new PageData();
                pd_a.put("NAME", "");
                pd_a.put("ADDRESS", "");
                pd_a.put("PHONE", "");
                pd_a.put("ADDRESS_ID", "");
            }
            PageData pd_c = couponService.findCount(pd);
            pd.clear();
            pd.put("data", list);
            pd.put("MONEY", Double.parseDouble(df.format(money)));
            pd.put("ADDTESS", pd_a);
            pd.put("ZIXUN", pd_z.getString("ZIXUN"));
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("count", pd_c.get("count1").toString());
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(pd);
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

    // =============================修改默认地址=====================================
    @RequestMapping(value = "updateDefault", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String updateDefault(String USER_ID, String ADDRESS_ID) {
        logBefore(logger, "修改默认地址");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            addressService.editUser(pd);
            addressService.editId(pd);
            pd.clear();
            pd.put("message", "正确返回数据!");
            pd.put("code", "1");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(pd);
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

    // =============================删除地址==================================
    @RequestMapping(value = "deleteAddress", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String deleteAddress(String ADDRESS_ID) {
        logBefore(logger, "删除地址");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            addressService.delete(pd);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(pd);
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

    // =============================修改地址==================================

    /**
     * @param ADDRESS_ID
     * @param NAME
     * @param PHONE
     * @param ADDRESS
     * @param PROVINCE
     * @param CITY
     * @return
     */
    @RequestMapping(value = "editAddress", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String editAddress(String ADDRESS_ID, String NAME, String PHONE, String ADDRESS, String PROVINCE,
                              String CITY) {
        logBefore(logger, "修改地址");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            addressService.edit(pd);
            pd.put("EMAIL", "");
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(pd);
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

    // =============================查询地址===================================
    @RequestMapping(value = "findAddress", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findAddress(String USER_ID) {
        logBefore(logger, "查询地址");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            List<PageData> list = addressService.findList(pd);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("data", list);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(pd);
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

    // =============================添加地址=====================================

    /**
     * @param USER_ID  ID
     * @param NAME     姓名
     * @param PHONE    电话
     * @param ADDRESS  地址
     * @param PROVINCE 省
     * @param CITY     市
     * @return
     */
    @RequestMapping(value = "saveAddress", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String saveAddress(String USER_ID, String NAME, String PHONE, String ADDRESS, String PROVINCE, String CITY) {
        logBefore(logger, "添加地址");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            pd.put("ADDRESS_ID", this.get32UUID());
            PageData pd_a = addressService.findDefault(pd);
            if (pd_a != null) {
                pd.put("STATUS", "0");
            } else {
                pd.put("STATUS", "1");
            }
            addressService.save(pd);
            pd.put("EMAIL", "");
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(pd);
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

    // =============================修改或删除购物车===============================

    /**
     * @param flag    0:修改购物车数量1:删除购物车 2:清空购物车
     * @param USER_ID 用户ID
     * @param NUM     修改后的数量
     * @param CART_ID 购物车ID
     * @return
     */
    @RequestMapping(value = "EditCart", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String EditCart(String flag, String USER_ID, String NUM, String CART_ID, String PRODUCT_ID) {
        logBefore(logger, "修改或删除购物车");
        PageData pd = new PageData();
        String code = "1";
        String message = "";
        double money = 0.0;
        double PMONEY = 0.0;
        try {
            pd = this.getPageData();
            if (flag.equals("0")) {
                if (NUM.equals("0")) {
                    cartService.delete(pd);
                } else {
                    cartService.edit(pd);
                }
                message = "修改购物车成功";
            } else if (flag.equals("1")) {
                cartService.delete(pd);
                message = "删除购物车成功";
            } else {
                cartService.deleteAll(pd);
                message = "清除购物车成功";
            }
            List<PageData> list = cartService.findListStatus(pd);
            for (int i = 0; i < list.size(); i++) {
                List<PageData> listr = priceService.findList(list.get(i));
                for (int j = 0; j < listr.size(); j++) {
                    if (Integer.valueOf(list.get(i).getString("NUM")) >= Integer.valueOf(listr.get(j).getString("MIN"))
                            && Integer.valueOf(list.get(i).getString("NUM")) <= Integer
                            .valueOf(listr.get(j).getString("MAX"))) {
                        money += Double.valueOf(list.get(i).getString("NUM"))
                                * Double.valueOf(listr.get(j).getString("PRICE"));
                    }
                }

            }
            PageData pd_c = cartService.findById(pd);
            List<PageData> listr = priceService.findList(pd_c);
            for (int j = 0; j < listr.size(); j++) {
                if (Integer.valueOf(pd_c.getString("NUM")) >= Integer.valueOf(listr.get(j).getString("MIN"))
                        && Integer.valueOf(pd_c.getString("NUM")) <= Integer.valueOf(listr.get(j).getString("MAX"))) {
                    PMONEY += Double.valueOf(pd_c.getString("NUM")) * Double.valueOf(listr.get(j).getString("PRICE"));
                }
            }
            PageData pd_cr = cartService.findCount(pd);
            pd.clear();
            pd.put("code", code);
            pd.put("message", message);
            pd.put("MONEY", money);
            pd.put("PMONEY", PMONEY);
            pd.put("COUNT", pd_cr.get("count").toString());
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(pd);
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

    // ==============================单独删除购物车==================================
    @RequestMapping(value = "deleteCart", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String deleteCart(String CART_ID, String USER_ID) {
        logBefore(logger, "单独删除购物车");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            cartService.delete1(pd);
            PageData pd_cr = cartService.findCount(pd);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "删除购物车成功");
            pd.put("COUNT", pd_cr.get("count").toString());
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错请联系管理员");
        }
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(pd);
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

    // =============================添加购物车=====================================

    /**
     * @param USER_ID    用户ID
     * @param PRODUCT_ID 商品ID
     * @param REMARK_ID  规格ID
     * @param NUM        数量
     * @return
     */
    @RequestMapping(value = "saveCart", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String saveCart(String USER_ID, String PRODUCT_ID, String REMARK_ID, String NUM) {
        logBefore(logger, "添加购物车");
        PageData pd = new PageData();
        Integer int1 = Integer.valueOf(NUM);
        try {
            pd = this.getPageData();
            PageData pd_r = remarkService.findById(pd);
            if (Integer.valueOf(pd_r.getString("KUCUN")) >= int1) {
                if (!PRODUCT_ID.equals("a75d632e0f704cf99ae8e14d09d2582c")) {
                    PageData pd_ca = cartService.findRemarkId(pd);
                    if (pd_ca != null) {
                        if (int1 < Integer.valueOf(pd_r.getString("NUM"))) {
                            int1 = Integer.valueOf(pd_r.getString("NUM"));
                        }
                        PageData pro = productService.findById(pd);
                        if (!"1".equals(pro.get("HSTATUS").toString())) {
                            Integer int2 = Integer.valueOf(pd_ca.getString("NUM"));
                            Integer num3 = int1 + int2;
                            pd_ca.put("NUM", num3);
                        }
                        cartService.edit(pd_ca);
                    } else {
                        if (int1 < Integer.valueOf(pd_r.getString("NUM"))) {
                            pd.put("NUM", pd_r.getString("NUM"));
                        }
                        pd.put("CART_ID", this.get32UUID());
                        pd.put("STATUS", "1");
                        cartService.save(pd);
                    }
                    List<PageData> list = cartService.findList(pd);
                    pd.clear();
                    pd.put("COUNT", list.size());
                    pd.put("code", "1");
                    pd.put("message", "添加购物车成功!");
                } else {
                    PageData pd_user = user_wendaQuanxianService.findQByUserId(pd);
                    if (pd_user != null) {
                        if (pd_user.getString("PAYSTA").equals("0")) {
                            PageData pd_ca = cartService.findRemarkId(pd);
                            if (pd_ca != null) {
                                if (int1 < Integer.valueOf(pd_r.getString("NUM"))) {
                                    int1 = Integer.valueOf(pd_r.getString("NUM"));
                                }
                                PageData pro = productService.findById(pd);
                                if (!"1".equals(pro.get("HSTATUS").toString())) {
                                    Integer int2 = Integer.valueOf(pd_ca.getString("NUM"));
                                    Integer num3 = int1 + int2;
                                    pd_ca.put("NUM", num3);
                                }
                                cartService.edit(pd_ca);
                            } else {
                                if (int1 < Integer.valueOf(pd_r.getString("NUM"))) {
                                    pd.put("NUM", pd_r.getString("NUM"));
                                }
                                pd.put("CART_ID", this.get32UUID());
                                pd.put("STATUS", "1");
                                cartService.save(pd);
                            }
                            List<PageData> list = cartService.findList(pd);
                            pd.clear();
                            pd.put("COUNT", list.size());
                            pd.put("code", "1");
                            pd.put("message", "添加购物车成功!");
                        } else {
                            pd.clear();
                            pd.put("code", "2");
                            pd.put("message", "你已经购买过此商品!");
                        }
                    } else {
                        pd.clear();
                        pd.put("code", "2");
                        pd.put("message", "请更新新版本!");
                    }
                }
            } else {
                pd.clear();
                pd.put("code", "2");
                pd.put("message", "库存不足！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错请联系管理员");
        }
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(pd);
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

    // ==========================修改购物车状态====================================

    /**
     * @param CART_ID ID
     * @param STATUS  0是取消 1是选中
     * @return
     */
    @RequestMapping(value = "editCartStatus", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String editCartStatus(String CART_ID, String STATUS) {
        logBefore(logger, "修改购物车状态");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            cartService.editStatus(pd);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // ==========================查询购物车总价=============================

    /**
     * @param USER_ID ID
     * @param CART_ID 购物车ID
     * @param STATUS  0是取消 1是选中 3全部选中 4全部取消
     * @return
     */
    @RequestMapping(value = "findCartPrice", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findCartPrice(String USER_ID, String STATUS, String CART_ID) {
        logBefore(logger, "查询购物车总价");
        PageData pd = new PageData();
        pd = this.getPageData();
        String message = "";
        String QUANXUAN = "0";
        try {
            if (STATUS.equals("3")) {
                pd.put("STATUS", "1");
                cartService.editStatusAll(pd);
                message = "全部选中成功";
                QUANXUAN = "0";
            } else if (STATUS.equals("4")) {
                pd.put("STATUS", "0");
                cartService.editStatusAll(pd);
                message = "全部取消成功";
                QUANXUAN = "1";
            } else {
                cartService.editStatus(pd);
                message = "正确返回数据!";
                QUANXUAN = "2";
            }
            double money = 0;
            List<PageData> list = cartService.findListStatus(pd);
            for (int i = 0; i < list.size(); i++) {
                List<PageData> listr = priceService.findList(list.get(i));
                for (int j = 0; j < listr.size(); j++) {
                    if (Integer.valueOf(list.get(i).getString("NUM")) >= Integer.valueOf(listr.get(j).getString("MIN"))
                            && Integer.valueOf(list.get(i).getString("NUM")) <= Integer
                            .valueOf(listr.get(j).getString("MAX"))) {
                        money += Double.valueOf(list.get(i).getString("NUM"))
                                * Double.valueOf(listr.get(j).getString("PRICE"));
                        DecimalFormat df = new DecimalFormat("#0.00");
                        String aa = df.format(Double.valueOf(list.get(i).getString("NUM"))
                                * Double.valueOf(listr.get(j).getString("PRICE")));
                        list.get(i).put("PMONEY", aa);
                    }
                }
            }
            pd.clear();
            pd.put("MONEY", money);
            pd.put("code", "1");
            pd.put("message", message);
            pd.put("QUANXUAN", QUANXUAN);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // ==========================查询购物车=============================
    @RequestMapping(value = "findCart", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findCart(String USER_ID) {
        logBefore(logger, "查询购物车");
        PageData pd = new PageData();
        pd = this.getPageData();
        DecimalFormat df = new DecimalFormat("#0.00");
        try {
            double money = 0;
            List<PageData> list1 = cartService.findList(pd);
            List<PageData> list = cartService.findListStatus(pd);
            for (int i = 0; i < list1.size(); i++) {
                List<PageData> listr = priceService.findList(list1.get(i));
                if (list1.get(i).getString("STATUS").equals("1")) {
                    for (int j = 0; j < listr.size(); j++) {
                        money += Double.valueOf(list1.get(i).getString("NUM"))
                                * Double.valueOf(listr.get(j).getString("PRICE"));
                        list1.get(i).put("PRICE", listr.get(j).getString("PRICE"));

                        String aa = df.format(Double.valueOf(list1.get(i).getString("NUM"))
                                * Double.valueOf(listr.get(j).getString("PRICE")));
                        list1.get(i).put("PMONEY", aa);
                    }
                } else {
                    for (int j = 0; j < listr.size(); j++) {
                        list1.get(i).put("PRICE", listr.get(j).getString("PRICE"));
                        String aa = df.format(Double.valueOf(list1.get(i).getString("NUM"))
                                * Double.valueOf(listr.get(j).getString("PRICE")));
                        list1.get(i).put("PMONEY", aa);
                    }
                }
            }
            pd.clear();
            if (list1.size() == list.size()) {
                pd.put("QUANXUAN", "0");
            } else {
                pd.put("QUANXUAN", "1");
            }
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("data", list1);
            pd.put("money", Double.parseDouble(df.format(money)));
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // ==========================查询首页版图=============================
    @RequestMapping(value = "findBanner", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findBanner() {
        logBefore(logger, "查询首页版图");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            List<PageData> list = bannerService.listAll(pd);
            pd.clear();
            pd.put("message", "正确返回数据!");
            pd.put("data", list);
            pd.put("code", "1");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("message", "程序出错,请联系管理员!");
            pd.put("code", "2");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // ==========================回复评论=====================================
    @RequestMapping(value = "ReplyPost", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String ReplyPost(String USER_ID, String PID, String MESSAGE) {
        logBefore(logger, "回复评论");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            PageData pd_p = post_InfoService.findById(pd);
            pd.put("FORUM_POST_ID", this.get32UUID());
            pd.put("TID", pd_p.getString("TID"));
            pd.put("PID", PID);
            pd.put("NAME", pd_p.getString("NAME"));
            pd.put("USER_ID", pd_p.getString("USER_ID"));
            pd.put("DATE", this.get32UUID());
            PageData pd_u = appuserService.findById(pd);
            pd.put("RNAME", pd_u.getString("NAME"));
            post_forim_postcommentService.save(pd);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // ==========================发布帖子=======================================

    /**
     * @param
     * @param
     * @param
     * @param
     * @return
     */
    @RequestMapping(value = "savePost", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String savePost(String MESSAGE, String USER_ID, String SUBJECT, String IMG, String FID) {
        logBefore(logger, "发布帖子");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            PageData pd1 = appuserService.findById111(pd);
            String TID = this.get32UUID();
            pd.put("TID", TID);
            pd.put("FID", FID);
            pd.put("NAME", pd1.getString("NAME"));
            pd.put("DATE", DateUtil.getTime());
            pd.put("VIEWS", "0");
            pd.put("HUIFU", "0");
            pd.put("TSTATUS", "0");
            pd.put("PRODUCT_ID", "");
            pd.put("ESTATUS", "0");
            pd.put("MSTATUS", "0");
            pd.put("KEYWORD1", "");
            pd.put("KEYWORD2", "");
            pd.put("BROWSE", "0");
            pd.put("URL", "");
            pd.put("HUODONG", "0");
            pd.put("HUODONG1", "");
            pd.put("ACTIVITY_ID", "");
            pd.put("POST_SPECIAL_TYPE_ID", "");
            postService.save(pd);
            PageData pd2 = new PageData();
            pd2.put("PID", this.get32UUID());
            pd2.put("TID", TID);
            pd2.put("FID", "0");
            pd2.put("FIRST", "1");
            pd2.put("NAME", pd1.getString("NAME"));
            pd2.put("USER_ID", USER_ID);
            pd2.put("DATE", DateUtil.getTime());
            pd2.put("SUBJECT", SUBJECT);
            String MESSAGE1 = MESSAGE.replace("!==!", "<br/>");
            MESSAGE1 = MESSAGE1.replace("\n", "<br/>");
            String ffile1 = this.get32UUID() + ".jpg";
            String filePath2 = PathUtil.getClasspath() + Const.FILEPATHIMG + "tiezi/" + DateUtil.getDays(); // 文件上传路径
            File file = new File(filePath2, ffile1);
            if (!file.exists()) {
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
            }
            if (IMG != null && !IMG.equals("")) {
                String TOUR_IMG[] = IMG.split(",");
                for (int i = 0; i < TOUR_IMG.length; i++) {
                    String ffile = this.get32UUID() + ".jpg";
                    String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + "tiezi/" + DateUtil.getDays() + "/"
                            + ffile; // 文件上传路径
                    // boolean
                    // flag=ImageAnd64Binary.generateImage(TOUR_IMG[i],filePath);
                    String TOUR_IMG1 = Const.SERVERPATH + Const.FILEPATHIMG + "tiezi/" + DateUtil.getDays() + "/"
                            + ffile;
                    MESSAGE1 += "<br/><img src=" + TOUR_IMG1 + "/><br/>";
                    // Thread2 td=((Object) new Thread2()).start();
                    new Thread(new Thread2(TOUR_IMG[i], TOUR_IMG1, filePath, TID, ffile, post_ImgService, i)).start();
                    PageData pd_i = new PageData();
                    // CompressPicDemo demo=new CompressPicDemo();
                    // demo.compressPic(PathUtil.getClasspath() +
                    // Const.FILEPATHIMG, ffile, ffile, "r1"+ffile+".jpg", 120,
                    // 120, true);
                    pd_i.put("TID", TID);
                    pd_i.put("IMG", TOUR_IMG1);
                    post_ImgService.save(pd_i);
                }
            }
            pd2.put("MESSAGE", "<style>img{max-width:100%;}</style><p>" + MESSAGE1 + "</p>");
            pd2.put("YUE", DateUtil.getDay3());
            post_InfoService.save(pd2);
            pd2.clear();
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // ==========================发布帖子=======================================

    /**
     * @param
     * @param
     * @param
     * @param
     * @return
     */
    @RequestMapping(value = "savePost2", produces = "text/html;charset=UTF-8", method = {RequestMethod.POST})
    @ResponseBody
    public String savePost2(String IMG, String MESSAGE, String USER_ID, String SUBJECT, String img1, String img2,
                            String img3, String img4, String img5, String img6, String FID, String count) {
        logBefore(logger, "发布帖子");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            List<PageData> list = post_Special_TypeService.findList(pd);
            PageData pd1 = appuserService.findById111(pd);
            String TID = this.get32UUID();
            pd.put("TID", TID);
            pd.put("FID", FID);
            pd.put("NAME", pd1.getString("NAME"));
            pd.put("DATE", DateUtil.getTime());
            pd.put("VIEWS", "0");
            pd.put("HUIFU", "0");
            pd.put("TSTATUS", "0");
            pd.put("PRODUCT_ID", "");
            pd.put("ESTATUS", "0");
            pd.put("MSTATUS", "0");
            pd.put("KEYWORD1", "");
            pd.put("KEYWORD2", "");
            pd.put("BROWSE", "0");
            pd.put("URL", "");
            pd.put("HUODONG", "0");
            pd.put("HUODONG1", "");
            pd.put("ACTIVITY_ID", "");
            pd.put("POST_SPECIAL_TYPE_ID", list.get(0).getString("POST_SPECIAL_TYPE_ID"));
            postService.save(pd);
            PageData pd2 = new PageData();
            pd2.put("PID", this.get32UUID());
            pd2.put("TID", TID);
            pd2.put("FID", "0");
            pd2.put("FIRST", "1");
            pd2.put("NAME", pd1.getString("NAME"));
            pd2.put("USER_ID", USER_ID);
            pd2.put("DATE", DateUtil.getTime());
            pd2.put("SUBJECT", SUBJECT);
            String MESSAGE1 = MESSAGE.replace("!==!", "<br/>");
            MESSAGE1 = MESSAGE1.replace("\n", "<br/>");
            String ffile1 = this.get32UUID() + ".jpg";
            String filePath2 = PathUtil.getClasspath() + Const.FILEPATHIMG + "tiezi/" + DateUtil.getDays(); // 文件上传路径
            File file = new File(filePath2, ffile1);
            if (!file.exists()) {
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
            }
            Integer count1 = Integer.valueOf(count);
            for (int i = 0; i < count1; i++) {
                String ffile = this.get32UUID() + ".jpg";
                String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + "tiezi/" + DateUtil.getDays() + "/"
                        + ffile; // 文件上传路径
                String TOUR_IMG1 = Const.SERVERPATH + Const.FILEPATHIMG + "tiezi/" + DateUtil.getDays() + "/" + ffile;
                MESSAGE1 += "<br/><img src=" + TOUR_IMG1 + "/><br/>";
                String ii = String.valueOf(i + 1);
                pd.put("TID", TID);
                pd.put("IMG", TOUR_IMG1);
                pd.put("ORDE_BY", ii);
                post_ImgService.save(pd);
                new Thread(new Thread2(pd.getString("img" + ii), TOUR_IMG1, filePath, TID, ffile, post_ImgService, i))
                        .start();
            }
            pd2.put("MESSAGE", "<style>img{max-width:100%;}</style><p>" + MESSAGE1 + "</p>");
            pd2.put("YUE", DateUtil.getDay3());
            post_InfoService.save(pd2);
            pd2.clear();
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // ==========================收藏帖子取消收藏=====================================
    @RequestMapping(value = "CollectionPost", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String CollectionPost(String USER_ID, String TID, String STATUS) {
        logBefore(logger, "收藏帖子");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            if (STATUS.equals("0")) {
                pd.put("COLLECTION_POST_ID", this.get32UUID());
                pd.put("DATE", DateUtil.getTime());
                collection_postService.save(pd);
            } else {
                collection_postService.delete(pd);
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
        } catch (JsonGenerationException e) {

            e.printStackTrace();
        } catch (JsonMappingException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
        return str;
    }

    // ==========================查询全部列表===================================
    @RequestMapping(value = "findQuanBu", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findQuanBu(String pageNum) {
        logBefore(logger, "查询全部列表");
        PageData pd = new PageData();
        pd = this.getPageData();
        Page page = new Page();
        String message = "正确返回数据!";
        try {
            PageData pd_special = new PageData();
            pd_special.put("TSTATUS", "1");
            pd_special = post_SpecialService.findById(pd_special);
            if (pageNum == null || pageNum.length() == 0) {
                pageNum = "1";
            }
            pd.put("FID", "0");
            page.setPd(pd);
            page.setShowCount(5);
            page.setCurrentPage(Integer.parseInt(pageNum));
            List<PageData> list = postService.postlistPage(page);
            for (int i = 0; i < list.size(); i++) {
                list.get(i).put("TIME", list.get(i).getString("DATE"));
                String YUE[] = list.get(i).getString("YUE").split("-");
                list.get(i).put("YUE", YUE[0]);
                list.get(i).put("RI", YUE[1]);
                list.get(i).put("VIEWS", zan_PostService.findCount(list.get(i)).get("count").toString());
                list.get(i).put("HUIFU", comment_PostService.findCount(list.get(i)).get("count1").toString());
                List<PageData> list3 = post_ImgService.findList(list.get(i));
                list.get(i).put("POST_IMG", list3);
                if (list.get(i).getString("IMG") == null) {
                    list.get(i).put("IMG", "http://obmmqs3o7.bkt.clouddn.com/dftheader.jpg");
                }
                list.get(i).put("MESSAGE", "");
            }
            pd.clear();
            pd.put("TSTATUS", "1");
            List<PageData> list1 = postService.findList(pd);
            for (int i = 0; i < list1.size(); i++) {
                list1.get(i).put("VIEWS", list1.get(i).get("VIEWS").toString());
            }
            Map<String, Object> map = new HashedMap();
            if (page.getCurrentPage() == Integer.parseInt(pageNum)) {
                map.put("object", list);
            } else {
                List<PageData> list4 = new ArrayList();
                message = "已加载全部数据!";
                map.put("object", list4);
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("data", map);
            pd.put("TOP", list1);
            pd.put("SPCIAL", pd_special);
            pd.put("message", message);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
        } catch (JsonGenerationException e) {

            e.printStackTrace();
        } catch (JsonMappingException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
        return str;
    }

    // ==========================关键字搜素帖子列表================================

    /**
     * @param pageNum
     * @return
     */
    @RequestMapping(value = "SearchPost", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String SearchPost(String pageNum, String KEYWORD) {
        logBefore(logger, "关键字搜素帖子列表");
        PageData pd = new PageData();
        Page page = new Page();
        pd = this.getPageData();
        String message = "正确返回数据!";
        try {
            PageData pd1 = post_KeywordService.findName(pd);
            if (KEYWORD != "") {
                if (pd1 != null) {
                    String VIEWS1 = String.valueOf(pd1.get("VIEWS"));
                    pd1.put("VIEWS", Integer.valueOf(VIEWS1) + 1);
                    post_KeywordService.edit(pd1);
                } else {
                    pd1 = new PageData();
                    pd1.put("POST_KEYWORD_ID", this.get32UUID());
                    pd1.put("KEYWORD", KEYWORD);
                    pd1.put("VIEWS", "1");
                    post_KeywordService.save(pd1);
                }
            }
            if (pageNum == null || pageNum.length() == 0) {
                pageNum = "1";
            }
            page.setPd(pd);
            page.setShowCount(5);
            page.setCurrentPage(Integer.parseInt(pageNum));
            List<PageData> list = postService.KelistPage(page);
            for (int i = 0; i < list.size(); i++) {
                list.get(i).put("TIME", list.get(i).getString("DATE"));
                String YUE[] = list.get(i).getString("YUE").split("-");
                list.get(i).put("YUE", YUE[0]);
                list.get(i).put("RI", YUE[1]);
                list.get(i).put("VIEWS", zan_PostService.findCount(list.get(i)).get("count").toString());
                List<PageData> list3 = post_ImgService.findList(list.get(i));
                list.get(i).put("POST_IMG", list3);
                list.get(i).put("MESSAGE", "");
                if (list.get(i).getString("IMG") == null) {
                    list.get(i).put("IMG", "http://obmmqs3o7.bkt.clouddn.com/dftheader.jpg");
                }
            }
            Map<String, Object> map = new HashedMap();
            if (page.getCurrentPage() == Integer.parseInt(pageNum)) {
                map.put("object", list);
            } else {
                List<PageData> list3 = new ArrayList();
                map.put("object", list3);
                message = "已加载全部数据!";
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("data", map);
            pd.put("message", message);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // ==========================查询活动帖子列表===========================
    @RequestMapping(value = "findHuoPost", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findHuoPost(String pageNum) {
        logBefore(logger, "查询活动帖子列表");
        PageData pd = new PageData();
        pd = this.getPageData();
        Page page = new Page();
        String message = "正确返回数据!";
        try {
            if (pageNum == null || pageNum.length() == 0) {
                pageNum = "1";
            }
            page.setPd(pd);
            page.setShowCount(5);
            page.setCurrentPage(Integer.parseInt(pageNum));
            List<PageData> list = postService.ReelistPage(page);
            for (int i = 0; i < list.size(); i++) {
                list.get(i).put("TIME", list.get(i).getString("DATE"));
                String YUE[] = list.get(i).getString("YUE").split("-");
                list.get(i).put("YUE", YUE[0]);
                list.get(i).put("RI", YUE[1]);
                list.get(i).put("VIEWS", list.get(i).get("VIEWS").toString());
                /*
                 * List<PageData> list3=post_ImgService.findList(list.get(i));
                 */
                List<PageData> list3 = post_ImgService.findList(list.get(i));
                list.get(i).put("POST_IMG", list3);
                String str = DateUtil.delHTMLTag(list.get(i).getString("MESSAGE"));
                String DETAILS1 = str.replace("\r", "");
                String DETAILS2 = DETAILS1.replace("\n", "");
                String DETAILS3 = DETAILS2.replace("&nbsp;", "");
                int qian = 56;
                if (DETAILS3.length() < 56) {
                    list.get(i).put("MESSAGE", DETAILS3);
                } else {
                    str = DETAILS3.substring(0, qian);
                    list.get(i).put("MESSAGE", str + "...");
                }
                if (list.get(i).getString("IMG") == null) {
                    list.get(i).put("IMG", "http://obmmqs3o7.bkt.clouddn.com/dftheader.jpg");
                }
            }
            Map<String, Object> map = new HashedMap();
            if (page.getCurrentPage() == Integer.parseInt(pageNum)) {
                map.put("object", list);
            } else {
                List<PageData> list4 = new ArrayList();
                message = "已加载全部数据!";
                map.put("object", list4);
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("data", map);
            pd.put("message", message);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // ==========================帖子列表================================

    /**
     * @param pageNum
     * @param STATUS  0是精华1是热门帖子
     * @return
     */
    @RequestMapping(value = "findPostList", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findPostList(String pageNum, String STATUS) {
        logBefore(logger, "查询帖子列表");
        PageData pd = new PageData();
        Page page = new Page();
        pd = this.getPageData();
        String message = "正确返回数据!";
        try {
            if (pageNum == null || pageNum.length() == 0) {
                pageNum = "1";
            }
            page.setPd(pd);
            page.setShowCount(5);
            page.setCurrentPage(Integer.parseInt(pageNum));
            List<PageData> list = new ArrayList();
            if (STATUS.equals("1")) {
                list = postService.RelistPage(page);
            } else {
                list = postService.postlistPage(page);
            }
            for (int i = 0; i < list.size(); i++) {
                list.get(i).put("TIME", list.get(i).getString("DATE"));
                String YUE[] = list.get(i).getString("YUE").split("-");
                list.get(i).put("YUE", YUE[0]);
                list.get(i).put("RI", YUE[1]);
                list.get(i).put("VIEWS", zan_PostService.findCount(list.get(i)).get("count").toString());
                list.get(i).put("HUIFU", comment_PostService.findCount(list.get(i)).get("count1").toString());
                List<PageData> list3 = post_ImgService.findList(list.get(i));
                list.get(i).put("POST_IMG", list3);
                String str = DateUtil.delHTMLTag(list.get(i).getString("MESSAGE"));
                String DETAILS1 = str.replace("\r", "");
                String DETAILS2 = DETAILS1.replace("\n", "");
                String DETAILS3 = DETAILS2.replace("&nbsp;", "");
                int qian = 0;
                if (list3 != null && list3.size() != 0) {
                    qian = 20;
                } else {
                    qian = 50;
                }
                if (DETAILS3.length() < 56) {
                    list.get(i).put("MESSAGE", DETAILS3);
                } else {
                    str = DETAILS3.substring(0, qian);
                    list.get(i).put("MESSAGE", str + "...");
                }
            }
            Map<String, Object> map = new HashedMap();
            if (page.getCurrentPage() == Integer.parseInt(pageNum)) {
                map.put("object", list);
                if (list.size() < 5) {
                    message = "已加载全部数据!";
                }
            } else {
                List<PageData> list4 = new ArrayList();
                message = "已加载全部数据!";
                map.put("object", list4);
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("data", map);
            pd.put("message", message);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // ==========================点赞帖子和取消点赞===================================
    @RequestMapping(value = "ZanPost", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String ZanPost(String STATUS, String TID, String USER_ID) {
        logBefore(logger, "点赞帖子和取消点赞");
        PageData pd = new PageData();
        pd = this.getPageData();
        String VIEWS = "";
        try {
            zanPost zanpost = new zanPost(TID, STATUS, USER_ID, zan_PostService);
            zanpost.ZanPost();
            VIEWS = zan_PostService.findCount(pd).get("count").toString();
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("VIEWS", VIEWS);
            pd.put("STATUS", STATUS);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("message", "程序出错,请联系管理员!");
            pd.put("code", "2");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // ==========================查询帖子详情=================================
    @RequestMapping(value = "findPost", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findPost(String TID, String USER_ID, String pageNum) {
        logBefore(logger, "查询帖子详情");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            PageData pd1 = postService.findById(pd);
            // pd1.put("URL",
            // "http://101.200.130.60:8080/NJK/static/jsp/post.html?tid="+TID);
            pd1.put("URL", "http://m.nongjike.cn/NJK/static/jsp/111.html?tid=" + TID);
            if (zan_PostService.findById(pd) != null) {
                pd1.put("ZAN", "1");
            } else {
                pd1.put("ZAN", "0");
            }
            pd1.put("HUIFU", comment_PostService.findCount1(pd1).get("count1").toString());
            List<PageData> list1 = post_ImgService.findList(pd);
            pd1.put("PIMG", list1);
            if (pd1.getString("FIMG") == null || pd1.getString("FIMG").equals("")) {
                if (list1.size() != 0) {
                    pd1.put("FIMG", list1.get(0).getString("IMG"));
                } else {
                    pd1.put("FIMG", "");
                }

            }
            String str = DateUtil.delHTMLTag(pd1.getString("MESSAGE"));
            String DETAILS1 = str.replace("\r", "");
            String DETAILS2 = DETAILS1.replace("\n", "");
            String DETAILS3 = DETAILS2.replace("&nbsp;", "");
            int qian = 20;
            if (DETAILS3.length() < 20) {
                pd1.put("JIANJIE", DETAILS3);
            } else {
                str = DETAILS2.substring(0, qian);
                pd1.put("JIANJIE", str + "...");
            }
            if (collection_postService.findCollection(pd) != null) {
                pd1.put("COLLECTION", "1");
            } else {
                pd1.put("COLLECTION", "0");
            }
            if (pd1.getString("IMG") == null) {
                pd1.put("IMG", "http://obmmqs3o7.bkt.clouddn.com/dftheader.jpg");
            }
            pd1.put("VIEWS", zan_PostService.findCount(pd).get("count").toString());
            pd1.put("SHUZI", Integer.valueOf(pd1.getString("SHUZI")) + 1);
            pd.put("FIRST", "0");
            postService.editShuZi(pd1);
            new Thread(new History(pd1.getString("SUBJECT"), TID, "3", "帖子", USER_ID, "3", historyService)).start();
            pd.clear();
            pd.put("message", "正确返回数据!");
            pd.put("code", "1");
            pd.put("POST", pd1);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("message", "程序出错,请联系管理员!");
            pd.put("code", "2");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // ==========================回复帖子评论========================================
    @RequestMapping(value = "CommentPostHui", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String CommentPostHui(String USER_ID, String TID, String MESSAGE, String PUSER_ID, String COMMENT_POST_ID,
                                 String STATUS) {
        logBefore(logger, "回复评论");
        PageData pd = new PageData();
        pd = this.getPageData();
        String inform = "";
        try {
            pd.put("COMMENT_POST_ID", this.get32UUID());
            pd.put("NAME", "");
            pd.put("PNAME", "");
            pd.put("DATE", DateUtil.getTime());
            pd.put("STATUS", "1");
            pd.put("VIEWS", "0");
            pd.put("YUE", DateUtil.getDay());
            pd.put("PID", COMMENT_POST_ID);
            comment_PostService.save(pd);
            //*****************增加用户积分*****************
            if (MESSAGE.length() >= 15) {
                String today = DateUtil.getDay();
                PageData comDto = new PageData();
                comDto.put("community_type", "1");
                comDto.put("create_date", today);
                comDto.put("USER_ID", USER_ID);
                pd.put("JIFEN", 1);
                appuserService.editJifen(pd);
                appuserService.addZJIFEN(pd);
                comDto.put("community_id", TID);
                comDto.put("NUM", "1");
                comDto.put("MIAOSHU", "回复评论");
                comDto.put("ORDER_NUM", "");
                countCommunityService.save(comDto);
                inform = "回复完成，获取有效积分+1";
            }
            //*****************end******************

            new Thread(new Thread11(USER_ID, TID, MESSAGE, COMMENT_POST_ID, comment_PostService, newsService,
                    appuserService)).start();
            pd.clear();
            pd.put("code", "1");
            pd.put("inform ", inform);
            pd.put("message", "正确返回数据!");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("message", "程序出错,请联系管理员!");
            pd.put("code", "2");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // ===============================回复帖子评论==================================
    @RequestMapping(value = "CommentPostHui2", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String CommentPostHui2(String USER_ID, String TID, String MESSAGE, String PUSER_ID, String PID,
                                  String STATUS) {
        logBefore(logger, "回复评论");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            pd.put("COMMENT_POST_ID", this.get32UUID());
            pd.put("NAME", "");
            pd.put("PNAME", "");
            pd.put("DATE", DateUtil.getTime());
            pd.put("STATUS", "1");
            pd.put("VIEWS", "0");
            pd.put("YUE", DateUtil.getDay());
            pd.put("PID", PID);
            comment_PostService.save(pd);
            new Thread(new Thread1(USER_ID, TID, MESSAGE, PUSER_ID, postService, appuserService, newsService)).start();
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("message", "程序出错,请联系管理员!");
            pd.put("code", "2");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // ==========================评论帖子=========================================
    @RequestMapping(value = "CommentPost", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String CommentPost(String USER_ID, String MESSAGE, String TID, String IMG, String STATUS) {
        logBefore(logger, "评论帖子");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            String COMMENT_POST_ID = this.get32UUID();
            pd.put("COMMENT_POST_ID", COMMENT_POST_ID);
            pd.put("NAME", "");
            pd.put("PUSER_ID", "");
            pd.put("PNAME", "");
            pd.put("DATE", DateUtil.getTime());
            pd.put("STATUS", "0");
            pd.put("VIEWS", "0");
            pd.put("YUE", DateUtil.getDay());
            pd.put("PID", "");
            comment_PostService.save(pd);
            if (IMG != "") {
                String TOUR_IMG[] = IMG.split(",");
                for (int i = 0; i < TOUR_IMG.length; i++) {
                    String ffile = this.get32UUID() + ".jpg";
                    String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + ffile; // 文件上传路径
                    boolean flag = ImageAnd64Binary.generateImage(TOUR_IMG[i], filePath);
                    String TOUR_IMG1 = Const.SERVERPATH + Const.FILEPATHIMG + ffile;
                    PageData pd1 = new PageData();
                    pd1.put("COMMENT_POST_ID", COMMENT_POST_ID);
                    pd1.put("IMG", TOUR_IMG1);
                    pd1.put("DATE", DateUtil.getTime());
                    comment_PostImgService.save(pd1);
                }
            }
            new Thread(new Thread111(TID, MESSAGE, USER_ID, postService, appuserService, newsService)).start();
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("message", "程序出错,请联系管理员!");
            pd.put("code", "2");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // ==========================评论帖子=========================================
    @RequestMapping(value = "CommentPost2", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String CommentPost2(String USER_ID, String MESSAGE, String TID, String img1, String img2, String img3,
                               String count, String STATUS) {
        logBefore(logger, "评论帖子");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            String COMMENT_POST_ID = this.get32UUID();
            pd.put("COMMENT_POST_ID", COMMENT_POST_ID);
            pd.put("NAME", "");
            pd.put("PUSER_ID", "");
            pd.put("PNAME", "");
            pd.put("DATE", DateUtil.getTime());
            pd.put("STATUS", "0");
            pd.put("VIEWS", "0");
            pd.put("YUE", DateUtil.getDay());
            pd.put("PID", "");
            comment_PostService.save(pd);
            Integer count1 = Integer.valueOf(count);
            String ffile1 = this.get32UUID() + ".jpg";
            String filePath2 = PathUtil.getClasspath() + Const.FILEPATHIMG + "pinglun/" + DateUtil.getDays(); // 文件上传路径
            File file = new File(filePath2, ffile1);
            if (!file.exists()) {
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
            }
            for (int i = 0; i < count1; i++) {
                String ffile = this.get32UUID() + ".jpg";
                String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + "pinglun/" + DateUtil.getDays() + "/"
                        + ffile; // 文件上传路径
                String TOUR_IMG1 = Const.SERVERPATH + Const.FILEPATHIMG + "pinglun/" + DateUtil.getDays() + "/" + ffile;
                String ii = String.valueOf(i + 1);
                PageData pd1 = new PageData();
                pd1.put("COMMENT_POST_ID", COMMENT_POST_ID);
                pd1.put("IMG", TOUR_IMG1);
                pd1.put("DATE", DateUtil.getTime());
                comment_PostImgService.save(pd1);
                new Thread(new Thread2(pd.getString("img" + ii), TOUR_IMG1, filePath, TOUR_IMG1, ffile, post_ImgService,
                        i)).start();
            }
            new Thread(new Thread111(TID, MESSAGE, USER_ID, postService, appuserService, newsService)).start();
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("message", "程序出错,请联系管理员!");
            pd.put("code", "2");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // ==========================点赞帖子评论和取消点赞评论===================================
    @RequestMapping(value = "ZanCommentPost", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String ZanCommentPost(String STATUS, String COMMENT_POST_ID, String USER_ID) {
        logBefore(logger, "点赞帖子评论和取消点赞评论");
        PageData pd = new PageData();
        pd = this.getPageData();
        String VIEWS = "";
        String STATUS1 = "";
        try {
            if (STATUS.equals("1")) {
                if (zan_CommentPostService.findById1(pd) == null) {
                    zan_CommentPostService.save(pd);
                }
                STATUS1 = "1";
            } else {
                zan_CommentPostService.delete(pd);
                STATUS1 = "0";
            }
            VIEWS = zan_CommentPostService.findcount(pd).get("count1").toString();
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("VIEWS", VIEWS);
            pd.put("STATUS", STATUS1);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("message", "程序出错,请联系管理员!");
            pd.put("code", "2");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // ==========================查询帖子评论=====================================
    @RequestMapping(value = "findCommentPost", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findCommentPost(String TID, String pageNum, String USER_ID) {
        logBefore(logger, "查询帖子评论");
        PageData pd = new PageData();
        pd = this.getPageData();
        Page page = new Page();
        String message = "正确返回数据!";
        try {
            if (pageNum == null || pageNum.length() == 0) {
                pageNum = "1";
            }
            pd.put("STATUS", "0");
            page.setPd(pd);
            page.setShowCount(5);
            page.setCurrentPage(Integer.parseInt(pageNum));
            List<PageData> list = comment_PostService.list(page);
            for (int i = 0; i < list.size(); i++) {
                list.get(i).put("USER_ID1", USER_ID);
                if (zan_CommentPostService.findById(list.get(i)) != null) {
                    list.get(i).put("ZAN", "1");
                } else {
                    list.get(i).put("ZAN", "0");
                }
                list.get(i).put("YUE", list.get(i).getString("DATE"));
                List<PageData> list1 = comment_PostImgService.findList(list.get(i));
                list.get(i).put("CIMG", list1);
                list.get(i).put("STATUS", "1");
                List<PageData> list2 = comment_PostService.findList(list.get(i));
                for (int f = 0; f < list2.size(); f++) {
                    if (list2.get(f).getString("PNAME") == null) {
                        list2.get(f).put("PNAME", "");
                    }
                }
                list.get(i).put("COMMENT", list2);
                list.get(i).put("VIEWS", zan_CommentPostService.findcount(list.get(i)).get("count1").toString());
            }
            Map<String, Object> map = new HashedMap();
            if (page.getCurrentPage() == Integer.parseInt(pageNum)) {
                map.put("object", list);
            } else {
                List<PageData> list3 = new ArrayList();
                map.put("object", list3);
                message = "已加载全部数据!";
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("message", message);
            pd.put("data", map);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        String str = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(pd);
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

    // ==========================查询商品轮播图=================================
    @RequestMapping(value = "findProductImg", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findProductImg(String PRODUCT_ID) {
        logBefore(logger, "查询商品轮播图");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            List<PageData> list = product_imgService.findList(pd);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("data", list);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // ==========================查询app活动=================================

    /**
     * 登录时查询活动
     *
     * @param USER_ID
     * @return
     */
    @RequestMapping(value = "findNJKHuoDong", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findNJKHuoDong(String USER_ID) {
        logBefore(logger, "查询app活动");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            pd.put("ORDE_BY", "1");
            PageData HD = researchService.findHUODONG(pd);
            if (null != HD) {
                String LS = HD.get("RESEARCH_ID").toString();
                pd.put("RESEARCH_ID", LS);
                PageData user = user_dailyService.findDailyByUserId(pd);
                if (null == user) {
                    user_dailyService.save(pd);
                    pd.clear();
                    pd.put("code", "1");
                    pd.put("message", "正确返回数据!");
                    pd.put("data", HD);
                    pd.put("seleT", "1");//可查看
                } else {
                    pd.clear();
                    pd.put("code", "1");
                    pd.put("message", "正确返回数据!");
                    pd.put("data", "");
                    pd.put("seleT", "2");//不可查看
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // ==========================申请VIP====================================

    /**
     * @param USER_ID ID
     * @param NAME    名字
     * @param PHONE   手机
     * @param ZHIYE   职业
     * @param ADDRESS 地址
     */
    @RequestMapping(value = "ApplyVip", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String ApplyVip(String IMG, String USER_ID, String NAME, String PHONE, String ZHIYE, String ADDRESS,
                           String QQ) {
        logBefore(logger, "申请VIP");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            apply_vipService.deleteA(pd);
            String APPLYVIP_ID = this.get32UUID();
            pd.put("APPLYVIP_ID", APPLYVIP_ID);
            pd.put("APPLY_DATE", DateUtil.getTime());
            apply_vipService.save(pd);
            if (!"".equals(IMG)) {
                String TOUR_IMG[] = IMG.split(",");
                for (int i = 0; i < TOUR_IMG.length; i++) {
                    String ffile = this.get32UUID() + ".jpg";
                    String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + ffile; // 文件上传路径
                    boolean flag = ImageAnd64Binary.generateImage(TOUR_IMG[i], filePath);
                    String TOUR_IMG1 = Const.SERVERPATH + Const.FILEPATHIMG + ffile;
                    PageData pd1 = new PageData();
                    pd1.put("MENTOU_IMG_ID", this.get32UUID());
                    pd1.put("MENTOU_IMG", TOUR_IMG1);
                    pd1.put("APPLYVIP_ID", APPLYVIP_ID);
                    menTouImfService.save(pd1);
                }
            }
			/*pd.put("VIP", "2");
			appuserService.editVip(pd);*/
            PageData user = appuserService.findById(pd);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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
     * @param USER_ID    ID
     * @param NAME       名字
     * @param PHONE      手机
     * @param ZHIYE      职业
     * @param ADDRESS    地址
     * @param PRODUCT_ID 商品ID
     */
    @RequestMapping(value = "ApplyVipWenDa", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String ApplyVipWenDa(String IMG, String USER_ID, String NAME, String PHONE, String ZHIYE, String ADDRESS,
                                String QQ, String PRODUCT_ID) {
        logBefore(logger, "申请问答VIP");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            apply_vip_wenService.deleteA(pd);
            String APPLYVIP_ID_WENDA = this.get32UUID();
            pd.put("APPLYVIP_ID_WENDA", APPLYVIP_ID_WENDA);
            pd.put("APPLY_DATE", DateUtil.getTime());
            pd.put("ZHIYE", "");
            pd.put("QQ", "");
            pd.put("CROP", "");
            pd.put("SEEK", "");
            apply_vip_wenService.save(pd);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // ==========================申请VIP====================================

    /**
     * @param
     * @param USER_ID ID
     * @param NAME    名字
     * @param PHONE   手机
     * @param ZHIYE   职业
     * @param ADDRESS 地址
     * @param QQ
     * @return
     */
    @RequestMapping(value = "ApplyVip2", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String ApplyVip2(String img1, String img2, String img3, String count, String USER_ID, String NAME,
                            String PHONE, String ZHIYE, String ADDRESS, String QQ) {
        logBefore(logger, "申请VIP");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            apply_vipService.deleteA(pd);
            String APPLYVIP_ID = this.get32UUID();
            pd.put("APPLYVIP_ID", APPLYVIP_ID);
            pd.put("APPLY_DATE", DateUtil.getTime());
            apply_vipService.save(pd);
            Integer count1 = Integer.valueOf(count);
            String ffile1 = this.get32UUID() + ".jpg";
            String filePath2 = PathUtil.getClasspath() + Const.FILEPATHIMG + "VIP/" + DateUtil.getDays(); // 文件上传路径
            File file = new File(filePath2, ffile1);
            if (!file.exists()) {
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
            }
            for (int i = 0; i < count1; i++) {
                String ffile = this.get32UUID() + ".jpg";
                String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + "VIP/" + DateUtil.getDays() + "/"
                        + ffile; // 文件上传路径
                String TOUR_IMG1 = Const.SERVERPATH + Const.FILEPATHIMG + "VIP/" + DateUtil.getDays() + "/" + ffile;
                String ii = String.valueOf(i + 1);
                PageData pd1 = new PageData();
                pd1.put("MENTOU_IMG_ID", this.get32UUID());
                pd1.put("MENTOU_IMG", TOUR_IMG1);
                pd1.put("APPLYVIP_ID", APPLYVIP_ID);
                menTouImfService.save(pd1);
                new Thread(new Thread2(pd.getString("img" + ii), TOUR_IMG1, filePath, TOUR_IMG1, ffile, post_ImgService,
                        i)).start();
            }
/*			pd.put("VIP", "2");
			appuserService.editVip(pd);*/
            PageData user = appuserService.findById(pd);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }
    // ==========================申请VIP====================================

    /**
     * @param
     * @param USER_ID ID
     * @param NAME    名字
     * @param PHONE   手机
     * @param ZHIYE   职业
     * @param ADDRESS 地址
     * @param QQ
     * @return
     */
    @RequestMapping(value = "ApplyVip3", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String ApplyVip3(String img1, String img2, String img3, String count, String USER_ID, String NAME,
                            String PHONE, String ZHIYE, String ADDRESS, String QQ, String SEEK) {
        logBefore(logger, "申请VIP");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            apply_vipService.deleteA(pd);
            String APPLYVIP_ID = this.get32UUID();
            pd.put("APPLYVIP_ID", APPLYVIP_ID);
            pd.put("APPLY_DATE", DateUtil.getTime());
            apply_vipService.save(pd);
            Integer count1 = Integer.valueOf(count);
            String ffile1 = this.get32UUID() + ".jpg";
            String filePath2 = PathUtil.getClasspath() + Const.FILEPATHIMG + "VIP/" + DateUtil.getDays(); // 文件上传路径
            File file = new File(filePath2, ffile1);
            if (!file.exists()) {
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
            }
            for (int i = 0; i < count1; i++) {
                String ffile = this.get32UUID() + ".jpg";
                String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + "VIP/" + DateUtil.getDays() + "/"
                        + ffile; // 文件上传路径
                String TOUR_IMG1 = Const.SERVERPATH + Const.FILEPATHIMG + "VIP/" + DateUtil.getDays() + "/" + ffile;
                String ii = String.valueOf(i + 1);
                PageData pd1 = new PageData();
                pd1.put("MENTOU_IMG_ID", this.get32UUID());
                pd1.put("MENTOU_IMG", TOUR_IMG1);
                pd1.put("APPLYVIP_ID", APPLYVIP_ID);
                menTouImfService.save(pd1);
                new Thread(new Thread2(pd.getString("img" + ii), TOUR_IMG1, filePath, TOUR_IMG1, ffile, post_ImgService,
                        i)).start();
            }
/*			pd.put("VIP", "2");
			appuserService.editVip(pd);*/
            PageData user = appuserService.findById(pd);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // ===========================查询评论==================================
    @RequestMapping(value = "findCommentPro", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findCommentPro(String PRODUCT_ID, String pageNum) {
        logBefore(logger, "查询评论");
        PageData pd = new PageData();
        Page page = new Page();
        String message = "正确返回数据!";
        try {
            pd = this.getPageData();
            if (pageNum == null || pageNum.length() == 0) {
                pageNum = "1";
            }
            page.setPd(pd);
            page.setShowCount(10);
            page.setCurrentPage(Integer.parseInt(pageNum));
            List<PageData> list = commentProService.list(page);
            for (int i = 0; i < list.size(); i++) {
                List<PageData> list1 = commentProImgService.findList(list.get(i));
                if (list1.size() == 0 || list1 == null) {
                    List<PageData> list4 = new ArrayList();
                    list.get(i).put("COMMENT_IMG", list4);
                } else {
                    list.get(i).put("COMMENT_IMG", list1);
                }
            }
            Map<String, Object> map = new HashMap();
            if (page.getCurrentPage() == Integer.parseInt(pageNum)) {
                map.put("object", list);
            } else {
                PageData pd4 = new PageData();
                List<PageData> list4 = new ArrayList();
                list4.add(pd4);
                message = "已加载全部数据!";
                list4.get(0).put("COMMENT_PRO_ID", "");
                list4.get(0).put("USER_ID", "");
                list4.get(0).put("PRODUCT_ID", "");
                list4.get(0).put("MESSAGE", "");
                list4.get(0).put("DATE", "1");
                list4.get(0).put("NAME", "");
                list4.get(0).put("HUIFU", "");
                list4.get(0).put("IMG", "");
                List<PageData> list5 = new ArrayList();
                list4.get(0).put("COMMENT_IMG", list5);
                map.put("object", list4);
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("message", message);
            pd.put("data", map);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // ==========================评论商品====================================
    @RequestMapping(value = "CommentPro", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String CommentPro(String USER_ID, String PRODUCT_ID, String MESSAGE, String IMG) {
        logBefore(logger, "评论商品");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            PageData pd1 = appuserService.findById(pd);
            String COLLECTION_PRO_ID = this.get32UUID();
            PageData pd2 = new PageData();
            pd2.put("COMMENT_PRO_ID", COLLECTION_PRO_ID);
            pd2.put("NAME", pd1.getString("NAME"));
            pd2.put("DATE", DateUtil.getTime());
            pd2.put("USER_ID", USER_ID);
            pd2.put("PRODUCT_ID", PRODUCT_ID);
            pd2.put("MESSAGE", MESSAGE);
            pd2.put("HUIFU", "");
            commentProService.save(pd2);
            if (!"".equals(IMG)) {
                String TOUR_IMG[] = IMG.split(",");
                for (int i = 0; i < TOUR_IMG.length; i++) {
                    String ffile = this.get32UUID() + ".jpg";
                    String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + ffile; // 文件上传路径
                    boolean flag = ImageAnd64Binary.generateImage(TOUR_IMG[i], filePath);
                    String TOUR_IMG1 = Const.SERVERPATH + Const.FILEPATHIMG + ffile;
                    PageData pd12 = new PageData();
                    pd12.put("COMMENT_PRO_IMG_ID", this.get32UUID());
                    pd12.put("IMG", TOUR_IMG1);
                    pd12.put("COMMENT_PRO_ID", COLLECTION_PRO_ID);
                    pd12.put("DATE", DateUtil.getTime());
                    commentProImgService.save(pd12);
                }
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
        } catch (Exception e) {
            e.printStackTrace();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // ==========================咨询客服=====================================
    @RequestMapping(value = "Customer", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String Customer(String USER_ID, String PRODUCT_ID) {
        logBefore(logger, "咨询客服");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            PageData pd_u = appuserService.findById(pd);
            PageData pd_p = productService.findById(pd);
            SmsBao sms = new SmsBao();
            /*if (pd_u.getString("EXCLU_ID").equals("5d18274e708c472ca679622f2c964ce0")) {*/
            String context = "用户(" + pd_u.getString("USERNAME") + ")对" + pd_p.getString("PRODUCT_NAME")
                    + "有意向,请尽快和用户联系.";
            String result = sms.sendSMS(pd_p.getString("PHONE"), context);
			/*} else {
				PageData pd_e = excluService.findById(pd_u);
				String context = "用户(" + pd_u.getString("USERNAME") + ")对" + pd_p.getString("PRODUCT_NAME")
						+ "有意向,请尽快和用户联系.";
				String result = sms.sendSMS(pd_e.getString("PHONE"), context);
			}*/
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "二十四小时内客服会和您电话联系");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // ==========================收藏商品=====================================

    /**
     * @param USER_ID
     * @param PRODUCT_ID
     * @param FLAG              0收藏1取消收藏
     * @param COLLECTION_PRO_ID
     * @return
     */
    @RequestMapping(value = "CollectionPro", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String CollectionPro(String USER_ID, String PRODUCT_ID, String FLAG, String COLLECTION_PRO_ID) {
        logBefore(logger, "收藏商品");
        PageData pd = new PageData();
        pd = this.getPageData();
        String COLLECTION = "0";
        String message = "正确返回数据!";
        try {
            if (FLAG.equals("0")) {
                if (collection_proService.findCollection(pd) != null) {
                    message = "你已收藏过该商品!";
                    COLLECTION = "1";
                } else {
                    pd.put("COLLECTION_PRO_ID", this.get32UUID());
                    pd.put("DATE", DateUtil.getTime());
                    collection_proService.save(pd);
                    COLLECTION = "1";
                }

            } else {
                collection_proService.delete(pd);
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("message", message);
            pd.put("COLLECTION", COLLECTION);
        } catch (Exception e) {
            e.printStackTrace();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // ==========================详情==============================
    @RequestMapping(value = "findProductId", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findProductId(String PRODUCT_ID, String USER_ID) {
        logBefore(logger, "查询商品详情");
        PageData pd = new PageData();
        pd = this.getPageData();
        Map<String, Object> map = new HashMap();                             //用来封装商品展示信息 和商品详细资讯信息的 DTOMAP
        try {
            PageData pd1 = productService.findById(pd);                //根据商品ID 查询 product表信息(商品展示信息)
            List<PageData> list1 = remarkService.findList(pd1);        //根据商品ID查询 商品的remark信息(规格，大小，价格)
            PageData pd_c = collection_proService.findCollection(pd);  //根据用户ID 和商品ID 查询收藏表的中 用户与商品收藏的信息  返回值  id /user_id /prodocut_id /date
            //根据用户的ID和商品编号查询该用户时候可以查看该商品的详情
			/*for(PageData pad:list1){
				PageData pdvu =null;
				pdvu = p_QuanXuanService.findProRoleByVipName(pd);   //判断该用户时候有权限查看当前货物信息
				if(pdvu == null){				//如果为空则无权限
					pad.remove("PRICE");
					pad.put("PRICE","点击咨询价格");          //修改PRICE属性值为 点击咨询
				}
			}*/
            List<PageData> list = p_QuanXuanService.findList(pd);
            if (list != null && list.size() != 0) {
                PageData pd_p = p_QuanXuanService.findById(pd);
                if (pd_p == null) {
                    for (int i = 0; i < list1.size(); i++) {
                        list1.get(i).put("EXPLAIN1", "点击咨询价格!");
                    }
                }
            }
            if (pd_c != null) {
                pd1.put("COLLECTION", "1");
            } else {
                pd1.put("COLLECTION", "0");
            }
            PageData pd_ca = cartService.findCount(pd);                //根据用户ID 获得其购物车商品数量

            /*
             * List<PageData> listp=contentService.findContent(pd);
             * if(listp.size()!=0){ Long
             * day=DateUtil.TimeDiff2(listp.get(0).getString("DATE"),DateUtil.
             * getTime()); Long
             * hour=DateUtil.TimeDiff1(listp.get(0).getString("DATE"),DateUtil.
             * getTime()); Long
             * fenzhong=DateUtil.TimeDiff(listp.get(0).getString("DATE"),
             * DateUtil.getTime()); if(day>0||hour>0||fenzhong>30){
             * listp.get(0).put("STOP_DATE", "未知");
             * contentService.edit(listp.get(0)); }else{
             * listp.get(0).put("STOP_DATE", fenzhong+"分钟");
             * contentService.edit(listp.get(0)); } }
             */
            /*
             * PageData pd_co=new PageData(); pd_co.put("CENSUS_ID",
             * this.get32UUID()); pd_co.put("ID", PRODUCT_ID);
             * pd_co.put("CONTENT", "查询商品("+pd1.getString("PRODUCT_NAME")+")");
             * pd_co.put("DATE", DateUtil.getTime()); pd_co.put("USER_ID",
             * USER_ID); pd_co.put("STATUS", "0"); pd_co.put("STOP_DATE", "");
             * pd_co.put("YUE", DateUtil.getDay()); contentService.save(pd_co);
             */
            /*
             * List<PageData> list=p_QuanXuanService.findList(pd);
             * if(list!=null&&list.size()!=0){ PageData
             * pd_p=p_QuanXuanService.findById(pd); if(pd_p==null){ for(int
             * i=0;i<list1.size();i++){ list1.get(i).put("EXPLAIN1",
             * "你没有查看该商品的价格的权限!"); } } }
             */
            pd1.put("CART", pd_ca.get("count"));//获取pd_ca中的查询的到的购物车货物数量并封装入 DTO pd1
            map.put("object", pd1);                //将获取到的购物车的商品 count 数量 和是否收藏次商品状态(0：未收藏；1：收藏)封装到 DTO Map中
            map.put("list2", list1);            //将获取到的商品详细资讯封装入 DTO map中
            pd.clear();                          //清空 DTO pdMap中的数据，重新进行注入key/value
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("data", map);
            pd.put("url", "http " + "://www.meitiannongzi.com/nongjike/static/jsp/product.html?PRODUCT_ID=" + PRODUCT_ID);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null; //创建一个DTO String 用来向移动端发送数据
        try {
            str = mapper.writeValueAsString(pd);  //将
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

    // ==========================查询全部商品================================
    @RequestMapping(value = "findProduct", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findProduct(String KEYWORD, String pageNum) {
        logBefore(logger, "查询全部商品s");
        PageData pd = new PageData();
        pd = this.getPageData();
        Page page = new Page();
        String message = "正确返回数据!";
        try {
            if (pageNum == null || pageNum.length() == 0) {
                pageNum = "1";
            }
            page.setPd(pd);
            page.setShowCount(10);
            page.setCurrentPage(Integer.parseInt(pageNum));
            List<PageData> list = productService.list2(page);
            for (int i = 0; i < list.size(); i++) {
                list.get(i).put("url", "http://www.meitiannongzi.com/nongjike/static/jsp/product.html?PRODUCT_ID="
                        + list.get(i).getString("PRODUCT_ID"));
                List<PageData> list1 = postService.selectPost(list.get(i));
                if (list1 != null) {
                    list.get(i).put("PList", list1);
                } else {
                    List<PageData> list2 = new ArrayList();
                    list.get(i).put("PList", list2);
                }
            }
            Map<String, Object> map = new HashMap();
            if (page.getCurrentPage() == Integer.parseInt(pageNum)) {
                map.put("object", list);
            } else {
                List<PageData> list4 = new ArrayList();
                message = "已加载全部数据!";
                map.put("object", list4);
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("message", message);
            pd.put("data", map);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // =========================修改密码=========================================

    /**
     * @param USER_ID   ID
     * @param PASSWORD  旧密码
     * @param XPASSWORD 新密码
     * @return
     */
    @RequestMapping(value = "UpdatePassword", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String UpdatePassword(String USER_ID, String PASSWORD, String XPASSWORD) {
        logBefore(logger, "修改密码");
        PageData pd = new PageData();
        String code = "1";
        String message = "";
        try {
            pd = this.getPageData();
            PageData pd1 = appuserService.findById(pd);
            if (pd1 != null) {
                PageData pd2 = new PageData();
                pd2.put("USERNAME", pd1.getString("USERNAME"));
                pd2.put("PASSWORD", MD5.md5(MD5.md5(PASSWORD) + pd1.getString("SALT")));
                PageData pd3 = appuserService.login(pd2);
                if (pd3 != null) {
                    pd3.put("PASSWORD", MD5.md5(MD5.md5(XPASSWORD) + pd3.getString("SALT")));
                    appuserService.editPassword(pd3);
                    message = "修改密码成功";
                } else {
                    code = "2";
                    message = "密码错误";
                }
            } else {
                code = "2";
                message = "用户不存在";
            }
            pd.clear();
            pd.put("code", code);
            pd.put("message", message);
        } catch (Exception e) {
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "请联系管理员");
            e.printStackTrace();
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // =========================忘记密码=========================================

    /**
     * @param USERNAME 用户名
     * @param YZM      验证码
     * @param PASSWORD 密码
     * @return
     */
    @RequestMapping(value = "BackPassword", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String BackPassword(String USERNAME, String YZM, String PASSWORD) {
        logBefore(logger, "忘记密码密码");
        PageData pd = new PageData();
        String code = "1";
        String message = "";
        try {
            pd = this.getPageData();
            PageData pd1 = appuserService.findName(pd);
            if (pd1 != null) {
                PageData pd_y = yzmService.findByPhone(pd1);
                if (pd_y != null) {
                    SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    long TIME = sdfTime.parse(DateUtil.getTime()).getTime();
                    long ENDTIME = sdfTime.parse(pd_y.getString("ENDTIME")).getTime();
                    if (TIME < ENDTIME) {
                        pd.put("USER_ID", pd1.getString("USER_ID"));
                        pd.put("PASSWORD", MD5.md5(MD5.md5(PASSWORD) + pd1.getString("SALT")));
                        appuserService.editPassword(pd);
                        message = "成功";
                    } else {
                        code = "2";
                        message = "验证码过期";
                    }
                } else {
                    code = "2";
                    message = "没有验证码";
                }
            } else {
                code = "2";
                message = "没有该用户";
            }
            pd.clear();
            pd.put("code", code);
            pd.put("message", message);
        } catch (Exception e) {
            e.printStackTrace();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // =========================注册=======================================

    /**
     * @param USERNAME 用户名
     * @param PASSWORD 密码
     * @param YZM      验证码
     * @param NAME     昵称
     * @return
     */
    @RequestMapping(value = "/register", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public synchronized String register(String USERNAME, String PASSWORD, String YZM, String NAME) {
        logBefore(logger, "注册");
        PageData pd = new PageData();
        Map<String, Object> map = new HashMap();
        String code = "1";
        String message = "";
        try {
            pd = this.getPageData();
            PageData pd1 = appuserService.findName(pd);
            PageData pd_y = yzmService.findByPhone(pd);
            if (pd_y != null) {
                if (pd_y.getString("YZM").equals(YZM)) {
                    SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    long TIME = sdfTime.parse(DateUtil.getTime()).getTime();
                    long ENDTIME = sdfTime.parse(pd_y.getString("ENDTIME")).getTime();
                    if (TIME < ENDTIME) {
                        if (pd1 != null) {
                            code = "2"; // 用户名已存在
                            message = "该用户已注册";
                        } else {
                            String yzm = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
                            pd.put("USER_ID", this.get32UUID());
                            pd.put("USERNAME", USERNAME);
                            String time = DateUtil.getTime();
                            pd.put("DATE", time);// 注册时间
                            pd.put("PASSWORD", MD5.md5(MD5.md5(PASSWORD) + yzm));
                            pd.put("ROLE_ID", "77"); // 角色
                            pd.put("NAME", NAME);
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
                            pd.put("OPENID", "");
                            pd.put("UNIONID", "");
                            pd.put("JIFEN", "0");
                            pd.put("ZJIFEN", "0");
                            pd.put("IMG", "");
                            pd.put("TYPE", "");
                            pd.put("PHONEADDRESS", "");
                            appuserService.save(pd);
                            PageData pd2 = new PageData();
                            pd2.put("SYS_APP_USERINFO_ID", this.get32UUID());
                            pd2.put("USER_ID", pd.getString("USER_ID"));
                            pd2.put("SEX", "0");
                            pd2.put("VIP", "0");
                            int max = 2;
                            int min = 1;
                            Random random = new Random();
                            int s = random.nextInt(max) % (max - min + 1) + min;
                            if (s == 1) {
                                pd2.put("IMG", "http://m.nongjike.cn/NJK/uploadFiles/uploadImgs/1.png");
                            } else if (s == 2) {
                                pd2.put("IMG", "http://m.nongjike.cn/NJK/uploadFiles/uploadImgs/2.png");
                            }
                            pd2.put("QQ", "");
                            pd2.put("NIAN1", "");
                            pd2.put("YUE", "");
                            pd2.put("RI", "");
                            pd2.put("ZHIYE", "");
                            pd2.put("ADDRESS", "");
                            pd2.put("PHONE", "");
                            pd2.put("QIANMING", "每天农资,祝你好心情!");
                            pd2.put("WEIXIN", "");
                            appUserInfoService.save(pd2);
                            message = "注册成功";
                            PageData pd_r = new PageData();
                            pd_r.put("REGISTER_RECORD_ID", this.get32UUID());
                            pd_r.put("DATE", DateUtil.getDay());
                            pd_r.put("NIAN1", DateUtil.getYear());
                            Calendar calendar = Calendar.getInstance();
                            // 获得当前时间的月份，月份从0开始所以结果要加1
                            int month = calendar.get(Calendar.MONTH) + 1;
                            pd_r.put("YUE", month);
                            register_recordService.save(pd_r);
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
            map.put("code", code);
            map.put("message", message);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", "2");
            map.put("message", "请联系管理员");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
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

    // =============================查询个人信息===============================
    @RequestMapping(value = "findAppUserInfo", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findAppUserInfo(String USER_ID, String SUSER_ID) {
        logBefore(logger, "查询个人信息");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            PageData pd1 = appuserService.findBySId(pd);
            PageData pd2 = home_FollowService.findById(pd);
            if (pd2 != null) {
                pd1.put("HOME", "1");
            } else {
                pd1.put("HOME", "0");
            }
            List<PageData> list = postService.findList(pd);
            if (list != null) {
                pd1.put("POST", "1");
            } else {
                pd1.put("POST", "0");
            }
            List<PageData> list1 = activity_PostService.findList(pd);
            if (list1 != null) {
                pd1.put("ACTIVITY", "1");
            } else {
                pd1.put("ACTIVITY", "0");
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("data", pd1);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("nessage", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // =============================查询个人信息================================
    @RequestMapping(value = "findAppUser", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findAppUser(String USER_ID) {
        logBefore(logger, "查询个人信息");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            PageData pd1 = appuserService.findById(pd);
            PageData pd4 = weixinzhifuService.findById(pd);
            String admin = "0";
            if (USER_ID.equals("17")) {
                admin = "1";
            }
            pd.clear();
            pd.put("WEIXIN", pd4.getString("WEIXIN"));
            pd.put("WSTATUS", pd4.getString("WSTATUS"));
            pd.put("data", pd1);
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("admin", admin);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("nessage", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // ============================查询关注或关注我的人=========================

    /**
     * @param USER_ID
     * @param STATUS  0关注的人 1关注我的人
     * @param pageNum
     * @return
     */
    @RequestMapping(value = "findGuanZhu", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findGuanZhu(String USER_ID, String STATUS, String pageNum) {
        logBefore(logger, "查询关注或关注我的人");
        PageData pd = new PageData();
        pd = this.getPageData();
        Page page = new Page();
        String message = "正确返回数据!";
        try {
            List<PageData> list = new ArrayList();
            if (pageNum == null || pageNum.length() == 0) {
                pageNum = "1";
            }
            page.setPd(pd);
            page.setShowCount(10);
            page.setCurrentPage(Integer.parseInt(pageNum));
            if (STATUS.equals("0")) {
                list = home_FollowService.list(page);
            } else {
                list = home_FollowService.list1(page);
            }
            Map<String, Object> map = new HashedMap();
            if (page.getCurrentPage() == Integer.parseInt(pageNum)) {
                map.put("data", list);
            } else {
                List<PageData> list4 = new ArrayList();
                message = "已加载全部数据!";
                map.put("object", list4);
            }
            pd.clear();
            pd.put("object", map);
            pd.put("message", message);
            pd.put("code", "1");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("nessage", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // =============================关注用户==================================
    @RequestMapping(value = "GuanZhu", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String GuanZhu(String USER_ID, String SUSER_ID, String SUSERNAME) {
        logBefore(logger, "关注用户");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            if (home_FollowService.findById(pd) == null) {
                PageData pd1 = appuserService.findById(pd);
                pd.put("USERNAME", pd1.getString("USERNAME"));
                pd.put("DATE", DateUtil.getTime());
                home_FollowService.save(pd);
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("nessage", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // =============================取消关注=====================================
    @RequestMapping(value = "QuXiaoGuanZhu", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String QuXiaoGuanZhu(String USER_ID, String SUSER_ID) {
        logBefore(logger, "取消关注");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            home_FollowService.delete(pd);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("nessage", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // =============================修改个人头像================================
    @RequestMapping(value = "updateImg", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String updateImg(String USER_ID, String IMG) {
        logBefore(logger, "修改个人图片");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            String ffile = this.get32UUID() + ".jpg";
            String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + "touxiang/" + DateUtil.getDays(); // 文件上传路径
            File file = new File(filePath, ffile);
            if (!file.exists()) {
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
            }
            String filePath1 = PathUtil.getClasspath() + Const.FILEPATHIMG + "touxiang/" + DateUtil.getDays() + "/"
                    + ffile; // 文件上传路径
            boolean flag = ImageAnd64Binary.generateImage(IMG, filePath1);
            String TOUR_IMG1 = Const.SERVERPATH + Const.FILEPATHIMG + "touxiang/" + DateUtil.getDays() + "/" + ffile;
            pd.put("IMG", TOUR_IMG1);
            appUserInfoService.editImg(pd);
            pd.clear();
            pd.put("code", "1");
            pd.put("IMG", TOUR_IMG1);
            pd.put("message", "正确返回数据!");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("nessage", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
        } catch (JsonGenerationException e) {

            e.printStackTrace();
        } catch (JsonMappingException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
        return str;
    }

    // =============================修改个人信息================================

    /**
     * @param USER_ID
     * @param NAME
     * @param SEX
     * @param NIAN1
     * @param YUE
     * @param RI
     * @param ZHIYE
     * @param ADDRESS
     * @param PHONE
     * @param QIANMING
     * @return
     */
    @RequestMapping(value = "updateAppuser", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String updateAppuser(String USER_ID, String NAME, String SEX, String NIAN1, String YUE, String RI,
                                String ZHIYE, String ADDRESS, String PHONE, String QIANMING, String QQ) {
        logBefore(logger, "修改个人信息");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            appUserInfoService.edit(pd);
            appuserService.editName(pd);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("nessage", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // =============================登录=====================================

    /**
     * 登录
     *
     * @param USERNAME 用户名 PASSWORD 密码 CHANNELID
     * @return
     */
    @RequestMapping(value = "/Login", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String Login(String USERNAME, String PASSWORD, String CHANNELID, String TYPE) {
        logBefore(logger, "登录");
        PageData pd = new PageData();
        Map<String, Object> map = new HashMap();
        // String flag="0";
        String message = "";
        String code = "1";
        try {
            pd = this.getPageData();
            PageData pd4 = weixinzhifuService.findById(pd);
            PageData pd1 = appuserService.findName(pd);
            if (pd1 == null) {
                code = "2";
                message = "该用户不存在";
            } else {
                pd.put("PASSWORD", new SimpleHash("SHA-1", PASSWORD, pd1.getString("SALT")).toString());
                String pad = MD5.md5(MD5.md5(PASSWORD) + pd1.getString("SALT"));
                pd.put("PASSWORD", pad);
                PageData pd2 = appuserService.login(pd);
                if (pd2 != null) {
                    message = "登录成功";
                    map.put("NAME", pd2.getString("NAME"));
                    /* map.put("SEX", pd2.getString("SEX")); */
                    map.put("SEX", pd2.getString("QIANMING"));
                    map.put("IMG", pd2.getString("IMG"));
                    map.put("VIP", pd2.getString("VIP"));
                    map.put("USER_ID", pd2.getString("USER_ID"));
                    map.put("username", USERNAME);
                    map.put("password", PASSWORD);
                    map.put("ENAME", pd2.getString("ENAME"));
                    map.put("EPHONE", pd2.getString("EPHONE"));
                    map.put("WEIXIN", pd4.getString("WEIXIN"));
                    map.put("WSTATUS", pd4.getString("WSTATUS"));
                    map.put("UNIONID", pd2.getString("UNIONID"));
                    if (pd2.getString("USER_ID").equals("17")) {
                        map.put("admin", "1");
                    } else {
                        map.put("admin", "0");
                    }
                    pd2.put("LAST_LOGIN", DateUtil.getTime());
                    appuserService.editloginDate(pd2);
                    if (TYPE != null && TYPE.length() != 0) {
                        pd2.put("TYPE", TYPE);
                        appuserService.editType(pd2);
                    }
                } else {
                    code = "2";
                    message = "密码错误";
                }
            }
            pd.clear();
            pd.put("code", code);
            pd.put("data", map);
            pd.put("message", message);
            // pd.put("flag", flag);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序错误,请联系系统管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    @RequestMapping(value = "WeiXinGuoDu", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String WeiXinGuoDu(String UNIONID, String TYPE) {
        logBefore(logger, "微信过度接口");
        PageData pd = new PageData();
        pd = this.getPageData();
        String code = "1";
        String message = "正确返回数据!";
        String STATUS = "1";
        Map<String, Object> map = new HashMap();
        try {
            PageData pd4 = weixinzhifuService.findById(pd);
            PageData pd_u = appuserService.findUnionId(pd);
            if (pd_u != null) {
                map.put("NAME", pd_u.getString("NAME"));
                /* map.put("SEX", pd2.getString("SEX")); */
                map.put("SEX", pd_u.getString("QIANMING"));
                map.put("IMG", pd_u.getString("IMG"));
                map.put("VIP", pd_u.getString("VIP"));
                map.put("USER_ID", pd_u.getString("USER_ID"));
                map.put("username", pd_u.getString("USERNAME"));
                map.put("password", "");
                map.put("ENAME", pd_u.getString("ENAME"));
                map.put("EPHONE", pd_u.getString("EPHONE"));
                map.put("WEIXIN", pd4.getString("WEIXIN"));
                map.put("WSTATUS", pd4.getString("WSTATUS"));
                map.put("UNIONID", UNIONID);
                if (pd_u.getString("USER_ID").equals("17")) {
                    map.put("admin", "1");
                } else {
                    map.put("admin", "0");
                }
                pd_u.put("LAST_LOGIN", DateUtil.getTime());
                appuserService.editloginDate(pd_u);
                if (TYPE != null && TYPE.length() != 0) {
                    pd_u.put("TYPE", TYPE);
                    appuserService.editType(pd_u);
                }
            } else {
                STATUS = "2";
            }
            pd.clear();
            pd.put("data", map);
            pd.put("code", code);
            pd.put("STATUS", STATUS);
            pd.put("message", message);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序错误,请联系系统管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    @RequestMapping(value = "PanDuanWeiXin", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String PanDuanWeiXin(String UNIONID, String USERNAME, String STATUS) {
        logBefore(logger, "判断微信");
        PageData pd = new PageData();
        pd = this.getPageData();
        String message = "获取验证码成功";
        String code = "1";
        try {
            PageData pd_u = appuserService.findUnionId(pd);
            PageData pd1 = appuserService.findName(pd);
            if (pd_u != null) {
                code = "2"; // 验证码过期
                message = "微信已经绑定过手机号";
            } else {
                if (STATUS.equals("0")) {
                    if (pd1 != null) {
                        code = "2"; // 验证码过期
                        message = "您的账号已存在.";
                    }
                } else {
                    if (pd1 == null) {
                        code = "2"; // 验证码过期
                        message = "您还没有创建账号,请创建账号";
                    } else if (!pd1.getString("UNIONID").equals("")) {
                        code = "2"; // 验证码过期
                        message = "您的账号已存在.";
                    }
                }
            }
            pd.clear();
            pd.put("code", code);
            pd.put("message", message);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序错误,请联系系统管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    @RequestMapping(value = "WeiXinLogin", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String WeiXinLogin(String UNIONID, String nickname, String sex, String city, String openid,
                              String headImageUrl, String USERNAME, String PASSWORD, String YZM, String STATUS) {
        logBefore(logger, "微信登录");
        PageData pd = new PageData();
        pd = this.getPageData();
        String code = "1";
        String message = "关联成功!";
        Map<String, Object> map = new HashMap();
        try {
            PageData pd4 = weixinzhifuService.findById(pd);
            PageData pd_y = yzmService.findByPhone(pd);
            if (pd_y != null) {
                if (pd_y.getString("YZM").equals(YZM)) {
                    SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    long TIME = sdfTime.parse(DateUtil.getTime()).getTime();
                    long ENDTIME = sdfTime.parse(pd_y.getString("ENDTIME")).getTime();
                    if (TIME < ENDTIME) {
                        PageData pd1 = appuserService.findName(pd);
                        if (STATUS.equals("0")) {
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
                                map.put("NAME", nickname);
                                /* map.put("SEX", pd2.getString("SEX")); */
                                map.put("SEX", "每天农资,祝你好心情!");
                                map.put("IMG", headImageUrl);
                                map.put("VIP", "0");
                                map.put("USER_ID", pd.getString("USER_ID"));
                                map.put("username", USERNAME);
                                map.put("password", PASSWORD);
                                map.put("ENAME", "每天农资");
                                map.put("EPHONE", "0371-55139556");
                                map.put("WEIXIN", pd4.getString("WEIXIN"));
                                map.put("WSTATUS", pd4.getString("WSTATUS"));
                                map.put("UNIONID", UNIONID);
                            } else {
                                code = "2"; // 验证码过期
                                message = "您的账号已存在.";
                            }
                        } else {
                            if (pd1 != null) {
                                String yzm = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
                                pd1.put("PASSWORD", MD5.md5(MD5.md5(PASSWORD) + yzm));
                                pd1.put("UNIONID", UNIONID);
                                pd1.put("NAME", nickname);
                                pd1.put("OPENID", openid);
                                pd1.put("SALT", yzm);
                                appuserService.editunionIDss(pd1);
                                pd1.put("IMG", headImageUrl);
                                appUserInfoService.editImg(pd1);
                                pd1.put("LAST_LOGIN", DateUtil.getTime());
                                appuserService.editloginDate(pd1);
                                if (pd1.getString("USER_ID").equals("17")) {
                                    map.put("admin", "1");
                                } else {
                                    map.put("admin", "0");
                                }
                                map.put("NAME", nickname);
                                /* map.put("SEX", pd2.getString("SEX")); */
                                map.put("SEX", pd1.getString("QIANMING"));
                                map.put("IMG", headImageUrl);
                                map.put("VIP", pd1.getString("VIP"));
                                map.put("USER_ID", pd1.getString("USER_ID"));
                                map.put("username", USERNAME);
                                map.put("password", PASSWORD);
                                map.put("ENAME", "每天农资");
                                map.put("EPHONE", "0371-55139556");
                                map.put("WEIXIN", pd4.getString("WEIXIN"));
                                map.put("WSTATUS", pd4.getString("WSTATUS"));
                                map.put("UNIONID", UNIONID);
                            } else {
                                code = "2"; // 验证码过期
                                message = "您还没有创建账号,请创建账号";
                            }
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
            pd.clear();
            pd.put("data", map);
            pd.put("code", code);
            pd.put("message", message);
        } catch (Exception e) {
            e.printStackTrace();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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
     * @param UNIONID
     * @param nickname
     * @param sex
     * @param city
     * @param openid
     * @param headImageUrl
     * @param USERNAME
     * @param PASSWORD
     * @param YZM
     * @param STATUS       0创建新账号 1已有账号
     * @return
     */
    @RequestMapping(value = "WeiXinLogins", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String WeiXinLogins(String UNIONID, String nickname, String sex, String city, String openid,
                               String headImageUrl, String USERNAME, String PASSWORD, String YZM, String STATUS) {
        logBefore(logger, "微信登录");
        PageData pd = new PageData();
        pd = this.getPageData();
        String code = "1";
        String message = "正确返回数据!";
        Map<String, Object> map = new HashMap();
        try {
            PageData pd4 = weixinzhifuService.findById(pd);
            PageData pd_y = yzmService.findByPhone(pd);
            if (pd_y != null) {
                if (pd_y.getString("YZM").equals(YZM)) {
                    SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    long TIME = sdfTime.parse(DateUtil.getTime()).getTime();
                    long ENDTIME = sdfTime.parse(pd_y.getString("ENDTIME")).getTime();
                    if (TIME < ENDTIME) {
                        PageData pd1 = appuserService.findName(pd);
                        if (STATUS.equals("0")) {
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
                                map.put("NAME", nickname);
                                /* map.put("SEX", pd2.getString("SEX")); */
                                map.put("SEX", "每天农资,祝你好心情!");
                                map.put("IMG", headImageUrl);
                                map.put("VIP", "0");
                                map.put("USER_ID", pd.getString("USER_ID"));
                                map.put("username", USERNAME);
                                map.put("password", PASSWORD);
                                map.put("ENAME", "每天农资");
                                map.put("EPHONE", "0371-55139556");
                                map.put("WEIXIN", pd4.getString("WEIXIN"));
                                map.put("WSTATUS", pd4.getString("WSTATUS"));
                                map.put("UNIONID", UNIONID);
                            } else {
                                code = "2"; // 验证码过期
                                message = "关联失败,你已有账号";
                            }
                        } else {
                            if (pd1 != null) {
                                String yzm = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
                                pd1.put("PASSWORD", MD5.md5(MD5.md5(PASSWORD) + yzm));
                                pd1.put("UNIONID", UNIONID);
                                pd1.put("NAME", nickname);
                                pd1.put("OPENID", openid);
                                pd1.put("SALT", yzm);
                                appuserService.editunionIDss(pd1);
                                pd1.put("IMG", headImageUrl);
                                appUserInfoService.editImg(pd1);
                                pd1.put("LAST_LOGIN", DateUtil.getTime());
                                appuserService.editloginDate(pd1);
                                if (pd1.getString("USER_ID").equals("17")) {
                                    map.put("admin", "1");
                                } else {
                                    map.put("admin", "0");
                                }
                                map.put("NAME", nickname);
                                /* map.put("SEX", pd2.getString("SEX")); */
                                map.put("SEX", pd1.getString("QIANMING"));
                                map.put("IMG", headImageUrl);
                                map.put("VIP", pd1.getString("VIP"));
                                map.put("USER_ID", pd.getString("USER_ID"));
                                map.put("username", USERNAME);
                                map.put("password", PASSWORD);
                                map.put("ENAME", "每天农资");
                                map.put("EPHONE", "0371-55139556");
                                map.put("WEIXIN", pd4.getString("WEIXIN"));
                                map.put("WSTATUS", pd4.getString("WSTATUS"));
                                map.put("UNIONID", UNIONID);
                            } else {
                                code = "2"; // 验证码过期
                                message = "关联失败,你还没有账号";
                            }
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
            pd.clear();
            pd.put("data", map);
            pd.put("code", code);
            pd.put("message", message);
        } catch (Exception e) {
            e.printStackTrace();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    @RequestMapping(value = "WeiXinBangDing", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String WeiXinBangDing(String UNIONID, String USERNAME, String YZM, String PASSWORD) {
        logBefore(logger, "微信绑定");
        PageData pd = new PageData();
        pd = this.getPageData();
        String message = "正确返回数据!";
        String code = "1";
        Map<String, Object> map = new HashMap();
        try {
            PageData pd4 = weixinzhifuService.findById(pd);
            PageData pd_y = yzmService.findByPhone(pd);
            PageData pd_uuin = appuserService.findUnionId(pd);
            if (pd_y != null) {
                if (pd_y.getString("YZM").equals(YZM)) {
                    SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    long TIME = sdfTime.parse(DateUtil.getTime()).getTime();
                    long ENDTIME = sdfTime.parse(pd_y.getString("ENDTIME")).getTime();
                    if (TIME < ENDTIME) {
                        PageData pd1 = appuserService.findName(pd);
                        if (pd1 != null) {
                            /*
                             * PageData pd_union=appuserService.findUnionId(pd);
                             */
                            String yzm = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
                            pd1.put("PASSWORD", MD5.md5(MD5.md5(PASSWORD) + yzm));
                            pd1.put("SALT", yzm);
                            pd1.put("UNIONID", UNIONID);
                            appuserService.editUnionId(pd1);
                            appuserService.delete(pd1);
                            pd1.put("DATE", DateUtil.getTime());
                            feiQiService.save(pd1);
                            /*
                             * pd_union.put("USER_ID1",
                             * pd1.getString("USER_ID"));
                             */
                            /*
                             * order_infoService.editUserId(pd_union);
                             * postService.editUserId(pd_union);
                             * tiWenService.editUserId(pd_union);
                             */
                            /*
                             * activity_PostService.editUserId(pd_union);
                             * activity_RoomService.editUserId(pd_union);
                             * zuoGuo_ShiJuanService.editUserId(pd_union);
                             * error_WendaService.editUserId(pd_union);
                             */
                            map.put("NAME", pd_uuin.getString("NAME"));
                            /* map.put("SEX", pd2.getString("SEX")); */
                            map.put("SEX", pd_uuin.getString("QIANMING"));
                            map.put("IMG", pd_uuin.getString("IMG"));
                            map.put("VIP", pd_uuin.getString("VIP"));
                            map.put("USER_ID", pd_uuin.getString("USER_ID"));
                            map.put("username", USERNAME);
                            map.put("password", PASSWORD);
                            map.put("ENAME", pd_uuin.getString("ENAME"));
                            map.put("EPHONE", pd_uuin.getString("EPHONE"));
                            map.put("WEIXIN", pd4.getString("WEIXIN"));
                            map.put("WSTATUS", pd4.getString("WSTATUS"));
                            map.put("UNIONID", UNIONID);
                        } else {
                            PageData pd_u = new PageData();
                            String yzm = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
                            pd_u.put("USERNAME", USERNAME);
                            pd_u.put("SALT", yzm);
                            pd_u.put("PASSWORD", MD5.md5(MD5.md5(PASSWORD) + YZM));
                            pd_u.put("UNIONID", UNIONID);
                            appuserService.editUnionIds(pd_u);
                            map.put("NAME", pd_uuin.getString("NAME"));
                            /* map.put("SEX", pd2.getString("SEX")); */
                            map.put("SEX", pd_uuin.getString("QIANMING"));
                            map.put("IMG", pd_uuin.getString("IMG"));
                            map.put("VIP", pd_uuin.getString("VIP"));
                            map.put("USER_ID", pd_uuin.getString("USER_ID"));
                            map.put("username", USERNAME);
                            map.put("password", PASSWORD);
                            map.put("ENAME", pd_uuin.getString("ENAME"));
                            map.put("EPHONE", pd_uuin.getString("EPHONE"));
                            map.put("WEIXIN", pd4.getString("WEIXIN"));
                            map.put("WSTATUS", pd4.getString("WSTATUS"));
                            map.put("UNIONID", UNIONID);
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
            pd.clear();
            pd.put("data", map);
            pd.put("code", code);
            pd.put("message", message);
        } catch (Exception e) {
            e.printStackTrace();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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
     * @param USERNAME 手机号
     * @param tel      正确加密
     * @param tels     错误加密
     * @param tls      错误加密
     * @param lop      错误加密
     * @return
     */
    @RequestMapping(value = "/verification", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public synchronized String verification(String USERNAME, String tel, String tels, String tls, String lop) {
        logBefore(logger, "发送验证码");
        PageData pd = new PageData();
        pd = this.getPageData();
        String STATUS = "0";
        String MESSAGE = "正确返回数据!";
        Map<String, Object> map = new HashMap();
        String md5s = MD5
                .md5(MD5.md5("asdas-asdaskjslfklsasdf-sfsdfks=asdjaks=sadas-asdjaksldjaslkdas-dsfsfasd") + USERNAME);
        try {
            if (md5s.equals(tel)) {
                pd.put("YUE", DateUtil.getDay());
                HttpServletRequest request = this.getRequest();
                String ip = "";
                if (request.getHeader("x-forwarded-for") == null) {
                    ip = request.getRemoteAddr();
                } else {
                    ip = request.getHeader("x-forwarded-for");
                }
                pd.put("IP", ip);
                List<PageData> list_ip = yzmService.findByPhoneIp(pd);
                if (list_ip.size() < 5) {
                    List<PageData> list_yue = yzmService.findByPhoneYue(pd);
                    if (list_yue.size() < 5) {
                        String yzm = String.valueOf((int) ((Math.random() * 9 + 1) * 1000));
                        System.out.println("验证码为：" + yzm);
                        SmsBao sms = new SmsBao();
                        String context = "验证码是【" + yzm + "】,有效时间:5分钟";
                        String result = sms.sendSMS(USERNAME, context);
                        PageData pd2 = new PageData();
                        pd2.put("YZM_ID", this.get32UUID());
                        pd2.put("YZM", yzm);
                        pd2.put("PHONE", USERNAME);
                        pd2.put("TIME", DateUtil.getTime());
                        Calendar nowTime = Calendar.getInstance();
                        Date nowDate = (Date) nowTime.getTime(); // 得到当前时间
                        Calendar afterTime = Calendar.getInstance();
                        afterTime.add(Calendar.MINUTE, 5); // 当前分钟+5
                        Date afterDate = (Date) afterTime.getTime();
                        pd2.put("ENDTIME", afterDate);
                        pd2.put("YUE", DateUtil.getDay());
                        pd2.put("IP", ip);
                        yzmService.save(pd2);
                        if ("0".equals(result.split(",")[0])) {
                            map.put("YZM", yzm);
                        } else {
                            map.put("YZM", "-1");
                        }
                    } else {
                        STATUS = "2"; // 手机号今天上限
                        MESSAGE = "手机号今天上限!";
                    }
                } else {
                    STATUS = "1"; // ip地址今天上限
                    MESSAGE = "ip地址今天上限!";
                }
            } else {
                STATUS = "5";
                MESSAGE = "验证不通过!";
            }

            pd.clear();
            pd.put("object", map);
            pd.put("STATUS", STATUS);
            pd.put("code", "1");
            pd.put("message", MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("MESSAGE", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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
     * @param USERNAME 手机号
     * @param tel      正确加密
     * @param tels     错误加密
     * @param tls      错误加密
     * @param lop      错误加密
     * @return
     */
    @RequestMapping(value = "/verifications", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public synchronized String verifications(String USERNAME, String tel, String tels, String tls, String lop) {
        logBefore(logger, "发送验证码");
        PageData pd = new PageData();
        pd = this.getPageData();
        String STATUS = "0";
        String MESSAGE = "正确返回数据!";
        Map<String, Object> map = new HashMap();
        try {
            pd.put("YUE", DateUtil.getDay());
            HttpServletRequest request = this.getRequest();
            String ip = "";
            if (request.getHeader("x-forwarded-for") == null) {
                ip = request.getRemoteAddr();
            } else {
                ip = request.getHeader("x-forwarded-for");
            }
            pd.put("IP", ip);
            List<PageData> list_ip = yzmService.findByPhoneIp(pd);
            if (list_ip.size() < 5) {
                List<PageData> list_yue = yzmService.findByPhoneYue(pd);
                if (list_yue.size() < 5) {
                    String yzm = String.valueOf((int) ((Math.random() * 9 + 1) * 1000));
                    System.out.println("验证码为：" + yzm);
                    SmsBao sms = new SmsBao();
                    String context = "验证码是【" + yzm + "】,有效时间:5分钟";
                    String result = sms.sendSMS(USERNAME, context);
                    PageData pd2 = new PageData();
                    pd2.put("YZM_ID", this.get32UUID());
                    pd2.put("YZM", yzm);
                    pd2.put("PHONE", USERNAME);
                    pd2.put("TIME", DateUtil.getTime());
                    Calendar nowTime = Calendar.getInstance();
                    Date nowDate = (Date) nowTime.getTime(); // 得到当前时间
                    Calendar afterTime = Calendar.getInstance();
                    afterTime.add(Calendar.MINUTE, 5); // 当前分钟+5
                    Date afterDate = (Date) afterTime.getTime();
                    pd2.put("ENDTIME", afterDate);
                    pd2.put("YUE", DateUtil.getDay());
                    pd2.put("IP", ip);
                    yzmService.save(pd2);
                    if ("0".equals(result.split(",")[0])) {
                        map.put("YZM", yzm);
                    } else {
                        map.put("YZM", "-1");
                    }
                } else {
                    STATUS = "2"; // 手机号今天上限
                    MESSAGE = "手机号今天上限!";
                }
            } else {
                STATUS = "1"; // ip地址今天上限
                MESSAGE = "ip地址今天上限!";
            }

            pd.clear();
            pd.put("object", map);
            pd.put("STATUS", STATUS);
            pd.put("code", "1");
            pd.put("message", MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("MESSAGE", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // =============================发送验证码=====================================
    /**
     * 发送验证码
     *
     * @param USERNAME
     *            手机号
     * @return
     */
    /*
     * @RequestMapping(value = "/sendYZM", produces = "text/html;charset=UTF-8")
     *
     * @ResponseBody public String sendYZM(String USERNAME) { logBefore(logger,
     * "发送验证码"); PageData pd = new PageData(); pd = this.getPageData(); try {
     * String yzm = String.valueOf((int) ((Math.random() * 9 + 1) * 1000));
     * System.out.println("验证码为：" + yzm); SmsBao sms = new SmsBao(); String
     * context = "验证码是【"+yzm+"】,有效时间:5分钟"; String result = sms.sendSMS(USERNAME,
     * context); Map<String, Object> map = new HashMap(); if
     * ("0".equals(result.split(",")[0])) { map.put("YZM", yzm); } else {
     * map.put("YZM", "-1"); } PageData pd_y = yzmService.findByPhone(pd); if
     * (pd_y != null) { pd_y.put("YZM", yzm); pd_y.put("TIME",
     * DateUtil.getTime()); Calendar nowTime = Calendar.getInstance(); Date
     * nowDate = (Date) nowTime.getTime(); // 得到当前时间 Calendar afterTime =
     * Calendar.getInstance(); afterTime.add(Calendar.MINUTE, 5); // 当前分钟+5 Date
     * afterDate = (Date) afterTime.getTime();
     *
     * System.out.println("今天时间" + nowDate); System.out.println( "修改后的 时间" +
     * afterDate);
     *
     * pd_y.put("ENDTIME", afterDate); yzmService.edit(pd_y); } else { PageData
     * pd2 = new PageData(); pd2.put("YZM_ID", this.get32UUID()); pd2.put("YZM",
     * yzm); pd2.put("PHONE", USERNAME); pd2.put("TIME", DateUtil.getTime());
     * Calendar nowTime = Calendar.getInstance(); Date nowDate = (Date)
     * nowTime.getTime(); // 得到当前时间 Calendar afterTime = Calendar.getInstance();
     * afterTime.add(Calendar.MINUTE, 5); // 当前分钟+5 Date afterDate = (Date)
     * afterTime.getTime(); pd2.put("ENDTIME", afterDate); yzmService.save(pd2);
     * } pd.clear(); pd.put("object", map); pd.put("code", "1");
     * pd.put("message", "正确返回数据"); } catch (Exception e) { e.printStackTrace();
     * pd.clear(); pd.put("code", "2"); pd.put("message", "程序错误,请联系系统管理员!"); }
     * ObjectMapper mapper = new ObjectMapper(); String str = null; try { str =
     * mapper.writeValueAsString(pd); } catch (JsonGenerationException e) { //
     *  e.printStackTrace(); } catch
     * (JsonMappingException e) { //
     * e.printStackTrace(); } catch (IOException e) {
     * catch block e.printStackTrace(); } return str; }
     */

    /**
     * @param ORDER_NUMBER 订单号
     * @param STATUS       1支付成功 2支付失败
     * @return
     */
    @RequestMapping(value = "/editOrderInfo", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String editOrderInfo(String ORDER_NUMBER, String STATUS, String USER_ID) {
        logBefore(logger, "修改订单状态");
        PageData pd = new PageData();
        pd = this.getPageData();
        String message = "";
        try {
            if (STATUS.equals("1")) {
                PageData od = new PageData();
                od.put("ORDER_NUMBER", ORDER_NUMBER);
                od = order_infoService.findByNumber(od);
                od.put("STATUS", "1");
                order_infoService.editStatus(od);
                od.put("PAY_DATE", DateUtil.getTime());
                order_infoService.editPayDate(od);
                List<PageData> cartS = cartService.findListStatus(od);
                PageData rema = null;
                for (int i = 0; i < cartS.size(); i++) {
                    rema = remarkService.findById(cartS.get(i));
                    int num1 = Integer.parseInt(rema.get("KUCUN").toString());//现有库存
                    int num2 = Integer.parseInt(cartS.get(i).get("NUM").toString());//购买数量
                    int yu = num1 - num2;
                    PageData dto = new PageData();
                    if (yu > 0) {
                        dto.put("KUCUN", String.valueOf(yu));
                    } else {
                        dto.put("KUCUN", "0");
                    }
                    dto.put("REMARK_ID", cartS.get(i).get("REMARK_ID").toString());
                    remarkService.editKUCUN(dto);
                }
                cartService.deleteStatus(od);
                if (!od.getString("COUPON_ID").equals("0")) {
                    couponService.editStatus(od);
                }
                List<PageData> orderList = order_ProService.findList2(od);
                for (int i = 0; i < orderList.size(); i++) {
                    orderList.get(i).put("USER_ID", od.getString("USER_ID"));
                    if (null != user_wendaQuanxianService.findQByUserId(orderList.get(i))) {
                        user_wendaQuanxianService.editPAYSTA(orderList.get(i));
                    }
                }
                message = "购买成功";
            } else {
                PageData od = new PageData();
                od.put("ORDER_NUMBER", ORDER_NUMBER);
                PageData od1 = order_infoService.findByNumber(od);
                od1.put("STATUS", "10");
                order_infoService.editStatus(od1);
                message = "购买失败";
            }
            PageData pd_rc = cartService.findCount(pd);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", message);
            pd.put("COUNT", pd_rc.get("count").toString());
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", 2);
            pd.put("message", "程序错误,请联系系统管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    @ResponseBody
    @RequestMapping(value = "/alipay", produces = "text/html;charset=UTF-8", method = {RequestMethod.POST,
            RequestMethod.GET})
    public String alipay(String ORDER_NUMBER, String MONEY) throws Exception {
        logBefore(logger, "调用支付宝");
        PageData pd1 = new PageData();
        pd1.put("ORDER_NUMBER", ORDER_NUMBER);
        pd1 = order_infoService.findByNumber(pd1);
        PageData pd = new PageData();
        /* if(MONEY.equals(pd1.getString("MONEY"))){ */
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",
                AlipayConfig.app_id, AlipayConfig.private_key, "json", "utf-8", AlipayConfig.alipay_public_key, "RSA");
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        // SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        // model.setPassbackParams(URLEncoder.encode(body.toString()));; //描述信息
        // 添加附加数据
        model.setSubject("农极客"); // 商品标题
        model.setOutTradeNo(ORDER_NUMBER); // 商家订单编号
        model.setTimeoutExpress("30m"); // 超时关闭该订单时间
        model.setTotalAmount(MONEY); // 订单总金额
        model.setProductCode("QUICK_MSECURITY_PAY"); // 销售产品码，商家和支付宝签约的产品码，为固定值QUICK_MSECURITY_PAY
        model.setBody("1");
        request.setBizModel(model);
        // request.setNotifyUrl("http://m.nongjike.cn/NJK/app/notify_url"); //
        // 回调地址
        request.setNotifyUrl("http://m.nongjike.cn/NJK/app/notify_url"); // 回调地址
        String orderStr = "";
        try {
            // 这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            orderStr = response.getBody();
            System.out.println(orderStr);// 就是orderString 可以直接给客户端请求，无需再做处理。
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        pd.put("code", "1");
        pd.put("message", "支付成功");
        // pd.put("data", json4.toString());
        pd.put("data", orderStr);
        /*
         * }else{ pd.put("code", "2"); pd.put("message", "支付异常");
         * //pd.put("data", json4.toString()); pd.put("data", ""); }
         */
        JSONObject json = JSONObject.fromObject(pd);
        System.out.println(json.toString());
        return json.toString();
        /* return orderString; */

        // String
        // a="partner="+AlipayConfig.app_id+"&seller_id=""&out_trade_no="1000000124"&total_fee="0.02"&notify_url="http://wxej.cckelifang.com/web/api/mkt/order/getAlipayResult"&service="mobile.securitypay.pay"&payment_type="1"&_input_charset="utf-8"&it_b_pay="30m"&show_url="m.alipay.com"&sign="0SON%2FcHuokUIeYLUrhcB13XbLiczU65Rz%2F5yzUEUisKxx7LupD2T85Fw2WBcti9%2FYPpcVTzf5JoK8SB20V9uPjdvjKKqNShOPUiE%2F0AwpZqE8m%2BRf3dKjaI%2BL7FsbSjgw%2BTrTGfGwfsqH0MgqYa9rjlCwnEeScGEfnMI2%2F6KTpo%3D"&sign_type="RSA""
    }

    /*
     * @ResponseBody
     *
     * @RequestMapping(value = "/callbacks.do", produces =
     * "text/html;charset=UTF-8",method={RequestMethod.POST}) public String
     * callbacks( HttpServletRequest request ) throws Exception { //接收支付宝返回的请求参数
     *
     * Map requestParams = request.getParameterMap();
     *
     * JSONObject json = JSONObject.fromObject(requestParams);
     *
     * String trade_status =
     * json.get("trade_status").toString().substring(2,json.get("trade_status").
     * toString().length()-2); String out_trade_no =
     * json.get("out_trade_no").toString().substring(2,json.get("out_trade_no").
     * toString().length()-2); String notify_id =
     * json.get("notify_id").toString().substring(2,json.get("notify_id").
     * toString().length()-2);
     *
     * System.out.println("===================================================="
     * ); System.out.println(json.toString()); System.out.println("支付宝回调地址！");
     * System.out.println("商户的订单编号：" + out_trade_no);
     * System.out.println("支付的状态：" + trade_status);
     *
     * if(trade_status.equals("TRADE_SUCCESS")) {
     *
     *//**
     * 支付成功之后的业务处理
     */

    /*
     *
     * return "SUCCESS"; }else {
     *
     */

    /**
     * 支付失败后的业务处理
     *//*
     *
     * return "SUCCESS";
     *
     * } return "SUCCESS"; }
     */
    @ResponseBody
    @RequestMapping(value = "/notify_url", produces = "text/html;charset=UTF-8", method = {RequestMethod.POST,
            RequestMethod.GET})
    public String notify_url(HttpServletRequest request) throws Exception {
        // 接收支付宝返回的请求参数
        logBefore(logger, "支付宝回调");
        Map requestParams = request.getParameterMap();

        JSONObject json = JSONObject.fromObject(requestParams);
        SmsBao sms = new SmsBao();
        String trade_status = json.get("trade_status").toString().substring(2,
                json.get("trade_status").toString().length() - 2);
        String out_trade_no = json.get("out_trade_no").toString().substring(2,
                json.get("out_trade_no").toString().length() - 2);
        String notify_id = json.get("notify_id").toString().substring(2, json.get("notify_id").toString().length() - 2);

        System.out.println("====================================================");
        System.out.println(json.toString());
        System.out.println("支付宝回调地址！");
        System.out.println("商户的订单编号：" + out_trade_no);
        System.out.println("支付的状态：" + trade_status);

        if (trade_status.equals("TRADE_SUCCESS")) {
            PageData pd = new PageData();
            pd.put("ORDER_NUMBER", out_trade_no);
            PageData od = order_infoService.findByNumber(pd);
            od.put("STATUS", "1");
            order_infoService.editStatus(od);
            od.put("PAY_DATE", DateUtil.getTime());
            order_infoService.editPayDate(od);

            //*****************增加用户积分*****************
            Double money = Double.valueOf(od.get("MONEY").toString());
            int JIF = 0;
            if (money < 5000) {
                JIF = (int) (money * 0.03);
            } else if (money >= 5000 && money < 10000) {
                JIF = (int) (money * 0.04);
            } else if (money >= 10000) {
                JIF = (int) (money * 0.05);
            }
            if (JIF > 0) {
                String today = DateUtil.getDay();
                PageData comDto = new PageData();
                comDto.put("community_type", "4");
                comDto.put("create_date", today);
                comDto.put("USER_ID", od.get("USER_ID").toString());
                pd.put("JIFEN", JIF);
                appuserService.editJifen(pd);
                appuserService.addZJIFEN(pd);
                comDto.put("community_id", od.get("ORDER_INFO_ID").toString());
                comDto.put("NUM", JIF);
                comDto.put("MIAOSHU", "购买农极客商品");
                comDto.put("ORDER_NUM", "");
                countCommunityService.save(comDto);
            }

            //*****************end******************/

            List<PageData> cartS = cartService.findListStatus(od);
            PageData rema = null;
            for (int i = 0; i < cartS.size(); i++) {
                rema = remarkService.findById(cartS.get(i));
                int num1 = Integer.parseInt(rema.get("KUCUN").toString());//现有库存
                int num2 = Integer.parseInt(cartS.get(i).get("NUM").toString());//购买数量
                int yu = num1 - num2;
                PageData dto = new PageData();
                if (yu > 0) {
                    dto.put("KUCUN", String.valueOf(yu));
                } else {
                    dto.put("KUCUN", "0");
                }
                dto.put("REMARK_ID", cartS.get(i).get("REMARK_ID").toString());
                remarkService.editKUCUN(dto);
            }
            cartService.deleteStatus(od);
            if (!od.getString("COUPON_ID").equals("0")) {
                couponService.editStatus(od);
            }
            List<PageData> orderList = order_ProService.findList2(od);
            for (int i = 0; i < orderList.size(); i++) {
                orderList.get(i).put("USER_ID", od.getString("USER_ID"));
                if (null != user_wendaQuanxianService.findQByUserId(orderList.get(i))) {
                    user_wendaQuanxianService.editPAYSTA(orderList.get(i));
                }
            }
            String text = new String();
            text = "【农极客】 支付成功通知，您在<农极客>购买的订单号为\"" + od.get("ORDER_NUMBER").toString() + "\"的商品已支付成功。查看详情请登录<农极客>App,查看\"我的订单\"。请勿相信以订单异常为由的退款要求，谨防诈骗";
            sms.sendSMS(od.get("PHONE").toString(), text);
            return "success";
        } else {
            PageData od = new PageData();
            od.put("ORDER_NUMBER", out_trade_no);
            PageData od1 = order_infoService.findByNumber(od);
            od1.put("STATUS", "10");
            order_infoService.editStatus(od1);
            return "success";
        }
    }

    /*
     * @ResponseBody
     *
     * @RequestMapping(value = "/notify_url", produces =
     * "text/html;charset=UTF-8",method ={RequestMethod.POST,RequestMethod.GET})
     * public String notify_url( HttpServletRequest request) throws Exception {
     * //接收支付宝返回的请求参数 logBefore(logger, "支付宝异步通知"); Map requestParams =
     * request.getParameterMap();
     *
     * JSONObject json = JSONObject.fromObject(requestParams); String
     * trade_status =
     * json.get("trade_status").toString().substring(2,json.get("trade_status").
     * toString().length()-2); String out_trade_no =
     * json.get("out_trade_no").toString().substring(2,json.get("out_trade_no").
     * toString().length()-2); String notify_id =
     * json.get("notify_id").toString().substring(2,json.get("notify_id").
     * toString().length()-2); String notify_url =
     * json.get("notify_url").toString().substring(2,json.get("notify_url").
     * toString().length()-2); PageData pd=new PageData();
     * System.out.println("===================================================="
     * ); System.out.println(json.toString());
     * System.out.println("支付宝回调地址！"+notify_url); System.out.println("商户的订单编号："
     * + out_trade_no); System.out.println("支付的状态：" + trade_status);
     * if(trade_status.equals("TRADE_SUCCESS")) { PageData od=new PageData();
     * od.put("ORDER_NUMBER", out_trade_no);
     * od=order_infoService.findByNumber(od); od.put("STATUS", "1");
     * order_infoService.editStatus(od); od.put("PAY_DATE", DateUtil.getTime());
     * order_infoService.editPayDate(od); cartService.deleteStatus(od); }else {
     * PageData od=new PageData(); od.put("ORDER_NUMBER", out_trade_no);
     * order_infoService.deleteOrderNumber(od); return "SUCCESS"; } return
     * "SUCCESS"; }
     */

    @RequestMapping(value = "fenxiang", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String fenxiang(String TID) {
        logBefore(logger, "分享");
        PageData pd = new PageData();
        pd = this.getPageData();
        ModelAndView mv = this.getModelAndView();
        try {
            pd.put("STATUS", "0");
            PageData pd_t = postService.findById(pd);
            List<PageData> list = comment_PostService.findList1(pd);
            for (int i = 0; i < list.size(); i++) {
                list.get(i).put("STATUS", "1");
                List<PageData> list1 = comment_PostService.findList(list.get(i));
                list.get(i).put("HUIFU", list1);
            }
            String str = DateUtil.delHTMLTag(pd_t.getString("MESSAGE"));
            String DETAILS1 = str.replace("\r", "");
            String DETAILS2 = DETAILS1.replace("\n", "");
            int qian = 56;
            if (str.length() < 56) {
                pd_t.put("JIANJIE", DETAILS2);
            } else {
                str = DETAILS2.substring(0, qian);
                pd_t.put("JIANJIE", str + "...");
            }
            if (pd_t.getString("IMG") != null) {
                pd_t.put("IMG", "http://obmmqs3o7.bkt.clouddn.com/dftheader.jpg");
            }
            List<PageData> list2 = post_ImgService.findList(pd_t);
            if (list2 != null && list2.size() != 0) {
                pd.put("POST_IMG", list2.get(0));
            } else {
                pd.put("POST_IMG", "http://m.nongjike.cn/NJK/uploadFiles/uploadImgs/logo.png");
            }
            mv.addObject("pd", pd_t);
            mv.addObject("list", list);
            mv.setViewName("system/fenxiang/fenxiang");
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", 2);
            pd.put("message", "程序错误,请联系系统管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    @RequestMapping(value = "WeiXin", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String fenxiang1(String url, String TID) {
        logBefore(logger, "微信分享配置查询");
        PageData pd = new PageData();
        pd = this.getPageData();
        String jsapiTicket = "";
        try {
            PageData pd12 = weiXinService.findById(pd);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long difference = format.parse(format.format(new Date())).getTime()
                    - format.parse(pd12.getString("DATE")).getTime();
            if (difference > 360000) { // 标签超时,重新到微信服务器请求标签超时时间为7200秒（7200000毫秒）
                String access_token = WeiXin.access_token();
                jsapiTicket = WeiXin.jsapiTicket(access_token);
                pd12.put("ticket", jsapiTicket);
                pd12.put("DATE", DateUtil.getTime());
                weiXinService.edit(pd12);
            } else {
                jsapiTicket = pd12.getString("ticket");
            }
            String nonceStr = WeiXin.createNonceStr(16);
            // 获取openId后调用统一支付接口https://api.mch.weixin.qq.com/pay/unifiedorder
            // 8位日期
            String strTime = Long.toString(new Date().getTime()).substring(0, 10);
            // String
            // url1="http://m.nongjike.cn/NJK/static/jsp/post3.html?tid="+TID;
            // WeiXinJsConfig
            // cf=WeiXin.signature(jsapiTicket,nonceStr,strTime,url);
            Map<String, String> cf = WeiXin.sign1(jsapiTicket, url);
            PageData pd1 = postService.findById(pd);
            List<PageData> list1 = post_ImgService.findList(pd);
            if (list1 != null && list1.size() != 0) {
                pd1.put("PIMG", list1.get(0).getString("IMG"));
            } else {
                pd1.put("PIMG", "http://m.nongjike.cn/NJK/uploadFiles/uploadImgs/logo.png");
            }
            String str = DateUtil.delHTMLTag(pd1.getString("MESSAGE"));
            String DETAILS1 = str.replace("\r", "");
            String DETAILS2 = DETAILS1.replace("\n", "");
            int qian = 56;
            if (str.length() < 56) {
                pd1.put("JIANJIE", DETAILS2);
            } else {
                str = DETAILS2.substring(0, qian);
                pd1.put("JIANJIE", str + "...");
            }
            pd.put("STATUS", "0");
            List<PageData> list = comment_PostService.findList1(pd);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("signature", cf.get("signature"));
            pd.put("nonceStr", cf.get("nonceStr"));
            pd.put("timestamp", cf.get("timestamp"));
            pd.put("post", pd1);
            pd.put("url", cf.get("url"));
            pd.put("appId", "wx6b912be972e69695");
            pd.put("comm", list);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    @RequestMapping(value = "ceshi", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String ceshi(String url) {
        logBefore(logger, "微信分享配置查询");
        PageData pd = new PageData();
        pd = this.getPageData();
        String jsapiTicket = "";
        try {
            PageData pd12 = weiXinService.findById(pd);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long difference = format.parse(format.format(new Date())).getTime()
                    - format.parse(pd12.getString("DATE")).getTime();
            if (difference > 360000) { // 标签超时,重新到微信服务器请求标签超时时间为7200秒（7200000毫秒）
                String access_token = WeiXin.access_token();
                jsapiTicket = WeiXin.jsapiTicket(access_token);
                pd12.put("ticket", jsapiTicket);
                pd12.put("DATE", DateUtil.getTime());
                weiXinService.edit(pd12);
            } else {
                jsapiTicket = pd12.getString("ticket");
            }
            String nonceStr = WeiXin.createNonceStr(16);
            // 获取openId后调用统一支付接口https://api.mch.weixin.qq.com/pay/unifiedorder
            // 8位日期
            String strTime = Long.toString(new Date().getTime()).substring(0, 10);
            // String
            // url1="http://m.nongjike.cn/NJK/static/jsp/post3.html?tid="+TID;
            // WeiXinJsConfig
            // cf=WeiXin.signature(jsapiTicket,nonceStr,strTime,url);
            Map<String, String> cf = WeiXin.sign1(jsapiTicket, url);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("signature", cf.get("signature"));
            pd.put("nonceStr", cf.get("nonceStr"));
            pd.put("timestamp", cf.get("timestamp"));
            pd.put("url", cf.get("url"));
            pd.put("appId", "wx6b912be972e69695");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    @RequestMapping(value = "WeiXin2", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String WeiXin2(String url, String ACTIVITY_ID) {
        logBefore(logger, "微信分享配置查询");
        PageData pd = new PageData();
        pd = this.getPageData();
        String jsapiTicket = "";
        try {
            PageData pd2 = activityService.findById(pd);
            PageData pd12 = weiXinService.findById(pd);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long difference = format.parse(format.format(new Date())).getTime()
                    - format.parse(pd12.getString("DATE")).getTime();
            if (difference > 360000) { // 标签超时,重新到微信服务器请求标签超时时间为7200秒（7200000毫秒）
                String access_token = WeiXin.access_token();
                jsapiTicket = WeiXin.jsapiTicket(access_token);
                pd12.put("ticket", jsapiTicket);
                pd12.put("DATE", DateUtil.getTime());
                weiXinService.edit(pd12);
            } else {
                jsapiTicket = pd12.getString("ticket");
            }
            List<PageData> list = postService.selectActivity(pd);
            List<PageData> list2 = activity_RoomService.findList1(pd);
            for (int i = 0; i < list2.size(); i++) {
                list2.get(i).put("COUNT", activity_PostService.findCount(list2.get(i)).get("count").toString());
            }
            Map<String, String> cf = WeiXin.sign1(jsapiTicket, url);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("signature", cf.get("signature"));
            pd.put("nonceStr", cf.get("nonceStr"));
            pd.put("timestamp", cf.get("timestamp"));
            pd.put("url", cf.get("url"));
            pd.put("appId", "wx6b912be972e69695");
            pd.put("activity", pd2);
            pd.put("comm2", list2);
            pd.put("comm1", list);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    @RequestMapping(value = "WeiXin3", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String WeiXin3(String url, String PRODUCT_ID) {
        logBefore(logger, "微信分享配置查询");
        PageData pd = new PageData();
        pd = this.getPageData();
        String jsapiTicket = "";
        try {
            PageData pd12 = weiXinService.findById(pd);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long difference = format.parse(format.format(new Date())).getTime()
                    - format.parse(pd12.getString("DATE")).getTime();
            if (difference > 360000) { // 标签超时,重新到微信服务器请求标签超时时间为7200秒（7200000毫秒）
                String access_token = WeiXin.access_token();
                jsapiTicket = WeiXin.jsapiTicket(access_token);
                pd12.put("ticket", jsapiTicket);
                pd12.put("DATE", DateUtil.getTime());
                weiXinService.edit(pd12);
            } else {
                jsapiTicket = pd12.getString("ticket");
            }
            Map<String, String> cf = WeiXin.sign1(jsapiTicket, url);
            PageData pd1 = productService.findById(pd);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("signature", cf.get("signature"));
            pd.put("nonceStr", cf.get("nonceStr"));
            pd.put("timestamp", cf.get("timestamp"));
            pd.put("product", pd1);
            pd.put("url", cf.get("url"));
            pd.put("appId", "wx6b912be972e69695");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    @RequestMapping(value = "WeiXin4", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String WeiXin4(String url, String ACTIVITY_ID, String USER_ID) {
        logBefore(logger, "微信分享配置查询");
        PageData pd = new PageData();
        pd = this.getPageData();
        String jsapiTicket = "";
        try {
            PageData pd12 = weiXinService.findById(pd);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long difference = format.parse(format.format(new Date())).getTime()
                    - format.parse(pd12.getString("DATE")).getTime();
            if (difference > 360000) { // 标签超时,重新到微信服务器请求标签超时时间为7200秒（7200000毫秒）
                String access_token = WeiXin.access_token();
                jsapiTicket = WeiXin.jsapiTicket(access_token);
                pd12.put("ticket", jsapiTicket);
                pd12.put("DATE", DateUtil.getTime());
                weiXinService.edit(pd12);
            } else {
                jsapiTicket = pd12.getString("ticket");
            }
            Map<String, String> cf = WeiXin.sign1(jsapiTicket, url);
            List<PageData> list = activity_PostService.findList3(pd);
            PageData pd2 = appuserService.findById(pd);
            for (int i = 0; i < list.size(); i++) {
                String str1 = DateUtil.delHTMLTag(list.get(i).getString("MESSAGE"));
                String DETAILS1 = str1.replace("\r", "");
                String DETAILS2 = DETAILS1.replace("\n", "");
                int qian = 200;
                if (DETAILS2.length() < 200) {
                    list.get(i).put("MESSAGE", DETAILS2);
                } else {
                    str1 = DETAILS2.substring(0, qian);
                    list.get(i).put("MESSAGE", str1 + "...");
                }
                List<PageData> list4 = activity_Post_ImgService.findList(list.get(i));
                list.get(i).put("POST_IMG", list4);
            }
            PageData pd1 = activityService.findById(pd);
            pd1.put("USER_ID", USER_ID);
            PageData pd4 = views_ActivityService.findCount(pd1);
            Integer fangwen = (Integer.valueOf(pd4.get("count").toString()));
            PageData pd5 = views_Activity_IpService.findCount(pd1);
            Integer fangwen1 = (Integer.valueOf(pd5.get("count").toString()));
            Integer fangwen2 = fangwen + fangwen1;
            pd1.put("FANGWEN", fangwen2);
            pd.put("STATUS", "0");
            List<PageData> list1 = comment_Activity_UserService.findList1(pd);

            HttpServletRequest request = this.getRequest();
            String ip = "";
            if (request.getHeader("x-forwarded-for") == null) {
                ip = request.getRemoteAddr();
            } else {
                ip =
                        request.getHeader("x-forwarded-for");
            }
            pd.put("IP", ip);
            pd.put("DATE", DateUtil.getTime());
            if (views_Activity_IpService.findById(pd) == null) {
                views_Activity_IpService.save(pd);
            }

            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("signature", cf.get("signature"));
            pd.put("nonceStr", cf.get("nonceStr"));
            pd.put("timestamp", cf.get("timestamp"));
            pd.put("url", cf.get("url"));
            pd.put("appId", "wx6b912be972e69695");
            pd.put("comm1", list);
            pd.put("comm2", list1);
            pd.put("user", pd2);
            pd.put("activity", pd1);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    @RequestMapping(value = "WeiXin5", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String WeiXin5(String url, String FID) {
        logBefore(logger, "微信分享配置查询");
        PageData pd = new PageData();
        pd = this.getPageData();
        String jsapiTicket = "";
        try {
            PageData pd12 = weiXinService.findById(pd);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long difference = format.parse(format.format(new Date())).getTime()
                    - format.parse(pd12.getString("DATE")).getTime();
            if (difference > 360000) { // 标签超时,重新到微信服务器请求标签超时时间为7200秒（7200000毫秒）
                String access_token = WeiXin.access_token();
                jsapiTicket = WeiXin.jsapiTicket(access_token);
                pd12.put("ticket", jsapiTicket);
                pd12.put("DATE", DateUtil.getTime());
                weiXinService.edit(pd12);
            } else {
                jsapiTicket = pd12.getString("ticket");
            }
            Map<String, String> cf = WeiXin.sign1(jsapiTicket, url);
            PageData pd1 = post_SpecialService.findById(pd);
            List<PageData> list = postService.findTList(pd);
            for (int i = 0; i < list.size(); i++) {
                String str = DateUtil.delHTMLTag(list.get(i).getString("MESSAGE"));
                String DETAILS1 = str.replace("\r", "");
                String DETAILS2 = DETAILS1.replace("\n", "");
                String DETAILS3 = DETAILS2.replace("&nbsp;", "");
                int qian = 56;
                if (DETAILS3.length() < 56) {
                    list.get(i).put("JIANJIE", DETAILS3);
                } else {
                    str = DETAILS3.substring(0, qian);
                    list.get(i).put("JIANJIE", str + "...");
                }
                if (list.get(i).getString("IMG") == null) {
                    list.get(i).put("IMG", "http://m.nongjike.cn/NJK/uploadFiles/uploadImgs/1.png");
                }
            }
            List<PageData> list3 = postService.findZhuan(pd);
            for (int i = 0; i < list3.size(); i++) {
                List<PageData> list4 = post_ImgService.findList(list3.get(i));
                if (list4 != null && list4.size() != 0) {
                    list3.get(i).put("SIMG", list4.get(0).getString("IMG"));
                }
                int qian = 15;
                if (list3.get(i).getString("SUBJECT").length() < 15) {
                    list3.get(i).put("SUBJECT", list3.get(i).getString("SUBJECT"));
                } else {
                    String str = list3.get(i).getString("SUBJECT").substring(0, qian);
                    list3.get(i).put("SUBJECT", str + "...");
                }
            }
            pd.put("STATUS", "0");
            List<PageData> list1 = comment_specialService.findList1(pd);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("signature", cf.get("signature"));
            pd.put("nonceStr", cf.get("nonceStr"));
            pd.put("timestamp", cf.get("timestamp"));
            pd.put("url", cf.get("url"));
            pd.put("appId", "wx6b912be972e69695");
            pd.put("comm1", list);
            pd.put("comm2", list1);
            pd.put("comm3", list3);
            pd.put("special", pd1);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    @RequestMapping(value = "WeiXinNongYao", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String WeiXinNongYao(String url, String NONGYAO_ID) {
        logBefore(logger, "农药微信分享配置查询");
        PageData pd = new PageData();
        pd = this.getPageData();
        String jsapiTicket = "";
        try {
            PageData pd12 = weiXinService.findById(pd);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long difference = format.parse(format.format(new Date())).getTime()
                    - format.parse(pd12.getString("DATE")).getTime();
            if (difference > 360000) { // 标签超时,重新到微信服务器请求标签超时时间为7200秒（7200000毫秒）
                String access_token = WeiXin.access_token();
                jsapiTicket = WeiXin.jsapiTicket(access_token);
                pd12.put("ticket", jsapiTicket);
                pd12.put("DATE", DateUtil.getTime());
                weiXinService.edit(pd12);
            } else {
                jsapiTicket = pd12.getString("ticket");
            }
            Map<String, String> cf = WeiXin.sign1(jsapiTicket, url);
            PageData pd1 = nongYaoService.findById(pd);
            if (pd1 != null) {
                if (pd1.getString("USAGE1") != "") {
                    String usage1 = pd1.getString("USAGE1");
                    // usage1=usage1.replace("82%", "");
                    usage1 = usage1.replace(
                            "BORDER-BOTTOM: medium none; BORDER-LEFT: medium none; WIDTH: 15cm; BORDER-COLLAPSE: collapse; BORDER-TOP: medium none; BORDER-RIGHT: medium none; mso-border-alt: solid windowtext .5pt; mso-padding-alt: 0cm 5.4pt 0cm 5.4pt; mso-yfti-tbllook: 480; mso-border-insideh: .5pt solid windowtext; mso-border-insidev: .5pt solid windowtext",
                            "BORDER-BOTTOM: medium none; BORDER-LEFT: medium none; BORDER-COLLAPSE: collapse; BORDER-TOP: medium none; BORDER-RIGHT: medium none; mso-border-alt: solid windowtext .5pt; mso-padding-alt: 0cm 5.4pt 0cm 5.4pt; mso-border-insideh: .5pt solid windowtext; mso-border-insidev: .5pt solid windowtext; mso-table-layout-alt: fixed");
                    usage1 = usage1.replace(
                            "BORDER-RIGHT: medium none; BORDER-TOP: medium none; BORDER-LEFT: medium none; WIDTH: 15cm; BORDER-BOTTOM: medium none; BORDER-COLLAPSE: collapse; mso-border-alt: solid windowtext .5pt; mso-padding-alt: 0cm 5.4pt 0cm 5.4pt; mso-yfti-tbllook: 480; mso-border-insideh: .5pt solid windowtext; mso-border-insidev: .5pt solid windowtext",
                            "BORDER-BOTTOM: medium none; BORDER-LEFT: medium none; BORDER-COLLAPSE: collapse; BORDER-TOP: medium none; BORDER-RIGHT: medium none; mso-border-alt: solid windowtext .5pt; mso-padding-alt: 0cm 5.4pt 0cm 5.4pt; mso-border-insideh: .5pt solid windowtext; mso-border-insidev: .5pt solid windowtext; mso-table-layout-alt: fixed");
                    usage1 = usage1.replace(
                            "BORDER-BOTTOM: medium none; BORDER-LEFT: medium none; WIDTH: 15cm; BORDER-COLLAPSE: collapse; BORDER-TOP: medium none; BORDER-RIGHT: medium none; mso-border-alt: solid windowtext .5pt; mso-yfti-tbllook: 480; mso-padding-alt: 0cm 5.4pt 0cm 5.4pt; mso-border-insideh: .5pt solid windowtext; mso-border-insidev: .5pt solid windowtext",
                            "BORDER-BOTTOM: medium none; BORDER-LEFT: medium none; BORDER-COLLAPSE: collapse; BORDER-TOP: medium none; BORDER-RIGHT: medium none; mso-border-alt: solid windowtext .5pt; mso-padding-alt: 0cm 5.4pt 0cm 5.4pt; mso-border-insideh: .5pt solid windowtext; mso-border-insidev: .5pt solid windowtext; mso-table-layout-alt: fixed");
                    pd1.put("USAGE1", usage1.subSequence(77, usage1.length()));
                    // pd1.put("USAGE1", usage1);
                }
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("signature", cf.get("signature"));
            pd.put("nonceStr", cf.get("nonceStr"));
            pd.put("timestamp", cf.get("timestamp"));
            pd.put("url", cf.get("url"));
            pd.put("appId", "wx6b912be972e69695");
            pd.put("nongyao", pd1);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    @RequestMapping(value = "WeiXinFeiLiao", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String WeiXinFeiLiao(String url, String FEILIAO_ID) {
        logBefore(logger, "农药微信分享配置查询");
        PageData pd = new PageData();
        pd = this.getPageData();
        String jsapiTicket = "";
        try {
            PageData pd12 = weiXinService.findById(pd);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long difference = format.parse(format.format(new Date())).getTime()
                    - format.parse(pd12.getString("DATE")).getTime();
            if (difference > 360000) { // 标签超时,重新到微信服务器请求标签超时时间为7200秒（7200000毫秒）
                String access_token = WeiXin.access_token();
                jsapiTicket = WeiXin.jsapiTicket(access_token);
                pd12.put("ticket", jsapiTicket);
                pd12.put("DATE", DateUtil.getTime());
                weiXinService.edit(pd12);
            } else {
                jsapiTicket = pd12.getString("ticket");
            }
            Map<String, String> cf = WeiXin.sign1(jsapiTicket, url);
            PageData pd1 = feiLiaoService.findById(pd);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("signature", cf.get("signature"));
            pd.put("nonceStr", cf.get("nonceStr"));
            pd.put("timestamp", cf.get("timestamp"));
            pd.put("url", cf.get("url"));
            pd.put("appId", "wx6b912be972e69695");
            pd.put("feiliao", pd1);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    @RequestMapping(value = "NoWeiXinNongYao", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String NoWeiXinNongYao(String url) {
        logBefore(logger, "农药微信分享配置查询");
        PageData pd = new PageData();
        pd = this.getPageData();
        String jsapiTicket = "";
        try {
            PageData pd12 = weiXinService.findById(pd);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long difference = format.parse(format.format(new Date())).getTime()
                    - format.parse(pd12.getString("DATE")).getTime();
            if (difference > 360000) { // 标签超时,重新到微信服务器请求标签超时时间为7200秒（7200000毫秒）
                String access_token = WeiXin.access_token();
                jsapiTicket = WeiXin.jsapiTicket(access_token);
                pd12.put("ticket", jsapiTicket);
                pd12.put("DATE", DateUtil.getTime());
                weiXinService.edit(pd12);
            } else {
                jsapiTicket = pd12.getString("ticket");
            }
            Map<String, String> cf = WeiXin.sign1(jsapiTicket, url);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("signature", cf.get("signature"));
            pd.put("nonceStr", cf.get("nonceStr"));
            pd.put("timestamp", cf.get("timestamp"));
            pd.put("url", cf.get("url"));
            pd.put("appId", "wx6b912be972e69695");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    @RequestMapping(value = "WeiXinSpecial", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String WeiXinSpecial(String url, String FID) {
        logBefore(logger, "微信分享配置查询");
        PageData pd = new PageData();
        pd = this.getPageData();
        String jsapiTicket = "";
        try {
            PageData pd12 = weiXinService.findById(pd);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long difference = format.parse(format.format(new Date())).getTime()
                    - format.parse(pd12.getString("DATE")).getTime();
            if (difference > 360000) { // 标签超时,重新到微信服务器请求标签超时时间为7200秒（7200000毫秒）
                String access_token = WeiXin.access_token();
                jsapiTicket = WeiXin.jsapiTicket(access_token);
                pd12.put("ticket", jsapiTicket);
                pd12.put("DATE", DateUtil.getTime());
                weiXinService.edit(pd12);
            } else {
                jsapiTicket = pd12.getString("ticket");
            }
            Map<String, String> cf = WeiXin.sign1(jsapiTicket, url);
            PageData pd1 = post_SpecialService.findById1(pd);
            List<PageData> top = postService.ZhuanTi(pd);// 查询专题最上方4个帖子
            List<PageData> list = post_Special_TypeService.findList(pd);
            for (int i = 0; i < list.size(); i++) {
                List<PageData> list2 = new ArrayList();
                if (list.size() > 1) {
                    list2 = postService.findListFid(list.get(i));
                } else {
                    list2 = postService.findListFid2(list.get(i));
                }
                for (int j = 0; j < list2.size(); j++) {
                    List<PageData> list3 = post_ImgService.findList(list2.get(j));
                    list2.get(j).put("POST_IMG", list3);
                    if (list2.get(j).getString("IMG") == null) {
                        list2.get(j).put("IMG", "http://obmmqs3o7.bkt.clouddn.com/dftheader.jpg");
                    }
                    String str = DateUtil.delHTMLTag(list2.get(j).getString("MESSAGE"));
                    String DETAILS1 = str.replace("\r", "");
                    String DETAILS2 = DETAILS1.replace("\n", "");
                    String DETAILS3 = DETAILS2.replace("&nbsp;", "");
                    int qian = 25;
                    if (DETAILS3.length() < 25) {
                        list2.get(j).put("MESSAGE", DETAILS3);
                    } else {
                        str = DETAILS3.substring(0, qian);
                        list2.get(j).put("MESSAGE", str + "...");
                    }
                    list2.get(j).put("YUE", list.get(i).getString("TYPE_NAME"));
                }
                list.get(i).put("POST", list2);

            }
            pd.put("STATUS", "0");
            List<PageData> list3 = comment_specialService.findList1(pd);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("signature", cf.get("signature"));
            pd.put("nonceStr", cf.get("nonceStr"));
            pd.put("timestamp", cf.get("timestamp"));
            pd.put("url", cf.get("url"));
            pd.put("appId", "wx6b912be972e69695");
            pd.put("list", list);
            pd.put("top", top);
            pd.put("special", pd1);
            pd.put("comm", list3);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    @RequestMapping(value = "WeiXin6", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String WeiXin6(String url, String ACTIVITY_POST_ID) {
        logBefore(logger, "微信分享配置查询");
        PageData pd = new PageData();
        pd = this.getPageData();
        String jsapiTicket = "";
        try {
            PageData pd12 = weiXinService.findById(pd);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long difference = format.parse(format.format(new Date())).getTime()
                    - format.parse(pd12.getString("DATE")).getTime();
            if (difference > 36000) { // 标签超时,重新到微信服务器请求标签超时时间为7200秒（7200000毫秒）
                String access_token = WeiXin.access_token();
                jsapiTicket = WeiXin.jsapiTicket(access_token);
                pd12.put("ticket", jsapiTicket);
                pd12.put("DATE", DateUtil.getTime());
                weiXinService.edit(pd12);
            } else {
                jsapiTicket = pd12.getString("ticket");
            }
            PageData pd1 = activity_PostService.findById(pd);
            List<PageData> list1 = activity_Post_ImgService.findList(pd);
            if (list1.size() != 0 && list1 != null) {
                pd1.put("PIMG", list1.get(0).getString("IMG"));
            }
            String str = DateUtil.delHTMLTag(pd1.getString("MESSAGE"));
            String DETAILS1 = str.replace("\r", "");
            String DETAILS2 = DETAILS1.replace("\n", "");
            String DETAILS3 = DETAILS2.replace("&nbsp;", "");
            int qian = 56;
            if (DETAILS3.length() < 56) {
                pd1.put("JIANJIE", DETAILS3);
            } else {
                str = DETAILS3.substring(0, qian);
                pd1.put("JIANJIE", str + "...");
            }
            if (pd1.getString("IMG") == null) {
                pd1.put("IMG", "http://obmmqs3o7.bkt.clouddn.com/dftheader.jpg");
            }
            PageData pd4 = views_ActivityService.findCount(pd1);
            Integer fangwen = (Integer.valueOf(pd4.get("count").toString()));
            PageData pd5 = views_Activity_IpService.findCount(pd1);
            Integer fangwen1 = (Integer.valueOf(pd5.get("count").toString()));
            Integer fangwen2 = fangwen + fangwen1;
            pd1.put("FANGWEN", fangwen2);
            Map<String, String> cf = WeiXin.sign1(jsapiTicket, url);
            pd.put("STATUS", "0");
            List<PageData> list = comment_ActivityService.findList1(pd);

            HttpServletRequest request = this.getRequest();
            String ip = "";
            if (request.getHeader("x-forwarded-for") == null) {
                ip = request.getRemoteAddr();
            } else {
                ip = request.getHeader("x-forwarded-for");
            }
            pd1.put("IP", ip);
            if (views_Activity_IpService.findById(pd1) == null) {
                views_Activity_IpService.save(pd1);
            }

            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("signature", cf.get("signature"));
            pd.put("nonceStr", cf.get("nonceStr"));
            pd.put("timestamp", cf.get("timestamp"));
            pd.put("url", cf.get("url"));
            pd.put("appId", "wx6b912be972e69695");
            pd.put("comm", list);
            pd.put("activity_Post", pd1);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    @RequestMapping(value = "WeiXin7", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String WeiXin7(String url, String TIWEN_ID) {
        logBefore(logger, "微信分享配置查询");
        PageData pd = new PageData();
        pd = this.getPageData();
        String jsapiTicket = "";
        try {
            PageData pd12 = weiXinService.findById(pd);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long difference = format.parse(format.format(new Date())).getTime()
                    - format.parse(pd12.getString("DATE")).getTime();
            if (difference > 360000) { // 标签超时,重新到微信服务器请求标签超时时间为7200秒（7200000毫秒）
                String access_token = WeiXin.access_token();
                jsapiTicket = WeiXin.jsapiTicket(access_token);
                pd12.put("ticket", jsapiTicket);
                pd12.put("DATE", DateUtil.getTime());
                weiXinService.edit(pd12);
            } else {
                jsapiTicket = pd12.getString("ticket");
            }
            PageData pd1 = tiWenService.findById(pd);
            List<PageData> list1 = tiWen_ImgService.findList(pd);
            if (list1.size() != 0 && list1 != null) {
                pd1.put("PIMG", list1.get(0).getString("IMG"));
                pd1.put("TIMG", list1);
            }
            int qian = 56;
            if (pd1.getString("MESSAGE").length() < 56) {
                pd1.put("JIANJIE", pd1.getString("MESSAGE"));
            } else {
                String str = pd1.getString("MESSAGE").substring(0, qian);
                pd1.put("JIANJIE", str + "...");
            }
            if (pd1.getString("IMG") == null) {
                pd1.put("IMG", "http://obmmqs3o7.bkt.clouddn.com/dftheader.jpg");
            }
            Map<String, String> cf = WeiXin.sign1(jsapiTicket, url);
            pd.put("STATUS", "0");
            List<PageData> list = comment_TiwenService.findList1(pd);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("signature", cf.get("signature"));
            pd.put("nonceStr", cf.get("nonceStr"));
            pd.put("timestamp", cf.get("timestamp"));
            pd.put("url", cf.get("url"));
            pd.put("appId", "wx6b912be972e69695");
            pd.put("comm", list);
            pd.put("tiwen", pd1);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    @RequestMapping(value = "WeiXin8", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String WeiXin8(String url) {
        logBefore(logger, "微信分享配置查询");
        PageData pd = new PageData();
        pd = this.getPageData();
        String jsapiTicket = "";
        try {
            PageData pd12 = weiXinService.findById(pd);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long difference = format.parse(format.format(new Date())).getTime()
                    - format.parse(pd12.getString("DATE")).getTime();
            if (difference > 360000) { // 标签超时,重新到微信服务器请求标签超时时间为7200秒（7200000毫秒）
                String access_token = WeiXin.access_token();
                jsapiTicket = WeiXin.jsapiTicket(access_token);
                pd12.put("ticket", jsapiTicket);
                pd12.put("DATE", DateUtil.getTime());
                weiXinService.edit(pd12);
            } else {
                jsapiTicket = pd12.getString("ticket");
            }
            Map<String, String> cf = WeiXin.sign1(jsapiTicket, url);
            pd.put("STATUS", "0");
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("signature", cf.get("signature"));
            pd.put("nonceStr", cf.get("nonceStr"));
            pd.put("timestamp", cf.get("timestamp"));
            pd.put("url", cf.get("url"));
            pd.put("appId", "wx6b912be972e69695");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    @RequestMapping(value = "findFPostId", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findFPostId(String TID) {
        logBefore(logger, "分享查询帖子");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            PageData pd1 = postService.findById(pd);
            String str = DateUtil.delHTMLTag(pd1.getString("MESSAGE"));
            String DETAILS1 = str.replace("\r", "");
            String DETAILS2 = DETAILS1.replace("\n", "");
            String DETAILS3 = DETAILS2.replace("&nbsp;", "");
            int qian = 56;
            if (DETAILS3.length() < 56) {
                pd1.put("JIANJIE", DETAILS3);
            } else {
                str = DETAILS3.substring(0, qian);
                pd1.put("JIANJIE", str + "...");
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("object", pd1);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    @RequestMapping(value = "Edition")
    @ResponseBody
    public String Edition(String version) {
        logBefore(logger, "更新版本");
        PageData pd = new PageData();
        Map<String, Object> map = new HashedMap();
        String code = "200";
        try {
            if (!version.equals("5.21")) {
                String url = "";
                String msg = "";
                url = "http://m.nongjike.cn/NJK/static/jsp/nongjike.apk";
                msg = "优化系统,修复部分BUG.";
                map.put("url", url);
                map.put("message", msg);
                code = "400";
            } else {
                code = "200";
            }
            pd.clear();
            pd.put("code", code);
            pd.put("STATUS", "1");
            if (!code.equals("200")) {
                pd.put("data", map);
            }
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "200");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    @RequestMapping(value = "TIANCHOU", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String TIANCHOU() {
        logBefore(logger, "更新版本");
        PageData pd = new PageData();
        Map<String, Object> map = new HashedMap();
        String code = "200";
        try {
            List<PageData> list = appUserInfoService.findList(pd);
            for (int i = 0; i < list.size(); i++) {
                appuserService.editTianChou(list.get(i));
            }
            pd.put("code", "200");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "200");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    @RequestMapping(value = "JiLogin", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String JiLogin(String USER_ID) {
        logBefore(logger, "今天登录人数");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            PageData pd_l = new PageData();
            pd_l.put("LOGIN_RECORD_ID", this.get32UUID());
            pd_l.put("DATE", DateUtil.getDay());
            pd_l.put("NIAN1", DateUtil.getYear());
            Calendar calendar = Calendar.getInstance();
            // 获得当前时间的月份，月份从0开始所以结果要加1
            int month = calendar.get(Calendar.MONTH) + 1;
            pd_l.put("YUE", month);
            pd_l.put("USER_ID", USER_ID);
            pd_l.put("TIME", DateUtil.getTime());
            login_recordService.save(pd_l);
            pd.clear();
            pd.put("IMG", bannerService.findStatus(pd).getString("PICTURE"));
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    @RequestMapping(value = "/wxPay_snatch", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String wxPay_snatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logBefore(logger, "微信支付");
        String partner = "1482116722";
        String appid = "wx3e8ef0db80a0580f";
        String appsecret = "278fc286264c9d8a96571752f88c3eb7";
        String partnerkey = "5BCB0B1B9455219FF9628FA9DED938A2";
        String ORDER_NUMBER = request.getParameter("ORDER_NUMBER");
        String MONEY = request.getParameter("MONEY");
        String code = "1";
        String flag = "1";

        // 获取openId后调用统一支付接口https://api.mch.weixin.qq.com/pay/unifiedorder
        String currTime = TenpayUtil.getCurrTime();
        // 8位日期
        String strTime = currTime.substring(8, currTime.length());
        // 四位随机数
        String strRandom = TenpayUtil.buildRandom(4) + "";
        // 10位序列号,可以自行调整。
        String strReq = strTime + strRandom;

        // 商户号
        String mch_id = partner;
        // 随机数
        String nonce_str = strReq;
        // 商品描述根据情况修改
        String body = "农极客";
        // 商户订单号
        String out_trade_no = ORDER_NUMBER;
        // 总金额以分为单位，不带小数点
        // Integer total_fee1=Integer.valueOf(MONEY)*100;
        String total_fee = MONEY;
        // 订单生成的机器 IP
        String spbill_create_ip = request.getRemoteAddr();
        // 这里notify_url是 支付完成后微信发给该链接信息，可以判断会员是否支付成功，改变订单状态等。
        String notify_url = "http://m.nongjike.cn/NJK/app/wxPayNotify";
        String trade_type = "APP";

        SortedMap<String, String> packageParams = new TreeMap<String, String>();
        packageParams.put("appid", appid);
        packageParams.put("attach", flag);
        packageParams.put("mch_id", mch_id);
        packageParams.put("nonce_str", nonce_str);
        packageParams.put("body", body);
        packageParams.put("out_trade_no", out_trade_no);
        packageParams.put("total_fee", total_fee);
        packageParams.put("spbill_create_ip", spbill_create_ip);
        packageParams.put("notify_url", notify_url);
        packageParams.put("trade_type", trade_type);

        RequestHandler reqHandler = new RequestHandler(request, response);
        reqHandler.init(appid, appsecret, partnerkey);

        String sign = reqHandler.createSign(packageParams);
        String xml = "<xml>" + "<appid>" + appid + "</appid>" + "<attach>" + flag + "</attach>" + "<mch_id>" + mch_id
                + "</mch_id>" + "<nonce_str>" + nonce_str + "</nonce_str>" + "<sign>" + sign + "</sign>" +
                // "<body>"+body+"</body>"+
                "<body><![CDATA[" + body + "]]></body>" + "<out_trade_no>" + out_trade_no + "</out_trade_no>"
                + "<total_fee>" + total_fee + "</total_fee>" + "<spbill_create_ip>" + spbill_create_ip
                + "</spbill_create_ip>" + "<notify_url>" + notify_url + "</notify_url>" + "<trade_type>" + trade_type
                + "</trade_type>" + "</xml>";
        System.out.println(xml);
        String createOrderURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
        String prepay_id = "";
        try {
            prepay_id = new GetWxOrderno().getPayNo(createOrderURL, xml);
            System.out.println(prepay_id);
            if (prepay_id.equals("")) {
                // request.setAttribute("ErrorMsg", "统一支付接口获取预支付订单出错");
                System.out.println("ErrorMsg  统一支付接口获取预支付订单出错");
                // response.sendRedirect("error.jsp");
                code = "2";
            }
        } catch (Exception e1) {
            //
            e1.printStackTrace();
        }

        SortedMap<String, String> finalpackage = new TreeMap<String, String>();
        String appid2 = appid;
        String timestamp = Sha1Util.getTimeStamp();
        String nonceStr2 = nonce_str;
        String prepay_id2 = "prepay_id=" + prepay_id;
        String packages = prepay_id;
        finalpackage.put("appId", appid2);
        finalpackage.put("timeStamp", timestamp);
        finalpackage.put("nonceStr", nonceStr2);
        finalpackage.put("package", packages);
        finalpackage.put("signType", "MD5");
        finalpackage.put("code", code);
        String finalsign = reqHandler.createSign(finalpackage);
        System.out.println(finalsign);
        finalpackage.put("sign", finalsign);
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(finalpackage);
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

    @RequestMapping(value = "/wxPay_snatch1", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String wxPay_snatch1(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logBefore(logger, "微信支付");
        String partner = "1482116722";
        String appid = "wx3e8ef0db80a0580f";
        String appsecret = "278fc286264c9d8a96571752f88c3eb7";
        String partnerkey = "5BCB0B1B9455219FF9628FA9DED938A2";
        String ORDER_NUMBER = request.getParameter("ORDER_NUMBER");
        String MONEY = request.getParameter("MONEY");
        String code = "1";
        String flag = "1";

        // 获取openId后调用统一支付接口https://api.mch.weixin.qq.com/pay/unifiedorder
        String currTime = TenpayUtil.getCurrTime();
        // 8位日期
        String strTime = currTime.substring(8, currTime.length());
        // 四位随机数
        String strRandom = TenpayUtil.buildRandom(4) + "";
        // 10位序列号,可以自行调整。
        String strReq = strTime + strRandom;

        // 商户号
        String mch_id = partner;
        // 随机数
        String nonce_str = strReq;
        // 商品描述根据情况修改
        String body = "农极客";
        // 商户订单号
        String out_trade_no = ORDER_NUMBER;
        // 总金额以分为单位，不带小数点
        // Integer total_fee1=Integer.valueOf(MONEY)*100;
        String total_fee = MONEY;
        // 订单生成的机器 IP
        String spbill_create_ip = request.getRemoteAddr();
        // 这里notify_url是 支付完成后微信发给该链接信息，可以判断会员是否支付成功，改变订单状态等。
        String notify_url = "http://m.nongjike.cn/NJK/app/wxPayNotify";
        String trade_type = "APP";

        SortedMap<String, String> packageParams = new TreeMap<String, String>();
        packageParams.put("appid", appid);
        packageParams.put("attach", flag);
        packageParams.put("mch_id", mch_id);
        packageParams.put("nonce_str", nonce_str);
        packageParams.put("body", body);
        packageParams.put("out_trade_no", out_trade_no);
        packageParams.put("total_fee", total_fee);
        packageParams.put("spbill_create_ip", spbill_create_ip);
        packageParams.put("notify_url", notify_url);
        packageParams.put("trade_type", trade_type);

        RequestHandler reqHandler = new RequestHandler(request, response);
        reqHandler.init(appid, appsecret, partnerkey);

        String sign = reqHandler.createSign(packageParams);
        String xml = "<xml>" + "<appid>" + appid + "</appid>" + "<attach>" + flag + "</attach>" + "<mch_id>" + mch_id
                + "</mch_id>" + "<nonce_str>" + nonce_str + "</nonce_str>" + "<sign>" + sign + "</sign>" +
                // "<body>"+body+"</body>"+
                "<body><![CDATA[" + body + "]]></body>" + "<out_trade_no>" + out_trade_no + "</out_trade_no>"
                + "<total_fee>" + total_fee + "</total_fee>" + "<spbill_create_ip>" + spbill_create_ip
                + "</spbill_create_ip>" + "<notify_url>" + notify_url + "</notify_url>" + "<trade_type>" + trade_type
                + "</trade_type>" + "</xml>";
        System.out.println(xml);
        String createOrderURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
        String prepay_id = "";
        try {
            prepay_id = new GetWxOrderno().getPayNo(createOrderURL, xml);
            System.out.println(prepay_id);
            if (prepay_id.equals("")) {
                // request.setAttribute("ErrorMsg", "统一支付接口获取预支付订单出错");
                System.out.println("ErrorMsg  统一支付接口获取预支付订单出错");
                // response.sendRedirect("error.jsp");
                code = "2";
            }
        } catch (Exception e1) {
            //
            e1.printStackTrace();
        }

        SortedMap<String, String> finalpackage = new TreeMap<String, String>();
        String appid2 = appid;
        String timestamp = Sha1Util.getTimeStamp();
        String nonceStr2 = nonce_str;
        String prepay_id2 = "prepay_id=" + prepay_id;
        String packages = prepay_id;
        finalpackage.put("appId", appid2);
        finalpackage.put("timeStamp", timestamp);
        finalpackage.put("nonceStr", nonceStr2);
        finalpackage.put("package", packages);
        finalpackage.put("signType", "MD5");
        finalpackage.put("code", code);
        SortedMap<String, String> finalpackage1 = new TreeMap<String, String>();
        finalpackage1.put("appid", appid);
        finalpackage1.put("timestamp", timestamp);
        finalpackage1.put("noncestr", nonce_str);
        finalpackage1.put("partnerid", partner);
        finalpackage1.put("package", "Sign=WXPay");
        finalpackage1.put("prepayid", prepay_id);
        String finalsign1 = reqHandler.createSign(finalpackage1);
        System.out.println(finalsign1);

        finalpackage.put("sign", finalsign1);
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(finalpackage);
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

    @RequestMapping(value = "/wxPayNotify", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String wxPayNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logBefore(logger, "微信回调");
        BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream()));
        String line = null;
        StringBuilder sb = new StringBuilder();
        SmsBao sms = new SmsBao();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        System.out.println(sb);
        Map<String, String> map = GetWxOrderno.doXMLParse(sb.toString());
        String attach = map.get("attach");
        String result_code = map.get("result_code");
        String ORDER_NUMBER = map.get("out_trade_no");
        String xml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                + "<return_msg><![CDATA[签名失败]]></return_msg>" + "</xml>";
        if ("SUCCESS".equals(result_code)) {
            PageData od = new PageData();
            od.put("ORDER_NUMBER", ORDER_NUMBER);
            od = order_infoService.findByNumber(od);
            od.put("STATUS", "1");
            order_infoService.editStatus(od);

            //*****************增加用户积分*****************
            Double money = Double.valueOf(od.get("MONEY").toString());
            int JIF = 0;
            if (money < 5000) {
                JIF = (int) (money * 0.03);
            } else if (money >= 5000 && money < 10000) {
                JIF = (int) (money * 0.04);
            } else if (money >= 10000) {
                JIF = (int) (money * 0.05);
            }
            if (JIF > 0) {
                String today = DateUtil.getDay();
                PageData comDto = new PageData();
                comDto.put("community_type", "4");
                comDto.put("create_date", today);
                comDto.put("USER_ID", od.get("USER_ID").toString());
                od.put("JIFEN", JIF);
                appuserService.editJifen(od);
                appuserService.addZJIFEN(od);
                comDto.put("community_id", od.get("ORDER_INFO_ID").toString());
                comDto.put("NUM", JIF);
                comDto.put("MIAOSHU", "购买农极客商品");
                comDto.put("ORDER_NUM", "");
                countCommunityService.save(comDto);
            }
            /*Double money = Double.valueOf(od.get("MONEY").toString());
            int JIF= (int)(money/20);
            od.put("JIFEN",JIF);
            appuserService.editJifen(od);*/
            //*****************end******************

            od.put("PAY_DATE", DateUtil.getTime());
            order_infoService.editPayDate(od);
            List<PageData> cartS = cartService.findListStatus(od);
            PageData rema = null;
            for (int i = 0; i < cartS.size(); i++) {
                rema = remarkService.findById(cartS.get(i));
                int num1 = Integer.parseInt(rema.get("KUCUN").toString());//现有库存
                int num2 = Integer.parseInt(cartS.get(i).get("NUM").toString());//购买数量
                int yu = num1 - num2;
                PageData dto = new PageData();
                if (yu > 0) {
                    dto.put("KUCUN", String.valueOf(yu));
                } else {
                    dto.put("KUCUN", "0");
                }
                dto.put("REMARK_ID", cartS.get(i).get("REMARK_ID").toString());
                remarkService.editKUCUN(dto);
            }
            cartService.deleteStatus(od);
            if (!od.getString("COUPON_ID").equals("0")) {
                couponService.editStatus(od);
            }
            List<PageData> orderList = order_ProService.findList2(od);
            for (int i = 0; i < orderList.size(); i++) {
                orderList.get(i).put("USER_ID", od.getString("USER_ID"));
                if (null != user_wendaQuanxianService.findQByUserId(orderList.get(i))) {
                    user_wendaQuanxianService.editPAYSTA(orderList.get(i));
                }
            }
            String text = new String();
            text = "【农极客】 支付成功通知，您在<农极客>购买的订单号为\"" + od.get("ORDER_NUMBER").toString() + "\"的商品已支付成功。查看详情请登录<农极客>App,查看\"我的订单\"。请勿相信以订单异常为由的退款要求，谨防诈骗";
            sms.sendSMS(od.get("PHONE").toString(), text);
            System.out.println("~~~~~~~~~~~~~~~~付款成功~~~~~~~~~");
            xml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>" + "<return_msg><![CDATA[OK]]></return_msg>"
                    + "</xml>";
        } else {
            PageData od = new PageData();
            od.put("ORDER_NUMBER", ORDER_NUMBER);
            PageData od1 = order_infoService.findByNumber(od);
            od1.put("STATUS", "10");
            order_infoService.editStatus(od1);
        }
        return xml;
    }

    // =============================通过code换取网页授权access_token==================================

    /**
     * 通过code换取网页授权access_token
     *
     * @param code state 重定向后会带上state参数，开发者可以填写a-zA-Z0-9的参数值，最多128字节
     * @return
     */
    @RequestMapping(value = "/access_token", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public synchronized String access_token(String code, String state) {
        logBefore(logger, "获取授权access_token");
        logBefore(logger, "code值" + code);
        PageData pd = new PageData();
        pd = this.getPageData();
        if (code == null || code.length() == 0) { // 缺少参数值
            pd.clear();
            pd.put("code", 0);
            pd.put("message", "缺少参数值code");
        } else {
            URL url = null;
            try {
                String CODE = code;
                url = new URL(
                        "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx3f6627be4ed503c9&secret=5080ea88e4197c91844e2aa76c971cb5&code="
                                + CODE + "&grant_type=authorization_code");
                BufferedReader in = null;
                String inputLine = "";
                try {
                    in = new BufferedReader(new InputStreamReader(url.openStream()));
                    inputLine = new String(in.readLine());
                } catch (Exception e) {
                    System.out.println("错误");
                    inputLine = "-1";
                }
                logBefore(logger, "返回值====================>" + inputLine);
                System.out.println("返回值====================>" + inputLine);
                org.json.JSONObject json = new org.json.JSONObject(inputLine);
                String openid = json.getString("openid");
                String access_token = json.getString("access_token");
                // String refresh_token=json.getString("refresh_token");
                System.out.println("openid" + openid);
                pd.put("USER_ID", openid);
                pd.clear();
                pd.put("object", openid);
                pd.put("access_token", access_token);
                // pd.put("refresh_token", refresh_token);
                pd.put("code", 1);
                pd.put("message", "正确返回数据");
            } catch (Exception e) {
                e.printStackTrace();
                pd.clear();
                pd.put("code", 2);
                pd.put("message", "程序错误,请联系系统管理员!");
            }
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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
     * @param accessToken 接口访问凭证
     * @param openId      用户标识
     * @return WeixinUserInfo
     */
    public String getUserInfo(String accessToken, String openId) {
        // 拼接请求地址
        PageData pd = new PageData();
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID";
        requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
        // 获取用户信息
        JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);
        if (null != jsonObject) {
            try {
                pd.put("code", "1");
                pd.put("message", "正确返回数据!");
                pd.put("data", jsonObject);
            } catch (Exception e) {
                pd.put("code", "2");
                pd.put("message", "获取失败!");
            }
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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
        System.out.println("用户信息" + str);
        return str;
    }

    // =============================微信开团=================================
    @RequestMapping(value = "deleteWFight", produces = "text/html;charset=UTF-8")
    @ResponseBody
    String deleteWFight(String FIGHT_ID) {
        logBefore(logger, "支付失败");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            pd.put("STATUS", "10");
            fightService.edit(pd);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // =============================微信开团=================================
    @RequestMapping(value = "saveWFight", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String saveWFight(String NAME, String PHONE, String ADDRESS, String USERNAME, String PRODUCT, String MONEY,
                             String OPENID, String IMG, String PRICE) {
        logBefore(logger, "微信开团");
        PageData pd = new PageData();
        pd = this.getPageData();
        String USER_ID = "";
        Integer MONEY1 = 0;
        String prod = PRODUCT.substring(0, PRODUCT.length() - 1);
        try {
            PageData pd_u = appuserService.findByUserName(pd);
            if (pd_u != null) {
                USER_ID = pd_u.getString("USER_ID");
                pd_u.put("OPENID", OPENID);
                appuserService.editOpenId(pd_u);
            } else {
                USER_ID = this.get32UUID();
                new Thread(new Register(USER_ID, USERNAME, IMG, OPENID, NAME, appuserService, appUserInfoService))
                        .start();
            }
            Date now = new Date();
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            String s = String.valueOf((int) ((Math.random() * 9 + 1) * 100));
            String no = s + sdf1.format(now).substring(2); // 订单号
            PageData pd_f = new PageData();
            String FIGHT_ID = this.get32UUID();
            PageData pd_ff = new PageData();
            pd_ff.put("FIGHT_ID", FIGHT_ID);
            pd_ff.put("USER_ID", USER_ID);
            pd_ff.put("NAME", NAME);
            pd_ff.put("DATE", DateUtil.getTime());
            Calendar afterTime = Calendar.getInstance();
            afterTime.add(Calendar.DATE, 1); // 当前分钟+5
            Date afterDate = (Date) afterTime.getTime();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateString = formatter.format(afterDate);
            pd_ff.put("PDATE", dateString);
            pd_ff.put("STATUS", "0");
            int max = 3;
            int min = 1;
            Random random = new Random();
            int s1 = random.nextInt(max) % (max - min + 1) + min;
            pd_ff.put("STATUS1", s1);
            fightService.save(pd_ff);
            pd_f.put("FIGHT_INFO_ID", this.get32UUID());
            pd_f.put("USER_ID", USER_ID);
            pd_f.put("PRODUCT", prod); // java转码 );
            pd_f.put("DATE", DateUtil.getTime());
            pd_f.put("FIGHT_ID", FIGHT_ID);
            pd_f.put("ADDRESS", ADDRESS);
            pd_f.put("ORDER_NUMBER", no);
            pd_f.put("PHONE", USERNAME);
            pd_f.put("STATUS", "0");
            pd_f.put("PAY_DATE", "");
            pd_f.put("PAY_METHOD", "wx");
            pd_f.put("MONEY", MONEY);
            pd_f.put("NAME", NAME);
            pd_f.put("OPENID", OPENID);
            pd_f.put("STATUS1", "1"); // 微信下单
            pd_f.put("KUAIDI", "");
            pd_f.put("NUMBER", "");
            pd_f.put("YMONEY", MONEY);
            fight_InfoService.save(pd_f);
            pd.clear();
            pd.put("FIGHT_ID", FIGHT_ID);
            pd.put("ORDER_NUMBER", no);
            pd.put("PRODUCT", prod);
            pd.put("MONEY", MONEY);
            pd.put("YMONEY", MONEY1);
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // =============================微信图谱====================================
    @RequestMapping(value = "/saveOpin", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public synchronized String saveOpin(String NAME, String PHONE, String ADDRESS, String USERNAME, String PRODUCT,
                                        String MONEY, String OPENID, String FIGHT_ID, String IMG) {
        logBefore(logger, "微信图谱");
        PageData pd = new PageData();
        pd = this.getPageData();
        String USER_ID = "";
        String prod = PRODUCT.substring(0, PRODUCT.length() - 1);
        try {
            if (Integer.valueOf(fight_InfoService.findCount(pd).get("count").toString()) < 3) {
                PageData pd_u = appuserService.findByUserName(pd);
                if (pd_u != null) {
                    USER_ID = pd_u.getString("USER_ID");
                    pd_u.put("OPENID", OPENID);
                    appuserService.editOpenId(pd_u);
                } else {
                    USER_ID = this.get32UUID();
                    new Thread(new Register(USER_ID, USERNAME, IMG, OPENID, NAME, appuserService, appUserInfoService))
                            .start();
                }
                Date now = new Date();
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
                String s = String.valueOf((int) ((Math.random() * 9 + 1) * 100));
                String no = s + sdf1.format(now).substring(2); // 订单号
                PageData pd_f = new PageData();
                pd_f.put("FIGHT_INFO_ID", this.get32UUID());
                pd_f.put("USER_ID", USER_ID);
                pd_f.put("PRODUCT", prod);
                pd_f.put("DATE", DateUtil.getTime());
                pd_f.put("FIGHT_ID", FIGHT_ID);
                pd_f.put("ADDRESS", ADDRESS);
                pd_f.put("ORDER_NUMBER", no);
                pd_f.put("PHONE", USERNAME);
                pd_f.put("STATUS", "0");
                pd_f.put("PAY_DATE", "");
                pd_f.put("PAY_METHOD", "wx");
                pd_f.put("MONEY", MONEY);
                pd_f.put("NAME", NAME);
                pd_f.put("OPENID", OPENID);
                pd_f.put("STATUS1", "1"); // 微信下单
                pd_f.put("KUAIDI", "");
                pd_f.put("NUMBER", "");
                pd_f.put("YMONEY", MONEY);
                fight_InfoService.save(pd_f);
                pd.clear();
                pd.put("FIGHT_ID", FIGHT_ID);
                pd.put("ORDER_NUMBER", no);
                pd.put("PRODUCT", prod);
                pd.put("MONEY", MONEY);
                pd.put("YMONEY", MONEY);
                pd.put("code", "1");
                pd.put("message", "正确返回数据!");
            } else {
                pd.put("code", "3");
                pd.put("message", "人数以够!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // ==============================查询本团的人数=================================
    @RequestMapping(value = "findFightNum", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findFightNum(String FIGHT_ID, String OPENID) {
        logBefore(logger, "查询本团人数");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            PageData pd_ff = fightService.findById(pd);
            if (pd_ff.getString("STATUS").equals("0") || pd_ff.getString("STATUS").equals("1")) {
                pd.put("STATUS", "1");
                List<PageData> list = fight_InfoService.findList(pd);
                PageData pd_i = integralService.findById(pd);
                List<PageData> list1 = fight_InfoService.findListOpenId(pd);
                PageData pd_f = fight_InfoService.findByUIdOId(pd);
                if (pd_f != null) {
                    pd_i.put("OSTATUS", "1");
                } else {
                    pd_i.put("OSTATUS", "0");
                }
                if (list.size() == Integer.valueOf(pd_i.getString("NUM"))) {
                    pd_ff.put("STATUS", "1");
                    fightService.edit(pd_ff);
                }
                int count = DateUtil.compare_date(pd_ff.getString("PDATE"), DateUtil.getTime());
                if (count != 1) {
                    if (pd_ff.getString("STATUS").equals("0")) {
                        pd_ff.put("STATUS", "2");
                        fightService.edit(pd_ff);
                    }
                }
                pd.clear();
                pd.put("data", list);
                pd.put("user", list1);
                pd.put("integral", pd_i);
                pd.put("count", count);
                pd.put("code", "1");
                pd.put("message", "正确返回数据!");

            } else {
                pd.clear();
                pd.put("code", "3");
                pd.put("message", "本次团购已经失败,请重新开团!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    @RequestMapping(value = "findFight", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findFight(String USER_ID, String pageNum) {
        logBefore(logger, "查询开团");
        PageData pd = new PageData();
        pd = this.getPageData();
        Page page = new Page();
        String message = "正确返回数据!";
        try {
            if (pageNum == null || pageNum.length() == 0) {
                pageNum = "1";
            }
            page.setPd(pd);
            page.setShowCount(5);
            page.setCurrentPage(Integer.parseInt(pageNum));
            List<PageData> list = fight_InfoService.datalistPage(page);
            Map<String, Object> map = new HashedMap();
            if (page.getCurrentPage() == Integer.parseInt(pageNum)) {
                map.put("object", list);
            } else {
                List<PageData> list4 = new ArrayList();
                message = "已加载全部数据!";
                map.put("object", list4);
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("message", message);
            pd.put("data", map);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // ==============================开团=========================================
    @RequestMapping(value = "/saveFight", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String saveFight(String USER_ID, String PRODUCT, String ADDRESS, String MONEY, String PAY_METHOD,
                            String PHONE, String NAME) {
        logBefore(logger, "开团");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            String FIGHT_ID = this.get32UUID();
            pd.put("FIGHT_ID", FIGHT_ID);
            pd.put("DATE", DateUtil.getTime());
            pd.put("STATUS", "0");
            fightService.save(pd);
            String FIGHT_INFO_ID = this.get32UUID();
            Date now = new Date();
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            String s = String.valueOf((int) ((Math.random() * 9 + 1) * 100));
            String no = s + sdf1.format(now).substring(2); // 订单号
            pd.put("FIGHT_INFO_ID", FIGHT_INFO_ID);
            pd.put("ORDER_NUMBER", no);
            pd.put("STATUS", "0");
            pd.put("PAY_DATE", "");
            pd.put("OPENID", "");
            pd.put("STATUS1", "0"); // app下单
            pd.put("KUAIDI", "");
            pd.put("NUMBER", "");
            pd.put("YMONEY", "");
            fight_InfoService.save(pd);
            pd.clear();
            pd.put("FIGHT_ID", FIGHT_ID);
            pd.put("FIGHT_INFO_ID", FIGHT_INFO_ID);
            pd.put("ORDER_NUMBER", no);
            pd.put("MONEY", MONEY);
            pd.put("URL", "http://m.nongjike.cn/nongjike/tushu.html?FIGHT_ID=" + FIGHT_ID);
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    @RequestMapping(value = "findIntegral", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findIntegral(String USER_ID) {
        logBefore(logger, "查询图谱");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            List<PageData> list = fight_InfoService.findListUserId(pd);
            PageData pd1 = integralService.findById(pd);
            List<PageData> list1 = integral_InfoService.findList(pd);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("TUPU", pd1);
            pd.put("TUPU_INFO", list1);
            if (list != null && list.size() != 0) {
                pd.put("USTATUS", "1");
            } else {
                pd.put("USTATUS", "0");
            }
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // =========================查询团购订单详情======================================
    @RequestMapping(value = "findFightInfo", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findFightInfo(String FIGHT_INFO_ID) {
        logBefore(logger, "查询团购订单详情");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            PageData pd_f = fight_InfoService.findById(pd);
            List<PageData> list = fight_InfoService.findList(pd_f);
            List<PageData> list1 = fight_InfoService.findListUserId(pd_f);
            PageData pd_i = integralService.findById(pd);
            if (String.valueOf(list.size()).equals(pd_i.getString("NUM"))) {
                pd_f.put("TUANGOU", "1");
            } else {
                pd_f.put("TUANGOU", "0");
            }
            if (pd_f.getString("FIGHT_INFO_ID").equals(list1.get(0).getString("FIGHT_INFO_ID"))) {
                pd_f.put("DIYI", "0");
            } else {
                pd_f.put("DIYI", "1");
            }
            pd.clear();
            pd.put("URL", "http://m.nongjike.cn/nongjike/tushu.html?FIGHT_ID=" + pd_f.getString("FIGHT_ID"));
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("FIGHT", pd_f);
            pd.put("Integral", pd_i);
            pd.put("data", list);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    @ResponseBody
    @RequestMapping(value = "/alipay1", produces = "text/html;charset=UTF-8", method = {RequestMethod.POST,
            RequestMethod.GET})
    public String alipay1(String ORDER_NUMBER, String MONEY) throws Exception {
        PageData pd = new PageData();
        /* if(MONEY.equals(pd1.getString("MONEY"))){ */
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",
                AlipayConfig.app_id, AlipayConfig.private_key, "json", "utf-8", AlipayConfig.alipay_public_key, "RSA");
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        // SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        // model.setPassbackParams(URLEncoder.encode(body.toString()));; //描述信息
        // 添加附加数据
        model.setSubject("农极客"); // 商品标题
        model.setOutTradeNo(ORDER_NUMBER); // 商家订单编号
        model.setTimeoutExpress("30m"); // 超时关闭该订单时间
        model.setTotalAmount(MONEY); // 订单总金额
        model.setBody("2");
        model.setProductCode("QUICK_MSECURITY_PAY"); // 销售产品码，商家和支付宝签约的产品码，为固定值QUICK_MSECURITY_PAY
        request.setBizModel(model);
        request.setNotifyUrl("http://m.nongjike.cn/NJK/app/notify_url"); // 回调地址
        String orderStr = "";
        try {
            // 这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            orderStr = response.getBody();
            System.out.println(orderStr);// 就是orderString 可以直接给客户端请求，无需再做处理。
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        pd.put("code", "1");
        pd.put("message", "支付成功");
        // pd.put("data", json4.toString());
        pd.put("data", orderStr);
        /*
         * }else{ pd.put("code", "2"); pd.put("message", "支付异常");
         * //pd.put("data", json4.toString()); pd.put("data", ""); }
         */
        JSONObject json = JSONObject.fromObject(pd);
        System.out.println(json.toString());
        return json.toString();
        /* return orderString; */

        // String
        // a="partner="+AlipayConfig.app_id+"&seller_id=""&out_trade_no="1000000124"&total_fee="0.02"&notify_url="http://wxej.cckelifang.com/web/api/mkt/order/getAlipayResult"&service="mobile.securitypay.pay"&payment_type="1"&_input_charset="utf-8"&it_b_pay="30m"&show_url="m.alipay.com"&sign="0SON%2FcHuokUIeYLUrhcB13XbLiczU65Rz%2F5yzUEUisKxx7LupD2T85Fw2WBcti9%2FYPpcVTzf5JoK8SB20V9uPjdvjKKqNShOPUiE%2F0AwpZqE8m%2BRf3dKjaI%2BL7FsbSjgw%2BTrTGfGwfsqH0MgqYa9rjlCwnEeScGEfnMI2%2F6KTpo%3D"&sign_type="RSA""
    }

    @RequestMapping(value = "findFightOpenId", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findFightOpenId(String OPENID) {
        logBefore(logger, "根据OPENID查询团购订单");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            PageData Fight_info = fight_InfoService.findOpenId(pd);
            if (Fight_info != null) {
                List<PageData> list = fight_InfoService.findList(Fight_info);
                PageData pd_i = integralService.findById(Fight_info);
                Integer cha = Integer.valueOf(pd_i.getString("NUM")) - list.size();
                PageData pd_f = fightService.findById(Fight_info);
                if (cha == 0) {
                    pd_f.put("STATUS", "1");
                    fightService.edit(pd_f);
                }
                int count = DateUtil.compare_date(pd_f.getString("PDATE"), DateUtil.getTime());
                if (count != 1) {
                    if (pd_f.getString("STATUS").equals("0")) {
                        pd_f.put("STATUS", "2");
                        fightService.edit(pd_f);
                    }
                }
                pd.clear();
                pd.put("code", "1");
                pd.put("message", "正确返回数据!");
                pd.put("data", list);
                pd.put("cha", cha);
                pd.put("count", count);
                pd.put("FIGHT_ID", Fight_info.getString("FIGHT_ID"));
                pd.put("KUAIDI", Fight_info.getString("KUAIDI"));
                pd.put("NUMBER", Fight_info.getString("NUMBER"));
            } else {
                pd.put("code", "3");
                pd.put("message", "你没有订单!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    /* public String */
    @RequestMapping(value = "findFightId", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findFightId(String FIGHT_ID) {
        logBefore(logger, "根据FightId查询信息");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            PageData pd_f = fightService.findById(pd);
            if (pd_f.getString("STATUS").equals("0") || pd_f.getString("STATUS").equals("1")) {
                pd.put("STATUS", "1");
                List<PageData> list = fight_InfoService.findList(pd);
                PageData pd_i = integralService.findById(pd);
                Integer cha = Integer.valueOf(pd_i.getString("NUM")) - list.size();
                if (cha <= 0) {
                    pd_f.put("STATUS", "1");
                    fightService.edit(pd_f);
                }
                int count = DateUtil.compare_date(pd_f.getString("PDATE"), DateUtil.getTime());
                if (count != 1) {
                    if (pd_f.getString("STATUS").equals("0")) {
                        pd_f.put("STATUS", "2");
                        fightService.edit(pd_f);
                    }
                }
                pd.clear();
                pd.put("code", "1");
                pd.put("message", "正确返回数据!");
                pd.put("data", list);
                pd.put("cha", cha);
                pd.put("count", count);
            } else {
                pd.put("code", "3");
                pd.put("message", "本次团购失败,请重新开团!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    @RequestMapping(value = "findOne", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findOne(String OPENID) {
        logBefore(logger, "根据OPId判断是否第一次买");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            List<PageData> list1 = fight_InfoService.findListOpenId(pd);
            pd.clear();
            pd.put("list", list1);
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    @ResponseBody
    @RequestMapping(value = "/notify_url1", produces = "text/html;charset=UTF-8", method = {RequestMethod.POST,
            RequestMethod.GET})
    public String notify_url1(HttpServletRequest request) throws Exception {
        // 接收支付宝返回的请求参数

        Map requestParams = request.getParameterMap();
        SmsBao sms = new SmsBao();
        JSONObject json = JSONObject.fromObject(requestParams);

        String trade_status = json.get("trade_status").toString().substring(2,
                json.get("trade_status").toString().length() - 2);
        String out_trade_no = json.get("out_trade_no").toString().substring(2,
                json.get("out_trade_no").toString().length() - 2);
        String body = json.get("body").toString().substring(2, json.get("body").toString().length() - 2);
        System.out.println("====================================================");
        System.out.println(json.toString());
        System.out.println("支付宝回调地址！");
        System.out.println("商户的订单编号：" + out_trade_no);
        System.out.println("支付的状态：" + trade_status);
        System.err.println("支付的编码：" + body);

        if (trade_status.equals("TRADE_SUCCESS")) {
            PageData pd1 = new PageData();
            pd1.put("ORDER_NUMBER", out_trade_no);
            PageData od = fight_InfoService.findNumber(pd1);
            od.put("STATUS", "1");
            od.put("PAY_DATE", DateUtil.getTime());
            fight_InfoService.editStatus(od);
            String text = new String();
            text = "【农极客】 支付成功通知，您在<农极客>购买的订单号为\"" + od.get("ORDER_NUMBER").toString() + "\"的商品已支付成功。查看详情请登录<农极客>App,查看\"我的订单\"。请勿相信以订单异常为由的退款要求，谨防诈骗";
            sms.sendSMS(od.get("PHONE").toString(), text);
            return "SUCCESS";
        } else {
            PageData pd1 = new PageData();
            pd1.put("ORDER_NUMBER", out_trade_no);
            PageData od = fight_InfoService.findNumber(pd1);
            od.put("STATUS", "10");
            od.put("PAY_DATE", "");
            fight_InfoService.editStatus(od);
            return "SUCCESS";
        }
    }

    @RequestMapping(value = "/wxPay", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String wxPay(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logBefore(logger, "微信支付");
        String partner = "1482116722";
        String appid = "wx3e8ef0db80a0580f";
        String appsecret = "278fc286264c9d8a96571752f88c3eb7";
        String partnerkey = "5BCB0B1B9455219FF9628FA9DED938A2";
        String ORDER_NUMBER = request.getParameter("ORDER_NUMBER");
        String MONEY = request.getParameter("MONEY");
        String code = "1";
        String flag = "2";

        // 获取openId后调用统一支付接口https://api.mch.weixin.qq.com/pay/unifiedorder
        String currTime = TenpayUtil.getCurrTime();
        // 8位日期
        String strTime = currTime.substring(8, currTime.length());
        // 四位随机数
        String strRandom = TenpayUtil.buildRandom(4) + "";
        // 10位序列号,可以自行调整。
        String strReq = strTime + strRandom;

        // 商户号
        String mch_id = partner;
        // 随机数
        String nonce_str = strReq;
        // 商品描述根据情况修改
        String body = "农极客";
        // 商户订单号
        String out_trade_no = ORDER_NUMBER;
        // 总金额以分为单位，不带小数点
        // Integer total_fee1=Integer.valueOf(MONEY)*100;
        String total_fee = MONEY;
        // 订单生成的机器 IP
        String spbill_create_ip = request.getRemoteAddr();
        // 这里notify_url是 支付完成后微信发给该链接信息，可以判断会员是否支付成功，改变订单状态等。
        String notify_url = "http://m.nongjike.cn/NJK/app/notify_url1";
        String trade_type = "APP";

        SortedMap<String, String> packageParams = new TreeMap<String, String>();
        packageParams.put("appid", appid);
        packageParams.put("attach", flag);
        packageParams.put("mch_id", mch_id);
        packageParams.put("nonce_str", nonce_str);
        packageParams.put("body", body);
        packageParams.put("out_trade_no", out_trade_no);
        packageParams.put("total_fee", total_fee);
        packageParams.put("spbill_create_ip", spbill_create_ip);
        packageParams.put("notify_url", notify_url);
        packageParams.put("trade_type", trade_type);

        RequestHandler reqHandler = new RequestHandler(request, response);
        reqHandler.init(appid, appsecret, partnerkey);

        String sign = reqHandler.createSign(packageParams);
        String xml = "<xml>" + "<appid>" + appid + "</appid>" + "<attach>" + flag + "</attach>" + "<mch_id>" + mch_id
                + "</mch_id>" + "<nonce_str>" + nonce_str + "</nonce_str>" + "<sign>" + sign + "</sign>" +
                // "<body>"+body+"</body>"+
                "<body><![CDATA[" + body + "]]></body>" + "<out_trade_no>" + out_trade_no + "</out_trade_no>"
                + "<total_fee>" + total_fee + "</total_fee>" + "<spbill_create_ip>" + spbill_create_ip
                + "</spbill_create_ip>" + "<notify_url>" + notify_url + "</notify_url>" + "<trade_type>" + trade_type
                + "</trade_type>" + "</xml>";
        System.out.println(xml);
        String createOrderURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
        String prepay_id = "";
        try {
            prepay_id = new GetWxOrderno().getPayNo(createOrderURL, xml);
            System.out.println(prepay_id);
            if (prepay_id.equals("")) {
                // request.setAttribute("ErrorMsg", "统一支付接口获取预支付订单出错");
                System.out.println("ErrorMsg  统一支付接口获取预支付订单出错");
                // response.sendRedirect("error.jsp");
                code = "2";
            }
        } catch (Exception e1) {
            //
            e1.printStackTrace();
        }

        SortedMap<String, String> finalpackage = new TreeMap<String, String>();
        String appid2 = appid;
        String timestamp = Sha1Util.getTimeStamp();
        String nonceStr2 = nonce_str;
        String prepay_id2 = "prepay_id=" + prepay_id;
        String packages = prepay_id;
        finalpackage.put("appId", appid2);
        finalpackage.put("timeStamp", timestamp);
        finalpackage.put("nonceStr", nonceStr2);
        finalpackage.put("package", packages);
        finalpackage.put("signType", "MD5");
        finalpackage.put("code", code);
        SortedMap<String, String> finalpackage1 = new TreeMap<String, String>();
        finalpackage1.put("appid", appid);
        finalpackage1.put("timestamp", timestamp);
        finalpackage1.put("noncestr", nonce_str);
        finalpackage1.put("partnerid", partner);
        finalpackage1.put("package", "Sign=WXPay");
        finalpackage1.put("prepayid", prepay_id);
        String finalsign1 = reqHandler.createSign(finalpackage1);
        System.out.println(finalsign1);

        finalpackage.put("sign", finalsign1);
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(finalpackage);
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

    // 安卓团购微信支付
    @RequestMapping(value = "/wxPay_snatch4", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String wxPay_snatch4(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logBefore(logger, "微信支付");
        String partner = "1482116722";
        String appid = "wx3e8ef0db80a0580f";
        String appsecret = "278fc286264c9d8a96571752f88c3eb7";
        String partnerkey = "5BCB0B1B9455219FF9628FA9DED938A2";
        String ORDER_NUMBER = request.getParameter("ORDER_NUMBER");
        String MONEY = request.getParameter("MONEY");
        String code = "1";
        String flag = "1";

        // 获取openId后调用统一支付接口https://api.mch.weixin.qq.com/pay/unifiedorder
        String currTime = TenpayUtil.getCurrTime();
        // 8位日期
        String strTime = currTime.substring(8, currTime.length());
        // 四位随机数
        String strRandom = TenpayUtil.buildRandom(4) + "";
        // 10位序列号,可以自行调整。
        String strReq = strTime + strRandom;

        // 商户号
        String mch_id = partner;
        // 随机数
        String nonce_str = strReq;
        // 商品描述根据情况修改
        String body = "农极客";
        // 商户订单号
        String out_trade_no = ORDER_NUMBER;
        // 总金额以分为单位，不带小数点
        // Integer total_fee1=Integer.valueOf(MONEY)*100;
        String total_fee = MONEY;
        // 订单生成的机器 IP
        String spbill_create_ip = request.getRemoteAddr();
        // 这里notify_url是 支付完成后微信发给该链接信息，可以判断会员是否支付成功，改变订单状态等。
        String notify_url = "http://m.nongjike.cn/NJK/NJK/app/notify_url1";
        String trade_type = "APP";

        SortedMap<String, String> packageParams = new TreeMap<String, String>();
        packageParams.put("appid", appid);
        packageParams.put("attach", flag);
        packageParams.put("mch_id", mch_id);
        packageParams.put("nonce_str", nonce_str);
        packageParams.put("body", body);
        packageParams.put("out_trade_no", out_trade_no);
        packageParams.put("total_fee", total_fee);
        packageParams.put("spbill_create_ip", spbill_create_ip);
        packageParams.put("notify_url", notify_url);
        packageParams.put("trade_type", trade_type);

        RequestHandler reqHandler = new RequestHandler(request, response);
        reqHandler.init(appid, appsecret, partnerkey);

        String sign = reqHandler.createSign(packageParams);
        String xml = "<xml>" + "<appid>" + appid + "</appid>" + "<attach>" + flag + "</attach>" + "<mch_id>" + mch_id
                + "</mch_id>" + "<nonce_str>" + nonce_str + "</nonce_str>" + "<sign>" + sign + "</sign>" +
                // "<body>"+body+"</body>"+
                "<body><![CDATA[" + body + "]]></body>" + "<out_trade_no>" + out_trade_no + "</out_trade_no>"
                + "<total_fee>" + total_fee + "</total_fee>" + "<spbill_create_ip>" + spbill_create_ip
                + "</spbill_create_ip>" + "<notify_url>" + notify_url + "</notify_url>" + "<trade_type>" + trade_type
                + "</trade_type>" + "</xml>";
        System.out.println(xml);
        String createOrderURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
        String prepay_id = "";
        try {
            prepay_id = new GetWxOrderno().getPayNo(createOrderURL, xml);
            System.out.println(prepay_id);
            if (prepay_id.equals("")) {
                // request.setAttribute("ErrorMsg", "统一支付接口获取预支付订单出错");
                System.out.println("ErrorMsg  统一支付接口获取预支付订单出错");
                // response.sendRedirect("error.jsp");
                code = "2";
            }
        } catch (Exception e1) {
            //
            e1.printStackTrace();
        }

        SortedMap<String, String> finalpackage = new TreeMap<String, String>();
        String appid2 = appid;
        String timestamp = Sha1Util.getTimeStamp();
        String nonceStr2 = nonce_str;
        String prepay_id2 = "prepay_id=" + prepay_id;
        String packages = prepay_id;
        finalpackage.put("appId", appid2);
        finalpackage.put("timeStamp", timestamp);
        finalpackage.put("nonceStr", nonceStr2);
        finalpackage.put("package", packages);
        finalpackage.put("signType", "MD5");
        finalpackage.put("code", code);
        String finalsign = reqHandler.createSign(finalpackage);
        System.out.println(finalsign);
        finalpackage.put("sign", finalsign);
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(finalpackage);
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

    @RequestMapping(value = "/wxPayZhiFu", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String wxPayZhiFu(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logBefore(logger, "微信团购支付");
        String partner = "1484798722";
        String appid = "wx3f6627be4ed503c9";
        String appsecret = "5080ea88e4197c91844e2aa76c971cb5";
        String partnerkey = "edc60a975209ebf1d3e308a36d5397d1";
        String flag = "1";
        String ORDER_NUMBER = request.getParameter("ORDER_NUMBER");
        String money = request.getParameter("money");
        String openId = request.getParameter("openId");

        // 获取openId后调用统一支付接口https://api.mch.weixin.qq.com/pay/unifiedorder
        String currTime = TenpayUtil.getCurrTime();
        // 8位日期
        String strTime = currTime.substring(8, currTime.length());
        // 四位随机数
        String strRandom = TenpayUtil.buildRandom(4) + "";
        // 10位序列号,可以自行调整。
        String strReq = strTime + strRandom;

        // 商户号
        String mch_id = partner;
        // 随机数
        String nonce_str = strReq;
        // 商品描述根据情况修改
        String body = "农极客团购";
        // 商户订单号
        String out_trade_no = ORDER_NUMBER;
        // 总金额以分为单位，不带小数点
        String total_fee = money;
        // 订单生成的机器 IP
        String spbill_create_ip = request.getRemoteAddr();
        // 这里notify_url是 支付完成后微信发给该链接信息，可以判断会员是否支付成功，改变订单状态等。
        String notify_url = "http://m.nongjike.cn/NJK/app/wxZhiNotify";
        String trade_type = "JSAPI";
        String openid = openId;

        SortedMap<String, String> packageParams = new TreeMap<String, String>();
        packageParams.put("appid", appid);
        packageParams.put("attach", flag);
        packageParams.put("mch_id", mch_id);
        packageParams.put("nonce_str", nonce_str);
        packageParams.put("body", body);
        packageParams.put("out_trade_no", out_trade_no);
        packageParams.put("total_fee", total_fee);
        packageParams.put("spbill_create_ip", spbill_create_ip);
        packageParams.put("notify_url", notify_url);
        packageParams.put("trade_type", trade_type);
        packageParams.put("openid", openid);

        RequestHandler reqHandler = new RequestHandler(request, response);
        reqHandler.init(appid, appsecret, partnerkey);

        String sign = reqHandler.createSign(packageParams);
        String xml = "<xml>" + "<appid>" + appid + "</appid>" + "<attach>" + flag + "</attach>" + "<mch_id>" + mch_id
                + "</mch_id>" + "<nonce_str>" + nonce_str + "</nonce_str>" + "<sign>" + sign + "</sign>" +
                // "<body>"+body+"</body>"+
                "<body><![CDATA[" + body + "]]></body>" + "<out_trade_no>" + out_trade_no + "</out_trade_no>"
                + "<total_fee>" + total_fee + "</total_fee>" + "<spbill_create_ip>" + spbill_create_ip
                + "</spbill_create_ip>" + "<notify_url>" + notify_url + "</notify_url>" + "<trade_type>" + trade_type
                + "</trade_type>" + "<openid>" + openid + "</openid>" + "</xml>";
        System.out.println(xml);
        String createOrderURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
        String prepay_id = "";
        try {
            prepay_id = new GetWxOrderno().getPayNo(createOrderURL, xml);
            if (prepay_id.equals("")) {
                // request.setAttribute("ErrorMsg", "统一支付接口获取预支付订单出错");
                System.out.println("ErrorMsg  统一支付接口获取预支付订单出错");
                // response.sendRedirect("error.jsp");
            }
        } catch (Exception e1) {
            //
            e1.printStackTrace();
        }
        SortedMap<String, String> finalpackage = new TreeMap<String, String>();
        String appid2 = appid;
        String timestamp = Sha1Util.getTimeStamp();
        String nonceStr2 = nonce_str;
        String prepay_id2 = "prepay_id=" + prepay_id;
        String packages = prepay_id2;
        finalpackage.put("appId", appid2);
        finalpackage.put("timeStamp", timestamp);
        finalpackage.put("nonceStr", nonceStr2);
        finalpackage.put("package", packages);
        finalpackage.put("signType", "MD5");
        String finalsign = reqHandler.createSign(finalpackage);
        System.out.println(finalsign);
        finalpackage.put("sign", finalsign);
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(finalpackage);
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

    // =============================微信支付结果通知==================================

    /**
     * 微信支付结果通知
     *
     * @param
     * @return
     */

    @RequestMapping(value = "/wxZhiNotify", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String wxZhiNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logBefore(logger, "微信回调wxPayNotify");
        BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream()));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        System.out.println(sb);
        // String xml1 =
        // "<xml><appid><![CDATA[wxaef63f3ca970558f]]></appid><bank_type><![CDATA[CMB_CREDIT]]></bank_type><cash_fee><![CDATA[1]]></cash_fee><fee_type><![CDATA[CNY]]></fee_type><is_subscribe><![CDATA[Y]]></is_subscribe><mch_id><![CDATA[1331665201]]></mch_id><nonce_str><![CDATA[1736098185]]></nonce_str><openid><![CDATA[omzvHwBD09rzhw_9op7ZURrPLjwo]]></openid><out_trade_no><![CDATA[333]]></out_trade_no><result_code><![CDATA[SUCCESS]]></result_code><return_code><![CDATA[SUCCESS]]></return_code><sign><![CDATA[DE70786DA375C9A9CA3289279C7B9DE4]]></sign><time_end><![CDATA[20160421173445]]></time_end><total_fee>1</total_fee><trade_type><![CDATA[JSAPI]]></trade_type><transaction_id><![CDATA[4003372001201604215061462403]]></transaction_id></xml>";
        @SuppressWarnings("unchecked")
        Map<String, String> map = GetWxOrderno.doXMLParse(sb.toString());
        String result_code = map.get("result_code");
        System.out.println("result_code:" + result_code);
        String xml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                + "<return_msg><![CDATA[签名失败]]></return_msg>" + "</xml>";
        String attach = map.get("attach");
        System.out.println("attach:" + attach);
        if ("1".equals(attach)) {
            String USER_ID = map.get("openid");
            System.out.println("openid:" + USER_ID);
            String ORDER_NUMBER = map.get("out_trade_no");
            System.out.println("orderNo:" + ORDER_NUMBER);
            String total_fee = map.get("total_fee");
            System.out.println("total_fee:" + total_fee);
            if ("SUCCESS".equals(result_code)) {
                PageData pd1 = new PageData();
                pd1.put("ORDER_NUMBER", ORDER_NUMBER);
                PageData od = fight_InfoService.findNumber(pd1);
                od.put("STATUS", "1");
                od.put("PAY_DATE", DateUtil.getTime());
                fight_InfoService.editStatus(od);
                List<PageData> list11 = fight_InfoService.findList(od);
                PageData pd_i = integralService.findById(od);
                if (pd_i.getString("NUM").equals(String.valueOf(list11.size()))) {
                    od.put("STATUS", "1");
                    fightService.edit(od);
                    for (int i = 0; i < list11.size(); i++) {
                        new Thread(new Wtuishong("本次团购已完成", list11.get(i).getString("ORDER_NUMBER"),
                                list11.get(i).getString("NAME"), list11.get(i).getString("ORDER_NUMBER"), "",
                                "本次团购已经完成,8月19号统一发货,请留意本公众号的快递通知", list11.get(i).getString("OPENID"),
                                "http://m.nongjike.cn/nongjike/middle.html?FIGHT_ID=" + od.getString("FIGHT_ID"),
                                tokenService, "TjafAft8Z2gBWt9K-VzjKCuSUHqJ9u_0cwfVeS-r52w")).start();
                    }
                }
                String[] a = od.getString("PRODUCT").split(",");
                new Thread(new Wtuishong("你已参加了本次团购", ORDER_NUMBER, String.valueOf(a.length), od.getString("MONEY"),
                        od.getString("DATE"), "点击此信息进入页面", USER_ID,
                        "http://m.nongjike.cn/nongjike/middle.html?FIGHT_ID=" + od.getString("FIGHT_ID"), tokenService,
                        "hc_GlELQc2wGsWLVvh0gukuI765UrFSVG4wgJx38jYg")).start();
                return "SUCCESS";
            } else {
                PageData pd1 = new PageData();
                pd1.put("ORDER_NUMBER", ORDER_NUMBER);
                PageData od = fight_InfoService.findNumber(pd1);
                od.put("STATUS", "10");
                od.put("PAY_DATE", "");
                fight_InfoService.editStatus(od);
                return "SUCCESS";
            }
        }
        System.out.println("~~~~~~~~~~~~~~~~付款成功~~~~~~~~~");
        xml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>" + "<return_msg><![CDATA[OK]]></return_msg>"
                + "</xml>";
        System.out.println(xml);
        return xml;
    }

    @RequestMapping(value = "get_StringNum", produces = "text/html;charset=UTF-8")
    public void get_StringNum() {
        logBefore(logger, "");
        try {
            PageData pd = new PageData();
            List<PageData> list = feiLiaoService.findList1(pd);
            for (int i = 0, len = list.size(); i < len; i++) {
                String SHUZI = StringUtil.get_StringNum(list.get(i).getString("ZHENGHAO"));
                list.get(i).put("SHUZI", SHUZI);
                feiLiaoService.edit(list.get(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "get_StringNum1", produces = "text/html;charset=UTF-8")
    public void get_StringNum1() {
        logBefore(logger, "");
        try {
            PageData pd = new PageData();
            List<PageData> list = feiLiaoService.findList3(pd);
            for (int i = 0; i < list.size(); i++) {
                feiLiaoService.delete(list.get(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * public String aaa(){ try { String token=""; PageData pd=new PageData();
     * PageData pd12 = tokenService.findById(pd); SimpleDateFormat format = new
     * SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); long difference =
     * format.parse(format.format(new Date())).getTime() -
     * format.parse(pd12.getString("DATE")).getTime(); if (difference > 3600000)
     * { // 标签超时,重新到微信服务器请求标签超时时间为7200秒（7200000毫秒） token =
     * WeiXin.access_token1(); pd12.put("token", token); pd12.put("DATE",
     * DateUtil.getTime()); tokenService.edit(pd12); } else { token =
     * pd12.getString("token"); } } catch (Exception e) { // TODO Auto-generated
     * catch block e.printStackTrace(); } // TODO Auto-generated method stub
     * JSONObject josn=null; String aa=sslk.fasong(openId, MoBanId, url,
     * "#173177",josn); }
     */
    @RequestMapping(value = "tuikuan", produces = "text/html;charset=UTF-8")
    public void tuikuan() throws AlipayApiException {
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",
                AlipayConfig.app_id, AlipayConfig.private_key, "json", "utf-8", AlipayConfig.alipay_public_key, "RSA");
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        request.setBizContent("{" + "\"out_trade_no\":\"739170729190257319\"," + "\"refund_amount\":10,"
                + "\"refund_reason\":\"正常退款\"," + "\"out_request_no\":\"HZ01RF001\"," + "\"operator_id\":\"OP001\","
                + "\"store_id\":\"NJ_S_001\"," + "\"terminal_id\":\"NJ_T_001\"" + "  }");
        AlipayTradeRefundResponse response = alipayClient.execute(request);
        if (response.isSuccess()) {
            System.out.println("调用成功");
        }

    }

    @RequestMapping(value = "shitui", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public void shitui(String FIGHT_ID) {
        PageData pd = new PageData();
        pd = this.getPageData();
        List<PageData> list11;
        try {
            list11 = fight_InfoService.findList(pd);
            for (int i = 0; i < list11.size(); i++) {
                System.err.println("dikjigesadjlkasdfkajsfkljsk" + i);
                System.err.println("dikjigesadjlkasdfkajsfkljsk" + list11.get(i).getString("ORDER_NUMBER"));
                System.err.println("dikjigesadjlkasdfkajsfkljsk" + list11.get(i).getString("NAME"));
                System.err.println("dikjigesadjlkasdfkajsfkljsk" + list11.get(i).getString("OPENID"));
                // new Thread(new Wtuishong("本次团购已完成",
                // list11.get(i).getString("ORDER_NUMBER"),
                // list11.get(i).getString("NAME"), "", "","本次团购已经完成,正在录入物流信息.",
                // list11.get(i).getString("OPENID"),"http://m.nongjike.cn/nongjike/middle.html?FIGHT_ID="+FIGHT_ID,tokenService,"TjafAft8Z2gBWt9K-VzjKCuSUHqJ9u_0cwfVeS-r52w")).start();
                new Thread(new Wtuishong("本次团购已完成", list11.get(i).getString("ORDER_NUMBER"),
                        list11.get(i).getString("NAME"), list11.get(i).getString("ORDER_NUMBER"), "",
                        "本次团购已经完成,正在录入物流信息.", list11.get(i).getString("OPENID"),
                        "http://m.nongjike.cn/nongjike/middle.html?FIGHT_ID=" + FIGHT_ID, tokenService,
                        "TjafAft8Z2gBWt9K-VzjKCuSUHqJ9u_0cwfVeS-r52w")).start();
                // new Thread(new Wtuishong("本次团购已完成", "111", "1111", "",
                // "","本次团购已经完成,正在录入物流信息.",
                // "o6_H-wP9ExmdaQigf_s4kG1QGYNc","http://m.nongjike.cn/nongjike/middle.html?FIGHT_ID=",tokenService,"TjafAft8Z2gBWt9K-VzjKCuSUHqJ9u_0cwfVeS-r52w")).start();
            }
            new Thread(new Wtuishong("本次团购已完成", "111", "1111", "", "", "本次团购已经完成,正在录入物流信息.",
                    "o6_H-wP9ExmdaQigf_s4kG1QGYNc", "http://m.nongjike.cn/nongjike/middle.html?FIGHT_ID=", tokenService,
                    "TjafAft8Z2gBWt9K-VzjKCuSUHqJ9u_0cwfVeS-r52w")).start();
        } catch (Exception e) {
            //
            e.printStackTrace();
        }

    }

    @RequestMapping(value = "findfeiliao2", produces = "text/html;charset=UTF-8")
    public void findfeiliao2() {
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            List<PageData> list = feiLiaoService.findList2(pd);
            for (int i = 0; i < list.size(); i++) {
                String ZHENGHAO[] = list.get(i).getString("ZHENGHAO").split("###");
                String ENAME[] = list.get(i).getString("ENAME").split("###");
                String NAME[] = list.get(i).getString("NAME").split("###");
                String PRNAME[] = list.get(i).getString("PRNAME").split("###");
                String XINGTAI[] = list.get(i).getString("XINGTAI").split("###");
                String JISHU[] = list.get(i).getString("JISHU").split("###");
                String CROP[] = list.get(i).getString("CROP").split("###");
                String DATE[] = list.get(i).getString("DATE").split("###");
                String CNAME[] = list.get(i).getString("CNAME").split("###");
                for (int j = 0; j < ZHENGHAO.length; j++) {
                    PageData pd1 = new PageData();
                    pd1.put("FEILIAO_ID", this.get32UUID());
                    pd1.put("ZHENGHAO", ZHENGHAO[j]);
                    pd1.put("ENAME", ENAME[j]);
                    pd1.put("NAME", NAME[j]);
                    pd1.put("PRNAME", PRNAME[j]);
                    pd1.put("XINGTAI", XINGTAI[j]);
                    pd1.put("JISHU", JISHU[j]);
                    pd1.put("CROP", CROP[j]);
                    pd1.put("DATE", DATE[j]);
                    String SHUZI = StringUtil.get_StringNum(ZHENGHAO[j]);
                    list.get(i).put("SHUZI", SHUZI);
                    feiLiaoService.save(pd1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param tel  错误的加密
     * @param tels 错误的加密
     * @param tls  正确的加密
     * @param lop  错误的加密
     * @param poi  手机号
     * @return
     */
    @RequestMapping(value = "findeSendS", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findeSendS(String tel, String tels, String tls, String lop, String poi) {
        logBefore(logger, "返回接口");
        PageData pd = new PageData();
        pd = this.getPageData();
        String message = "正确返回数据!";
        String verification = "verification";
        String verieication = "verieication";
        String vericication = "vericication";
        Map<String, Object> map = new HashedMap();
        String YH = "0";
        try {
            String md5s = MD5
                    .md5(MD5.md5("asdas-asdaskjslfklsasdf-dklfgkdjsgjdfljgldf-asdjaksldjaslkdas-dsfsfasd") + poi);
            if (tls.equals(md5s)) {
                map.put("verieication", verieication);
                map.put("verification", verification);
                map.put("vericication", vericication);
            } else {
                message = "验证不通过!";
            }
            pd.put("USERNAME", poi);
            PageData pd_u = appuserService.findName(pd);
            if (pd_u != null) {
                YH = "1";
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("message", message);
            pd.put("object", map);
            pd.put("YH", YH);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // 查询试卷详情
    @RequestMapping(value = "findWenDaShiJuanById", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findWenDaShiJuanById(String WENDA_SHIJUAN_ID) {
        logBefore(logger, "查询试卷详情（期数）");
        PageData pd = new PageData();
        pd = this.getPageData();
        String message = "正确返回数据!";
        try {
            PageData shiJuan = wenDa_ShiJuanService.findById(pd);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据");
            pd.put("shiJuan", shiJuan);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // 查询试卷
    @RequestMapping(value = "findWenDaShiJuan", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findWenDaShiJuan(String WENDA_TYPE_ID, String pageNum) {
        logBefore(logger, "查询试卷");
        PageData pd = new PageData();
        Page page = new Page();
        pd = this.getPageData();
        String message = "正确返回数据!";
        try {
            if (pageNum == null || pageNum.length() == 0) {
                pageNum = "1";
            }
            pd.put("STATUS", "1");
            pd.put("WENDA_TYPE_ID", "1");
            page.setPd(pd);
            page.setShowCount(10);
            page.setCurrentPage(Integer.parseInt(pageNum));
            List<PageData> list = wenDa_ShiJuanService.list(page);
            Map<String, Object> map = new HashedMap();
            if (page.getCurrentPage() == Integer.parseInt(pageNum)) {
                map.put("object", list);
            } else {
                List<PageData> list4 = new ArrayList();
                message = "已加载全部数据!";
                map.put("object", list4);
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("message", message);
            pd.put("data", map);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // 查询答题
    @RequestMapping(value = "findShiJuan", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findShiJuan(String WENDA_SHIJUAN_ID, String USER_ID) {
        logBefore(logger, "查询答题");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            pd.put("YUE", DateUtil.getDay());
            PageData pd_c = zuoGuo_ShiJuanService.findCounts(pd);
            pd.put("STATUS", "1");
            List<PageData> list = wenDaService.findList(pd);
            for (int i = 0, len = list.size(); i < len; i++) {
                list.get(i).put("CORRECT", "");
                list.get(i).put("XUANZE", "");
                list.get(i).put("USER_ID", USER_ID);
                list.get(i).put("DATE", DateUtil.getTime());
                List<PageData> info = wenDa_InfnService.findList(list.get(i));
                if (collection_WendaService.findById(list.get(i)) != null) {
                    list.get(i).put("COLLECTION", "1");
                } else {
                    list.get(i).put("COLLECTION", "0");
                }
                list.get(i).put("INFO", info);
            }
            PageData pd_f = new PageData();
            pd_f.put("USER_ID", USER_ID);
            pd_f.put("STATUS", "3");
            pd_f.put("DATE", DateUtil.getTime());
            pd_f.put("WENDA_SHIJUAN_ID", WENDA_SHIJUAN_ID);
            fenXiangService.save(pd_f);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("data", list);
            pd.put("url", "https://www.meitiannongzi.com/nongjike/static/jsp/share_dati.html?WENDA_SHIJUAN_ID=" + WENDA_SHIJUAN_ID);
            pd.put("timg", "http://m.nongjike.cn/NJK/uploadFiles/uploadImgs/datitupiao.png");
            pd.put("count", pd_c.get("count1").toString());
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // 收藏问题
    @RequestMapping(value = "CollectioWenDa", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String CollectioWenDa(String USER_ID, String WENDA_ID, String STATUS) {
        logBefore(logger, "收藏问题");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            if (STATUS.equals("1")) {
                if (collection_WendaService.findById(pd) == null) {
                    pd.put("COLLECTION_WENDA_ID", this.get32UUID());
                    pd.put("DATE", DateUtil.getTime());
                    collection_WendaService.save(pd);
                }
            } else {
                collection_WendaService.deletes(pd);
            }

            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    @RequestMapping(value = "findCollectionWenDa", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findCollectionWenDa(String USER_ID, String pageNum) {
        logBefore(logger, "查询收藏问题");
        PageData pd = new PageData();
        Page page = new Page();
        pd = this.getPageData();
        String message = "查询收藏问题";
        try {
            if (pageNum == null || pageNum.length() == 0) {
                pageNum = "1";
            }
            page.setPd(pd);
            page.setShowCount(10);
            page.setCurrentPage(Integer.parseInt(pageNum));
            List<PageData> list = collection_WendaService.list(page);
            for (int i = 0, len = list.size(); i < len; i++) {
                List<PageData> info = wenDa_InfnService.findList(list.get(i));
                list.get(i).put("INFO", info);
            }
            Map<String, Object> map = new HashedMap();
            if (page.getCurrentPage() == Integer.parseInt(pageNum)) {
                map.put("object", list);
            } else {
                List<PageData> list4 = new ArrayList();
                message = "已加载全部数据!";
                map.put("object", list4);
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("message", message);
            pd.put("data", map);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    @RequestMapping(value = "deleteCollectioWenDa", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String deleteCollectioWenDa(String COLLECTION_WENDA_ID, String STATUS, String USER_ID) {
        logBefore(logger, "删除问答");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            if (STATUS.equals("1")) {
                String COLLECTION_WENDA_IDs[] = COLLECTION_WENDA_ID.split(",");
                collection_WendaService.delete(COLLECTION_WENDA_IDs);
            } else {
                collection_WendaService.deleteAll(pd);
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // 查询错题
    @RequestMapping(value = "findErrorWenDa", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findErrorWenDa(String USER_ID, String pageNum) {
        logBefore(logger, "查询错题");
        PageData pd = new PageData();
        pd = this.getPageData();
        Page page = new Page();
        String message = "正确返回数据!";
        try {
            if (pageNum == null || pageNum.length() == 0) {
                pageNum = "1";
            }
            page.setPd(pd);
            page.setShowCount(20);
            page.setCurrentPage(Integer.parseInt(pageNum));
            List<PageData> list = error_WendaService.list(page);
            for (int i = 0, len = list.size(); i < len; i++) {
                List<PageData> info = wenDa_InfnService.findList(list.get(i));
                list.get(i).put("INFO", info);
            }
            Map<String, Object> map = new HashedMap();
            if (page.getCurrentPage() == Integer.parseInt(pageNum)) {
                map.put("object", list);
            } else {
                List<PageData> list4 = new ArrayList();
                message = "已加载全部数据!";
                map.put("object", list4);
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("message", message);
            pd.put("data", map);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // 查询未答试卷
    @RequestMapping(value = "findUnreadWenDa", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findUnreadWenDa(String USER_ID, String pageNum) {
        logBefore(logger, "查询未答试卷");
        PageData pd = new PageData();
        Page page = new Page();
        pd = this.getPageData();
        String message = "正确返回数据!";
        try {
            if (pageNum == null || pageNum.length() == 0) {
                pageNum = "1";
            }
            pd.put("WENDA_TYPE_ID", "1");
            page.setPd(pd);
            page.setShowCount(10);
            page.setCurrentPage(Integer.parseInt(pageNum));
            List<PageData> list = wenDa_ShiJuanService.weidalistPage(page);
            Map<String, Object> map = new HashedMap();
            if (page.getCurrentPage() == Integer.parseInt(pageNum)) {
                map.put("object", list);
            } else {
                List<PageData> list4 = new ArrayList();
                message = "已加载全部数据!";
                map.put("object", list4);
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("message", message);
            pd.put("data", map);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    // 查询已做试卷
    @RequestMapping(value = "findYiZuoWenDa", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findYiZuoWenDa(String USER_ID, String pageNum) {
        logBefore(logger, "查询已做试卷");
        PageData pd = new PageData();
        pd = this.getPageData();
        Page page = new Page();
        String message = "正确返回数据!";
        try {
            if (pageNum == null || pageNum.length() == 0) {
                pageNum = "1";
            }
            pd.put("YUE", DateUtil.getDay());
            //pd.put("WENDA_TYPE_ID", "1");
            page.setPd(pd);
            page.setShowCount(20);
            page.setCurrentPage(Integer.parseInt(pageNum));
            List<PageData> list = zuoGuo_ShiJuanService.yizuolistPage(page);
            Map<String, Object> map = new HashedMap();
            if (page.getCurrentPage() == Integer.parseInt(pageNum)) {
                map.put("object", list);
            } else {
                List<PageData> list4 = new ArrayList();
                message = "已加载全部数据!";
                map.put("object", list4);
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("message", message);
            pd.put("data", map);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    @RequestMapping(value = "findZuiXinWenDa", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findZuiXinWenDa(String USER_ID) {
        logBefore(logger, "查询最新问答");
        PageData pd = new PageData();
        pd = this.getPageData();
        String TONGZHI = "";
        String STATUS = "0";
        try {
            PageData pd_w = wenDa_ShiJuanService.findZuiXin(pd);
            PageData pd_g = guangGaoService.findByIdS(pd);
            pd.put("YUE", DateUtil.getDay());
            pd.put("WENDA_SHIJUAN_ID", "");
            PageData pd_s = zuoGuo_ShiJuanService.findCount(pd);
            TONGZHI = "恭喜星航在考卷中获得100分";
            List<PageData> list = zuoGuo_ShiJuanService.findPaoMaDing(pd);

            PageData pd_q = qianDaoService.findUserIdDate(pd);
            PageData pd_e0 = wenDa_ShiJuanService.findPage(pd);          //肥料
            if (pd_q != null) {

            } else {
                pd.put("YUE", DateUtil.getSpecifiedDayBefore(DateUtil.getTime()));
                PageData pd_qq = qianDaoService.findUserIdDate(pd);
                if (pd_qq != null) {
                    pd.put("YUE", DateUtil.getDay());
                    pd.put("DATE", DateUtil.getTime());
                    qianDaoService.editS(pd);
                } else {
                    if (qianDaoService.findUserId(pd) != null) {
                        pd.put("COUNT", "1");
                        pd.put("DATE", DateUtil.getTime());
                        pd.put("YUE", DateUtil.getDay());
                        qianDaoService.edit(pd);
                    } else {
                        PageData pd_p = new PageData();
                        pd_p.put("USER_ID", USER_ID);
                        pd_p.put("COUNT", "1");
                        pd_p.put("DATE", DateUtil.getTime());
                        pd_p.put("YUE", DateUtil.getDay());
                        pd_p.put("KDATE", DateUtil.getTime());
                        qianDaoService.save(pd_p);
                    }
                }
            }
            if (pd_e0 != null) {
                STATUS = "1";
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("wenda", pd_w);
            pd.put("guanggao", pd_g);
            pd.put("count", pd_s.get("count1").toString());
            pd.put("tongzhi", TONGZHI);
            pd.put("suijimiao", pd_w.getString("SUIJI"));
            pd.put("PAOMADING", list);
            pd.put("STATUS", STATUS);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    @RequestMapping(value = "saveWenDa", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String saveWenDa(String json, String USER_ID, String FRACTION) {
        logBefore(logger, "完成答题");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            String ZUOGUO_SHIJUAN_ID = "";
            if (null != USER_ID && !"".equals(USER_ID)) {
                json = DateUtil.delHTMLTag(json);
                json = json.replace("\r", "");
                json = json.replace("\n", "");
                json = json.replace("\\", "");
                json = json.replace("\"[{\"MESSAGE", "[{\"MESSAGE");
                json = json.replace("}]\",\"MESSAGE\"", "}],\"MESSAGE\"");
                json = json.replace("\"[{\"DATE", "[{\"DATE");
                json = json.replace("}]\",\"DATE\"", "}],\"DATE\"");
                Gson gson = new Gson();
                List<PageData> list = gson.fromJson(json, new TypeToken<List<PageData>>() {
                }.getType());
                error_WendaService.editAll(list, USER_ID);
                error_WendaService.saveAll(list);
                PageData pd_z = new PageData();
                pd_z.put("USER_ID", USER_ID);
                pd_z.put("WENDA_SHIJUAN_ID", list.get(0).getString("WENDA_SHIJUAN_ID"));
                zuoGuo_ShiJuanService.edit(pd_z);
                ZUOGUO_SHIJUAN_ID = this.get32UUID();
                pd_z.put("ZUOGUO_SHIJUAN_ID", ZUOGUO_SHIJUAN_ID);
                pd_z.put("WENDA_SHIJUAN_ID", list.get(0).getString("WENDA_SHIJUAN_ID"));
                pd_z.put("DATE", DateUtil.getTime());
                pd_z.put("FRACTION", FRACTION);
                pd_z.put("STATUS", "0");
                pd_z.put("data", json);
                pd_z.put("YUE", DateUtil.getDay());
                pd_z.put("WENDA_TYPE_ID", list.get(0).getString("WENDA_TYPE_ID"));
                zuoGuo_ShiJuanService.save(pd_z);
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("ID", ZUOGUO_SHIJUAN_ID);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    /*
     * @RequestMapping(value="deleteWenDaError",produces=
     * "text/html;charset=UTF-8")
     *
     * @ResponseBody public void deleteWenDaError(String json,String USER_ID){
     * try{ Gson gson = new Gson(); List<PageData> list = gson.fromJson(json,
     * new TypeToken<List<PageData>>(){}.getType());
     * error_WendaService.deleteAll(list); }catch (Exception e) {
     * e.printStackTrace();
     *
     * } }
     */

    // 查询随机答题
    @RequestMapping(value = "findSuiJiWenDa", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findSuiJiWenDa(String USER_ID) {
        logBefore(logger, "查询随机答题");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            List<PageData> list = wenDaService.findSuiJi(pd);
            for (int i = 0, len = list.size(); i < len; i++) {
                list.get(i).put("CORRECT", "");
                list.get(i).put("XUANZE", "");
                list.get(i).put("USER_ID", USER_ID);
                list.get(i).put("DATE", DateUtil.getTime());
                List<PageData> info = wenDa_InfnService.findList(list.get(i));
                if (collection_WendaService.findById(list.get(i)) != null) {
                    list.get(i).put("COLLECTION", "1");
                } else {
                    list.get(i).put("COLLECTION", "0");
                }
                list.get(i).put("INFO", info);
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("data", list);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    @RequestMapping(value = "saveSuiJiWenDa", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String saveSuiJiWenDa(String json, String USER_ID, String FRACTION) {
        logBefore(logger, "完成答题");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            String ZUOGUO_SHIJUAN_ID = "";
            if (null != USER_ID && !"".equals(USER_ID)) {
                json = json.replace("\r", "");
                json = json.replace("\n", "");
                json = json.replace("\\", "");
                json = json.replace("\"[{\"MESSAGE", "[{\"MESSAGE");
                json = json.replace("}]\",\"MESSAGE\"", "}],\"MESSAGE\"");
                json = json.replace("\"[{\"DATE", "[{\"DATE");
                json = json.replace("}]\",\"DATE\"", "}],\"DATE\"");
                Gson gson = new Gson();
                List<PageData> list = gson.fromJson(json, new TypeToken<List<PageData>>() {
                }.getType());
                error_WendaService.editAll(list, USER_ID);
                error_WendaService.saveAlls(list);
                PageData pd_z = new PageData();
                ZUOGUO_SHIJUAN_ID = this.get32UUID();
                pd_z.put("ZUOGUO_SHIJUAN_ID", ZUOGUO_SHIJUAN_ID);
                pd_z.put("USER_ID", USER_ID);
                pd_z.put("WENDA_SHIJUAN_ID", "");
                pd_z.put("DATE", DateUtil.getTime());
                pd_z.put("FRACTION", FRACTION);
                pd_z.put("STATUS", "1");
                pd_z.put("data", json);
                pd_z.put("YUE", DateUtil.getDay());
                pd_z.put("WENDA_TYPE_ID", "4");
                zuoGuo_ShiJuanService.save(pd_z);
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("ID", ZUOGUO_SHIJUAN_ID);
            pd.put("message", "正确返回数据!");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    @RequestMapping(value = "findWenDaXiangQing", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findWenDaXiangQing(String WENDA_ID) {
        logBefore(logger, "查询详情");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            List<PageData> list = wenDa_InfnService.findList(pd);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("data", list);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    @RequestMapping(value = "deleteerrorWenDa", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String deleteerrorWenDa(String ERROR_WENDA_ID, String STATUS, String USER_ID) {
        logBefore(logger, "删除问答");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            if (STATUS.equals("1")) {
                String COLLECTION_WENDA_IDs[] = ERROR_WENDA_ID.split(",");
                error_WendaService.edita(COLLECTION_WENDA_IDs);
            } else {
                error_WendaService.editaa(pd);
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    @RequestMapping(value = "findPaiMingZongFei", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findPaiMingZongFei(String USER_ID) {
        logBefore(logger, "查询分数排行榜");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            List<PageData> list = zuoGuo_ShiJuanService.FractionpageList(pd);
            PageData pd_f = new PageData();
            if (pd_f != null) {
                pd_f = zuoGuo_ShiJuanService.findFractionPaiMing(pd);
            }
            if (pd_f == null) {
                PageData pd_u = appuserService.findById(pd);
                pd_f = new PageData();
                pd_f.put("WENDA_COUNT_ID", "");
                pd_f.put("USER_ID", USER_ID);
                pd_f.put("count1", 0);
                pd_f.put("rowno", 0);
                pd_f.put("NAME", pd_u.getString("NAME"));
                pd_f.put("IMG", pd_u.getString("IMG"));
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("data", list);
            pd.put("paiming", pd_f);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    @RequestMapping(value = "findPaiMingCiShu", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findPaiMingCiShu(String USER_ID) {
        logBefore(logger, "查询分数排行榜");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            List<PageData> list = zuoGuo_ShiJuanService.ZuoTilist(pd);
            PageData pd_f = new PageData();
            if (pd_f != null) {
                pd_f = zuoGuo_ShiJuanService.ZuoTilistPaiMing(pd);
            }
            if (pd_f == null) {
                PageData pd_u = appuserService.findById(pd);
                pd_f = new PageData();
                pd_f.put("WENDA_COUNT_ID", "");
                pd_f.put("USER_ID", USER_ID);
                pd_f.put("count1", 0);
                pd_f.put("rowno", 0);
                pd_f.put("NAME", pd_u.getString("NAME"));
                pd_f.put("IMG", pd_u.getString("IMG"));
            }
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("data", list);
            pd.put("paiming", pd_f);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    @RequestMapping(value = "findDaTiShiJuan", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findDaTiShiJuan(String USER_ID, String WENDA_SHIJUAN_ID) {
        logBefore(logger, "查询答题过的试卷");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            PageData pd_z = new PageData();
            if (WENDA_SHIJUAN_ID != null && WENDA_SHIJUAN_ID.length() != 0) {
                pd_z = zuoGuo_ShiJuanService.findZiJiDeFei(pd);
            } else {
                pd_z = zuoGuo_ShiJuanService.findZuiXinDaTi(pd);
            }
            Gson gson = new Gson();
            List<PageData> list = gson.fromJson(pd_z.getString("data"), new TypeToken<List<PageData>>() {
            }.getType());
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("data", list);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    @RequestMapping(value = "findZuoGuoShiJuanID", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findZuoGuoShiJuanID(String ZUOGUO_SHIJUAN_ID) {
        logBefore(logger, "分享查询做过试卷");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            PageData pd_z = new PageData();
            if (ZUOGUO_SHIJUAN_ID != null && ZUOGUO_SHIJUAN_ID.length() != 0) {
                pd_z = zuoGuo_ShiJuanService.findById(pd);
            } else {
                pd_z = zuoGuo_ShiJuanService.findByIds(pd);
            }
            Gson gson = new Gson();
            List<PageData> list = gson.fromJson(pd_z.getString("data"), new TypeToken<List<PageData>>() {
            }.getType());
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("data", list);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    @RequestMapping(value = "deleteZuoGuoShiJuan", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String deleteZuoGuoShiJuan(String WENDA_SHIJUAN_ID, String USER_ID) {
        logBefore(logger, "删除已做试卷");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            zuoGuo_ShiJuanService.editStatus(pd);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    @RequestMapping(value = "findWeiXinZongFeiPaiHang", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findWeiXinZongFeiPaiHang(String USER_ID, String url) {
        logBefore(logger, "分享总分排行榜");
        PageData pd = new PageData();
        pd = this.getPageData();
        String jsapiTicket = "";
        try {
            PageData pd12 = weiXinService.findById(pd);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long difference = format.parse(format.format(new Date())).getTime()
                    - format.parse(pd12.getString("DATE")).getTime();
            if (difference > 360000) { // 标签超时,重新到微信服务器请求标签超时时间为7200秒（7200000毫秒）
                String access_token = WeiXin.access_token();
                jsapiTicket = WeiXin.jsapiTicket(access_token);
                pd12.put("ticket", jsapiTicket);
                pd12.put("DATE", DateUtil.getTime());
                weiXinService.edit(pd12);
            } else {
                jsapiTicket = pd12.getString("ticket");
            }
            Map<String, String> cf = WeiXin.sign1(jsapiTicket, url);
            List<PageData> list = zuoGuo_ShiJuanService.FractionpageList(pd);
            PageData pd_f = zuoGuo_ShiJuanService.findFractionPaiMing(pd);
            PageData pd1 = new PageData();
            pd1.put("USER_ID", "");
            pd1.put("STATUS", "0");
            pd1.put("DATE", DateUtil.getTime());
            pd1.put("WENDA_SHIJUAN_ID", "");
            fenXiangService.save(pd1);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("signature", cf.get("signature"));
            pd.put("nonceStr", cf.get("nonceStr"));
            pd.put("timestamp", cf.get("timestamp"));
            pd.put("url", cf.get("url"));
            pd.put("appId", "wx6b912be972e69695");
            pd.put("data", list);
            pd.put("paiming", pd_f);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    @RequestMapping(value = "findWeiXinCiShuPaiHang", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findWeiXinCiShuPaiHang(String USER_ID, String url) {
        logBefore(logger, "分享次数排行榜");
        PageData pd = new PageData();
        pd = this.getPageData();
        String jsapiTicket = "";
        try {
            PageData pd12 = weiXinService.findById(pd);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long difference = format.parse(format.format(new Date())).getTime()
                    - format.parse(pd12.getString("DATE")).getTime();
            if (difference > 360000) { // 标签超时,重新到微信服务器请求标签超时时间为7200秒（7200000毫秒）
                String access_token = WeiXin.access_token();
                jsapiTicket = WeiXin.jsapiTicket(access_token);
                pd12.put("ticket", jsapiTicket);
                pd12.put("DATE", DateUtil.getTime());
                weiXinService.edit(pd12);
            } else {
                jsapiTicket = pd12.getString("ticket");
            }
            Map<String, String> cf = WeiXin.sign1(jsapiTicket, url);
            List<PageData> list = zuoGuo_ShiJuanService.ZuoTilist(pd);
            PageData pd_f = zuoGuo_ShiJuanService.ZuoTilistPaiMing(pd);
            PageData pd1 = new PageData();
            pd1.put("USER_ID", "");
            pd1.put("STATUS", "1");
            pd1.put("DATE", DateUtil.getTime());
            pd1.put("WENDA_SHIJUAN_ID", "");
            fenXiangService.save(pd1);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("signature", cf.get("signature"));
            pd.put("nonceStr", cf.get("nonceStr"));
            pd.put("timestamp", cf.get("timestamp"));
            pd.put("url", cf.get("url"));
            pd.put("appId", "wx6b912be972e69695");
            pd.put("data", list);
            pd.put("paiming", pd_f);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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

    @RequestMapping(value = "findWeiXinZuoGuoShiJuan", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findWeiXinZuoGuoShiJuan(String ZUOGUO_SHIJUAN_ID, String url) {
        logBefore(logger, "分享次数排行榜");
        PageData pd = new PageData();
        pd = this.getPageData();
        String jsapiTicket = "";
        try {
            PageData pd12 = weiXinService.findById(pd);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long difference = format.parse(format.format(new Date())).getTime()
                    - format.parse(pd12.getString("DATE")).getTime();
            if (difference > 360000) { // 标签超时,重新到微信服务器请求标签超时时间为7200秒（7200000毫秒）
                String access_token = WeiXin.access_token();
                jsapiTicket = WeiXin.jsapiTicket(access_token);
                pd12.put("ticket", jsapiTicket);
                pd12.put("DATE", DateUtil.getTime());
                weiXinService.edit(pd12);
            } else {
                jsapiTicket = pd12.getString("ticket");
            }
            Map<String, String> cf = WeiXin.sign1(jsapiTicket, url);
            PageData pd_z = zuoGuo_ShiJuanService.findById(pd);
            PageData pd1 = new PageData();
            pd1.put("USER_ID", "");
            pd1.put("STATUS", "2");
            pd1.put("DATE", DateUtil.getTime());
            pd1.put("WENDA_SHIJUAN_ID", "");
            fenXiangService.save(pd1);
            pd.clear();
            pd.put("code", "1");
            pd.put("message", "正确返回数据!");
            pd.put("signature", cf.get("signature"));
            pd.put("nonceStr", cf.get("nonceStr"));
            pd.put("timestamp", cf.get("timestamp"));
            pd.put("url", cf.get("url"));
            pd.put("appId", "wx6b912be972e69695");
            pd.put("data", pd_z);
        } catch (Exception e) {
            e.printStackTrace();
            pd.clear();
            pd.put("code", "2");
            pd.put("message", "程序出错,请联系管理员!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(pd);
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


    @RequestMapping(value = "fasongyouhuijuan", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public void fasongyouhuijuan() {
        logBefore(logger, "VIP发送优惠卷");
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            List<PageData> list = appuserService.findVipAppUser(pd);
            couponService.saves(list);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "findYouHunJuans", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public void findYouHunJuans() {
        logBefore(logger, "发送优惠卷");
        try {
            List<PageData> list = faSongPhoneService.findList(new PageData());
            PageData pd = new PageData();
            for (int i = 0; i < list.size(); i++) {
                PageData pd_a = appuserService.findByUserName(list.get(i));
                if (pd_a != null) {
                    pd.put("USER_ID", pd_a.get("USER_ID").toString());
                    pd.put("DATE", DateUtil.getDay());
                    PageData coupon = couponService.findCopyUser(pd);
                    if (coupon == null) {
                        PageData pd_c = new PageData();
                        pd_c.put("MAX", "50");
                        pd_c.put("JIAN", "50");
                        pd_c.put("DATE", DateUtil.getDay());
                        pd_c.put("USER_ID", pd_a.getString("USER_ID"));
                        pd_c.put("END", "2018-12-01");
                        pd_c.put("STATUS", "0");
                        pd_c.put("IMG", "http://m.nongjike.cn/NJK/uploadFiles/uploadImgs/50.png");
                        pd_c.put("BEIZHU", "5亿菌剂代言50元优惠卷");
                        pd_c.put("ZSTATUS", "0");
                        couponService.save(pd_c);
                    }
                } else {
                    System.err.println(list.get(i).getString("USERNAME"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
