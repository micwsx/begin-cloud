package com.micwsx.cloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Michael
 * @create 8/12/2020 2:34 PM
 * hystrix/ribbon方式调用
 */
@RefreshScope
@RestController
public class UserController {

    private Logger logger= LoggerFactory.getLogger(getClass());

    @Value("${config.datasource.driverClassName}")
    private String driverName;
    @Value("${config.datasource.url}")
    private String url;
    @Value("${config.datasource.name}")
    private String dbName;
    @Value("${config.datasource.username}")
    private String userName;
    @Value("${config.datasource.password}")
    private String password;
    @Value("${config.datasource.minPoolSize}")
    private String minPoolSize;
    @Value("${config.datasource.maxPoolSize}")
    private String maxPoolSize;
    @Value("${config.datasource.maxIdleTime}")
    private String maxIdleTime;

    @Autowired
    private Environment environment;


    @RequestMapping("/queryUser")
    public String queryUser(){

        logger.info("读取配置数据：");
        String env_username = environment.getProperty("config.datasource.username");
        logger.info("env_username:"+env_username);
        logger.info("val_username:"+userName);

        System.out.println("micro-order ==== queryUser");
        return "micro-order===queryUser";
    }

    @RequestMapping("/queryTicket")
    public String queryTicket(){
        System.out.println("micro-order ==== queryTicket");
        return "micro-order===queryTicket";
    }

    @RequestMapping("/timeout/{milli}")
    public String timeout(@PathVariable("milli") Integer milliseconds){
        System.out.println("micro-order ==== timeout("+milliseconds+")");
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "micro-order===timeout("+milliseconds+")";
    }



    @RequestMapping("/ping")
    public String ping(){
        System.out.println("micro-order ==== ping");
        return "micro-order===ping";
    }

}
