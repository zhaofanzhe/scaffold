package com.github.zhaofanzhe.scaffold.orderno;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
public class OrderNoAutoConfiguration {

    @Bean
    public OrderNoGenerator orderNoGenerator(StringRedisTemplate redisTemplate) {
        return new OrderNoGenerator(redisTemplate);
    }

}
