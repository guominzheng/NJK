package com.fh.controller.app.appuser;

import javax.annotation.Resource;

import com.fh.service.system.history.HistoryService;
import com.fh.util.DateUtil;
import com.fh.util.PageData;
import com.fh.util.UuidUtil;

public class History implements Runnable{

	private String SUBJECT;
	private String ID;
	private String STATUS;
	private String TYPE;
	private String USER_ID;
	private String url;
	@Resource(name="historyService")
	private HistoryService historyService;
	
    public History(String SUBJECT,String ID,String STATUS,String TYPE,String USER_ID,String url,HistoryService historyService) {
		// TODO Auto-generated constructor stub
    	this.SUBJECT=SUBJECT;
    	this.ID=ID;
    	this.STATUS=STATUS;
    	this.TYPE=TYPE;
    	this.USER_ID=USER_ID;
    	this.url=url;
    	this.historyService=historyService;
	}
	
	public synchronized void run() {
		// TODO Auto-generated method stub
		try{
			PageData pd=new PageData();
			pd.put("HISTORY_ID", UuidUtil.get32UUID());
			pd.put("SUBJECT", SUBJECT);
			pd.put("ID", ID);
			pd.put("STATUS", STATUS);
			pd.put("TYPE", TYPE);
			pd.put("USER_ID", USER_ID);
			pd.put("DATE", DateUtil.getTime());
			pd.put("url", url);
			historyService.delete(pd);
			historyService.save(pd);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
