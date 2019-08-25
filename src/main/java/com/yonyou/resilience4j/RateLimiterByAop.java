package com.yonyou.resilience4j;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * aop方式实现限流
 * mxf
 * 2019年08月24日22:09:39
 */
@RestController
@RateLimiter(name = "backendA")
public class RateLimiterByAop {

    @Autowired
    private FeignHelloService helloService;

    @GetMapping("/hello5")
    public String hello(String name) {
        return helloService.hello(name);
    }
}
