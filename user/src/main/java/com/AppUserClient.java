package com;

import config.OrderRuleConfig;
import config.PowerRuleConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;

/**
 *
 */
@SpringBootApplication
@EnableEurekaClient
@RibbonClients({
        @RibbonClient(name = "SERVER-ORDER",configuration = OrderRuleConfig.class),
        @RibbonClient(name = "SERVER-POWER",configuration = PowerRuleConfig.class)
})
@EnableFeignClients
//(clients = 这里加的话 PowerFeignClient 后面就不会扫描包
//Map<String, Object> attrs = metadata
//        .getAnnotationAttributes(EnableFeignClients.class.getName());
//        AnnotationTypeFilter annotationTypeFilter = new AnnotationTypeFilter(
//        FeignClient.class);
//final Class<?>[] clients = attrs == null ? null
//        : (Class<?>[]) attrs.get("clients");
//        if (clients == null || clients.length == 0) {
//        scanner.addIncludeFilter(annotationTypeFilter);
//        basePackages = getBasePackages(metadata);
//        }
// )//可加(defaultConfiguration = app配置类) 也可以进行配置隔离，也有子容器概念,单没有父容器的概念
//改变了ioc 行为 进行了动态代理
// feign 首先把接口代理出来 加上发送请求的额逻辑 把代理后的类 再注册到容器中
//@EnableFeignClients @Import(FeignClientsRegistrar.class)
//FeignClientsRegistrar  ImportBeanDefinitionRegistrar,
//		ResourceLoaderAware, EnvironmentAware
// ImportBeanDefinitionRegistrar
//private void registerFeignClient(BeanDefinitionRegistry registry,
//        AnnotationMetadata annotationMetadata, Map<String, Object> attributes) {
//        String className = annotationMetadata.getClassName();
//        BeanDefinitionBuilder definition = BeanDefinitionBuilder
//        .genericBeanDefinition(FeignClientFactoryBean.class); 这段代码很重要
// FeignClientFactoryBean implements FactoryBean<Object>, InitializingBean,
//        ApplicationContextAware {
//通过getobject 返回的
//return (T) targeter.target(this, builder, context, new HardCodedTarget<>(
//        this.type, this.name, url));一路走下去可以看到最终调用jdk动态代理返回的对象 FeignInvocationHandler
//如果配置了url，就不会结合ribbon走负载均衡
////@FeignClient(name = "SERVER-POWER")//,configuration =  ,url =  可以传微服务名字也可以传url,传微服务名字会结合ribbon负载均衡调用
// 传url不会结合ribbon，而是直接构建请求发送)
//处理默认方法的逻辑   defaultMethodHandler.bindTo(proxy);
//definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
//请求的时候执行到FeignInvocationHandler invoke方法 SynchronousMethodHandler  会有重试机制
//  RequestTemplate template = buildTemplateFromArgs.create(argv);
//    Retryer retryer = this.retryer.clone();
//    while (true) {
//      try {
//        return executeAndDecode(template);
//      } catch (RetryableException e) {
//        retryer.continueOrPropagate(e);
//        if (logLevel != Logger.Level.NONE) {
//          logger.logRetry(metadata.configKey(), logLevel);
//        }
//        continue;
//      }
//
//执行到 AbstractLoadBalancerAwareClient executeWithLoadBalancer  用到了Observable 观察者模式的发布定阅
//解析微服务名对应的ip地址
public class AppUserClient {

    public static void main(String[] args) {
        SpringApplication.run(AppUserClient.class);
    }
}
