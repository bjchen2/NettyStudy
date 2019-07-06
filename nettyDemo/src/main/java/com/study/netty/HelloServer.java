package com.study.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 实现客户端发送请求，访问localhost:8088/*时（因为没有做任何拦截，所以只要访问8088即会被响应），服务器返回hello netty
 * @author Cx
 * @version jdk8 and idea On 2019/5/31 11:59
 */
public class HelloServer {
    public static void main(String[] args) throws InterruptedException {
        //定义一对线程组
        //主线程组：用于接收客户端的连接，但不做任何处理；从线程组：获取主线程组提供的任务，并执行
        EventLoopGroup bossGroup = new NioEventLoopGroup(), workerGroup = new NioEventLoopGroup();

        try {
            //netty服务器启动类
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            //设置主从线程组、nio的双向通道、子处理器：用于处理workerGroup
            serverBootstrap.group(bossGroup,workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new HelloServerInitializer());

            //启动server，绑定到8088端口，启动方式为同步（即会阻塞直到启动完毕）
            ChannelFuture channelFuture = serverBootstrap.bind(8088).sync();

            //获取channel关闭监听器，设置为同步的方式（即只有当channel关闭时，该语句才解除阻塞）
            channelFuture.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
