package com.codesocial.manager.filter;

import com.codesocial.utils.JwtUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
@Component
public class ManagerFilter extends ZuulFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
        String token = request.getHeader("Authorization");
        if(!StringUtils.isEmpty(token) && token.startsWith("Bearer ")) {
            // 解析token, 判断登陆用户是否有admin角色
            Claims claims = jwtUtil.parseJWT(token.substring(7));
            if("admin".equals(claims.get("roles"))) {
                // 放入请求中，转发给具体的微服
                RequestContext.getCurrentContext().addZuulRequestHeader("Authorization", token);
                return null;
            }
        }
        // 拒绝用户的请求, 跳转走了
        RequestContext requestContext = RequestContext.getCurrentContext();
        requestContext.setSendZuulResponse(false); // 不要转发了，中止请求
        requestContext.setResponseStatusCode(401);// 没有权限
        requestContext.setResponseBody("权限不足");
        requestContext.getResponse().setContentType("text/html;charset=utf-8");

        return null;
    }
}
