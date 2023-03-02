package com.github.zhaofanzhe.scaffold.auth;

@FunctionalInterface
public interface SearchPermission {
    public boolean search(String code);
}
