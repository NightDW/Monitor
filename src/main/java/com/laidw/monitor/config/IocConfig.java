package com.laidw.monitor.config;

import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.awt.*;

/**
 * 往IOC容器中添加一些组件
 *
 * @author NightDW 2021/8/28 18:19
 */
@Configuration
public class IocConfig {

    /**
     * 创建一个Robot，由它来完成屏幕截取、模拟鼠标点击等功能
     */
    @Bean
    public Robot robot() {
        try {
            return new Robot();
        } catch (AWTException e) {
            throw new RuntimeException("Failed to create robot: " + e.getMessage());
        }
    }

    /**
     * 创建一个ChannelGroup，所有客户端的websocket连接都将存放在这里
     */
    @Bean
    public ChannelGroup channelGroup() {
        return new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    }
}
