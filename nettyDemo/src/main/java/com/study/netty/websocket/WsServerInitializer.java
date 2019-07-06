package com.study.netty.websocket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * 初始化器，channel注册后，会执行里面相应的初始化方法
 * @author Cx
 * @version jdk8 and idea On 2019/6/2 18:44
 */
public class WsServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        //========================为http协议handler=============================
        pipeline.addLast(new HttpServerCodec());
        //对写大数据流支持
        pipeline.addLast(new ChunkedWriteHandler());
        //对httpMessage进行聚合，聚合为FullHttpRequest或Response，消息内容最大长度为1024*64
        //几乎所有netty编程都会用到该handler
        pipeline.addLast(new HttpObjectAggregator(1024*64));

        //========================webSocket协议handler=============================
        //webSocket协议服务器处理类，参数用于指定客户端连接时访问路由：ws://ip:端口/参数（如下述例子参数即为ws）
        //该handler会处理绝大多数webSocket基本操作，如握手（close、ping、pong），ping + pong = 心跳，以Frame进行传输
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));

        //========================end=============================
        //自定义助手类，读取用户请求消息并进行响应
        pipeline.addLast(new ChatHandler());

    }
}
