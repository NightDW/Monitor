package com.laidw.monitor.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * websocket服务器
 *
 * @author NightDW 2021/8/28 18:29
 */
@Component
public class WsServer {

    @Autowired
    private WsProperties wsProperties;

    @Autowired
    private WsChannelInitializer wsChannelInitializer;

    private ServerBootstrap serverBootstrap;
    private NioEventLoopGroup boss, work;
    public WsServer() {
        serverBootstrap = new ServerBootstrap();
        boss = new NioEventLoopGroup();
        work = new NioEventLoopGroup();
        serverBootstrap.group(this.boss, this.work);
    }

    /**
     * 对象构造完成后，启用一个线程来启动websocket服务器
     */
    @PostConstruct
    public void startWithNewThread() {
        serverBootstrap.channel(NioServerSocketChannel.class);
        serverBootstrap.childHandler(wsChannelInitializer);
        new Thread(() -> {
            try {
                serverBootstrap.bind(wsProperties.getPort()).sync().channel().closeFuture().sync();
            } catch (InterruptedException var5) {
                throw new RuntimeException(var5);
            } finally {
                close();
            }

        }).start();
    }

    /**
     * 对象销毁前关闭线程池
     */
    @PreDestroy
    public void close() {
        boss.shutdownGracefully();
        work.shutdownGracefully();
    }
}
