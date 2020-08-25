package com.micwsx.cloud.controller;

import com.micwsx.cloud.service.feign.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Michael
 * @create 8/18/2020 3:08 PM
 */
@RestController
@RequestMapping("/score")
public class ScoreController {

    @Autowired
    private ScoreService scoreService;

    @RequestMapping("/getScore/{id}")
    public String getScore(@PathVariable("id") Integer id){
        String s = scoreService.queryScore(id);
        return s;
    }
}
