package com.micwsx.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author Michael
 * @create 8/12/2020 2:27 PM
 */
@SpringBootApplication
@EnableEurekaClient
public class MicroOrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(MicroOrderApplication.class, args);
    }
}
