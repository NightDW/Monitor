package com.laidw.monitor.screen;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 和截屏相关的属性
 *
 * @author NightDW 2021/8/28 18:32
 */
@Data
@Component
@ConfigurationProperties(prefix = "screen")
public class ScreenProperties {

    /**
     * 截屏发送的频率，即每隔多少毫秒向客户端发送一次截图
     */
    private int refreshMillis = 3000;

    /**
     * 向客户端发送截图时，将截图转成指定类型的格式再发送
     */
    private String imageFormat = "jpeg";

    /**
     * 用户通过浏览器操作服务器后，可以在短时间内提高截屏发送频率（快速模式），以让用户快点看到操作的效果
     * 该变量就表示快速模式下，截屏每隔多少毫秒就会发送一次
     */
    private int fastRefreshMillis = 300;

    /**
     * 快速模式下，截屏发送次数达到该值后，将回到正常模式
     */
    private int fastRefreshTimes = 10;
}
