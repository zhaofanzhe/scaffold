package com.github.zhaofanzhe.scaffold.storage;

public interface Storage {

    public boolean exist();

    public String getStorageId();

    public String getUrl();

    public boolean delete();

}
