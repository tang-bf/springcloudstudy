package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * 需要咨询java高级课程的同学可以加安其拉老师的QQ：3164703201
 * author：鲁班学院-商鞅老师
 */

@EnableHystrixDashboard
@SpringBootApplication
public class AppHystrixDashboard {

    public static void main(String[] args) {
        SpringApplication.run(AppHystrixDashboard.class);
    }
}
/** 基于rx java
 * 线程隔离、信号量隔离、降级策略、熔断技术。
 */
