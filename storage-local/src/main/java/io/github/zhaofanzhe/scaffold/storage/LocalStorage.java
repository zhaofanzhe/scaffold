package io.github.zhaofanzhe.scaffold.storage;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.zhaofanzhe.scaffold.storage.Storage;
import io.github.zhaofanzhe.scaffold.storage.secure.LocalStorageUrl;
import io.github.zhaofanzhe.scaffold.storage.secure.StorageId;

import java.io.File;
import java.time.LocalDateTime;

public class LocalStorage implements Storage {

    private final LocalStorageConfig config;

    private final String path;


    public LocalStorage(LocalStorageConfig config, String path) {
        this.config = config;
        this.path = path;
    }

    @Override
    public boolean exist() {
        return getFile().exists();
    }

    @Override
    public String getStorageId() {
        final StorageId storageId = new StorageId();
        storageId.set("type", "local");
        storageId.set("path", path);
        return storageId.getStorageId();
    }

    @Override
    public String getUrl() {
        final LocalStorageUrl storageId = new LocalStorageUrl();
        storageId.setUrl(config.getBaseUrl() + path);
        storageId.setExpire(LocalDateTime.now().plusMinutes(10));
        return storageId.build();
    }

    @Override
    public boolean delete() {
        return getFile().delete();
    }

    @JsonIgnore
    public File getFile() {
        return new File(config.getPath(), path);
    }

}
