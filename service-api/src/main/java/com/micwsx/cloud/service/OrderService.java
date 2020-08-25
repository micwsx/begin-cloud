package com.micwsx.cloud.service;

import com.micwsx.cloud.bean.Order;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Michael
 * @create 8/18/2020 11:12 AM
 * feign接口暴露调用方请求接口和地址
 */
public interface OrderService {

    @GetMapping("/getAllOrders")//这个地址与提供者定义地址必须保持一样
    List<Order> getAllOrders();

    @GetMapping("/getOrderById")
    Order getOrderById(@RequestParam("id") String id);

    @GetMapping("/getOrderByProduct/{product}")
    Order getOrderByProduct(@PathVariable("product") String product);

    @PostMapping("/saveOrder")
    boolean saveOrder(@RequestBody Order order);

    @GetMapping("/errorMessage/{num}")
    int errorMessage(@PathVariable("num") Integer num);

    @GetMapping("/timeout/{milli}")
    String timeout(@PathVariable("milli") Integer milliseconds);


}

