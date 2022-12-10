package com.killstan.takeout.util.sms;

import com.killstan.takeout.util.ConstantUtil;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;
import com.tencentcloudapi.sms.v20210111.models.SendStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j
@Component
public class SendSmsUtil {

    private final SmsProperty smsProperty;

    @Autowired
    public SendSmsUtil(SmsProperty smsProperty) {
        this.smsProperty = smsProperty;
    }

    public boolean send(String phone, String code) {
        //判断手机是否为null
        if (!StringUtils.hasLength(phone)) {
            return false;
        }

        /* 必要步骤：
         * 实例化一个认证对象，入参需要传入腾讯云账户密钥对 secretId 和 secretKey
         * 本示例采用从环境变量读取的方式，需要预先在环境变量中设置这两个值
         * 您也可以直接在代码中写入密钥对，但需谨防泄露，不要将代码复制、上传或者分享给他人
         * CAM 密钥查询：https://console.cloud.tencent.com/cam/capi
         */
        Credential cred = new Credential(smsProperty.getSecretId(), smsProperty.getSecretKey());

        // 实例化一个http选项，可选的，没有特殊需求可以跳过
        // HttpProfile httpProfile = new HttpProfile();
        // httpProfile.setEndpoint(smsProperty.getEndpoint());
        // // 实例化一个client选项，可选的，没有特殊需求可以跳过
        // ClientProfile clientProfile = new ClientProfile();
        // clientProfile.setHttpProfile(httpProfile);

        // 实例化要请求产品的client对象,clientProfile是可选的
        SmsClient client = new SmsClient(cred, "ap-guangzhou");

        // 实例化一个请求对象,每个接口都会对应一个request对象
        SendSmsRequest req = new SendSmsRequest();
        String[] phoneNumberSet1 = {phone};
        req.setPhoneNumberSet(phoneNumberSet1);

        req.setSmsSdkAppId(smsProperty.getAppId());
        req.setSignName(smsProperty.getSignName());
        req.setTemplateId(smsProperty.getTemplateId());

        // 设置模版参数，验证码与限制时间（分钟）
        String[] templateParamSet1 = {code, ConstantUtil.REDIS_CODE_TIME};
        req.setTemplateParamSet(templateParamSet1);

        // 返回的resp是一个SendSmsResponse的实例，与请求对象对应
        SendSmsResponse resp = null;
        try {
            resp = client.SendSms(req);
        } catch (TencentCloudSDKException e) {
            log.error(e.getMessage());
            e.printStackTrace();
            return false;
        }
        SendStatus[] sendStatusSet = resp.getSendStatusSet();
        SendStatus sendStatus = sendStatusSet[0];

        // 发送失败时，记录log
        if (!"Ok".equals(sendStatus.getCode())) {
            log.warn("短信发送失败");
            log.warn("SMS错误码为:" + sendStatus.getCode());
            log.warn("SMS错误码描述为:" + sendStatus.getMessage());
            return false;
        }

        log.info("向号码 " + sendStatus.getPhoneNumber() + " 发送验证码 " + code);
        return true;
    }

}
