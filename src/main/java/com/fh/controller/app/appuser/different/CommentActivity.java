package com.fh.controller.app.appuser.different;

import javax.annotation.Resource;

import com.fh.service.system.activity_post.Activity_PostService;
import com.fh.service.system.appuser.AppuserService;
import com.fh.service.system.news.NewsService;
import com.fh.util.DateUtil;
import com.fh.util.PageData;
import com.fh.util.UuidUtil;

public class CommentActivity implements Runnable{

	public String ID;
	public String USER_ID;
	public String MESSAGE;
	@Resource(name="activity_PostService")
	private Activity_PostService activity_PostService;
	@Resource(name="appuserService")
	public AppuserService appuserService;
	@Resource(name="newsService")
	public NewsService newsService;
	
	public CommentActivity(String ACTIVITY_POST_ID,String USER_ID,String MESSAGE,Activity_PostService activity_PostService,AppuserService appuserService,NewsService newsService){
		this.ID=ACTIVITY_POST_ID;
		this.USER_ID=USER_ID;
		this.MESSAGE=MESSAGE;
		this.activity_PostService=activity_PostService;
		this.appuserService=appuserService;
		this.newsService=newsService;
	}
	
	@Override
	public synchronized void run() {
		// TODO Auto-generated method stub
		try{
			PageData pd=new PageData();
			pd.put("ACTIVITY_POST_ID", ID);
			pd.put("MESSAGE", MESSAGE);
			pd.put("USER_ID", USER_ID);
			PageData pd_t=activity_PostService.findById(pd);
			String str = DateUtil.delHTMLTag(pd_t.getString("MESSAGE"));
			String DETAILS1 = str.replace("\r", "");
			String DETAILS2 = DETAILS1.replace("\n", "");
			String DETAILS3 = DETAILS2.replace("&nbsp;", "");
			PageData pd_u=appuserService.findById111(pd);
			PageData pd1=new PageData();
			pd1.put("NEWS_ID", UuidUtil.get32UUID());
			pd1.put("ID", pd_t.getString("ACTIVITY_POST_ID"));
			pd1.put("MESSAGE",pd.getString("MESSAGE"));
			pd1.put("NAME", pd_u.getString("NAME"));
			pd1.put("IMG", pd_u.getString("IMG"));
			pd1.put("DATE", DateUtil.getTime());
			pd1.put("USER_ID", pd_t.getString("USER_ID"));
			pd1.put("SUBJECT", DETAILS3);
			pd1.put("STATUS", "2");
			pd1.put("USER_ID1", pd.getString("USER_ID"));
			pd1.put("url", "");
			newsService.save(pd1);
			pd_t.put("STATUS", "1");
			appuserService.editStatus(pd_t);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
