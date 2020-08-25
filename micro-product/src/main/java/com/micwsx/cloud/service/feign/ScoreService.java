package com.micwsx.cloud.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Michael
 * @create 8/18/2020 10:45 AM
 * 定义feign服务接口
 */
@FeignClient(name = "MICRO-ORDER", path = "/feign/score")
public interface ScoreService {

    @GetMapping("/getScore/{id}")
    String queryScore(@PathVariable("id") Integer id);
}
