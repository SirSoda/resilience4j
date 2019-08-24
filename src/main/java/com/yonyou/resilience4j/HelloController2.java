package com.yonyou.resilience4j;

import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.vavr.control.Try;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
public class HelloController2 {

    @Autowired
    private FeignHelloService helloService;

    @GetMapping("/hello2")
    public String hello2(String name) {
        RetryConfig config = RetryConfig.custom()
                .maxAttempts(3)
                .waitDuration(Duration.ofMillis(5000)) // 下次重试等待时间
                .build();
        Retry retry = Retry.of("retryA", config);
        Try<String> result = Try.ofSupplier(Retry.decorateSupplier(retry, () -> helloService.hello(name)));
        return result.get();
    }
}
