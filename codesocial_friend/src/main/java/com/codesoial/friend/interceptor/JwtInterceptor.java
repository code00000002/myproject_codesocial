package com.codesoial.friend.interceptor;

import com.codesocial.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private JwtUtil jwtUtil;
    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("进入拦截器");
        String header = req.getHeader("Authorization");
        if (null != header && header.startsWith("Bearer ")){
            final String token = header.substring(7);
            Claims claims = jwtUtil.parseJWT(token);
            if (null != claims){
                if ("admin".equals(claims.get("roles"))){
                    req.setAttribute("admin_claims",claims);
                }
                if ("user".equals(claims.get("roles"))){
                    req.setAttribute("user_claims",claims);
                }
            }
        }
        //请求放行
        return true;
    }
}
