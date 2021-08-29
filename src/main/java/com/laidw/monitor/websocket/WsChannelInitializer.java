package com.laidw.monitor.websocket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 初始化SocketChannel
 *
 * @author NightDW 2021/8/28 18:30
 */
@Component
public class WsChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Autowired
    private WsProperties wsProperties;

    @Autowired
    private WsHandler wsHandler;

    protected void initChannel(SocketChannel ch) {
        ch.pipeline().addLast("http-codec", new HttpServerCodec());
        ch.pipeline().addLast("http-chunked", new ChunkedWriteHandler());
        ch.pipeline().addLast("aggregator", new HttpObjectAggregator(65536));
        ch.pipeline().addLast("ws-protocol-handler", new WebSocketServerProtocolHandler(wsProperties.getPath()));
        ch.pipeline().addLast("ws-handler", wsHandler);
    }
}
