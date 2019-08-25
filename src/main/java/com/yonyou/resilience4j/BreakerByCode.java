package com.yonyou.resilience4j;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.vavr.control.Try;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

/**
 * 编码方式实现服务熔断降级
 * mxf
 * 2019年08月24日21:57:02
 */
@RestController
public class BreakerByCode {

    @Autowired
    private FeignHelloService helloService;

    @GetMapping("/hello4")
    public String hello(String name) {
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .waitDurationInOpenState(Duration.ofMillis(1000))
                .ringBufferSizeInHalfOpenState(20)
                .ringBufferSizeInClosedState(20)
                .build();
        CircuitBreakerRegistry circuitBreakerRegistry = CircuitBreakerRegistry.of(circuitBreakerConfig);
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("backendB", circuitBreakerConfig);
        Try<String> supplier = Try.ofSupplier(io.github.resilience4j.circuitbreaker.CircuitBreaker
                .decorateSupplier(circuitBreaker,
                        () -> helloService.hello(name)))
                .recover(Exception.class, "有异常，访问失败!"); // 若抛出异常在recover进行降级处理
        return supplier.get();
    }
}
