package com.micwsx.cloud;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;

/**
 * @author Michael
 * @create 8/13/2020 5:18 PM
 * http://localhost:9990/hystrix
 * 监控@HystrixCommand注解工程（这里是micro-proudct工程）地址: http://localhost:8094/actuator/hystrix.stream
 */
@SpringBootApplication
@EnableHystrixDashboard
public class SpringcloudDashboardApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringcloudDashboardApplication.class, args);
    }
}
