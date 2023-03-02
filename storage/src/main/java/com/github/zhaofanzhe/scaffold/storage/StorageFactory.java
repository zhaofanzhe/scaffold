package com.github.zhaofanzhe.scaffold.storage;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface StorageFactory<S extends Storage> {

    public S save(File originalFile) throws Throwable;

    public S save(MultipartFile originalFile) throws Throwable;

    public S find(String storageId);

}
