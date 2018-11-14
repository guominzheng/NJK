package com.fh.controller.app.appuser.different;

import javax.annotation.Resource;

import com.fh.service.system.appuser.AppuserService;
import com.fh.service.system.news.NewsService;
import com.fh.service.system.tiwen.TiWenService;
import com.fh.util.DateUtil;
import com.fh.util.PageData;
import com.fh.util.UuidUtil;

public class CommetnTiwenHuiFu2 implements Runnable{
	private String USER_ID;
	private String TIWEN_ID;
	private String MESSAGE;
	private String PUSER_ID;
	private String url;
	@Resource(name="tiWenService")
	private TiWenService tiWenService;
	@Resource(name="appuserService")
	private AppuserService appuserService;
	@Resource(name="newsService")
	private NewsService newsService;
	
	public CommetnTiwenHuiFu2(String USER_ID,String TIWEN_ID,String MESSAGE,String PUSER_ID,TiWenService tiWenService,AppuserService appuserService,NewsService newsService,String url){
		this.USER_ID=USER_ID;
		this.TIWEN_ID=TIWEN_ID;
		this.MESSAGE=MESSAGE;
		this.PUSER_ID=PUSER_ID;
		this.tiWenService=tiWenService;
		this.appuserService=appuserService;
		this.newsService=newsService;
		this.url=url;
	}
	
	
	@Override
	public synchronized void run() {
					try {
						PageData pd=new PageData();
						pd.put("USER_ID", USER_ID);
						pd.put("TIWEN_ID", TIWEN_ID);
						pd.put("MESSAGE", MESSAGE);
						pd.put("PUSER_ID", PUSER_ID);
						PageData pd_p = tiWenService.findById(pd);
						PageData pd_c=appuserService.findById111(pd);
						PageData pd_u=appuserService.findById222(pd);
						PageData pd1=new PageData();
						pd1.put("NEWS_ID", UuidUtil.get32UUID());
						pd1.put("ID", pd.getString("TIWEN_ID"));
						pd1.put("MESSAGE",pd.getString("MESSAGE"));
						pd1.put("NAME", pd_c.getString("NAME"));
						pd1.put("IMG", pd_c.getString("IMG"));
						pd1.put("DATE", DateUtil.getTime());
						pd1.put("USER_ID", pd.getString("PUSER_ID"));
						pd1.put("SUBJECT", pd_p.getString("MESSAGE"));
						pd1.put("STATUS", "1");
						pd1.put("USER_ID1", pd.getString("USER_ID"));
						pd1.put("url", url);
						newsService.save(pd1);
						pd_u.put("STATUS", "1");
						appuserService.editStatus(pd_u);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	}

}
