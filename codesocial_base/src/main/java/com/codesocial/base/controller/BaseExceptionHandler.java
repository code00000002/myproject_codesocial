package com.codesocial.base.controller;

import com.codesocial.entity.Result;
import com.codesocial.entity.StatusCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice //拦截controller
public class BaseExceptionHandler {

    @ExceptionHandler(Exception.class) //异常拦截
    @ResponseBody
    public Result handlerException(Exception e){
        e.printStackTrace();
        return new Result(false, StatusCode.ERROR, "发生了异常，请联系管理员邮箱");
    }
}
