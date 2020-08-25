package com.micwsx.cloud.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Michael
 * @create 8/24/2020 2:28 PM
 *
 */
@Component
public class PreLogFilter extends ZuulFilter {

        /*
    * pre: 这种过滤器在请求被路由之前调用。可利用这种过滤器实现身份验证、在集群中选择请求的微服务，记录调试信息等。
      routing: 这种过滤器将请求路由到微服务。这种过滤器用于构建发送给微服务的请求，并使用apache httpclient或netflix ribbon请求微服务。
      post: 这种过滤器在路由到微服务以后执行。这种过滤器可用来为响应添加标准的http header、收集统计信息和指标、将响应从微服务发送给客户端等。
      error: 在其他阶段发送错误时执行该过滤器。
    *
    * */

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return RequestContext.getCurrentContext().sendZuulResponse();
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        System.out.println("zuul pre filter-->" + request.getRequestURL() + "-->" + request.getMethod());
        return null;
    }
}
