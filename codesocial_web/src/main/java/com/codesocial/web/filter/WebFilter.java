package com.codesocial.web.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class WebFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre"; //前置过滤器
    }

    @Override
    public int filterOrder() {
        return 0; //最高优先级，数字越大优先级越低
    }

    @Override
    public boolean shouldFilter() {
        return true; //是否执行过滤器，true
    }

    @Override
    public Object run() throws ZuulException {
        System.out.println("执行了Zuul过滤器。。。");
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        String authorization = request.getHeader("Authorization");
        if (null != authorization){
            currentContext.addZuulRequestHeader("Authorization",authorization);
        }
        return null;
    }
}
