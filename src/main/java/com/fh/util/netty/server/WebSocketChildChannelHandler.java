package com.fh.util.netty.server;

import javax.annotation.Resource;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;

import com.fh.util.netty.nettyUtil.SslUtil;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.springframework.stereotype.Component;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.util.concurrent.TimeUnit;

@Component
public class WebSocketChildChannelHandler extends ChannelInitializer<SocketChannel> {

    @Resource(name = "webSocketServerHandler")
    private ChannelHandler webSocketServerHandler;

    //private ChannelHandler webSocketServerHandler = new WebSocketServerHandler();
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        /*SSLContext sslContext = SslUtil.createSSLContext("JKS", "C://Users/xshel/hitman.net.cn.jks", "3ti5nj6ngsq");
        //SSLEngine 此类允许使用ssl安全套接层协议进行安全通信
        SSLEngine engine = sslContext.createSSLEngine();
        engine.setUseClientMode(false);*/
        // TODO Auto-generated method stub
        //ch.pipeline().addLast(new SslHandler(engine));
        ch.pipeline().addLast("http-codec", new HttpServerCodec());
        ch.pipeline().addLast("aggregator", new HttpObjectAggregator(65536));
        ch.pipeline().addLast("http-chunked", new ChunkedWriteHandler());
        ch.pipeline().addLast("handler", webSocketServerHandler);
        ch.pipeline().addLast("ping", new IdleStateHandler(30, 30, 60, TimeUnit.SECONDS));
    }

}
