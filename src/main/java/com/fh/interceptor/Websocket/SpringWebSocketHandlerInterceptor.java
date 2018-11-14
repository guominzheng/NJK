package com.fh.interceptor.Websocket;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * WebSocket拦截器
 * @author WANG
 *
 */
public class SpringWebSocketHandlerInterceptor extends HttpSessionHandshakeInterceptor {

    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception {
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            HttpSession session = servletRequest.getServletRequest().getSession(false);
            CopyOnWriteArraySet<Object> webSocketSet = null;
                //使用userName区分WebSocketHandler，以便定向发送消息
                //String userName = (String) session.getAttribute("SESSION_USERNAME");
                HttpServletRequest servletRequests = ((ServletServerHttpRequest) request).getServletRequest();
                String id = servletRequests.getSession().getId();
                System.out.println("beforeHandshake: \n"+id);
                String liveId =servletRequests.getParameter("liveId");
                if (liveId==null) {
                    return false;
                }
                attributes.put("liveId",liveId);
        }
        return true;

    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                               Exception ex) {
        super.afterHandshake(request, response, wsHandler, ex);
    }
}
