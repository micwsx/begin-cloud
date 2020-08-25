package com.micwsx.cloud.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author Michael
 * @create 8/13/2020 4:07 PM
 */
@Service
public class TicketServiceImp implements TicketService {

    private Logger logger= LoggerFactory.getLogger(getClass());

    @Autowired
    private RestTemplate restTemplate;

    private final String SERVICE_ORDER_NAME="micro-order";

    /**
     * 同时只允许10个线程并发执行
     * 服务调用失败则执行fallbackMethod方法
     * Command属性
     * execution.isolation.strategy  执行的隔离策略
     * THREAD 线程池隔离策略  独立线程接收请求
     * SEMAPHORE 信号量隔离策略 在调用线程上执行
     * <p>
     * execution.isolation.thread.timeoutInMilliseconds  设置HystrixCommand执行的超时时间，单位毫秒
     * execution.timeout.enabled  是否启动超时时间，true，false
     * execution.isolation.semaphore.maxConcurrentRequests  隔离策略为信号量的时候，该属性来配置信号量的大小，最大并发达到信号量时，后续请求被拒绝
     * <p>
     * circuitBreaker.enabled   是否开启断路器功能
     * circuitBreaker.requestVolumeThreshold  该属性设置在滚动时间窗口中，断路器的最小请求数。默认20，如果在窗口时间内请求次数19，即使19个全部失败，断路器也不会打开
     * circuitBreaker.sleepWindowInMilliseconds    改属性用来设置当断路器打开之后的休眠时间，休眠时间结束后断路器为半开状态，断路器能接受请求，如果请求失败又重新回到打开状态，如果请求成功又回到关闭状态
     * circuitBreaker.errorThresholdPercentage  该属性设置断路器打开的错误百分比。在滚动时间内，在请求数量超过circuitBreaker.requestVolumeThreshold,如果错误请求数的百分比超过这个比例，断路器就为打开状态
     * circuitBreaker.forceOpen   true表示强制打开断路器，拒绝所有请求
     * circuitBreaker.forceClosed  true表示强制进入关闭状态，接收所有请求
     * <p>
     * metrics.rollingStats.timeInMilliseconds   设置滚动时间窗的长度，单位毫秒。这个时间窗口就是断路器收集信息的持续时间。断路器在收集指标信息的时会根据这个时间窗口把这个窗口拆分成多个桶，每个桶代表一段时间的指标，默认10000
     * metrics.rollingStats.numBuckets   滚动时间窗统计指标信息划分的桶的数量，但是滚动时间必须能够整除这个桶的个数，要不然抛异常
     * <p>
     * requestCache.enabled   是否开启请求缓存，默认为true
     * requestLog.enabled 是否打印日志到HystrixRequestLog中，默认true
     *
     * @HystrixCollapser 请求合并
     * maxRequestsInBatch  设置一次请求合并批处理中允许的最大请求数
     * timerDelayInMilliseconds  设置批处理过程中每个命令延迟时间
     * requestCache.enabled   批处理过程中是否开启请求缓存，默认true
     * <p>
     * threadPoolProperties
     * threadPoolProperties 属性
     * coreSize   执行命令线程池的最大线程数，也就是命令执行的最大并发数，默认10
     */
    @HystrixCommand(fallbackMethod = "queryTicketFallBack",
            commandKey = "queryTicketCommandKey",//监控中会显示这个值，并且这个HystrixCommand配置可以根据这个配置
            groupKey = "queryGroupOne",// 若其它方法没有配置threadPoolKey，配置了相同的groupKey，则使用同一线程池。
                                        // 若都配置则threadPoolKey相同，则也使用同一线程池。
    commandProperties = {
            // 信号量最大并发数设置10个
//            @HystrixProperty(name = "execution.isolation.semaphore.maxConcurrentRequests",value = "10"),
//            @HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE"),//信号量
            @HystrixProperty(name = "execution.isolation.strategy",value = "THREAD"),//线程池
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value="100000"),
            @HystrixProperty(name="circuitBreaker.enabled",value = "true"),//是否开启断路器
            @HystrixProperty(name="circuitBreaker.requestVolumeThreshold",value = "10"),//请求次数
            @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds",value = "10000"),//时间窗口期
            @HystrixProperty(name="circuitBreaker.errorThresholdPercentage",value = "60")//失败率达到多少后跳闸
    }
    // 线程池最大并发数
    ,threadPoolKey = "queryTicketThreadPoolKey",threadPoolProperties = {@HystrixProperty(name="coreSize",value = "10")}
    )
    @Override
    public String queryTicket(Integer id) {

        logger.info(Thread.currentThread().getName()+"===queryTicket("+id+")");
        if (id>100){
            throw new RuntimeException("id 大于100");
        }
        String url="http://"+SERVICE_ORDER_NAME+"/queryTicket";
        String ticket = restTemplate.getForObject(url, String.class);
        return ticket;
    }

    // 服务降级方法保证返回结果与目标方法一致
    public String queryTicketFallBack(Integer id){
        logger.warn("=====queryTicketFallBack========="+id);
        // 请求失败逻辑：这里可以再次尝试请求
        return null;
    }

    @HystrixCommand(fallbackMethod ="queryTicketFallBack",commandKey = "saveTicketCommandKey")
    @Override
    public String saveTicket(Integer id) {
        logger.info(Thread.currentThread().getName()+"===saveTicket("+id+")");
        return "saveTicket("+id+")";
    }
}
