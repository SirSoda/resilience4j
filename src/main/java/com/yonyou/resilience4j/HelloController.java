package com.yonyou.resilience4j;

import com.yonyou.commons.feign.HelloService;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.annotation.Retry;
import io.vavr.control.Try;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
@Retry(name = "retryBackendA")
public class HelloController{

    @Autowired
    private FeignHelloService helloService;

    @GetMapping("/hello")
    public String hello(String s) {
        return helloService.hello(s);
    }

}
