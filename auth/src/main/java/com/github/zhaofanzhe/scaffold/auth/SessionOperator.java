package com.github.zhaofanzhe.scaffold.auth;

public interface SessionOperator {

    public String getId();

    public <T> T getAttribute(String key);

    public <T> void setAttribute(String key, T value);

    public void removeAttribute(String key);

}
