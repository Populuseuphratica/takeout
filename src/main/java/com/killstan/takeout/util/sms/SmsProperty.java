package com.killstan.takeout.util.sms;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "tencent.sms")
public class SmsProperty {

    // @Value("${tencent.sms.id}")
    private String secretId ;

    // @Value("${tencent.sms.secret}")
    private String secretKey ;

    // @Value("${tencent.sms.appId}")
    private String appId;

    // @Value("${tencent.sms.signName}")
    private String signName;

    // @Value("${tencent.sms.templateId}")
    private String templateId;

    private String endpoint;

}

