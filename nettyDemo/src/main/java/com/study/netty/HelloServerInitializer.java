package com.study.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * 初始化器，channel注册后，会执行里面相应的初始化方法
 * @author Cx
 * @version jdk8 and idea On 2019/5/31 12:45
 */
public class HelloServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        //通过channel获取对应管道
        ChannelPipeline pipeline = socketChannel.pipeline();

        //通过管道添加handler，当请求到服务端时进行解码，响应到客户端做编码
        pipeline.addLast("HttpServerCodec",new HttpServerCodec());

        //添加自定义助手类，返回"hello netty"
        pipeline.addLast("customHandler",new CustomHandler());
    }
}
