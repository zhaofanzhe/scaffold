package io.github.zhaofanzhe.scaffold.storage;

import cn.hutool.core.util.StrUtil;
import com.aliyun.oss.OSS;
import io.github.zhaofanzhe.scaffold.storage.secure.StorageId;
import lombok.SneakyThrows;

import java.util.Date;

/**
 * 阿里云存储对象
 */
public class AliYunStorage implements Storage {

    private final OSS client;

    private final AliYunStorageConfig config;

    private final String path;

    public AliYunStorage(OSS client, AliYunStorageConfig config, String path) {
        this.client = client;
        this.config = config;
        this.path = path;
    }

    private String object() {
        return path.substring(1);
    }

    @SneakyThrows
    @Override
    public boolean exist() {
        try {
            return client.doesObjectExist(config.getBucket(), object());
        } catch (Throwable e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String getStorageId() {
        final StorageId storageId = new StorageId();
        storageId.set("type", "aliyun");
        storageId.set("path", path);
        return storageId.getStorageId();
    }

    @SneakyThrows
    @Override
    public String getUrl() {
        if (StrUtil.isEmpty(path)) {
            return "";
        }
        final Date expiration = new Date(new Date().getTime() + 24 * 3600 * 1000);
        return client.generatePresignedUrl(config.getBucket(), object(), expiration).toString();
    }

    @Override
    public boolean delete() {
        try {
            client.deleteObject(config.getBucket(), object());
        } catch (Throwable e) {
            return false;
        }
        return true;
    }


}
