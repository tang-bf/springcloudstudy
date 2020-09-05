package com;

import config.OrderRuleConfig;
import config.PowerRuleConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 *
 */
@SpringBootApplication
@EnableEurekaClient
@RibbonClients({
        @RibbonClient(name = "SERVER-ORDER",configuration = OrderRuleConfig.class),
        @RibbonClient(name = "SERVER-POWER",configuration = PowerRuleConfig.class)
})
@EnableFeignClients//可加(defaultConfiguration = app配置类) 也可以进行配置隔离，也有子容器概念
//改变了ioc 行为 进行了动态代理
// feign 首先把接口代理出来 加上发送请求的额逻辑 把代理后的类 再注册到容器中
//@EnableFeignClients @Import(FeignClientsRegistrar.class)
//FeignClientsRegistrar  ImportBeanDefinitionRegistrar,
//		ResourceLoaderAware, EnvironmentAware
// ImportBeanDefinitionRegistrar
public class AppUserClient {

    public static void main(String[] args) {
        SpringApplication.run(AppUserClient.class);
    }
}
