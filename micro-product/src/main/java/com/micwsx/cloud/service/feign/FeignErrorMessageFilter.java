package com.micwsx.cloud.service.feign;

import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @author Michael
 * @create 8/18/2020 3:36 PM
 * Feign消息过滤器将异常消息再次封装，传到fallbackFactory则可以获取详细异常信息。
 */
@Configuration
public class FeignErrorMessageFilter {

    @Bean
    public ErrorDecoder errorDecoder(){
        return new FeignErrorDecoder();
    }

    /**
     * 服务调用后若状态不是200，则会返回消息传入Feign的ErrorDecoder中
     * {"timestamp":"2020-02-17T14:01:18.080+0000","status":500,"error":"Internal Server Error","message":"/ by zero","path":"/feign/student/errorMessage"}
     * 只有这种方式才能获取所有的被feign包装过的异常信息
     *
     * 这里如果创建的Exception是HystrixBadRequestException
     * 则不会走熔断逻辑，不记入熔断统计
     */
    private class FeignErrorDecoder implements ErrorDecoder{
        private Logger logger = LoggerFactory.getLogger(FeignErrorDecoder.class);

        @Override
        public Exception decode(String s, Response response) {
            RuntimeException exception=null;
            try {
                String  returnMessage = Util.toString(response.body().asReader());
                logger.info(returnMessage);
                exception=new RuntimeException(returnMessage);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return exception;
        }
    }
}
