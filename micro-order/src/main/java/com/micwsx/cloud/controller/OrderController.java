package com.micwsx.cloud.controller;

import com.micwsx.cloud.bean.Order;
import com.micwsx.cloud.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Michael
 * @create 8/18/2020 11:34 AM
 * 订单服务提供方,提供方的请求地址和方法签名必须与feign接口定义一样。
 */
@RestController
@RequestMapping("/feign/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/getAllOrders", method = RequestMethod.GET)
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @RequestMapping(value = "/getOrderById", method = RequestMethod.GET)
    public Order getOrderById(@RequestParam("id") String id) {
        return orderService.getOrderById(id);
    }

    @RequestMapping(value = "/getOrderByProduct/{product}", method = RequestMethod.GET)
    public Order getOrderByProduct(@PathVariable("product") String product) {
        return orderService.getOrderByProduct(product);
    }

    @RequestMapping(value = "/saveOrder", method = RequestMethod.POST)
    public boolean saveOrder(@RequestBody Order order) {
        return orderService.saveOrder(order);
    }

    @RequestMapping(value = "/errorMessage/{num}", method = RequestMethod.GET)
    public int errorMessage(@PathVariable("num") Integer num) {
       return orderService.errorMessage(num);
    }

    @RequestMapping(value = "/timeout/{milli}", method = RequestMethod.GET)
    public  String timeout(@PathVariable("milli") Integer milliseconds){
        return orderService.timeout(milliseconds);
    }



}
