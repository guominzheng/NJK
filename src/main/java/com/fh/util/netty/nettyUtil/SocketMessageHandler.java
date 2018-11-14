package com.fh.util.netty.nettyUtil;

import com.alibaba.fastjson.JSONObject;
import com.fh.service.wx_classRoom.RecordService;
import com.fh.util.Websocket.WebSocketRunable;
import com.fh.util.netty.constant.Constant;
import com.fh.util.netty.server.BaseWebSocketServerHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.Map;

public class SocketMessageHandler implements Runnable {
    private ChannelHandlerContext ctx;
    private WebSocketFrame frame;
    private WebSocketServerHandshaker handshaker;
    private RecordService recordService;

    public SocketMessageHandler(ChannelHandlerContext ctx, WebSocketFrame frame, WebSocketServerHandshaker handshaker, RecordService recordService) {
        this.ctx = ctx;
        this.frame = frame;
        this.handshaker = handshaker;
        this.recordService = recordService;
    }

    public RecordService getRecordService() {
        return recordService;
    }

    public void setRecordService(RecordService recordService) {
        this.recordService = recordService;
    }

    public ChannelHandlerContext getCtx() {
        return ctx;
    }

    public void setCtx(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    public WebSocketFrame getFrame() {
        return frame;
    }

    public void setFrame(WebSocketFrame frame) {
        this.frame = frame;
    }

    public WebSocketServerHandshaker getHandshaker() {
        return handshaker;
    }

    public void setHandshaker(WebSocketServerHandshaker handshaker) {
        this.handshaker = handshaker;
    }

    public void run() {
        //关闭请求
        try {
            if (frame instanceof CloseWebSocketFrame) {
                handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
                return;
            }
            //ping请求
            if (frame instanceof PingWebSocketFrame) {
                ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
                return;
            }
            //只支持文本格式，不支持二进制消息
            if (!(frame instanceof TextWebSocketFrame)) {
                throw new Exception("仅支持文本格式");
            }
            //客服端发送过来的消息
            Map<String, ChannelGroup> map = Constant.liveCtxMap;
            String request = ((TextWebSocketFrame) frame).text();
            JSONObject jsonObject = null;
            try {
                //判断消息是否为空
                jsonObject = JSONObject.parseObject(request);
                if (null == jsonObject) {
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            //获取心跳
            Boolean heart  = (Boolean) jsonObject.get("heartBeat");
            //获取消息内容
            String message = (String) jsonObject.get("message");
            //获取房间ID
            String liveId = (String) jsonObject.get("liveId");
            //判断房间Id是否为空
            if(liveId==null||"".equals(liveId)){
                return;
            }
            //判断是否为心跳信息
            if(null!= heart && heart){
                new Thread(new NettyRunn(liveId, request, ctx)).start();
                return;
            }
            //判断是否为房间通讯
            if (null != message && !"".equals(message)) {
                //new Thread(new NettyRunn(liveId, request, ctx)).start();
                if (!Constant.liveCtxMap.containsKey(liveId)) {
                    ChannelGroup liveGroupChannels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
                    liveGroupChannels.add(ctx.channel());
                    Constant.liveCtxMap.put(liveId, liveGroupChannels);
                }
                BaseWebSocketServerHandler.push(Constant.liveCtxMap.get(liveId), request);
                new WebSocketRunable(request, this.recordService, null).run();
                return;
            }
            //根据id判断是否登陆或者是否有权限等
            if (liveId != null && !"".equals(liveId)) {
                //用户是否有权限
                new NettyLive(map, liveId, request, ctx).run();
                //根据type 存放进对于的channel池，这里就简单实现，直接放进aaChannelGroup,方便群发
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
