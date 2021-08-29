package com.laidw.monitor.controller;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 和首页相关的一些配置
 */
@Data
@Component
@ConfigurationProperties(prefix = "index")
public class IndexProperties {

    /**
     * 用于判断浏览器所在设备是手机还是电脑；浏览器宽度低于该值时为手机，否则为电脑
     */
    private int widthSplitLine = 1300;

    /**
     * 如果是手机，则相框的宽度应该等于多少倍的浏览器宽度
     */
    private double phoneWidthRatio = 1.0;

    /**
     * 如果是电脑，则相框的宽度应该等于多少倍的浏览器宽度
     */
    private double computerWidthRatio = 0.85;

    /**
     * 相框的宽高比（宽）
     */
    private int widthHeightRatioW = 16;

    /**
     * 相框的宽高比（高）
     */
    private int widthHeightRatioH = 9;
}
