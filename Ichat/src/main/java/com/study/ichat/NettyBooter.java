package com.study.ichat;

import com.study.ichat.netty.WsServer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * 当项目启动成功后，自动启动Netty服务，初始化WebSocket
 * ContextRefreshedEvent ： ApplicationContext刷新事件（监听该事件一般用于项目初始化成功后进行相应逻辑处理）
 * @author Cx
 * @version jdk8 and idea On 2019/6/5 18:35
 */
@Component
public class NettyBooter implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        //当前容器的父容器为空时（即当前容器为root容器，因为该事件可能被其他容器初始化时触发），初始化WebSocket
        if (contextRefreshedEvent.getApplicationContext().getParent() == null){
            WsServer.getInstance().start(8088);
        }
    }
}
