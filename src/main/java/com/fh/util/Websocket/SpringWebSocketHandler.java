package com.fh.util.Websocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fh.entity.dto.MessageDto;
import com.fh.service.wx_classRoom.RecordService;
import com.fh.util.SqlCentre;
import com.fh.util.jdeis.JedisClient;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.json.JsonObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class SpringWebSocketHandler extends TextWebSocketHandler {
    private static final ArrayList<WebSocketSession> users;//这个会出现性能问题，最好用Map来存储，key用userid
    private static Logger logger = Logger.getLogger(SpringWebSocketHandler.class);
    @Autowired
    private RecordService recordService;
    @Autowired
    private JedisClient jedisClient;
    static {
        users = new ArrayList<WebSocketSession>();
    }

    public static ArrayList<WebSocketSession> getUsers() {
        return users;
    }

    public SpringWebSocketHandler() {
    }

    /**
     * 连接成功时候，会触发页面上onopen方法
     */
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // TODO Auto-generated method stub
        System.out.println("connect to the websocket success......当前数量:" + users.size());
        users.add(session);
        //这块会实现自己业务，比如，当用户登录后，会把离线消息推送给用户
        System.out.println("在线人数加1-----------------》" + users.size());
        String message = "{\"state\":\"1\",\"num\":\"" + users.size() + "\"}";
        TextMessage returnMessage = new TextMessage(message);
        session.sendMessage(returnMessage);
    }

    /**
     * 关闭连接时触发
     */
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        logger.debug("websocket connection closed......");
        String username = (String) session.getAttributes().get("WEBSOCKET_USERNAME");
        System.out.println("在线人数加1-----------------》" + users.size());
        users.remove(session);
        String message = "{\"state\":\"1\",\"num\":\"" + users.size() + "\"}";
        TextMessage returnMessage = new TextMessage(message);
        session.sendMessage(returnMessage);
    }

    /**
     * js调用websocket.send时候，会调用该方法
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        try {
            System.out.println("==============================>进入了socket send");
            new Thread(new WebSocketRunable(message.getPayload(),recordService,jedisClient)).start();
            //super.handleTextMessage(session, message);
            sendMessageToUser(session.getAttributes().get("liveId").toString(), message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if (session.isOpen()) {
            session.close();
        }
        logger.debug("websocket connection closed......");
        users.remove(session);
        String message = "{\"state\":\"1\",\"num\":\"" + users.size() + "\"}";
        TextMessage returnMessage = new TextMessage(message);
        session.sendMessage(returnMessage);
    }

    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * 给某个用户发送消息
     *
     * @param liveId
     * @param message
     */
    public static void sendMessageToUser(String liveId, TextMessage message) {
        for (WebSocketSession user : SpringWebSocketHandler.getUsers()) {
            if (user.getAttributes().get("liveId").equals(liveId)) {
                try {
                    user.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 给所有在线用户发送消息
     *
     * @param message
     */
    public static void sendMessageToUsers(TextMessage message) {
        for (WebSocketSession user : users) {
            try {
                if (user.isOpen()) {
                    user.sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
