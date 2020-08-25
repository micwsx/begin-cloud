package com.micwsx.cloud.service.feign;

import com.micwsx.cloud.bean.Order;
import com.micwsx.cloud.service.OrderService;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Michael
 * @create 8/18/2020 2:34 PM
 */
@Service
public class OrderServiecFallbackFactory  implements FallbackFactory<OrderService> {

    private Logger logger= LoggerFactory.getLogger(getClass());

    @Override
    public OrderService create(Throwable throwable) {
        logger.warn("<<<<<OrderServiecFallbackFactory.异常对象:"+throwable);
        if (throwable==null)return null;
        String message = throwable.getMessage();
        return new OrderService() {
            @Override
            public List<Order> getAllOrders() {
                logger.warn("<<<<<OrderServiecFallbackFactory.getAllOrders():"+message);
                return null;
            }

            @Override
            public Order getOrderById(String id) {
                logger.warn("<<<<<OrderServiecFallbackFactory.getOrderById():"+message);
                return null;
            }

            @Override
            public Order getOrderByProduct(String product) {
                logger.warn("<<<<<OrderServiecFallbackFactory.getOrderByProduct():"+message);
                return null;
            }

            @Override
            public boolean saveOrder(Order order) {
                logger.warn("<<<<<OrderServiecFallbackFactory.saveOrder():"+message);
                return false;
            }

            @Override
            public int errorMessage(Integer num) {
                logger.warn("<<<<<OrderServiecFallbackFactory.errorMessage():"+message);
                return 0;
            }

            @Override
            public String timeout(Integer milliseconds) {
                logger.warn("<<<<<OrderServiecFallbackFactory.timeout():"+message);
                return null;
            }
        };
    }
}
