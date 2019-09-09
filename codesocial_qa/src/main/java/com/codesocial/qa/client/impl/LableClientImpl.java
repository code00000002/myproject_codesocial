package com.codesocial.qa.client.impl;

import com.codesocial.entity.Result;
import com.codesocial.entity.StatusCode;
import com.codesocial.qa.client.LabelClient;
import org.springframework.stereotype.Component;

@Component
public class LableClientImpl implements LabelClient {

    @Override
    public Result findById(String id) {
        return new Result(false, StatusCode.ERROR,"熔断器启动了");
    }
}
