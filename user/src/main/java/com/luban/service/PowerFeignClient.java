package com.luban.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 */
@FeignClient(name = "SERVER-POWER")
public interface PowerFeignClient {

    @RequestMapping("/getPower.do")
    public Object getPower();
}
