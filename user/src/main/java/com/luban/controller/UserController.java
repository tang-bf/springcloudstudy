package com.luban.controller;

import com.luban.service.PowerFeignClient;
import com.luban.util.R;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
@RestController
public class UserController {

    @Autowired
    RestTemplate restTemplate;

    private static final String  POWER_URL="http://SERVER-POWER";
    private static final String  ORDER_URL="http://SERVER-ORDER";

    @Autowired//改变了ioc 行为 进行了动态代理
    // feign 首先把接口代理出来 加上发送请求的额逻辑 把代理后的类 再注册到容器中
    //@EnableFeignClients @Import(FeignClientsRegistrar.class)
    //FeignClientsRegistrar  ImportBeanDefinitionRegistrar,
    //		ResourceLoaderAware, EnvironmentAware
    // ImportBeanDefinitionRegistrar
    private  PowerFeignClient powerFeignClient;


    @RequestMapping("/getUser.do")
    public R getUser(){
        Map<String,Object> map = new HashMap<>();
        map.put("key","user");

        return R.success("返回成功",map);
    }
public Object testfallback(){
        return  "降级";
}
    @HystrixCommand(fallbackMethod = "testfallback")//服务降级  fallbackMethod 方法参数，返回值必须和原方法一样
    // 10s中调用20次出错会进行熔断 不会再发restTemplate.getForOb请求，直接执行testfallback
    @RequestMapping("/getOrder.do")
    public R getOrder(){
        return R.success("操作成功",restTemplate.getForObject(ORDER_URL+"/getOrder.do",Object.class));
    }


    @RequestMapping("/getFeignPower.do")
    public R getFeignPower(){

        return R.success("操作成功",powerFeignClient.getPower());
    }

    @RequestMapping("/getPower.do")
    public R getPower(){
        return R.success("操作成功",restTemplate.getForObject(POWER_URL+"/getPower.do",Object.class));
    }

}
