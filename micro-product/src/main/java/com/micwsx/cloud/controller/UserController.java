package com.micwsx.cloud.controller;

import com.micwsx.cloud.bean.ConfigProperties;
import com.micwsx.cloud.bean.Order;
import com.micwsx.cloud.conifg.DataSourceProperties;
import com.micwsx.cloud.service.TicketService;
import com.micwsx.cloud.service.UserService;
import com.netflix.discovery.converters.Auto;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Michael
 * @create 8/12/2020 3:55 PM
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private Logger logger= LoggerFactory.getLogger(getClass());

    @Autowired
    private DataSourceProperties dataSourceProperties;

    @Autowired
    private UserService userService;

    @Autowired
    private TicketService ticketService;

    @RequestMapping("/queryContent")
    public String queryContent() {
        logger.info("读取配置数据：");
//        String env_username = environment.getProperty("spring.datasource.username");
//        logger.info("env_username:"+env_username);
//        logger.info("val_username:"+userName);
//        logger.info("configFromValue->"+configFromValue);
//        logger.info("configFromEvn->"+configFromEvn);

        logger.info("dataSourceProperties->"+dataSourceProperties);
        String s = userService.queryUser();
        return s;
    }

    @RequestMapping(value = {"/queryTicket/{id}"})
    public String queryTicket(@PathVariable(name = "id") Integer id) {
        System.out.println("客户端"+Thread.currentThread().getName()+">>>>调用queryTicket");
        String s = ticketService.queryTicket(id);
        return s;
    }

    @RequestMapping("/saveTicket/{id}")
    public String saveTicket(@PathVariable(name = "id")Integer id) {
        System.out.println("客户端"+Thread.currentThread().getName()+">>>>调用saveTicket("+id+")");
        String s = ticketService.saveTicket(id);
        return s;
    }


    @RequestMapping("/timeout/{milli}")
    public String timeout(@PathVariable(name = "milli")Integer millisecond) {
        long t1 = System.currentTimeMillis();
        String s = userService.timeout(millisecond);
        long t2 = System.currentTimeMillis();
        System.out.println("客户端"+Thread.currentThread().getName()+">>>>timeout耗时："+(t2-t1)+"ms");
        return s;
    }





    public static boolean isAvailable = true;

    /**
     * 模拟客户端是否健康
     *
     * @param can
     */
    @RequestMapping("/db/{can}")
    public void setDB(@PathVariable(name = "can") boolean can) {
        isAvailable = can;
    }
}
