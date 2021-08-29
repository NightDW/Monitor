package com.laidw.monitor;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class MonitorApplication {

    public static void main(String[] args) {

        // 这里需要手动将headless设置为false，否则创建Robot时会报错
        new SpringApplicationBuilder(MonitorApplication.class).headless(false).run(args);
    }

}
