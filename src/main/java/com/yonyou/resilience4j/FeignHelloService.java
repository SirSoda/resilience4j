package com.yonyou.resilience4j;

import com.yonyou.commons.feign.HelloService;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("service-provider")
public interface FeignHelloService extends HelloService {
}
