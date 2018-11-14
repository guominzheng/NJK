package com.fh.controller.app.appuser.different;

import javax.annotation.Resource;

import com.fh.service.system.appuser.AppuserService;
import com.fh.service.system.news.NewsService;
import com.fh.service.system.tiwen.TiWenService;
import com.fh.util.DateUtil;
import com.fh.util.PageData;
import com.fh.util.UuidUtil;

public class CommentTiwen implements Runnable{
	
	public String ID;
	public String USER_ID;
	public String MESSAGE;
	public String url;
	@Resource(name="tiWenService")
	public TiWenService tiWenService;
	@Resource(name="appuserService")
	public AppuserService appuserService;
	@Resource(name="newsService")
	public NewsService newsService;
	
	public CommentTiwen(String TIWEN_ID,String MESSAGE,String USER_ID,TiWenService tiWenService,AppuserService appuserService,NewsService newsService,String url){
		this.ID=TIWEN_ID;
		this.USER_ID=USER_ID;
		this.MESSAGE=MESSAGE;
		this.tiWenService=tiWenService;
		this.appuserService=appuserService;
		this.newsService=newsService;
		this.url=url;
	}
	
	@Override
	public synchronized void run() {
		// TODO Auto-generated method stub
		try{
			PageData pd=new PageData();
			pd.put("TIWEN_ID", ID);
			pd.put("MESSAGE", MESSAGE);
			pd.put("USER_ID", USER_ID);
			PageData pd_t=tiWenService.findById(pd);
			PageData pd_u=appuserService.findById111(pd);
			PageData pd1=new PageData();
			pd1.put("NEWS_ID", UuidUtil.get32UUID());
			pd1.put("ID", pd.getString("TIWEN_ID"));
			pd1.put("MESSAGE",pd.getString("MESSAGE"));
			pd1.put("NAME", pd_u.getString("NAME"));
			pd1.put("IMG", pd_u.getString("IMG"));
			pd1.put("DATE", DateUtil.getTime());
			pd1.put("USER_ID", pd_t.getString("USER_ID"));
			pd1.put("SUBJECT", pd_t.getString("MESSAGE"));
			pd1.put("STATUS", "1");
			pd1.put("USER_ID1", pd.getString("USER_ID"));
			pd1.put("url", url);
			newsService.save(pd1);
			pd_t.put("STATUS", "1");
			appuserService.editStatus(pd_t);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
