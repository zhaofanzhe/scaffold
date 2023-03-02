package io.github.zhaofanzhe.scaffold.storage;

import lombok.SneakyThrows;
import org.springframework.core.convert.converter.Converter;
import org.springframework.web.multipart.MultipartFile;

/**
 * 阿里云存储自动转换
 * 用于将 表单 中的 文件/storageId,Json中的 storageId 转换为 阿里云储存对象
 */
public class AliYunStorageConverter implements Converter<Object, AliYunStorage> {

    private final AliYunStorageFactory aliYunStorageFactory;

    public AliYunStorageConverter(AliYunStorageFactory aliYunStorageFactory) {
        this.aliYunStorageFactory = aliYunStorageFactory;
    }

    @SneakyThrows
    @Override
    public AliYunStorage convert(Object source) {
        //noinspection DuplicatedCode
        try {
            if (source instanceof String storageId) {
                return aliYunStorageFactory.find(storageId);
            } else if (source instanceof MultipartFile file) {
                return aliYunStorageFactory.save(file);
            } else {
                return null;
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw e;
        }
    }

}
