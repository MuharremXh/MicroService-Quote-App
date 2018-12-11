package com.rremi.zuul_edge_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
@EnableEurekaClient
public class ZuulEdgeServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZuulEdgeServerApplication.class, args);
    }
}
