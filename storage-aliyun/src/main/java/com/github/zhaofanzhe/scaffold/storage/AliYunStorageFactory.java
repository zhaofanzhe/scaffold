package com.github.zhaofanzhe.scaffold.storage;

import cn.hutool.core.util.StrUtil;
import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.comm.Protocol;
import com.aliyun.oss.model.PutObjectRequest;
import com.github.zhaofanzhe.scaffold.storage.secure.StorageId;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.util.Objects;

public class AliYunStorageFactory implements StorageFactory<AliYunStorage> {

    private final AliYunStorageConfig config;

    private final OSS client;

    public AliYunStorageFactory(AliYunStorageConfig aliYunStorageConfig) {
        if (StrUtil.isEmpty(aliYunStorageConfig.getAccessKeyId())) {
            throw new IllegalArgumentException("accessKeyId 不能为空");
        }
        if (StrUtil.isEmpty(aliYunStorageConfig.getSecretAccessKey())) {
            throw new IllegalArgumentException("secretAccessKey 不能为空");
        }
        this.config = aliYunStorageConfig;
        final ClientBuilderConfiguration configuration = new ClientBuilderConfiguration();
        // 配置协议为 HTTPS
        if (Objects.equals(aliYunStorageConfig.getProtocol(), AliYunStorageConfig.Protocol.HTTPS)) {
            configuration.setProtocol(Protocol.HTTPS);
        }
        this.client = new OSSClientBuilder()
                .build(aliYunStorageConfig.getEndpoint(), aliYunStorageConfig.getAccessKeyId(), aliYunStorageConfig.getSecretAccessKey(), configuration);
    }

    @Override
    public AliYunStorage save(File originalFile) throws Throwable {
        if (originalFile == null) return null;

        final StoragePath storagePath = StoragePath.path(originalFile.getName());

        PutObjectRequest putObjectRequest = new PutObjectRequest(config.getBucket(), storagePath.getFilePath().substring(1), originalFile);

        client.putObject(putObjectRequest);

        return new AliYunStorage(client, config, storagePath.getFilePath());
    }

    @Override
    public AliYunStorage save(MultipartFile originalFile) throws Throwable {
        if (originalFile == null) return null;

        final StoragePath storagePath = StoragePath.path(originalFile.getOriginalFilename());

        final InputStream inputStream = originalFile.getInputStream();

        PutObjectRequest putObjectRequest = new PutObjectRequest(config.getBucket(), storagePath.getFilePath().substring(1), inputStream);

        client.putObject(putObjectRequest);

        return new AliYunStorage(client, config, storagePath.getFilePath());
    }

    @Override
    public AliYunStorage find(String storageId) {
        final StorageId _storageId = StorageId.parse(storageId);
        final String path = _storageId.get("path");
        if (StrUtil.isEmpty(path)) {
            return null;
        }
        return new AliYunStorage(client, config, path);
    }

}
