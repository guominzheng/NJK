package com.fh.util.netty.server;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

import com.fh.service.wx_classRoom.RecordService;
import com.fh.util.Websocket.WebSocketRunable;
import com.fh.util.jdeis.JedisClient;
import com.fh.util.netty.constant.Constant;
import com.fh.util.netty.nettyUtil.NettyLive;
import com.fh.util.netty.nettyUtil.NettyRunn;
import com.fh.util.netty.nettyUtil.SocketMessageHandler;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import io.netty.util.CharsetUtil;
import org.springframework.stereotype.Controller;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;

/**
 * websocket 具体业务处理方法
 */

@Component
@Sharable
public class WebSocketServerHandler extends BaseWebSocketServerHandler {

    private WebSocketServerHandshaker handshaker;

    @Autowired
    private RecordService recordService;
    @Autowired
    private JedisClient jedisClient;


    /**
     * 当客户端连接成功，返回个成功信息
     */
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Constant.aaChannelGroup.add(ctx.channel());
    }

    /**
     * 当客户端断开连接
     */
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ctx.close();
    }

    /**
     *
     * @param ctx
     * @throws Exception
     */
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    /**
     * netty read
     * @param ctx
     * @param msg
     */
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof FullHttpRequest) {
            handleHttpRequest(ctx, (FullHttpRequest) msg);
        } else if (msg instanceof WebSocketFrame) {
            handlerWebSocketFrame(ctx, (WebSocketFrame) msg);
        }
    }

    /**
     * 消息处理方法
     *
     * @param ctx   管道
     * @param frame 消息
     */
    private void handlerWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {
        new SocketMessageHandler(ctx, frame, handshaker, this.recordService).run();
    }

    /**
     * 第一次请求的处理
     *
     * http请求处理， 包括ws以及wss
     * @param ctx
     * @param req
     */
    public void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) {
        if (!req.decoderResult().isSuccess()) {
            sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
            return;
        }
        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory("wss:/" + ctx.channel() + "/websocket", null, false);
        handshaker = wsFactory.newHandshaker(req);
        if (handshaker == null) {
            //不支持
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
        } else {
            handshaker.handshake(ctx.channel(), req);
        }
    }


    public static void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req, DefaultFullHttpResponse res) {
        // 返回应答给客户端
        if (res.status().code() != 200) {
            ByteBuf buf = Unpooled.copiedBuffer(res.status().toString(), CharsetUtil.UTF_8);
            res.content().writeBytes(buf);
            buf.release();
        }

        // 如果是非Keep-Alive，关闭连接
        ChannelFuture f = ctx.channel().writeAndFlush(res);
        if (!isKeepAlive(req) || res.status().code() != 200) {
            f.addListener(ChannelFutureListener.CLOSE);
        }

    }

    private static boolean isKeepAlive(FullHttpRequest req) {
        return false;
    }


    //异常处理，netty默认是关闭channel
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        //输出日志
        cause.printStackTrace();
        ctx.close();



        /* Map<String, ChannelGroup> map = Constant.liveCtxMap;
        System.out.println("=============================>" + map.size());
        for (String key : map.keySet()) {
            ChannelGroup channel = map.get(key);
            boolean a = channel.remove(ctx.channel());
            if (a) {
                System.out.println("当前课堂用户数量================>" + Constant.liveCtxMap.get(key).size());
                push(Constant.liveCtxMap.get(key), "{\"state\":\"1\",\"num\":\"" + Constant.liveCtxMap.get(key).size() + "\"}");
            }
        }*/
    }

}
