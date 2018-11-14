package com.fh.controller.app.appuser.different;

import javax.annotation.Resource;

import com.fh.service.system.activity_post.Activity_PostService;
import com.fh.service.system.appuser.AppuserService;
import com.fh.service.system.news.NewsService;
import com.fh.util.DateUtil;
import com.fh.util.PageData;
import com.fh.util.UuidUtil;

public class CommentActivityHui2 implements Runnable{
	private String USER_ID;
	private String ACTIVITY_POST_ID;
	private String MESSAGE;
	private String PUSER_ID;
	@Resource(name="activity_PostService")
	private Activity_PostService activity_PostService;
	@Resource(name="appuserService")
	private AppuserService appuserService;
	@Resource(name="newsService")
	private NewsService newsService;
	
	public CommentActivityHui2(String USER_ID,String ACTIVITY_POST_ID,String MESSAGE,String PUSER_ID,Activity_PostService activity_PostService,AppuserService appuserService,NewsService newsService){
		this.USER_ID=USER_ID;
		this.ACTIVITY_POST_ID=ACTIVITY_POST_ID;
		this.MESSAGE=MESSAGE;
		this.PUSER_ID=PUSER_ID;
		this.activity_PostService=activity_PostService;
		this.appuserService=appuserService;
		this.newsService=newsService;
	}
	
	
	public synchronized void run() {
					try {
						PageData pd=new PageData();
						pd.put("USER_ID", USER_ID);
						pd.put("ACTIVITY_POST_ID", ACTIVITY_POST_ID);
						pd.put("MESSAGE", MESSAGE);
						pd.put("PUSER_ID", PUSER_ID);
						PageData pd_p = activity_PostService.findById(pd);
						String str = DateUtil.delHTMLTag(pd_p.getString("MESSAGE"));
						String DETAILS1 = str.replace("\r", "");
						String DETAILS2 = DETAILS1.replace("\n", "");
						String DETAILS3 = DETAILS2.replace("&nbsp;", "");
						PageData pd_c=appuserService.findById111(pd);
						PageData pd_u=appuserService.findById222(pd);
						PageData pd1=new PageData();
						pd1.put("NEWS_ID", UuidUtil.get32UUID());
						pd1.put("ID", pd.getString("ACTIVITY_POST_ID"));
						pd1.put("MESSAGE",pd.getString("MESSAGE"));
						pd1.put("NAME", pd_c.getString("NAME"));
						pd1.put("IMG", pd_c.getString("IMG"));
						pd1.put("DATE", DateUtil.getTime());
						pd1.put("USER_ID", pd.getString("PUSER_ID"));
						pd1.put("SUBJECT", DETAILS3);
						pd1.put("STATUS", "2");
						pd1.put("USER_ID1", pd.getString("USER_ID"));
						pd1.put("url", "");
						newsService.save(pd1);
						pd_u.put("STATUS", "1");
						appuserService.editStatus(pd_u);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	}
}
