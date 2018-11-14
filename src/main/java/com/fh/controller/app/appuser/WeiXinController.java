package com.fh.controller.app.appuser;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fh.controller.base.BaseController;
import com.fh.service.system.token.TokenService;
import com.fh.service.system.weixin.WeiXinService;
import com.fh.service.system.wenda.WenDaService;
import com.fh.util.DateUtil;
import com.fh.util.PageData;
import com.fh.util.WeiXin;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/weixinApp", produces = "text/html;charset=UTF-8")
public class WeiXinController extends BaseController{
	@Resource(name="wenDaService")
	private WenDaService wenDaService;
	@Resource(name="weiXinService")
	private WeiXinService weiXinService;
	@Resource(name="tokenService")
	private TokenService tokenService;
	
	@RequestMapping(value="saveWenDa",produces="text/html;charset=UTF-8")
	@ResponseBody
	public synchronized String saveWenDa(String OPENID,String VALUE,String FEN){
		logBefore(logger, "答题记录");
		PageData pd=new PageData();
		pd=this.getPageData();
		try{
			String WENDA_ID= this.get32UUID();
			pd.put("WENDA_ID",WENDA_ID);
			pd.put("DATE", DateUtil.getTime());
			wenDaService.save(pd);
			new Thread(new Wtuishong("你好，你回答的问题结果已产生","农极客农资在线考试","本次参与", DateUtil.getTime(), "本次获得了"+FEN+"分","点击此信息进入页面",OPENID,"http://m.nongjike.cn/nongjike/wenda/daan.html?FIGHT_ID="+WENDA_ID, tokenService,"qRbBDUsQ0JiHyONoPbymWfeIoamfKwXXDVk6xdhjIZ4")).start();
			pd.clear();
			pd.put("code", "1");
			pd.put("WENDA_ID", WENDA_ID);
			pd.put("message", "正确返回数据!");
		}catch(Exception e){
			pd.clear();
			pd.put("code", "2");
			pd.put("message", "程序出错,请联系管理员!");
		}
		ObjectMapper mapper = new ObjectMapper();
		String str = null;
		try {
			str = mapper.writeValueAsString(pd);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}
	
	@RequestMapping(value="findWenDa",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String findWenDa(String WENDA_ID){
		logBefore(logger, "查询问答");
		PageData pd=new PageData();
		pd=this.getPageData();
		try{ 
			PageData pd1=wenDaService.findById(pd);
			pd.clear();
			pd.put("code", "1");
			pd.put("message", "正确返回数据!");
			pd.put("data", pd1);
		}catch(Exception e){
			pd.clear();
			pd.put("code", "2");
			pd.put("message", "程序出错,请联系管理员!");
		}
		ObjectMapper mapper = new ObjectMapper();
		String str = null;
		try {
			str = mapper.writeValueAsString(pd);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}
	
	@RequestMapping(value="WenXinFenXiang",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String FenXiangWenDa(String url) {
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}
	
	//=============================判断是否关注过微信公众号======================================
	@RequestMapping(value="FollowWeiXin",produces="text/html;charset=UTF-8")
	@ResponseBody
	public synchronized String FollowWeiXin(String OPENID){
		logBefore(logger, "判断是否关注过微信公众号");
		PageData pd=new PageData();
		pd=this.getPageData();
		String STATUS="1";
		try{
			String token="";
			PageData pd12 = tokenService.findById(pd);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			long difference = format.parse(format.format(new Date())).getTime()
					- format.parse(pd12.getString("DATE")).getTime();
			if (difference > 100000) { // 标签超时,重新到微信服务器请求标签超时时间为7200秒（7200000毫秒）
				token = WeiXin.access_token1(); 
				pd12.put("token", token);
				pd12.put("DATE", DateUtil.getTime());
				tokenService.edit(pd12);
			} else {
				token = pd12.getString("token");
			}
			JSONObject jsonObject=WeixinInfo.getUserInfo(token, OPENID);
			if(jsonObject==null||jsonObject.getInt("subscribe")!=1){
				STATUS="0";
			}
			System.err.println("===============状态======================="+STATUS);
			System.err.println("===============返回值====================="+jsonObject);
			System.err.println("===============状态=======================11111111"+STATUS);
			pd.clear();
			pd.put("code", "1");
			pd.put("message", "正确返回数据!");
			pd.put("STATUS", STATUS);
			pd.put("WeiXinInfo", jsonObject);
		}catch(Exception e){
			e.printStackTrace();
			pd.clear();
			pd.put("code", "2");
			pd.put("message", "程序错误,请联系管理员!");
		}
		ObjectMapper mapper = new ObjectMapper();
		String str = null;
		try {
			str = mapper.writeValueAsString(pd);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}
	
	//=============================判断是否关注过微信公众号======================================
		@RequestMapping(value="FollowWeiXin1",produces="text/html;charset=UTF-8")
		@ResponseBody
		public synchronized String FollowWeiXin1(String OPENID,String access_token){
			logBefore(logger, "网页授权获取用户基本信息");
			PageData pd=new PageData();
			pd=this.getPageData();
			String STATUS="1";
			try{
				//String token = WeiXin.refresh_token(access_token); 
				JSONObject jsonObject=WeixinInfo.getUserInfo1(access_token, OPENID);
				System.err.println("===============OPENID======================="+OPENID);
				System.err.println("===============返回值====================="+jsonObject);
				pd.clear();
				pd.put("code", "1");
				pd.put("message", "正确返回数据!");
				pd.put("STATUS", STATUS);
				pd.put("WeiXinInfo", jsonObject);
			}catch(Exception e){
				e.printStackTrace();
				pd.clear();
				pd.put("code", "2");
				pd.put("message", "程序错误,请联系管理员!");
			}
			ObjectMapper mapper = new ObjectMapper();
			String str = null;
			try {
				str = mapper.writeValueAsString(pd);
			} catch (JsonGenerationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return str;
		}
	
	@RequestMapping(value="WenTiFenXiang",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String WenTiFenXiang(String url) {
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	} 
	
}
