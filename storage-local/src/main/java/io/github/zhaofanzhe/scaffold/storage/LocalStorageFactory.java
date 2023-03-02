package io.github.zhaofanzhe.scaffold.storage;

import cn.hutool.core.util.StrUtil;
import io.github.zhaofanzhe.scaffold.storage.StorageFactory;
import io.github.zhaofanzhe.scaffold.storage.secure.StorageId;
import io.github.zhaofanzhe.scaffold.storage.StoragePath;
import io.github.zhaofanzhe.scaffold.storage.secure.LocalStorageUrl;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class LocalStorageFactory implements StorageFactory<LocalStorage> {

    private final LocalStorageConfig config;

    public LocalStorageFactory(LocalStorageConfig config) {
        this.config = config;
    }

    private void mkdirs(String dirPath) {
        // 创建目录
        File dir = new File(config.getPath(), dirPath);
        if (!dir.isDirectory()) {
            //noinspection ResultOfMethodCallIgnored
            dir.mkdirs();
        }
    }

    @Override
    public LocalStorage save(File originalFile) throws IOException {
        if (originalFile == null) return null;

        final StoragePath storagePath = StoragePath.path(originalFile.getName());

        mkdirs(storagePath.getDirPath());

        File file = new File(config.getPath(), storagePath.getFilePath());

        Files.copy(originalFile.toPath(), file.toPath());

        return new LocalStorage(config, storagePath.getFilePath());
    }

    @Override
    public LocalStorage save(MultipartFile originalFile) throws IOException {
        if (originalFile == null) return null;

        final StoragePath storagePath = StoragePath.path(originalFile.getOriginalFilename());

        mkdirs(storagePath.getDirPath());

        File file = new File(config.getPath(), storagePath.getFilePath());

        originalFile.transferTo(file);

        return new LocalStorage(config, storagePath.getFilePath());
    }

    @Override
    public LocalStorage find(String storageId) {
        final StorageId _storageId = StorageId.parse(storageId);
        final String path = _storageId.get("path");
        if (StrUtil.isEmpty(path)) {
            return null;
        }
        return new LocalStorage(config, path);
    }

    public LocalStorage findUrl(String originalUrl) {
        final LocalStorageUrl localStorageUrl = LocalStorageUrl.parse(originalUrl);
        if (!localStorageUrl.verifySign()) {
            return null;
        }
        if (!localStorageUrl.verifyExpire()) {
            return null;
        }
        String url = localStorageUrl.getUrl();
        if (!url.startsWith(config.getBaseUrl())) {
            return null;
        }
        url = url.substring(config.getBaseUrl().length());
        return new LocalStorage(config, url);
    }

}
