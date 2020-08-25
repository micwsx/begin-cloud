package com.micwsx.cloud;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author Michael
 * @create 8/24/2020 5:01 PM
 */
@SpringBootApplication
@EnableEurekaClient
@EnableAdminServer
public class SpringcloudAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringcloudAdminApplication.class, args);
    }

}
