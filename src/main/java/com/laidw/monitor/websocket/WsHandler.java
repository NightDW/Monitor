package com.laidw.monitor.websocket;

import com.laidw.monitor.screen.ScreenRobot;
import com.laidw.monitor.screen.ScreenSpider;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * websocket处理器
 *
 * @author NightDW 2021/8/28 18:29
 */
@Component
@ChannelHandler.Sharable
public class WsHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Autowired
    private ChannelGroup channels;

    @Autowired
    private ScreenRobot robot;

    @Autowired
    private ScreenSpider spider;

    /**
     * 当有一个客户端连接服务器时，将与该客户端的通道保存起来，并让spider线程暂时进入快速模式
     */
    public void handlerAdded(ChannelHandlerContext ctx) {
        channels.add(ctx.channel());
        spider.switchToFastModeTemporarily();
    }

    /**
     * 客户端断开连接时，将与该客户端的通道移除
     */
    public void handlerRemoved(ChannelHandlerContext ctx) {
        channels.remove(ctx.channel());
    }

    /**
     * 客户端发送指令过来时，执行该指令；指令格式为"cmdType:cmdContent"
     */
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
        String[] split = msg.text().split(":");

        // 目前只支持鼠标点击的指令
        if ("click".equals(split[0])) {
            this.doClickCmd(split[1]);
        } else {
            System.err.println("Unsupported command: " + split[1]);
        }

        // 让spider线程暂时进入快速模式
        spider.switchToFastModeTemporarily();
    }

    /**
     * 执行鼠标点击的指令
     */
    private void doClickCmd(String cmd) {
        String[] split = cmd.split(",");
        robot.mouseMoveAndLeftClick(Double.valueOf(split[0]), Double.valueOf(split[1]));
    }
}
