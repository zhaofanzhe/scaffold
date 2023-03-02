package io.github.zhaofanzhe.scaffold.storage;

import io.github.zhaofanzhe.scaffold.mybatis.AppMapperBuilderCustomizer;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

public class AliYunAppMapperBuilderCustomizer implements AppMapperBuilderCustomizer {

    private final AliYunStorageJsonDeserializer aliYunStorageJsonDeserializer;

    public AliYunAppMapperBuilderCustomizer(AliYunStorageJsonDeserializer aliYunStorageJsonDeserializer) {
        this.aliYunStorageJsonDeserializer = aliYunStorageJsonDeserializer;
    }

    @Override
    public void customize(Jackson2ObjectMapperBuilder builder) {
         builder.deserializerByType(AliYunStorage.class, aliYunStorageJsonDeserializer)
                .serializerByType(AliYunStorage.class, new StorageJsonDatabaseSerializer<>());
    }

}
