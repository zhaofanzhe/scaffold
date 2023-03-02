package io.github.zhaofanzhe.scaffold.storage;

import io.github.zhaofanzhe.scaffold.mybatis.AppJacksonConfig;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 阿里云存储自动配置
 */
@Configuration
@EnableConfigurationProperties({AliYunStorageConfig.class})
public class AliYunStorageAutoConfiguration {

    /**
     * 存储工厂
     */
    @Bean
    public AliYunStorageFactory aliYunStorageFactory(AliYunStorageConfig aliYunStorageConfig) {
        return new AliYunStorageFactory(aliYunStorageConfig);
    }

    /**
     * 请求时 表单内 文件/字符串 转为 存储对象
     */
    @Bean
    public AliYunStorageConverter aliYunStorageConverter(AliYunStorageFactory aliYunStorageFactory) {
        return new AliYunStorageConverter(aliYunStorageFactory);
    }

    /**
     * 反序列化器
     */
    @Bean
    public AliYunStorageJsonDeserializer aliYunStorageJsonDeserializer(AliYunStorageFactory aliYunStorageFactory) {
        return new AliYunStorageJsonDeserializer(aliYunStorageFactory);
    }

    /**
     * 请求时 json 字段 转为 存储对象
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer aliyunStorageJackson2ObjectMapperBuilderCustomizer(AliYunStorageJsonDeserializer aliYunStorageJsonDeserializer) {
        return builder -> builder.deserializerByType(AliYunStorage.class, aliYunStorageJsonDeserializer);
    }

    /**
     * 数据库 json字段 序列化/反序列化
     */
    @Bean
    public AliYunAppMapperBuilderCustomizer aliYunStorageMapperBuilderCustomizer(AliYunStorageJsonDeserializer aliYunStorageJsonDeserializer) {
        return new AliYunAppMapperBuilderCustomizer(aliYunStorageJsonDeserializer);
    }

    /**
     * 数据库 序列化/反序列化
     */
    @Bean
    public AliYunStorageTypeHandler aliYunStorageTypeHandler(AliYunStorageFactory aliYunStorageFactory) {
        return new AliYunStorageTypeHandler(aliYunStorageFactory);
    }

    /**
     * 数据库 序列化/反序列化
     */
    @Bean
    public AliYunStoragesTypeHandler aliYunStoragesTypeHandler(AppJacksonConfig config) {
        return new AliYunStoragesTypeHandler(config);
    }

}
