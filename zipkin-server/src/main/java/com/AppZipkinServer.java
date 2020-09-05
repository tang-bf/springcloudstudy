package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import zipkin.server.internal.EnableZipkinServer;

/**
 * 需要咨询java高级课程的同学可以加安其拉老师的QQ：3164703201
 * author：鲁班学院-商鞅老师
 */
@SpringBootApplication
@EnableZipkinServer
public class AppZipkinServer {

    public static void main(String[] args) {
        SpringApplication.run(AppZipkinServer.class);
    }
}
