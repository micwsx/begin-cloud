package com.micwsx.cloud;

import com.micwsx.cloud.service.TicketService;
import com.micwsx.cloud.service.UserService;
import org.databene.contiperf.PerfTest;
import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @author Michael
 * @create 8/13/2020 4:32 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MicroProductApplication.class)
@WebAppConfiguration
public class MyTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private TicketService ticketService;

    @Rule
    public ContiPerfRule contiPerfRule=new ContiPerfRule();

    // hystrix模拟11个线程并发执行11个请求
    @Test
    @PerfTest(invocations = 11,threads = 11)
    public void hystrixTest(){
        String ticketResult=ticketService.queryTicket(1);
        logger.info(Thread.currentThread().getName()+"得出结果："+ticketResult);
    }
}
