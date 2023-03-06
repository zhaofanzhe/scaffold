package com.github.zhaofanzhe.scaffold.sms;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AutoMockSmsConfiguration {

    @Bean
    public MockSmsClient mockSmsClient() throws Exception {
        return new MockSmsClient();
    }

    @Bean
    public SmsClient smsClient(MockSmsClient mockSmsClient) throws Exception {
        return mockSmsClient;
    }

}
