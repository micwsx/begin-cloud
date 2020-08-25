package com.micwsx.cloud.conifg;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.context.annotation.Configuration;
import ribbon.config.LoadBalanceMicroOrderConfig;

/**
 * @author Michael
 * @create 8/14/2020 11:05 AM
 * 这个是针对 micro-order服务的 ribbon配置
 */
//@Configuration
//@RibbonClients(value = {
//        @RibbonClient(name = "micro-order",configuration = LoadBalanceMicroOrderConfig.class)
//})
public class RibbonMicroOrderConfig {

}
