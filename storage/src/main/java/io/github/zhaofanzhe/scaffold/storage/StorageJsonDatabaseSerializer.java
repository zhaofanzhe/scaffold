package io.github.zhaofanzhe.scaffold.storage;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class StorageJsonDatabaseSerializer<S extends Storage> extends JsonSerializer<S> {

    public void serialize(S storage, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(storage.getStorageId());
    }

}
