package com.fh.util.netty.nettyUtil;

import com.fh.util.netty.constant.Constant;
import com.fh.util.netty.server.BaseWebSocketServerHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.concurrent.locks.ReentrantLock;

public class NettyRunn implements Runnable {
    private String liveId;
    private String request;
    private ChannelHandlerContext ctx;

    public NettyRunn(String liveId, String request, ChannelHandlerContext ctx) {
        this.liveId = liveId;
        this.request = request;
        this.ctx = ctx;
    }

    private static ReentrantLock lock = new ReentrantLock();

    public void run() {
        try {
            lock.lock();
            if (!Constant.liveCtxMap.containsKey(liveId)) {
                ChannelGroup liveGroupChannels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
                liveGroupChannels.add(ctx.channel());
                Constant.liveCtxMap.put(liveId, liveGroupChannels);
            }
            BaseWebSocketServerHandler.push(Constant.liveCtxMap.get(liveId), request);
        } finally {
            lock.unlock();
        }

    }
}
