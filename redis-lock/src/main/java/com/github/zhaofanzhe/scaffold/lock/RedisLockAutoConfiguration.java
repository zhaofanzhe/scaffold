package com.github.zhaofanzhe.scaffold.lock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.integration.redis.util.RedisLockRegistry;

@Import({
        RedisLockAspect.class,
})
@Configuration
public class RedisLockAutoConfiguration {

    @Bean
    public RedisLockRegistry redisLockRegistry(RedisConnectionFactory redisConnectionFactory) {
        // 2分钟
        return new RedisLockRegistry(redisConnectionFactory, "Lock", 120000L);
    }

}
