package com.yonyou.resilience4j;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import io.vavr.control.Try;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.function.Supplier;

/**
 * 编码方式实现限流
 * mxf
 * 2019年08月24日22:19:49
 */
@RestController
public class RateLimiterByCode {

    @Autowired
    private FeignHelloService helloService;

    @GetMapping("/hello6")
    public String hello(String name) {
        RateLimiterConfig config = RateLimiterConfig.custom()
                .limitRefreshPeriod(Duration.ofMillis(5000))
                .limitForPeriod(1)
                .timeoutDuration(Duration.ofMillis(6000))
                .build();
        RateLimiterRegistry rateLimiterRegistry = RateLimiterRegistry.of(config);
        RateLimiter rateLimiter = rateLimiterRegistry.rateLimiter("backendB", config);
        Supplier<String> supplier = RateLimiter.decorateSupplier(rateLimiter, () ->
                helloService.hello(name)
        );
        Try<String> aTry = Try.ofSupplier(supplier);
        return aTry.get();
    }
}
