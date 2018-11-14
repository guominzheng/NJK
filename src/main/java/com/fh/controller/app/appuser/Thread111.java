package com.fh.controller.app.appuser;

import javax.annotation.Resource;

import com.fh.service.system.appuser.AppuserService;
import com.fh.service.system.news.NewsService;
import com.fh.service.system.post.PostService;
import com.fh.util.DateUtil;
import com.fh.util.PageData;
import com.fh.util.UuidUtil;

public class Thread111 implements Runnable{

	private String ID;
	private String MESSAGE;
	private String USER_ID;
	@Resource(name="postService")
	private PostService postService;
	@Resource(name="appuserService")
	private AppuserService appuserService;
	@Resource(name="newsService")
	private NewsService newsService;
	
	public Thread111(String ID,String MESSAGE,String USER_ID,PostService postService,AppuserService appuserService,NewsService newsService){
		this.ID=ID;
		this.MESSAGE=MESSAGE;
		this.USER_ID=USER_ID;
		this.postService=postService;
		this.appuserService=appuserService;
		this.newsService=newsService;
	}
	
	public synchronized void run() {
		// TODO Auto-generated method stub
		try {
			PageData pd=new PageData();
			pd.put("TID", ID);
			pd.put("MESSAGE", MESSAGE);
			pd.put("USER_ID", USER_ID);
			PageData pd_p=postService.findById111(pd);
			pd_p.put("HUIFU", Integer.valueOf(pd_p.getString("HUIFU"))+1);
			postService.editHuiFu(pd_p);
			PageData pd_u=appuserService.findById111(pd);
			PageData pd1=new PageData();
			pd1.put("NEWS_ID", UuidUtil.get32UUID());
			pd1.put("ID", pd.getString("TID"));
			pd1.put("MESSAGE",pd.getString("MESSAGE"));
			pd1.put("NAME", pd_u.getString("NAME"));
			pd1.put("IMG", pd_u.getString("IMG"));
			pd1.put("DATE", DateUtil.getTime());
			pd1.put("USER_ID", pd_p.getString("USER_ID"));
			pd1.put("SUBJECT", pd_p.getString("SUBJECT"));
			pd1.put("STATUS", "0");
			pd1.put("USER_ID1", pd.getString("USER_ID"));
			pd1.put("url", "");
			newsService.save(pd1);
			pd_p.put("STATUS", "1");
			appuserService.editStatus(pd_p);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
