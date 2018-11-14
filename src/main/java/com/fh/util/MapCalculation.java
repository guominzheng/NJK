package com.fh.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

import org.apache.commons.lang.StringUtils;

import com.fh.controller.base.BaseController;

public class MapCalculation extends BaseController{

	 public static Object getGeocoderLatitude(String address){  
	        BufferedReader in = null;
	        Map<String,Object> map=new HashMap();
	        try {  
	            address = URLEncoder.encode(address, "UTF-8");  
	            URL tirc = new URL("http://api.map.baidu.com/geocoder?address="+ address +"&output=json&key="+"7d9fbeb43e975cd1e9477a7e5d5e192a");  
	            in = new BufferedReader(new InputStreamReader(tirc.openStream(),"UTF-8"));  
	            String res;  
	            StringBuilder sb = new StringBuilder("");  
	            while((res = in.readLine())!=null){  
	                sb.append(res.trim());  
	            }  
	            String str = sb.toString();  
	            if(StringUtils.isNotEmpty(str)){  
	                int lngStart = str.indexOf("lng\":");  
	                int lngEnd = str.indexOf(",\"lat");  
	                int latEnd = str.indexOf("},\"precise");  
	                if(lngStart > 0 && lngEnd > 0 && latEnd > 0){  
	                    String lng = str.substring(lngStart+5, lngEnd);  
	                    String lat = str.substring(lngEnd+7, latEnd);
	                    map.put("lng", lng);
	                    map.put("lat", lat);
	                }  
	            } 
	        }catch (Exception e) {  
	            e.printStackTrace();  
	        }finally{  
	            try {  
	                in.close();  
	            } catch (IOException e) {  
	                e.printStackTrace();  
	            }  
	        }
            return map;
	    }
	 
		
}
