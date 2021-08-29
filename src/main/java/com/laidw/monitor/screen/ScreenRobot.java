package com.laidw.monitor.screen;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;

/**
 * 可以完成截取屏幕、模拟鼠标点击等功能的机器人
 *
 * @author NightDW 2021/8/28 18:19
 */
@Component
public class ScreenRobot {

    /**
     * 底层功能由Robot来完成
     */
    @Autowired
    private Robot robot;

    /**
     * 按下按键后，间隔多长时间再松开按键
     */
    private static final int KEY_DELAY = 10;

    /**
     * 用于指定屏幕的操作范围，指定后无法更改
     */
    private final Rectangle rectangle;

    /**
     * 默认操作整个屏幕
     */
    public ScreenRobot() {
        this(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
    }

    /**
     * 方法私有，也就是说暂不支持自定义屏幕范围
     */
    private ScreenRobot(Rectangle rectangle) {
        if (Math.abs(rectangle.getHeight() * 16 - rectangle.getWidth() * 9) >= 8) {
            System.err.println("Width and height ratio is not 16:9, so it may cause some problems!");
        }
        this.rectangle = rectangle;
    }

    /**
     * 截取当前屏幕
     */
    @SuppressWarnings("WeakerAccess")
    public BufferedImage capture(){
        return robot.createScreenCapture(rectangle);
    }

    /**
     * 鼠标左击屏幕的指定位置
     */
    public void mouseMoveAndLeftClick(double xPercent, double yPercent) {
        mouseMove(xPercent, yPercent);
        mouseLeftClick();
    }

    /**
     * 鼠标移动到指定位置
     */
    private void mouseMove(double xPercent, double yPercent) {
        int x = (int)(xPercent * rectangle.getWidth() + rectangle.getX());
        int y = (int)(yPercent * rectangle.getHeight() + rectangle.getY());
        robot.mouseMove(x, y);
    }

    /**
     * 鼠标左击当前位置
     */
    private void mouseLeftClick() {
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.delay(KEY_DELAY);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
    }
}
