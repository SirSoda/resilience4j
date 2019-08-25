package com.yonyou.resilience4j;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * aop方式实现熔断 但是无法降级
 * mxf
 * 2019年08月24日21:46:55
 */
@RestController
@CircuitBreaker(name = "backendA")
public class BreakerByAop {

    @Autowired
    private FeignHelloService helloService;

    @GetMapping("/hello3")
    public String hello(String name) {
        return helloService.hello(name);
    }
}
