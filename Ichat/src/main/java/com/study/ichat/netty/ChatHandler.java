package com.study.ichat.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.time.LocalDateTime;

/**
 * 处理消息助手类，处理WebSocket的文本消息请求
 * TextWebSocketFrame：专门为WebSocket传输文本的对象
 * @author Cx
 * @version jdk8 and idea On 2019/6/2 19:04
 */
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    /**
     * 记录和管理所有客户端channel
     */
    private static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        //获取客户端传输过来的消息
        String content = msg.text();
        System.out.println("【new message】" + content);

        //转发给所有channel
        clients.writeAndFlush(new TextWebSocketFrame(LocalDateTime.now()+ ": " + content));
    }

    /**
     * 当客户端连接服务端时，存储客户端channel
     * @param ctx 上下文
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        clients.add(ctx.channel());
    }

    /**
     * 移除客户端channel
     * @param ctx 上下文
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        // 当handler Removed时，ChannelGroup会自动移除对应客户端channel，所以不用手动移除:clients.remove(ctx.channel())
        System.out.println("客户端断开，channel对应长id为：" + ctx.channel().id().asLongText());
        System.out.println("客户端断开，channel对应短id为：" + ctx.channel().id().asShortText());
    }
}
