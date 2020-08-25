package com.micwsx.cloud.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Michael
 * @create 8/24/2020 2:27 PM
 * 查看所有路由：http://localhost:9080/actuator/routes
 * 当路由键值是 /local/**	"forward:/local"
 * 测试：http://localhost:9080/local/queryUser->http://localhost:9080/local/queryUser
 */
@RestController
@RequestMapping("local")
public class LocalController {

    @RequestMapping("/queryUser")
    public String queryUser(){
        System.out.println("zuul->queryUser");
        return "zuul->queryUser";
    }
}
