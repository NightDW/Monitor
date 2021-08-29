package com.laidw.monitor.websocket;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 和websocket相关的配置
 *
 * @author NightDW 2021/8/28 18:28
 */
@Data
@Component
@ConfigurationProperties(prefix = "ws")
public class WsProperties {

    /**
     * websocket服务器的ip地址
     */
    private String ip = "localhost";

    /**
     * websocket服务器的端口
     */
    private int port = 8081;

    /**
     * 将http协议升级为websocket协议时的url地址
     */
    private String path = "/ws";
}
