package com.fh.controller.weixin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.marker.weixin.DefaultSession;
import org.marker.weixin.HandleMessageAdapter;
import org.marker.weixin.MySecurity;
import org.marker.weixin.msg.Data4Item;
import org.marker.weixin.msg.Msg4Event;
import org.marker.weixin.msg.Msg4Image;
import org.marker.weixin.msg.Msg4ImageText;
import org.marker.weixin.msg.Msg4Link;
import org.marker.weixin.msg.Msg4Location;
import org.marker.weixin.msg.Msg4Text;
import org.marker.weixin.msg.Msg4Video;
import org.marker.weixin.msg.Msg4Voice;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fh.controller.base.BaseController;
import com.fh.service.system.token.TokenService;
import com.fh.service.weixin.command.CommandService;
import com.fh.service.weixin.imgmsg.ImgmsgService;
import com.fh.service.weixin.textmsg.TextmsgService;
import com.fh.util.Const;
import com.fh.util.DateUtil;
import com.fh.util.PageData;
import com.fh.util.Tools;
import com.fh.util.WeiXin;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;

import javax.net.ssl.X509TrustManager;

/**
 * 类名称：WeixinController.java 类描述： 微信公共平台开发
 * @author FH 作者单位： 联系方式： 创建时间：2014年7月10日
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/weixin")
public class WeixinController extends BaseController {

	@Resource(name = "textmsgService")
	private TextmsgService textmsgService;
	@Resource(name = "commandService")
	private CommandService commandService;
	@Resource(name = "imgmsgService")
	private ImgmsgService imgmsgService;
	@Resource(name="tokenService")
	private TokenService tokenService;

	/**
	 * 接口验证,总入口
	 * @param out
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/index")
	public void index(PrintWriter out, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logBefore(logger, "微信接口");
		PageData pd = new PageData();
		try {
			fasong();
			pd = this.getPageData();
			String signature = pd.getString("signature"); // 微信加密签名
			String timestamp = pd.getString("timestamp"); // 时间戳
			String nonce = pd.getString("nonce"); // 随机数
			String echostr = pd.getString("echostr"); // 字符串

			if (null != signature && null != timestamp && null != nonce && null != echostr) {/* 接口验证 */
				logBefore(logger, "进入身份验证");
				List<String> list = new ArrayList<String>(3) {
					private static final long serialVersionUID = 2621444383666420433L;

					public String toString() { // 重写toString方法，得到三个参数的拼接字符串
						return this.get(0) + this.get(1) + this.get(2);
					}
				};
				list.add(Tools.readTxtFile(Const.WEIXIN)); // 读取Token(令牌)
				list.add(timestamp);
				list.add(nonce);
				Collections.sort(list); // 排序
				String tmpStr = new MySecurity().encode(list.toString(), MySecurity.SHA_1); // SHA-1加密

				if (signature.equals(tmpStr)) {
					out.write(echostr); // 请求验证成功，返回随机码
				} else {
					out.write("");
				}
				out.flush();
				out.close();
			} else {/* 消息处理 */
				logBefore(logger, "进入消息处理");
				response.reset();
				sendMsg(request, response);
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
	}

	/**
	 * 处理微信服务器发过来的各种消息，包括：文本、图片、地理位置、音乐等等
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void sendMsg(HttpServletRequest request, HttpServletResponse response) throws Exception {

		InputStream is = request.getInputStream();
		OutputStream os = response.getOutputStream();

		final DefaultSession session = DefaultSession.newInstance();
		session.addOnHandleMessageListener(new HandleMessageAdapter() {

			/**
			 * 事件
			 */
			@Override
			public void onEventMsg(Msg4Event msg) {
				/**
				 * msg.getEvent() unsubscribe：取消关注 ; subscribe：关注
				 */
				if ("subscribe".equals(msg.getEvent())) {
					returnMSg(msg, null, "关注");
				}
			}

			/**
			 * 收到的文本消息
			 */
			@Override
			public void onTextMsg(Msg4Text msg) {
				returnMSg(null, msg, msg.getContent().trim());
			}

			@Override
			public void onImageMsg(Msg4Image msg) {
				// TODO Auto-generated method stub
				super.onImageMsg(msg);
			}

			@Override
			public void onLocationMsg(Msg4Location msg) {
				// TODO Auto-generated method stub
				super.onLocationMsg(msg);
			}

			@Override
			public void onLinkMsg(Msg4Link msg) {
				// TODO Auto-generated method stub
				super.onLinkMsg(msg);
			}

			@Override
			public void onVideoMsg(Msg4Video msg) {
				// TODO Auto-generated method stub
				super.onVideoMsg(msg);
			}

			@Override
			public void onVoiceMsg(Msg4Voice msg) {
				// TODO Auto-generated method stub
				Recognition(null, msg, msg.getRecognition());
			}

			@Override
			public void onErrorMsg(int errorCode) {
				// TODO Auto-generated method stub
				super.onErrorMsg(errorCode);
			}

			
			public void Recognition(Msg4Event msg,Msg4Voice vmsg,String recog){
				System.err.println("---------------接受语音信息------------------");
				PageData voicepd;
				PageData pd=new PageData();
				String toUserName, fromUserName, recognition;
				if (null == msg) {
					toUserName = vmsg.getToUserName();
					fromUserName = vmsg.getFromUserName();
					recognition=vmsg.getRecognition();
				} else {
					toUserName = msg.getToUserName();
					fromUserName = msg.getFromUserName();
					recognition = msg.getCreateTime();
					
				}
				System.err.println(recognition+"识别小时=========================");
				pd.put("KEYWORD", recognition);
				try {
					voicepd = textmsgService.findByKw(pd);
					if (null != voicepd) {
						Msg4Text rmsg = new Msg4Text();
						rmsg.setFromUserName(toUserName);
						rmsg.setToUserName(fromUserName);
						// rmsg.setFuncFlag("0");
						rmsg.setContent(voicepd.getString("CONTENT")); // 回复文字消息
						session.callback(rmsg);
					} else {
						voicepd = imgmsgService.findByKw(pd);
						if (null != voicepd) {
							Msg4ImageText mit = new Msg4ImageText();
							mit.setFromUserName(toUserName);
							mit.setToUserName(fromUserName);
							mit.setCreateTime(recognition);
							// 回复图文消息
							if (null != voicepd.getString("TITLE1") && null != voicepd.getString("IMGURL1")) {
								Data4Item d1 = new Data4Item(voicepd.getString("TITLE1"), voicepd.getString("DESCRIPTION1"), voicepd.getString("IMGURL1"), voicepd.getString("TOURL1"));
								mit.addItem(d1);

								if (null != voicepd.getString("TITLE2") && null != voicepd.getString("IMGURL2") && !"".equals(voicepd.getString("TITLE2").trim()) && !"".equals(voicepd.getString("IMGURL2").trim())) {
									Data4Item d2 = new Data4Item(voicepd.getString("TITLE2"), voicepd.getString("DESCRIPTION2"), voicepd.getString("IMGURL2"), voicepd.getString("TOURL2"));
									mit.addItem(d2);
								}
								if (null != voicepd.getString("TITLE3") && null != voicepd.getString("IMGURL3") && !"".equals(voicepd.getString("TITLE3").trim()) && !"".equals(voicepd.getString("IMGURL3").trim())) {
									Data4Item d3 = new Data4Item(voicepd.getString("TITLE3"), voicepd.getString("DESCRIPTION3"), voicepd.getString("IMGURL3"), voicepd.getString("TOURL3"));
									mit.addItem(d3);
								}
								if (null != voicepd.getString("TITLE4") && null != voicepd.getString("IMGURL4") && !"".equals(voicepd.getString("TITLE4").trim()) && !"".equals(voicepd.getString("IMGURL4").trim())) {
									Data4Item d4 = new Data4Item(voicepd.getString("TITLE4"), voicepd.getString("DESCRIPTION4"), voicepd.getString("IMGURL4"), voicepd.getString("TOURL4"));
									mit.addItem(d4);
								}
								if (null != voicepd.getString("TITLE5") && null != voicepd.getString("IMGURL5") && !"".equals(voicepd.getString("TITLE5").trim()) && !"".equals(voicepd.getString("IMGURL5").trim())) {
									Data4Item d5 = new Data4Item(voicepd.getString("TITLE5"), voicepd.getString("DESCRIPTION5"), voicepd.getString("IMGURL5"), voicepd.getString("TOURL5"));
									mit.addItem(d5);
								}
								if (null != voicepd.getString("TITLE6") && null != voicepd.getString("IMGURL6") && !"".equals(voicepd.getString("TITLE6").trim()) && !"".equals(voicepd.getString("IMGURL6").trim())) {
									Data4Item d6 = new Data4Item(voicepd.getString("TITLE6"), voicepd.getString("DESCRIPTION6"), voicepd.getString("IMGURL6"), voicepd.getString("TOURL6"));
									mit.addItem(d6);
								}
								if (null != voicepd.getString("TITLE7") && null != voicepd.getString("IMGURL7") && !"".equals(voicepd.getString("TITLE7").trim()) && !"".equals(voicepd.getString("IMGURL7").trim())) {
									Data4Item d7 = new Data4Item(voicepd.getString("TITLE7"), voicepd.getString("DESCRIPTION7"), voicepd.getString("IMGURL7"), voicepd.getString("TOURL7"));
									mit.addItem(d7);
								}
								if (null != voicepd.getString("TITLE8") && null != voicepd.getString("IMGURL8") && !"".equals(voicepd.getString("TITLE8").trim()) && !"".equals(voicepd.getString("IMGURL8").trim())) {
									Data4Item d8 = new Data4Item(voicepd.getString("TITLE8"), voicepd.getString("DESCRIPTION8"), voicepd.getString("IMGURL8"), voicepd.getString("TOURL8"));
									mit.addItem(d8);
								}
							}
							// mit.setFuncFlag("0");
							session.callback(mit);
						} else {
							voicepd = commandService.findByKw(pd);
							if (null != voicepd) {
								Runtime runtime = Runtime.getRuntime();
								runtime.exec(voicepd.getString("COMMANDCODE"));
							} else {
								Msg4Text rmsg = new Msg4Text();
								rmsg.setFromUserName(toUserName);
								rmsg.setToUserName(fromUserName);
								rmsg.setContent("【关键词：与数据库里的关键词没有匹配结果】");
								session.callback(rmsg);
							}
						}
					}
				} catch (Exception e1) {
					logBefore(logger, "匹配错误");
				}
			}
			
			/**
			 * 返回消息
			 * @param emsg
			 * @param tmsg
			 * @param getmsg
			 */
			public void returnMSg(Msg4Event emsg, Msg4Text tmsg, String getmsg) {
				PageData msgpd;
				PageData pd = new PageData();
				String toUserName, fromUserName, createTime;
				if (null == emsg) {
					toUserName = tmsg.getToUserName();
					fromUserName = tmsg.getFromUserName();
					createTime = tmsg.getCreateTime();
				} else {
					toUserName = emsg.getToUserName();
					fromUserName = emsg.getFromUserName();
					createTime = emsg.getCreateTime();
					
				}
				
				pd.put("KEYWORD", getmsg);
				try {
					msgpd = textmsgService.findByKw(pd);
					if (null != msgpd) {
						Msg4Text rmsg = new Msg4Text();
						rmsg.setFromUserName(toUserName);
						rmsg.setToUserName(fromUserName);
						// rmsg.setFuncFlag("0");
						rmsg.setContent(msgpd.getString("CONTENT")); // 回复文字消息
						session.callback(rmsg);
					} else {
						msgpd = imgmsgService.findByKw(pd);
						if (null != msgpd) {
							Msg4ImageText mit = new Msg4ImageText();
							mit.setFromUserName(toUserName);
							mit.setToUserName(fromUserName);
							mit.setCreateTime(createTime);
							// 回复图文消息
							if (null != msgpd.getString("TITLE1") && null != msgpd.getString("IMGURL1")) {
								Data4Item d1 = new Data4Item(msgpd.getString("TITLE1"), msgpd.getString("DESCRIPTION1"), msgpd.getString("IMGURL1"), msgpd.getString("TOURL1"));
								mit.addItem(d1);

								if (null != msgpd.getString("TITLE2") && null != msgpd.getString("IMGURL2") && !"".equals(msgpd.getString("TITLE2").trim()) && !"".equals(msgpd.getString("IMGURL2").trim())) {
									Data4Item d2 = new Data4Item(msgpd.getString("TITLE2"), msgpd.getString("DESCRIPTION2"), msgpd.getString("IMGURL2"), msgpd.getString("TOURL2"));
									mit.addItem(d2);
								}
								if (null != msgpd.getString("TITLE3") && null != msgpd.getString("IMGURL3") && !"".equals(msgpd.getString("TITLE3").trim()) && !"".equals(msgpd.getString("IMGURL3").trim())) {
									Data4Item d3 = new Data4Item(msgpd.getString("TITLE3"), msgpd.getString("DESCRIPTION3"), msgpd.getString("IMGURL3"), msgpd.getString("TOURL3"));
									mit.addItem(d3);
								}
								if (null != msgpd.getString("TITLE4") && null != msgpd.getString("IMGURL4") && !"".equals(msgpd.getString("TITLE4").trim()) && !"".equals(msgpd.getString("IMGURL4").trim())) {
									Data4Item d4 = new Data4Item(msgpd.getString("TITLE4"), msgpd.getString("DESCRIPTION4"), msgpd.getString("IMGURL4"), msgpd.getString("TOURL4"));
									mit.addItem(d4);
								}
								if (null != msgpd.getString("TITLE5") && null != msgpd.getString("IMGURL5") && !"".equals(msgpd.getString("TITLE5").trim()) && !"".equals(msgpd.getString("IMGURL5").trim())) {
									Data4Item d5 = new Data4Item(msgpd.getString("TITLE5"), msgpd.getString("DESCRIPTION5"), msgpd.getString("IMGURL5"), msgpd.getString("TOURL5"));
									mit.addItem(d5);
								}
								if (null != msgpd.getString("TITLE6") && null != msgpd.getString("IMGURL6") && !"".equals(msgpd.getString("TITLE6").trim()) && !"".equals(msgpd.getString("IMGURL6").trim())) {
									Data4Item d6 = new Data4Item(msgpd.getString("TITLE6"), msgpd.getString("DESCRIPTION6"), msgpd.getString("IMGURL6"), msgpd.getString("TOURL6"));
									mit.addItem(d6);
								}
								if (null != msgpd.getString("TITLE7") && null != msgpd.getString("IMGURL7") && !"".equals(msgpd.getString("TITLE7").trim()) && !"".equals(msgpd.getString("IMGURL7").trim())) {
									Data4Item d7 = new Data4Item(msgpd.getString("TITLE7"), msgpd.getString("DESCRIPTION7"), msgpd.getString("IMGURL7"), msgpd.getString("TOURL7"));
									mit.addItem(d7);
								}
								if (null != msgpd.getString("TITLE8") && null != msgpd.getString("IMGURL8") && !"".equals(msgpd.getString("TITLE8").trim()) && !"".equals(msgpd.getString("IMGURL8").trim())) {
									Data4Item d8 = new Data4Item(msgpd.getString("TITLE8"), msgpd.getString("DESCRIPTION8"), msgpd.getString("IMGURL8"), msgpd.getString("TOURL8"));
									mit.addItem(d8);
								}
							}
							// mit.setFuncFlag("0");
							session.callback(mit);
						} else {
							msgpd = commandService.findByKw(pd);
							if (null != msgpd) {
								Runtime runtime = Runtime.getRuntime();
								runtime.exec(msgpd.getString("COMMANDCODE"));
							} else {
								Msg4Text rmsg = new Msg4Text();
								rmsg.setFromUserName(toUserName);
								rmsg.setToUserName(fromUserName);
								rmsg.setContent("现在不在线哦，有事可以直接留言，看到了，我会及时回复的。");
								session.callback(rmsg);
							}
						}
					}
				} catch (Exception e1) {
					logBefore(logger, "匹配错误");
				}
			}

		});

		/* 必须调用这两个方法 如果不调用close方法，将会出现响应数据串到其它Servlet中。 */
		session.process(is, os); // 处理微信消息
		session.close(); // 关闭Session
	}

	// ================================================获取关注列表==============================================================
	public final static String gz_url = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=";

	// 获取access_token
	@RequestMapping(value = "/getGz")
	public void getGz(PrintWriter out) {
		logBefore(logger, "获取关注列表");
		try {
			String access_token = readTxtFile("e:/access_token.txt");

			System.out.println(access_token + "============");

			String requestUrl = gz_url.replace("ACCESS_TOKEN", access_token);

			System.out.println(requestUrl + "============");

			JSONObject jsonObject = httpRequst(requestUrl, "GET", null);
			System.out.println(jsonObject);
			// System.out.println(jsonObject.getString("total")+"============");

			/*
			 * PrintWriter pw; try { pw = new PrintWriter( new FileWriter( "e:/gz.txt" ) ); pw.print(jsonObject.getString("total")); pw.close(); } catch (IOException e) { // TODO Auto-generated catch block
			 * e.printStackTrace(); } out.write("success"); out.close();
			 */
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
	}

	// 读取文件
	public String readTxtFile(String filePath) {
		try {
			String encoding = "utf-8";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					// System.out.println(lineTxt);
					return lineTxt;
				}
				read.close();
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
		return "";
	}

	// ================================================获取access_token==============================================================
	public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?" + "grant_type=client_credential&appid=APPID&secret=APPSECRET";

	// 获取access_token
	@RequestMapping(value = "/getAt")
	public void getAt(PrintWriter out) {
		logBefore(logger, "获取access_token");
		try {
			String appid = "wx3f6627be4ed503c9";
			String appsecret = "5080ea88e4197c91844e2aa76c971cb5";

			String requestUrl = access_token_url.replace("APPID", appid).replace("APPSECRET", appsecret);
			JSONObject jsonObject = httpRequst(requestUrl, "GET", null);

			// System.out.println(jsonObject.getString("access_token")+"============");

			PrintWriter pw;
			try {
				pw = new PrintWriter(new FileWriter("e:/access_token.txt"));
				pw.print(jsonObject.getString("access_token"));
				pw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			out.write("success");
			out.close();
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
	}

	public void fasong(){
		logBefore(logger, "发送菜单");
		PageData pd=new PageData();
		String token="";
			try {
				PageData pd12 = tokenService.findById(pd);
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				long difference = format.parse(format.format(new Date())).getTime()
						- format.parse(pd12.getString("DATE")).getTime();
				if (difference > 60000) { // 标签超时,重新到微信服务器请求标签超 时时间为7200秒（7200000毫秒）
					token = WeiXin.access_token1(); 
					pd12.put("token", token);
					pd12.put("DATE", DateUtil.getTime());
					tokenService.edit(pd12);
				} else {
					token = pd12.getString("token");
				}
				//String user="{\"button\":[{\"type\":\"view\",\"name\":\"微商城\",\"key\":\"11\",\"url\":\"http://www.meitiannongzi.com/nongjike/group.html\"}]}";
				String user="{\"button\":[{\"type\":\"view\",\"name\":\"历史信息\",\"key\":\"11\",\"url\":\"https://mp.weixin.qq.com/mp/profile_ext?action=home&__biz=MzIzNzkzMzU0Nw==&scene=124#wechat_redirect\"},{\"type\":\"view\",\"name\":\"app下载\",\"key\":\"11\",\"url\":\"http://a.app.qq.com/o/simple.jsp?pkgname=com.ylsoft.njk\"},{\"type\":\"view\",\"name\":\"我的订单\",\"key\":\"11\",\"url\":\"http://www.meitiannongzi.com/nongjike/order.html\"}]}";
/*				String user_define_menu = "{\"button\":["
						+ "{\"name\":\"微登录\",\"sub_button\":"
						+ "[{\"type\":\"view\",\"name\":\"授权登录\",\"key\":\"11\",\"url\":\"http://www.baidu.com\"},"
						+ "{\"type\":\"click\",\"name\":\"帐号解绑\",\"key\":\"22\"}]}"
						+ ",{\"type\":\"click\",\"name\":\"微推送\",\"key\":\"20_PROMANAGE\"}]}";*/
				System.err.println(user); 
				String action = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+token;
				URL url = new URL(action);
				HttpURLConnection http = (HttpURLConnection) url.openConnection();

				http.setRequestMethod("POST");
				http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
				http.setDoOutput(true);
				http.setDoInput(true);
				System.setProperty("sun.net.client.defaultConnectTimeout", "30000");//连接超时30秒
				System.setProperty("sun.net.client.defaultReadTimeout", "30000"); //读取超时30秒

				http.connect();
				OutputStream os= http.getOutputStream();
				os.write(user.getBytes("UTF-8"));//传入参数
				os.flush();
				os.close();

				InputStream is =http.getInputStream();
				int size =is.available();
				byte[] jsonBytes =new byte[size];
				is.read(jsonBytes);
				String message=new String(jsonBytes,"UTF-8");
				System.out.println(message);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
	public JSONObject httpRequst(String requestUrl, String requetMethod, String outputStr) {
		JSONObject jsonobject = null;
		StringBuffer buffer = new StringBuffer();
		try {
			// 创建SSLContext对象，并使用我们指定的新人管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslcontext = SSLContext.getInstance("SSL", "SunJSSE");
			sslcontext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocktFactory对象
			SSLSocketFactory ssf = sslcontext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requetMethod);

			if ("GET".equalsIgnoreCase(requetMethod))
				httpUrlConn.connect();

			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			jsonobject = JSONObject.fromObject(buffer.toString());
		} catch (ConnectException ce) {
			// TODO: handle exception
		} catch (Exception e) {
		}
		return jsonobject;
	}
	// ================================================获取access_token==============================================================
}

// ================================================获取access_token==============================================================
class MyX509TrustManager implements X509TrustManager {

	public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		// TODO Auto-generated method stub

	}

	public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		// TODO Auto-generated method stub

	}

	public X509Certificate[] getAcceptedIssuers() {
		// TODO Auto-generated method stub
		return null;
	}
}
// ================================================获取access_token==============================================================