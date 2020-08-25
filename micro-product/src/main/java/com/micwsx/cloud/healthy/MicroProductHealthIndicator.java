package com.micwsx.cloud.healthy;

import com.micwsx.cloud.controller.UserController;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael
 * @create 8/12/2020 5:23 PM
 */
@Configuration
public class MicroProductHealthIndicator implements HealthIndicator {
    @Override
    public Health health() {
        if (UserController.isAvailable){
            return new Health.Builder(Status.UP).build();
        }else{
            return new Health.Builder(Status.DOWN).build();
        }
    }
}
