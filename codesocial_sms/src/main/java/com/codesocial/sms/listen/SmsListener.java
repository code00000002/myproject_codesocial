package com.codesocial.sms.listen;

import com.aliyuncs.exceptions.ClientException;
import com.codesocial.utils.SmsUtil;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RabbitListener(queues = "sms")
public class SmsListener {

    @Autowired
    private SmsUtil smsUtil;

    @Value("${aliyun.sms.template_code}")
    private String template_code;

    @Value("${aliyun.sms.sign_name}")
    private String sign_name;

    @RabbitHandler
    // 注意：参数的类型必须与写入消息时的类型一致
    public void sendSms(Map<String,String> paramMap){
        // 获取手机号码
        String mobile = paramMap.get("phone");
        // 验证码
        String code = paramMap.get("code");

        System.out.println(String.format("mobile=%s, code=%s", mobile, code));

        String param = String.format("{\"code\": %s}", code);
        // 发送验证码
        try {
            smsUtil.sendSms(mobile,template_code,sign_name, param);
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}
