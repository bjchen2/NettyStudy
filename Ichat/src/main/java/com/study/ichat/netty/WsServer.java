package com.study.ichat.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
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

    /**
     * 阅读样例，未参与使用，比较成熟的初始化方法
     * @param port 需要监听的端口
     * @param workerNum 从线程组大小
     * @param waterMark 水位，单位：kb
     */
    private void readDemo(int port,int workerNum, int waterMark){
        boolean win = System.getProperty("os.name").startsWith("Windows");
        EventLoopGroup bossGroup = win ? new NioEventLoopGroup() : new EpollEventLoopGroup();
        EventLoopGroup workerGroup = win ? new NioEventLoopGroup(workerNum) : new EpollEventLoopGroup(workerNum);

        // 启动netty监听
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup)
                .channel(win ? NioServerSocketChannel.class : EpollServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                //使用内存池
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                //使用内存池
                .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                //禁用Nagle,使消息立即发出去，不用等待到一定的数据量才进行发送
                .childOption(ChannelOption.TCP_NODELAY, true)
                //保持长连接状态
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                //控制输出水位，当Buffer数据超过上限时停止写入数据，等到buffer低于下限时重新接受写入的数据
                .childOption(ChannelOption.WRITE_BUFFER_WATER_MARK, new WriteBufferWaterMark(waterMark * 1024 / 2, waterMark * 1024))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline p = ch.pipeline();
                        //添加自动解包handler，能根据设置自动截取长度域，然后根据长度域获取包
                        p.addLast(new LengthFieldBasedFrameDecoder(100 * 1024 * 1024, 0, 4, -4, 0));
                    }
                });


        //监听端口启动是否成功
        b.bind(port).addListener((ChannelFutureListener) future -> {
                    if (!future.isSuccess()) {
                        future.channel().pipeline().fireExceptionCaught(future.cause());
                        System.exit(1);
                    }
                });
    }
}
