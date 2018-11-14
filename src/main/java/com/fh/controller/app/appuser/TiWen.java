package com.fh.controller.app.appuser;

import javax.annotation.Resource;

import com.fh.service.system.tiwen_img.TiWen_ImgService;
import com.fh.util.ImageAnd64Binary;

public class TiWen implements Runnable{
	private String TOUR_IMG; 
	private String filePath;
	@Resource(name="tiWen_ImgService")
	private TiWen_ImgService tiWen_ImgService;
	
	public  TiWen(String TOUR_IMG,String filePath,TiWen_ImgService tiWen_ImgService) {  
	       this.TOUR_IMG=TOUR_IMG;  
	       this.filePath=filePath;
	       this.tiWen_ImgService=tiWen_ImgService;
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
