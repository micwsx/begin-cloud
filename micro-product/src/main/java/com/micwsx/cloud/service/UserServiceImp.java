package com.micwsx.cloud.service;

import com.netflix.discovery.converters.Auto;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author Michael
 * @create 8/12/2020 3:56 PM
 */
@Service
public class UserServiceImp implements UserService {

    private Logger logger= LoggerFactory.getLogger(getClass());

    @Autowired
    private RestTemplate restTemplate;

    private final String SERVICE_ORDER_NAME="micro-order";

    @HystrixCommand(fallbackMethod ="queryUserFallBack",commandKey = "queryUserCommandKey")
    @Override
    public String queryUser() {
        String url="http://"+SERVICE_ORDER_NAME+"/queryUser";
        String content = restTemplate.getForObject(url, String.class);
        return content;
    }


    // 服务降级方法保证返回结果与目标方法一致
    public String queryUserFallBack(){
        logger.warn("=====queryTicketFallBack=========");
        // 请求失败逻辑：这里可以再次尝试请求
        return null;
    }


    @HystrixCommand(fallbackMethod ="queryTimeoutFallBack",commandKey = "timeoutCommandKey")
    @Override
    public String timeout(Integer second){
        //logger.warn("客户端"+Thread.currentThread().getName()+"ribbon开始调用=====timeOut("+second+")");
        String url="http://"+SERVICE_ORDER_NAME+"/timeout/"+second;
        String content = restTemplate.getForObject(url, String.class);
        return content;
    }

    /**
     * 服务降级方法签名与原方法必须一致
     * @param miliseconds
     * @param throwable，获取ribbon调用异常，有可能为null
     * @return
     */
    public String queryTimeoutFallBack(Integer miliseconds,Throwable throwable){
        String message="empty message";
        if (throwable!=null){
             message = throwable.getMessage();
        }
        logger.warn("=====queryTimeOutFallBack("+miliseconds+")=========异常消息："+message);
        // 请求失败逻辑：这里可以再次尝试请求
        return "请求异常fallBack，参数时间(毫秒)"+miliseconds;
    }

}
