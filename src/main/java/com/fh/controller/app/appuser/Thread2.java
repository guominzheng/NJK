package com.fh.controller.app.appuser;

import javax.annotation.Resource;

import com.fh.service.system.post_img.Post_ImgService;
import com.fh.service.system.token.TokenService;
import com.fh.util.Const;
import com.fh.util.DateUtil;
import com.fh.util.ImageAnd64Binary;
import com.fh.util.PageData;

public class Thread2 implements Runnable{

	private String TOUR_IMG; 
	private String filePath;
	private String TID;
	private String ffile;
	private int ORDE_BY;
	@Resource(name="post_ImgService")
	private Post_ImgService post_ImgService;
	
	public Thread2(String TOUR_IMG,String TOUR_IMG1,String filePath,String TID,String ffile,Post_ImgService post_ImgService,int ORDE_BY) {  
	       this.TOUR_IMG=TOUR_IMG;  
	       this.filePath=filePath;
	       this.TID=TID;
	       this.ffile=ffile;
	       this.post_ImgService=post_ImgService;
	       this.ORDE_BY=ORDE_BY;
	 } 
	

	public synchronized void run() {
		// TODO Auto-generated method stub
    		try {
            		boolean flag=ImageAnd64Binary.generateImage(TOUR_IMG,filePath);
                	/*String TOUR_IMG1=Const.SERVERPATH+Const.FILEPATHIMG+"tiezi/"+DateUtil.getDays()+"/"+ffile;*/
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    	
	}
	

}
