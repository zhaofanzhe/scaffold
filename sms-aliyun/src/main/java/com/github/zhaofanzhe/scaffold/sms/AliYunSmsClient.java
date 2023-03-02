package com.github.zhaofanzhe.scaffold.sms;

import cn.hutool.json.JSONUtil;
import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.teautil.models.RuntimeOptions;

public class AliYunSmsClient implements SmsClient {

    private final Client aliyunClient;

    public AliYunSmsClient(Client aliyunClient) {
        this.aliyunClient = aliyunClient;
    }

    public boolean send(SendSmsParams params) {

        final String phoneNumbers = String.join(",", params.getPhones());

        final String templateParam = JSONUtil.toJsonStr(params.getTemplateParams());

        SendSmsRequest sendSmsRequest = new SendSmsRequest()
                .setPhoneNumbers(phoneNumbers)
                .setSignName(params.getAutograph())
                .setTemplateCode(params.getTemplateCode())
                .setTemplateParam(templateParam);

        RuntimeOptions runtime = new RuntimeOptions();

        try {
            final SendSmsResponse response = aliyunClient.sendSmsWithOptions(sendSmsRequest, runtime);
            if (response.statusCode != 200) {
                return false;
            }
            return response.getBody().getCode().equals("OK");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
