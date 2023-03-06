package com.github.zhaofanzhe.scaffold.sms;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MockSmsClient implements SmsClient {

    public MockSmsClient() {

    }

    public boolean send(SendSmsParams params) {
        log.info("MockSms: send to phone {} ,params: {}", params.getPhones(), JSONUtil.toJsonStr(params.getTemplateParams()));
        return true;
    }

}
