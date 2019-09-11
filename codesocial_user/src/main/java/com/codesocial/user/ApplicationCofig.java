package com.codesocial.user;

import com.codesocial.user.interceptor.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
//拦截器的配置类
@Configuration
public class ApplicationCofig extends WebMvcConfigurationSupport {
    //添加拦截的接口路径

    @Autowired
    private JwtInterceptor jwtInterceptor;
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        //添加拦截器
        registry.addInterceptor(jwtInterceptor)
                //设置拦截所有
                .addPathPatterns("/**")
                //排除的请求
                .excludePathPatterns("/**/login");
    }
}
