package com.luban;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 *
 */
@SpringBootApplication
@EnableEurekaServer
public class AppEureka3001 {

    public static void main(String[] args) {
        SpringApplication.run(AppEureka3001.class);
    }
}
