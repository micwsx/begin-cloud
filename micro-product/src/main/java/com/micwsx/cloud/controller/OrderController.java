package com.micwsx.cloud.controller;

import com.micwsx.cloud.bean.Order;
import com.micwsx.cloud.service.feign.OrderServiceFeign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Michael
 * @create 8/18/2020 11:24 AM
 * 使用公共接口feign调用
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private OrderServiceFeign orderServiceFeign;

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public List<Order> getAll() {

        logger.info("=====getAll()");
        List<Order> allOrders = orderServiceFeign.getAllOrders();
        return allOrders;
    }

    @RequestMapping(value = "/getById", method = RequestMethod.GET)
    public Order getById(@RequestParam("id") String id) {
        logger.info("=====getById(" + id + ")");
        return orderServiceFeign.getOrderById(id);
    }

    @RequestMapping(value = "/getByProduct/{product}", method = RequestMethod.GET)
    public Order getByProduct(@PathVariable("product") String product) {
        logger.info("=====getByProduct(" + product + ")");
        return orderServiceFeign.getOrderByProduct(product);
    }

    /**
     * @param order:接收Order对象json字符串
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public boolean save(@RequestBody Order order) {
        logger.info("=====saveOrder(" + order + ")");
        return orderServiceFeign.saveOrder(order);
    }

    @RequestMapping(value = "/error/{num}", method = RequestMethod.GET)
    public int errorMessage(@PathVariable("num") Integer num) {
        logger.info("=====errorMessage(" + num + ")");
        return orderServiceFeign.errorMessage(num);
    }

    @RequestMapping(value = "/timeout/{milli}", method = RequestMethod.GET)
    public String timeout(@PathVariable("milli") Integer milliseconds) {
        long t1 = System.currentTimeMillis();
        String content = orderServiceFeign.timeout(milliseconds);
        long t2 = System.currentTimeMillis();
        logger.info("=====调用方耗时：" + (t2 - t1) + "ms");
        return content;
    }


}
