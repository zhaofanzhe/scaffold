package io.github.zhaofanzhe.scaffold.orderno;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

public class OrderNoGenerator {

    @Value("${order-no-generator.global-prefix}")
    private String globalPrefix;

    private final static int MAX_SEQUENCE_VALUE = 1000000;

    private final StringRedisTemplate redisTemplate;

    public OrderNoGenerator(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    private int nextRedisValue(String key) {
        final Long valueBox = redisTemplate.opsForValue().increment(key);

        if (valueBox == null) throw new RuntimeException("生成订单号异常");

        int value = valueBox.intValue();

        if (value == 1) {
            redisTemplate.expire(key, 1, TimeUnit.SECONDS);
        }

        if (value >= MAX_SEQUENCE_VALUE) {
            return -1;
        }

        return value;
    }

    public String next(int prefix) {
        long timeMillis;
        String key;
        int sequence;

        do {
            timeMillis = System.currentTimeMillis();
            key = prefix + String.valueOf(timeMillis);
            sequence = nextRedisValue(key);
        } while (sequence <= 0);

        long offset = timeMillis % MAX_SEQUENCE_VALUE;

        long finalSequence = (offset + sequence) % MAX_SEQUENCE_VALUE;

        return String.format("%s%s%d%06d", globalPrefix, prefix, timeMillis, finalSequence);
    }

    /**
     * 判断是否包含前缀
     *
     * @param no     订单号
     * @param prefix 前缀
     * @return 是否包含前缀
     */
    public boolean hasPrefix(@NotNull String no, int prefix) {
        return no.startsWith(String.format("%s%d", globalPrefix, prefix));
    }

}
