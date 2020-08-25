package com.micwsx.cloud.service.feign;

import com.micwsx.cloud.bean.Order;
import com.micwsx.cloud.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Michael
 * @create 8/18/2020 2:30 PM
 */
@Service
public class OrderServiceFallback implements OrderService {

    private Logger logger= LoggerFactory.getLogger(getClass());
    @Override
    public List<Order> getAllOrders() {
        logger.warn("<---OrderServiceFallback.getAllOrders()---->");
        return null;
    }

    @Override
    public Order getOrderById(String id) {
        logger.warn("<---OrderServiceFallback.getOrderById("+id+")---->");
        return new Order("0", "empty order",0d);
    }

    @Override
    public Order getOrderByProduct(String product) {
        logger.warn("<---OrderServiceFallback.getOrderByProduct("+product+")---->");
        return new Order("0", "empty product",0d);
    }

    @Override
    public boolean saveOrder(Order order) {
        logger.warn("<---OrderServiceFallback.saveOrder("+order+")---->");
        return false;
    }

    @Override
    public int errorMessage(Integer num) {
        logger.warn("<---OrderServiceFallback.errorMessage("+num+")---->");return 0;
    }

    @Override
    public String timeout(Integer milliseconds) {
        logger.warn("<---OrderServiceFallback.timeout("+milliseconds+")---->");return null;
    }
}
