package com.github.zhaofanzhe.scaffold.storage;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class LocalStorageJsonDeserializer extends JsonDeserializer<LocalStorage> {

    private final LocalStorageFactory localStorageFactory;

    public LocalStorageJsonDeserializer(LocalStorageFactory localStorageFactory) {
        this.localStorageFactory = localStorageFactory;
    }

    public LocalStorage deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        return localStorageFactory.find(jsonParser.getText().trim());
    }

}
