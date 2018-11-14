package com.fh.util;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse; 

public class JspFilter implements Filter{
	 public void destroy() {     // TODO 自动生成方法存根   } 
		 
	 }
	 
	 public void doFilter(ServletRequest request, ServletResponse response,       
			 FilterChain chain) throws IOException, ServletException {     
		 HttpServletRequest hreq = (HttpServletRequest) request;     
		 HttpServletResponse hresp = (HttpServletResponse) response;     
		 String name = hreq.getRequestURL().substring(hreq.getRequestURL().lastIndexOf("/") + 1,          
				 hreq.getRequestURL().lastIndexOf("."));     
		 if (hreq.getRequestURL().indexOf(".jsp") != -1 && (null == hreq.getParameter("type") || hreq.getParameter("type").equals(""))) {
			 hresp.sendRedirect(hreq.getContextPath()+"/conversion?name="+hreq.getRequestURL());         return ;     }
		 chain.doFilter(request, response);   
		 }
	 
	  public void init(FilterConfig arg0) throws ServletException { 
		  // TODO 自动生成方法存根   } 
	  }
}

