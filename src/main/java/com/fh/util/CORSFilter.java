package com.fh.util;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
 
public class CORSFilter implements Filter {
 
    public void init(FilterConfig filterConfig) throws ServletException {
 
    }
 
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    	  if(servletRequest!=null&&servletResponse!=null){
    		  HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
    		  httpResponse.addHeader("Access-Control-Allow-Origin", "*");
    		  filterChain.doFilter(servletRequest, servletResponse);
    	  }
    }	
 
    public void destroy() {
 
    }
}
