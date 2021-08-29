package com.laidw.monitor.controller;

import com.laidw.monitor.websocket.WsProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 用于处理一些基本的http请求
 *
 * @author NightDW 2021/8/28 18:27
 */
@Controller
public class BaseController {

    @Autowired
    private WsProperties wsProperties;

    @Autowired
    private IndexProperties indexProperties;

    /**
     * 将首页返回给客户端；程序与用户的交互操作将在首页中进行
     */
    @GetMapping({"/", "/index"})
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("index");

        // 手动拼接websocket服务器的请求地址，并提供index页面所需的参数；这样，当需要更改参数时，只需要修改配置文件，而不需要修改html页面代码
        mav.addObject("ws_url", "ws://" + wsProperties.getIp() + ":" + wsProperties.getPort() + wsProperties.getPath());
        mav.addObject("width_split_line", indexProperties.getWidthSplitLine());
        mav.addObject("phone_width_ratio", indexProperties.getPhoneWidthRatio());
        mav.addObject("computer_width_ratio", indexProperties.getComputerWidthRatio());
        mav.addObject("width_height_ratio_w", indexProperties.getWidthHeightRatioW());
        mav.addObject("width_height_ratio_h", indexProperties.getWidthHeightRatioH());

        return mav;
    }
}
