package com.fh.util.netty.nettyUtil;

import com.fh.util.netty.constant.Constant;
import com.fh.util.netty.server.BaseWebSocketServerHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class NettyLive implements Runnable {
    private Map<String, ChannelGroup> map;
    private String liveId;
    private String request;
    private ChannelHandlerContext ctx;

    public Map<String, ChannelGroup> getMap() {
        return map;
    }

    public void setMap(Map<String, ChannelGroup> map) {
        this.map = map;
    }

    public String getLiveId() {
        return liveId;
    }

    public void setLiveId(String liveId) {
        this.liveId = liveId;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public ChannelHandlerContext getCtx() {
        return ctx;
    }

    public void setCtx(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    public NettyLive(Map<String, ChannelGroup> map, String liveId, String request, ChannelHandlerContext ctx) {
        this.map = map;
        this.liveId = liveId;
        this.request = request;
        this.ctx = ctx;
    }

    private ReentrantLock lock = new ReentrantLock();

    public void run() {
        try {
            lock.lock();
            boolean liveIdAccess = true;
            if (liveIdAccess) {
                if (!map.isEmpty()) {
                    if (map.containsKey(liveId)) {
                        ChannelGroup liveGroupChannel = map.get(liveId);
                        liveGroupChannel.add(ctx.channel());
                    } else {
                        ChannelGroup liveGroupChannel = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
                        liveGroupChannel.add(ctx.channel());
                        Constant.liveCtxMap.put(liveId, liveGroupChannel);
                    }
                } else {
                    ChannelGroup liveGroupChannel = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
                    liveGroupChannel.add(ctx.channel());
                    Constant.liveCtxMap.put(liveId, liveGroupChannel);
                }
                BaseWebSocketServerHandler.push(map.get(liveId), "{\"state\":\"1\",\"num\":\"" + map.get(liveId).size() + "\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

}
