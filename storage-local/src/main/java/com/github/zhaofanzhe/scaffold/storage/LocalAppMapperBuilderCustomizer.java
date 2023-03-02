package com.github.zhaofanzhe.scaffold.storage;

import com.github.zhaofanzhe.scaffold.mybatis.AppMapperBuilderCustomizer;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

public class LocalAppMapperBuilderCustomizer implements AppMapperBuilderCustomizer {

    private final LocalStorageJsonDeserializer localStorageJsonDeserializer;

    public LocalAppMapperBuilderCustomizer(LocalStorageJsonDeserializer localStorageJsonDeserializer) {
        this.localStorageJsonDeserializer = localStorageJsonDeserializer;
    }

    @Override
    public void customize(Jackson2ObjectMapperBuilder builder) {
        builder.deserializerByType(LocalStorage.class, localStorageJsonDeserializer)
                .serializerByType(LocalStorage.class, new StorageJsonDatabaseSerializer<>());
    }
}
