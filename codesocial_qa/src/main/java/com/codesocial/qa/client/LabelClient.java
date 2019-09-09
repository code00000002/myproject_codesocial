package com.codesocial.qa.client;

import com.codesocial.entity.Result;
import com.codesocial.qa.client.impl.LableClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "codesocial-base",fallback = LableClientImpl.class)
public interface LabelClient {

    @RequestMapping(value="/label/{id}", method = RequestMethod.GET)
    Result findById(@PathVariable("id") String id);
}