package com.fh.controller.app.appuser.different;

import javax.annotation.Resource;

import com.fh.service.system.appuser.AppuserService;
import com.fh.service.system.comment_activity.Comment_ActivityService;
import com.fh.service.system.comment_tiwen.Comment_TiwenService;
import com.fh.service.system.news.NewsService;
import com.fh.util.DateUtil;
import com.fh.util.PageData;
import com.fh.util.UuidUtil;

public class CommentActivityHui implements Runnable{

	private String USER_ID;
	private String ACTIVITY_POST_ID;
	private String MESSAGE;
	private String COMMENT_ACTIVITY_ID;
	@Resource(name="appuserService")
	private AppuserService appuserService;
	@Resource(name="comment_ActivityService")
	private Comment_ActivityService comment_ActivityService;
	@Resource(name="newsService")
	private NewsService newsService;
	
	public CommentActivityHui(String USER_ID,String ACTIVITY_POST_ID,String MESSAGE,String COMMENT_ACTIVITY_ID,Comment_ActivityService comment_ActivityService,AppuserService appuserService,NewsService newsService){
		this.USER_ID=USER_ID;
		this.ACTIVITY_POST_ID=ACTIVITY_POST_ID;
		this.MESSAGE=MESSAGE;
		this.COMMENT_ACTIVITY_ID=COMMENT_ACTIVITY_ID;
		this.comment_ActivityService=comment_ActivityService;
		this.appuserService=appuserService;
		this.newsService=newsService;
	}

	public synchronized void run() {
		// TODO Auto-generated method stub
				try {
					PageData pd=new PageData();
					pd.put("USER_ID", USER_ID);
					pd.put("ACTIVITY_POST_ID", ACTIVITY_POST_ID);
					pd.put("MESSAGE", MESSAGE);
					pd.put("COMMENT_ACTIVITY_ID", COMMENT_ACTIVITY_ID);
					PageData pd_c=comment_ActivityService.findById(pd);
					PageData pd_u=appuserService.findById111(pd);
					String str = DateUtil.delHTMLTag(pd_c.getString("MESSAGE"));
					String DETAILS1 = str.replace("\r", "");
					String DETAILS2 = DETAILS1.replace("\n", "");
					String DETAILS3 = DETAILS2.replace("&nbsp;", "");
					PageData pd1=new PageData();
					pd1.put("NEWS_ID", UuidUtil.get32UUID());
					pd1.put("ID", pd_c.getString("ACTIVITY_POST_ID"));
					pd1.put("MESSAGE",pd.getString("MESSAGE"));
					pd1.put("NAME", pd_u.getString("NAME"));
					pd1.put("IMG", pd_u.getString("IMG"));
					pd1.put("DATE", DateUtil.getTime());
					pd1.put("USER_ID", pd_c.getString("USER_ID"));
					pd1.put("SUBJECT", DETAILS3);
					pd1.put("STATUS", "2");
					pd1.put("USER_ID1", pd.getString("USER_ID"));
					pd1.put("url", "");
					newsService.save(pd1);
					pd_c.put("STATUS", "1");
					appuserService.editStatus(pd_c);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
	}
}
