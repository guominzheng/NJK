package com.fh.controller.app.appuser;

import javax.annotation.Resource;

import com.fh.service.system.appuser.AppuserService;
import com.fh.service.system.comment_post.Comment_PostService;
import com.fh.service.system.news.NewsService;
import com.fh.util.DateUtil;
import com.fh.util.PageData;
import com.fh.util.UuidUtil;

public class Thread11 implements Runnable{

	private String USER_ID;
	private String TID;
	private String MESSAGE;
	private String COMMENT_POST_ID;
	@Resource(name="comment_PostService")
	private Comment_PostService comment_PostService;
	@Resource(name="newsService")
	private NewsService newsService;
	@Resource(name="appuserService")
	private AppuserService appuserService;
	
	public Thread11(String USER_ID,String TID,String MESSAGE,String COMMENT_POST_ID,Comment_PostService comment_PostService,NewsService newsService,AppuserService appuserService){
		this.USER_ID=USER_ID;
		this.TID=TID;
		this.MESSAGE=MESSAGE;
		this.COMMENT_POST_ID=COMMENT_POST_ID;
		this.comment_PostService=comment_PostService;
		this.newsService=newsService;
		this.appuserService=appuserService;
	}
	public synchronized void run() {
		// TODO Auto-generated method stub
		try {
			PageData pd=new PageData();
			pd.put("USER_ID", USER_ID);
			pd.put("TID", TID);
			pd.put("MESSAGE", MESSAGE);
			pd.put("COMMENT_POST_ID", COMMENT_POST_ID);
			PageData pd_c=comment_PostService.findById111(pd);
			PageData pd_u=appuserService.findById111(pd);
			PageData pd1=new PageData();
			pd1.put("NEWS_ID", UuidUtil.get32UUID());
			pd1.put("ID", pd.getString("TID"));
			pd1.put("MESSAGE",pd.getString("MESSAGE"));
			pd1.put("NAME", pd_u.getString("NAME"));
			pd1.put("IMG", pd_u.getString("IMG"));
			pd1.put("DATE", DateUtil.getTime());
			pd1.put("USER_ID", pd_c.getString("USER_ID"));
			pd1.put("SUBJECT", pd_c.getString("SUBJECT"));
			pd1.put("STATUS", "0");
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
