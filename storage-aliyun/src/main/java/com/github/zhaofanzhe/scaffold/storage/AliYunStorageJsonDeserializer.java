package com.github.zhaofanzhe.scaffold.storage;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class AliYunStorageJsonDeserializer extends JsonDeserializer<AliYunStorage> {

    private final AliYunStorageFactory aliYunStorageFactory;

    public AliYunStorageJsonDeserializer(AliYunStorageFactory aliYunStorageFactory) {
        this.aliYunStorageFactory = aliYunStorageFactory;
    }

    public AliYunStorage deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        return aliYunStorageFactory.find(jsonParser.getText().trim());
    }

}
