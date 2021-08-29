package com.laidw.monitor.screen;

import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 定时向客户端发送服务器屏幕截图的线程
 *
 * @author NightDW 2021/8/28 18:27
 */
@Component
public class ScreenSpider extends Thread {

    /**
     * 用于截取服务器屏幕
     */
    @Autowired
    private ScreenRobot robot;

    /**
     * 当前连接到服务器的所有客户端的通道
     */
    @Autowired
    private ChannelGroup channels;

    /**
     * 和屏幕截图相关的一些属性
     */
    @Autowired
    private ScreenProperties screenProperties;

    /**
     * 用于读取屏幕截图的数据
     */
    private ByteArrayOutputStream out = new ByteArrayOutputStream();

    /**
     * 快速模式的剩余次数；大于0则说明正处于快速模式，否则是正常模式
     */
    private int remainFastRefreshTimes = 0;

    /**
     * 是否停止线程
     */
    private boolean stop = false;

    /**
     * 向所有客户端发送服务器的屏幕截图，然后休眠一段时间；如此循环下去
     */
    @Override
    public void run() {
        while(!stop) {
            sendCapture();

            // 如果截屏期间stop标志已经变为了true，则直接跳出循环
            if(stop) break;

            // 休眠时间，默认是正常模式，即休眠时间为screenProperties.getRefreshMillis()
            int sleepTime = screenProperties.getRefreshMillis();

            // 如果是快速模式，则快速模式计数减一，然后将休眠时间改为screenProperties.getFastRefreshMillis()
            if(remainFastRefreshTimes > 0){
                remainFastRefreshTimes--;
                sleepTime = screenProperties.getFastRefreshMillis();
            }

            // 休眠一段时间；注意该线程可能会经常被打断，因此catch块留空即可，无需e.printStackTrace()
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                // e.printStackTrace();
            }
        }
    }

    /**
     * 暂时切换到快速模式；快速模式次数减小为0后将恢复到正常模式
     */
    public void switchToFastModeTemporarily(){
        remainFastRefreshTimes = screenProperties.getFastRefreshTimes();
        interrupt(); // 线程可能正在休眠，因此需要立即唤醒该线程，让该线程立即发送截图
    }

    /**
     * 向所有客户端发送服务器屏幕截图
     */
    private void sendCapture(){

        // 没有连接通道，则直接返回
        if(channels.isEmpty()) return;

        try {
            ImageIO.write(robot.capture(), screenProperties.getImageFormat(), out);
            channels.writeAndFlush(new TextWebSocketFrame(Base64Utils.encodeToString(out.toByteArray())));
            out.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 对象创建后自动启动线程
     */
    @PostConstruct
    public void threadStart() {
        start();
    }

    /**
     * 对象销毁前先停止线程
     */
    @PreDestroy
    public void threadStop() {
        stop = true;
        interrupt();
    }
}
