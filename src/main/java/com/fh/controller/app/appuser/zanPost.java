package com.fh.controller.app.appuser;

import java.io.Serializable;

import javax.annotation.Resource;

import com.fh.service.system.zan_post.Zan_PostService;
import com.fh.util.PageData;

public class zanPost implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Resource(name="zan_PostService")
	private Zan_PostService zan_PostService;
	
	private String TID;
	private String USER_ID;
	private String STATUS;
	
	public zanPost(String TID,String STATUS,String USER_ID,Zan_PostService zan_PostService){
		this.TID=TID;
		this.USER_ID=USER_ID;
		this.STATUS=STATUS;
		this.zan_PostService=zan_PostService;
	}
	
	public void ZanPost() throws Exception{
		PageData pd=new PageData();
		pd.put("TID", TID);
		pd.put("USER_ID", USER_ID);
		if("1".equals(STATUS)){
			if(zan_PostService.findById(pd)==null){	
				zan_PostService.save(pd);
			}
		}else{
			zan_PostService.delete(pd);
		}
	}
}
