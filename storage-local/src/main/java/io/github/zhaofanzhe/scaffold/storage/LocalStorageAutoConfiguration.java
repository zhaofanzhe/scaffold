package io.github.zhaofanzhe.scaffold.storage;

import io.github.zhaofanzhe.scaffold.mybatis.AppJacksonConfig;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({LocalStorageConfig.class})
public class LocalStorageAutoConfiguration {

    /**
     * 存储工厂
     */
    @Bean
    public LocalStorageFactory localStorageFactory(LocalStorageConfig localStorageConfig) {
        return new LocalStorageFactory(localStorageConfig);
    }

    /**
     * 请求时 表单内 文件/字符串 转为 存储对象
     */
    @Bean
    public LocalStorageConverter localStorageConverter(LocalStorageFactory localStorageFactory) {
        return new LocalStorageConverter(localStorageFactory);
    }

    /**
     * 反序列化器
     */
    @Bean
    public LocalStorageJsonDeserializer localStorageJsonDeserializer(LocalStorageFactory localStorageFactory) {
        return new LocalStorageJsonDeserializer(localStorageFactory);
    }

    /**
     * 请求时 json 字段 转为 存储对象
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer localStorageJackson2ObjectMapperBuilderCustomizer(LocalStorageJsonDeserializer localStorageJsonDeserializer) {
        return builder -> builder.deserializerByType(LocalStorage.class, localStorageJsonDeserializer);
    }

    /**
     * 数据库 json字段 序列化/反序列化
     */
    @Bean
    public LocalAppMapperBuilderCustomizer localAppJackson2ObjectMapperBuilderCustomizer(LocalStorageJsonDeserializer localStorageJsonDeserializer) {
        return new LocalAppMapperBuilderCustomizer(localStorageJsonDeserializer);
    }

    /**
     * 数据库 序列化/反序列化
     */
    @Bean
    public LocalStorageTypeHandler localStorageTypeHandler(LocalStorageFactory localStorageFactory) {
        return new LocalStorageTypeHandler(localStorageFactory);
    }

    /**
     * 数据库 序列化/反序列化
     */
    @Bean
    public LocalStoragesTypeHandler aliYunStoragesTypeHandler(AppJacksonConfig config) {
        return new LocalStoragesTypeHandler(config);
    }

}
