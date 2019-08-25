package com.yonyou.resilience4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HelloController {

    @Autowired
    private RestTemplate restTemplate;
    @GetMapping("/hello7")
    public String hello(String name) {
        // 要配置代理才行
        // 否则 GET request for "http://service-provider/hello": service-provider; nested exception is java.net.UnknownHostException: service-provider
        return restTemplate.getForObject("http://service-provider/hello?name={1}", String.class, name);
    }
}
