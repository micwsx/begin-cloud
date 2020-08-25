package com.micwsx.cloud.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

/**
 * @author Michael
 * @create 8/18/2020 3:03 PM
 */
@Service
public class ScoreServiceImp implements ScoreService {
    private Logger logger= LoggerFactory.getLogger(getClass());
    @Override
    public String queryScore(Integer id) {
        logger.info("========queryScore("+id+")");
        return id+"-"+ UUID.randomUUID().toString().replace("-","");
    }
}
