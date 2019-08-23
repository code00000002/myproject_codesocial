package com.codesocial.spit.controller;

import com.codesocial.entity.Result;
import com.codesocial.entity.StatusCode;
import com.codesocial.exception.MyException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class BaseExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result error(Exception e){
        e.printStackTrace();
        return new Result(false, StatusCode.ERROR, "执行出错");
    }

    @ExceptionHandler(value = MyException.class)
    @ResponseBody
    public Result error(MyException e){
        e.printStackTrace();
        return new Result(false, StatusCode.ERROR, e.getMessage());
    }
}
