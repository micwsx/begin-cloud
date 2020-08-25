package com.micwsx.cloud.service.feign;

import com.micwsx.cloud.service.OrderService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Michael
 * @create 8/18/2020 1:12 PM
 * 指定服务提供方应用Eureka中可以获取name值
 */
@FeignClient(name = "MICRO-ORDER",path = "/feign/order",
//        fallback = OrderServiceFallback.class,
fallbackFactory = OrderServiecFallbackFactory.class)
public interface OrderServiceFeign extends OrderService {

}
