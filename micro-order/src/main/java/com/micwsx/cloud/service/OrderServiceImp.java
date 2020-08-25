package com.micwsx.cloud.service;

import com.micwsx.cloud.bean.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Michael
 * @create 8/18/2020 11:34 AM
 * feign接口实现类
 */
@Service
public class OrderServiceImp implements OrderService {

    private Logger logger= LoggerFactory.getLogger(getClass());

    @Override
    public List<Order> getAllOrders() {
        logger.info("micro-order====getAllOrders");
        List<Order> list = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < random.nextInt(5); i++) {
            String orderId = "Order[" + i + "]";
            Order order = new Order(orderId, "book", i * 10.0);
            list.add(order);
        }
        return list;
    }

    @Override
    public Order getOrderById(String id) {
        logger.info("micro-order====getOrderById("+id+")");
        return new Order(id, "chocolate", 100d);
    }

    @Override
    public Order getOrderByProduct(String product) {
        logger.info("micro-order====getOrderByProduct("+product+")");
        return new Order(product+"[1000]", product, 100d);
    }

    @Override
    public boolean saveOrder(Order order) {
        logger.info("micro-order====saveOrder("+order+")");
        return true;
    }

    @Override
    public int errorMessage(Integer num) {
        logger.info("micro-order====errorMessage("+num+")");
        if (num>100){
            throw new RuntimeException(num+"大于100");
        }
        return num;
    }

    @Override
    public String timeout(Integer milliseconds) {
        logger.info("提供方耗时："+milliseconds);
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "提供方耗时："+milliseconds;
    }
}
