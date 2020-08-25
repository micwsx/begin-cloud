package ribbon.config;

import com.netflix.client.config.CommonClientConfigKey;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import com.netflix.loadbalancer.*;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.netflix.ribbon.RibbonClientName;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author Michael
 * @create 8/14/2020 10:52 AM
 * 这个类最好不要出现在启动类的@ComponentScan扫描范围
 * 如果出现在@ComponentScan扫描访问，那么这个配置类就是每个服务共用的配置了
 */
//@Component
public class LoadBalanceMicroOrderConfig {

    @RibbonClientName
    private String SERVICE_NAME="micro-order";

    @Bean
    public IClientConfig defaultClientConfigImp(){
        DefaultClientConfigImpl config=new DefaultClientConfigImpl();
        config.loadProperties(SERVICE_NAME);
        config.set(CommonClientConfigKey.MaxAutoRetries, 2);
        config.set(CommonClientConfigKey.MaxAutoRetriesNextServer, 2);
        config.set(CommonClientConfigKey.ConnectTimeout, 1000);
        config.set(CommonClientConfigKey.ReadTimeout, 3000);
        config.set(CommonClientConfigKey.OkToRetryOnAllOperations,true);
        return config;
    }

    // 判断服务是否存活
    @Bean
    public IPing iPing(){
        PingUrl pingUrl=new PingUrl();
        pingUrl.setPingAppendString("/ping");
        return pingUrl;
    }

    @Bean
    public IRule ribbonRule(){
//        new RoundRobinRule(); //线性轮询
//        new RetryRule();//可以重试轮询
//        new WeightedResponseTimeRule();//计算权重
//        new BestAvailableRule();//过滤掉故障实例，选择请求数最小实例

        return new RandomRule();
    }


}
