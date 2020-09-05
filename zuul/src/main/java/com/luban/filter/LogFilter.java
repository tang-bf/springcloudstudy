package com.luban.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.catalina.servlet4preview.http.HttpFilter;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 想要咨询vip课程相关的同学加一下木兰老师QQ：2746251334
 * 想要往期视频的同学加一下安其拉老师QQ：3164703201
 * author：鲁班学院-商鞅老师
 */

@Component
public class LogFilter extends ZuulFilter {






    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {

        return FilterConstants.PRE_DECORATION_FILTER_ORDER+1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String s = ctx.get(FilterConstants.REQUEST_URI_KEY).toString();
        System.out.println(request.getRemoteAddr()+"访问了"+request.getRequestURI()+"路由后的地址:"+s);
        return null;
    }
}
