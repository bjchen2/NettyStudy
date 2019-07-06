package com.study.ichat.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.stereotype.Component;

/**
 * webSocketServer
 * @author Cx
 * @version jdk8 and idea On 2019/6/2 18:34
 */
@Component
public class WsServer {
    private static final WsServer INSTANCE = new WsServer();
    private final EventLoopGroup parentGroup,childGroup;
    private final ServerBootstrap bootstrap;
    private ChannelFuture startFuture;

    public WsServer() {
        this.parentGroup = new NioEventLoopGroup();
        this.childGroup = new NioEventLoopGroup();
        this.bootstrap = new ServerBootstrap().group(parentGroup,childGroup).channel(NioServerSocketChannel.class)
                .childHandler(new WsServerInitializer());
    }

    /**
     * 启动webSocket
     * @param port 启动端口
     */
    public void start(int port){
        this.startFuture = bootstrap.bind(port);
        System.out.println("==================netty websocket server 启动成功======================");
    }

    public static WsServer getInstance(){
        return INSTANCE;
    }
}
