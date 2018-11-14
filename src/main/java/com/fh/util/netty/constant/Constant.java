package com.fh.util.netty.constant;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
/**
 * 常量
 * */
public class Constant {
    //存放所有的ChannelHandlerContext
    public static Map<String, ChannelHandlerContext> pushCtxMap = new ConcurrentHashMap<String, ChannelHandlerContext>() ;
    public static Map<String, ChannelGroup> liveCtxMap = new ConcurrentHashMap<String, ChannelGroup>() ;

    //存放某一类的channel
    public static ChannelGroup aaChannelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    //存放某一个直播间
    public static ChannelGroup  liveGroupChannel=null;


}
