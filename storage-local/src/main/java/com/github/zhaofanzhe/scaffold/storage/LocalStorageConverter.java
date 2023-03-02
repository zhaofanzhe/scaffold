package com.github.zhaofanzhe.scaffold.storage;

import lombok.SneakyThrows;
import org.springframework.core.convert.converter.Converter;
import org.springframework.web.multipart.MultipartFile;

public class LocalStorageConverter implements Converter<Object, LocalStorage> {

    private final LocalStorageFactory localStorageFactory;

    public LocalStorageConverter(LocalStorageFactory localStorageFactory) {
        this.localStorageFactory = localStorageFactory;
    }

    @SneakyThrows
    @Override
    public LocalStorage convert(Object source) {
        //noinspection DuplicatedCode
        try {
            if (source instanceof String storageId) {
                return localStorageFactory.find(storageId);
            } else if (source instanceof MultipartFile file) {
                return localStorageFactory.save(file);
            } else {
                return null;
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw e;
        }
    }

}
