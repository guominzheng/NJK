package com.fh.controller.app.appuser;

import javax.annotation.Resource;

import com.fh.service.system.appuser.AppuserService;
import com.fh.service.system.news.NewsService;
import com.fh.service.system.post.PostService;
import com.fh.util.DateUtil;
import com.fh.util.PageData;
import com.fh.util.UuidUtil;

public class Thread1 implements Runnable{
	
	
	private String USER_ID;
	private String TID;
	private String MESSAGE;
	private String PUSER_ID;
	@Resource(name="postService")
	private PostService postService;
	@Resource(name="appuserService")
	private AppuserService appuserService;
	@Resource(name="newsService")
	private NewsService newsService;
	
	public Thread1(String USER_ID,String TID,String MESSAGE,String PUSER_ID,PostService postService,AppuserService appuserService,NewsService newsService){
		this.USER_ID=USER_ID;
		this.TID=TID;
		this.MESSAGE=MESSAGE;
		this.PUSER_ID=PUSER_ID;
		this.postService=postService;
		this.appuserService=appuserService;
		this.newsService=newsService;
	}
	
	
	public synchronized void run() {
		// TODO Auto-generated method stub
			try {
				PageData pd=new PageData();
				pd.put("USER_ID", USER_ID);
				pd.put("TID", TID);
				pd.put("MESSAGE", MESSAGE);
				pd.put("PUSER_ID", PUSER_ID);
				PageData pd_p = postService.findById111(pd);
				PageData pd_c=appuserService.findById111(pd);
				PageData pd_u=appuserService.findById222(pd);
				PageData pd1=new PageData();
				pd1.put("NEWS_ID", UuidUtil.get32UUID());
				pd1.put("ID", pd.getString("TID"));
				pd1.put("MESSAGE",pd.getString("MESSAGE"));
				pd1.put("NAME", pd_c.getString("NAME"));
				pd1.put("IMG", pd_c.getString("IMG"));
				pd1.put("DATE", DateUtil.getTime());
				pd1.put("USER_ID", pd.getString("PUSER_ID"));
				pd1.put("SUBJECT", pd_p.getString("SUBJECT"));
				pd1.put("STATUS", "0");
				pd1.put("USER_ID1", pd.getString("USER_ID"));
				pd1.put("url", "");
				newsService.save(pd1);
				pd_u.put("STATUS", "1");
				appuserService.editStatus(pd_u);
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
}

