package com.micwsx.cloud.service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Michael
 * @create 8/18/2020 3:02 PM
 */
public interface ScoreService {
    String queryScore(Integer id);
}