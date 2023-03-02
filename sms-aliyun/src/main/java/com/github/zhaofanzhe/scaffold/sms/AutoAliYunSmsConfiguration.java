package com.github.zhaofanzhe.scaffold.sms;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.teaopenapi.models.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AutoAliYunSmsConfiguration {

    @Bean
    public AliYunSmsConfig aliYunSmsConfig() {
        return new AliYunSmsConfig();
    }

    private Client aliyunClient(AliYunSmsConfig aliYunSmsConfig) throws Exception {
        Config config = new Config()
                .setAccessKeyId(aliYunSmsConfig.getAccessKeyId())
                .setAccessKeySecret(aliYunSmsConfig.getAccessKeySecret());
        config.endpoint = "dysmsapi.aliyuncs.com";
        return new Client(config);
    }

    @Bean
    public AliYunSmsClient aliYunSmsClient(AliYunSmsConfig aliYunSmsConfig) throws Exception {
        return new AliYunSmsClient(aliyunClient(aliYunSmsConfig));
    }

}
