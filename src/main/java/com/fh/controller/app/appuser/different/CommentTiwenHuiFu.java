package com.fh.controller.app.appuser.different;


import javax.annotation.Resource;

import com.fh.service.system.appuser.AppuserService;
import com.fh.service.system.comment_tiwen.Comment_TiwenService;
import com.fh.service.system.news.NewsService;
import com.fh.util.DateUtil;
import com.fh.util.PageData;
import com.fh.util.UuidUtil;

public class CommentTiwenHuiFu implements Runnable{
	
	private String USER_ID;
	private String TIWEN_ID;
	private String MESSAGE;
	private String COMMENT_TIWEN_ID;
	private String url;
	@Resource(name="appuserService")
	private AppuserService appuserService;
	@Resource(name="comment_TiwenService")
	private Comment_TiwenService comment_TiwenService;
	@Resource(name="newsService")
	private NewsService newsService;
	
	public CommentTiwenHuiFu(String USER_ID,String TIWEN_ID,String MESSAGE,String COMMENT_TIWEN_ID,Comment_TiwenService comment_TiwenService,AppuserService appuserService,NewsService newsService,String url){
		this.USER_ID=USER_ID;
		this.TIWEN_ID=TIWEN_ID;
		this.MESSAGE=MESSAGE;
		this.COMMENT_TIWEN_ID=COMMENT_TIWEN_ID;
		this.comment_TiwenService=comment_TiwenService;
		this.appuserService=appuserService;
		this.newsService=newsService;
		this.url=url;
	}

	public synchronized void run() {
		// TODO Auto-generated method stub
				try {
					PageData pd=new PageData();
					pd.put("USER_ID", USER_ID);
					pd.put("TIWEN_ID", TIWEN_ID);
					pd.put("MESSAGE", MESSAGE);
					pd.put("COMMENT_TIWEN_ID", COMMENT_TIWEN_ID);
					PageData pd_c=comment_TiwenService.findById(pd);
					PageData pd_u=appuserService.findById111(pd);
					PageData pd1=new PageData();
					pd1.put("NEWS_ID", UuidUtil.get32UUID());
					pd1.put("ID", pd_c.getString("TIWEN_ID"));
					pd1.put("MESSAGE",pd.getString("MESSAGE"));
					pd1.put("NAME", pd_u.getString("NAME"));
					pd1.put("IMG", pd_u.getString("IMG"));
					pd1.put("DATE", DateUtil.getTime());
					pd1.put("USER_ID", pd_c.getString("USER_ID"));
					pd1.put("SUBJECT", pd_c.getString("MESSAGE"));
					pd1.put("STATUS", "1");
					pd1.put("USER_ID1", pd.getString("USER_ID"));
					pd1.put("url", url);
					newsService.save(pd1);
					pd_c.put("STATUS", "1");
					appuserService.editStatus(pd_c);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
	}

	
}
